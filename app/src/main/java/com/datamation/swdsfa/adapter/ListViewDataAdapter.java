package com.datamation.swdsfa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.settings.ContentItem;

import java.util.List;

public class ListViewDataAdapter extends ArrayAdapter<ContentItem> {

    Context context;
    List<ContentItem> objects;

    public ListViewDataAdapter(Context context, List<ContentItem> objects2) {
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

        ContentItem c = getItem(position);

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
            holder.icon=(ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(objects.get(position).getName());
        holder.tvDesc.setText(objects.get(position).getDesc());
        holder.icon.setImageResource(objects.get(position).getIcon());
        return convertView;
    }

    private class ViewHolder {

        TextView tvName, tvDesc;
        ImageView icon;
    }
}