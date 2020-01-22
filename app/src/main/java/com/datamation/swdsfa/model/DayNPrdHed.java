package com.datamation.swdsfa.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DayNPrdHed implements Serializable
{

    @Override
    public String toString() {
        return "DayNPrdHed{" +
                "ConsoleDB='" + ConsoleDB + '\'' +
                ", DistDB='" + DistDB + '\'' +
                ", NextNumVal='" + NextNumVal + '\'' +
                ", NONPRDHED_REFNO='" + NONPRDHED_REFNO + '\'' +
                ", NONPRDHED_TXNDATE='" + NONPRDHED_TXNDATE + '\'' +
                ", NONPRDHED_DEALCODE='" + NONPRDHED_DEALCODE + '\'' +
                ", NONPRDHED_REPCODE='" + NONPRDHED_REPCODE + '\'' +
                ", NONPRDHED_REMARKS='" + NONPRDHED_REMARKS + '\'' +
                ", NONPRDHED_COSTCODE='" + NONPRDHED_COSTCODE + '\'' +
                ", NONPRDHED_ADDUSER='" + NONPRDHED_ADDUSER + '\'' +
                ", NONPRDHED_ADDDATE='" + NONPRDHED_ADDDATE + '\'' +
                ", NONPRDHED_ADDMACH='" + NONPRDHED_ADDMACH + '\'' +
                ", NONPRDHED_TRANSBATCH='" + NONPRDHED_TRANSBATCH + '\'' +
                ", NONPRDHED_IS_SYNCED='" + NONPRDHED_IS_SYNCED + '\'' +
                ", NONPRDHED_ADDRESS='" + NONPRDHED_ADDRESS + '\'' +
                ", NONPRDHED_LONGITUDE='" + NONPRDHED_LONGITUDE + '\'' +
                ", NONPRDHED_LATITUDE='" + NONPRDHED_LATITUDE + '\'' +
                ", NONPRDHED_DEBCODE='" + NONPRDHED_DEBCODE + '\'' +
                ", NONPRDHED_IS_ACTIVE='" + NONPRDHED_IS_ACTIVE + '\'' +
                ", NONPRDHED_REASON='" + NONPRDHED_REASON + '\'' +
                ", NonPrdDet=" + NonPrdDet +
                '}';
    }

    private String ConsoleDB;
    private String DistDB;
    private String NextNumVal;

    private String NONPRDHED_REFNO;
    private String NONPRDHED_TXNDATE;
    private String NONPRDHED_DEALCODE;
    private String NONPRDHED_REPCODE;
    private String NONPRDHED_REMARKS;
    private String NONPRDHED_COSTCODE;
    private String NONPRDHED_ADDUSER;
    private String NONPRDHED_ADDDATE;
    private String NONPRDHED_ADDMACH;
    private String NONPRDHED_TRANSBATCH;
    private String NONPRDHED_IS_SYNCED;
    private String NONPRDHED_ADDRESS;
    private String NONPRDHED_LONGITUDE;
    private String NONPRDHED_LATITUDE;
    private String NONPRDHED_DEBCODE;
    private String NONPRDHED_IS_ACTIVE;
    private String NONPRDHED_REASON;
    private ArrayList<DayNPrdDet>NonPrdDet;

    // rashmi - 2019-12-17 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_DAYEXPHED = "DayExpHed";
    // table attributes
    public static final String FDAYEXPHED_ID = "FDayExpHed_id";
    public static final String FDAYEXPHED_REPNAME = "RepName";
    public static final String FDAYEXPHED_COSTCODE = "CostCode";
    public static final String FDAYEXPHED_REMARKS = "Remarks";
    public static final String FDAYEXPHED_AREACODE = "AreaCode";
    public static final String FDAYEXPHED_ADDUSER = "AddUser";
    public static final String FDAYEXPHED_ADDDATE = "AddDate";
    public static final String FDAYEXPHED_ADDMATCH = "AddMach";
    public static final String FDAYEXPHED_LONGITUDE = "Longitude";
    public static final String FDAYEXPHED_LATITUDE = "Latitude";
    public static final String FDAYEXPHED_ISSYNC = "issync";
    public static final String FDAYEXPHED_ACTIVESTATE = "ActiveState";
    public static final String FDAYEXPHED_TOTAMT = "TotAmt";
    public static final String FDAYEXPHED_ADDRESS = "Address";
    public static final String REFNO = "RefNo";
    public static final String TXNDATE = "TxnDate";
    public static final String REPCODE = "RepCode";
    public static final String DEALCODE = "DealCode";
    public static final String DEBCODE = "DebCode";
    // create String
    public static final String CREATE_DAYEXPHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_DAYEXPHED + " (" + FDAYEXPHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + REFNO + " TEXT, " + TXNDATE + " TEXT, " + FDAYEXPHED_REPNAME + " TEXT, " + DEALCODE + " TEXT, " + FDAYEXPHED_COSTCODE + " TEXT, " + REPCODE + " TEXT, " + FDAYEXPHED_REMARKS + " TEXT, " + FDAYEXPHED_AREACODE + " TEXT, " + FDAYEXPHED_ADDUSER + " TEXT, " + FDAYEXPHED_ADDDATE + " TEXT, " + FDAYEXPHED_ADDMATCH + " TEXT, " + FDAYEXPHED_LONGITUDE + " TEXT," + FDAYEXPHED_LATITUDE + " TEXT," + FDAYEXPHED_ISSYNC + " TEXT," + FDAYEXPHED_ACTIVESTATE + " TEXT," + FDAYEXPHED_TOTAMT + " TEXT," + FDAYEXPHED_ADDRESS + " TEXT); ";

    public ArrayList<DayNPrdDet> getNonPrdDet() {
        return NonPrdDet;
    }

    public void setNonPrdDet(ArrayList<DayNPrdDet> nonPrdDet) {
        NonPrdDet = nonPrdDet;
    }

    public String getNONPRDHED_REASON() {
        return NONPRDHED_REASON;
    }

    public void setNONPRDHED_REASON(String NONPRDHED_REASON) {
        this.NONPRDHED_REASON = NONPRDHED_REASON;
    }

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

    public String getNONPRDHED_LONGITUDE() {
        return NONPRDHED_LONGITUDE;
    }

    public void setNONPRDHED_LONGITUDE(String NONPRDHED_LONGITUDE) {
        this.NONPRDHED_LONGITUDE = NONPRDHED_LONGITUDE;
    }

    public String getNONPRDHED_LATITUDE() {
        return NONPRDHED_LATITUDE;
    }

    public void setNONPRDHED_LATITUDE(String NONPRDHED_LATITUDE) {
        this.NONPRDHED_LATITUDE = NONPRDHED_LATITUDE;
    }

    public String getNONPRDHED_DEBCODE() {
        return NONPRDHED_DEBCODE;
    }

    public void setNONPRDHED_DEBCODE(String NONPRDHED_DEBCODE) {
        this.NONPRDHED_DEBCODE = NONPRDHED_DEBCODE;
    }

    public String getNONPRDHED_IS_ACTIVE() {
        return NONPRDHED_IS_ACTIVE;
    }

    public void setNONPRDHED_IS_ACTIVE(String NONPRDHED_IS_ACTIVE) {
        this.NONPRDHED_IS_ACTIVE = NONPRDHED_IS_ACTIVE;
    }

    public String getNONPRDHED_ADDRESS() {
        return NONPRDHED_ADDRESS;
    }

    public void setNONPRDHED_ADDRESS(String nONPRDHED_ADDRESS) {
        NONPRDHED_ADDRESS = nONPRDHED_ADDRESS;
    }

    public String getNONPRDHED_REFNO() {
        return NONPRDHED_REFNO;
    }

    public void setNONPRDHED_REFNO(String nONPRDHED_REFNO) {
        NONPRDHED_REFNO = nONPRDHED_REFNO;
    }

    public String getNONPRDHED_TXNDATE() {
        return NONPRDHED_TXNDATE;
    }

    public void setNONPRDHED_TXNDATE(String nONPRDHED_TXNDAET) {
        NONPRDHED_TXNDATE = nONPRDHED_TXNDAET;
    }

    public String getNONPRDHED_DEALCODE() {
        return NONPRDHED_DEALCODE;
    }

    public void setNONPRDHED_DEALCODE(String nONPRDHED_DEALCODE) {
        NONPRDHED_DEALCODE = nONPRDHED_DEALCODE;
    }

    public String getNONPRDHED_REPCODE() {
        return NONPRDHED_REPCODE;
    }

    public void setNONPRDHED_REPCODE(String nONPRDHED_REPCODE) {
        NONPRDHED_REPCODE = nONPRDHED_REPCODE;
    }

    public String getNONPRDHED_REMARKS() {
        return NONPRDHED_REMARKS;
    }

    public void setNONPRDHED_REMARKS(String NONPRDHED_REMARKS) {
        this.NONPRDHED_REMARKS = NONPRDHED_REMARKS;
    }

    public String getNONPRDHED_COSTCODE() {
        return NONPRDHED_COSTCODE;
    }

    public void setNONPRDHED_COSTCODE(String nONPRDHED_COSTCODE) {
        NONPRDHED_COSTCODE = nONPRDHED_COSTCODE;
    }

    public String getNONPRDHED_ADDUSER() {
        return NONPRDHED_ADDUSER;
    }

    public void setNONPRDHED_ADDUSER(String nONPRDHED_ADDUSER) {
        NONPRDHED_ADDUSER = nONPRDHED_ADDUSER;
    }

    public String getNONPRDHED_ADDDATE() {
        return NONPRDHED_ADDDATE;
    }

    public void setNONPRDHED_ADDDATE(String nONPRDHED_ADDDATE) {
        NONPRDHED_ADDDATE = nONPRDHED_ADDDATE;
    }

    public String getNONPRDHED_ADDMACH() {
        return NONPRDHED_ADDMACH;
    }

    public void setNONPRDHED_ADDMACH(String nONPRDHED_ADDMACH) {
        NONPRDHED_ADDMACH = nONPRDHED_ADDMACH;
    }

    public String getNONPRDHED_TRANSBATCH() {
        return NONPRDHED_TRANSBATCH;
    }

    public void setNONPRDHED_TRANSBATCH(String nONPRDHED_TRANSBATCH) {
        NONPRDHED_TRANSBATCH = nONPRDHED_TRANSBATCH;
    }

    public String getNONPRDHED_IS_SYNCED() {
        return NONPRDHED_IS_SYNCED;
    }

    public void setNONPRDHED_IS_SYNCED(String nONPRDHED_IS_SYNCED) {
        NONPRDHED_IS_SYNCED = nONPRDHED_IS_SYNCED;
    }

}
