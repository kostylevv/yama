package com.kostylevv.yama;

import android.net.Uri;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class used to request MBD API, parse JSON request and return list of movies
 */
public class FetchMoviesTask {
    /**
     *
     * @param - sort param value
     * @return
     */
    public static List<Movie> fetch(String... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String moviesJsonString = null;

        // Will contain the result
        List<Movie> result = new ArrayList<Movie>();

        //Movie.setSortBy(params[0]);//FIXME

        try {

            //Base URL for MDB API
            final String BASE_URL_PARAM = "http://api.themoviedb.org/3/discover/movie";

            //API KEY should be in file "secrets.properties" in the app root folder
            final String API_KEY_PARAM = "api_key";

            //Sort parameter name
            final String SORT_NAME = "sort_by";

            Uri.Builder buildUri = Uri.parse(BASE_URL_PARAM).buildUpon()
                    //.appendQueryParameter(SORT_NAME, params[0]) // FIXME: 14/02/2017
                    .appendQueryParameter(SORT_NAME, "popularity.desc")
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY);

            URL url = new URL(buildUri.toString());

            // Create the request to the MDB, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            moviesJsonString = buffer.toString();
            result = getMovieDataFromJSON(moviesJsonString);

        } catch (IOException e) {
            // If the code didn't successfully get the movie data,
            // there's no point in attemping to parse it.
            return null;
        } catch (JSONException e) {
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     *
     * @param moviesJsonString - JSON which contains movie data
     * @return - list of Movie objects
     * @throws JSONException
     */
    private static List<Movie> getMovieDataFromJSON(String moviesJsonString)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        // "Root JSON array"
        final String MDB_RESULTS = "results";
        // Movie title
        final String MDB_TITLE = "original_title";

        // Poster path
        final String MDB_POSTER = "poster_path";

        // Plot synopsis
        final String MDB_PLOT = "overview";

        // Vote avg
        final String MDB_VOTE_AVG = "vote_average";

        // Release date
        final String MDB_RELEASE_DATE = "release_date";


        // Base URL for poster images
        final String MDB_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185//";


        JSONObject moviesJson = new JSONObject(moviesJsonString);
        JSONArray moviesArray = moviesJson.getJSONArray(MDB_RESULTS);

        List<Movie> result = new ArrayList<Movie>();


        for (int i = 0; i < moviesArray.length(); i++) {

            Movie movie = new Movie();

            JSONObject movieJson = moviesArray.getJSONObject(i);
            Log.v("TGA", moviesJson.toString());

            movie.setTitle(movieJson.getString(MDB_TITLE));
            movie.setPoster(MDB_POSTER_BASE_URL + movieJson.getString(MDB_POSTER));
            movie.setOverview(movieJson.getString(MDB_PLOT));
            movie.setReleaseDate(movieJson.getString(MDB_RELEASE_DATE));
            movie.setVote_average(movieJson.getDouble(MDB_VOTE_AVG));

            result.add(movie);

        }
        return result;
    }
}



