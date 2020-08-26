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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.PrintReceiptAdapter;
import com.datamation.hmdsfa.controller.CompanyDetailsController;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.ReceiptController;
import com.datamation.hmdsfa.controller.ReceiptDetController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.helpers.ListExpandHelper;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Control;
import com.datamation.hmdsfa.model.Customer;
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
    String buttomRaw = "";
    String Heading_d = "";
    private Customer debtor;
    String BILL;
    LinearLayout lnBank, lnCHQno,lnCHDate;

    Dialog dialogProgress;

    ListView lvItemDetails;

    String PRefno = "";

    String printMainInvDiscount, printMainInvDiscountVal,
            PrintNetTotalValuePrintVal, printCaseQuantity, printPicesQuantity,
            TotalInvoiceDiscount;

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

        final TextView SalOrdDate = (TextView) promptView.findViewById(R.id.printsalorddate);
        final TextView OrderNo = (TextView) promptView.findViewById(R.id.printrefno);
        final TextView Remarks = (TextView) promptView.findViewById(R.id.printremark);

        final TextView tvTotalAlloc = (TextView) promptView.findViewById(R.id.recTotalAlloc);
        final TextView tvPayMode = (TextView) promptView.findViewById(R.id.recPayMode);
        final TextView tvBank = (TextView) promptView.findViewById(R.id.recBank);
        final TextView tvChqNo = (TextView) promptView.findViewById(R.id.recChqNo);
        final TextView tvChqDate = (TextView) promptView.findViewById(R.id.recChqDate);

        //final TextView tvChequeOrCardNoText = (TextView) promptView.findViewById(R.id.recChequeOrCard);

        lnBank = (LinearLayout) promptView.findViewById(R.id.linearBank);
        lnCHQno = (LinearLayout) promptView.findViewById(R.id.linearChqNo);
        lnCHDate = (LinearLayout) promptView.findViewById(R.id.linearChqDate);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title.toUpperCase());

        alertDialogBuilder.setView(promptView);

        ArrayList<Control> controlList;
        controlList = new CompanyDetailsController(context).getAllControl();

        PRefno = refno;

        // Print Preview Company Details.
        Companyname.setText(controlList.get(0).getFCONTROL_COM_NAME());
        Companyaddress1.setText(controlList.get(0).getFCONTROL_COM_ADD1());
        Companyaddress2.setText(controlList.get(0).getFCONTROL_COM_ADD2());
        CompanyTele.setText(controlList.get(0).getFCONTROL_COM_TEL1());
        Companyweb.setText(controlList.get(0).getFCONTROL_COM_WEB());
        Companyemail.setText(controlList.get(0).getFCONTROL_COM_EMAIL());

        SalRep salrep = new SalRepController(context).getSaleRep(new SalRepController(context).getCurrentRepCode());
        SalesRepname.setText(salrep.getNAME());
        //SalesRepPhone.setText(salrep.getMOBILE());

        ReceiptHed recHed = new ReceiptController(context).getReceiptByRefno(refno);
        ArrayList<ReceiptDet> list = new ReceiptDetController(context).GetReceiptByRefno(refno);
        debtor = new CustomerController(context).getSelectedCustomerByCode(recHed.getFPRECHED_DEBCODE());

        Debname.setText(debtor.getCusName());
        Debaddress1.setText(debtor.getCusAdd1() + " ");
        Debaddress2.setText(debtor.getCusAdd2() + " " + debtor.getCusAdd3());
        DebTele.setText(debtor.getCusMob());
        DebRoute.setVisibility(View.GONE);
        DebTown.setVisibility(View.GONE);

        SalOrdDate.setText("Receipt Date: " + recHed.getFPRECHED_TXNDATE());
        Remarks.setText("Remarks: " + recHed.getFPRECHED_REMARKS());
        OrderNo.setText("Receipt No: " + refno);

        lvItemDetails = (ListView) promptView.findViewById(R.id.vansaleList);
        lvItemDetails.setAdapter(new PrintReceiptAdapter(context, list, refno));


        tvTotalAlloc.setText(String.format("%,.2f", Double.parseDouble(recHed.getFPRECHED_TOTALAMT())));
        if(recHed.getFPRECHED_PAYTYPE().equals("CH"))
        {
            tvPayMode.setText("CHEQUE");
        }
        else if(recHed.getFPRECHED_PAYTYPE().equals("CA"))
        {
            tvPayMode.setText("CASH");
        }


        String[] fullBankArray = {};
        String fullBankName = null;

        if(!recHed.getFPRECHED_CUSBANK().isEmpty())
        {
            fullBankName = recHed.getFPRECHED_CUSBANK();
            if(fullBankName.contains("-"))
            {
                //fullBankArray = fullBankName.split("-");
                //fullBankName = new BankDS(context).getBankCodeAndBranchCodeByBankName(fullBankArray[0],fullBankArray[1]);
                tvBank.setText(fullBankName.toString());
            }
        }


        if (recHed.getFPRECHED_PAYTYPE().equals("CA"))
        {
            lnBank.setVisibility(View.GONE);
            lnCHQno.setVisibility(View.GONE);
            lnCHDate.setVisibility(View.GONE);
        }
        else if (recHed.getFPRECHED_PAYTYPE().equals("CH"))
        {
            //tvChequeOrCardNoText.setText("Cheque No");
            lnBank.setVisibility(View.VISIBLE);
            lnCHQno.setVisibility(View.VISIBLE);
            lnCHDate.setVisibility(View.VISIBLE);
            tvChqNo.setText(recHed.getFPRECHED_CHQNO());
            tvChqDate.setText(recHed.getFPRECHED_CHQDATE());
        }
        else if (recHed.getFPRECHED_PAYTYPE().equals("CC"))
        {
           // tvChequeOrCardNoText.setText("Card No");
            lnBank.setVisibility(View.GONE);
            lnCHQno.setVisibility(View.VISIBLE);
            lnCHDate.setVisibility(View.GONE);
            tvChqNo.setText(recHed.getFPRECHED_CHQNO());
        }
        else if (recHed.getFPRECHED_PAYTYPE().equals("DD"))
        {
           // tvChequeOrCardNoText.setText("Slip No");
            lnBank.setVisibility(View.GONE);
            lnCHQno.setVisibility(View.VISIBLE);
            lnCHDate.setVisibility(View.GONE);
            tvChqNo.setText(recHed.getFPRECHED_CHQNO());
        }
        else if (recHed.getFPRECHED_PAYTYPE().equals("BD"))
        {
           // tvChequeOrCardNoText.setText("Draft No");
            lnBank.setVisibility(View.GONE);
            lnCHQno.setVisibility(View.VISIBLE);
            lnCHDate.setVisibility(View.GONE);
            tvChqNo.setText(recHed.getFPRECHED_CHQNO());
        }

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
        return 0;
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

        String subTitleheadACom = printGapAdjustACom + controlList.get(0).getFCONTROL_COM_NAME();
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

        String SalesRepNamestr = "Sales Rep :" + salrep.getNAME().trim();

        int lengthDealE = SalesRepNamestr.length();
        int lengthDealEB = (LINECHAR - lengthDealE) / 2;
        String printGapAdjustE = printGapAdjust.substring(0, Math.min(lengthDealEB, printGapAdjust.length()));
        String subTitleheadF = printGapAdjustE + SalesRepNamestr;

