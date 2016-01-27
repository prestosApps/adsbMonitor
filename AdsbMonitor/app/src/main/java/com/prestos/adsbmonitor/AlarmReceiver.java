package com.prestos.adsbmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by prestos on 27/01/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(AlarmReceiver.class.getName(), "You're Fired!!!");
        Intent historyServiceIntent = new Intent(context, HistoryService.class);
        context.startService(historyServiceIntent);
    }
}