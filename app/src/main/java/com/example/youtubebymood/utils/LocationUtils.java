package com.example.youtubebymood.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class LocationUtils extends ActivityCompat {
    private static LocationManager locationManager;
    private static Location location;
    private static double latitude;
    private static double longitude;
    private static boolean permissionIsGranted;
    private static final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {}

        public void onStatusChanged(String provider, int status, Bundle extras){}
    };

    public static void findLocation(Context context) {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        permissionIsGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        if (permissionIsGranted) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000,
                    500.0f, locationListener);
            location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                setLocation();
            }
        }

        else {
            requestPermission(context);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000,
                    500.0f, locationListener);
            location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                setLocation();
            }
        }
    }

    private static void setLocation(){
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    private static void requestPermission(Context context){

    }

    private static void updateWithNewLocation(Location location) {
        if (location != null) {
            setLocation();
        }
        else {
            latitude = 0;
            longitude = 0;
        }
    }

    public static double getLatitude() {
        return latitude;
    }

    public static double getLongitude() {
        return longitude;
    }
}
