package com.datamation.hmdsfa.dialog;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import android.app.ActionBar.LayoutParams;
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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.PrintPayModeAdapter;
import com.datamation.hmdsfa.adapter.PrintReceiptAdapter;
import com.datamation.hmdsfa.controller.BankController;
import com.datamation.hmdsfa.controller.CompanyDetailsController;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.PayModeController;
import com.datamation.hmdsfa.controller.ReceiptController;
import com.datamation.hmdsfa.controller.ReceiptDetController;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.helpers.ListExpandHelper;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Control;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.PayMode;
import com.datamation.hmdsfa.model.ReceiptDet;
import com.datamation.hmdsfa.model.ReceiptHed;
import com.datamation.hmdsfa.model.SalRep;
import com.datamation.hmdsfa.view.DebtorDetailsActivity;

public class ReceiptPreviewAlertBox {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Control control;
    SalRep salRep;

    String Fdealadd3 = "";
    String Fdealmob = "";

    String printLineSeperator = "____________________________________________";
    String printSpaceName = "                    ";
    String printSpaceQty = "     ";
    String Heading_a = "";
    String Heading_bmh = "";
    String Heading_b = "";
    String Heading_e = "";
    String Heading_g = "";
    String buttomRaw = "";
    String Heading_d = "";
    String Heading_h = "";
    private Customer debtor;
    String BILL;


    Dialog dialogProgress;

    ListView lvItemDetails , lvPaymodeDetails;

    String PRefno = "";

    int countCountInv;

    BluetoothAdapter mBTAdapter;
    BluetoothSocket mBTSocket = null;

    String PRINTER_MAC_ID;
    public static final int RESULT_CANCELED = 0;

    public static final int RESULT_OK = -1;

    public static final String SETTING = "SETTINGS";
    Context context;

    public ReceiptPreviewAlertBox(Context context) {
        this.context = context;
    }

    public int PrintDetailsDialogbox(final Context context, String title, String refno) {

        try {

            LayoutInflater layoutInflater = LayoutInflater.from(context);

            View promptView = layoutInflater.inflate(R.layout.sales_management_receipt_print_view, null);
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
            final TextView DebRoute = (TextView) promptView.findViewById(R.id.headcusroute);
            final TextView DebTown = (TextView) promptView.findViewById(R.id.headcustown);

            final TextView ReceiptDate = (TextView) promptView.findViewById(R.id.printsalorddate);
            final TextView ReceiptNo = (TextView) promptView.findViewById(R.id.printrefno);
            final TextView RepName = (TextView) promptView.findViewById(R.id.printremark);
            final TextView AreaCode = (TextView) promptView.findViewById(R.id.printareacode);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(title.toUpperCase());

            alertDialogBuilder.setView(promptView);

            ArrayList<Control> controlList;
            controlList = new CompanyDetailsController(context).getAllControl();

            PRefno = refno;

            // Print Preview Company Details.
            Companyname.setText("H S Marketing Private Limited");
            Companyaddress1.setText("22/2, Rawathawatta Rd, Rawathawatta, Moratuwa. Colombo");
            Companyaddress2.setText("Tel : 0112655024 Fax No : 112655102");
            CompanyTele.setText("Email : wholesales@hameedia.lk");
            Companyweb.setText("VAT Registration No : 114236314-7000");

            SalRep salrep = new SalRepController(context).getSaleRep(new SalRepController(context).getCurrentRepCode());

            SalesRepname.setText("RECEIPT");

            ReceiptHed recHed = new ReceiptController(context).getReceiptByCommnRefNo(refno);
            ArrayList<ReceiptDet> list = new ReceiptDetController(context).GetReceiptByCommonRefNo(refno);
            ArrayList<PayMode> paymodelist = new ReceiptController(context).getPaidModesByCommonRef(refno);
            debtor = new CustomerController(context).getSelectedCustomerByCode(recHed.getFPRECHED_DEBCODE());

            Debname.setText(debtor.getCusName());
            Debaddress1.setText(debtor.getCusAdd1() + " ");
            Debaddress2.setText(debtor.getCusAdd2()==null? "":debtor.getCusAdd2() + " " + debtor.getCusAdd3()==null? "":debtor.getCusAdd3());
            DebTele.setText(debtor.getCusMob()==null? "": debtor.getCusMob());

            ReceiptDate.setText("Receipt No: " + refno);
            RepName.setText(salrep.getNAME());
            ReceiptNo.setText("Date: " + recHed.getFPRECHED_TXNDATE());
            String routecode = new RouteDetController(context).getRouteCodeByDebCode(debtor.getCusCode());
            AreaCode.setText(""+new RouteController(context).getAreaCodeByRouteCode(routecode));

            lvItemDetails = (ListView) promptView.findViewById(R.id.vansaleList);
            lvItemDetails.setAdapter(new PrintReceiptAdapter(context, list, refno));

            lvPaymodeDetails = (ListView) promptView.findViewById(R.id.paymodelist);
            lvPaymodeDetails.setAdapter(new PrintPayModeAdapter(context, paymodelist, refno));

            localSP = context.getSharedPreferences(SETTINGS, 0);//Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE
            PRINTER_MAC_ID = new SharedPref(context).getGlobalVal("printer_mac_address").toString();
            Log.v("mac_id", PRINTER_MAC_ID);

            alertDialogBuilder.setCancelable(false).setPositiveButton("Print", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Log.v("", "***************************");
                    PrintCurrentview();
                }
            });

