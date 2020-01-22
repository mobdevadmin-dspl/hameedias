package com.datamation.swdsfa.salesreturn;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.adapter.SalesReturnDetailsAdapter;
import com.datamation.swdsfa.controller.CompanyDetailsController;
import com.datamation.swdsfa.controller.CustomerController;
import com.datamation.swdsfa.controller.ItemController;
import com.datamation.swdsfa.controller.SalRepController;
import com.datamation.swdsfa.controller.SalesReturnController;
import com.datamation.swdsfa.controller.SalesReturnDetController;
import com.datamation.swdsfa.controller.SalesReturnTaxDTController;
import com.datamation.swdsfa.controller.SalesReturnTaxRGController;
import com.datamation.swdsfa.helpers.SalesReturnResponseListener;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.Control;
import com.datamation.swdsfa.model.Customer;
import com.datamation.swdsfa.model.FInvRDet;
import com.datamation.swdsfa.model.FInvRHed;
import com.datamation.swdsfa.model.StkIss;
import com.datamation.swdsfa.model.User;
import com.datamation.swdsfa.settings.GPSTracker;
import com.datamation.swdsfa.settings.ReferenceNum;
import com.datamation.swdsfa.utils.UtilityContainer;
import com.datamation.swdsfa.view.DebtorDetailsActivity;
import com.datamation.swdsfa.view.SalesReturnActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SalesReturnSummary extends Fragment {

    View view;
    TextView lblNetVal, lblGross;
    //TextView lblDisc;
    double ftotAmt = 0.00, totReturnDiscount = 0, fTotQty = 0.0;
    String RefNo = null;
    ArrayList<FInvRHed> HedList;
    ArrayList<FInvRDet> returnDetList;
    SalesReturnActivity activity;
    GPSTracker gpsTracker;
    FloatingActionButton fabPause, fabDiscard, fabSave;
    MyReceiver r;
    FloatingActionMenu fam;
    boolean isSalesReturnPending = false;
    SharedPref mSharedPref;
    Activity thisActivity;
    SalesReturnResponseListener salesReturnResponseListener;
    private Customer outlet;

    // to print

    String printLineSeperatorNew = "--------------------------------------------";
    String Heading_a = "";
    String Heading_bmh = "";
    String Heading_b = "";
    String Heading_c = "";
    String Heading_d = "";
    String Heading_e = "";
    String buttomRaw = "";
    String BILL;
    BluetoothAdapter mBTAdapter;
    BluetoothSocket mBTSocket = null;
    String PRINTER_MAC_ID;
    int countCountInv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sales_return_summary, container, false);

        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        mSharedPref = new SharedPref(getActivity());
        fabPause = (FloatingActionButton) view.findViewById(R.id.fab2);
        fabDiscard = (FloatingActionButton) view.findViewById(R.id.fab3);
        fabSave = (FloatingActionButton) view.findViewById(R.id.fab1);
        fam = (FloatingActionMenu) view.findViewById(R.id.fab_menu);
        lblNetVal = (TextView) view.findViewById(R.id.lblNetVal);
        //lblDisc = (TextView) view.findViewById(R.id.lblDisc);
        lblGross = (TextView) view.findViewById(R.id.lblGross);

        activity = (SalesReturnActivity)getActivity();
        thisActivity = getActivity();