//        String SalesRepPhonestr = "Tele :" + salrep.getMOBILE().trim();
//        int lengthDealF = SalesRepPhonestr.length();
//        int lengthDealFB = (LINECHAR - lengthDealF) / 2;
//        String printGapAdjustF = printGapAdjust.substring(0, Math.min(lengthDealFB, printGapAdjust.length()));
//        String subTitleheadG = printGapAdjustF + SalesRepPhonestr;

        String subTitleheadH = printLineSeperator;

        ReceiptHed recHed = new ReceiptController(context).getReceiptByRefno(PRefno);
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
        String subTitleheadK = printGapAdjustK + debtor.getCusAdd3();
        String subTitleheadL = printGapAdjustL + debtor.getCusMob();

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

        if (recHed.getFPRECHED_REMARKS().equals(""))
            subTitleheadR = "Remarks : None";
        else
            subTitleheadR = "Remarks : " + recHed.getFPRECHED_REMARKS();

        int lengthDealR = subTitleheadR.length();
        int lengthDealRB = (LINECHAR - lengthDealR) / 2;
        String printGapAdjustR = printGapAdjust.substring(0, Math.min(lengthDealRB, printGapAdjust.length()));

        subTitleheadM = printGapAdjustM + subTitleheadM;
        // subTitleheadMD = printGapAdjustMD + subTitleheadMD;
        subTitleheadN = printGapAdjustN + subTitleheadN;
        subTitleheadR = printGapAdjustR + subTitleheadR;

        String title_Print_F = "\r\n" + subTitleheadF;
