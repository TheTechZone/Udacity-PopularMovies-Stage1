package com.example.techz.popularmovies_stage1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MovieDetailsActivity extends AppCompatActivity {

    MovieDetailsFragment mMovieDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar appBar = (Toolbar) findViewById(R.id.toolbar_movie_details);
        setSupportActionBar(appBar);
        setTitle(R.string.movie_details);
        mMovieDetailsFragment = (MovieDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_movie_details);
    }

}
