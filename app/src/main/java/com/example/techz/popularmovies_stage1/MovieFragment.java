package com.example.techz.popularmovies_stage1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.techz.popularmovies_stage1.utils.Movie;
import com.example.techz.popularmovies_stage1.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieFragment extends Fragment {

    public View mParentView;
    public TextView mErrorTextView;
    public RecyclerView mMoviesRecyclerView;


    public static Movie[] movies;
    public String[] posters;
    public String jsonData;
    public String sortBy;

    public MovieFragment() {
        // Required empty public constructor
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.fragment_movie, container, false);
        mErrorTextView = (TextView) mParentView.findViewById(R.id.fragment_error);
        mMoviesRecyclerView = (RecyclerView) mParentView.findViewById(R.id.rv_movies);

        Context context = mParentView.getContext();
        sortBy = getSortPref();

        startFetchMovies(sortBy);

        return mParentView;
    }


    public String getSortPref() {
        SharedPreferences mSharedPref = this.getActivity().getSharedPreferences("sort_type", Context.MODE_PRIVATE);
        return mSharedPref.getString("movie_sort_type", "popularity");
    }

    public void update() {
        startFetchMovies(getSortPref());
    }

    public void setupRecyclerView(Context context) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mMoviesRecyclerView.setLayoutManager(gridLayoutManager);
        PosterAdapter mPosterAdapter = new PosterAdapter(context, posters);
        mMoviesRecyclerView.setAdapter(mPosterAdapter);

    }

    public void startFetchMovies(String sortBy) {
        String url = NetworkUtils.buildUrl(sortBy);
        MovieQueryTask getData = new MovieQueryTask();
        getData.execute(url);

    }

    public void fetchMovies() {

        if (jsonData != null) {
            try {
                movies = parseJsonData(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (movies != null) {
            for (int i = 0; i < movies.length; i++) {
            }

            posters = new String[movies.length];
            for (int i = 0; i < movies.length; i++) {
                posters[i] = movies[i].getPoster("w342");
            }
            setupRecyclerView(getContext());
        }
    }


    private Movie[] parseJsonData(String JsonResponse) throws JSONException {
        JSONObject JsonData = new JSONObject(JsonResponse);
        JSONArray JsonResults = JsonData.getJSONArray("results");

        Movie[] movies = new Movie[JsonResults.length()];
        for (int i = 0; i < JsonResults.length(); i++) {
            //Populate the array
            JSONObject movie = new JSONObject(JsonResults.getString(i));
            String title = movie.getString("original_title");
            String poster = movie.getString("poster_path");
            String synopsis = movie.getString("overview");
            String userRating = movie.getString("vote_average");
            String releaseDate = movie.getString("release_date");
            movies[i] = new Movie(title, poster, synopsis, userRating, releaseDate);
        }
        return movies;
    }

    public class MovieQueryTask extends AsyncTask<String, Void, String> {
        private OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... urls) {
            String query = urls[0];
            Request request = new Request.Builder()
                    .url(query)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                setJsonData(s);
                //mTextView.setText(jsonData);
                fetchMovies();
            }
        }
    }

    public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.ViewHolder> {
        private Context context;
        private String[] posterUrls;

        public PosterAdapter(Context context, String[] posterUrls) {
            this.context = context;
            this.posterUrls = posterUrls;
        }

        @Override
        public PosterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(PosterAdapter.ViewHolder holder, int position) {
            holder.posterImageView.setImageBitmap(null);
            Picasso.with(holder.posterImageView.getContext()).cancelRequest(holder.posterImageView);
            Picasso.with(holder.posterImageView.getContext()).load(posterUrls[position]).into(holder.posterImageView);
        }

        @Override
        public int getItemCount() {
            return posterUrls != null ? posterUrls.length : 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView posterImageView;


            public ViewHolder(View itemView) {
                super(itemView);

                itemView.setOnClickListener(this);
                posterImageView = (ImageView) itemView.findViewById(R.id.poster_image_view);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                String poster = movies[position].getPoster("w780");
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class)
                        .putExtra("title", movies[position].title)
                        .putExtra("poster", poster)
                        .putExtra("synopsis", movies[position].synopsis)
                        .putExtra("userRating", movies[position].userRating)
                        .putExtra("releaseDate", movies[position].releaseDate);
                startActivity(intent);
            }

        }
    }
}