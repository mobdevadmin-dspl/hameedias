package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.FItenrDet;

import java.util.ArrayList;

/**
 * Created by Rashmi
 */

public class FItenrDetController {
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    private String TAG = "FItenrDetDS";
    // rashmi - 2019-12-24 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FITENRDET = "FItenrDet";
    // table attributes
    public static final String FITENRDET_ID = "FItenrDet_id";
    public static final String FITENRDET_NO_OUTLET = "NoOutlet";
    public static final String FITENRDET_NO_SHCUCAL = "NoShcuCal";
    public static final String FITENRDET_RD_TARGET = "RDTarget";

    public static final String FITENRDET_REMARKS = "Remarks";
    public static final String FITENRDET_ROUTE_CODE = "RouteCode";

    public static final String CREATE_FITENRDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FITENRDET + " (" + FITENRDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FITENRDET_NO_OUTLET + " TEXT, " + FITENRDET_NO_SHCUCAL + " TEXT, " + FITENRDET_RD_TARGET + " TEXT, " + DatabaseHelper.REFNO + " TEXT, " + FITENRDET_REMARKS + " TEXT, " + FITENRDET_ROUTE_CODE + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT); ";

    public FItenrDetController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFItenrDet(ArrayList<FItenrDet> list) {
    Log.d("Iteanery listfor insert",list.toString());
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (FItenrDet fItenrDet : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FITENRDET+ " WHERE " + DatabaseHelper.REFNO + "='" + fItenrDet.getFITENRDET_REF_NO() + "' and "+ DatabaseHelper.TXNDATE + "='" + fItenrDet.getFITENRDET_TXN_DATE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.REFNO,  fItenrDet.getFITENRDET_REF_NO());
                values.put(DatabaseHelper.TXNDATE, fItenrDet.getFITENRDET_TXN_DATE());
                values.put(FITENRDET_NO_OUTLET, fItenrDet.getFITENRDET_NO_OUTLET());
                values.put(FITENRDET_ROUTE_CODE, fItenrDet.getFITENRDET_ROUTE_CODE());
                values.put(FITENRDET_NO_SHCUCAL, fItenrDet.getFITENRDET_NO_SHCUCAL());
                values.put(FITENRDET_REMARKS,fItenrDet.getFITENRDET_REMARKS());
                values.put(FITENRDET_RD_TARGET,fItenrDet.getFITENRDET_RD_TARGET());

                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FITENRDET, values, "", null);
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(TABLE_FITENRDET, null, values);
                    Log.v(TAG, "Inserted " + count);
                }
                cursor.close();
            }

        } catch (Exception e) {
            Log.v(TAG, e.toString());
        } finally {
            dB.close();
        }
        return count;

    }
    public String getRouteFromItenary(String date) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String Rcode = "";

        String selectQuery = "SELECT RouteCode from " + TABLE_FITENRDET + " where " + dbHelper.TXNDATE +" ='" + date + "'";
        System.out.println(TAG + selectQuery);
        Cursor cursor = null;

        cursor = dB.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0)
            while (cursor.moveToNext()) {
                Rcode = cursor.getString(cursor.getColumnIndex(FITENRDET_ROUTE_CODE));
            }
        return Rcode;


    }
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
            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FITENRDET, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FITENRDET, null, null);
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
}