            alertDialogBuilder.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(context, DebtorDetailsActivity.class);
                    intent.putExtra("outlet", debtor);
                    context.startActivity(intent);
                    dialog.cancel();
                }
            });


            AlertDialog alertD = alertDialogBuilder.create();
            alertD.show();
            Window window = alertD.getWindow();
            window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            ListExpandHelper.getListViewSize(lvItemDetails);
            ListExpandHelper.getListViewSize(lvPaymodeDetails);
            return 1;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return -1;
        }
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void printItems() {

        final int LINECHAR = 44;
        String printGapAdjustCom = "                        ";

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

        String TelCom = "Tel:" + controlList.get(0).getFCONTROL_COM_TEL1().trim();
        int lengthDealDCom = TelCom.length();
        int lengthDealDBCom = (LINECHAR - lengthDealDCom) / 2;
        String printGapAdjustDCom = printGapAdjustCom.substring(0, Math.min(lengthDealDBCom, printGapAdjustCom.length()));

        int lengthDealECom = controlList.get(0).getFCONTROL_COM_WEB().length();
        int lengthDealEBCom = (LINECHAR - lengthDealECom) / 2;
        String printGapAdjustECom = printGapAdjustCom.substring(0, Math.min(lengthDealEBCom, printGapAdjustCom.length()));

        int lengthDealFCom = controlList.get(0).getFCONTROL_COM_EMAIL().length();
        int lengthDealFBCom = (LINECHAR - lengthDealFCom) / 2;
        String printGapAdjustFCom = printGapAdjustCom.substring(0, Math.min(lengthDealFBCom, printGapAdjustCom.length()));
        String comname = "<b>" + controlList.get(0).getFCONTROL_COM_NAME() + "</b> ";

        String subTitleheadACom = printGapAdjustACom + Html.fromHtml(comname);
        String subTitleheadBCom = printGapAdjustBCom + controlList.get(0).getFCONTROL_COM_ADD1();
        String subTitleheadCCom = printGapAdjustCCom + controlList.get(0).getFCONTROL_COM_ADD2() + ", " + controlList.get(0).getFCONTROL_COM_ADD3() + ".";
        String subTitleheadDCom = printGapAdjustDCom + controlList.get(0).getFCONTROL_COM_TEL1();
        String subTitleheadECom = printGapAdjustECom + controlList.get(0).getFCONTROL_COM_WEB();
        String subTitleheadFCom = printGapAdjustFCom + controlList.get(0).getFCONTROL_COM_EMAIL();
        String subTitleheadGCom = printLineSeperator;

        String title_Print_ACom = "\r\n" + subTitleheadACom;
        String title_Print_BCom = "\r\n" + subTitleheadBCom;
        String title_Print_CCom = "\r\n" + subTitleheadCCom;
        String title_Print_DCom = "\r\n" + subTitleheadDCom;
        String title_Print_ECom = "\r\n" + subTitleheadECom;
        String title_Print_FCom = "\r\n" + subTitleheadFCom;
        String title_Print_GCom = "\r\n" + subTitleheadGCom;

        Heading_a = title_Print_ACom + title_Print_BCom + title_Print_CCom + title_Print_DCom + title_Print_ECom + title_Print_FCom + title_Print_GCom;

        String printGapAdjust = "                        ";

        String SalesRepNamestr = "<RECEIPT>";

        int lengthDealE = SalesRepNamestr.length();
        int lengthDealEB = (LINECHAR - lengthDealE) / 2;
        String printGapAdjustE = printGapAdjust.substring(0, Math.min(lengthDealEB, printGapAdjust.length()));
        String subTitleheadF = printGapAdjustE + SalesRepNamestr;

        String subTitleheadH = printLineSeperator;

        ReceiptHed recHed = new ReceiptController(context).getReceiptByCommnRefNo(PRefno);
        Customer debtor = new CustomerController(context).getSelectedCustomerByCode(recHed.getFPRECHED_DEBCODE());

        int lengthDealI = debtor.getCusName().length();
        int lengthDealIB = (LINECHAR - lengthDealI) / 2;
        String printGapAdjustI = printGapAdjust.substring(0, Math.min(lengthDealIB, printGapAdjust.length()));

        String customerAddressStr = debtor.getCusAdd1() + "," + debtor.getCusAdd2();
        int lengthDealJ = customerAddressStr.length();
        int lengthDealJB = 0;
        if(LINECHAR>=lengthDealJ){
            lengthDealJB =  (LINECHAR - lengthDealJ) / 2;
        }
        String printGapAdjustJ = printGapAdjust.substring(0, Math.min(lengthDealJB, printGapAdjust.length()));

        String printGapAdjustK = null;
        String printGapAdjustL = null;

        if(debtor.getCusAdd3() != null)
        {
            int lengthDealK = debtor.getCusAdd3().length();
            int lengthDealKB = (LINECHAR - lengthDealK) / 2;
            printGapAdjustK = printGapAdjust.substring(0, Math.min(lengthDealKB, printGapAdjust.length()));
        }else{
            int lengthDealK = 1;
            int lengthDealKB = (LINECHAR - lengthDealK) / 2;
            printGapAdjustK = printGapAdjust.substring(0, Math.min(lengthDealKB, printGapAdjust.length()));
        }

        if(debtor.getCusMob() != null)
        {
            int lengthDealL = debtor.getCusMob().length();
            int lengthDealLB = (LINECHAR - lengthDealL) / 2;
            printGapAdjustL = printGapAdjust.substring(0, Math.min(lengthDealLB, printGapAdjust.length()));
        }else{
            int lengthDealL = 1;
            int lengthDealLB = (LINECHAR - lengthDealL) / 2;
            printGapAdjustL = printGapAdjust.substring(0, Math.min(lengthDealLB, printGapAdjust.length()));
        }


        String subTitleheadI = printGapAdjustI + debtor.getCusName();
        String subTitleheadJ = printGapAdjustJ + debtor.getCusAdd1() + "," + debtor.getCusAdd2();
        String subTitleheadK = printGapAdjustK + debtor.getCusAdd3()==null? "":debtor.getCusAdd3();
        String subTitleheadL = printGapAdjustL + debtor.getCusMob()==null? "":debtor.getCusMob();

        String subTitleheadO = printLineSeperator;

        String subTitleheadM = "Receipt Date : " + recHed.getFPRECHED_TXNDATE();
        int lengthDealM = subTitleheadM.length();
        int lengthDealMB = (LINECHAR - lengthDealM) / 2;
        String printGapAdjustM = printGapAdjust.substring(0, Math.min(lengthDealMB, printGapAdjust.length()));

        String subTitleheadN = "Receipt No : " + PRefno;
        int lengthDealN = subTitleheadN.length();
        int lengthDealNB = (LINECHAR - lengthDealN) / 2;
        String printGapAdjustN = printGapAdjust.substring(0, Math.min(lengthDealNB, printGapAdjust.length()));

        String subTitleheadR;
        String subTitleheadArea;

        String repCode = new SalRepController(context).getCurrentRepCode();
        SalRep salRep = new SalRepController(context).getSaleRepDet(repCode);

        subTitleheadR = "" + salRep.getRepCode() + "/ " + salRep.getNAME();
        String routecode = new RouteDetController(context).getRouteCodeByDebCode(debtor.getCusCode());
        subTitleheadArea = ""+new RouteController(context).getAreaCodeByRouteCode(routecode);

        int lengthDealR = subTitleheadR.length();
        int lengthDealRB = (LINECHAR - lengthDealR) / 2;
        String printGapAdjustR = printGapAdjust.substring(0, Math.min(lengthDealRB, printGapAdjust.length()));

        int lengthArea = subTitleheadArea.length();
        int lengthAreaRB = (LINECHAR - lengthArea) / 2;
        String printGapAdjustArea = printGapAdjust.substring(0, Math.min(lengthAreaRB, printGapAdjust.length()));

        subTitleheadM = printGapAdjustM + subTitleheadM;
        subTitleheadN = printGapAdjustN + subTitleheadN;
        subTitleheadR = printGapAdjustR + subTitleheadR;
        subTitleheadArea = printGapAdjustArea + subTitleheadArea;

        String title_Print_F = "\r\n" + subTitleheadF; // Print Heading RECEIPT
        String title_Print_H = "\r\n" + subTitleheadH; // Print line Separator

        String title_Print_I = "\r\n" + subTitleheadI; // Print Customer Name
        String title_Print_J = "\r\n" + subTitleheadJ; // Print Customer Address 1 and 2
        String title_Print_K = "\r\n" + subTitleheadK; // Print Customer Address 3
        String title_Print_L = "\r\n" + subTitleheadL; // Print Customer Mobile
        String title_Print_O = "\r\n" + subTitleheadO; // Print line Separator

        String title_Print_M = "\r\n" + subTitleheadM; // Print Receipt Date
        String title_Print_N = "\r\n" + subTitleheadN; // Print Receipt No
        String title_Print_R = "\r\n" + subTitleheadR; // Print Sales Rep Name
        String title_Print_Area = "\r\n" + subTitleheadArea; // Print Route

        Heading_d = "";
        countCountInv = 0;

        if (subTitleheadK.toString().equalsIgnoreCase(" ") && subTitleheadK.toString()== null) {
            Heading_bmh = "\r" + title_Print_F + title_Print_H + title_Print_I + title_Print_J + title_Print_O + title_Print_M + title_Print_N + title_Print_R + title_Print_Area;
        } else

            Heading_bmh = "\r" + title_Print_F + title_Print_H + title_Print_I + title_Print_J + title_Print_K + title_Print_L + title_Print_O + title_Print_M + title_Print_N + title_Print_R + title_Print_Area;

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*paymode details*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String title_pay_mode = "\r\nPAY     BANK    CHQ NO  CHQ DATE     REC AMT ";

        Heading_g = "\r\n" + printLineSeperator + title_pay_mode + "\r\n" + printLineSeperator;

        ArrayList<PayMode> plist = new ReceiptController(context).getPaidModesByCommonRef(PRefno);

        String spc1,spc2,spc3,spc4,spc5;
        double totalamt_received = 0;

        for (PayMode payMode : plist) {

            String PayType = payMode.getFPAYMODE_PAID_TYPE();
            String Bank = new BankController(context).getBankNamebyCode(payMode.getFPAYMODE_PAID_BANK());
            String ChqNo = payMode.getFPAYMODE_PAID_CHEQUE_NO();
            String ChqDate = "";

            if (PayType.equals("CA")){
                 ChqDate = payMode.getFPAYMODE_PAID_DATE();
            }else if(PayType.equals("CH")){
                 ChqDate = payMode.getFPAYMODE_PAID_CHEQUE_DATE();
            }
            String Amt = payMode.getFPAYMODE_PAID_AMOUNT();//String.format(Locale.US, "%,.2f", Double.parseDouble(recDet.getFPRECDET_ALOAMT()));;
            totalamt_received += Double.parseDouble(payMode.getFPAYMODE_PAID_AMOUNT());
            int banknameLen = Bank.length();

            if(banknameLen>10){
                Bank = Bank.substring(0,10).trim();
            }else
            {
                Bank = Bank;
            }


            spc1 = padString("",(4 - (PayType.length())));
            spc2 = padString("",(12 - (Bank.length())));
            spc3 = padString("",(7 - (ChqNo.length())));
            spc4 = padString("",(11 - (ChqDate.length())));
            spc5 = padString("",(11 - (Amt.length())));

            Heading_h += "\r\n" + PayType + spc1 + Bank  + spc2 + ChqNo + spc3 + ChqDate + spc4+spc5 + Amt ;
        }

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*Set Total Received Title*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/


        String title_tot_received = "\r\nTOTAL RECEIVED         "+ String.valueOf(totalamt_received);

        Heading_e = "\r\n" + printLineSeperator + title_tot_received + "\r\n" ;

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*item details*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String title_cb = "\r\nINVNO            INV DATE   PAID AMT  DUE AMT ";

        Heading_b = "\r\n" + printLineSeperator + title_cb + "\r\n" + printLineSeperator;

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*item details*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        ArrayList<ReceiptDet> list = new ReceiptDetController(context).GetReceiptByCommonRefNo(PRefno);

        String SPACE1, SPACE2, SPACE3, SPACE4;

        for (ReceiptDet recDet : list) {

            String Refno = recDet.getFPRECDET_REFNO1()+" ";
            String InvDate = recDet.getFPRECDET_DTXNDATE();


            String BalAmt = String.format(Locale.US, "%.2f",Double.parseDouble(recDet.getFPRECDET_BAMT()));;
            String Amt = recDet.getFPRECDET_ALOAMT();//String.format(Locale.US, "%,.2f", Double.parseDouble(recDet.getFPRECDET_ALOAMT()));;
            SPACE1 = padString("",(17 - (Refno.length())));
            SPACE2 = padString("",(11 - (InvDate.length())));
            SPACE3 = padString("",(9 - (Amt.length())));
            SPACE4 = padString("",(9 - (BalAmt.length())));

            Heading_d += "\r\n" + Refno + SPACE1 + InvDate  + SPACE3 + Amt + SPACE4 + BalAmt;
        }

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String summaryBottom_cpoyline1 = " By Datamation Systems/www.datamation.lk ";
        int lengthsummarybottm = summaryBottom_cpoyline1.length();
        int lengthsummarybottmline1 = (LINECHAR - lengthsummarybottm) / 2;
        String printGapbottmline1 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline1, printGapAdjust.length()));


        String buttomTitlecopyw = "\r\n\n\n" + printGapbottmline1 + summaryBottom_cpoyline1;

        String buttomTitlefb = "\r\n\n\n" + "------------------        ------------------" + "\r\n" + "Received on behalf of  Dealer authorization of";
        String buttomTitlef = "\r" + "HS Marketing (Pvt) Ltd.  payment (Sig. & Stmp)" + "\r\n" ;
        buttomRaw = "\r\n" + printLineSeperator + "\r\n" + buttomTitlefb + "\r\n"  + buttomTitlef + "\r\n" + printLineSeperator + buttomTitlecopyw  + "\r\n\n\n\n\n" + "\n";

        callPrintDevice();

    }

    public void PrintCurrentview() {
        checkPrinter();
        if (PRINTER_MAC_ID.equals("404")) {
            Log.v("", "No MAC Address Found.Enter Printer MAC Address.");
            Toast.makeText(context, "No MAC Address Found.Enter Printer MAC Address.", Toast.LENGTH_LONG).show();
        }
        else {
            printItems();
        }
    }


    public String formatDecimal(String sVal) {

        return String.format("%,.2f", Double.parseDouble(sVal));
    }

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

    private void callPrintDevice() {
        BILL = " ";

        BILL = Heading_a + Heading_bmh + Heading_g + Heading_h + Heading_e + Heading_b + Heading_d + buttomRaw;
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

            Toast.makeText(context, "Device has no bluetooth capability...", Toast.LENGTH_SHORT).show();

        } else {
            if (!mBTAdapter.isEnabled()) {
                Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            }
            printBillToDevice(PRINTER_MAC_ID);
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);


        }
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            try {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    System.out.println("***" + device.getName() + " : " + device.getAddress());

                    if (device.getAddress().equalsIgnoreCase(PRINTER_MAC_ID)) {
                        mBTAdapter.cancelDiscovery();
                        dialogProgress.dismiss();
                        printBillToDevice(PRINTER_MAC_ID);
//                        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//                        Intent intent1 = new Intent(context, DebtorDetailsActivity.class);
//                        intent1.putExtra("outlet", debtor);
//                        context.startActivity(intent1);
                    }
                }
            } catch (Exception e) {
                Log.e("Class  ", "fire 1 ", e);

            }
        }
    };

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
            Toast.makeText(context, "Printer Device Disable Or Invalid MAC.Please Enable the Printer or MAC Address.", Toast.LENGTH_LONG).show();
            Log.d(">>>BILL",">>>"+BILL);
            e.printStackTrace();
            this.PrintDetailsDialogbox(context, "", PRefno);
        }
    }



    public static String padString(String str, int leng) {
        for (int i = str.length(); i < leng; i++)
            str += " ";
        return str;
    }

}

