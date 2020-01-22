package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.TaxHed;

import java.util.ArrayList;

public class TaxHedController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbeHelper;
    private String TAG = "TaxHedDS";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FTAXHED = "fTaxHed";
    public static final String FTAXHED_ID = "Id";
    public static final String FTAXHED_COMCODE = "TaxComCode";
    public static final String FTAXHED_COMNAME = "TaxComName";
    public static final String FTAXHED_ACTIVE = "Active";
    public static final String CREATE_FTAXHED_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FTAXHED + " (" + FTAXHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTAXHED_COMCODE + " TEXT, " + FTAXHED_COMNAME + " TEXT, " + FTAXHED_ACTIVE + " TEXT ); ";

    public TaxHedController(Context context) {
        this.context = context;
        dbeHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbeHelper.getWritableDatabase();
    }

    public int createOrUpdateTaxHed(ArrayList<TaxHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (TaxHed taxHed : list) {

                ContentValues values = new ContentValues();

                String selectQuery = "SELECT * FROM " + TABLE_FTAXHED + " WHERE " + FTAXHED_COMCODE + " = '" + taxHed.getTAXCOMCODE() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                values.put(FTAXHED_ACTIVE, taxHed.getACTIVE());
                values.put(FTAXHED_COMCODE, taxHed.getTAXCOMCODE());
                values.put(FTAXHED_COMNAME, taxHed.getTAXCOMNAME());

                int cn = cursor.getCount();
                if (cn > 0)
                    count = dB.update(TABLE_FTAXHED, values, FTAXHED_COMCODE + " =?", new String[]{String.valueOf(taxHed.getTAXCOMCODE())});
                else
                    count = (int) dB.insert(TABLE_FTAXHED, null, values);

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
