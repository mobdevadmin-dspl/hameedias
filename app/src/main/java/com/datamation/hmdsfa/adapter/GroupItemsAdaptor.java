package com.datamation.hmdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.ItemPriceController;
import com.datamation.hmdsfa.model.InvDet;
import com.datamation.hmdsfa.model.StockInfo;

import java.util.ArrayList;

public class GroupItemsAdaptor extends ArrayAdapter<InvDet> {
    Context context;
    ArrayList<InvDet> list;
    String itemPrice = "";

    public GroupItemsAdaptor(Context context, ArrayList<InvDet> list) {

        super(context, R.layout.row_group_items, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_group_items, parent, false);

        TextView itemcode = (TextView) row.findViewById(R.id.row_itemcode);
        TextView itemname = (TextView) row.findViewById(R.id.row_itemname);
        TextView qty = (TextView) row.findViewById(R.id.row_qty);

        itemcode.setText(list.get(position).getFINVDET_ITEM_CODE());
        itemname.setText(list.get(position).getFINVDET_ITEM_NAME());
        qty.setText(list.get(position).getFINVDET_QTY());

        return row;
    }
}
