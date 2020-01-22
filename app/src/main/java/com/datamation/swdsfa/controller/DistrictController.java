package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.District;
import com.datamation.swdsfa.model.Route;

import java.util.ArrayList;

public class DistrictController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "RouteDS";
    // rashmi - 2019-12-18 move from database_helper , because of reduce coding in database helper*******************************************************************************

    // table
    public static final String TABLE_ROUTE = "Route";
    public static final String ROUTE_NAME = "RouteName";
    public static final String ROUTE_CODE = "RouteCode";
    public DistrictController(Context context) {
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
    public int createOrUpdateRoute(ArrayList<Route> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (Route route : list) {

                cursor = dB.rawQuery("SELECT * FROM " + TABLE_ROUTE, null);

                ContentValues values = new ContentValues();


                values.put(ROUTE_NAME, route.getFROUTE_ROUTE_NAME());
                values.put(ROUTE_CODE, route.getFROUTE_ROUTECODE());


                if (cursor.getCount() > 0) {
                    dB.update(TABLE_ROUTE, values, ROUTE_CODE + "=?", new String[]{route.getFROUTE_ROUTECODE().toString()});
                    Log.v("TABLE_ROUTE : ", "Updated");
                } else {
                    count = (int) dB.insert(TABLE_ROUTE, null, values);
                    Log.v("TABLE_ROUTE : ", "Inserted " + count);
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

    public ArrayList<District> getAllDistrict() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<District> list = new ArrayList<District>();

        String selectQuery = "select * from fRoute";
        Cursor cursor=null;
        try {
            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext())
            {
                District district = new District();
                district.setDistrictCode(cursor.getString(cursor.getColumnIndex(ROUTE_CODE)));
                district.setDistrictName(cursor.getString(cursor.getColumnIndex(ROUTE_NAME)));

                list.add(district);

            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}
