package com.example.youtubebymood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubebymood.core.Constants;
import com.example.youtubebymood.core.HashTagAdapter;
import com.example.youtubebymood.core.model.CurrentWeatherModel;
import com.example.youtubebymood.core.model.HashTagModel;
import com.example.youtubebymood.sync.WeatherSyncUtils;
import com.example.youtubebymood.utils.WeatherUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CurrentWeatherModel.CurrentWeatherModelListener, HashTagAdapter.HashTagAdapterOnClickHandler {
    private ImageView weather;
    private TextView musicSuggestion;
    private Handler mainThreadHandler;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainThreadHandler = new Handler();

        CurrentWeatherModel.setCurrentWeatherModelListener(this);

        WeatherSyncUtils.initialize(this);
    }

    @Override
    public void onInitFinished() {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                setUpWeather();
            }
        });
    }

    private void setUpWeather(){
        weather = (ImageView) findViewById(R.id.iv_weather);
        musicSuggestion = (TextView) findViewById(R.id.tv_music_suggestion);

        int weatherId = CurrentWeatherModel.getWeatherId();
        int weatherImageId = WeatherUtils.getSmallArtResourceIdForWeatherCondition(weatherId);
        int suggestionId = WeatherUtils.getSuggestionIdForWeatherCondition(weatherId);

        weather.setImageResource(weatherImageId);
        musicSuggestion.setText(suggestionId);

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
