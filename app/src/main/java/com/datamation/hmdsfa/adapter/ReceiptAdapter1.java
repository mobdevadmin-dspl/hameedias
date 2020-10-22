package com.datamation.hmdsfa.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.PaymentAllocateController;
import com.datamation.hmdsfa.model.FddbNote;
import com.datamation.hmdsfa.model.PaymentAllocate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReceiptAdapter1 extends ArrayAdapter<FddbNote> {
    Context context;
    ArrayList<FddbNote> list;
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<PaymentAllocate>paidList;
    ArrayList<PaymentAllocate>updatedOrderList, updatedOrderList1;
    String comRefNo;

    public ReceiptAdapter1(Context context, ArrayList<FddbNote> list, final String comRefNo) {

        super(context, R.layout.row_receipt_details1, list);
        this.context = context;
        this.list = list;
        this.comRefNo = comRefNo;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = convertView;
        long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        final ViewHolder viewHolder;
        final FddbNote fdDbNote = list.get(position);

        if (row == null){

            viewHolder = new ViewHolder();

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_receipt_details1, parent, false);

            viewHolder.lblRefNo = (TextView) row.findViewById(R.id.row_refno);
            viewHolder.lblTxnDate = (TextView) row.findViewById(R.id.row_txndate);
            viewHolder.lblDueAmt = (TextView) row.findViewById(R.id.row_dueAmt);
            viewHolder.lblAmt = (TextView) row.findViewById(R.id.row_Amt);
            viewHolder.lblDays = (TextView) row.findViewById(R.id.days);

            row.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.lblRefNo.setText(fdDbNote.getFDDBNOTE_REFNO());
        viewHolder.lblTxnDate.setText(fdDbNote.getFDDBNOTE_TXN_DATE().toString());

        Date date;
        long txn = 0;
        try {
            date = (Date)formatter.parse(fdDbNote.getFDDBNOTE_TXN_DATE());
            System.out.println("receipt date is " +date.getTime());
            txn = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        int numOfDays =   (int) ((System.currentTimeMillis()  - txn) / DAY_IN_MILLIS);
        viewHolder.lblDays.setText(""+numOfDays);

        if (fdDbNote.getFDDBNOTE_ENTER_AMT().equals("") || fdDbNote.getFDDBNOTE_ENTER_AMT()== null)
        {
            viewHolder.lblAmt.setText("0.00");

            if (Double.parseDouble(getTotalPaidAmt(fdDbNote.getFDDBNOTE_REFNO()))>0)
            {
                viewHolder.lblDueAmt.setText(String.format("%,.2f", Double.parseDouble(fdDbNote.getFDDBNOTE_TOT_BAL())
                        - Double.parseDouble(getTotalPaidAmt(fdDbNote.getFDDBNOTE_REFNO()))));
                viewHolder.lblAmt.setText(String.format("%,.2f", Double.parseDouble(getTotalPaidAmt(fdDbNote.getFDDBNOTE_REFNO()))));
            }
            else
            {
                viewHolder.lblDueAmt.setText(String.format("%,.2f", Double.parseDouble(fdDbNote.getFDDBNOTE_TOT_BAL())));
            }
        }
        else
        {
            viewHolder.lblAmt.setText(fdDbNote.getFDDBNOTE_ENTER_AMT());
        }

        viewHolder.lblAmt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                EditPaymentDialogBox(viewHolder, fdDbNote, comRefNo);

                return false;
            }
        });

        return row;
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

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public FddbNote getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder
    {
        TextView lblRefNo;
        TextView lblTxnDate;
        TextView lblDueAmt;
        TextView lblAmt;
        TextView lblDays;
    }

    public void EditPaymentDialogBox(final ViewHolder viewHolder, final FddbNote fdDbNote, final String payComRefNo)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.payment_dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        final ListView lvPayments = (ListView) promptView.findViewById(R.id.lv_payment_edit_list);

        lvPayments.clearTextFilter();
        updatedOrderList = new PaymentAllocateController(context).getAllPaidRecordsByTwoRefNo(fdDbNote.getFDDBNOTE_REFNO(), payComRefNo);
        lvPayments.setAdapter(new PaymentAllocateAdapter(context, updatedOrderList, payComRefNo));
        alertDialogBuilder.setTitle("Edit paid amounts");
        alertDialogBuilder.setCancelable(false).setNegativeButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (Double.parseDouble(getTotalPaidAmt(fdDbNote.getFDDBNOTE_REFNO()))>0)
                {
                    viewHolder.lblDueAmt.setText(String.format("%,.2f", Double.parseDouble(fdDbNote.getFDDBNOTE_TOT_BAL())
                            - Double.parseDouble(getTotalPaidAmt(fdDbNote.getFDDBNOTE_REFNO()))));
                    viewHolder.lblAmt.setText(String.format("%,.2f", Double.parseDouble(getTotalPaidAmt(fdDbNote.getFDDBNOTE_REFNO()))));

                    updatedOrderList1 = new PaymentAllocateController(context).getAllPaidRecordsByTwoRefNo(fdDbNote.getFDDBNOTE_REFNO(), payComRefNo);
                    Double remTotBal = Double.parseDouble(fdDbNote.getFDDBNOTE_TOT_BAL());
                    ArrayList<PaymentAllocate>paymentAllocateArrayList = new ArrayList<>();

                    for (PaymentAllocate allocate:updatedOrderList1)
                    {
                        PaymentAllocate paymentAllocate = new PaymentAllocate();

                        paymentAllocate.setFPAYMENT_ALLOCATE_ID(allocate.getFPAYMENT_ALLOCATE_ID());
                        paymentAllocate.setFPAYMENT_ALLOCATE_REFNO(allocate.getFPAYMENT_ALLOCATE_REFNO());
                        paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TOTAL_BAL(String.valueOf(remTotBal -= Double.parseDouble(allocate.getFPAYMENT_ALLOCATE_PAY_ALLO_AMT())));
                        paymentAllocate.setFPAYMENT_ALLOCATE_PAY_ALLO_AMT(allocate.getFPAYMENT_ALLOCATE_PAY_ALLO_AMT());

                        paymentAllocate.setFPAYMENT_ALLOCATE_COMMON_REFNO(allocate.getFPAYMENT_ALLOCATE_COMMON_REFNO());
                        paymentAllocate.setFPAYMENT_ALLOCATE_FDD_REFNO(allocate.getFPAYMENT_ALLOCATE_FDD_REFNO());
                        paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TXN_DATE(allocate.getFPAYMENT_ALLOCATE_FDD_TXN_DATE());
                        paymentAllocate.setFPAYMENT_ALLOCATE_FDD_PAID_AMT(allocate.getFPAYMENT_ALLOCATE_FDD_PAID_AMT());
                        paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REF_NO(allocate.getFPAYMENT_ALLOCATE_PAY_REF_NO());
                        paymentAllocate.setFPAYMENT_ALLOCATE_PAY_MODE(allocate.getFPAYMENT_ALLOCATE_PAY_MODE());
                        paymentAllocate.setFPAYMENT_ALLOCATE_PAY_DATE(allocate.getFPAYMENT_ALLOCATE_PAY_DATE());
                        paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE(allocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE());
                        paymentAllocate.setFPAYMENT_ALLOCATE_PAY_AMT(allocate.getFPAYMENT_ALLOCATE_PAY_AMT());
                        paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REM_AMT(allocate.getFPAYMENT_ALLOCATE_PAY_REM_AMT());
                        paymentAllocate.setFPAYMENT_ALLOCATE_PAY_BANK(allocate.getFPAYMENT_ALLOCATE_PAY_BANK());
                        paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_NO(allocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_NO());
                        paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO(allocate.getFPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO());
                        paymentAllocate.setFPAYMENT_ALLOCATE_PAY_SLIP_NO(allocate.getFPAYMENT_ALLOCATE_PAY_SLIP_NO());
                        paymentAllocate.setFPAYMENT_ALLOCATE_PAY_DRAFT_NO(allocate.getFPAYMENT_ALLOCATE_PAY_DRAFT_NO());

                        paymentAllocateArrayList.add(paymentAllocate);
                    }

                    if (paymentAllocateArrayList.size()>0)
                    {
                        new PaymentAllocateController(context).createOrUpdatePaymentAllocate(paymentAllocateArrayList);
                    }

                    new PaymentAllocateController(context).updatePaidAmount(fdDbNote.getFDDBNOTE_REFNO(), getTotalPaidAmt(fdDbNote.getFDDBNOTE_REFNO()));
                }
                else
                {
                    viewHolder.lblDueAmt.setText(String.format("%,.2f", Double.parseDouble(fdDbNote.getFDDBNOTE_TOT_BAL())));
                }
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}