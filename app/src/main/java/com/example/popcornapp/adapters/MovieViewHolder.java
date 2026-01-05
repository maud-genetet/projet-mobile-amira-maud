package com.example.popcornapp.adapters;

import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.popcornapp.Models.Movie;

import com.example.popcornapp.R;


public class MovieViewHolder extends RecyclerView.ViewHolder {
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