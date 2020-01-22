package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FreeMslab {

    private String FFREEMSLAB_ID;
    private String FFREEMSLAB_REFNO;
    private String FFREEMSLAB_QTY_F;
    private String FFREEMSLAB_QTY_T;
    private String FFREEMSLAB_ITEM_QTY;
    private String FFREEMSLAB_FREE_IT_QTY;
    private String FFREEMSLAB_ADD_USER;
    private String FFREEMSLAB_ADD_DATE;
    private String FFREEMSLAB_ADD_MACH;
    private String FFREEMSLAB_RECORD_ID;
    private String FFREEMSLAB_TIMESTAMP_COLUMN;
    private String FFREEMSLAB_SEQ_NO;

    public String getFFREEMSLAB_ID() {
        return FFREEMSLAB_ID;
    }

    public void setFFREEMSLAB_ID(String fFREEMSLAB_ID) {
        FFREEMSLAB_ID = fFREEMSLAB_ID;
    }

    public String getFFREEMSLAB_REFNO() {
        return FFREEMSLAB_REFNO;
    }

    public void setFFREEMSLAB_REFNO(String fFREEMSLAB_REFNO) {
        FFREEMSLAB_REFNO = fFREEMSLAB_REFNO;
    }

    public String getFFREEMSLAB_QTY_F() {
        return FFREEMSLAB_QTY_F;
    }

    public void setFFREEMSLAB_QTY_F(String fFREEMSLAB_QTY_F) {
        FFREEMSLAB_QTY_F = fFREEMSLAB_QTY_F;
    }

    public String getFFREEMSLAB_QTY_T() {
        return FFREEMSLAB_QTY_T;
    }

    public void setFFREEMSLAB_QTY_T(String fFREEMSLAB_QTY_T) {
        FFREEMSLAB_QTY_T = fFREEMSLAB_QTY_T;
    }

    public String getFFREEMSLAB_ITEM_QTY() {
        return FFREEMSLAB_ITEM_QTY;
    }

    public void setFFREEMSLAB_ITEM_QTY(String fFREEMSLAB_ITEM_QTY) {
        FFREEMSLAB_ITEM_QTY = fFREEMSLAB_ITEM_QTY;
    }

    public String getFFREEMSLAB_FREE_IT_QTY() {
        return FFREEMSLAB_FREE_IT_QTY;
    }

    public void setFFREEMSLAB_FREE_IT_QTY(String fFREEMSLAB_FREE_IT_QTY) {
        FFREEMSLAB_FREE_IT_QTY = fFREEMSLAB_FREE_IT_QTY;
    }

    public String getFFREEMSLAB_ADD_USER() {
        return FFREEMSLAB_ADD_USER;
    }

    public void setFFREEMSLAB_ADD_USER(String fFREEMSLAB_ADD_USER) {
        FFREEMSLAB_ADD_USER = fFREEMSLAB_ADD_USER;
    }

    public String getFFREEMSLAB_ADD_DATE() {
        return FFREEMSLAB_ADD_DATE;
    }

    public void setFFREEMSLAB_ADD_DATE(String fFREEMSLAB_ADD_DATE) {
        FFREEMSLAB_ADD_DATE = fFREEMSLAB_ADD_DATE;
    }

    public String getFFREEMSLAB_ADD_MACH() {
        return FFREEMSLAB_ADD_MACH;
    }

    public void setFFREEMSLAB_ADD_MACH(String fFREEMSLAB_ADD_MACH) {
        FFREEMSLAB_ADD_MACH = fFREEMSLAB_ADD_MACH;
    }

    public String getFFREEMSLAB_RECORD_ID() {
        return FFREEMSLAB_RECORD_ID;
    }

    public void setFFREEMSLAB_RECORD_ID(String fFREEMSLAB_RECORD_ID) {
        FFREEMSLAB_RECORD_ID = fFREEMSLAB_RECORD_ID;
    }

    public String getFFREEMSLAB_TIMESTAMP_COLUMN() {
        return FFREEMSLAB_TIMESTAMP_COLUMN;
    }

    public void setFFREEMSLAB_TIMESTAMP_COLUMN(String fFREEMSLAB_TIMESTAMP_COLUMN) {
        FFREEMSLAB_TIMESTAMP_COLUMN = fFREEMSLAB_TIMESTAMP_COLUMN;
    }

    public String getFFREEMSLAB_SEQ_NO() {
        return FFREEMSLAB_SEQ_NO;
    }

    public void setFFREEMSLAB_SEQ_NO(String fFREEMSLAB_SEQ_NO) {
        FFREEMSLAB_SEQ_NO = fFREEMSLAB_SEQ_NO;
    }
    public static FreeMslab parseFreeMslab(JSONObject instance) throws JSONException {

        if (instance != null) {
            FreeMslab freeMslab = new FreeMslab();

                        freeMslab.setFFREEMSLAB_REFNO(instance.getString("Refno"));
                        freeMslab.setFFREEMSLAB_QTY_F(instance.getString("Qtyf"));
                        freeMslab.setFFREEMSLAB_QTY_T(instance.getString("Qtyt"));
                        freeMslab.setFFREEMSLAB_ITEM_QTY(instance.getString("ItemQty"));
                        freeMslab.setFFREEMSLAB_FREE_IT_QTY(instance.getString("FreeItQty"));
                        freeMslab.setFFREEMSLAB_ADD_USER(instance.getString("AddUser"));
                        freeMslab.setFFREEMSLAB_ADD_DATE(instance.getString("AddDate"));
                        freeMslab.setFFREEMSLAB_ADD_MACH(instance.getString("AddMach"));
                        freeMslab.setFFREEMSLAB_SEQ_NO(instance.getString("Seqno"));

            return freeMslab;
        }

        return null;
    }
}
