package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.PreProductController;
import com.datamation.swdsfa.dialog.CustomKeypadDialogCases;
import com.datamation.swdsfa.dialog.CustomKeypadDialogFree;
import com.datamation.swdsfa.model.FreeItemDetails;
import com.datamation.swdsfa.model.ItemFreeIssue;

import java.util.ArrayList;

public class FreeIssueAdapter extends ArrayAdapter<ItemFreeIssue> {
    Context context;
    ArrayList<ItemFreeIssue> list;
    FreeItemDetails itemDetails;

    public FreeIssueAdapter(Context context, ArrayList<ItemFreeIssue> list,FreeItemDetails itemDetails) {

        super(context, R.layout.row_free_items, list);
        this.context = context;
        this.list = list;
        this.itemDetails = itemDetails;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_free_items, parent, false);

        TextView code = (TextView) row.findViewById(R.id.tv_item_code);
        TextView name = (TextView) row.findViewById(R.id.tv_item_name);
        final TextView alloc = (TextView) row.findViewById(R.id.tv_alloc);

        code.setText(list.get(position).getItems().getFITEM_ITEM_CODE());
        name.setText(list.get(position).getItems().getFITEM_ITEM_NAME());
        alloc.setText(list.get(position).getAlloc());

//developed by rashmi
        alloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomKeypadDialogFree keypad = new CustomKeypadDialogFree(context, false, new CustomKeypadDialogFree.IOnOkClickListener() {
                    @Override
                    public void okClicked(double value) {
                        int enterQty = (int) value;


                            int availableQty = 0, allocatedQty = 0, alreadyAllocatedQty = 0;
                            for(ItemFreeIssue free : list){
                                allocatedQty += Integer.parseInt(free.getAlloc());
                            }

                            //Log.d("Free",">>>>>>"+enterQty);

                            if (itemDetails.getFreeQty() > 0 && enterQty<= itemDetails.getFreeQty()) {
//case 1 : free qty > 0 and first time enter qty less than or equal free qty
                                availableQty = itemDetails.getFreeQty() - allocatedQty;
                                if (!alloc.getText().toString().equals("0")) {
//case 2 : if already allocated edit occation
                                    for (ItemFreeIssue free : list) {//case 3 : if already allocated get allocated qty for edit
                                        alreadyAllocatedQty += Integer.parseInt(free.getAlloc());
                                    }
                                    //case 4 : if already allocated get available qty for edit

                                    availableQty = itemDetails.getFreeQty() - alreadyAllocatedQty;

                                    if ((availableQty < 0) || (availableQty < enterQty)) {
                                   //    case 5 : most probably available qty < enterqty
                                        if((enterQty < Integer.parseInt(alloc.getText().toString())) || (enterQty <= (availableQty+Integer.parseInt(alloc.getText().toString())) )){
                                            //    case 6 : previously allocated qty edit when enter qty less than it
                                            //    case 7 : or previously allocated qty edit when enter qty grater than it and enter qty less now actual available qty
                                            //(now actual available qty = availableQty+Integer.parseInt(alloc.getText().toString())) in edit
                                            list.get(position).setAlloc("" + enterQty);
                                            alloc.setText("" + enterQty);
                                            //this if else for give separate messages
                                            if(enterQty < Integer.parseInt(alloc.getText().toString())) {
                                                Toast.makeText(context, "Free Items Left " + (Integer.parseInt(alloc.getText().toString()) - enterQty), Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(context, "Free Items Left " + ((Integer.parseInt(alloc.getText().toString())+availableQty) - enterQty), Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        else{
                                            Toast.makeText(context, "You don't have enough sufficient quantity to order", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if(availableQty == 0){
                                        //    case 8 :
                                        list.get(position).setAlloc("" + enterQty);
                                        alloc.setText("" + enterQty);
                                        Toast.makeText(context, "Free Items Left " + (Integer.parseInt(alloc.getText().toString()) - enterQty), Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        list.get(position).setAlloc("" + enterQty);
                                        alloc.setText("" + enterQty);
                                        Toast.makeText(context, "Free Items Left " + (availableQty - enterQty), Toast.LENGTH_SHORT).show();
                                    }
                                }
//                                else if(){
//
//
//                                }
                                else{// first time allocated free for item
                                    if ((availableQty < 0) || (availableQty<enterQty)) {
                                        Toast.makeText(context, "You don't have enough sufficient quantity to order", Toast.LENGTH_SHORT).show();
                                    } else {
                                        list.get(position).setAlloc("" + enterQty);
                                        alloc.setText(""+enterQty);
                                        Toast.makeText(context, "Free Items Left "+ (availableQty-enterQty), Toast.LENGTH_SHORT).show();
                                    }
                                }



                            }else{
                                Toast.makeText(context, "You don't have enough sufficient quantity to order", Toast.LENGTH_SHORT).show();
                            }
                    }
                });

                keypad.show();

                keypad.setHeader("ALLOCATE FREE");
                keypad.loadValue(Double.parseDouble(list.get(position).getAlloc()));
            }
        });

        return row;
    }
}

