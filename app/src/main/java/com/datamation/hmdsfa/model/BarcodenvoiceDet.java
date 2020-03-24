package com.datamation.hmdsfa.model;

import java.io.Serializable;

public class BarcodenvoiceDet implements Serializable {

    private String refno;
    private int id;
    private String type;
    private String itemNo;
    private String barcodeNo;
    private String variantCode;
    private String articleNo;
    private int qty;
    private double price;
    private String IsActive;

    public BarcodenvoiceDet() {}

    public BarcodenvoiceDet(String refno, String type, String itemNo, String barcodeNo, String variantCode, String articleNo,int qty, double price) {
        this.refno = refno;
        this.type = type;
        this.itemNo = itemNo;
        this.barcodeNo = barcodeNo;
        this.variantCode = variantCode;
        this.articleNo = articleNo;
        this.qty = qty;
        this.price = price;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getBarcodeNo() {
        return barcodeNo;
    }

    public void setBarcodeNo(String barcodeNo) {
        this.barcodeNo = barcodeNo;
    }

    public String getVariantCode() {
        return variantCode;
    }

    public void setVariantCode(String variantCode) {
        this.variantCode = variantCode;
    }

    public String getArticleNo() {
        return articleNo;
    }

    public void setArticleNo(String articleNo) {
        this.articleNo = articleNo;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
