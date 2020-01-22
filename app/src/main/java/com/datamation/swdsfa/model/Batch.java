package com.datamation.swdsfa.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Batch implements Serializable {

//            "batch_id": "1",
//            "product_id": "1",
//            "batch_no": "BT001",
//            "expire_date": "2016-12-29",
//            "status": "1",
//            "added_date": "2015-12-10",
//            "added_time": "10:23:44",
//            "timestamp": "2015-12-10 16:50:43"

    private int batchId;
    private String batchNo;
    private List<PriceRange> priceRanges;
    private int product_id;
    private int qty;
    private String expire_date;
    private double selectedQty;
    private int freeItemCount;
    private double price; // affected price for single item
    private String itemName;

    public Batch(int batchId, String batchNo, int product_id ,int qty,List<PriceRange> priceRanges,String expire_date) {
        this.batchId = batchId;
        this.batchNo = batchNo;
        this.product_id=product_id;
        this.priceRanges = priceRanges;
        this.qty = qty;
        this.expire_date = expire_date;
    }
    public Batch(){}
    public static Batch parseBatch(JSONObject instance) throws JSONException {
        ArrayList<PriceRange> priceRanges = new ArrayList<>();
        JSONArray prices = instance.getJSONArray("price_categories");

        for(int i=0;i<prices.length();i++){
            priceRanges.add(PriceRange.parsePriceRange(prices.getJSONObject(i)));
        }
        return new Batch(
                instance.getInt("batch_id"),
                instance.getString("batch_no"),
                instance.getInt("product_id"),
                instance.getInt("qty"),
                priceRanges,
                instance.getString("expire_date")
        );
    }
    public static JSONObject getBatchAsJSON(Batch batch){
        HashMap<String,Object> data = new HashMap<>();
        data.put("batch_id",batch.getBatchId());
        data.put("qty",batch.getSelectedQty());
        data.put("price",batch.getPrice());
        data.put("free",batch.getFreeItemCount());
        return new JSONObject(data);
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public List<PriceRange> getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(List<PriceRange> priceRanges) {
        this.priceRanges = priceRanges;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getSelectedQty() {
        return selectedQty;
    }

    public void setSelectedQty(double selectedQty) {
        this.selectedQty = selectedQty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Batch batch = (Batch) o;

        return batchId == batch.batchId;

    }

    @Override
    public int hashCode() {
        return batchId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFreeItemCount() {
        return freeItemCount;
    }

    public void setFreeItemCount(int freeItemCount) {
        this.freeItemCount = freeItemCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}

