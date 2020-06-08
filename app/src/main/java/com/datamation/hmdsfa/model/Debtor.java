package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Debtor {



    @SerializedName("AreaCode")
    private String FDEBTOR_AREA_CODE;
    @SerializedName("CrdLimit")
    private String FDEBTOR_CRD_LIMIT;
    @SerializedName("CrdPeriod")
    private String FDEBTOR_CRD_PERIOD;
    @SerializedName("DbGrCode")
    private String FDEBTOR_DBGR_CODE;
    @SerializedName("DebAdd1")
    private String FDEBTOR_ADD1;
    @SerializedName("DebAdd2")
    private String FDEBTOR_ADD2;
    @SerializedName("DebAdd3")
    private String FDEBTOR_ADD3;
    @SerializedName("DebCode")
    private String FDEBTOR_CODE;
    @SerializedName("DebEMail")
    private String FDEBTOR_EMAIL;
    @SerializedName("DebMob")
    private String FDEBTOR_MOB;
    @SerializedName("DebName")
    private String FDEBTOR_NAME;
    @SerializedName("DebTele")
    private String FDEBTOR_TELE;
    @SerializedName("PrilCode")
    private String FDEBTOR_PRILLCODE;
    @SerializedName("RankCode")
    private String FDEBTOR_RANK_CODE;
    @SerializedName("Status")
    private String FDEBTOR_STATUS;
    @SerializedName("TaxReg")
    private String FDEBTOR_TAX_REG;
    @SerializedName("DebType")
    private String FDEBTOR_TYPE;
    @SerializedName("RepCode")
    private String FDEBTOR_REPCODE;
    @SerializedName("Latitude")
    private String FDEBTOR_LATITUDE;
    @SerializedName("Longitude")
    private String FDEBTOR_LONGITUDE;
    @SerializedName("ImgURL")
    private String FDEBTOR_IMG_URL;
    public static Debtor parseOutlet(JSONObject instance) throws JSONException {

        if (instance != null) {
            Debtor aDebtor = new Debtor();
            aDebtor.setFDEBTOR_ADD1(instance.getString("DebAdd1"));
            aDebtor.setFDEBTOR_ADD2(instance.getString("DebAdd2"));
            aDebtor.setFDEBTOR_ADD3(instance.getString("DebAdd3"));
            aDebtor.setFDEBTOR_AREA_CODE(instance.getString("AreaCode"));
            aDebtor.setFDEBTOR_CODE(instance.getString("DebCode"));
            aDebtor.setFDEBTOR_CRD_LIMIT(instance.getString("CrdLimit"));
            aDebtor.setFDEBTOR_CRD_PERIOD(instance.getString("CrdPeriod"));
            aDebtor.setFDEBTOR_DBGR_CODE(instance.getString("DbGrCode"));
            aDebtor.setFDEBTOR_EMAIL(instance.getString("DebEMail"));
            aDebtor.setFDEBTOR_MOB(instance.getString("DebMob"));
            aDebtor.setFDEBTOR_NAME(instance.getString("DebName"));
            aDebtor.setFDEBTOR_PRILLCODE(instance.getString("PrilCode"));
            aDebtor.setFDEBTOR_RANK_CODE(instance.getString("RankCode"));
            aDebtor.setFDEBTOR_STATUS(instance.getString("Status"));
            aDebtor.setFDEBTOR_TAX_REG(instance.getString("TaxReg"));
            aDebtor.setFDEBTOR_TELE(instance.getString("DebTele"));
            aDebtor.setFDEBTOR_REPCODE(instance.getString("RepCode"));
            aDebtor.setFDEBTOR_LATITUDE(instance.getString("Latitude"));
            aDebtor.setFDEBTOR_LONGITUDE(instance.getString("Longitude"));
            aDebtor.setFDEBTOR_IMG_URL(instance.getString("ImgURL"));

//            aDebtor.setFDEBTOR_IMG_URL(instance.getString("FDEBTOR_IMG_URL"));

            return aDebtor;
        }

        return null;
    }
//    public String DISTRIBUTE_DB;
//    public String CONSOLE_DB;
//    private  String  FDEBTOR_COSTCODE;
//    private String FDEBTOR_ADD_MACH;
//    private String FDEBTOR_ADD_USER;
//    private String FDEBTOR_ID;
//    private String FDEBTOR_CREATEDATE;
//    private String FDEBTOR_REM_DIS;
//    private String FDEBTOR_DEB_CAT_CODE;
//    private String FDEBTOR_DEB_CLS_CODE;
//    private String FDEBTOR_LYLTY;
//    private String FDEBTOR_DEAL_CODE;
//    private String FDEBTOR_ADD_DATE_DEB;
//    private String FDEBTOR_RECORD_ID;
//    private String FDEBTOR_TIME_STAMP;
//    private String FDEBTOR_TRAN_DATE;
//    private String FDEBTOR_TRAN_BATCH;
//    private String FDEBTOR_SUMMARY;
//    private String FDEBTOR_OUT_DIS;
//    private String FDEBTOR_DEB_FAX;
//    private String FDEBTOR_DEB_WEB;
//    private String FDEBTOR_DEBCT_NAM;
//    private String FDEBTOR_DEBCT_ADD1;
//    private String FDEBTOR_DEBCT_ADD2;
//    private String FDEBTOR_DEBCT_ADD3;
//    private String FDEBTOR_DEBCT_TELE;
//    private String FDEBTOR_DEBCT_FAX;
//    private String FDEBTOR_DEBCT_EMAIL;
//    private String FDEBTOR_DEL_PERSN;
//    private String FDEBTOR_DEL_ADD1;
//    private String FDEBTOR_DEL_ADD2;
//    private String FDEBTOR_DEL_ADD3;
//    private String FDEBTOR_DEL_TELE;
//    private String FDEBTOR_DEL_FAX;
//    private String FDEBTOR_DEL_EMAIL;
//    private String FDEBTOR_DATE_OFB;
//    private String FDEBTOR_CUSDISPER;
//    private String FDEBTOR_CUSDISSTAT;
//    private String FDEBTOR_BUS_RGNO;
//    private String FDEBTOR_POSTCODE;
//    private String FDEBTOR_GEN_REMARKS;
//    private String FDEBTOR_BRANCODE;
//    private String FDEBTOR_BANK;
//    private String FDEBTOR_BRANCH;
//    private String FDEBTOR_ACCTNO;
//    private String FDEBTOR_CUS_VATNO;
//    private String FDEBTOR_NIC;
//    private String FDEBTOR_BIS_REG;
//    private String FDEBTOR_IS_SYNC;
//    private String FDEBTOR_IS_CORDINATE_UPDATE;


    public String getFDEBTOR_AREA_CODE() {
        return FDEBTOR_AREA_CODE;
    }

    public void setFDEBTOR_AREA_CODE(String FDEBTOR_AREA_CODE) {
        this.FDEBTOR_AREA_CODE = FDEBTOR_AREA_CODE;
    }

    public String getFDEBTOR_CRD_LIMIT() {
        return FDEBTOR_CRD_LIMIT;
    }

    public void setFDEBTOR_CRD_LIMIT(String FDEBTOR_CRD_LIMIT) {
        this.FDEBTOR_CRD_LIMIT = FDEBTOR_CRD_LIMIT;
    }

    public String getFDEBTOR_CRD_PERIOD() {
        return FDEBTOR_CRD_PERIOD;
    }

    public void setFDEBTOR_CRD_PERIOD(String FDEBTOR_CRD_PERIOD) {
        this.FDEBTOR_CRD_PERIOD = FDEBTOR_CRD_PERIOD;
    }

    public String getFDEBTOR_DBGR_CODE() {
        return FDEBTOR_DBGR_CODE;
    }

    public void setFDEBTOR_DBGR_CODE(String FDEBTOR_DBGR_CODE) {
        this.FDEBTOR_DBGR_CODE = FDEBTOR_DBGR_CODE;
    }

    public String getFDEBTOR_ADD1() {
        return FDEBTOR_ADD1;
    }

    public void setFDEBTOR_ADD1(String FDEBTOR_ADD1) {
        this.FDEBTOR_ADD1 = FDEBTOR_ADD1;
    }

    public String getFDEBTOR_ADD2() {
        return FDEBTOR_ADD2;
    }

    public void setFDEBTOR_ADD2(String FDEBTOR_ADD2) {
        this.FDEBTOR_ADD2 = FDEBTOR_ADD2;
    }

    public String getFDEBTOR_ADD3() {
        return FDEBTOR_ADD3;
    }

    public void setFDEBTOR_ADD3(String FDEBTOR_ADD3) {
        this.FDEBTOR_ADD3 = FDEBTOR_ADD3;
    }

    public String getFDEBTOR_CODE() {
        return FDEBTOR_CODE;
    }

    public void setFDEBTOR_CODE(String FDEBTOR_CODE) {
        this.FDEBTOR_CODE = FDEBTOR_CODE;
    }

    public String getFDEBTOR_EMAIL() {
        return FDEBTOR_EMAIL;
    }

    public void setFDEBTOR_EMAIL(String FDEBTOR_EMAIL) {
        this.FDEBTOR_EMAIL = FDEBTOR_EMAIL;
    }

    public String getFDEBTOR_MOB() {
        return FDEBTOR_MOB;
    }

    public void setFDEBTOR_MOB(String FDEBTOR_MOB) {
        this.FDEBTOR_MOB = FDEBTOR_MOB;
    }

    public String getFDEBTOR_NAME() {
        return FDEBTOR_NAME;
    }

    public void setFDEBTOR_NAME(String FDEBTOR_NAME) {
        this.FDEBTOR_NAME = FDEBTOR_NAME;
    }

    public String getFDEBTOR_TELE() {
        return FDEBTOR_TELE;
    }

    public void setFDEBTOR_TELE(String FDEBTOR_TELE) {
        this.FDEBTOR_TELE = FDEBTOR_TELE;
    }

    public String getFDEBTOR_PRILLCODE() {
        return FDEBTOR_PRILLCODE;
    }

    public void setFDEBTOR_PRILLCODE(String FDEBTOR_PRILLCODE) {
        this.FDEBTOR_PRILLCODE = FDEBTOR_PRILLCODE;
    }

    public String getFDEBTOR_RANK_CODE() {
        return FDEBTOR_RANK_CODE;
    }

    public void setFDEBTOR_RANK_CODE(String FDEBTOR_RANK_CODE) {
        this.FDEBTOR_RANK_CODE = FDEBTOR_RANK_CODE;
    }

    public String getFDEBTOR_STATUS() {
        return FDEBTOR_STATUS;
    }

    public void setFDEBTOR_STATUS(String FDEBTOR_STATUS) {
        this.FDEBTOR_STATUS = FDEBTOR_STATUS;
    }

    public String getFDEBTOR_TAX_REG() {
        return FDEBTOR_TAX_REG;
    }

    public void setFDEBTOR_TAX_REG(String FDEBTOR_TAX_REG) {
        this.FDEBTOR_TAX_REG = FDEBTOR_TAX_REG;
    }

    public String getFDEBTOR_TYPE() {
        return FDEBTOR_TYPE;
    }

    public void setFDEBTOR_TYPE(String FDEBTOR_TYPE) {
        this.FDEBTOR_TYPE = FDEBTOR_TYPE;
    }

    public String getFDEBTOR_REPCODE() {
        return FDEBTOR_REPCODE;
    }

    public void setFDEBTOR_REPCODE(String FDEBTOR_REPCODE) {
        this.FDEBTOR_REPCODE = FDEBTOR_REPCODE;
    }

    public String getFDEBTOR_LATITUDE() {
        return FDEBTOR_LATITUDE;
    }

    public void setFDEBTOR_LATITUDE(String FDEBTOR_LATITUDE) {
        this.FDEBTOR_LATITUDE = FDEBTOR_LATITUDE;
    }

    public String getFDEBTOR_LONGITUDE() {
        return FDEBTOR_LONGITUDE;
    }

    public void setFDEBTOR_LONGITUDE(String FDEBTOR_LONGITUDE) {
        this.FDEBTOR_LONGITUDE = FDEBTOR_LONGITUDE;
    }

    public String getFDEBTOR_IMG_URL() {
        return FDEBTOR_IMG_URL;
    }

    public void setFDEBTOR_IMG_URL(String FDEBTOR_IMG_URL) {
        this.FDEBTOR_IMG_URL = FDEBTOR_IMG_URL;
    }
}
