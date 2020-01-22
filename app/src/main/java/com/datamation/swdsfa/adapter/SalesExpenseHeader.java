package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.DayExpHed;

import java.util.ArrayList;

public class SalesExpenseHeader extends ArrayAdapter<DayExpHed> {
    Context context;
    ArrayList<DayExpHed> list;

    public SalesExpenseHeader(Context context, ArrayList<DayExpHed> list) {
        super(context, R.layout.row_expense_history, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_expense_history, parent, false);

        TextView refno = (TextView) row.findViewById(R.id.refno);
        TextView date = (TextView) row.findViewById(R.id.date);
        TextView amount = (TextView) row.findViewById(R.id.amount);


        refno.setText(list.get(position).getEXP_REFNO());
        date.setText(list.get(position).getEXP_TXNDATE());
        amount.setText(String.format("%.2f", Double.parseDouble(list.get(position).getEXP_TOTAMT())));
        return row;
    }
}
