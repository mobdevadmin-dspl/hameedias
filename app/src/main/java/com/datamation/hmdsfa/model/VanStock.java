package com.datamation.hmdsfa.model;

//*****kaveesha - 12-06-2020

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class VanStock {

    @SerializedName("Barcode")
    private String Barcode;
    @SerializedName("Item_No")
    private String Item_No;
    @SerializedName("Quantity_Issued")
    private String Quantity_Issued;
    @SerializedName("Salesperson_Code")
    private String Salesperson_Code;
    @SerializedName("To_Location_Code")
    private String To_Location_Code;
    @SerializedName("Variant_Code")
    private String Variant_Code;

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getItem_No() {
        return Item_No;
    }

    public void setItem_No(String item_No) {
        Item_No = item_No;
    }

    public String getQuantity_Issued() {
        return Quantity_Issued;
    }

    public void setQuantity_Issued(String quantity_Issued) {
        Quantity_Issued = quantity_Issued;
    }

    public String getSalesperson_Code() {
        return Salesperson_Code;
    }

    public void setSalesperson_Code(String salesperson_Code) {
        Salesperson_Code = salesperson_Code;
    }

    public String getTo_Location_Code() {
        return To_Location_Code;
    }

    public void setTo_Location_Code(String to_Location_Code) {
        To_Location_Code = to_Location_Code;
    }

    public String getVariant_Code() {
        return Variant_Code;
    }

    public void setVariant_Code(String variant_Code) {
        Variant_Code = variant_Code;
    }

    public static VanStock parseVanStock(JSONObject instance) throws JSONException
    {
        if(instance != null)
        {
            VanStock vanStock = new VanStock();

            vanStock.setBarcode(instance.getString("Barcode"));
            vanStock.setItem_No(instance.getString("Item_No"));
            vanStock.setQuantity_Issued(instance.getString("Quantity_Issued"));
            vanStock.setSalesperson_Code(instance.getString("Salesperson_Code"));
            vanStock.setTo_Location_Code(instance.getString("To_Location_Code"));
            vanStock.setVariant_Code(instance.getString("Variant_Code"));

            return vanStock;
        }

        return  null;
    }
}
