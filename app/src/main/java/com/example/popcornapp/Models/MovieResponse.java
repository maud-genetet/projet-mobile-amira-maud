package com.example.popcornapp.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieResponse {
    @SerializedName("titles")
    private List<Movie> titles;

    public List<Movie> getTitles() {
        return titles;
    }
}