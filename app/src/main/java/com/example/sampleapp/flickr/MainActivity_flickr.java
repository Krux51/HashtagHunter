package com.example.sampleapp.flickr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleapp.R;

import java.util.ArrayList;

public class MainActivity_flickr extends BaseActivity implements GetFlickrJsonData.OnDataAvailable, RecyclerItemClickListener.OnRecyclerClickListener{
    private static final String TAG = "MainActivity";
    private FlickrRecycleViewAdapter recycleViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainf);
        activateToolbar(false);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(this,recyclerView,this));
        recycleViewAdapter = new FlickrRecycleViewAdapter(new ArrayList<PhotoData>(),this);
        recyclerView.setAdapter(recycleViewAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_main,menu);
        Log.d(TAG, "onCreateOptionsMenu: Menu created");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_search){
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: start");
        GetFlickrJsonData flickrJsonData = new GetFlickrJsonData("en-us",true,this);
//        flickrJsonData.executeOnSameThread("android,oreo");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String queryTags = sharedPreferences.getString(FLICKR_QUERY,"");
        if(queryTags.length()>0){
            flickrJsonData.execute(queryTags);
        }
        Log.d(TAG, "onResume: ends");
    }

    @Override
    public void onDataAvailable(ArrayList<PhotoData> data, DownloadStatus status) {
        if(status== DownloadStatus.OK){
//            Log.d(TAG, "onDataAvailable: data is: "+data);
            recycleViewAdapter.loadNewData(data);
        }else {
            Log.e(TAG, "onDataAvailable: download failed with status: "+status);

        }




    }

    @Override
    public void OnItemClickListener(View view, int position) {
//        Toast.makeText(MainActivity.this,"Normal Tap at "+position+" position",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemLongClickListener(View view, int position) {
//        Toast.makeText(MainActivity.this,"Long Tap "+position+" position",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.putExtra(PHOTO_TRANSFER,recycleViewAdapter.getPhotoData(position));
        startActivity(intent);
    }
}
