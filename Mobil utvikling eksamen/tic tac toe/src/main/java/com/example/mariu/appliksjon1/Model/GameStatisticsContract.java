package com.example.mariu.appliksjon1.Model;

import android.provider.BaseColumns;

/**
 * Created by mariu on 12-May-17.
 */

public final class GameStatisticsContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GameStatistics.db";

    private GameStatisticsContract() {
    }

    public static class StatisticsTable implements BaseColumns {
        public static final String TABLE_NAME = "game";
        public static final String COLUMN_NAME_WINNER = "winner";
        public static final String COLUMN_NAME_LOSER = "loser";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + GameStatisticsContract.StatisticsTable.TABLE_NAME + " (" +
                        GameStatisticsContract.StatisticsTable.COLUMN_NAME_WINNER + " TEXT," +
                        GameStatisticsContract.StatisticsTable.COLUMN_NAME_LOSER + " TEXT);";
    }
}
