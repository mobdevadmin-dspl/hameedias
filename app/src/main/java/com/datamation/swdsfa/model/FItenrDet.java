package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rashmi.
 */

public class FItenrDet {
    private String FITENRDET_ID;
    private String FITENRDET_NO_OUTLET;
    private String FITENRDET_NO_SHCUCAL;
    private String FITENRDET_RD_TARGET;
    private String FITENRDET_REF_NO;
    private String FITENRDET_REMARKS;
    private String FITENRDET_ROUTE_CODE;
    private String FITENRDET_TXN_DATE;

    public String getFITENRDET_ID() {
        return FITENRDET_ID;
    }
    public void setFITENRDET_ID(String fITENRDET_ID) {
        FITENRDET_ID = fITENRDET_ID;
    }
    public String getFITENRDET_NO_OUTLET() {
        return FITENRDET_NO_OUTLET;
    }
    public void setFITENRDET_NO_OUTLET(String fITENRDET_NO_OUTLET) {
        FITENRDET_NO_OUTLET = fITENRDET_NO_OUTLET;
    }
    public String getFITENRDET_NO_SHCUCAL() {
        return FITENRDET_NO_SHCUCAL;
    }
    public void setFITENRDET_NO_SHCUCAL(String fITENRDET_NO_SHCUCAL) {
        FITENRDET_NO_SHCUCAL = fITENRDET_NO_SHCUCAL;
    }
    public String getFITENRDET_RD_TARGET() {
        return FITENRDET_RD_TARGET;
    }
    public void setFITENRDET_RD_TARGET(String fITENRDET_RD_TARGET) {
        FITENRDET_RD_TARGET = fITENRDET_RD_TARGET;
    }
    public String getFITENRDET_REF_NO() {
        return FITENRDET_REF_NO;
    }
    public void setFITENRDET_REF_NO(String fITENRDET_REF_NO) {
        FITENRDET_REF_NO = fITENRDET_REF_NO;
    }
    public String getFITENRDET_REMARKS() {
        return FITENRDET_REMARKS;
    }
    public void setFITENRDET_REMARKS(String fITENRDET_REMARKS) {
        FITENRDET_REMARKS = fITENRDET_REMARKS;
    }
    public String getFITENRDET_ROUTE_CODE() {
        return FITENRDET_ROUTE_CODE;
    }
    public void setFITENRDET_ROUTE_CODE(String fITENRDET_ROUTE_CODE) {
        FITENRDET_ROUTE_CODE = fITENRDET_ROUTE_CODE;
    }
    public String getFITENRDET_TXN_DATE() {
        return FITENRDET_TXN_DATE;
    }
    public void setFITENRDET_TXN_DATE(String fITENRDET_TXN_DATE) {
        FITENRDET_TXN_DATE = fITENRDET_TXN_DATE;
    }
    public static FItenrDet parseIteanaryDet(JSONObject jObject) throws JSONException {

        if (jObject != null) {
            FItenrDet fItenrDet = new FItenrDet();

            fItenrDet.setFITENRDET_NO_OUTLET(jObject.getString("NoOutlet"));
            fItenrDet.setFITENRDET_NO_SHCUCAL(jObject.getString("NoShcuCal"));
            fItenrDet.setFITENRDET_REF_NO(jObject.getString("RefNo"));
            fItenrDet.setFITENRDET_REMARKS("Remarks");
            fItenrDet.setFITENRDET_ROUTE_CODE(jObject.getString("RouteCode"));
           // fItenrDet.setFITENRDET_TXN_DATE(jObject.getString("TxnDate"));
            String[] TxnDate = jObject.getString("TxnDate").split(" ");
            String[] date = TxnDate[0].split("/");
            if(Integer.parseInt(date[1])<10){
                date[1] = "0"+date[1];
            }
            if(Integer.parseInt(date[0])<10){
                date[0] = "0"+date[0];
            }
            String mofiedDate = date[2] + "-" + date[0] + "-" + date[1];
            fItenrDet.setFITENRDET_TXN_DATE(mofiedDate);
            fItenrDet.setFITENRDET_RD_TARGET(jObject.getString("RDTarget"));

            return fItenrDet;
        }

        return null;
    }

    @Override
    public String toString() {
        return "FItenrDet{" +

                ", FITENRDET_NO_OUTLET='" + FITENRDET_NO_OUTLET + '\'' +
                ", FITENRDET_NO_SHCUCAL='" + FITENRDET_NO_SHCUCAL + '\'' +
                ", FITENRDET_REF_NO='" + FITENRDET_REF_NO + '\'' +
                ", FITENRDET_ROUTE_CODE='" + FITENRDET_ROUTE_CODE + '\'' +

                '}';
    }
}
