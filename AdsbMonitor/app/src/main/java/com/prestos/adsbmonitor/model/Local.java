package com.prestos.adsbmonitor.model;

import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prestos on 24/10/2015.
 */
public class Local {
    private int samplesProcessed;
    private int samplesDropped;
    private int modeac;
    private int modes;
    private int bad;
    private int unknownIcao;
    private List<Integer> accepted;
    private double signal;
    private double peakSignal;
    private int strongSignals;

    public Local(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals(Stat.SAMPLES_PROCESSED)) {
                samplesProcessed = jsonReader.nextInt();
            } else if (name.equals(Stat.SAMPLES_DROPPED)) {
                samplesProcessed = jsonReader.nextInt();
            } else if (name.equals(Stat.MODEAC)) {
                modeac = jsonReader.nextInt();
            } else if (name.equals(Stat.MODES)) {
                modes = jsonReader.nextInt();
            } else if (name.equals(Stat.BAD)) {
                bad = jsonReader.nextInt();
            } else if (name.equals(Stat.UNKNOWN_ICAO)) {
                unknownIcao = jsonReader.nextInt();
            } else if (name.equals(Stat.SIGNAL)) {
                signal = jsonReader.nextDouble();
            } else if (name.equals(Stat.PEAK_SIGNAL)) {
                peakSignal = jsonReader.nextDouble();
            } else if (name.equals(Stat.STRONG_SIGNALS)) {
                strongSignals = jsonReader.nextInt();
            } else if (name.equals(Stat.ACCEPTED)) {
                accepted = new ArrayList<Integer>();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    accepted.add(jsonReader.nextInt());
                }
                jsonReader.endArray();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
    }

    public int getSamplesProcessed() {
        return samplesProcessed;
    }

    public void setSamplesProcessed(int samplesProcessed) {
        this.samplesProcessed = samplesProcessed;
    }

    public int getSamplesDropped() {
        return samplesDropped;
    }

    public void setSamplesDropped(int samplesDropped) {
        this.samplesDropped = samplesDropped;
    }

    public int getModeac() {
        return modeac;
    }

    public void setModeac(int modeac) {
        this.modeac = modeac;
    }

    public int getModes() {
        return modes;
    }

    public void setModes(int modes) {
        this.modes = modes;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public int getUnknownIcao() {
        return unknownIcao;
    }

    public void setUnknownIcao(int unknownIcao) {
        this.unknownIcao = unknownIcao;
    }

    public List<Integer> getAccepted() {
        return accepted;
    }

    public void setAccepted(List<Integer> accepted) {
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

    public int getStrongSignals() {
        return strongSignals;
    }

    public void setStrongSignals(int strongSignals) {
        this.strongSignals = strongSignals;
    }
}
