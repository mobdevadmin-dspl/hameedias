package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.model.Route;
import com.datamation.swdsfa.R;

import java.util.ArrayList;

/**
 * Created by Rashmi.
 */

public class RouteAdapter extends ArrayAdapter<Route> {

    Context context;
    ArrayList<Route> list;

    public RouteAdapter(Context context, ArrayList<Route> list) {
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

        TextView routeCodeTxt = (TextView) row.findViewById(R.id.tv_item_code);
        TextView routeName = (TextView) row.findViewById(R.id.TextView01);

        routeCodeTxt.setText(list.get(position).getFROUTE_ROUTECODE());
        routeName.setText(list.get(position).getFROUTE_ROUTE_NAME());

        return row;
    }

}
