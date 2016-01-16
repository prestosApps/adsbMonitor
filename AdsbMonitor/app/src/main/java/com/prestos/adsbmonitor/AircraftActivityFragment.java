package com.prestos.adsbmonitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.prestos.adsbmonitor.model.AircraftData;

import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public class AircraftActivityFragment extends Fragment {

    private static final String HOSTNAME = "192.168.1.79";
    private ListView aircraftListview;
    private TextView aircraftTotal;
    private TextView time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aircraft, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aircraftListview = (ListView) getActivity().findViewById(R.id.aircraft_listview);
        aircraftTotal = (TextView) getActivity().findViewById(R.id.aircraft_total);
        time = (TextView) getActivity().findViewById(R.id.aircraft_time);
        new AircraftDataLoader().execute(HOSTNAME);
    }

    private void handleAircraftDataResult(AircraftData aircraftData) {
        aircraftTotal.setText(aircraftData.getAircraftList().size() + " aircraft");
        time.setText(DateUtils.formatDateTime(getContext(), aircraftData.getNowAsDate().getTime(), DateUtils.FORMAT_SHOW_TIME));
        AircraftArrayAdapter adapter = new AircraftArrayAdapter(getActivity(), aircraftData.getAircraftList());
        aircraftListview.setAdapter(adapter);
    }

    /*
    Aircraft Async task
     */
    private class AircraftDataLoader extends AsyncTask<String, Void, AircraftData> {

        private String URI = "/dump1090/data/aircraft.json";
        private String HISTORY = "/dump1090/data/history_{0}.json";

        @Override
        protected AircraftData doInBackground(String... voids) {
            String receiverUrl = "http://" + voids[0] + URI;
            AircraftData aircraftData = null;
            try {
                /*
                Now get the current data and add the historic data
                 */
                aircraftData = new AircraftData(DataHandler.getData(receiverUrl));
            } catch (IOException e) {
                Log.e(AircraftActivityFragment.AircraftDataLoader.class.getName(), "AAaarrggh!!", e);
            }
            return aircraftData;
        }

        @Override
        protected void onPostExecute(AircraftData aircraftData) {
            super.onPostExecute(aircraftData);
            handleAircraftDataResult(aircraftData);
        }
    }


}
