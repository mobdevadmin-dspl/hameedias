package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Debtor {
    public String CONSOLE_DB;
    public String DISTRIBUTE_DB;

    private String FDEBTOR_ID;
    private String FDEBTOR_DEBCODE;
    private String FDEBTOR_NAME;
    private String FDEBTOR_ADD1;
    private String FDEBTOR_ADD2;
    private String FDEBTOR_ADD3;
    private String FDEBTOR_TELE;
    private String FDEBTOR_MOB;
    private String FDEBTOR_EMAIL;
    private String FDEBTOR_CREATEDATE;
    private String FDEBTOR_REM_DIS;
    private String FDEBTOR_TOWN_CODE;
    private String FDEBTOR_AREA_CODE;
    private String FDEBTOR_DEB_CAT_CODE;
    private String FDEBTOR_DBGR_CODE;
    private String FDEBTOR_DEB_CLS_CODE;
    private String FDEBTOR_STATUS;
    private String FDEBTOR_LYLTY;
    private String FDEBTOR_DEAL_CODE;
    private String FDEBTOR_ADD_USER;
    private String FDEBTOR_ADD_DATE_DEB;
    private String FDEBTOR_ADD_MACH;
    private String FDEBTOR_RECORD_ID;
    private String FDEBTOR_TIME_STAMP;
    private String FDEBTOR_CRD_PERIOD;
    private String FDEBTOR_CHK_CRD_PRD;
    private String FDEBTOR_CRD_LIMIT;
    private String FDEBTOR_CHK_CRD_LIMIT;
    private String FDEBTOR_RANK_CODE;
    private String FDEBTOR_TRAN_DATE;
    private String FDEBTOR_TRAN_BATCH;
    private String FDEBTOR_SUMMARY;
    private String FDEBTOR_OUT_DIS;
    private String FDEBTOR_DEB_FAX;
    private String FDEBTOR_DEB_WEB;
    private String FDEBTOR_DEBCT_NAM;
    private String FDEBTOR_DEBCT_ADD1;
    private String FDEBTOR_DEBCT_ADD2;
    private String FDEBTOR_DEBCT_ADD3;
    private String FDEBTOR_DEBCT_TELE;
    private String FDEBTOR_DEBCT_FAX;
    private String FDEBTOR_DEBCT_EMAIL;
    private String FDEBTOR_DEL_PERSN;
    private String FDEBTOR_DEL_ADD1;
    private String FDEBTOR_DEL_ADD2;
    private String FDEBTOR_DEL_ADD3;
    private String FDEBTOR_DEL_TELE;
    private String FDEBTOR_DEL_FAX;
    private String FDEBTOR_DEL_EMAIL;
    private String FDEBTOR_DATE_OFB;
    private String FDEBTOR_TAX_REG;
    private String FDEBTOR_CUSDISPER;
    private String FDEBTOR_PRILLCODE;
    private String FDEBTOR_CUSDISSTAT;
    private String FDEBTOR_BUS_RGNO;
    private String FDEBTOR_POSTCODE;
    private String FDEBTOR_GEN_REMARKS;
    private String FDEBTOR_BRANCODE;
    private String FDEBTOR_BANK;
    private String FDEBTOR_BRANCH;
    private String FDEBTOR_ACCTNO;
    private String FDEBTOR_CUS_VATNO;

    private String FDEBTOR_LATITUDE;
    private String FDEBTOR_LONGITUDE;
    private String FDEBTOR_IMG_URL;
    private String FDEBTOR_REPCODE;
    private String FDEBTOR_NIC;
    private String FDEBTOR_BIS_REG;

    private String FDEBTOR_IS_SYNC;
    private String FDEBTOR_IS_CORDINATE_UPDATE;


    public String getCONSOLE_DB() {
        return CONSOLE_DB;
    }

    public void setCONSOLE_DB(String CONSOLE_DB) {
        this.CONSOLE_DB = CONSOLE_DB;
    }

    public String getDISTRIBUTE_DB() {
        return DISTRIBUTE_DB;
    }

    public void setDISTRIBUTE_DB(String DISTRIBUTE_DB) {
        this.DISTRIBUTE_DB = DISTRIBUTE_DB;
    }

    public String getFDEBTOR_NIC() {
        return FDEBTOR_NIC;
    }

    public void setFDEBTOR_NIC(String FDEBTOR_NIC) {
        this.FDEBTOR_NIC = FDEBTOR_NIC;
    }

    public String getFDEBTOR_BIS_REG() {
        return FDEBTOR_BIS_REG;
    }

    public void setFDEBTOR_BIS_REG(String FDEBTOR_BIS_REG) {
        this.FDEBTOR_BIS_REG = FDEBTOR_BIS_REG;
    }

    public String getFDEBTOR_ID() {
        return FDEBTOR_ID;
    }

    public void setFDEBTOR_ID(String fDEBTOR_ID) {
        FDEBTOR_ID = fDEBTOR_ID;
    }

    public String getFDEBTOR_CODE() {
        return FDEBTOR_DEBCODE;
    }

    public void setFDEBTOR_CODE(String fDEBTOR_CODE) {
        FDEBTOR_DEBCODE = fDEBTOR_CODE;
    }

    public String getFDEBTOR_NAME() {
        return FDEBTOR_NAME;
    }

    public void setFDEBTOR_NAME(String fDEBTOR_NAME) {
        FDEBTOR_NAME = fDEBTOR_NAME;
    }

    public String getFDEBTOR_ADD1() {
        return FDEBTOR_ADD1;
    }

    public void setFDEBTOR_ADD1(String fDEBTOR_ADD1) {
        FDEBTOR_ADD1 = fDEBTOR_ADD1;
    }

    public String getFDEBTOR_ADD2() {
        return FDEBTOR_ADD2;
    }

    public void setFDEBTOR_ADD2(String fDEBTOR_ADD2) {
        FDEBTOR_ADD2 = fDEBTOR_ADD2;
    }

    public String getFDEBTOR_ADD3() {
        return FDEBTOR_ADD3;
    }

    public void setFDEBTOR_ADD3(String fDEBTOR_ADD3) {
        FDEBTOR_ADD3 = fDEBTOR_ADD3;
    }

    public String getFDEBTOR_TELE() {
        return FDEBTOR_TELE;
    }

    public void setFDEBTOR_TELE(String fDEBTOR_TELE) {
        FDEBTOR_TELE = fDEBTOR_TELE;
    }

    public String getFDEBTOR_MOB() {
        return FDEBTOR_MOB;
    }

    public void setFDEBTOR_MOB(String fDEBTOR_MOB) {
        FDEBTOR_MOB = fDEBTOR_MOB;
    }

    public String getFDEBTOR_EMAIL() {
        return FDEBTOR_EMAIL;
    }

    public void setFDEBTOR_EMAIL(String fDEBTOR_EMAIL) {
        FDEBTOR_EMAIL = fDEBTOR_EMAIL;
    }

    public String getFDEBTOR_CREATEDATE() {
        return FDEBTOR_CREATEDATE;
    }

    public void setFDEBTOR_CREATEDATE(String fDEBTOR_CREATEDATE) {
        FDEBTOR_CREATEDATE = fDEBTOR_CREATEDATE;
    }

    public String getFDEBTOR_REM_DIS() {
        return FDEBTOR_REM_DIS;
    }

    public void setFDEBTOR_REM_DIS(String fDEBTOR_REM_DIS) {
        FDEBTOR_REM_DIS = fDEBTOR_REM_DIS;
    }

    public String getFDEBTOR_TOWN_CODE() {
        return FDEBTOR_TOWN_CODE;
    }

    public void setFDEBTOR_TOWN_CODE(String fDEBTOR_TOWN_CODE) {
        FDEBTOR_TOWN_CODE = fDEBTOR_TOWN_CODE;
    }

    public String getFDEBTOR_AREA_CODE() {
        return FDEBTOR_AREA_CODE;
    }

    public void setFDEBTOR_AREA_CODE(String fDEBTOR_AREA_CODE) {
        FDEBTOR_AREA_CODE = fDEBTOR_AREA_CODE;
    }

    public String getFDEBTOR_DEB_CAT_CODE() {
        return FDEBTOR_DEB_CAT_CODE;
    }

    public void setFDEBTOR_DEB_CAT_CODE(String fDEBTOR_DEB_CAT_CODE) {
        FDEBTOR_DEB_CAT_CODE = fDEBTOR_DEB_CAT_CODE;
    }

    public String getFDEBTOR_DBGR_CODE() {
        return FDEBTOR_DBGR_CODE;
    }

    public void setFDEBTOR_DBGR_CODE(String fDEBTOR_DBGR_CODE) {
        FDEBTOR_DBGR_CODE = fDEBTOR_DBGR_CODE;
    }

    public String getFDEBTOR_DEB_CLS_CODE() {
        return FDEBTOR_DEB_CLS_CODE;
    }

    public void setFDEBTOR_DEB_CLS_CODE(String fDEBTOR_DEB_CLS_CODE) {
        FDEBTOR_DEB_CLS_CODE = fDEBTOR_DEB_CLS_CODE;
    }

    public String getFDEBTOR_STATUS() {
        return FDEBTOR_STATUS;
    }

    public void setFDEBTOR_STATUS(String fDEBTOR_STATUS) {
        FDEBTOR_STATUS = fDEBTOR_STATUS;
    }

    public String getFDEBTOR_LYLTY() {
        return FDEBTOR_LYLTY;
    }

    public void setFDEBTOR_LYLTY(String fDEBTOR_LYLTY) {
        FDEBTOR_LYLTY = fDEBTOR_LYLTY;
    }

    public String getFDEBTOR_DEAL_CODE() {
        return FDEBTOR_DEAL_CODE;
    }

    public void setFDEBTOR_DEAL_CODE(String fDEBTOR_DEAL_CODE) {
        FDEBTOR_DEAL_CODE = fDEBTOR_DEAL_CODE;
    }

    public String getFDEBTOR_ADD_USER() {
        return FDEBTOR_ADD_USER;
    }

    public void setFDEBTOR_ADD_USER(String fDEBTOR_ADD_USER) {
        FDEBTOR_ADD_USER = fDEBTOR_ADD_USER;
    }

    public String getFDEBTOR_ADD_DATE_DEB() {
        return FDEBTOR_ADD_DATE_DEB;
    }

    public void setFDEBTOR_ADD_DATE_DEB(String fDEBTOR_ADD_DATE_DEB) {
        FDEBTOR_ADD_DATE_DEB = fDEBTOR_ADD_DATE_DEB;
    }

    public String getFDEBTOR_ADD_MACH() {
        return FDEBTOR_ADD_MACH;
    }

    public void setFDEBTOR_ADD_MACH(String fDEBTOR_ADD_MACH) {
        FDEBTOR_ADD_MACH = fDEBTOR_ADD_MACH;
    }

    public String getFDEBTOR_RECORD_ID() {
        return FDEBTOR_RECORD_ID;
    }

    public void setFDEBTOR_RECORD_ID(String fDEBTOR_RECORD_ID) {
        FDEBTOR_RECORD_ID = fDEBTOR_RECORD_ID;
    }

    public String getFDEBTOR_TIME_STAMP() {
        return FDEBTOR_TIME_STAMP;
    }

    public void setFDEBTOR_TIME_STAMP(String fDEBTOR_TIME_STAMP) {
        FDEBTOR_TIME_STAMP = fDEBTOR_TIME_STAMP;
    }

    public String getFDEBTOR_CRD_PERIOD() {
        return FDEBTOR_CRD_PERIOD;
    }

    public void setFDEBTOR_CRD_PERIOD(String fDEBTOR_CRD_PERIOD) {
        FDEBTOR_CRD_PERIOD = fDEBTOR_CRD_PERIOD;
    }

    public String getFDEBTOR_CHK_CRD_PRD() {
        return FDEBTOR_CHK_CRD_PRD;
    }

    public void setFDEBTOR_CHK_CRD_PRD(String fDEBTOR_CHK_CRD_PRD) {
        FDEBTOR_CHK_CRD_PRD = fDEBTOR_CHK_CRD_PRD;
    }

    public String getFDEBTOR_CRD_LIMIT() {
        return FDEBTOR_CRD_LIMIT;
    }

    public void setFDEBTOR_CRD_LIMIT(String fDEBTOR_CRD_LIMIT) {
        FDEBTOR_CRD_LIMIT = fDEBTOR_CRD_LIMIT;
    }

    public String getFDEBTOR_CHK_CRD_LIMIT() {
        return FDEBTOR_CHK_CRD_LIMIT;
    }

    public void setFDEBTOR_CHK_CRD_LIMIT(String fDEBTOR_CHK_CRD_LIMIT) {
        FDEBTOR_CHK_CRD_LIMIT = fDEBTOR_CHK_CRD_LIMIT;
    }


    public String getFDEBTOR_RANK_CODE() {
        return FDEBTOR_RANK_CODE;
    }

    public void setFDEBTOR_RANK_CODE(String fDEBTOR_RANK_CODE) {
        FDEBTOR_RANK_CODE = fDEBTOR_RANK_CODE;
    }

    public String getFDEBTOR_TRAN_DATE() {
        return FDEBTOR_TRAN_DATE;
    }

    public void setFDEBTOR_TRAN_DATE(String fDEBTOR_TRAN_DATE) {
        FDEBTOR_TRAN_DATE = fDEBTOR_TRAN_DATE;
    }

    public String getFDEBTOR_TRAN_BATCH() {
        return FDEBTOR_TRAN_BATCH;
    }

    public void setFDEBTOR_TRAN_BATCH(String fDEBTOR_TRAN_BATCH) {
        FDEBTOR_TRAN_BATCH = fDEBTOR_TRAN_BATCH;
    }

    public String getFDEBTOR_SUMMARY() {
        return FDEBTOR_SUMMARY;
    }

    public void setFDEBTOR_SUMMARY(String fDEBTOR_SUMMARY) {
        FDEBTOR_SUMMARY = fDEBTOR_SUMMARY;
    }

    public String getFDEBTOR_OUT_DIS() {
        return FDEBTOR_OUT_DIS;
    }

    public void setFDEBTOR_OUT_DIS(String fDEBTOR_OUT_DIS) {
        FDEBTOR_OUT_DIS = fDEBTOR_OUT_DIS;
    }

    public String getFDEBTOR_DEB_FAX() {
        return FDEBTOR_DEB_FAX;
    }

    public void setFDEBTOR_DEB_FAX(String fDEBTOR_DEB_FAX) {
        FDEBTOR_DEB_FAX = fDEBTOR_DEB_FAX;
    }

    public String getFDEBTOR_DEB_WEB() {
        return FDEBTOR_DEB_WEB;
    }

    public void setFDEBTOR_DEB_WEB(String fDEBTOR_DEB_WEB) {
        FDEBTOR_DEB_WEB = fDEBTOR_DEB_WEB;
    }

    public String getFDEBTOR_DEBCT_NAM() {
        return FDEBTOR_DEBCT_NAM;
    }

    public void setFDEBTOR_DEBCT_NAM(String fDEBTOR_DEBCT_NAM) {
        FDEBTOR_DEBCT_NAM = fDEBTOR_DEBCT_NAM;
    }

    public String getFDEBTOR_DEBCT_ADD1() {
        return FDEBTOR_DEBCT_ADD1;
    }

    public void setFDEBTOR_DEBCT_ADD1(String fDEBTOR_DEBCT_ADD1) {
        FDEBTOR_DEBCT_ADD1 = fDEBTOR_DEBCT_ADD1;
    }

    public String getFDEBTOR_DEBCT_ADD2() {
        return FDEBTOR_DEBCT_ADD2;
    }

    public void setFDEBTOR_DEBCT_ADD2(String fDEBTOR_DEBCT_ADD2) {
        FDEBTOR_DEBCT_ADD2 = fDEBTOR_DEBCT_ADD2;
    }

    public String getFDEBTOR_DEBCT_ADD3() {
        return FDEBTOR_DEBCT_ADD3;
    }

    public void setFDEBTOR_DEBCT_ADD3(String fDEBTOR_DEBCT_ADD3) {
        FDEBTOR_DEBCT_ADD3 = fDEBTOR_DEBCT_ADD3;
    }

    public String getFDEBTOR_DEBCT_TELE() {
        return FDEBTOR_DEBCT_TELE;
    }

    public void setFDEBTOR_DEBCT_TELE(String fDEBTOR_DEBCT_TELE) {
        FDEBTOR_DEBCT_TELE = fDEBTOR_DEBCT_TELE;
    }

    public String getFDEBTOR_DEBCT_FAX() {
        return FDEBTOR_DEBCT_FAX;
    }

    public void setFDEBTOR_DEBCT_FAX(String fDEBTOR_DEBCT_FAX) {
        FDEBTOR_DEBCT_FAX = fDEBTOR_DEBCT_FAX;
    }

    public String getFDEBTOR_DEBCT_EMAIL() {
        return FDEBTOR_DEBCT_EMAIL;
    }

    public void setFDEBTOR_DEBCT_EMAIL(String fDEBTOR_DEBCT_EMAIL) {
        FDEBTOR_DEBCT_EMAIL = fDEBTOR_DEBCT_EMAIL;
    }

    public String getFDEBTOR_DEL_PERSN() {
        return FDEBTOR_DEL_PERSN;
    }

    public void setFDEBTOR_DEL_PERSN(String fDEBTOR_DEL_PERSN) {
        FDEBTOR_DEL_PERSN = fDEBTOR_DEL_PERSN;
    }

    public String getFDEBTOR_DEL_ADD1() {
        return FDEBTOR_DEL_ADD1;
    }

    public void setFDEBTOR_DEL_ADD1(String fDEBTOR_DEL_ADD1) {
        FDEBTOR_DEL_ADD1 = fDEBTOR_DEL_ADD1;
    }

    public String getFDEBTOR_DEL_ADD2() {
        return FDEBTOR_DEL_ADD2;
    }

    public void setFDEBTOR_DEL_ADD2(String fDEBTOR_DEL_ADD2) {
        FDEBTOR_DEL_ADD2 = fDEBTOR_DEL_ADD2;
    }

    public String getFDEBTOR_DEL_ADD3() {
        return FDEBTOR_DEL_ADD3;
    }

    public void setFDEBTOR_DEL_ADD3(String fDEBTOR_DEL_ADD3) {
        FDEBTOR_DEL_ADD3 = fDEBTOR_DEL_ADD3;
    }

    public String getFDEBTOR_DEL_TELE() {
        return FDEBTOR_DEL_TELE;
    }

    public void setFDEBTOR_DEL_TELE(String fDEBTOR_DEL_TELE) {
        FDEBTOR_DEL_TELE = fDEBTOR_DEL_TELE;
    }

    public String getFDEBTOR_DEL_FAX() {
        return FDEBTOR_DEL_FAX;
    }

    public void setFDEBTOR_DEL_FAX(String fDEBTOR_DEL_FAX) {
        FDEBTOR_DEL_FAX = fDEBTOR_DEL_FAX;
    }

    public String getFDEBTOR_DEL_EMAIL() {
        return FDEBTOR_DEL_EMAIL;
    }

    public void setFDEBTOR_DEL_EMAIL(String fDEBTOR_DEL_EMAIL) {
        FDEBTOR_DEL_EMAIL = fDEBTOR_DEL_EMAIL;
    }

    public String getFDEBTOR_DATE_OFB() {
        return FDEBTOR_DATE_OFB;
    }

    public void setFDEBTOR_DATE_OFB(String fDEBTOR_DATE_OFB) {
        FDEBTOR_DATE_OFB = fDEBTOR_DATE_OFB;
    }

    public String getFDEBTOR_TAX_REG() {
        return FDEBTOR_TAX_REG;
    }

    public void setFDEBTOR_TAX_REG(String fDEBTOR_TAX_REG) {
        FDEBTOR_TAX_REG = fDEBTOR_TAX_REG;
    }

    public String getFDEBTOR_CUSDISPER() {
        return FDEBTOR_CUSDISPER;
    }

    public void setFDEBTOR_CUSDISPER(String fDEBTOR_CUSDISPER) {
        FDEBTOR_CUSDISPER = fDEBTOR_CUSDISPER;
    }

    public String getFDEBTOR_PRILLCODE() {
        return FDEBTOR_PRILLCODE;
    }

    public void setFDEBTOR_PRILLCODE(String fDEBTOR_PRILLCODE) {
        FDEBTOR_PRILLCODE = fDEBTOR_PRILLCODE;
    }

    public String getFDEBTOR_CUSDISSTAT() {
        return FDEBTOR_CUSDISSTAT;
    }

    public void setFDEBTOR_CUSDISSTAT(String fDEBTOR_CUSDISSTAT) {
        FDEBTOR_CUSDISSTAT = fDEBTOR_CUSDISSTAT;
    }

    public String getFDEBTOR_BUS_RGNO() {
        return FDEBTOR_BUS_RGNO;
    }

    public void setFDEBTOR_BUS_RGNO(String fDEBTOR_BUS_RGNO) {
        FDEBTOR_BUS_RGNO = fDEBTOR_BUS_RGNO;
    }

    public String getFDEBTOR_POSTCODE() {
        return FDEBTOR_POSTCODE;
    }

    public void setFDEBTOR_POSTCODE(String fDEBTOR_POSTCODE) {
        FDEBTOR_POSTCODE = fDEBTOR_POSTCODE;
    }

    public String getFDEBTOR_GEN_REMARKS() {
        return FDEBTOR_GEN_REMARKS;
    }

    public void setFDEBTOR_GEN_REMARKS(String fDEBTOR_GEN_REMARKS) {
        FDEBTOR_GEN_REMARKS = fDEBTOR_GEN_REMARKS;
    }

    public String getFDEBTOR_BRANCODE() {
        return FDEBTOR_BRANCODE;
    }

    public void setFDEBTOR_BRANCODE(String fDEBTOR_BRANCODE) {
        FDEBTOR_BRANCODE = fDEBTOR_BRANCODE;
    }

    public String getFDEBTOR_BANK() {
        return FDEBTOR_BANK;
    }

    public void setFDEBTOR_BANK(String fDEBTOR_BANK) {
        FDEBTOR_BANK = fDEBTOR_BANK;
    }

    public String getFDEBTOR_BRANCH() {
        return FDEBTOR_BRANCH;
    }

    public void setFDEBTOR_BRANCH(String fDEBTOR_BRANCH) {
        FDEBTOR_BRANCH = fDEBTOR_BRANCH;
    }

    public String getFDEBTOR_ACCTNO() {
        return FDEBTOR_ACCTNO;
    }

    public void setFDEBTOR_ACCTNO(String fDEBTOR_ACCTNO) {
        FDEBTOR_ACCTNO = fDEBTOR_ACCTNO;
    }

    public String getFDEBTOR_CUS_VATNO() {
        return FDEBTOR_CUS_VATNO;
    }

    public void setFDEBTOR_CUS_VATNO(String fDEBTOR_CUS_VATNO) {
        FDEBTOR_CUS_VATNO = fDEBTOR_CUS_VATNO;
    }

    public String getFDEBTOR_LATITUDE() {
        return FDEBTOR_LATITUDE;
    }

    public void setFDEBTOR_LATITUDE(String fDEBTOR_LATITUDE) {
        FDEBTOR_LATITUDE = fDEBTOR_LATITUDE;
    }

    public String getFDEBTOR_LONGITUDE() {
        return FDEBTOR_LONGITUDE;
    }

    public void setFDEBTOR_LONGITUDE(String fDEBTOR_LONGITUDE) {
        FDEBTOR_LONGITUDE = fDEBTOR_LONGITUDE;
    }

    public String getFDEBTOR_REPCODE() {
        return FDEBTOR_REPCODE;
    }

    public String getFDEBTOR_IS_SYNC() {
        return FDEBTOR_IS_SYNC;
    }

    public void setFDEBTOR_IS_SYNC(String FDEBTOR_IS_SYNC) {
        this.FDEBTOR_IS_SYNC = FDEBTOR_IS_SYNC;
    }

    public String getFDEBTOR_IS_CORDINATE_UPDATE() {
        return FDEBTOR_IS_CORDINATE_UPDATE;
    }

    public void setFDEBTOR_IS_CORDINATE_UPDATE(String FDEBTOR_IS_CORDINATE_UPDATE) {
        this.FDEBTOR_IS_CORDINATE_UPDATE = FDEBTOR_IS_CORDINATE_UPDATE;
    }

    public String getFDEBTOR_IMG_URL() {
        return FDEBTOR_IMG_URL;
    }

    public void setFDEBTOR_IMG_URL(String FDEBTOR_IMG_URL) {
        this.FDEBTOR_IMG_URL = FDEBTOR_IMG_URL;
    }

    public void setFDEBTOR_REPCODE(String fDEBTOR_REPCODE) {
        FDEBTOR_REPCODE = fDEBTOR_REPCODE;
    }
    public static Debtor parseOutlet(JSONObject instance) throws JSONException {

        if (instance != null) {
            Debtor aDebtor = new Debtor();
            aDebtor.setFDEBTOR_ADD1(instance.getString("DebAdd1"));
            aDebtor.setFDEBTOR_ADD2(instance.getString("DebAdd2"));
            aDebtor.setFDEBTOR_ADD3(instance.getString("DebAdd3"));
            aDebtor.setFDEBTOR_AREA_CODE(instance.getString("AreaCode"));
            aDebtor.setFDEBTOR_CHK_CRD_LIMIT(instance.getString("ChkCrdLmt"));
            aDebtor.setFDEBTOR_CHK_CRD_PRD(instance.getString("ChkCrdPrd"));
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
            aDebtor.setFDEBTOR_TOWN_CODE(instance.getString("TownCode"));
            aDebtor.setFDEBTOR_REPCODE(instance.getString("RepCode"));
            aDebtor.setFDEBTOR_LATITUDE(instance.getString("Latitude"));
            aDebtor.setFDEBTOR_LONGITUDE(instance.getString("Longitude"));
            aDebtor.setFDEBTOR_IMG_URL(instance.getString("ImgURL"));

//            aDebtor.setFDEBTOR_IMG_URL(instance.getString("FDEBTOR_IMG_URL"));

            return aDebtor;
        }

        return null;
    }
}
