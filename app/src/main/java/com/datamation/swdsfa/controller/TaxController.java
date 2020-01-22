package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Tax;

import java.util.ArrayList;

public class TaxController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbeHelper;
    private String TAG = "TaxDS";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FTAX = "fTax";
    public static final String FTAX_ID = "Id";
    public static final String FTAX_TAXCODE = "TaxCode";
    public static final String FTAX_TAXNAME = "TaxName";
    public static final String FTAX_TAXPER = "TaxPer";
    public static final String FTAX_TAXREGNO = "TaxRegNo";
    public static final String CREATE_FTAX_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FTAX + " (" + FTAX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTAX_TAXCODE + " TEXT, " + FTAX_TAXNAME + " TEXT, " + FTAX_TAXPER + " TEXT, " + FTAX_TAXREGNO + " TEXT ); ";

    public TaxController(Context context) {
        this.context = context;
        dbeHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbeHelper.getWritableDatabase();
    }

    public int createOrUpdateTaxHed(ArrayList<Tax> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (Tax tax : list) {

                ContentValues values = new ContentValues();

                String selectQuery = "SELECT * FROM " + TABLE_FTAX + " WHERE " + FTAX_TAXCODE + " = '" + tax.getTAXCODE() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                values.put(FTAX_TAXCODE, tax.getTAXCODE());
                values.put(FTAX_TAXNAME, tax.getTAXNAME());
                values.put(FTAX_TAXPER, tax.getTAXPER());
                values.put(FTAX_TAXREGNO, tax.getTAXREGNO());

                int cn = cursor.getCount();
                if (cn > 0)
                    count = dB.update(TABLE_FTAX, values, FTAX_TAXCODE + " =?", new String[]{String.valueOf(tax.getTAXCODE())});
                else
                    count = (int) dB.insert(TABLE_FTAX, null, values);

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


    public String getTaxRGNo(String Taxcode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + TABLE_FTAX + " WHERE " + FTAX_TAXCODE + "='" + Taxcode + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(FTAX_TAXREGNO));

            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            //if (cursor != null) {
            //	cursor.close();
            //}
            //dB.close();
        }
        return "";
    }


}
