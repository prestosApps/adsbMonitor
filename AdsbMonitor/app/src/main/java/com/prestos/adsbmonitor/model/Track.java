package com.prestos.adsbmonitor.model;

import android.util.JsonReader;

import java.io.IOException;

/**
 * Created by prestos on 24/10/2015.
 */
public class Track {

    public static final String ALL = "all";
    public static final String SINGLE_MESSAGE = "single_message";

    private long all;
    private long singleMessage;

    public Track(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals(ALL)) {
                all = jsonReader.nextLong();
            } else if (name.equals(SINGLE_MESSAGE)) {
                singleMessage = jsonReader.nextLong();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
    }

    public long getAll() {
        return all;
    }

    public void setAll(long all) {
        this.all = all;
    }

    public long getSingleMessage() {
        return singleMessage;
    }

    public void setSingleMessage(long singleMessage) {
        this.singleMessage = singleMessage;
    }
}
