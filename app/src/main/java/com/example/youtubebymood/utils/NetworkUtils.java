package com.example.youtubebymood.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.youtubebymood.core.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather";
    private static final String QUERY_PARAM = "weather";

    private static final String LAT_PARAM = "lat";
    private static final String LON_PARAM = "lon";
    private static final String APPID_PARAM = "appid";
    private static final String APPID = "a19ebb0527ec2ab8021ac2b37f23f3d0";

    public static URL getUrl(Context context) {
        LocationUtils.findLocation(context);
        double latitude = LocationUtils.getLatitude();
        double longitude = LocationUtils.getLongitude();
        return buildUrlWithLatitudeLongitude(latitude, longitude);
    }

    private static URL buildUrlWithLatitudeLongitude(Double latitude, Double longitude) {
        Uri weatherQueryUri = Uri.parse(WEATHER_URL).buildUpon()
                .appendQueryParameter(LAT_PARAM, String.valueOf(latitude))
                .appendQueryParameter(LON_PARAM, String.valueOf(longitude))
                .appendQueryParameter(APPID_PARAM, APPID)
                .build();

        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            Log.v(Constants.LOGTAG, "URL: " + weatherQueryUrl);
            return weatherQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
}
