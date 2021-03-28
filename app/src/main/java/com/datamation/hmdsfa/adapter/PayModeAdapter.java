package com.datamation.hmdsfa.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.PayModeController;
import com.datamation.hmdsfa.dialog.CustomKeypadDialogReceipt;
import com.datamation.hmdsfa.dialog.PayModeKeypadDialog;
import com.datamation.hmdsfa.model.PayMode;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class PayModeAdapter extends ArrayAdapter<PayMode> {

    Context context;
    ArrayList<PayMode> list;
    boolean isInvoiceSelected;
    ArrayList<PayMode>allPayModeList;
    ArrayList<PayMode>data;

    public PayModeAdapter(Context context, ArrayList<PayMode> list, boolean isInvoiceSelected ) {
        super(context, R.layout.row_pay_mode, list);
        this.context = context;
        this.list = list;
        this.isInvoiceSelected = isInvoiceSelected;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public PayMode getItem(int position) {
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
        final PayMode payMode = list.get(position);

        if (row == null)
        {
            holder = new Holder();

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_pay_mode, parent, false);

            holder.lnStripe = (LinearLayout)row.findViewById(R.id.lnPaymodeStripe);
            holder.refNo = (TextView)row.findViewById(R.id.txtPayModerRefNo);
            holder.mode = (TextView) row.findViewById(R.id.txtPayMode);
            holder.date = (TextView) row.findViewById(R.id.txtPayDate);
            holder.amt = (TextView)row.findViewById(R.id.txtAmount);
            holder.remAmt = (TextView)row.findViewById(R.id.txtRemAmount);
            holder.allocatedAmt = (TextView)row.findViewById(R.id.editAlloAmount);

            row.setTag(holder);
        }
        else
        {
            holder = (Holder)row.getTag();
        }


        holder.date.setText(payMode.getFPAYMODE_PAID_DATE());
        holder.amt.setText(payMode.getFPAYMODE_PAID_AMOUNT());
        holder.refNo.setText(payMode.getFPAYMODE_REF_NO());

        if (payMode.getFPAYMODE_PAID_TYPE().equals("CA"))
        {
            holder.mode.setText("CASH");
        }
        else if (payMode.getFPAYMODE_PAID_TYPE().equals("CH"))
        {
            holder.mode.setText("CHEQUE");
        }
        else if (payMode.getFPAYMODE_PAID_TYPE().equals("CC"))
        {
            holder.mode.setText("CREDIT CARD");
        }
        else if (payMode.getFPAYMODE_PAID_TYPE().equals("DD"))
        {
            holder.mode.setText("DIRECT DEPOSIT");
        }
        else if (payMode.getFPAYMODE_PAID_TYPE().equals("BD"))
        {
            holder.mode.setText("BANK DRAFT");
        }

        if (payMode.getFPAYMODE_PAID_REMAMT() == null || payMode.getFPAYMODE_PAID_REMAMT().isEmpty() )
        {
            holder.remAmt.setText("0.00");
        }
        else
        {
            holder.remAmt.setText(payMode.getFPAYMODE_PAID_REMAMT());
        }



        if (payMode.getFPAYMODE_PAID_ALLOAMT()== null|| payMode.getFPAYMODE_PAID_ALLOAMT().isEmpty())
        {
            holder.allocatedAmt.setText("0.00");
        }
        else
        {
            if (Double.parseDouble(payMode.getFPAYMODE_PAID_REMAMT().replaceAll(",",""))< Double.parseDouble(payMode.getFPAYMODE_PAID_AMOUNT().replaceAll(",","")))
            {
                holder.allocatedAmt.setText(String.valueOf(Double.parseDouble(payMode.getFPAYMODE_PAID_AMOUNT().replaceAll(",","")) - Double.parseDouble(payMode.getFPAYMODE_PAID_REMAMT().replaceAll(",",""))));
            }
            else
            {
                holder.allocatedAmt.setText(payMode.getFPAYMODE_PAID_ALLOAMT());
            }

        }

        //holder.allocatedAmt.setText("0.00");

        if (Double.parseDouble(holder.allocatedAmt.getText().toString()) > 0) {
            holder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));

        } else {
            holder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));
        }

        holder.amt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Double.parseDouble(holder.remAmt.getText().toString().replaceAll(",",""))== Double.parseDouble(holder.amt.getText().toString().replaceAll(",","")))
                {
                    final CustomKeypadDialogReceipt keypad = new CustomKeypadDialogReceipt(context, false, new CustomKeypadDialogReceipt.IOnOkClickListener() {
                        @Override
                        public void okClicked(double value) {

                            int enteredAmt = (int) value;

                            if (enteredAmt>0)
                            {
                                new PayModeController(context).updatePaidAmount(payMode.getFPAYMODE_REF_NO(), String.valueOf(enteredAmt));
                                payMode.setFPAYMODE_PAID_AMOUNT(String.valueOf(enteredAmt));
                                payMode.setFPAYMODE_PAID_REMAMT(String.valueOf(enteredAmt));
                                holder.amt.setText(payMode.getFPAYMODE_PAID_AMOUNT());
                                holder.remAmt.setText(payMode.getFPAYMODE_PAID_REMAMT());
                            }
                        }
                    });

                    keypad.show();
                    keypad.setHeader("EDIT PAYMODE PAY AMOUNT");
                }

            }
        });

        holder.allocatedAmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInvoiceSelected)
                {
                    final CustomKeypadDialogReceipt keypad = new CustomKeypadDialogReceipt(context, false, new CustomKeypadDialogReceipt.IOnOkClickListener() {
                        @Override
                        public void okClicked(double value) {
                            //String distrStock = preProduct.getPREPRODUCT_QOH();
                            double enteredAmt =  value;
                            DecimalFormat df = new DecimalFormat("###.##");


                            String amt = payMode.getFPAYMODE_PAID_REMAMT().replaceAll(",","");

                            if (enteredAmt>Double.parseDouble(amt))
                            {
                                Toast.makeText(context, "Entered amount can not be exceed the Reamin amount", Toast.LENGTH_LONG).show();
                            }
                            else if (payMode.getFPAYMODE_PAID_ALLOAMT().equals("0.00"))
                            {
                                Double remAmt = Double.parseDouble(amt)- Double.parseDouble(String.valueOf(enteredAmt));
                                new PayModeController(context).updateRemainAmount(payMode.getFPAYMODE_PAID_ID(), String.valueOf(remAmt));
                                new PayModeController(context).updateAllocateAmt(payMode.getFPAYMODE_PAID_ID(), String.valueOf(enteredAmt));
                                payMode.setFPAYMODE_PAID_ALLOAMT(String.valueOf(enteredAmt));
                                payMode.setFPAYMODE_PAID_REMAMT(String.valueOf(remAmt));
                                holder.allocatedAmt.setText(df.format(enteredAmt));
                                //holder.allocatedAmt.setText(payMode.getFPAYMODE_PAID_ALLOAMT());
                                holder.remAmt.setText(payMode.getFPAYMODE_PAID_REMAMT());

                                //*Change colors*//**//*
                                if (Double.parseDouble(holder.allocatedAmt.getText().toString()) > 0)
                                    holder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
                                else
                                    holder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));

                            }


                        }
                    });

                    keypad.show();

                    keypad.setHeader("ADD ALLOCATE AMOUNT");
                    //keypad.loadValue(Double.parseDouble(payMode.getFPAYMODE_PAID_ALLOAMT()));
                }
                else if (!payMode.getFPAYMODE_PAID_ALLOAMT().equals("0.00"))
                {
                    final PayModeKeypadDialog keypad = new PayModeKeypadDialog(context, false, new PayModeKeypadDialog.IOnOkClickListener() {
                        @Override
                        public void okClicked(double value) {
                            //String distrStock = preProduct.getPREPRODUCT_QOH();
                            double enteredAmt = value;
                            DecimalFormat df = new DecimalFormat("###.##");

                            String paidAmt = payMode.getFPAYMODE_PAID_AMOUNT().replaceAll(",","");

                            if (enteredAmt>Double.parseDouble(paidAmt))
                            {
                                Toast.makeText(context, "Entered amount can not be exceed the Paid amount", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if (Double.parseDouble(new PayModeController(context).getPayModeRemAmtByRefNo(payMode.getFPAYMODE_REF_NO()))>0)
                                {
                                    Double lastRemAmt = Double.parseDouble(new PayModeController(context).getPayModeRemAmtByRefNo(payMode.getFPAYMODE_REF_NO()));
                                    Double currentRemAmt = lastRemAmt + Double.parseDouble(payMode.getFPAYMODE_PAID_ALLOAMT().replaceAll(",",""));
                                    Double finalRemAmt = currentRemAmt - (double)enteredAmt;

                                    new PayModeController(context).updateRemainAmount(payMode.getFPAYMODE_PAID_ID(), String.valueOf(finalRemAmt));
                                    new PayModeController(context).updateAllocateAmt(payMode.getFPAYMODE_PAID_ID(), String.valueOf(enteredAmt));
                                    payMode.setFPAYMODE_PAID_ALLOAMT(String.valueOf(enteredAmt));
                                    payMode.setFPAYMODE_PAID_REMAMT(String.valueOf(finalRemAmt));
                                    holder.allocatedAmt.setText(df.format(enteredAmt));
                                    holder.remAmt.setText(payMode.getFPAYMODE_PAID_REMAMT());

                                }
                            }


                        }
                    });

                    keypad.show();

                    keypad.setHeader("EDIT ALLOCATE AMOUNT");
                    //keypad.loadValue(Double.parseDouble(payMode.getFPAYMODE_PAID_ALLOAMT()));
                }
            }
        });

//        holder.lnStripe.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
//                //updatePaymodeDialogBox(holder, payMode.getFPAYMODE_REF_NO());
//
//                return true;
//            }
//        });

        return row;
    }

    public static class Holder {
        TextView mode;
        TextView date;
        TextView amt;
        TextView remAmt;
        TextView allocatedAmt;
        LinearLayout lnStripe;
        TextView refNo;
    }

    public void updatePaymodeDialogBox(final Holder holder, String paymodeRefNo)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.edit_paymode_dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        final ListView lvPaymodes = (ListView) promptView.findViewById(R.id.lv_paymode_edit_list);

        lvPaymodes.clearTextFilter();

        allPayModeList = new PayModeController(context).getPayModeByRefNo(paymodeRefNo);
        lvPaymodes.setAdapter(new PayModeEditAdapter(context, allPayModeList));

        alertDialogBuilder.setCancelable(false).setNegativeButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                updateData(list);

                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void updateData(ArrayList<PayMode> data) {

        this.data = data;
        notifyDataSetChanged();
    }
}
