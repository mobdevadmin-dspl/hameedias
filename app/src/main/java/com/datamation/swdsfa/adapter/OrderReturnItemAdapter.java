package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.FreeHedController;
import com.datamation.swdsfa.controller.ItemController;
import com.datamation.swdsfa.controller.ReasonController;
import com.datamation.swdsfa.model.FreeHed;
import com.datamation.swdsfa.model.OrderDetail;

import java.util.ArrayList;

public class OrderReturnItemAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    ArrayList<OrderDetail> list;
    ArrayList<FreeHed> arrayList;
    Context context;
    String debCode;
    String resonnm;

    public OrderReturnItemAdapter(Context context, ArrayList<OrderDetail> list, String debCode){
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.debCode = debCode;

    }
    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }
    @Override
    public OrderDetail getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        OrderReturnItemAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new OrderReturnItemAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.row_retrun_details, parent, false);
            viewHolder.lblItem = (TextView) convertView.findViewById(R.id.row_item);
            viewHolder.lblQty = (TextView) convertView.findViewById(R.id.row_qty);
            viewHolder.lblReturnReason = (TextView) convertView.findViewById(R.id.row_reason);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (OrderReturnItemAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.lblItem.setText(list.get(position).getFORDERDET_ITEMCODE() + " - " + new ItemController(convertView.getContext()).getItemNameByCode(list.get(position).getFORDERDET_ITEMCODE()));
        viewHolder.lblQty.setText(list.get(position).getFORDERDET_QTY());
        resonnm = new ReasonController(context).getReaNameByCode(list.get(position).getFORDERDET_REACODE().trim());
        viewHolder.lblReturnReason.setText(resonnm);

        return convertView;
    }
    private  static  class  ViewHolder{
        TextView lblItem;
        TextView lblQty;
        TextView lblReturnReason;
    }
}
