package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.FreeSlab;

import java.util.ArrayList;

public class FreeSlabController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    // table
    public static final String TABLE_FFREESLAB = "Ffreeslab";
    // table attributes
    public static final String FFREESLAB_ID = "Ffreeslab_id";

    public static final String FFREESLAB_QTY_F = "Qtyf";
    public static final String FFREESLAB_QTY_T = "Qtyt";
    public static final String FFREESLAB_FITEM_CODE = "fItemCode";
    public static final String FFREESLAB_FREE_QTY = "freeQty";
    public static final String FFREESLAB_ADD_USER = "AddUser";
    public static final String FFREESLAB_ADD_DATE = "AddDate";
    public static final String FFREESLAB_ADD_MACH = "AddMach";
    public static final String FFREESLAB_RECORD_ID = "RecordId";
    public static final String FFREESLAB_TIMESTAP_COLUMN = "timestamp_column";
    public static final String FFREESLAB_SEQ_NO = "seqno";

    // create String
    public static final String CREATE_FFREESLAB_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FFREESLAB + " (" + FFREESLAB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + FFREESLAB_QTY_F + " TEXT, " + FFREESLAB_QTY_T + " TEXT, " + FFREESLAB_FITEM_CODE + " TEXT, " + FFREESLAB_FREE_QTY + " TEXT, " + FFREESLAB_ADD_USER + " TEXT, " + FFREESLAB_ADD_DATE + " TEXT, " + FFREESLAB_ADD_MACH + " TEXT, " + FFREESLAB_RECORD_ID + " TEXT, " + FFREESLAB_TIMESTAP_COLUMN + " TEXT, " + FFREESLAB_SEQ_NO + " TEXT); ";
    public static final String IDXFREESLAB = "CREATE UNIQUE INDEX IF NOT EXISTS idxfreeslab_something ON " + TABLE_FFREESLAB + " (" + DatabaseHelper.REFNO + ", " + FFREESLAB_SEQ_NO + ")";

    public FreeSlabController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFreeSlab(ArrayList<FreeSlab> list) {

        int serverdbID = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        Cursor cursor_ini = null;

        try {

            cursor_ini = dB.rawQuery("SELECT * FROM " + TABLE_FFREESLAB, null);
            // if(cursor_ini.getCount()>0){
            // int success = dB.delete(TABLE_FFREESLAB, null, null);
            // Log.v("Success", success+"");
            // }

            for (FreeSlab freeSlab : list) {

                ContentValues values = new ContentValues();

                values.put(dbHelper.REFNO, freeSlab.getFFREESLAB_REFNO());
                values.put(FFREESLAB_QTY_F, freeSlab.getFFREESLAB_QTY_F());
                values.put(FFREESLAB_QTY_T, freeSlab.getFFREESLAB_QTY_T());
                values.put(FFREESLAB_FITEM_CODE, freeSlab.getFFREESLAB_FITEM_CODE());
                values.put(FFREESLAB_FREE_QTY, freeSlab.getFFREESLAB_FREE_QTY());
                values.put(FFREESLAB_ADD_USER, freeSlab.getFFREESLAB_ADD_USER());
                values.put(FFREESLAB_ADD_DATE, freeSlab.getFFREESLAB_ADD_DATE());
                values.put(FFREESLAB_ADD_MACH, freeSlab.getFFREESLAB_ADD_MACH());
                values.put(FFREESLAB_RECORD_ID, freeSlab.getFFREESLAB_RECORD_ID());
                values.put(FFREESLAB_TIMESTAP_COLUMN, freeSlab.getFFREESLAB_TIMESTAP_COLUMN());
                values.put(FFREESLAB_SEQ_NO, freeSlab.getFFREESLAB_SEQ_NO());

                if (cursor_ini.getCount() > 0) {
                    String selectQuery = "SELECT * FROM " + TABLE_FFREESLAB + " WHERE " + dbHelper.REFNO + "='" + freeSlab.getFFREESLAB_REFNO() + "' AND " + FFREESLAB_SEQ_NO + " = '" + freeSlab.getFFREESLAB_SEQ_NO() + "'";
                    cursor = dB.rawQuery(selectQuery, null);

                    if (cursor.getCount() > 0) {
                        serverdbID = (int) dB.update(TABLE_FFREESLAB, values, dbHelper.REFNO + "='" + freeSlab.getFFREESLAB_REFNO() + "' AND " + FFREESLAB_SEQ_NO + " = '" + freeSlab.getFFREESLAB_SEQ_NO() + "'", null);
                    } else {
                        serverdbID = (int) dB.insert(TABLE_FFREESLAB, null, values);
                    }

                } else {
                    serverdbID = (int) dB.insert(TABLE_FFREESLAB, null, values);
                }
            }
        } catch (Exception e) {

            Log.v("Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (cursor_ini != null) {
                cursor_ini.close();
            }
            dB.close();
        }
        return serverdbID;

    }

    public ArrayList<FreeSlab> getSlabdetails(String refno, int tQty) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FreeSlab> list = new ArrayList<FreeSlab>();

        String selectQuery = "select * from fFreesLab where refno='" + refno + "' and " + tQty + " between CAST(Qtyf as double) and CAST(Qtyt as double)";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            FreeSlab freeSlab = new FreeSlab();

            freeSlab.setFFREESLAB_ID(cursor.getString(cursor.getColumnIndex(FFREESLAB_ID)));
            freeSlab.setFFREESLAB_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
            freeSlab.setFFREESLAB_QTY_F(cursor.getString(cursor.getColumnIndex(FFREESLAB_QTY_F)));
            freeSlab.setFFREESLAB_QTY_T(cursor.getString(cursor.getColumnIndex(FFREESLAB_QTY_T)));
            freeSlab.setFFREESLAB_FITEM_CODE(cursor.getString(cursor.getColumnIndex(FFREESLAB_FITEM_CODE)));
            freeSlab.setFFREESLAB_FREE_QTY(cursor.getString(cursor.getColumnIndex(FFREESLAB_FREE_QTY)));
            freeSlab.setFFREESLAB_ADD_USER(cursor.getString(cursor.getColumnIndex(FFREESLAB_ADD_USER)));
            freeSlab.setFFREESLAB_ADD_DATE(cursor.getString(cursor.getColumnIndex(FFREESLAB_ADD_DATE)));
            freeSlab.setFFREESLAB_ADD_MACH(cursor.getString(cursor.getColumnIndex(FFREESLAB_ADD_MACH)));
            freeSlab.setFFREESLAB_RECORD_ID(cursor.getString(cursor.getColumnIndex(FFREESLAB_RECORD_ID)));
            freeSlab.setFFREESLAB_TIMESTAP_COLUMN(cursor.getString(cursor.getColumnIndex(FFREESLAB_TIMESTAP_COLUMN)));
            freeSlab.setFFREESLAB_SEQ_NO(cursor.getString(cursor.getColumnIndex(FFREESLAB_SEQ_NO)));

            list.add(freeSlab);

        }

        return list;
    }

    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FFREESLAB, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FFREESLAB, null, null);
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
