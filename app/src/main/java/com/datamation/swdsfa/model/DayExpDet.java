package com.datamation.swdsfa.model;

public class DayExpDet {


    private String EXPDET_DESCRIPTION;
    private String EXPDET_EXPCODE;
    private String EXPDET_AMOUNT;
    private String EXPDET_TXNDATE;
    private String EXPDET_REFNO;

    public String getEXPDET_AMOUNT() {
        return EXPDET_AMOUNT;
    }

    public void setEXPDET_AMOUNT(String EXPDET_AMOUNT) {
        this.EXPDET_AMOUNT = EXPDET_AMOUNT;
    }

    public String getEXPDET_EXPCODE() {
        return EXPDET_EXPCODE;
    }

    public void setEXPDET_EXPCODE(String EXPDET_EXPCODE) {
        this.EXPDET_EXPCODE = EXPDET_EXPCODE;
    }

    public String getEXPDET_REFNO() {
        return EXPDET_REFNO;
    }

    public void setEXPDET_REFNO(String EXPDET_REFNO) {
        this.EXPDET_REFNO = EXPDET_REFNO;
    }

    public String getEXPDET_DESCRIPTION() {
        return EXPDET_DESCRIPTION;
    }

    public void setEXPDET_DESCRIPTION(String EXPDET_DESCRIPTION) {
        this.EXPDET_DESCRIPTION = EXPDET_DESCRIPTION;
    }

    public String getEXPDET_TXNDATE() {
        return EXPDET_TXNDATE;
    }

    public void setEXPDET_TXNDATE(String EXPDET_TXNDATE) {
        this.EXPDET_TXNDATE = EXPDET_TXNDATE;
    }
}
