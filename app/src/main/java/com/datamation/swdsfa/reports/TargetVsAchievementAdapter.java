package com.datamation.swdsfa.reports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.Target;

import java.text.NumberFormat;
import java.util.ArrayList;

public class TargetVsAchievementAdapter extends BaseAdapter {
    Context context;
    ArrayList<Target> targetData;
    LayoutInflater layoutInflater;
    Target targetModel;
    private NumberFormat numberFormat = NumberFormat.getInstance();
    public TargetVsAchievementAdapter(Context context, ArrayList<Target> targetData) {
        this.context = context;
        this.targetData = targetData;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return targetData.size();
    }

    @Override
    public Object getItem(int i) {
        return targetData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View rowView = view;
        if (rowView==null) {
            rowView = layoutInflater.inflate(R.layout.target_vs_actual_row_responsive_layout, null, true);
        }
        //link views

        TextView targetDate = rowView.findViewById(R.id.target_date);
        TextView targetAmt = rowView.findViewById(R.id.target_amount);
        TextView actualAmt = rowView.findViewById(R.id.actual_amount);

        targetModel = targetData.get(position);


        targetDate.setText(targetModel.getDate());
        targetAmt.setText("" + numberFormat.format(targetModel.getTargetAmt()));
        actualAmt.setText("" + numberFormat.format(targetModel.getAchieveAmt()));

        return rowView;
    }
}

