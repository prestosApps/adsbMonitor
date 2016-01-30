package com.prestos.adsbmonitor.database;

import android.provider.BaseColumns;

/**
 * Created by prestos on 28/01/2016.
 */
public class Dump1090Contract {

    public Dump1090Contract() {
    }

    public static abstract class Aircraft implements BaseColumns {
        public static final String TABLE_NAME = "aircraft";
        public static final String COLUMN_NAME_HEXCODE = "hexcode";
        public static final String COLUMN_NAME_SQUAWK = "squawk";
        public static final String COLUMN_NAME_FLIGHT = "flight";
        public static final String COLUMN_NAME_MESSAGES = "messages";
        public static final String COLUMN_NAME_MLAT = "mlat";
        public static final String COLUMN_NAME_FIRST_SEEN_IN_PERIOD = "firstSeenInPeriod";
        public static final String COLUMN_NAME_LAST_SEEN_IN_PERIOD = "lastSeenInPeriod";
    }
}
