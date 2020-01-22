package com.datamation.swdsfa.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.datamation.swdsfa.helpers.SharedPref;

public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;
    protected LocationManager locationManager;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    SharedPref mSharedPref;
    private double latitude;
    private double longitude;

    public GPSTracker(Context context) {
        this.mContext = context;
        mSharedPref = new SharedPref(context);
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            /* Check network provider */
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            /* If enabled, set location listener */
            if (isNetworkEnabled) {

                if (ActivityCompat.checkSelfPermission((Activity)mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity)mContext, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 10);
                }

                //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                    canGetLocation = true;
                    Log.d("Network ***", "Network");
                //}
//                else
//                {
//                    canGetLocation = false;
//                }

            } else
                canGetLocation = false;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* Remove listener */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /* Latitude getter */
    public double getLatitude() {
        if (!canGetLocation) {
            return 0.0;
        }

        return latitude;
    }

    /* Longitude getter */
    public double getLongitude() {
        if (!canGetLocation) {
            return 0.0;
        }

        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /* Show GPS settings dialog */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS is settings");

        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        alertDialog.show();
    }

    /* Location updates */
    @Override
    public void onLocationChanged(Location location) {
        canGetLocation = true;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        mSharedPref.setGlobalVal("Longitude", String.valueOf(location.getLongitude()));
        mSharedPref.setGlobalVal("Latitude", String.valueOf(location.getLatitude()));
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
