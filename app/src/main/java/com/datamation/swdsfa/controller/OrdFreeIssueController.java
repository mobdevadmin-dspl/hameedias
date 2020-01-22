package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.OrdFreeIssue;

import java.util.ArrayList;

public class OrdFreeIssueController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "OrdFreeIssueDS";
    // rashmi - 2019-12-18 move from database_helper , because of reduce coding in database helper*******************************************************************************

    /*-*-*-*-*-*-*-*-*-*-*-*-*-FOrdFreeIss table info-*-**-**-**-**-**-**-**-*-*-*-*/

    public static final String TABLE_FORDFREEISS = "FOrdFreeIss";
    public static final String FORDFREEISS_REFNO1 = "RefNo1";
    public static final String FORDFREEISS_ITEMCODE = "ItemCode";
    public static final String FORDFREEISS_QTY = "Qty";

    public static final String CREATE_FORDFREEISS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FORDFREEISS + " (" + DatabaseHelper.REFNO + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT, " + FORDFREEISS_REFNO1 + " TEXT, " + FORDFREEISS_ITEMCODE + " TEXT, " + FORDFREEISS_QTY + " TEXT ); ";

    public OrdFreeIssueController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void UpdateOrderFreeIssue(OrdFreeIssue ordFreeIssue) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.REFNO, ordFreeIssue.getOrdFreeIssue_RefNo());
            values.put(DatabaseHelper.TXNDATE, ordFreeIssue.getOrdFreeIssue_TxnDate());
            values.put(FORDFREEISS_REFNO1, ordFreeIssue.getOrdFreeIssue_RefNo1());
            values.put(FORDFREEISS_ITEMCODE, ordFreeIssue.getOrdFreeIssue_ItemCode());
            values.put(FORDFREEISS_QTY, ordFreeIssue.getOrdFreeIssue_Qty());

            dB.insert(TABLE_FORDFREEISS, null, values);
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {

            dB.close();
        }

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void ClearFreeIssues(String RefNo) {

        int count;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FORDFREEISS + " WHERE " + DatabaseHelper.REFNO + " = '" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                dB.delete(TABLE_FORDFREEISS, DatabaseHelper.REFNO + " = '" + RefNo + "'", null);
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

    }

    public void ClearFreeIssuesForPreSale(String RefNo) {

        int count;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FORDFREEISS + " WHERE " + FORDFREEISS_REFNO1 + " = '" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                dB.delete(TABLE_FORDFREEISS, FORDFREEISS_REFNO1 + " = '" + RefNo + "'", null);
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

    }

    public void RemoveFreeIssue(String RefNo, String itemCode) {

        int count;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FORDFREEISS + " WHERE " + DatabaseHelper.REFNO + " = '" + RefNo + "' AND itemcode='" + itemCode + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                dB.delete(TABLE_FORDFREEISS, DatabaseHelper.REFNO + " = '" + RefNo + "' AND itemcode='" + itemCode + "'", null);
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<OrdFreeIssue> getAllFreeIssues(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<OrdFreeIssue> list = new ArrayList<OrdFreeIssue>();

        try {
            Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FORDFREEISS + " WHERE RefNo1='" + Refno + "'", null);

            while (cursor.moveToNext()) {

                OrdFreeIssue freeIssue = new OrdFreeIssue();

                freeIssue.setOrdFreeIssue_ItemCode(cursor.getString(cursor.getColumnIndex(FORDFREEISS_ITEMCODE)));
                freeIssue.setOrdFreeIssue_Qty(cursor.getString(cursor.getColumnIndex(FORDFREEISS_QTY)));
                freeIssue.setOrdFreeIssue_RefNo1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                freeIssue.setOrdFreeIssue_RefNo(cursor.getString(cursor.getColumnIndex("RefNo1")));
                freeIssue.setOrdFreeIssue_TxnDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
                list.add(freeIssue);
            }
            cursor.close();

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return list;
    }

}
