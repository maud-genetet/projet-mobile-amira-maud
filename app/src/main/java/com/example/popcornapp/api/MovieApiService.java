package com.example.popcornapp.api;

import com.example.popcornapp.Models.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("titles")
    Call<MovieResponse> getMovies(
            @Query("types") String types,
            @Query("limit") int limit
    );
}