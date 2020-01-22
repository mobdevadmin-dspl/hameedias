package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FreeDeb {

    private String FFREEDEB_ID;
    private String FFREEDEB_REFNO;
    private String FFREEDEB_DEB_CODE;
    private String FFREEDEB_ADD_USER;
    private String FFREEDEB_ADD_DATE;
    private String FFREEDEB_ADD_MACH;
    private String FFREEDEB_RECORD_ID;
    private String FFREEDEB_TIMESTAMP_COLUMN;

    public String getFFREEDEB_ID() {
        return FFREEDEB_ID;
    }

    public void setFFREEDEB_ID(String fFREEDEB_ID) {
        FFREEDEB_ID = fFREEDEB_ID;
    }

    public String getFFREEDEB_REFNO() {
        return FFREEDEB_REFNO;
    }

    public void setFFREEDEB_REFNO(String fFREEDEB_REFNO) {
        FFREEDEB_REFNO = fFREEDEB_REFNO;
    }

    public String getFFREEDEB_DEB_CODE() {
        return FFREEDEB_DEB_CODE;
    }

    public void setFFREEDEB_DEB_CODE(String fFREEDEB_DEB_CODE) {
        FFREEDEB_DEB_CODE = fFREEDEB_DEB_CODE;
    }

    public String getFFREEDEB_ADD_USER() {
        return FFREEDEB_ADD_USER;
    }

    public void setFFREEDEB_ADD_USER(String fFREEDEB_ADD_USER) {
        FFREEDEB_ADD_USER = fFREEDEB_ADD_USER;
    }

    public String getFFREEDEB_ADD_DATE() {
        return FFREEDEB_ADD_DATE;
    }

    public void setFFREEDEB_ADD_DATE(String fFREEDEB_ADD_DATE) {
        FFREEDEB_ADD_DATE = fFREEDEB_ADD_DATE;
    }

    public String getFFREEDEB_ADD_MACH() {
        return FFREEDEB_ADD_MACH;
    }

    public void setFFREEDEB_ADD_MACH(String fFREEDEB_ADD_MACH) {
        FFREEDEB_ADD_MACH = fFREEDEB_ADD_MACH;
    }

    public String getFFREEDEB_RECORD_ID() {
        return FFREEDEB_RECORD_ID;
    }

    public void setFFREEDEB_RECORD_ID(String fFREEDEB_RECORD_ID) {
        FFREEDEB_RECORD_ID = fFREEDEB_RECORD_ID;
    }

    public String getFFREEDEB_TIMESTAMP_COLUMN() {
        return FFREEDEB_TIMESTAMP_COLUMN;
    }

    public void setFFREEDEB_TIMESTAMP_COLUMN(String fFREEDEB_TIMESTAMP_COLUMN) {
        FFREEDEB_TIMESTAMP_COLUMN = fFREEDEB_TIMESTAMP_COLUMN;
    }
    public static FreeDeb parseFreeDeb(JSONObject instance) throws JSONException {

        if (instance != null) {
            FreeDeb deb = new FreeDeb();

            deb.setFFREEDEB_REFNO(instance.getString("Refno"));
            deb.setFFREEDEB_DEB_CODE(instance.getString("Debcode"));
            deb.setFFREEDEB_ADD_USER("");
            deb.setFFREEDEB_ADD_DATE("");
            deb.setFFREEDEB_ADD_MACH("");
            deb.setFFREEDEB_TIMESTAMP_COLUMN("");

            return deb;
        }

        return null;
    }

}
