package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemLoc
{
    private String FITEMLOC_ID;
    private String FITEMLOC_ITEM_CODE;
    private String FITEMLOC_LOC_CODE;
    private String FITEMLOC_QOH;
    private String FITEMLOC_RECORD_ID;

    public String getFITEMLOC_ID() {
        return FITEMLOC_ID;
    }

    public void setFITEMLOC_ID(String fITEMLOC_ID) {
        FITEMLOC_ID = fITEMLOC_ID;
    }

    public String getFITEMLOC_ITEM_CODE() {
        return FITEMLOC_ITEM_CODE;
    }

    public void setFITEMLOC_ITEM_CODE(String fITEMLOC_ITEM_CODE) {
        FITEMLOC_ITEM_CODE = fITEMLOC_ITEM_CODE;
    }

    public String getFITEMLOC_LOC_CODE() {
        return FITEMLOC_LOC_CODE;
    }

    public void setFITEMLOC_LOC_CODE(String fITEMLOC_LOC_CODE) {
        FITEMLOC_LOC_CODE = fITEMLOC_LOC_CODE;
    }

    public String getFITEMLOC_QOH() {
        return FITEMLOC_QOH;
    }

    public void setFITEMLOC_QOH(String fITEMLOC_QOH) {
        FITEMLOC_QOH = fITEMLOC_QOH;
    }

    public String getFITEMLOC_RECORD_ID() {
        return FITEMLOC_RECORD_ID;
    }

    public void setFITEMLOC_RECORD_ID(String fITEMLOC_RECORD_ID) {
        FITEMLOC_RECORD_ID = fITEMLOC_RECORD_ID;
    }
    public static ItemLoc parseItemLocs(JSONObject instance) throws JSONException {

        if (instance != null) {
            ItemLoc loc = new ItemLoc();
            loc.setFITEMLOC_ITEM_CODE(instance.getString("ItemCode"));
            loc.setFITEMLOC_LOC_CODE(instance.getString("LocCode"));
            loc.setFITEMLOC_QOH(instance.getString("QOH"));
            loc.setFITEMLOC_RECORD_ID(instance.getString("RecordId"));
            return loc;
        }

        return null;
    }
}
