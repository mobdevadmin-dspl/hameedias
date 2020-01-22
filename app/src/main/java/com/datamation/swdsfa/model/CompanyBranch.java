package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanyBranch {

    private String FCOMPANYBRANCH_ID;
    private String FCOMPANYBRANCH_BRANCH_CODE;
    private String FCOMPANYBRANCH_RECORD_ID;
    private String FCOMPANYBRANCH_CSETTINGS_CODE;
    private String FCOMPANYBRANCH_NNUM_VAL;
    private String NYEAR;
    private String NMONTH;
    // rashmi - 2019-12-17 move from database_helper , because of reduce coding in database helper*******************************************************************************

    // table
    public static final String TABLE_FCOMPANYBRANCH = "FCompanyBranch";
    // table attributes
    public static final String ID = "fcombra_id";
    public static final String BRANCH_CODE = "BranchCode";
    public static final String RECORD_ID = "RecordId";
    public static final String CSETTINGS_CODE = "cSettingsCode";
    public static final String NNUM_VAL = "nNumVal";
    public static final String FCOMPANYBRANCH_YEAR = "nYear";
    public static final String FCOMPANYBRANCH_MONTH = "nMonth";

    public static final String CREATE_FCOMPANYBRANCH_TABLE = "CREATE  TABLE IF NOT EXISTS " + CompanyBranch.TABLE_FCOMPANYBRANCH + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BRANCH_CODE + " TEXT, " + RECORD_ID + " TEXT, " + CSETTINGS_CODE + " TEXT, " + NNUM_VAL + " TEXT," + FCOMPANYBRANCH_YEAR + " TEXT," + FCOMPANYBRANCH_MONTH + " TEXT);";

    public String getFCOMPANYBRANCH_ID() {
        return FCOMPANYBRANCH_ID;
    }

    public void setFCOMPANYBRANCH_ID(String fCOMPANYBRANCH_ID) {
        FCOMPANYBRANCH_ID = fCOMPANYBRANCH_ID;
    }

    public String getFCOMPANYBRANCH_BRANCH_CODE() {
        return FCOMPANYBRANCH_BRANCH_CODE;
    }

    public void setFCOMPANYBRANCH_BRANCH_CODE(String fCOMPANYBRANCH_BRANCH_CODE) {
        FCOMPANYBRANCH_BRANCH_CODE = fCOMPANYBRANCH_BRANCH_CODE;
    }

    public String getFCOMPANYBRANCH_RECORD_ID() {
        return FCOMPANYBRANCH_RECORD_ID;
    }

    public void setFCOMPANYBRANCH_RECORD_ID(String fCOMPANYBRANCH_RECORD_ID) {
        FCOMPANYBRANCH_RECORD_ID = fCOMPANYBRANCH_RECORD_ID;
    }

    public String getFCOMPANYBRANCH_CSETTINGS_CODE() {
        return FCOMPANYBRANCH_CSETTINGS_CODE;
    }

    public void setFCOMPANYBRANCH_CSETTINGS_CODE(
            String fCOMPANYBRANCH_CSETTINGS_CODE) {
        FCOMPANYBRANCH_CSETTINGS_CODE = fCOMPANYBRANCH_CSETTINGS_CODE;
    }

    public String getFCOMPANYBRANCH_NNUM_VAL() {
        return FCOMPANYBRANCH_NNUM_VAL;
    }

    public void setFCOMPANYBRANCH_NNUM_VAL(String fCOMPANYBRANCH_NNUM_VAL) {
        FCOMPANYBRANCH_NNUM_VAL = fCOMPANYBRANCH_NNUM_VAL;
    }

    public String getNYEAR() {
        return NYEAR;
    }

    public void setNYEAR(String nYEAR) {
        NYEAR = nYEAR;
    }

    public String getNMONTH() {
        return NMONTH;
    }

    public void setNMONTH(String nMONTH) {
        NMONTH = nMONTH;
    }
    public static CompanyBranch parseSettings(JSONObject instance) throws JSONException {

        if (instance != null) {
            CompanyBranch branch = new CompanyBranch();
            branch.setFCOMPANYBRANCH_BRANCH_CODE(instance.getString("BranchCode"));
            branch.setFCOMPANYBRANCH_CSETTINGS_CODE(instance.getString("cSettingsCode"));
            branch.setFCOMPANYBRANCH_NNUM_VAL(instance.getString("nNumVal"));
            branch.setNYEAR(instance.getString("nYear"));
            branch.setNMONTH(instance.getString("nMonth"));
            return branch;
        }

        return null;
    }
}
