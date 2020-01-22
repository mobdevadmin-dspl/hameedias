package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Discslab;

import java.util.ArrayList;

public class DiscslabController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";
    // rashmi - 2019-12-24 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FDISCSLAB = "FDiscslab";
    // table attributes
    public static final String FDISCSLAB_ID = "FDiscdet_id";

    public static final String FDISCSLAB_SEQ_NO = "seqno";
    public static final String FDISCSLAB_QTY_F = "Qtyf";
    public static final String FDISCSLAB_QTY_T = "Qtyt";
    public static final String FDISCSLAB_DIS_PER = "disper";
    public static final String FDISCSLAB_DIS_AMUT = "disamt";
    public static final String FDISCSLAB_RECORD_ID = "RecordId";
    public static final String FDISCSLAB_TIMESTAMP_COLUMN = "timestamp_column";

    // create String
    public static final String CREATE_FDISCSLAB_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISCSLAB + " (" + FDISCSLAB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + FDISCSLAB_SEQ_NO + " TEXT, " + FDISCSLAB_QTY_F + " TEXT, " + FDISCSLAB_QTY_T + " TEXT, " + FDISCSLAB_DIS_PER + " TEXT, " + FDISCSLAB_DIS_AMUT + " TEXT, " + FDISCSLAB_RECORD_ID + " TEXT, " + FDISCSLAB_TIMESTAMP_COLUMN + " TEXT); ";

    public DiscslabController(Context context) {

        this.context = context;
        dbHelper = new DatabaseHelper(context);

    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateDiscslab(ArrayList<Discslab> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (Discslab discslab : list) {

                ContentValues values = new ContentValues();

                values.put(dbHelper.REFNO, discslab.getFDISCSLAB_REF_NO());
                values.put(FDISCSLAB_SEQ_NO, discslab.getFDISCSLAB_SEQ_NO());
                values.put(FDISCSLAB_QTY_F, discslab.getFDISCSLAB_QTY_F());
                values.put(FDISCSLAB_QTY_T, discslab.getFDISCSLAB_QTY_T());
                values.put(FDISCSLAB_DIS_PER, discslab.getFDISCSLAB_DIS_PER());
                values.put(FDISCSLAB_DIS_AMUT, discslab.getFDISCSLAB_DIS_AMUT());
                values.put(FDISCSLAB_RECORD_ID, discslab.getFDISCSLAB_RECORD_ID());
                values.put(FDISCSLAB_TIMESTAMP_COLUMN, discslab.getFDISCSLAB_TIMESTAMP_COLUMN());

                count = (int) dB.insert(TABLE_FDISCSLAB, null, values);

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (cursor != null) {
                cursor.close();
            }
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

            int success = dB.delete(TABLE_FDISCSLAB, null, null);
            Log.v("Success", success + "");
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

    public Discslab getDiscountSlabInfo(String refno, int tQty) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Discslab discSlab = new Discslab();

        String selectQuery = "select * from fdiscslab where refno='" + refno + "' and " + tQty + " between CAST(Qtyf as double) and CAST(Qtyt as double)";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                discSlab.setFDISCSLAB_ID(cursor.getString(cursor.getColumnIndex(FDISCSLAB_ID)));
                discSlab.setFDISCSLAB_REF_NO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
                discSlab.setFDISCSLAB_QTY_F(cursor.getString(cursor.getColumnIndex(FDISCSLAB_QTY_F)));
                discSlab.setFDISCSLAB_QTY_T(cursor.getString(cursor.getColumnIndex(FDISCSLAB_QTY_T)));
                discSlab.setFDISCSLAB_SEQ_NO(cursor.getString(cursor.getColumnIndex(FDISCSLAB_SEQ_NO)));
                discSlab.setFDISCSLAB_DIS_PER(cursor.getString(cursor.getColumnIndex(FDISCSLAB_DIS_PER)));
                discSlab.setFDISCSLAB_DIS_AMUT(cursor.getString(cursor.getColumnIndex(FDISCSLAB_DIS_AMUT)));
                discSlab.setFDISCSLAB_RECORD_ID(cursor.getString(cursor.getColumnIndex(FDISCSLAB_RECORD_ID)));
                discSlab.setFDISCSLAB_TIMESTAMP_COLUMN(cursor.getString(cursor.getColumnIndex(FDISCSLAB_TIMESTAMP_COLUMN)));

            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return discSlab;
    }

}
