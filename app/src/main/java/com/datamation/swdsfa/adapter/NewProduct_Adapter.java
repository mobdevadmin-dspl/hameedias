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
import com.datamation.swdsfa.controller.ProductController;
import com.datamation.swdsfa.dialog.CustomKeypadDialogPrice;
import com.datamation.swdsfa.model.Product;

import java.util.ArrayList;


public class NewProduct_Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    Context context;
    ArrayList<Product> list;
    String preText = null;

    public NewProduct_Adapter(Context context, final ArrayList<Product> list) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list !=null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Product getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView,ViewGroup parent) {
     final ViewHolder viewHolder;

        if(convertView ==null){
            viewHolder =new ViewHolder();
            convertView =inflater.inflate(R.layout.row_product_item,parent,false);

            viewHolder.lnStripe = (LinearLayout) convertView.findViewById(R.id.lnProductStripe);
            viewHolder.itemCode =(TextView)convertView.findViewById(R.id.row_itemcode);
            viewHolder.ItemName =(TextView)convertView.findViewById(R.id.row_itemname);
            viewHolder.Price =(TextView)convertView.findViewById(R.id.row_price);
            viewHolder.HoQ =(TextView)convertView.findViewById(R.id.row_qoh);
            viewHolder.lblQty =(TextView)convertView.findViewById(R.id.et_qty);
            viewHolder.btnPlus =(ImageButton)convertView.findViewById(R.id.btnAddition);
            viewHolder.btnMinus =(ImageButton) convertView.findViewById(R.id.btnSubtract);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
           final  Product product=getItem(position);

        viewHolder.itemCode.setText(product.getFPRODUCT_ITEMCODE());
        viewHolder.ItemName.setText(product.getFPRODUCT_ITEMNAME());
        viewHolder.Price.setText(product.getFPRODUCT_PRICE());
        viewHolder.HoQ.setText(product.getFPRODUCT_QOH());
        viewHolder.lblQty.setText(product.getFPRODUCT_QTY());

        /*Change colors*/
        if (Integer.parseInt(viewHolder.lblQty.getText().toString()) > 0){
            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
        }else{
            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));
        }
        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty = Integer.parseInt(viewHolder.lblQty.getText().toString());

                if (--qty >= 0) {
                    viewHolder.lblQty.setText((Integer.parseInt(viewHolder.lblQty.getText().toString()) - 1) + "");
                    list.get(position).setFPRODUCT_QTY(viewHolder.lblQty.getText().toString());
                    new ProductController(context).updateProductQty(product.getFPRODUCT_ITEMCODE(), viewHolder.lblQty.getText().toString());
                }

                /*Change colors*/
                if (qty == 0)
                    viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));

            }
        });

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double qty = Double.parseDouble(viewHolder.lblQty.getText().toString());

                viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));

                if (qty < (Double.parseDouble(viewHolder.HoQ.getText().toString()))) {
                    viewHolder.lblQty.setText((Integer.parseInt(viewHolder.lblQty.getText().toString()) + 1) + "");
                    product.setFPRODUCT_QTY(viewHolder.lblQty.getText().toString());
                    new ProductController(context).updateProductQty(product.getFPRODUCT_ITEMCODE(), viewHolder.lblQty.getText().toString());
                }else{
                    Toast.makeText(context, "Exceeds available  stock", Toast.LENGTH_SHORT).show();
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

        /*viewHolder.lblQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    int enteredQty = Integer.parseInt(s.toString());

                    if (enteredQty > Integer.parseInt(list.get(position).getFPRODUCT_QOH())) {
                        Toast.makeText(context, "Quantity exceeds QOH !", Toast.LENGTH_SHORT).show();
                        viewHolder.lblQty.setText(preText);

                    } else
                        new ProductDS(context).updateProductQty(list.get(position).getFPRODUCT_ITEMCODE(), String.valueOf(enteredQty));

                } else {
                    viewHolder.lblQty.setText("0");

                }

                *//*Change colors*//*
                if (Integer.parseInt(viewHolder.lblQty.getText().toString()) > 0)
                    viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
                else
                    viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));

            }
        });*/
        //--------------------------------------------------------------------------------------------------------------------------
        viewHolder.lblQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomKeypadDialog keypad = new CustomKeypadDialog(context, false, new CustomKeypadDialog.IOnOkClickListener() {
                    @Override
                    public void okClicked(double value) {
                        //String distrStock = product.getFPRODUCT_QOH();
                        double distrStock = Double.parseDouble(product.getFPRODUCT_QOH());
                        int enteredQty = (int) value;
                        Log.d("<>+++++","" + distrStock);

                        if (enteredQty > (int)distrStock) {
                            viewHolder.lblQty.setText("0");
                            Toast.makeText(context, "Exceeds available  stock", Toast.LENGTH_SHORT).show();
                        } else {
                            new ProductController(context).updateProductQty(product.getFPRODUCT_ITEMCODE(), String.valueOf(enteredQty));

                            product.setFPRODUCT_QTY(String.valueOf(enteredQty));
                            viewHolder.lblQty.setText(product.getFPRODUCT_QTY());
                        }




                        //*Change colors*//**//*
                        if (Integer.parseInt(viewHolder.lblQty.getText().toString()) > 0)
                            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
                        else
                            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));

                    }
                });

                keypad.show();

                keypad.setHeader("SELECT QUANTITY");
                keypad.loadValue(Double.parseDouble(product.getFPRODUCT_QTY()));


            }
        });


        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
        viewHolder.Price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Rashmi - 2018-10-22
                final double minPrice,maxPrice,price;
                if(!product.getFPRODUCT_PRICE().equals("")) {
                    price = Double.parseDouble(product.getFPRODUCT_PRICE());
                }else{
                    price = 0.0;
                }
                if(!product.getFPRODUCT_MIN_PRICE().equals("")) {
                    minPrice = Double.parseDouble(product.getFPRODUCT_MIN_PRICE());
                }else{
                    minPrice = 0.0;
                }
                if(!product.getFPRODUCT_MAX_PRICE().equals("")){
                    maxPrice = Double.parseDouble(product.getFPRODUCT_MAX_PRICE());
                }else{
                    maxPrice = 0.0;
                }


