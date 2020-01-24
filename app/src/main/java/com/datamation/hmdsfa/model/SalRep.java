package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class SalRep {

    @SerializedName("AddMach")
    private String ADDMACH;
    @SerializedName("AddUser")
    private String ADDUSER;
    @SerializedName("CONSOLE_DB")
    private String CONSOLE_DB;
    @SerializedName("DIST_DB")
    private String DIST_DB;
    @SerializedName("DealCode")
    private String DEALCODE;
    @SerializedName("EMAIL")
    private String EMAIL;
    @SerializedName("IsApplyQOHexdVldtn")
    private String IsApplyQOHexdVldtn;
    @SerializedName("LocCode")
    private String LOCCODE;
    @SerializedName("Password")
    private String PASSWORD;
    @SerializedName("RecordId")
    private String REPID;
    @SerializedName("RepCode")
    private String RepCode;
    @SerializedName("RepIdNo")
    private String REPIDNO;
    @SerializedName("RepMobil")
    private String MOBILE;
    @SerializedName("RepName")
    private String NAME;
    @SerializedName("RepPrefix")
    private String PREFIX;
    @SerializedName("RepTCode")
    private String REPTCODE;
    @SerializedName("RepTele")
    private String TELE;
    @SerializedName("Status")
    private String STATUS;
    @SerializedName("firebaseTokenID")
    private String firebaseTokenID;
    @SerializedName("macid")
    private String MACID;
    @SerializedName("isZeroQOHAllow")
    private String IS_ZERO_QOH_ALLOW;

    private String AREACODE;
    private String ISSYNC;

    public String getREPIDNO() {
        return REPIDNO;
    }

    public void setREPIDNO(String REPIDNO) {
        this.REPIDNO = REPIDNO;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getIsApplyQOHexdVldtn() {
        return IsApplyQOHexdVldtn;
    }

    public void setIsApplyQOHexdVldtn(String isApplyQOHexdVldtn) {
        IsApplyQOHexdVldtn = isApplyQOHexdVldtn;
    }

    public String getCONSOLE_DB() {
        return CONSOLE_DB;
    }

    public void setCONSOLE_DB(String CONSOLE_DB) {
        this.CONSOLE_DB = CONSOLE_DB;
    }

    public String getDIST_DB() {
        return DIST_DB;
    }

    public void setDIST_DB(String DIST_DB) {
        this.DIST_DB = DIST_DB;
    }

    public String getREPTCODE() {
        return REPTCODE;
    }

    public void setREPTCODE(String REPTCODE) {
        this.REPTCODE = REPTCODE;
    }

    public String getISSYNC() {
        return ISSYNC;
    }

    public void setISSYNC(String ISSYNC) {
        this.ISSYNC = ISSYNC;
    }

    public String getMACID() {
        return MACID;
    }

    public void setMACID(String MACID) {
        this.MACID = MACID;
    }

    public String getDEALCODE() {
        return DEALCODE;
    }

    public void setDEALCODE(String DEALCODE) {
        this.DEALCODE = DEALCODE;
    }

    public String getAREACODE() {
        return AREACODE;
    }

    public void setAREACODE(String AREACODE) {
        this.AREACODE = AREACODE;
    }

    public String getEMAIL() {

        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getLOCCODE() {
        return LOCCODE;
    }

    public void setLOCCODE(String LOCCODE) {
        this.LOCCODE = LOCCODE;
    }


    public String getADDMACH() {
        return ADDMACH;
    }

    public void setADDMACH(String aDDMACH) {
        ADDMACH = aDDMACH;
    }

    public String getADDUSER() {
        return ADDUSER;
    }

    public void setADDUSER(String aDDUSER) {
        ADDUSER = aDDUSER;
    }

    public String getRepCode() {
        return RepCode;
    }

    public void setRepCode(String repCode) {
        RepCode = repCode;
    }

    public String getREPID() {
        return REPID;
    }

    public void setREPID(String rEPID) {
        REPID = rEPID;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String mOBILE) {
        MOBILE = mOBILE;
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

    public String getTELE() {
        return TELE;
    }

    public void setTELE(String tELE) {
        TELE = tELE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String sTATUS) {
        STATUS = sTATUS;
    }

    public String getIS_ZERO_QOH_ALLOW() {
        return IS_ZERO_QOH_ALLOW;
    }

    public void setIS_ZERO_QOH_ALLOW(String IS_ZERO_QOH_ALLOW) {
        this.IS_ZERO_QOH_ALLOW = IS_ZERO_QOH_ALLOW;
    }

    public String getFirebaseTokenID() {
        return firebaseTokenID;
    }

    public void setFirebaseTokenID(String firebaseTokenID) {
        this.firebaseTokenID = firebaseTokenID;
    }

    public static SalRep parseUser(JSONObject instance) throws JSONException, NumberFormatException {

        if (instance != null) {
            SalRep user = new SalRep();
            user.setRepCode(instance.getString("RepCode"));
            user.setNAME(instance.getString("RepName"));
            user.setREPID(instance.getString("RepIdNo"));
            user.setADDMACH(instance.getString("AddMach"));
            user.setADDUSER(instance.getString("AddUser"));
            user.setMOBILE(instance.getString("RepMobil"));
            user.setLOCCODE(instance.getString("LocCode").trim());
            user.setDEALCODE(instance.getString("DealCode").trim());
            user.setSTATUS(instance.getString("Status"));
            user.setPREFIX(instance.getString("RepPrefix"));
            user.setTELE(instance.getString("RepTele"));
            user.setEMAIL(instance.getString("EMAIL"));
            user.setMACID(instance.getString("macid"));
            user.setREPTCODE(instance.getString("RepTCode"));
            user.setIS_ZERO_QOH_ALLOW(instance.getString("isZeroQOHAllow"));
            user.setFirebaseTokenID(instance.getString("firebaseTokenID"));
            user.setIsApplyQOHexdVldtn(instance.getString("IsApplyQOHexdVldtn"));

            return user;
        }

        return null;
    }

    @Override
    public String toString() {
        return "SalRep{" +
                "CONSOLE_DB='" + CONSOLE_DB + '\'' +
                ", DIST_DB='" + DIST_DB + '\'' +
                ", NAME='" + NAME + '\'' +
                ", REPID='" + REPID + '\'' +
                ", ADDMACH='" + ADDMACH + '\'' +
                ", ADDUSER='" + ADDUSER + '\'' +
                ", MOBILE='" + MOBILE + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", PREFIX='" + PREFIX + '\'' +
                ", TELE='" + TELE + '\'' +
                ", LOCCODE='" + LOCCODE + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", AREACODE='" + AREACODE + '\'' +
                ", DEALCODE='" + DEALCODE + '\'' +
                ", MACID='" + MACID + '\'' +
                ", ISSYNC='" + ISSYNC + '\'' +
                ", REPTCODE='" + REPTCODE + '\'' +
                ", REPCODE='" + RepCode + '\'' +
                ", FirebaseTokenID='" + firebaseTokenID + '\'' +

                '}';
    }

}
