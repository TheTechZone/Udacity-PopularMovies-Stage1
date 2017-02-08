package com.example.techz.popularmovies_stage1.utils;


import android.net.Uri;

import com.example.techz.popularmovies_stage1.BuildConfig;

public class Movie {

    public String title;
    public String poster;
    public String synopsis;
    public String userRating;
    public String releaseDate;

    public String API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;

    public Movie(String title, String poster, String synopsis, String userRating, String releaseDate) {
        this.title = title;
        this.poster = poster;
        this.synopsis = synopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public String getPoster(String size) {
        return new Uri.Builder()
                .scheme("https")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath(size)
                .appendEncodedPath(poster)
                .build()
                .toString();
    }

}