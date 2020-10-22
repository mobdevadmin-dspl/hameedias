package com.datamation.hmdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.PaymentAllocateController;
import com.datamation.hmdsfa.model.PaymentAllocate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReceiptSummaryAdapter extends ArrayAdapter<PaymentAllocate> {

    Context context;
    ArrayList<PaymentAllocate>list;
    ArrayList<PaymentAllocate>paidList;
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String comRefNo;

    public ReceiptSummaryAdapter(Context context, ArrayList<PaymentAllocate> list, String comRefNo ) {
        super(context, R.layout.row_pay_mode_summary, list);
        this.context = context;
        this.list = list;
        this.comRefNo = comRefNo;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public PaymentAllocate getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = convertView;
        final Holder holder;
        final PaymentAllocate paymentAllocate = list.get(position);
        long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

        if (row == null)
        {
            holder = new Holder();

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_receipt_details1, parent, false);

            holder.refNo = (TextView) row.findViewById(R.id.row_refno);
            holder.txnDate = (TextView) row.findViewById(R.id.row_txndate);
            holder.dueAmt = (TextView)row.findViewById(R.id.row_dueAmt);
            holder.paidAmt = (TextView)row.findViewById(R.id.row_Amt);
            holder.days = (TextView)row.findViewById(R.id.days);

            row.setTag(holder);
        }
        else
        {
            holder = (Holder)row.getTag();
        }

        holder.refNo.setText(paymentAllocate.getFPAYMENT_ALLOCATE_FDD_REFNO());
        holder.txnDate.setText(paymentAllocate.getFPAYMENT_ALLOCATE_FDD_TXN_DATE());
//        holder.dueAmt.setText(String.valueOf(Double.parseDouble(paymentAllocate.getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL())- Double.parseDouble(getTotalPaidAmt(holder.refNo.getText().toString()))));
        holder.dueAmt.setText(String.valueOf(Double.parseDouble(paymentAllocate.getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL())));
        holder.paidAmt.setText(getTotalPaidAmt(holder.refNo.getText().toString()));

        long txn = 0;
        long current =0;
        Date date;
        try {
            date = (Date)formatter.parse(paymentAllocate.getFPAYMENT_ALLOCATE_FDD_TXN_DATE());
            System.out.println("receipt date is " +date.getTime());
            txn = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        int numOfDays =   (int) ((System.currentTimeMillis()  - txn) / DAY_IN_MILLIS);

        holder.days.setText(String.valueOf(numOfDays));

        return row;
    }

    public static class Holder {
        TextView refNo;
        TextView txnDate;
        TextView dueAmt;
        TextView paidAmt;
        TextView days;
    }

    private String getTotalPaidAmt(String refNo)
    {
        paidList = new PaymentAllocateController(context).getAllPaidRecords(refNo, comRefNo);
        Double totalPaidAmount = 0.0;

        for (PaymentAllocate paymentAllocate : paidList)
        {
            totalPaidAmount += Double.parseDouble(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_ALLO_AMT());
        }

        return String.valueOf(totalPaidAmount);
    }
}
