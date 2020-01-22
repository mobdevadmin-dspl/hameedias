package com.datamation.swdsfa.model;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class Order {

    private String ORDER_ID;
    private String ORDER_REFNO;
    private String ORDER_REFNO1;
    private String ORDER_TXNDATE;
    private String ORDER_MANUREF;
    private String ORDER_COSTCODE;
    private String ORDER_REMARKS;
    private String ORDER_TXNTYPE;
    private String ORDER_TOTALAMT;
    private String ORDER_CURCODE;
    private String ORDER_CURRATE;
    private String ORDER_DEBCODE;
    private String ORDER_QUOTERM;
    private String ORDER_REPCODE;
    private String ORDER_BTOTALDIS;
    private String ORDER_TOTALDIS;
    private String ORDER_PTOTALDIS;
    private String ORDER_BPTOTALDIS;
    private String ORDER_BTOTALTAX;
    private String ORDER_TOTALTAX;
    private String ORDER_BTOTALAMT;
    private String ORDER_TAXREG;
    private String ORDER_CONTACT;
    private String ORDER_CUSADD1;
    private String ORDER_CUSADD2;
    private String ORDER_CUSADD3;
    private String ORDER_CUSTELE;
    private String ORDER_ADDMACH;
    private String ORDER_TXNDELDATE;
    private String ORDER_IS_SYNCED;
    private String ORDER_IS_ACTIVE;
    private String ORDER_LONGITUDE;
    private String ORDER_LATITUDE;
    private String ORDER_START_TIMESO;
    private String ORDER_END_TIMESO;
    private String ORDER_ADDRESS;
    private String ORDER_TOTQTY;
    private String ORDER_TOTFREE;
    private String ORDER_TOURCODE;
    private String ORDER_LOCCODE;
    private String ORDER_AREACODE;
    private String ORDER_ROUTECODE;
    private String ORDER_ADDDATE;
    private String ORDER_ADDUSER;
    private String ORDER_TOTFREEQTY;
    private String ORDER_SETTING_CODE;
    private String ORDER_DELIVERY_DATE;
    private String ORDER_PAYTYPE;
    private String ORDER_DEALCODE;
    private String ORDER_TOTALMKRAMT;
    private String ORDER_FEEDBACK;

    public String getORDER_FEEDBACK() {
        return ORDER_FEEDBACK;
    }

    public void setORDER_FEEDBACK(String ORDER_FEEDBACK) {
        this.ORDER_FEEDBACK = ORDER_FEEDBACK;
    }

    public String getORDER_TOTALMKRAMT() {
        return ORDER_TOTALMKRAMT;
    }

    public void setORDER_TOTALMKRAMT(String ORDER_TOTALMKRAMT) {
        this.ORDER_TOTALMKRAMT = ORDER_TOTALMKRAMT;
    }

    public String getORDER_DEALCODE() {
        return ORDER_DEALCODE;
    }

    public void setORDER_DEALCODE(String ORDER_DEALCODE) {
        this.ORDER_DEALCODE = ORDER_DEALCODE;
    }

    public String getORDER_DELIVERY_DATE() {
        return ORDER_DELIVERY_DATE;
    }

    public void setORDER_DELIVERY_DATE(String ORDER_DELIVERY_DATE) {
        this.ORDER_DELIVERY_DATE = ORDER_DELIVERY_DATE;
    }

    private String ConsoleDB;

    public String getORDER_PAYTYPE() {
        return ORDER_PAYTYPE;
    }

    public void setORDER_PAYTYPE(String ORDER_PAYTYPE) {
        this.ORDER_PAYTYPE = ORDER_PAYTYPE;
    }

    private String DistDB;
    private String NextNumVal;

    private ArrayList<OrderDetail> ordDet;
    private ArrayList<TaxDT> taxDTs;
    private ArrayList<TaxRG> taxRGs;
    private ArrayList<OrderDisc> ordDisc;
    private ArrayList<OrdFreeIssue> freeIssues;

    public Order() {
    }

    public String getNextNumVal() {
        return NextNumVal;
    }

    public void setNextNumVal(String nextNumVal) {
        NextNumVal = nextNumVal;
    }

    public ArrayList<TaxDT> getTaxDTs() {
        return taxDTs;
    }

    public void setTaxDTs(ArrayList<TaxDT> taxDTs) {
        this.taxDTs = taxDTs;
    }

    public ArrayList<TaxRG> getTaxRGs() {
        return taxRGs;
    }

    public void setTaxRGs(ArrayList<TaxRG> taxRGs) {
        this.taxRGs = taxRGs;
    }

    public ArrayList<OrderDisc> getOrdDisc() {
        return ordDisc;
    }

    public void setOrdDisc(ArrayList<OrderDisc> ordDisc) {
        this.ordDisc = ordDisc;
    }

    public ArrayList<OrdFreeIssue> getFreeIssues() {
        return freeIssues;
    }

    public void setFreeIssues(ArrayList<OrdFreeIssue> freeIssues) {
        this.freeIssues = freeIssues;
    }

    public String getConsoleDB() {
        return ConsoleDB;
    }

    public void setConsoleDB(String consoleDB) {
        ConsoleDB = consoleDB;
    }

    public String getDistDB() {
        return DistDB;
    }

    public void setDistDB(String distDB) {
        DistDB = distDB;
    }

    public String getORDER_TOTFREEQTY() {
        return ORDER_TOTFREEQTY;
    }

    public void setORDER_TOTFREEQTY(String ORDER_TOTFREEQTY) {
        this.ORDER_TOTFREEQTY = ORDER_TOTFREEQTY;
    }

    public String getORDER_SETTING_CODE() {
        return ORDER_SETTING_CODE;
    }

    public void setORDER_SETTING_CODE(String ORDER_SETTING_CODE) {
        this.ORDER_SETTING_CODE = ORDER_SETTING_CODE;
    }

    public String getORDER_ADDUSER() {
        return ORDER_ADDUSER;
    }

    public void setORDER_ADDUSER(String ORDER_ADDUSER) {
        this.ORDER_ADDUSER = ORDER_ADDUSER;
    }


    public String getORDER_ID() {
        return ORDER_ID;
    }

    public String getORDER_ADDDATE() {
        return ORDER_ADDDATE;
    }

    public void setORDER_ADDDATE(String ORDER_ADDDATE) {
        this.ORDER_ADDDATE = ORDER_ADDDATE;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }

    public String getORDER_REFNO() {
        return ORDER_REFNO;
    }

    public void setORDER_REFNO(String ORDER_REFNO) {
        this.ORDER_REFNO = ORDER_REFNO;
    }

    public String getORDER_REFNO1() {
        return ORDER_REFNO1;
    }

    public void setORDER_REFNO1(String ORDER_REFNO1) {
        this.ORDER_REFNO1 = ORDER_REFNO1;
    }

    public String getORDER_TXNDATE() {
        return ORDER_TXNDATE;
    }

    public void setORDER_TXNDATE(String ORDER_TXNDATE) {
        this.ORDER_TXNDATE = ORDER_TXNDATE;
    }

    public String getORDER_MANUREF() {
        return ORDER_MANUREF;
    }

    public void setORDER_MANUREF(String ORDER_MANUREF) {
        this.ORDER_MANUREF = ORDER_MANUREF;
    }

    public String getORDER_COSTCODE() {
        return ORDER_COSTCODE;
    }

    public void setORDER_COSTCODE(String ORDER_COSTCODE) {
        this.ORDER_COSTCODE = ORDER_COSTCODE;
    }

    public String getORDER_REMARKS() {
        return ORDER_REMARKS;
    }

    public void setORDER_REMARKS(String ORDER_REMARKS) {
        this.ORDER_REMARKS = ORDER_REMARKS;
    }

    public String getORDER_TXNTYPE() {
        return ORDER_TXNTYPE;
    }

    public void setORDER_TXNTYPE(String ORDER_TXNTYPE) {
        this.ORDER_TXNTYPE = ORDER_TXNTYPE;
    }

    public String getORDER_TOTALAMT() {
        return ORDER_TOTALAMT;
    }

    public void setORDER_TOTALAMT(String ORDER_TOTALAMT) {
        this.ORDER_TOTALAMT = ORDER_TOTALAMT;
    }

    public String getORDER_CURCODE() {
        return ORDER_CURCODE;
    }

    public void setORDER_CURCODE(String ORDER_CURCODE) {
        this.ORDER_CURCODE = ORDER_CURCODE;
    }

    public String getORDER_CURRATE() {
        return ORDER_CURRATE;
    }

    public void setORDER_CURRATE(String ORDER_CURRATE) {
        this.ORDER_CURRATE = ORDER_CURRATE;
    }

    public String getORDER_DEBCODE() {
        return ORDER_DEBCODE;
    }

    public void setORDER_DEBCODE(String ORDER_DEBCODE) {
        this.ORDER_DEBCODE = ORDER_DEBCODE;
    }

    public String getORDER_QUOTERM() {
        return ORDER_QUOTERM;
    }

    public void setORDER_QUOTERM(String ORDER_QUOTERM) {
        this.ORDER_QUOTERM = ORDER_QUOTERM;
    }

    public String getORDER_REPCODE() {
        return ORDER_REPCODE;
    }

    public void setORDER_REPCODE(String ORDER_REPCODE) {
        this.ORDER_REPCODE = ORDER_REPCODE;
    }

    public String getORDER_BTOTALDIS() {
        return ORDER_BTOTALDIS;
    }

    public void setORDER_BTOTALDIS(String ORDER_BTOTALDIS) {
        this.ORDER_BTOTALDIS = ORDER_BTOTALDIS;
    }

    public String getORDER_TOTALDIS() {
        return ORDER_TOTALDIS;
    }

    public void setORDER_TOTALDIS(String ORDER_TOTALDIS) {
        this.ORDER_TOTALDIS = ORDER_TOTALDIS;
    }

    public String getORDER_PTOTALDIS() {
        return ORDER_PTOTALDIS;
    }

    public void setORDER_PTOTALDIS(String ORDER_PTOTALDIS) {
        this.ORDER_PTOTALDIS = ORDER_PTOTALDIS;
    }

    public String getORDER_BPTOTALDIS() {
        return ORDER_BPTOTALDIS;
    }

    public void setORDER_BPTOTALDIS(String ORDER_BPTOTALDIS) {
        this.ORDER_BPTOTALDIS = ORDER_BPTOTALDIS;
    }

    public String getORDER_BTOTALTAX() {
        return ORDER_BTOTALTAX;
    }

    public void setORDER_BTOTALTAX(String ORDER_BTOTALTAX) {
        this.ORDER_BTOTALTAX = ORDER_BTOTALTAX;
    }

    public String getORDER_TOTALTAX() {
        return ORDER_TOTALTAX;
    }

    public void setORDER_TOTALTAX(String ORDER_TOTALTAX) {
        this.ORDER_TOTALTAX = ORDER_TOTALTAX;
    }

    public String getORDER_BTOTALAMT() {
        return ORDER_BTOTALAMT;
    }

    public void setORDER_BTOTALAMT(String ORDER_BTOTALAMT) {
        this.ORDER_BTOTALAMT = ORDER_BTOTALAMT;
    }

    public String getORDER_TAXREG() {
        return ORDER_TAXREG;
    }

    public void setORDER_TAXREG(String ORDER_TAXREG) {
        this.ORDER_TAXREG = ORDER_TAXREG;
    }

    public String getORDER_CONTACT() {
        return ORDER_CONTACT;
    }

    public void setORDER_CONTACT(String ORDER_CONTACT) {
        this.ORDER_CONTACT = ORDER_CONTACT;
    }

    public String getORDER_CUSADD1() {
        return ORDER_CUSADD1;
    }

    public void setORDER_CUSADD1(String ORDER_CUSADD1) {
        this.ORDER_CUSADD1 = ORDER_CUSADD1;
    }

    public String getORDER_CUSADD2() {
        return ORDER_CUSADD2;
    }

    public void setORDER_CUSADD2(String ORDER_CUSADD2) {
        this.ORDER_CUSADD2 = ORDER_CUSADD2;
    }

    public String getORDER_CUSADD3() {
        return ORDER_CUSADD3;
    }

    public void setORDER_CUSADD3(String ORDER_CUSADD3) {
        this.ORDER_CUSADD3 = ORDER_CUSADD3;
    }

    public String getORDER_CUSTELE() {
        return ORDER_CUSTELE;
    }

    public void setORDER_CUSTELE(String ORDER_CUSTELE) {
        this.ORDER_CUSTELE = ORDER_CUSTELE;
    }

    public String getORDER_ADDMACH() {
        return ORDER_ADDMACH;
    }

    public void setORDER_ADDMACH(String ORDER_ADDMACH) {
        this.ORDER_ADDMACH = ORDER_ADDMACH;
    }

    public String getORDER_TXNDELDATE() {
        return ORDER_TXNDELDATE;
    }

    public void setORDER_TXNDELDATE(String ORDER_TXNDELDATE) {
        this.ORDER_TXNDELDATE = ORDER_TXNDELDATE;
    }

    public String getORDER_IS_SYNCED() {
        return ORDER_IS_SYNCED;
    }

    public void setORDER_IS_SYNCED(String ORDER_IS_SYNCED) {
        this.ORDER_IS_SYNCED = ORDER_IS_SYNCED;
    }

    public String getORDER_IS_ACTIVE() {
        return ORDER_IS_ACTIVE;
    }

    public void setORDER_IS_ACTIVE(String ORDER_IS_ACTIVE) {
        this.ORDER_IS_ACTIVE = ORDER_IS_ACTIVE;
    }

    public String getORDER_LONGITUDE() {
        return ORDER_LONGITUDE;
    }

    public void setORDER_LONGITUDE(String ORDER_LONGITUDE) {
        this.ORDER_LONGITUDE = ORDER_LONGITUDE;
    }

    public String getORDER_LATITUDE() {
        return ORDER_LATITUDE;
    }

    public void setORDER_LATITUDE(String ORDER_LATITUDE) {
        this.ORDER_LATITUDE = ORDER_LATITUDE;
    }

    public String getORDER_START_TIMESO() {
        return ORDER_START_TIMESO;
    }

    public void setORDER_START_TIMESO(String ORDER_START_TIMESO) {
        this.ORDER_START_TIMESO = ORDER_START_TIMESO;
    }

    public String getORDER_END_TIMESO() {
        return ORDER_END_TIMESO;
    }

    public void setORDER_END_TIMESO(String ORDER_END_TIMESO) {
        this.ORDER_END_TIMESO = ORDER_END_TIMESO;
    }

    public String getORDER_ADDRESS() {
        return ORDER_ADDRESS;
    }

    public void setORDER_ADDRESS(String ORDER_ADDRESS) {
        this.ORDER_ADDRESS = ORDER_ADDRESS;
    }

    public String getORDER_TOTQTY() {
        return ORDER_TOTQTY;
    }

    public void setORDER_TOTQTY(String ORDER_TOTQTY) {
        this.ORDER_TOTQTY = ORDER_TOTQTY;
    }

    public String getORDER_TOTFREE() {
        return ORDER_TOTFREE;
    }

    public void setORDER_TOTFREE(String ORDER_TOTFREE) {
        this.ORDER_TOTFREE = ORDER_TOTFREE;
    }

    public String getORDER_TOURCODE() {
        return ORDER_TOURCODE;
    }

    public void setORDER_TOURCODE(String ORDER_TOURCODE) {
        this.ORDER_TOURCODE = ORDER_TOURCODE;
    }

    public String getORDER_LOCCODE() {
        return ORDER_LOCCODE;
    }

    public void setORDER_LOCCODE(String ORDER_LOCCODE) {
        this.ORDER_LOCCODE = ORDER_LOCCODE;
    }

    public String getORDER_AREACODE() {
        return ORDER_AREACODE;
    }

    public void setORDER_AREACODE(String ORDER_AREACODE) {
        this.ORDER_AREACODE = ORDER_AREACODE;
    }

    public String getORDER_ROUTECODE() {
        return ORDER_ROUTECODE;
    }

    public void setORDER_ROUTECODE(String ORDER_ROUTECODE) {
        this.ORDER_ROUTECODE = ORDER_ROUTECODE;
    }

    public ArrayList<OrderDetail> getOrdDet() {
        return ordDet;
    }

    public void setOrdDet(ArrayList<OrderDetail> ordDet) {
        this.ordDet = ordDet;
    }

    public JSONObject getOrderAsJSON(Context context) throws JSONException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        HashMap<String, Object> finalJSONParams = new HashMap<>();

//        HashMap<String, Object> invoiceParams = new HashMap<>();
//        invoiceParams.put("orderId", String.valueOf(orderId));
//        invoiceParams.put("invtype", 0);
//        invoiceParams.put("outletid", outletId);
//        invoiceParams.put("routeid", routeId);
//        invoiceParams.put("lat", latitude);
//        invoiceParams.put("lon", longitude);
//        invoiceParams.put("bat", batteryLevel);
//        invoiceParams.put("discount", discount);
//        invoiceParams.put("grossamount", grossAmount);
//        invoiceParams.put("returnamount", returnAmount);
//        invoiceParams.put("eligibleamount", eligibleAmount);
//        invoiceParams.put("marginamount", marginAmount);
//        invoiceParams.put("netamount", netAmount);
//        invoiceParams.put("lineamount", lineAmount);
//        invoiceParams.put("discountpercentage", discountPercentage);
//        invoiceParams.put("nextvisitdate", nextVisit);
//        invoiceParams.put("locationType", locationType);
//        invoiceParams.put("locationAccuracy", locationAccuracy);
//        invoiceParams.put("hasFree", hasFree());
//        invoiceParams.put("hasSalesReturn", false);
//        invoiceParams.put("hasMarketReturn", returnDetails != null && returnDetails.size() > 0);
//        invoiceParams.put("paymentMethodCode", paymentMethodCode);
//        invoiceParams.put("currentIP",mobileIP);
//        try {
//            invoiceParams.put("app_version",context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        // Add the sales order flag to the outgoing JSON
//        invoiceParams.put("isSalesOrder", isSalesOrder);
//
//        Date date = new Date(orderTime);
//
//        String combinedDate = sdf.format(date);
//
//        String[] broken = combinedDate.split(" ");
//
//        invoiceParams.put("invDate", broken[0]);
//        invoiceParams.put("invtime", broken[1]);
//
//        JSONObject invoiceJSON = new JSONObject(invoiceParams);
//
//        finalJSONParams.put("Invoice", invoiceJSON);
//
//        finalJSONParams.put("posm", new JSONArray());
//
//        JSONArray itemsArray = new JSONArray();
//
//        if (orderDetails != null) {
////            for (int i = 0; i < orderDetails.size(); i++) {
////                JSONObject tmpItemJSON = orderDetails.get(i).getOrderDetailAsJSON(this);
////                if (tmpItemJSON != null) {
////                    itemsArray.put(tmpItemJSON);
////                }
////            }
//        }
//        finalJSONParams.put("invitems", itemsArray);
//        JSONArray returnArray = new JSONArray();
//        if (returnDetails != null) {
//            for (int i = 0; i < returnDetails.size(); i++) {
//                JSONObject tmpItemJSON = returnDetails.get(i).getReturnDetailAsJSON();
//                if (tmpItemJSON != null) {
//                    returnArray.put(tmpItemJSON);
//                }
//            }
//        }
//
//        finalJSONParams.put("returnitems", returnArray);
//
//        JSONArray freeArray = new JSONArray();
//        if (orderDetails != null) {
//            for (int i = 0; i < orderDetails.size(); i++) {
////                Log.d("<>","fuck");
////                ItemPromotion freeIssueDetail = orderDetails.get(i).getItemPromotion();
////                if (freeIssueDetail != null) {
//////                    Log.d("<>","fuck fuck");
////                    freeArray.put(freeIssueDetail.toJSON(freeIssueDetail));
////                }
//
//            }
//        }
//        finalJSONParams.put("item_promotions", freeArray);
//
//        if (payments!=null&&payments.size()>0) {
//            HashMap<String,Object> pymnts = new HashMap<>();
//            JSONArray cashArray = new JSONArray();
//            JSONArray cheqArray = new JSONArray();
//
//            for(Payment payment:payments){
//                if(payment.isCash()){
//                    cashArray.put(payment.getPaymentAsJSON());
//                }
//                else{
//                    cheqArray.put(payment.getPaymentAsJSON());
//                }
//            }
//            pymnts.put("cash",cashArray);
//            pymnts.put("cheq",cheqArray);
//
//            finalJSONParams.put("Payment", new JSONObject(pymnts));
//        }
//        JSONObject finalObject = new JSONObject(finalJSONParams);
//
//        Log.wtf(LOG_TAG, "ORDER JSON\n" + finalObject.toString());

        return new JSONObject(finalJSONParams);
    }






}

