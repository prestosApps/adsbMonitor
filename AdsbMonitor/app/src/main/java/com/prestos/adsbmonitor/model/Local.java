package com.prestos.adsbmonitor.model;

import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prestos on 24/10/2015.
 */
public class Local {
    private long samplesProcessed;
    private long samplesDropped;
    private long modeac;
    private long modes;
    private long bad;
    private long unknownIcao;
    private List<Long> accepted;
    private double signal;
    private double peakSignal;
    private long strongSignals;

    public Local(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals(Stat.SAMPLES_PROCESSED)) {
                samplesProcessed = jsonReader.nextLong();
            } else if (name.equals(Stat.SAMPLES_DROPPED)) {
                samplesDropped = jsonReader.nextLong();
            } else if (name.equals(Stat.MODEAC)) {
                modeac = jsonReader.nextLong();
            } else if (name.equals(Stat.MODES)) {
                modes = jsonReader.nextLong();
            } else if (name.equals(Stat.BAD)) {
                bad = jsonReader.nextLong();
            } else if (name.equals(Stat.UNKNOWN_ICAO)) {
                unknownIcao = jsonReader.nextLong();
            } else if (name.equals(Stat.SIGNAL)) {
                signal = jsonReader.nextDouble();
            } else if (name.equals(Stat.PEAK_SIGNAL)) {
                peakSignal = jsonReader.nextDouble();
            } else if (name.equals(Stat.STRONG_SIGNALS)) {
                strongSignals = jsonReader.nextLong();
            } else if (name.equals(Stat.ACCEPTED)) {
                accepted = new ArrayList<Long>();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    accepted.add(jsonReader.nextLong());
                }
                jsonReader.endArray();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
    }

    public long getSamplesProcessed() {
        return samplesProcessed;
    }

    public void setSamplesProcessed(long samplesProcessed) {
        this.samplesProcessed = samplesProcessed;
    }

    public long getSamplesDropped() {
        return samplesDropped;
    }

    public void setSamplesDropped(long samplesDropped) {
        this.samplesDropped = samplesDropped;
    }

    public long getModeac() {
        return modeac;
    }

    public void setModeac(long modeac) {
        this.modeac = modeac;
    }

    public long getModes() {
        return modes;
    }

    public void setModes(long modes) {
        this.modes = modes;
    }

    public long getBad() {
        return bad;
    }

    public void setBad(long bad) {
        this.bad = bad;
    }

    public long getUnknownIcao() {
        return unknownIcao;
    }

    public void setUnknownIcao(long unknownIcao) {
        this.unknownIcao = unknownIcao;
    }

    public List<Long> getAccepted() {
        return accepted;
    }

    public void setAccepted(List<Long> accepted) {
        this.accepted = accepted;
    }

    public double getSignal() {
        return signal;
    }

    public void setSignal(double signal) {
        this.signal = signal;
    }

    public double getPeakSignal() {
        return peakSignal;
    }

    public void setPeakSignal(double peakSignal) {
        this.peakSignal = peakSignal;
    }

    public long getStrongSignals() {
        return strongSignals;
    }

    public void setStrongSignals(long strongSignals) {
        this.strongSignals = strongSignals;
    }
}
