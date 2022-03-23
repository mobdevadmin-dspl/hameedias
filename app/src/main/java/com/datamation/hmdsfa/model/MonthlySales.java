package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class MonthlySales {

    @SerializedName("Repcode")
    String repCode;

    @SerializedName("saleValue")
    double salValue;

    @SerializedName("saleMonth")
    String salMonth;

    @SerializedName("saleYear")
    String salYear;

    public String getRepCode() {
        return repCode;
    }

    public void setRepCode(String repCode) {
        this.repCode = repCode;
    }

    public double getSalValue() {
        return salValue;
    }

    public void setSalValue(double salValue) {
        this.salValue = salValue;
    }

    public String getSalMonth() {
        return salMonth;
    }

    public void setSalMonth(String salMonth) {
        this.salMonth = salMonth;
    }

    public String getSalYear() {
        return salYear;
    }

    public void setSalYear(String salYear) {
        this.salYear = salYear;
    }

    public static MonthlySales parseMonthSales(JSONObject instance) throws JSONException
    {
        if(instance != null)
        {
            MonthlySales mt = new MonthlySales();

            mt.setRepCode(instance.getString("Repcode"));
            mt.setSalMonth(instance.getString("saleMonth"));
            mt.setSalYear(instance.getString("saleYear"));
            mt.setSalValue(instance.getDouble("saleValue"));

            return mt;
        }

        return  null;
    }
}
