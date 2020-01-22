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
import com.datamation.swdsfa.model.FItenrHed;

import java.util.ArrayList;

public class FItenrHedController {
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    private String TAG = "FItenrHedDS";
    // rashmi - 2019-12-24 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FITENRHED = "FItenrHed";
    // table attributes
    public static final String FITENRHED_ID = "FItenrHed_id";
    public static final String FITENRHED_COST_CODE = "CostCode";
    public static final String FITENRHED_MONTH = "Month";

    public static final String FITENRHED_REMARKS1 = "Remarks1";
    public static final String FITENRHED_YEAR = "Year";

    // create String
    public static final String CREATE_FITENRHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FITENRHED + " (" + FITENRHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FITENRHED_COST_CODE + " TEXT, " + DatabaseHelper.DEALCODE + " TEXT, " + FITENRHED_MONTH + " TEXT, " + DatabaseHelper.REFNO + " TEXT, " + FITENRHED_REMARKS1 + " TEXT, " + DatabaseHelper.REPCODE + " TEXT, " + FITENRHED_YEAR + " TEXT); ";

    public FItenrHedController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFItenrHed(ArrayList<FItenrHed> list) {
        Log.d("Iteanery listfor insert",list.toString());
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (FItenrHed fItenrhed : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FITENRHED+ " WHERE " + DatabaseHelper.REFNO+ "='" + fItenrhed.getFITENRHED_REF_NO() + "'", null);

                ContentValues values = new ContentValues();

                values.put(FITENRHED_COST_CODE, fItenrhed.getFITENRHED_COST_CODE());
                values.put(DatabaseHelper.DEALCODE, fItenrhed.getFITENRHED_DEAL_CODE());
                values.put(FITENRHED_MONTH, fItenrhed.getFITENRHED_MONTH());
                values.put(DatabaseHelper.REFNO, fItenrhed.getFITENRHED_REF_NO());
                values.put(FITENRHED_REMARKS1,fItenrhed.getFITENRHED_REMARKS1());
                values.put(DatabaseHelper.REPCODE,fItenrhed.getFITENRHED_REP_CODE());
                values.put(FITENRHED_YEAR,fItenrhed.getFITENRHED_YEAR());

                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FITENRHED, values, "", null);
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(TABLE_FITENRHED, null, values);
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
            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FITENRHED, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FITENRHED, null, null);
                Log.v("Success", success + "");
            }
        } catch (Exception e) {

            Log.v(TAG + " FItenrHedDS", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return count;

    }
}
