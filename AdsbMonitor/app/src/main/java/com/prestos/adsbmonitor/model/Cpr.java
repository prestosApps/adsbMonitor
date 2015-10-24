package com.prestos.adsbmonitor.model;

/**
 * Created by prestos on 24/10/2015.
 */
public class Cpr {
    private int surface;
    private int airborne;
    private int globalOk;
    private int globalBad;
    private int globalRange;
    private int globalSpeed;
    private int globalSkipped;
    private int localOk;
    private int localAircraftRelative;
    private int localReceiverRelative;
    private int localSkipped;
    private int localRange;
    private int localSpeed;
    private int filtered;

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getAirborne() {
        return airborne;
    }

    public void setAirborne(int airborne) {
        this.airborne = airborne;
    }

    public int getGlobalOk() {
        return globalOk;
    }

    public void setGlobalOk(int globalOk) {
        this.globalOk = globalOk;
    }

    public int getGlobalBad() {
        return globalBad;
    }

    public void setGlobalBad(int globalBad) {
        this.globalBad = globalBad;
    }

    public int getGlobalRange() {
        return globalRange;
    }

    public void setGlobalRange(int globalRange) {
        this.globalRange = globalRange;
    }

    public int getGlobalSpeed() {
        return globalSpeed;
    }

    public void setGlobalSpeed(int globalSpeed) {
        this.globalSpeed = globalSpeed;
    }

    public int getGlobalSkipped() {
        return globalSkipped;
    }

    public void setGlobalSkipped(int globalSkipped) {
        this.globalSkipped = globalSkipped;
    }

    public int getLocalOk() {
        return localOk;
    }

    public void setLocalOk(int localOk) {
        this.localOk = localOk;
    }

    public int getLocalAircraftRelative() {
        return localAircraftRelative;
    }

    public void setLocalAircraftRelative(int localAircraftRelative) {
        this.localAircraftRelative = localAircraftRelative;
    }

    public int getLocalReceiverRelative() {
        return localReceiverRelative;
    }

    public void setLocalReceiverRelative(int localReceiverRelative) {
        this.localReceiverRelative = localReceiverRelative;
    }

    public int getLocalSkipped() {
        return localSkipped;
    }

    public void setLocalSkipped(int localSkipped) {
        this.localSkipped = localSkipped;
    }

    public int getLocalRange() {
        return localRange;
    }

    public void setLocalRange(int localRange) {
        this.localRange = localRange;
    }

    public int getLocalSpeed() {
        return localSpeed;
    }

    public void setLocalSpeed(int localSpeed) {
        this.localSpeed = localSpeed;
    }

    public int getFiltered() {
        return filtered;
    }

    public void setFiltered(int filtered) {
        this.filtered = filtered;
    }
}
