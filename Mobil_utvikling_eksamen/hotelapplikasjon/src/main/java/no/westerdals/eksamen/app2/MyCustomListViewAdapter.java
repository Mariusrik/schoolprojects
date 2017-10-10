package no.westerdals.eksamen.app2;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import no.westerdals.eksamen.app2.Model.RoomServiceRow;

/**
 * Created by mariu on 25-May-17.
 */

public class MyCustomListViewAdapter extends ArrayAdapter<RoomServiceRow> {

    private int layoutResourceId;
    private List<RoomServiceRow> roomServiceRow;

    public MyCustomListViewAdapter(@NonNull Context context, int layoutResourceId, List<RoomServiceRow> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.roomServiceRow = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View view = layoutInflater.inflate(layoutResourceId, parent, false);


        TextView textView1 = (TextView) view.findViewById(R.id.roomServiceColumn1);
        TextView textView2 = (TextView) view.findViewById(R.id.roomServiceColumn2);
        TextView textView3 = (TextView) view.findViewById(R.id.roomServiceColumn3);
        TextView textView4 = (TextView) view.findViewById(R.id.roomServiceColumn4);

        textView1.setText(roomServiceRow.get(position).getItem());
        textView2.setText(roomServiceRow.get(position).getDescription());
        textView3.setText(roomServiceRow.get(position).getPrice());
        textView4.setText(roomServiceRow.get(position).getWhereToBuy());

        return view;
    }
}
