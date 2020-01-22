package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class TaxDet {

    private String ID;
    private String TAXCOMCODE;
    private String TAXCODE;
    private String RATE;
    private String SEQ;
    private String MODE;
    private String TAXVAL;
    private String TAXTYPE;

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getTAXCOMCODE() {
        return TAXCOMCODE;
    }

    public void setTAXCOMCODE(String tAXCOMCODE) {
        TAXCOMCODE = tAXCOMCODE;
    }

    public String getTAXCODE() {
        return TAXCODE;
    }

    public void setTAXCODE(String tAXCODE) {
        TAXCODE = tAXCODE;
    }

    public String getRATE() {
        return RATE;
    }

    public void setRATE(String rATE) {
        RATE = rATE;
    }

    public String getSEQ() {
        return SEQ;
    }

    public void setSEQ(String sEQ) {
        SEQ = sEQ;
    }

    public String getMODE() {
        return MODE;
    }

    public void setMODE(String mODE) {
        MODE = mODE;
    }

    public String getTAXVAL() {
        return TAXVAL;
    }

    public void setTAXVAL(String tAXVAL) {
        TAXVAL = tAXVAL;
    }

    public String getTAXTYPE() {
        return TAXTYPE;
    }

    public void setTAXTYPE(String tAXTYPE) {
        TAXTYPE = tAXTYPE;
    }

    public static TaxDet parseTaxDet(JSONObject instance) throws JSONException {

        if (instance != null) {
            TaxDet tax = new TaxDet();

            tax.setTAXVAL(instance.getString("TaxRate"));
            tax.setSEQ(instance.getString("TaxSeq"));
            tax.setTAXCODE(instance.getString("TaxCode"));
            tax.setTAXCOMCODE(instance.getString("TaxComCode"));
            tax.setMODE(instance.getString("TaxMode"));
            tax.setRATE(instance.getString("TaxPer"));

            return tax;
        }

        return null;
    }
}
