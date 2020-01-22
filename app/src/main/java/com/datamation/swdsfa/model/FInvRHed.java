package com.datamation.swdsfa.model;

import java.io.Serializable;
import java.util.ArrayList;

public class FInvRHed implements Serializable{

    private String ConsoleDB;
    private String DistDB;
    private String NextNumVal;

    private String FINVRHED_ID;
    private String FINVRHED_REFNO;
    private String FINVRHED_ORD_REFNO;
    private String FINVRHED_TXN_DATE;
    private String FINVRHED_MANUREF;
    private String FINVRHED_DEBCODE;
    private String FINVRHED_REMARKS;
    private String FINVRHED_ROUTE_CODE;
    private String FINVRHED_TOTAL_AMT;
    private String FINVRHED_ADD_DATE;
    private String FINVRHED_ADD_MACH;
    private String FINVRHED_ADD_USER;
    private String FINVRHED_TXNTYPE;
    private String FINVRHED_IS_ACTIVE;
    private String FINVRHED_IS_SYNCED;
    private String FINVRHED_ADDRESS;
    private String FINVRHED_REASON_CODE;
    private String FINVRHED_COSTCODE;
    private String FINVRHED_LOCCODE;
    private String FINVRHED_TAX_REG;
    private String FINVRHED_TOTAL_TAX;
    private String FINVRHED_TOTAL_DIS;
    private String FINVRHED_LONGITUDE;
    private String FINVRHED_LATITUDE;
    private String FINVRHED_START_TIME;
    private String FINVRHED_END_TIME;
    private String FINVRHED_INV_REFNO;
    private String FINVRHED_INV_DATE;
    private String FINVRHED_REP_CODE;
    private String FINVRHED_RETURN_TYPE;
    private String FINVRHED_TOURCODE;
    private String FINVRHED_AREACODE;
    private String FINVRHED_LORRYCODE;
    private String FINVRHED_HELPERCODE;
    private String FINVRHED_DRIVERCODE;

    private ArrayList<FInvRDet> FinvrtDets;
    private ArrayList<TaxDT> taxDTs;
    private ArrayList<TaxRG> taxRGs;

    public String getFINVRHED_ORD_REFNO() {
        return FINVRHED_ORD_REFNO;
    }

    public void setFINVRHED_ORD_REFNO(String FINVRHED_ORD_REFNO) {
        this.FINVRHED_ORD_REFNO = FINVRHED_ORD_REFNO;
    }

    public String getNextNumVal() {
        return NextNumVal;
    }

    public void setNextNumVal(String nextNumVal) {
        NextNumVal = nextNumVal;
    }

    public ArrayList<FInvRDet> getFinvrtDets() {
        return FinvrtDets;
    }

    public void setFinvrtDets(ArrayList<FInvRDet> finvrtDets) {
        FinvrtDets = finvrtDets;
    }

    public ArrayList<TaxDT> getTaxDTs() {
        return taxDTs;
    }

    public void setTaxDTs(ArrayList<TaxDT> taxDTs) {
        this.taxDTs = taxDTs;
    }

    public ArrayList<TaxRG> getTaxRGs() {
        return taxRGs;
    }

    public void setTaxRGs(ArrayList<TaxRG> taxRGs) {
        this.taxRGs = taxRGs;
    }

