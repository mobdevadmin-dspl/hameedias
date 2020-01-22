package com.datamation.swdsfa.view.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.InvDetController;
import com.datamation.swdsfa.model.InvDet;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Used to show the user the list of invoices.
 */
public class InvoiceDetailsFragment extends Fragment {

    private static final String LOG_TAG = InvoiceDetailsFragment.class.getSimpleName();

    private TextView tvDate;
    private InvoiceAdapter adapter;
    private List<InvDet> pinHolders;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private NumberFormat numberFormat;

    //    private Calendar /*calendarBegin, calendarEnd, */nowCalendar;
//    private long timeInMillis;


   // private CalendarDatePickerDialog calendarDatePickerDialogFrom, calendarDatePickerDialogTo;
    private int mYearFrom, mMonthFrom, mDayFrom, mYearTo, mMonthTo, mDayTo;

    private RelativeLayout filterHolder;
    private ImageView arrow;

    private TextView dateFrom, dateTo;
    private Spinner filterSpinner;

    private long selectedFromTime, selectedToTime;

    private boolean filtersOpen = false, timeFrameChanged = false, filterChanged = false;

    private OvershootInterpolator overshootInterpolator;

    private TextView invoiceGrossTotal;
    private TextView invoiceNetTotal;
    private TextView invoiceOutstandingTotal;
    private TextView invoiceDiscountTotal;
    private TextView invoiceMarketReturnTotal;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        dbHandler = DatabaseHandler.getDbHandler(getActivity());
////        pref = SharedPref.getInstance(getActivity());
//        networkFunctions = new NetworkFunctions(getActivity());

        filtersOpen = false;

        View rootView = inflater.inflate(R.layout.fragment_invoice_details, container, false);

//        timeInMillis = System.currentTimeMillis();

        selectedFromTime = System.currentTimeMillis();
        selectedToTime = System.currentTimeMillis();

//        tvDate = (TextView) rootView.findViewById(R.id.fragment_invoice_details_select_date);
        StickyListHeadersListView listView = (StickyListHeadersListView)
                rootView.findViewById(R.id.fragment_invoice_details_listview);

//        RelativeLayout filterHeader = (RelativeLayout) rootView.findViewById(R.id.fragment_invoice_details_rl_filter_header);
//        filterHolder = (RelativeLayout) rootView.findViewById(R.id.fragment_invoice_details_rl_filter_params);
//
//        filterSpinner = (Spinner) rootView.findViewById(R.id.fragment_invoice_details_spinner_filter_params);
//        dateFrom = (TextView) rootView.findViewById(R.id.fragment_invoice_details_tv_filter_params_date_from);
//        dateTo = (TextView) rootView.findViewById(R.id.fragment_invoice_details_tv_filter_params_date_to);

        invoiceGrossTotal = (TextView) rootView.findViewById(R.id.item_invoice_details_tv_invoice_gross_total);
        invoiceNetTotal = (TextView) rootView.findViewById(R.id.item_invoice_details_tv_invoice_net_total);
        invoiceOutstandingTotal = (TextView) rootView.findViewById(R.id.item_invoice_details_tv_invoice_outstanding_total);
        invoiceDiscountTotal = (TextView) rootView.findViewById(R.id.item_invoice_details_tv_invoice_discount_total);
        invoiceMarketReturnTotal = (TextView) rootView.findViewById(R.id.item_invoice_details_tv_invoice_market_return_total);

       // dateFrom.setText(dateFormat.format(new Date(selectedFromTime)));
     //   dateTo.setText(dateFormat.format(new Date(selectedToTime)));

//        dateFrom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  calendarDatePickerDialogFrom.show(getFragmentManager(), "TAG_FROM");
//            }
//        });
//
//        dateTo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //calendarDatePickerDialogTo.show(getFragmentManager(), "TAG_TO");
//            }
//        });

