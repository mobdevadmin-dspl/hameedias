package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.PreProductController;
import com.datamation.swdsfa.controller.ProductController;
import com.datamation.swdsfa.controller.SalRepController;
import com.datamation.swdsfa.dialog.CustomKeypadDialogCases;
import com.datamation.swdsfa.dialog.CustomKeypadDialogPrice;
import com.datamation.swdsfa.model.PreProduct;
import com.datamation.swdsfa.model.Product;

import java.util.ArrayList;

public class PreOrderAdapter extends BaseAdapter
{
    private LayoutInflater inflater;
    Context context;
    ArrayList<PreProduct> list;
    String type, reason;
    String preText = null;

    public PreOrderAdapter(Context context, final ArrayList<PreProduct> list, String type, String reason) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        this.type = type;
        this.reason = reason;
    }

    @Override
    public int getCount() {
        if(list !=null){
            return list.size();
        }
        return 0;
    }

    @Override
    public PreProduct getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PreOrderAdapter.ViewHolder viewHolder;

        if(convertView ==null){
            viewHolder =new PreOrderAdapter.ViewHolder();
            convertView =inflater.inflate(R.layout.row_product_item_responsive_layout,parent,false);

            viewHolder.lnStripe = (LinearLayout) convertView.findViewById(R.id.lnProductStripe);
            viewHolder.itemCode =(TextView)convertView.findViewById(R.id.row_itemcode);
            viewHolder.unit =(TextView)convertView.findViewById(R.id.row_unit);
            viewHolder.ItemName =(TextView)convertView.findViewById(R.id.row_itemname);
            viewHolder.Price =(TextView)convertView.findViewById(R.id.row_price);
            viewHolder.HoQ =(TextView)convertView.findViewById(R.id.row_qoh);
            viewHolder.lblQty =(TextView)convertView.findViewById(R.id.et_qty);
            viewHolder.lblCase =(TextView)convertView.findViewById(R.id.et_case);
            viewHolder.btnPlus =(ImageButton)convertView.findViewById(R.id.btnAddition);
            viewHolder.btnMinus =(ImageButton) convertView.findViewById(R.id.btnSubtract);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (PreOrderAdapter.ViewHolder) convertView.getTag();
        }
        final  PreProduct product=getItem(position);

        viewHolder.itemCode.setText(product.getPREPRODUCT_ITEMCODE());
        viewHolder.unit.setText("Units("+product.getPREPRODUCT_UNIT()+")");
        viewHolder.ItemName.setText(product.getPREPRODUCT_ITEMNAME());
        viewHolder.Price.setText(product.getPREPRODUCT_PRICE());
        viewHolder.HoQ.setText(product.getPREPRODUCT_QOH());
        viewHolder.lblQty.setText(product.getPREPRODUCT_QTY());
        viewHolder.lblCase.setText(product.getPREPRODUCT_CASE());

        /*Change colors*/
        if (Integer.parseInt(viewHolder.lblQty.getText().toString())> 0 || Integer.parseInt(viewHolder.lblCase.getText().toString()) > 0)
            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
        else
            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty = Integer.parseInt(viewHolder.lblQty.getText().toString());
                int cases = Integer.parseInt(viewHolder.lblCase.getText().toString());
                qty = qty + (cases*Integer.parseInt(product.getPREPRODUCT_UNIT()));
                if (--qty > 0) {

                    qty = qty - 1;

                    cases = qty / (Integer.parseInt(product.getPREPRODUCT_UNIT()));
                    qty = qty % (Integer.parseInt(product.getPREPRODUCT_UNIT()));
                    viewHolder.lblQty.setText(qty + "");
                    viewHolder.lblCase.setText(cases + "");

                    //new PreProductController(context).updateBalQty(product.getPREPRODUCT_ITEMCODE(), ""+(qty-1));
                    list.get(position).setPREPRODUCT_QTY(viewHolder.lblQty.getText().toString());
                    list.get(position).setPREPRODUCT_CASE(viewHolder.lblCase.getText().toString());
                    new PreProductController(context).updateProductQty(product.getPREPRODUCT_ITEMCODE(), viewHolder.lblQty.getText().toString(),product.getPREPRODUCT_TXN_TYPE());
                    new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), viewHolder.lblCase.getText().toString(),product.getPREPRODUCT_TXN_TYPE());

                }else{
                    Toast.makeText(context, "Cannot allow minus values", Toast.LENGTH_SHORT).show();
                    qty = 0;
                }
                /*Change colors*/
                if (qty == 0 && cases == 0) {
                    viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));
                    new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), "",product.getPREPRODUCT_TXN_TYPE());
                }

            }
        });

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty = Integer.parseInt(viewHolder.lblQty.getText().toString());
                int cases = Integer.parseInt(viewHolder.lblCase.getText().toString());
                qty = qty + (cases*Integer.parseInt(product.getPREPRODUCT_UNIT()));

                viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));

