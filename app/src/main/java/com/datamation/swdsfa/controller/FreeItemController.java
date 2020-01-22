package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.FreeItem;

import java.util.ArrayList;

public class FreeItemController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************
    // table
    public static final String TABLE_FFREEITEM = "fFreeItem";
    // table attributes
    public static final String FFREEITEM_ID = "fFreeItem_id";

    public static final String FFREEITEM_ITEMCODE = "Itemcode";
    public static final String FFREEITEM_RECORD_ID = "RecordId";

    // create String
    public static final String CREATE_FFREEITEM_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FFREEITEM + " (" + FFREEITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + FFREEITEM_ITEMCODE + " TEXT, " + FFREEITEM_RECORD_ID + " TEXT); ";

    public static final String IDXFREEITEM = "CREATE UNIQUE INDEX IF NOT EXISTS idxfreeitem_something ON " + TABLE_FFREEITEM + " (" + DatabaseHelper.REFNO + ", " + FFREEITEM_ITEMCODE + ")";

    public FreeItemController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFreeItem(ArrayList<FreeItem> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        Cursor cursor_ini = null;

        try {

            cursor_ini = dB.rawQuery("SELECT * FROM " + TABLE_FFREEITEM, null);

            for (FreeItem item : list) {

                ContentValues values = new ContentValues();

                values.put(FFREEITEM_ITEMCODE, item.getFFREEITEM_ITEMCODE());
                values.put(dbHelper.REFNO, item.getFFREEITEM_REFNO());
                values.put(FFREEITEM_RECORD_ID, item.getFFREEITEM_RECORD_ID());

                if (cursor_ini.getCount() > 0) {
                    String selectQuery = "SELECT * FROM " + TABLE_FFREEITEM + " WHERE " + dbHelper.REFNO + "='" + item.getFFREEITEM_REFNO() + "' AND " + FFREEITEM_ITEMCODE + " = '" + item.getFFREEITEM_ITEMCODE() + "'";
                    cursor = dB.rawQuery(selectQuery, null);

                    if (cursor.getCount() > 0) {
                        count = (int) dB.update(TABLE_FFREEITEM, values, dbHelper.REFNO + "='" + item.getFFREEITEM_REFNO() + "' AND " + FFREEITEM_ITEMCODE + " = '" + item.getFFREEITEM_ITEMCODE() + "'", null);
                    } else {
                        count = (int) dB.insert(TABLE_FFREEITEM, null, values);
                    }

                } else {
                    count = (int) dB.insert(TABLE_FFREEITEM, null, values);
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
            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FFREEITEM, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FFREEITEM, null, null);
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

    public ArrayList<FreeItem> getFreeItemssByRefno(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FreeItem> list = new ArrayList<FreeItem>();

        String selectQuery = "select * from ffreeItem where refno='" + refno + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            FreeItem item = new FreeItem();

            item.setFFREEITEM_ID(cursor.getString(cursor.getColumnIndex(FFREEITEM_ID)));
            item.setFFREEITEM_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
            item.setFFREEITEM_ITEMCODE(cursor.getString(cursor.getColumnIndex(FFREEITEM_ITEMCODE)));
            item.setFFREEITEM_RECORD_ID(cursor.getString(cursor.getColumnIndex(FFREEITEM_RECORD_ID)));

            list.add(item);

        }

        return list;
    }
    public ArrayList<String> getFreeItemsByRefno(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<String> list = new ArrayList<String>();

        String selectQuery = "select * from ffreeItem where refno='" + refno + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            String item = cursor.getString(cursor.getColumnIndex(FFREEITEM_ITEMCODE));


            list.add(item);

        }

        return list;
    }
}