       // Button btnFilter = (Button) rootView.findViewById(R.id.fragment_invoice_details_btn_filter_params_execute);
//        btnFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (timeFrameChanged || filterChanged) {
//                    //refreshList(filterSpinner.getSelectedItemPosition());
//                } else {
//                    MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
//                            .content("Already displaying search results. Are you sure you want to force refresh?")
//                            .positiveText("Yes")
//                            .positiveColor(getResources().getColor(R.color.material_alert_positive_button))
//                            .negativeText("No")
//                            .negativeColor(getResources().getColor(R.color.material_alert_negative_button))
//                            .callback(new MaterialDialog.ButtonCallback() {
//                                @Override
//                                public void onPositive(MaterialDialog dialog) {
//                                    super.onPositive(dialog);
//                                    //refreshList(filterSpinner.getSelectedItemPosition());
//                                }
//                            })
//                            .build();
//                    materialDialog.show();
//                }
//            }
//        });

//        List<String> filters = new ArrayList<>();
//        filters.add("All");
//        filters.add("Discounted");
//        filters.add("Free Issued");
//
//        filterSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, filters));
//
//        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                filterChanged = true;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        arrow = (ImageView) rootView.findViewById(R.id.fragment_invoice_details_imageview_arrow);
//
//        overshootInterpolator = new OvershootInterpolator();
//
//        filterHeader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (filtersOpen) {
//                    ViewCompat.animate(arrow).rotation(0).setDuration(300).setInterpolator(overshootInterpolator);
//                    //AnimationUtil.collapse(filterHolder, 300);
//                } else {
//                    ViewCompat.animate(arrow).rotation(180).setDuration(300).setInterpolator(overshootInterpolator);
//                   // AnimationUtil.expand(filterHolder, 300);
//                }
//
//                filtersOpen = !filtersOpen;
//            }
//        });

        numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);

        pinHolders = new InvDetController(getActivity()).getTodayInvoices();
//        try {
//            outlets = dbHandler.getAllOutlets();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
      // mapHistoriesWithFreeIssues();

        adapter = new InvoiceAdapter(getActivity(), pinHolders);
//
        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final InvDet selectedDetail = adapter.getItem(position);
//
//                if (selectedDetail.getInvoice() != null) {
//                    Order order = dbHandler.getOrderOfOrderId(selectedDetail.getInvoice().getInvoiceId());
//
//                    if (order != null) {
//                        Intent intent = new Intent(getActivity(), InvoiceReprintActivity.class);
//                        intent.putExtra("order", order);
//                        startActivity(intent);
//                    } else {
//                        if (NetworkUtil.isNetworkAvailable(getActivity())) {
//                            MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
//                                    .content("Order Details not found!\nDo you wish to get order details from server?")
//                                    .positiveText("Yes")
//                                    .negativeText("No")
//                                    .positiveColor(getResources().getColor(R.color.material_alert_positive_button))
//                                    .negativeColor(getResources().getColor(R.color.material_alert_negative_button))
//                                    .callback(new MaterialDialog.ButtonCallback() {
//                                        @Override
//                                        public void onPositive(MaterialDialog dialog) {
//                                            super.onPositive(dialog);
//                                            dialog.dismiss();
//                                            new OrderDetailFetcher(selectedDetail.getInvoice().getInvoiceId(), selectedDetail.getOutletId()).execute();
//                                        }
//
//                                        @Override
//                                        public void onNegative(MaterialDialog dialog) {
//                                            super.onNegative(dialog);
//                                            dialog.dismiss();
//                                        }
//                                    })
//                                    .build();
//                            materialDialog.show();
//                        } else {
//                            // Details not available to print. Internet not available to download
////                            Toast.makeText(getActivity(), "Order details not available", Toast.LENGTH_SHORT).show();
//                            MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
//                                    .content("Order Details not available to produce")
//                                    .positiveText("Ok")
//                                    .positiveColor(getResources().getColor(R.color.material_alert_neutral_button))
//                                    .callback(new MaterialDialog.ButtonCallback() {
//                                        @Override
//                                        public void onPositive(MaterialDialog dialog) {
//                                            super.onPositive(dialog);
//                                            dialog.dismiss();
//                                        }
//                                    })
//                                    .build();
//                            materialDialog.show();
//                        }
//
//                    }
//                }
//
//            }
//        });
//
//        calendarDatePickerDialogFrom = new CalendarDatePickerDialog();
//        calendarDatePickerDialogFrom.setOnDateSetListener(new CalendarDatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(CalendarDatePickerDialog calendarDatePickerDialog, int year, int month, int day) {
//
//                if (year != mYearFrom || month != mMonthFrom || day != mDayFrom) {
//                    Log.d(LOG_TAG, "Different date selected");
//                    mYearFrom = year;
//                    mMonthFrom = month;
//                    mDayFrom = day;
//
//                    selectedFromTime = TimeUtils.parseIntoTimeInMillis(mYearFrom, mMonthFrom, mDayFrom);
//                    dateFrom.setText(dateFormat.format(new Date(selectedFromTime)));
//
//                    timeFrameChanged = true;
//                }
//
//            }
//        });
//
//        calendarDatePickerDialogTo = new CalendarDatePickerDialog();
//        calendarDatePickerDialogTo.setOnDateSetListener(new CalendarDatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(CalendarDatePickerDialog calendarDatePickerDialog, int year, int month, int day) {
//
//                if (year != mYearTo || month != mMonthTo || day != mDayTo) {
//                    Log.d(LOG_TAG, "Different date selected");
//                    mYearTo = year;
//                    mMonthTo = month;
//                    mDayTo = day;
//
//                    selectedToTime = TimeUtils.parseIntoTimeInMillis(mYearTo, mMonthTo, mDayTo);
//                    dateTo.setText(dateFormat.format(new Date(selectedToTime)));
//
//                    timeFrameChanged = true;
//                }
//
//            }
//        });

