package com.datamation.hmdsfa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.FreeHedController;
import com.datamation.hmdsfa.controller.ItemBundleController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.model.Discount;
import com.datamation.hmdsfa.model.FreeHed;
import com.datamation.hmdsfa.model.InvDet;

import java.util.ArrayList;

public class DiscountAdapter extends ArrayAdapter<Discount> {
    Context context;
    ArrayList<Discount> list;

    public DiscountAdapter(Context context, ArrayList<Discount> list) {

        super(context, R.layout.row_discount, list);
        this.context = context;
        this.list = list;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_discount, parent, false);

        TextView GroupName = (TextView) row.findViewById(R.id.row_groupName);
        TextView Discount = (TextView) row.findViewById(R.id.row_discount);

        ItemController itm = new ItemController(getContext());
        GroupName.setText(itm.getGroupNameByCode(list.get(position).getProductGroup()));

        if(list.get(position).getPayType().equals("CASH"))
        {
            Discount.setText(list.get(position).getProductCashDis());
        }else{
            Discount.setText(list.get(position).getProductDis());
        }


        return row;
    }


}
