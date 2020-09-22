package com.datamation.hmdsfa.dialog;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.PrintPreSaleItemAdapter;
import com.datamation.hmdsfa.adapter.PrintVanSaleItemAdapter;
import com.datamation.hmdsfa.adapter.PrintVanSaleReturnAdapter;
import com.datamation.hmdsfa.controller.CompanyDetailsController;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.InvDetController;
import com.datamation.hmdsfa.controller.InvHedController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.OrderController;
import com.datamation.hmdsfa.controller.OrderDetailController;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.controller.SalesReturnController;
import com.datamation.hmdsfa.controller.SalesReturnDetController;
import com.datamation.hmdsfa.controller.TaxDetController;
import com.datamation.hmdsfa.helpers.ListExpandHelper;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Control;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.FInvRDet;
import com.datamation.hmdsfa.model.FInvRHed;
import com.datamation.hmdsfa.model.InvDet;
import com.datamation.hmdsfa.model.InvHed;
import com.datamation.hmdsfa.model.OrderDetail;
import com.datamation.hmdsfa.model.Order;
import com.datamation.hmdsfa.model.SalRep;
import com.datamation.hmdsfa.model.StkIss;
import com.datamation.hmdsfa.model.VanSalPrintPre;
import com.datamation.hmdsfa.utils.EnglishNumberToWords;
import com.datamation.hmdsfa.view.DebtorDetailsActivity;

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
    String printLineSeperatorNew = "--------------------------------------------";
    String Heading_a = "";
    String Heading_bmh = "";
    String Heading_b = "";
    String Heading_c = "";
    String Heading_d = "";
   // String Heading_e = "";
    String buttomRaw = "";
    String BILL;
    Dialog dialogProgress;
    ListView lvItemDetails;
    String PRefno = "";
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

    public int PrintDetailsDialogbox(final Context context, final String title, String refno) {

        try
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);

            View promptView = layoutInflater.inflate(R.layout.sales_management_vansales_print_view, null);

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
            final TextView Debvat = (TextView) promptView.findViewById(R.id.headcusvatno);

            final TextView SalOrdDate = (TextView) promptView.findViewById(R.id.printsalorddate);
            final TextView OrderNo = (TextView) promptView.findViewById(R.id.printrefno);
            final TextView Remarks = (TextView) promptView.findViewById(R.id.printremark);

            final TextView txtfiQty = (TextView) promptView.findViewById(R.id.printFiQty);
            final TextView TotalDiscount = (TextView) promptView.findViewById(R.id.printtotaldisamt);
            final TextView TotalNetValue = (TextView) promptView.findViewById(R.id.printnettotal);
            final TextView AmtInWord = (TextView) promptView.findViewById(R.id.amtinwrd);
            final TextView noteofbill = (TextView) promptView.findViewById(R.id.note);

            final TextView txtTotVal = (TextView) promptView.findViewById(R.id.printTotalVal);
            final TextView TotalPieceQty = (TextView) promptView.findViewById(R.id.printpiecesqty);
            final TextView txtRoute = (TextView) promptView.findViewById(R.id.printRoute);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(title.toUpperCase());

            alertDialogBuilder.setView(promptView);

            PRefno = refno;

            Companyname.setText("H S Marketing Private Limited");
            Companyaddress1.setText("22/2, Rawathawatta Rd, Rawathawatta, Moratuwa. Colombo");
            Companyaddress2.setText("Tel : 0112655024 Fax No : 112655102");
            CompanyTele.setText("Email : wholesales@hameedia.lk");
            Companyweb.setText("VAT Registration No : 114236314-7000");

            String repCode = new SalRepController(context).getCurrentRepCode();
            SalRep salRep = new SalRepController(context).getSaleRepDet(repCode);
            InvHed invhed = new InvHedController(context).getDetailsforPrint(refno);
            final Customer debtor = new CustomerController(context).getSelectedCustomerByCode(invhed.getFINVHED_DEBCODE());
            outlet = new CustomerController(context).getSelectedCustomerByCode(invhed.getFINVHED_DEBCODE());
