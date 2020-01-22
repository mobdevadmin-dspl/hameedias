package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Discdet {

    private String FDISCDET_ID;
    private String FDISCDET_REF_NO;
    private String FDISCDET_ITEM_CODE;
    private String FDISCDET_RECORD_ID;
    private String FDISCHED_TIEMSTAMP_COLUMN;

    public String getFDISCDET_ID() {
        return FDISCDET_ID;
    }

    public void setFDISCDET_ID(String fDISCDET_ID) {
        FDISCDET_ID = fDISCDET_ID;
    }

    public String getFDISCDET_REF_NO() {
        return FDISCDET_REF_NO;
    }

    public void setFDISCDET_REF_NO(String fDISCDET_REF_NO) {
        FDISCDET_REF_NO = fDISCDET_REF_NO;
    }

    public String getFDISCDET_ITEM_CODE() {
        return FDISCDET_ITEM_CODE;
    }

    public void setFDISCDET_ITEM_CODE(String fDISCDET_ITEM_CODE) {
        FDISCDET_ITEM_CODE = fDISCDET_ITEM_CODE;
    }

    public String getFDISCDET_RECORD_ID() {
        return FDISCDET_RECORD_ID;
    }

    public void setFDISCDET_RECORD_ID(String fDISCDET_RECORD_ID) {
        FDISCDET_RECORD_ID = fDISCDET_RECORD_ID;
    }

    public String getFDISCHED_TIEMSTAMP_COLUMN() {
        return FDISCHED_TIEMSTAMP_COLUMN;
    }

    public void setFDISCHED_TIEMSTAMP_COLUMN(String fDISCHED_TIEMSTAMP_COLUMN) {
        FDISCHED_TIEMSTAMP_COLUMN = fDISCHED_TIEMSTAMP_COLUMN;
    }
    public static Discdet parseDiscDet(JSONObject instance) throws JSONException {

        if (instance != null) {
            Discdet did = new Discdet();

            did.setFDISCDET_REF_NO(instance.getString("Refno"));
            did.setFDISCDET_ITEM_CODE(instance.getString("Itemcode"));
            did.setFDISCHED_TIEMSTAMP_COLUMN("");


            return did;
        }

        return null;
    }
}
