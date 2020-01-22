package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Expense {

    private String FEXPENSE_ID;
    private String FEXPENSE_CODE;
    private String FEXPENSE_GRP_CODE;
    private String FEXPENSE_NAME;
    private String FEXPENSE_RECORDID;
    private String FEXPENSE_STATUS;
    private String FEXPENSE_ADD_MACH;
    private String FEXPENSE_ADD_USER;
    private String FEXPENSE_ADD_DATE;


    public String getFEXPENSE_ADD_DATE() {
        return FEXPENSE_ADD_DATE;
    }

    public void setFEXPENSE_ADD_DATE(String fEXPENSE_ADD_DATE) {
        FEXPENSE_ADD_DATE = fEXPENSE_ADD_DATE;
    }

    public String getFEXPENSE_ID() {
        return FEXPENSE_ID;
    }

    public void setFEXPENSE_ID(String fEXPENSE_ID) {
        FEXPENSE_ID = fEXPENSE_ID;
    }

    public String getFEXPENSE_CODE() {
        return FEXPENSE_CODE;
    }

    public void setFEXPENSE_CODE(String fEXPENSE_CODE) {
        FEXPENSE_CODE = fEXPENSE_CODE;
    }

    public String getFEXPENSE_GRP_CODE() {
        return FEXPENSE_GRP_CODE;
    }

    public void setFEXPENSE_GRP_CODE(String fEXPENSE_GRP_CODE) {
        FEXPENSE_GRP_CODE = fEXPENSE_GRP_CODE;
    }

    public String getFEXPENSE_NAME() {
        return FEXPENSE_NAME;
    }

    public void setFEXPENSE_NAME(String fEXPENSE_NAME) {
        FEXPENSE_NAME = fEXPENSE_NAME;
    }

    public String getFEXPENSE_RECORDID() {
        return FEXPENSE_RECORDID;
    }

    public void setFEXPENSE_RECORDID(String fEXPENSE_RECORDID) {
        FEXPENSE_RECORDID = fEXPENSE_RECORDID;
    }

    public String getFEXPENSE_STATUS() {
        return FEXPENSE_STATUS;
    }

    public void setFEXPENSE_STATUS(String fEXPENSE_STATUS) {
        FEXPENSE_STATUS = fEXPENSE_STATUS;
    }

    public String getFEXPENSE_ADD_MACH() {
        return FEXPENSE_ADD_MACH;
    }

    public void setFEXPENSE_ADD_MACH(String fEXPENSE_ADD_MACH) {
        FEXPENSE_ADD_MACH = fEXPENSE_ADD_MACH;
    }

    public String getFEXPENSE_ADD_USER() {
        return FEXPENSE_ADD_USER;
    }

    public void setFEXPENSE_ADD_USER(String fEXPENSE_ADD_USER) {
        FEXPENSE_ADD_USER = fEXPENSE_ADD_USER;
    }
    public static Expense parseExpense(JSONObject instance) throws JSONException {

        if (instance != null) {
            Expense expense = new Expense();
            expense.setFEXPENSE_ADD_DATE(instance.getString("AddDate"));
            expense.setFEXPENSE_ADD_MACH(instance.getString("AddMach"));
            expense.setFEXPENSE_ADD_USER(instance.getString("AddUser"));
            expense.setFEXPENSE_CODE(instance.getString("ExpCode"));
            // expense.setFEXPENSE_GRP_CODE(jObject.getString("ExpGrpCode"));
            expense.setFEXPENSE_NAME(instance.getString("ExpName"));
            expense.setFEXPENSE_STATUS(instance.getString("Status"));
            return expense;
        }

        return null;
    }
}
