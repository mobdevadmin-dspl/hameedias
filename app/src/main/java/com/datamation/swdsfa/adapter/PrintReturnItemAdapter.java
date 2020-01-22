package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.FInvRDet;

import java.util.ArrayList;

public class PrintReturnItemAdapter extends ArrayAdapter<FInvRDet> {
    Context context;
    ArrayList<FInvRDet> list;
    String refno;
    String debtorCode;

    public PrintReturnItemAdapter(Context context, ArrayList<FInvRDet> list, String refno, String debtorCode) {

        super(context, R.layout.return_sale_listview_printrow, list);
        this.context = context;
        this.list = list;
        this.refno = refno;
        this.debtorCode = debtorCode;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.return_sale_listview_printrow, parent, false);

        TextView itemname = (TextView) row.findViewById(R.id.printitemname);
        TextView pieceqty = (TextView) row.findViewById(R.id.printpiecesQty);
        TextView printindex = (TextView) row.findViewById(R.id.printindex);
        TextView mrp = (TextView) row.findViewById(R.id.printPreMRP);
        TextView disc = (TextView) row.findViewById(R.id.printPreDisc);
        TextView amount = (TextView) row.findViewById(R.id.printamount);
        TextView type = (TextView)row.findViewById(R.id.printPreType);

        itemname.setText(list.get(position).getFINVRDET_ITEMCODE());
        pieceqty.setText(list.get(position).getFINVRDET_QTY());

        double amt = Double.parseDouble(list.get(position).getFINVRDET_AMT());

        if (list.get(position).getFINVRDET_TAX_AMT()== null)
        {
            double finalAmt = amt;

        }
        else
        {
            double taxAmt = Double.parseDouble(list.get(position).getFINVRDET_TAX_AMT());
            double finalAmt = amt - taxAmt;

        }

        // if Debtor is Tax enable

//        if (new FDebTaxDS(context).isDebtorTax(debtorCode))
//        {
//            amount.setText(String.format("%.2f", finalAmt));
//            mrp.setText(String.format("%.2f", Double.parseDouble(list.get(position).getFINVRDET_SELL_PRICE())));
//        }
//        else
//        {
            amount.setText(list.get(position).getFINVRDET_AMT());
            mrp.setText(String.format("%.2f", Double.parseDouble(list.get(position).getFINVRDET_T_SELL_PRICE())));
        //}

        //amount.setText(list.get(position).getFINVRDET_AMT());
        disc.setText(list.get(position).getFINVRDET_DIS_AMT());

        type.setText(list.get(position).getFINVRDET_RETURN_TYPE());

        position = position + 1;
        String pos = Integer.toString(position);
        printindex.setText(pos + ". ");

        return row;
    }
}