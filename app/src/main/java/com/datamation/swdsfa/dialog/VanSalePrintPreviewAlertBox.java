package com.datamation.swdsfa.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.adapter.PrintPreSaleItemAdapter;
import com.datamation.swdsfa.adapter.PrintVanSaleItemAdapter;
import com.datamation.swdsfa.adapter.PrintVanSaleReturnAdapter;
import com.datamation.swdsfa.controller.CompanyDetailsController;
import com.datamation.swdsfa.controller.CustomerController;
import com.datamation.swdsfa.controller.InvDetController;
import com.datamation.swdsfa.controller.InvHedController;
import com.datamation.swdsfa.controller.ItemController;
import com.datamation.swdsfa.controller.OrderController;
import com.datamation.swdsfa.controller.OrderDetailController;
import com.datamation.swdsfa.controller.SalRepController;
import com.datamation.swdsfa.controller.SalesReturnController;
import com.datamation.swdsfa.controller.SalesReturnDetController;
import com.datamation.swdsfa.controller.TaxDetController;
import com.datamation.swdsfa.helpers.ListExpandHelper;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.Control;
import com.datamation.swdsfa.model.Customer;
import com.datamation.swdsfa.model.FInvRDet;
import com.datamation.swdsfa.model.FInvRHed;
import com.datamation.swdsfa.model.InvDet;
import com.datamation.swdsfa.model.InvHed;
import com.datamation.swdsfa.model.OrderDetail;
import com.datamation.swdsfa.model.Order;
import com.datamation.swdsfa.model.SalRep;
import com.datamation.swdsfa.model.StkIss;
import com.datamation.swdsfa.model.VanSalPrintPre;
import com.datamation.swdsfa.view.DebtorDetailsActivity;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class VanSalePrintPreviewAlertBox {

    public static final String SETTINGS = "SETTINGS";
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = -1;
    public static final String SETTING = "SETTINGS";
    public static SharedPreferences localSP;
    ResourceBundle.Control control;
    SalRep salRep;
    Customer debtor;
    VanSalPrintPre vansalprintpre;
    String Fdealadd3 = "";
    String Fdealmob = "";
    String printLineSeperator = "____________________________________________";
    String printLineSeperatorNew = "--------------------------------------------";
    String printSpaceName = "                    ";
    String printSpaceQty = "     ";
    String Heading_a = "";
    String Heading_bmh = "";
    String Heading_b = "";
    String Heading_c = "";
    String Heading_d = "";
    String Heading_e = "";
    String buttomRaw = "";
    String BILL;
    LinearLayout lnComp, lnCus, lnTerm;
    Dialog dialogProgress;
    ListView lvItemDetails,lvReturnDetails;
    String PRefno = "";
    String printMainInvDiscount, printMainInvDiscountVal, PrintNetTotalValuePrintVal, printCaseQuantity,
            printPicesQuantity, TotalInvoiceDiscount;
    int countCountInv;
    BluetoothAdapter mBTAdapter;
    BluetoothSocket mBTSocket = null;
    String PRINTER_MAC_ID;
    Context context;
    private Customer outlet;

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            try {
                String action = intent.getAction();

                if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    System.out.println("***" + device.getName() + " : " + device.getAddress());

                    if (device.getAddress().equalsIgnoreCase(PRINTER_MAC_ID)) {
                        mBTAdapter.cancelDiscovery();
                        dialogProgress.dismiss();
                        printBillToDevice(PRINTER_MAC_ID);
                    }
                }
            } catch (Exception e) {
                Log.e("Class  ", "fire 1 ", e);

            }
        }
    };

    public VanSalePrintPreviewAlertBox(Context context) {
        this.context = context;
    }

	/*-*-*-*-*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-**-*-**-*-*-*--*/

    public int PrintDetailsDialogbox(final Context context, String title, String refno, String retRef, boolean isPreSale) {

        try
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);

            View promptView = layoutInflater.inflate(R.layout.sales_management_vansales_print_view, null);
            localSP = context.getSharedPreferences(SETTINGS, 0);

            final TextView Companyname = (TextView) promptView.findViewById(R.id.headcompanyname);
            final TextView Companyaddress1 = (TextView) promptView.findViewById(R.id.headaddress1);
            final TextView Companyaddress2 = (TextView) promptView.findViewById(R.id.headaddress2);
            final TextView CompanyTele = (TextView) promptView.findViewById(R.id.headteleno);
            final TextView Companyweb = (TextView) promptView.findViewById(R.id.headwebsite);
            final TextView Companyemail = (TextView) promptView.findViewById(R.id.heademail);

            final TextView SalesRepname = (TextView) promptView.findViewById(R.id.salesrepname);
            final TextView SalesRepPhone = (TextView) promptView.findViewById(R.id.salesrepphone);

            final TextView Debname = (TextView) promptView.findViewById(R.id.headcusname);
            final TextView Debaddress1 = (TextView) promptView.findViewById(R.id.headcusaddress1);
            final TextView Debaddress2 = (TextView) promptView.findViewById(R.id.headcusaddress2);
            final TextView DebTele = (TextView) promptView.findViewById(R.id.headcustele);

            final TextView SalOrdDate = (TextView) promptView.findViewById(R.id.printsalorddate);
            final TextView OrderNo = (TextView) promptView.findViewById(R.id.printrefno);
            final TextView Remarks = (TextView) promptView.findViewById(R.id.printremark);

            final TextView txtfiQty = (TextView) promptView.findViewById(R.id.printFiQty);
            final TextView TotalDiscount = (TextView) promptView.findViewById(R.id.printtotaldisamt);
            final TextView TotalNetValue = (TextView) promptView.findViewById(R.id.printnettotal);

            final TextView txtTotVal = (TextView) promptView.findViewById(R.id.printTotalVal);
            final TextView TotalPieceQty = (TextView) promptView.findViewById(R.id.printpiecesqty);
            final TextView txtRoute = (TextView) promptView.findViewById(R.id.printRoute);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(title.toUpperCase());

            alertDialogBuilder.setView(promptView);

            ArrayList<Control> controlList;
            controlList = new CompanyDetailsController(context).getAllControl();

            PRefno = refno;

