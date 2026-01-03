package com.example.popcornapp.api;

import com.example.popcornapp.Models.MovieDetail;
import com.example.popcornapp.Models.MovieResponse;
import com.example.popcornapp.Models.VideoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("titles")
    Call<MovieResponse> getMovies(
            @Query("types") String types,
            @Query("limit") int limit
    );

    @GET("titles/{titleId}")
    Call<MovieDetail> getMovieDetail(@Path("titleId") String titleId);

    @GET("titles/{titleId}/videos")
    Call<VideoResponse> getMovieVideo(@Path("titleId") String titleId);
}