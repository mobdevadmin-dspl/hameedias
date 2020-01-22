package com.datamation.swdsfa.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;



/**
 *
 * The custom designed geo locating algorithms are defined here.
 */
public class LocationProvider implements LocationListener {

    // Static variables to define the location type
    public static final int LOCATION_TYPE_GPS = 100901;
    public static final int LOCATION_TYPE_NETWORK = 100902;

    private static final String LOG_TAG = LocationProvider.class.getSimpleName();

    private LocationManager locationManager;

    // The custom interfaces to implement to
    private ICustomLocationListener customLocationListener;
    private OnGPSTimeoutListener onGPSTimeoutListener;

    private Handler locationHandler;
    private Runnable switchRunnable, notifyRunnable;

    private long timeOutDuration, gpsTimeOutDuration;

    private boolean gpsActive, networkActive;

    private LocationListener locationListener;

    private int locationType;

    private boolean gotLocation;
    private boolean firstAttempt, needsNotify;

    /**
     * Use this constructor to create a CustomLocationListenerService object without a timeout.
     *
     * @param locationManager        A LocationManager object.
     * @param customLocationListener A custom listener object to implement pre, on progress, post,
     *                               on unable execution methods.
     */
    public LocationProvider(@NonNull LocationManager locationManager,
                            @NonNull ICustomLocationListener customLocationListener) {

        this.locationManager = locationManager;
        this.customLocationListener = customLocationListener;
        this.timeOutDuration = 0;

        gpsActive = false;
        networkActive = false;

        locationHandler = new Handler();
        locationListener = LocationProvider.this;

        firstAttempt = true;
        needsNotify = false;

        // Set the location switch offset to default value
        gpsTimeOutDuration = ValueHolder.LOCATION_SWITCH_DELAY;

    }

    /**
     * Use this constructor to create a CustomLocationListenerService object with a timeout.
     *
     * @param locationManager        A LocationManager object.
     * @param customLocationListener A custom listener object to implement pre, on progress, post,
     *                               on unable execution methods.
     * @param timeOutDuration        Time out time in milliseconds. (0 if unlimited)
     */
    public LocationProvider(@NonNull LocationManager locationManager,
                            @NonNull ICustomLocationListener customLocationListener,
                            long timeOutDuration) {

        this.locationManager = locationManager;
        this.customLocationListener = customLocationListener;
        this.timeOutDuration = timeOutDuration;

        gpsActive = false;
        networkActive = false;

        locationHandler = new Handler();
        locationListener = LocationProvider.this;

        firstAttempt = true;
        needsNotify = false;

        // Set the location switch offset to default value
        gpsTimeOutDuration = ValueHolder.LOCATION_SWITCH_DELAY;

    }

    /**
     * Set a GPS Timeout listener. This will execute the provided interface method onGPSTimeOut() when
     * the time exceeds the timeout duration.
     * @param onGPSTimeoutListener The interface object to execute on time out.
     * @param timeOut The duration of the time out. (Pass 0 to keep default value)
     * throws UnsupportedOperationException When GPS is not available
     */
    public void setOnGPSTimeoutListener(@NonNull OnGPSTimeoutListener onGPSTimeoutListener,
                                        long timeOut) throws UnsupportedOperationException {
        refreshLocationParams();
        if (gpsActive) {
            this.onGPSTimeoutListener = onGPSTimeoutListener;
            if (timeOut > 0) this.gpsTimeOutDuration = timeOut;
            needsNotify = true;
        } else {
            throw new UnsupportedOperationException("GPS not available to set a timeout listener");
        }

    }


//    public void setNeedsNotify(boolean needsNotify) {
//        this.needsNotify = needsNotify;
//    }

    /**
     * Begin the GPS/Network locating process.
     */
    public void startLocating() {
        requestLocationUpdates();
    }

