package com.datamation.swdsfa.view.dashboard;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.InvDetController;
import com.datamation.swdsfa.controller.InvHedController;
import com.datamation.swdsfa.model.InvDet;
import com.datamation.swdsfa.model.InvHed;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Used to show the user the list of invoices.
 */
public class InvoiceDetailsFragmentNew extends Fragment {

    private static final String LOG_TAG = InvoiceDetailsFragmentNew.class.getSimpleName();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<InvHed> listDataHeader;
    HashMap<InvHed, List<InvDet>> listDataChild;
    TextView total;
    //    private DatabaseHandler dbHandler;
//    private CalendarDatePickerDialog calendarDatePickerDialog;
    private int mYear, mMonth, mDay;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private NumberFormat numberFormat = NumberFormat.getInstance();

    //    private Calendar /*calendarBegin, calendarEnd, */nowCalendar;

    String url = "http://203.143.21.121:8080/LankaHDWebServices/LankaHDWebServicesRest.svc/ffreehed/mobile123/lhd"; // Replace with your own url
    //ExpandableListAdapter listAdapter;

    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.promotion, container, false);


        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(true);
        total = (TextView)rootView.findViewById(R.id.item_payment_details_tv_outstanding_amount_total) ;
//        progressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Please wait...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        // Creating database to store menu items
        // database = openOrCreateDatabase("Menu.db", MODE_PRIVATE, null);
        // final String q = "Create Table if not exists List (dishName varchar(50), categoryName varchar(50), categoryID varchar(20), itemCode varchar(20), rateOfHalf varchar(10), rateOfFull varchar(10), itemStatus varchar(20), half varchar(20), full varchar(20))";
        // database.execSQL(q);
        // Get data from JSON
        // getMenu();

        // Swipe down to refresh list
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //  getMenu();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);


        final int[] prevExpandPosition = {-1};
        //Lisview on group expand listner... to close other expanded headers...
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                if (prevExpandPosition[0] >= 0) {
                    expListView.collapseGroup(prevExpandPosition[0]);
                }
                prevExpandPosition[0] = i;
            }
        });


        // Listview on child click listener


        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String itemName = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getFINVDET_ITEM_CODE();
                Toast.makeText(getActivity(), "You selected : " + itemName, Toast.LENGTH_SHORT).show();
                Log.e("Child", listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getFINVDET_ITEM_CODE());
                return false;
            }
        });
        return rootView;
    }
    //https://github.com/Rishijay/Dynamic-Expandable-ListView
    private void prepareListData() {
        listDataHeader = new InvHedController(getActivity()).getTodayOrders();
        listDataChild = new HashMap<InvHed, List<InvDet>>();

        for(InvHed free : listDataHeader){
            listDataChild.put(free,new InvDetController(getActivity()).getTodayOrderDets(free.getFINVHED_REFNO()));
        }

    }

    public void refresh() {
        //   if (adapter != null) adapter.notifyDataSetChanged();
    }


    private static class HeaderViewHolder {
        TextView pinLabel;
    }

    private static class ViewHolder {
        TextView tvInvoiceDetails;
        TextView tvGrossAmount;
        TextView tvNetAmount;
        TextView tvOutstandingAmount;
        TextView tvCashPayment;
        TextView tvChequeAmount;
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<InvHed> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<InvHed, List<InvDet>> _listDataChild;

        public ExpandableListAdapter(Context context, List<InvHed> listDataHeader,
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
            InvHed headerTitle = (InvHed) getGroup(groupPosition);
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
            TextView type = (TextView) convertView.findViewById(R.id.type);
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
            type.setText(headerTitle.getFINVHED_TXNTYPE());
            date.setText(headerTitle.getFINVHED_TXNDATE());
            tot.setText(headerTitle.getFINVHED_TOTALAMT());

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

//            invoiceGrossTotal.setText(numberFormat.format(grossTotal));
//            invoiceNetTotal.setText(numberFormat.format(netTotal));
//            invoiceOutstandingTotal.setText(numberFormat.format(outstandingTotal));
//            invoiceMarketReturnTotal.setText(numberFormat.format(marketReturnTotal));
//            invoiceDiscountTotal.setText(numberFormat.format(discountTotal));

        }
    }


}
