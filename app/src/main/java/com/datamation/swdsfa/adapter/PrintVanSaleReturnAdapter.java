package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.FInvRDet;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PrintVanSaleReturnAdapter extends ArrayAdapter<FInvRDet> {
    Context context;
    ArrayList<FInvRDet> list;
    String refno;
    BigDecimal disc;

    public PrintVanSaleReturnAdapter(Context context, ArrayList<FInvRDet> list) {

        super(context, R.layout.row_printitems_listview, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.row_printitems_listview, parent, false);

        TextView itemname = (TextView) row.findViewById(R.id.printitemnameVan);
        TextView pieceqty = (TextView) row.findViewById(R.id.printpiecesQtyVan);
        TextView amount = (TextView) row.findViewById(R.id.printamountVan);
        TextView printindex = (TextView) row.findViewById(R.id.printindexVan);
        TextView Disc = (TextView) row.findViewById(R.id.printVanDisc);
        TextView mrp = (TextView) row.findViewById(R.id.printVanMRP);

        itemname.setText(list.get(position).getFINVRDET_ITEMCODE());
        pieceqty.setText(list.get(position).getFINVRDET_QTY());
        if(list.get(position).getFINVRDET_SELL_PRICE().equals(list.get(position).getFINVRDET_CHANGED_PRICE())) {
            mrp.setText(list.get(position).getFINVRDET_SELL_PRICE());
        }else{
            mrp.setText(list.get(position).getFINVRDET_CHANGED_PRICE());
        }
        //Disc.setText(list.get(position).getFINVDET_DISVALAMT());
        amount.setText(list.get(position).getFINVRDET_AMT());

        position = position + 1;
        String pos = Integer.toString(position);
        printindex.setText(pos + ". ");

        return row;
    }
}