//        String title_Print_G = "\r\n" + subTitleheadG;
        String title_Print_H = "\r\n" + subTitleheadH;

        String title_Print_I = "\r\n" + subTitleheadI;
        String title_Print_J = "\r\n" + subTitleheadJ;
        String title_Print_K = "\r\n" + subTitleheadK;
        String title_Print_L = "\r\n" + subTitleheadL;
        String title_Print_O = "\r\n" + subTitleheadO;

        String title_Print_M = "\r\n" + subTitleheadM;
        String title_Print_N = "\r\n" + subTitleheadN;
        String title_Print_R = "\r\n" + subTitleheadR;

        Heading_d = "";
        countCountInv = 0;

        if (subTitleheadK.toString().equalsIgnoreCase(" ") && subTitleheadK.toString()!= null) {
            Heading_bmh = "\r" + title_Print_F + title_Print_H + title_Print_I + title_Print_J + title_Print_O + title_Print_M + title_Print_N + title_Print_R;
           // Heading_bmh = "\r" + title_Print_F + title_Print_G + title_Print_H + title_Print_I + title_Print_J + title_Print_O + title_Print_M + title_Print_N + title_Print_R;
        } else
            Heading_bmh = "\r" + title_Print_F + title_Print_H + title_Print_I + title_Print_J + title_Print_K + title_Print_L + title_Print_O + title_Print_M + title_Print_N + title_Print_R;
           // Heading_bmh = "\r" + title_Print_F + title_Print_G + title_Print_H + title_Print_I + title_Print_J + title_Print_K + title_Print_L + title_Print_O + title_Print_M + title_Print_N + title_Print_R;

        String title_cb = "\r\nINVNO         INV DATE     TOT DUE     PAID ";

        Heading_b = "\r\n" + printLineSeperator + title_cb + "\r\n" + printLineSeperator;

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*Item details*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        ArrayList<ReceiptDet> list = new ReceiptDetController(context).GetReceiptByRefno(PRefno);

        String SPACE1, SPACE2, SPACE3, SPACE4;

        for (ReceiptDet recDet : list) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            long txn = 0;

            String Refno = recDet.getFPRECDET_SALEREFNO()+" ";
            String InvDate = recDet.getFPRECDET_DTXNDATE();

            /*try {
                date = (Date) formatter.parse(InvDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("receipt date is " + date.getTime());
            txn = date.getTime();

            String numOfDays =  String.valueOf(((System.currentTimeMillis() - txn) / DAY_IN_MILLIS));*/

            String BalAmt = String.format(Locale.US, "%.2f",Double.parseDouble(recDet.getFPRECDET_OVPAYAMT()));;
            String Amt = recDet.getFPRECDET_ALOAMT();//String.format(Locale.US, "%,.2f", Double.parseDouble(recDet.getFPRECDET_ALOAMT()));;
            //SPACE1 = String.format("%" + (13 - (Refno.length())) + "s", " ");
            SPACE1 = padString("",(15 - (Refno.length())));
            //SPACE2 = String.format("%" + (12 - (InvDate.length())) + "s", " ");
            SPACE2 = padString("",(13 - (InvDate.length())));
            //SPACE3 = String.format("%" + (8 - (Amt.length())) + "s", " ");
            SPACE3 = padString("",(9 - (BalAmt.length())));
            //SPACE4 = String.format("%" + (7 - (numOfDays.length())) + "s", " ");
            SPACE4 = padString("",(10 - (Amt.length())));
            Heading_d += "\r\n" + Refno + SPACE1 + InvDate  + SPACE3 +BalAmt + SPACE4 + Amt;
        }

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String space;

        space = String.format("%" + (LINECHAR - ("Total Allocation".length() + formatDecimal(recHed.getFPRECHED_TOTALAMT()).length())) + "s", " ");
        String summaryTitle_c_Val = "Total Allocation" + space + formatDecimal(recHed.getFPRECHED_TOTALAMT());


        String summaryTitle_e_Val = null;

        if(recHed.getFPRECHED_PAYTYPE().equals("CH"))
        {
            space = String.format("%" + (LINECHAR - ("Pay Mode".length() + "CHEQUE".length())) + "s", " ");
            summaryTitle_e_Val = "Pay Mode" + space + "CHEQUE";
        }
        else if(recHed.getFPRECHED_PAYTYPE().equals("CA"))
        {
            space = String.format("%" + (LINECHAR - ("Pay Mode".length() + "CASH".length())) + "s", " ");
            summaryTitle_e_Val = "Pay Mode" + space + "CASH";
        }

        String summeryCHqNo = null,summeryCHqDate = null,summaryBank = null;

        if(recHed.getFPRECHED_PAYTYPE().equals("CH"))
        {
            space = String.format("%" + (LINECHAR - ("Cheque No".length() + recHed.getFPRECHED_CHQNO().length())) + "s", " ");
            summeryCHqNo = "Cheque No" + space + recHed.getFPRECHED_CHQNO();

            space = String.format("%" + (LINECHAR - ("Cheque Date".length() + recHed.getFPRECHED_CHQDATE().length())) + "s", " ");
            summeryCHqDate = "Cheque Date" + space + recHed.getFPRECHED_CHQDATE();

            space = String.format("%" + (LINECHAR - ("Bank".length() + recHed.getFPRECHED_CUSBANK().length())) + "s", " ");
            summaryBank = "Bank" + space + recHed.getFPRECHED_CUSBANK();
        }
        else if(recHed.getFPRECHED_PAYTYPE().equals("CC"))
        {
            space = String.format("%" + (LINECHAR - ("Card No".length() + recHed.getFPRECHED_CHQNO().length())) + "s", " ");
            summeryCHqNo = "Card No" + space + recHed.getFPRECHED_CHQNO();

            //space = String.format("%" + (LINECHAR - ("Cheque Date".length() + recHed.getFPRECHED_CHQDATE().length())) + "s", " ");
            summeryCHqDate = "";
        }
        else if(recHed.getFPRECHED_PAYTYPE().equals("DD"))
        {
            space = String.format("%" + (LINECHAR - ("Slip No".length() + recHed.getFPRECHED_CHQNO().length())) + "s", " ");
            summeryCHqNo = "Slip No" + space + recHed.getFPRECHED_CHQNO();

            //space = String.format("%" + (LINECHAR - ("Cheque Date".length() + recHed.getFPRECHED_CHQDATE().length())) + "s", " ");
            summeryCHqDate = "";
        }
        else if(recHed.getFPRECHED_PAYTYPE().equals("BD"))
        {
            space = String.format("%" + (LINECHAR - ("Draft No".length() + recHed.getFPRECHED_CHQNO().length())) + "s", " ");
            summeryCHqNo = "Draft No" + space + recHed.getFPRECHED_CHQNO();

            //space = String.format("%" + (LINECHAR - ("Cheque Date".length() + recHed.getFPRECHED_CHQDATE().length())) + "s", " ");
            summeryCHqDate = "";
        }

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String summaryBottom_cpoyline1 = " By Datamation Systems/www.datamation.lk ";
        int lengthsummarybottm = summaryBottom_cpoyline1.length();
        int lengthsummarybottmline1 = (LINECHAR - lengthsummarybottm) / 2;
        String printGapbottmline1 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline1, printGapAdjust.length()));

