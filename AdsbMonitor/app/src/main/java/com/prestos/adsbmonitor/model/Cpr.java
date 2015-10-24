package com.prestos.adsbmonitor.model;

import android.util.JsonReader;

import java.io.IOException;

/**
 * Created by prestos on 24/10/2015.
 */
public class Cpr {
    public static final String SURFACE = "surface";
    public static final String AIRBORNE = "airborne";
    public static final String GLOBAL_OK = "global_ok";
    public static final String GLOBAL_BAD = "global_bad";
    public static final String GLOBAL_RANGE = "global_range";
    public static final String GLOBAL_SPEED = "global_speed";
    public static final String GLOBAL_SKIPPED = "global_skipped";
    public static final String LOCAL_OK = "local_ok";
    public static final String LOCAL_AIRCRAFT_RELATIVE = "local_aircraft_relative";
    public static final String LOCAL_RECEIVER_RELATIVE = "local_receiver_relative";
    public static final String LOCAL_SKIPPED = "local_skipped";
    public static final String LOCAL_RANGE = "local_range";
    public static final String LOCAL_SPEED = "local_speed";
    public static final String FILTERED = "filtered";

    private long surface;
    private long airborne;
    private long globalOk;
    private long globalBad;
    private long globalRange;
    private long globalSpeed;
    private long globalSkipped;
    private long localOk;
    private long localAircraftRelative;
    private long localReceiverRelative;
    private long localSkipped;
    private long localRange;
    private long localSpeed;
    private long filtered;

    public Cpr(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals(SURFACE)) {
                surface = jsonReader.nextLong();
            } else if (name.equals(AIRBORNE)) {
                airborne = jsonReader.nextLong();
            } else if (name.equals(GLOBAL_OK)) {
                globalOk = jsonReader.nextLong();
            } else if (name.equals(GLOBAL_BAD)) {
                globalBad = jsonReader.nextLong();
            } else if (name.equals(GLOBAL_RANGE)) {
                globalRange = jsonReader.nextLong();
            } else if (name.equals(GLOBAL_SPEED)) {
                globalSpeed = jsonReader.nextLong();
            } else if (name.equals(GLOBAL_SKIPPED)) {
                globalSkipped = jsonReader.nextLong();
            } else if (name.equals(LOCAL_OK)) {
                localOk = jsonReader.nextLong();
            } else if (name.equals(LOCAL_AIRCRAFT_RELATIVE)) {
                localAircraftRelative = jsonReader.nextLong();
            } else if (name.equals(LOCAL_RECEIVER_RELATIVE)) {
                localReceiverRelative = jsonReader.nextLong();
            } else if (name.equals(LOCAL_SKIPPED)) {
                localSkipped = jsonReader.nextLong();
            } else if (name.equals(LOCAL_RANGE)) {
                localRange = jsonReader.nextLong();
            } else if (name.equals(LOCAL_SPEED)) {
                localSpeed = jsonReader.nextLong();
            } else if (name.equals(FILTERED)) {
                filtered = jsonReader.nextLong();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
    }

    public long getSurface() {
        return surface;
    }

    public void setSurface(long surface) {
        this.surface = surface;
    }

    public long getAirborne() {
        return airborne;
    }

    public void setAirborne(long airborne) {
        this.airborne = airborne;
    }

    public long getGlobalOk() {
        return globalOk;
    }

    public void setGlobalOk(long globalOk) {
        this.globalOk = globalOk;
    }

    public long getGlobalBad() {
        return globalBad;
    }

    public void setGlobalBad(long globalBad) {
        this.globalBad = globalBad;
    }

    public long getGlobalRange() {
        return globalRange;
    }

    public void setGlobalRange(long globalRange) {
        this.globalRange = globalRange;
    }

    public long getGlobalSpeed() {
        return globalSpeed;
    }

    public void setGlobalSpeed(long globalSpeed) {
        this.globalSpeed = globalSpeed;
    }

    public long getGlobalSkipped() {
        return globalSkipped;
    }

    public void setGlobalSkipped(long globalSkipped) {
        this.globalSkipped = globalSkipped;
    }

    public long getLocalOk() {
        return localOk;
    }

    public void setLocalOk(long localOk) {
        this.localOk = localOk;
    }

    public long getLocalAircraftRelative() {
        return localAircraftRelative;
    }

    public void setLocalAircraftRelative(long localAircraftRelative) {
        this.localAircraftRelative = localAircraftRelative;
    }

    public long getLocalReceiverRelative() {
        return localReceiverRelative;
    }

    public void setLocalReceiverRelative(long localReceiverRelative) {
        this.localReceiverRelative = localReceiverRelative;
    }

    public long getLocalSkipped() {
        return localSkipped;
    }

    public void setLocalSkipped(long localSkipped) {
        this.localSkipped = localSkipped;
    }

    public long getLocalRange() {
        return localRange;
    }

    public void setLocalRange(long localRange) {
        this.localRange = localRange;
    }

    public long getLocalSpeed() {
        return localSpeed;
    }

    public void setLocalSpeed(long localSpeed) {
        this.localSpeed = localSpeed;
    }

    public long getFiltered() {
        return filtered;
    }

    public void setFiltered(long filtered) {
        this.filtered = filtered;
    }
}
