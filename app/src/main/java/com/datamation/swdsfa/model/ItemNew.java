package com.datamation.swdsfa.model;

import android.net.Uri;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TaZ on 1/5/15.
 * Model class to hold Item details
 */
public class ItemNew implements Serializable {

    private int itemNo, sequenceNo, categoryId, typeId, unitQty;
    private String unit;
    private String itemName, itemShortName, itemWeight, packaging;
    private double stockQty;
    private double wholesalePrice, consumerPrice;
    private String imageUri;
    private boolean hasFlavours;
    //private List<Flavour> flavours;
    private int freeIssue;
    private boolean free;
    private double freeIssueAmount;

    private double selectedReturnPrice;

   // private Flavour flavour;

    private double selectedQty;
    private int returnQty;

    private String localPath;

    public ItemNew() {}

    public ItemNew(int itemNo, int sequenceNo, int categoryId, int typeId, String itemName, String itemWeight, String packaging, int stockQty, double wholesalePrice, double consumerPrice, String imageUri, boolean hasFlavours, String unit, int unitQty) {
        this.itemNo = itemNo;
        this.sequenceNo = sequenceNo;
        this.categoryId = categoryId;
        this.typeId = typeId;
        this.itemName = itemName;
        this.itemWeight = itemWeight;
        this.packaging = packaging;
        this.stockQty = stockQty;
        this.wholesalePrice = wholesalePrice;
        this.consumerPrice = consumerPrice;
        this.imageUri = imageUri;
     //   this.flavours = flavours;
        this.unit = unit;
        this.unitQty = unitQty;
        this.selectedReturnPrice = wholesalePrice/unitQty;
    }

    public double getFreeIssueAmount() {
        return freeIssueAmount;
    }

    public void setFreeIssueAmount(double freeIssueAmount) {
        this.freeIssueAmount = freeIssueAmount;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public int getFreeIssue() {
        return freeIssue;
    }

    public void setFreeIssue(int freeIssue) {
        this.freeIssue = freeIssue;
    }

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemShortName() {
        return itemShortName;
    }

    public void setItemShortName(String itemShortName) {
        this.itemShortName = itemShortName;
    }

    public String getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(String itemWeight) {
        this.itemWeight = itemWeight;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public double getStockQty() {
        return stockQty;
    }

    public void setStockQty(double stockQty) {
        this.stockQty = stockQty;
    }

    public double getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public double getConsumerPrice() {
        return consumerPrice;
    }

    public void setConsumerPrice(double consumerPrice) {
        this.consumerPrice = consumerPrice;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public boolean hasFlavours() {
        return hasFlavours;
    }

    public void setHasFlavours(boolean hasFlavours) {
        this.hasFlavours = hasFlavours;
    }

//    public List<Flavour> getFlavours() {
//        return flavours;
//    }
//
//    public Flavour getFlavour() {
//        return flavour;
//    }
//
//    public void setFlavour(Flavour flavour) {
//        this.flavour = flavour;
//    }
//
//    public void setFlavours(List<Flavour> flavours) {
//        this.flavours = flavours;
//    }

//    public void setFlavour(Flavour flavour) {
//        List<Flavour> flavourList = new ArrayList<Flavour>();
//        flavourList.add(flavour);
//        this.flavours = flavourList;
//    }

    public double getSelectedQty() {
        return selectedQty;
    }

    public void setSelectedQty(double selectedQty) {
        this.selectedQty = selectedQty;
    }

    public int getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(int returnQty) {
        this.returnQty = returnQty;
    }

    public int getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(int unitQty) {
        this.unitQty = unitQty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getSelectedReturnPrice() {
        return selectedReturnPrice;
    }

    public void setSelectedReturnPrice(double selectedReturnPrice) {
        this.selectedReturnPrice = selectedReturnPrice;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(itemNo).append(". ");
        sb.append(itemName);
//        if(flavour != null && flavour.getFlavourName().length() > 0){
//            sb.append(" - ").append(flavour.getFlavourName());
//        }
        return sb.toString();
    }

//    public static Item parseItem(JSONObject instance, int categoryId) throws JSONException {
//
//        if(instance != null) {
//            Item item = new Item();
//            item.setItemNo(instance.getInt("iditem"));
//            item.setCategoryId(categoryId);
//            item.setItemName(instance.getString("item_name"));
//            item.setItemWeight("N/A");
//            item.setPackaging("N/A");
//            item.setStockQty(100);
//            item.setWholesalePrice(instance.getDouble("ip_whole_price"));
//            item.setConsumerPrice(instance.getDouble("ip_price_visible"));
//
//            return item;
//        }
//
//        return null;
//    }

    public static ItemNew parseItem(JSONObject instance, int categoryId) throws JSONException {

        if(instance != null) {
            ItemNew item = new ItemNew();
            item.setItemNo(instance.getInt("product_id"));
            item.setCategoryId(categoryId);
            item.setItemName(instance.getString("product_type"));
            item.setItemShortName(instance.getString("tab_name"));
            item.setTypeId(instance.getInt("product_type_id"));
            item.setItemWeight(instance.getString("weight"));
            item.setPackaging(item.getItemWeight() + "x" + instance.getString("unit_in_box") + "x" + instance.getString("box_no_in_carton"));
            item.setStockQty(0);
            item.setWholesalePrice(instance.getDouble("dealer_price"));
            item.setConsumerPrice(instance.getDouble("consumer_price"));
            item.setImageUri(instance.getString("image_name"));
            item.setUnit(instance.getString("unit"));
            item.setUnitQty(instance.getInt("unit_in_box"));
//            item.setUnitQty(12);
            item.setSequenceNo(instance.getInt("display_seqence"));
//            item.setSequenceNo(0);
            if(item.getImageUri() != null) {
                item.setLocalPath(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/CHELCEY/Products" + item.getImageUri())).toString());
            }

//            JSONArray flavoursArray = instance.getJSONArray("Flavors");
//            if(flavoursArray.length() > 0) {
//                item.setHasFlavours(true);
//
//                List<Flavour> flavourList = new ArrayList<>();
//
//                for(int i=0; i<flavoursArray.length(); i++) {
//                    flavourList.add(new Flavour(flavoursArray.getJSONObject(i).getInt("product_flavour_id"), null, null));
//                }
//
//                item.setFlavours(flavourList);
//
//            }

            return item;
        }

        return null;
    }

}
