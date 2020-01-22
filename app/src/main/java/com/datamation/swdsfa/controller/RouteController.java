package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Route;

import java.util.ArrayList;

public class RouteController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "RouteDS";
    // rashmi - 2019-12-18 move from database_helper , because of reduce coding in database helper*******************************************************************************

    // table
    public static final String TABLE_FROUTE = "fRoute";
    // table attributes
    public static final String FROUTE_ID = "route_id";
    public static final String FROUTE_ROUTECODE = "RouteCode";
    public static final String FROUTE_ROUTE_NAME = "RouteName";
    public static final String FROUTE_RECORDID = "RecordId";
    public static final String FROUTE_ADDDATE = "AddDate";
    public static final String FROUTE_ADD_MACH = "AddMach";
    public static final String FROUTE_ADD_USER = "AddUser";
    public static final String FROUTE_AREACODE = "AreaCode";
    public static final String FROUTE_FREQNO = "FreqNo";
    public static final String FROUTE_KM = "Km";
    public static final String FROUTE_MINPROCALL = "MinProcall";
    public static final String FROUTE_RDALORATE = "RDAloRate";
    public static final String FROUTE_RDTARGET = "RDTarget";
    public static final String FROUTE_REMARKS = "Remarks";
    public static final String FROUTE_STATUS = "Status";
    public static final String FROUTE_TONNAGE = "Tonnage";
    public static final String REFNO = "RefNo";
    public static final String TXNDATE = "TxnDate";
    public static final String REPCODE = "RepCode";
    public static final String DEALCODE = "DealCode";
    public static final String DEBCODE = "DebCode";
    // table
    public static final String TABLE_ROUTE = "Route";
    public static final String ROUTE_ID = "RouteID";
    // table attributes
    public static final String ROUTE_NAME = "RouteName";
    public static final String ROUTE_CODE = "RouteCode";
    // create String
    public static final String CREATE_FROUTE_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FROUTE + " (" + FROUTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + REPCODE + " TEXT, " + FROUTE_ROUTECODE + " TEXT, " + FROUTE_ROUTE_NAME + " TEXT, " + FROUTE_RECORDID + " TEXT, " + FROUTE_ADDDATE + " TEXT, " + FROUTE_ADD_MACH + " TEXT, " + FROUTE_ADD_USER + " TEXT, " + FROUTE_AREACODE + " TEXT, " + DEALCODE + " TEXT, " + FROUTE_FREQNO + " TEXT, " + FROUTE_KM + " TEXT, " + FROUTE_MINPROCALL + " TEXT, " + FROUTE_RDALORATE + " TEXT, " + FROUTE_RDTARGET + " TEXT, " + FROUTE_REMARKS + " TEXT, " + FROUTE_STATUS + " TEXT, " + FROUTE_TONNAGE + " TEXT); ";

    public RouteController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    /*
     * insert code
     */
    @SuppressWarnings("static-access")
    public int createOrUpdateFRoute(ArrayList<Route> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (Route route : list) {

                cursor = dB.rawQuery("SELECT * FROM " + TABLE_FROUTE + " WHERE " + FROUTE_ROUTECODE + "='" + route.getFROUTE_ROUTECODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(REPCODE, route.getFROUTE_REPCODE());
                values.put(FROUTE_ROUTECODE, route.getFROUTE_ROUTECODE());
                values.put(FROUTE_ROUTE_NAME, route.getFROUTE_ROUTE_NAME());
                values.put(FROUTE_RECORDID, route.getFROUTE_RECORDID());
                values.put(FROUTE_ADDDATE, route.getFROUTE_ADDDATE());
                values.put(FROUTE_ADD_MACH, route.getFROUTE_ADD_MACH());
                values.put(FROUTE_ADD_USER, route.getFROUTE_ADD_USER());
                values.put(FROUTE_AREACODE, route.getFROUTE_AREACODE());
                values.put(DEALCODE, route.getFROUTE_DEALCODE());
                values.put(FROUTE_FREQNO, route.getFROUTE_FREQNO());
                values.put(FROUTE_KM, route.getFROUTE_KM());
                values.put(FROUTE_MINPROCALL, route.getFROUTE_MINPROCALL());
                values.put(FROUTE_RDALORATE, route.getFROUTE_RDALORATE());
                values.put(FROUTE_RDTARGET, route.getFROUTE_RDTARGET());
                values.put(FROUTE_REMARKS, route.getFROUTE_REMARKS());
                values.put(FROUTE_STATUS, route.getFROUTE_STATUS());
                values.put(FROUTE_TONNAGE, route.getFROUTE_TONNAGE());

                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FROUTE, values, FROUTE_ROUTECODE + "=?", new String[]{route.getFROUTE_ROUTECODE().toString()});
                    Log.v("TABLE_FROUTE : ", "Updated");
                } else {
                    count = (int) dB.insert(TABLE_FROUTE, null, values);
                    Log.v("TABLE_FROUTE : ", "Inserted " + count);
                }

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;

    }

    /*
     * delete code
     */
    @SuppressWarnings("static-access")
    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_ROUTE, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_ROUTE, null, null);
                Log.v("Success", success + "");
            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return count;

    }


    @SuppressWarnings("static-access")
    public String getRouteNameByCode(String code) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + TABLE_FROUTE + " WHERE " + FROUTE_ROUTECODE + "='" + code + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(FROUTE_ROUTE_NAME));

        }

        return "";
    }

    //----------------------------getAllRoute---------------------------------------
    public ArrayList<Route> getRoute() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Route> list = new ArrayList<Route>();

        String selectQuery = "select * from fRoute";
        Cursor cursor = null;
        try {
            cursor = dB.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                Route route = new Route();
                route.setFROUTE_ROUTECODE(cursor.getString(cursor.getColumnIndex(ROUTE_CODE)));
                route.setFROUTE_ROUTE_NAME(cursor.getString(cursor.getColumnIndex(ROUTE_NAME)));

                list.add(route);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
