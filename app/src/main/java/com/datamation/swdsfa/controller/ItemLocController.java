package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.FInvRDet;
import com.datamation.swdsfa.model.InvDet;
import com.datamation.swdsfa.model.ItemLoc;
import com.datamation.swdsfa.model.OrderDetail;

import java.util.ArrayList;

public class ItemLocController
{
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "Swadeshi";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    // table
    public static final String TABLE_FITEMLOC = "fItemLoc";
    // table attributes
    public static final String FITEMLOC_ID = "fItemLoc_id";
    public static final String FITEMLOC_ITEM_CODE = "ItemCode";
    public static final String FITEMLOC_LOC_CODE = "LocCode";
    public static final String FITEMLOC_QOH = "QOH";
    public static final String FITEMLOC_RECORD_ID = "RecordId";
    // create String
    public static final String CREATE_FITEMLOC_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FITEMLOC + " (" + FITEMLOC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FITEMLOC_ITEM_CODE + " TEXT, " + FITEMLOC_LOC_CODE + " TEXT, " + FITEMLOC_QOH + " TEXT, "+ FITEMLOC_RECORD_ID + " TEXT); ";

    public static final String TESTITEMLOC = "CREATE UNIQUE INDEX IF NOT EXISTS idxitemloc_something ON " + TABLE_FITEMLOC + " (" + FITEMLOC_ITEM_CODE + "," + FITEMLOC_LOC_CODE + ")";
    // table

    public ItemLocController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public void InsertOrReplaceItemLoc(ArrayList<ItemLoc> list) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_FITEMLOC + " (ItemCode,LocCode,QOH,RecordId) VALUES (?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (ItemLoc itemLoc : list) {

                stmt.bindString(1, itemLoc.getFITEMLOC_ITEM_CODE());
                stmt.bindString(2, itemLoc.getFITEMLOC_LOC_CODE());
                stmt.bindString(3, itemLoc.getFITEMLOC_QOH());
                stmt.bindString(4, itemLoc.getFITEMLOC_RECORD_ID());

                stmt.execute();
                stmt.clearBindings();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dB.setTransactionSuccessful();
            dB.endTransaction();
            dB.close();
        }

    }

    public int deleteAllItemLoc() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FITEMLOC, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FITEMLOC, null, null);
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

    public void UpdateInvoiceQOHInReturn(String RefNo, String Task, String locCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ArrayList<FInvRDet> list = new SalesReturnDetController(context).getAllInvRDetForPrint(RefNo);

            for (FInvRDet item : list) {

                int qoh = 0;

                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FITEMLOC + " WHERE " + FITEMLOC_ITEM_CODE + "='" + item.getFINVRDET_ITEMCODE() + "' AND " + FITEMLOC_LOC_CODE + "='" + locCode + "'", null);
                int Qty = Integer.parseInt(item.getFINVRDET_QTY());

                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {
                        qoh = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FITEMLOC_QOH)));
                    }

                    ContentValues values = new ContentValues();

                    if (Task.equals("+")) {
                        values.put(FITEMLOC_QOH, String.valueOf(qoh + Qty));
                    } else {
                        values.put(FITEMLOC_QOH, String.valueOf(qoh - Qty));
                    }

                    dB.update(TABLE_FITEMLOC, values, FITEMLOC_ITEM_CODE + "=? AND " + FITEMLOC_LOC_CODE + "=?", new String[]{item.getFINVRDET_ITEMCODE(), locCode});

                }

                cursor.close();

            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

    }

    public void UpdateInvoiceQOH(String RefNo, String Task, String locCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ArrayList<InvDet> list = new InvDetController(context).getAllItemsforPrint(RefNo);

            for (InvDet item : list) {

                int qoh = 0;

                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FITEMLOC + " WHERE " + FITEMLOC_ITEM_CODE + "='" + item.getFINVDET_ITEM_CODE() + "' AND " + FITEMLOC_LOC_CODE + "='" + locCode + "'", null);
                int Qty = Integer.parseInt(item.getFINVDET_QTY());

                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {
                        qoh = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FITEMLOC_QOH)));
                    }

                    ContentValues values = new ContentValues();

                    if (Task.equals("+")) {
                        values.put(FITEMLOC_QOH, String.valueOf(qoh + Qty));
                    } else {
                        values.put(FITEMLOC_QOH, String.valueOf(qoh - Qty));
                    }

                    dB.update(TABLE_FITEMLOC, values, FITEMLOC_ITEM_CODE + "=? AND " + FITEMLOC_LOC_CODE + "=?", new String[]{item.getFINVDET_ITEM_CODE(), locCode});

                }

                cursor.close();

            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

    }
