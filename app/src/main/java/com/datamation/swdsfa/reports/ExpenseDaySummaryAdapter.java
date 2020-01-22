package com.datamation.swdsfa.reports;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.model.DayExpDet;
import com.datamation.swdsfa.model.DayExpHed;

import java.util.HashMap;
import java.util.List;

public class ExpenseDaySummaryAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<DayExpHed> _listDataDEHeader; // header titles
    // child data in format of header title, child title
    private HashMap<DayExpHed, List<DayExpDet>> _listDataDEChild;

    public ExpenseDaySummaryAdapter(Context _context, List<DayExpHed> _listDataDEHeader, HashMap<DayExpHed, List<DayExpDet>> _listDataDEChild) {
        this._context = _context;
        this._listDataDEHeader = _listDataDEHeader;
        this._listDataDEChild = _listDataDEChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataDEChild.get(this._listDataDEHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final DayExpDet childText = (DayExpDet) getChild(groupPosition, childPosition);

        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_items_1, null);
        }

        TextView reason = (TextView) convertView.findViewById(R.id.reason);
        TextView remarks = (TextView) convertView.findViewById(R.id.remark);

        reason.setText(childText.getEXPDET_EXPCODE());
        remarks.setText(childText.getEXPDET_AMOUNT());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataDEChild.get(this._listDataDEHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataDEHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataDEHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent)
    {
        DayExpHed headerTitle = (DayExpHed)getGroup(groupPosition);

        if (convertView == null)
        {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_1, null);
        }

        TextView lblrefNo = (TextView) convertView.findViewById(R.id.refno);
        TextView lbldebCode = (TextView) convertView.findViewById(R.id.debcode);
        TextView lbldate = (TextView) convertView.findViewById(R.id.date);
        TextView lblreason = (TextView) convertView.findViewById(R.id.total);
        TextView lblstatus = (TextView) convertView.findViewById(R.id.status);

        lblrefNo.setTypeface(null, Typeface.BOLD);
        lblrefNo.setText(headerTitle.getEXP_REFNO());
        lbldate.setText(headerTitle.getEXP_TXNDATE());
        lbldebCode.setText(headerTitle.getEXP_REPCODE());
        lblstatus.setText(headerTitle.getEXP_IS_SYNCED());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

