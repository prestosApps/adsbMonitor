package com.prestos.adsbmonitor.model;

import com.prestos.adsbmonitor.ApplicationException;
import com.prestos.adsbmonitor.BuildConfig;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by prestos on 30/01/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class HistoryTest {

    private static final String VALID_AIRCRAFT_STRING_1 = "{ \"now\" : 1453331057.3,\"messages\" : 629740367,\"aircraft\" : [{\"hex\":\"4ca9ed\",\"messages\":25,\"seen\":12.0,\"rssi\":-25.5},{\"hex\":\"738072\",\"squawk\":\"4741\",\"lat\":51.302382,\"lon\":0.617676,\"nucp\":7,\"seen_pos\":0.1,\"altitude\":18175,\"vert_rate\":3136,\"track\":100,\"speed\":405,\"messages\":167,\"seen\":0.1,\"rssi\":-20.8},{\"hex\":\"a4a998\",\"squawk\":\"2702\",\"flight\":\"N4T     \",\"lat\":51.540683,\"lon\":-0.541382,\"nucp\":0,\"seen_pos\":1.7,\"altitude\":45000,\"vert_rate\":-256,\"track\":104,\"speed\":599,\"mlat\":[\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\":710,\"seen\":0.0,\"rssi\":-19.5}]}";
    private static final String VALID_AIRCRAFT_STRING_2 = "{ \"now\" : 1453331058.3,\"messages\" : 629740367,\"aircraft\" : [{\"hex\":\"4ca9ed\",\"messages\":25,\"seen\":12.0,\"rssi\":-25.5},{\"hex\":\"738072\",\"squawk\":\"4741\",\"lat\":51.302382,\"lon\":0.617676,\"nucp\":7,\"seen_pos\":0.1,\"altitude\":18175,\"vert_rate\":3136,\"track\":100,\"speed\":405,\"messages\":167,\"seen\":0.1,\"rssi\":-20.8},{\"hex\":\"a4a998\",\"squawk\":\"2702\",\"flight\":\"N4T     \",\"lat\":51.540683,\"lon\":-0.541382,\"nucp\":0,\"seen_pos\":1.7,\"altitude\":45000,\"vert_rate\":-256,\"track\":104,\"speed\":599,\"mlat\":[\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\":710,\"seen\":0.0,\"rssi\":-19.5}]}";
    private static final String VALID_AIRCRAFT_STRING_3 = "{ \"now\" : 1453331059.3,\"messages\" : 629740367,\"aircraft\" : [{\"hex\":\"4ca9ed\",\"messages\":25,\"seen\":12.0,\"rssi\":-25.5},{\"hex\":\"738072\",\"squawk\":\"4741\",\"lat\":51.302382,\"lon\":0.617676,\"nucp\":7,\"seen_pos\":0.1,\"altitude\":18175,\"vert_rate\":3136,\"track\":100,\"speed\":405,\"messages\":167,\"seen\":0.1,\"rssi\":-20.8},{\"hex\":\"a4a998\",\"squawk\":\"2702\",\"flight\":\"N4T     \",\"lat\":51.540683,\"lon\":-0.541382,\"nucp\":0,\"seen_pos\":1.7,\"altitude\":45000,\"vert_rate\":-256,\"track\":104,\"speed\":599,\"mlat\":[\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\":710,\"seen\":0.0,\"rssi\":-19.5}]}";
    private static final String VALID_AIRCRAFT_STRING_4 = "{ \"now\" : 1453331060.3,\"messages\" : 629740367,\"aircraft\" : [{\"hex\":\"4ca9ed\",\"messages\":25,\"seen\":12.0,\"rssi\":-25.5},{\"hex\":\"738072\",\"squawk\":\"4741\",\"lat\":51.302382,\"lon\":0.617676,\"nucp\":7,\"seen_pos\":0.1,\"altitude\":18175,\"vert_rate\":3136,\"track\":100,\"speed\":405,\"messages\":167,\"seen\":0.1,\"rssi\":-20.8},{\"hex\":\"a4a998\",\"squawk\":\"2702\",\"flight\":\"N4T     \",\"lat\":51.540683,\"lon\":-0.541382,\"nucp\":0,\"seen_pos\":1.7,\"altitude\":45000,\"vert_rate\":-256,\"track\":104,\"speed\":599,\"mlat\":[\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\":710,\"seen\":0.0,\"rssi\":-19.5}]}";
    private static final String VALID_AIRCRAFT_STRING_5 = "{ \"now\" : 1453331061.3,\"messages\" : 629740367,\"aircraft\" : [{\"hex\":\"4ca9ed\",\"messages\":25,\"seen\":12.0,\"rssi\":-25.5},{\"hex\":\"738072\",\"squawk\":\"4741\",\"lat\":51.302382,\"lon\":0.617676,\"nucp\":7,\"seen_pos\":0.1,\"altitude\":18175,\"vert_rate\":3136,\"track\":100,\"speed\":405,\"messages\":167,\"seen\":0.1,\"rssi\":-20.8},{\"hex\":\"a4a998\",\"squawk\":\"2702\",\"flight\":\"N4T     \",\"lat\":51.540683,\"lon\":-0.541382,\"nucp\":0,\"seen_pos\":1.7,\"altitude\":45000,\"vert_rate\":-256,\"track\":104,\"speed\":599,\"mlat\":[\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\":710,\"seen\":0.0,\"rssi\":-19.5}]}";

    private History history;
    private List<AircraftData> aircraftDataList;

    @Before
    public void setUp() {
        history = new History();
        aircraftDataList = new ArrayList<AircraftData>();
        dataSetup();
    }

    @Test
    public void history_setHistory() {
        history.setHistory(aircraftDataList);
        Assert.assertEquals(5, history.getHistory().size());
    }

    @Test
    public void history_getAircraftHistoryCount() {
        history.setHistory(aircraftDataList);
        Assert.assertEquals(15, history.getAircraftHistoryCount());
    }

    @Test
    public void history_reduce() {
        history.setHistory(aircraftDataList);
        Map<String, List<Aircraft>> reducedMap = history.reduce();

        Assert.assertEquals(3, reducedMap.size());

        Assert.assertTrue(reducedMap.containsKey("4ca9ed"));
        Assert.assertTrue(reducedMap.containsKey("a4a998"));
        Assert.assertTrue(reducedMap.containsKey("738072"));

        Assert.assertEquals(5, reducedMap.get("4ca9ed").size());
        Assert.assertEquals(5, reducedMap.get("a4a998").size());
        Assert.assertEquals(5, reducedMap.get("738072").size());
    }

    private void dataSetup() {
        try {
            AircraftData aircraftData1 = new AircraftData(VALID_AIRCRAFT_STRING_1);
            AircraftData aircraftData2 = new AircraftData(VALID_AIRCRAFT_STRING_2);
            AircraftData aircraftData3 = new AircraftData(VALID_AIRCRAFT_STRING_3);
            AircraftData aircraftData4 = new AircraftData(VALID_AIRCRAFT_STRING_4);
            AircraftData aircraftData5 = new AircraftData(VALID_AIRCRAFT_STRING_5);
            aircraftDataList.add(aircraftData1);
            aircraftDataList.add(aircraftData2);
            aircraftDataList.add(aircraftData3);
            aircraftDataList.add(aircraftData4);
            aircraftDataList.add(aircraftData5);
        } catch (ApplicationException e) {
            Assert.fail("Test should have passed: " + e.getMessage());
        }
    }
}
