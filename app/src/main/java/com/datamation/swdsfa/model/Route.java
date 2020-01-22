package com.datamation.swdsfa.model;


import org.json.JSONException;
import org.json.JSONObject;

public class Route {
    private String FROUTE_REPCODE;
    private String FROUTE_ROUTECODE;
    private String FROUTE_ROUTE_NAME;
    private String FROUTE_RECORDID;
    private String FROUTE_ADDDATE;
    private String FROUTE_ADD_MACH;
    private String FROUTE_ADD_USER;
    private String FROUTE_AREACODE;
    private String FROUTE_DEALCODE;
    private String FROUTE_FREQNO;
    private String FROUTE_KM;
    private String FROUTE_MINPROCALL;
    private String FROUTE_RDALORATE;
    private String FROUTE_RDTARGET;
    private String FROUTE_REMARKS;
    private String FROUTE_STATUS;
    private String FROUTE_TONNAGE;

    public String getFROUTE_REPCODE() {
        return FROUTE_REPCODE;
    }

    public void setFROUTE_REPCODE(String fROUTE_REPCODE) {
        FROUTE_REPCODE = fROUTE_REPCODE;
    }

    public String getFROUTE_ROUTECODE() {
        return FROUTE_ROUTECODE;
    }

    public void setFROUTE_ROUTECODE(String fROUTE_ROUTECODE) {
        FROUTE_ROUTECODE = fROUTE_ROUTECODE;
    }

    public String getFROUTE_ROUTE_NAME() {
        return FROUTE_ROUTE_NAME;
    }

    public void setFROUTE_ROUTE_NAME(String fROUTE_ROUTE_NAME) {
        FROUTE_ROUTE_NAME = fROUTE_ROUTE_NAME;
    }

    public String getFROUTE_RECORDID() {
        return FROUTE_RECORDID;
    }

    public void setFROUTE_RECORDID(String fROUTE_RECORDID) {
        FROUTE_RECORDID = fROUTE_RECORDID;
    }

    public String getFROUTE_ADDDATE() {
        return FROUTE_ADDDATE;
    }

    public void setFROUTE_ADDDATE(String fROUTE_ADDDATE) {
        FROUTE_ADDDATE = fROUTE_ADDDATE;
    }

    public String getFROUTE_ADD_MACH() {
        return FROUTE_ADD_MACH;
    }

    public void setFROUTE_ADD_MACH(String fROUTE_ADD_MACH) {
        FROUTE_ADD_MACH = fROUTE_ADD_MACH;
    }

    public String getFROUTE_ADD_USER() {
        return FROUTE_ADD_USER;
    }

    public void setFROUTE_ADD_USER(String fROUTE_ADD_USER) {
        FROUTE_ADD_USER = fROUTE_ADD_USER;
    }

    public String getFROUTE_AREACODE() {
        return FROUTE_AREACODE;
    }

    public void setFROUTE_AREACODE(String fROUTE_AREACODE) {
        FROUTE_AREACODE = fROUTE_AREACODE;
    }

    public String getFROUTE_DEALCODE() {
        return FROUTE_DEALCODE;
    }

    public void setFROUTE_DEALCODE(String fROUTE_DEALCODE) {
        FROUTE_DEALCODE = fROUTE_DEALCODE;
    }

    public String getFROUTE_FREQNO() {
        return FROUTE_FREQNO;
    }

    public void setFROUTE_FREQNO(String fROUTE_FREQNO) {
        FROUTE_FREQNO = fROUTE_FREQNO;
    }

    public String getFROUTE_KM() {
        return FROUTE_KM;
    }

    public void setFROUTE_KM(String fROUTE_KM) {
        FROUTE_KM = fROUTE_KM;
    }

    public String getFROUTE_MINPROCALL() {
        return FROUTE_MINPROCALL;
    }

    public void setFROUTE_MINPROCALL(String fROUTE_MINPROCALL) {
        FROUTE_MINPROCALL = fROUTE_MINPROCALL;
    }

    public String getFROUTE_RDALORATE() {
        return FROUTE_RDALORATE;
    }

    public void setFROUTE_RDALORATE(String fROUTE_RDALORATE) {
        FROUTE_RDALORATE = fROUTE_RDALORATE;
    }

    public String getFROUTE_RDTARGET() {
        return FROUTE_RDTARGET;
    }

    public void setFROUTE_RDTARGET(String fROUTE_RDTARGET) {
        FROUTE_RDTARGET = fROUTE_RDTARGET;
    }

    public String getFROUTE_REMARKS() {
        return FROUTE_REMARKS;
    }

    public void setFROUTE_REMARKS(String fROUTE_REMARKS) {
        FROUTE_REMARKS = fROUTE_REMARKS;
    }

    public String getFROUTE_STATUS() {
        return FROUTE_STATUS;
    }

    public void setFROUTE_STATUS(String fROUTE_STATUS) {
        FROUTE_STATUS = fROUTE_STATUS;
    }

    public String getFROUTE_TONNAGE() {
        return FROUTE_TONNAGE;
    }

    public void setFROUTE_TONNAGE(String fROUTE_TONNAGE) {
        FROUTE_TONNAGE = fROUTE_TONNAGE;
    }


    public static Route parseRoute(JSONObject instance) throws JSONException {

        if (instance != null) {
            Route route = new Route();
            route.setFROUTE_ADDDATE(instance.getString("AddDate"));
            route.setFROUTE_ADD_MACH(instance.getString("AddMach"));
            route.setFROUTE_ADD_USER(instance.getString("AddUser"));
            route.setFROUTE_AREACODE(instance.getString("AreaCode"));
            // route.setFROUTE_DEALCODE(jObject.getString("DealCode"));
            route.setFROUTE_FREQNO(instance.getString("FreqNo"));
            route.setFROUTE_KM(instance.getString("Km"));
            route.setFROUTE_MINPROCALL(instance.getString("MinProcall"));
            route.setFROUTE_RDALORATE(instance.getString("RDAloRate"));
            route.setFROUTE_RDTARGET(instance.getString("RDTarget"));
            route.setFROUTE_REMARKS(instance.getString("Remarks"));
            route.setFROUTE_REPCODE(instance.getString("RepCode"));
            route.setFROUTE_ROUTECODE(instance.getString("RouteCode"));
            route.setFROUTE_ROUTE_NAME(instance.getString("RouteName"));
            route.setFROUTE_STATUS(instance.getString("Status"));
            route.setFROUTE_TONNAGE(instance.getString("Tonnage"));

            return route;
        }

        return null;
    }
}
