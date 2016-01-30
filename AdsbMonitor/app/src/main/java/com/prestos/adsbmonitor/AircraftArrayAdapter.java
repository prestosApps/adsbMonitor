package com.prestos.adsbmonitor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.prestos.adsbmonitor.components.AircraftActivity;
import com.prestos.adsbmonitor.model.Aircraft;
import com.prestos.adsbmonitor.model.Receiver;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by prestos on 16/01/2016.
 */
public class AircraftArrayAdapter extends ArrayAdapter<Aircraft> {

    private static final double METRES_TO_NAUTICAL_MILES = 0.000539957d;

    private List<Aircraft> aircraftList;
    private Activity context;
    private AndroidObfuscator androidObfuscator;
    private double receiverLat;
    private double receiverLon;

    private TextView hexcode;
    private TextView flight;
    private TextView squawk;
    private TextView altitude;
    private TextView speed;
    private TextView track;
    private TextView vertical;
    private TextView distanceFromReceiver;

    public AircraftArrayAdapter(Activity context, List<Aircraft> objects, AndroidObfuscator androidObfuscator) {
        super(context, R.layout.aircraft_list_item, objects);
        aircraftList = objects;
        this.context = context;
        this.androidObfuscator = androidObfuscator;
        SharedPreferences prefs = context.getSharedPreferences(AircraftActivity.PREFS, Context.MODE_PRIVATE);
        receiverLat = Double.valueOf(prefs.getString(Receiver.LATITUDE, "0"));
        receiverLon = Double.valueOf(prefs.getString(Receiver.LONGITUDE, "0"));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.aircraft_list_item, null);
        }

        hexcode = (TextView) rowView.findViewById(R.id.aircraft_hexcode);
        flight = (TextView) rowView.findViewById(R.id.aircraft_flight);
        squawk = (TextView) rowView.findViewById(R.id.aircraft_squawk);
        altitude = (TextView) rowView.findViewById(R.id.aircraft_altitude);
        speed = (TextView) rowView.findViewById(R.id.aircraft_speed);
        vertical = (TextView) rowView.findViewById(R.id.aircraft_vertical);
        track = (TextView) rowView.findViewById(R.id.aircraft_track);
        distanceFromReceiver = (TextView) rowView.findViewById(R.id.aircraft_distance_from_receiver);

        /*
        Set values for the required fields
         */
        hexcode.setText(aircraftList.get(position).getHex().toUpperCase());
        flight.setText(aircraftList.get(position).getFlight());
        squawk.setText(aircraftList.get(position).getSquawk());
        altitude.setText(aircraftList.get(position).getAltitude());
        speed.setText(String.valueOf(aircraftList.get(position).getSpeed()));
        vertical.setText(getVerticalValue(aircraftList.get(position)));
        track.setText(String.valueOf(aircraftList.get(position).getTrack()));

        if (aircraftList.get(position).getLat() != 0) {
            DecimalFormat df = new DecimalFormat("###.#");
            distanceFromReceiver.setText(df.format(calculateDistanceFromReceiver(aircraftList.get(position))));
            distanceFromReceiver.setVisibility(View.VISIBLE);
        } else {
            distanceFromReceiver.setVisibility(View.GONE);
        }

        /*
        Set row colour based on content. Mlat = blue, has position = green, everything else is white
         */
        if (aircraftList.get(position).isMlat()) {
            rowView.setBackgroundColor(androidObfuscator.getColour(213, 213, 255));
        } else if (aircraftList.get(position).getLat() != 0) {
            rowView.setBackgroundColor(androidObfuscator.getColour(213, 255, 213));
        } else {
            rowView.setBackgroundColor(androidObfuscator.getColour(255, 255, 255));
        }

        return rowView;
    }

    /**
     * Sets the aircraft list without sorting it
     *
     * @param aircraftList
     */
    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList.clear();
        this.aircraftList.addAll(aircraftList);
        this.notifyDataSetChanged();
    }

    /**
     * Sorts the list with the provided comparator then stores the list
     *
     * @param aircraftList
     * @param comparator
     */
    public void setAircraftList(List<Aircraft> aircraftList, Comparator comparator) {
        Collections.sort(aircraftList, comparator);
        this.aircraftList.clear();
        this.aircraftList.addAll(aircraftList);
        this.notifyDataSetChanged();
    }

    private String getVerticalValue(Aircraft aircraft) {
        String response = null;
        int value = aircraft.getVertRate();
        if (value < 0) {
            response = "descending";
        } else if (value == 0) {
            response = "level";
        } else if (value > 0) {
            response = "climbing";
        }
        return response;
    }

    private double calculateDistanceFromReceiver(Aircraft aircraft) {
        float[] results = new float[3];
        Location.distanceBetween(receiverLat, receiverLon, aircraft.getLat(), aircraft.getLon(), results);
        return results[0] * METRES_TO_NAUTICAL_MILES;
    }
}
