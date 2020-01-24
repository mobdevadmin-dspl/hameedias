package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Town {
//    private String townID;

    @SerializedName("TownCode")
    private String townCode;
    @SerializedName("TownName")
    private String townName;

    public Town() {
    }

//    public String getTownID() {
//        return townID;
//    }
//
//    public void setTownID(String townID) {
//        this.townID = townID;
//    }

    public String getTownCode() {
        return townCode;
    }

    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public static Town parseTown(JSONObject instance) throws JSONException {

        if (instance != null) {
            Town town = new Town();

            town.setTownCode(instance.getString("TownCode"));
            town.setTownName(instance.getString("TownName"));
            return town;
        }
        return null;
    }
}
