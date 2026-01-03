package com.example.popcornapp.Models;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("name")
    private String name;

    @SerializedName("primaryImage")
    private PrimaryImage primaryImage;

    @SerializedName("description")
    private String description;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    @SerializedName("runtimeSeconds")
    private int runtimeSeconds;

    // Getters
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public PrimaryImage getPrimaryImage() {
        return primaryImage;
    }

    public String getDescription() {
        return description;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRuntimeSeconds() {
        return runtimeSeconds;
    }
}