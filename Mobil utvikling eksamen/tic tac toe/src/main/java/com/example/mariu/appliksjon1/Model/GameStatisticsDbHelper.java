package com.example.mariu.appliksjon1.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mariu on 12-May-17.
 */

public class GameStatisticsDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = GameStatisticsContract.DATABASE_VERSION;
    private static final String DATABASE_NAME = GameStatisticsContract.DATABASE_NAME;

    private static final String SQL_CREATE_TABLE = GameStatisticsContract.StatisticsTable.SQL_CREATE_TABLE;

    public GameStatisticsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GameStatisticsContract.StatisticsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GameStatisticsContract.StatisticsTable.TABLE_NAME);
        onCreate(db);
    }
}
