package com.datamation.hmdsfa.view.dashboard;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.DashboardController;
import com.datamation.hmdsfa.controller.FItenrDetController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.helpers.SharedPref;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.Math.round;

public class DaySummaryFragment  extends Fragment {

    private static final String LOG_TAG = DaySummaryFragment.class.getSimpleName();
    private TextView tvDate;

    private TextView tvSalesGross, tvSalesReturn, tvDiscount, tvNetValue, tvTarget,tvProductive,tvNonprdctive;
    private TextView tvDayCredit, tvDayCreditPercentage, tvDayCash, tvDayCashPercentage, tvDayCheque, tvDayChequePercentage;
    //    private TextView tvPreviousCredit, tvPreviousCreditPercentage, tvPreviousCash, tvPreviousCashPercentage, tvPreviousCheque, tvPreviousChequePercentage;
    private TextView tvPreviousCredit, tvPreviousCash, tvPreviousCheque;
    private TextView tvCashTotal, tvChequeTotal;
    private TextView tvTMGross, tvTMNet, tvTMReturn, tvTMDiscount, tvTMTarget, tvTMProductive, tvTMNonProductive;
    private TextView tvPMGross, tvPMNet, tvPMReturn, tvPMDiscount, tvPMTarget, tvPMProductive, tvPMNonProductive;

    // private CalendarDatePickerDialog calendarDatePickerDialog;
    //    private Calendar nowCalendar;
    private int mYear, mMonth, mDay;
    private long timeInMillis;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa", Locale.getDefault());
    private NumberFormat format = NumberFormat.getInstance();

//    private DatabaseHandler dbHandler;
//
//    private List<PaymentPinHolder> pinHolders;
//    private List<Outlet> outlets;

    //private DaySummaryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_day_summary_dspl, container, false);

        timeInMillis = System.currentTimeMillis();

        tvDate = (TextView) rootView.findViewById(R.id.fragment_day_summary_select_date);

        tvDate.setText(dateFormat.format(new Date(timeInMillis)));

        tvSalesGross = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_gross_sale);
        tvSalesReturn = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_market_return);
        tvDiscount = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_discount);
        tvNetValue = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_net_sale);
        tvTarget = (TextView) rootView.findViewById(R.id.dashboard_tv_card_today_target);
        tvProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_today_productive_calls);
        tvNonprdctive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_today_unproductive_calls);

        tvDayCredit = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_day_credit);
        tvDayCreditPercentage = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_day_credit_percentage);
        tvDayCash = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_day_cash);
        tvDayCashPercentage = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_day_cash_percentage);
        tvDayCheque = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_day_cheque);
        tvDayChequePercentage = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_day_cheque_percentage);

        tvPreviousCredit = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_previous_credit);
//        tvPreviousCreditPercentage = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_previous_credit_percentage);
        tvPreviousCash = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_previous_cash);
//        tvPreviousCashPercentage = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_previous_cash_percentage);
        tvPreviousCheque = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_previous_cheque);
