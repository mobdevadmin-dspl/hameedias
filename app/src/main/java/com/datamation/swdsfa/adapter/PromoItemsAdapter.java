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
import com.datamation.swdsfa.model.FreeHed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class PromoItemsAdapter extends BaseAdapter {
   // Context context;
   private LayoutInflater inflater;
    ArrayList<FreeHed> list;


    public PromoItemsAdapter(Context context, ArrayList<FreeHed> list){
        this.inflater = LayoutInflater.from(context);
        this.list = list;

    }
    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }
    @Override
    public FreeHed getItem(int position) {
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

                convertView =inflater.inflate(R.layout.row_promo_layout,parent,false);

                viewHolder.txtItem = (TextView) convertView.findViewById(R.id.txtItem);

                viewHolder.txtItem.setText(list.get(position).getFFREEHED_REMARKS());

        }else
        {
                viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    private  static  class  ViewHolder{

        TextView txtItem;

    }



}
