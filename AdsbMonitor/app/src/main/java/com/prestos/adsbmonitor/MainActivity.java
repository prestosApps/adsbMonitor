package com.prestos.adsbmonitor;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
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
    private TextView txtLatValue;
    private TextView txtLonValue;
    /*
    Stats
     */
    private TextView txtStarted;
    //    private TextView txtStatTotalTitle;
//    private TextView txtStatLocalSamplesProcessed;
//    private TextView txtStatLocalSamplesDropped;
//    private TextView txtStatLocalModeac;
//    private TextView txtStatLocalModes;
//    private TextView txtStatLocalModesBad;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtVersionValue = (TextView) findViewById(R.id.txtVersionValue);
        txtLatValue = (TextView) findViewById(R.id.txtLatValue);
        txtLonValue = (TextView) findViewById(R.id.txtLonValue);
        txtStarted = (TextView) findViewById(R.id.txtStarted);
//        txtStatTotalTitle = (TextView) findViewById(R.id.txtStatTitle);
//        txtStatLocalSamplesProcessed = (TextView) findViewById(R.id.txtStatLocalSamplesProcessedValue);
//        txtStatLocalSamplesDropped = (TextView) findViewById(R.id.txtStatLocalSamplesDroppedValue);
//        txtStatLocalModeac = (TextView) findViewById(R.id.txtStatLocalModeacValue);
//        txtStatLocalModes = (TextView) findViewById(R.id.txtStatLocalModesValue);
//        txtStatLocalModesBad = (TextView) findViewById(R.id.txtStatLocalModesBadValue);
        pieChart = (PieChart) findViewById(R.id.pieChart);

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
//        txtStatTotalTitle.setText("Total");
//        NumberFormat nf = NumberFormat.getNumberInstance();
//        txtStatLocalSamplesProcessed.setText(nf.format(stats.getTotal().getLocal().getSamplesProcessed()));
//        txtStatLocalSamplesDropped.setText(nf.format(stats.getTotal().getLocal().getSamplesDropped()));
//        txtStatLocalModeac.setText(nf.format(stats.getTotal().getLocal().getModeac()));
//        txtStatLocalModes.setText(nf.format(stats.getTotal().getLocal().getModes()));
//        txtStatLocalModesBad.setText(nf.format(stats.getTotal().getLocal().getBad()));
        List<Entry> pieDataList = new ArrayList<Entry>();
        Entry entry1 = new Entry(stats.getLast1Min().getLocal().getBad(), 1);
        Entry entry2 = new Entry(stats.getLast1Min().getLocal().getUnknownIcao(), 2);
        Entry entry3 = new Entry(stats.getLast1Min().getLocal().getAccepted().get(0), 3);
        Entry entry4 = new Entry(stats.getLast1Min().getLocal().getAccepted().get(1), 4);
        pieDataList.add(entry1);
        pieDataList.add(entry2);
        pieDataList.add(entry3);
        pieDataList.add(entry4);
        PieDataSet pieDataSet = new PieDataSet(pieDataList, "Mode S");
        pieDataSet.setSliceSpace(2.0f);
        pieDataSet.setColors(new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW});

        List<String> xvals = new ArrayList<String>();
        xvals.add("Bad");
        xvals.add("Unknown ICAO");
        xvals.add("1 Bit");
        xvals.add("2 Bit");

        PieData pieData = new PieData(xvals, pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
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
