package com.hisamoto.leandro.mapaandroid.tracker;


import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MyLocation {

    public static Timer timer1;
    LocationManager lm;
    LocationResult locationResult;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    public static Context context;

    public boolean getLocation(Context context, LocationResult result) {

        this.context = context;
        locationResult = result;

        if (lm == null) {

            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        try {

            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

            Log.i("GPSAndroid", "ERRO: GPS_PROVIDER");
        }

        try {

            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {

            Log.i("GPSAndroid", "ERRO: NETWORK_PROVIDER");
        }

        if (!gps_enabled && !network_enabled) {

            return false;
        }

        if (gps_enabled) {

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        }

        if (network_enabled) {

            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        }

        try {

            timer1 = new Timer();
            timer1.schedule(new GetLastLocation(), 3000);
        } catch (Exception e) {

            e.printStackTrace();
        }

        return true;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {

            locationResult.gotLocation(location);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {

        public void onLocationChanged(Location location) {

            locationResult.gotLocation(location);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    class GetLastLocation extends TimerTask {

        @Override
        public void run() {

            Log.i("GPSAndroid", "Atualizando localização");
            Location net_loc = null, gps_loc = null;

            gps_loc = null;
            net_loc = null;

            if (gps_enabled) {

                gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            if (network_enabled) {

                net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (gps_loc != null && net_loc != null) {

                if (gps_loc.getTime() > net_loc.getTime()) {

                    locationResult.gotLocation(gps_loc);
                } else {

                    locationResult.gotLocation(net_loc);
                }
            } else if (gps_loc != null) {

                locationResult.gotLocation(gps_loc);
            } else if (net_loc != null) {

                locationResult.gotLocation(net_loc);
            }
        }
    }

    public static abstract class LocationResult {

        public abstract void gotLocation(Location location);
    }
}