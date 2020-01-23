package com.datamation.hmdsfa.view.dashboard;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.PromoItemsAdapter;
import com.datamation.hmdsfa.controller.FreeDebController;
import com.datamation.hmdsfa.controller.FreeHedController;
import com.datamation.hmdsfa.model.FreeDeb;
import com.datamation.hmdsfa.model.FreeHed;
import com.datamation.hmdsfa.model.free;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Used to show the user a list of recorded payments.
 */
public class PromotionDetailsFragment extends Fragment  {

    private static final String LOG_TAG = PromotionDetailsFragment.class.getSimpleName();
    ExpandableListAdapter listAdapter;
    ExpandableListAdapterDeb listAdapterDeb;
    ExpandableListView expListView;
    TextView viewDebFree;
    List<FreeHed> listDataHeader;
    HashMap<FreeHed, List<free>> listDataChild;
    HashMap<FreeHed, List<FreeDeb>> listDataChild2;
    //    private DatabaseHandler dbHandler;
//    private CalendarDatePickerDialog calendarDatePickerDialog;
    private int mYear, mMonth, mDay;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private NumberFormat numberFormat = NumberFormat.getInstance();

    //    private Calendar /*calendarBegin, calendarEnd, */nowCalendar;

    SwipeRefreshLayout mSwipeRefreshLayout;
    RelativeLayout sublayout;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.promotion_new, container, false);


        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              //  getMenu();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
        viewDebFree = (TextView) rootView.findViewById(R.id.viewDebtors);

        viewDebFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDebPromo();
            }
        });
        //sublayout = (RelativeLayout) rootView.findViewById(R.id.fragment_order_details_header_container_child);


//        final int[] prevExpandPosition = {-1};
//        //Lisview on group expand listner... to close other expanded headers...
//        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int i) {
//                if (prevExpandPosition[0] >= 0) {
//                    //sublayout.setVisibility(View.VISIBLE);
//                    expListView.collapseGroup(prevExpandPosition[0]);
//                }
//                prevExpandPosition[0] = i;
//            }
//        });


        // Listview on child click listener
