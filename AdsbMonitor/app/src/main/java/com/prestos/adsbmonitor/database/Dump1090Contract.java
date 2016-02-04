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
        public static final String COLUMN_NAME_DATE_SEEN = "dateSeen";
        public static final String COLUMN_NAME_SQUAWK = "squawk";
        public static final String COLUMN_NAME_FLIGHT = "flight";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LON = "lon";
        public static final String COLUMN_NAME_ALTITUDE = "altitude";
        public static final String COLUMN_NAME_VERT_RATE = "vertRate";
        public static final String COLUMN_NAME_TRACK = "track";
        public static final String COLUMN_NAME_SPEED = "speed";
        public static final String COLUMN_NAME_MESSAGES = "messages";
        public static final String COLUMN_NAME_MLAT = "mlat";
        public static final String COLUMN_NAME_TIME_SEEN = "timeSeen";
        public static final String COLUMN_NAME_RSSI = "rssi";
    }
}
