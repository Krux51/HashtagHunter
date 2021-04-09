package com.example.sampleapp.httpclient.unsplash;

import com.example.sampleapp.models.unsplash.UnsplashPhoto;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UnsplashClient {

    private static final String BASE_URL = "https://api.unsplash.com/";
    private final String CLIENT_ID = "Client-ID 96aa97b4a2e353514285fcca6761170bab03c63032ddb6feecb982b81dad8969";

    private UnsplashInterface unsplashApi;

    public UnsplashClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        unsplashApi = retrofit.create(UnsplashInterface.class);
    }

    public void getRandomPhoto(OnPhotoLoadedListener onPhotoLoadedListener) {
        unsplashApi.getRandomPhoto(CLIENT_ID).enqueue(onPhotoLoadedListener);
    }

    public interface OnPhotoLoadedListener extends Callback<UnsplashPhoto> {}


}