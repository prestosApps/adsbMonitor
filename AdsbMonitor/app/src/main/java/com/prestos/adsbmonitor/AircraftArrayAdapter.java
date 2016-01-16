package com.prestos.adsbmonitor;

import android.app.Activity;
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

        hexcode.setText(aircraftList.get(position).getHex().toUpperCase());
        flight.setText(aircraftList.get(position).getFlight());
        altitude.setText(aircraftList.get(position).getAltitude());

        return rowView;
    }
}
