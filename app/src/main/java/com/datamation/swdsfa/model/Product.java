package com.datamation.swdsfa.model;

public class Product {

    private String FPRODUCT_ID;
    private String FPRODUCT_ITEMCODE;
    private String FPRODUCT_ITEMNAME;
    private String FPRODUCT_PRICE;
    private String FPRODUCT_QOH;
    private String FPRODUCT_QTY;
    private String FPRODUCT_MAX_PRICE;
    private String FPRODUCT_MIN_PRICE;
    private String FPRODUCT_CHANGED_PRICE;
    private String FPRODUCT_TXN_TYPE;

    public String getFPRODUCT_TXN_TYPE() {
        return FPRODUCT_TXN_TYPE;
    }

    public void setFPRODUCT_TXN_TYPE(String FPRODUCT_TXN_TYPE) {
        this.FPRODUCT_TXN_TYPE = FPRODUCT_TXN_TYPE;
    }

    public String getFPRODUCT_CHANGED_PRICE() {
        return FPRODUCT_CHANGED_PRICE;
    }

    public void setFPRODUCT_CHANGED_PRICE(String FPRODUCT_CHANGED_PRICE) {
        this.FPRODUCT_CHANGED_PRICE = FPRODUCT_CHANGED_PRICE;
    }

    public String getFPRODUCT_MAX_PRICE() {
        return FPRODUCT_MAX_PRICE;
    }

    public void setFPRODUCT_MAX_PRICE(String FPRODUCT_MAX_PRICE) {
        this.FPRODUCT_MAX_PRICE = FPRODUCT_MAX_PRICE;
    }

    public String getFPRODUCT_MIN_PRICE() {
        return FPRODUCT_MIN_PRICE;
    }

    public void setFPRODUCT_MIN_PRICE(String FPRODUCT_MIN_PRICE) {
        this.FPRODUCT_MIN_PRICE = FPRODUCT_MIN_PRICE;
    }

    public String getFPRODUCT_ID() {
        return FPRODUCT_ID;
    }

    public void setFPRODUCT_ID(String FPRODUCT_ID) {
        this.FPRODUCT_ID = FPRODUCT_ID;
    }

    public String getFPRODUCT_ITEMCODE() {
        return FPRODUCT_ITEMCODE;
    }

    public void setFPRODUCT_ITEMCODE(String FPRODUCT_ITEMCODE) {
        this.FPRODUCT_ITEMCODE = FPRODUCT_ITEMCODE;
    }

    public String getFPRODUCT_ITEMNAME() {
        return FPRODUCT_ITEMNAME;
    }

    public void setFPRODUCT_ITEMNAME(String FPRODUCT_ITEMNAME) {
        this.FPRODUCT_ITEMNAME = FPRODUCT_ITEMNAME;
    }

    public String getFPRODUCT_PRICE() {
        return FPRODUCT_PRICE;
    }

    public void setFPRODUCT_PRICE(String FPRODUCT_PRICE) {
        this.FPRODUCT_PRICE = FPRODUCT_PRICE;
    }

    public String getFPRODUCT_QOH() {
        return FPRODUCT_QOH;
    }

    public void setFPRODUCT_QOH(String FPRODUCT_QOH) {
        this.FPRODUCT_QOH = FPRODUCT_QOH;
    }

    public String getFPRODUCT_QTY() {
        return FPRODUCT_QTY;
    }

    public void setFPRODUCT_QTY(String FPRODUCT_QTY) {
        this.FPRODUCT_QTY = FPRODUCT_QTY;
    }
}
