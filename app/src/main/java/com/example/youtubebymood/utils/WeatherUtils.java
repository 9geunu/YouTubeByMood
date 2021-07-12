package com.example.youtubebymood.utils;

import android.util.Log;

import com.example.youtubebymood.R;
import com.example.youtubebymood.core.Constants;
import com.example.youtubebymood.core.model.HashTagModel;

import java.util.ArrayList;

public class WeatherUtils {

    public static ArrayList<HashTagModel> getHashTagsForWeatherCondition(int weatherId){
        ArrayList<HashTagModel> cloudyTag = new ArrayList<>();
        ArrayList<HashTagModel> rainTag = new ArrayList<>();
        ArrayList<HashTagModel> clearTag = new ArrayList<>();
        ArrayList<HashTagModel> snowTag = new ArrayList<>();
        ArrayList<HashTagModel> unknownTag = new ArrayList<>();

        cloudyTag.add(new HashTagModel("#몽환적인","몽환적인 노래"));
        cloudyTag.add(new HashTagModel("#잔잔한","잔잔한 노래"));
        cloudyTag.add(new HashTagModel("#감성적인","감성적인 노래"));

        rainTag.add(new HashTagModel("#비와_어울리는","비 오는 날 노래"));
        rainTag.add(new HashTagModel("#뉴에이지", "감성 뉴에이지"));

        clearTag.add(new HashTagModel("#신나는", "신나는 노래"));
        clearTag.add(new HashTagModel("#밝은", "밝은 노래"));
        clearTag.add(new HashTagModel("#맑은_날", "맑은 날 노래"));
        clearTag.add(new HashTagModel("#그루브", "그루브 노래"));

        snowTag.add(new HashTagModel("#눈이_오면", "눈 오는 날 노래"));
        snowTag.add(new HashTagModel("#크리스마스", "크리스마스 캐롤"));

        unknownTag.add(new HashTagModel("#Error", "아무 노래"));

        if (weatherId >= 200 && weatherId <= 232) {
            return cloudyTag;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return rainTag;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return rainTag;
        } else if (weatherId == 511) {
            return snowTag;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return rainTag;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return snowTag;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return cloudyTag;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return cloudyTag;
        } else if (weatherId == 800) {
            return clearTag;
        } else if (weatherId == 801) {
            return clearTag;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return cloudyTag;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return cloudyTag;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return cloudyTag;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return clearTag;
        }

        Log.e(Constants.LOGTAG, "Unknown Weather: " + weatherId);
        return unknownTag;
    }

    public static int getSmallArtResourceIdForWeatherCondition(int weatherId) {

        /*
         * Based on weather code data for Open Weather Map.
         */
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.ic_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.ic_rain;
        } else if (weatherId == 511) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.ic_fog;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.ic_storm;
        } else if (weatherId == 800) {
            return R.drawable.ic_clear;
        } else if (weatherId == 801) {
            return R.drawable.ic_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.ic_cloudy;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.drawable.ic_clear;
        }

        Log.e(Constants.LOGTAG, "Unknown Weather: " + weatherId);
        return R.drawable.ic_storm;
    }

    public static int getSuggestionIdForWeatherCondition(int weatherId){
        if (weatherId >= 200 && weatherId <= 232) {
            return R.string.cloudy_suggestion;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.string.rainy_suggestion;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.string.rainy_suggestion;
        } else if (weatherId == 511) {
            return R.string.snow_suggestion;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.string.rainy_suggestion;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.string.snow_suggestion;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.string.cloudy_suggestion;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.string.cloudy_suggestion;
        } else if (weatherId == 800) {
            return R.string.clear_suggestion;
        } else if (weatherId == 801) {
            return R.string.clear_suggestion;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.string.cloudy_suggestion;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.string.cloudy_suggestion;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.string.cloudy_suggestion;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.string.clear_suggestion;
        }

        Log.e(Constants.LOGTAG, "Unknown Weather: " + weatherId);
        return R.string.unknown_suggestion;
    }

    public static int getBackgroundIdForWeatherCondition(int weatherId){
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.cloudy;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.rainy;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.rainy;
        } else if (weatherId == 511) {
            return R.drawable.snowy;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.rainy;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.snowy;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.cloudy;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.cloudy;
        } else if (weatherId == 800) {
            return R.drawable.sunny;
        } else if (weatherId == 801) {
            return R.drawable.sunny;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.cloudy;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.drawable.cloudy;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.drawable.cloudy;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.drawable.sunny;
        }

        Log.e(Constants.LOGTAG, "Unknown Weather: " + weatherId);
        return R.drawable.snowy;
    }

    public static boolean isClear(int weatherId){
        return weatherId == 800 || weatherId == 801 || weatherId >= 951 && weatherId <= 957;
    }

    public static boolean isRainy(int weatherId){
        return (weatherId >= 300 && weatherId <= 321) || (weatherId >= 500 && weatherId <= 504) || (weatherId >= 520 && weatherId <= 531);
    }
}
