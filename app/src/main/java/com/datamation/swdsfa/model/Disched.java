package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Disched {

    private String FDISCHED_ID;
    private String FDISCHED_REF_NO;
    private String FDISCHED_TXN_DATE;
    private String FDISCHED_DISC_DESC;
    private String FDISCHED_PRIORITY;
    private String FDISCHED_DIS_TYPE;
    private String FDISCHED_V_DATE_F;
    private String FDISCHED_V_DATE_T;
    private String FDISCHED_REMARK;
    private String FDISCHED_ADD_USER;
    private String FDISCHED_ADD_DATE;
    private String FDISCHED_ADD_MACH;
    private String FDISCHED_RECORD_ID;
    private String FDISCHED_TIMESTAMP_COLUMN;

    public String getFDISCHED_ID() {
        return FDISCHED_ID;
    }

    public void setFDISCHED_ID(String fDISCHED_ID) {
        FDISCHED_ID = fDISCHED_ID;
    }

    public String getFDISCHED_REF_NO() {
        return FDISCHED_REF_NO;
    }

    public void setFDISCHED_REF_NO(String fDISCHED_REF_NO) {
        FDISCHED_REF_NO = fDISCHED_REF_NO;
    }

    public String getFDISCHED_TXN_DATE() {
        return FDISCHED_TXN_DATE;
    }

    public void setFDISCHED_TXN_DATE(String fDISCHED_TXN_DATE) {
        FDISCHED_TXN_DATE = fDISCHED_TXN_DATE;
    }

    public String getFDISCHED_DISC_DESC() {
        return FDISCHED_DISC_DESC;
    }

    public void setFDISCHED_DISC_DESC(String fDISCHED_DISC_DESC) {
        FDISCHED_DISC_DESC = fDISCHED_DISC_DESC;
    }

    public String getFDISCHED_PRIORITY() {
        return FDISCHED_PRIORITY;
    }

    public void setFDISCHED_PRIORITY(String fDISCHED_PRIORITY) {
        FDISCHED_PRIORITY = fDISCHED_PRIORITY;
    }

    public String getFDISCHED_DIS_TYPE() {
        return FDISCHED_DIS_TYPE;
    }

    public void setFDISCHED_DIS_TYPE(String fDISCHED_DIS_TYPE) {
        FDISCHED_DIS_TYPE = fDISCHED_DIS_TYPE;
    }

    public String getFDISCHED_V_DATE_F() {
        return FDISCHED_V_DATE_F;
    }

    public void setFDISCHED_V_DATE_F(String fDISCHED_V_DATE_F) {
        FDISCHED_V_DATE_F = fDISCHED_V_DATE_F;
    }

    public String getFDISCHED_V_DATE_T() {
        return FDISCHED_V_DATE_T;
    }

    public void setFDISCHED_V_DATE_T(String fDISCHED_V_DATE_T) {
        FDISCHED_V_DATE_T = fDISCHED_V_DATE_T;
    }

    public String getFDISCHED_REMARK() {
        return FDISCHED_REMARK;
    }

    public void setFDISCHED_REMARK(String fDISCHED_REMARK) {
        FDISCHED_REMARK = fDISCHED_REMARK;
    }

    public String getFDISCHED_ADD_USER() {
        return FDISCHED_ADD_USER;
    }

    public void setFDISCHED_ADD_USER(String fDISCHED_ADD_USER) {
        FDISCHED_ADD_USER = fDISCHED_ADD_USER;
    }

    public String getFDISCHED_ADD_DATE() {
        return FDISCHED_ADD_DATE;
    }

    public void setFDISCHED_ADD_DATE(String fDISCHED_ADD_DATE) {
        FDISCHED_ADD_DATE = fDISCHED_ADD_DATE;
    }

    public String getFDISCHED_ADD_MACH() {
        return FDISCHED_ADD_MACH;
    }

    public void setFDISCHED_ADD_MACH(String fDISCHED_ADD_MACH) {
        FDISCHED_ADD_MACH = fDISCHED_ADD_MACH;
    }

    public String getFDISCHED_RECORD_ID() {
        return FDISCHED_RECORD_ID;
    }

    public void setFDISCHED_RECORD_ID(String fDISCHED_RECORD_ID) {
        FDISCHED_RECORD_ID = fDISCHED_RECORD_ID;
    }

    public String getFDISCHED_TIMESTAMP_COLUMN() {
        return FDISCHED_TIMESTAMP_COLUMN;
    }

    public void setFDISCHED_TIMESTAMP_COLUMN(String fDISCHED_TIMESTAMP_COLUMN) {
        FDISCHED_TIMESTAMP_COLUMN = fDISCHED_TIMESTAMP_COLUMN;
    }
    public static Disched parseDisched(JSONObject instance) throws JSONException {

        if (instance != null) {
            Disched disched = new Disched();

            disched.setFDISCHED_REF_NO(instance.getString("Refno"));
            disched.setFDISCHED_TXN_DATE(instance.getString("Txndate"));
            disched.setFDISCHED_DISC_DESC(instance.getString("DiscDesc"));
            disched.setFDISCHED_PRIORITY(instance.getString("Priority"));
            disched.setFDISCHED_DIS_TYPE(instance.getString("DisType"));
            disched.setFDISCHED_V_DATE_F(instance.getString("Vdatef"));
            disched.setFDISCHED_V_DATE_T(instance.getString("Vdatet"));
            disched.setFDISCHED_REMARK("");
            disched.setFDISCHED_ADD_USER("");
            disched.setFDISCHED_ADD_DATE("");
            disched.setFDISCHED_ADD_MACH("");
            disched.setFDISCHED_RECORD_ID("");
            disched.setFDISCHED_TIMESTAMP_COLUMN("");


            return disched;
        }

        return null;
    }
}
