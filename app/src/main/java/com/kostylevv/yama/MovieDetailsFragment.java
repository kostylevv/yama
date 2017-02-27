package com.kostylevv.yama;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.IOException;


/**
 * Created by vkostylev on 05/01/16.
 *
 * Fragment which shows details of the movie selected.
 *
 */

public class MovieDetailsFragment extends Fragment {
    private static final String EXTRA_MOVIE = "extramovie";

    Movie mCurrentMovie = null;
    TextView movieTitle;
    TextView releaseDate;
    TextView voteAverage;
    TextView overview;
    ImageView poster;
    View view;
    View titleBlock;
    TextView duration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mCurrentMovie = savedInstanceState.getParcelable(EXTRA_MOVIE);
        }

        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.details, container, false);
        movieTitle = (TextView) view.findViewById(R.id.details_movie_title);
        releaseDate = (TextView) view.findViewById(R.id.details_release_date);
        voteAverage = (TextView) view.findViewById(R.id.details_rating);
        overview = (TextView) view.findViewById(R.id.details_plot);
        poster = (ImageView) view.findViewById(R.id.details_poster);
        titleBlock = (LinearLayout) view.findViewById(R.id.llTitleBlock);
        duration = (TextView) view.findViewById(R.id.details_movie_length);//TODO



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        Bundle args = getArguments();
        if (args != null) {
            // Set movieTitle based on argument passed in
            updateMovieDetails((Movie) args.getParcelable(EXTRA_MOVIE));
        } else if (mCurrentMovie != null) {
            // Set movieTitle based on saved instance state defined during onCreateView
            updateMovieDetails(mCurrentMovie);
        }
    }

    public void updateMovieDetails(Movie movie) {
        movieTitle.setText(movie.getTitle());
        releaseDate.setText("Release: " + movie.getReleaseDate());
        voteAverage.setText("Rated: " + movie.getVote_average());
        overview.setText(movie.getOverview());
        Picasso
                .with(getContext())
                .load(movie.getPoster())
                .into(poster);





        poster.setBackgroundColor(movie.getBackgroundColor());
        movieTitle.setTextColor(movie.getTextColor());
        titleBlock.setBackgroundColor(movie.getBackgroundColor());
        duration.setTextColor(movie.getTextColor());



        mCurrentMovie = movie;
        Log.v("MDET", movie.getTitle());
    }

    public static MovieDetailsFragment newInstance(Movie movie) {
        MovieDetailsFragment f = new MovieDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_MOVIE, movie);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_MOVIE, mCurrentMovie);
    }
}