//                if(minPrice<=price || price<=maxPrice) {
                    CustomKeypadDialogPrice keypadPrice = new CustomKeypadDialogPrice(context, true, new CustomKeypadDialogPrice.IOnOkClickListener() {
                        @Override
                        public void okClicked(double value) {
                            //price cannot be changed less than gross profit
                            if(minPrice <=value && value <= maxPrice) {
                                //  save changed price
                                new ProductController(context).updateProductPrice(product.getFPRODUCT_ITEMCODE(), String.valueOf(value),product.getFPRODUCT_TXN_TYPE());
                                //  value should be set for another variable in preProduct
                                //  preProduct.setPREPRODUCT_PRICE(String.valueOf(value));
                                product.setFPRODUCT_CHANGED_PRICE(String.valueOf(value));
                                viewHolder.Price.setText(product.getFPRODUCT_CHANGED_PRICE());
                            }else{
                                Toast.makeText(context,"Price cannot be change..",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    keypadPrice.show();

                    keypadPrice.setHeader("CHANGE PRICE");
//                if(preProduct.getPREPRODUCT_CHANGED_PRICE().equals("0")){
                    keypadPrice.loadValue(Double.parseDouble(product.getFPRODUCT_PRICE()));
//                }else {
//                    keypadPrice.loadValue(Double.parseDouble(preProduct.getPREPRODUCT_CHANGED_PRICE()));
//                }
//                }else{
//                    Toast.makeText(context,"Price cannot be change..",Toast.LENGTH_LONG).show();
//                }
            }
        });
       /* viewHolder.lblQty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    preText = viewHolder.lblQty.getText().toString();
                else
                    preText = null;
            }
        });
*/
       return convertView;
    }

    private  static  class  ViewHolder{
        LinearLayout lnStripe;
        TextView itemCode;
        TextView ItemName;
        TextView Price;
        TextView HoQ;
        TextView lblQty;
        ImageButton btnPlus;
        ImageButton btnMinus;

    }

}
