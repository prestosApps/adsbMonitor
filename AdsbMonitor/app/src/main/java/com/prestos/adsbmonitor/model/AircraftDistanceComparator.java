package com.prestos.adsbmonitor.model;

import android.location.Location;

import java.util.Comparator;

/**
 * Created by prestos on 30/01/2016.
 */
public class AircraftDistanceComparator implements Comparator<Aircraft> {

    private double receiverLat = 0.0d;
    private double receiverLon = 0.0d;

    public AircraftDistanceComparator(double receiverLat, double receiverLon) {
        this.receiverLat = receiverLat;
        this.receiverLon = receiverLon;
    }

    @Override
    public int compare(Aircraft aircraft, Aircraft t1) {
        int result = 0;
        if (aircraft.getLat() != 0.0d && aircraft.getLon() != 0.0d && t1.getLat() != 0.0d && t1.getLon() != 0.0d) {
            float[] aircraftResults = new float[3];
            Location.distanceBetween(receiverLat, receiverLon, aircraft.getLat(), aircraft.getLon(), aircraftResults);
            float[] t1Results = new float[3];
            Location.distanceBetween(receiverLat, receiverLon, t1.getLat(), t1.getLon(), t1Results);

            if (aircraftResults[0] < t1Results[0]) {
                result = -1;
            } else if (aircraftResults[0] == t1Results[0]) {
                result = 0;
            } else {
                result = 1;
            }
        } else {
            if (aircraft.getLat() != 0 && t1.getLat() == 0) {
                result = -1;
            } else if (aircraft.getLat() == 0 && t1.getLat() != 0) {
                result = 1;
            }
        }
        return result;
    }
}
