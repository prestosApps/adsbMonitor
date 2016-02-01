package com.prestos.adsbmonitor.model;

import com.prestos.adsbmonitor.filters.AircraftSquawkFilter;

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

    public static List<AircraftSummary> getSummaries(List<Aircraft> aircraftList) {
        List<List<Aircraft>> filteredAircraftList = AircraftSquawkFilter.filter(aircraftList);
        List<AircraftSummary> aircraftSummaryList = new ArrayList<AircraftSummary>();

        for (List<Aircraft> aircraftListItem : filteredAircraftList) {
            Collections.sort(aircraftListItem, new AircraftDateComparator());
            if (aircraftList.size() > 0) {
                AircraftSummary aircraftSummary = new AircraftSummary();
                aircraftSummary.setFirstSeenInPeriod(aircraftListItem.get(0).getTimestamp());
                aircraftSummary.setLastSeenInPeriod(aircraftListItem.get((aircraftListItem.size() - 1)).getTimestamp());
                aircraftSummary.setHexcode(aircraftListItem.get(0).getHex());
                aircraftSummary.setSquawk(aircraftListItem.get(0).getSquawk());
                aircraftSummary.setFlight(aircraftListItem.get(0).getFlight());
                aircraftSummary.setMlat(aircraftListItem.get(0).isMlat());
                aircraftSummary.setMessages(aircraftListItem.get((aircraftListItem.size() - 1)).getMessages());
                aircraftSummaryList.add(aircraftSummary);
            }
        }

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
