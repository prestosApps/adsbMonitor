package com.prestos.adsbmonitor;

import android.os.*;
import android.os.Process;

/**
 * Created by prestos on 10/11/2015.
 */
public class AircraftDataLoader implements Runnable {


    @Override
    public void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        System.out.println("Bong!!!");
    }
}