//            Companyname.setText(controlList.get(0).getFCONTROL_COM_NAME());
//            Companyaddress1.setText(controlList.get(0).getFCONTROL_COM_ADD1());
//            Companyaddress2.setText(controlList.get(0).getFCONTROL_COM_ADD2());
//            CompanyTele.setText("Tele: " + controlList.get(0).getFCONTROL_COM_TEL1() + " / Fax: " + controlList.get(0).getFCONTROL_COM_FAX());
//            Companyweb.setText(controlList.get(0).getFCONTROL_COM_WEB());
//            Companyemail.setText(controlList.get(0).getFCONTROL_COM_EMAIL());
//
//            SalRep salrep = new SalRepController(context).getSaleRep(new SalRepController(context).getCurrentRepCode());
//            SalesRepname.setText(salrep.getREPCODE() + "/ " + salrep.getNAME());
//            SalesRepPhone.setText("Tele: " + salrep.getMOBILE());

            Companyname.setText("DATAMATION SYSTEMS (PVT) LTD");
            Companyaddress1.setText("No.15, SHRUBBERY GARDENS,");
            Companyaddress2.setText("COLOMBO 04");
            CompanyTele.setText("Tele: " +"0114100100");
            Companyweb.setText("datamation.lk");
            Companyemail.setText("info@datamation.lk");

            String repCode = new SalRepController(context).getCurrentRepCode();
            SalRep salRep = new SalRepController(context).getSaleRepDet(repCode);

