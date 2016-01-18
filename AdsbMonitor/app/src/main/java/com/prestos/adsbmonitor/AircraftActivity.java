package com.prestos.adsbmonitor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class AircraftActivity extends AppCompatActivity implements IpAddressDialogFragment.IpAddressDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.contains("prefs_ip_address")) {
            Log.d(AircraftActivity.class.getName(), "found IP address preferences: " + prefs.getString("prefs_ip_address", null));
            setContentView(R.layout.activity_aircraft);
        } else {
            Log.d(AircraftActivity.class.getName(), "Can not find prefs_ip_address, so need to get it");
            createIpAddressDialog();
        }
    }

    private void createIpAddressDialog() {
        IpAddressDialogFragment ipDialog = new IpAddressDialogFragment();
        ipDialog.show(getSupportFragmentManager(), "ipaddress");
    }

    @Override
    public void onDialogClick(String ipAddress) {
        Log.d(AircraftActivity.class.getName(), "Deal with " + ipAddress);
    }
}
