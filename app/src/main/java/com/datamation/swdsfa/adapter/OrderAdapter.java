package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.Order;

import java.util.ArrayList;


public class OrderAdapter extends ArrayAdapter<Order> {
    Context context;
    ArrayList<Order> list;


    public OrderAdapter(Context context, ArrayList<Order> list) {
        super(context, R.layout.row_cus_view, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        row = inflater.inflate(R.layout.row_cus_view, parent, false);

        TextView Itemname = (TextView) row.findViewById(R.id.row_refno);
        TextView Itemcode = (TextView) row.findViewById(R.id.row_cus_name);
        TextView syncStatus = (TextView) row.findViewById(R.id.addeddate);
       // ImageView imageViewCus = (ImageView) row.findViewById(R.id.imageViewCus);
        LinearLayout sts = (LinearLayout)row.findViewById(R.id.sts);


    //    list = new OrderController(context).getAllOrders();

//       if(list.get(position).getORDHED_IS_SYNCED().equalsIgnoreCase("1")){
//           sts.setBackgroundResource(R.drawable.status_synced);// synced
//       }else{
//           sts.setBackgroundResource(R.drawable.status_line);//not synced
//       }
//
//        Itemcode.setText(""+new CustomerController(context).getSelectedCustomerByCode(list.get(position).getORDHED_CUS_CODE()).getCusName());
//        Itemname.setText(list.get(position).getORDHED_REFNO());
//        syncStatus.setText(list.get(position).getORDHED_TXN_DATE());

        return row;
    }
}
