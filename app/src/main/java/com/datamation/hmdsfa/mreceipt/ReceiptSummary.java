package com.datamation.hmdsfa.mreceipt;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.ReceiptSummaryAdapter;
import com.datamation.hmdsfa.adapter.ReceiptSummaryPaymodeAdapter;
import com.datamation.hmdsfa.controller.OutstandingController;
import com.datamation.hmdsfa.controller.PayModeController;
import com.datamation.hmdsfa.controller.PaymentAllocateController;
import com.datamation.hmdsfa.controller.ReceiptController;
import com.datamation.hmdsfa.controller.ReceiptDetController;
import com.datamation.hmdsfa.dialog.ReceiptPreviewAlertBox;
import com.datamation.hmdsfa.helpers.ReceiptResponseListener;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.FddbNote;
import com.datamation.hmdsfa.model.PayMode;
import com.datamation.hmdsfa.model.PaymentAllocate;
import com.datamation.hmdsfa.model.ReceiptHed;
import com.datamation.hmdsfa.settings.ReferenceNum;
import com.datamation.hmdsfa.utils.GPSTracker;
import com.datamation.hmdsfa.utils.UtilityContainer;
import com.datamation.hmdsfa.view.DebtorDetailsActivity;
import com.datamation.hmdsfa.view.ReceiptActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ReceiptSummary extends Fragment {

    View view;
    TextView lblRecAmt;
    SharedPref mSharedPref;
    public static SharedPreferences localSP;
    public static final String SETTINGS = "SETTINGS";
    String RefNo = null;
    ListView lv_payment_allco, lv_pay_mode;
    ReceiptActivity activity;
    ArrayList<FddbNote> fddbnoteList;
    GPSTracker gps;
    FloatingActionMenu fam;
    FloatingActionButton fabPause, fabDiscard, fabSave;
    MyReceiver r;
    ArrayList<PayMode>paidModeList,payModeArrayList;
    ArrayList<PaymentAllocate>paymentAllocateArrayList, payModePaymentArrayList, refNoList;
    ArrayList<ReceiptHed>recHHedList;
    String commonRefNo;
    ReceiptResponseListener listener;
    GPSTracker gpsTracker;
    private Customer outlet;

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_multiple_receipt_summery, container, false);
        mSharedPref = new SharedPref(getActivity());
//        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = getActivity().getSharedPreferences(SETTINGS, 0);
        gps = new GPSTracker(getActivity());
        setHasOptionsMenu(true);
        fam = (FloatingActionMenu) view.findViewById(R.id.fab_menu);
        fabPause = (FloatingActionButton) view.findViewById(R.id.fab2);
        fabDiscard = (FloatingActionButton) view.findViewById(R.id.fab3);
        fabSave = (FloatingActionButton) view.findViewById(R.id.fab1);
        lblRecAmt = (TextView) view.findViewById(R.id.lblRecAmt);
        lv_pay_mode = (ListView)view.findViewById(R.id.lv_paymode_summary);
        lv_payment_allco = (ListView) view.findViewById(R.id.lv_payment_alloc);
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.ReceiptNumVal));
        commonRefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.RecNumValCom));
//        commonRefNo = "MO1809/0001";


        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });

