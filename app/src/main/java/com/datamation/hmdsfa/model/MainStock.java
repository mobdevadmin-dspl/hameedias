package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class MainStock
{
    @SerializedName("BarCode")
    private String FMAINSTCOK_BARCODE;
    @SerializedName("ItemCode")
    private String FMAINSTCOK_ITEM_CODE;
    @SerializedName("ItemName")
    private String FMAINSTCOK_ITEM_NAME;
    @SerializedName("Quantity")
    private String FMAINSTCOK_QUANTITY;
    @SerializedName("VariantCode")
    private String FMAINSTCOK_VARIANT_CODE;


    public String getFMAINSTCOK_BARCODE() {
        return FMAINSTCOK_BARCODE;
    }

    public void setFMAINSTCOK_BARCODE(String FMAINSTCOK_BARCODE) {
        this.FMAINSTCOK_BARCODE = FMAINSTCOK_BARCODE;
    }

    public String getFMAINSTCOK_ITEM_CODE() {
        return FMAINSTCOK_ITEM_CODE;
    }

    public void setFMAINSTCOK_ITEM_CODE(String FMAINSTCOK_ITEM_CODE) {
        this.FMAINSTCOK_ITEM_CODE = FMAINSTCOK_ITEM_CODE;
    }

    public String getFMAINSTCOK_ITEM_NAME() {
        return FMAINSTCOK_ITEM_NAME;
    }

    public void setFMAINSTCOK_ITEM_NAME(String FMAINSTCOK_ITEM_NAME) {
        this.FMAINSTCOK_ITEM_NAME = FMAINSTCOK_ITEM_NAME;
    }

    public String getFMAINSTCOK_QUANTITY() {
        return FMAINSTCOK_QUANTITY;
    }

    public void setFMAINSTCOK_QUANTITY(String FMAINSTCOK_QUANTITY) {
        this.FMAINSTCOK_QUANTITY = FMAINSTCOK_QUANTITY;
    }

    public String getFMAINSTCOK_VARIANT_CODE() {
        return FMAINSTCOK_VARIANT_CODE;
    }

    public void setFMAINSTCOK_VARIANT_CODE(String FMAINSTCOK_VARIANT_CODE) {
        this.FMAINSTCOK_VARIANT_CODE = FMAINSTCOK_VARIANT_CODE;
    }

    public static MainStock parseMainStock(JSONObject instance) throws JSONException {

        if (instance != null) {
            MainStock mainStock = new MainStock();

            mainStock.setFMAINSTCOK_BARCODE(instance.getString("BarCode"));
            mainStock.setFMAINSTCOK_ITEM_CODE(instance.getString("ItemCode"));
            mainStock.setFMAINSTCOK_ITEM_NAME(instance.getString("ItemName"));
            mainStock.setFMAINSTCOK_QUANTITY(instance.getString("Quantity"));
            mainStock.setFMAINSTCOK_VARIANT_CODE(instance.getString("VariantCode"));

            return mainStock;
        }

        return null;
    }
}
