package com.prestos.adsbmonitor.model;

import java.util.Collections;
import java.util.List;

/**
 * Created by prestos on 24/10/2015.
 */
public class History {

    public static final String HISTORY = "history";
    private List<AircraftData> history;

    public List<AircraftData> getHistory() {
        return history;
    }

    public void setHistory(List<AircraftData> history) {
        this.history = history;
        Collections.sort(history, new AircraftDataComparator());
    }

    public int getAircraftHistoryCount() {
        int result = 0;
        for (AircraftData data : history) {
            result += data.getAircraftList().size();
        }
        return result;
    }
}
