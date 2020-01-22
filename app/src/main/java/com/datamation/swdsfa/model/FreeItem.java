package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FreeItem {

    private String FFREEITEM_ID;
    private String FFREEITEM_REFNO;
    private String FFREEITEM_ITEMCODE;
    private String FFREEITEM_RECORD_ID;

    public String getFFREEITEM_ID() {
        return FFREEITEM_ID;
    }

    public void setFFREEITEM_ID(String fFREEITEM_ID) {
        FFREEITEM_ID = fFREEITEM_ID;
    }

    public String getFFREEITEM_REFNO() {
        return FFREEITEM_REFNO;
    }

    public void setFFREEITEM_REFNO(String fFREEITEM_REFNO) {
        FFREEITEM_REFNO = fFREEITEM_REFNO;
    }

    public String getFFREEITEM_ITEMCODE() {
        return FFREEITEM_ITEMCODE;
    }

    public void setFFREEITEM_ITEMCODE(String fFREEITEM_ITEMCODE) {
        FFREEITEM_ITEMCODE = fFREEITEM_ITEMCODE;
    }

    public String getFFREEITEM_RECORD_ID() {
        return FFREEITEM_RECORD_ID;
    }

    public void setFFREEITEM_RECORD_ID(String fFREEITEM_RECORD_ID) {
        FFREEITEM_RECORD_ID = fFREEITEM_RECORD_ID;
    }
    public static FreeItem parseFreeItem(JSONObject instance) throws JSONException {

        if (instance != null) {
            FreeItem freeItem = new FreeItem();

            freeItem.setFFREEITEM_ITEMCODE(instance.getString("Itemcode"));
            freeItem.setFFREEITEM_REFNO(instance.getString("Refno"));

            return freeItem;
        }

        return null;
    }

}
