package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.ItemController;
import com.datamation.swdsfa.controller.ItemPriceController;
import com.datamation.swdsfa.model.StockInfo;

import java.util.ArrayList;

public class StockInquiryAdaptor extends ArrayAdapter<StockInfo> {
    Context context;
    ArrayList<StockInfo> list;
    String itemPrice = "";

    public StockInquiryAdaptor(Context context, ArrayList<StockInfo> list) {

        super(context, R.layout.row_stock_inquiry, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_stock_inquiry, parent, false);

        TextView itemcode = (TextView) row.findViewById(R.id.row_itemcode);
        TextView itemname = (TextView) row.findViewById(R.id.row_itemname);
        TextView qty = (TextView) row.findViewById(R.id.row_qty);
        TextView price = (TextView) row.findViewById(R.id.row_price);

        itemcode.setText(list.get(position).getStock_Itemcode());
        itemname.setText(list.get(position).getStock_Itemname());
        qty.setText(list.get(position).getStock_Qoh());
        itemPrice = new ItemPriceController(context).getProductPriceByCode(list.get(position).getStock_Itemcode(), "WSP001");
        //itemPrice = new ItemPriceController(context).getProductPriceByCode(list.get(position).getStock_Itemcode(), list.get(position).getStock_Pril_Code());
        price.setText(itemPrice);
        return row;
    }
}
