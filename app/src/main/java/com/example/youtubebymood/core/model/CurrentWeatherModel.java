package com.example.youtubebymood.core.model;

public class CurrentWeatherModel {
    private static int weatherId = 0;
    private static String weatherMain = null;
    private static String weatherDescription = null;
    private static CurrentWeatherModelListener currentWeatherModelListener;

    public interface CurrentWeatherModelListener{
        void onInitFinished();
    }

    public static void setCurrentWeatherModelListener(CurrentWeatherModelListener currentWeatherModelListener) {
        CurrentWeatherModel.currentWeatherModelListener = currentWeatherModelListener;
    }

    public static void init(int id, String main, String description){
        weatherId = id;
        weatherMain = main;
        weatherDescription = description;
        currentWeatherModelListener.onInitFinished();
    }

    public static int getWeatherId() {
        return weatherId;
    }

    public static String getWeatherMain() {
        return weatherMain;
    }

    public static String getWeatherDescription() {
        return weatherDescription;
    }
}
