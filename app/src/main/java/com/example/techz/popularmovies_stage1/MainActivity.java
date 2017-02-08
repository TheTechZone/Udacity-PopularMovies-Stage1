package com.example.techz.popularmovies_stage1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MovieFragment mMovieFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar appbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(R.id.app_title);
        setSupportActionBar(appbar);

        mMovieFragment = (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_movie);
    }
    //Inflating the Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenuItemId = item.getItemId();

        if (selectedMenuItemId == R.id.action_sort) {
            if (getSortPref().equals("popularity.desc")) {
                setSortPref("vote_average.desc");
                Toast.makeText(this, "Sorting by rating", Toast.LENGTH_SHORT).show();
            } else {
                setSortPref("popularity.desc");
                Toast.makeText(this, "Sorting by popularity", Toast.LENGTH_SHORT).show();
            }
            mMovieFragment.update();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setSortPref(String sortType) {
        SharedPreferences mSharedPref = getSharedPreferences("sort_type", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPref.edit();
        mEditor.putString("movie_sort_type", sortType);
        mEditor.apply();
    }

    public String getSortPref() {
        SharedPreferences mSharedPref = getSharedPreferences("sort_type", MODE_PRIVATE);
        return mSharedPref.getString("movie_sort_type", "popularity");
    }
}
