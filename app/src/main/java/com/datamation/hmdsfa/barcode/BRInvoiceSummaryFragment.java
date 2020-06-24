package com.datamation.hmdsfa.barcode;

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
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.InvDetAdapter;
import com.datamation.hmdsfa.controller.CompanyDetailsController;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.DebItemPriController;
import com.datamation.hmdsfa.controller.DispDetController;
import com.datamation.hmdsfa.controller.DispHedController;
import com.datamation.hmdsfa.controller.InvDetController;
import com.datamation.hmdsfa.controller.InvHedController;
import com.datamation.hmdsfa.controller.InvTaxDTController;
import com.datamation.hmdsfa.controller.InvTaxRGController;
import com.datamation.hmdsfa.controller.InvoiceBarcodeController;
import com.datamation.hmdsfa.controller.InvoiceDetBarcodeController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.ProductController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.controller.SalesReturnController;
import com.datamation.hmdsfa.controller.SalesReturnDetController;
import com.datamation.hmdsfa.controller.TaxDetController;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.BarcodenvoiceDet;
import com.datamation.hmdsfa.model.Control;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.FInvRDet;
import com.datamation.hmdsfa.model.FInvRHed;
import com.datamation.hmdsfa.model.InvDet;
import com.datamation.hmdsfa.model.InvHed;
import com.datamation.hmdsfa.model.Product;
import com.datamation.hmdsfa.model.SalRep;
import com.datamation.hmdsfa.model.StkIss;
import com.datamation.hmdsfa.settings.ReferenceNum;
import com.datamation.hmdsfa.view.DebtorDetailsActivity;
import com.datamation.hmdsfa.view.VanSalesActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class BRInvoiceSummaryFragment extends Fragment {
    public static final String SETTINGS = "VanSalesSummary";
    public static SharedPreferences localSP;
    View view;
    TextView lblGross, lblFreeQty, lblDiscount, lblNetVal, lblLines, lblQty;
    SharedPref mSharedPref;
    String RefNo = null,ReturnRefNo = null;
    ArrayList<InvDet> list;
    ArrayList<BarcodenvoiceDet> listNew;
    ArrayList<FInvRDet> returnList;
    Activity activity;
    String locCode;
    FloatingActionButton fabPause, fabDiscard, fabSave;
    FloatingActionMenu fam;
    MyReceiver r;
    int iTotFreeQty = 0;
    private SweetAlertDialog pDialog;
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

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Cancel order*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_van_sales_summary, container, false);

        mSharedPref = new SharedPref(getActivity());
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.VanNumVal));
        ReturnRefNo = new SalesReturnController(getActivity()).getCurRefNoOfRetWitInv(RefNo);
        fabPause = (FloatingActionButton) view.findViewById(R.id.fab2);
        fabDiscard = (FloatingActionButton) view.findViewById(R.id.fab3);
        fabSave = (FloatingActionButton) view.findViewById(R.id.fab1);
        fam = (FloatingActionMenu) view.findViewById(R.id.fab_menu);

        lblNetVal = (TextView) view.findViewById(R.id.lblNetVal_Inv);
        lblDiscount = (TextView) view.findViewById(R.id.lbl_discount_tot);
        lblFreeQty = (TextView) view.findViewById(R.id.lblFreeQty);
        lblLines = (TextView) view.findViewById(R.id.lblLines);
        lblGross = (TextView) view.findViewById(R.id.lblGross_Inv);
        lblQty = (TextView) view.findViewById(R.id.lblQty_Inv);
        PRINTER_MAC_ID = mSharedPref.getGlobalVal("printer_mac_address").toString();

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

                saveSummaryDialog();

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

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Clear Shared preference-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void undoEditingData() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Do you want to discard the invoice ?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.VanNumVal));

                String orRefNo = new InvHedController(getActivity()).getActiveInvoiceRef();

                int result = new InvHedController(getActivity()).restDataBC(RefNo);
                if (result > 0) {
                  //  new ProductController(getActivity()).mClearTables();
//                    new InvHedController(getActivity()).InactiveStatusUpdate(RefNo);
//                    new InvDetController(getActivity()).InactiveStatusUpdate(RefNo);
                    new InvDetController(getActivity()).restData(RefNo);

                }

                Toast.makeText(getActivity(), "Invoice details discarded successfully..!", Toast.LENGTH_SHORT).show();

                Intent intnt = new Intent(getActivity(),DebtorDetailsActivity.class);
                startActivity(intnt);
                getActivity().finish();

            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Save primary & secondary invoice-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*/

    public void mRefreshData() {
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.VanNumVal));

        String orRefNo = new InvHedController(getActivity()).getActiveInvoiceRef();

        int ftotQty = 0, fTotFree = 0, returnQty = 0, lines = 0;
        double ftotAmt = 0, fTotLineDisc = 0, fTotSchDisc = 0, totalReturn = 0;

        //locCode = new SharedPref(getActivity()).getGlobalVal("KeyLocCode");

        list = new InvDetController(getActivity()).getAllInvDet(RefNo);
        //listNew = new InvoiceDetBarcodeController(getActivity()).getAllInvDet(RefNo);
        lines = list.size();
        for (InvDet ordDet : list) {
            ftotAmt += Double.parseDouble(ordDet.getFINVDET_AMT());
            ftotQty += Integer.parseInt(ordDet.getFINVDET_QTY());
            fTotSchDisc += Double.parseDouble(ordDet.getFINVDET_DIS_AMT());

        }
        iTotFreeQty = fTotFree;
        lblQty.setText(String.valueOf(ftotQty));
        lblGross.setText(String.format("%.2f", ftotAmt + fTotSchDisc + fTotLineDisc));
        lblDiscount.setText(String.format("%.2f", fTotLineDisc+fTotSchDisc));
        lblNetVal.setText(String.format("%.2f", ftotAmt-fTotSchDisc-fTotLineDisc));
        lblFreeQty.setText(String.valueOf(fTotFree));
        lblLines.setText(String.valueOf(lines));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/
