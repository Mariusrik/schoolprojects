package no.westerdals.eksamen.app2.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

/**
 * Created by mariu on 24-May-17.
 */

public class RoomServiceDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = RoomServiceContract.DATABASE_VERSION;
    private static final String DATABASE_NAME = RoomServiceContract.DATABASE_NAME;

    private static final String SQL_CREATE_OUTLET_TABLE = RoomServiceContract.OutletTable.SQL_CREATE_TABLE;
    private static final String SQL_CREATE_ITEM_TABLE = RoomServiceContract.ItemTable.SQL_CREATE_TABLE;

    public RoomServiceDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_OUTLET_TABLE);
        db.execSQL(SQL_CREATE_ITEM_TABLE);

        db.execSQL("INSERT INTO " + RoomServiceContract.OutletTable.TABLE_NAME + " VALUES (1, 'Bar')");
        db.execSQL("INSERT INTO " + RoomServiceContract.OutletTable.TABLE_NAME + " VALUES (2, 'Reception')");
        db.execSQL("INSERT INTO " + RoomServiceContract.OutletTable.TABLE_NAME + " VALUES (3, 'Resturant')");

        db.execSQL("INSERT INTO " + RoomServiceContract.ItemTable.TABLE_NAME + " VALUES (null, 'Beer', '0.5L Carlsberg', 60, 2)");
        db.execSQL("INSERT INTO " + RoomServiceContract.ItemTable.TABLE_NAME + " VALUES (null, 'Umbrella', '90m diameter', 40 , 1)");

        db.execSQL("INSERT INTO " + RoomServiceContract.ItemTable.TABLE_NAME + " VALUES (null, 'Ice', 'Chocolate', 30, 1)");
        db.execSQL("INSERT INTO " + RoomServiceContract.ItemTable.TABLE_NAME + " VALUES (null, 'Hamburger', '200g', 230 , 3)");

        db.execSQL("INSERT INTO " + RoomServiceContract.ItemTable.TABLE_NAME + " VALUES (null, 'Taxi', 'fixed price taxi','200', 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RoomServiceContract.OutletTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RoomServiceContract.ItemTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RoomServiceContract.OutletTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RoomServiceContract.ItemTable.TABLE_NAME);
        onCreate(db);
    }
}