//        User salrep = SharedPref.getInstance(context).getLoginUser();
            SalesRepname.setText(salRep.getRepCode() + "/ " + salRep.getNAME());
            SalesRepPhone.setText("Tele: " + salRep.getMOBILE());

            if (!isPreSale) // if van sale
            {
                InvHed invhed = new InvHedController(context).getDetailsforPrint(refno);

                ArrayList<InvDet> list = new InvDetController(context).getAllItemsforPrint(refno);
                ArrayList<FInvRDet> Rlist = new SalesReturnDetController(context).getAllInvRDetForPrint(refno.trim());

                Customer debtor = new CustomerController(context).getSelectedCustomerByCode(invhed.getFINVHED_DEBCODE());
                outlet = debtor;

                Debname.setText(debtor.getCusCode() + "-" + debtor.getCusName());
                Debaddress1.setText(debtor.getCusAdd1() + ", " + debtor.getCusAdd2());
                //Debaddress2.setText(debtor.getFDEBTOR_ADD3());
                DebTele.setText(debtor.getCusMob());

                SalOrdDate.setText("Date: " + invhed.getFINVHED_TXNDATE() + " " + currentTime());
                Remarks.setText("Remarks: " + invhed.getFINVHED_REMARKS());
                OrderNo.setText("Ref No: " + refno);
                txtRoute.setText(invhed.getFINVHED_TOURCODE() + " / " + invhed.getFINVHED_ROUTECODE());

                int qty = 0, fiQty = 0, returnQty = 0;
                double dDisc = 0, dTotAmt = 0, returnTot = 0;

                for (InvDet det : list) {

                    if (det.getFINVDET_TYPE().equals("SA"))
                        qty += Integer.parseInt(det.getFINVDET_QTY());
                    //            else
                    //                fiQty += Integer.parseInt(det.getFINVDET_QTY());

                    // dDisc += Double.parseDouble(det.getFINVDET_DISVALAMT());
                    dTotAmt += Double.parseDouble(det.getFINVDET_AMT());
                }
                for (FInvRDet retrnDet :Rlist){
                    returnQty += Integer.parseInt(retrnDet.getFINVRDET_QTY());
                    returnTot += Double.parseDouble(retrnDet.getFINVRDET_AMT());
                }

                lvItemDetails = (ListView) promptView.findViewById(R.id.vansaleList);
                lvReturnDetails = (ListView) promptView.findViewById(R.id.returnList);
                lvItemDetails.setAdapter(new PrintVanSaleItemAdapter(context, list));
                lvReturnDetails.setAdapter(new PrintVanSaleReturnAdapter(context, Rlist));

                /*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-Gross/Net values*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

                TotalPieceQty.setText(String.valueOf(qty));
                TotalNetValue.setText(String.format("%,.2f", (dTotAmt-returnTot)));
                TotalDiscount.setText(String.format("%,.2f", returnTot));
                txtfiQty.setText(String.valueOf(returnQty));
                txtTotVal.setText(String.format("%,.2f", dTotAmt));

                localSP = context.getSharedPreferences(SETTINGS, 0);
                PRINTER_MAC_ID =  new SharedPref(context).getGlobalVal("printer_mac_address").toString();

                alertDialogBuilder.setCancelable(false).setPositiveButton("Print", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PrintCurrentview();
                    }
                });

                alertDialogBuilder.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertD = alertDialogBuilder.create();
                alertD.show();
                ListExpandHelper.getListViewSize(lvItemDetails);
                ListExpandHelper.getListViewSize(lvReturnDetails);

                return 1;

            }
            else // if pre sale
            {
                Order presale = new OrderController(context).getDetailsForPrint(refno);

                ArrayList<OrderDetail> list = new OrderDetailController(context).getAllItemsForPrint(refno);

                Customer debtor = new CustomerController(context).getSelectedCustomerByCode(presale.getORDER_DEBCODE());
                outlet = debtor;

                Debname.setText(debtor.getCusCode() + "-" + debtor.getCusName());
                Debaddress1.setText(debtor.getCusAdd1() + ", " + debtor.getCusAdd2());
                //Debaddress2.setText(debtor.getFDEBTOR_ADD3());
                DebTele.setText(debtor.getCusMob());

                SalOrdDate.setText("Date: " + presale.getORDER_TXNDATE() + " " + currentTime());
                Remarks.setText("Remarks: " + presale.getORDER_REMARKS());
                OrderNo.setText("Ref No: " + refno);
                txtRoute.setText(presale.getORDER_TOURCODE() + " / " + presale.getORDER_ROUTECODE());

                int qty = 0, fiQty = 0, returnQty = 0;
                double dDisc = 0, dTotAmt = 0, returnTot = 0;
                String itemCode = "";

                for (OrderDetail det : list) {
                    itemCode = det.getFORDERDET_ITEMCODE();
                    if (det.getFORDERDET_TYPE().equals("SA"))
                    {
                        qty += Integer.parseInt(det.getFORDERDET_QTY());

                    }
                    else
                    {
                        fiQty += Integer.parseInt(det.getFORDERDET_QTY());
                    }

                    // dDisc += Double.parseDouble(det.getFINVDET_DISVALAMT());
                    dTotAmt += Double.parseDouble(det.getFORDERDET_AMT());
                }

                if (!retRef.equals(""))
                {
                    ArrayList<FInvRDet> Rlist = new SalesReturnDetController(context).getAllInvRDetForPrint(retRef);

                    for (FInvRDet retrnDet :Rlist){
                        returnQty += Integer.parseInt(retrnDet.getFINVRDET_QTY());
                        returnTot += Double.parseDouble(retrnDet.getFINVRDET_AMT());
                    }
                    lvReturnDetails = (ListView) promptView.findViewById(R.id.returnList);
                    lvReturnDetails.setAdapter(new PrintVanSaleReturnAdapter(context, Rlist));
                }
                else
                {
                    lvReturnDetails = (ListView) promptView.findViewById(R.id.returnList);
                    lvReturnDetails.setAdapter(null);
                }


                lvItemDetails = (ListView) promptView.findViewById(R.id.vansaleList);
                lvItemDetails.setAdapter(new PrintPreSaleItemAdapter(context, list, outlet.getCusCode()));

                /*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-Gross/Net values*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

                String sArray[] = new TaxDetController(context).calculateTaxForwardFromDebTax(outlet.getCusCode(), itemCode, dTotAmt);
                String amt = String.format("%.2f",Double.parseDouble(sArray[0]));

                TotalPieceQty.setText(String.valueOf(qty));
                TotalNetValue.setText(String.format("%,.2f", (Double.parseDouble(amt)-returnTot)));
                TotalDiscount.setText(String.format("%,.2f", returnTot));
                txtfiQty.setText(String.valueOf(returnQty));
                txtTotVal.setText(String.format("%,.2f", Double.parseDouble(amt)));

                localSP = context.getSharedPreferences(SETTINGS, 0);
                PRINTER_MAC_ID =  new SharedPref(context).getGlobalVal("printer_mac_address").toString();

                alertDialogBuilder.setCancelable(false).setPositiveButton("Print", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //PrintCurrentview();
                        checkPrinter();
                        onCancelClick(dialog,id);

                    }
                });

                alertDialogBuilder.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onCancelClick(dialog,id);
                        dialog.cancel();
                    }
                });

                AlertDialog alertD = alertDialogBuilder.create();
                alertD.show();
                ListExpandHelper.getListViewSize(lvItemDetails);
                ListExpandHelper.getListViewSize(lvReturnDetails);

                return 1;

            }

        }
        catch (Exception ex)
        {
            return -1;
        }
    }

    public void onCancelClick(DialogInterface dialog, int which) {
        Intent intent = new Intent(context, DebtorDetailsActivity.class);
        intent.putExtra("outlet", outlet);
        //intent.putExtra("sales_order", false);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public double getNonTaxTotal(ArrayList<InvDet> list) {

        double totAmt = 0;

        for (InvDet det : list) {
            double sellPrice = Double.parseDouble(det.getFINVDET_SELL_PRICE()) - (Double.parseDouble(det.getFINVDET_TAX_AMT()) / Double.parseDouble(det.getFINVDET_QTY()));
            totAmt += Double.parseDouble(det.getFINVDET_QTY()) * sellPrice;
        }

        return totAmt;
    }

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

    public void printItems() {
        final int LINECHAR = 44;
        String printGapAdjustCom = "                      ";

        ArrayList<Control> controlList;
        controlList = new CompanyDetailsController(context).getAllControl();

        SalRep salrep = new SalRepController(context).getSaleRep(new SalRepController(context).getCurrentRepCode());

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

        String SalesRepNamestr = "Sales Rep: " + salrep.getRepCode() + "/ " + salrep.getNAME().trim();// +
        // "/
        // "
        // +
        // salrep.getLOCCODE();

        int lengthDealE = SalesRepNamestr.length();
        int lengthDealEB = (LINECHAR - lengthDealE) / 2;
        String printGapAdjustE = printGapAdjust.substring(0, Math.min(lengthDealEB, printGapAdjust.length()));
        String subTitleheadF = printGapAdjustE + SalesRepNamestr;

        String SalesRepPhonestr = "Tele: " + salrep.getTELE().trim();
        int lengthDealF = SalesRepPhonestr.length();
        int lengthDealFB = (LINECHAR - lengthDealF) / 2;
        String printGapAdjustF = printGapAdjust.substring(0, Math.min(lengthDealFB, printGapAdjust.length()));
        String subTitleheadG = printGapAdjustF + SalesRepPhonestr;

        String subTitleheadH = printLineSeperatorNew;

        InvHed invHed = new InvHedController(context).getDetailsforPrint(PRefno);
        FInvRHed invRHed = new SalesReturnController(context).getDetailsforPrint(PRefno);
        Customer debtor = new CustomerController(context).getSelectedCustomerByCode(invHed.getFINVHED_DEBCODE());

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

        String subTitleheadM = "VJO Date: " + invHed.getFINVHED_TXNDATE() + " " + currentTime();
        int lengthDealM = subTitleheadM.length();
        int lengthDealMB = (LINECHAR - lengthDealM) / 2;
        String printGapAdjustM = printGapAdjust.substring(0, Math.min(lengthDealMB, printGapAdjust.length()));

        String subTitleheadN = "VJO Number: " + PRefno;
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

        ArrayList<InvDet> itemList = new InvDetController(context).getAllItemsforPrint(PRefno);
        ArrayList<FInvRDet> Rlist = new SalesReturnDetController(context).getAllInvRDetForPrint(PRefno);

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
            String sItemname = new ItemController(context).getItemNameByCode(sItemcode);
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
                String sRetItemname = new ItemController(context).getItemNameByCode(sRetItemcode);
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

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

    public void PrintCurrentview() {
         checkPrinter();
//        if (PRINTER_MAC_ID.equals("404")) {
//        Log.v("", "No MAC Address Found.Enter Printer MAC Address.");
//        Toast.makeText(context, "No MAC Address Found.Enter Printer MAC Address.", Toast.LENGTH_LONG).show();
//        }
//        else {
        //printItems();
        // }
    }

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

    private void checkPrinter() {

        if (PRINTER_MAC_ID.trim().length() == 0) {
            PRINTER_MAC_ID = "404";
        } else {
            PRINTER_MAC_ID = PRINTER_MAC_ID;
        }

        if (PRINTER_MAC_ID.equals("404")) {
            Log.v("", "No MAC Address Found.Enter Printer MAC Address.");
            Toast.makeText(context, "No MAC Address Found.Enter Printer MAC Address.", Toast.LENGTH_LONG).show();
        }
    }

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

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
            android.widget.Toast.makeText(context, "Device has no bluetooth		 capability...", android.widget.Toast.LENGTH_SHORT).show();
        } else {
            if (!mBTAdapter.isEnabled()) {
                Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            }
            printBillToDevice(PRINTER_MAC_ID);
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        }
    }

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

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
            android.widget.Toast.makeText(context, "Printer Device Disable Or Invalid MAC.Please Enable the Printer or MAC Address.", android.widget.Toast.LENGTH_LONG).show();
            e.printStackTrace();
            this.PrintDetailsDialogbox(context, "", PRefno,"",false);
        }
    }

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

    private String currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }


    public static String padString(String str, int leng) {
        for (int i = str.length(); i < leng; i++)
            str += " ";
        return str;
    }
}