//        tvPreviousChequePercentage = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_previous_cheque_percentage);

        tvCashTotal = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_total_cash);
        tvChequeTotal = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_total_cheque);

        tvTMGross        = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_gross_sale);
        tvTMNet          = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_net_sale);
        tvTMReturn       = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_market_return);
        tvTMDiscount     = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_discount);
        tvTMTarget       = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_target);
        tvTMProductive   = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_productive_calls);
        tvTMNonProductive= (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_unproductive_calls);

        tvPMGross        = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_gross_sale);
        tvPMNet          = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_net_sale);
        tvPMReturn       = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_market_return);
        tvPMDiscount     = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_discount);
        tvPMTarget       = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_target);
        tvPMProductive   = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_productive_calls);
        tvPMNonProductive= (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_unproductive_calls);

        String route = "";
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));

        String curdate = curYear+"-"+ String.format("%02d", curMonth) + "-" + String.format("%02d", curDate);


        double dailyAchieve = new DashboardController(getActivity()).getDailyAchievement();
        double dailyTarget = new DashboardController(getActivity()).getRepTarget()/30;
        double dailyDiscount = new DashboardController(getActivity()).getTodayDiscount();
        double dailyReturn = new DashboardController(getActivity()).getTodayReturn();
        double dayCash = new DashboardController(getActivity()).getTodayCashCollection();
        double dayCheque = new DashboardController(getActivity()).getTodayChequeCollection();
        double previousCash = new DashboardController(getActivity()).getTodayCashPreviousCollection();
        double previousCheque = new DashboardController(getActivity()).getTodayChequePreviousCollection();
        int nonprd = new DashboardController(getActivity()).getNonPrdCount();
        int ordcount = new DashboardController(getActivity()).getProductiveCount();
        if(!new FItenrDetController(getActivity()).getRouteFromItenary(curdate).equals("")) {
            route = new DashboardController(getActivity()).getRoute();
        }else{
            route = new RouteDetController(getActivity()).getRouteCodeByDebCode(SharedPref.getInstance(getActivity()).getSelectedDebCode());
        }
        int outlets = new DashboardController(getActivity()).getOutletCount(route);
        int notVisit = outlets - (ordcount+nonprd);
        if(notVisit > 0){
            notVisit = outlets - (ordcount+nonprd);
        }else{
            notVisit = 0;
        }
        double thisMonthTarget = new DashboardController(getActivity()).getRepTarget();
        double preMonthTarget = new DashboardController(getActivity()).getPMRepTarget();
        double thisMonthDiscount = new DashboardController(getActivity()).getTMDiscounts();
        double preMonthDiscount = new DashboardController(getActivity()).getPMDiscounts();
        double thisMonthAchieve = new DashboardController(getActivity()).getMonthAchievement();
        double preMonthAchieve = new DashboardController(getActivity()).getPMonthAchievement();
        double thisMonthReturn = new DashboardController(getActivity()).getTMReturn();
        double preMonthReturn = new DashboardController(getActivity()).getPMReturn();

        int tMordcount = new DashboardController(getActivity()).getTMProductiveCount();
        int pMordcount = new DashboardController(getActivity()).getPMProductiveCount();

        int tMNpcount = new DashboardController(getActivity()).getTMNonPrdCount();
        int pMNpcount = new DashboardController(getActivity()).getPMNonPrdCount();


        tvTMGross.setText(""+format.format(thisMonthAchieve));
        tvTMNet.setText(""+format.format(thisMonthAchieve-thisMonthDiscount-thisMonthReturn));
        tvTMReturn.setText(""+format.format(thisMonthReturn));
        tvTMDiscount.setText(""+format.format(thisMonthDiscount));
        tvTMTarget.setText(""+format.format(thisMonthTarget));
        tvTMProductive.setText(""+tMordcount);
        tvTMNonProductive.setText(""+tMNpcount);

        tvPMGross.setText(""+format.format(preMonthAchieve));
        tvPMNet.setText(""+format.format(preMonthAchieve-preMonthReturn-preMonthDiscount));
        tvPMReturn.setText(""+format.format(preMonthReturn));
        tvPMDiscount.setText(""+format.format(preMonthDiscount));
        tvPMTarget.setText(""+format.format(preMonthTarget));
        tvPMProductive.setText(""+pMordcount);
        tvPMNonProductive.setText(""+pMNpcount);



        tvSalesGross.setText(""+format.format(dailyAchieve));
        tvNetValue.setText(""+format.format(dailyAchieve));
        tvTarget.setText(""+format.format(dailyTarget));
        tvDiscount.setText("" +format.format(dailyDiscount));
        tvSalesReturn.setText("" +format.format(dailyReturn));
        tvProductive.setText(""+ordcount);
        tvNonprdctive.setText(""+nonprd);
        tvDayCash.setText(""+format.format(dayCash));
        tvDayCheque.setText(""+format.format(dayCheque));
        tvPreviousCash.setText(""+format.format(previousCash));
        tvPreviousCheque.setText(""+format.format(previousCheque));
        tvCashTotal.setText(""+format.format(dayCash+previousCash));
        tvChequeTotal.setText(""+format.format(dayCheque+previousCheque));
        //TODO::dailyDiscount,dailyDiscount should be set after create tables(FOrdDisc,fInvRdet)

        return rootView;
    }

