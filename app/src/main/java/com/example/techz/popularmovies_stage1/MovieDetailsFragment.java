package com.example.techz.popularmovies_stage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MovieDetailsFragment extends Fragment {
    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mParentView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String poster = intent.getStringExtra("poster");
            String synopsis = intent.getStringExtra("synopsis");
            String userRating = intent.getStringExtra("userRating");
            String releaseDate = intent.getStringExtra("releaseDate");

            TextView titleTextView = (TextView) mParentView.findViewById(R.id.movie_details_title);
            titleTextView.setText(title);

            ImageView posterImageView = (ImageView) mParentView.findViewById(R.id.movie_details_poster);
            Picasso.with(getContext()).load(poster).into(posterImageView);

            TextView synopsisTextView = (TextView) mParentView.findViewById(R.id.movie_details_synopsis);
            synopsisTextView.setText(synopsis);

            TextView userRatingTextView = (TextView) mParentView.findViewById(R.id.movie_details_user_rating);
            userRatingTextView.setText(R.string.rating_text);
            userRatingTextView.append(" " + userRating + " / 10");

            TextView releaseDateTextView = (TextView) mParentView.findViewById(R.id.movie_details_release_date);
            releaseDateTextView.setText(R.string.release_date_text);
            releaseDateTextView.append(" " + formatDate(releaseDate));

        }

        return mParentView;
    }

    public String formatDate(String dateString) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        String formattedDateString = null;
        try {
            date = format1.parse(dateString);
            DateFormat format2 = new SimpleDateFormat("MMM dd, yyyy");
            formattedDateString = format2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDateString;
    }
}