//        String summaryBottom_cpoyline3 = "www.datamation.lk";
//        int lengthsummarybotline3 = summaryBottom_cpoyline3.length();
//        int lengthsummarybottmline3 = (LINECHAR - lengthsummarybotline3) / 2;
//        String printGapbottmline3 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline3, printGapAdjust.length()));
//
//        String summaryBottom_cpoyline2 = " +94 11 2 501202 / + 94 (0) 777 899899 ";
//        int lengthsummarybotline2 = summaryBottom_cpoyline2.length();
//        int lengthsummarybottmline2 = (LINECHAR - lengthsummarybotline2) / 2;
//        String printGapbottmline2 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline2, printGapAdjust.length()));

        String bottomTitleString = "";

        if (recHed.getFPRECHED_PAYTYPE().equals("CA"))
        {
            bottomTitleString = summaryTitle_c_Val + "\r\n\n" + summaryTitle_e_Val;

        }
        else if (recHed.getFPRECHED_PAYTYPE().equals("CH"))
        {
            bottomTitleString = summaryTitle_c_Val + "\r\n\n" + summaryTitle_e_Val + "\r\n" + summeryCHqNo + "\r\n" + summaryBank + "\r\n" + summeryCHqDate;
        }
        else
        {
            bottomTitleString = summaryTitle_c_Val + "\r\n\n" + summaryTitle_e_Val + "\r\n" + summeryCHqNo + "\r\n"  + summeryCHqDate;
        }

        String buttomTitlef = "\r\n\n\n" + "Receipt Accepted............................";
        String buttomTitlefa = "\r\n\n\n" + "Please place The Rubber Stamp.";
        String buttomTitlecopyw = "\r\n\n\n" + printGapbottmline1 + summaryBottom_cpoyline1;
