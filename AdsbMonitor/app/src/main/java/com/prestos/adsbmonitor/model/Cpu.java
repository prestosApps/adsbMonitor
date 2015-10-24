package com.prestos.adsbmonitor.model;

/**
 * Created by prestos on 24/10/2015.
 */
public class Cpu {
    private int demod;
    private int reader;
    private int background;

    public int getDemod() {
        return demod;
    }

    public void setDemod(int demod) {
        this.demod = demod;
    }

    public int getReader() {
        return reader;
    }

    public void setReader(int reader) {
        this.reader = reader;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }
}
