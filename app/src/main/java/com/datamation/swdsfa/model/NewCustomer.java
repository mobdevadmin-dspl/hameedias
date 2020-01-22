package com.datamation.swdsfa.model;

public class NewCustomer {

    public String CONSOLE_DB;
    public String DISTRIBUTE_DB;
    public String CUSTOMER_ID;
    public String C_OTHERCODE;
    public String C_REGNUM;
    public String NAME;
    public String CUSTOMER_NIC ;
    public String ADDRESS1 ;
    public String ADDRESS2;
    public String CITY;
    public String PHONE;
    public String MOBILE;
    public String FAX;
    public String E_MAIL;
    //public String COMPANY ;
    public String C_TOWN ;
    public String ROUTE_ID;
    public String DISTRICT;
    public String OLD_CODE;
    public String TXNDATE;
    public String C_IMAGE;
    public String C_IMAGE1;
    public String C_IMAGE2;
    public String C_IMAGE3;
    private String C_LONGITUDE;
    private String C_LATITUDE;
    private String C_SYNCSTATE;
    private String C_REPCODE;
    private String C_ADDDATE;
    private String NUMVAL;
    private String ADDMAC;
    private String C_DATE;
    private  String CONTACTPERSON;

    public String getCONTACTPERSON() {
        return CONTACTPERSON;
    }

    public void setCONTACTPERSON(String CONTACTPERSON) {
        this.CONTACTPERSON = CONTACTPERSON;
    }

    public String getC_DATE() {
        return C_DATE;
    }

    public void setC_DATE(String c_DATE) {
        C_DATE = c_DATE;
    }

    private String IS_SYNCED;

    public String isIS_SYNCED() {
        return IS_SYNCED;
    }

    public void setIS_SYNCED(String IS_SYNCED) {
        this.IS_SYNCED = IS_SYNCED;
    }

    public String getC_OTHERCODE() {
        return C_OTHERCODE;
    }

    public void setC_OTHERCODE(String c_OTHERCODE) {
        C_OTHERCODE = c_OTHERCODE;
    }

    public String getC_REGNUM() {
        return C_REGNUM;
    }

    public void setC_REGNUM(String c_REGNUM) {
        C_REGNUM = c_REGNUM;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getC_ADDDATE() {
        return C_ADDDATE;
    }

    public void setC_ADDDATE(String c_ADDDATE) {
        C_ADDDATE = c_ADDDATE;
    }

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

    public String getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(String CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCUSTOMER_NIC() {
        return CUSTOMER_NIC;
    }

    public void setCUSTOMER_NIC(String CUSTOMER_NIC) {
        this.CUSTOMER_NIC = CUSTOMER_NIC;
    }

    public String getADDRESS1() {
        return ADDRESS1;
    }

    public void setADDRESS1(String ADDRESS1) {
        this.ADDRESS1 = ADDRESS1;
    }

    public String getADDRESS2() {
        return ADDRESS2;
    }

    public void setADDRESS2(String ADDRESS2) {
        this.ADDRESS2 = ADDRESS2;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getFAX() {
        return FAX;
    }

    public void setFAX(String FAX) {
        this.FAX = FAX;
    }

    public String getE_MAIL() {
        return E_MAIL;
    }

    public void setE_MAIL(String e_MAIL) {
        E_MAIL = e_MAIL;
    }

    /*public String getCOMPANY() {
        return COMPANY;
    }

    public void setCOMPANY(String COMPANY) {
        this.COMPANY = COMPANY;
    }*/

    public String getC_TOWN() {
        return C_TOWN;
    }

    public void setC_TOWN(String c_TOWN) {
        C_TOWN = c_TOWN;
    }

    public String getROUTE_ID() {
        return ROUTE_ID;
    }

    public void setROUTE_ID(String ROUTE_ID) {
        this.ROUTE_ID = ROUTE_ID;
    }

    public String getDISTRICT() {
        return DISTRICT;
    }

    public void setDISTRICT(String DISTRICT) {
        this.DISTRICT = DISTRICT;
    }

    public String getOLD_CODE() {
        return OLD_CODE;
    }

    public void setOLD_CODE(String OLD_CODE) {
        this.OLD_CODE = OLD_CODE;
    }

    public String getTxnDate() {
        return TXNDATE;
    }

    public void setTxnDate(String txnDate) {
        TXNDATE = txnDate;
    }

    public String getC_IMAGE() {
        return C_IMAGE;
    }

    public void setC_IMAGE(String c_IMAGE) {
        C_IMAGE = c_IMAGE;
    }

    public String getC_IMAGE1() {
        return C_IMAGE1;
    }

    public void setC_IMAGE1(String c_IMAGE1) {
        C_IMAGE1 = c_IMAGE1;
    }

    public String getC_IMAGE2() {
        return C_IMAGE2;
    }

    public void setC_IMAGE2(String c_IMAGE2) {
        C_IMAGE2 = c_IMAGE2;
    }

    public String getC_IMAGE3() {
        return C_IMAGE3;
    }

    public void setC_IMAGE3(String c_IMAGE3) {
        C_IMAGE3 = c_IMAGE3;
    }

    public String getC_LONGITUDE() {
        return C_LONGITUDE;
    }

    public void setC_LONGITUDE(String c_LONGITUDE) {
        C_LONGITUDE = c_LONGITUDE;
    }

    public String getC_LATITUDE() {
        return C_LATITUDE;
    }

    public void setC_LATITUDE(String c_LATITUDE) {
        C_LATITUDE = c_LATITUDE;
    }

    public String getC_SYNCSTATE() {
        return C_SYNCSTATE;
    }

    public void setC_SYNCSTATE(String c_SYNCSTATE) {
        C_SYNCSTATE = c_SYNCSTATE;
    }

    public String getC_REPCODE() {
        return C_REPCODE;
    }

    public void setC_REPCODE(String c_REPCODE) {
        C_REPCODE = c_REPCODE;
    }



    public String getnNumVal() {
        return NUMVAL;
    }

    public void setnNumVal(String nNumVal) {
        this.NUMVAL = nNumVal;
    }

    public String getAddMac() {
        return ADDMAC;
    }

    public void setAddMac(String addMac) {
        ADDMAC = addMac;
    }
}