//    private void calculateValues() {
//
//        double todayCredits = 0;
//        double todayCash = 0;
//        double todayCheque = 0;
//
//        double previousCredits = 0;
//        double previousCash = 0;
//        double previousCheque = 0;
//
//        double totalDayGrossSale = 0;
//        double totalDayDiscount = 0;
//        double totalDayMarketReturn = 0;
//        double totalDayNetSale = 0;
//
////        double totalPreviousGrossSale = 0;
////        double totalPreviousDiscount = 0;
////        double totalPreviousMarketReturn = 0;
////        double totalPreviousNetSale = 0;
//
//        if (pinHolders != null) {
//            for (PaymentPinHolder pinHolder : pinHolders) {
//                if (pinHolder.getType() == PaymentPinHolder.TYPE_DAY) {
//                    // Day collection
//                    Invoice invoice = pinHolder.getHistoryDetail().getInvoice();
//                    if (invoice != null) {
//
//                        todayCash += invoice.getTotalCashPayments();
//                        todayCheque += invoice.getTotalChequePayments();
//
//                        if (invoice.getInvoiceType() == Invoice.SALES_ORDER) {
//                            totalDayGrossSale += invoice.getTotalAmount();
//                            totalDayDiscount += invoice.getTotalDiscount();
//                            totalDayMarketReturn += invoice.getReturnAmount();
//                            totalDayNetSale += invoice.getNetAmount();
//
//                            todayCredits += invoice.getNetAmount() - invoice.getTotalPaidAmount();
//                        }
//
//                    }
//                } else {
//                    // Other day collection
//                    Invoice invoice = pinHolder.getHistoryDetail().getInvoice();
//                    if (invoice != null) {
//                        previousCredits += invoice.getNetAmount() - invoice.getTotalPaidAmount();
//                        previousCash += invoice.getTotalCashPayments();
//                        previousCheque += invoice.getTotalChequePayments();
//
////                        if (invoice.getInvoiceType() == Invoice.SALES_ORDER) {
////                            totalPreviousGrossSale += invoice.getTotalAmount();
////                            totalPreviousDiscount += invoice.getTotalDiscount();
////                            totalPreviousMarketReturn += invoice.getReturnAmount();
////                            totalPreviousNetSale += invoice.getNetAmount();
////                        }
//
//                    }
//                }
//            }
//        }
//
////        tvSalesGross.setText(format.format(totalDayGrossSale + totalPreviousGrossSale));
////        tvDiscount.setText(format.format(totalDayDiscount + totalPreviousDiscount));
////        tvSalesMarketReturn.setText(format.format(totalDayMarketReturn + totalPreviousMarketReturn));
////        tvNetValue.setText(format.format(totalDayNetSale + totalPreviousNetSale));
//
//        tvSalesGross.setText(format.format(totalDayGrossSale));
//        tvDiscount.setText(format.format(totalDayDiscount));
//        tvSalesMarketReturn.setText(format.format(totalDayMarketReturn));
//        tvNetValue.setText(format.format(totalDayNetSale));
//
//        tvDayCredit.setText(format.format(todayCredits));
//        tvDayCash.setText(format.format(todayCash));
//        tvDayCheque.setText(format.format(todayCheque));
//
//        tvPreviousCredit.setText(format.format(previousCredits));
//        tvPreviousCash.setText(format.format(previousCash));
//        tvPreviousCheque.setText(format.format(previousCheque));
//
////        if (totalDayNetSale + totalPreviousNetSale > 0) {
////            tvDayCreditPercentage.setText("(" + format.format(todayCredits / (totalDayNetSale + totalPreviousNetSale) * 100) + "%)");
////            tvDayCashPercentage.setText("(" + format.format(todayCash / (totalDayNetSale + totalPreviousNetSale) * 100) + "%)");
////            tvDayChequePercentage.setText("(" + format.format(todayCheque / (totalDayNetSale + totalPreviousNetSale) * 100) + "%)");
////
////            tvPreviousCreditPercentage.setText("(" + format.format(todayCredits / (totalPreviousNetSale + totalPreviousNetSale) * 100) + "%)");
////            tvPreviousCashPercentage.setText("(" + format.format(todayCash / (totalPreviousNetSale + totalPreviousNetSale) * 100) + "%)");
////            tvPreviousChequePercentage.setText("(" + format.format(todayCheque / (totalPreviousNetSale + totalPreviousNetSale) * 100) + "%)");
////        }
//
//        if (totalDayNetSale > 0) {
//            tvDayCreditPercentage.setText("(" + format.format(todayCredits / (totalDayNetSale) * 100) + "%)");
//            tvDayCashPercentage.setText("(" + format.format(todayCash / (totalDayNetSale) * 100) + "%)");
//            tvDayChequePercentage.setText("(" + format.format(todayCheque / (totalDayNetSale) * 100) + "%)");
//
////            tvPreviousCreditPercentage.setText("(" + format.format(todayCredits / (totalPreviousNetSale + totalPreviousNetSale) * 100) + "%)");
////            tvPreviousCashPercentage.setText("(" + format.format(todayCash / (totalPreviousNetSale + totalPreviousNetSale) * 100) + "%)");
////            tvPreviousChequePercentage.setText("(" + format.format(todayCheque / (totalPreviousNetSale + totalPreviousNetSale) * 100) + "%)");
//        }
//
//        tvCashTotal.setText(format.format(todayCash + previousCash));
//        tvChequeTotal.setText(format.format(todayCheque + previousCheque));
//
//    }
//
//    public void showCalendar() {
//        calendarDatePickerDialog.show(getFragmentManager(), "TAG");
//    }
//
//    public void refresh() {
//        if (adapter != null) adapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onFragmentVisible(DashboardContainerFragment dashboardContainerFragment) {
//        dashboardContainerFragment.currentFragment = this;
//    }

    private static class ViewHolder {

        TextView openBalanceIndicator;

        TextView invoiceId, dealerName, invoiceDate;
        TextView grossSale, marketReturn, discount, netSale;
        TextView creditHeader, credit, cashHeader, cash, chequeHeader, cheque, chequeNoHeader,
                chequeNo, chequeDateHeader, chequeDate, days;
    }

    private static class HeaderViewHolder {
        View emptyView;
        TextView pinLabel;
    }