//        fabPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPauseinvoice();
//            }
//        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSummaryDialog(getActivity());
            }
        });

        fabDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoEditingData(getActivity());
            }
        });

        return view;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }

        inflater.inflate(R.menu.frag_per_sales_summary, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_pre_sales_save) {

            if (Double.parseDouble(mSharedPref.getGlobalVal("ReckeyRemnant")) <= 0)
                saveSummaryDialog(getActivity());
            else
                Toast.makeText(getActivity(), "Please allocate remaining amount..!", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.action_pre_sale_undo) {
            undoEditingData(getActivity());
        }
        return super.onOptionsItemSelected(item);

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Cancel order*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    private void undoEditingData(final Context context) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do you want to discard the receipt?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                ReceiptActivity activity = (ReceiptActivity) getActivity();
                new OutstandingController(getActivity()).ClearFddbNoteData();
                //new RecHedDS(getActivity()).CancelReceiptS("CNU1809/0005");

//                ArrayList<PaymentAllocate>paymodeList = new PaymentAllocateDS(getActivity()).getPayRefNoByCommonRef(commonRefNo);
//
//                for (PaymentAllocate paymentAllocate: paymodeList)
//                {
//                    new PayModeDS(getActivity()).clearPayMode(paymentAllocate.getFPAYMENT_ALLOCATE_PAY_REF_NO());
//                }

                new PayModeController(getActivity()).clearAllPayModeS();
                new PaymentAllocateController(getActivity()).clearAllPayAllocS();

                ArrayList<PaymentAllocate> refList = new PaymentAllocateController(getActivity()).getRefNoByCommonRef(commonRefNo);

                if (refList.size()>0)
                {
                    for (PaymentAllocate paymentAllocate: refList)
                    {
//   menaka 09-01-2021                     new ReceiptController(getActivity()).CancelReceiptS(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
//   menaka 09-01-2021                     new ReceiptDetController(getActivity()).restData(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
//                        new PaymentAllocateController(getActivity()).clearPaymentAlloc(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
                        new ReceiptController(getActivity()).CancelActiveReceiptS();
                    }
                }
                else
                {
                    new ReceiptController(getActivity()).CancelReceiptS(RefNo);
                    new ReceiptDetController(getActivity()).restData(RefNo);
//                    new PaymentAllocateController(getActivity()).clearPaymentAlloc(RefNo);
                }

                activity.cusPosition = 0;
                activity.selectedDebtor = null;
                activity.selectedRecHed = null;
                //new ReferenceNum(getActivity()).NumValueUpdate(getResources().getString(R.string.RecNumVal));
                Toast.makeText(getActivity(), "Receipt discarded successfully..!", Toast.LENGTH_SHORT).show();
                UtilityContainer.ClearReceiptSharedPref(getActivity());
                Intent intnt = new Intent(getActivity(),DebtorDetailsActivity.class);
                startActivity(intnt);
                getActivity().finish();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**/

//    public void mPauseinvoice() {
//
//        if (mSharedPref.getGlobalVal("ReckeyHeader").equals("1")){
//            Intent intnt = new Intent(getActivity(), DebtorDetailsActivity.class);
//            intnt.putExtra("outlet", outlet);
//            startActivity(intnt);
//            getActivity().finish();
//        }
//        else {
//            Toast.makeText(activity, "Select Customer/Fill in header details before Pause", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Clear Shared preference-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    /* Clear shared preference */
    public void ClearSharedPref() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("ReckeyPayModePos");
        editor.remove("ReckeyPayMode");
        editor.remove("ReckeyRemnant");
        editor.remove("ReckeyRecAmt");
        editor.remove("ReckeyCHQNo");
        editor.remove("ReckeyCustomer");
        editor.remove("ReckeyHeader");
        editor.remove("ReckeyCusCode");
        editor.commit();

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**/

    public void FetchData() {

        payModeArrayList = new PayModeController(getActivity()).getPaidModesByCommonRef(commonRefNo);
        lv_pay_mode.setAdapter(new ReceiptSummaryPaymodeAdapter(getActivity(), payModeArrayList));

        FetchFddNoteData();

    }

    private void FetchFddNoteData()
    {
        lv_payment_allco.setAdapter(null);
        paymentAllocateArrayList = new PaymentAllocateController(getActivity()).getPaidRecordsByCommonRef(commonRefNo);
        lv_payment_allco.setAdapter(new ReceiptSummaryAdapter(getActivity(),paymentAllocateArrayList, commonRefNo));
        //lv_fddbnote.setAdapter(new ReceiptAdapter1(getActivity(), fddbnoteList, true));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**/

    private String currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Save primary & secondary invoice-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*/

    private void saveSummaryDialog(final Context context) {

        gpsTracker = new GPSTracker(getActivity());

        if (!(gpsTracker.canGetLocation())) {
            gpsTracker.showSettingsAlert();
        } else
        {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("Do you want to save the Receipt?");
            alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(final DialogInterface dialog, int id) {

                    if (Double.parseDouble(new PayModeController(getActivity()).getTotalRemainAmt()) > 0.00) {
                        Toast.makeText(getActivity(), "You should allocate total payments to invoices", Toast.LENGTH_LONG).show();
                    } else if (Double.parseDouble(new ReceiptDetController(getActivity()).getTotalREcDetByComRefNo(commonRefNo)) == 0.00) {
                        //else if (Double.parseDouble(new RecDetDS(getActivity()).getTotalREcDetByComRefNo(commonRefNo)) == 0.00) {

                        validateRecDetDialogBox();
                    } else {
                        Toast.makeText(getActivity(), "You can save the receipt", Toast.LENGTH_LONG).show();

                        if (paymentAllocateArrayList.size() > 0) {
                            ArrayList<FddbNote> fdDbNotes = new ArrayList<>();

                            for (PaymentAllocate paymentAllocate : paymentAllocateArrayList) {
                                FddbNote dbNote = new FddbNote();

                                Double dueAmt = Double.parseDouble(paymentAllocate.getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL());

                                dbNote.setFDDBNOTE_REFNO(paymentAllocate.getFPAYMENT_ALLOCATE_FDD_REFNO());
                                dbNote.setFDDBNOTE_TOT_BAL(String.valueOf(dueAmt));

                                fdDbNotes.add(dbNote);
                            }

                            if (fdDbNotes.size() > 0) {
                                new OutstandingController(getActivity()).UpdateFddbNoteBalance(fdDbNotes);
                                activity.cusPosition = 0;
                                activity.selectedDebtor = null;
                                activity.selectedRecHed = null;
                                activity.ReceivedAmt = 0.00;

                                //refNoList = new PaymentAllocateDS(getActivity()).getRefNoByCommonRef(commonRefNo);
                                recHHedList = new ReceiptController(getActivity()).getHedRefNoByCommonRef(commonRefNo);

//                            for (PaymentAllocate paymentAllocate : refNoList) {
//                                if (refNoList.size() > 0) {
//                                    new RecHedDS(getActivity()).UpdateRecHeadTotalAmount(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
//                                    new RecHedDS(getActivity()).InactiveStatusUpdate(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
//                                    new ReceiptPreviewAlertBox(getActivity()).PrintDetailsDialogbox(getActivity(), "Print preview", paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
//
//                                    // to generate a new receipt ref no
//                                    //new ReferenceNum(getActivity()).NumValueUpdate(getResources().getString(R.string.RecNumVal));
//                                }
//                            }
                                new PayModeController(getActivity()).clearAllPayModeS();

                                new PaymentAllocateController(getActivity()).clearAllPayAlloS();

                                for (ReceiptHed recHed : recHHedList) {
                                    if (recHHedList.size() > 0) {
                                        new ReceiptController(getActivity()).UpdateRecHeadTotalAmount(recHed.getFPRECHED_REFNO());
                                        new ReceiptController(getActivity()).InactiveStatusUpdate(recHed.getFPRECHED_REFNO());
                                        new ReceiptPreviewAlertBox(getActivity()).PrintDetailsDialogbox(getActivity(), "Print preview", commonRefNo);

                                        // to generate a new receipt ref no
                                        //new ReferenceNum(getActivity()).NumValueUpdate(getResources().getString(R.string.RecNumVal));
                                    }
                                }



//                            ArrayList<PaymentAllocate> refList = new PaymentAllocateController(getActivity()).getRefNoByCommonRef(commonRefNo);
//
//                            for (PaymentAllocate paymentAllocate : refList) {
//                                new PaymentAllocateController(getActivity()).clearPaymentAlloc(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
//                            }

                                // to generate a new common ref no
                                new ReferenceNum(getActivity()).nNumValueInsertOrUpdate(getResources().getString(R.string.RecNumValCom));

                                Toast.makeText(getActivity(), "Receipt saved successfully..!", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();
                                ClearSharedPref();
                            }
                        }


//                    RecHed recHed = new RecHed();
//                    recHed.setFPRECHED_LATITUDE(gps.getLatitude() + "");
//                    recHed.setFPRECHED_LONGITUDE(gps.getLongitude() + "");
//                    recHed.setFPRECHED_START_TIME(localSP.getString("Van_Start_Time", "").toString());
//                    recHed.setFPRECHED_END_TIME(currentTime());
//                    recHed.setFPRECHED_ADDRESS("None");
//                    recHed.setFPRECHED_COSTCODE(mSharedPref.getGlobalVal("PrekeyCost"));
//                    new RecHedDS(getActivity()).UpdateRecHed(recHed, RefNo);
//                    final MainActivity activity = (MainActivity) getActivity();

//                    ArrayList<RecDet> RecList = new ArrayList<>();
//
//                    for (FDDbNote fddb : fddbnoteList) {
//
//                        RecDet recDet = new RecDet();
//                        recDet.setFPRECDET_REFNO(RefNo);
//                        recDet.setFPRECDET_AMT(String.valueOf(Double.parseDouble(fddb.getFDDBNOTE_TOT_BAL()) - Double.parseDouble(fddb.getFDDBNOTE_ENTER_AMT())));
//                        recDet.setFPRECDET_BAMT(String.valueOf(Double.parseDouble(fddb.getFDDBNOTE_TOT_BAL()) - Double.parseDouble(fddb.getFDDBNOTE_ENTER_AMT())));
//                        recDet.setFPRECDET_AMT(fddb.getFDDBNOTE_ENTER_AMT());
//                        recDet.setFPRECDET_BAMT(fddb.getFDDBNOTE_ENTER_AMT());
//                        recDet.setFPRECDET_ALOAMT(fddb.getFDDBNOTE_ENTER_AMT());
//                        recDet.setFPRECDET_SALEREFNO(fddb.getFDDBNOTE_REFNO());
//                        recDet.setFPRECDET_REPCODE(new SalRepDS(getActivity()).getCurrentRepCode());
//                        recDet.setFPRECDET_DCURCODE("LKR");
//                        recDet.setFPRECDET_DCURRATE("1.0");
//                        recDet.setFPRECDET_DTXNDATE(fddb.getFDDBNOTE_TXN_DATE());
//                        recDet.setFPRECDET_DTXNTYPE(fddb.getFDDBNOTE_TXN_TYPE());
//                        recDet.setFPRECDET_TXNDATE(currentDate());
//                        recDet.setFPRECDET_TXNTYPE("21");
//                        recDet.setFPRECDET_REFNO1(fddb.getFDDBNOTE_REFNO());
//                        recDet.setFPRECDET_MANUREF("");
//                        recDet.setFPRECDET_OCURRATE("1.00");
//                        recDet.setFPRECDET_OVPAYAMT("0.00");
//                        recDet.setFPRECDET_OVPAYBAL("0.00");
//                        recDet.setFPRECDET_RECORDID("");
//                        recDet.setFPRECDET_TIMESTAMP("");
//                        recDet.setFPRECDET_ISDELETE("0");
//                        recDet.setFPRECDET_REMARK(fddb.getFDDBNOTE_REMARKS());
//                        recDet.setFPRECDET_DEBCODE(activity.selectedDebtor.getFDEBTOR_CODE());
//                        RecList.add(recDet);
//                    }
//
//                    new RecDetDS(getActivity()).createOrUpdateRecDetS(RecList);
//                    new FDDbNoteDS(getActivity()).UpdateFddbNoteBalance(fddbnoteList);
//                    new RecHedDS(getActivity()).InactiveStatusUpdate(RefNo);
//
//                    //new ReceiptPreviewAlertBox(getActivity()).PrintDetailsDialogbox(getActivity(), "Print preview", RefNo);
//
//                    activity.cusPosition = 0;
//                    activity.selectedDebtor = null;
//                    activity.selectedRecHed = null;
//                    activity.ReceivedAmt = 0.00;
//                    new ReferenceNum(getActivity()).nNumValueInsertOrUpdate(getResources().getString(R.string.RecNumVal));
//
//                    /*-*-*-*-*-*-*-*-*-*-*-Check if deadline passed-*-*-*-*-*-*-*-*-*-*-*/
//
//                    Toast.makeText(getActivity(), "Receipt saved successfully..!", Toast.LENGTH_SHORT).show();
//                    UtilityContainer.mLoadFragment(new ReceiptInvoice(), getActivity());
//                    dialog.dismiss();
//                    ClearSharedPref();/* Clear shared preference */
                    }
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alertD = alertDialogBuilder.create();
            alertD.show();
        }
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    private String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        } else if (!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        return true;
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_SUMMARY"));
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mRefreshHeader() {
        //Double receiptAmt = Double.parseDouble(new PayModeDS(getActivity()).getTotalPaidAmt());
        Double receiptAmt = Double.parseDouble(new PaymentAllocateController(getActivity()).getTotalPaidAmtByComRefNo(commonRefNo));
        if (receiptAmt>0)
        {
            lblRecAmt.setText(String.valueOf(receiptAmt));
        }
        else
        {
            lblRecAmt.setText("0.00");
        }

        ReceiptHed RCHed = new ReceiptController(getActivity()).getReceiptByRefno(RefNo);
        //lblPayMode.setText(mSharedPref.getGlobalVal("ReckeyPayMode").toString());
        activity = (ReceiptActivity) getActivity();

//        if (mSharedPref.getGlobalVal("ReckeyPayModePos").equals("0")) {
//            lblBank.setText("N/A");
//            lblCHQNo.setText("N/A");
//        } else if(mSharedPref.getGlobalVal("ReckeyPayModePos").equals("1")) {
//            lblBank.setText("N/A");
//            lblCHQNo.setText("N/A");
//        }else if(mSharedPref.getGlobalVal("ReckeyPayModePos").equals("2")) {
//            textBank.setText("BANK NAME : ");
//            //lblBank.setText(RCHed.getFPRECHED_CUSBANK().toString());
//            lblCHQNo.setText(mSharedPref.getGlobalVal("ReckeyCHQNo"));
//        }else if(mSharedPref.getGlobalVal("ReckeyPayModePos").equals("3")) {
//            textBank.setText("CARD TYPE : ");
//            //lblBank.setText(RCHed.getFPRECHED_CUSBANK().toString());
//
//            textNo.setText("CREDIT CARD NO : ");
//            lblCHQNo.setText(mSharedPref.getGlobalVal("ReckeyCHQNo"));
//        }else if(mSharedPref.getGlobalVal("ReckeyPayModePos").equals("4")) {
//            textBank.setVisibility(View.GONE);
//            lblBank.setVisibility(View.GONE);
//
//            textNo.setText("DEPOSIT NO : ");
//            lblCHQNo.setText(mSharedPref.getGlobalVal("ReckeyCHQNo"));
//        }else{
//            textBank.setVisibility(View.GONE);
//            lblBank.setVisibility(View.GONE);
//
//            textNo.setText("DRAFT NO : ");
//            lblCHQNo.setText(mSharedPref.getGlobalVal("ReckeyCHQNo"));
//        }

//        if (!mSharedPref.getGlobalVal("ReckeyRecAmt").equals(""))
//            lblRecAmt.setText(String.format("%,.2f", Double.parseDouble(mSharedPref.getGlobalVal("ReckeyRecAmt").replaceAll(",", ""))));

        FetchData();
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ReceiptSummary.this.mRefreshHeader();
        }
    }

    public void validateRecDetDialogBox()
    {
        String message = "Please use the DONE button before save this Receipt/s";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Save Error");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                listener.moveToFragments(1);

            }
        }).setNegativeButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (ReceiptResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }
}
