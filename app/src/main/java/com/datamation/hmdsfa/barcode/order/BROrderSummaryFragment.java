package com.datamation.hmdsfa.barcode.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.OrderDetailsAdapter;
import com.datamation.hmdsfa.adapter.OrderFreeIssueDetailAdapter;
import com.datamation.hmdsfa.adapter.OrderReturnItemAdapter;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.ItemLocController;
import com.datamation.hmdsfa.controller.OrderController;
import com.datamation.hmdsfa.controller.OrderDetailController;
import com.datamation.hmdsfa.controller.PreProductController;
import com.datamation.hmdsfa.controller.PreSaleTaxDTController;
import com.datamation.hmdsfa.controller.PreSaleTaxRGController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.controller.SalesReturnController;
import com.datamation.hmdsfa.controller.SalesReturnDetController;
import com.datamation.hmdsfa.controller.TaxDetController;
import com.datamation.hmdsfa.helpers.PreSalesResponseListener;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.FInvRDet;
import com.datamation.hmdsfa.model.Order;
import com.datamation.hmdsfa.model.OrderDetail;
import com.datamation.hmdsfa.model.OrderDisc;
import com.datamation.hmdsfa.settings.ReferenceNum;
import com.datamation.hmdsfa.utils.GPSTracker;
import com.datamation.hmdsfa.utils.UtilityContainer;
import com.datamation.hmdsfa.view.DebtorDetailsActivity;
import com.datamation.hmdsfa.view.PreSalesActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class BROrderSummaryFragment extends Fragment {

    public static final String SETTINGS = "PreSalesSummary";
    public static SharedPreferences localSP;
    View view;
    TextView lblGross, lblReturnQty, lblReturn, lblNetVal, lblDiscount, lblQty,lblSummaryHeader;
    SharedPref mSharedPref;
    String RefNo = null, customerName = "" , ReturnRefNo = null;
    ArrayList<OrderDetail> list;
    ArrayList<FInvRDet> returnList;
    ArrayList<OrderDisc> discList;
    String locCode;
    FloatingActionButton fabPause, fabDiscard, fabSave;
    FloatingActionMenu fam;
    MyReceiver r;
    double iTotFreeQty = 0;
    double totalMKReturn = 0;
    PreSalesActivity mainActivity;
    private Customer outlet;
    GPSTracker gpsTracker;
    PreSalesResponseListener responseListener;
    Activity mactivity;
    private double currentLatitude, currentLongitude;
    private Customer customer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_summary, container, false);

        mSharedPref = new SharedPref(getActivity());
        mainActivity = (PreSalesActivity) getActivity();
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.NumVal));
        fabPause = (FloatingActionButton) view.findViewById(R.id.fab2);
        fabDiscard = (FloatingActionButton) view.findViewById(R.id.fab3);
        fabSave = (FloatingActionButton) view.findViewById(R.id.fab1);
        fam = (FloatingActionMenu) view.findViewById(R.id.fab_menu);
        gpsTracker = new GPSTracker(getActivity());

        lblNetVal = (TextView) view.findViewById(R.id.lblNetVal_Inv);
        lblSummaryHeader = (TextView) view.findViewById(R.id.summary_header);
        lblReturn = (TextView) view.findViewById(R.id.lbl_return_tot);
        lblReturnQty = (TextView) view.findViewById(R.id.lblReturnQty);
        lblDiscount = (TextView) view.findViewById(R.id.lblDiscount);
        lblGross = (TextView) view.findViewById(R.id.lblGross_Inv);
        lblQty = (TextView) view.findViewById(R.id.lblQty_Inv);

        mactivity = getActivity();
        customerName = new CustomerController(getActivity()).getCusNameByCode(SharedPref.getInstance(getActivity()).getSelectedDebCode());

        lblSummaryHeader.setText("ORDER SUMMARY - ("+customerName+")");

        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });

        fabPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPauseinvoice();
            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customer = new CustomerController(getActivity()).getCustomerGPS(SharedPref.getInstance(getActivity()).getSelectedDebCode());
                if (!SharedPref.getInstance(getActivity()).getGlobalVal("Latitude").equals(""))
                    currentLatitude = Double.parseDouble(SharedPref.getInstance(getActivity()).getGlobalVal("Latitude"));
                else
                    currentLatitude = 0.0;
                if (!SharedPref.getInstance(getActivity()).getGlobalVal("Longitude").equals(""))
                    currentLongitude = Double.parseDouble(SharedPref.getInstance(getActivity()).getGlobalVal("Longitude"));
                else
                    currentLongitude = 0.0;

                Location currentLocation = new Location("point Current");
                currentLocation.setLatitude(currentLatitude);
                currentLocation.setLongitude(currentLongitude);

                Location customerLocation = new Location("point Customer");

                if (!customer.getLatitude().equals("") && !customer.getLatitude().equals(null))
                    customerLocation.setLatitude(Double.parseDouble(customer.getLatitude()));
                else
                    customerLocation.setLatitude(0.0);

                if (!customer.getLongitude().equals("") && !customer.getLongitude().equals(null))
                    customerLocation.setLongitude(Double.parseDouble(customer.getLongitude()));
                else
                    customerLocation.setLongitude(0.0);
                float distance = currentLocation.distanceTo(customerLocation);
                float distance1 = customerLocation.distanceTo(currentLocation);
                Log.d("<<<customer Longi<<<<", " " + customer.getLongitude());
                Log.d("<<<customer Lati<<<<", " " + customer.getLatitude());
                Log.d("<<<current Longi<<<<", " " + currentLongitude);
                Log.d("<<<current Lati<<<<", " " + currentLatitude);
                Log.d("<<<Distance<<<<", " " + distance);
                if (Double.parseDouble(lblGross.getText().toString()) >0) {
                    saveSummaryDialog();
                } else {
                    Toast.makeText(getActivity(), "Cannot save zero bill amount", Toast.LENGTH_SHORT).show();
                }

            }
        });

        fabDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoEditingData();
            }
        });

        return view;
    }

    public void undoEditingData() {

        String orRefNo = new OrderController(getActivity()).getActiveRefNoFromOrders();
        String activeRetRefNo = new SalesReturnController(getActivity()).getActiveInnerReturnRefNoByOrderRefNo(orRefNo);

        if (activeRetRefNo.equals(""))
        {
            ReturnRefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        }
        else
        {
            ReturnRefNo = activeRetRefNo;
        }

        Order hed = new OrderController(getActivity()).getAllActiveOrdHed();
        outlet = new CustomerController(getActivity()).getSelectedCustomerByCode(hed.getORDER_DEBCODE());


        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Do you want to discard the order with return ?")
                .positiveColor(ContextCompat.getColor(getActivity(), R.color.material_alert_positive_button))
                .positiveText("Yes")
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.material_alert_negative_button))
                .negativeText("No, Exit")
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);

                        int result = new OrderController(getActivity()).restData(RefNo);
                        int resultReturn = new SalesReturnController(getActivity()).restDataForOrders(ReturnRefNo);

                        if (result>0) {
                            new OrderDetailController(getActivity()).restData(RefNo);
                            new PreProductController(getActivity()).mClearTables();
                        }
                        if(resultReturn != 0){
                            new SalesReturnDetController(getActivity()).restData(ReturnRefNo);
                        }

                        //    activity.cusPosition = 0;
