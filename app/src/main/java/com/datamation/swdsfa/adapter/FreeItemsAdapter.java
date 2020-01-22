package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.ItemController;
import com.datamation.swdsfa.model.InvDet;

import java.util.ArrayList;

public class FreeItemsAdapter extends ArrayAdapter<InvDet> {
    Context context;
    ArrayList<InvDet> list;

    public FreeItemsAdapter(Context context, ArrayList<InvDet> list) {

        super(context, R.layout.row_order_details, list);
        this.context = context;
        this.list = list;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_free_issue_details, parent, false);

        TextView itemName = (TextView) row.findViewById(R.id.row_item);
        TextView itemCode = (TextView) row.findViewById(R.id.row_piece);
        TextView Qty = (TextView) row.findViewById(R.id.row_cases);

        itemCode.setText(new ItemController(context).getItemNameByCode(list.get(position).getFINVDET_ITEM_CODE()));
        itemName.setText(list.get(position).getFINVDET_ITEM_CODE());
        Qty.setText(list.get(position).getFINVDET_QTY());

        return row;
    }
}
