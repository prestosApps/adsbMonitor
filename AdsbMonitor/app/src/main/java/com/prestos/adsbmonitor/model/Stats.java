package com.prestos.adsbmonitor.model;

import android.util.JsonReader;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by prestos on 24/10/2015.
 */
public class Stats {

    public static final String LATEST = "latest";
    public static final String LAST_1_MIN = "last1min";
    public static final String LAST_5_MIN = "last5min";
    public static final String LAST_15_MIN = "last15min";
    public static final String TOTAL = "total";


    private String jsonString;
    private Stat latest;
    private Stat last1Min;
    private Stat last5Min;
    private Stat last15Min;
    private Stat total;

    public Stats(String jsonString) throws IOException {
        this.jsonString = jsonString;
        handleJsonMessage();
    }

    private void handleJsonMessage() throws IOException {
        JsonReader jsonReader = new JsonReader(new StringReader(jsonString));
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals(LATEST)) {
                latest = new Stat(jsonReader);
            } else if (name.equals(LAST_1_MIN)) {
                last1Min = new Stat(jsonReader);
            } else if (name.equals(LAST_5_MIN)) {
                last5Min = new Stat(jsonReader);
            } else if (name.equals(LAST_15_MIN)) {
                last15Min = new Stat(jsonReader);
            } else if (name.equals(TOTAL)) {
                total = new Stat(jsonReader);
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.close();
    }

    public void setLatest(Stat latest) {
        this.latest = latest;
    }

    public Stat getLatest() {
        return latest;
    }

    public Stat getLast1Min() {
        return last1Min;
    }

    public void setLast1Min(Stat last1Min) {
        this.last1Min = last1Min;
    }

    public Stat getLast5Min() {
        return last5Min;
    }

    public void setLast5Min(Stat last5Min) {
        this.last5Min = last5Min;
    }

    public Stat getLast15Min() {
        return last15Min;
    }

    public void setLast15Min(Stat last15Min) {
        this.last15Min = last15Min;
    }

    public Stat getTotal() {
        return total;
    }

    public void setTotal(Stat total) {
        this.total = total;
    }
}
