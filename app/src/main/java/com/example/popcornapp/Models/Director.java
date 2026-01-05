package com.example.popcornapp.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Director {
    @SerializedName("id")
    private String id;

    @SerializedName("displayName")
    private String displayName;

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }
}