package com.datamation.swdsfa.view.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.DashboardController;
import com.datamation.swdsfa.controller.DayNPrdHedController;
import com.datamation.swdsfa.controller.FItenrDetController;
import com.datamation.swdsfa.controller.ItemController;
import com.datamation.swdsfa.controller.RouteDetController;
import com.datamation.swdsfa.controller.SalRepController;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.Item;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static java.lang.Math.round;

/**
 * Used to display the summary of a selected day.
 */
public class DaySummaryFragment extends Fragment {

    private static final String LOG_TAG = DaySummaryFragment.class.getSimpleName();
    private TextView tvDate;

    private TextView tvSalesGross, tvDiscount, tvNetValue, tvTarget, tvProductive, tvNonprdctive, tvTotalcalls, tvProductivePrecentage, tvTonnage, tvUReturn, tvMReturn, tvfreecom, tvfreedist,tvavgLineNumber;
    private TextView tvDayCredit, tvDayCreditPercentage, tvDayCash, tvDayCashPercentage, tvDayCheque, tvDayChequePercentage;
    //    private TextView tvPreviousCredit, tvPreviousCreditPercentage, tvPreviousCash, tvPreviousCashPercentage, tvPreviousCheque, tvPreviousChequePercentage;
    private TextView tvPreviousCredit, tvPreviousCash, tvPreviousCheque;
    private TextView tvCashTotal, tvChequeTotal;
    private TextView tvTMGross, tvTMNet, tvTMReturn, tvTMDiscount, tvTMTarget, tvTMProductive, tvTMNonProductive, tvTMInvSale, tvTMTargetAchivement;
    private TextView tvPMGross, tvPMNet, tvPMReturn, tvPMDiscount, tvPMTarget, tvPMProductive, tvPMNonProductive,tvPMInvSale, tvPMTargetAchivement;

    private int mYear, mMonth, mDay;
    private long timeInMillis;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa", Locale.getDefault());
    private NumberFormat format = NumberFormat.getInstance();