    private void refreshLocationParams() {
        try {
            gpsActive = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
            gpsActive = false;
        }

        try {
            networkActive = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
            networkActive = false;
        }

        Log.d(LOG_TAG, "GPS State : " + gpsActive + "\nNetwork State : " + networkActive);

    }

    private void requestLocationUpdates() {

        refreshLocationParams();

        boolean possibleToGetLocation;

        if (networkActive) {
            if (gpsActive) {
                // Request timed location update using GPS
               // requestGPSLocation();
                locationType = LOCATION_TYPE_GPS;

                if (firstAttempt && needsNotify) {
                    // Show a message to move to a more clear area
                    if (notifyRunnable == null) {
                        notifyRunnable = new Runnable() {
                            @Override
                            public void run() {
                                if (!gotLocation) {
                                    stopLocating();
                                    onGPSTimeoutListener.onGPSTimeOut();
                                }
                                firstAttempt = false;
                            }
                        };
                    }

                    // Notify the user to move to a clear area
                    locationHandler.postDelayed(notifyRunnable, gpsTimeOutDuration);
                    possibleToGetLocation = true;

                } else {
                    // Switch to network as usual
                    if (switchRunnable == null) {
                        switchRunnable = new Runnable() {
                            @Override
                            public void run() {
                                if (!gotLocation) {
                                    locationManager.removeUpdates(locationListener);
                                    //requestNetworkLocation();
                                    locationType = LOCATION_TYPE_NETWORK;
                                }
                            }
                        };
                    }

                    // Stop getting the location from GPS and get from network if taking too long (more than 20s)
                    locationHandler.postDelayed(switchRunnable, ValueHolder.LOCATION_SWITCH_DELAY);
                    possibleToGetLocation = true;
                }


            } else {
                // Request location update using network
              //  requestNetworkLocation();
                locationType = LOCATION_TYPE_NETWORK;
                possibleToGetLocation = true;
            }
        } else {
            if (gpsActive) {
                // Only GPS active. Request location from GPS
               // requestGPSLocation();
                locationType = LOCATION_TYPE_GPS;
                possibleToGetLocation = true;
            } else {
                // Absolutely unable to get location.
                customLocationListener.onUnableToGetLocation();
                possibleToGetLocation = false;
            }
        }

        if (possibleToGetLocation) {
            if (timeOutDuration > 0) {
                // There's a timeout defined. Therefore terminate the location accessing process
                Runnable terminateProcess = new Runnable() {
                    @Override
                    public void run() {
                        stopLocating();
                    }
                };
                locationHandler.postDelayed(terminateProcess, timeOutDuration);
            }
        }

    }

    /**
     * Force stop the geo locating process.
     */
    public void stopLocating() {
        locationManager.removeUpdates(locationListener);
        if (!gotLocation) {
            customLocationListener.onUnableToGetLocation();
            Log.d(LOG_TAG, "Terminating the locating process");
            locationHandler.removeCallbacks(notifyRunnable);
        }
    }

//    private void requestGPSLocation() {
//        customLocationListener.onProgress(LOCATION_TYPE_GPS);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//    }

//    private void requestNetworkLocation() {
//        customLocationListener.onProgress(LOCATION_TYPE_NETWORK);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {

            customLocationListener.onGotLocation(location, locationType);
            locationManager.removeUpdates(locationListener);
            gotLocation = true;
        } else customLocationListener.onUnableToGetLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        customLocationListener.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        customLocationListener.onProviderDisabled(provider);
    }

    @IntDef({LOCATION_TYPE_GPS, LOCATION_TYPE_NETWORK})
    public @interface LocationType {}

    public interface ICustomLocationListener {
        void onProviderEnabled(String provider);

        void onProviderDisabled(String provider);

        void onUnableToGetLocation();

        void onGotLocation(Location location, @LocationType int locationType);

        void onProgress(@LocationType int type);

//        void onGPSTimeout();
    }

    public interface OnGPSTimeoutListener {
        void onGPSTimeOut();
    }

}
