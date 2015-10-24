package com.prestos.adsbmonitor.model;

import android.util.JsonReader;

import java.io.IOException;
import java.util.Date;

/**
 * Created by prestos on 24/10/2015.
 */
public class Stat {

    public static final String SAMPLES_PROCESSED = "samples_processed";
    public static final String SAMPLES_DROPPED = "samples_dropped";
    public static final String MODEAC = "modeac";
    public static final String MODES = "modes";
    public static final String BAD = "bad";
    public static final String UNKNOWN_ICAO = "unknown_icao";
    public static final String ACCEPTED = "accepted";
    public static final String STRONG_SIGNALS = "strong_signals";
    public static final String SIGNAL = "signal";
    public static final String PEAK_SIGNAL = "peak_signal";

    private Date start;
    private Date end;
    private Local local;
    private Remote remote;
    private int httpRequests;
    private Cpu cpu;
    private int altitudeSuppressed;
    private Cpr cpr;
    private Track track;
    private int messages;

    public Stat(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("local")) {
                local = new Local(jsonReader);
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Remote getRemote() {
        return remote;
    }

    public void setRemote(Remote remote) {
        this.remote = remote;
    }

    public int getHttpRequests() {
        return httpRequests;
    }

    public void setHttpRequests(int httpRequests) {
        this.httpRequests = httpRequests;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public int getAltitudeSuppressed() {
        return altitudeSuppressed;
    }

    public void setAltitudeSuppressed(int altitudeSuppressed) {
        this.altitudeSuppressed = altitudeSuppressed;
    }

    public Cpr getCpr() {
        return cpr;
    }

    public void setCpr(Cpr cpr) {
        this.cpr = cpr;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public int getMessages() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }
}
