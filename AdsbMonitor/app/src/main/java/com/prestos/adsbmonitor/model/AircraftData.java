package com.prestos.adsbmonitor.model;

import android.util.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by prestos on 24/10/2015.
 */
public class AircraftData {

    public static final String AIRCRAFT_DATA = "aircraftData";
    public static final String NOW = "now";
    public static final String MESSAGES = "messages";
    public static final String AIRCRAFT = "aircraft";

    private Double now;
    private long messages;
    private List<Aircraft> aircraftList;

    public AircraftData(String jsonString) throws IOException {
        JsonReader jsonReader = new JsonReader(new StringReader(jsonString));
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals(MESSAGES)) {
                messages = jsonReader.nextLong();
            } else if (name.equals(AIRCRAFT)) {
                aircraftList = new ArrayList<Aircraft>();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    aircraftList.add(new Aircraft(jsonReader));
                }
                jsonReader.endArray();
            } else if (name.equals(NOW)) {
                now = jsonReader.nextDouble();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.close();
    }

    public Double getNow() {
        return now;
    }

    public Date getNowAsDate() {
        Long value = now.longValue();
        Date date = new Date();
        date.setTime(value * 1000);
        return date;
    }

    public void setNow(Double now) {
        this.now = now;
    }

    public long getMessages() {
        return messages;
    }

    public void setMessages(long messages) {
        this.messages = messages;
    }

    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

}