//        String buttomTitlecopywbottom = "\r\n" + printGapbottmline2 + summaryBottom_cpoyline2;
//        String buttomTitlecopywbottom3 = "\r\n" + printGapbottmline3 + summaryBottom_cpoyline3;

      //  buttomRaw = "\r\n" + printLineSeperator + "\r\n" + bottomTitleString + "\r\n" + printLineSeperator + "\r\n" + buttomTitlef + buttomTitlefa + "\r\n" + printLineSeperator + buttomTitlecopyw + buttomTitlecopywbottom + buttomTitlecopywbottom3 + "\r\n\n\n\n\n\n\n" + printLineSeperator + "\n";
        buttomRaw = "\r\n" + printLineSeperator + "\r\n" + bottomTitleString + "\r\n" + printLineSeperator + "\r\n" + buttomTitlef + buttomTitlefa + "\r\n" + printLineSeperator + buttomTitlecopyw  + "\r\n\n\n\n\n\n\n" + printLineSeperator + "\n";

        callPrintDevice();

    }

    public void PrintCurrentview() {
        //checkPrinter();
       // if (PRINTER_MAC_ID.equals("404")) {
        //    Log.v("", "No MAC Address Found.Enter Printer MAC Address.");
        //    Toast.makeText(context, "No MAC Address Found.Enter Printer MAC Address.", Toast.LENGTH_LONG).show();
       // } else {
            printItems();
       // }
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
    }

    private void callPrintDevice() {
        BILL = " ";

        BILL = Heading_a + Heading_bmh + Heading_b + Heading_d + buttomRaw;
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
                // int REQUEST_ENABLE_BT = 1;
                // startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT);
            }
            printBillToDevice(PRINTER_MAC_ID);
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            // registerReceiver(mReceiver, filter); // Don't forget to
            // unregister
            // during
            // onDestroy

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
            System.out.println("**************************#****connecting");
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
            // setResult(RESULT_OK);
            // finish();
        } catch (Exception e) {
            Log.e("Class ", "fire 2 ", e);
            // toast.createToastErrorMessage("Device has no MacAddress.Please Enter the MAC Address..",
            // null);
            Toast.makeText(context, "Printer Device Disable Or Invalid MAC.Please Enable the Printer or MAC Address.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            this.PrintDetailsDialogbox(context, "", PRefno);
            // setResult(RESULT_CANCELED);
            // finish();

        }

    }



    public static String padString(String str, int leng) {
        for (int i = str.length(); i < leng; i++)
            str += " ";
        return str;
    }

    // protected void onDestroy() {
    // Log.i("Dest ", "Checking Ddest");
    // //finish();
    // try {
    // if (dialogProgress != null)
    // dialogProgress.dismiss();
    // if (mBTAdapter != null)
    // mBTAdapter.cancelDiscovery();
    // this.unregisterReceiver(mReceiver);
    // } catch (Exception e) {
    // Log.e("Class ", "fire 3", e);
    // }
    // }

}