//        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//
//                String itemName = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getSaleItem();
//                Toast.makeText(getActivity(), "You selected : " + itemName, Toast.LENGTH_SHORT).show();
//                Log.e("Child", listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getIssueItem());
//                return false;
//            }
//        });

        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        return rootView;
    }
    public void ViewDebPromo() {
        final Dialog repDialog = new Dialog(getActivity());
        repDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        repDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        repDialog.setCancelable(false);
//        repDialog.setCanceledOnTouchOutside(false);
        repDialog.setContentView(R.layout.debtor_promotion);

        //initializations
        final ExpandableListView listView = (ExpandableListView) repDialog.findViewById(R.id.lvExp1);
        listDataHeader = new FreeHedController(getActivity()).getFreeIssueHeaders();
        listDataChild2= new HashMap<FreeHed, List<FreeDeb>>();

        for(FreeHed free : listDataHeader){
            listDataChild2.put(free,new FreeDebController(getActivity()).getFreeIssueDebtors(free.getFFREEHED_REFNO()));
        }
//        ArrayList<FreeDeb> list = new FreeDebController(getActivity()).getFreeIssueDebtors(SharedPref.getInstance(getActivity()).getSelectedDebCode());
//        listView.setAdapter(new ExpandableListAdapterDeb(getActivity(), list));
        listAdapterDeb = new ExpandableListAdapterDeb(getActivity(), listDataHeader, listDataChild2);

        // setting list adapter
        listView.setAdapter(listAdapterDeb);
        //close
        repDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repDialog.dismiss();
            }
        });

        repDialog.show();
    }
    //https://github.com/Rishijay/Dynamic-Expandable-ListView
    private void prepareListData() {
        listDataHeader = new FreeHedController(getActivity()).getFreeIssueHeaders();
        listDataChild = new HashMap<FreeHed, List<free>>();

        for(FreeHed free : listDataHeader){
            listDataChild.put(free,new FreeHedController(getActivity()).getFreeIssueDetails(free.getFFREEHED_REFNO(),free.getFFREEHED_FTYPE()));
        }

    }



    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<FreeHed> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<FreeHed, List<free>> _listDataChild;

        public ExpandableListAdapter(Context context, List<FreeHed> listDataHeader,
                                     HashMap<FreeHed, List<free>> listChildData) {
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
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final free childText = (free) getChild(groupPosition, childPosition);
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            FreeHed header = (FreeHed) getGroup(groupPosition);
            final String refno = header.getFFREEHED_REFNO();
            TextView txtListChild;
            TextView txtListChild1;
            TextView txtListChild2;
            TextView txtListChild3;
            TextView txtListChild4;
            TextView txtListChild5;
//            if (convertView == null) {
//
//                convertView = infalInflater.inflate(R.layout.list_items_free, null);
//            }
            if(childPosition ==0)
            {
                convertView = infalInflater.inflate(R.layout.child_header, null);
                 txtListChild = (TextView) convertView.findViewById(R.id.sale_item0);
                 txtListChild1 = (TextView) convertView.findViewById(R.id.free_item0);
                 txtListChild2 = (TextView) convertView.findViewById(R.id.from_qty0);
                 txtListChild3 = (TextView) convertView.findViewById(R.id.to_qty0);
                 txtListChild4 = (TextView) convertView.findViewById(R.id.item_qty0);
                 txtListChild5 = (TextView) convertView.findViewById(R.id.free_qty0);

                txtListChild.setText(childText.getSaleItem());
                txtListChild1.setText(childText.getIssueItem());
                txtListChild2.setText(childText.getFromQty());
                txtListChild3.setText(childText.getToQty());
                txtListChild4.setText(childText.getItemQty());
                txtListChild5.setText(childText.getFreeQty());
//                TextView txtHeader = (TextView)convertView.findViewById(R.id.txtHeader);
//                txtHeader.setText(currentParent.textToHeader);
            }
            else
            //Here is the ListView of the ChildView
            //if(childPosition>0 && childPosition<getChildrenCount(groupPosition))
            {
                convertView = infalInflater.inflate(R.layout.list_items_free, null);
                 txtListChild = (TextView) convertView.findViewById(R.id.sale_item);
                 txtListChild1 = (TextView) convertView.findViewById(R.id.free_item);
                 txtListChild2 = (TextView) convertView.findViewById(R.id.from_qty);
                 txtListChild3 = (TextView) convertView.findViewById(R.id.to_qty);
                 txtListChild4 = (TextView) convertView.findViewById(R.id.item_qty);
                 txtListChild5 = (TextView) convertView.findViewById(R.id.free_qty);

                txtListChild.setText(childText.getSaleItem());
                txtListChild1.setText(childText.getIssueItem());
                txtListChild2.setText(childText.getFromQty());
                txtListChild3.setText(childText.getToQty());
                txtListChild4.setText(childText.getItemQty());
                txtListChild5.setText(childText.getFreeQty());
            }
               txtListChild.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Toast.makeText(getActivity(),"SaleItems for refno"+refno,Toast.LENGTH_LONG).show();
                       LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                       View promptView = layoutInflater.inflate(R.layout.promo_items_mix, null);
                       AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                      // alertDialogBuilder.setTitle("SALE ITEMS");
                       alertDialogBuilder.setView(promptView);

                       final ListView listView = (ListView) promptView.findViewById(R.id.lvItems);
                       final TextView textView = (TextView) promptView.findViewById(R.id.title);
                       textView.setText("SALE ITEMS");
                       ArrayList<FreeHed> list = new FreeHedController(getActivity()).getSaleItems(refno);
                       listView.setAdapter(new PromoItemsAdapter(getActivity(), list));

                       alertDialogBuilder.setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               dialog.cancel();
                           }
                       });

                       AlertDialog alertDialog = alertDialogBuilder.create();
                       alertDialog.show();
                   }
               });
                txtListChild1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //Toast.makeText(getActivity(),"FreeItem for refno"+refno,Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(),"SaleItems for refno"+refno,Toast.LENGTH_LONG).show();
                        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                        View promptView = layoutInflater.inflate(R.layout.promo_items_mix, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        // alertDialogBuilder.setTitle("SALE ITEMS");
                        alertDialogBuilder.setView(promptView);

                        final ListView listView = (ListView) promptView.findViewById(R.id.lvItems);
                        final TextView textView = (TextView) promptView.findViewById(R.id.title);
                        textView.setText("FREE ITEMS");
                        ArrayList<FreeHed> list = new FreeHedController(getActivity()).getFreeItems(refno);
                        listView.setAdapter(new PromoItemsAdapter(getActivity(), list));

                        alertDialogBuilder.setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }});
                return convertView;
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
            FreeHed headerTitle = (FreeHed) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //convertView = infalInflater.inflate(R.layout.test_constraint, null);
                convertView = infalInflater.inflate(R.layout.list_group_new, null);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.free_refno);
            TextView lblListHeader1 = (TextView) convertView.findViewById(R.id.from_date);
            TextView lblListHeader2 = (TextView) convertView.findViewById(R.id.to_date);
           // TextView lblListHeader3 = (TextView) convertView.findViewById(R.id.type);
          //  TextView lblListHeader4 = (TextView) convertView.findViewById(R.id.free_priority);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getFFREEHED_DISC_DESC());
            lblListHeader1.setText(headerTitle.getFFREEHED_VDATEF());
            lblListHeader2.setText(headerTitle.getFFREEHED_VDATET());
        //    lblListHeader3.setText(headerTitle.getFFREEHED_FTYPE());
         //   lblListHeader4.setText(headerTitle.getFFREEHED_PRIORITY());

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
    public class ExpandableListAdapterDeb extends BaseExpandableListAdapter {

        private Context _context;
        private List<FreeHed> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<FreeHed, List<FreeDeb>> _listDataChild;

        public ExpandableListAdapterDeb(Context context, List<FreeHed> listDataHeader,
                                     HashMap<FreeHed, List<FreeDeb>> listChildData) {
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
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            final FreeDeb childText = (FreeDeb) getChild(groupPosition, childPosition);
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_items_deb, null);
                TextView txtListChild = (TextView) convertView.findViewById(R.id.itemcode);
                txtListChild.setText(childText.getFFREEDEB_DEB_CODE());
            return convertView;
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
            FreeHed headerTitle = (FreeHed) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = infalInflater.inflate(R.layout.list_group_new_responsive_layout, null);
                //convertView = infalInflater.inflate(R.layout.list_group_new, null);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.free_refno);
            TextView lblListHeader1 = (TextView) convertView.findViewById(R.id.from_date);
            TextView lblListHeader2 = (TextView) convertView.findViewById(R.id.to_date);
          //  TextView lblListHeader3 = (TextView) convertView.findViewById(R.id.type);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getFFREEHED_DISC_DESC());
            lblListHeader1.setText(headerTitle.getFFREEHED_VDATEF());
            lblListHeader2.setText(headerTitle.getFFREEHED_VDATET());
           // lblListHeader3.setText(headerTitle.getFFREEHED_FTYPE());

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
}
