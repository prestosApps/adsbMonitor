package com.prestos.adsbmonitor.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prestos on 28/01/2016.
 */
public class Dump1090DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dump1090.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "create table " + Dump1090Contract.Aircraft.TABLE_NAME + " (" +
            Dump1090Contract.Aircraft._ID + " integer primary key," +
            Dump1090Contract.Aircraft.COLUMN_NAME_HEXCODE + TEXT_TYPE + COMMA_SEP +
            Dump1090Contract.Aircraft.COLUMN_NAME_SQUAWK + TEXT_TYPE + COMMA_SEP +
            Dump1090Contract.Aircraft.COLUMN_NAME_FLIGHT + TEXT_TYPE + COMMA_SEP +
            Dump1090Contract.Aircraft.COLUMN_NAME_MESSAGES + INT_TYPE + COMMA_SEP +
            Dump1090Contract.Aircraft.COLUMN_NAME_MLAT + TEXT_TYPE + COMMA_SEP +
            Dump1090Contract.Aircraft.COLUMN_NAME_FIRST_SEEN_IN_PERIOD + INT_TYPE + COMMA_SEP +
            Dump1090Contract.Aircraft.COLUMN_NAME_LAST_SEEN_IN_PERIOD + INT_TYPE + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Dump1090Contract.Aircraft.TABLE_NAME;

    public Dump1090DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Do nothing for the time being
    }
}
