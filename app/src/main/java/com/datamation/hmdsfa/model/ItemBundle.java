package com.datamation.hmdsfa.model;


import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemBundle {

    @SerializedName("ArticleNo")
    private String ArticleNo;
    @SerializedName("DocumentNo")
    private String DocumentNo;
    @SerializedName("Barcode")
    private String Barcode;
    @SerializedName("Description")
    private String Description;
    @SerializedName("ItemNo")
    private String ItemNo;
    @SerializedName("VariantCode")
    private String VariantCode;
    @SerializedName("VariantColour")
    private String VariantColour;
    @SerializedName("VariantSize")
    private String VariantSize;
    @SerializedName("Quantity")
    private int Quantity;

    private int isChecked;

    public String getArticleNo() {
        return ArticleNo;
    }

    public void setArticleNo(String articleNo) {
        ArticleNo = articleNo;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    public String getDocumentNo() {
        return DocumentNo;
    }

    public void setDocumentNo(String documentNo) {
        DocumentNo = documentNo;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getItemNo() {
        return ItemNo;
    }

    public void setItemNo(String itemNo) {
        ItemNo = itemNo;
    }

    public String getVariantCode() {
        return VariantCode;
    }

    public void setVariantCode(String variantCode) {
        VariantCode = variantCode;
    }

    public String getVariantColour() {
        return VariantColour;
    }

    public void setVariantColour(String variantColour) {
        VariantColour = variantColour;
    }

    public String getVariantSize() {
        return VariantSize;
    }

    public void setVariantSize(String variantSize) {
        VariantSize = variantSize;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
    public static ItemBundle parseItemBundle(JSONObject instance) throws JSONException {

        if (instance != null) {
            ItemBundle itmbundle = new ItemBundle();
            itmbundle.setArticleNo(instance.getString("ArticleNo"));
            itmbundle.setDocumentNo(instance.getString("DocumentNo"));
            itmbundle.setBarcode(instance.getString("Barcode"));
            itmbundle.setDescription(instance.getString("Description"));
            itmbundle.setItemNo(instance.getString("ItemNo"));
            itmbundle.setVariantCode(instance.getString("VariantCode"));
            itmbundle.setVariantColour(instance.getString("VariantColour"));
            itmbundle.setVariantSize(instance.getString("VariantSize"));
            itmbundle.setQuantity(instance.getInt("Quantity"));
            return itmbundle;
        }

        return null;
    }
}
