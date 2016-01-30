package com.prestos.adsbmonitor.model;

import java.util.Comparator;

/**
 * Created by prestos on 30/01/2016.
 */
public class AircraftDateComparator implements Comparator<Aircraft> {
    @Override
    public int compare(Aircraft aircraft, Aircraft t1) {
        Long value = aircraft.getTimestamp() - t1.getTimestamp();
        return value.intValue();
    }
}
