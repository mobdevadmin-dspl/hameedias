package com.datamation.hmdsfa.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.BarcodeVarientController;
import com.datamation.hmdsfa.controller.FreeHedController;
import com.datamation.hmdsfa.controller.ItemBundleController;
import com.datamation.hmdsfa.model.FreeHed;
import com.datamation.hmdsfa.model.InvDet;

import java.util.ArrayList;


public class InvoiceAddedItemViewAdapter extends RecyclerView.Adapter<Invoices> {
    Context context;
    ArrayList<InvDet> list;
    ArrayList<FreeHed> arrayList;

    public InvoiceAddedItemViewAdapter(Context context, final ArrayList<InvDet> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Invoices onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewObj = inflater.inflate(R.layout.row_order_details, viewGroup, false);
        return new Invoices(viewObj);
    }

    @Override
    public void onBindViewHolder(@NonNull Invoices holder, int position) {
        final InvDet invoice = list.get(position);
        ItemBundleController ds = new ItemBundleController(context);
        holder.item.setText(invoice.getFINVDET_ITEM_CODE()+" - "+ds.getItemNameByCode(invoice.getFINVDET_ITEM_CODE()));
        holder.Qty.setText(""+invoice.getFINVDET_QTY());
        holder.Amt.setText(invoice.getFINVDET_AMT());
        holder.barcode.setText(invoice.getFINVDET_BARCODE());


        FreeHedController freeHedDS = new FreeHedController(context);
        arrayList = freeHedDS.getFreeIssueItemDetailByRefno(list.get(position).getFINVDET_ITEM_CODE(),"" );

        //if(arrayList.size()>0){
        for(FreeHed freeHed:arrayList){
            int itemQty = (int) Float.parseFloat(freeHed.getFFREEHED_ITEM_QTY());
            int enterQty = (int) Float.parseFloat(list.get(position).getFINVDET_QTY());
            //int enterQty = list.get(position).getFINVDET_QTY();
            if(enterQty<itemQty){
                //other products------this procut has't free items
                holder.showStatus.setBackgroundColor(Color.WHITE);
            }else{
                //free item eligible product
                holder.showStatus.setBackground(context.getResources().getDrawable(R.drawable.ic_free_b));
            }
        }

        holder.Qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomKeypadDialog keypad = new CustomKeypadDialog(context, false
                        , new CustomKeypadDialog.IOnOkClickListener() {
                    @Override
                    public void okClicked(double value) {
                  //      double distrStock = Double.parseDouble(product.getFPRODUCT_QOH());
                        double enteredQty = value;
//                        Log.d("<>+++++", "" + distrStock);
//                        mSharedPref.setHeaderNextClicked("1");
//                        new SharedPref(context).setDiscountClicked("0");
//
//                        if (enteredQty > (int) distrStock) {
//                            preOrders.lblQty.setText("0");
//                            Toast.makeText(context, "Exceeds available  stock"
//                                    , Toast.LENGTH_SHORT).show();
//                        } else {
                        holder.Qty.setText(String.valueOf(enteredQty));

                        invoice.setFINVDET_QTY(holder.Qty.getText().toString());

                        new BarcodeVarientController(context).mUpdateInvoice(invoice.getFINVDET_BARCODE(),invoice.getFINVDET_ITEM_CODE(),invoice.getFINVDET_QTY(),invoice.getFINVDET_PRICE(),invoice.getFINVDET_VARIANTCODE(),invoice.getFINVDET_QOH(),invoice.getFINVDET_ARTICLENO(),invoice.getFINVDET_PRIL_CODE(),Integer.parseInt(invoice.getFINVDET_SEQNO()));

                        //   }

                        //*Change colors*//**//*


                    }
                });

                keypad.show();

                keypad.setHeader("SELECT QUANTITY");
                keypad.loadValue(Double.parseDouble(invoice.getFINVDET_QTY()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class Invoices extends RecyclerView.ViewHolder {
    TextView item, Qty, Amt, barcode, showStatus;

    public Invoices(View row) {
        super(row);
        item = (TextView) row.findViewById(R.id.row_item);
        Qty = (TextView) row.findViewById(R.id.row_cases);
        Amt = (TextView) row.findViewById(R.id.row_piece);
        barcode = (TextView) row.findViewById(R.id.row_pieces);
        showStatus=(TextView)row.findViewById(R.id.row_free_status);
    }



}



