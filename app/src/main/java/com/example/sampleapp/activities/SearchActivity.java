package com.example.sampleapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.sampleapp.R;



public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    public static final String KEYWORD = "keyword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toast.makeText( this, "Welcome to HunterHunter", Toast.LENGTH_SHORT ).show();

        searchEditText = findViewById(R.id.searchEditText);
    }


    public void Search(View view) {
        Intent intent = new Intent(SearchActivity.this, PlaylistActivity.class);
        String keyword = searchEditText.getText().toString().trim();
        if(keyword.equals("")){
            return;
        }
        intent = intent.putExtra(KEYWORD, keyword);
        startActivity(intent);
    }
}

