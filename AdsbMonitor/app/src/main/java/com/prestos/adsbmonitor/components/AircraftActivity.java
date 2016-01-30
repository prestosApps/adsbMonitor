package com.prestos.adsbmonitor.components;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.prestos.adsbmonitor.AlarmReceiver;
import com.prestos.adsbmonitor.ApplicationErrorListener;
import com.prestos.adsbmonitor.ApplicationException;
import com.prestos.adsbmonitor.ApplicationURL;
import com.prestos.adsbmonitor.DataHandler;
import com.prestos.adsbmonitor.R;
import com.prestos.adsbmonitor.model.Receiver;

import java.util.Calendar;

public class AircraftActivity extends AppCompatActivity implements IpAddressDialogFragment.IpAddressDialogListener, ApplicationErrorListener {

    public static final String PREFS = "applicationPrefs";
    public static final String PREFS_IP_ADDRESS = "prefs_ip_address";

    private ApplicationException applicationException = null;
    private SharedPreferences prefs;

    private AlarmManager alarm;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft);
        prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        if (savedInstanceState != null) {
            return;
        }

        //Set up hourly alarm
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, alarmIntent);

        if (findViewById(R.id.fragment_container) != null) {
            if (prefs.contains(PREFS_IP_ADDRESS)) {
                new ReceiverDataLoader().execute(prefs.getString(PREFS_IP_ADDRESS, null));
            } else {
                createIpAddressDialog();
            }
        } else {
            Log.d(AircraftActivity.class.getName(), "Can not find the necessary view");
        }
    }

    private void createIpAddressDialog() {
        IpAddressDialogFragment ipDialog = new IpAddressDialogFragment();
        ipDialog.show(getSupportFragmentManager(), "ipaddress");
    }

    @Override
    public void onDialogClick(String ipAddress) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFS_IP_ADDRESS, ipAddress).commit();
        new ReceiverDataLoader().execute(prefs.getString(PREFS_IP_ADDRESS, null));
    }

    @Override
    public void onApplicationError(ApplicationException ex) {
        Log.d(AircraftActivity.class.getName(), "I take exception: " + ex);
        Log.e(AircraftActivity.class.getName(), "Oops!: " + ex.getError());
    }

    private void handleReceiverResponse(Receiver receiver) {
        if (applicationException == null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Receiver.VERSION, receiver.getVersion());
            editor.putString(Receiver.LATITUDE, String.valueOf(receiver.getLat()));
            editor.putString(Receiver.LONGITUDE, String.valueOf(receiver.getLon()));
            editor.commit();
            /*
            The following needs to be here so that the lat and lon are picked up for the receiver
             */
            AircraftActivityFragment aircraftActivityFragment = new AircraftActivityFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, aircraftActivityFragment).commit();
        } else {
            onApplicationError(applicationException);
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
}
