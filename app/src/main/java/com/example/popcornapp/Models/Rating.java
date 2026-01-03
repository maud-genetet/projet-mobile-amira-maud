package com.example.popcornapp.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Rating {
    @SerializedName("aggregateRating")
    private double aggregateRating;

    @SerializedName("voteCount")
    private int voteCount;

    public double getAggregateRating() {
        return aggregateRating;
    }

    public int getVoteCount() {
        return voteCount;
    }
}