    public String getFINVRHED_INV_REFNO() {
        return FINVRHED_INV_REFNO;
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

    public void setFINVRHED_INV_REFNO(String FINVRHED_INV_REFNO) {
        this.FINVRHED_INV_REFNO = FINVRHED_INV_REFNO;
    }

    public String getFINVRHED_REP_CODE() {
        return FINVRHED_REP_CODE;
    }

    public void setFINVRHED_REP_CODE(String FINVRHED_REP_CODE) {
        this.FINVRHED_REP_CODE = FINVRHED_REP_CODE;
    }

    public String getFINVRHED_RETURN_TYPE() {
        return FINVRHED_RETURN_TYPE;
    }

    public void setFINVRHED_RETURN_TYPE(String FINVRHED_RETURN_TYPE) {
        this.FINVRHED_RETURN_TYPE = FINVRHED_RETURN_TYPE;
    }

    public String getFINVRHED_TOURCODE() {
        return FINVRHED_TOURCODE;
    }

    public void setFINVRHED_TOURCODE(String FINVRHED_TOURCODE) {
        this.FINVRHED_TOURCODE = FINVRHED_TOURCODE;
    }

    public String getFINVRHED_AREACODE() {
        return FINVRHED_AREACODE;
    }

    public void setFINVRHED_AREACODE(String FINVRHED_AREACODE) {
        this.FINVRHED_AREACODE = FINVRHED_AREACODE;
    }

    public String getFINVRHED_LORRYCODE() {
        return FINVRHED_LORRYCODE;
    }

    public void setFINVRHED_LORRYCODE(String FINVRHED_LORRYCODE) {
        this.FINVRHED_LORRYCODE = FINVRHED_LORRYCODE;
    }

    public String getFINVRHED_HELPERCODE() {
        return FINVRHED_HELPERCODE;
    }

    public void setFINVRHED_HELPERCODE(String FINVRHED_HELPERCODE) {
        this.FINVRHED_HELPERCODE = FINVRHED_HELPERCODE;
    }

    public String getFINVRHED_DRIVERCODE() {
        return FINVRHED_DRIVERCODE;
    }

    public void setFINVRHED_DRIVERCODE(String FINVRHED_DRIVERCODE) {
        this.FINVRHED_DRIVERCODE = FINVRHED_DRIVERCODE;
    }

    public String getFINVRHED_INV_DATE() {
        return FINVRHED_INV_DATE;
    }

    public void setFINVRHED_INV_DATE(String FINVRHED_INV_DATE) {
        this.FINVRHED_INV_DATE = FINVRHED_INV_DATE;
    }

    public String getFINVRHED_ROUTE_CODE() {
        return FINVRHED_ROUTE_CODE;
    }

    public void setFINVRHED_ROUTE_CODE(String fINVRHED_ROUTE_CODE) {
        FINVRHED_ROUTE_CODE = fINVRHED_ROUTE_CODE;
    }

    public String getFINVRHED_TXNTYPE() {
        return FINVRHED_TXNTYPE;
    }

    public void setFINVRHED_TXNTYPE(String fINVRHED_TXNTYPE) {
        FINVRHED_TXNTYPE = fINVRHED_TXNTYPE;
    }

    public String getFINVRHED_ADDRESS() {
        return FINVRHED_ADDRESS;
    }

    public void setFINVRHED_ADDRESS(String fINVRHED_ADDRESS) {
        FINVRHED_ADDRESS = fINVRHED_ADDRESS;
    }

    public String getFINVRHED_REASON_CODE() {
        return FINVRHED_REASON_CODE;
    }

    public void setFINVRHED_REASON_CODE(String fINVRHED_REASON_CODE) {
        FINVRHED_REASON_CODE = fINVRHED_REASON_CODE;
    }

    public String getFINVRHED_TAX_REG() {
        return FINVRHED_TAX_REG;
    }

    public void setFINVRHED_TAX_REG(String fINVRHED_TAX_REG) {
        FINVRHED_TAX_REG = fINVRHED_TAX_REG;
    }

    public String getFINVRHED_TOTAL_TAX() {
        return FINVRHED_TOTAL_TAX;
    }

    public void setFINVRHED_TOTAL_TAX(String fINVRHED_TOTAL_TAX) {
        FINVRHED_TOTAL_TAX = fINVRHED_TOTAL_TAX;
    }

    public String getFINVRHED_TOTAL_DIS() {
        return FINVRHED_TOTAL_DIS;
    }

    public void setFINVRHED_TOTAL_DIS(String fINVRHED_TOTAL_DIS) {
        FINVRHED_TOTAL_DIS = fINVRHED_TOTAL_DIS;
    }

    public String getFINVRHED_LONGITUDE() {
        return FINVRHED_LONGITUDE;
    }

    public void setFINVRHED_LONGITUDE(String fINVRHED_LONGITUDE) {
        FINVRHED_LONGITUDE = fINVRHED_LONGITUDE;
    }

    public String getFINVRHED_LATITUDE() {
        return FINVRHED_LATITUDE;
    }

    public void setFINVRHED_LATITUDE(String fINVRHED_LATITUDE) {
        FINVRHED_LATITUDE = fINVRHED_LATITUDE;
    }

    public String getFINVRHED_START_TIME() {
        return FINVRHED_START_TIME;
    }

    public void setFINVRHED_START_TIME(String fINVRHED_START_TIME) {
        FINVRHED_START_TIME = fINVRHED_START_TIME;
    }

    public String getFINVRHED_END_TIME() {
        return FINVRHED_END_TIME;
    }

    public void setFINVRHED_END_TIME(String fINVRHED_END_TIME) {
        FINVRHED_END_TIME = fINVRHED_END_TIME;
    }

    public String getFINVRHED_ID() {
        return FINVRHED_ID;
    }

    public void setFINVRHED_ID(String fINVRHED_ID) {
        FINVRHED_ID = fINVRHED_ID;
    }

    public String getFINVRHED_REFNO() {
        return FINVRHED_REFNO;
    }

    public void setFINVRHED_REFNO(String fINVRHED_REFNO) {
        FINVRHED_REFNO = fINVRHED_REFNO;
    }

    public String getFINVRHED_TXN_DATE() {
        return FINVRHED_TXN_DATE;
    }

    public void setFINVRHED_TXN_DATE(String fINVRHED_TXN_DATE) {
        FINVRHED_TXN_DATE = fINVRHED_TXN_DATE;
    }

    public String getFINVRHED_REMARKS() {
        return FINVRHED_REMARKS;
    }

    public void setFINVRHED_REMARKS(String fINVRHED_REMARKS) {
        FINVRHED_REMARKS = fINVRHED_REMARKS;
    }

    public String getFINVRHED_DEBCODE() {
        return FINVRHED_DEBCODE;
    }

    public void setFINVRHED_DEBCODE(String fINVRHED_DEBCODE) {
        FINVRHED_DEBCODE = fINVRHED_DEBCODE;
    }

    public String getFINVRHED_TOTAL_AMT() {
        return FINVRHED_TOTAL_AMT;
    }

    public void setFINVRHED_TOTAL_AMT(String fINVRHED_TOTAL_AMT) {
        FINVRHED_TOTAL_AMT = fINVRHED_TOTAL_AMT;
    }

    public String getFINVRHED_ADD_DATE() {
        return FINVRHED_ADD_DATE;
    }

    public void setFINVRHED_ADD_DATE(String fINVRHED_ADD_DATE) {
        FINVRHED_ADD_DATE = fINVRHED_ADD_DATE;
    }

    public String getFINVRHED_ADD_MACH() {
        return FINVRHED_ADD_MACH;
    }

    public void setFINVRHED_ADD_MACH(String fINVRHED_ADD_MACH) {
        FINVRHED_ADD_MACH = fINVRHED_ADD_MACH;
    }

    public String getFINVRHED_ADD_USER() {
        return FINVRHED_ADD_USER;
    }

    public void setFINVRHED_ADD_USER(String fINVRHED_ADD_USER) {
        FINVRHED_ADD_USER = fINVRHED_ADD_USER;
    }

    public String getFINVRHED_MANUREF() {
        return FINVRHED_MANUREF;
    }

    public void setFINVRHED_MANUREF(String fINVRHED_MANUREF) {
        FINVRHED_MANUREF = fINVRHED_MANUREF;
    }

    public String getFINVRHED_IS_ACTIVE() {
        return FINVRHED_IS_ACTIVE;
    }

    public void setFINVRHED_IS_ACTIVE(String fINVRHED_IS_ACTIVE) {
        FINVRHED_IS_ACTIVE = fINVRHED_IS_ACTIVE;
    }

    public String getFINVRHED_IS_SYNCED() {
        return FINVRHED_IS_SYNCED;
    }

    public void setFINVRHED_IS_SYNCED(String fINVRHED_IS_SYNCED) {
        FINVRHED_IS_SYNCED = fINVRHED_IS_SYNCED;
    }

    public String getFINVRHED_COSTCODE() {
        return FINVRHED_COSTCODE;
    }

    public void setFINVRHED_COSTCODE(String fINVRHED_COSTCODE) {
        FINVRHED_COSTCODE = fINVRHED_COSTCODE;
    }

    public String getFINVRHED_LOCCODE() {
        return FINVRHED_LOCCODE;
    }

    public void setFINVRHED_LOCCODE(String fINVRHED_LOCCODE) {
        FINVRHED_LOCCODE = fINVRHED_LOCCODE;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
