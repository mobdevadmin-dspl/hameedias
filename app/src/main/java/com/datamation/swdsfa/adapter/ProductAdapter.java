package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.model.Item;
import com.datamation.swdsfa.R;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Item> {
    Context context;
    ArrayList<Item> list;

    public ProductAdapter(Context context, ArrayList<Item> list) {

        super(context, R.layout.row_item_listview, list);
        this.context = context;
        this.list = list;

    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_item_listview, parent, false);

        TextView itemCode = (TextView) row.findViewById(R.id.tv_item_code);
        TextView ItemName = (TextView) row.findViewById(R.id.tv_item_name);

        itemCode.setText(list.get(position).getFITEM_ITEM_CODE());
        ItemName.setText(list.get(position).getFITEM_ITEM_NAME());

        return row;
    }
}
