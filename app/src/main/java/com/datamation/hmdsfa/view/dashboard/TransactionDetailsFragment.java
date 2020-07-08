package com.datamation.hmdsfa.view.dashboard;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.InvDetController;
import com.datamation.hmdsfa.controller.InvHedController;
import com.datamation.hmdsfa.controller.ItemLocController;
import com.datamation.hmdsfa.controller.OrderController;
import com.datamation.hmdsfa.controller.OrderDetailController;
import com.datamation.hmdsfa.controller.PreProductController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.controller.SalesReturnController;
import com.datamation.hmdsfa.controller.SalesReturnDetController;
import com.datamation.hmdsfa.dialog.VanSalePrintPreviewAlertBox;
import com.datamation.hmdsfa.model.FInvRDet;
import com.datamation.hmdsfa.model.FInvRHed;
import com.datamation.hmdsfa.model.InvDet;
import com.datamation.hmdsfa.model.InvHed;
import com.datamation.hmdsfa.model.Order;
import com.datamation.hmdsfa.model.OrderDetail;
import com.datamation.hmdsfa.utils.UtilityContainer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionDetailsFragment extends Fragment {

    private View view;
    ExpandableListView expListView;
    TextView total;
    private NumberFormat numberFormat = NumberFormat.getInstance();
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Spinner spnTrans;

    ExpandablePreListAdapter listPreAdapter;
    List<Order> listPreDataHeader;
    HashMap<Order, List<OrderDetail>> listPreDataChild;


    ExpandableVanListAdapter listVanAdapter;
    List<InvHed> listVanDataHeader;
    HashMap<InvHed, List<InvDet>> listVanDataChild;

    ExpandableRetListAdapter listRetAdapter;
    List<FInvRHed> listRetDataHeader;
    HashMap<FInvRHed, List<FInvRDet>> listRetDataChild;

    public TransactionDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transaction_details_dspl, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeToRefresh);

        spnTrans = (Spinner)view.findViewById(R.id.spnMainTrans);

        ArrayList<String> otherList = new ArrayList<String>();
        otherList.add("INVOICES");
        otherList.add("ORDERS");
        otherList.add("RETURNS");

        final ArrayAdapter<String> otherAdapter = new ArrayAdapter<String>(getActivity(),R.layout.reason_spinner_item, otherList);
        otherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTrans.setAdapter(otherAdapter);

        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(true);

        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

