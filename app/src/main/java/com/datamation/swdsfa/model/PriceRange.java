package com.datamation.swdsfa.model;

import com.datamation.swdsfa.freeissue.FreeIssue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class PriceRange implements Serializable {

//            "price_category_id": "1",
//            "product_id": "1",
//            "batch_id": "1",
//            "unit_id": "9",
//            "starting_point": "1",
//            "end_point": "2",
//            "cheque_value": "261",
//            "cash_value": "261.00",
//            "percentage_cheque": "0.0",
//            "percentage_cash": "0.0",
//            "single_unit_price": "25.00",
//            "pieces": "12",
//            "piece_price": "300.00",
//            "small_unit": "1",
//            "large_unit": "1",
//            "status": "1"

    private int priceRangeId;               // price_category_id
    private int point;                      // starting_point
    private int batch_id;                   // batch_id
    private int product_id;                 // product_id
    private double piecePrice;              // single_unit_price
    private double cashValue;               // cash_value
    private double chequeValue;             // cheque_value
    private double cashValuePercentage;     // percentage_cash
    private double chequeValuePercentage;   // percentage_cheque
    private double unitPrice;               // piece_price

    // These are the units which the item can be bundled
    private int unit;                       // pieces
    private int smallUnit;                  // small_unit
    private int largeUnit;                  // large_unit

    private FreeIssue freeIssue;

    public PriceRange(int priceRangeId, int point, int batch_id, int product_id, double piecePrice, double cashValue, double chequeValue, double cashValuePercentage, double chequeValuePercentage, double unitPrice, int unit, int smallUnit, int largeUnit, FreeIssue freeIssues) {
        this.priceRangeId = priceRangeId;
        this.point = point;
        this.batch_id = batch_id;
        this.product_id = product_id;
        this.piecePrice = piecePrice;
        this.cashValue = cashValue;
        this.chequeValue = chequeValue;
        this.cashValuePercentage = cashValuePercentage;
        this.chequeValuePercentage = chequeValuePercentage;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.smallUnit = smallUnit;
        this.largeUnit = largeUnit;
        this.freeIssue = freeIssues;
    }

    public static PriceRange parsePriceRange(JSONObject instance) throws JSONException {
        FreeIssue freeIssue =null ;
        JSONArray array = instance.getJSONArray("free_issue");

        for(int i=0;i<array.length();i++){
            //freeIssue=FreeIssue.parceFreeIssue((JSONObject) array.get(i));
        }
        return new PriceRange(
                instance.getInt("price_category_id"),
                instance.getInt("starting_point"),
                instance.getInt("batch_id"),
                instance.getInt("product_id"),
                instance.getDouble("single_unit_price"),
                instance.getDouble("cash_value"),
                instance.getDouble("cheque_value"),
                instance.getDouble("percentage_cash"),
                instance.getDouble("percentage_cheque"),
                instance.getDouble("piece_price"),
                instance.getInt("pieces"),
                instance.getInt("small_unit"),
                instance.getInt("large_unit"),
                freeIssue
        );
    }
    public int getPriceRangeId() {
        return priceRangeId;
    }

    public void setPriceRangeId(int priceRangeId) {
        this.priceRangeId = priceRangeId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public double getPiecePrice() {
        return piecePrice;
    }

    public void setPiecePrice(double piecePrice) {
        this.piecePrice = piecePrice;
    }

    public double getCashValue() {
        return cashValue;
    }

    public void setCashValue(double cashValue) {
        this.cashValue = cashValue;
    }

    public double getChequeValue() {
        return chequeValue;
    }

    public void setChequeValue(double chequeValue) {
        this.chequeValue = chequeValue;
    }

    public double getCashValuePercentage() {
        return cashValuePercentage;
    }

    public void setCashValuePercentage(double cashValuePercentage) {
        this.cashValuePercentage = cashValuePercentage;
    }

    public double getChequeValuePercentage() {
        return chequeValuePercentage;
    }

    public void setChequeValuePercentage(double chequeValuePercentage) {
        this.chequeValuePercentage = chequeValuePercentage;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getSmallUnit() {
        return smallUnit;
    }

    public void setSmallUnit(int smallUnit) {
        this.smallUnit = smallUnit;
    }

    public int getLargeUnit() {
        return largeUnit;
    }

    public void setLargeUnit(int largeUnit) {
        this.largeUnit = largeUnit;
    }

    public int getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(int batch_id) {
        this.batch_id = batch_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public FreeIssue getFreeIssue() {
        return freeIssue;
    }

    public void setFreeIssue(FreeIssue freeIssue) {
        this.freeIssue = freeIssue;
    }
}
