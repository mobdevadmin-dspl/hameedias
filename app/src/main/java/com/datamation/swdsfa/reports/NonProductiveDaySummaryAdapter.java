package com.datamation.swdsfa.reports;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.DayNPrdDetController;
import com.datamation.swdsfa.controller.DayNPrdHedController;
import com.datamation.swdsfa.model.DayNPrdDet;
import com.datamation.swdsfa.model.DayNPrdHed;
import com.datamation.swdsfa.view.dashboard.ReportFragment;

import java.util.HashMap;
import java.util.List;

    public class NonProductiveDaySummaryAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<DayNPrdHed> _listDataNPHeader; // header titles
        // child data in format of header title, child title
        private HashMap<DayNPrdHed, List<DayNPrdDet>> _listDataNPChild;

        public NonProductiveDaySummaryAdapter(Context _context, List<DayNPrdHed> _listDataNPHeader, HashMap<DayNPrdHed, List<DayNPrdDet>> _listDataNPChild) {
            this._context = _context;
            this._listDataNPHeader = _listDataNPHeader;
            this._listDataNPChild = _listDataNPChild;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataNPChild.get(this._listDataNPHeader.get(groupPosition)).get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final DayNPrdDet childText = (DayNPrdDet) getChild(groupPosition, childPosition);

            if (convertView == null)
            {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_items_1, null);
            }

            TextView reason = (TextView) convertView.findViewById(R.id.reason);
            TextView remarks = (TextView) convertView.findViewById(R.id.remark);

            reason.setText(childText.getNONPRDDET_REASON());
            remarks.setText(childText.getNONPRDDET_REMARK());

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataNPChild.get(this._listDataNPHeader.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataNPHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataNPHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent)
        {
            DayNPrdHed headerTitle = (DayNPrdHed)getGroup(groupPosition);

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
            lblrefNo.setText(headerTitle.getNONPRDHED_REFNO());
            lbldate.setText(headerTitle.getNONPRDHED_TXNDATE());
            lbldebCode.setText(headerTitle.getNONPRDHED_DEBCODE());
            lblstatus.setText(headerTitle.getNONPRDHED_IS_SYNCED());

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

