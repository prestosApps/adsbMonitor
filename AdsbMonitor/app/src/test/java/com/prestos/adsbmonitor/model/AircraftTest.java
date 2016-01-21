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

/**
 * Created by prestos on 21/01/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class AircraftTest {

    private static final String VALID_AIRCRAFT = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_NO_MLAT = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";

    private Aircraft aircraft;
    private JsonReader jsonReader;

    @Before
    public void setUp() {
        jsonReader = new JsonReader(new StringReader(VALID_AIRCRAFT));
        try {
            aircraft = new Aircraft(jsonReader);
        } catch (ApplicationException e) {
            e.printStackTrace();
            Assert.fail("Unexpected test failure");
        }
    }

    @Test
    public void aircraft_valid() {
        Assert.assertNotNull(aircraft);
    }

    @Test
    public void hex_valid() {
        Assert.assertEquals("a4a998", aircraft.getHex());
    }

    @Test
    public void squawk_valid() {
        Assert.assertEquals("2702", aircraft.getSquawk());
    }

    @Test
    public void flight_valid() {
        Assert.assertEquals("N4T     ", aircraft.getFlight());
    }

    @Test
    public void lat_valid() {
        Assert.assertEquals(51.540683d, aircraft.getLat());
    }

    @Test
    public void lon_valid() {
        Assert.assertEquals(-0.541382d, aircraft.getLon());
    }

    @Test
    public void nucp_valid() {
        Assert.assertEquals(0, aircraft.getNucp());
    }

    @Test
    public void seen_pos_valid() {
        Assert.assertEquals(1.7d, aircraft.getSeenPos());
    }

    @Test
    public void altitude_valid() {
        Assert.assertEquals("45000", aircraft.getAltitude());
    }

    @Test
    public void vert_rate_valid() {
        Assert.assertEquals(-256, aircraft.getVertRate());
    }

    @Test
    public void track_valid() {
        Assert.assertEquals(104, aircraft.getTrack());
    }

    @Test
    public void speed_valid() {
        Assert.assertEquals(599, aircraft.getSpeed());
    }

    @Test
    public void mlat_valid() {
        Assert.assertEquals(true, aircraft.isMlat());
    }

    @Test
    public void mlat_false_valid() {
        jsonReader = new JsonReader(new StringReader(VALID_AIRCRAFT_NO_MLAT));
        try {
            aircraft = new Aircraft(jsonReader);
        } catch (ApplicationException e) {
            e.printStackTrace();
            Assert.fail("Unexpected test failure");
        }
        Assert.assertEquals(false, aircraft.isMlat());
    }

    @Test
    public void messages_valid() {
        Assert.assertEquals(710, aircraft.getMessages());
    }

    @Test
    public void seen_valid() {
        Assert.assertEquals(0.0, aircraft.getSeen());
    }

    @Test
    public void rssi_valid() {
        Assert.assertEquals(-19.5d, aircraft.getRssi());
    }
}
