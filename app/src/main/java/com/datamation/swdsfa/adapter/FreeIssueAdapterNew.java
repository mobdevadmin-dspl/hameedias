package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.dialog.CustomKeypadDialogFree;
import com.datamation.swdsfa.model.ItemFreeIssue;

import java.util.ArrayList;

public class FreeIssueAdapterNew extends ArrayAdapter<ItemFreeIssue> {
    Context context;
    ArrayList<ItemFreeIssue> list;

    public FreeIssueAdapterNew(Context context, ArrayList<ItemFreeIssue> list) {

        super(context, R.layout.row_free_items, list);
        this.context = context;
        this.list = list;
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


        alloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomKeypadDialogFree keypad = new CustomKeypadDialogFree(context, false, new CustomKeypadDialogFree.IOnOkClickListener() {
                    @Override
                    public void okClicked(double value) {
                        int enterQty = (int) value;
                        if (enterQty >= 0) {

                            Log.d("Free",">>>>>>"+enterQty);
                            list.get(position).setAlloc(""+enterQty);
                            alloc.setText(list.get(position).getAlloc());
//                                itemDetails.setFreeQty(avaliableQty);
//                                freeQty.setText("Free Qty you have : " + itemDetails.getFreeQty());
//                            }else{
//                                freeQty.setText("Free Qty you have : " + itemDetails.getFreeQty());
//                            }
//
//                            if(!enteredQty.getText().toString().equals("")){
//                                itemFreeIssues.get(position).setAlloc(enteredQty.getText().toString());
//                                listView.clearTextFilter();
//                                listView.setAdapter(new FreeIssueAdapter(getActivity(), itemFreeIssues));
//                            }else{
//                                Toast.makeText(getActivity(), "Please enter valid quantity", Toast.LENGTH_SHORT).show();
//                            }

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

