package com.datamation.swdsfa.model;


public class Attendance {

    public String CONSOLE_DB;
    public String DISTRIBUTE_DB;
    private String FTOUR_ID;
    private String FTOUR_DATE;
    private String FTOUR_S_TIME;
    private String FTOUR_F_TIME;
    private String FTOUR_VEHICLE;
    private String FTOUR_S_KM;
    private String FTOUR_F_KM;
    private String FTOUR_ROUTE;
    private String FTOUR_DISTANCE;
    private String FTOUR_IS_SYNCED;
    private String FTOUR_REPCODE;
    private String FTOUR_MAC;
    private String FTOUR_DRIVER;
    private String FTOUR_ASSIST;
    private String FTOUR_STLATITIUDE;
    private String FTOUR_STLONGTITIUDE;
    private String FTOUR_ENDLATITIUDE;
    private String FTOUR_ENDLONGTITIUDE;
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-ATTENDANCE-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    // rashmi - 2019-12-17 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String REPCODE = "RepCode";
    public static final String TABLE_ATTENDANCE = "Attendance";
    public static final String ATTENDANCE_ID = "Id";
    public static final String ATTENDANCE_DATE = "tDate";
    public static final String ATTENDANCE_S_TIME = "StartTime";
    public static final String ATTENDANCE_F_TIME = "EndTime";
    public static final String ATTENDANCE_VEHICLE = "Vehicle";
    public static final String ATTENDANCE_S_KM = "StartKm";
    public static final String ATTENDANCE_F_KM = "EndKm";
    public static final String ATTENDANCE_ROUTE = "Route";
    public static final String ATTENDANCE_DRIVER = "Driver";
    public static final String ATTENDANCE_ASSIST = "Assist";
    public static final String ATTENDANCE_DISTANCE = "Distance";
    public static final String ATTENDANCE_IS_SYNCED = "IsSynced";
    public static final String ATTENDANCE_MAC = "MacAdd";
    public static final String ATTENDANCE_STLATITUDE = "StLtitiude";
    public static final String ATTENDANCE_STLONGTITUDE = "StLongtitiude";
    public static final String ATTENDANCE_ENDLATITUDE = "EndLtitiude";
    public static final String ATTENDANCE_ENDLONGTITUDE = "EndLongtitiude";

    public static final String CREATE_ATTENDANCE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ATTENDANCE + " (" + ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

            ATTENDANCE_DATE + " TEXT, " + ATTENDANCE_S_TIME + " TEXT, " + ATTENDANCE_F_TIME + " TEXT, " + ATTENDANCE_VEHICLE + " TEXT, " + ATTENDANCE_S_KM + " TEXT, " + ATTENDANCE_F_KM + " TEXT, " + ATTENDANCE_DISTANCE + " TEXT, " + ATTENDANCE_IS_SYNCED + " TEXT, "

            + REPCODE + " TEXT, " + ATTENDANCE_DRIVER + " TEXT, " + ATTENDANCE_ASSIST + " TEXT, " + ATTENDANCE_MAC + " TEXT, " + ATTENDANCE_ROUTE + " TEXT," + ATTENDANCE_STLATITUDE + " TEXT, " + ATTENDANCE_STLONGTITUDE + " TEXT," + ATTENDANCE_ENDLATITUDE + " TEXT," + ATTENDANCE_ENDLONGTITUDE + " TEXT ); ";



