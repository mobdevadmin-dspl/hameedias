package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Tax {

    private String ID;
    private String TAXCODE;
    private String TAXNAME;
    private String TAXPER;
    private String TAXREGNO;

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
