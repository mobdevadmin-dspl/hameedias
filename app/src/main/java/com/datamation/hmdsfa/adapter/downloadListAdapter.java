package com.datamation.hmdsfa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.model.Control;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class downloadListAdapter extends ArrayAdapter<Control> {

    Context context;
    ArrayList<Control> list;

    public downloadListAdapter(Context context, ArrayList<Control> list) {
        super(context, R.layout.row_download_view, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = null;
        View row = null;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        row = inflater.inflate(R.layout.row_download_view, parent, false);

        TextView title = (TextView) row.findViewById(R.id.row_title);
        TextView count = (TextView) row.findViewById(R.id.row_count);
        title.setText(list.get(position).getFCONTROL_COM_NAME());

        if(!(list.get(position).getFCONTROL_COM_ADD1().equals(list.get(position).getFCONTROL_COM_ADD2())) || (list.get(position).getFCONTROL_COM_ADD1().equals("0")))
        {

            count.setTextColor(Color.parseColor("#EE0000"));
            count.setText(list.get(position).getFCONTROL_COM_ADD1()+"/"+list.get(position).getFCONTROL_COM_ADD2());
        }
        else
        {
            count.setTextColor(Color.parseColor("#4CAF50"));
            count.setText(list.get(position).getFCONTROL_COM_ADD1()+"/"+list.get(position).getFCONTROL_COM_ADD2());
        }

        return row;

    }
}
