package com.datamation.hmdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.model.MainStock;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainStockAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    ArrayList<MainStock> list;
    Context context;
    String debCode;
    private NumberFormat numberFormat = NumberFormat.getInstance();


    public MainStockAdapter(Context context, ArrayList<MainStock> list){
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }
    @Override
    public MainStock getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_main_stock, parent, false);
            viewHolder.lblBarcode = (TextView) convertView.findViewById(R.id.row_barcode);
            viewHolder.lblVariantcode = (TextView) convertView.findViewById(R.id.row_Varientcode);
            viewHolder.lblQOH = (TextView) convertView.findViewById(R.id.row_QOH);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.lblBarcode.setText(list.get(position).getFMAINSTCOK_BARCODE());
        viewHolder.lblVariantcode.setText(list.get(position).getFMAINSTCOK_VARIANT_CODE());
        int qoh = (int) Math.round(Float.parseFloat(list.get(position).getFMAINSTCOK_QUANTITY()));
        viewHolder.lblQOH.setText(qoh+ "");

        return convertView;
    }
    private  static  class  ViewHolder{
        TextView lblBarcode;
        TextView lblVariantcode;
        TextView lblQOH;
    }

}
