package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.NewCustomer;

import java.util.ArrayList;


/**
 * Created by Rashmi on 4/5/2018.
 */
public class Customer_Adapter extends ArrayAdapter<NewCustomer> {

    Context context;
    ArrayList<NewCustomer> list;

    public Customer_Adapter(Context context, ArrayList<NewCustomer> list){

        super(context, R.layout.row_item_listview, list);
        this.context = context;
        this.list = list;

    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_item_listview_responsive_layout, parent, false);
       //row = inflater.inflate(R.layout.non_test_layout, parent, false);

        TextView itemCode = (TextView) row.findViewById(R.id.tv_item_code);
        TextView ItemName = (TextView) row.findViewById(R.id.tv_item_name);
        //TextView Description=(TextView) row.findViewById(R.id.TextView01);

        itemCode.setText(list.get(position).getCUSTOMER_ID());
        ItemName.setText(list.get(position).getNAME());

        itemCode.setText(list.get(position).getCUSTOMER_ID());


        return row;
    }
}