//use order as sale in swadeshi..
    public void UpdateOrderQOH(String RefNo, String Task, String locCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            //ArrayList<InvDet> list = new InvDetController(context).getAllItemsforPrint(RefNo);
            ArrayList<OrderDetail> list = new OrderDetailController(context).getAllUnSync(RefNo);//2019-11-18 rashmi

            for (OrderDetail item : list) {

                int qoh = 0;

                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FITEMLOC + " WHERE " + FITEMLOC_ITEM_CODE + "='" + item.getFORDERDET_ITEMCODE() + "' AND " + FITEMLOC_LOC_CODE + "='" + locCode + "'", null);
                int Qty = Integer.parseInt(item.getFORDERDET_QTY());
                String type = item.getFORDERDET_TYPE();


                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {
                        qoh = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FITEMLOC_QOH)));
                    }

                    ContentValues values = new ContentValues();

//                    if (Task.equals("+")) {
//                        values.put(FITEMLOC_QOH, String.valueOf(qoh + Qty));
//                    } else {
//                        values.put(FITEMLOC_QOH, String.valueOf(qoh - Qty));
//                    }

                    if(type.equals("SA")){
                        values.put(FITEMLOC_QOH, String.valueOf(qoh - Qty));
                    }else if(type.equals("UR")){
                        values.put(FITEMLOC_QOH, String.valueOf(qoh + Qty));
                    }else if(type.equals("MR")){
                        values.put(FITEMLOC_QOH, String.valueOf(qoh + Qty));
                    }else if(type.equals("FI")){
                        values.put(FITEMLOC_QOH, String.valueOf(qoh - Qty));
                    }else if(type.equals("FD")){
                        values.put(FITEMLOC_QOH, String.valueOf(qoh - Qty));
                    }

                    dB.update(TABLE_FITEMLOC, values, FITEMLOC_ITEM_CODE + "=? AND " + FITEMLOC_LOC_CODE + "=?", new String[]{item.getFORDERDET_ITEMCODE(), locCode});

                }

                cursor.close();

            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

    }

    public void UpdateOrderQOHInReturn(String RefNo, String Task, String locCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ArrayList<FInvRDet> list = new SalesReturnDetController(context).getAllInvRDetForPrint(RefNo);

            for (FInvRDet item : list) {

                int qoh = 0;

                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FITEMLOC + " WHERE " + FITEMLOC_ITEM_CODE + "='" + item.getFINVRDET_ITEMCODE() + "' AND " + FITEMLOC_LOC_CODE + "='" + locCode + "'", null);
                int Qty = Integer.parseInt(item.getFINVRDET_QTY());

                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext()) {
                        qoh = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FITEMLOC_QOH)));
                    }

                    ContentValues values = new ContentValues();

                    if (Task.equals("+")) {
                        values.put(FITEMLOC_QOH, String.valueOf(qoh + Qty));
                    } else {
                        values.put(FITEMLOC_QOH, String.valueOf(qoh - Qty));
                    }

                    dB.update(TABLE_FITEMLOC, values, FITEMLOC_ITEM_CODE + "=? AND " + FITEMLOC_LOC_CODE + "=?", new String[]{item.getFINVRDET_ITEMCODE(), locCode});

                }

                cursor.close();

            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

    }

}
