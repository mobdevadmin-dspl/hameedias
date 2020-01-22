package com.datamation.swdsfa.model;


import org.json.JSONException;
import org.json.JSONObject;

public class ReferenceDetail {

    private String REFERENCE_REP_CODE;
    private String REFERENCE_SETTING_CODE;
    private String REFERENCE_NNUM_VAL;
    private String REFERENCE_NYEAR_VAL;
    private String REFERENCE_NMONTH_VAL;

    public String getREFERENCE_REP_CODE() {
        return REFERENCE_REP_CODE;
    }
    public void setREFERENCE_REP_CODE(String fCOMPANYBRANCH_BRANCH_CODE) {
        REFERENCE_REP_CODE = fCOMPANYBRANCH_BRANCH_CODE;
    }

    public String getREFERENCE_SETTING_CODE() {
        return REFERENCE_SETTING_CODE;
    }
    public void setREFERENCE_SETTING_CODE(
            String fCOMPANYBRANCH_CSETTINGS_CODE) {
        REFERENCE_SETTING_CODE = fCOMPANYBRANCH_CSETTINGS_CODE;
    }
    public String getREFERENCE_NNUM_VAL() {
        return REFERENCE_NNUM_VAL;
    }
    public void setREFERENCE_NNUM_VAL(String fCOMPANYBRANCH_NNUM_VAL) {
        REFERENCE_NNUM_VAL = fCOMPANYBRANCH_NNUM_VAL;
    }
    public String getREFERENCE_NYEAR_VAL() {
        return REFERENCE_NYEAR_VAL;
    }
    public void setREFERENCE_NYEAR_VAL(String fCOMPANYBRANCH_NYEAR_VAL) {
        REFERENCE_NYEAR_VAL = fCOMPANYBRANCH_NYEAR_VAL;
    }
    public String getREFERENCE_NMONTH_VAL() {
        return REFERENCE_NMONTH_VAL;
    }
    public void setREFERENCE_NMONTH_VAL(String fCOMPANYBRANCH_NMONTH_VAL) {
        REFERENCE_NMONTH_VAL = fCOMPANYBRANCH_NMONTH_VAL;
    }


    public static ReferenceDetail parseRef(JSONObject instance) throws JSONException {

        if (instance != null) {
            ReferenceDetail ref = new ReferenceDetail();
            ref.setREFERENCE_REP_CODE(instance.getString("repCode"));
            ref.setREFERENCE_SETTING_CODE(instance.getString("settingCode"));
            ref.setREFERENCE_NMONTH_VAL(instance.getString("nMonth"));
            ref.setREFERENCE_NYEAR_VAL(instance.getString("nYear"));
            ref.setREFERENCE_NNUM_VAL(instance.getString("nNumVal"));

            return ref;
        }

        return null;
    }
}
