package com.datamation.hmdsfa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.PayModeController;
import com.datamation.hmdsfa.controller.PaymentAllocateController;
import com.datamation.hmdsfa.model.PaymentAllocate;

import java.util.ArrayList;

public class PaymentAllocateAdapter extends ArrayAdapter<PaymentAllocate> {

    Context context;
    ArrayList<PaymentAllocate> list;
    String comRefNo;

    public PaymentAllocateAdapter(Context context, ArrayList<PaymentAllocate> list, String comRefNo ) {
        super(context, R.layout.row_payment_allocate, list);
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

        if (row == null)
        {
            holder = new Holder();

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_payment_allocate, parent, false);


            holder.id = (TextView) row.findViewById(R.id.txtPayModeID);
            holder.mode = (TextView) row.findViewById(R.id.txtPayMode);
            holder.paidAmt = (TextView)row.findViewById(R.id.txtAmount);
            holder.remAmt = (TextView)row.findViewById(R.id.txtRemAmount);
            holder.allocatedAmt = (TextView)row.findViewById(R.id.editAlloAmount);

            row.setTag(holder);
        }
        else
        {
            holder = (Holder)row.getTag();
        }

        holder.mode.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_MODE());
        holder.id.setText(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
        holder.paidAmt.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_AMT());
//        holder.remAmt.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_REM_AMT());
        holder.remAmt.setText(new PayModeController(context).getRemAmtByPayRefNo(new PaymentAllocateController(context).getPayRefNoByAllocRefNo(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO())));
        holder.allocatedAmt.setText(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_ALLO_AMT());

        holder.allocatedAmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final CustomKeypadDialog keypad = new CustomKeypadDialog(context, false, new CustomKeypadDialog.IOnOkClickListener() {
                    @Override
                    public void okClicked(double value)
                    {
                        if ((Double.parseDouble(holder.remAmt.getText().toString())== 0.0) && value>(Double.parseDouble(holder.allocatedAmt.getText().toString())))
                        {
                            Toast.makeText(context, "Unable to update the paid amount.", Toast.LENGTH_LONG).show();
                        }
                        else if ((Double.parseDouble(holder.remAmt.getText().toString())== 0.0) && value<=(Double.parseDouble(holder.allocatedAmt.getText().toString())))
                        {
                            Double payModeUpdatableAmt = Double.parseDouble(holder.allocatedAmt.getText().toString().replaceAll(",",""))- value;

                            String lastAllocRemAmt = new PaymentAllocateController(context).getRemAmtByAllocRefNo(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
                            Double paymentAllocUpdatableRemAmt = Double.parseDouble(lastAllocRemAmt) + payModeUpdatableAmt;

                            new PaymentAllocateController(context).updateAllocAmount(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO(), String.valueOf(value));
                            new PaymentAllocateController(context).updateRemAmount(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO(), String.valueOf(paymentAllocUpdatableRemAmt));

                            //holder.remAmt.setText(String.valueOf(paymentAllocUpdatableRemAmt));
                            holder.allocatedAmt.setText(String.valueOf(value));

                            String payRefNo = new PaymentAllocateController(context).getPayRefNoByAllocRefNo(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());

                            if (!payRefNo.equals(""))
                            {
                                String lastRemAmt = new PayModeController(context).getRemAmtByPayRefNo(payRefNo);

                                if (!lastRemAmt.isEmpty())
                                {
                                    Double newPayModeRemAmt = Double.parseDouble(lastRemAmt) + payModeUpdatableAmt;
                                    holder.remAmt.setText(String.valueOf(newPayModeRemAmt));
                                    new PayModeController(context).updatePayMode(payRefNo, String.valueOf(newPayModeRemAmt));

//                                    ReceiptOrderDetails receiptOrderDetails = new ReceiptOrderDetails();
//                                    receiptOrderDetails.FetchPayModeData();

                                    //UtilityContainer.mLoadFragment(new ReceiptOrderDetails(), context);
                                }
                            }

                            Log.d("PAYMENT_ALLOC_ADAPTER", "PAY_ID_IS: " + payRefNo);
                            Toast.makeText(context, "PAY_ID_IS: " + payRefNo, Toast.LENGTH_LONG).show();
                        }
                        else if ((Double.parseDouble(holder.remAmt.getText().toString().replaceAll(",",""))>0)
                                && value<=((Double.parseDouble(holder.remAmt.getText().toString().replaceAll(",","")))+ Double.parseDouble(holder.allocatedAmt.getText().toString().replaceAll(",",""))))
                        {
                            Double payModeUpdatableAmt = Double.parseDouble(holder.allocatedAmt.getText().toString().replaceAll(",",""))- value;

                            String lastAllocRemAmt = new PaymentAllocateController(context).getRemAmtByAllocRefNo(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
                            Double paymentAllocUpdatableRemAmt = Double.parseDouble(lastAllocRemAmt) + payModeUpdatableAmt;

                            new PaymentAllocateController(context).updateAllocAmount(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO(), String.valueOf(value));
                            new PaymentAllocateController(context).updateRemAmount(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO(), String.valueOf(paymentAllocUpdatableRemAmt));

//                            holder.remAmt.setText(String.valueOf(paymentAllocUpdatableRemAmt));
                            holder.allocatedAmt.setText(String.valueOf(value));

                            String payRefNo = new PaymentAllocateController(context).getPayRefNoByAllocRefNo(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());

                            if (!payRefNo.equals(""))
                            {
                                String lastRemAmt = new PayModeController(context).getRemAmtByPayRefNo(payRefNo);

                                if (!lastRemAmt.isEmpty())
                                {
                                    Double newPayModeRemAmt = Double.parseDouble(lastRemAmt) + payModeUpdatableAmt;
                                    holder.remAmt.setText(String.valueOf(newPayModeRemAmt));
                                    new PayModeController(context).updatePayMode(payRefNo, String.valueOf(newPayModeRemAmt));

//                                    ReceiptOrderDetails receiptOrderDetails = new ReceiptOrderDetails();
//                                    receiptOrderDetails.FetchPayModeData();

                                    //UtilityContainer.mLoadFragment(new ReceiptOrderDetails(), context);
                                }
                            }

                            Log.d("PAYMENT_ALLOC_ADAPTER", "PAY_ID_IS: " + payRefNo);
                            Toast.makeText(context, "PAY_ID_IS: " + payRefNo, Toast.LENGTH_LONG).show();
                        }
                        else if (value>(Double.parseDouble(holder.remAmt.getText().toString().replaceAll(",","")) + Double.parseDouble(holder.allocatedAmt.getText().toString().replaceAll(",",""))))
                        {
                            Toast.makeText(context, "Please enter valid amount.", Toast.LENGTH_LONG).show();
                        }

//                        if (value>Double.parseDouble(holder.paidAmt.getText().toString().replaceAll(",","")))
//                        {
//                            Toast.makeText(context, "Entered amount can not be exceed the Pay Amt.", Toast.LENGTH_LONG).show();
//                        }


//                        else if (value>0 && comRefNo.equalsIgnoreCase(paymentAllocate.getFPAYMENT_ALLOCATE_COMMON_REFNO()))
//                        {
//                            Double payModeUpdatableAmt = Double.parseDouble(holder.allocatedAmt.getText().toString().replaceAll(",",""))- value;
//
//                            String lastAllocRemAmt = new PaymentAllocateDS(context).getRemAmtByAllocRefNo(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
//                            Double paymentAllocUpdatableRemAmt = Double.parseDouble(lastAllocRemAmt) + payModeUpdatableAmt;
//
//                            new PaymentAllocateDS(context).updateAllocAmount(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO(), String.valueOf(value));
//                            new PaymentAllocateDS(context).updateRemAmount(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO(), String.valueOf(paymentAllocUpdatableRemAmt));
//
//                            holder.remAmt.setText(String.valueOf(paymentAllocUpdatableRemAmt));
//                            holder.allocatedAmt.setText(String.valueOf(value));
//
//                            String payRefNo = new PaymentAllocateDS(context).getPayRefNoByAllocRefNo(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
//
//                            if (!payRefNo.equals(""))
//                            {
//                                String lastRemAmt = new PayModeDS(context).getRemAmtByPayRefNo(payRefNo);
//
//                                if (!lastRemAmt.isEmpty())
//                                {
//                                    Double newPayModeRemAmt = Double.parseDouble(lastRemAmt) + payModeUpdatableAmt;
//                                    new PayModeDS(context).updatePayMode(payRefNo, String.valueOf(newPayModeRemAmt));
//
////                                    ReceiptOrderDetails receiptOrderDetails = new ReceiptOrderDetails();
////                                    receiptOrderDetails.FetchPayModeData();
//
//                                    //UtilityContainer.mLoadFragment(new ReceiptOrderDetails(), context);
//                                }
//                            }
//
//                            Log.d("PAYMENT_ALLOC_ADAPTER", "PAY_ID_IS: " + payRefNo);
//                            Toast.makeText(context, "PAY_ID_IS: " + payRefNo, Toast.LENGTH_LONG).show();
//                        }
                    }
                });

                keypad.show();
                keypad.setHeader("EDIT PAID AMOUNT");
                keypad.loadValue(Double.parseDouble(holder.allocatedAmt.getText().toString()));
            }
        });

        return row;
    }

    public static class Holder
    {
        TextView id;
        TextView mode;
        TextView paidAmt;
        TextView remAmt;
        TextView allocatedAmt;
    }
}
