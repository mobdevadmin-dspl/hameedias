package com.datamation.swdsfa.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class ReturnDetail implements Serializable {

    private long orderId;
    private Item item;
    private int /*flavourId,*/ qty;
    private double returnPrice;
    private Batch batch;
    private boolean market;

    public ReturnDetail() {}

    public ReturnDetail(long orderId, Item item, /*int flavourId,*/ int qty,Batch batch) {
        this.orderId = orderId;
        this.item = item;
//        this.flavourId = flavourId;
        this.qty = qty;
        this.returnPrice = batch.getPrice();
        this.batch = batch;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

//    public int getFlavourId() {
//        return flavourId;
//    }
//
//    public void setFlavourId(int flavourId) {
//        this.flavourId = flavourId;
//    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getReturnPrice() {
        return returnPrice;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        returnPrice = batch.getPrice();
        this.batch = batch;
    }

    public boolean isMarket() {
        return market;
    }

    public void setMarket(boolean market) {
        this.market = market;
    }

    public void setReturnPrice(double returnPrice) {
        this.returnPrice = returnPrice;
    }

    public JSONObject getReturnDetailAsJSON() {

        HashMap<String, Object> params = new HashMap<>();
        //params.put("id_item", item.getItemNo());
        params.put("price", batch.getPrice());
        params.put("qty", qty);
        params.put("return_type", market?2:1);
        params.put("batch_no", batch.getBatchNo());
        params.put("batch_id", batch.getBatchId());
        return new JSONObject(params);
    }


}

