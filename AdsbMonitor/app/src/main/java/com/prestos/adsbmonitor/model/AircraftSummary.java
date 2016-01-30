package com.prestos.adsbmonitor.model;

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

    public AircraftSummary(List<Aircraft> aircraftList) {
        for (Aircraft aircraft : aircraftList) {
            hexcode = aircraft.getHex();
            squawk = aircraft.getSquawk();
            flight = aircraft.getFlight();
            messages += aircraft.getMessages();
            mlat = aircraft.isMlat();
        }

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
}
