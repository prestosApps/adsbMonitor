package com.prestos.adsbmonitor.model;

import android.util.JsonReader;

import java.io.IOException;

/**
 * Created by prestos on 24/10/2015.
 */
public class Stat {

    public static final String LOCAL = "local";
    public static final String REMOTE = "remote";
    public static final String HTTP_REQUESTS = "http_requests";
    public static final String CPR = "cpr";
    public static final String CPU = "cpu";
    public static final String ALTITUDE_SUPPRESSED = "altitude_suppressed";
    public static final String TRACKS = "tracks";
    public static final String MESSAGES = "messages";
    public static final String START = "start";
    public static final String END = "end";

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

    private double start;
    private double end;
    private Local local;
    private Remote remote;
    private long httpRequests;
    private Cpu cpu;
    private long altitudeSuppressed;
    private Cpr cpr;
    private Track track;
    private long messages;

    public Stat(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals(LOCAL)) {
                local = new Local(jsonReader);
            } else if (name.equals(REMOTE)) {
                remote = new Remote(jsonReader);
            } else if (name.equals(HTTP_REQUESTS)) {
                httpRequests = jsonReader.nextLong();
            } else if (name.equals(CPR)) {
                cpr = new Cpr(jsonReader);
            } else if (name.equals(ALTITUDE_SUPPRESSED)) {
                altitudeSuppressed = jsonReader.nextLong();
            } else if (name.equals(CPU)) {
                cpu = new Cpu(jsonReader);
            } else if (name.equals(TRACKS)) {
                track = new Track(jsonReader);
            } else if (name.equals(MESSAGES)) {
                messages = jsonReader.nextLong();
            } else if (name.equals(START)) {
                start = jsonReader.nextDouble();
            } else if (name.equals(END)) {
                end = jsonReader.nextDouble();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
    }

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    public Double getEnd() {
        return end;
    }

    public void setEnd(Double end) {
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

    public long getHttpRequests() {
        return httpRequests;
    }

    public void setHttpRequests(long httpRequests) {
        this.httpRequests = httpRequests;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public long getAltitudeSuppressed() {
        return altitudeSuppressed;
    }

    public void setAltitudeSuppressed(long altitudeSuppressed) {
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

    public long getMessages() {
        return messages;
    }

    public void setMessages(long messages) {
        this.messages = messages;
    }
}
