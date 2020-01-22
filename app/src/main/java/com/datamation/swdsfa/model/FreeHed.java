package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FreeHed {

    private String FFREEHED_ID;
    private String FFREEHED_REFNO;
    private String FFREEHED_TXNDATE;
    private String FFREEHED_DISC_DESC;
    private String FFREEHED_PRIORITY;
    private String FFREEHED_VDATEF;
    private String FFREEHED_VDATET;
    private String FFREEHED_REMARKS;
    private String FFREEHED_RECORD_ID;
    private String FFREEHED_ITEM_QTY;
    private String FFREEHED_FREE_IT_QTY;
    private String FFREEHED_FTYPE;
    private String FFREEHED_COSTCODE;
    private String ITEMQTY;

    public String getITEMQTY() {
        return ITEMQTY;
    }

    public void setITEMQTY(String ITEMQTY) {
        this.ITEMQTY = ITEMQTY;
    }

    public String getFFREEHED_ID() {
        return FFREEHED_ID;
    }

    public void setFFREEHED_ID(String fFREEHED_ID) {
        FFREEHED_ID = fFREEHED_ID;
    }

    public String getFFREEHED_REFNO() {
        return FFREEHED_REFNO;
    }

    public void setFFREEHED_REFNO(String fFREEHED_REFNO) {
        FFREEHED_REFNO = fFREEHED_REFNO;
    }

    public String getFFREEHED_TXNDATE() {
        return FFREEHED_TXNDATE;
    }

    public void setFFREEHED_TXNDATE(String fFREEHED_TXNDATE) {
        FFREEHED_TXNDATE = fFREEHED_TXNDATE;
    }

    public String getFFREEHED_DISC_DESC() {
        return FFREEHED_DISC_DESC;
    }

    public void setFFREEHED_DISC_DESC(String fFREEHED_DISC_DESC) {
        FFREEHED_DISC_DESC = fFREEHED_DISC_DESC;
    }

    public String getFFREEHED_PRIORITY() {
        return FFREEHED_PRIORITY;
    }

    public void setFFREEHED_PRIORITY(String fFREEHED_PRIORITY) {
        FFREEHED_PRIORITY = fFREEHED_PRIORITY;
    }

    public String getFFREEHED_VDATEF() {
        return FFREEHED_VDATEF;
    }

    public void setFFREEHED_VDATEF(String fFREEHED_VDATEF) {
        FFREEHED_VDATEF = fFREEHED_VDATEF;
    }

    public String getFFREEHED_VDATET() {
        return FFREEHED_VDATET;
    }

    public void setFFREEHED_VDATET(String fFREEHED_VDATET) {
        FFREEHED_VDATET = fFREEHED_VDATET;
    }

    public String getFFREEHED_REMARKS() {
        return FFREEHED_REMARKS;
    }

    public void setFFREEHED_REMARKS(String fFREEHED_REMARKS) {
        FFREEHED_REMARKS = fFREEHED_REMARKS;
    }

    public String getFFREEHED_RECORD_ID() {
        return FFREEHED_RECORD_ID;
    }

    public void setFFREEHED_RECORD_ID(String fFREEHED_RECORD_ID) {
        FFREEHED_RECORD_ID = fFREEHED_RECORD_ID;
    }

    public String getFFREEHED_ITEM_QTY() {
        return FFREEHED_ITEM_QTY;
    }

    public void setFFREEHED_ITEM_QTY(String fFREEHED_ITEM_QTY) {
        FFREEHED_ITEM_QTY = fFREEHED_ITEM_QTY;
    }

    public String getFFREEHED_FREE_IT_QTY() {
        return FFREEHED_FREE_IT_QTY;
    }

    public void setFFREEHED_FREE_IT_QTY(String fFREEHED_FREE_IT_QTY) {
        FFREEHED_FREE_IT_QTY = fFREEHED_FREE_IT_QTY;
    }

    public String getFFREEHED_FTYPE() {
        return FFREEHED_FTYPE;
    }

    public void setFFREEHED_FTYPE(String fFREEHED_FTYPE) {
        FFREEHED_FTYPE = fFREEHED_FTYPE;
    }

    public String getFFREEHED_COSTCODE() {
        return FFREEHED_COSTCODE;
    }

    public void setFFREEHED_COSTCODE(String fFREEHED_COSTCODE) {
        FFREEHED_COSTCODE = fFREEHED_COSTCODE;
    }

    public static FreeHed parseFreeHed(JSONObject instance) throws JSONException {

        if (instance != null) {
            FreeHed hed = new FreeHed();

            hed.setFFREEHED_REFNO(instance.getString("Refno"));
            hed.setFFREEHED_TXNDATE(instance.getString("Txndate"));
            hed.setFFREEHED_DISC_DESC(instance.getString("DiscDesc"));
            hed.setFFREEHED_PRIORITY(instance.getString("Priority"));
            hed.setFFREEHED_VDATEF(instance.getString("Vdatef"));
            hed.setFFREEHED_VDATET(instance.getString("Vdatet"));
            hed.setFFREEHED_REMARKS(instance.getString("Remarks"));
            hed.setFFREEHED_ITEM_QTY(instance.getString("ItemQty"));
            hed.setFFREEHED_FREE_IT_QTY(instance.getString("FreeItQty"));
            hed.setFFREEHED_FTYPE(instance.getString("Ftype"));
            hed.setFFREEHED_COSTCODE(instance.getString("CostCode"));

            return hed;
        }

        return null;
    }

    @Override
    public String toString() {
        return "FreeHed{" +
                "FFREEHED_REFNO='" + FFREEHED_REFNO + '\'' +
                ", FFREEHED_PRIORITY='" + FFREEHED_PRIORITY + '\'' +
                ", ITEMQTY='" + ITEMQTY + '\'' +
                '}';
    }
}
