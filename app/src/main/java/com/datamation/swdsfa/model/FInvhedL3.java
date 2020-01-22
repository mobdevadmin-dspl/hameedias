package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FInvhedL3 {

    private String FINVHEDL3_ID;
    private String FINVHEDL3_DEB_CODE;
    private String FINVHEDL3_REF_NO;
    private String FINVHEDL3_REF_NO1;
    private String FINVHEDL3_TOTAL_AMT;
    private String FINVHEDL3_TOTAL_TAX;
    private String FINVHEDL3_TXN_DATE;

    public String getFINVHEDL3_ID() {
        return FINVHEDL3_ID;
    }

    public void setFINVHEDL3_ID(String FINVHEDL3_ID) {
        this.FINVHEDL3_ID = FINVHEDL3_ID;
    }

    public String getFINVHEDL3_DEB_CODE() {
        return FINVHEDL3_DEB_CODE;
    }

    public void setFINVHEDL3_DEB_CODE(String FINVHEDL3_DEB_CODE) {
        this.FINVHEDL3_DEB_CODE = FINVHEDL3_DEB_CODE;
    }

    public String getFINVHEDL3_REF_NO() {
        return FINVHEDL3_REF_NO;
    }

    public void setFINVHEDL3_REF_NO(String FINVHEDL3_REF_NO) {
        this.FINVHEDL3_REF_NO = FINVHEDL3_REF_NO;
    }

    public String getFINVHEDL3_REF_NO1() {
        return FINVHEDL3_REF_NO1;
    }

    public void setFINVHEDL3_REF_NO1(String FINVHEDL3_REF_NO1) {
        this.FINVHEDL3_REF_NO1 = FINVHEDL3_REF_NO1;
    }

    public String getFINVHEDL3_TOTAL_AMT() {
        return FINVHEDL3_TOTAL_AMT;
    }

    public void setFINVHEDL3_TOTAL_AMT(String FINVHEDL3_TOTAL_AMT) {
        this.FINVHEDL3_TOTAL_AMT = FINVHEDL3_TOTAL_AMT;
    }

    public String getFINVHEDL3_TOTAL_TAX() {
        return FINVHEDL3_TOTAL_TAX;
    }

    public void setFINVHEDL3_TOTAL_TAX(String FINVHEDL3_TOTAL_TAX) {
        this.FINVHEDL3_TOTAL_TAX = FINVHEDL3_TOTAL_TAX;
    }

    public String getFINVHEDL3_TXN_DATE() {
        return FINVHEDL3_TXN_DATE;
    }

    public void setFINVHEDL3_TXN_DATE(String FINVHEDL3_TXN_DATE) {
        this.FINVHEDL3_TXN_DATE = FINVHEDL3_TXN_DATE;
    }
    public static FInvhedL3 parseInvoiceHeds(JSONObject jObject) throws JSONException {

        if (jObject != null) {
            FInvhedL3 finvHedL3 = new FInvhedL3();

            finvHedL3.setFINVHEDL3_DEB_CODE(jObject.getString("DebCode"));
            finvHedL3.setFINVHEDL3_REF_NO(jObject.getString("RefNo"));
            finvHedL3.setFINVHEDL3_REF_NO1(jObject.getString("RefNo1"));
            finvHedL3.setFINVHEDL3_TOTAL_AMT(jObject.getString("TotalAmt"));
            finvHedL3.setFINVHEDL3_TOTAL_TAX(jObject.getString("TotalTax"));
            finvHedL3.setFINVHEDL3_TXN_DATE(jObject.getString("TxnDate"));
            //finvHedL3.setFINVHEDL3_COST_CODE(jObject.getString("CostCode"));


            return finvHedL3;
        }

        return null;
    }
}
