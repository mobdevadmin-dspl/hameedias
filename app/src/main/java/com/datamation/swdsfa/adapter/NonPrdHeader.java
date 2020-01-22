package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.CustomerController;
import com.datamation.swdsfa.controller.DayNPrdDetController;
import com.datamation.swdsfa.model.DayNPrdHed;

import java.util.ArrayList;

public class NonPrdHeader extends ArrayAdapter<DayNPrdHed> {
    Context context;
    ArrayList<DayNPrdHed> list;

    public NonPrdHeader(Context context, ArrayList<DayNPrdHed> list) {
        super(context, R.layout.row_non_productive_header, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_non_productive_header, parent, false);

        TextView Itemname = (TextView) row.findViewById(R.id.row_nonprdhed_refno);
        TextView Itemcode = (TextView) row.findViewById(R.id.row_nonprdhed_date);

            if(list.get(position).getNONPRDHED_IS_SYNCED().equals("1")) {
                Itemname.setText(list.get(position).getNONPRDHED_REFNO());
                Itemname.setTextColor(Color.parseColor("#357a38"));
                Itemcode.setText(list.get(position).getNONPRDHED_ADDDATE());
                Itemcode.setTextColor(Color.parseColor("#357a38"));
            }else{
                Itemname.setText(list.get(position).getNONPRDHED_REFNO());
                Itemname.setTextColor(Color.parseColor("#ab003c"));
                Itemcode.setText(list.get(position).getNONPRDHED_ADDDATE());
                Itemcode.setTextColor(Color.parseColor("#ab003c"));
            }
        return row;
    }
}
