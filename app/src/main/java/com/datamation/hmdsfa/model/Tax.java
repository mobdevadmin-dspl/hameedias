package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Tax {

    @SerializedName("TaxCode")
    private String TAXCODE;
    @SerializedName("TaxName")
    private String TAXNAME;
    @SerializedName("TaxPer")
    private String TAXPER;
    @SerializedName("TaxRegNo")
    private String TAXREGNO;

    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getTAXCODE() {
        return TAXCODE;
    }

    public void setTAXCODE(String tAXCODE) {
        TAXCODE = tAXCODE;
    }

    public String getTAXNAME() {
        return TAXNAME;
    }

    public void setTAXNAME(String tAXNAME) {
        TAXNAME = tAXNAME;
    }

    public String getTAXPER() {
        return TAXPER;
    }

    public void setTAXPER(String tAXPER) {
        TAXPER = tAXPER;
    }

    public String getTAXREGNO() {
        return TAXREGNO;
    }

    public void setTAXREGNO(String tAXREGNO) {
        TAXREGNO = tAXREGNO;
    }

    public static Tax parseTax(JSONObject instance) throws JSONException {

        if (instance != null) {
            Tax tax = new Tax();

            tax.setTAXCODE(instance.getString("TaxCode"));
            tax.setTAXNAME(instance.getString("TaxName"));
            tax.setTAXPER(instance.getString("TaxPer"));
            tax.setTAXREGNO(instance.getString("TaxRegNo"));

            return tax;
        }

        return null;
    }
}
