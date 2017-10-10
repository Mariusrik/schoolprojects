package com.example.mariu.appliksjon1.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mariu.appliksjon1.Model.GameStatisticsContract;
import com.example.mariu.appliksjon1.Model.GameStatisticsDbHelper;
import com.example.mariu.appliksjon1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariu on 17-May-17.
 */

public class StatisticsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_statistics, null);


        new dbAsyncTask().execute();
        return root;
    }


    /*
    * Jeg tok med at man ser antall ganger en person har tapt også selv om oppgaven bare ba om antall
    * vunnet. Fordi jeg syntes det var interresant å se.
    * */
    private String selectSQL = "SELECT name, woncount, lostcount FROM " +
            "(SELECT " + GameStatisticsContract.StatisticsTable.COLUMN_NAME_WINNER + " as name FROM " + GameStatisticsContract.StatisticsTable.TABLE_NAME +
            " union " +
            "SELECT " + GameStatisticsContract.StatisticsTable.COLUMN_NAME_LOSER + " as name FROM "+ GameStatisticsContract.StatisticsTable.TABLE_NAME +") t1 " +
            "left join (SELECT " + GameStatisticsContract.StatisticsTable.COLUMN_NAME_LOSER + ", count(" + GameStatisticsContract.StatisticsTable.COLUMN_NAME_LOSER + ") as lostcount " +
            "FROM "+ GameStatisticsContract.StatisticsTable.TABLE_NAME +
            " GROUP BY " + GameStatisticsContract.StatisticsTable.COLUMN_NAME_LOSER + ") t2 " +
            "on t1.name = t2." + GameStatisticsContract.StatisticsTable.COLUMN_NAME_LOSER +
            " left join (SELECT " + GameStatisticsContract.StatisticsTable.COLUMN_NAME_WINNER + ", count(" + GameStatisticsContract.StatisticsTable.COLUMN_NAME_WINNER + ") as woncount " +
            "FROM " + GameStatisticsContract.StatisticsTable.TABLE_NAME +
            " GROUP BY " + GameStatisticsContract.StatisticsTable.COLUMN_NAME_WINNER + ") t3 " +
            "on t1.name = t3." + GameStatisticsContract.StatisticsTable.COLUMN_NAME_WINNER +
            " ORDER BY woncount DESC";

    private class dbAsyncTask extends AsyncTask<Void, Void, List<Object>> {
        @Override
        protected List<Object> doInBackground(Void... params) {
            GameStatisticsDbHelper dbHelper = new GameStatisticsDbHelper(getActivity());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectSQL, null);

            List<Object> resultList = new ArrayList<>();

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                resultList.add(cursor.getString(cursor.getColumnIndex("name")));
                resultList.add(cursor.getString(cursor.getColumnIndex("woncount")));
                resultList.add(cursor.getString(cursor.getColumnIndex("lostcount")));
            }

            return resultList;
        }

        @Override
        protected void onPostExecute(List<Object> resultList) {

            if(resultList.size() < 1){
                Toast.makeText(getActivity(), "No statistics to show!", Toast.LENGTH_SHORT).show();
                return;
            }


            TableLayout table = (TableLayout) getView().findViewById(R.id.statisticsTable);

            for (int i = 0; i < resultList.size(); i++) {


                View tableRow = (View) getActivity().getLayoutInflater().inflate(R.layout.statistics_row, null);

                table.addView(tableRow, new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


                TextView textView1 = (TextView) getView().findViewById(R.id.statisticsColumn1);
                if (resultList.get(i) != null)
                    textView1.setText(resultList.get(i).toString());
                else
                    textView1.setText("");

                textView1.setId(i);
                i++;

                TextView textView2 = (TextView) getView().findViewById(R.id.statisticsColumn2);
                if (resultList.get(i) != null)
                    textView2.setText(resultList.get(i).toString());
                else
                    textView2.setText("0");
                textView2.setId(i);
                i++;

                TextView textView3 = (TextView) getView().findViewById(R.id.statisticsColumn3);
                if (resultList.get(i) != null)
                    textView3.setText(resultList.get(i).toString());
                else
                    textView3.setText("0");
                textView3.setId(i);
            }
        }
    }

}