//        if (activity.selectedReturnHed == null && new SalesReturnDetController(getActivity()).isAnyActiveRetuens())
//        {
//            activity.selectedReturnHed = new SalesReturnController(getActivity()).getActiveReturnHed();
//        }

        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });
        fam.setClosedOnTouchOutside(true);


        fabPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPauseinvoice();
            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveSummaryDialog();
            }
        });

        fabDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoEditingData();
            }
        });

        if (new SalesReturnController(getActivity()).isAnyActive()){
            isSalesReturnPending = true;
        }
        else
        {
            isSalesReturnPending = false;
        }

        return view;
    }

    public void mRefreshData() {
        String itemCode = "";
        ReferenceNum referenceNum = new ReferenceNum(getActivity());

        if (new SalesReturnController(getActivity()).getDirectSalesReturnRefNo().equals(""))
        {
            RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        }
        else
        {
            RefNo = new SalesReturnController(getActivity()).getDirectSalesReturnRefNo();
        }

        if (activity.selectedReturnHed != null )
        {
            //RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
            HedList = new SalesReturnController(getActivity()).getAllActiveInvrhed();
            returnDetList = new SalesReturnDetController(getActivity()).getAllInvRDetForSalesReturn(RefNo);

            for (FInvRDet retDet : returnDetList) {
                ftotAmt += Double.parseDouble(retDet.getFINVRDET_AMT());
                totReturnDiscount += Double.parseDouble(retDet.getFINVRDET_DIS_AMT());
                fTotQty += Double.parseDouble(retDet.getFINVRDET_QTY());
                itemCode = retDet.getFINVRDET_ITEMCODE();
            }
//        String grossArray[] = new TaxDetDS(getActivity()).calculateTaxForwardFromDebTax("", itemCode, ftotAmt + totReturnDiscount );
//        String NetArray[] = new TaxDetDS(getActivity()).calculateTaxForwardFromDebTax("", itemCode, ftotAmt );
//        String disArray[] = new TaxDetDS(getActivity()).calculateTaxForwardFromDebTax("", itemCode, totReturnDiscount );
//        lblGross.setText(String.format("%.2f", Double.parseDouble(grossArray[0])));
//        lblDisc.setText(String.format("%.2f", Double.parseDouble(disArray[0])));
//        lblNetVal.setText(String.format("%.2f", Double.parseDouble(NetArray[0])));

            lblGross.setText(String.format("%.2f", ftotAmt));
          //  lblDisc.setText(String.format("%.2f", totReturnDiscount));
            lblNetVal.setText(String.format("%.2f", (ftotAmt - totReturnDiscount)));

            ftotAmt = 0;
            totReturnDiscount = 0;
            fTotQty = 0;
        }
        else if (new SalesReturnDetController(getActivity()).isAnyActiveReturnHedDet(referenceNum.getCurrentRefNo(getResources().getString(R.string.salRet))))
        {
            activity.selectedReturnHed = new SalesReturnController(getActivity()).getActiveReturnHed(referenceNum.getCurrentRefNo(getResources().getString(R.string.salRet)));

            //RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
            HedList = new SalesReturnController(getActivity()).getAllActiveInvrhed();
            returnDetList = new SalesReturnDetController(getActivity()).getAllInvRDetForSalesReturn(RefNo);

            for (FInvRDet retDet : returnDetList) {
                ftotAmt += Double.parseDouble(retDet.getFINVRDET_AMT());
                totReturnDiscount += Double.parseDouble(retDet.getFINVRDET_DIS_AMT());
                fTotQty += Double.parseDouble(retDet.getFINVRDET_QTY());
                itemCode = retDet.getFINVRDET_ITEMCODE();
            }

            lblGross.setText(String.format("%.2f", ftotAmt));
          //  lblDisc.setText(String.format("%.2f", totReturnDiscount));
            lblNetVal.setText(String.format("%.2f", (ftotAmt - totReturnDiscount)));

            ftotAmt = 0;
            totReturnDiscount = 0;
            fTotQty = 0;
        }
        else
        {
            Toast.makeText(getActivity(), "Invalid sales return head..." , Toast.LENGTH_LONG).show();
            salesReturnResponseListener.moveBackTo_ret(0);
        }
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*- Save final Sales order to database-*-*-*-**-*-*-*-*-*-*-*-*-*/

    public void undoEditingData() {

        if (new SalesReturnController(getActivity()).getDirectSalesReturnRefNo().equals(""))
        {
            RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        }
        else
        {
            RefNo = new SalesReturnController(getActivity()).getDirectSalesReturnRefNo();
        }

        //RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        FInvRHed hed = new SalesReturnController(getActivity()).getActiveReturnHed(RefNo);
        outlet = new CustomerController(getActivity()).getSelectedCustomerByCode(hed.getFINVRHED_DEBCODE());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Do you want to discard the return?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                int result = new SalesReturnController(getActivity()).restDataForDirectSalesReturnHed(RefNo);

                if (result > 0)
                {
                    int detResult = new SalesReturnDetController(getActivity()).restDataDirectSalesReturnDets(RefNo);

                    if(detResult> 0)
                    {
                        Toast.makeText(getActivity(), "Return Hed and Det discarded successfully..!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Return Hed discarded successfully..!", Toast.LENGTH_LONG).show();
                    }

                    UtilityContainer.ClearReturnSharedPref(getActivity());
                    activity.selectedReturnHed = null;
                    Intent intent = new Intent(getActivity(), DebtorDetailsActivity.class);
                    intent.putExtra("outlet", outlet);
                    startActivity(intent);
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(getActivity(), "Return discard success without data..!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), DebtorDetailsActivity.class);
                    intent.putExtra("outlet", outlet);
                    startActivity(intent);
                    getActivity().finish();
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

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void saveSummaryDialog()
    {
        gpsTracker = new GPSTracker(getActivity());

        if (new SalesReturnController(getActivity()).getDirectSalesReturnRefNo().equals(""))
        {
            RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        }
        else
        {
            RefNo = new SalesReturnController(getActivity()).getDirectSalesReturnRefNo();
        }

        //RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));

        Log.d("SALES_RETRUN", "SUMMARY_IS:" + activity.selectedReturnHed);

        if (!(gpsTracker.canGetLocation()))
        {
            gpsTracker.showSettingsAlert();
        }
        else if (new SalesReturnDetController(getActivity()).getItemCount(RefNo) > 0) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View promptView = layoutInflater.inflate(R.layout.sales_management_sales_return_dialog, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle("Do you want to save the Sales Retrun ?");
            alertDialogBuilder.setView(promptView);

            final ListView lvProducts_Return = (ListView) promptView.findViewById(R.id.lvProducts_Summary_Dialog_Ret);
            ArrayList<FInvRDet> returnItemList = null;
            returnItemList = new SalesReturnDetController(getActivity()).getAllItemsAddedInCurrentReturn(RefNo);
            lvProducts_Return.setAdapter(new SalesReturnDetailsAdapter(getActivity(), returnItemList));

            alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(final DialogInterface dialog, int id) {

                    FInvRHed mainHead = new FInvRHed();
                    ArrayList<FInvRHed> returnHedList = new ArrayList<FInvRHed>();

                    if (!HedList.isEmpty()) {

                        mainHead.setFINVRHED_REFNO(RefNo);
                        mainHead.setFINVRHED_INV_REFNO("NON");
                        mainHead.setFINVRHED_ORD_REFNO("");
                        mainHead.setFINVRHED_DEBCODE(HedList.get(0).getFINVRHED_DEBCODE());
                        mainHead.setFINVRHED_ADD_DATE(HedList.get(0).getFINVRHED_ADD_DATE());
                        mainHead.setFINVRHED_MANUREF(HedList.get(0).getFINVRHED_MANUREF());
                        mainHead.setFINVRHED_REMARKS(HedList.get(0).getFINVRHED_REMARKS());
                        mainHead.setFINVRHED_ADD_MACH(HedList.get(0).getFINVRHED_ADD_MACH());
                        mainHead.setFINVRHED_ADD_USER(new SalRepController(getActivity()).getCurrentRepCode());
                        mainHead.setFINVRHED_TXN_DATE(HedList.get(0).getFINVRHED_TXN_DATE());
                        mainHead.setFINVRHED_ROUTE_CODE(HedList.get(0).getFINVRHED_ROUTE_CODE());
                        //mainHead.setFINVRHED_TOTAL_AMT(HedList.get(0).getFINVRHED_TOTAL_AMT());
                        mainHead.setFINVRHED_TOTAL_AMT(lblNetVal.getText().toString());
                        mainHead.setFINVRHED_TXNTYPE(HedList.get(0).getFINVRHED_TXNTYPE());
                        mainHead.setFINVRHED_ADDRESS(HedList.get(0).getFINVRHED_ADDRESS());
                        mainHead.setFINVRHED_REASON_CODE(HedList.get(0).getFINVRHED_REASON_CODE());
                        mainHead.setFINVRHED_COSTCODE(HedList.get(0).getFINVRHED_COSTCODE());
                        mainHead.setFINVRHED_LOCCODE(HedList.get(0).getFINVRHED_LOCCODE());
                        mainHead.setFINVRHED_TAX_REG(HedList.get(0).getFINVRHED_TAX_REG());
                        mainHead.setFINVRHED_TOTAL_TAX(HedList.get(0).getFINVRHED_TOTAL_TAX());
                        //mainHead.setFINVRHED_TOTAL_DIS(HedList.get(0).getFINVRHED_TOTAL_DIS());
//                        mainHead.setFINVRHED_TOTAL_DIS(lblDisc.getText().toString());
                        mainHead.setFINVRHED_TOTAL_DIS("0.00");
                        mainHead.setFINVRHED_LONGITUDE(HedList.get(0).getFINVRHED_LONGITUDE());
                        mainHead.setFINVRHED_LATITUDE(HedList.get(0).getFINVRHED_LATITUDE());
                        mainHead.setFINVRHED_START_TIME(HedList.get(0).getFINVRHED_START_TIME());
                        mainHead.setFINVRHED_END_TIME(HedList.get(0).getFINVRHED_END_TIME());
                        mainHead.setFINVRHED_IS_ACTIVE("0");
                        mainHead.setFINVRHED_IS_SYNCED("0");
                        mainHead.setFINVRHED_REP_CODE(HedList.get(0).getFINVRHED_REP_CODE());
                        mainHead.setFINVRHED_RETURN_TYPE(HedList.get(0).getFINVRHED_RETURN_TYPE());
                        mainHead.setFINVRHED_TOURCODE(HedList.get(0).getFINVRHED_TOURCODE());
                        mainHead.setFINVRHED_DRIVERCODE(HedList.get(0).getFINVRHED_DRIVERCODE());
                        mainHead.setFINVRHED_HELPERCODE(HedList.get(0).getFINVRHED_HELPERCODE());
                        mainHead.setFINVRHED_AREACODE(HedList.get(0).getFINVRHED_AREACODE());
                        mainHead.setFINVRHED_LORRYCODE(HedList.get(0).getFINVRHED_LORRYCODE());

                        Log.d("SALES_RETURN_SUMMARY", "REP_CODE: " + mainHead.getFINVRHED_REP_CODE());

                    }

                    returnHedList.add(mainHead);

                    if (new SalesReturnController(getActivity()).createOrUpdateInvRHed(returnHedList) > 0) {

                        new SalesReturnDetController(getActivity()).InactiveStatusUpdate(RefNo);
                        new SalesReturnController(getActivity()).InactiveStatusUpdate(RefNo);

                        UpdateTaxDetails(RefNo, mSharedPref.getSelectedDebCode());
                        activity.selectedReturnHed = null;

                        //commented due to details update the refno ----------------------
                        new ReferenceNum(getActivity()).NumValueUpdate(getResources().getString(R.string.salRet));
                        //-----------------------------

                        Toast.makeText(getActivity(), "Return saved successfully !", Toast.LENGTH_LONG).show();
                        UtilityContainer.ClearReturnSharedPref(getActivity());
                        //new PrintPreviewAlertBox(getActivity()).PrintDetailsDialogbox(getActivity(),"SALES RETURN", RefNo);

                        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                                .content("Do you want to get print?")
                                .positiveColor(ContextCompat.getColor(getActivity(), R.color.material_alert_positive_button))
                                .positiveText("Yes")
                                .negativeColor(ContextCompat.getColor(getActivity(), R.color.material_alert_negative_button))
                                .negativeText("No, Exit")
                                .callback(new MaterialDialog.ButtonCallback() {

                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        super.onPositive(dialog);

                                        printItems(RefNo);
                                        Intent intent = new Intent(getActivity(),DebtorDetailsActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();

                                    }

                                    @Override
                                    public void onNegative(MaterialDialog dialog) {
                                        super.onNegative(dialog);
                                        Intent intent = new Intent(getActivity(),DebtorDetailsActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                        dialog.dismiss();
                                    }
                                })
                                .build();
                        materialDialog.setCanceledOnTouchOutside(false);
                        materialDialog.show();
//
                    } else {
                        Toast.makeText(getActivity(), "Return failed !", Toast.LENGTH_LONG)
                                .show();
                    }

                }

            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alertD = alertDialogBuilder.create();
            alertD.show();

        }else{
            Toast.makeText(getActivity(), "Return det failed !", Toast.LENGTH_LONG).show();
            saveValidationDialogBox();
        }
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mPauseinvoice() {

        if (new SalesReturnController(getActivity()).getDirectSalesReturnRefNo().equals(""))
        {
            RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        }
        else
        {
            RefNo = new SalesReturnController(getActivity()).getDirectSalesReturnRefNo();
        }

        if (new SalesReturnDetController(getActivity()).getItemCount(RefNo) > 0)
        {
            FInvRHed hed = new SalesReturnController(getActivity()).getActiveReturnHed(RefNo);
            outlet = new CustomerController(getActivity()).getSelectedCustomerByCode(hed.getFINVRHED_DEBCODE());

            // when paused and redirect to sales return, refno should be updated -------
            new ReferenceNum(getActivity()).NumValueUpdate(getResources().getString(R.string.salRet));
            //--------------------------------------

            Intent intent = new Intent(getActivity(), DebtorDetailsActivity.class);
            intent.putExtra("outlet", outlet);
            startActivity(intent);
            getActivity().finish();
        }
        else
            Toast.makeText(getActivity(), "Add items before pause ...!", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onAttach(Activity activity) {
//        this.thisActivity = activity;
//        super.onAttach(activity);
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            salesReturnResponseListener = (SalesReturnResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            SalesReturnSummary.this.mRefreshData();
        }
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_RET_SUMMARY"));
    }

    public void saveValidationDialogBox() {

        String message = "Please add products for this Sales Return.";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Sales Return");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
                if (fam.isOpened()) {
                    fam.close(true);
                }

                if (isSalesReturnPending) {
//                    SalesReturnDetails salesReturn = new SalesReturnDetails();
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean("Active", true);
//                    salesReturn.setArguments(bundle);
//                    UtilityContainer.mLoadFragment(salesReturn, activity);
                }

            }
        }).setNegativeButton("", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();

    }

    public void UpdateTaxDetails(String refNo, String retDebtorCode) {

        ArrayList<FInvRDet> list = new SalesReturnDetController(activity).getEveryItem(refNo);
        new SalesReturnDetController(activity).UpdateItemTaxInfo(list, retDebtorCode);
        new SalesReturnTaxRGController(activity).UpdateReturnTaxRG(list, retDebtorCode);
        new SalesReturnTaxDTController(activity).UpdateReturnTaxDT(list);

    }

    public void printItems(String refNo) {

        final int LINECHAR = 60;
        String printGapAdjustCom = "                      ";

        ArrayList<Control> controlList;
        controlList = new CompanyDetailsController(getActivity()).getAllControl();

        User salrep = SharedPref.getInstance(getActivity()).getLoginUser();

        int lengthDealACom = controlList.get(0).getFCONTROL_COM_NAME().length();
        int lengthDealABCom = (LINECHAR - lengthDealACom) / 2;
        String printGapAdjustACom = printGapAdjustCom.substring(0, Math.min(lengthDealABCom, printGapAdjustCom.length()));

        int lengthDealBCom = controlList.get(0).getFCONTROL_COM_ADD1().length();
        int lengthDealBBCom = (LINECHAR - lengthDealBCom) / 2;
        String printGapAdjustBCom = printGapAdjustCom.substring(0, Math.min(lengthDealBBCom, printGapAdjustCom.length()));

        String addressCCom = controlList.get(0).getFCONTROL_COM_ADD2().trim() + ", " + controlList.get(0).getFCONTROL_COM_ADD3().trim() + ".";
        int lengthDealCCom = addressCCom.length();
        int lengthDealCBCom = (LINECHAR - lengthDealCCom) / 2;
        String printGapAdjustCCom = printGapAdjustCom.substring(0, Math.min(lengthDealCBCom, printGapAdjustCom.length()));

        String TelCom = "Tel: " + controlList.get(0).getFCONTROL_COM_TEL1().trim() + " / Fax: " + controlList.get(0).getFCONTROL_COM_FAX().trim();
        int lengthDealDCom = TelCom.length();
        int lengthDealDBCom = (LINECHAR - lengthDealDCom) / 2;
        String printGapAdjustDCom = printGapAdjustCom.substring(0, Math.min(lengthDealDBCom, printGapAdjustCom.length()));

        int lengthDealECom = controlList.get(0).getFCONTROL_COM_WEB().length();
        int lengthDealEBCom = (LINECHAR - lengthDealECom) / 2;
        String printGapAdjustECom = printGapAdjustCom.substring(0, Math.min(lengthDealEBCom, printGapAdjustCom.length()));

        int lengthDealFCom = controlList.get(0).getFCONTROL_COM_EMAIL().length();
        int lengthDealFBCom = (LINECHAR - lengthDealFCom) / 2;
        String printGapAdjustFCom = printGapAdjustCom.substring(0, Math.min(lengthDealFBCom, printGapAdjustCom.length()));

        String subTitleheadACom = printGapAdjustACom + controlList.get(0).getFCONTROL_COM_NAME();
        String subTitleheadBCom = printGapAdjustBCom + controlList.get(0).getFCONTROL_COM_ADD1();
        String subTitleheadCCom = printGapAdjustCCom + controlList.get(0).getFCONTROL_COM_ADD2() + ", " + controlList.get(0).getFCONTROL_COM_ADD3() + ".";
        String subTitleheadDCom = printGapAdjustDCom + "Tel: " + controlList.get(0).getFCONTROL_COM_TEL1() + " / Fax: " + controlList.get(0).getFCONTROL_COM_FAX().trim();
        String subTitleheadECom = printGapAdjustECom + controlList.get(0).getFCONTROL_COM_WEB();
        String subTitleheadFCom = printGapAdjustFCom + controlList.get(0).getFCONTROL_COM_EMAIL();

        String subTitleheadGCom = printLineSeperatorNew;

        String title_Print_ACom = "\r\n" + subTitleheadACom;
        String title_Print_BCom = "\r\n" + subTitleheadBCom;
        String title_Print_CCom = "\r\n" + subTitleheadCCom;
        String title_Print_DCom = "\r\n" + subTitleheadDCom;
        String title_Print_ECom = "\r\n" + subTitleheadECom;
        String title_Print_FCom = "\r\n" + subTitleheadFCom;;
        String title_Print_GCom = "\r\n" + subTitleheadGCom;

        Heading_a = title_Print_ACom + title_Print_BCom + title_Print_CCom + title_Print_DCom + title_Print_ECom + title_Print_FCom + title_Print_GCom;

        String printGapAdjust = "                        ";

        String SalesRepNamestr = "Sales Rep: " + salrep.getCode() + "/ " + salrep.getName().trim();// +
        // "/
        // "
        // +
        // salrep.getLOCCODE();

        int lengthDealE = SalesRepNamestr.length();
        int lengthDealEB = (LINECHAR - lengthDealE) / 2;
        String printGapAdjustE = printGapAdjust.substring(0, Math.min(lengthDealEB, printGapAdjust.length()));
        String subTitleheadF = printGapAdjustE + SalesRepNamestr;

        String SalesRepPhonestr = "Tele: " + salrep.getMobile().trim();
        int lengthDealF = SalesRepPhonestr.length();
        int lengthDealFB = (LINECHAR - lengthDealF) / 2;
        String printGapAdjustF = printGapAdjust.substring(0, Math.min(lengthDealFB, printGapAdjust.length()));
        String subTitleheadG = printGapAdjustF + SalesRepPhonestr;

        String subTitleheadH = printLineSeperatorNew;

        FInvRHed invRHed = new SalesReturnController(getActivity()).getReturnDetailsForPrint(refNo);
        Customer debtor = new CustomerController(getActivity()).getSelectedCustomerByCode(SharedPref.getInstance(getActivity()).getSelectedDebCode());

        int lengthDealI = debtor.getCusCode().length() + "-".length() + debtor.getCusName().length();
        int lengthDealIB = (LINECHAR - lengthDealI) / 2;
        String printGapAdjustI = printGapAdjust.substring(0, Math.min(lengthDealIB, printGapAdjust.length()));

        String customerAddressStr = debtor.getCusAdd1() + "," + debtor.getCusAdd2();
        int lengthDealJ = customerAddressStr.length();
        int lengthDealJB = (LINECHAR - lengthDealJ) / 2;
        String printGapAdjustJ = printGapAdjust.substring(0, Math.min(lengthDealJB, printGapAdjust.length()));

        int lengthDealK = debtor.getCusAdd2().length();
        int lengthDealKB = (LINECHAR - lengthDealK) / 2;
        String printGapAdjustK = printGapAdjust.substring(0, Math.min(lengthDealKB, printGapAdjust.length()));

        int lengthDealL = debtor.getCusMob().length();
        int lengthDealLB = (LINECHAR - lengthDealL) / 2;
        String printGapAdjustL = printGapAdjust.substring(0, Math.min(lengthDealLB, printGapAdjust.length()));

        int cusVatNo = 0;
//        if(TextUtils.isEmpty(debtor.getFDEBTOR_CUS_VATNO()))
//        {
//
//        }
//        else
//        {
//            cusVatNo = "TIN No: ".length() + debtor.getFDEBTOR_CUS_VATNO().length();
//        }

        //int lengthCusTIN = (LINECHAR - cusVatNo) / 2;
        //String printGapCusTIn = printGapAdjust.substring(0, Math.min(lengthCusTIN, printGapAdjust.length()));

        String subTitleheadI = printGapAdjustI + debtor.getCusCode() + "-" + debtor.getCusName();
        String subTitleheadJ = printGapAdjustJ + debtor.getCusAdd1() + "," + debtor.getCusAdd2();

        String subTitleheadK = printGapAdjustK + debtor.getCusAdd2();
        String subTitleheadL = printGapAdjustL + debtor.getCusMob();
        //String subTitleheadTIN = printGapCusTIn + "TIN No: " + debtor.getFDEBTOR_CUS_VATNO();

        String subTitleheadO = printLineSeperatorNew;

        String subTitleheadM = "VJO Date: " + invRHed.getFINVRHED_TXN_DATE() + " " + currentTime();
        int lengthDealM = subTitleheadM.length();
        int lengthDealMB = (LINECHAR - lengthDealM) / 2;
        String printGapAdjustM = printGapAdjust.substring(0, Math.min(lengthDealMB, printGapAdjust.length()));

        String subTitleheadN = "VJO Number: " + refNo;
        int lengthDealN = subTitleheadN.length();
        int lengthDealNB = (LINECHAR - lengthDealN) / 2;
        String printGapAdjustN = printGapAdjust.substring(0, Math.min(lengthDealNB, printGapAdjust.length()));

        // String TempsubTermCode = "Terms: " + invHed.getFINVHED_TERMCODE() +
        // "/" + new
        // TermDS(context).getTermDetails(invHed.getFINVHED_TERMCODE());
        // int lenTerm = TempsubTermCode.length();
        // String sp = String.format("%" + ((LINECHAR - lenTerm) / 2) + "s", "
        // ");
        // TempsubTermCode = sp + "Terms: " + invHed.getFINVHED_TERMCODE() + "/"
        // + new TermDS(context).getTermDetails(invHed.getFINVHED_TERMCODE());

        String subTitleheadR;

        if (invRHed.getFINVRHED_REMARKS()== null)
            subTitleheadR = "Remarks : None";
        else
            subTitleheadR = "Remarks : " + invRHed.getFINVRHED_REMARKS();

        int lengthDealR = subTitleheadR.length();
        int lengthDealRB = (LINECHAR - lengthDealR) / 2;
        String printGapAdjustR = printGapAdjust.substring(0, Math.min(lengthDealRB, printGapAdjust.length()));

        subTitleheadM = printGapAdjustM + subTitleheadM;
        subTitleheadN = printGapAdjustN + subTitleheadN;
        subTitleheadR = printGapAdjustR + subTitleheadR;

        String title_Print_F = "\r\n" + subTitleheadF;
        String title_Print_G = "\r\n" + subTitleheadG;
        String title_Print_H = "\r\n" + subTitleheadH;

        String title_Print_I = "\r\n" + subTitleheadI;
        String title_Print_J = "\r\n" + subTitleheadJ;
        String title_Print_K = "\r\n" + subTitleheadK;
        String title_Print_O = "\r\n" + subTitleheadO;

        String title_Print_M = "\r\n" + subTitleheadM;
        String title_Print_N = "\r\n" + subTitleheadN;
        String title_Print_R = "\r\n";// + TempsubTermCode + "\r\n" +
        // subTitleheadR;

        ArrayList<FInvRDet> itemList = new SalesReturnDetController(getActivity()).getReturnItemsforPrint(refNo);
        //ArrayList<FInvRDet> Rlist = new SalesReturnDetController(getActivity()).getAllInvRDetForPrint(ReturnRefNo);

        BigDecimal compDisc = BigDecimal.ZERO;// new
        // BigDecimal(itemList.get(0).getFINVDET_COMDISPER().toString());
        BigDecimal cusDisc = BigDecimal.ZERO;// new
        // BigDecimal(itemList.get(0).getFINVDET_CUSDISPER().toString());
        BigDecimal termDisc = BigDecimal.ZERO;// new
        // BigDecimal(itemList.get(0).getFINVDET_TERM_DISPER().toString());

        Heading_c = "";
        countCountInv = 0;

        if (subTitleheadK.toString().equalsIgnoreCase(" ")) {
            Heading_bmh = "\r" + title_Print_F + title_Print_G + title_Print_H + title_Print_I + title_Print_J + title_Print_O + title_Print_M + title_Print_N + title_Print_R;
        } else
            Heading_bmh = "\r" + title_Print_F + title_Print_G + title_Print_H + title_Print_I + title_Print_J + title_Print_K + title_Print_O + title_Print_M + title_Print_N + title_Print_R;

        String title_cb = "\r\nITEM CODE          QTY     PRICE     AMOUNT ";
        String title_cc = "\r\nITEM NAME								   ";
        String title_cd = "\r\n             INVOICE DETAILS                ";

        Heading_b = "\r\n" + printLineSeperatorNew + title_cb + title_cc + title_cd+ "\r\n" + printLineSeperatorNew+"\n";

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*Individual Item details*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        int totQty = 0 ;
        ArrayList<StkIss> list = new ArrayList<StkIss>();

        //Order Item total
        for (FInvRDet det : itemList) {
            totQty += Integer.parseInt(det.getFINVRDET_QTY());
        }

        int nos = 1;
        String SPACE0, SPACE1, SPACE2, SPACE3, SPACE4, SPACE5, SPACE6;
        SPACE6 = "                                            ";

        //for (StkIss iss : list) {
        for (FInvRDet det : itemList) {

            String sItemcode = det.getFINVRDET_ITEMCODE();
            String sItemname = new ItemController(getActivity()).getItemNameByCode(sItemcode);
            String sQty = det.getFINVRDET_QTY();
            // String sMRP = iss.getPRICE().substring(0, iss.getPRICE().length()
            // - 3);

            String sPrice = "", sTotal = "";

            sTotal = det.getFINVRDET_AMT();
            sPrice = det.getFINVRDET_T_SELL_PRICE();

            String sDiscount;

            //sPrice = "";// iss.getPRICE();
            //sTotal = "";// iss.getAMT();
            sDiscount = "";// iss.getBrand();
            sDiscount = det.getFINVRDET_DIS_AMT();


            int itemCodeLength = sItemcode.length();

            if(itemCodeLength > 15)
            {
                sItemcode = sItemcode.substring(0,15);
            }

            //SPACE0 = String.format("%"+ (44 - (sItemname.length())) +(String.valueOf(nos).length() + 2)+ "s", " ");
            //SPACE1 = String.format("%" + (20 - (sItemcode.length() + (String.valueOf(nos).length() + 2))) + "s", " ");
            SPACE1 = padString("",(20 - (sItemcode.length() + (String.valueOf(nos).length() + 2))));
            //SPACE2 = String.format("%" + (9 - (sPrice.length())) + "s", " ");
            SPACE2 = padString("",(9 - (sPrice.length())));
            //SPACE3 = String.format("%" + (3 - (sQty.length())) + "s", " ");
            SPACE3 = padString("",(3 - (sQty.length())));
            //SPACE4 = String.format("%" + (12 - (sTotal.length())) + "s", " ");
            SPACE4 = padString("",(12 - (sTotal.length())));
            //SPACE5 = String.format("%" + (String.valueOf(nos).length() + 2) + "s", " ");
            SPACE5 = padString("",(String.valueOf(nos).length() + 2));


            String doubleLineItemName1 = "",doubleLineItemName2 = "";
            int itemNameLength = sItemname.length();
            if(itemNameLength > 40)
            {
                doubleLineItemName1 += sItemname.substring(0,40);
                doubleLineItemName2 += sItemname.substring(41,sItemname.length());

                Heading_c += nos + ". " + sItemcode +	SPACE1

                        // + SPACE2
                        + sQty

                        + SPACE3
                        +SPACE2
                        + sPrice +SPACE4+ sTotal
                        +
                        "\r\n" +SPACE5+doubleLineItemName1.trim()+
                        "\r\n" +SPACE5+doubleLineItemName2.trim()+

                        "\r\n"+SPACE6+"\r\n";
            }
            else
            {
                doubleLineItemName1 += sItemname.substring(0,itemNameLength);

                Heading_c += nos + ". " + sItemcode +	SPACE1

                        //+ SPACE2
                        + sQty

                        + SPACE3
                        +SPACE2
                        + sPrice +SPACE4+ sTotal
                        +
                        "\r\n" +SPACE5+doubleLineItemName1.trim()+


                        "\r\n"+SPACE6+"\r\n";
            }

            nos++;
        }

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/


        /*-*-*-*-*-*-*-*-*-*-*-*-*-*Return Header*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
        Heading_d = "";
        String title_da = "\r\nITEM CODE          QTY     PRICE     AMOUNT ";
        String title_db = "\r\nITEM NAME								   ";
        String title_dc = "\r\n             RETURN DETAILS                 ";

        Heading_d = "\r\n" + printLineSeperatorNew + title_da + title_db + title_dc+ "\r\n" + printLineSeperatorNew+"\r\n";

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/


        String space = "";
        String sNetTot = "", sGross = "", sRetGross = "0.00";

        // if (invHed.getFINVHED_INV_TYPE().equals("NON")) {


        sGross = String.format(Locale.US, "%,.2f",Double.parseDouble(invRHed.getFINVRHED_TOTAL_AMT()) + Double.parseDouble(invRHed.getFINVRHED_TOTAL_DIS()));


        int totReturnQty = 0;
        Double returnTot = 0.00;

        sNetTot = String.format(Locale.US, "%,.2f", Double.parseDouble(invRHed.getFINVRHED_TOTAL_AMT()));




        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Discounts*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        //  BigDecimal TotalAmt = new BigDecimal(Double.parseDouble(invHed.getFINVHED_TOTALAMT()) + Double.parseDouble(invHed.getFINVHED_TOTALDIS()));
        BigDecimal TotalAmt = new BigDecimal(Double.parseDouble(invRHed.getFINVRHED_TOTAL_AMT()));

        String sComDisc, sCusdisc = "0", sTermDisc = "0";
        String fullDisc_String = "";

        if (compDisc.doubleValue() > 0) {
            // sComDisc = String.format(Locale.US, "%,.2f", (TotalAmt.divide(new
            // BigDecimal("100"))).multiply(compDisc));
            TotalAmt = TotalAmt.divide(new BigDecimal("100")).multiply(new BigDecimal("100").subtract(compDisc));
            sGross = String.format(Locale.US, "%,.2f", TotalAmt);
            // space = String.format("%" + (LINECHAR -
            // (" Company Discount @ ".length() + compDisc.toString().length()
            // + "%".length() + sComDisc.length())) + "s", " ");
            // fullDisc_String += " Company Discount @ " + compDisc.toString()
            // + "%" + space + sComDisc + "\r\n";
        }

        if (cusDisc.doubleValue() > 0) {
            sCusdisc = String.format(Locale.US, "%,.2f", (TotalAmt.divide(new BigDecimal("100"))).multiply(cusDisc));
            TotalAmt = TotalAmt.divide(new BigDecimal("100")).multiply(new BigDecimal("100").subtract(cusDisc));
            space = String.format("%" + (LINECHAR - ("   Customer Discount @ ".length() + cusDisc.toString().length() + "%".length() + sCusdisc.length())) + "s", " ");
            fullDisc_String += "   Customer Discount @ " + cusDisc.toString() + "%" + space + sCusdisc + "\r\n";
        }

        if (termDisc.doubleValue() > 0) {
            sTermDisc = String.format(Locale.US, "%,.2f", (TotalAmt.divide(new BigDecimal("100"))).multiply(termDisc));
            TotalAmt = TotalAmt.divide(new BigDecimal("100")).multiply(new BigDecimal("100").subtract(termDisc));
            space = String.format("%" + (LINECHAR - ("   Term Discount @ ".length() + termDisc.toString().length() + "%".length() + sTermDisc.length())) + "s", " ");
            fullDisc_String += "   Term Discount @ " + termDisc.toString() + "%" + space + sTermDisc + "\r\n";
        }

        String sDisc = String.format(Locale.US, "%,.2f", Double.parseDouble(sTermDisc.replace(",", "")) + Double.parseDouble(sCusdisc.replace(",", "")));

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Gross Net values-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String printSpaceSumName = "                    ";
        String summaryTitle_a = "Total Quantity" + printSpaceSumName;
        summaryTitle_a = summaryTitle_a.substring(0, Math.min(20, summaryTitle_a.length()));

        //Total Order Item Qty
        space = String.format("%" + (LINECHAR - ("Total Quantity".length() + String.valueOf(totQty).length())) + "s", " ");
        String buttomTitlea = "\r\n\n\n" + "Total Quantity" + space + String.valueOf(totQty);

        //Total Return Item Qty
        space = String.format("%" + (LINECHAR - ("Total Return Quantity".length() + String.valueOf(totReturnQty).length())) + "s", " ");
        String buttomTitleb = "\r\n"+"Total Return Quantity" + space + String.valueOf(totReturnQty);

        /* print gross amount */
        space = String.format("%" + (LINECHAR - ("Total Value".length() + sGross.length())) + "s", " ");
        String summaryTitle_c_Val = "Total Value" + space + sGross;

        space = String.format("%" + (LINECHAR - ("Total Return Value".length() + sRetGross.length())) + "s", " ");
        String summaryTitle_RetVal = "Total Return Value" + space + sRetGross;

        /* print net total */
        space = String.format("%" + (LINECHAR - ("Net Total".length() + sNetTot.length())) + "s", " ");
        String summaryTitle_e_Val = "Net Total" + space + sNetTot;

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String summaryBottom_cpoyline1 = "by Datamation Systems / www.datamation.lk";
        int lengthsummarybottm = summaryBottom_cpoyline1.length();
        int lengthsummarybottmline1 = (LINECHAR - lengthsummarybottm) / 2;
        String printGapbottmline1 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline1, printGapAdjust.length()));

        // String summaryBottom_cpoyline3 = "www.datamation.lk";
        // int lengthsummarybotline3 = summaryBottom_cpoyline3.length();
        // int lengthsummarybottmline3 = (LINECHAR - lengthsummarybotline3) / 2;
        // String printGapbottmline3 = printGapAdjust.substring(0,
        // Math.min(lengthsummarybottmline3, printGapAdjust.length()));

        // String summaryBottom_cpoyline2 = " +94 11 2 501202 / + 94 (0) 777
        // 899899 ";
        // int lengthsummarybotline2 = summaryBottom_cpoyline2.length();
        // int lengthsummarybottmline2 = (LINECHAR - lengthsummarybotline2) / 2;
        // String printGapbottmline2 = printGapAdjust.substring(0,
        // Math.min(lengthsummarybottmline2, printGapAdjust.length()));

        String buttomTitlec = "\r\n" + summaryTitle_c_Val;
        String buttomTitled = "\r\n" + summaryTitle_RetVal;
        String buttomTitlee = "\r\n" + summaryTitle_e_Val;



        String buttomTitlef = "\r\n\n\n" + "------------------        ------------------" + "\r\n" + "     Customer               Sales Executive";

        String buttomTitlefa = "\r\n\n\n" + "Please place the rubber stamp.";
        String buttomTitlecopyw = "\r\n" + printGapbottmline1 + summaryBottom_cpoyline1;
        // String buttomTitlecopywbottom = "\r\n" + printGapbottmline2 +
        // summaryBottom_cpoyline2;
        // String buttomTitlecopywbottom3 = "\r\n" + printGapbottmline3 +
        // summaryBottom_cpoyline3;
        buttomRaw = printLineSeperatorNew + buttomTitlea + buttomTitleb + buttomTitlec + buttomTitled + "\r\n" + printLineSeperatorNew + buttomTitlee + "\r\n" + printLineSeperatorNew + "\r\n" + buttomTitlef + buttomTitlefa + "\r\n" + printLineSeperatorNew + buttomTitlecopyw + "\r\n" + printLineSeperatorNew + "\n";
        callPrintDevice();
    }
    public static String padString(String str, int leng) {
        for (int i = str.length(); i < leng; i++)
            str += " ";
        return str;
    }
    /*************************************************************/
    private void callPrintDevice() {
        BILL = " ";

        BILL = Heading_a + Heading_bmh + Heading_b + Heading_c + Heading_d + Heading_e + buttomRaw;
        Log.v("", "BILL :" + BILL);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();

        try {
            if (mBTAdapter.isDiscovering())
                mBTAdapter.cancelDiscovery();
            else
                mBTAdapter.startDiscovery();
        } catch (Exception e) {
            Log.e("Class ", "fire 4", e);
        }
        System.out.println("BT Searching status :" + mBTAdapter.isDiscovering());

        if (mBTAdapter == null) {
            android.widget.Toast.makeText(getActivity(), "Device has no bluetooth		 capability...", android.widget.Toast.LENGTH_SHORT).show();
        } else {
            if (!mBTAdapter.isEnabled()) {
                Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            }
            printBillToDevice(PRINTER_MAC_ID);
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        }
    }
    /*******************************************************************/
    public void printBillToDevice(final String address) {

        mBTAdapter.cancelDiscovery();
        try {
            BluetoothDevice mdevice = mBTAdapter.getRemoteDevice(address);
            Method m = mdevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
            mBTSocket = (BluetoothSocket) m.invoke(mdevice, 1);

            mBTSocket.connect();
            OutputStream os = mBTSocket.getOutputStream();
            os.flush();
            os.write(BILL.getBytes());
            System.out.println(BILL);

            if (mBTAdapter != null)
                mBTAdapter.cancelDiscovery();
        } catch (Exception e) {
            android.widget.Toast.makeText(getActivity(), "Printer Device Disable Or Invalid MAC.Please Enable the Printer or MAC Address.", android.widget.Toast.LENGTH_LONG).show();
            e.printStackTrace();
            // this.PrintDetailsDialogbox(getActivity(), "", RefNo,"",false);
        }
    }

    private String currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}
