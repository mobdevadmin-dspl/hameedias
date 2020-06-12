package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class SalesPrice {

    @SerializedName("AllowLineDis")
    private String AllowLineDis;
    @SerializedName("EndingDate")
    private String EndingDate;
    @SerializedName("ItemNo")
    private  String ItemNo;
    @SerializedName("Markup")
    private String Markup;
    @SerializedName("PriceInclVat")
    private String PriceInclVat;
    @SerializedName("Profit")
    private String Profit;
    @SerializedName("ProfitLCY")
    private String ProfitLCY;
    @SerializedName("SalesType")
    private String SalesType;
    @SerializedName("StartingDate")
    private String StartingDate;
    @SerializedName("UnitOfMea")
    private String UnitOfMea;
    @SerializedName("UnitPrice")
    private String UnitPrice;
    @SerializedName("UnitPriceInclVat")
    private String UnitPriceInclVat;
    @SerializedName("VarientCode")
    private String VarientCode;


    public String getAllowLineDis() {
        return AllowLineDis;
    }

    public void setAllowLineDis(String allowLineDis) {
        AllowLineDis = allowLineDis;
    }

    public String getEndingDate() {
        return EndingDate;
    }

    public void setEndingDate(String endingDate) {
        EndingDate = endingDate;
    }

    public String getItemNo() {
        return ItemNo;
    }

    public void setItemNo(String itemNo) {
        ItemNo = itemNo;
    }

    public String getMarkup() {
        return Markup;
    }

    public void setMarkup(String markup) {
        Markup = markup;
    }

    public String getPriceInclVat() {
        return PriceInclVat;
    }

    public void setPriceInclVat(String priceInclVat) {
        PriceInclVat = priceInclVat;
    }

    public String getProfit() {
        return Profit;
    }

    public void setProfit(String profit) {
        Profit = profit;
    }

    public String getProfitLCY() {
        return ProfitLCY;
    }

    public void setProfitLCY(String profitLCY) {
        ProfitLCY = profitLCY;
    }

    public String getSalesType() {
        return SalesType;
    }

    public void setSalesType(String salesType) {
        SalesType = salesType;
    }

    public String getStartingDate() {
        return StartingDate;
    }

    public void setStartingDate(String startingDate) {
        StartingDate = startingDate;
    }

    public String getUnitOfMea() {
        return UnitOfMea;
    }

    public void setUnitOfMea(String unitOfMea) {
        UnitOfMea = unitOfMea;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getUnitPriceInclVat() {
        return UnitPriceInclVat;
    }

    public void setUnitPriceInclVat(String unitPriceInclVat) {
        UnitPriceInclVat = unitPriceInclVat;
    }

    public String getVarientCode() {
        return VarientCode;
    }

    public void setVarientCode(String varientCode) {
        VarientCode = varientCode;
    }

    public static SalesPrice parseSalespri(JSONObject instance) throws JSONException {

        if (instance != null) {
            SalesPrice salespri = new SalesPrice();

            salespri.setAllowLineDis("0");
            salespri.setEndingDate("0");
            salespri.setItemNo(instance.getString("ItemNo"));
            salespri.setMarkup("0");
            salespri.setPriceInclVat("0");
            salespri.setProfit("0");
            salespri.setProfitLCY("0");
            salespri.setSalesType("0");
            salespri.setStartingDate("0");
            salespri.setUnitOfMea("0");
            salespri.setUnitPrice(instance.getString("UnitPrice"));
            salespri.setUnitPriceInclVat("0");
            salespri.setVarientCode(instance.getString("VarientCode"));


            return salespri;
        }

        return null;
    }
}
