package com.prestos.adsbmonitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.prestos.adsbmonitor.model.Receiver;
import com.prestos.adsbmonitor.model.Stats;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String HOSTNAME = "192.168.1.79";
    private TextView txtVersionValue;
    private TextView txtRefreshValue;
    private TextView txtHistoryValue;
    private TextView txtLatValue;
    private TextView txtLonValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtVersionValue = (TextView) findViewById(R.id.txtVersionValue);
        txtRefreshValue = (TextView) findViewById(R.id.txtRefreshValue);
        txtHistoryValue = (TextView) findViewById(R.id.txtHistoryValue);
        txtLatValue = (TextView) findViewById(R.id.txtLatValue);
        txtLonValue = (TextView) findViewById(R.id.txtLonValue);
        loadData();
    }

    private void loadData() {
        new ReceiverDataLoader().execute(HOSTNAME);
        new StatsDataLoader().execute(HOSTNAME);
    }

    private void displayReceiverResult(Receiver receiver) {
        txtVersionValue.setText(receiver.getVersion());
        txtRefreshValue.setText(String.valueOf(receiver.getRefresh()));
        txtHistoryValue.setText(String.valueOf(receiver.getHistory()));
        txtLatValue.setText(String.valueOf(receiver.getLat()));
        txtLonValue.setText(String.valueOf(receiver.getLon()));
    }

    private void displayStatsResult(Stats stats) {
        Log.d(MainActivity.class.getName(), "samples processed: " + stats.getLatest().getLocal().getSamplesProcessed());
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
            displayReceiverResult(receiver);
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
            displayStatsResult(stats);
        }
    }
}
