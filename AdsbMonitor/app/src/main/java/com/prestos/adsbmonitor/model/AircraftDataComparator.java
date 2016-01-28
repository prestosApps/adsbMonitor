package com.prestos.adsbmonitor.model;

import com.prestos.adsbmonitor.model.AircraftData;

import java.util.Comparator;

/**
 * Created by prestos on 24/10/2015.
 */
public class AircraftDataComparator implements Comparator<AircraftData> {
    @Override
    public int compare(AircraftData aircraftData, AircraftData t1) {
        return Double.compare(aircraftData.getNow(), t1.getNow());
    }
}
