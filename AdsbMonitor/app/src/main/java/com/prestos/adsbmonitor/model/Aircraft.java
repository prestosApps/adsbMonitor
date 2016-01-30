package com.prestos.adsbmonitor.model;

import android.util.JsonReader;
import android.util.Log;

import com.prestos.adsbmonitor.ApplicationException;

import java.io.IOException;

/**
 * Created by prestos on 24/10/2015.
 */
public class Aircraft {

    public static final String HEX = "hex";
    public static final String SQUAWK = "squawk";
    public static final String FLIGHT = "flight";
    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String NUCP = "nucp";
    public static final String SEEN_POS = "seen_pos";
    public static final String ALTITUDE = "altitude";
    public static final String VERT_RATE = "vert_rate";
    public static final String TRACK = "track";
    public static final String SPEED = "speed";
    public static final String CATEGORY = "category";
    public static final String MESSAGES = "messages";
    public static final String SEEN = "seen";
    public static final String RSSI = "rssi";
    public static final String MLAT = "mlat";

    private String hex;
    private String squawk;
    private String flight;
    private double lat;
    private double lon;
    private int nucp;
    private double seenPos;
    private String altitude;
    private int vertRate;
    private int track;
    private int speed;
    private String category;
    private long messages;
    private double seen;
    private double rssi;
    private boolean mlat = false;
    private long timestamp;

    public Aircraft(JsonReader jsonReader) throws ApplicationException {
        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equals(HEX)) {
                    hex = jsonReader.nextString();
                } else if (name.equals(SQUAWK)) {
                    squawk = jsonReader.nextString();
                } else if (name.equals(FLIGHT)) {
                    flight = jsonReader.nextString();
                } else if (name.equals(LAT)) {
                    lat = jsonReader.nextDouble();
                } else if (name.equals(LON)) {
                    lon = jsonReader.nextDouble();
                } else if (name.equals(NUCP)) {
                    nucp = jsonReader.nextInt();
                } else if (name.equals(SEEN_POS)) {
                    seenPos = jsonReader.nextDouble();
                } else if (name.equals(ALTITUDE)) {
                    altitude = jsonReader.nextString();
                } else if (name.equals(VERT_RATE)) {
                    vertRate = jsonReader.nextInt();
                } else if (name.equals(TRACK)) {
                    track = jsonReader.nextInt();
                } else if (name.equals(SPEED)) {
                    speed = jsonReader.nextInt();
                } else if (name.equals(CATEGORY)) {
                    category = jsonReader.nextString();
                } else if (name.equals(MESSAGES)) {
                    messages = jsonReader.nextLong();
                } else if (name.equals(SEEN)) {
                    seen = jsonReader.nextDouble();
                } else if (name.equals(RSSI)) {
                    rssi = jsonReader.nextDouble();
                } else if (name.equals(MLAT)) {
                    mlat = true;
                    jsonReader.skipValue();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
        } catch (IOException e) {
            Log.e(Aircraft.class.getName(), "Error occurred whilst trying to parse JSON object", e);
            throw new ApplicationException("Unable to parse JSON", Errors.JSON_PARSING_ERROR, e);
        }
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getNucp() {
        return nucp;
    }

    public void setNucp(int nucp) {
        this.nucp = nucp;
    }

    public double getSeenPos() {
        return seenPos;
    }

    public void setSeenPos(double seenPos) {
        this.seenPos = seenPos;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public int getVertRate() {
        return vertRate;
    }

    public void setVertRate(int vertRate) {
        this.vertRate = vertRate;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getMessages() {
        return messages;
    }

    public void setMessages(long messages) {
        this.messages = messages;
    }

    public double getSeen() {
        return seen;
    }

    public void setSeen(double seen) {
        this.seen = seen;
    }

    public double getRssi() {
        return rssi;
    }

    public void setRssi(double rssi) {
        this.rssi = rssi;
    }

    public boolean isMlat() {
        return mlat;
    }

    public void setMlat(boolean mlat) {
        this.mlat = mlat;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
