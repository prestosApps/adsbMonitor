package com.prestos.adsbmonitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class AircraftActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private String hostname = null;
    private ListView aircraftListview;
    private TextView aircraftTotal;
    private TextView aircraftWithPositions;
    private TextView mlat;
    private TextView time;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApplicationException applicationException;

    private ApplicationErrorListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ApplicationErrorListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() + " must implement ApplicationErrorListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aircraft, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        hostname = prefs.getString("prefs_ip_address", null);
        Log.d(AircraftActivityFragment.class.getName(), "Using IP Address: " + hostname);

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
        new AircraftDataLoader().execute(hostname);
    }

    private void handleAircraftDataResult(AircraftData aircraftData) {
        if (applicationException == null) {
            swipeRefreshLayout.setRefreshing(false);
            aircraftTotal.setText(String.valueOf(aircraftData.getAircraftList().size()));
            aircraftWithPositions.setText(String.valueOf(aircraftData.getAircraftWithPositions()));
            mlat.setText(String.valueOf(aircraftData.getMlat()));
            time.setText(DateUtils.formatDateTime(getContext(), aircraftData.getNowAsDate().getTime(), DateUtils.FORMAT_SHOW_TIME));
            AircraftArrayAdapter adapter = new AircraftArrayAdapter(getActivity(), aircraftData.getAircraftList(), new AndroidObfuscator());
            aircraftListview.setAdapter(adapter);
        } else {
            mListener.onApplicationError(applicationException);
        }
    }

    @Override
    public void onRefresh() {
        new AircraftDataLoader().execute(hostname);
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
            applicationException = null;
            try {
                ApplicationURL applicationURL = new ApplicationURL(receiverUrl);
                aircraftData = new AircraftData(DataHandler.getData(applicationURL));
            } catch (ApplicationException ex) {
                Log.e(AircraftActivityFragment.AircraftDataLoader.class.getName(), "Error trying to get aircraft data from " + receiverUrl, ex);
                applicationException = ex;
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
