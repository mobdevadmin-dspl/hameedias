package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Discount {

    @SerializedName("DebCode")
    private String DebCode;
    @SerializedName("DebName")
    private String DebName;
    @SerializedName("LocCode")
    private String LocCode;
    @SerializedName("ProductDis")
    private String ProductDis;
    @SerializedName("ProductGroup")
    private String ProductGroup;
    @SerializedName("RepCode")
    private String RepCode;

    public String getDebCode() {
        return DebCode;
    }

    public void setDebCode(String debCode) {
        DebCode = debCode;
    }

    public String getDebName() {
        return DebName;
    }

    public void setDebName(String debName) {
        DebName = debName;
    }

    public String getLocCode() {
        return LocCode;
    }

    public void setLocCode(String locCode) {
        LocCode = locCode;
    }

    public String getProductDis() {
        return ProductDis;
    }

    public void setProductDis(String productDis) {
        ProductDis = productDis;
    }

    public String getProductGroup() {
        return ProductGroup;
    }

    public void setProductGroup(String productGroup) {
        ProductGroup = productGroup;
    }

    public String getRepCode() {
        return RepCode;
    }

    public void setRepCode(String repCode) {
        RepCode = repCode;
    }
    public static Discount parseDiscounts(JSONObject instance) throws JSONException {

        if (instance != null) {
            Discount discount = new Discount();

            discount.setDebCode(instance.getString("DebCode"));
            discount.setDebName(instance.getString("DebName"));
            discount.setLocCode(instance.getString("LocCode"));
            discount.setProductDis(instance.getString("ProductDis"));
            discount.setProductGroup(instance.getString("ProductGroup"));
            discount.setRepCode(instance.getString("RepCode"));
            return discount;
        }

        return null;
    }
}
