package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class MonthlyTarget {

    @SerializedName("Repcode")
    String repCode;

    @SerializedName("tarValue")
    double tarValue;

    @SerializedName("tarMonth")
    String tarMonth;

    @SerializedName("tarYear")
    String tarYear;

    public String getRepCode() {
        return repCode;
    }

    public void setRepCode(String repCode) {
        this.repCode = repCode;
    }

    public double getTarValue() {
        return tarValue;
    }

    public void setTarValue(double tarValue) {
        this.tarValue = tarValue;
    }

    public String getTarMonth() {
        return tarMonth;
    }

    public void setTarMonth(String tarMonth) {
        this.tarMonth = tarMonth;
    }

    public String getTarYear() {
        return tarYear;
    }

    public void setTarYear(String tarYear) {
        this.tarYear = tarYear;
    }

    public static MonthlyTarget parseMonthTargets(JSONObject instance) throws JSONException
    {
        if(instance != null)
        {
            MonthlyTarget mt = new MonthlyTarget();

            mt.setRepCode(instance.getString("repCode"));
            mt.setTarMonth(instance.getString("tarMonth"));
            mt.setTarYear(instance.getString("tarYear"));
            mt.setTarValue(instance.getDouble("tarValue"));

            return mt;
        }

        return  null;
    }
}
