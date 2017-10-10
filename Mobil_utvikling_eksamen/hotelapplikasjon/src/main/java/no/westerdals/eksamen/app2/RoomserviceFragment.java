package no.westerdals.eksamen.app2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import no.westerdals.eksamen.app2.Model.RoomServiceContract;
import no.westerdals.eksamen.app2.Model.RoomServiceDBHelper;
import no.westerdals.eksamen.app2.Model.RoomServiceRow;


/**
 *
 */
public class RoomserviceFragment extends Fragment {
    RoomServiceDBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_roomservice, container, false);


        dbHelper = new RoomServiceDBHelper(getActivity());

        Log.v("TABLE", RoomServiceContract.OutletTable.SQL_CREATE_TABLE);
        Log.v("TABLE", RoomServiceContract.ItemTable.SQL_CREATE_TABLE);

        Log.v("SELECT", RoomServiceContract.ItemTable.SQL_SELECT_JOIN_OUTLET_TABLE);

        new dbAsyncTask().execute();
        return rootView;
    }

    private class dbAsyncTask extends AsyncTask<Void, Void, List<RoomServiceRow>> {
        @Override
        protected List<RoomServiceRow> doInBackground(Void... params) {
            Log.v("PRINT", "1");
            RoomServiceDBHelper dbHelper = new RoomServiceDBHelper(getActivity());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(RoomServiceContract.ItemTable.SQL_SELECT_JOIN_OUTLET_TABLE, null);


            List<RoomServiceRow> resultList = new ArrayList<>();


            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                RoomServiceRow roomServiceRow = new RoomServiceRow(
                        cursor.getString(cursor.getColumnIndex(RoomServiceContract.ItemTable.COLUMN_NAME_ITEM_NAME)),
                        cursor.getString(cursor.getColumnIndex(RoomServiceContract.ItemTable.COLUMN_NAME_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(RoomServiceContract.ItemTable.COLUMN_NAME_PRICE)),
                        cursor.getString(cursor.getColumnIndex(RoomServiceContract.OutletTable.COLUMN_NAME_OUTLET_NAME))
                );
                resultList.add(roomServiceRow);
            }

            return resultList;
        }


        //TODO: implement list view instead.
        @Override
        protected void onPostExecute(List<RoomServiceRow> resultList) {


            ArrayAdapter myCustomListViewAdapter = new MyCustomListViewAdapter(getContext(), R.layout.roomservice_row, resultList);

            ListView theListView = (ListView) getView().findViewById(R.id.room_service_listview);

            theListView.setAdapter(myCustomListViewAdapter);

        }

    }
}
