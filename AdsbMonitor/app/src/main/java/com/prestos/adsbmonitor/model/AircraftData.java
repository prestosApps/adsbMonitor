package com.prestos.adsbmonitor.model;

import android.util.JsonReader;
import android.util.Log;

import com.prestos.adsbmonitor.ApplicationException;
import com.prestos.adsbmonitor.Errors;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by prestos on 24/10/2015.
 */
public class AircraftData {

    public static final String AIRCRAFT_DATA = "aircraftData";
    public static final String NOW = "now";
    public static final String MESSAGES = "messages";
    public static final String AIRCRAFT = "aircraft";

    private Double now;
    private long messages;
    private List<Aircraft> aircraftList;
    private int aircraftWithPositions = 0;
    private int mlat = 0;

    public AircraftData(String jsonString) throws ApplicationException {
        try {
            JsonReader jsonReader = new JsonReader(new StringReader(jsonString));
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                if (name.equals(MESSAGES)) {
                    messages = jsonReader.nextLong();
                } else if (name.equals(AIRCRAFT)) {
                    aircraftList = new ArrayList<Aircraft>();
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        aircraftList.add(new Aircraft(jsonReader));
                    }
                    jsonReader.endArray();
                } else if (name.equals(NOW)) {
                    now = jsonReader.nextDouble();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.close();
        } catch (IOException ex) {
            Log.e(AircraftData.class.getName(), "Error occurred whilst trying to parse JSON string " + jsonString, ex);
            throw new ApplicationException("Unable to parse JSON", Errors.JSON_PARSING_ERROR, ex);
        } catch (NullPointerException ex) {
            Log.e(AircraftData.class.getName(), "Error occurred whilst trying to parse JSON string " + jsonString, ex);
            throw new ApplicationException("Unable to parse JSON", Errors.JSON_PARSING_ERROR, ex);
        }

        //Determine how many of the aircraft have positions or are MLAT
        for (Aircraft aircraft : aircraftList) {
            if (aircraft.getLat() != 0) {
                aircraftWithPositions++;
            }
            if (aircraft.isMlat()) {
                mlat++;
            }
        }
    }

    public Double getNow() {
        return now;
    }

    public Date getNowAsDate() {
        Long value = now.longValue();
        Date date = new Date();
        date.setTime(value * 1000);
        return date;
    }

    public void setNow(Double now) {
        this.now = now;
    }

    public long getMessages() {
        return messages;
    }

    public void setMessages(long messages) {
        this.messages = messages;
    }

    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    public int getAircraftWithPositions() {
        return aircraftWithPositions;
    }

    public void setAircraftWithPositions(int aircraftWithPositions) {
        this.aircraftWithPositions = aircraftWithPositions;
    }

    public int getMlat() {
        return mlat;
    }

    public void setMlat(int mlat) {
        this.mlat = mlat;
    }
}
