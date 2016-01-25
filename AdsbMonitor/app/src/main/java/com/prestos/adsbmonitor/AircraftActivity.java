package com.prestos.adsbmonitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class AircraftActivity extends AppCompatActivity implements IpAddressDialogFragment.IpAddressDialogListener, ApplicationErrorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft);

        if (savedInstanceState != null) {
            return;
        }

        if (findViewById(R.id.fragment_container) != null) {

            SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
            if (prefs.contains("prefs_ip_address")) {
                Log.d(AircraftActivity.class.getName(), "found IP address preferences: " + prefs.getString("prefs_ip_address", null));
                AircraftActivityFragment aircraftActivityFragment = new AircraftActivityFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, aircraftActivityFragment).commit();
            } else {
                Log.d(AircraftActivity.class.getName(), "Can not find prefs_ip_address, so need to get it");
//                IpCheckFragment ipCheckFragment = new IpCheckFragment();
//                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ipCheckFragment).commit();
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
        Log.d(AircraftActivity.class.getName(), "Store IP address: " + ipAddress);

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("prefs_ip_address", ipAddress).commit();
        AircraftActivityFragment aircraftActivityFragment = new AircraftActivityFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, aircraftActivityFragment).commit();
    }

    @Override
    public void onApplicationError(ApplicationException ex) {
        Log.e(AircraftActivity.class.getName(), "Handling error: " + ex.getError());
    }
}
