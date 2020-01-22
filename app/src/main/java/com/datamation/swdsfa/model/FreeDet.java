package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FreeDet {

    private String FFREEDET_ID;
    private String FFREEDET_REFNO;
    private String FFREEDET_ITEM_CODE;
    private String FFREEDET_RECORD_ID;

    public String getFFREEDET_ID() {
        return FFREEDET_ID;
    }

    public void setFFREEDET_ID(String fFREEDET_ID) {
        FFREEDET_ID = fFREEDET_ID;
    }

    public String getFFREEDET_REFNO() {
        return FFREEDET_REFNO;
    }

    public void setFFREEDET_REFNO(String fFREEDET_REFNO) {
        FFREEDET_REFNO = fFREEDET_REFNO;
    }

    public String getFFREEDET_ITEM_CODE() {
        return FFREEDET_ITEM_CODE;
    }

    public void setFFREEDET_ITEM_CODE(String fFREEDET_ITEM_CODE) {
        FFREEDET_ITEM_CODE = fFREEDET_ITEM_CODE;
    }

    public String getFFREEDET_RECORD_ID() {
        return FFREEDET_RECORD_ID;
    }

    public void setFFREEDET_RECORD_ID(String fFREEDET_RECORD_ID) {
        FFREEDET_RECORD_ID = fFREEDET_RECORD_ID;
    }
    public static FreeDet parseFreeDet(JSONObject instance) throws JSONException {

        if (instance != null) {
            FreeDet det = new FreeDet();

            det.setFFREEDET_REFNO(instance.getString("Refno"));
            det.setFFREEDET_ITEM_CODE(instance.getString("Itemcode"));
            return det;
        }

        return null;
    }

}
