package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.FreeHedController;
import com.datamation.swdsfa.controller.ItemController;
import com.datamation.swdsfa.model.FreeHed;
import com.datamation.swdsfa.model.InvDet;

import java.util.ArrayList;

public class InvDetAdapter extends ArrayAdapter<InvDet> {
    Context context;
    ArrayList<InvDet> list;
    ArrayList<FreeHed> arrayList;

    public InvDetAdapter(Context context, ArrayList<InvDet> list) {

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

        TextView item = (TextView) row.findViewById(R.id.row_item);
        TextView Qty = (TextView) row.findViewById(R.id.row_cases);
        TextView Amt = (TextView) row.findViewById(R.id.row_piece);
        TextView showStatus=(TextView)row.findViewById(R.id.row_free_status);

        ItemController ds = new ItemController(getContext());
        item.setText(ds.getItemNameByCode(list.get(position).getFINVDET_ITEM_CODE()));
        Qty.setText(list.get(position).getFINVDET_QTY());
        Amt.setText(list.get(position).getFINVDET_AMT());


        FreeHedController freeHedDS = new FreeHedController(context);
        arrayList = freeHedDS.getFreeIssueItemDetailByRefno(list.get(position).getFINVDET_ITEM_CODE(),"" );

        //if(arrayList.size()>0){
            for(FreeHed freeHed:arrayList){
                int itemQty = (int) Float.parseFloat(freeHed.getFFREEHED_ITEM_QTY());
                int enterQty = (int) Float.parseFloat(list.get(position).getFINVDET_QTY());

                if(enterQty<itemQty){
                    //other products------this procut has't free items
                    showStatus.setBackgroundColor(Color.WHITE);
                }else{
                      //free item eligible product
                    showStatus.setBackground(context.getResources().getDrawable(R.drawable.ic_free_b));
                }


            }


        /*}else{
           showStatus.setBackgroundColor(Color.WHITE);
        }*/


        return row;
    }


}
