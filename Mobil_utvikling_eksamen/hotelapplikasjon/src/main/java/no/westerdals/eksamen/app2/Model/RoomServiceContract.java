package no.westerdals.eksamen.app2.Model;

import android.provider.BaseColumns;

/**
 * Created by mariu on 24-May-17.
 */

public class RoomServiceContract {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RoomService.db";

    private RoomServiceContract() {
    }

    /**
     * OutletTable class
     */
    public static class OutletTable implements BaseColumns {
        public static final String TABLE_NAME = "outlet";
        public static final String COLUMN_NAME_OUTLET_ID= "id";
        public static final String COLUMN_NAME_OUTLET_NAME = "outlet_name";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + OutletTable.TABLE_NAME + " (" +
                        COLUMN_NAME_OUTLET_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_OUTLET_NAME + " TEXT);"
                ;
    }

    /**
     * Item Table classs
     */
    public static class ItemTable implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_ITEM_NAME = "name";
        public static final String COLUMN_NAME_ITEM_ID = "id";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_SERVICE_OUTLET_ID = "service_outlet_id";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_NAME_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME_ITEM_NAME + " TEXT," +
                        COLUMN_NAME_DESCRIPTION + " TEXT," +
                        COLUMN_NAME_PRICE + " TEXT," +
                        COLUMN_NAME_SERVICE_OUTLET_ID + " INTEGER," +
                        "FOREIGN KEY(" + COLUMN_NAME_SERVICE_OUTLET_ID + ") REFERENCES " +
                        OutletTable.TABLE_NAME + "(" + OutletTable.COLUMN_NAME_OUTLET_ID + "));"
                ;

        public static final String SQL_SELECT_JOIN_OUTLET_TABLE =
                "SELECT "+ COLUMN_NAME_ITEM_NAME +", " + COLUMN_NAME_DESCRIPTION + ", " + COLUMN_NAME_PRICE + ", " + OutletTable.TABLE_NAME + "." + OutletTable.COLUMN_NAME_OUTLET_NAME + " FROM "+ TABLE_NAME +
                        " INNER JOIN "+ OutletTable.TABLE_NAME +
                        " on "+ TABLE_NAME + "." + COLUMN_NAME_SERVICE_OUTLET_ID + " = " + OutletTable.TABLE_NAME + "." + OutletTable.COLUMN_NAME_OUTLET_ID + ";"
                ;
    }
}
