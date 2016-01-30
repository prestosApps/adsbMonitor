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
import com.prestos.adsbmonitor.model.AircraftSummary;
import com.prestos.adsbmonitor.model.History;
import com.prestos.adsbmonitor.model.Receiver;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            History history = voids[0];
            Map<String, List<Aircraft>> reducedMap = history.reduce();

            Dump1090DbHelper dbHelper = new Dump1090DbHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues contentValues;
            for (Map.Entry<String, List<Aircraft>> entry : reducedMap.entrySet()) {
                AircraftSummary aircraftSummary = new AircraftSummary(entry.getValue());
                contentValues = new ContentValues();
                contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_HEXCODE, aircraftSummary.getHexcode());
                contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_SQUAWK, aircraftSummary.getSquawk());
                contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_FLIGHT, aircraftSummary.getFlight());
                contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_MESSAGES, aircraftSummary.getMessages());
                contentValues.put(Dump1090Contract.Aircraft.COLUMN_NAME_MLAT, aircraftSummary.isMlat());

                db.insert(Dump1090Contract.Aircraft.TABLE_NAME, null, contentValues);
            }
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
