package com.prestos.adsbmonitor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prestos on 24/10/2015.
 */
public class History {

    public static final String HISTORY = "history";
    private List<AircraftData> history;

    public List<AircraftData> getHistory() {
        return history;
    }

    /**
     * Sets the list of AircraftData objects then sorts it in ascending order based on the aircraft data timestamp
     *
     * @param history List of AircraftData objects
     */
    public void setHistory(List<AircraftData> history) {
        this.history = history;
        Collections.sort(history, new AircraftDataTimeComparator());
    }

    public int getAircraftHistoryCount() {
        int result = 0;
        for (AircraftData data : history) {
            result += data.getAircraftList().size();
        }
        return result;
    }

    /**
     * Returns a map of all aircraft by hexcode
     *
     * @return Map containing a list of aircraft, indexed by aircraft hexcode
     */
    public Map<String, List<Aircraft>> reduce() {
        Map<String, List<Aircraft>> reducedDataMap = new HashMap<String, List<Aircraft>>();
        for (AircraftData aircraftData : history) {
            for (Aircraft aircraft : aircraftData.getAircraftList()) {
                if (reducedDataMap.containsKey(aircraft.getHex())) {
                    reducedDataMap.get(aircraft.getHex()).add(aircraft);
                } else {
                    List<Aircraft> aircraftList = new ArrayList<Aircraft>();
                    aircraftList.add(aircraft);
                    reducedDataMap.put(aircraft.getHex(), aircraftList);
                }
            }
        }
        return reducedDataMap;
    }
}
