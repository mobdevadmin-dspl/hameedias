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

public class ReturnDetailsAdapter extends ArrayAdapter<FInvRDet> {
    Context context;
    ArrayList<FInvRDet> list;

    public ReturnDetailsAdapter(Context context, ArrayList<FInvRDet> list) {

        super(context, R.layout.row_order_details, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_order_details, parent, false);

        TextView lblItem = (TextView) row.findViewById(R.id.row_item);
        TextView lblQty = (TextView) row.findViewById(R.id.row_cases);
        TextView lblAMt = (TextView) row.findViewById(R.id.row_piece);


//		if(list.get(position).getFTRANSODET_TYPE().equals("FI")){
//			
//			lblItem.setTypeface(null,Typeface.BOLD);
//			lblQty.setTypeface(null,Typeface.BOLD);
//			lblAMt.setTypeface(null,Typeface.BOLD);
//		}
//		else
//		{
//			lblItem.setTypeface(null,Typeface.NORMAL);
//			lblQty.setTypeface(null,Typeface.NORMAL);
//			lblAMt.setTypeface(null,Typeface.NORMAL);
//		}

        lblItem.setText(new ItemController(getContext()).getItemNameByCode(list.get(position).getFINVRDET_ITEMCODE()) + " - " + list.get(position).getFINVRDET_ITEMCODE());
        lblQty.setText(list.get(position).getFINVRDET_QTY());
        lblAMt.setText(list.get(position).getFINVRDET_RETURN_REASON());


        return row;
    }


}
