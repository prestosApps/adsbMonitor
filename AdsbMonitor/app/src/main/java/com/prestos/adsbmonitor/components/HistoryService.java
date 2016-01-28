package com.prestos.adsbmonitor.components;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.prestos.adsbmonitor.ApplicationException;
import com.prestos.adsbmonitor.ApplicationURL;
import com.prestos.adsbmonitor.DataHandler;
import com.prestos.adsbmonitor.database.Dump1090Contract;
import com.prestos.adsbmonitor.database.Dump1090DbHelper;
import com.prestos.adsbmonitor.model.Aircraft;
import com.prestos.adsbmonitor.model.AircraftData;
import com.prestos.adsbmonitor.model.History;
import com.prestos.adsbmonitor.model.Receiver;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prestos on 27/01/2016.
 */
public class HistoryService extends Service {

    private ApplicationException applicationException;
    private int historyCount;
    private String ipAddress;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences prefs = getSharedPreferences(AircraftActivity.PREFS, Context.MODE_PRIVATE);
        ipAddress = prefs.getString(AircraftActivity.PREFS_IP_ADDRESS, null);
        new ReceiverDataLoader().execute(ipAddress);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(HistoryService.class.getName(), "All done for now");
    }

    private void handleReceiverResponse(Receiver receiver) {
        if (applicationException == null) {
            historyCount = receiver.getHistory();
            new HistoryDataLoader().execute(ipAddress);
        } else {
            stopSelf();
        }
    }

    private void handleAircraftDataResult(History history) {
        if (applicationException == null) {
            Log.d(HistoryService.class.getName(), "Aircraft: " + history.getAircraftHistoryCount());
            new WriteToDatabase().execute(history);
        }
    }

    private void handleWriteToDatabaseResult() {
        stopSelf();
    }

    private class WriteToDatabase extends AsyncTask<History, Void, Void> {
        @Override
        protected Void doInBackground(History... voids) {
            Log.d(HistoryService.class.getName(), "Updating database");
            History history = voids[0];
            Dump1090DbHelper dbHelper = new Dump1090DbHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues contentValues;
            for (AircraftData aircraftData : history.getHistory()) {
                for (Aircraft aircraft : aircraftData.getAircraftList()) {
                    contentValues = new ContentValues();
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_HEXCODE, aircraft.getHex());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_SQUAWK, aircraft.getSquawk());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_FLIGHT, aircraft.getFlight());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_LAT, aircraft.getLat());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_LON, aircraft.getLon());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_NUCP, aircraft.getNucp());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_SEEN_POS, aircraft.getSeenPos());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_ALTITUDE, aircraft.getAltitude());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_VERT_RATE, aircraft.getVertRate());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_TRACK, aircraft.getTrack());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_SPEED, aircraft.getSpeed());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_CATEGORY, aircraft.getCategory());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_MESSAGES, aircraft.getMessages());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_SEEN, aircraft.getSeen());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_RSSI, aircraft.getRssi());
                    contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_MLAT, aircraft.isMlat());

                    db.insert(Dump1090Contract.Aircraft.TABLE_NAME, null, contentValues);
                }
            }
            Log.d(HistoryService.class.getName(), "Database updated");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            handleWriteToDatabaseResult();
        }
    }

    private class ReceiverDataLoader extends AsyncTask<String, Void, Receiver> {

        private String URI = "/dump1090/data/receiver.json";

        @Override
        protected Receiver doInBackground(String... voids) {
            Log.d(HistoryService.class.getName(), "Getting Receiver Data");
            String receiverUrl = "http://" + voids[0] + URI;
            Receiver receiver = null;
            try {
                ApplicationURL applicationURL = new ApplicationURL(receiverUrl);
                receiver = new Receiver(DataHandler.getData(applicationURL));
            } catch (ApplicationException e) {
                applicationException = e;
            }
            return receiver;
        }

        @Override
        protected void onPostExecute(Receiver receiver) {
            super.onPostExecute(receiver);
            handleReceiverResponse(receiver);
        }
    }

    private class HistoryDataLoader extends AsyncTask<String, Void, History> {

        private String HISTORY = "/dump1090/data/history_{0}.json";

        @Override
        protected History doInBackground(String... voids) {
            String uri;
            AircraftData aircraftData = null;
            List<AircraftData> historyList = new ArrayList<AircraftData>();
            ApplicationURL applicationURL;
            for (int i = 0; i < historyCount; i++) {
                uri = "http://" + voids[0] + MessageFormat.format(HISTORY, i);
                try {
                    applicationURL = new ApplicationURL(uri);
                    aircraftData = new AircraftData(DataHandler.getData(applicationURL));
                } catch (ApplicationException e) {
                    applicationException = e;
                }
                historyList.add(aircraftData);
            }
            History history = new History();
            history.setHistory(historyList);
            return history;
        }

        @Override
        protected void onPostExecute(History history) {
            super.onPostExecute(history);
            handleAircraftDataResult(history);
        }
    }
}
