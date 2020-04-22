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
