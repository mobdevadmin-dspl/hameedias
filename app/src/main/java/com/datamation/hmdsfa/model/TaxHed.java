package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class TaxHed {

    @SerializedName("Active")
    private String ACTIVE;
    @SerializedName("TaxComCode")
    private String TAXCOMCODE;
    @SerializedName("TaxComName")
    private String TAXCOMNAME;


    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getACTIVE() {
        return ACTIVE;
    }

    public void setACTIVE(String aCTIVE) {
        ACTIVE = aCTIVE;
    }

    public String getTAXCOMCODE() {
        return TAXCOMCODE;
    }

    public void setTAXCOMCODE(String tAXCOMCODE) {
        TAXCOMCODE = tAXCOMCODE;
    }

    public String getTAXCOMNAME() {
        return TAXCOMNAME;
    }

    public void setTAXCOMNAME(String tAXCOMNAME) {
        TAXCOMNAME = tAXCOMNAME;
    }

    public static TaxHed parseTaxHed(JSONObject instance) throws JSONException {

        if (instance != null) {
            TaxHed tax = new TaxHed();

            tax.setACTIVE(instance.getString("Active"));
            tax.setTAXCOMCODE(instance.getString("TaxComCode"));
            tax.setTAXCOMNAME(instance.getString("TaxComName"));

            return tax;
        }

        return null;
    }
}
