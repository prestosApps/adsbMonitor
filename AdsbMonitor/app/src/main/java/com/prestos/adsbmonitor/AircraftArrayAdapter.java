package com.prestos.adsbmonitor;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.prestos.adsbmonitor.model.Aircraft;

import java.util.List;

/**
 * Created by prestos on 16/01/2016.
 */
public class AircraftArrayAdapter extends ArrayAdapter<Aircraft> {

    private List<Aircraft> aircraftList;
    private Activity context;

    private TextView hexcode;
    private TextView flight;
    private TextView altitude;
    private TextView speed;
    private TextView track;
    private TextView vertical;

    public AircraftArrayAdapter(Activity context, List<Aircraft> objects) {
        super(context, R.layout.aircraft_list_item, objects);
        aircraftList = objects;
        this.context = context;
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
        altitude = (TextView) rowView.findViewById(R.id.aircraft_altitude);
        speed = (TextView) rowView.findViewById(R.id.aircraft_speed);
        track = (TextView) rowView.findViewById(R.id.aircraft_track);
        vertical = (TextView) rowView.findViewById(R.id.aircraft_vertical);

        hexcode.setText(aircraftList.get(position).getHex().toUpperCase());
        flight.setText(aircraftList.get(position).getFlight());
        altitude.setText(aircraftList.get(position).getAltitude());
        speed.setText(String.valueOf(aircraftList.get(position).getSpeed()));
        vertical.setText(getVerticalValue(aircraftList.get(position)));

        /*
        Set row colour based on content. Mlat = blue, has position = green, everything else is white
         */
        if (aircraftList.get(position).isMlat()) {
            rowView.setBackgroundColor(Color.rgb(213, 213, 255));
        } else if(aircraftList.get(position).getLat() != 0){
            rowView.setBackgroundColor(Color.rgb(213, 255, 213));
        } else{
            rowView.setBackgroundColor(Color.rgb(255, 255, 255));
        }

        return rowView;
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
}
