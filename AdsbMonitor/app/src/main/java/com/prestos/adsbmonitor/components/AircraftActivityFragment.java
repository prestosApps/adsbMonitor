package com.prestos.adsbmonitor.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.prestos.adsbmonitor.AircraftArrayAdapter;
import com.prestos.adsbmonitor.AndroidObfuscator;
import com.prestos.adsbmonitor.ApplicationErrorListener;
import com.prestos.adsbmonitor.ApplicationException;
import com.prestos.adsbmonitor.ApplicationURL;
import com.prestos.adsbmonitor.DataHandler;
import com.prestos.adsbmonitor.R;
import com.prestos.adsbmonitor.model.Aircraft;
import com.prestos.adsbmonitor.model.AircraftData;
import com.prestos.adsbmonitor.model.AircraftDistanceComparator;
import com.prestos.adsbmonitor.model.Receiver;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A placeholder fragment containing a simple view.
 */
public class AircraftActivityFragment extends Fragment {

    private String hostname = null;
    private ListView aircraftListview;
    private TextView aircraftTotal;
    private TextView aircraftWithPositions;
    private TextView mlat;
    private TextView version;
    private ApplicationException applicationException;
    private AircraftArrayAdapter adapter;
    private Comparator listSortOrder;
    private ApplicationErrorListener mListener;
    private Handler mHandler;
    private Runnable runnable;
    SharedPreferences prefs;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ApplicationErrorListener) context;
        } catch (ClassCastException e) {
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
        mHandler = new Handler();

        prefs = getActivity().getSharedPreferences(AircraftActivity.PREFS, Context.MODE_PRIVATE);
        hostname = prefs.getString("prefs_ip_address", null);

        listSortOrder = new AircraftDistanceComparator(Double.valueOf(prefs.getString(Receiver.LATITUDE, "0")), Double.valueOf(prefs.getString(Receiver.LONGITUDE, "0")));

        aircraftListview = (ListView) getActivity().findViewById(R.id.aircraft_listview);
        aircraftTotal = (TextView) getActivity().findViewById(R.id.aircraft_total);
        aircraftWithPositions = (TextView) getActivity().findViewById(R.id.aircraft_total_with_positions);
        mlat = (TextView) getActivity().findViewById(R.id.aircraft_mlat);
        version = (TextView) getActivity().findViewById(R.id.aircraft_receiver_version);
        version.setText(prefs.getString(Receiver.VERSION, null));

        adapter = new AircraftArrayAdapter(getActivity(), new ArrayList<Aircraft>(), new AndroidObfuscator());
        aircraftListview.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        runnable = new Runnable() {
            @Override
            public void run() {
                new AircraftDataLoader().execute(hostname);
                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(runnable, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }

    private void handleAircraftDataResult(AircraftData aircraftData) {
        if (applicationException == null) {
            aircraftTotal.setText(String.valueOf(aircraftData.getAircraftList().size()));
            aircraftWithPositions.setText(String.valueOf(aircraftData.getAircraftWithPositions()));
            mlat.setText(String.valueOf(aircraftData.getMlat()));
            adapter.setAircraftList(aircraftData.getAircraftList(), listSortOrder);
        } else {
            mHandler.removeCallbacks(runnable);
            mListener.onApplicationError(applicationException);
        }
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
