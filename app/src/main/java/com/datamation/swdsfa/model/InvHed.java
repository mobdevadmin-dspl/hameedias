package com.datamation.swdsfa.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class InvHed implements Serializable {


    private String FINVHED_ID;
    private String FINVHED_REFNO;
    private String FINVHED_REFNO1;
    private String FINVHED_TXNDATE;
    private String FINVHED_MANUREF;
    private String FINVHED_COSTCODE;
    private String FINVHED_CURCODE;
    private String FINVHED_CURRATE;
    private String FINVHED_DEBCODE;
    private String FINVHED_REMARKS;
    private String FINVHED_TXNTYPE;
    private String FINVHED_LOCCODE;
    private String FINVHED_REPCODE;
    private String FINVHED_CONTACT;
    private String FINVHED_CUSADD1;
    private String FINVHED_CUSADD2;
    private String FINVHED_CUSADD3;
    private String FINVHED_CUSTELE;
    private String FINVHED_TOTALDIS;
    private String FINVHED_BTOTALDIS;
    private String FINVHED_PTOTALDIS;
    private String FINVHED_BPTOTALDIS;
    private String FINVHED_TOTALTAX;
    private String FINVHED_BTOTALTAX;
    private String FINVHED_TOTALAMT;
    private String FINVHED_BTOTALAMT;
    private String FINVHED_TAXREG;
    private String FINVHED_TOTCASH;
    private String FINVHED_TOTDDEP;
    private String FINVHED_TOTCARD;
    private String FINVHED_TOTCHQ;
    private String FINVHED_TOTALLO;
    private String FINVHED_TOTBAL;
    private String FINVHED_TENDERAMT;
    private String FINVHED_TENDERBAL;
    private String FINVHED_ADDUSER;
    private String FINVHED_ADDDATE;
    private String FINVHED_ADDMACH;
    private String FINVHED_START_TIME_SO;
    private String FINVHED_END_TIME_SO;
    private String FINVHED_LONGITUDE;
    private String FINVHED_LATITUDE;
    private String FINVHED_ADDRESS;
    private String FINVHED_IS_SYNCED;
    private String FINVHED_IS_ACTIVE;
    private String FINVHED_TOTQTY;
    private String FINVHED_TOTFREEQTY;
    private String FINVHED_TOURCODE;
    private String FINVHED_ROUTECODE;
    private String FINVHED_AREACODE;
    private String FINVHED_PAYTYPE;
    private String FINVHED_SETTING_CODE;

    private String ConsoleDB;
    private String DistDB;

    private String NextNumVal;

    private ArrayList<InvDet> InvDets;
    private ArrayList<InvTaxDt> invTaxDTs;
    private ArrayList<InvTaxRg> invTaxRGs;
    private ArrayList<OrderDisc> orderDiscs;
    private ArrayList<OrdFreeIssue> freeIssues;
    private ArrayList<StkIss> stkIsses;
    private ArrayList<DispHed> dispHeds;
    private ArrayList<DispDet> dispDets;
    private ArrayList<DispIss> dispIsses;

    public ArrayList<InvDet> getInvDets() {
        return InvDets;
    }

    public void setInvDets(ArrayList<InvDet> invDets) {
        InvDets = invDets;
    }

    public ArrayList<InvTaxDt> getInvTaxDTs() {
        return invTaxDTs;
    }

    public void setInvTaxDTs(ArrayList<InvTaxDt> invTaxDTs) {
        this.invTaxDTs = invTaxDTs;
    }

    public ArrayList<InvTaxRg> getInvTaxRGs() {
        return invTaxRGs;
    }

    public void setInvTaxRGs(ArrayList<InvTaxRg> invTaxRGs) {
        this.invTaxRGs = invTaxRGs;
    }

    public ArrayList<OrderDisc> getOrderDiscs() {
        return orderDiscs;
    }

    public void setOrderDiscs(ArrayList<OrderDisc> orderDiscs) {
        this.orderDiscs = orderDiscs;
    }

    public ArrayList<OrdFreeIssue> getFreeIssues() {
        return freeIssues;
    }

    public void setFreeIssues(ArrayList<OrdFreeIssue> freeIssues) {
        this.freeIssues = freeIssues;
    }

    public ArrayList<StkIss> getStkIsses() {
        return stkIsses;
    }

    public void setStkIsses(ArrayList<StkIss> stkIsses) {
        this.stkIsses = stkIsses;
    }

    public ArrayList<DispHed> getDispHeds() {
        return dispHeds;
    }

    public void setDispHeds(ArrayList<DispHed> dispHeds) {
        this.dispHeds = dispHeds;
    }

    public ArrayList<DispDet> getDispDets() {
        return dispDets;
    }

    public void setDispDets(ArrayList<DispDet> dispDets) {
        this.dispDets = dispDets;
    }

    public ArrayList<DispIss> getDispIsses() {
        return dispIsses;
    }

    public void setDispIsses(ArrayList<DispIss> dispIsses) {
        this.dispIsses = dispIsses;
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

    public String getNextNumVal() {
        return NextNumVal;
    }

    public void setNextNumVal(String nextNumVal) {
        NextNumVal = nextNumVal;
    }

    private ArrayList<InvDet>invDetArrayList;


    public ArrayList<InvDet> getInvDetArrayList() {
        return invDetArrayList;
    }

    public void setInvDetArrayList(ArrayList<InvDet> invDetArrayList) {
        this.invDetArrayList = invDetArrayList;
    }

    public String getFINVHED_PAYTYPE() {
        return FINVHED_PAYTYPE;
    }

    public void setFINVHED_PAYTYPE(String FINVHED_PAYTYPE) {
        this.FINVHED_PAYTYPE = FINVHED_PAYTYPE;
    }

    public String getFINVHED_SETTING_CODE() {
        return FINVHED_SETTING_CODE;
    }

    public void setFINVHED_SETTING_CODE(String FINVHED_SETTING_CODE) {
        this.FINVHED_SETTING_CODE = FINVHED_SETTING_CODE;
    }

    public String getFINVHED_ID() {
        return FINVHED_ID;
    }

    public void setFINVHED_ID(String fINVHED_ID) {
        FINVHED_ID = fINVHED_ID;
    }

    public String getFINVHED_REFNO() {
        return FINVHED_REFNO;
    }

    public void setFINVHED_REFNO(String fINVHED_REFNO) {
        FINVHED_REFNO = fINVHED_REFNO;
    }

    public String getFINVHED_REFNO1() {
        return FINVHED_REFNO1;
    }

    public void setFINVHED_REFNO1(String fINVHED_REFNO1) {
        FINVHED_REFNO1 = fINVHED_REFNO1;
    }

    public String getFINVHED_TXNDATE() {
        return FINVHED_TXNDATE;
    }

    public void setFINVHED_TXNDATE(String fINVHED_TXNDATE) {
        FINVHED_TXNDATE = fINVHED_TXNDATE;
    }

    public String getFINVHED_MANUREF() {
        return FINVHED_MANUREF;
    }

    public void setFINVHED_MANUREF(String fINVHED_MANUREF) {
        FINVHED_MANUREF = fINVHED_MANUREF;
    }

    public String getFINVHED_COSTCODE() {
        return FINVHED_COSTCODE;
    }

    public void setFINVHED_COSTCODE(String fINVHED_COSTCODE) {
        FINVHED_COSTCODE = fINVHED_COSTCODE;
    }

    public String getFINVHED_CURCODE() {
        return FINVHED_CURCODE;
    }

    public void setFINVHED_CURCODE(String fINVHED_CURCODE) {
        FINVHED_CURCODE = fINVHED_CURCODE;
    }

    public String getFINVHED_CURRATE() {
        return FINVHED_CURRATE;
    }

    public void setFINVHED_CURRATE(String fINVHED_CURRATE) {
        FINVHED_CURRATE = fINVHED_CURRATE;
    }

    public String getFINVHED_DEBCODE() {
        return FINVHED_DEBCODE;
    }

    public void setFINVHED_DEBCODE(String fINVHED_DEBCODE) {
        FINVHED_DEBCODE = fINVHED_DEBCODE;
    }

    public String getFINVHED_REMARKS() {
        return FINVHED_REMARKS;
    }

    public void setFINVHED_REMARKS(String fINVHED_REMARKS) {
        FINVHED_REMARKS = fINVHED_REMARKS;
    }

    public String getFINVHED_TXNTYPE() {
        return FINVHED_TXNTYPE;
    }

    public void setFINVHED_TXNTYPE(String fINVHED_TXNTYPE) {
        FINVHED_TXNTYPE = fINVHED_TXNTYPE;
    }

    public String getFINVHED_LOCCODE() {
        return FINVHED_LOCCODE;
    }

    public void setFINVHED_LOCCODE(String fINVHED_LOCCODE) {
        FINVHED_LOCCODE = fINVHED_LOCCODE;
    }

    public String getFINVHED_REPCODE() {
        return FINVHED_REPCODE;
    }

    public void setFINVHED_REPCODE(String fINVHED_REPCODE) {
        FINVHED_REPCODE = fINVHED_REPCODE;
    }

    public String getFINVHED_CONTACT() {
        return FINVHED_CONTACT;
    }

    public void setFINVHED_CONTACT(String fINVHED_CONTACT) {
        FINVHED_CONTACT = fINVHED_CONTACT;
    }

    public String getFINVHED_CUSADD1() {
        return FINVHED_CUSADD1;
    }

    public void setFINVHED_CUSADD1(String fINVHED_CUSADD1) {
        FINVHED_CUSADD1 = fINVHED_CUSADD1;
    }

    public String getFINVHED_CUSADD2() {
        return FINVHED_CUSADD2;
    }

    public void setFINVHED_CUSADD2(String fINVHED_CUSADD2) {
        FINVHED_CUSADD2 = fINVHED_CUSADD2;
    }

    public String getFINVHED_CUSADD3() {
        return FINVHED_CUSADD3;
    }

    public void setFINVHED_CUSADD3(String fINVHED_CUSADD3) {
        FINVHED_CUSADD3 = fINVHED_CUSADD3;
    }

    public String getFINVHED_CUSTELE() {
        return FINVHED_CUSTELE;
    }

    public void setFINVHED_CUSTELE(String fINVHED_CUSTELE) {
        FINVHED_CUSTELE = fINVHED_CUSTELE;
    }

    public String getFINVHED_TOTALDIS() {
        return FINVHED_TOTALDIS;
    }

    public void setFINVHED_TOTALDIS(String fINVHED_TOTALDIS) {
        FINVHED_TOTALDIS = fINVHED_TOTALDIS;
    }

    public String getFINVHED_BTOTALDIS() {
        return FINVHED_BTOTALDIS;
    }

    public void setFINVHED_BTOTALDIS(String fINVHED_BTOTALDIS) {
        FINVHED_BTOTALDIS = fINVHED_BTOTALDIS;
    }

    public String getFINVHED_PTOTALDIS() {
        return FINVHED_PTOTALDIS;
    }

    public void setFINVHED_PTOTALDIS(String fINVHED_PTOTALDIS) {
        FINVHED_PTOTALDIS = fINVHED_PTOTALDIS;
    }

    public String getFINVHED_BPTOTALDIS() {
        return FINVHED_BPTOTALDIS;
    }

    public void setFINVHED_BPTOTALDIS(String fINVHED_BPTOTALDIS) {
        FINVHED_BPTOTALDIS = fINVHED_BPTOTALDIS;
    }

    public String getFINVHED_TOTALTAX() {
        return FINVHED_TOTALTAX;
    }

    public void setFINVHED_TOTALTAX(String fINVHED_TOTALTAX) {
        FINVHED_TOTALTAX = fINVHED_TOTALTAX;
    }

    public String getFINVHED_BTOTALTAX() {
        return FINVHED_BTOTALTAX;
    }

    public void setFINVHED_BTOTALTAX(String fINVHED_BTOTALTAX) {
        FINVHED_BTOTALTAX = fINVHED_BTOTALTAX;
    }

    public String getFINVHED_TOTALAMT() {
        return FINVHED_TOTALAMT;
    }

    public void setFINVHED_TOTALAMT(String fINVHED_TOTALAMT) {
        FINVHED_TOTALAMT = fINVHED_TOTALAMT;
    }

    public String getFINVHED_BTOTALAMT() {
        return FINVHED_BTOTALAMT;
    }

    public void setFINVHED_BTOTALAMT(String fINVHED_BTOTALAMT) {
        FINVHED_BTOTALAMT = fINVHED_BTOTALAMT;
    }

    public String getFINVHED_TAXREG() {
        return FINVHED_TAXREG;
    }

    public void setFINVHED_TAXREG(String fINVHED_TAXREG) {
        FINVHED_TAXREG = fINVHED_TAXREG;
    }

    public String getFINVHED_TOTCASH() {
        return FINVHED_TOTCASH;
    }

    public void setFINVHED_TOTCASH(String fINVHED_TOTCASH) {
        FINVHED_TOTCASH = fINVHED_TOTCASH;
    }

    public String getFINVHED_TOTDDEP() {
        return FINVHED_TOTDDEP;
    }

    public void setFINVHED_TOTDDEP(String fINVHED_TOTDDEP) {
        FINVHED_TOTDDEP = fINVHED_TOTDDEP;
    }

    public String getFINVHED_TOTCARD() {
        return FINVHED_TOTCARD;
    }

    public void setFINVHED_TOTCARD(String fINVHED_TOTCARD) {
        FINVHED_TOTCARD = fINVHED_TOTCARD;
    }

    public String getFINVHED_TOTCHQ() {
        return FINVHED_TOTCHQ;
    }

    public void setFINVHED_TOTCHQ(String fINVHED_TOTCHQ) {
        FINVHED_TOTCHQ = fINVHED_TOTCHQ;
    }

    public String getFINVHED_TOTALLO() {
        return FINVHED_TOTALLO;
    }

    public void setFINVHED_TOTALLO(String fINVHED_TOTALLO) {
        FINVHED_TOTALLO = fINVHED_TOTALLO;
    }

    public String getFINVHED_TOTBAL() {
        return FINVHED_TOTBAL;
    }

    public void setFINVHED_TOTBAL(String fINVHED_TOTBAL) {
        FINVHED_TOTBAL = fINVHED_TOTBAL;
    }

    public String getFINVHED_TENDERAMT() {
        return FINVHED_TENDERAMT;
    }

    public void setFINVHED_TENDERAMT(String fINVHED_TENDERAMT) {
        FINVHED_TENDERAMT = fINVHED_TENDERAMT;
    }

    public String getFINVHED_TENDERBAL() {
        return FINVHED_TENDERBAL;
    }

    public void setFINVHED_TENDERBAL(String fINVHED_TENDERBAL) {
        FINVHED_TENDERBAL = fINVHED_TENDERBAL;
    }

    public String getFINVHED_ADDUSER() {
        return FINVHED_ADDUSER;
    }

    public void setFINVHED_ADDUSER(String fINVHED_ADDUSER) {
        FINVHED_ADDUSER = fINVHED_ADDUSER;
    }

    public String getFINVHED_ADDDATE() {
        return FINVHED_ADDDATE;
    }

    public void setFINVHED_ADDDATE(String fINVHED_ADDDATE) {
        FINVHED_ADDDATE = fINVHED_ADDDATE;
    }

    public String getFINVHED_ADDMACH() {
        return FINVHED_ADDMACH;
    }

    public void setFINVHED_ADDMACH(String fINVHED_ADDMACH) {
        FINVHED_ADDMACH = fINVHED_ADDMACH;
    }

    public String getFINVHED_START_TIME_SO() {
        return FINVHED_START_TIME_SO;
    }

    public void setFINVHED_START_TIME_SO(String fINVHED_START_TIME_SO) {
        FINVHED_START_TIME_SO = fINVHED_START_TIME_SO;
    }

    public String getFINVHED_END_TIME_SO() {
        return FINVHED_END_TIME_SO;
    }

    public void setFINVHED_END_TIME_SO(String fINVHED_END_TIME_SO) {
        FINVHED_END_TIME_SO = fINVHED_END_TIME_SO;
    }

    public String getFINVHED_LONGITUDE() {
        return FINVHED_LONGITUDE;
    }

    public void setFINVHED_LONGITUDE(String fINVHED_LONGITUDE) {
        FINVHED_LONGITUDE = fINVHED_LONGITUDE;
    }

    public String getFINVHED_LATITUDE() {
        return FINVHED_LATITUDE;
    }

    public void setFINVHED_LATITUDE(String fINVHED_LATITUDE) {
        FINVHED_LATITUDE = fINVHED_LATITUDE;
    }

    public String getFINVHED_ADDRESS() {
        return FINVHED_ADDRESS;
    }

    public void setFINVHED_ADDRESS(String fINVHED_ADDRESS) {
        FINVHED_ADDRESS = fINVHED_ADDRESS;
    }

    public String getFINVHED_IS_SYNCED() {
        return FINVHED_IS_SYNCED;
    }

    public void setFINVHED_IS_SYNCED(String fINVHED_IS_SYNCED) {
        FINVHED_IS_SYNCED = fINVHED_IS_SYNCED;
    }

    public String getFINVHED_IS_ACTIVE() {
        return FINVHED_IS_ACTIVE;
    }

    public void setFINVHED_IS_ACTIVE(String fINVHED_IS_ACTIVE) {
        FINVHED_IS_ACTIVE = fINVHED_IS_ACTIVE;
    }

    public String getFINVHED_TOTQTY() {
        return FINVHED_TOTQTY;
    }

    public void setFINVHED_TOTQTY(String fINVHED_TOTQTY) {
        FINVHED_TOTQTY = fINVHED_TOTQTY;
    }

    public String getFINVHED_TOTFREEQTY() {
        return FINVHED_TOTFREEQTY;
    }

    public void setFINVHED_TOTFREEQTY(String fINVHED_TOTFREEQTY) {
        FINVHED_TOTFREEQTY = fINVHED_TOTFREEQTY;
    }

    public String getFINVHED_TOURCODE() {
        return FINVHED_TOURCODE;
    }

    public void setFINVHED_TOURCODE(String fINVHED_TOURCODE) {
        FINVHED_TOURCODE = fINVHED_TOURCODE;
    }

    public String getFINVHED_ROUTECODE() {
        return FINVHED_ROUTECODE;
    }

    public void setFINVHED_ROUTECODE(String fINVHED_ROUTECODE) {
        FINVHED_ROUTECODE = fINVHED_ROUTECODE;
    }

    public String getFINVHED_AREACODE() {
        return FINVHED_AREACODE;
    }

    public void setFINVHED_AREACODE(String fINVHED_AREACODE) {
        FINVHED_AREACODE = fINVHED_AREACODE;
    }

    public JSONObject getInvoiceAsJSON(Context context) throws JSONException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        HashMap<String, Object> finalJSONParams = new HashMap<>();

        HashMap<String, Object> invoiceParams = new HashMap<>();
        invoiceParams.put("ConsoleDB", ConsoleDB);
        invoiceParams.put("DistDB", "");
        invoiceParams.put("FINVHED_ADDDATE", FINVHED_ADDDATE);
        invoiceParams.put("FINVHED_ADDMACH", FINVHED_ADDMACH);
        invoiceParams.put("FINVHED_ADDRESS", FINVHED_ADDRESS);
        invoiceParams.put("FINVHED_ADDUSER", FINVHED_ADDUSER);
        invoiceParams.put("FINVHED_AREACODE", FINVHED_AREACODE);
        invoiceParams.put("FINVHED_CONTACT", FINVHED_CONTACT);
        invoiceParams.put("FINVHED_COSTCODE", FINVHED_COSTCODE);
        invoiceParams.put("FINVHED_CURCODE", FINVHED_CURCODE);
        invoiceParams.put("FINVHED_CURRATE", FINVHED_CURRATE);
        invoiceParams.put("FINVHED_CUSADD1", FINVHED_CUSADD1);
        invoiceParams.put("FINVHED_CUSADD2", FINVHED_CUSADD2);
        invoiceParams.put("FINVHED_CUSADD3", FINVHED_CUSADD3);
        invoiceParams.put("FINVHED_CUSTELE", FINVHED_CUSTELE);
        invoiceParams.put("FINVHED_DEBCODE", FINVHED_DEBCODE);
        invoiceParams.put("FINVHED_END_TIME_SO", FINVHED_END_TIME_SO);
        invoiceParams.put("FINVHED_ID", FINVHED_ID);
        invoiceParams.put("FINVHED_IS_ACTIVE", FINVHED_IS_ACTIVE);
        invoiceParams.put("FINVHED_IS_SYNCED", FINVHED_IS_SYNCED);
        invoiceParams.put("FINVHED_LATITUDE", FINVHED_LATITUDE);
        invoiceParams.put("FINVHED_LOCCODE", FINVHED_LOCCODE);
        invoiceParams.put("FINVHED_LONGITUDE",FINVHED_LONGITUDE);
        invoiceParams.put("FINVHED_MANUREF",FINVHED_MANUREF);
        invoiceParams.put("FINVHED_REFNO",FINVHED_REFNO);
        invoiceParams.put("FINVHED_REMARKS",FINVHED_REMARKS);
        invoiceParams.put("FINVHED_REPCODE",FINVHED_REPCODE);
        invoiceParams.put("FINVHED_ROUTECODE",FINVHED_ROUTECODE);
        invoiceParams.put("FINVHED_SETTING_CODE",FINVHED_SETTING_CODE);
        invoiceParams.put("FINVHED_START_TIME_SO",FINVHED_START_TIME_SO);
        invoiceParams.put("FINVHED_TAXREG",FINVHED_TAXREG);
        invoiceParams.put("FINVHED_TOTALAMT",FINVHED_TOTALAMT);
        invoiceParams.put("FINVHED_TOTALDIS",FINVHED_TOTALDIS);
        invoiceParams.put("FINVHED_TOTALTAX",FINVHED_TOTALTAX);
        invoiceParams.put("FINVHED_TOURCODE",FINVHED_TOURCODE);
        invoiceParams.put("FINVHED_TXNDATE",FINVHED_TXNDATE);
        invoiceParams.put("FINVHED_TXNTYPE",FINVHED_TXNTYPE);
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