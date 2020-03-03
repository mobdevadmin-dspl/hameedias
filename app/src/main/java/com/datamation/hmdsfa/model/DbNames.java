package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pubudu on 11/13/2017.
 */

public class DbNames {
    @SerializedName("Name")
    private String DbName;

    public String getDbName() {
        return DbName;
    }

    public void setDbName(String dbName) {
        DbName = dbName;
    }
}
