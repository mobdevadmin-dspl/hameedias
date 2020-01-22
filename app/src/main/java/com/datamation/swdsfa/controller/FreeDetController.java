package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.FreeDet;

import java.util.ArrayList;
import java.util.List;

public class FreeDetController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "deletx";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    // table
    public static final String TABLE_FFREEDET = "Ffreedet";
    // table attributes
    public static final String FFREEDET_ID = "Ffreedet_id";

    public static final String FFREEDET_ITEM_CODE = "Itemcode";
    public static final String FFREEDET_RECORD_ID = "RecordId";

    // create String
    public static final String CREATE_FFREEDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FFREEDET + " (" + FFREEDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + FFREEDET_ITEM_CODE + " TEXT, " + FFREEDET_RECORD_ID + " TEXT); ";

    public static final String IDXFREEDET = "CREATE UNIQUE INDEX IF NOT EXISTS idxfreedet_something ON " + TABLE_FFREEDET + " (" + DatabaseHelper.REFNO + ", " + FFREEDET_ITEM_CODE + ")";

    public FreeDetController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFreeDet(ArrayList<FreeDet> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        Cursor cursor_ini = null;
        try {
            cursor_ini = dB.rawQuery("SELECT * FROM " + TABLE_FFREEDET, null);

            for (FreeDet freedet : list) {

                ContentValues values = new ContentValues();

                values.put(dbHelper.REFNO, freedet.getFFREEDET_REFNO());
                values.put(FFREEDET_ITEM_CODE, freedet.getFFREEDET_ITEM_CODE());
                values.put(FFREEDET_RECORD_ID, freedet.getFFREEDET_RECORD_ID());

                if (cursor_ini.getCount() > 0) {
                    String selectQuery = "SELECT * FROM " + TABLE_FFREEDET + " WHERE " + dbHelper.REFNO + "='" + freedet.getFFREEDET_REFNO() + "' AND " + FFREEDET_ITEM_CODE + " = '" + freedet.getFFREEDET_ITEM_CODE() + "'";
                    cursor = dB.rawQuery(selectQuery, null);

                    if (cursor.getCount() > 0) {
                        count = (int) dB.update(TABLE_FFREEDET, values, dbHelper.REFNO + "='" + freedet.getFFREEDET_REFNO() + "' AND " + FFREEDET_ITEM_CODE + " = '" + freedet.getFFREEDET_ITEM_CODE() + "'", null);
                    } else {
                        count = (int) dB.insert(TABLE_FFREEDET, null, values);
                    }

                } else {
                    count = (int) dB.insert(TABLE_FFREEDET, null, values);
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

    public int getAssoCountByRefno(String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT count(*) FROM " + TABLE_FFREEDET + " WHERE " + dbHelper.REFNO + "='" + refno + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getInt(0);

        }
        return 0;

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

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FFREEDET, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FFREEDET, null, null);
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

    public List<String> getAssortByRefno(String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        List<String> AssortList = new ArrayList<String>();

        String selectQuery = "SELECT * FROM " + TABLE_FFREEDET + " WHERE " + dbHelper.REFNO + "='" + refno + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                AssortList.add(cursor.getString(cursor.getColumnIndex(FFREEDET_ITEM_CODE)));
            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return AssortList;

    }


}