//            if(new CustomerController(context).getCustomerVatStatus(debtor.getCusCode()).equals("VAT")) {
            if(invhed.getFINVHED_VAT_CODE().equals("VAT")) {
                SalesRepname.setText("TAX INVOICE");
            }else{
                SalesRepname.setText("INVOICE");
            }


                ArrayList<InvDet> list = new InvDetController(context).getAllItemsforPrintOnly(refno);
                outlet = debtor;

                Debname.setText(debtor.getCusCode() + "-" + debtor.getCusName());
                Debaddress1.setText(debtor.getCusAdd1() + ", " );
                Debaddress2.setText(debtor.getCusAdd2());
                DebTele.setText(debtor.getCusMob());
                Debvat.setText("<VAT NO>");

                SalOrdDate.setText("Invoice No: " + refno);
                Remarks.setText(salRep.getRepCode() + "/ " + salRep.getNAME());
                OrderNo.setText("Date: " + invhed.getFINVHED_TXNDATE() + " " + currentTime());
                String routecode = new RouteDetController(context).getRouteCodeByDebCode(debtor.getCusCode());
                txtRoute.setText(""+new RouteController(context).getAreaCodeByRouteCode(routecode));

                int qty = 0 ;
                double dDisc = 0, dTotAmt = 0, dTax = 0;

                for (InvDet det : list) {
                    qty += Double.parseDouble(det.getFINVDET_QTY());
                    dDisc += Double.parseDouble(det.getFINVDET_DIS_AMT());
                    dTotAmt += Double.parseDouble(det.getFINVDET_AMT());
                    dTax += Double.parseDouble(det.getFINVDET_TAX_AMT());
                }

                lvItemDetails = (ListView) promptView.findViewById(R.id.vansaleList);
                lvItemDetails.setAdapter(new PrintVanSaleItemAdapter(context, list));
                /*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-Gross/Net values*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

                TotalPieceQty.setText(String.valueOf(qty));
                TotalNetValue.setText(String.format("%,.2f", (dTotAmt)));//-dDisc
                txtTotVal.setText(String.format("%,.2f", dTotAmt-dTax));
                txtfiQty.setText(String.format("%,.2f", dDisc));
            if(new CustomerController(context).getCustomerVatStatus(debtor.getCusCode()).equals("VAT")) {
                TotalDiscount.setText(String.format("%,.2f", dTax));
            }else{
                TotalDiscount.setText("0.00");
            }

                double netval = dTotAmt;//-dDisc
                String net_val_in_english =  ""+ EnglishNumberToWords.convert((int)netval);
             //   String net_val_in_english =   "";
                AmtInWord.setText("("+net_val_in_english+")");


                PRINTER_MAC_ID =  new SharedPref(context).getGlobalVal("printer_mac_address").toString();

                alertDialogBuilder.setCancelable(false).setPositiveButton("Print", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(title.split("-")[1].trim().equals("original")) {
                            PrintCurrentview(title.split("-")[1].trim());
                        }else{
                            PrintCurrentview("");
                        }

                    }
                });

                alertDialogBuilder.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(title.split("-")[1].trim().equals("original")) {
                            Intent intent = new Intent(context, DebtorDetailsActivity.class);
                            intent.putExtra("outlet", debtor);
                            context.startActivity(intent);
                            dialog.cancel();
                        }else{
                            dialog.cancel();
                        }
                    }
                });

                AlertDialog alertD = alertDialogBuilder.create();
                alertD.show();
                ListExpandHelper.getListViewSize(lvItemDetails);

                return 1;
        }
        catch (Exception ex)
        {
            return -1;
        }
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

    public void printItems(String print_loc) {
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
        String comname = "<b>" + controlList.get(0).getFCONTROL_COM_NAME() + "</b> ";

        String subTitleheadACom = printGapAdjustACom + Html.fromHtml(comname);
        String subTitleheadBCom = printGapAdjustBCom + controlList.get(0).getFCONTROL_COM_ADD1();
        String subTitleheadCCom = printGapAdjustCCom + controlList.get(0).getFCONTROL_COM_ADD2() + ", " + controlList.get(0).getFCONTROL_COM_ADD3() + ".";
        String subTitleheadDCom = printGapAdjustDCom + "Tel: " + controlList.get(0).getFCONTROL_COM_TEL1() + " / Fax: " + controlList.get(0).getFCONTROL_COM_FAX().trim();
        String subTitleheadECom = printGapAdjustECom + controlList.get(0).getFCONTROL_COM_WEB();
        String subTitleheadFCom = printGapAdjustFCom + controlList.get(0).getFCONTROL_COM_EMAIL();

        String subTitleheadGCom = printLineSeperatorNew;

//       String title_Print_ACom = "\r\n\u001B" + subTitleheadACom+"MENAKA";
        String title_Print_ACom = "\r\n" + subTitleheadACom;
        String title_Print_BCom = "\r\n" + subTitleheadBCom;
        String title_Print_CCom = "\r\n" + subTitleheadCCom;
        String title_Print_DCom = "\r\n" + subTitleheadDCom;
        String title_Print_ECom = "\r\n" + subTitleheadECom;
        String title_Print_FCom = "\r\n" + subTitleheadFCom;;
        String title_Print_GCom = "\r\n" + subTitleheadGCom;

        Heading_a = title_Print_ACom + title_Print_BCom + title_Print_CCom + title_Print_DCom + title_Print_ECom + title_Print_FCom + title_Print_GCom;

        String printGapAdjust = "                        ";
        InvHed invHed = new InvHedController(context).getDetailsforPrint(PRefno);
        FInvRHed invRHed = new SalesReturnController(context).getDetailsforPrint(PRefno);
        Customer debtor = new CustomerController(context).getSelectedCustomerByCode(invHed.getFINVHED_DEBCODE());
        String SalesRepNamestr = "";// +
//        if(new CustomerController(context).getCustomerVatStatus(debtor.getCusCode()).equals("VAT")) {
        if(invHed.getFINVHED_VAT_CODE().equals("VAT")) {
            SalesRepNamestr = "<TAX INVOICE> - (RE-PRINT)";
        }else{
            SalesRepNamestr = "<INVOICE> - (RE-PRINT)";
        }
      //  String SalesRepNamestr = "Sales Rep: " + salrep.getRepCode() + "/ " + salrep.getNAME().trim();// +

        int lengthDealE = SalesRepNamestr.length();
        int lengthDealEB = (LINECHAR - lengthDealE) / 2;
        String printGapAdjustE = printGapAdjust.substring(0, Math.min(lengthDealEB, printGapAdjust.length()));
        String subTitleheadF = printGapAdjustE + SalesRepNamestr;


        String subTitleheadH = printLineSeperatorNew;



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


        String subTitleheadI = printGapAdjustI + debtor.getCusCode() + "-" + debtor.getCusName();
        String subTitleheadJ = printGapAdjustJ + debtor.getCusAdd1() + "," + debtor.getCusAdd2();

        String subTitleheadK = printGapAdjustK + debtor.getCusAdd2();
        String subTitleheadL = printGapAdjustL + debtor.getCusMob();
        //String subTitleheadTIN = printGapCusTIn + "TIN No: " + debtor.getFDEBTOR_CUS_VATNO();

        String subTitleheadO = printLineSeperatorNew;

        String subTitleheadM = "" +PRefno ;//date
        int lengthDealM = subTitleheadM.length();
        int lengthDealMB = (LINECHAR - lengthDealM) / 2;
        String printGapAdjustM = printGapAdjust.substring(0, Math.min(lengthDealMB, printGapAdjust.length()));

        String subTitleheadN = "" + invHed.getFINVHED_TXNDATE() + " " + currentTime();//refno
        int lengthDealN = subTitleheadN.length();
        int lengthDealNB = (LINECHAR - lengthDealN) / 2;
        String printGapAdjustN = printGapAdjust.substring(0, Math.min(lengthDealNB, printGapAdjust.length()));

        String subTitleheadR;
        String subTitleheadArea;
        String repCode = new SalRepController(context).getCurrentRepCode();
        SalRep salRep = new SalRepController(context).getSaleRepDet(repCode);
//        if (invHed.getFINVHED_REMARKS().equals(""))
//            subTitleheadR = "Remarks : None";
//        else
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

        String title_Print_F = "\r\n" + subTitleheadF;
       // String title_Print_G = "\r\n" + subTitleheadG;
        String title_Print_H = "\r\n" + subTitleheadH;

        String title_Print_I = "\r\n" + subTitleheadI;
        String title_Print_J = "\r\n" + subTitleheadJ;
        String title_Print_K = "\r\n" + subTitleheadK;
        String title_Print_O = "\r\n" + subTitleheadO;

        String title_Print_M = "\r\n" + subTitleheadM;
        String title_Print_N = "\r\n" + subTitleheadN;
        String title_Print_R = "\r\n" + subTitleheadR;
        String title_Print_Area = "\r\n" + subTitleheadArea;
        // subTitleheadR;

        ArrayList<InvDet> itemList = new InvDetController(context).getAllItemsforPrintOnly(PRefno);
        ArrayList<FInvRDet> Rlist = new SalesReturnDetController(context).getAllInvRDetForPrint(PRefno);

        BigDecimal compDisc = BigDecimal.ZERO;// new
        // BigDecimal(itemList.get(0).getFINVDET_COMDISPER().toString());
        BigDecimal cusDisc = BigDecimal.ZERO;// new
        // BigDecimal(itemList.get(0).getFINVDET_CUSDISPER().toString());
        BigDecimal termDisc = BigDecimal.ZERO;// new
        // BigDecimal(itemList.get(0).getFINVDET_TERM_DISPER().toString());

        Heading_c = "";
        countCountInv = 0;

     //   if(new CustomerController(context).getCustomerVatStatus(debtor.getCusCode()).equals("VAT")) {
            if (subTitleheadK.toString().equalsIgnoreCase(" ")) {
                Heading_bmh = "\r" + title_Print_F + title_Print_H + title_Print_I + title_Print_J + title_Print_O + title_Print_M + title_Print_N + title_Print_R;
            } else {
                Heading_bmh = "\r" + title_Print_F + title_Print_H + title_Print_I + title_Print_J + title_Print_K + title_Print_O + title_Print_M + title_Print_N + title_Print_R + title_Print_Area;
            }
//        }else{
//            if (subTitleheadK.toString().equalsIgnoreCase(" ")) {
//                Heading_bmh = "\r"   + title_Print_I + title_Print_J + title_Print_O + title_Print_M + title_Print_N + title_Print_R;
//            } else {
//                Heading_bmh = "\r"   + title_Print_I + title_Print_J + title_Print_K + title_Print_O + title_Print_M + title_Print_N + title_Print_R + title_Print_Area;
//            }
//        }
        String title_cb = "\r\nARTICLE_NO  QTY   PRICE    DISC(%)   DISC.AMT";
        String title_cc = "\r\nITEM CODE                        LINE AMOUNT ";
        String title_cd = "\r\nITEM NAME                                    ";

        Heading_b = "\r\n" + printLineSeperatorNew +title_cd + title_cb + title_cc +"\r\n" + printLineSeperatorNew+"\n";
		/*-*-*-*-*-*-*-*-*-*-*-*-*-*Individual Item details*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        int totQty = 0 ;
        double totalamt = 0,totaldis = 0,totaltax = 0;
        ArrayList<StkIss> list = new ArrayList<StkIss>();

        //Order Item total
        for (InvDet det : itemList) {
            totQty += Double.parseDouble(det.getFINVDET_QTY());
            totalamt += Double.parseDouble(det.getFINVDET_AMT());
            totaldis += Double.parseDouble(det.getFINVDET_DIS_AMT());
            totaltax += Double.parseDouble(det.getFINVDET_TAX_AMT());
        }

        int nos = 1;
        String SpcItmCodeAndNOS, SpcPrice, SpcQty, SPcTotal, SpcNOS, SPACE6;
        String SpcVarntCde, SpcArticleNo, SpcDisper, SpcDisc, SPACE55, SPACE66;
        SPACE6 = "                                            ";

        //for (StkIss iss : list) {
        for (InvDet det : itemList) {

            String sItemcode = det.getFINVDET_ITEM_CODE();
            String sItemname = new ItemController(context).getItemNameByCode(sItemcode);
            String sQty = det.getFINVDET_QTY();
            String variantcode = det.getFINVDET_VARIANTCODE();
            String articleno = det.getFINVDET_ARTICLENO();
            String disper = det.getFINVDET_DIS_PER();
            // String sMRP = iss.getPRICE().substring(0, iss.getPRICE().length()
            // - 3);

            String sPrice = "", sTotal = "";

            sTotal = det.getFINVDET_AMT();
            sPrice = det.getFINVDET_SELL_PRICE();

            String sDiscount = det.getFINVDET_DIS_AMT();


            int itemCodeLength = sItemcode.length();

            if(itemCodeLength > 15)
            {
                sItemcode = sItemcode.substring(0,15);
            }

            //SPACE0 = String.format("%"+ (44 - (sItemname.length())) +(String.valueOf(nos).length() + 2)+ "s", " ");
            //SPACE1 = String.format("%" + (20 - (sItemcode.length() + (String.valueOf(nos).length() + 2))) + "s", " ");
            SpcItmCodeAndNOS = padString("",(15 - (sItemcode.length() + (String.valueOf(nos).length() + 2))));
            SpcVarntCde = padString("",(15 - (variantcode.length() + (String.valueOf(nos).length() + 2))));
            //SPACE2 = String.format("%" + (9 - (sPrice.length())) + "s", " ");
            SpcPrice = padString("",(9 - (sPrice.length())));
            SpcArticleNo = padString("",(9 - (articleno.length())));
            //SPACE3 = String.format("%" + (3 - (sQty.length())) + "s", " ");
            SpcQty = padString("",(3 - (sQty.length())));
            SpcDisper = padString("",(3 - (disper.length())));
            //SPACE4 = String.format("%" + (12 - (sTotal.length())) + "s", " ");
            SPcTotal = padString("",(12 - (sTotal.length())));
            SpcDisc = padString("",(12 - (sDiscount.length())));
            //SPACE5 = String.format("%" + (String.valueOf(nos).length() + 2) + "s", " ");
            SpcNOS = padString("",(String.valueOf(nos).length() + 2));


            String doubleLineItemName1 = "",doubleLineItemName2 = "";
            int itemNameLength = sItemname.length();
            if(itemNameLength > 40)
            {
                doubleLineItemName1 += sItemname.substring(0,40);
                doubleLineItemName2 += sItemname.substring(41,sItemname.length());

                Heading_c += nos + "."  +doubleLineItemName1.trim()
                                +"\r\n"+ SpcNOS+ doubleLineItemName2.trim()
                                +"\r\n"+ SpcNOS+ variantcode +SpcArticleNo+articleno +SpcQty+SpcPrice+ sPrice +SpcVarntCde + disper
                                +"\r\n" +SpcNOS+sItemcode +SpcItmCodeAndNOS+ sQty+SpcDisper+SpcDisc+sDiscount +SPcTotal+ sTotal+"\r\n\r\n";
            }
            else
            {
                doubleLineItemName1 += sItemname.substring(0,itemNameLength);
                Heading_c += nos + "."  +doubleLineItemName1.trim()
                        +"\r\n"+ SpcNOS+ variantcode +SpcArticleNo+articleno +SpcQty+SpcPrice+ sPrice +SpcVarntCde + disper
                        +"\r\n" +SpcNOS+sItemcode +SpcItmCodeAndNOS+ sQty+SpcDisper+SpcDisc+sDiscount +SPcTotal+ sTotal+"\r\n\r\n";
            }

            nos++;
        }

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/






        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/


        String space = "";
        String sNetTot = "", sGross = "", sRetGross = "0.00", sDiscount = "0.00", stax = "0.00";

        // if (invHed.getFINVHED_INV_TYPE().equals("NON")) {


        sGross = String.format(Locale.US, "%,.2f", totalamt-totaltax);


     //   int totReturnQty = 0;


            sNetTot = String.format(Locale.US, "%,.2f", (totalamt));//-totaldis
            sDiscount = String.format(Locale.US, "%,.2f", totaldis);
        if(new CustomerController(context).getCustomerVatStatus(debtor.getCusCode()).equals("VAT")) {
            stax = String.format(Locale.US, "%,.2f", totaltax);
        }





		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Discounts*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

      //  BigDecimal TotalAmt = new BigDecimal(Double.parseDouble(invHed.getFINVHED_TOTALAMT()) + Double.parseDouble(invHed.getFINVHED_TOTALDIS()));
     //   BigDecimal TotalAmt = new BigDecimal(Double.parseDouble(invHed.getFINVHED_TOTALAMT()));

        String sComDisc, sCusdisc = "0", sTermDisc = "0", totDiscount = "0.00";
        String fullDisc_String = "";



        String sDisc = String.format(Locale.US, "%,.2f", Double.parseDouble(sTermDisc.replace(",", "")) + Double.parseDouble(sCusdisc.replace(",", "")));

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Gross Net values-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/


        //Total Order Item Qty
        space = String.format("%" + (LINECHAR - ("Total Quantity".length() + String.valueOf(totQty).length())) + "s", " ");
        String buttomTitlea = "\r\n" + "Total Quantity" + space + String.valueOf(totQty);

        //Total Return Item Qty
        space = String.format("%" + (LINECHAR - ("Tax".length() + stax.length())) + "s", " ");
        String buttomTitleb = "Tax" + space + stax;

		/* print gross amount */
        space = String.format("%" + (LINECHAR - ("Gross Total".length() + sGross.length())) + "s", " ");
        String summaryTitle_c_Val = "Gross Total" + space + sGross;

        space = String.format("%" + (LINECHAR - ("Bulk Discount".length() + sDiscount.length())) + "s", " ");
        String summaryTitle_RetVal = "Bulk Discount" + space + sDiscount;

		/* print net total */
        space = String.format("%" + (LINECHAR - ("Net Total".length() + sNetTot.length())) + "s", " ");
        String summaryTitle_e_Val = "Net Total" + space + sNetTot;
        String summaryTitle_amtinword = "(" +EnglishNumberToWords.convert(sNetTot)+")" ;
        int amountword_index = summaryTitle_amtinword.indexOf("rupees");
        int amountwordLength = summaryTitle_amtinword.length();
        String amountword1="", amountword2="";
        if(amountwordLength > 40) {
            amountword1 += summaryTitle_amtinword.substring(0, amountword_index);
            amountword2 += summaryTitle_amtinword.substring(amountword_index, summaryTitle_amtinword.length());
        }else {

        }

		/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        String summaryBottom_cpoyline1 = "by Datamation Systems / www.datamation.lk";
        int lengthsummarybottm = summaryBottom_cpoyline1.length();
        int lengthsummarybottmline1 = (LINECHAR - lengthsummarybottm) / 2;
        String printGapbottmline1 = printGapAdjust.substring(0, Math.min(lengthsummarybottmline1, printGapAdjust.length()));
        String buttomTitlec = "\r\n" + summaryTitle_c_Val;
        String buttomTitled = "\r\n" + summaryTitle_RetVal;
        String buttomTitletax = "\r\n" + buttomTitleb;
        String buttomTitlee = "\r\n" + summaryTitle_e_Val;
        String buttomTitlef = "\r\n\n\n" + "------------------        ------------------" + "\r\n" + "     Customer               Sales Executive";

        String buttomTitlenote="";

        if(amountwordLength > 40)
        {
            buttomTitlenote = "\r\n" + amountword1+"\r\n" +amountword2.trim();
        }else {
            buttomTitlenote = "\r\n" + summaryTitle_amtinword.trim();
        }

        String buttomTitlefa = "\r\n\n\n" + "All Cheques should be drawn In favour of \n"+
                "H S Marketing Private Limited &\n" +
                " crossed Account Payee Only.";
        String buttomTitlecopyw = "\r\n" + printGapbottmline1 + summaryBottom_cpoyline1;
        if(new CustomerController(context).getCustomerVatStatus(debtor.getCusCode()).equals("VAT")) {
            buttomRaw = printLineSeperatorNew + buttomTitlea  + buttomTitlec +buttomTitled  +buttomTitletax+ "\r\n" + printLineSeperatorNew + buttomTitlee + "\r\n"+buttomTitlenote+ "\r\n" + printLineSeperatorNew + "\r\n" + buttomTitlef + buttomTitlefa + "\r\n" + printLineSeperatorNew + buttomTitlecopyw + "\r\n" + printLineSeperatorNew + "\n\n\n\n\n\n\n\n\n\n\n";
        }else{
            buttomRaw = printLineSeperatorNew + buttomTitlea  + buttomTitlec +buttomTitled  + "\r\n" + printLineSeperatorNew + buttomTitlee + "\r\n"+buttomTitlenote+ "\r\n" + printLineSeperatorNew + "\r\n" + buttomTitlef + buttomTitlefa + "\r\n" + printLineSeperatorNew + buttomTitlecopyw + "\r\n" + printLineSeperatorNew + "\n\n\n\n\n\n\n\n\n\n\n";

        }
       callPrintDevice(print_loc);
    }

	/*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*--*-*-*-*-*-*-*-*/

    public void PrintCurrentview(String printloc) {
         checkPrinter();
        if (PRINTER_MAC_ID.equals("404")) {
        Log.v("", "No MAC Address Found.Enter Printer MAC Address.");
        Toast.makeText(context, "No MAC Address Found.Enter Printer MAC Address.", Toast.LENGTH_LONG).show();
        }
       else {
         printItems(printloc);
        }
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

    private void callPrintDevice(String printloc) {
        BILL = " ";

        BILL = Heading_a + Heading_bmh + Heading_b + Heading_c + Heading_d  + buttomRaw;
        Log.d("BILL>>>", "BILL >>>:" + BILL);
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

            if(printloc.equals("original")) {
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                Intent intent = new Intent(context, DebtorDetailsActivity.class);
                intent.putExtra("outlet", outlet);
                context.startActivity(intent);
            }else{
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            }

//            Intent intent = new Intent(context, DebtorDetailsActivity.class);
//            intent.putExtra("outlet", outlet);
//            context.startActivity(intent);
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
            Log.d(">>>BILL",">>>"+BILL);
            e.printStackTrace();
            this.PrintDetailsDialogbox(context, "", PRefno);
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
