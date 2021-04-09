package com.example.sampleapp.httpclient.unsplash;

import com.example.sampleapp.models.unsplash.UnsplashPhoto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface UnsplashInterface {
    @GET("photos/random")
    Call<UnsplashPhoto> getRandomPhoto(@Header("Authorization") String clientID);
//    @GET("search/photos")
//    fun search(@Query("query") query: String, @Query("per_page") perPage: Int, @Query("page") page: Int): Call<List<Photo>>
}
