package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FreeSlab {
    private String FFREESLAB_ID;
    private String FFREESLAB_REFNO;
    private String FFREESLAB_QTY_F;
    private String FFREESLAB_QTY_T;
    private String FFREESLAB_FITEM_CODE;
    private String FFREESLAB_FREE_QTY;
    private String FFREESLAB_ADD_USER;
    private String FFREESLAB_ADD_DATE;
    private String FFREESLAB_ADD_MACH;
    private String FFREESLAB_RECORD_ID;
    private String FFREESLAB_TIMESTAP_COLUMN;
    private String FFREESLAB_SEQ_NO;

    public String getFFREESLAB_ID() {
        return FFREESLAB_ID;
    }

    public void setFFREESLAB_ID(String fFREESLAB_ID) {
        FFREESLAB_ID = fFREESLAB_ID;
    }

    public String getFFREESLAB_REFNO() {
        return FFREESLAB_REFNO;
    }

    public void setFFREESLAB_REFNO(String fFREESLAB_REFNO) {
        FFREESLAB_REFNO = fFREESLAB_REFNO;
    }

    public String getFFREESLAB_QTY_F() {
        return FFREESLAB_QTY_F;
    }

    public void setFFREESLAB_QTY_F(String fFREESLAB_QTY_F) {
        FFREESLAB_QTY_F = fFREESLAB_QTY_F;
    }

    public String getFFREESLAB_QTY_T() {
        return FFREESLAB_QTY_T;
    }

    public void setFFREESLAB_QTY_T(String fFREESLAB_QTY_T) {
        FFREESLAB_QTY_T = fFREESLAB_QTY_T;
    }

    public String getFFREESLAB_FITEM_CODE() {
        return FFREESLAB_FITEM_CODE;
    }

    public void setFFREESLAB_FITEM_CODE(String fFREESLAB_FITEM_CODE) {
        FFREESLAB_FITEM_CODE = fFREESLAB_FITEM_CODE;
    }

    public String getFFREESLAB_FREE_QTY() {
        return FFREESLAB_FREE_QTY;
    }

    public void setFFREESLAB_FREE_QTY(String fFREESLAB_FREE_QTY) {
        FFREESLAB_FREE_QTY = fFREESLAB_FREE_QTY;
    }

    public String getFFREESLAB_ADD_USER() {
        return FFREESLAB_ADD_USER;
    }

    public void setFFREESLAB_ADD_USER(String fFREESLAB_ADD_USER) {
        FFREESLAB_ADD_USER = fFREESLAB_ADD_USER;
    }

    public String getFFREESLAB_ADD_DATE() {
        return FFREESLAB_ADD_DATE;
    }

    public void setFFREESLAB_ADD_DATE(String fFREESLAB_ADD_DATE) {
        FFREESLAB_ADD_DATE = fFREESLAB_ADD_DATE;
    }

    public String getFFREESLAB_ADD_MACH() {
        return FFREESLAB_ADD_MACH;
    }

    public void setFFREESLAB_ADD_MACH(String fFREESLAB_ADD_MACH) {
        FFREESLAB_ADD_MACH = fFREESLAB_ADD_MACH;
    }

    public String getFFREESLAB_RECORD_ID() {
        return FFREESLAB_RECORD_ID;
    }

    public void setFFREESLAB_RECORD_ID(String fFREESLAB_RECORD_ID) {
        FFREESLAB_RECORD_ID = fFREESLAB_RECORD_ID;
    }

    public String getFFREESLAB_TIMESTAP_COLUMN() {
        return FFREESLAB_TIMESTAP_COLUMN;
    }

    public void setFFREESLAB_TIMESTAP_COLUMN(String fFREESLAB_TIMESTAP_COLUMN) {
        FFREESLAB_TIMESTAP_COLUMN = fFREESLAB_TIMESTAP_COLUMN;
    }

    public String getFFREESLAB_SEQ_NO() {
        return FFREESLAB_SEQ_NO;
    }

    public void setFFREESLAB_SEQ_NO(String fFREESLAB_SEQ_NO) {
        FFREESLAB_SEQ_NO = fFREESLAB_SEQ_NO;
    }
    public static FreeSlab parseFreeSlab(JSONObject instance) throws JSONException {

        if (instance != null) {
            FreeSlab slab = new FreeSlab();
            slab.setFFREESLAB_FITEM_CODE(instance.getString("Fitemcode"));
            slab.setFFREESLAB_REFNO(instance.getString("Refno"));
            slab.setFFREESLAB_QTY_F(instance.getString("Qtyf"));
            slab.setFFREESLAB_QTY_T(instance.getString("Qtyt"));
            slab.setFFREESLAB_FREE_QTY(instance.getString("Freeqty"));
            return slab;
        }

        return null;
    }
}
