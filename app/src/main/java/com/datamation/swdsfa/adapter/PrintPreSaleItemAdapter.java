package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.TaxDetController;
import com.datamation.swdsfa.model.OrderDetail;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PrintPreSaleItemAdapter extends ArrayAdapter<OrderDetail> {
    Context context;
    ArrayList<OrderDetail> list;
    String refno;
    BigDecimal disc;
    String debCode;

    public PrintPreSaleItemAdapter(Context context, ArrayList<OrderDetail> list, String debCode) {

        super(context, R.layout.row_printitems_listview, list);
        this.context = context;
        this.list = list;
        this.debCode = debCode;
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

        itemname.setText(list.get(position).getFORDERDET_ITEMCODE());
        pieceqty.setText(list.get(position).getFORDERDET_QTY());
        String sArray[] = new TaxDetController(context).calculateTaxForwardFromDebTax(debCode, list.get(position).getFORDERDET_ITEMCODE(), Double.parseDouble(list.get(position).getFORDERDET_TSELLPRICE()));
        String price = String.format("%.2f",Double.parseDouble(sArray[0]));
        mrp.setText(price);
        String sArray1[] = new TaxDetController(context).calculateTaxForwardFromDebTax(debCode, list.get(position).getFORDERDET_ITEMCODE(), Double.parseDouble(list.get(position).getFORDERDET_AMT()));
        String amt = String.format("%.2f",Double.parseDouble(sArray1[0]));
        Disc.setText(list.get(position).getFORDERDET_BPDISAMT());
        amount.setText(amt);

        position = position + 1;
        String pos = Integer.toString(position);
        printindex.setText(pos + ". ");

        return row;
    }
}
