package com.example.youtubebymood;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubebymood.core.Constants;
import com.example.youtubebymood.core.HashTagAdapter;
import com.example.youtubebymood.core.model.CurrentWeatherModel;
import com.example.youtubebymood.core.model.HashTagModel;
import com.example.youtubebymood.sync.WeatherSyncUtils;
import com.example.youtubebymood.utils.LocationUtils;
import com.example.youtubebymood.utils.WeatherUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CurrentWeatherModel.CurrentWeatherModelListener, HashTagAdapter.HashTagAdapterOnClickHandler {
    private ImageView weather;
    private TextView musicSuggestion;
    private Handler mainThreadHandler;
    private RecyclerView recyclerView;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainThreadHandler = new Handler();

        CurrentWeatherModel.setCurrentWeatherModelListener(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);

        WeatherSyncUtils.initialize(this);
    }

    @Override
    public void onInitFinished() {
        mainThreadHandler.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                setUpWeather();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setUpWeather(){
        weather = (ImageView) findViewById(R.id.iv_weather);
        musicSuggestion = (TextView) findViewById(R.id.tv_music_suggestion);
        layout = findViewById(R.id.main_background);

        int weatherId = CurrentWeatherModel.getWeatherId();
        int weatherImageId = WeatherUtils.getSmallArtResourceIdForWeatherCondition(weatherId);
        int suggestionId = WeatherUtils.getSuggestionIdForWeatherCondition(weatherId);
        int backgroundId = WeatherUtils.getBackgroundIdForWeatherCondition(weatherId);

        layout.setBackground(ContextCompat.getDrawable(this, backgroundId));
        weather.setImageResource(weatherImageId);


        if (WeatherUtils.isClear(weatherId) || WeatherUtils.isRainy(weatherId)){
            musicSuggestion.setText(suggestionId);
            musicSuggestion.setTextColor(Color.WHITE);
        } else musicSuggestion.setText(suggestionId);

        setUpRecyclerView();
        populateRecyclerView(weatherId);
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_hashtag);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void populateRecyclerView(int weatherId) {
        final ArrayList<HashTagModel> hashTagModelArrayList = WeatherUtils.getHashTagsForWeatherCondition(weatherId);
        Log.d(Constants.LOGTAG, "hashTag num: " + hashTagModelArrayList.size());
        HashTagAdapter adapter = new HashTagAdapter(this, this, hashTagModelArrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(String query) {
        startActivity(new Intent(MainActivity.this, ListActivity.class).putExtra("query", query));
        Log.d(Constants.LOGTAG, query + " 에 대한 검색 결과");
    }
}
