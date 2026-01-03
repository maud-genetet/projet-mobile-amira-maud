package com.example.popcornapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.popcornapp.Managers.LikesDAO;
import com.example.popcornapp.Managers.SessionManager;
import com.example.popcornapp.Managers.UserHandler;
import com.example.popcornapp.Models.MovieDetail;
import com.example.popcornapp.Models.User;
import com.example.popcornapp.api.MovieApiService;
import com.example.popcornapp.api.RetrofitClient;
import com.example.popcornapp.Models.MovieDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG = "MovieDetailActivity";

    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieYear;
    private TextView movieRating;
    private TextView movieGenres;
    private TextView moviePlot;
    private TextView movieDirectors;
    private ProgressBar progressBar;
    private Button btnLike;

    private String movieId;
    private int currentUserId = -1;
    private LikesDAO likesDAO;

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
        btnLike = findViewById(R.id.btnLike);


        this.movieId = getIntent().getStringExtra("MOVIE_ID");


        if (movieId == null || movieId.isEmpty()) {
            Toast.makeText(this, "Erreur: ID du film manquant", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialiser les DAOs et Managers
        likesDAO = new LikesDAO(this);
        SessionManager sessionManager = new SessionManager(this);
        UserHandler userHandler = new UserHandler(this);

        String email = sessionManager.getUserEmail();
        Log.d(TAG, "Email de session: " + email);

        if (email == null) {
            Toast.makeText(this, "Erreur: Utilisateur non connecté", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        User currentUser = userHandler.getUserByEmail(email);

        if (currentUser != null) {
            currentUserId = currentUser.getId();
            Log.d(TAG, "currentUserId: " + currentUserId);
        } else {
            Toast.makeText(this, "Erreur: Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Bouton Like
        if (btnLike != null) {
            btnLike.setOnClickListener(v -> toggleLike());
            Log.d(TAG, "Listener du bouton Like configuré");
        } else {
            Log.e(TAG, "ERREUR: btnLike est NULL!");
        }

        updateLikeButton();

        // Charger les détails du film
        loadMovieDetails(movieId);
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

    private void toggleLike() {
        Log.d(TAG, "toggleLike() appelé");

        if (currentUserId == -1 || movieId == null) {
            Toast.makeText(this, "Erreur: Données manquantes", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            boolean isCurrentlyLiked = likesDAO.isLiked(currentUserId, movieId);
            Log.d(TAG, "Film actuellement liké: " + isCurrentlyLiked);

            if (isCurrentlyLiked) {
                // Supprimer le like
                if (likesDAO.removeLike(currentUserId, movieId)) {
                    Log.d(TAG, "Like supprimé avec succès");
                    Toast.makeText(this, "Like supprimé", Toast.LENGTH_SHORT).show();
                    updateLikeButton();
                } else {
                    Log.e(TAG, "Erreur lors de la suppression du like");
                    Toast.makeText(this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Ajouter le like
                if (likesDAO.addLike(currentUserId, movieId, movieTitle.getText().toString())) {
                    Log.d(TAG, "Like ajouté avec succès");
                    Toast.makeText(this, "Film ajouté aux favoris", Toast.LENGTH_SHORT).show();
                    updateLikeButton();
                } else {
                    Log.e(TAG, "Erreur lors de l'ajout du like");
                    Toast.makeText(this, "Erreur lors de l'ajout du like", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Erreur dans toggleLike", e);
            Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLikeButton() {
        Log.d(TAG, "updateLikeButton() appelé");

        if (btnLike == null) {
            Log.e(TAG, "ERREUR: btnLike est NULL dans updateLikeButton!");
            return;
        }

        try {
            boolean isLiked = likesDAO.isLiked(currentUserId, movieId);
            Log.d(TAG, "Film est liké: " + isLiked);

            if (isLiked) {
                btnLike.setText("❤️ Aimé");
                btnLike.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                Log.d(TAG, "Bouton changé en AIMÉ");
            } else {
                btnLike.setText("🤍 Aimer");
                btnLike.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                Log.d(TAG, "Bouton changé en AIMER");
            }
        } catch (Exception e) {
            Log.e(TAG, "Erreur dans updateLikeButton", e);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}