//        final int[] prevExpandPosition = {-1};
//        //Lisview on group expand listner... to close other expanded headers...
//        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int i) {
//                if (prevExpandPosition[0] >= 0) {
//                    expListView.collapseGroup(prevExpandPosition[0]);
//                }
//                prevExpandPosition[0] = i;
//            }
//        });

        spnTrans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position==0)
                {
                    expListView.setAdapter((BaseExpandableListAdapter)null);
                    //expListView.clearTextFilter();
                    prepareVanListData();
                }
                else if (position == 1)
                {

                    expListView.setAdapter((BaseExpandableListAdapter)null);
                    //expListView.clearTextFilter();
                    preparePreListData();
                }
                else
                {
                    expListView.setAdapter((BaseExpandableListAdapter)null);
                    //expListView.clearTextFilter();
                    prepareRetListData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return view;
    }

    public void preparePreListData()
    {
        listPreDataHeader = new OrderController(getActivity()).getTodayOrders();

        if (listPreDataHeader.size()== 0)
        {
            Toast.makeText(getActivity(), "No data to display", Toast.LENGTH_LONG).show();

            listPreDataChild = new HashMap<Order, List<OrderDetail>>();

            for(Order free : listPreDataHeader){
                listPreDataChild.put(free,new OrderDetailController(getActivity()).getTodayOrderDets(free.getORDER_REFNO()));
            }

            listPreAdapter = new ExpandablePreListAdapter(getActivity(), listPreDataHeader, listPreDataChild);
            expListView.setAdapter(listPreAdapter);
        }
        else
        {
            listPreDataChild = new HashMap<Order, List<OrderDetail>>();

            for(Order free : listPreDataHeader){
                listPreDataChild.put(free,new OrderDetailController(getActivity()).getTodayOrderDets(free.getORDER_REFNO()));
            }

            listPreAdapter = new ExpandablePreListAdapter(getActivity(), listPreDataHeader, listPreDataChild);
            expListView.setAdapter(listPreAdapter);
        }
    }

    public void prepareVanListData()
    {
        listVanDataHeader = new InvHedController(getActivity()).getTodayOrders();

        if (listVanDataHeader.size()== 0)
        {
            Toast.makeText(getActivity(), "No data to display", Toast.LENGTH_LONG).show();
        }
        else
        {
            listVanDataChild = new HashMap<InvHed, List<InvDet>>();

            for(InvHed free : listVanDataHeader){
                listVanDataChild.put(free,new InvDetController(getActivity()).getTodayOrderDets(free.getFINVHED_REFNO()));
            }

            listVanAdapter = new ExpandableVanListAdapter(getActivity(), listVanDataHeader, listVanDataChild);
            expListView.setAdapter(listVanAdapter);
        }
    }

    public void prepareRetListData()
    {
        listRetDataHeader = new SalesReturnController(getActivity()).getTodayReturns();

        if (listRetDataHeader.size()== 0)
        {
            Toast.makeText(getActivity(), "No data to display", Toast.LENGTH_LONG).show();
        }
        else
        {
            listRetDataChild = new HashMap<FInvRHed, List<FInvRDet>>();

            for(FInvRHed free : listRetDataHeader){
                listRetDataChild.put(free,new SalesReturnDetController(getActivity()).getTodayOrderDets(free.getFINVRHED_REFNO()));
            }

            listRetAdapter = new ExpandableRetListAdapter(getActivity(), listRetDataHeader, listRetDataChild);
            expListView.setAdapter(listRetAdapter);
        }
    }

    // adapter for pre sale

    public class ExpandablePreListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<Order> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<Order, List<OrderDetail>> _listDataChild;

        public ExpandablePreListAdapter(Context context, List<Order> listDataHeader,
                                     HashMap<Order, List<OrderDetail>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View grpview, ViewGroup parent) {

            final OrderDetail childText = (OrderDetail) getChild(groupPosition, childPosition);

            if (grpview == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                grpview = infalInflater.inflate(R.layout.list_items, null);
            }

            TextView txtListChild = (TextView) grpview.findViewById(R.id.itemcode);
            TextView txtListChild1 = (TextView) grpview.findViewById(R.id.qty);
            TextView txtListChild2 = (TextView) grpview.findViewById(R.id.amount);

            txtListChild.setText("ItemCode - "+childText.getFORDERDET_ITEMCODE());
            txtListChild1.setText("Qty - "+childText.getFORDERDET_QTY());
            txtListChild2.setText("Amount - "+numberFormat.format(Double.parseDouble(childText.getFORDERDET_AMT())));
            return grpview;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            final Order headerTitle = (Order) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.transaction_details_list_group, null);
                //convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.refno);
            TextView deb = (TextView) convertView.findViewById(R.id.debcode);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView tot = (TextView) convertView.findViewById(R.id.total);
            TextView stats = (TextView) convertView.findViewById(R.id.status);
            TextView delete = (TextView) convertView.findViewById(R.id.type);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getORDER_REFNO());
            deb.setText(headerTitle.getORDER_DEBCODE());
            if(headerTitle.getORDER_IS_SYNCED().equals("1")){
                delete.setBackground(null);
                stats.setText("Synced");
                stats.setTextColor(getResources().getColor(R.color.material_alert_positive_button));
            }else{
                delete.setBackground(getResources().getDrawable(R.drawable.icon_minus));
                stats.setText("Not Synced");
                stats.setTextColor(getResources().getColor(R.color.material_alert_negative_button));
            }
            //type.setText(headerTitle.getORDER_TXNTYPE());
            date.setText(headerTitle.getORDER_TXNDATE());
            tot.setText(headerTitle.getORDER_TOTALAMT());

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteOrder(headerTitle.getORDER_REFNO());
                }
            });

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

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            double grossTotal = 0;


            List<Order> searchingDetails = _listDataHeader;


            for (Order invoice : searchingDetails) {

                if (invoice != null) {
                    grossTotal += Double.parseDouble(invoice.getORDER_TOTALAMT());

                }
            }

        }
    }

    public void deleteOrder(final String RefNo) {

        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Do you want to delete this order ?")
                .positiveColor(ContextCompat.getColor(getActivity(), R.color.material_alert_positive_button))
                .positiveText("Yes")
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.material_alert_negative_button))
                .negativeText("No, Exit")
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);

                        int result = new OrderController(getActivity()).restData(RefNo);

                        if (result>0) {
                            new OrderDetailController(getActivity()).restData(RefNo);
                            new PreProductController(getActivity()).mClearTables();
                            Toast.makeText(getActivity(), "Order deleted successfully..!", Toast.LENGTH_SHORT).show();

                            preparePreListData();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Order delete unsuccess..!", Toast.LENGTH_SHORT).show();
                        }


                        UtilityContainer.ClearReturnSharedPref(getActivity());
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);

                        dialog.dismiss();


                    }
                })
                .build();
        materialDialog.setCanceledOnTouchOutside(false);
        materialDialog.show();
    }
    public void deleteInvoice(final String RefNo) {

        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Do you want to delete this invoice ?")
                .positiveColor(ContextCompat.getColor(getActivity(), R.color.material_alert_positive_button))
                .positiveText("Yes")
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.material_alert_negative_button))
                .negativeText("No, Exit")
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        ArrayList<InvHed> logHedList = new InvHedController(getActivity()).getAllUnsyncedforLog();
                        new InvHedController(getActivity()).createOrUpdateInvHedLog(logHedList);
                        ArrayList<InvDet> logDetList = new InvDetController(getActivity()).getAllInvDet(RefNo);
                        new InvDetController(getActivity()).createOrUpdateBCInvDetLog(logDetList);
                        prepareVanListData();
                        int result = new InvHedController(getActivity()).restDataBC(RefNo);

                        if (result>0) {
                            new InvDetController(getActivity()).restData(RefNo);
                            new ItemLocController(getActivity()).UpdateVanStock(RefNo,"+",new SalRepController(getActivity()).getCurrentLoccode().trim());

                            Toast.makeText(getActivity(), "Invoice deleted successfully..!", Toast.LENGTH_SHORT).show();

                            prepareVanListData();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Invoice delete unsuccess..!", Toast.LENGTH_SHORT).show();
                            prepareVanListData();
                        }


                        UtilityContainer.ClearReturnSharedPref(getActivity());
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);

                        dialog.dismiss();


                    }
                })
                .build();
        materialDialog.setCanceledOnTouchOutside(false);
        materialDialog.show();
    }

    public void printInvoice(final String RefNo) {

        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Do you want to print this invoice ?")
                .positiveColor(ContextCompat.getColor(getActivity(), R.color.material_alert_positive_button))
                .positiveText("Yes")
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.material_alert_negative_button))
                .negativeText("No, Exit")
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);

                        int a = new VanSalePrintPreviewAlertBox(getActivity()).PrintDetailsDialogbox(getActivity(), "Print preview", RefNo);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);

                        dialog.dismiss();


                    }
                })
                .build();
        materialDialog.setCanceledOnTouchOutside(false);
        materialDialog.show();
    }
    // adapter for van sale


    public class ExpandableVanListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<InvHed> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<InvHed, List<InvDet>> _listDataChild;

        public ExpandableVanListAdapter(Context context, List<InvHed> listDataHeader,
                                     HashMap<InvHed, List<InvDet>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View grpview, ViewGroup parent) {

            final InvDet childText = (InvDet) getChild(groupPosition, childPosition);

            if (grpview == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                grpview = infalInflater.inflate(R.layout.list_items, null);
            }

            TextView txtListChild = (TextView) grpview.findViewById(R.id.itemcode);
            TextView txtListChild1 = (TextView) grpview.findViewById(R.id.qty);
            TextView txtListChild2 = (TextView) grpview.findViewById(R.id.amount);

            txtListChild.setText("ItemCode - "+childText.getFINVDET_ITEM_CODE());
            txtListChild1.setText("Qty - "+childText.getFINVDET_QTY());
            txtListChild2.setText("Amount - "+numberFormat.format(Double.parseDouble(childText.getFINVDET_AMT())));
            return grpview;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            final InvHed headerTitle = (InvHed) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.refno);
            TextView deb = (TextView) convertView.findViewById(R.id.debcode);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView tot = (TextView) convertView.findViewById(R.id.total);
            TextView stats = (TextView) convertView.findViewById(R.id.status);
            ImageView type = (ImageView) convertView.findViewById(R.id.type);
            ImageView print = (ImageView) convertView.findViewById(R.id.print);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getFINVHED_REFNO());
            deb.setText(headerTitle.getFINVHED_DEBCODE());
            if(headerTitle.getFINVHED_IS_SYNCED().equals("1")){
                stats.setText("Synced");
                stats.setTextColor(getResources().getColor(R.color.material_alert_positive_button));
            }else{
                stats.setText("Not Synced");
                stats.setTextColor(getResources().getColor(R.color.material_alert_negative_button));

            }
            date.setText(headerTitle.getFINVHED_TXNDATE());
            tot.setText(headerTitle.getFINVHED_TOTALAMT());
            type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteInvoice(headerTitle.getFINVHED_REFNO());
                }
            });
            print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    printInvoice(headerTitle.getFINVHED_REFNO());
                }
            });

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

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            double grossTotal = 0;


            List<InvHed> searchingDetails = _listDataHeader;


            for (InvHed invoice : searchingDetails) {

                if (invoice != null) {
                    grossTotal += Double.parseDouble(invoice.getFINVHED_TOTALAMT());

                }
            }

        }
    }

    // adapter for sale return

    public class ExpandableRetListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<FInvRHed> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<FInvRHed, List<FInvRDet>> _listDataChild;

        public ExpandableRetListAdapter(Context context, List<FInvRHed> listDataHeader,
                                        HashMap<FInvRHed, List<FInvRDet>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View grpview, ViewGroup parent) {

            final FInvRDet childText = (FInvRDet) getChild(groupPosition, childPosition);

            if (grpview == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                grpview = infalInflater.inflate(R.layout.list_items, null);
            }

            TextView txtListChild = (TextView) grpview.findViewById(R.id.itemcode);
            TextView txtListChild1 = (TextView) grpview.findViewById(R.id.qty);
            TextView txtListChild2 = (TextView) grpview.findViewById(R.id.amount);

            txtListChild.setText("ItemCode - "+childText.getFINVRDET_ITEMCODE());
            txtListChild1.setText("Qty - "+childText.getFINVRDET_QTY());
            txtListChild2.setText("Amount - "+numberFormat.format(Double.parseDouble(childText.getFINVRDET_AMT())));
            return grpview;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            FInvRHed headerTitle = (FInvRHed) getGroup(groupPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.refno);
            TextView deb = (TextView) convertView.findViewById(R.id.debcode);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView tot = (TextView) convertView.findViewById(R.id.total);
            TextView stats = (TextView) convertView.findViewById(R.id.status);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getFINVRHED_REFNO());
            deb.setText(headerTitle.getFINVRHED_DEBCODE());
            if(headerTitle.getFINVRHED_IS_SYNCED().equals("1")){
                stats.setText("Synced");
                stats.setTextColor(getResources().getColor(R.color.material_alert_positive_button));
            }else{
                stats.setText("Not Synced");
                stats.setTextColor(getResources().getColor(R.color.material_alert_negative_button));

            }
            date.setText(headerTitle.getFINVRHED_TXN_DATE());
            tot.setText(headerTitle.getFINVRHED_TOTAL_AMT());

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

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            double grossTotal = 0;


            List<FInvRHed> searchingDetails = _listDataHeader;


            for (FInvRHed invoice : searchingDetails) {

                if (invoice != null) {
                    grossTotal += Double.parseDouble(invoice.getFINVRHED_TOTAL_AMT());

                }
            }

        }
    }

}
