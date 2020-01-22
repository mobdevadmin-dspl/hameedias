package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Locations {

    private String FLOCATIONS_ID;
    private String FLOCATIONS_ADD_MACH;
    private String FLOCATIONS_ADD_USER;
    private String FLOCATIONS_LOC_CODE;
    private String FLOCATIONS_LOC_NAME;
    private String FLOCATIONS_LOC_T_CODE;
    private String FLOCATIONS_REP_CODE;

    public String getFLOCATIONS_ID() {
        return FLOCATIONS_ID;
    }

    public void setFLOCATIONS_ID(String fLOCATIONS_ID) {
        FLOCATIONS_ID = fLOCATIONS_ID;
    }

    public String getFLOCATIONS_ADD_MACH() {
        return FLOCATIONS_ADD_MACH;
    }

    public void setFLOCATIONS_ADD_MACH(String fLOCATIONS_ADD_MACH) {
        FLOCATIONS_ADD_MACH = fLOCATIONS_ADD_MACH;
    }

    public String getFLOCATIONS_ADD_USER() {
        return FLOCATIONS_ADD_USER;
    }

    public void setFLOCATIONS_ADD_USER(String fLOCATIONS_ADD_USER) {
        FLOCATIONS_ADD_USER = fLOCATIONS_ADD_USER;
    }

    public String getFLOCATIONS_LOC_CODE() {
        return FLOCATIONS_LOC_CODE;
    }

    public void setFLOCATIONS_LOC_CODE(String fLOCATIONS_LOC_CODE) {
        FLOCATIONS_LOC_CODE = fLOCATIONS_LOC_CODE;
    }

    public String getFLOCATIONS_LOC_NAME() {
        return FLOCATIONS_LOC_NAME;
    }

    public void setFLOCATIONS_LOC_NAME(String fLOCATIONS_LOC_NAME) {
        FLOCATIONS_LOC_NAME = fLOCATIONS_LOC_NAME;
    }

    public String getFLOCATIONS_LOC_T_CODE() {
        return FLOCATIONS_LOC_T_CODE;
    }

    public void setFLOCATIONS_LOC_T_CODE(String fLOCATIONS_LOC_T_CODE) {
        FLOCATIONS_LOC_T_CODE = fLOCATIONS_LOC_T_CODE;
    }

    public String getFLOCATIONS_REP_CODE() {
        return FLOCATIONS_REP_CODE;
    }

    public void setFLOCATIONS_REP_CODE(String fLOCATIONS_REP_CODE) {
        FLOCATIONS_REP_CODE = fLOCATIONS_REP_CODE;
    }
    public static Locations parseLocs(JSONObject instance) throws JSONException {

        if (instance != null) {
            Locations locations = new Locations();
            locations.setFLOCATIONS_ADD_MACH(instance.getString("AddMach"));
            locations.setFLOCATIONS_ADD_USER(instance.getString("AddUser"));
            locations.setFLOCATIONS_LOC_CODE(instance.getString("LocCode"));
            locations.setFLOCATIONS_LOC_NAME(instance.getString("LocName"));
            locations.setFLOCATIONS_LOC_T_CODE(instance.getString("LoctCode"));
            locations.setFLOCATIONS_REP_CODE(instance.getString("RepCode"));
            return locations;
        }

        return null;
    }
}
