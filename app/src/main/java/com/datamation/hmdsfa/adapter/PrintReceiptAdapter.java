package com.datamation.hmdsfa.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.ReceiptController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.model.ReceiptDet;


public class PrintReceiptAdapter extends ArrayAdapter<ReceiptDet> {
    Context context;
    ArrayList<ReceiptDet> list;
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String Refno;

    public PrintReceiptAdapter(Context context, ArrayList<ReceiptDet> list, String RefNo) {

        super(context, R.layout.row_receipt_details1, list);
        this.context = context;
        this.list = list;
        this.Refno = RefNo;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = mInflater.inflate(R.layout.row_receipt_details1, parent, false);

        TextView Refno = (TextView) row.findViewById(R.id.row_refno);
        TextView TxnDate = (TextView) row.findViewById(R.id.row_txndate);
        TextView AlloAmt = (TextView) row.findViewById(R.id.row_dueAmt);
        TextView BAmt = (TextView) row.findViewById(R.id.row_Amt);

        Refno.setText(list.get(position).getFPRECDET_REFNO());
        TxnDate.setText(list.get(position).getFPRECDET_TXNDATE());
        AlloAmt.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFPRECDET_ALOAMT())));
        BAmt.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFPRECDET_BAMT())));


        return row;

    }
}