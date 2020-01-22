package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.ItemController;
import com.datamation.swdsfa.model.FInvRDet;

import java.util.ArrayList;

public class SalesReturnDetailsAdapter extends ArrayAdapter<FInvRDet> {
    Context context;
    ArrayList<FInvRDet> list;

    public SalesReturnDetailsAdapter(Context context, ArrayList<FInvRDet> list) {

        super(context, R.layout.row_retrun_details, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = convertView;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_retrun_details, parent, false);

        TextView lblItem = (TextView) row.findViewById(R.id.row_item);
        TextView lblQty = (TextView) row.findViewById(R.id.row_qty);
        TextView lblReason = (TextView) row.findViewById(R.id.row_reason);

        lblItem.setText(new ItemController(getContext()).getItemNameByCode(list.get(position).getFINVRDET_ITEMCODE()) + " - " + list.get(position).getFINVRDET_ITEMCODE());
        lblQty.setText(list.get(position).getFINVRDET_QTY());
        lblReason.setText(list.get(position).getFINVRDET_RETURN_REASON());


        return row;
    }


}
