package com.prestos.adsbmonitor.model;

import android.util.JsonReader;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by prestos on 22/10/2015.
 */
public class Receiver {

    public static final String RECEIVER = "receiver";

    private String jsonString;
    private String version;
    private int refresh;
    private int history;
    private double lat;
    private double lon;

    public Receiver(String jsonString) throws IOException {
        this.jsonString = jsonString;
        handleJsonMessage();
    }

    private void handleJsonMessage() throws IOException {
        JsonReader jsonReader = new JsonReader(new StringReader(jsonString));

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("version")) {
                version = jsonReader.nextString();
            } else if (name.equals("refresh")) {
                refresh = jsonReader.nextInt();
            } else if (name.equals("history")) {
                history = jsonReader.nextInt();
            } else if (name.equals("lat")) {
                lat = jsonReader.nextDouble();
            } else if (name.equals("lon")) {
                lon = jsonReader.nextDouble();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.close();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getRefresh() {
        return refresh;
    }

    public void setRefresh(int refresh) {
        this.refresh = refresh;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
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

}
