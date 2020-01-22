package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.ProductController;
import com.datamation.swdsfa.model.Product;

import java.util.ArrayList;

public class NewProductAdapter extends ArrayAdapter<Product> {
    Context context;
    ArrayList<Product> list;
    String preText = null;

    public NewProductAdapter(Context context, final ArrayList<Product> list) {
        super(context, R.layout.row_item_listview, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.row_product_item, parent, false);

        TextView itemCode = (TextView) row.findViewById(R.id.row_itemcode);
        TextView ItemName = (TextView) row.findViewById(R.id.row_itemname);
        final TextView Price = (TextView) row.findViewById(R.id.row_price);
        final TextView HoQ = (TextView) row.findViewById(R.id.row_qoh);
        final EditText lblQty = (EditText) row.findViewById(R.id.et_qty);
        final ImageButton btnPlus = (ImageButton) row.findViewById(R.id.btnAddition);
        final ImageButton btnMinus = (ImageButton) row.findViewById(R.id.btnSubtract);
        final LinearLayout lnStripe = (LinearLayout) row.findViewById(R.id.lnProductStripe);

        itemCode.setText(list.get(position).getFPRODUCT_ITEMCODE());
        ItemName.setText(list.get(position).getFPRODUCT_ITEMNAME());
        Price.setText(list.get(position).getFPRODUCT_PRICE());
        HoQ.setText(list.get(position).getFPRODUCT_QOH());
        lblQty.setText(list.get(position).getFPRODUCT_QTY());

        /*Change colors*/
        if (Integer.parseInt(lblQty.getText().toString()) > 0)
            lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
        else
            lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qty = Integer.parseInt(lblQty.getText().toString());

                if (--qty >= 0) {
                    lblQty.setText((Integer.parseInt(lblQty.getText().toString()) - 1) + "");
                    list.get(position).setFPRODUCT_QTY(lblQty.getText().toString());
                    new ProductController(context).updateProductQty(list.get(position).getFPRODUCT_ITEMCODE(), lblQty.getText().toString());
                }

                /*Change colors*/
                if (qty == 0)
                    lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));

            }
        });

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double qty = Double.parseDouble(lblQty.getText().toString());

                lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));

                if (qty <= (Double.parseDouble(HoQ.getText().toString()))) {
                    lblQty.setText((Integer.parseInt(lblQty.getText().toString()) + 1) + "");
                    list.get(position).setFPRODUCT_QTY(lblQty.getText().toString());
                    new ProductController(context).updateProductQty(list.get(position).getFPRODUCT_ITEMCODE(), lblQty.getText().toString());
                }
            }
        });

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        btnPlus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        btnPlus.setBackground(context.getResources().getDrawable(R.drawable.icon_plus_d));
                    }
                    break;

                    case MotionEvent.ACTION_UP: {
                        btnPlus.setBackground(context.getResources().getDrawable(R.drawable.icon_plus));
                    }
                    break;
                }
                return false;
            }
        });

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        btnMinus.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        btnMinus.setBackground(context.getResources().getDrawable(R.drawable.icon_minus_d));
                    }
                    break;

                    case MotionEvent.ACTION_UP: {
                        btnMinus.setBackground(context.getResources().getDrawable(R.drawable.icon_minus));
                    }
                    break;
                }
                return false;
            }
        });

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lblQty.addTextChangedListener(new TextWatcher() {
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
                        lblQty.setText(preText);
                        lblQty.selectAll();
                    } else
                        new ProductController(context).updateProductQty(list.get(position).getFPRODUCT_ITEMCODE(), String.valueOf(enteredQty));

                } else {
                    lblQty.setText("0");
                    lblQty.selectAll();
                }

                /*Change colors*/
                if (Integer.parseInt(lblQty.getText().toString()) > 0)
                    lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
                else
                    lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));

            }
        });

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lblQty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    preText = lblQty.getText().toString();
                else
                    preText = null;
            }
        });

       return row;
    }
}
