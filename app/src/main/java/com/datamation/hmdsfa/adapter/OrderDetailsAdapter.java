package com.datamation.hmdsfa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.datamation.hmdsfa.controller.FreeHedController;
import com.datamation.hmdsfa.controller.ItemBundleController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.TaxDetController;
import com.datamation.hmdsfa.model.FreeHed;
import com.datamation.hmdsfa.model.OrderDetail;
import com.datamation.hmdsfa.R;

import java.util.ArrayList;


public class OrderDetailsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    ArrayList<OrderDetail> list;
    Context context;
    String debCode;

    public OrderDetailsAdapter(Context context, ArrayList<OrderDetail> list, String debCode){
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

        ViewHolder viewHolder;
        if(convertView ==null) {
            viewHolder = new ViewHolder();
            convertView =inflater.inflate(R.layout.row_order_details,parent,false);
            viewHolder.lblItem = (TextView) convertView.findViewById(R.id.row_item);
            viewHolder.lblQty = (TextView) convertView.findViewById(R.id.row_cases);
            viewHolder.lblAMt = (TextView) convertView.findViewById(R.id.row_piece);
            viewHolder.showStatus=(TextView)convertView.findViewById(R.id.row_free_status);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ItemBundleController ds = new ItemBundleController(context);
        viewHolder.lblItem.setText(list.get(position).getFORDERDET_BARCODE()+ " - "+list.get(position).getFORDERDET_ITEMCODE()+ " - " +ds.getItemNameByCode(list.get(position).getFORDERDET_ITEMCODE()));
        viewHolder.lblQty.setText(list.get(position).getFORDERDET_QTY());
     //   String sArray[] = new TaxDetController(context).calculateTaxForwardFromDebTax(debCode, list.get(position).getFORDERDET_ITEMCODE(), Double.parseDouble(list.get(position).getFORDERDET_AMT()));
        String amt = String.format("%.2f",Double.parseDouble(list.get(position).getFORDERDET_AMT()));
        viewHolder.lblAMt.setText(amt);

        return convertView;
    }
    //12972
    //1067
    // 1003.75
    //1649
    //600
    //990+419.37
    //19215.00

    private  static  class  ViewHolder{
        TextView lblItem;
        TextView lblQty;
        TextView lblAMt;
        TextView showStatus;

    }

}
