package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.Route;
import com.datamation.swdsfa.model.Town;

import java.util.ArrayList;

public class TownAdapter extends ArrayAdapter<Town> {

    Context context;
    ArrayList<Town> list;

    public TownAdapter(Context context, ArrayList<Town> list) {
        super(context, R.layout.item_listview, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.item_listview, parent, false);

        TextView townCodeTxt = (TextView) row.findViewById(R.id.tv_item_code);
        TextView townName = (TextView) row.findViewById(R.id.TextView01);

        townCodeTxt.setText(list.get(position).getTownCode());
        townName.setText(list.get(position).getTownName());

        return row;
    }
}
