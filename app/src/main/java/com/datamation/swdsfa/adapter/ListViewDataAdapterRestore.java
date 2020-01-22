package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.model.ContentItemBackups;
import com.datamation.swdsfa.R;

import java.util.List;

/**
 * Created by Rashmi.
 */

public class ListViewDataAdapterRestore extends ArrayAdapter<ContentItemBackups> {

    Context context;
    List<ContentItemBackups> objects;

    public ListViewDataAdapterRestore(Context context, List<ContentItemBackups> objects2) {
        super(context, 0, objects2);
        this.context = context;
        this.objects = objects2;
    }


    @Override
    public int getCount() {
        return objects.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContentItemBackups c = getItem(position);

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_restore, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
            holder.size=(TextView) convertView.findViewById(R.id.size);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(objects.get(position).getDate());
        holder.tvDesc.setText(objects.get(position).getFileName());
        holder.size.setText(objects.get(position).getSize());
        // holder.icon.setImageResource(objects.get(position).getIcon());
        return convertView;
    }

    private class ViewHolder {

        TextView tvName, tvDesc,size;
        //ImageView icon;
    }
}