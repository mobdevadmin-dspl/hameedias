package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Discdeb {

    private String FDISCDEB_ID;
    private String FDISCDEB_REF_NO;
    private String FDISCDEB_DEB_CODE;
    private String FDISCDEB_RECORD_ID;
    private String FDISCDEB_TIEMSTAMP_COLUMN;

    public String getFDISCDEB_ID() {
        return FDISCDEB_ID;
    }

    public void setFDISCDEB_ID(String fDISCDEB_ID) {
        FDISCDEB_ID = fDISCDEB_ID;
    }

    public String getFDISCDEB_REF_NO() {
        return FDISCDEB_REF_NO;
    }

    public void setFDISCDEB_REF_NO(String fDISCDEB_REF_NO) {
        FDISCDEB_REF_NO = fDISCDEB_REF_NO;
    }

    public String getFDISCDEB_DEB_CODE() {
        return FDISCDEB_DEB_CODE;
    }

    public void setFDISCDEB_DEB_CODE(String fDISCDEB_DEB_CODE) {
        FDISCDEB_DEB_CODE = fDISCDEB_DEB_CODE;
    }

    public String getFDISCDEB_RECORD_ID() {
        return FDISCDEB_RECORD_ID;
    }

    public void setFDISCDEB_RECORD_ID(String fDISCDEB_RECORD_ID) {
        FDISCDEB_RECORD_ID = fDISCDEB_RECORD_ID;
    }

    public String getFDISCDEB_TIEMSTAMP_COLUMN() {
        return FDISCDEB_TIEMSTAMP_COLUMN;
    }

    public void setFDISCDEB_TIEMSTAMP_COLUMN(String fDISCDEB_TIEMSTAMP_COLUMN) {
        FDISCDEB_TIEMSTAMP_COLUMN = fDISCDEB_TIEMSTAMP_COLUMN;
    }
    public static Discdeb parseDiscDeb(JSONObject instance) throws JSONException {

        if (instance != null) {
            Discdeb did = new Discdeb();

            did.setFDISCDEB_REF_NO(instance.getString("Refno"));
            did.setFDISCDEB_DEB_CODE(instance.getString("Debcode"));

            return did;
        }

        return null;
    }
}
