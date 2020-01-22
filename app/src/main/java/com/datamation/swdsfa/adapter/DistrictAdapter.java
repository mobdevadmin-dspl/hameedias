package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.District;
import com.datamation.swdsfa.model.Town;

import java.util.ArrayList;

public class DistrictAdapter extends ArrayAdapter<District>{

    Context context;
    ArrayList<District> list;

    public DistrictAdapter(Context context, ArrayList<District> list) {
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

        TextView disCodeTxt = (TextView) row.findViewById(R.id.tv_item_code);
        TextView disName = (TextView) row.findViewById(R.id.TextView01);

        disCodeTxt.setText(list.get(position).getDistrictCode());
        disName.setText(list.get(position).getDistrictName());

        return row;
    }
}
