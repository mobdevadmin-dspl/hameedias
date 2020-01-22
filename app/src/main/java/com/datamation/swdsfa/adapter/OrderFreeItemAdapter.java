package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.ItemController;
import com.datamation.swdsfa.model.OrderDetail;

import java.util.ArrayList;

public class OrderFreeItemAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<OrderDetail> list;
    Context context;

    public OrderFreeItemAdapter(Context context, ArrayList<OrderDetail> list) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView ==null){
            viewHolder =new ViewHolder();
            convertView =inflater.inflate(R.layout.row_free_issue_details,parent,false);
            viewHolder.Itemcode=(TextView)convertView.findViewById(R.id.row_item);
            viewHolder.Itemname=(TextView)convertView.findViewById(R.id.row_piece);
            viewHolder.Qty =(TextView)convertView.findViewById(R.id.row_cases);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final OrderDetail orderDet=getItem(position);
        viewHolder.Itemcode.setText(orderDet.getFORDERDET_ITEMCODE());
        viewHolder.Itemname.setText(new ItemController(context).getItemNameByCode(orderDet.getFORDERDET_ITEMCODE()));
        viewHolder.Qty.setText(orderDet.getFORDERDET_QTY());

        return convertView;
    }

    private  static  class  ViewHolder{
        RelativeLayout relativeLayout;
        TextView Itemcode;
        TextView Itemname;
        TextView Qty;


    }
}
