package com.datamation.hmdsfa.barcode.invoce;

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

import android.text.Html;
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
import com.datamation.hmdsfa.controller.ItemLocController;
import com.datamation.hmdsfa.controller.ProductController;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.controller.SalesReturnController;
import com.datamation.hmdsfa.controller.SalesReturnDetController;
import com.datamation.hmdsfa.controller.TaxDetController;
import com.datamation.hmdsfa.dialog.VanSalePrintPreviewAlertBox;
import com.datamation.hmdsfa.helpers.PreSalesResponseListener;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.helpers.VanSalesResponseListener;
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
import com.datamation.hmdsfa.utils.EnglishNumberToWords;
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
    VanSalesResponseListener responseListener;
    TextView lblGross, lblFreeQty, lblDiscount, lblNetVal, lblLines, lblQty;
    SharedPref mSharedPref;
    String RefNo = null,ReturnRefNo = null;
    ArrayList<InvDet> list;
    ArrayList<BarcodenvoiceDet> listNew;
    ArrayList<FInvRDet> returnList;
    Activity activity;
    String locCode;
    private Customer outlet;
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

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Clear Shared preference-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void undoEditingData() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Do you want to discard the invoice ?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                outlet = new CustomerController(getActivity()).getSelectedCustomerByCode(mSharedPref.getSelectedDebCode());
                new SharedPref(getActivity()).setGlobalVal("KeyVat","");
                new SharedPref(getActivity()).setGlobalVal("KeyPayType","");
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
                intnt.putExtra("outlet", outlet);
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
        if (mSharedPref.getDiscountClicked().equals("0")) {
            responseListener.moveBackToCustomer(1);
            Toast.makeText(getActivity(), "Please tap on Discount Button", Toast.LENGTH_LONG).show();
        }
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.VanNumVal));

        String orRefNo = new InvHedController(getActivity()).getActiveInvoiceRef();

        int ftotQty = 0, fTotFree = 0, returnQty = 0, lines = 0;
        double ftotAmt = 0, fTotLineDisc = 0, fTotSchDisc = 0, totalReturn = 0;

        locCode = new SalRepController(getActivity()).getCurrentLoccode().trim();

        list = new InvDetController(getActivity()).getAllInvDet(RefNo);
        //listNew = new InvoiceDetBarcodeController(getActivity()).getAllInvDet(RefNo);
        lines = list.size();
        for (InvDet ordDet : list) {
            ftotAmt += Double.parseDouble(ordDet.getFINVDET_AMT());
            ftotQty += Double.parseDouble(ordDet.getFINVDET_QTY());
            fTotSchDisc += Double.parseDouble(ordDet.getFINVDET_DIS_AMT());

        }
        iTotFreeQty = fTotFree;
        lblQty.setText(String.valueOf(ftotQty));
        lblGross.setText(String.format("%.2f", ftotAmt + fTotSchDisc ));//+ fTotLineDisc
        lblDiscount.setText(String.format("%.2f", fTotSchDisc));//fTotLineDisc+
        lblNetVal.setText(String.format("%.2f", ftotAmt));//-fTotLineDisc-fTotSchDisc
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
                        new SharedPref(getActivity()).setGlobalVal("KeyVat","");
                        new SharedPref(getActivity()).setGlobalVal("KeyPayType","");
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
                          //  UpdateTaxDetails(RefNo);//2020-/06/24
                            /*-*-*-*-*-*-*-*-*-*-QOH update-*-*-*-*-*-*-*-*-*/
                             //commented by rashmi 2020-03-23 till qoh get from fitemloc

                            //UpdateQOH_FIFO();
           new ItemLocController(getActivity()).UpdateVanStock(RefNo,"-",locCode);
//           Customer debtor = new CustomerController(getActivity()).getSelectedCustomerByCode(invHed.getFINVHED_DEBCODE());
           dialog.cancel();

           Intent intent = new Intent(getActivity(), DebtorDetailsActivity.class);
           intent.putExtra("outlet", outlet);
           getActivity().startActivity(intent);
//           dialog.cancel()
   // Menaka Commented        int a = new VanSalePrintPreviewAlertBox(getActivity()).PrintDetailsDialogbox(getActivity(), "Print preview - original", RefNo);

           // new ItemLocController(getActivity()).UpdateInvoiceQOH(RefNo, "-", locCode);
                           // updateDispTables(sHed);

//                            MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
//                                    .content("Do you want to get print?")
//                                    .positiveColor(ContextCompat.getColor(getActivity(), R.color.material_alert_positive_button))
//                                    .positiveText("Yes")
//                                    .negativeColor(ContextCompat.getColor(getActivity(), R.color.material_alert_negative_button))
//                                    .negativeText("No, Exit")
//                                    .callback(new MaterialDialog.ButtonCallback() {
//                                        @Override
//                                        public void onPositive(MaterialDialog dialog) {
//                                            super.onPositive(dialog);
//
//                                            printItems();
//                                            outlet = new CustomerController(getActivity()).getSelectedCustomerByCode(mSharedPref.getSelectedDebCode());
//                                            Intent intnt = new Intent(getActivity(), DebtorDetailsActivity.class);
//                                            intnt.putExtra("outlet", outlet);
//                                            startActivity(intnt);
//
//                                            getActivity().finish();
//
//                                        }
//                                        @Override
//                                        public void onNegative(MaterialDialog dialog) {
//                                            super.onNegative(dialog);
//                                            outlet = new CustomerController(getActivity()).getSelectedCustomerByCode(mSharedPref.getSelectedDebCode());
//                                            Intent intnt = new Intent(getActivity(), DebtorDetailsActivity.class);
//                                            intnt.putExtra("outlet", outlet);
//                                            startActivity(intnt);
//                                            getActivity().finish();
//                                            dialog.dismiss();
//                                        }
//                                    })
//                                    .build();
//                            materialDialog.setCanceledOnTouchOutside(false);
//                            materialDialog.show();
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
        new InvDetController(activity).UpdateItemTax(list);
        new InvTaxRGController(activity).UpdateInvTaxRG(list);
        new InvTaxDTController(activity).UpdateInvTaxDT(list);
    }
    public void UpdateReturnTotal(String refNo) {
        ArrayList<FInvRDet> list = new SalesReturnDetController(activity).getAllInvRDet(refNo);
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
        try {
            responseListener = (VanSalesResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/
    private void UpdateQOH_FIFO() {

        ArrayList<InvDet> list = new InvDetController(getActivity()).getAllInvDet(RefNo);

        /*-*-*-*-*-*-*-*-*-*-*-*-each itemcode has multiple sizecodes*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*/
        for (InvDet item : list) {

            int Qty = (int) Double.parseDouble(item.getFINVDET_QTY());

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
            outlet = new CustomerController(getActivity()).getSelectedCustomerByCode(mSharedPref.getSelectedDebCode());
            Intent intnt = new Intent(getActivity(), DebtorDetailsActivity.class);
            intnt.putExtra("outlet", outlet);
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


}
