package com.prestos.adsbmonitor.model;

import java.util.List;

/**
 * Created by prestos on 24/10/2015.
 */
public class Remote {
    private int modeac;
    private int modes;
    private int bad;
    private int unknownIcao;
    private List<Integer> accepted;

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

}