//        filterChanged = false;

        return rootView;
    }

//    private void refreshList(int spinnerPosition) {
//
//        historyDetails = dbHandler.getInvoicesOfTimeFrame(TimeUtils.getDayBeginningTime(selectedFromTime),
//                TimeUtils.getDayEndTime(selectedToTime));
//        mapHistoriesWithFreeIssues();
//        adapter.setDetails(historyDetails);
//
//        adapter.setMode(spinnerPosition);
//
//        timeFrameChanged = false;
//        filterChanged = false;
//
//        adapter.notifyDataSetChanged();
//
//    }
//
//    private void mapHistoriesWithFreeIssues() {
//
//        historyFreeMap = new HashMap<>();
//
//        if (historyDetails != null) {
//
//            for (HistoryDetail historyDetail : historyDetails) {
//                Invoice invoice = historyDetail.getInvoice();
//                if (invoice != null) {
//
//                        List<FreeIssueDetail> freeIssueDetails = dbHandler.getFreeIssueDetailByOrderId(invoice.getInvoiceId());
//                    if(freeIssueDetails!=null){
//                        historyFreeMap.put(invoice.getInvoiceId(), freeIssueDetails);
//                    }else{
//                        List<OrderDetail>freeIssueDetailsFromOder=dbHandler.getOrderDetailsByOrderId(invoice.getInvoiceId());
//                        historyfreeMap2.put(invoice.getInvoiceId(), freeIssueDetailsFromOder);
//                    }
//
//
//
//                }
//            }
//
//        }
//
//
//    }
//
    public void refresh() {
        if (adapter != null) adapter.notifyDataSetChanged();
    }
//
//    public void showCalendar() {
//        if (calendarDatePickerDialogFrom != null)
//            calendarDatePickerDialogFrom.show(getFragmentManager(), "TAG");
//    }
//
//    @Override
//    public void onFragmentVisible(DashboardContainerFragment dashboardContainerFragment) {
//        dashboardContainerFragment.currentFragment = this;
//    }

    private static class ViewHolder {
        TextView invoiceId;
        TextView invoiceGross;
        TextView invoiceNet;
        TextView invoiceOutstanding;
        TextView invoiceDiscount;
        ImageView freeIssueIndicator;
        TextView invoiceMarketReturn;
        TextView invoiceFreeItems;
    }

    private static class HeaderViewHolder {
        TextView pinLabel;
    }

    private class InvoiceAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        private List<InvDet> details;
        private LayoutInflater inflater;

