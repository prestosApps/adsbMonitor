package com.prestos.adsbmonitor.model;

import com.prestos.adsbmonitor.ApplicationException;
import com.prestos.adsbmonitor.BuildConfig;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

/**
 * Created by prestos on 20/01/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class AircraftDataTest {

    private static final String VALID_AIRCRAFT_STRING = "{ \"now\" : 1453331057.3,\"messages\" : 629740367,\"aircraft\" : [{\"hex\":\"4ca9ed\",\"messages\":25,\"seen\":12.0,\"rssi\":-25.5},{\"hex\":\"738072\",\"squawk\":\"4741\",\"lat\":51.302382,\"lon\":0.617676,\"nucp\":7,\"seen_pos\":0.1,\"altitude\":18175,\"vert_rate\":3136,\"track\":100,\"speed\":405,\"messages\":167,\"seen\":0.1,\"rssi\":-20.8},{\"hex\":\"a4a998\",\"squawk\":\"2702\",\"flight\":\"N4T     \",\"lat\":51.540683,\"lon\":-0.541382,\"nucp\":0,\"seen_pos\":1.7,\"altitude\":45000,\"vert_rate\":-256,\"track\":104,\"speed\":599,\"mlat\":[\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\":710,\"seen\":0.0,\"rssi\":-19.5}]}";
    private static final String INVALID_AIRCRAFT_STRING = " \"now\" : 1453331057.3,\"messages\" : 629740367,\"aircraft\" : [{\"hex\",\"messages\":25,\"seen\":12.0,\"rssi\":-25.5},{\"hex\":\"738072\",\"squawk\":\"4741\",\"lat\":51.302382,\"lon\":0.617676,\"nucp\":7,\"seen_pos\":0.1,\"altitude\":18175,\"vert_rate\":3136,\"track\":100,\"speed\":405,\"messages\":167,\"seen\":0.1,\"rssi\":-20.8},{\"hex\":\"a4a998\",\"squawk\":\"2702\",\"flight\":\"N4T     \",\"lat\":51.540683,\"lon\":-0.541382,\"nucp\":0,\"seen_pos\":1.7,\"altitude\":45000,\"vert_rate\":-256,\"track\":104,\"speed\":599,\"mlat\":[\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\":710,\"seen\":0.0,\"rssi\":-19.5}]}";
    private static final String NULL_AIRCRAFT_STRING = null;

    private AircraftData aircraftData;
    private Date nowDate;

    @Before
    public void setUp() {
        nowDate = new Date();
        nowDate.setTime(1453331057000l);
        try {
            aircraftData = new AircraftData(VALID_AIRCRAFT_STRING);
        } catch (ApplicationException e) {
            e.printStackTrace();
            Assert.fail("Unexpected test failure");
        }
    }

    @Test
    public void aircraftData_object_constructed() {
        Assert.assertNotNull(aircraftData);
    }

    @Test
    public void aircraftData_object_null_string() {
        try {
            aircraftData = new AircraftData(NULL_AIRCRAFT_STRING);
            Assert.fail("Test should have failed");
        } catch (ApplicationException e) {
            Assert.assertEquals(Errors.JSON_PARSING_ERROR, e.getError());
        }
    }

    @Test
    public void aircraftData_object_invalid_string() {
        try {
            aircraftData = new AircraftData(INVALID_AIRCRAFT_STRING);
            Assert.fail("Test should have failed");
        } catch (ApplicationException e) {
            Assert.assertEquals(Errors.JSON_PARSING_ERROR, e.getError());
        }
    }

    @Test
    public void aircraftData_messages_valid() {
        Assert.assertEquals(629740367l, aircraftData.getMessages());
    }

    @Test
    public void aircraftData_dates_valid() {
        Assert.assertEquals(1453331057.3d, aircraftData.getNow());
        Assert.assertEquals(0, nowDate.compareTo(aircraftData.getNowAsDate()));
    }

    @Test
    public void aircraftData_aircraftList_valid() {
        Assert.assertEquals(3, aircraftData.getAircraftList().size());
    }

    @Test
    public void aircraftData_aircraft_with_positions_valid() {
        Assert.assertEquals(2, aircraftData.getAircraftWithPositions());
    }

    @Test
    public void aircraftData_aircraft_with_mlat_valid() {
        Assert.assertEquals(1, aircraftData.getMlat());
    }
}
