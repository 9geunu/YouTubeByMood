package com.example.youtubebymood.sync;

import android.content.Context;

import com.example.youtubebymood.utils.NetworkUtils;
import com.example.youtubebymood.utils.OpenWeatherMapJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class WeatherSyncTask {
    synchronized public static void syncWeather(Context context){

        try {
            URL weatherRequestUrl = NetworkUtils.getUrl(context);

            String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

            OpenWeatherMapJsonUtils.setCurrentWeatherModelFromJson(context, jsonWeatherResponse);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