//        private List<HistoryDetail> discountedDetails;
//        private List<HistoryDetail> freeIssuedDetails;

        /**
         * 1 : Discounted
         * 2 : Free Issued
         * Else : All
         */
        private int mode = 0;

        public InvoiceAdapter(Context context, List<InvDet> details) {
            this.details = details;
            this.inflater = LayoutInflater.from(context);

            filterDetails();
        }

        private void filterDetails() {
//            discountedDetails = new ArrayList<>();
//            freeIssuedDetails = new ArrayList<>();
//
//            for (HistoryDetail historyDetail : details) {
//                Invoice invoice = historyDetail.getInvoice();
//                if (invoice != null) {
//                    if (invoice.getTotalDiscount() > 0) discountedDetails.add(historyDetail);
//                    if (invoice.hasFree()) freeIssuedDetails.add(historyDetail);
//                }
//            }
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        @Override
        public int getCount() {
            if (details != null) {
                return details.size();
            }
            return 0;
        }

        @Override
        public InvDet getItem(int position) {
            return details.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_invoice_details, null, false);

                viewHolder = new ViewHolder();
                viewHolder.invoiceId = (TextView) convertView.findViewById(R.id.item_invoice_details_tv_invoice_id);
//                viewHolder.invoiceDate = (TextView)convertView.findViewById(R.id.item_invoice_details_tv_invoice_date);
//                viewHolder.dealerName = (TextView)convertView.findViewById(R.id.item_invoice_details_tv_invoice_dealer);
                viewHolder.invoiceGross = (TextView) convertView.findViewById(R.id.item_invoice_details_tv_invoice_gross);
                viewHolder.invoiceNet = (TextView) convertView.findViewById(R.id.item_invoice_details_tv_invoice_net);
                viewHolder.invoiceOutstanding = (TextView) convertView.findViewById(R.id.item_invoice_details_tv_invoice_outstanding);
                viewHolder.invoiceDiscount = (TextView) convertView.findViewById(R.id.item_invoice_details_tv_invoice_discount);
//                viewHolder.hasFreeIndicator = convertView.findViewById(R.id.item_invoice_details_view_free_indicator);
                viewHolder.freeIssueIndicator = (ImageView) convertView.findViewById(R.id.item_invoice_details_cb_free_issue);
                viewHolder.invoiceMarketReturn = (TextView) convertView.findViewById(R.id.item_invoice_details_tv_invoice_market_return);
                viewHolder.invoiceFreeItems = (TextView) convertView.findViewById(R.id.item_invoice_details_cb_free_issue_items);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            InvDet invoice = getItem(position);

            if (invoice != null) {

//                if (invoice.hasFree()) {
//                    viewHolder.freeIssueIndicator.setVisibility(View.VISIBLE);
//
//                    List<FreeIssueDetail> freeIssueDetails = historyFreeMap.get(invoice.getInvoiceId());
//                    if (freeIssueDetails != null && freeIssueDetails.size() > 0) {
//                        StringBuilder freeBuilder = new StringBuilder();
//                        boolean first = true;
//                        for (FreeIssueDetail freeIssueDetail : freeIssueDetails) {
//                            if (freeIssueDetail.getSelectedFreeQty() > 0) {
//
//                                Item item = freeIssueDetail.getItem();
//                                if (item != null) {
//
//                                    if (first) {
//                                        freeBuilder.append("Free : ");
//                                    } else {
//                                        freeBuilder.append("\n");
//                                    }
//
//                                    Flavour flavour = item.getFlavour();
//                                    if (flavour != null) {
//                                        freeBuilder.append(freeIssueDetail.getItem().getItemName())
//                                                .append(" - ")
//                                                .append(flavour.getFlavourName())
//                                                .append(" x ")
//                                                .append(freeIssueDetail.getSelectedFreeQty());
//                                    } else {
//                                        freeBuilder.append(freeIssueDetail.getItem().getItemShortName())
//                                                .append(" x ")
//                                                .append(freeIssueDetail.getSelectedFreeQty());
//                                    }
//
//                                    first = false;
//                                }
//                            }
//                        }
//
//                        viewHolder.invoiceFreeItems.setText(freeBuilder.toString());
//                        viewHolder.invoiceFreeItems.setVisibility(View.VISIBLE);
//                    } else {
//                        viewHolder.invoiceFreeItems.setText("");
//                        viewHolder.invoiceFreeItems.setVisibility(View.GONE);
//                    }
//
//
//                } else {
                    viewHolder.freeIssueIndicator.setVisibility(View.INVISIBLE);
                    viewHolder.invoiceFreeItems.setText("");
                    viewHolder.invoiceFreeItems.setVisibility(View.GONE);
               // }

//                StringBuilder invoiceBuilder = new StringBuilder();
//
//                Outlet outlet = getOutletOfId(historyDetail.getOutletId());
//                if (outlet != null) {
//                    invoiceBuilder.append(outlet.getOutletName()).append("\n");
//                }
//
//                invoiceBuilder.append(invoice.getInvoiceId());
//
////                String invId = String.valueOf(invoice.getInvoiceId());
//                if (invoice.getInvoiceType() == Invoice.OPEN_BALANCE) {
//                    invoiceBuilder.append("*");
//                }

                viewHolder.invoiceId.setText(invoice.getFINVDET_REFNO());
//                viewHolder.invoiceDate.setText(dateFormat.format(new Date(invoice.getInvoiceTime())));
                viewHolder.invoiceDiscount.setText(numberFormat.format(0.0));

//            viewHolder.invoiceId.setText("123123123123");
//            viewHolder.invoiceDate.setText("2015-04-08");
//            viewHolder.dealerName.setText("Test Dealer");
//            viewHolder.invoiceGross.setText(numberFormat.format(180000.00));
//            viewHolder.invoiceNet.setText(numberFormat.format(170000.00));
//            viewHolder.invoiceOutstanding.setText(numberFormat.format(100000.00));

                viewHolder.invoiceGross.setText(numberFormat.format(Double.parseDouble(invoice.getFINVDET_AMT())));

                //double net = Double.parseDouble(invoice.getFINVDET_AMT()) - invoice.getReturnAmount() - invoice.getTotalDiscount();
                double net = Double.parseDouble(invoice.getFINVDET_AMT());


                viewHolder.invoiceNet.setText(numberFormat.format(net));
                viewHolder.invoiceOutstanding.setText(invoice.getFINVDET_QTY());

               // viewHolder.invoiceMarketReturn.setText(numberFormat.format(invoice.getReturnAmount()));
                viewHolder.invoiceMarketReturn.setText(numberFormat.format(0.0));
            }

            return convertView;
        }