//    private class DaySummaryAdapter extends BaseAdapter implements StickyListHeadersAdapter {
//
//        private LayoutInflater inflater;
//        private List<PaymentPinHolder> paymentPinHolders;
//
//        private DaySummaryAdapter(Context context, @NonNull List<PaymentPinHolder> paymentPinHolders) {
//            this.paymentPinHolders = paymentPinHolders;
//            inflater = LayoutInflater.from(context);
//
//            filterList();
//        }
//
//        private void filterList() {
//            boolean dayOpenBalanceFound = false;
//            boolean previousOpenBalanceFound = false;
//
//            for(PaymentPinHolder paymentPinHolder : pinHolders) {
//                Invoice invoice = paymentPinHolder.getHistoryDetail().getInvoice();
//                if(invoice != null && invoice.getInvoiceType() == Invoice.OPEN_BALANCE) {
//                    if(!dayOpenBalanceFound && paymentPinHolder.getType() == PaymentPinHolder.TYPE_DAY) {
//                        paymentPinHolder.setIsHeader(true);
//                        dayOpenBalanceFound = true;
//                    }
//
//                    if(!previousOpenBalanceFound && paymentPinHolder.getType() == PaymentPinHolder.TYPE_OTHER) {
//                        paymentPinHolder.setIsHeader(true);
//                        previousOpenBalanceFound = true;
//                    }
//                }
//
//                if(dayOpenBalanceFound && previousOpenBalanceFound) break;
//            }
//
//        }
//
//        @Override
//        public View getHeaderView(int position, View view, ViewGroup viewGroup) {
//            HeaderViewHolder headerViewHolder;
//            if (view == null) {
//                view = inflater.inflate(R.layout.item_payment_details_header, null, false);
//
//                headerViewHolder = new HeaderViewHolder();
//                headerViewHolder.pinLabel = (TextView) view.findViewById(R.id.item_payment_details_tv_pin_txt);
//                headerViewHolder.emptyView = view.findViewById(R.id.item_payment_details_view_empty);
//
////                headerViewHolder.emptyView.setVisibility(View.VISIBLE);
//
//                view.setTag(headerViewHolder);
//            } else {
//                headerViewHolder = (HeaderViewHolder) view.getTag();
//            }
//
//            String label;
//
//            if (paymentPinHolders.get(position).getType() == PaymentPinHolder.TYPE_DAY) {
//                // Selected day invoice
//                label = "Invoiced and Collected on " + dateFormat.format(paymentPinHolders.get(position).getHistoryDetail().getDate());
//            } else {
//                // Earlier invoice
//                label = "Previous Collections ";
//            }
//
//            headerViewHolder.pinLabel.setText(label);
//
//            return view;
//        }
//
//        @Override
//        public long getHeaderId(int position) {
//            if (paymentPinHolders != null) return paymentPinHolders.get(position).getType();
//            return 0;
//        }
//
//        @Override
//        public int getCount() {
//            if (paymentPinHolders != null) return paymentPinHolders.size();
//            return 0;
//        }
//
//        @Override
//        public PaymentPinHolder getItem(int position) {
//            if (paymentPinHolders != null) return paymentPinHolders.get(position);
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            ViewHolder viewHolder;
//            if (convertView == null) {
//                convertView = inflater.inflate(R.layout.item_card_summary_day_invoices, null, false);
//
//                viewHolder = new ViewHolder();
//
//                viewHolder.openBalanceIndicator = (TextView) convertView.findViewById(R.id.day_summary_tv_open_balance_header);
//
//                viewHolder.invoiceId = (TextView) convertView.findViewById(R.id.card_day_summary_tv_invoice_id);
//                viewHolder.dealerName = (TextView) convertView.findViewById(R.id.card_day_summary_tv_invoice_dealer_name);
//                viewHolder.invoiceDate = (TextView) convertView.findViewById(R.id.card_day_summary_tv_invoice_date);
//
//                viewHolder.grossSale = (TextView) convertView.findViewById(R.id.card_day_tv_gross_sale);
//                viewHolder.marketReturn = (TextView) convertView.findViewById(R.id.card_day_tv_market_return);
//                viewHolder.discount = (TextView) convertView.findViewById(R.id.card_day_tv_discount);
//                viewHolder.netSale = (TextView) convertView.findViewById(R.id.card_day_tv_net_sale);
//
//                viewHolder.creditHeader = (TextView) convertView.findViewById(R.id.card_day_tv_credit_header);
//                viewHolder.credit = (TextView) convertView.findViewById(R.id.card_day_tv_credit);
//
//                viewHolder.cashHeader = (TextView) convertView.findViewById(R.id.card_day_tv_cash_header);
//                viewHolder.cash = (TextView) convertView.findViewById(R.id.card_day_tv_cash);
//
//                viewHolder.chequeHeader = (TextView) convertView.findViewById(R.id.card_day_tv_cheque_header);
//                viewHolder.cheque = (TextView) convertView.findViewById(R.id.card_day_tv_cheque);
//
//                viewHolder.chequeNoHeader = (TextView) convertView.findViewById(R.id.card_day_tv_cheque_no_header);
//                viewHolder.chequeNo = (TextView) convertView.findViewById(R.id.card_day_tv_cheque_no);
//
//                viewHolder.chequeDateHeader = (TextView) convertView.findViewById(R.id.card_day_tv_cheque_date_header);
//                viewHolder.chequeDate = (TextView) convertView.findViewById(R.id.card_day_tv_cheque_date);
//
//                viewHolder.days = (TextView) convertView.findViewById(R.id.card_day_tv_days);
//
//                convertView.setTag(viewHolder);
//
//            } else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//
//            PaymentPinHolder holder = paymentPinHolders.get(position);
//            Invoice invoice = holder.getHistoryDetail().getInvoice();
//
//            if(holder.isHeader()) {
//                viewHolder.openBalanceIndicator.setVisibility(View.VISIBLE);
//            } else {
//                viewHolder.openBalanceIndicator.setVisibility(View.GONE);
//            }
//
//            for (Outlet outlet : outlets) {
//                if (outlet.getOutletId() == holder.getHistoryDetail().getOutletId()) {
//                    viewHolder.dealerName.setText(outlet.getOutletName());
//                    break;
//                }
//            }
//
//            if (invoice != null) {
//                String invId = "ID : " + invoice.getInvoiceId();
//                if (invoice.getInvoiceType() == Invoice.OPEN_BALANCE) {
//                    invId = invId + "*";
//                }
//
//                viewHolder.invoiceId.setText(invId);
//                viewHolder.invoiceDate.setText(dateTimeFormat.format(invoice.getInvoiceTime()));
//
//                viewHolder.grossSale.setText(format.format(invoice.getTotalAmount()));
//                viewHolder.marketReturn.setText(format.format(invoice.getReturnAmount()));
//                viewHolder.discount.setText(format.format(invoice.getTotalDiscount()));
//
//                viewHolder.netSale.setText(format.format(invoice.getTotalAmount()
//                        - invoice.getTotalDiscount()
//                        - invoice.getReturnAmount()));
//
//                double credit = invoice.getTotalAmount()
//                        - invoice.getReturnAmount()
//                        - invoice.getTotalDiscount()
//                        - invoice.getTotalPaidAmount();
//                if (holder.getType() == PaymentPinHolder.TYPE_DAY && credit > 0) {
//                    viewHolder.creditHeader.setVisibility(View.VISIBLE);
//                    viewHolder.credit.setText(format.format(credit));
//                    viewHolder.credit.setVisibility(View.VISIBLE);
//                } else {
//                    viewHolder.creditHeader.setVisibility(View.INVISIBLE);
//                    viewHolder.credit.setVisibility(View.INVISIBLE);
//                }
//
//                viewHolder.cash.setText(format.format(invoice.getTotalCashPayments()));
//                if (invoice.getTotalCashPayments() > 0) {
//                    viewHolder.cash.setVisibility(View.VISIBLE);
//                    viewHolder.cashHeader.setVisibility(View.VISIBLE);
//                } else {
//                    viewHolder.cash.setVisibility(View.INVISIBLE);
//                    viewHolder.cashHeader.setVisibility(View.INVISIBLE);
//                }
//
//                viewHolder.cheque.setText(format.format(invoice.getTotalChequePayments()));
//                if (invoice.getTotalChequePayments() > 0) {
//                    if (invoice.getChequePayments() != null && invoice.getChequePayments().size() > 0) {
//                        viewHolder.chequeNo.setText(invoice.getChequePayments().get(0).getChequeNo());
//                        viewHolder.chequeDate.setText(dateFormat.format(new Date(invoice.getChequePayments().get(0).getChequeDate())));
//                        int numOfDays = (int) ((System.currentTimeMillis() - invoice.getInvoiceTime()) / ValueHolder.DAY_IN_MILLIS);
//                        viewHolder.days.setText(String.valueOf(numOfDays));
//                    }
//                    viewHolder.cheque.setVisibility(View.VISIBLE);
//                    viewHolder.chequeHeader.setVisibility(View.VISIBLE);
//
//                    viewHolder.chequeNo.setVisibility(View.VISIBLE);
//                    viewHolder.chequeNoHeader.setVisibility(View.VISIBLE);
//
//                    viewHolder.chequeDate.setVisibility(View.VISIBLE);
//                    viewHolder.chequeDateHeader.setVisibility(View.VISIBLE);
//                } else {
//                    viewHolder.cheque.setVisibility(View.INVISIBLE);
//                    viewHolder.chequeHeader.setVisibility(View.INVISIBLE);
//
//                    viewHolder.chequeNo.setVisibility(View.INVISIBLE);
//                    viewHolder.chequeNoHeader.setVisibility(View.INVISIBLE);
//
//                    viewHolder.chequeDate.setVisibility(View.INVISIBLE);
//                    viewHolder.chequeDateHeader.setVisibility(View.INVISIBLE);
//                }
//
//            }
//
//            return convertView;
//        }
//
//        public void setPaymentPinHolders(List<PaymentPinHolder> paymentPinHolders) {
//            this.paymentPinHolders = paymentPinHolders;
//            notifyDataSetChanged();
//        }
//    }
//    private void setPreviousMonthDetails(JSONObject previousMonth) throws JSONException {
//
//        double target = previousMonth.getDouble("target");
//        double grossSales = previousMonth.getDouble("gross_sale");
//        double discount = previousMonth.getDouble("discount");
//        double discountPercentage = previousMonth.getDouble("discount_percentage");
//        double returns = previousMonth.getDouble("returns");
//
//        double netSale = grossSales - discount - returns;
//
//        tvPrevMonthTarget.setText(numberFormat.format(target));
//        tvPrevMonthGrossSale.setText(numberFormat.format(grossSales));
//
//        if (grossSales > 0 && netSale > 0) {
//            tvPrevMonthGrossSalePercentage.setText("(" + numberFormat.format(netSale / grossSales * 100) + "%)");
//        } else {
//            tvPrevMonthGrossSalePercentage.setText("(0.00%)");
//        }
//
//        tvPrevMonthMarketReturn.setText(numberFormat.format(returns));
//        tvPrevMonthDiscount.setText(numberFormat.format(discount));
//        tvPrevMonthDiscountPercentage.setText("(" + numberFormat.format(discountPercentage) + "%)");
//
//        tvPrevMonthNetSale.setText(numberFormat.format(netSale));
//
//        tvPrevMonthProductive.setText(previousMonth.getString("productive_calls"));
//        tvPrevMonthUnproductive.setText(previousMonth.getString("unproductive_cals"));
//
//    }
//
//    private void setThisMonthDetails(JSONObject thisMonth) throws JSONException {
//
//        Log.d(LOG_TAG, "This month : \n" + thisMonth);
//
////        targetValues = new ArrayList<>();
//        achievementValues = new ArrayList<>();
//
//        double monthTarget = 0;
//        double monthGrossSales = 0;
//        double monthDiscount = 0;
//        double monthDiscountPercentage = 0;
//        double monthReturns = 0;
//        double monthNetSale = 0;
//
//        int monthProductive = 0;
//        int monthUnproductive = 0;
//
////        double target = 0;
////        double grossSales = 0;
////        double discount = 0;
////        double discountPercentage = 0;
////        double returns = 0;
//        double netSale = 0;
//
//        int discountPercentageCount = 0;
////
////        int productive = 0;
////        int unproductive = 0;
//
//        boolean targetsNeeded = targetValues.size() == 0;
//
//        if(targetsNeeded) targetValues.add(0.0);
//
//        achievementValues.add(0.0);
//
//        for (int dayIndex = 1; dayIndex < 32; dayIndex++) {
//
//            if (thisMonth.has(String.valueOf(dayIndex))) {
//
//                JSONObject dayJSON = thisMonth.getJSONObject(String.valueOf(dayIndex));
//
//                if(targetsNeeded) targetValues.add(dayJSON.getDouble("target"));
//
//                if(dayIndex <= thisDay) achievementValues.add(dayJSON.getDouble("gross_sale"));
//
//                monthTarget += dayJSON.getDouble("target");
//                monthGrossSales += dayJSON.getDouble("gross_sale");
//                monthDiscount += dayJSON.getDouble("discount");
//                monthReturns += dayJSON.getDouble("returns");
//                monthNetSale += netSale;
//
//                monthProductive += dayJSON.getInt("productive_calls");
//                monthUnproductive += dayJSON.getInt("unproductive_cals");
//
//                if(dayJSON.getDouble("discount_percentage") > 0) {
//                    monthDiscountPercentage += dayJSON.getDouble("discount_percentage");
//                    discountPercentageCount++;
//                }
//
//            } else break;
//        }
//
//        tvThisMonthTarget.setText(numberFormat.format(monthTarget));
//        tvThisMonthGrossSale.setText(numberFormat.format(monthGrossSales));
//
//        tvThisMonthMarketReturn.setText(numberFormat.format(monthReturns));
//        tvThisMonthDiscount.setText(numberFormat.format(monthDiscount));
////        tvThisMonthDiscountPercentage.setText("(" + "0.00" + "%)");
//
//        tvThisMonthNetSale.setText(numberFormat.format(monthNetSale));
//
//        tvThisMonthProductive.setText(String.valueOf(monthProductive));
//        tvThisMonthUnproductive.setText(String.valueOf(monthUnproductive));
//
//        if(monthDiscountPercentage > 0) {
//            tvThisMonthDiscountPercentage.setText("("
//                    + numberFormat.format(monthDiscountPercentage/discountPercentageCount) + "%)");
//        } else {
//            tvThisMonthDiscountPercentage.setText("(0.00%");
//        }
//
//        if(monthTarget > 0) {
//            tvThisMonthGrossSalePercentage.setText("("
//                    + numberFormat.format(monthGrossSales/monthTarget * 100) + "%)");
//        } else {
//            tvThisMonthGrossSalePercentage.setText("N/A");
//        }
//
//    }
//

