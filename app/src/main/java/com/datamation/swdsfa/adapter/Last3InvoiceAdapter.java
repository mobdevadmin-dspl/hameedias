package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.Last3Invoice;

import java.util.ArrayList;

public class Last3InvoiceAdapter extends RecyclerView.Adapter<Last3InvoiceAdapter.MyViewHolder>{

private ArrayList<Last3Invoice> last3InvList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final Context context;
        //TextView
        private TextView tvItemName,tvQty1,tvVal1,
                tvQty2,tvVal2,
                tvQty3,tvVal3,
                tvSummaryQty,tvSummaryVal;


        public MyViewHolder(Context context, View view){
            super(view);
            this.context = context;

            tvItemName = view.findViewById(R.id.tvItemName);
            tvQty1 = view.findViewById(R.id.tvQty1);
            tvVal1 = view.findViewById(R.id.tvVal1);

            tvQty2 = view.findViewById(R.id.tvQty2);
            tvVal2 = view.findViewById(R.id.tvVal2);

            tvQty3 = view.findViewById(R.id.tvQty3);
            tvVal3 = view.findViewById(R.id.tvVal3);

            tvSummaryQty = view.findViewById(R.id.tvSummaryQty);
            tvSummaryVal = view.findViewById(R.id.tvSummaryVal);
        }
    }


    public Last3InvoiceAdapter(ArrayList<Last3Invoice> last3InvList) {
        this.last3InvList = last3InvList;
    }

    @NonNull
    @Override
    public Last3InvoiceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_custom_last_three_invoice, parent, false);

        return new Last3InvoiceAdapter.MyViewHolder(parent.getContext(),itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Last3InvoiceAdapter.MyViewHolder holder, final int position) {

        final Last3Invoice last3Invoice = last3InvList.get(position);

        if(last3Invoice !=null){

            Log.v("position",position+"");

            holder.tvItemName.setText(" "+(position+1) +"-"+last3Invoice.getItemName());
            holder.tvQty1.setText(last3Invoice.getQty1()+"");
            holder.tvVal1.setText(last3Invoice.getVal1()+"");

            holder.tvQty2.setText(last3Invoice.getQty2()+"");
            holder.tvVal2.setText(last3Invoice.getVal2()+"");

            holder.tvQty3.setText(last3Invoice.getQty3()+"");
            holder.tvVal3.setText(last3Invoice.getVal3()+"");


            //Line Total
            holder.tvSummaryQty.setText(last3Invoice.getQty1()+last3Invoice.getQty2()+last3Invoice.getQty3() +"");
            holder.tvSummaryVal.setText(last3Invoice.getVal1()+last3Invoice.getVal2()+last3Invoice.getVal3()+"");

//            holder.tvSummaryVal.setText((Double.parseDouble(last3Invoice.getVal1()) + Double.parseDouble(last3Invoice.getVal2()) + Double.parseDouble(last3Invoice.getVal3())) +"");

        }

    }

    @Override
    public int getItemCount() {
        return last3InvList.size();
    }

}
