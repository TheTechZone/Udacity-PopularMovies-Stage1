package com.example.techz.popularmovies_stage1.utils;


import android.net.Uri;

import com.example.techz.popularmovies_stage1.BuildConfig;

public class NetworkUtils {

    private static final String SCHEME = "https";
    private static final String BASE_URL = "api.themoviedb.org";
    private static final String API_VERSION = "3";
    private static final String OPTION = "discover";
    private static final String MEDIA_TYPE = "movie";
    private static final String QUERY_PARAM = "sort_by";
    private static final String API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;

    public static String buildUrl(String sortType) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(BASE_URL)
                .appendPath(API_VERSION)
                .appendPath(OPTION)
                .appendPath(MEDIA_TYPE)
                .appendQueryParameter(QUERY_PARAM, sortType)
                .appendQueryParameter("api_key", API_KEY);
        return builder.build().toString();
    }

}
