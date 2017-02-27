package com.kostylevv.yama;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by vkostylev on 30/01/2017.
 */
public class MoviesFragment
        extends Fragment
        implements
        LoaderManager.LoaderCallbacks<List<Movie>>,
        MoviesAdapter.MoviesAdapterOnClickHandler {

    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.movies, null);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        GridLayoutManager layoutManager
                = new GridLayoutManager(getContext(), 2);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(getContext(), this);

        mRecyclerView.setAdapter(mMoviesAdapter);

        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        String sortBy = getString(preferences.getInt(getString(R.string.sort_preference), R.string.sort_most_popular_def));

        Bundle bundleForLoader = new Bundle();
        bundleForLoader.putString(getString(R.string.sort_preference), sortBy);

        LoaderManager.LoaderCallbacks<List<Movie>> callback = this;

        getActivity().getSupportLoaderManager().initLoader(MOVIES_LOADER_ID, bundleForLoader, callback);

        return rootView;
    }

    @Override
    public void onClick(Movie movie) {
        Log.v("SELCAL", movie.getTitle());
        mMovieSelectedListener.onMovieSelected(movie);
    }


    @Override
    public Loader<List<Movie>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<Movie>>(getContext()) {

            List<Movie> mMovieData = null;

            @Override
            protected void onStartLoading() {
                if (mMovieData != null) {
                    deliverResult(mMovieData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<Movie> loadInBackground() {

                try {
                    List<Movie> result = MovieFetcher.fetch(getContext(), args.getString(getString(R.string.sort_preference)));
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(List<Movie> data) {
                mMovieData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        mMoviesAdapter.setMoviesData(data);
    }


    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }

    public void reload(String sortBy) {
        Activity activity = getActivity();
        if(activity != null && isAdded()) {
            Bundle bundleForLoader = new Bundle();
            bundleForLoader.putString(getString(R.string.sort_preference), sortBy);

            LoaderManager.LoaderCallbacks<List<Movie>> callback = this;

            getActivity().getSupportLoaderManager().restartLoader(MOVIES_LOADER_ID, bundleForLoader, callback);
        }
    }


    public interface OnMovieSelectedListener {
        public void onMovieSelected(Movie movie);
    }

    public void setOnMovieSelectedListener(OnMovieSelectedListener listener) {
        mMovieSelectedListener = listener;
    }


    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private static final int MOVIES_LOADER_ID = 42;
    private OnMovieSelectedListener mMovieSelectedListener;
}

