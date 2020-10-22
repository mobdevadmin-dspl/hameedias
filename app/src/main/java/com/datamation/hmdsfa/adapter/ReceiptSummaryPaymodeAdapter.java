package com.datamation.hmdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.model.PaymentAllocate;

import java.util.ArrayList;

public class ReceiptSummaryPaymodeAdapter extends ArrayAdapter<PaymentAllocate> {

    Context context;
    ArrayList<PaymentAllocate> list;

    public ReceiptSummaryPaymodeAdapter(Context context, ArrayList<PaymentAllocate> list ) {
        super(context, R.layout.row_pay_mode_summary, list);
        this.context = context;
        this.list = list;
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

        if (row == null)
        {
            holder = new Holder();

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_pay_mode_summary, parent, false);

            holder.allocPayMode = (TextView) row.findViewById(R.id.txtPayMode);
            holder.allocChqNo = (TextView) row.findViewById(R.id.txtChqNo);
            holder.allocChqDate = (TextView)row.findViewById(R.id.txtDate);
            holder.allocPaidAmt = (TextView)row.findViewById(R.id.txtAmount);

            row.setTag(holder);
        }
        else
        {
            holder = (Holder)row.getTag();
        }

        if (paymentAllocate.getFPAYMENT_ALLOCATE_PAY_MODE().equalsIgnoreCase("CA"))
        {
            holder.allocChqNo.setText("N/A");
            holder.allocChqDate.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_DATE());
            holder.allocPayMode.setText("CASH");
        }
        else if (paymentAllocate.getFPAYMENT_ALLOCATE_PAY_MODE().equalsIgnoreCase("CH"))
        {
            holder.allocChqNo.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_NO());
            holder.allocChqDate.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE());
            holder.allocPayMode.setText("CHEQUE");
        }
        else if (paymentAllocate.getFPAYMENT_ALLOCATE_PAY_MODE().equalsIgnoreCase("CC"))
        {
            holder.allocChqNo.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO());
            holder.allocChqDate.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE());
            holder.allocPayMode.setText("CREDIT CARD");
        }
        else if (paymentAllocate.getFPAYMENT_ALLOCATE_PAY_MODE().equalsIgnoreCase("DD"))
        {
            holder.allocChqNo.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_SLIP_NO());
            holder.allocChqDate.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE());
            holder.allocPayMode.setText("DIRECT DEPOSIT");
        }
        else if (paymentAllocate.getFPAYMENT_ALLOCATE_PAY_MODE().equalsIgnoreCase("BD"))
        {
            holder.allocChqNo.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_DRAFT_NO());
            holder.allocChqDate.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE());
            holder.allocPayMode.setText("BANK DRAFT");
        }

        holder.allocPaidAmt.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_AMT());

        return row;
    }

    public static class Holder {
        TextView allocPayMode;
        TextView allocChqNo;
        TextView allocChqDate;
        TextView allocPaidAmt;

    }
}