//    private void setTodayDetails(JSONObject dayJSON) throws JSONException {
//
//        double target;
//        double grossSales;
//        double discount;
//        double discountPercentage;
//        double returns;
//        double netSale;
//
//        int productive;
//        int unproductive;
//
//        target = dayJSON.getDouble("target");
//        grossSales = dayJSON.getDouble("gross_sale");
//        discount = dayJSON.getDouble("discount");
//        discountPercentage = dayJSON.getDouble("discount_percentage");
//        returns = dayJSON.getDouble("returns");
//
//        netSale = grossSales - discount - returns;
//
//        productive = dayJSON.getInt("productive_calls");
//        unproductive = dayJSON.getInt("unproductive_cals");
//
//        tvTodayTarget.setText(numberFormat.format(target));
//        tvTodayGrossSale.setText(numberFormat.format(grossSales));
//
//        if (target > 0) {
//            tvTodayGrossSalePercentage.setText("("
//                    + numberFormat.format(grossSales / target * 100) + "%)");
//        } else {
//            tvTodayGrossSalePercentage.setText("N/A");
//        }
//
//        tvTodayMarketReturn.setText(numberFormat.format(returns));
//        tvTodayDiscount.setText(numberFormat.format(discount));
//        tvTodayDiscountPercentage.setText("(" + numberFormat.format(discountPercentage) + "%)");
//
//        tvTodayNetSale.setText(numberFormat.format(netSale));
//
//        tvTodayProductive.setText(String.valueOf(productive));
//        tvTodayUnproductive.setText(String.valueOf(unproductive));
//    }

}


