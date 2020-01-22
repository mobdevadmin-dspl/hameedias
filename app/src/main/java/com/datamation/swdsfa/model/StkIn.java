package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class StkIn {

    private String ID;
    private String REFNO;
    private String TXNDATE;
    private String STKRECNO;
    private String STKRecDate;
    private String LOCCODE;
    private String ITEMCODE;
    private String INQTY;
    private String BALQTY;
    private String COSTPRICE;
    private String OTHCOST;
    private String TXNTYPE;
    private String STKTXNNO;


    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getREFNO() {
        return REFNO;
    }

    public void setREFNO(String rEFNO) {
        REFNO = rEFNO;
    }

    public String getTXNDATE() {
        return TXNDATE;
    }

    public void setTXNDATE(String tXNDATE) {
        TXNDATE = tXNDATE;
    }

    public String getSTKRECNO() {
        return STKRECNO;
    }

    public void setSTKRECNO(String sTKRECNO) {
        STKRECNO = sTKRECNO;
    }

    public String getSTKRecDate() {
        return STKRecDate;
    }

    public void setSTKRecDate(String sTKRecDate) {
        STKRecDate = sTKRecDate;
    }

    public String getLOCCODE() {
        return LOCCODE;
    }

    public void setLOCCODE(String lOCCODE) {
        LOCCODE = lOCCODE;
    }

    public String getITEMCODE() {
        return ITEMCODE;
    }

    public void setITEMCODE(String iTEMCODE) {
        ITEMCODE = iTEMCODE;
    }

    public String getINQTY() {
        return INQTY;
    }

    public void setINQTY(String iNQTY) {
        INQTY = iNQTY;
    }

    public String getBALQTY() {
        return BALQTY;
    }

    public void setBALQTY(String bALQTY) {
        BALQTY = bALQTY;
    }

    public String getCOSTPRICE() {
        return COSTPRICE;
    }

    public void setCOSTPRICE(String cOSTPRICE) {
        COSTPRICE = cOSTPRICE;
    }

    public String getOTHCOST() {
        return OTHCOST;
    }

    public void setOTHCOST(String oTHCOST) {
        OTHCOST = oTHCOST;
    }

    public String getTXNTYPE() {
        return TXNTYPE;
    }

    public void setTXNTYPE(String tXNTYPE) {
        TXNTYPE = tXNTYPE;
    }

    public String getSTKTXNNO() {
        return STKTXNNO;
    }

    public void setSTKTXNNO(String sTKTXNNO) {
        STKTXNNO = sTKTXNNO;
    }
    public static StkIn parseStkIn(JSONObject instance) throws JSONException {

        if (instance != null) {
            StkIn stkIn = new StkIn();

            stkIn.setBALQTY((instance.getString("BalQty")));
            stkIn.setCOSTPRICE((instance.getString("CostPrice")));
            stkIn.setINQTY((instance.getString("InQty")));
            stkIn.setITEMCODE((instance.getString("ItemCode")));
            stkIn.setLOCCODE((instance.getString("LocCode")));
            stkIn.setOTHCOST((instance.getString("OthCost")));
            stkIn.setREFNO((instance.getString("RefNo")));
            stkIn.setSTKRecDate((instance.getString("StkRecDate")));
            stkIn.setSTKRECNO((instance.getString("StkRecNo")));
            stkIn.setTXNDATE((instance.getString("TxnDate")));
            stkIn.setTXNTYPE((instance.getString("TxnType")));

            return stkIn;
        }

        return null;
    }
}