    public String getFTOUR_ID() {
        return FTOUR_ID;
    }
    public void setFTOUR_ID(String fTOUR_ID) {
        FTOUR_ID = fTOUR_ID;
    }
    public String getFTOUR_DATE() {
        return FTOUR_DATE;
    }
    public void setFTOUR_DATE(String fTOUR_DATE) {
        FTOUR_DATE = fTOUR_DATE;
    }
    public String getFTOUR_S_TIME() {
        return FTOUR_S_TIME;
    }
    public void setFTOUR_S_TIME(String fTOUR_S_TIME) {
        FTOUR_S_TIME = fTOUR_S_TIME;
    }
    public String getFTOUR_F_TIME() {
        return FTOUR_F_TIME;
    }
    public void setFTOUR_F_TIME(String fTOUR_F_TIME) {
        FTOUR_F_TIME = fTOUR_F_TIME;
    }
    public String getFTOUR_VEHICLE() {
        return FTOUR_VEHICLE;
    }
    public void setFTOUR_VEHICLE(String fTOUR_VEHICLE) {
        FTOUR_VEHICLE = fTOUR_VEHICLE;
    }
    public String getFTOUR_S_KM() {
        return FTOUR_S_KM;
    }
    public void setFTOUR_S_KM(String fTOUR_S_KM) {
        FTOUR_S_KM = fTOUR_S_KM;
    }
    public String getFTOUR_F_KM() {
        return FTOUR_F_KM;
    }
    public void setFTOUR_F_KM(String fTOUR_F_KM) {
        FTOUR_F_KM = fTOUR_F_KM;
    }
    public String getFTOUR_ROUTE() {
        return FTOUR_ROUTE;
    }
    public void setFTOUR_ROUTE(String fTOUR_ROUTE) {
        FTOUR_ROUTE = fTOUR_ROUTE;
    }
    public String getFTOUR_DISTANCE() {
        return FTOUR_DISTANCE;
    }
    public void setFTOUR_DISTANCE(String fTOUR_DISTANCE) {
        FTOUR_DISTANCE = fTOUR_DISTANCE;
    }
    public String getFTOUR_IS_SYNCED() {
        return FTOUR_IS_SYNCED;
    }
    public void setFTOUR_IS_SYNCED(String fTOUR_IS_SYNCED) {
        FTOUR_IS_SYNCED = fTOUR_IS_SYNCED;
    }
    public String getFTOUR_REPCODE() {
        return FTOUR_REPCODE;
    }
    public void setFTOUR_REPCODE(String fTOUR_REPCODE) {
        FTOUR_REPCODE = fTOUR_REPCODE;
    }
    public String getFTOUR_MAC() {
        return FTOUR_MAC;
    }
    public void setFTOUR_MAC(String fTOUR_MAC) {
        FTOUR_MAC = fTOUR_MAC;
    }
    public String getFTOUR_DRIVER() {
        return FTOUR_DRIVER;
    }
    public void setFTOUR_DRIVER(String fTOUR_DRIVER) {
        FTOUR_DRIVER = fTOUR_DRIVER;
    }
    public String getFTOUR_ASSIST() {
        return FTOUR_ASSIST;
    }
    public void setFTOUR_ASSIST(String fTOUR_ASSIST) {
        FTOUR_ASSIST = fTOUR_ASSIST;
    }

    public String getFTOUR_STLATITIUDE() {
        return FTOUR_STLATITIUDE;
    }

    public void setFTOUR_STLATITIUDE(String FTOUR_STLATITIDE) {
        this.FTOUR_STLATITIUDE = FTOUR_STLATITIDE;
    }

    public String getFTOUR_STLONGTITIUDE() {
        return FTOUR_STLONGTITIUDE;
    }

    public void setFTOUR_STLONGTITIUDE(String FTOUR_STLONGTITIUDE) {
        this.FTOUR_STLONGTITIUDE = FTOUR_STLONGTITIUDE;
    }

    public String getFTOUR_ENDLATITIUDE() {
        return FTOUR_ENDLATITIUDE;
    }

    public void setFTOUR_ENDLATITIUDE(String FTOUR_ENDLATITIUDE) {
        this.FTOUR_ENDLATITIUDE = FTOUR_ENDLATITIUDE;
    }

    public String getFTOUR_ENDLONGTITIUDE() {
        return FTOUR_ENDLONGTITIUDE;
    }

    public void setFTOUR_ENDLONGTITIUDE(String FTOUR_ENDLONGTITIUDE) {
        this.FTOUR_ENDLONGTITIUDE = FTOUR_ENDLONGTITIUDE;
    }

    public String getCONSOLE_DB() {
        return CONSOLE_DB;
    }

    public void setCONSOLE_DB(String CONSOLE_DB) {
        this.CONSOLE_DB = CONSOLE_DB;
    }

    public String getDISTRIBUTE_DB() {
        return DISTRIBUTE_DB;
    }

    public void setDISTRIBUTE_DB(String DISTRIBUTE_DB) {
        this.DISTRIBUTE_DB = DISTRIBUTE_DB;
    }
}