//                mainActivity.selectedDebtor = null;
//                mainActivity.selectedRetDebtor = null;
//                mainActivity.selectedPreHed = null;
//                mainActivity.selectedReturnHed = null;
                        Toast.makeText(getActivity(), "Order discarded successfully..!", Toast.LENGTH_SHORT).show();
                        // UtilityContainer.ClearVanSharedPref(getActivity());
                        UtilityContainer.ClearReturnSharedPref(getActivity());

                        Intent intnt = new Intent(getActivity(),DebtorDetailsActivity.class);
                        intnt.putExtra("outlet", outlet);
                        startActivity(intnt);
                        getActivity().finish();


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

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Save primary & secondary invoice-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*/

    public void mRefreshData() {

        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.NumVal));

        String orRefNo = new OrderController(getActivity()).getActiveRefNoFromOrders();
        String activeRetRefNo = new SalesReturnController(getActivity()).getActiveInnerReturnRefNoByOrderRefNo(orRefNo);

        if (activeRetRefNo.equals(""))
        {
            ReturnRefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        }
        else
        {
            ReturnRefNo = activeRetRefNo;
        }

        double ftotQty = 0, fTotFree = 0, returnQty = 0, replacements = 0;
        double ftotAmt = 0, fTotLineDisc = 0, fTotSchDisc = 0, totalReturn = 0;
        String itemCode = "";

        locCode = new SharedPref(getActivity()).getGlobalVal("KeyLocCode");

        list = new OrderDetailController(getActivity()).getAllOrderDetails(RefNo);
        returnList = new SalesReturnDetController(getActivity()).getAllInvRDetForOrders(ReturnRefNo);

        for (OrderDetail ordDet : list) {
            ftotAmt += Double.parseDouble(ordDet.getFORDERDET_AMT());
            itemCode = ordDet.getFORDERDET_ITEMCODE();

//            if (ordDet.getFORDERDET_TYPE().equals("SA"))
            ftotQty += Double.parseDouble(ordDet.getFORDERDET_QTY());
            //else
            //fTotFree += Integer.parseInt(ordDet.getFORDERDET_QTY());

                fTotLineDisc += Double.parseDouble(ordDet.getFORDERDET_DISAMT());
            //    fTotSchDisc += Double.parseDouble(ordDet.getFINVDET_DISVALAMT());
        }
        for (FInvRDet returnDet : returnList){
            if(!returnDet.getFINVRDET_RETURN_TYPE().equals("RP")) {
                totalReturn += Double.parseDouble(returnDet.getFINVRDET_AMT());
                returnQty += Double.parseDouble(returnDet.getFINVRDET_QTY());
            }else{
                replacements += Double.parseDouble(returnDet.getFINVRDET_QTY());
            }
        }

        iTotFreeQty = fTotFree;
        lblQty.setText(String.valueOf(ftotQty + fTotFree));
