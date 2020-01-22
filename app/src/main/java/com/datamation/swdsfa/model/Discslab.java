package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Discslab {

    private String FDISCSLAB_ID;
    private String FDISCSLAB_REF_NO;
    private String FDISCSLAB_SEQ_NO;
    private String FDISCSLAB_QTY_F;
    private String FDISCSLAB_QTY_T;
    private String FDISCSLAB_DIS_PER;
    private String FDISCSLAB_DIS_AMUT;
    private String FDISCSLAB_RECORD_ID;
    private String FDISCSLAB_TIMESTAMP_COLUMN;

    public String getFDISCSLAB_ID() {
        return FDISCSLAB_ID;
    }

    public void setFDISCSLAB_ID(String fDISCSLAB_ID) {
        FDISCSLAB_ID = fDISCSLAB_ID;
    }

    public String getFDISCSLAB_REF_NO() {
        return FDISCSLAB_REF_NO;
    }

    public void setFDISCSLAB_REF_NO(String fDISCSLAB_REF_NO) {
        FDISCSLAB_REF_NO = fDISCSLAB_REF_NO;
    }

    public String getFDISCSLAB_SEQ_NO() {
        return FDISCSLAB_SEQ_NO;
    }

    public void setFDISCSLAB_SEQ_NO(String fDISCSLAB_SEQ_NO) {
        FDISCSLAB_SEQ_NO = fDISCSLAB_SEQ_NO;
    }

    public String getFDISCSLAB_QTY_F() {
        return FDISCSLAB_QTY_F;
    }

    public void setFDISCSLAB_QTY_F(String fDISCSLAB_QTY_F) {
        FDISCSLAB_QTY_F = fDISCSLAB_QTY_F;
    }

    public String getFDISCSLAB_QTY_T() {
        return FDISCSLAB_QTY_T;
    }

    public void setFDISCSLAB_QTY_T(String fDISCSLAB_QTY_T) {
        FDISCSLAB_QTY_T = fDISCSLAB_QTY_T;
    }

    public String getFDISCSLAB_DIS_PER() {
        return FDISCSLAB_DIS_PER;
    }

    public void setFDISCSLAB_DIS_PER(String fDISCSLAB_DIS_PER) {
        FDISCSLAB_DIS_PER = fDISCSLAB_DIS_PER;
    }

    public String getFDISCSLAB_DIS_AMUT() {
        return FDISCSLAB_DIS_AMUT;
    }

    public void setFDISCSLAB_DIS_AMUT(String fDISCSLAB_DIS_AMUT) {
        FDISCSLAB_DIS_AMUT = fDISCSLAB_DIS_AMUT;
    }

    public String getFDISCSLAB_RECORD_ID() {
        return FDISCSLAB_RECORD_ID;
    }

    public void setFDISCSLAB_RECORD_ID(String fDISCSLAB_RECORD_ID) {
        FDISCSLAB_RECORD_ID = fDISCSLAB_RECORD_ID;
    }

    public String getFDISCSLAB_TIMESTAMP_COLUMN() {
        return FDISCSLAB_TIMESTAMP_COLUMN;
    }

    public void setFDISCSLAB_TIMESTAMP_COLUMN(String fDISCSLAB_TIMESTAMP_COLUMN) {
        FDISCSLAB_TIMESTAMP_COLUMN = fDISCSLAB_TIMESTAMP_COLUMN;
    }
    public static Discslab parseDiscslab(JSONObject instance) throws JSONException {

        if (instance != null) {
            Discslab discslab = new Discslab();

            discslab.setFDISCSLAB_REF_NO(instance.getString("Refno"));
            discslab.setFDISCSLAB_SEQ_NO("");
            discslab.setFDISCSLAB_QTY_F(instance.getString("Qtyf"));
            discslab.setFDISCSLAB_QTY_T(instance.getString("Qtyt"));
            discslab.setFDISCSLAB_DIS_PER(instance.getString("Disper"));
            discslab.setFDISCSLAB_DIS_AMUT(instance.getString("Disamt"));
            discslab.setFDISCSLAB_TIMESTAMP_COLUMN("");


            return discslab;
        }

        return null;
    }
}
