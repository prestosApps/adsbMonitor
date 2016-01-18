package com.prestos.adsbmonitor;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
public class AircraftActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String HOSTNAME = "192.168.1.79";
    private ListView aircraftListview;
    private TextView aircraftTotal;
    private TextView aircraftWithPositions;
    private TextView mlat;
    private TextView time;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aircraft, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        aircraftListview = (ListView) getActivity().findViewById(R.id.aircraft_listview);
        aircraftTotal = (TextView) getActivity().findViewById(R.id.aircraft_total);
        aircraftWithPositions = (TextView) getActivity().findViewById(R.id.aircraft_total_with_positions);
        mlat = (TextView) getActivity().findViewById(R.id.aircraft_mlat);
        time = (TextView) getActivity().findViewById(R.id.aircraft_time);
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.aircraft_refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        new AircraftDataLoader().execute(HOSTNAME);
    }

    private void handleAircraftDataResult(AircraftData aircraftData) {
        swipeRefreshLayout.setRefreshing(false);
        aircraftTotal.setText(String.valueOf(aircraftData.getAircraftList().size()));
        aircraftWithPositions.setText(String.valueOf(aircraftData.getAircraftWithPositions()));
        mlat.setText(String.valueOf(aircraftData.getMlat()));
        time.setText(DateUtils.formatDateTime(getContext(), aircraftData.getNowAsDate().getTime(), DateUtils.FORMAT_SHOW_TIME));
        AircraftArrayAdapter adapter = new AircraftArrayAdapter(getActivity(), aircraftData.getAircraftList());
        aircraftListview.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        new AircraftDataLoader().execute(HOSTNAME);
    }

    /*
    Aircraft Async task
     */
    private class AircraftDataLoader extends AsyncTask<String, Void, AircraftData> {

        private String URI = "/dump1090/data/aircraft.json";

        @Override
        protected AircraftData doInBackground(String... voids) {
            String receiverUrl = "http://" + voids[0] + URI;
            AircraftData aircraftData = null;
            try {
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
