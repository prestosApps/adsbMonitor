package com.prestos.adsbmonitor.filters;

import android.util.JsonReader;

import com.prestos.adsbmonitor.ApplicationException;
import com.prestos.adsbmonitor.BuildConfig;
import com.prestos.adsbmonitor.model.Aircraft;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prestos on 01/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class AircraftSquawkFilterTest {

    private static final String VALID_AIRCRAFT_1 = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_2 = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_3 = "{\"hex\": \"a4a998\",\"squawk\": \"7700\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_4 = "{\"hex\": \"a4a998\",\"squawk\": \"7700\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_5 = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_6 = "{\"hex\": \"a4a998\",\"squawk\": \"7700\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_7 = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";

    List<Aircraft> aircraftList;

    @Before
    public void setUp() {
        aircraftList = new ArrayList<Aircraft>();
    }

    @Test
    public void aircraftSquawkFilter_success() {
        setSuccessData();
        List<List<Aircraft>> resultList = AircraftSquawkFilter.filter(aircraftList);
        Assert.assertEquals(2, resultList.size());
    }

    @Test
    public void aircraftSquawkFilter_success_lists_filtered(){
        setSuccessData();
        List<List<Aircraft>> resultList = AircraftSquawkFilter.filter(aircraftList);
        List<Aircraft> filteredList1 = resultList.get(0);
        List<Aircraft> filteredList2 = resultList.get(1);

        for(Aircraft aircraft : filteredList1){
            Assert.assertEquals("2702", aircraft.getSquawk());
        }

        for(Aircraft aircraft : filteredList2){
            Assert.assertEquals("7700", aircraft.getSquawk());
        }
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
