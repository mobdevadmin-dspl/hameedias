package com.datamation.hmdsfa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.BarcodeVarientController;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.FreeHedController;
import com.datamation.hmdsfa.controller.ItemBundleController;
import com.datamation.hmdsfa.controller.SalesPriceController;
import com.datamation.hmdsfa.controller.VATController;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.BarcodenvoiceDet;
import com.datamation.hmdsfa.model.FreeHed;
import com.datamation.hmdsfa.model.InvDet;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;

public class InvDetAdapterNew extends BaseAdapter {
    Context context;
    ArrayList<InvDet> list;
    ArrayList<FreeHed> arrayList;
    private LayoutInflater inflater;
    SharedPref mSharedPref;

    public InvDetAdapterNew(Context context, ArrayList<InvDet> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.mSharedPref = SharedPref.getInstance(context);
        this.list = list;
    }


    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public InvDet getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder;
        final InvDet invoice = getItem(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_order_details, parent, false);
            viewHolder.item = (TextView) convertView.findViewById(R.id.row_item);
            viewHolder.Qty = (TextView) convertView.findViewById(R.id.row_cases);
            viewHolder.Amt = (TextView) convertView.findViewById(R.id.row_piece);
            viewHolder.barcode = (TextView) convertView.findViewById(R.id.row_pieces);
            viewHolder.showStatus = (TextView) convertView.findViewById(R.id.row_free_status);
            //ItemController ds = new ItemController(getContext());
            ItemBundleController ds = new ItemBundleController(context);

            //item.setText(ds.getItemNameByCode(list.get(position).getFINVDET_ITEM_CODE()));
            viewHolder.item.setText(list.get(position).getFINVDET_ITEM_CODE() + " - " + ds.getItemNameByCode(list.get(position).getFINVDET_ITEM_CODE()));
            viewHolder.Qty.setText("" + list.get(position).getFINVDET_QTY());
            viewHolder.Amt.setText(list.get(position).getFINVDET_AMT());
            viewHolder.barcode.setText(list.get(position).getFINVDET_BARCODE());
            FreeHedController freeHedDS = new FreeHedController(context);
            arrayList = freeHedDS.getFreeIssueItemDetailByRefno(list.get(position).getFINVDET_ITEM_CODE(), "");
            for (FreeHed freeHed : arrayList) {
                int itemQty = (int) Float.parseFloat(freeHed.getFFREEHED_ITEM_QTY());
                int enterQty = (int) Float.parseFloat(list.get(position).getFINVDET_QTY());
                //int enterQty = list.get(position).getFINVDET_QTY();

                if (enterQty < itemQty) {
                    //other products------this procut has't free items
                    viewHolder.showStatus.setBackgroundColor(Color.WHITE);
                } else {
                    //free item eligible product
                    viewHolder.showStatus.setBackground(context.getResources().getDrawable(R.drawable.ic_free_b));
                }


            }
            //if(arrayList.size()>0){

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.Qty.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
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
                        viewHolder.Qty.setText(String.valueOf(enteredQty));

                        invoice.setFINVDET_QTY(viewHolder.Qty.getText().toString());
                        String price = new SalesPriceController(context).getPrice(invoice.getFINVDET_ITEM_CODE(), invoice.getFINVDET_VARIANTCODE());
                        double unitprice = Double.parseDouble(price);
                        String taxRevValue = new VATController(context).calculateReverse(mSharedPref.getGlobalVal("KeyVat"),new BigDecimal(price));
                        // unitprice = Double.parseDouble(price) - Double.parseDouble(taxRevValue);


//by rashmi 2020/06/22 according to meeting minute(2020/06/17) point 02
                        if(new CustomerController(context).getCustomerVatStatus(mSharedPref.getSelectedDebCode()).trim().equals("VAT")){
                            unitprice = Double.parseDouble(price) - Double.parseDouble(taxRevValue);

                        }else if(new CustomerController(context).getCustomerVatStatus(mSharedPref.getSelectedDebCode()).trim().equals("NOVAT")){
                            unitprice = Double.parseDouble(price);

                        }
                        double amt = unitprice * Double.parseDouble(viewHolder.Qty.getText().toString());
                        viewHolder.Amt.setText(String.format("%.2f", amt));
                        new BarcodeVarientController(context).mUpdateInvoice(invoice.getFINVDET_BARCODE(), invoice.getFINVDET_ITEM_CODE(), invoice.getFINVDET_QTY(), price, invoice.getFINVDET_VARIANTCODE(), invoice.getFINVDET_QOH(), invoice.getFINVDET_ARTICLENO(), invoice.getFINVDET_PRIL_CODE(), Integer.parseInt(invoice.getFINVDET_SEQNO()));

                        //   }

                        //*Change colors*//**//*


                    }
                });

                keypad.show();

                keypad.setHeader("SELECT QUANTITY");
                keypad.loadValue(Double.parseDouble(invoice.getFINVDET_QTY()));

            }
        });
        return convertView;
    }


        /*}else{
           showStatus.setBackgroundColor(Color.WHITE);
        }*/

}

 class ViewHolder {
    TextView item;
    TextView Qty;
    TextView Amt;
    TextView barcode;
    TextView showStatus;

}