//        private Outlet getOutletOfId(int outletId) {
//
//            for (Outlet outlet : outlets) {
//                if (outlet.getOutletId() == outletId) return outlet;
//            }
//
//            return null;
//        }

        public void setDetails(List<InvDet> details) {
            this.details = details;

            filterDetails();

//            notifyDataSetChanged();
        }

        @SuppressLint("InflateParams")
        @Override
        public View getHeaderView(int position, View view, ViewGroup viewGroup) {

            HeaderViewHolder headerViewHolder;
            if (view == null) {
                view = inflater.inflate(R.layout.item_payment_details_header, null, false);

                headerViewHolder = new HeaderViewHolder();
                headerViewHolder.pinLabel = (TextView) view.findViewById(R.id.item_payment_details_tv_pin_txt);

                view.setTag(headerViewHolder);
            } else {
                headerViewHolder = (HeaderViewHolder) view.getTag();
            }

            InvDet invoice = details.get(position);
            // Invoice won't be null. But just in case.
            if (invoice != null) {
              //  headerViewHolder.pinLabel.setText(dateFormat.format(new Date(invoice.getInvoiceTime())));
            }

            return view;
        }

        @Override
        public long getHeaderId(int position) {
            return 0;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();

            double grossTotal = 0;
            double netTotal = 0;
            double outstandingTotal = 0;
            double marketReturnTotal = 0;
            double discountTotal = 0;

            List<InvDet> searchingDetails = details;


            for (InvDet invoice : searchingDetails) {

                if (invoice != null) {
                    grossTotal += Double.parseDouble(invoice.getFINVDET_AMT());
                    netTotal += Double.parseDouble(invoice.getFINVDET_AMT());
//                    outstandingTotal += (invoice.getNetAmount() - invoice.getTotalPaidAmount());
//                    marketReturnTotal += invoice.getReturnAmount();
//                    discountTotal += invoice.getTotalDiscount();
                }
            }

            invoiceGrossTotal.setText(numberFormat.format(grossTotal));
            invoiceNetTotal.setText(numberFormat.format(netTotal));
            invoiceOutstandingTotal.setText(numberFormat.format(outstandingTotal));
            invoiceMarketReturnTotal.setText(numberFormat.format(marketReturnTotal));
            invoiceDiscountTotal.setText(numberFormat.format(discountTotal));

        }
    }



}
