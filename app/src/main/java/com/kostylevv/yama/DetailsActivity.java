package com.kostylevv.yama;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


public class DetailsActivity extends FragmentActivity {
    // The news category index and the article index for the article we are to display
    Movie mMovie;
    private static final String EXTRA_MOVIE = "extramovie";


    /**
     * Sets up the activity.
     *
     * Setting up the activity means reading the category/article index from the Intent that
     * fired this Activity and loading it onto the UI. We also detect if there has been a
     * screen configuration change (in particular, a rotation) that makes this activity
     * unnecessary, in which case we do the honorable thing and get out of the way.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String x = getIntent().getStringExtra("extrastr");

        if (mMovie == null) {
            Log.v("TGADFsfasdf", "mMovie==null");

        } else {
            Log.v("TGADFsfasdf", mMovie.toString());
        }

        if (x == null) {
            Log.v("TGADFsfasdf-R", "extrastr==null");

        } else {
            Log.v("TGADFsfasdf-R", x);
        }



        // Place an ArticleFragment as our content pane
        MovieDetailsFragment f = MovieDetailsFragment.newInstance(mMovie);


        getSupportFragmentManager().beginTransaction().add(android.R.id.content, f).commit();

        // Display the correct news article on the fragment
    }
}


