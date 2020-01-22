package com.datamation.swdsfa.reports;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.DayExpDet;
import com.datamation.swdsfa.model.OrderDetail;

import java.text.NumberFormat;
import java.util.ArrayList;

public class PreSalesReportAdapter extends BaseAdapter {

    Context context;
    ArrayList<OrderDetail> OrderData;
    LayoutInflater layoutInflater;
    OrderDetail OrderModel;

    private NumberFormat numberFormat = NumberFormat.getInstance();

    public PreSalesReportAdapter(Context context , ArrayList<OrderDetail>OrderData)
    {
        this.context = context ;
        this.OrderData = OrderData;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return OrderData.size();
    }

    @Override
    public Object getItem(int i) {
        return OrderData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View rowView = view;
        if(rowView==null)
        {
            rowView = layoutInflater.inflate(R.layout.pre_sale_report,null,true );

        }

        //link views
        TextView itemname = rowView.findViewById(R.id.Item_Name);
        TextView caseqty = rowView.findViewById(R.id.Case_Qty);
        TextView pieceqty = rowView.findViewById(R.id.pice_qty);
        TextView amt = rowView.findViewById(R.id.Pre_amount);
        TextView reach = rowView.findViewById(R.id.Reach);

        OrderModel = OrderData.get(i);
        int pieces = Integer.parseInt(OrderModel.getFORDERDET_PICE_QTY());
        int cases = Integer.parseInt(OrderModel.getFORDERDET_CASES());
        int units = Integer.parseInt(OrderModel.getFORDERDET_QOH());
        int reachOfItem = Integer.parseInt(OrderModel.getFORDERDET_SEQNO());

        cases = cases + (pieces/units);
        pieces = pieces % units;

        itemname.setText(OrderModel.getFORDERDET_ITEMNAME());
        caseqty.setText(""+cases);
        pieceqty.setText(""+pieces);
        amt.setText(OrderModel.getFORDERDET_AMT());
        reach.setText(""+OrderModel.getFORDERDET_SEQNO());

        return rowView;

    }
}

