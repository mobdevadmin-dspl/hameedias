package com.datamation.swdsfa.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class DayExpHed {

    @Override
    public String toString() {
        return "DayExpHed{" +
                "EXP_REFNO='" + EXP_REFNO + '\'' +
                ", EXP_TXNDATE='" + EXP_TXNDATE + '\'' +
                ", EXP_REPCODE='" + EXP_REPCODE + '\'' +
                ", EXP_AREACODE='" + EXP_AREACODE + '\'' +
                ", EXP_REMARK='" + EXP_REMARK + '\'' +
                ", EXP_ADDDATE='" + EXP_ADDDATE + '\'' +
                ", EXP_LONGITUDE='" + EXP_LONGITUDE + '\'' +
                ", EXP_LATITUDE='" + EXP_LATITUDE + '\'' +
                ", EXP_IS_SYNCED='" + EXP_IS_SYNCED + '\'' +
                ", EXP_TOTAMT='" + EXP_TOTAMT + '\'' +
                ", EXP_ACTIVESTATE='" + EXP_ACTIVESTATE + '\'' +
                ", EXP_ADDUSER='" + EXP_ADDUSER + '\'' +
                ", EXP_ADDMACH='" + EXP_ADDMACH + '\'' +
                ", EXP_ADDRESS='" + EXP_ADDRESS + '\'' +
                ", EXP_REPNAME='" + EXP_REPNAME + '\'' +
                ", EXP_COSTCODE='" + EXP_COSTCODE + '\'' +
                ", ExpnseDetList=" + ExpnseDetList +
                ", ConsoleDB='" + ConsoleDB + '\'' +
                ", DistDB='" + DistDB + '\'' +
                ", NextNumVal='" + NextNumVal + '\'' +
                '}';
    }

    private String EXP_REFNO;
    private String EXP_TXNDATE;
    private String EXP_REPCODE;
    private String EXP_AREACODE;
    private String EXP_REMARK;
    private String EXP_ADDDATE;
    private String EXP_LONGITUDE;
    private String EXP_LATITUDE;
    private String EXP_IS_SYNCED;
    private String EXP_TOTAMT;
    private String EXP_ACTIVESTATE;
    private String EXP_ADDUSER;
    private String EXP_ADDMACH;
    private String EXP_ADDRESS;
    private String EXP_REPNAME;
    private String EXP_COSTCODE;
    private ArrayList<DayExpDet>ExpnseDetList;

    public ArrayList<DayExpDet> getExpnseDetList() {
        return ExpnseDetList;
    }

    public void setExpnseDetList(ArrayList<DayExpDet> expnseDetList) {
        ExpnseDetList = expnseDetList;
    }

    public String getEXP_REPNAME() {
        return EXP_REPNAME;
    }

    public void setEXP_REPNAME(String EXP_REPNAME) {
        this.EXP_REPNAME = EXP_REPNAME;
    }

    public String getEXP_COSTCODE() {
        return EXP_COSTCODE;
    }

    public void setEXP_COSTCODE(String EXP_COSTCODE) {
        this.EXP_COSTCODE = EXP_COSTCODE;
    }

    public String getEXP_ADDUSER() {
        return EXP_ADDUSER;
    }

    public void setEXP_ADDUSER(String EXP_ADDUSER) {
        this.EXP_ADDUSER = EXP_ADDUSER;
    }

    public String getEXP_ADDMACH() {
        return EXP_ADDMACH;
    }

    public void setEXP_ADDMACH(String EXP_ADDMACH) {
        this.EXP_ADDMACH = EXP_ADDMACH;
    }

    public String getEXP_AREACODE() {
        return EXP_AREACODE;
    }

    public void setEXP_AREACODE(String EXP_AREACODE) {
        this.EXP_AREACODE = EXP_AREACODE;
    }

    public String getEXP_ADDRESS() {
        return EXP_ADDRESS;
    }

    public void setEXP_ADDRESS(String EXP_ADDRESS) {
        this.EXP_ADDRESS = EXP_ADDRESS;
    }

    private String ConsoleDB;
    private String DistDB;
    private String NextNumVal;

    public String getNextNumVal() {
        return NextNumVal;
    }

    public void setNextNumVal(String nextNumVal) {
        NextNumVal = nextNumVal;
    }

    public String getConsoleDB() {
        return ConsoleDB;
    }

    public void setConsoleDB(String consoleDB) {
        ConsoleDB = consoleDB;
    }

    public String getDistDB() {
        return DistDB;
    }

    public void setDistDB(String distDB) {
        DistDB = distDB;
    }

    public String getEXP_ADDDATE() {
        return EXP_ADDDATE;
    }

    public void setEXP_ADDDATE(String EXP_ADDDATE) {
        this.EXP_ADDDATE = EXP_ADDDATE;
    }

    public String getEXP_REFNO() {
        return EXP_REFNO;
    }

    public void setEXP_REFNO(String eXPHED_REFNO) {
        EXP_REFNO = eXPHED_REFNO;
    }

    public String getEXP_REPCODE() {
        return EXP_REPCODE;
    }

    public void setEXP_REPCODE(String eXPHED_REPCODE) {
        EXP_REPCODE = eXPHED_REPCODE;
    }

    public String getEXP_REMARK() {
        return EXP_REMARK;
    }

    public void setEXP_REMARK(String eXPHED_REMARK) {
        EXP_REMARK = eXPHED_REMARK;
    }

    public String getEXP_IS_SYNCED() {
        return EXP_IS_SYNCED;
    }

    public void setEXP_IS_SYNCED(String eXPHED_IS_SYNCED) {
        EXP_IS_SYNCED = eXPHED_IS_SYNCED;
    }


    public String getEXP_TXNDATE() {
        return EXP_TXNDATE;
    }

    public void setEXP_TXNDATE(String eXPHED_TXNDATE) {
        EXP_TXNDATE = eXPHED_TXNDATE;
    }

    public String getEXP_TOTAMT() {
        return EXP_TOTAMT;
    }

    public void setEXP_TOTAMT(String eXPHED_TOTAMT) {
        EXP_TOTAMT = eXPHED_TOTAMT;
    }

    public String getEXP_ACTIVESTATE() {
        return EXP_ACTIVESTATE;
    }

    public void setEXP_ACTIVESTATE(String eXPHED_ACTIVESTATE) {
        EXP_ACTIVESTATE = eXPHED_ACTIVESTATE;
    }
    public String getEXP_LATITUDE() {
        return EXP_LATITUDE;
    }

    public void setEXP_LATITUDE(String eXPHED_LATITUDE) {
        EXP_LATITUDE = eXPHED_LATITUDE;
    }

    public String getEXP_LONGITUDE() {
        return EXP_LONGITUDE;
    }

    public void setEXP_LONGITUDE(String eXPHED_LONGITUDE) {
        EXP_LONGITUDE = eXPHED_LONGITUDE;
    }

}
