package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class RouteDet {

    private String FROUTEDET_ID;
    private String FROUTEDET_DEB_CODE;
    private String FROUTEDET_ROUTE_CODE;

    public String getFROUTEDET_ID() {
        return FROUTEDET_ID;
    }

    public void setFROUTEDET_ID(String fROUTEDET_ID) {
        FROUTEDET_ID = fROUTEDET_ID;
    }

    public String getFROUTEDET_DEB_CODE() {
        return FROUTEDET_DEB_CODE;
    }

    public void setFROUTEDET_DEB_CODE(String fROUTEDET_DEB_CODE) {
        FROUTEDET_DEB_CODE = fROUTEDET_DEB_CODE;
    }

    public String getFROUTEDET_ROUTE_CODE() {
        return FROUTEDET_ROUTE_CODE;
    }

    public void setFROUTEDET_ROUTE_CODE(String fROUTEDET_ROUTE_CODE) {
        FROUTEDET_ROUTE_CODE = fROUTEDET_ROUTE_CODE;
    }
    public static RouteDet parseRoute(JSONObject instance) throws JSONException {

        if (instance != null) {
            RouteDet routeDet = new RouteDet();
            routeDet.setFROUTEDET_DEB_CODE(instance.getString("DebCode"));
            routeDet.setFROUTEDET_ROUTE_CODE(instance.getString("RouteCode"));

            return routeDet;
        }

        return null;
    }
}
