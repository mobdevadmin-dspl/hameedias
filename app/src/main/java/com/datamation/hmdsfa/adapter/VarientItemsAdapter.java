package com.datamation.hmdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.model.ItemBundle;
import com.datamation.hmdsfa.model.PreProduct;
import com.datamation.hmdsfa.model.Product;

import java.util.ArrayList;


public class VarientItemsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    Context context;
    ArrayList<PreProduct> list;
    String preText = null;

    public VarientItemsAdapter(Context context, final ArrayList<PreProduct> list) {
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
    public PreProduct getItem(int position) {
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
            convertView =inflater.inflate(R.layout.row_bundle_items,parent,false);

            viewHolder.lnStripe = (LinearLayout) convertView.findViewById(R.id.lnProductStripe);
            viewHolder.itemCode =(TextView)convertView.findViewById(R.id.row_itemcode);
            viewHolder.ItemName =(TextView)convertView.findViewById(R.id.row_itemname);
            viewHolder.Price =(TextView)convertView.findViewById(R.id.row_price);
            viewHolder.HoQ =(TextView)convertView.findViewById(R.id.row_qoh);
            viewHolder.lblQty =(TextView)convertView.findViewById(R.id.et_qty);
            viewHolder.checkScan =(CheckBox) convertView.findViewById(R.id.btnAddition);
            //viewHolder.btnMinus =(ImageButton) convertView.findViewById(R.id.btnSubtract);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
           final  PreProduct product=getItem(position);

        viewHolder.itemCode.setText(product.getPREPRODUCT_ITEMCODE());
        viewHolder.ItemName.setText(product.getPREPRODUCT_VariantCode()+" "+product.getPREPRODUCT_ITEMNAME()+" "+product.getPREPRODUCT_VariantSize()+" "+product.getPREPRODUCT_VariantColour());
        viewHolder.Price.setText(product.getPREPRODUCT_Barcode());
        viewHolder.HoQ.setText(product.getPREPRODUCT_QOH());
        viewHolder.lblQty.setText(""+product.getPREPRODUCT_QTY());
        viewHolder.checkScan.setChecked(true);



        viewHolder.checkScan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
                    //new ProductController(context).updateBarCode(viewHolder.Price.getText().toString(),"1");
                } else {
                    viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));
                   // new ProductController(context).updateBarCode(viewHolder.Price.getText().toString(),"0");
                }

            }
        });

        /*Change colors*/
        if (viewHolder.checkScan.isChecked()){
          //  new ProductController(context).updateBarCode(viewHolder.Price.getText().toString(),"1");
            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
        }else{
            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));
        }
        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int qty = Integer.parseInt(viewHolder.lblQty.getText().toString());
//
//                if (--qty >= 0) {
//                    viewHolder.lblQty.setText((Integer.parseInt(viewHolder.lblQty.getText().toString()) - 1) + "");
//                    list.get(position).setFPRODUCT_QTY(viewHolder.lblQty.getText().toString());
//                    new ProductController(context).updateProductQty(product.getFPRODUCT_ITEMCODE(), viewHolder.lblQty.getText().toString());
//                }
//
//                /*Change colors*/
//                if (qty == 0)
//                    viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));
//
//            }
//        });

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                double qty = Double.parseDouble(viewHolder.lblQty.getText().toString());
//
//                viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
//
//                if (qty < (Double.parseDouble(viewHolder.HoQ.getText().toString()))) {
//                    viewHolder.lblQty.setText((Integer.parseInt(viewHolder.lblQty.getText().toString()) + 1) + "");
//                    product.setFPRODUCT_QTY(viewHolder.lblQty.getText().toString());
//                    new ProductController(context).updateProductQty(product.getFPRODUCT_ITEMCODE(), viewHolder.lblQty.getText().toString());
//                }else{
//                    Toast.makeText(context, "Exceeds available  stock", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/



        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/



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
//        viewHolder.lblQty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CustomKeypadDialog keypad = new CustomKeypadDialog(context, false, new CustomKeypadDialog.IOnOkClickListener() {
//                    @Override
//                    public void okClicked(double value) {
//                        //String distrStock = product.getFPRODUCT_QOH();
//                        double distrStock = Double.parseDouble(product.getFPRODUCT_QOH());
//                        int enteredQty = (int) value;
//                        Log.d("<>+++++","" + distrStock);
//
//                        if (enteredQty > (int)distrStock) {
//                            viewHolder.lblQty.setText("0");
//                            Toast.makeText(context, "Exceeds available  stock", Toast.LENGTH_SHORT).show();
//                        } else {
//                            new ProductController(context).updateProductQty(product.getFPRODUCT_ITEMCODE(), String.valueOf(enteredQty));
//
//                            product.setFPRODUCT_QTY(String.valueOf(enteredQty));
//                            viewHolder.lblQty.setText(product.getFPRODUCT_QTY());
//                        }
//
//
//
//
//                        //*Change colors*//**//*
//                        if (Integer.parseInt(viewHolder.lblQty.getText().toString()) > 0)
//                            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox_new));
//                        else
//                            viewHolder.lnStripe.setBackground(context.getResources().getDrawable(R.drawable.custom_textbox));
//
//                    }
//                });
//
//                keypad.show();
//
//                keypad.setHeader("SELECT QUANTITY");
//                keypad.loadValue(Double.parseDouble(product.getFPRODUCT_QTY()));
//
//
//            }
//        });


        /*-*-*-*-*-*-*--*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

       return convertView;
    }

    private  static  class  ViewHolder{
        LinearLayout lnStripe;
        TextView itemCode;
        TextView ItemName;
        TextView Price;
        TextView HoQ;
        TextView lblQty;
        CheckBox checkScan;
       // ImageButton btnMinus;

    }

}
