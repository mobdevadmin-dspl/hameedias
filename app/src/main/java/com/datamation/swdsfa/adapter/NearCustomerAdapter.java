package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.Customer;
import com.datamation.swdsfa.model.NearDebtor;

import java.util.ArrayList;

public class NearCustomerAdapter extends ArrayAdapter<NearDebtor> {
    Context context;
    ArrayList<NearDebtor> list;

    public NearCustomerAdapter(Context context, ArrayList<NearDebtor> list) {

        super(context, R.layout.row_customer_listview, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, View row, final ViewGroup parent) {

        LayoutInflater inflater = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_customer_listview, parent, false);

        LinearLayout layout = (LinearLayout) row.findViewById(R.id.linearLayout);
        TextView code = (TextView) row.findViewById(R.id.debCode);
        TextView name = (TextView) row.findViewById(R.id.debName);
        TextView address = (TextView) row.findViewById(R.id.debAddress);

        code.setText(list.get(position).getFNEARDEBTOR_RETAILER());
        name.setText(list.get(position).getFNEARDEBTOR_ADDRESS());
        address.setText(list.get(position).getFNEARDEBTOR_TERRITORY());

        return row;
    }
}
