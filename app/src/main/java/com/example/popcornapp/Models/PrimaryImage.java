package com.example.popcornapp.Models;

import com.google.gson.annotations.SerializedName;

public class PrimaryImage {

    @SerializedName("url")
    private String url;

    public String getUrl() { return url; }
}