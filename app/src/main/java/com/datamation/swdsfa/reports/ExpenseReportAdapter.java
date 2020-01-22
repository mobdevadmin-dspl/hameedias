package com.datamation.swdsfa.reports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.DayExpDet;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ExpenseReportAdapter extends BaseAdapter {
    Context context;
    ArrayList<DayExpDet> expenseData;
    LayoutInflater layoutInflater;
    DayExpDet expenseModel;
    //Target targetModel;
    private NumberFormat numberFormat = NumberFormat.getInstance();
    public ExpenseReportAdapter(Context context, ArrayList<DayExpDet> expenseData) {
        this.context = context;
        this.expenseData = expenseData;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return expenseData.size();
    }

    @Override
    public Object getItem(int i) {
        return expenseData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    /*Develop by kaveesha*/
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View rowView = view;
        if (rowView==null) {
            rowView = layoutInflater.inflate(R.layout.day_expence_report, null, true);
        }
        //link views

        TextView expenceCode = rowView.findViewById(R.id.expence_code);
        TextView expenceAmount = rowView.findViewById(R.id.expense_amount);
        TextView expenseDescription = rowView.findViewById(R.id.expense_name);

        expenseModel = expenseData.get(position);


        expenceCode.setText(expenseModel.getEXPDET_EXPCODE());
        expenceAmount.setText(expenseModel.getEXPDET_AMOUNT());
        expenseDescription.setText(expenseModel.getEXPDET_DESCRIPTION());

        return rowView;
    }
}

