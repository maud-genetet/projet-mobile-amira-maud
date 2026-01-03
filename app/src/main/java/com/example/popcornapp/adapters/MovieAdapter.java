package com.example.popcornapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.popcornapp.MovieDetailActivity;
import com.example.popcornapp.R;
import com.example.popcornapp.Models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("MOVIE_ID", movie.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public void setMovies(List<Movie> movieList) {
        if (movieList != null) {
            this.movies = movieList;
        } else {
            this.movies = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView moviePoster;
        private final TextView movieTitle;
        private final TextView movieYear;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieYear = itemView.findViewById(R.id.movieYear);
        }

        public void bind(Movie movie) {
            movieTitle.setText(movie.getPrimaryTitle());
            movieYear.setText(String.valueOf(movie.getStartYear()));

            if (movie.getPrimaryImage() != null && movie.getPrimaryImage().getUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(movie.getPrimaryImage().getUrl())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(moviePoster);
            }
        }
    }
}