package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class SalRep {

    @SerializedName("EMAIL")
    private String EMAIL;
    @SerializedName("Password")
    private String PASSWORD;
    @SerializedName("RepCode")
    private String RepCode;
    @SerializedName("RepName")
    private String NAME;
    @SerializedName("RepPrefix")
    private String PREFIX;
    @SerializedName("firebaseTokenID")
    private String firebaseTokenID;
    @SerializedName("macid")
    private String MACID;
    @SerializedName("CurrentVanLoc")
    private String CurrentVanLoc;
    @SerializedName("RepType")
    private String RepType;

    public String getRepType() {
        return RepType;
    }

    public void setRepType(String repType) {
        RepType = repType;
    }

    public String getCurrentVanLoc() {
        return CurrentVanLoc;
    }

    public void setCurrentVanLoc(String currentVanLoc) {
        CurrentVanLoc = currentVanLoc;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getMACID() {
        return MACID;
    }

    public void setMACID(String MACID) {
        this.MACID = MACID;
    }

    public String getEMAIL() {

        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getRepCode() {
        return RepCode;
    }

    public void setRepCode(String repCode) {
        RepCode = repCode;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String nAME) {
        NAME = nAME;
    }

    public String getPREFIX() {
        return PREFIX;
    }

    public void setPREFIX(String pREFIX) {
        PREFIX = pREFIX;
    }


    public String getFirebaseTokenID() {
        return firebaseTokenID;
    }

    public void setFirebaseTokenID(String firebaseTokenID) {
        this.firebaseTokenID = firebaseTokenID;
    }

    public static SalRep parseSalRep(JSONObject instance) throws JSONException {

        if (instance != null) {
            SalRep salRep = new SalRep();

            salRep.setRepCode(instance.getString("RepCode").trim());
            salRep.setNAME(instance.getString("RepName").trim());
            salRep.setPREFIX(instance.getString("RepPrefix").trim());
            salRep.setCurrentVanLoc(instance.getString("CurrentVanLoc").trim());
            salRep.setEMAIL(instance.getString("EMAIL").trim());
            salRep.setPASSWORD(instance.getString("Password").trim());
            salRep.setRepType(instance.getString("RepType").trim());
            salRep.setFirebaseTokenID(instance.getString("firebaseTokenID").trim());
            salRep.setMACID(instance.getString("macid").trim());

            return salRep;
        }
        return null;
    }



    @Override
    public String toString() {
        return "SalRep{" +
                ", NAME='" + NAME + '\'' +
                ", PREFIX='" + PREFIX + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", MACID='" + MACID + '\'' +
                ", REPCODE='" + RepCode + '\'' +
                ", FirebaseTokenID='" + firebaseTokenID + '\'' +
                '}';
    }

}