//
    public void saveSummaryDialog() {
        if (new InvDetController(getActivity()).getItemCount(RefNo) > 0)
        {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View promptView = layoutInflater.inflate(R.layout.sales_management_van_sales_summary_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Do you want to save the invoice ?");
                alertDialogBuilder.setView(promptView);
                final ListView lvProducts_Invoice = (ListView) promptView.findViewById(R.id.lvProducts_Summary_Dialog_Inv);
                ArrayList<InvDet> invoiceItemList = null;
                invoiceItemList = new InvDetController(getActivity()).getAllItemsAddedInCurrentSale(RefNo);
                lvProducts_Invoice.setAdapter(new InvDetAdapter(getActivity(), invoiceItemList));
                alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(final DialogInterface dialog, int id) {
                        ArrayList<InvHed> invHedList = new ArrayList<InvHed>();
                        InvHed invHed = new InvHedController(getActivity()).getActiveInvhed();
                        InvHed sHed = new InvHed();
                        sHed.setFINVHED_REFNO(RefNo);
                        sHed.setFINVHED_DEBCODE(new SharedPref(getActivity()).getSelectedDebCode());
                        sHed.setFINVHED_ADDDATE(invHed.getFINVHED_ADDDATE());
                        sHed.setFINVHED_MANUREF(invHed.getFINVHED_MANUREF());
                        sHed.setFINVHED_REMARKS(invHed.getFINVHED_REMARKS());
                        sHed.setFINVHED_ADDMACH(invHed.getFINVHED_ADDMACH());
                        sHed.setFINVHED_ADDUSER(invHed.getFINVHED_ADDUSER());
                        sHed.setFINVHED_CURCODE(invHed.getFINVHED_CURCODE());
                        sHed.setFINVHED_CURRATE(invHed.getFINVHED_CURRATE());
                        sHed.setFINVHED_LOCCODE(invHed.getFINVHED_LOCCODE());
                        sHed.setFINVHED_CUSTELE(invHed.getFINVHED_CUSTELE());
                        sHed.setFINVHED_CONTACT(invHed.getFINVHED_CONTACT());
                        sHed.setFINVHED_CUSADD1(invHed.getFINVHED_CUSADD1());
                        sHed.setFINVHED_CUSADD2(invHed.getFINVHED_CUSADD2());
                        sHed.setFINVHED_CUSADD3(invHed.getFINVHED_CUSADD3());
                        sHed.setFINVHED_TXNTYPE(invHed.getFINVHED_TXNTYPE());
                        sHed.setFINVHED_IS_ACTIVE(invHed.getFINVHED_IS_ACTIVE());
                        sHed.setFINVHED_IS_SYNCED(invHed.getFINVHED_IS_SYNCED());
                        sHed.setFINVHED_LOCCODE(invHed.getFINVHED_LOCCODE());
                        sHed.setFINVHED_ROUTECODE(invHed.getFINVHED_ROUTECODE());
                        sHed.setFINVHED_AREACODE(invHed.getFINVHED_AREACODE());
                        sHed.setFINVHED_COSTCODE(invHed.getFINVHED_COSTCODE());
                        sHed.setFINVHED_TAXREG(invHed.getFINVHED_TAXREG());
                        sHed.setFINVHED_TOURCODE(invHed.getFINVHED_TOURCODE());
                        sHed.setFINVHED_START_TIME_SO(invHed.getFINVHED_START_TIME_SO());
                        sHed.setFINVHED_BPTOTALDIS("0");
                        sHed.setFINVHED_BTOTALAMT("0");
                        sHed.setFINVHED_BTOTALDIS("0");
                        sHed.setFINVHED_BTOTALTAX("0");
                        sHed.setFINVHED_END_TIME_SO(currentTime());
                        //  sHed.setFINVHED_START_TIME_SO(localSP.getString("Van_Start_Time", "").toString());
                        sHed.setFINVHED_LATITUDE(mSharedPref.getGlobalVal("Latitude").equals("") ? "0.00" : mSharedPref.getGlobalVal("Latitude"));
                        sHed.setFINVHED_LONGITUDE(mSharedPref.getGlobalVal("Longitude").equals("") ? "0.00" : mSharedPref.getGlobalVal("Longitude"));
                        // sHed.setFINVHED_ADDRESS(localSP.getString("GPS_Address", "").toString());
                        sHed.setFINVHED_TOTALTAX("0");
                        sHed.setFINVHED_TOTALDIS(lblDiscount.getText().toString());
                        sHed.setFINVHED_TOTALAMT(lblNetVal.getText().toString());
                        sHed.setFINVHED_TXNDATE(invHed.getFINVHED_TXNDATE());
                        sHed.setFINVHED_REPCODE(new SalRepController(getActivity()).getCurrentRepCode());
                        sHed.setFINVHED_REFNO1("");
                        sHed.setFINVHED_TOTQTY(lblQty.getText().toString());
                        sHed.setFINVHED_TOTFREEQTY(iTotFreeQty + "");
                        sHed.setFINVHED_VAT_CODE(invHed.getFINVHED_VAT_CODE());
                        sHed.setFINVHED_PAYTYPE(invHed.getFINVHED_PAYTYPE());

                        invHedList.add(sHed);

                        if (new InvHedController(getActivity()).createOrUpdateInvHed(invHedList) > 0)
       {
                            //new ProductController(getActivity()).mClearTables();
                            new InvHedController(getActivity()).InactiveStatusUpdate(RefNo);
                            new InvDetController(getActivity()).InactiveStatusUpdate(RefNo);
                            new ReferenceNum(getActivity()).NumValueUpdate(getResources().getString(R.string.VanNumVal));

                            /*-*-*-*-*-*-*-*-*-*-QOH update-*-*-*-*-*-*-*-*-*/
                             //commented by rashmi 2020-03-23 till qoh get from fitemloc
                            //UpdateTaxDetails(RefNo);
                           // UpdateQOH_FIFO();
                           // new ItemLocController(getActivity()).UpdateInvoiceQOH(RefNo, "-", locCode);
                           // updateDispTables(sHed);
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

                                            printItems();
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
                        } else {
                            Toast.makeText(getActivity(), "Failed..", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(activity, "Add items before save ...!", Toast.LENGTH_SHORT).show();
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/
    public void UpdateTaxDetails(String refNo) {
        ArrayList<InvDet> list = new InvDetController(activity).getAllInvDet(refNo);
        new InvDetController(activity).UpdateItemTaxInfo(list);
        new InvTaxRGController(activity).UpdateInvTaxRG(list);
        new InvTaxDTController(activity).UpdateInvTaxDT(list);
    }
    public void UpdateReturnTotal(String refNo) {
        ArrayList<FInvRDet> list = new SalesReturnDetController(activity).getAllInvRDetForInvoice(refNo);
        new SalesReturnDetController(activity).UpdateReturnTot(list);

    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/
    private String currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/
    private void UpdateQOH_FIFO() {

        ArrayList<InvDet> list = new InvDetController(getActivity()).getAllInvDet(RefNo);

        /*-*-*-*-*-*-*-*-*-*-*-*-each itemcode has multiple sizecodes*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/
        for (InvDet item : list) {

            int Qty = (int) Double.parseDouble(item.getFINVDET_QTY());

            // ArrayList<StkIn> GRNList = new STKInController(activity).getAscendingGRNList(item.getFINVDET_ITEM_CODE(), locCode);

            /*-*-*-*-*-*-*-*-*-*-multiple GRN for each sizecode-*-*-*-*-*-*-*-*-*-*-*/
//            for (StkIn size : GRNList) {
//                int balQty = (int) Double.parseDouble(size.getBALQTY());
//
//                if (balQty > 0) {
//                    if (Qty > balQty) {
//                        Qty = Qty - balQty;
//                        size.setBALQTY("0");
//                 //       new StkIssController(activity).InsertSTKIssData(size, RefNo, String.valueOf(balQty), locCode);
//
//                    } else {
//                        size.setBALQTY(String.valueOf(balQty - Qty));
//                     //   new StkIssController(activity).InsertSTKIssData(size, RefNo, String.valueOf(Qty), locCode);
//                        break;
//                    }
//                }
//            }
//            new STKInController(activity).UpdateBalQtyByFIFO(GRNList);
        }
    }
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/
    public void updateDispTables(InvHed invHed) {

        String dispREfno = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.DispVal));

        int res = new DispHedController(getActivity()).updateHeader(invHed, dispREfno);

        if (res > 0) {
            ArrayList<InvDet> list = new InvDetController(getActivity()).getAllInvDet(invHed.getFINVHED_REFNO());
            new DispDetController(getActivity()).updateDispDet(list, dispREfno);
            //    new DispIssController(getActivity()).updateDispIss(new StkIssController(getActivity()).getUploadData(invHed.getFINVHED_REFNO()), dispREfno);
            new ReferenceNum(getActivity()).NumValueUpdate(getResources().getString(R.string.DispVal));
        }
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/
    public void mPauseinvoice() {
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.VanNumVal));

        if (new InvDetController(getActivity()).getItemCount(RefNo) > 0) {
            String activeRetRefNo1 = new SalesReturnController(getActivity()).getCurRefNoOfRetWitInv(RefNo);

            if (!activeRetRefNo1.equals(""))
            {
                // when paused and redirect to sales return, refno should be updated -------
                new ReferenceNum(getActivity()).NumValueUpdate(getResources().getString(R.string.salRet));
                //--------------------------------------
            }
            Intent intnt = new Intent(getActivity(),DebtorDetailsActivity.class);
            startActivity(intnt);
            getActivity().finish();
        } else
            Toast.makeText(activity, "Add items before pause ...!", Toast.LENGTH_SHORT).show();
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/
    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_SUMMARY"));
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mRefreshData();
        }
    }


    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/
    public void updateQtyInItemLocTblItemWise()
    {
        try
        {
            ArrayList<InvDet> list = new InvDetController(getActivity()).getAllInvDet(RefNo);

            /*-*-*-*-*-*-*-*-*-*-*-*-each itemcode has multiple sizecodes*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/
            for (InvDet item : list)
            {
                int Qty = (int) Double.parseDouble(item.getFINVDET_QTY());
                //  ArrayList<StkIn> GRNList = new STKInController(activity).getAscendingGRNList(item.getFINVDET_ITEM_CODE(), locCode);

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }



    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public void printItems() {
        final int LINECHAR = 44;
        String printGapAdjustCom = "                      ";

        ArrayList<Control> controlList;
        controlList = new CompanyDetailsController(getActivity()).getAllControl();

        SalRep salrep = SharedPref.getInstance(getActivity()).getLoginUser();

        int lengthDealACom = controlList.get(0).getFCONTROL_COM_NAME().length();
        int lengthDealABCom = (LINECHAR - lengthDealACom) / 2;
        String printGapAdjustACom = printGapAdjustCom.substring(0, Math.min(lengthDealABCom, printGapAdjustCom.length()));

        int lengthDealBCom = controlList.get(0).getFCONTROL_COM_ADD1().length();
        int lengthDealBBCom = (LINECHAR - lengthDealBCom) / 2;
        String printGapAdjustBCom = printGapAdjustCom.substring(0, Math.min(lengthDealBBCom, printGapAdjustCom.length()));

        //String addressCCom = controlList.get(0).getFCONTROL_COM_ADD2().trim() + ", " + controlList.get(0).getFCONTROL_COM_ADD3().trim() + ".";
        String addressCCom = controlList.get(0).getFCONTROL_COM_ADD3().trim() + ".";
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

        String SalesRepNamestr = "Sales Rep: " + salrep.getRepCode() + "/ " + salrep.getNAME().trim();// +
        // "/
        // "
        // +
        // salrep.getLOCCODE();

        int lengthDealE = SalesRepNamestr.length();
        int lengthDealEB = (LINECHAR - lengthDealE) / 2;
        String printGapAdjustE = printGapAdjust.substring(0, Math.min(lengthDealEB, printGapAdjust.length()));
        String subTitleheadF = printGapAdjustE + SalesRepNamestr;

        String SalesRepPhonestr = "Tele: " ;
        int lengthDealF = SalesRepPhonestr.length();
        int lengthDealFB = (LINECHAR - lengthDealF) / 2;
        String printGapAdjustF = printGapAdjust.substring(0, Math.min(lengthDealFB, printGapAdjust.length()));
        String subTitleheadG = printGapAdjustF + SalesRepPhonestr;

        String subTitleheadH = printLineSeperatorNew;

        InvHed invHed = new InvHedController(getActivity()).getDetailsforPrint(RefNo);
        FInvRHed invRHed = new SalesReturnController(getActivity()).getDetailsforPrint(RefNo);
        Customer debtor = new CustomerController(getActivity()).getSelectedCustomerByCode(SharedPref.getInstance(getActivity()).getSelectedDebCode());

        int lengthDealI = debtor.getCusCode().length();
        // int lengthDealI = debtor.getCusCode().length() + "-".length() + debtor.getCusName().length();
        int lengthDealIB = (LINECHAR - lengthDealI) / 2;
        String printGapAdjustI = printGapAdjust.substring(0, Math.min(lengthDealIB, printGapAdjust.length()));

        //String customerAddressStr = debtor.getCusAdd1() + "," + debtor.getCusAdd2();
        String customerAddressStr = debtor.getCusAdd1() ;
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

        String subTitleheadM = "VJO Date: " + invHed.getFINVHED_TXNDATE() + " " + currentTime();
        int lengthDealM = subTitleheadM.length();
        int lengthDealMB = (LINECHAR - lengthDealM) / 2;
        String printGapAdjustM = printGapAdjust.substring(0, Math.min(lengthDealMB, printGapAdjust.length()));

        String subTitleheadN = "VJO Number: " + RefNo;
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

        if (invHed.getFINVHED_REMARKS().equals(""))
            subTitleheadR = "Remarks : None";
        else
            subTitleheadR = "Remarks : " + invHed.getFINVHED_REMARKS();

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

        ArrayList<InvDet> itemList = new InvDetController(getActivity()).getAllItemsforPrint(RefNo);
        ArrayList<FInvRDet> Rlist = new SalesReturnDetController(getActivity()).getAllInvRDetForPrint(RefNo);

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
        for (InvDet det : itemList) {
            totQty += Integer.parseInt(det.getFINVDET_QTY());
        }

        int nos = 1;
        String SPACE0, SPACE1, SPACE2, SPACE3, SPACE4, SPACE5, SPACE6;
        SPACE6 = "                                            ";

        //for (StkIss iss : list) {
        for (InvDet det : itemList) {

            String sItemcode = det.getFINVDET_ITEM_CODE();
            String sItemname = new ItemController(getActivity()).getItemNameByCode(sItemcode);
            String sQty = det.getFINVDET_QTY();
            // String sMRP = iss.getPRICE().substring(0, iss.getPRICE().length()
            // - 3);

            String sPrice = "", sTotal = "";

            sTotal = det.getFINVDET_AMT();
            sPrice = det.getFINVDET_SELL_PRICE();

            String sDiscount;

            //sPrice = "";// iss.getPRICE();
            //sTotal = "";// iss.getAMT();
            sDiscount = "";// iss.getBrand();
            sDiscount = det.getFINVDET_DISVALAMT();


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


        sGross = String.format(Locale.US, "%,.2f",
                Double.parseDouble(invHed.getFINVHED_TOTALAMT()) +
                        Double.parseDouble(invHed.getFINVHED_TOTALDIS()));


        int totReturnQty = 0;
        Double returnTot = 0.00;

        if(invRHed.getFINVRHED_REFNO() != null) {

            sRetGross = String.format(Locale.US, "%,.2f",
                    Double.parseDouble(invRHed.getFINVRHED_TOTAL_AMT()));


            sNetTot = String.format(Locale.US, "%,.2f", Double.parseDouble(invHed.getFINVHED_TOTALAMT()) -  Double.parseDouble(invRHed.getFINVRHED_TOTAL_AMT()));
            /*-*-*-*-*-*-*-*-*-*-*-*-*-*Individual Return Item details*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
            Heading_e = "";
            //Return Item Total
            for (FInvRDet retrnDet1 : Rlist) {
                totReturnQty += Integer.parseInt(retrnDet1.getFINVRDET_QTY());
                returnTot += Double.parseDouble(retrnDet1.getFINVRDET_AMT());
            }

            int retNos = 1;

            for (FInvRDet retrnDet : Rlist) {

                String sRetItemcode = retrnDet.getFINVRDET_ITEMCODE();
                String sRetItemname = new ItemController(getActivity()).getItemNameByCode(sRetItemcode);
                String sRetQty = retrnDet.getFINVRDET_QTY();
                // String sMRP = iss.getPRICE().substring(0, iss.getPRICE().length()
                // - 3);

                String sRetPrice = "", sRetTotal = "";

                sRetTotal = retrnDet.getFINVRDET_AMT();
                sRetPrice = String.format(Locale.US, "%,.2f",
                        Double.parseDouble(retrnDet.getFINVRDET_SELL_PRICE()));

                int itemCodeLength = sRetItemcode.length();

                if(itemCodeLength > 15)
                {
                    sRetItemcode = sRetItemcode.substring(0,15);
                }

                //SPACE0 = String.format("%" + (44 - (sRetItemname.length())) + (String.valueOf(retNos).length() + 2) + "s", " ");
                //SPACE1 = String.format("%" + (20 - (sRetItemcode.length() + (String.valueOf(retNos).length() + 2))) + "s", " ");
                SPACE1 = padString("",(20 - (sRetItemcode.length() + (String.valueOf(retNos).length() + 2))));
                //SPACE2 = String.format("%" + (9 - (sRetPrice.length())) + "s", " ");
                SPACE2 = padString("",(9 - (sRetPrice.length())));
                //SPACE3 = String.format("%" + (3 - (sRetQty.length())) + "s", " ");
                SPACE3 = padString("",(3 - (sRetQty.length())));
                //SPACE4 = String.format("%" + (12 - (sRetTotal.length())) + "s", " ");
                SPACE4 = padString("",(12 - (sRetTotal.length())));
                //SPACE5 = String.format("%" + (String.valueOf(retNos).length() + 2) + "s", " ");
                SPACE5 = padString("",(String.valueOf(retNos).length() + 2));

                String doubleLineItemName1 = "", doubleLineItemName2 = "";
                int itemNameLength = sRetItemname.length();
                if (itemNameLength > 40) {
                    doubleLineItemName1 += sRetItemname.substring(0, 40);
                    doubleLineItemName2 += sRetItemname.substring(41, sRetItemname.length());

                    Heading_e += retNos + ". " + sRetItemcode + SPACE1

                            // + SPACE2
                            + sRetQty

                            + SPACE3
                            + SPACE2
                            + sRetPrice + SPACE4 + sRetTotal
                            +
                            "\r\n" + SPACE5 + doubleLineItemName1.trim() +
                            "\r\n" + SPACE5 + doubleLineItemName2.trim() +

                            "\r\n"+SPACE6+"\r\n";
                } else {
                    doubleLineItemName1 += sRetItemname.substring(0, itemNameLength);

                    Heading_e += retNos + ". " + sRetItemcode + SPACE1

                            //+ SPACE2
                            + sRetQty

                            + SPACE3
                            + SPACE2
                            + sRetPrice + SPACE4 + sRetTotal
                            +
                            "\r\n" + SPACE5 + doubleLineItemName1.trim() +


                            "\r\n"+SPACE6+"\r\n";
                }

                retNos++;
            }

            /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        }else{
            sNetTot = String.format(Locale.US, "%,.2f", Double.parseDouble(invHed.getFINVHED_TOTALAMT()));
        }



        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Discounts*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        //  BigDecimal TotalAmt = new BigDecimal(Double.parseDouble(invHed.getFINVHED_TOTALAMT()) + Double.parseDouble(invHed.getFINVHED_TOTALDIS()));
        BigDecimal TotalAmt = new BigDecimal(Double.parseDouble(invHed.getFINVHED_TOTALAMT()));

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

}
