package com.prestos.adsbmonitor.filters;

import com.prestos.adsbmonitor.model.Aircraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prestos on 01/02/16.
 */
public class AircraftSquawkFilter {

    public static List<List<Aircraft>> filter(List<Aircraft> aircraftList) {
        List<List<Aircraft>> filteredList = new ArrayList<List<Aircraft>>();
        Map<String, List<Aircraft>> filteredMap = new HashMap<String, List<Aircraft>>();

        for (Aircraft aircraft : aircraftList) {
            if (filteredMap.containsKey(aircraft.getSquawk())) {
                filteredMap.get(aircraft.getSquawk()).add(aircraft);
            } else {
                List<Aircraft> subAircraftList = new ArrayList<Aircraft>();
                subAircraftList.add(aircraft);
                filteredMap.put(aircraft.getSquawk(), subAircraftList);
            }
        }

        for (Map.Entry<String, List<Aircraft>> entry : filteredMap.entrySet()) {
            filteredList.add(entry.getValue());
        }
        return filteredList;
    }

}
