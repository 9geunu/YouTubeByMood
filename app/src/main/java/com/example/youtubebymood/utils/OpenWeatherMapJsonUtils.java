package com.example.youtubebymood.utils;

import android.content.Context;
import android.util.Log;

import com.example.youtubebymood.core.Constants;
import com.example.youtubebymood.core.model.CurrentWeatherModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class OpenWeatherMapJsonUtils {
    private static final String OWM_WEATHER = "weather";
    private static final String OWM_WEATHER_ID = "id";
    private static final String OWM_WEATHER_MAIN = "main";
    private static final String OWM_WEATHER_DESCRIPTION = "description";

    private static final String OWM_MESSAGE_CODE = "cod";

    public static void setCurrentWeatherModelFromJson(Context context, String weatherDataListJsonStr) throws JSONException {

        JSONObject weatherDataListJson = new JSONObject(weatherDataListJsonStr);

        if (weatherDataListJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = weatherDataListJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                default:
                    /* Server probably down */
            }
        }
        JSONObject weatherObject =
                weatherDataListJson.getJSONArray(OWM_WEATHER).getJSONObject(0);

        int weatherId = weatherObject.getInt(OWM_WEATHER_ID);
        String weatherMain = weatherObject.getString(OWM_WEATHER_MAIN);
        String weatherDescription = weatherObject.getString(OWM_WEATHER_DESCRIPTION);

        CurrentWeatherModel.init(weatherId,weatherMain,weatherDescription);
    }
}
