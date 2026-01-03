package com.example.popcornapp.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VideoResponse {
    @SerializedName("videos")
    private List<Video> videos;

    public List<Video> getvideos() {
        return videos;
    }
}