//        lblGross.setText(String.format("%.2f", ftotAmt + fTotSchDisc + fTotLineDisc));
//        lblReturn.setText(String.format("%.2f", totalReturn));
//        lblNetVal.setText(String.format("%.2f", ftotAmt-totalReturn));

        String sArray[] = new TaxDetController(getActivity()).calculateTaxForwardFromDebTax(mSharedPref.getSelectedDebCode(), itemCode, ftotAmt);
        String amt = String.format("%.2f",Double.parseDouble(sArray[0]));


        lblGross.setText(String.format("%.2f", Double.parseDouble(amt)+fTotLineDisc));
        lblReturn.setText(String.format("%.2f", totalReturn));
        lblNetVal.setText(String.format("%.2f", (Double.parseDouble(amt)-totalReturn)));

        lblReturnQty.setText(String.valueOf(returnQty));
        lblDiscount.setText(String.format("%.2f",  fTotLineDisc));


    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void saveSummaryDialog() {

        if (new OrderDetailController(getActivity()).getItemCount(RefNo) > 0) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View promptView = layoutInflater.inflate(R.layout.sales_management_van_sales_summary_dialog, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle("Do you want to save the invoice ?");
            alertDialogBuilder.setView(promptView);

            final ListView lvProducts_Invoice = (ListView) promptView.findViewById(R.id.lvProducts_Summary_Dialog_Inv);
            ViewGroup.LayoutParams invItmparams = lvProducts_Invoice.getLayoutParams();
            ArrayList<OrderDetail> orderItemList = null;
            orderItemList = new OrderDetailController(getActivity()).getAllItemsAddedInCurrentSale(RefNo, "SA","");
            if(orderItemList.size()>0){
                invItmparams.height = 200;
            }else {
                invItmparams.height = 0;
            }
            lvProducts_Invoice.setLayoutParams(invItmparams);

            lvProducts_Invoice.setAdapter(new OrderDetailsAdapter(getActivity(), orderItemList, mSharedPref.getSelectedDebCode()));

            //MMS - freeissues
            ListView lvProducts_freeIssue = (ListView) promptView.findViewById(R.id.lvProducts_Summary_freeIssue);
            ViewGroup.LayoutParams params = lvProducts_freeIssue.getLayoutParams();
            ArrayList<OrderDetail> orderFreeIssueItemList = null;
            orderFreeIssueItemList = new OrderDetailController(getActivity()).getAllItemsAddedInCurrentSale(RefNo, "FI","FD");
            if(orderFreeIssueItemList.size()>0){
                params.height = 200;
            }else {
                params.height = 0;
            }

            lvProducts_freeIssue.setLayoutParams(params);
            lvProducts_freeIssue.setAdapter(new OrderFreeIssueDetailAdapter(getActivity(), orderFreeIssueItemList, mSharedPref.getSelectedDebCode()));

            //MMS - return item
            ListView lvProducts_return = (ListView) promptView.findViewById(R.id.lvProducts_Summary_Dialog_Ret);
            ViewGroup.LayoutParams retItmparams = lvProducts_return.getLayoutParams();

            ArrayList<OrderDetail> orderReturnItemList = null;
            orderReturnItemList = new OrderDetailController(getActivity()).getAllItemsAddedInCurrentSale(RefNo, "UR","MR");
            Log.d("**re", "saveSummaryDialog: "+orderReturnItemList.toString());
            if(orderReturnItemList.size()>0){
                retItmparams.height = 200;
            }else {
                retItmparams.height = 0;
            }
            lvProducts_return.setLayoutParams(retItmparams);

            lvProducts_return.setAdapter(new OrderReturnItemAdapter(getActivity(), orderReturnItemList, mSharedPref.getSelectedDebCode()));

            alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(final DialogInterface dialog, int id) {

                    Order ordHed = new Order();
                    ArrayList<Order> ordHedList = new ArrayList<Order>();
                    Order presale = new OrderController(getActivity()).getAllActiveOrdHed();
                    ordHed.setORDER_REFNO(RefNo);
                    ordHed.setORDER_DEBCODE(presale.getORDER_DEBCODE());
                    ordHed.setORDER_ADDDATE(presale.getORDER_ADDDATE());
                    ordHed.setORDER_MANUREF(presale.getORDER_MANUREF());
                    ordHed.setORDER_REMARKS(presale.getORDER_REMARKS());
                    ordHed.setORDER_ADDMACH(presale.getORDER_ADDMACH());
                    ordHed.setORDER_ADDUSER(presale.getORDER_ADDUSER());
                    ordHed.setORDER_CURCODE(presale.getORDER_CURCODE());
                    ordHed.setORDER_CURRATE(presale.getORDER_CURRATE());
                    ordHed.setORDER_LOCCODE(presale.getORDER_LOCCODE());
                    ordHed.setORDER_CUSTELE(presale.getORDER_CUSTELE());
                    ordHed.setORDER_START_TIMESO(presale.getORDER_START_TIMESO());
                    ordHed.setORDER_CONTACT(presale.getORDER_CONTACT());
                    ordHed.setORDER_CUSADD1(presale.getORDER_CUSADD1());
                    ordHed.setORDER_CUSADD2(presale.getORDER_CUSADD2());
                    ordHed.setORDER_CUSADD3(presale.getORDER_CUSADD3());
                    ordHed.setORDER_TXNTYPE(presale.getORDER_TXNTYPE());
                    ordHed.setORDER_IS_ACTIVE(presale.getORDER_IS_ACTIVE());
                    ordHed.setORDER_IS_SYNCED(presale.getORDER_IS_SYNCED());
                    ordHed.setORDER_LOCCODE(presale.getORDER_LOCCODE());
                    ordHed.setORDER_AREACODE(presale.getORDER_AREACODE());
                    ordHed.setORDER_ROUTECODE(presale.getORDER_ROUTECODE());
                    ordHed.setORDER_COSTCODE(presale.getORDER_COSTCODE());
                    ordHed.setORDER_TAXREG(presale.getORDER_TAXREG());
                    ordHed.setORDER_TOURCODE(presale.getORDER_TOURCODE());
                    ordHed.setORDER_CURCODE(presale.getORDER_CURCODE());
                    ordHed.setORDER_CURRATE(presale.getORDER_CURRATE());
                    ordHed.setORDER_VATCODE(presale.getORDER_VATCODE());
                    ordHed.setORDER_DELIVERY_DATE(presale.getORDER_DELIVERY_DATE());
                    ordHed.setORDER_PAYTYPE(presale.getORDER_PAYTYPE());
                    ordHed.setORDER_LATITUDE(presale.getORDER_LATITUDE());
                    ordHed.setORDER_LONGITUDE(presale.getORDER_LONGITUDE());
                    ordHed.setORDER_BPTOTALDIS("0");
                    ordHed.setORDER_BTOTALAMT("0");
                    ordHed.setORDER_TOTALTAX("0");
                    ordHed.setORDER_TOTALDIS(lblDiscount.getText().toString());
                    ordHed.setORDER_TOTALAMT(lblNetVal.getText().toString());
                    ordHed.setORDER_TXNDATE(presale.getORDER_TXNDATE());
                    ordHed.setORDER_REPCODE(new SalRepController(getActivity()).getCurrentRepCode());
                    ordHed.setORDER_REFNO1("");
                    ordHed.setORDER_TOTQTY(lblQty.getText().toString());
                    ordHed.setORDER_TOTFREEQTY(iTotFreeQty + "");
                    ordHed.setORDER_SETTING_CODE(presale.getORDER_SETTING_CODE());
                    ordHed.setORDER_DEALCODE(presale.getORDER_DEALCODE());
                    ordHed.setORDER_TOTALMKRAMT(String.format("%.2f", totalMKReturn) + "");

                    ordHedList.add(ordHed);

                    if (new OrderController(getActivity()).createOrUpdateOrdHed(ordHedList) > 0) {
                        new PreProductController(getActivity()).mClearTables();
                        new OrderController(getActivity()).InactiveStatusUpdate(RefNo);
                        new OrderDetailController(getActivity()).InactiveStatusUpdate(RefNo);

                        final PreSalesActivity activity = (PreSalesActivity) getActivity();
                        /*-*-*-*-*-*-*-*-*-*-QOH update-*-*-*-*-*-*-*-*-*/

                        new ReferenceNum(getActivity()).NumValueUpdate(getResources().getString(R.string.NumVal));
                 //       UpdateTaxDetails(RefNo);
                        new ItemLocController(getActivity()).UpdateOrderQOH(RefNo, "-", locCode);
                        Toast.makeText(getActivity(), "Order saved successfully..!", Toast.LENGTH_SHORT).show();
                        activity.selectedReturnHed = null;
                        activity.selectedPreHed = null;
                        mSharedPref.setDiscountClicked("0");
                        UtilityContainer.ClearReturnSharedPref(getActivity());
                        outlet = new CustomerController(getActivity()).getSelectedCustomerByCode(mSharedPref.getSelectedDebCode());
                        Intent intnt = new Intent(getActivity(), DebtorDetailsActivity.class);
                        intnt.putExtra("outlet", outlet);
                        startActivity(intnt);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Order Save Failed..", Toast.LENGTH_SHORT).show();
                    }

                }

            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alertD = alertDialogBuilder.create();
            alertD.show();


        } else
            Toast.makeText(getActivity(), "Add items before save ...!", Toast.LENGTH_SHORT).show();


    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void UpdateTaxDetails(String refNo) {

        ArrayList<OrderDetail> list = new OrderDetailController(getActivity()).getAllOrderDetailsForTaxUpdate(refNo);
        new OrderDetailController(getActivity()).UpdateItemTaxInfoWithDiscount(list, mSharedPref.getSelectedDebCode());
        new PreSaleTaxRGController(getActivity()).UpdateSalesTaxRG(list, mSharedPref.getSelectedDebCode());
        new PreSaleTaxDTController(getActivity()).UpdateSalesTaxDT(list);
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void mPauseinvoice() {

        if (new OrderDetailController(getActivity()).getItemCount(RefNo) > 0) {
            Order hed = new OrderController(getActivity()).getAllActiveOrdHed();
            outlet = new CustomerController(getActivity()).getSelectedCustomerByCode(hed.getORDER_DEBCODE());
            Intent intnt = new Intent(getActivity(), DebtorDetailsActivity.class);
            intnt.putExtra("outlet", outlet);
            startActivity(intnt);
            getActivity().finish();
        } else
            Toast.makeText(getActivity(), "Add items before pause ...!", Toast.LENGTH_SHORT).show();
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_PRE_SUMMARY"));
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mRefreshData();
        }
    }
    /*******************************************************************/
    @Override
    public void onAttach(Activity activity) {
        this.mactivity = activity;
        super.onAttach(mactivity);
        try {
            responseListener = (PreSalesResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }
    public void popupFeedBack(final Context context) {
        final Dialog repDialog = new Dialog(context);
        repDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        repDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        repDialog.setCancelable(false);
        repDialog.setCanceledOnTouchOutside(false);
        repDialog.setContentView(R.layout.feedback_popup);

        //initializations

        final ImageView happy = (ImageView) repDialog.findViewById(R.id.emoji_happy);
        final ImageView sad = (ImageView) repDialog.findViewById(R.id.emoji_bad);
        final ImageView normal = (ImageView) repDialog.findViewById(R.id.emoji_neutral);
       // final ImageView angry = (ImageView) repDialog.findViewById(R.id.emoji_angry);

        final TextView happylbl = (TextView) repDialog.findViewById(R.id.lbl_emoji_happy) ;
        final TextView sadlbl = (TextView) repDialog.findViewById(R.id.lbl_emoji_sad) ;
        final TextView neutrallbl = (TextView) repDialog.findViewById(R.id.lbl_emoji_normal) ;
       // final TextView angrylbl = (TextView) repDialog.findViewById(R.id.lbl_emoji_angry) ;

        repDialog.findViewById(R.id.emoji_happy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("<<<<HAPPY>>","<<HAPPY");
                new OrderController(context).updateFeedback("1",RefNo);
                happy.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.smile));
                sad.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.sad_bw));
                normal.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.confused_bw));
               // angry.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.angry_bw));

                happylbl.setTextColor(getActivity().getResources().getColor(R.color.achievecolor));
                sadlbl.setTextColor(getActivity().getResources().getColor(R.color.half_black));
                neutrallbl.setTextColor(getActivity().getResources().getColor(R.color.half_black));
               // angrylbl.setTextColor(getActivity().getResources().getColor(R.color.half_black));
            }
        });
        repDialog.findViewById(R.id.emoji_bad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("<<<<SAD>>","<<SAD");
                new OrderController(context).updateFeedback("2",RefNo);
                happy.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.happiness_bw));
                sad.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.sad));
                normal.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.confused_bw));
                //angry.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.angry_bw));

                happylbl.setTextColor(getActivity().getResources().getColor(R.color.half_black));
                sadlbl.setTextColor(getActivity().getResources().getColor(R.color.achievecolor));
                neutrallbl.setTextColor(getActivity().getResources().getColor(R.color.half_black));
               // angrylbl.setTextColor(getActivity().getResources().getColor(R.color.half_black));
            }
        });
        repDialog.findViewById(R.id.emoji_neutral).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("<<<<NORMAL>>", "<<NORMAL");
                new OrderController(context).updateFeedback("3",RefNo);
                happy.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.happiness_bw));
                sad.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.sad_bw));
                normal.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.confused));
            //    angry.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.angry_bw));

                happylbl.setTextColor(getActivity().getResources().getColor(R.color.half_black));
                sadlbl.setTextColor(getActivity().getResources().getColor(R.color.half_black));
                neutrallbl.setTextColor(getActivity().getResources().getColor(R.color.achievecolor));
              //  angrylbl.setTextColor(getActivity().getResources().getColor(R.color.half_black));
            }
        });

//        repDialog.findViewById(R.id.emoji_angry).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("<<<<ANGRY>>","<<ANGRY");
//                new OrderController(context).updateFeedback("4",RefNo);
//                happy.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.happiness_bw));
//                sad.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.sad_bw));
//                normal.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.confused_bw));
//                angry.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.angry));
//
//                happylbl.setTextColor(getActivity().getResources().getColor(R.color.half_black));
//                sadlbl.setTextColor(getActivity().getResources().getColor(R.color.half_black));
//                neutrallbl.setTextColor(getActivity().getResources().getColor(R.color.half_black));
//                angrylbl.setTextColor(getActivity().getResources().getColor(R.color.achievecolor));
//            }
//        });

        repDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    saveSummaryDialog();

                    repDialog.dismiss();

            }
        });


        repDialog.show();
    }

}
