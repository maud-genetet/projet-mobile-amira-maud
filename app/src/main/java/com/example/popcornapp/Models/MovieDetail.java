package com.example.popcornapp.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieDetail {
    @SerializedName("id")
    private String id;

    @SerializedName("primaryTitle")
    private String primaryTitle;

    @SerializedName("primaryImage")
    private PrimaryImage primaryImage;

    @SerializedName("startYear")
    private int startYear;

    @SerializedName("genres")
    private List<String> genres;

    @SerializedName("rating")
    private Rating rating;

    @SerializedName("plot")
    private String plot;

    @SerializedName("directors")
    private List<Director> directors;

    // Getters
    public String getId() {
        return id;
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public PrimaryImage getPrimaryImage() {
        return primaryImage;
    }

    public int getStartYear() {
        return startYear;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Rating getRating() {
        return rating;
    }

    public String getPlot() {
        return plot;
    }

    public List<Director> getDirectors() {
        return directors;
    }
}