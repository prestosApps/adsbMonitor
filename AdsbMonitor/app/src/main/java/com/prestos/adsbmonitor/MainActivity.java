package com.prestos.adsbmonitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.prestos.adsbmonitor.model.AircraftData;
import com.prestos.adsbmonitor.model.History;
import com.prestos.adsbmonitor.model.Receiver;
import com.prestos.adsbmonitor.model.Stats;

import java.io.IOException;
import java.text.Format;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String HOSTNAME = "192.168.1.79";
    private TextView txtVersionValue;
    private TextView txtRefreshValue;
    private TextView txtHistoryValue;
    private TextView txtLatValue;
    private TextView txtLonValue;
    /*
    Stats
     */
    private TextView txtStarted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtVersionValue = (TextView) findViewById(R.id.txtVersionValue);
        txtRefreshValue = (TextView) findViewById(R.id.txtRefreshValue);
        txtHistoryValue = (TextView) findViewById(R.id.txtHistoryValue);
        txtLatValue = (TextView) findViewById(R.id.txtLatValue);
        txtLonValue = (TextView) findViewById(R.id.txtLonValue);

        txtStarted = (TextView) findViewById(R.id.txtStarted);
        loadData();
    }

    private void loadData() {
        new ReceiverDataLoader().execute(HOSTNAME);
        new StatsDataLoader().execute(HOSTNAME);
    }

    private void handleReceiverResult(Receiver receiver) {
        Cache.setCacheItem(Receiver.RECEIVER, receiver);
        new AircraftDataLoader().execute(HOSTNAME);
        txtVersionValue.setText(receiver.getVersion());
        txtRefreshValue.setText(String.valueOf(receiver.getRefresh()));
        txtHistoryValue.setText(String.valueOf(receiver.getHistory()));
        txtLatValue.setText(String.valueOf(receiver.getLat()));
        txtLonValue.setText(String.valueOf(receiver.getLon()));
    }

    private void handleStatsResult(Stats stats) {
        Cache.setCacheItem(Stats.STATS, stats);
        Date date = stats.getTotal().getStartAsDate();
        Format dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        String pattern = ((SimpleDateFormat) dateFormat).toLocalizedPattern();
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        txtStarted.setText(df.format(date));
    }

    private void handleAircraftDataResult(AircraftData aircraftData) {
        Cache.setCacheItem(AircraftData.AIRCRAFT_DATA, aircraftData);
        History history = (History) Cache.getCacheItem(History.HISTORY);
        Log.d(this.getClass().getName(), aircraftData.getMessages() + " : " + aircraftData.getAircraftList().size());
        Log.d(this.getClass().getName(), "History: " + history.getHistory().size());
        Log.d(this.getClass().getName(), "Aircraft count: " + history.getAircraftHistoryCount());
    }

    private class ReceiverDataLoader extends AsyncTask<String, Void, Receiver> {

        private String URI = "/dump1090/data/receiver.json";

        @Override
        protected Receiver doInBackground(String... voids) {
            String receiverUrl = "http://" + voids[0] + URI;
            Receiver receiver = null;
            try {
                receiver = new Receiver(DataHandler.getData(receiverUrl));
            } catch (IOException e) {
                Log.e(MainActivity.ReceiverDataLoader.class.getName(), "AAaarrggh!!", e);
            }
            return receiver;
        }

        @Override
        protected void onPostExecute(Receiver receiver) {
            super.onPostExecute(receiver);
            handleReceiverResult(receiver);
        }
    }

    private class StatsDataLoader extends AsyncTask<String, Void, Stats> {

        private String URI = "/dump1090/data/stats.json";

        @Override
        protected Stats doInBackground(String... voids) {
            String receiverUrl = "http://" + voids[0] + URI;
            Stats stats = null;
            try {
                stats = new Stats(DataHandler.getData(receiverUrl));
            } catch (IOException e) {
                Log.e(MainActivity.ReceiverDataLoader.class.getName(), "AAaarrggh!!", e);
            }
            return stats;
        }

        @Override
        protected void onPostExecute(Stats stats) {
            super.onPostExecute(stats);
            handleStatsResult(stats);
        }
    }

    private class AircraftDataLoader extends AsyncTask<String, Void, AircraftData> {

        private String URI = "/dump1090/data/aircraft.json";
        private String HISTORY = "/dump1090/data/history_{0}.json";

        @Override
        protected AircraftData doInBackground(String... voids) {
            String receiverUrl = "http://" + voids[0] + URI;
            AircraftData aircraftData = null;
            try {
                /*
                First, lets get the history data
                 */
                Receiver receiver = (Receiver) Cache.getCacheItem(Receiver.RECEIVER);
                List<AircraftData> historyList = new ArrayList<AircraftData>();
                for (int i = 0; i < receiver.getHistory(); i++) {
                    String uri = "http://" + voids[0] + MessageFormat.format(HISTORY, i);
                    AircraftData data = new AircraftData(DataHandler.getData(uri));
                    historyList.add(data);
                }
                History history = new History();
                history.setHistory(historyList);
                Cache.setCacheItem(History.HISTORY, history);
                /*
                Now get the current data and add the historic data
                 */
                aircraftData = new AircraftData(DataHandler.getData(receiverUrl));
            } catch (IOException e) {
                Log.e(MainActivity.ReceiverDataLoader.class.getName(), "AAaarrggh!!", e);
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
