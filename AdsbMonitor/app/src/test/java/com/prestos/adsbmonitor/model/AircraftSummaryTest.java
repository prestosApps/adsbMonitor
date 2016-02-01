package com.prestos.adsbmonitor.model;

import android.util.JsonReader;

import com.prestos.adsbmonitor.ApplicationException;
import com.prestos.adsbmonitor.BuildConfig;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prestos on 30/01/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class AircraftSummaryTest {

    private static final String VALID_AIRCRAFT_1 = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 110,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_2 = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 210,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_3 = "{\"hex\": \"a4a998\",\"squawk\": \"7700\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 310,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_4 = "{\"hex\": \"a4a998\",\"squawk\": \"7700\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 410,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_5 = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 510,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_6 = "{\"hex\": \"a4a998\",\"squawk\": \"7700\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 610,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_7 = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";

    List<Aircraft> aircraftList;

    @Before
    public void setUp() {
        aircraftList = new ArrayList<Aircraft>();
    }

    @Test
    public void success_basic() {
        setSuccessData();
        List<AircraftSummary> aircraftSummaryList = AircraftSummary.getSummaries(aircraftList);
        Assert.assertEquals(2, aircraftSummaryList.size());
    }

    @Test
    public void success_detail() {
        setSuccessData();
        List<AircraftSummary> aircraftSummaryList = AircraftSummary.getSummaries(aircraftList);
        AircraftSummary summary1 = aircraftSummaryList.get(0);
        AircraftSummary summary2 = aircraftSummaryList.get(1);

        Assert.assertEquals("a4a998", summary1.getHexcode());
        Assert.assertEquals("2702", summary1.getSquawk());
        Assert.assertEquals("N4T     ", summary1.getFlight());
        Assert.assertEquals(1453331057000l, summary1.getFirstSeenInPeriod());
        Assert.assertEquals(1453331057180l, summary1.getLastSeenInPeriod());
        Assert.assertEquals(710, summary1.getMessages());

        Assert.assertEquals("a4a998", summary2.getHexcode());
        Assert.assertEquals("7700", summary2.getSquawk());
        Assert.assertEquals("N4T     ", summary2.getFlight());
        Assert.assertEquals(1453331057060l, summary2.getFirstSeenInPeriod());
        Assert.assertEquals(1453331057150l, summary2.getLastSeenInPeriod());
        Assert.assertEquals(610, summary2.getMessages());
    }

    private void setSuccessData() {
        Aircraft aircraft1;
        Aircraft aircraft2;
        Aircraft aircraft3;
        Aircraft aircraft4;
        Aircraft aircraft5;
        Aircraft aircraft6;
        Aircraft aircraft7;

        try {
            //Load in data
            aircraft1 = new Aircraft(new JsonReader(new StringReader(VALID_AIRCRAFT_1)));
            aircraft2 = new Aircraft(new JsonReader(new StringReader(VALID_AIRCRAFT_2)));
            aircraft3 = new Aircraft(new JsonReader(new StringReader(VALID_AIRCRAFT_3)));
            aircraft4 = new Aircraft(new JsonReader(new StringReader(VALID_AIRCRAFT_4)));
            aircraft5 = new Aircraft(new JsonReader(new StringReader(VALID_AIRCRAFT_5)));
            aircraft6 = new Aircraft(new JsonReader(new StringReader(VALID_AIRCRAFT_6)));
            aircraft7 = new Aircraft(new JsonReader(new StringReader(VALID_AIRCRAFT_7)));

            //Set timestamps
            aircraft1.setTimestamp(1453331057000l);
            aircraft2.setTimestamp(1453331057030l);
            aircraft3.setTimestamp(1453331057060l);
            aircraft4.setTimestamp(1453331057090l);
            aircraft5.setTimestamp(1453331057120l);
            aircraft6.setTimestamp(1453331057150l);
            aircraft7.setTimestamp(1453331057180l);

            //Add to list
            aircraftList.add(aircraft1);
            aircraftList.add(aircraft2);
            aircraftList.add(aircraft3);
            aircraftList.add(aircraft4);
            aircraftList.add(aircraft5);
            aircraftList.add(aircraft6);
            aircraftList.add(aircraft7);

            //Bobs yer uncle, Fannies yer aunt
        } catch (ApplicationException e) {
            e.printStackTrace();
            junit.framework.Assert.fail("Unexpected test failure");
        }
    }
}
