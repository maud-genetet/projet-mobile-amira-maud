package com.example.popcornapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popcornapp.adapters.MovieAdapter;
import com.example.popcornapp.api.MovieApiService;
import com.example.popcornapp.api.RetrofitClient;
import com.example.popcornapp.databinding.FragmentHomeBinding;
import com.example.popcornapp.Models.MovieResponse;
import com.example.popcornapp.Models.Movie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MovieAdapter movieAdapter;
    private static final int LIMIT = 20;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupRecyclerView();
        setupSearchView();
        loadMovies();

        return root;
    }

    private void setupRecyclerView() {
        movieAdapter = new MovieAdapter();
        RecyclerView recyclerView = binding.recyclerViewMovies;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(movieAdapter);
    }

    private void setupSearchView() {
        SearchView searchView = binding.searchViewMovies;
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) {
                    searchMovies(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.trim().isEmpty()) {
                    searchMovies(newText);
                }
                return false;
            }
        });
    }

    private void loadMovies() {
        binding.progressBar.setVisibility(View.VISIBLE);

        MovieApiService apiService = RetrofitClient.getClient()
                .create(MovieApiService.class);

        Call<MovieResponse> call = apiService.getMovies("MOVIE", LIMIT);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    movieAdapter.setMovies(response.body().getTitles());
                } else {
                    Toast.makeText(getContext(), "Erreur lors du chargement",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Erreur: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchMovies(String query) {
        binding.progressBar.setVisibility(View.VISIBLE);

        MovieApiService apiService = RetrofitClient.getClient()
                .create(MovieApiService.class);

        Call<MovieResponse> call = apiService.searchMovies(query, LIMIT);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getTitles();
                    if (movies != null && !movies.isEmpty()) {
                        movieAdapter.setMovies(movies);
                    } else {
                        movieAdapter.setMovies(null);
                        Toast.makeText(getContext(), "Aucun film trouvé",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Erreur lors de la recherche",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Erreur: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}