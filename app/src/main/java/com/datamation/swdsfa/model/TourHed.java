package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class TourHed {


    private String TOURHED_ID;
    private String TOURHED_REFNO;
    private String TOURHED_MANUREF;
    private String TOURHED_TXNDATE;
    private String TOURHED_LORRYCODE;
    private String TOURHED_ROUTECODE;
    private String TOURHED_AREACODE;
    private String TOURHED_COSTCODE;
    private String TOURHED_REMARKS;
    private String TOURHED_LOCCODEF;
    private String TOURHED_LOCCODE;
    private String TOURHED_REPCODE;
    private String TOURHED_HELPERCODE;
    private String TOURHED_ADDUSER;
    private String TOURHED_ADDMACH;
    private String TOURHED_DRIVERCODE;
    private String TOURHED_VANLOADFLG;
    private String TOURHED_CLSFLG;
    private String TOURHED_TOURTYPE;
    private String TOURHED_DATEFROM;
    private String TOURHED_DATETO;

    public String getTOURHED_DATEFROM() {
        return TOURHED_DATEFROM;
    }

    public void setTOURHED_DATEFROM(String TOURHED_DATEFROM) {
        this.TOURHED_DATEFROM = TOURHED_DATEFROM;
    }

    public String getTOURHED_DATETO() {
        return TOURHED_DATETO;
    }

    public void setTOURHED_DATETO(String TOURHED_DATETO) {
        this.TOURHED_DATETO = TOURHED_DATETO;
    }

    public String getTOURHED_ID() {
        return TOURHED_ID;
    }

    public void setTOURHED_ID(String tOURHED_ID) {
        TOURHED_ID = tOURHED_ID;
    }

    public String getTOURHED_REFNO() {
        return TOURHED_REFNO;
    }

    public void setTOURHED_REFNO(String tOURHED_REFNO) {
        TOURHED_REFNO = tOURHED_REFNO;
    }

    public String getTOURHED_MANUREF() {
        return TOURHED_MANUREF;
    }

    public void setTOURHED_MANUREF(String tOURHED_MANUREF) {
        TOURHED_MANUREF = tOURHED_MANUREF;
    }

    public String getTOURHED_TXNDATE() {
        return TOURHED_TXNDATE;
    }

    public void setTOURHED_TXNDATE(String tOURHED_TXNDATE) {
        TOURHED_TXNDATE = tOURHED_TXNDATE;
    }

    public String getTOURHED_LORRYCODE() {
        return TOURHED_LORRYCODE;
    }

    public void setTOURHED_LORRYCODE(String tOURHED_LORRYCODE) {
        TOURHED_LORRYCODE = tOURHED_LORRYCODE;
    }

    public String getTOURHED_ROUTECODE() {
        return TOURHED_ROUTECODE;
    }

    public void setTOURHED_ROUTECODE(String tOURHED_ROUTECODE) {
        TOURHED_ROUTECODE = tOURHED_ROUTECODE;
    }

    public String getTOURHED_AREACODE() {
        return TOURHED_AREACODE;
    }

    public void setTOURHED_AREACODE(String tOURHED_AREACODE) {
        TOURHED_AREACODE = tOURHED_AREACODE;
    }

    public String getTOURHED_COSTCODE() {
        return TOURHED_COSTCODE;
    }

    public void setTOURHED_COSTCODE(String tOURHED_COSTCODE) {
        TOURHED_COSTCODE = tOURHED_COSTCODE;
    }

    public String getTOURHED_REMARKS() {
        return TOURHED_REMARKS;
    }

    public void setTOURHED_REMARKS(String tOURHED_REMARKS) {
        TOURHED_REMARKS = tOURHED_REMARKS;
    }

    public String getTOURHED_LOCCODEF() {
        return TOURHED_LOCCODEF;
    }

    public void setTOURHED_LOCCODEF(String tOURHED_LOCCODEF) {
        TOURHED_LOCCODEF = tOURHED_LOCCODEF;
    }

    public String getTOURHED_LOCCODE() {
        return TOURHED_LOCCODE;
    }

    public void setTOURHED_LOCCODE(String tOURHED_LOCCODE) {
        TOURHED_LOCCODE = tOURHED_LOCCODE;
    }

    public String getTOURHED_REPCODE() {
        return TOURHED_REPCODE;
    }

    public void setTOURHED_REPCODE(String tOURHED_REPCODE) {
        TOURHED_REPCODE = tOURHED_REPCODE;
    }

    public String getTOURHED_HELPERCODE() {
        return TOURHED_HELPERCODE;
    }

    public void setTOURHED_HELPERCODE(String tOURHED_HELPERCODE) {
        TOURHED_HELPERCODE = tOURHED_HELPERCODE;
    }

    public String getTOURHED_ADDUSER() {
        return TOURHED_ADDUSER;
    }

    public void setTOURHED_ADDUSER(String tOURHED_ADDUSER) {
        TOURHED_ADDUSER = tOURHED_ADDUSER;
    }

    public String getTOURHED_ADDMACH() {
        return TOURHED_ADDMACH;
    }

    public void setTOURHED_ADDMACH(String tOURHED_ADDMACH) {
        TOURHED_ADDMACH = tOURHED_ADDMACH;
    }

    public String getTOURHED_DRIVERCODE() {
        return TOURHED_DRIVERCODE;
    }

    public void setTOURHED_DRIVERCODE(String tOURHED_DRIVERCODE) {
        TOURHED_DRIVERCODE = tOURHED_DRIVERCODE;
    }

    public String getTOURHED_VANLOADFLG() {
        return TOURHED_VANLOADFLG;
    }

    public void setTOURHED_VANLOADFLG(String tOURHED_VANLOADFLG) {
        TOURHED_VANLOADFLG = tOURHED_VANLOADFLG;
    }

    public String getTOURHED_CLSFLG() {
        return TOURHED_CLSFLG;
    }

    public void setTOURHED_CLSFLG(String tOURHED_CLSFLG) {
        TOURHED_CLSFLG = tOURHED_CLSFLG;
    }

    public String getTOURHED_TOURTYPE() {
        return TOURHED_TOURTYPE;
    }

    public void setTOURHED_TOURTYPE(String tOURHED_TOURTYPE) {
        TOURHED_TOURTYPE = tOURHED_TOURTYPE;
    }
    public static TourHed parseTours(JSONObject instance) throws JSONException {

        if (instance != null) {
            TourHed hed = new TourHed();

            hed.setTOURHED_ADDMACH(instance.getString("AddMach"));
            hed.setTOURHED_ADDUSER(instance.getString("AddUser"));
            hed.setTOURHED_AREACODE(instance.getString("AreaCode"));
            hed.setTOURHED_CLSFLG(instance.getString("Clsflg"));
            hed.setTOURHED_COSTCODE(instance.getString("CostCode"));
            hed.setTOURHED_DRIVERCODE(instance.getString("DriverCode"));
            hed.setTOURHED_HELPERCODE(instance.getString("HelperCode"));
            hed.setTOURHED_LOCCODE(instance.getString("LocCode"));
            hed.setTOURHED_LOCCODEF(instance.getString("LocCodeF"));
            hed.setTOURHED_LORRYCODE(instance.getString("LorryCode"));
            hed.setTOURHED_MANUREF(instance.getString("ManuRef"));
            hed.setTOURHED_REFNO(instance.getString("RefNo"));
            hed.setTOURHED_REMARKS(instance.getString("Remarks"));
            hed.setTOURHED_REPCODE(instance.getString("RepCode"));
            hed.setTOURHED_ROUTECODE(instance.getString("RouteCode"));
            hed.setTOURHED_TOURTYPE(instance.getString("TourType"));
            hed.setTOURHED_TXNDATE(instance.getString("TxnDate"));
            hed.setTOURHED_VANLOADFLG(instance.getString("VanLoadFlg"));
            hed.setTOURHED_DATEFROM(instance.getString("DateFrom"));
            hed.setTOURHED_DATETO(instance.getString("DateTo"));

            return hed;
        }

        return null;
    }

}