//                qty = qty + 1;
//                cases = (qty)/Integer.parseInt(product.getPREPRODUCT_UNIT());
//                qty = (qty) % Integer.parseInt(product.getPREPRODUCT_UNIT());
//                viewHolder.lblQty.setText(qty + "");
//                viewHolder.lblCase.setText(cases + "");
//                product.setPREPRODUCT_QTY(viewHolder.lblQty.getText().toString());
//                product.setPREPRODUCT_CASE(viewHolder.lblCase.getText().toString());
//                new PreProductController(context).updateProductQty(product.getPREPRODUCT_ITEMCODE(), viewHolder.lblQty.getText().toString(),product.getPREPRODUCT_TXN_TYPE());
//                new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), viewHolder.lblCase.getText().toString(),product.getPREPRODUCT_TXN_TYPE());
//                if(product.getPREPRODUCT_TXN_TYPE().equals("MR")){
//                    new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason,product.getPREPRODUCT_TXN_TYPE());
//                }else{
//                    new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason,product.getPREPRODUCT_TXN_TYPE());
//                }

                if(product.getPREPRODUCT_TXN_TYPE().equals("SA") || product.getPREPRODUCT_TXN_TYPE().equals("FD") || product.getPREPRODUCT_TXN_TYPE().equals("FI")) {
//check is validation flag 21-01-2020
                    if(new SalRepController(context).isFireQohExdValidation().equals("1")) {
                        if (qty < (Integer.parseInt(viewHolder.HoQ.getText().toString()))) {
                            qty = qty + 1;
                            cases = (qty) / Integer.parseInt(product.getPREPRODUCT_UNIT());
                            qty = (qty) % Integer.parseInt(product.getPREPRODUCT_UNIT());
                            viewHolder.lblQty.setText(qty + "");
                            viewHolder.lblCase.setText(cases + "");
                            product.setPREPRODUCT_QTY(viewHolder.lblQty.getText().toString());
                            product.setPREPRODUCT_CASE(viewHolder.lblCase.getText().toString());
                            new PreProductController(context).updateProductQty(product.getPREPRODUCT_ITEMCODE(), viewHolder.lblQty.getText().toString(), product.getPREPRODUCT_TXN_TYPE());
                            new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), viewHolder.lblCase.getText().toString(), product.getPREPRODUCT_TXN_TYPE());
                        } else {
                            Toast.makeText(context, "Exceeds available  stock", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        qty = qty + 1;
                        cases = (qty) / Integer.parseInt(product.getPREPRODUCT_UNIT());
                        qty = (qty) % Integer.parseInt(product.getPREPRODUCT_UNIT());
                        viewHolder.lblQty.setText(qty + "");
                        viewHolder.lblCase.setText(cases + "");
                        product.setPREPRODUCT_QTY(viewHolder.lblQty.getText().toString());
                        product.setPREPRODUCT_CASE(viewHolder.lblCase.getText().toString());
                        new PreProductController(context).updateProductQty(product.getPREPRODUCT_ITEMCODE(), viewHolder.lblQty.getText().toString(), product.getPREPRODUCT_TXN_TYPE());
                        new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), viewHolder.lblCase.getText().toString(), product.getPREPRODUCT_TXN_TYPE());

                    }
                }else{
                    qty = qty + 1;
                    cases = (qty)/Integer.parseInt(product.getPREPRODUCT_UNIT());
                    qty = (qty) % Integer.parseInt(product.getPREPRODUCT_UNIT());
                    viewHolder.lblQty.setText(qty + "");
                    viewHolder.lblCase.setText(cases + "");
                    product.setPREPRODUCT_QTY(viewHolder.lblQty.getText().toString());
                    product.setPREPRODUCT_CASE(viewHolder.lblCase.getText().toString());
                    new PreProductController(context).updateProductQty(product.getPREPRODUCT_ITEMCODE(), viewHolder.lblQty.getText().toString(),product.getPREPRODUCT_TXN_TYPE());
                    new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), viewHolder.lblCase.getText().toString(),product.getPREPRODUCT_TXN_TYPE());
                    if(product.getPREPRODUCT_TXN_TYPE().equals("MR")){
                        new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason,product.getPREPRODUCT_TXN_TYPE());
                    }else{
                        new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason,product.getPREPRODUCT_TXN_TYPE());
                    }

                }
            }
        });

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        viewHolder.btnPlus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        viewHolder.btnPlus.setBackground(context.getResources().getDrawable(R.drawable.icon_plus_d));
                    }
                    break;

                    case MotionEvent.ACTION_UP: {
                        viewHolder.btnPlus.setBackground(context.getResources().getDrawable(R.drawable.icon_plus));
                    }
                    break;
                }
                return false;
            }
        });

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        viewHolder.btnMinus.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        viewHolder.btnMinus.setBackground(context.getResources().getDrawable(R.drawable.icon_minus_d));
                    }
                    break;

                    case MotionEvent.ACTION_UP: {
                        viewHolder.btnMinus.setBackground(context.getResources().getDrawable(R.drawable.icon_minus));
                    }
                    break;
                }
                return false;
            }
        });

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/


        //--------------------------------------------------------------------------------------------------------------------------
        viewHolder.lblQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomKeypadDialog keypad = new CustomKeypadDialog(context, false, new CustomKeypadDialog.IOnOkClickListener() {
                    @Override
                    public void okClicked(double value) {
                        //String distrStock = product.getFPRODUCT_QOH();
                        double distrStock = Double.parseDouble(product.getPREPRODUCT_QOH());
                        int enteredQty = (int) value;
                        Log.d("<>+++++","" + distrStock);

                        //////// Log.d("***", "okClicked: "+enteredQty +" \n"+product.getPREPRODUCT_UNIT());
//                        int cases =  enteredQty / Integer.parseInt(product.getPREPRODUCT_UNIT());
//                        //new PreProductController(context).updateBalQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(Integer.parseInt(product.getPREPRODUCT_QTY())+(Integer.parseInt(product.getPREPRODUCT_CASE())*Integer.parseInt(product.getPREPRODUCT_UNIT()))));
//                        enteredQty = enteredQty % Integer.parseInt(product.getPREPRODUCT_UNIT());
//                        cases = cases + Integer.parseInt(viewHolder.lblCase.getText().toString());
//                        new PreProductController(context).updateProductQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(enteredQty),product.getPREPRODUCT_TXN_TYPE());
//                        new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), String.valueOf(cases), product.getPREPRODUCT_TXN_TYPE());
//
//                        product.setPREPRODUCT_QTY(String.valueOf(enteredQty));
//                        product.setPREPRODUCT_CASE(String.valueOf(cases));
//                        viewHolder.lblQty.setText(product.getPREPRODUCT_QTY());
//                        viewHolder.lblCase.setText(product.getPREPRODUCT_CASE());
//                        if(product.getPREPRODUCT_TXN_TYPE().equals("MR")){
//                            new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason,product.getPREPRODUCT_TXN_TYPE());
//                        }else{
//                            new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason,product.getPREPRODUCT_TXN_TYPE());
//                        }

                        if(product.getPREPRODUCT_TXN_TYPE().equals("SA") || product.getPREPRODUCT_TXN_TYPE().equals("FD") || product.getPREPRODUCT_TXN_TYPE().equals("FI")) {

                            if(new SalRepController(context).isFireQohExdValidation().equals("1")) {
                                if (enteredQty > (int) distrStock) {
                                    viewHolder.lblQty.setText("0");
                                    Toast.makeText(context, "Exceeds available  stock", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Log.d("***", "okClicked: "+enteredQty +" \n"+product.getPREPRODUCT_UNIT());
                                    int cases = enteredQty / Integer.parseInt(product.getPREPRODUCT_UNIT());
                                    //new PreProductController(context).updateBalQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(Integer.parseInt(product.getPREPRODUCT_QTY())+(Integer.parseInt(product.getPREPRODUCT_CASE())*Integer.parseInt(product.getPREPRODUCT_UNIT()))));
                                    enteredQty = enteredQty % Integer.parseInt(product.getPREPRODUCT_UNIT());
                                    cases = cases + Integer.parseInt(viewHolder.lblCase.getText().toString());
                                    new PreProductController(context).updateProductQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(enteredQty), product.getPREPRODUCT_TXN_TYPE());
                                    new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), String.valueOf(cases), product.getPREPRODUCT_TXN_TYPE());

                                    product.setPREPRODUCT_QTY(String.valueOf(enteredQty));
                                    product.setPREPRODUCT_CASE(String.valueOf(cases));
                                    viewHolder.lblQty.setText(product.getPREPRODUCT_QTY());
                                    viewHolder.lblCase.setText(product.getPREPRODUCT_CASE());

                                    //new PreProductController(context).updateBalQty(product.getPREPRODUCT_ITEMCODE(), ""+Integer.parseInt(product.getPREPRODUCT_QTY())+(Integer.parseInt(product.getPREPRODUCT_CASE())*Integer.parseInt(product.getPREPRODUCT_UNIT())));
                                }
                            }else{
                                int cases = enteredQty / Integer.parseInt(product.getPREPRODUCT_UNIT());
                                //new PreProductController(context).updateBalQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(Integer.parseInt(product.getPREPRODUCT_QTY())+(Integer.parseInt(product.getPREPRODUCT_CASE())*Integer.parseInt(product.getPREPRODUCT_UNIT()))));
                                enteredQty = enteredQty % Integer.parseInt(product.getPREPRODUCT_UNIT());
                                cases = cases + Integer.parseInt(viewHolder.lblCase.getText().toString());
                                new PreProductController(context).updateProductQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(enteredQty), product.getPREPRODUCT_TXN_TYPE());
                                new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), String.valueOf(cases), product.getPREPRODUCT_TXN_TYPE());

                                product.setPREPRODUCT_QTY(String.valueOf(enteredQty));
                                product.setPREPRODUCT_CASE(String.valueOf(cases));
                                viewHolder.lblQty.setText(product.getPREPRODUCT_QTY());
                                viewHolder.lblCase.setText(product.getPREPRODUCT_CASE());
                            }
                        }else{

                                // Log.d("***", "okClicked: "+enteredQty +" \n"+product.getPREPRODUCT_UNIT());
                                int cases =  enteredQty / Integer.parseInt(product.getPREPRODUCT_UNIT());
                                //new PreProductController(context).updateBalQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(Integer.parseInt(product.getPREPRODUCT_QTY())+(Integer.parseInt(product.getPREPRODUCT_CASE())*Integer.parseInt(product.getPREPRODUCT_UNIT()))));
                                enteredQty = enteredQty % Integer.parseInt(product.getPREPRODUCT_UNIT());
                                cases = cases + Integer.parseInt(viewHolder.lblCase.getText().toString());
                                new PreProductController(context).updateProductQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(enteredQty),product.getPREPRODUCT_TXN_TYPE());
                                new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), String.valueOf(cases), product.getPREPRODUCT_TXN_TYPE());

                                product.setPREPRODUCT_QTY(String.valueOf(enteredQty));
                                product.setPREPRODUCT_CASE(String.valueOf(cases));
                                viewHolder.lblQty.setText(product.getPREPRODUCT_QTY());
                                viewHolder.lblCase.setText(product.getPREPRODUCT_CASE());
                                if(product.getPREPRODUCT_TXN_TYPE().equals("MR")){
                                    new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason,product.getPREPRODUCT_TXN_TYPE());
                                }else{
                                    new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason,product.getPREPRODUCT_TXN_TYPE());
                                }
                                //new PreProductController(context).updateBalQty(product.getPREPRODUCT_ITEMCODE(), ""+Integer.parseInt(product.getPREPRODUCT_QTY())+(Integer.parseInt(product.getPREPRODUCT_CASE())*Integer.parseInt(product.getPREPRODUCT_UNIT())));

                        }
                        //*Change colors*//**//*
                        if (Integer.parseInt(viewHolder.lblQty.getText().toString())> 0 || Integer.parseInt(viewHolder.lblCase.getText().toString()) > 0) {
                            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
                        }else {
                            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));
                            new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), "",product.getPREPRODUCT_TXN_TYPE());
                        }

                    }
                });

                keypad.show();

                keypad.setHeader("SELECT QUANTITY");
                keypad.loadValue(Double.parseDouble(product.getPREPRODUCT_QTY()));
                if (Integer.parseInt(viewHolder.lblQty.getText().toString())> 0 || Integer.parseInt(viewHolder.lblCase.getText().toString()) > 0) {
                    viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
                }else {
                    viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));
                    new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), "",product.getPREPRODUCT_TXN_TYPE());
                }

            }
        });

        viewHolder.Price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product.getPREPRODUCT_TXN_TYPE().equals("MR")){
                    CustomKeypadDialogPrice keypadPrice = new CustomKeypadDialogPrice(context, true, new CustomKeypadDialogPrice.IOnOkClickListener() {
                        @Override
                        public void okClicked(double value) {
                            //price cannot be changed less than gross profit
                            if((Double.parseDouble(product.getPREPRODUCT_PRICE())/Double.parseDouble(product.getPREPRODUCT_UNIT())) >= value) {
                                //  save changed price
                                new ProductController(context).updateProductPrice(product.getPREPRODUCT_ITEMCODE(), String.valueOf(value),"MR");
                                //  value should be set for another variable in preProduct
                                //  preProduct.setPREPRODUCT_PRICE(String.valueOf(value));
                                product.setPREPRODUCT_CHANGED_PRICE(String.valueOf(value));
                                viewHolder.Price.setText(product.getPREPRODUCT_PRICE());
                            }else{
                                Toast.makeText(context,"Price cannot be change..",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    keypadPrice.show();

                    keypadPrice.setHeader("CHANGE PRICE");
                if(product.getPREPRODUCT_CHANGED_PRICE().equals("0")) {
                    keypadPrice.loadValue(Double.parseDouble(product.getPREPRODUCT_PRICE()) / Double.parseDouble(product.getPREPRODUCT_UNIT()));
                }else{
                    keypadPrice.loadValue(Double.parseDouble(product.getPREPRODUCT_CHANGED_PRICE()));
                }
                }else{
                    Toast.makeText(context, "Does not allow to change price", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.lblCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomKeypadDialogCases keypad = new CustomKeypadDialogCases(context, false, new CustomKeypadDialogCases.IOnOkClickListener() {
                    @Override
                    public void okClicked(double value) {
                        //String distrStock = product.getFPRODUCT_QOH();
                        double distrStock = Double.parseDouble(product.getPREPRODUCT_QOH());
                        int enteredQty = (int) value;
                        int units = Integer.parseInt(product.getPREPRODUCT_UNIT());
                        Log.d("<>+++++","" + distrStock);

//                        product.setPREPRODUCT_CASE(String.valueOf(enteredQty));
//                        viewHolder.lblQty.setText(product.getPREPRODUCT_QTY());
//                        viewHolder.lblCase.setText(product.getPREPRODUCT_CASE());
//                        // new PreProductController(context).updateBalQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(Integer.parseInt(product.getPREPRODUCT_QTY())+(Integer.parseInt(product.getPREPRODUCT_CASE())*Integer.parseInt(product.getPREPRODUCT_UNIT()))));
//                        new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), product.getPREPRODUCT_CASE(), product.getPREPRODUCT_TXN_TYPE());
//                        Log.d("DEBUG1>> In OK click", product.getPREPRODUCT_CASE());
//                        if (product.getPREPRODUCT_TXN_TYPE().equals("MR")) {
//                            new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason, product.getPREPRODUCT_TXN_TYPE());
//                        } else {
//                            new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason, product.getPREPRODUCT_TXN_TYPE());
//                        }


                        if(product.getPREPRODUCT_TXN_TYPE().equals("SA") || product.getPREPRODUCT_TXN_TYPE().equals("FD") || product.getPREPRODUCT_TXN_TYPE().equals("FI")) {

                            if(new SalRepController(context).isFireQohExdValidation().equals("1")) {
                                if ((enteredQty * units) > (int) distrStock) {
                                    // viewHolder.lblQty.setText("0");
                                    viewHolder.lblCase.setText("0");
                                    Toast.makeText(context, "Exceeds available  stock", Toast.LENGTH_SHORT).show();
                                } else {

                                    // new PreProductController(context).updateProductQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(enteredQty));


                                    product.setPREPRODUCT_CASE(String.valueOf(enteredQty));
                                    viewHolder.lblQty.setText(product.getPREPRODUCT_QTY());
                                    viewHolder.lblCase.setText(product.getPREPRODUCT_CASE());
                                    // new PreProductController(context).updateBalQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(Integer.parseInt(product.getPREPRODUCT_QTY())+(Integer.parseInt(product.getPREPRODUCT_CASE())*Integer.parseInt(product.getPREPRODUCT_UNIT()))));
                                    new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), product.getPREPRODUCT_CASE(), product.getPREPRODUCT_TXN_TYPE());
                                    Log.d("DEBUG1>> In OK click", product.getPREPRODUCT_CASE());
                                    if (product.getPREPRODUCT_TXN_TYPE().equals("MR")) {
                                        new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason, product.getPREPRODUCT_TXN_TYPE());
                                    } else {
                                        new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason, product.getPREPRODUCT_TXN_TYPE());
                                    }

                                }
                            }else{
                                product.setPREPRODUCT_CASE(String.valueOf(enteredQty));
                                viewHolder.lblQty.setText(product.getPREPRODUCT_QTY());
                                viewHolder.lblCase.setText(product.getPREPRODUCT_CASE());
                                // new PreProductController(context).updateBalQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(Integer.parseInt(product.getPREPRODUCT_QTY())+(Integer.parseInt(product.getPREPRODUCT_CASE())*Integer.parseInt(product.getPREPRODUCT_UNIT()))));
                                new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), product.getPREPRODUCT_CASE(), product.getPREPRODUCT_TXN_TYPE());
                                Log.d("DEBUG1>> In OK click", product.getPREPRODUCT_CASE());
                                if (product.getPREPRODUCT_TXN_TYPE().equals("MR")) {
                                    new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason, product.getPREPRODUCT_TXN_TYPE());
                                } else {
                                    new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), reason, product.getPREPRODUCT_TXN_TYPE());
                                }
                            }
                        }else{
                            product.setPREPRODUCT_CASE(String.valueOf(enteredQty));
                            viewHolder.lblQty.setText(product.getPREPRODUCT_QTY());
                            viewHolder.lblCase.setText(product.getPREPRODUCT_CASE());
                            // new PreProductController(context).updateBalQty(product.getPREPRODUCT_ITEMCODE(), String.valueOf(Integer.parseInt(product.getPREPRODUCT_QTY())+(Integer.parseInt(product.getPREPRODUCT_CASE())*Integer.parseInt(product.getPREPRODUCT_UNIT()))));
                            new PreProductController(context).updateProductCase(product.getPREPRODUCT_ITEMCODE(), product.getPREPRODUCT_CASE(), product.getPREPRODUCT_TXN_TYPE());
                            Log.d("DEBUG1>> In OK click", product.getPREPRODUCT_CASE());

                        }
                        //*Change colors*//**//*
                        if (Integer.parseInt(viewHolder.lblQty.getText().toString())> 0 || Integer.parseInt(viewHolder.lblCase.getText().toString()) > 0) {
                            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
                        }else {
                            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));
                            new PreProductController(context).updateReason(product.getPREPRODUCT_ITEMCODE(), "",product.getPREPRODUCT_TXN_TYPE());
                        }
                    }
                });

                keypad.show();

                keypad.setHeader("SELECT CASES");
                keypad.loadValue(Double.parseDouble(product.getPREPRODUCT_CASE()));
            }
        });
        return convertView;
    }

    private  static  class  ViewHolder{
        LinearLayout lnStripe;
        TextView itemCode;
        TextView unit;
        TextView ItemName;
        TextView Price;
        TextView HoQ;
        TextView lblQty;
        TextView lblCase;
        ImageButton btnPlus;
        ImageButton btnMinus;

    }
}
