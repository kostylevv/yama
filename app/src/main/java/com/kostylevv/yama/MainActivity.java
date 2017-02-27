package com.kostylevv.yama;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity
        extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener,
        MoviesFragment.OnMovieSelectedListener {

    private static final String EXTRA_MOVIE = "extramovie";
    private static SharedPreferences preferences;
    private MoviesFragment moviesFragment;
    private MovieDetailsFragment detailsFragment;
    private boolean mIsDualPane;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        moviesFragment = (MoviesFragment) getSupportFragmentManager().findFragmentById(
                R.id.movies_fragment);

        detailsFragment = (MovieDetailsFragment) getSupportFragmentManager().findFragmentById(
                R.id.details_fragment);

        // Determine whether we are in single-pane or dual-pane mode by testing the visibility
        // of the article view.
        View metarView = findViewById(R.id.details_fragment);
        mIsDualPane = metarView != null && metarView.getVisibility() == View.VISIBLE;

        // Register ourselves as the listener for the headlines fragment events.
        moviesFragment.setOnMovieSelectedListener(this);

        preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        //Init adapter for sorting spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sorting_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Init sorting spinner
        MenuItem item = menu.findItem(R.id.spinner_menu_item);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinner.setAdapter(adapter);

        //Get preferred sorting value, if present
        long  sortPref = preferences.getInt(getString(R.string.sort_preference), R.string.sort_most_popular_def);

        //Set spinner selection accordingly
        if (sortPref == R.string.sort_most_popular_def) {
            spinner.setSelection(0);
        } else {
            spinner.setSelection(1);
        }

        //Attach listener to spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            //If spinner is selected, write preferences accordingly
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                SharedPreferences.Editor editor = preferences.edit();

                //pos == 0 means that "Most Popular" (default) is selected, if not - "Highest Rated",
                //since we have only two options.
                if (pos == 0) {
                    editor.putInt(getString(R.string.sort_preference), R.string.sort_most_popular_def);
                } else {
                    editor.putInt(getString(R.string.sort_preference), R.string.sort_highest_rated);
                }
                editor.commit();
            }

            //Nothing to do here
            @Override
            public void onNothingSelected(AdapterView<?> parent){}
        });

        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        String sortBy = getString(sharedPreferences.getInt(getString(R.string.sort_preference),
                R.string.sort_most_popular_def));

        moviesFragment.reload(sortBy);

    }

    @Override
    public void onMovieSelected(Movie movie) {
        if (mIsDualPane) {
            detailsFragment.updateMovieDetails(movie);
        }
        else {
            // use separate activity
            Log.v("ATGC", "Use sep act");
            Intent i = new Intent(this, DetailsActivity.class);
            i.putExtra(EXTRA_MOVIE, movie);
            i.putExtra("extrastr", "test");
            startActivity(i);
        }
    }
}
