package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.FddbNote;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


//pubudu
public class CustomerDebtAdapter extends BaseAdapter {
   // Context context;
   private LayoutInflater inflater;
    ArrayList<FddbNote> list;


    public CustomerDebtAdapter(Context context, ArrayList<FddbNote> list){
        this.inflater = LayoutInflater.from(context);
        this.list = list;

    }
    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }
    @Override
    public FddbNote getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position,  View convertView, final ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView ==null) {
            viewHolder = new ViewHolder();
            try {
                convertView =inflater.inflate(R.layout.row_cus_debt,parent,false);
                viewHolder.layout=(RelativeLayout)convertView.findViewById(R.id.linearLayout);
                viewHolder.txtRefno = (TextView) convertView.findViewById(R.id.txtRefno);
                viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
                viewHolder.txtAge = (TextView) convertView.findViewById(R.id.txtAge);
                viewHolder.txtAmount = (TextView) convertView.findViewById(R.id.txtAmount);

                viewHolder.txtRefno.setText(list.get(position).getFDDBNOTE_REFNO());
                viewHolder.txtDate.setText(list.get(position).getFDDBNOTE_TXN_DATE());

                viewHolder.txtAge.setText(Daybetween(list.get(position).getFDDBNOTE_TXN_DATE()));
                viewHolder.txtAmount.setText(getBalance(list.get(position).getFDDBNOTE_TOT_BAL(), list.get(position).getFDDBNOTE_TOT_BAL1()));
            //txtRefno.setText(list.get(position).getFDDBNOTE_REFNO());
           // txtDate.setText(list.get(position).getFDDBNOTE_TXN_DATE());

            //txtAge.setText(Daybetween(list.get(position).getFDDBNOTE_TXN_DATE()));

           // txtAmount.setText(getBalance(list.get(position).getFDDBNOTE_TOT_BAL(), list.get(position).getFDDBNOTE_TOT_BAL1()));

        } catch (ParseException e) {
                e.printStackTrace();

            }
        }else
        {
                viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    private  static  class  ViewHolder{
        RelativeLayout layout;
        TextView txtRefno;
        TextView txtDate;
        TextView txtAge;
        TextView txtAmount;
   ;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/

    public String getBalance(String totBal, String totBal1) {

        return String.format("%,.2f", (Double.parseDouble(totBal) - Double.parseDouble(totBal1)));
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/

    public String Daybetween(String sDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf1.format(new Date());

        Calendar c1 = Calendar.getInstance();
        Date Date1 = sdf1.parse(currentDate);
        c1.setTime(Date1);

        Calendar c2 = Calendar.getInstance();
        Date Date2 = sdf.parse(sDate);
        c2.setTime(Date2);

        long lDate1 = c1.getTimeInMillis();
        long lDate2 = c2.getTimeInMillis();

        return String.valueOf((int) ((lDate1 - lDate2) / (24 * 60 * 60 * 1000)));
    }

}
