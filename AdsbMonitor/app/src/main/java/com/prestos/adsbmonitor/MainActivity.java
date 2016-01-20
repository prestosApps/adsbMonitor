package com.prestos.adsbmonitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.prestos.adsbmonitor.model.AircraftData;
import com.prestos.adsbmonitor.model.Receiver;
import com.prestos.adsbmonitor.model.Stats;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String HOSTNAME = "192.168.1.79";
    private Handler mHandler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Bong!!!");
                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }

    private class ReceiverDataLoader extends AsyncTask<String, Void, Receiver> {

        private String URI = "/dump1090/data/receiver.json";

        @Override
        protected Receiver doInBackground(String... voids) {
            String receiverUrl = "http://" + voids[0] + URI;
            Receiver receiver = null;
//            try {
//                receiver = new Receiver(DataHandler.getData(receiverUrl));
//            } catch (IOException e) {
//                Log.e(MainActivity.ReceiverDataLoader.class.getName(), "AAaarrggh!!", e);
//            }
            return receiver;
        }

        @Override
        protected void onPostExecute(Receiver receiver) {
            super.onPostExecute(receiver);
            //handleReceiverResult(receiver);
        }
    }

    private void handleAircraftDataResult(AircraftData aircraftData) {

    }

    private class StatsDataLoader extends AsyncTask<String, Void, Stats> {

        private String URI = "/dump1090/data/stats.json";

        @Override
        protected Stats doInBackground(String... voids) {
            String receiverUrl = "http://" + voids[0] + URI;
            Stats stats = null;
//            try {
//                stats = new Stats(DataHandler.getData(receiverUrl));
//            } catch (IOException e) {
//                Log.e(MainActivity.ReceiverDataLoader.class.getName(), "AAaarrggh!!", e);
//            }
            return stats;
        }

        @Override
        protected void onPostExecute(Stats stats) {
            super.onPostExecute(stats);
            //handleStatsResult(stats);
        }
    }

    private class AircraftDataLoader extends AsyncTask<String, Void, AircraftData> {

        private String URI = "/dump1090/data/aircraft.json";
        private String HISTORY = "/dump1090/data/history_{0}.json";

        @Override
        protected AircraftData doInBackground(String... voids) {
            String receiverUrl = "http://" + voids[0] + URI;
            AircraftData aircraftData = null;
//            try {
//                /*
//                Now get the current data and add the historic data
//                 */
//                aircraftData = new AircraftData(DataHandler.getData(receiverUrl));
//            } catch (IOException e) {
//                Log.e(MainActivity.ReceiverDataLoader.class.getName(), "AAaarrggh!!", e);
//            }
            return aircraftData;
        }

        @Override
        protected void onPostExecute(AircraftData aircraftData) {
            super.onPostExecute(aircraftData);
            handleAircraftDataResult(aircraftData);
        }
    }
}
