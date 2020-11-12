package com.datamation.hmdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.ReceiptController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.model.PayMode;
import com.datamation.hmdsfa.model.ReceiptDet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrintPayModeAdapter extends ArrayAdapter<PayMode>  {

        Context context;
        ArrayList<PayMode> list;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String Refno;

        public PrintPayModeAdapter(Context context, ArrayList<PayMode> list, String RefNo) {

            super(context, R.layout.row_receipt_paymode_details1, list);
            this.context = context;
            this.list = list;
            this.Refno = RefNo;
        }


    @Override
        public View getView(int position, View convertView, final ViewGroup parent) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = mInflater.inflate(R.layout.row_receipt_paymode_details1, parent, false);

            TextView PayType = (TextView) row.findViewById(R.id.row_paytype);
            TextView Bank = (TextView) row.findViewById(R.id.row_bank);
            TextView ChqNo = (TextView) row.findViewById(R.id.row_chqno);
            TextView ChqDate = (TextView) row.findViewById(R.id.row_chqdate);
            TextView Amt = (TextView) row.findViewById(R.id.row_Amt);

            PayType.setText(list.get(position).getFPAYMODE_PAID_TYPE());
            Bank.setText(list.get(position).getFPAYMODE_PAID_BANK());
            ChqNo.setText(list.get(position).getFPAYMODE_PAID_CHEQUE_NO());

            if(list.get(position).getFPAYMODE_PAID_TYPE().equals("CA")){
                ChqDate.setText(list.get(position).getFPAYMODE_PAID_DATE());
            }
            else {
                ChqDate.setText(list.get(position).getFPAYMODE_PAID_CHEQUE_DATE());
            }

            Amt.setText(String.format("%,.2f", Double.parseDouble(list.get(position).getFPAYMODE_PAID_AMOUNT())));

            return row;

        }

}
