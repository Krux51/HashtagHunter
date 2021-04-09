package com.example.sampleapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.sampleapp.activities.SearchActivity;
import com.example.sampleapp.flickr.MainActivity_flickr;
import com.example.sampleapp.httpclient.unsplash.UnsplashClient;
import com.example.sampleapp.models.unsplash.UnsplashPhoto;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private UnsplashClient unsplash;
    private ImageView imageView;
    private Button ytButton;
    private Button flickrButton;
    private Button tweeterBtn;

    //IGQVJXenN3dks4T1hNaEp2WGl0MmpXc2MtSGtBSndMbmlpTkpBRUJJcVdjSElxRnZASQTJ0X2dsLWN6WlBRaDZAFMVIwNFdnS2ZAYM0RMVW5jNmRqdUhOdU5jMTl0bzExWDJ6bVQ4RlNCSTgwN3NhSTg2QwZDZD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageIv);
      ytButton = findViewById(R.id.btn_youtube);
        flickrButton = findViewById(R.id.btn_flickr);
        tweeterBtn = findViewById(R.id.tweeterBtn);
        unsplash = new UnsplashClient();
        unsplash.getRandomPhoto(
                new UnsplashClient.OnPhotoLoadedListener() {
                    @Override
                    public void onResponse(Call<UnsplashPhoto> call, Response<UnsplashPhoto> response) {
                        int statusCode = response.code();
                        if (statusCode == 200) {
                            Glide
                                    .with(MainActivity.this)
                                    .load(Uri.parse(response.body().getUrls().getRegular()))
                                    .into(imageView);
                        } else if (statusCode >= 400) {
                            Toast.makeText(MainActivity.this, String.valueOf(statusCode), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UnsplashPhoto> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
      ytButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });
        flickrButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity_flickr.class);
            startActivity(intent);
        });
        tweeterBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity_twitter.class);
            startActivity(intent);
        });
    }
}