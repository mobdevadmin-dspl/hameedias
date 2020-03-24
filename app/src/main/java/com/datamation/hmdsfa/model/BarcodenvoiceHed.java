package com.datamation.hmdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class BarcodenvoiceHed implements Serializable {

    private String refno;
    private int id;
    private String repCode;
    private String txndate;
    private String cuscode;
    private String locCode;
    private String areaCode;

    public BarcodenvoiceHed() {}

    public BarcodenvoiceHed( String refno, String repCode, String txndate, String cuscode, String locCode, String areaCode) {
        this.refno = refno;
        this.repCode = repCode;
        this.txndate = txndate;
        this.cuscode = cuscode;
        this.locCode = locCode;
        this.areaCode = areaCode;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepCode() {
        return repCode;
    }

    public void setRepCode(String repCode) {
        this.repCode = repCode;
    }

    public String getTxndate() {
        return txndate;
    }

    public void setTxndate(String txndate) {
        this.txndate = txndate;
    }

    public String getCuscode() {
        return cuscode;
    }

    public void setCuscode(String cuscode) {
        this.cuscode = cuscode;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
