package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class DebItemPri {

    private String BRANDCODE;
    private String DEBCODE;
    private String DISPER;

    public String getBRANDCODE() {
        return BRANDCODE;
    }

    public void setBRANDCODE(String bRANDCODE) {
        BRANDCODE = bRANDCODE;
    }

    public String getDEBCODE() {
        return DEBCODE;
    }

    public void setDEBCODE(String dEBCODE) {
        DEBCODE = dEBCODE;
    }

    public String getDISPER() {
        return DISPER;
    }

    public void setDISPER(String dISPER) {
        DISPER = dISPER;
    }

    public static DebItemPri parseDebPri(JSONObject instance) throws JSONException {

        if (instance != null) {
            DebItemPri dip = new DebItemPri();

            dip.setBRANDCODE(instance.getString("BrandCode"));
            dip.setDEBCODE(instance.getString("DebCode"));
            dip.setDISPER(instance.getString("Disper"));

            return dip;
        }

        return null;
    }
}
