package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.RouteDet;

import java.util.ArrayList;

public class RouteDetController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";
    // rashmi - 2019-12-24 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FROUTEDET = "fRouteDet";
    // table attributes
    public static final String FROUTEDET_ID = "fRouteDet_id";
    public static final String FROUTEDET_ROUTE_CODE = "RouteCode";

    // create String
    public static final String CREATE_FROUTEDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FROUTEDET + " (" + FROUTEDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.DEBCODE + " TEXT, " + FROUTEDET_ROUTE_CODE + " TEXT); ";

    public static final String TESTROUTEDET = "CREATE UNIQUE INDEX IF NOT EXISTS idxrutdet_something ON " + TABLE_FROUTEDET + " (" + DatabaseHelper.DEBCODE + "," + FROUTEDET_ROUTE_CODE + ")";

    public RouteDetController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFRouteDet(ArrayList<RouteDet> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            for (RouteDet routeDet : list) {

                cursor = dB.rawQuery("SELECT * FROM " + TABLE_FROUTEDET + " WHERE " + dbHelper.DEBCODE + "='" + routeDet.getFROUTEDET_DEB_CODE() + "' AND " + FROUTEDET_ROUTE_CODE + "='" + routeDet.getFROUTEDET_ROUTE_CODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(dbHelper.DEBCODE, routeDet.getFROUTEDET_DEB_CODE());
                values.put(FROUTEDET_ROUTE_CODE, routeDet.getFROUTEDET_ROUTE_CODE());

                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FROUTEDET, values, dbHelper.DEBCODE + "=? AND " + FROUTEDET_ROUTE_CODE + "=?", new String[]{routeDet.getFROUTEDET_DEB_CODE().toString(), routeDet.getFROUTEDET_ROUTE_CODE().toString()});
                    Log.v("TABLE_FREASON : ", "Updated");
                } else {
                    count = (int) dB.insert(TABLE_FROUTEDET, null, values);
                    Log.v("TABLE_FREASON : ", "Inserted " + count);
                }

            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;
    }

    public void InsertOrReplaceRouteDet(ArrayList<RouteDet> list) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
//            int reccount = 0;
        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_FROUTEDET + " ("+dbHelper.DEBCODE+","+FROUTEDET_ROUTE_CODE+") VALUES (?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (RouteDet routeDet : list) {

                stmt.bindString(1, routeDet.getFROUTEDET_DEB_CODE());
                stmt.bindString(2, routeDet.getFROUTEDET_ROUTE_CODE());


                stmt.execute();
                stmt.clearBindings();
//                reccount += 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dB.setTransactionSuccessful();
            dB.endTransaction();
            dB.close();
        }
//        return reccount;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
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

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FROUTEDET, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FROUTEDET, null, null);
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

    public String getRouteCodeByDebCode(String debCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + TABLE_FROUTEDET +  " WHERE "  + dbHelper.DEBCODE + "='" + debCode + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(FROUTEDET_ROUTE_CODE));

        }

        return "";
    }
}
