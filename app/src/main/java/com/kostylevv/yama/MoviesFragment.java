package com.kostylevv.yama;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by vkostylev on 30/01/2017.
 */
public class MoviesFragment extends Fragment
        implements
        LoaderManager.LoaderCallbacks<List<Movie>> {


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

        mMoviesAdapter = new MoviesAdapter(getContext());

        mRecyclerView.setAdapter(mMoviesAdapter);

        LoaderManager.LoaderCallbacks<List<Movie>> callback = this;

        getActivity().getSupportLoaderManager().initLoader(MOVIES_LOADER_ID, null, callback);

        return rootView;
    }


    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
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
                    List<Movie> result = FetchMoviesTask.fetch();
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

    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private static final int MOVIES_LOADER_ID = 42;
}

