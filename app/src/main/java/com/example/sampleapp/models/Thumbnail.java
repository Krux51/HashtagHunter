package com.example.sampleapp.models;

import com.google.gson.annotations.SerializedName;

public class Thumbnail {

    @SerializedName("medium")
    private ImageInfo imageInfo;

    public ImageInfo getImageInfo() {
        return imageInfo;
    }
}
