package com.prestos.adsbmonitor.model;

import android.util.JsonReader;

import java.io.IOException;

/**
 * Created by prestos on 24/10/2015.
 */
public class Cpu {
    public static final String DEMOD = "demod";
    public static final String READER = "reader";
    public static final String BACKGROUND = "background";

    private long demod;
    private long reader;
    private long background;

    public Cpu(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals(DEMOD)) {
                demod = jsonReader.nextLong();
            } else if (name.equals(READER)) {
                reader = jsonReader.nextLong();
            } else if (name.equals(BACKGROUND)) {
                background = jsonReader.nextLong();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
    }

    public long getDemod() {
        return demod;
    }

    public void setDemod(long demod) {
        this.demod = demod;
    }

    public long getReader() {
        return reader;
    }

    public void setReader(long reader) {
        this.reader = reader;
    }

    public long getBackground() {
        return background;
    }

    public void setBackground(long background) {
        this.background = background;
    }
}
