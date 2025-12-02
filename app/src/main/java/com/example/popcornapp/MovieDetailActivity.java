package com.example.popcornapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.popcornapp.api.MovieApiService;
import com.example.popcornapp.api.RetrofitClient;
import com.example.popcornapp.Models.MovieDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieYear;
    private TextView movieRating;
    private TextView movieGenres;
    private TextView moviePlot;
    private TextView movieDirectors;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Initialiser les vues
        moviePoster = findViewById(R.id.movieDetailPoster);
        movieTitle = findViewById(R.id.movieDetailTitle);
        movieYear = findViewById(R.id.movieDetailYear);
        movieRating = findViewById(R.id.movieDetailRating);
        movieGenres = findViewById(R.id.movieDetailGenres);
        moviePlot = findViewById(R.id.movieDetailPlot);
        movieDirectors = findViewById(R.id.movieDetailDirectors);
        progressBar = findViewById(R.id.progressBarDetail);

        // Récupérer l'ID du film depuis l'intent
        String movieId = getIntent().getStringExtra("MOVIE_ID");

        if (movieId != null) {
            loadMovieDetails(movieId);
        } else {
            Toast.makeText(this, "Erreur: ID du film manquant", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Bouton retour
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadMovieDetails(String movieId) {
        progressBar.setVisibility(View.VISIBLE);

        MovieApiService apiService = RetrofitClient.getClient()
                .create(MovieApiService.class);

        Call<MovieDetail> call = apiService.getMovieDetail(movieId);

        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    displayMovieDetails(response.body());
                } else {
                    Toast.makeText(MovieDetailActivity.this,
                            "Erreur lors du chargement des détails",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MovieDetailActivity.this,
                        "Erreur: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayMovieDetails(MovieDetail movie) {
        // Titre
        movieTitle.setText(movie.getPrimaryTitle());

        // Année
        movieYear.setText(String.valueOf(movie.getStartYear()));

        // Note
        if (movie.getRating() != null) {
            movieRating.setText(String.format("⭐ %.1f", movie.getRating().getAggregateRating()));
        }

        // Genres
        if (movie.getGenres() != null && !movie.getGenres().isEmpty()) {
            movieGenres.setText(String.join(", ", movie.getGenres()));
        }

        // Synopsis
        if (movie.getPlot() != null && !movie.getPlot().isEmpty()) {
            moviePlot.setText(movie.getPlot());
        } else {
            moviePlot.setText("Aucun synopsis disponible.");
        }

        // Réalisateurs
        if (movie.getDirectors() != null && !movie.getDirectors().isEmpty()) {
            StringBuilder directors = new StringBuilder();
            for (int i = 0; i < movie.getDirectors().size(); i++) {
                directors.append(movie.getDirectors().get(i).getDisplayName());
                if (i < movie.getDirectors().size() - 1) {
                    directors.append(", ");
                }
            }
            movieDirectors.setText(directors.toString());
        } else {
            movieDirectors.setText("Information non disponible");
        }

        // Affiche
        if (movie.getPrimaryImage() != null && movie.getPrimaryImage().getUrl() != null) {
            Glide.with(this)
                    .load(movie.getPrimaryImage().getUrl())
                    .into(moviePoster);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}