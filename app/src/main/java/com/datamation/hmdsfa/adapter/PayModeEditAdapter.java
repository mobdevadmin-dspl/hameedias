package com.datamation.hmdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.PayModeController;
import com.datamation.hmdsfa.model.PayMode;

import java.util.ArrayList;

public class PayModeEditAdapter extends ArrayAdapter<PayMode> {

    Context context;
    ArrayList<PayMode> list;


    public PayModeEditAdapter(Context context, ArrayList<PayMode> list) {
        super(context, R.layout.row_pay_mode, list);
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

        holder.mode.setText(payMode.getFPAYMODE_PAID_TYPE());
        holder.date.setText(payMode.getFPAYMODE_PAID_DATE());
        holder.amt.setText(payMode.getFPAYMODE_PAID_AMOUNT());
        holder.refNo.setText(payMode.getFPAYMODE_REF_NO());

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
            holder.allocatedAmt.setText(payMode.getFPAYMODE_PAID_ALLOAMT());
        }

        holder.allocatedAmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    final CustomKeypadDialog keypad = new CustomKeypadDialog(context, false, new CustomKeypadDialog.IOnOkClickListener() {
                        @Override
                        public void okClicked(double value) {
                            //String distrStock = preProduct.getPREPRODUCT_QOH();
                            int enteredAmt = (int) value;

                            String amt = payMode.getFPAYMODE_PAID_AMOUNT().replaceAll(",","");

                            if (enteredAmt>Double.parseDouble(amt))
                            {
                                Toast.makeText(context, "Entered amount can not be exceed the Paid amount", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Double remAmt = Double.parseDouble(amt)- Double.parseDouble(String.valueOf(enteredAmt));
                                new PayModeController(context).updateRemainAmount(payMode.getFPAYMODE_PAID_ID(), String.valueOf(remAmt));
                                new PayModeController(context).updateAllocateAmt(payMode.getFPAYMODE_PAID_ID(), String.valueOf(enteredAmt));
                                payMode.setFPAYMODE_PAID_ALLOAMT(String.valueOf(enteredAmt));
                                payMode.setFPAYMODE_PAID_REMAMT(String.valueOf(remAmt));
                                holder.allocatedAmt.setText(payMode.getFPAYMODE_PAID_ALLOAMT());
                                holder.remAmt.setText(payMode.getFPAYMODE_PAID_REMAMT());

//                                PayModeAdapter adapter = new PayModeAdapter(context, list, false);
//                                adapter.updateData(list);


//                                //*Change colors*//**//*
//                                if (Double.parseDouble(holder.allocatedAmt.getText().toString()) > 0)
//                                    holder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
//                                else
//                                    holder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));

                            }

                        }
                    });

                    keypad.show();

                    keypad.setHeader("SELECT PAID AMOUNT");
                    //keypad.loadValue(Double.parseDouble(payMode.getFPAYMODE_PAID_ALLOAMT()));

            }
        });

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
}
