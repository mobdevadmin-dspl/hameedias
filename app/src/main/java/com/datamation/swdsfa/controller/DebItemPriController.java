package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.DebItemPri;

import java.util.ArrayList;

public class DebItemPriController {


    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "DebItemPriDS";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_DEBITEMPRI = "fDebItemPri";
    public static final String DEBITEMPRI_ID = "Id";
    public static final String DEBITEMPRI_BRANDCODE = "BrandCode";
    public static final String DEBITEMPRI_DISPER = "Disper";

    public static final String CREATE_DEBITEMPRI_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_DEBITEMPRI + " (" + DEBITEMPRI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DEBITEMPRI_BRANDCODE + " TEXT, " + DatabaseHelper.DEBCODE + " TEXT, " + DEBITEMPRI_DISPER + " TEXT); ";

    public DebItemPriController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }


    public int createOrUpdateDebItemPri(ArrayList<DebItemPri> list) {

        int serverdbID = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (DebItemPri hed : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_DEBITEMPRI + " WHERE " + DEBITEMPRI_BRANDCODE + "='" + hed.getBRANDCODE() + "'", null);
                ContentValues values = new ContentValues();

                values.put(DEBITEMPRI_BRANDCODE, hed.getBRANDCODE());
                values.put(DatabaseHelper.DEBCODE, hed.getDEBCODE());
                values.put(DEBITEMPRI_DISPER, hed.getDISPER());

                if (cursor.getCount() > 0) {
                    dB.update(TABLE_DEBITEMPRI, values, DEBITEMPRI_BRANDCODE + "=?", new String[]{hed.getBRANDCODE().toString()});
                } else {
                    dB.insert(TABLE_DEBITEMPRI, null, values);
                }

                cursor.close();
            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return serverdbID;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public double getBrandDiscount(String brand, String debcode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String s = "SELECT disper FROM " + TABLE_DEBITEMPRI + " WHERE " + DEBITEMPRI_BRANDCODE + "='" + brand + "' AND " + DatabaseHelper.DEBCODE + "='" + debcode + "'";
        Cursor cursor = dB.rawQuery(s, null);

        while (cursor.moveToNext()) {
            return cursor.getDouble(cursor.getColumnIndex(DEBITEMPRI_DISPER));

        }
        cursor.close();
        dB.close();
        return 0.0;
    }


}
