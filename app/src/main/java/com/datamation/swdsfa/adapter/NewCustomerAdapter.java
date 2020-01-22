package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datamation.swdsfa.model.NewCustomer;
import com.datamation.swdsfa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rashmi.
 */

public class NewCustomerAdapter extends ArrayAdapter<NewCustomer> {
    Context context;
    ArrayList<NewCustomer> list;


    public NewCustomerAdapter(Context context, ArrayList<NewCustomer> list) {
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
        ImageView imageViewCus = (ImageView) row.findViewById(R.id.imageViewCus);
        LinearLayout sts = (LinearLayout)row.findViewById(R.id.sts);


        Picasso.get()
                .load(list.get(position).getC_IMAGE())
                .placeholder(R.drawable.ic_image2)
                .error(R.drawable.ic_image2)
                .into(imageViewCus);


       if(list.get(position).getC_SYNCSTATE().equalsIgnoreCase("1")){
           sts.setBackgroundResource(R.drawable.status_synced);// synced
       }else{
           sts.setBackgroundResource(R.drawable.status_line);//not synced
       }

        Itemname.setText(list.get(position).getCUSTOMER_ID());
        Itemcode.setText(list.get(position).getNAME());
        syncStatus.setText("");

        return row;
    }
}
