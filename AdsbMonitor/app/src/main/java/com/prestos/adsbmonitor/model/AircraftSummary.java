package com.prestos.adsbmonitor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A summary of some of the Aircraft objects data.  The primary purpose of this class is for getting the necessary data to be stored in the database
 * Created by prestos on 30/01/2016.
 */
public class AircraftSummary {

    private String hexcode;
    private String squawk;
    private String flight;
    private long messages;
    private boolean mlat;
    private long firstSeenInPeriod = 0;
    private long lastSeenInPeriod = 0;

    public AircraftSummary() {

    }

    public AircraftSummary(List<Aircraft> aircraftList) {
        Collections.sort(aircraftList, new AircraftDateComparator());

        if (aircraftList.size() > 0) {
            firstSeenInPeriod = aircraftList.get(0).getTimestamp();
            lastSeenInPeriod = aircraftList.get((aircraftList.size() - 1)).getTimestamp();
            hexcode = aircraftList.get(0).getHex();
            squawk = aircraftList.get(0).getSquawk();
            flight = aircraftList.get(0).getFlight();
            mlat = aircraftList.get(0).isMlat();
            messages = aircraftList.get((aircraftList.size() - 1)).getMessages();
        }
    }

    public static List<AircraftSummary> getSummaries(List<Aircraft> aircraftList) {
        Collections.sort(aircraftList, new AircraftDateComparator());
        List<AircraftSummary> aircraftSummaryList = new ArrayList<AircraftSummary>();
        return aircraftSummaryList;
    }

    public String getHexcode() {
        return hexcode;
    }

    public void setHexcode(String hexcode) {
        this.hexcode = hexcode;
    }

    public String getSquawk() {
        return squawk;
    }

    public void setSquawk(String squawk) {
        this.squawk = squawk;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public long getMessages() {
        return messages;
    }

    public void setMessages(long messages) {
        this.messages = messages;
    }

    public boolean isMlat() {
        return mlat;
    }

    public void setMlat(boolean mlat) {
        this.mlat = mlat;
    }

    public long getFirstSeenInPeriod() {
        return firstSeenInPeriod;
    }

    public void setFirstSeenInPeriod(long firstSeenInPeriod) {
        this.firstSeenInPeriod = firstSeenInPeriod;
    }

    public long getLastSeenInPeriod() {
        return lastSeenInPeriod;
    }

    public void setLastSeenInPeriod(long lastSeenInPeriod) {
        this.lastSeenInPeriod = lastSeenInPeriod;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String separator = " : ";
        sb.append(hexcode).append(separator).append(squawk).append(separator).append(flight).append(separator).append(messages).append(separator).append(isMlat()).append(separator).append(firstSeenInPeriod).append(separator).append(lastSeenInPeriod);
        return sb.toString();
    }
}
