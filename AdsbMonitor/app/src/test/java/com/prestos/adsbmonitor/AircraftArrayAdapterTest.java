package com.prestos.adsbmonitor;

import android.graphics.drawable.ColorDrawable;
import android.util.JsonReader;
import android.view.View;
import android.widget.TextView;

import com.prestos.adsbmonitor.components.AircraftActivity;
import com.prestos.adsbmonitor.model.Aircraft;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prestos on 22/01/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class AircraftArrayAdapterTest {

    private static final String VALID_AIRCRAFT_LEVEL = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": 0,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_DESCENDING = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": -256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String VALID_AIRCRAFT_CLIMBING = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": 256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String MLAT_BACKGROUND_COLOUR = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": 256,\"track\": 104,\"speed\": 599,\"mlat\": [\"lat\",\"lon\",\"track\",\"speed\",\"vert_rate\"],\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String LOCATION_BACKGROUND_COLOUR = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"lat\": 51.540683,\"lon\": -0.541382,\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": 256,\"track\": 104,\"speed\": 599,\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";
    private static final String NO_LOCATION_BACKGROUND_COLOUR = "{\"hex\": \"a4a998\",\"squawk\": \"2702\",\"flight\": \"N4T     \",\"nucp\": 0,\"seen_pos\": 1.7,\"altitude\": 45000,\"vert_rate\": 256,\"track\": 104,\"speed\": 599,\"messages\": 710,\"seen\": 0.0,\"rssi\": -19.5}";

    private AircraftArrayAdapter aircraftArrayAdapter;
    private AircraftActivity aircraftActivity;
    private AndroidObfuscator androidObfuscator;
    private List<Aircraft> aircraftList;
    private View responseView;
    private Aircraft aircraft;
    private JsonReader jsonReader;

    public void setUp(String aircraftDataString) {
        jsonReader = new JsonReader(new StringReader(aircraftDataString));
        try {
            aircraft = new Aircraft(jsonReader);
        } catch (ApplicationException e) {
            e.printStackTrace();
            Assert.fail("Unexpected test failure");
        }
        aircraftList = new ArrayList<Aircraft>();
        aircraftList.add(aircraft);
        androidObfuscator = new AndroidObfuscator();
        aircraftActivity = Robolectric.setupActivity(AircraftActivity.class);
        aircraftArrayAdapter = new AircraftArrayAdapter(aircraftActivity, aircraftList, androidObfuscator);
    }

    @Test
    public void aircraftArrayAdapter_created() {
        setUp(VALID_AIRCRAFT_DESCENDING);
        Assert.assertNotNull(aircraftArrayAdapter);
    }

    @Test
    public void aircraftArrayAdapter_view_success() {
        setUp(VALID_AIRCRAFT_DESCENDING);
        View responseView = aircraftArrayAdapter.getView(0, null, null);

        TextView hexcode = (TextView) responseView.findViewById(R.id.aircraft_hexcode);
        Assert.assertEquals("A4A998", hexcode.getText());

        TextView flight = (TextView) responseView.findViewById(R.id.aircraft_flight);
        Assert.assertEquals("N4T     ", flight.getText());

        TextView squawk = (TextView) responseView.findViewById(R.id.aircraft_squawk);
        Assert.assertEquals("2702", squawk.getText());

        TextView altitude = (TextView) responseView.findViewById(R.id.aircraft_altitude);
        Assert.assertEquals("45000", altitude.getText());

        TextView speed = (TextView) responseView.findViewById(R.id.aircraft_speed);
        Assert.assertEquals("599", speed.getText());

        TextView vertical = (TextView) responseView.findViewById(R.id.aircraft_vertical);
        Assert.assertEquals("descending", vertical.getText());

        TextView track = (TextView) responseView.findViewById(R.id.aircraft_track);
        Assert.assertEquals("104", track.getText());
    }

    @Test
    public void aircraftArrayAdapter_level() {
        setUp(VALID_AIRCRAFT_LEVEL);
        View responseView = aircraftArrayAdapter.getView(0, null, null);

        TextView vertical = (TextView) responseView.findViewById(R.id.aircraft_vertical);
        Assert.assertEquals("level", vertical.getText());
    }

    @Test
    public void aircraftArrayAdapter_climbing() {
        setUp(VALID_AIRCRAFT_CLIMBING);
        View responseView = aircraftArrayAdapter.getView(0, null, null);

        TextView vertical = (TextView) responseView.findViewById(R.id.aircraft_vertical);
        Assert.assertEquals("climbing", vertical.getText());
    }

    @Test
    public void aircraftArrayAdapter_mlat_backgroundColour() {
        setUp(MLAT_BACKGROUND_COLOUR);
        View responseView = aircraftArrayAdapter.getView(0, null, null);
        ColorDrawable colorDrawable = (ColorDrawable) responseView.getBackground();
        Assert.assertEquals(androidObfuscator.getColour(213, 213, 255), colorDrawable.getColor());
    }

    @Test
    public void aircraftArrayAdapter_location_backgroundColour() {
        setUp(LOCATION_BACKGROUND_COLOUR);
        View responseView = aircraftArrayAdapter.getView(0, null, null);
        ColorDrawable colorDrawable = (ColorDrawable) responseView.getBackground();
        Assert.assertEquals(androidObfuscator.getColour(213, 255, 213), colorDrawable.getColor());
    }

    @Test
    public void aircraftArrayAdapter_no_location_backgroundColour() {
        setUp(NO_LOCATION_BACKGROUND_COLOUR);
        View responseView = aircraftArrayAdapter.getView(0, null, null);
        ColorDrawable colorDrawable = (ColorDrawable) responseView.getBackground();
        Assert.assertEquals(androidObfuscator.getColour(255, 255, 255), colorDrawable.getColor());
    }
}
