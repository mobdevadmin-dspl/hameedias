package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanySetting {

    private String FCOMPANYSETTING_ID;
    private String FCOMPANYSETTING_SETTINGS_CODE;
    private String FCOMPANYSETTING_GRP;
    private String FCOMPANYSETTING_LOCATION_CHAR;
    private String FCOMPANYSETTING_CHAR_VAL;
    private String FCOMPANYSETTING_NUM_VAL;
    private String FCOMPANYSETTING_REMARKS;
    private String FCOMPANYSETTING_TYPE;
    private String FCOMPANYSETTING_COMPANY_CODE;
    // rashmi - 2019-12-17 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FCOMPANYSETTING = "fCompanySetting";
    // table attributes
    public static final String ID = "fcomset_id";// ok
    public static final String SETTINGS_CODE = "cSettingsCode";// ok
    public static final String GRP = "cSettingGrp";// ok
    public static final String LOCATION_CHAR = "cLocationChar";// ok
    public static final String CHAR_VAL = "cCharVal";// ok
    public static final String NUM_VAL = "nNumVal";// ok
    public static final String REMARKS = "cRemarks";// ok
    public static final String TYPE = "nType";// ok
    public static final String COMPANY_CODE = "cCompanyCode";// ok
    // create String
    public static final String CREATE_FCOMPANYSETTING_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FCOMPANYSETTING + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SETTINGS_CODE + " TEXT, " + GRP + " TEXT, " + LOCATION_CHAR + " TEXT, " + CHAR_VAL + " TEXT, " + NUM_VAL + " TEXT, " + REMARKS + " TEXT, " + TYPE + " TEXT, " + COMPANY_CODE + " TEXT); ";


    public String getFCOMPANYSETTING_ID() {
        return FCOMPANYSETTING_ID;
    }

    public void setFCOMPANYSETTING_ID(String fCOMPANYSETTING_ID) {
        FCOMPANYSETTING_ID = fCOMPANYSETTING_ID;
    }

    public String getFCOMPANYSETTING_SETTINGS_CODE() {
        return FCOMPANYSETTING_SETTINGS_CODE;
    }

    public void setFCOMPANYSETTING_SETTINGS_CODE(
            String fCOMPANYSETTING_SETTINGS_CODE) {
        FCOMPANYSETTING_SETTINGS_CODE = fCOMPANYSETTING_SETTINGS_CODE;
    }

    public String getFCOMPANYSETTING_GRP() {
        return FCOMPANYSETTING_GRP;
    }

    public void setFCOMPANYSETTING_GRP(String fCOMPANYSETTING_GRP) {
        FCOMPANYSETTING_GRP = fCOMPANYSETTING_GRP;
    }

    public String getFCOMPANYSETTING_LOCATION_CHAR() {
        return FCOMPANYSETTING_LOCATION_CHAR;
    }

    public void setFCOMPANYSETTING_LOCATION_CHAR(
            String fCOMPANYSETTING_LOCATION_CHAR) {
        FCOMPANYSETTING_LOCATION_CHAR = fCOMPANYSETTING_LOCATION_CHAR;
    }

    public String getFCOMPANYSETTING_CHAR_VAL() {
        return FCOMPANYSETTING_CHAR_VAL;
    }

    public void setFCOMPANYSETTING_CHAR_VAL(String fCOMPANYSETTING_CHAR_VAL) {
        FCOMPANYSETTING_CHAR_VAL = fCOMPANYSETTING_CHAR_VAL;
    }

    public String getFCOMPANYSETTING_NUM_VAL() {
        return FCOMPANYSETTING_NUM_VAL;
    }

    public void setFCOMPANYSETTING_NUM_VAL(String fCOMPANYSETTING_NUM_VAL) {
        FCOMPANYSETTING_NUM_VAL = fCOMPANYSETTING_NUM_VAL;
    }

    public String getFCOMPANYSETTING_REMARKS() {
        return FCOMPANYSETTING_REMARKS;
    }

    public void setFCOMPANYSETTING_REMARKS(String fCOMPANYSETTING_REMARKS) {
        FCOMPANYSETTING_REMARKS = fCOMPANYSETTING_REMARKS;
    }

    public String getFCOMPANYSETTING_TYPE() {
        return FCOMPANYSETTING_TYPE;
    }

    public void setFCOMPANYSETTING_TYPE(String fCOMPANYSETTING_TYPE) {
        FCOMPANYSETTING_TYPE = fCOMPANYSETTING_TYPE;
    }

    public String getFCOMPANYSETTING_COMPANY_CODE() {
        return FCOMPANYSETTING_COMPANY_CODE;
    }

    public void setFCOMPANYSETTING_COMPANY_CODE(String fCOMPANYSETTING_COMPANY_CODE) {
        FCOMPANYSETTING_COMPANY_CODE = fCOMPANYSETTING_COMPANY_CODE;
    }

    public static CompanySetting parseSettings(JSONObject instance) throws JSONException {

        if (instance != null) {
            CompanySetting setting = new CompanySetting();
            setting.setFCOMPANYSETTING_CHAR_VAL(instance.getString("cCharVal"));
            setting.setFCOMPANYSETTING_COMPANY_CODE(instance.getString("cCompanyCode"));
            setting.setFCOMPANYSETTING_LOCATION_CHAR(instance.getString("cLocationChar"));
            setting.setFCOMPANYSETTING_REMARKS(instance.getString("cRemarks"));
            setting.setFCOMPANYSETTING_GRP(instance.getString("cSettingGrp"));
            setting.setFCOMPANYSETTING_SETTINGS_CODE(instance.getString("cSettingsCode"));
            setting.setFCOMPANYSETTING_NUM_VAL(instance.getString("nNumVal"));
            setting.setFCOMPANYSETTING_TYPE(instance.getString("nType"));
            return setting;
        }

        return null;
    }
}
