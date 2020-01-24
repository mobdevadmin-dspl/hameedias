package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class NearDebtor
{
    @SerializedName("Area")
    private String FNEARDEBTOR_AREA;
    @SerializedName("Territory")
    private String FNEARDEBTOR_TERRITORY;
    @SerializedName("Description")
    private String FNEARDEBTOR_DESCRIPTION;
    @SerializedName("Retailer")
    private String FNEARDEBTOR_RETAILER;
    @SerializedName("RetCategory")
    private String FNEARDEBTOR_RETCATEGORY;
    @SerializedName("Address1")
    private String FNEARDEBTOR_ADDRESS;
    @SerializedName("Longitude")
    private String FNEARDEBTOR_LONGI;
    @SerializedName("Latitude")
    private String FNEARDEBTOR_LATI;

    public String getFNEARDEBTOR_AREA() {
        return FNEARDEBTOR_AREA;
    }

    public void setFNEARDEBTOR_AREA(String FNEARDEBTOR_AREA) {
        this.FNEARDEBTOR_AREA = FNEARDEBTOR_AREA;
    }

    public String getFNEARDEBTOR_TERRITORY() {
        return FNEARDEBTOR_TERRITORY;
    }

    public void setFNEARDEBTOR_TERRITORY(String FNEARDEBTOR_TERRITORY) {
        this.FNEARDEBTOR_TERRITORY = FNEARDEBTOR_TERRITORY;
    }

    public String getFNEARDEBTOR_DESCRIPTION() {
        return FNEARDEBTOR_DESCRIPTION;
    }

    public void setFNEARDEBTOR_DESCRIPTION(String FNEARDEBTOR_DESCRIPTION) {
        this.FNEARDEBTOR_DESCRIPTION = FNEARDEBTOR_DESCRIPTION;
    }

    public String getFNEARDEBTOR_RETAILER() {
        return FNEARDEBTOR_RETAILER;
    }

    public void setFNEARDEBTOR_RETAILER(String FNEARDEBTOR_RETAILER) {
        this.FNEARDEBTOR_RETAILER = FNEARDEBTOR_RETAILER;
    }

    public String getFNEARDEBTOR_RETCATEGORY() {
        return FNEARDEBTOR_RETCATEGORY;
    }

    public void setFNEARDEBTOR_RETCATEGORY(String FNEARDEBTOR_RETCATEGORY) {
        this.FNEARDEBTOR_RETCATEGORY = FNEARDEBTOR_RETCATEGORY;
    }

    public String getFNEARDEBTOR_ADDRESS() {
        return FNEARDEBTOR_ADDRESS;
    }

    public void setFNEARDEBTOR_ADDRESS(String FNEARDEBTOR_ADDRESS) {
        this.FNEARDEBTOR_ADDRESS = FNEARDEBTOR_ADDRESS;
    }

    public String getFNEARDEBTOR_LONGI() {
        return FNEARDEBTOR_LONGI;
    }

    public void setFNEARDEBTOR_LONGI(String FNEARDEBTOR_LONGI) {
        this.FNEARDEBTOR_LONGI = FNEARDEBTOR_LONGI;
    }

    public String getFNEARDEBTOR_LATI() {
        return FNEARDEBTOR_LATI;
    }

    public void setFNEARDEBTOR_LATI(String FNEARDEBTOR_LATI) {
        this.FNEARDEBTOR_LATI = FNEARDEBTOR_LATI;
    }

    public static NearDebtor parseNearOutlet(JSONObject instance) throws JSONException {

        if (instance != null) {
            NearDebtor nDebtor = new NearDebtor();
            nDebtor.setFNEARDEBTOR_AREA(instance.getString("Area"));
            nDebtor.setFNEARDEBTOR_TERRITORY(instance.getString("Territory"));
            nDebtor.setFNEARDEBTOR_DESCRIPTION(instance.getString("Description"));
            nDebtor.setFNEARDEBTOR_RETAILER(instance.getString("Retailer"));
            nDebtor.setFNEARDEBTOR_RETCATEGORY(instance.getString("RetCategory"));
            nDebtor.setFNEARDEBTOR_ADDRESS(instance.getString("Address1"));
            nDebtor.setFNEARDEBTOR_LONGI(instance.getString("Longitude"));
            nDebtor.setFNEARDEBTOR_LATI(instance.getString("Latitude"));

            return nDebtor;
        }

        return null;
    }
}
