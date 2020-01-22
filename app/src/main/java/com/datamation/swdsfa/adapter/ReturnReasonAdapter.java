package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.Reason;

import java.util.ArrayList;

public class ReturnReasonAdapter extends BaseAdapter {

    Context context;
    ArrayList<Reason> list;

    public ReturnReasonAdapter(Context context, ArrayList<Reason> list) {
        //super(context, textViewResourceId, list);
        this.context = context;
        this.list = list;

    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_return_reasons, parent, false);

        TextView Itemcode = (TextView) row.findViewById(R.id.row_return_reasonName);


        Itemcode.setText(list.get(position).getFREASON_NAME());


        return row;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}
