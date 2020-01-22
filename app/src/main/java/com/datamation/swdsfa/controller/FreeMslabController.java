package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.FreeMslab;

import java.util.ArrayList;

public class FreeMslabController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    // table
    public static final String TABLE_FFREEMSLAB = "FfreeMslab";
    // table attributes
    public static final String FFREEMSLAB_ID = "FfreeMslab_id";

    public static final String FFREEMSLAB_QTY_F = "Qtyf";
    public static final String FFREEMSLAB_QTY_T = "Qtyt";
    public static final String FFREEMSLAB_ITEM_QTY = "ItemQty";
    public static final String FFREEMSLAB_FREE_IT_QTY = "FreeItQty";
    public static final String FFREEMSLAB_ADD_USER = "AddUser";
    public static final String FFREEMSLAB_ADD_DATE = "AddDate";
    public static final String FFREEMSLAB_ADD_MACH = "AddMach";
    public static final String FFREEMSLAB_RECORD_ID = "RecordId";
    public static final String FFREEMSLAB_TIMESTAMP_COLUMN = "timestamp_column";
    public static final String FFREEMSLAB_SEQ_NO = "seqno";

    // create String
    public static final String CREATE_FFREEMSLAB_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FFREEMSLAB + " (" + FFREEMSLAB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + FFREEMSLAB_QTY_F + " TEXT, " + FFREEMSLAB_QTY_T + " TEXT, " + FFREEMSLAB_ITEM_QTY + " TEXT, " + FFREEMSLAB_FREE_IT_QTY + " TEXT, " + FFREEMSLAB_ADD_USER + " TEXT, " + FFREEMSLAB_ADD_DATE + " TEXT, " + FFREEMSLAB_ADD_MACH + " TEXT, " + FFREEMSLAB_RECORD_ID + " TEXT, " + FFREEMSLAB_TIMESTAMP_COLUMN + " TEXT, " + FFREEMSLAB_SEQ_NO + " TEXT); ";

    public static final String IDXFREEMSLAB = "CREATE UNIQUE INDEX IF NOT EXISTS idxfreemslab_something ON " + TABLE_FFREEMSLAB + " (" + DatabaseHelper.REFNO + ", " + FFREEMSLAB_SEQ_NO + ")";


    public FreeMslabController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFreeMslab(ArrayList<FreeMslab> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        Cursor cursor_ini = null;

        try {

            cursor_ini = dB.rawQuery("SELECT * FROM " + TABLE_FFREEMSLAB, null);

            for (FreeMslab freeMslab : list) {
                ContentValues values = new ContentValues();

                values.put(dbHelper.REFNO, freeMslab.getFFREEMSLAB_REFNO());
                values.put(FFREEMSLAB_QTY_F, freeMslab.getFFREEMSLAB_QTY_F());
                values.put(FFREEMSLAB_QTY_T, freeMslab.getFFREEMSLAB_QTY_T());
                values.put(FFREEMSLAB_ITEM_QTY, freeMslab.getFFREEMSLAB_ITEM_QTY());
                values.put(FFREEMSLAB_FREE_IT_QTY, freeMslab.getFFREEMSLAB_FREE_IT_QTY());
                values.put(FFREEMSLAB_ADD_USER, freeMslab.getFFREEMSLAB_ADD_USER());
                values.put(FFREEMSLAB_ADD_DATE, freeMslab.getFFREEMSLAB_ADD_DATE());
                values.put(FFREEMSLAB_ADD_MACH, freeMslab.getFFREEMSLAB_ADD_MACH());
                values.put(FFREEMSLAB_RECORD_ID, freeMslab.getFFREEMSLAB_RECORD_ID());
                values.put(FFREEMSLAB_TIMESTAMP_COLUMN, freeMslab.getFFREEMSLAB_TIMESTAMP_COLUMN());
                values.put(FFREEMSLAB_SEQ_NO, freeMslab.getFFREEMSLAB_SEQ_NO());

                if (cursor_ini.getCount() > 0) {
                    String selectQuery = "SELECT * FROM " + TABLE_FFREEMSLAB + " WHERE " + dbHelper.REFNO + "='" + freeMslab.getFFREEMSLAB_REFNO() + "' AND " + FFREEMSLAB_SEQ_NO + " = '" + freeMslab.getFFREEMSLAB_SEQ_NO() + "'";
                    cursor = dB.rawQuery(selectQuery, null);

                    if (cursor.getCount() > 0) {
                        count = (int) dB.update(TABLE_FFREEMSLAB, values, dbHelper.REFNO + "='" + freeMslab.getFFREEMSLAB_REFNO() + "' AND " + FFREEMSLAB_SEQ_NO + " = '" + freeMslab.getFFREEMSLAB_SEQ_NO() + "'", null);
                    } else {
                        count = (int) dB.insert(TABLE_FFREEMSLAB, null, values);
                    }

                } else {
                    count = (int) dB.insert(TABLE_FFREEMSLAB, null, values);
                }


            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (cursor_ini != null) {
                cursor_ini.close();
            }
            dB.close();
        }
        return count;
    }


    public ArrayList<FreeMslab> getMixDetails(String refno, int tQty) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FreeMslab> list = new ArrayList<FreeMslab>();

        //String selectQuery = "select * from ffreeMslab where refno='" + refno + "' and " + tQty + " between CAST(Qtyf as double) and CAST(Qtyt as double)";

       //get range which quantity include ; ex : if we enter 150 as qty, there is a scheme mix type 1-143 : 8-1
        String selectQuery = "select * from ffreeMslab where refno='" + refno + "' and " + tQty + " >= CAST(Qtyf as double) order by Qtyf desc limit 1";


        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            FreeMslab freeMslab = new FreeMslab();

            freeMslab.setFFREEMSLAB_ID(cursor.getString(cursor.getColumnIndex(FFREEMSLAB_ID)));
            freeMslab.setFFREEMSLAB_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
            freeMslab.setFFREEMSLAB_QTY_F(cursor.getString(cursor.getColumnIndex(FFREEMSLAB_QTY_F)));
            freeMslab.setFFREEMSLAB_QTY_T(cursor.getString(cursor.getColumnIndex(FFREEMSLAB_QTY_T)));
            freeMslab.setFFREEMSLAB_ITEM_QTY(cursor.getString(cursor.getColumnIndex(FFREEMSLAB_ITEM_QTY)));
            freeMslab.setFFREEMSLAB_FREE_IT_QTY(cursor.getString(cursor.getColumnIndex(FFREEMSLAB_FREE_IT_QTY)));
            freeMslab.setFFREEMSLAB_ADD_USER(cursor.getString(cursor.getColumnIndex(FFREEMSLAB_ADD_USER)));
            freeMslab.setFFREEMSLAB_ADD_DATE(cursor.getString(cursor.getColumnIndex(FFREEMSLAB_ADD_DATE)));
            freeMslab.setFFREEMSLAB_ADD_MACH(cursor.getString(cursor.getColumnIndex(FFREEMSLAB_ADD_MACH)));
            freeMslab.setFFREEMSLAB_RECORD_ID(cursor.getString(cursor.getColumnIndex(FFREEMSLAB_RECORD_ID)));
            freeMslab.setFFREEMSLAB_TIMESTAMP_COLUMN(cursor.getString(cursor.getColumnIndex(FFREEMSLAB_TIMESTAMP_COLUMN)));
            freeMslab.setFFREEMSLAB_SEQ_NO(cursor.getString(cursor.getColumnIndex(FFREEMSLAB_SEQ_NO)));

            list.add(freeMslab);

        }

        return list;
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
            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FFREEMSLAB, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FFREEMSLAB, null, null);
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