    SharedPref pref;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.day_summary_responsive_layout, container, false);

        timeInMillis = System.currentTimeMillis();

        pref = SharedPref.getInstance(getContext());

        format.setGroupingUsed(true);
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(0);

        tvDate = (TextView) rootView.findViewById(R.id.fragment_day_summary_select_date);

        tvDate.setText(dateFormat.format(new Date(timeInMillis)));

        tvSalesGross = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_gross_sale);
        tvDiscount = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_discount);
        tvNetValue = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_net_sale);
        tvUReturn = (TextView) rootView.findViewById(R.id.fragment_day_usable_return_value);
        tvMReturn = (TextView) rootView.findViewById(R.id.dashboard_tv_card_today_market_return_value);
        tvTarget = (TextView) rootView.findViewById(R.id.dashboard_tv_card_today_target);
        tvProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_today_productive_calls);
        tvNonprdctive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_today_unproductive_calls);
        tvTotalcalls = (TextView) rootView.findViewById(R.id.dashboard_tv_card_total_calls_value);
        tvProductivePrecentage = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_np_percentage);
        tvfreecom = (TextView) rootView.findViewById(R.id.dashboard_tv_card_today_free_company_value);
        tvfreedist = (TextView) rootView.findViewById(R.id.dashboard_tv_card_total_free_mannual_value);
        tvavgLineNumber = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_linePerInvoice_value);

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

        tvTMGross = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_gross_sale);
        tvTMNet = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_net_sale);
        tvTMReturn = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_market_return);
        tvTMDiscount = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_discount);
        tvTMTarget = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_target);
        tvTMProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_productive_calls);
        tvTMNonProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_unproductive_calls);
        tvTMInvSale = (TextView) rootView.findViewById(R.id.dashboard_tv_card_this_month_invoice_sale);
        tvTMTargetAchivement = (TextView) rootView.findViewById(R.id.dashboard_tv_card_target_Achivement_precentage_value);

        tvPMGross = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_gross_sale);
        tvPMNet = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_net_sale);
        tvPMReturn = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_market_return);
        tvPMDiscount = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_discount);
        tvPMTarget = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_target);
        tvPMProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_productive_calls);
        tvPMNonProductive = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_unproductive_calls);
        tvPMInvSale = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_invoice_gross_sale);
        tvPMTargetAchivement = (TextView) rootView.findViewById(R.id.dashboard_tv_card_prev_month_target_Achivement_precentage_value);


        tvTonnage = (TextView) rootView.findViewById(R.id.fragment_day_summary_card_tv_tonnage_value);

        double dailyAchieve = new DashboardController(getActivity()).getDailyAchievement();
        double dailyTarget = new DashboardController(getActivity()).getRepTarget() / 30;
        double dailyDiscount = new DashboardController(getActivity()).getTodayDiscount();
        double dailyReturn = new DashboardController(getActivity()).getTodayReturn();
        double dayCash = new DashboardController(getActivity()).getTodayCashCollection();
        double dayCheque = new DashboardController(getActivity()).getTodayChequeCollection();
        double previousCash = new DashboardController(getActivity()).getTodayCashPreviousCollection();
        double previousCheque = new DashboardController(getActivity()).getTodayChequePreviousCollection();
        double usableReturn = new DashboardController(getActivity()).getTodayUsableReturn();
        double marketReturn = new DashboardController(getActivity()).getTodayMarketReturn();
        int freecompany = new DashboardController(getActivity()).getFreeCompany();
        int freedistributor = new DashboardController(getActivity()).getFreeDistributor();


        int nonprd = new DashboardController(getActivity()).getNonPrdCount();
        int ordcount = new DashboardController(getActivity()).getProductiveCount();

        String route = "";
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));

        String curdate = curYear+"-"+ String.format("%02d", curMonth) + "-" + String.format("%02d", curDate);
        if(!new FItenrDetController(getActivity()).getRouteFromItenary(curdate).equals("")) {
            route = new DashboardController(getActivity()).getRoute();
        }else{
            route = new RouteDetController(getActivity()).getRouteCodeByDebCode(SharedPref.getInstance(getActivity()).getSelectedDebCode());
        }


        int outlets = new DashboardController(getActivity()).getOutletCount(route);
        int notVisit = outlets - (ordcount + nonprd);

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

        int orderHedQty = new DashboardController(getActivity()).getNumberOfOrder();
        int orderDetLine = new DashboardController(getActivity()).getNumberOfOrderDet();

        double PrefTMInvSale = Double.parseDouble(pref.getTMInvSale());
        double PrefTMReturn = Double.parseDouble(pref.getTMReturn());

        double PrefPMInvSale = Double.parseDouble(pref.getPMInvSale());
        double PrefPMReturn = Double.parseDouble(pref.getPMReturn());

        double TM_RD_Value = PrefTMInvSale - PrefTMReturn ;
        double PM_RD_Value = PrefPMInvSale - PrefPMReturn;

       // tvTMGross.setText("" + format.format(thisMonthAchieve+thisMonthDiscount));
        tvTMGross.setText(""+format.format(Double.parseDouble(pref.getTMOrdSale())+dailyAchieve));
        tvTMNet.setText("" + format.format(TM_RD_Value));
        //tvTMNet.setText("" + format.format(thisMonthAchieve - thisMonthDiscount + thisMonthReturn));
        //tvTMReturn.setText("" + (usableReturn + marketReturn));
        //tvTMReturn.setText("" + format.format(thisMonthReturn));
        tvTMReturn.setText(""+format.format(Double.parseDouble(pref.getTMReturn())));
        tvUReturn.setText("" + format.format(usableReturn));
        tvMReturn.setText("" + format.format(marketReturn));
        tvTMDiscount.setText("" + format.format(thisMonthDiscount));
        tvTMTarget.setText("" + format.format(thisMonthTarget));
        tvTMProductive.setText("" + tMordcount);
        tvTMNonProductive.setText("" + tMNpcount);
        tvTMInvSale.setText(""+format.format(Double.parseDouble(pref.getTMInvSale())));

      if(thisMonthTarget > 0)
      {
          DecimalFormat df = new DecimalFormat("####0.00");
          tvTMTargetAchivement.setText(df.format((new Double(TM_RD_Value) / new Double(thisMonthTarget)) * 100.0) + "%");
      }

        tvfreecom.setText("" + freecompany);
        tvfreedist.setText("" + freedistributor);


        //tvPMGross.setText("" + format.format(preMonthAchieve+preMonthDiscount));
        tvPMGross.setText(""+format.format(Double.parseDouble(pref.getPMOrdSale())));
        tvPMNet.setText("" + format.format(PM_RD_Value));
        //tvPMReturn.setText("" + format.format(preMonthReturn));
        tvPMReturn.setText(""+format.format(Double.parseDouble(pref.getPMReturn())));
        tvPMDiscount.setText("" + format.format(preMonthDiscount));
        tvPMTarget.setText("" + format.format(preMonthTarget));
        tvPMProductive.setText("" + pMordcount);
        tvPMNonProductive.setText("" + pMNpcount);
        tvPMInvSale.setText(""+format.format(Double.parseDouble(pref.getPMInvSale())));
        if(preMonthTarget > 0)
        {
            DecimalFormat df = new DecimalFormat("####0.00");
            tvPMTargetAchivement.setText(df.format((new Double(PM_RD_Value) / new Double(preMonthTarget)) * 100.0) + "%");
        }

        if(orderHedQty>0)
            tvavgLineNumber.setText(""+(orderDetLine/orderHedQty));
        else
            tvavgLineNumber.setText("0");

        tvSalesGross.setText("" + format.format(dailyAchieve+dailyDiscount));
        tvNetValue.setText("" + format.format(dailyAchieve-dailyDiscount+dailyReturn));
        tvTarget.setText("" + format.format(dailyTarget));
        tvDiscount.setText("" + format.format(dailyDiscount));
        //tvSalesMarketReturn.setText("" + format.format(dailyReturn));
        tvProductive.setText("" + ordcount);
        tvNonprdctive.setText("" + nonprd);
        tvTotalcalls.setText("" + (ordcount + nonprd));
        if (ordcount > 0) {

            DecimalFormat df = new DecimalFormat("####0.00");
            tvProductivePrecentage.setText(df.format((new Double(ordcount) / new Double(ordcount + nonprd)) * 100.0) + "%");

        }

        //tvDayCash.setText("" + format.format(dayCash));
       // tvDayCheque.setText("" + format.format(dayCheque));
        //tvPreviousCash.setText("" + format.format(previousCash));
        //tvPreviousCheque.setText("" + format.format(previousCheque));
        //tvCashTotal.setText("" + format.format(dayCash + previousCash));
        //tvChequeTotal.setText("" + format.format(dayCheque + previousCheque));
        double tonnage = getTonnage();
        DecimalFormat df = new DecimalFormat("####0.000000");
        //tvTonnage.setText("" + df.format(tonnage));
        //TODO::dailyDiscount,dailyDiscount should be set after create tables(FOrdDisc,fInvRdet)
        return rootView;
    }

    private double getTonnage() {
        double tonnage = 0.0;
        String unitsPerCase = "";
        ItemController itmController = new ItemController(getActivity());
        ArrayList<Item> itemTonnageList = new ArrayList<>();
        itemTonnageList = itmController.getTonnage(dateFormat.format(new Date(timeInMillis)));//get ItemCode ItemWeight Qty as list
        for (Item item : itemTonnageList) {// filter item list
            String weight = item.getFITEM_UNITCODE().split("\\*")[0].trim(); // filter only weight part
            if (item.getFITEM_UNITCODE().split("\\*").length > 1) //check split array size
                unitsPerCase = item.getFITEM_UNITCODE().split("\\*")[1].trim();// get units per case

            double qty = Double.parseDouble(item.getFITEM_QOH());//get item quantity

//            String weightWithoutUOM = weight.replaceAll("\\D+", "");//get weight removing string

            String newWeight = "";
            if (weight.contains("g")) {
                newWeight = weight.replace("g", "");
            } else if (weight.contains("G")) {
                newWeight = weight.replace("G", "");
            } else if (weight.contains("ml")) {
                newWeight = weight.replace("ml", "");
            } else if (weight.contains("ML")) {
                newWeight = weight.replace("ML", "");
            }

            if (!weight.equals("") && !unitsPerCase.equals(""))
                try {
                    tonnage += (Double.parseDouble(newWeight) * qty) / (1000 * 1000);//tonnage sum
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return tonnage;
    }
}
