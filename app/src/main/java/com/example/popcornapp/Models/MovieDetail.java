package com.example.popcornapp.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieDetail {
    @SerializedName("id")
    private String id;

    @SerializedName("primaryTitle")
    private String primaryTitle;

    @SerializedName("primaryImage")
    private Movie.PrimaryImage primaryImage;

    @SerializedName("startYear")
    private int startYear;

    @SerializedName("genres")
    private List<String> genres;

    @SerializedName("rating")
    private Movie.Rating rating;

    @SerializedName("plot")
    private String plot;

    @SerializedName("directors")
    private List<Director> directors;

    // Getters
    public String getId() { return id; }
    public String getPrimaryTitle() { return primaryTitle; }
    public Movie.PrimaryImage getPrimaryImage() { return primaryImage; }
    public int getStartYear() { return startYear; }
    public List<String> getGenres() { return genres; }
    public Movie.Rating getRating() { return rating; }
    public String getPlot() { return plot; }
    public List<Director> getDirectors() { return directors; }

    public static class Director {
        @SerializedName("id")
        private String id;

        @SerializedName("displayName")
        private String displayName;

        public String getId() { return id; }
        public String getDisplayName() { return displayName; }
    }
}