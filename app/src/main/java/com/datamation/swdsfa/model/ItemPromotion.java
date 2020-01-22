package com.datamation.swdsfa.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemPromotion implements Serializable {
    private int item_id;
    private int freeItemId;
    private int freeCount;
    private double discount;
    private List<Batch> batches;

    public ItemPromotion(int item_id,int freeItemId, int freeCount, double discount) {
        this.item_id = item_id;
        this.freeItemId = freeItemId;
        this.freeCount = freeCount;
        this.discount = discount;
    }

    public int getFreeItemId() {
        return freeItemId;
    }

    public void setFreeItemId(int freeItemId) {
        this.freeItemId = freeItemId;
    }

    public int getFreeCount() {
        return freeCount;
    }

    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

    public void addBatche(Batch batche) {
        if(this.batches!=null){this.batches.add(batche);}
        else{this.batches = new ArrayList<>();batches.add(batche);}
    }
    public void removeBatche(Batch batche) {
        if(batche!=null){this.batches.remove(batche);}
//        else{this.batches = new ArrayList<>();batches.add(batche);}
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public static JSONObject toJSON(ItemPromotion itemPromotion){
        HashMap<String,Object> data = new HashMap<>();
        data.put("item_id",itemPromotion.getItem_id());
        data.put("free_item_id",itemPromotion.getFreeItemId());
        data.put("discount",itemPromotion.getDiscount());
        JSONArray array = new JSONArray();
        if (itemPromotion.getBatches()!=null) {
            for (Batch batch:itemPromotion.getBatches()){
                array.put(Batch.getBatchAsJSON(batch));
            }
        }
        data.put("free_issue_batches",array);
        return new JSONObject(data);
    }
}
