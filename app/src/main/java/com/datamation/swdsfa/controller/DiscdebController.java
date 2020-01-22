package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Discdeb;

import java.util.ArrayList;

public class DiscdebController {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";
    // rashmi - 2019-12-24 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FDISCDEB = "FDiscdeb";
    // table attributes
    public static final String FDISCDEB_ID = "FDiscdet_id";

    public static final String FDISCDEB_DEB_CODE = "debcode";
    public static final String FDISCDEB_RECORD_ID = "RecordId";
    public static final String FDISCDEB_TIEMSTAMP_COLUMN = "timestamp_column";

    // create String
    public static final String CREATE_FDISCDEB_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISCDEB + " (" + FDISCDEB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + FDISCDEB_DEB_CODE + " TEXT, " + DiscdetController.FDISCDET_RECORD_ID + " TEXT, " + DiscdetController.FDISCHED_TIEMSTAMP_COLUMN + " TEXT); ";

    public DiscdebController(Context context) {

        this.context = context;
        dbHelper = new DatabaseHelper(context);

    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateDiscdeb(ArrayList<Discdeb> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        Cursor cursor_ini = null;

        try {

            cursor_ini = dB.rawQuery("SELECT * FROM " + TABLE_FDISCDEB, null);

            for (Discdeb discdeb : list) {

                ContentValues values = new ContentValues();

                values.put(dbHelper.REFNO, discdeb.getFDISCDEB_REF_NO());
                values.put(FDISCDEB_DEB_CODE, discdeb.getFDISCDEB_DEB_CODE());
                values.put(FDISCDEB_RECORD_ID, discdeb.getFDISCDEB_RECORD_ID());
                values.put(FDISCDEB_TIEMSTAMP_COLUMN, discdeb.getFDISCDEB_TIEMSTAMP_COLUMN());


                if (cursor_ini.getCount() > 0) {
                    String selectQuery = "SELECT * FROM " + TABLE_FDISCDEB + " WHERE " + dbHelper.REFNO + "='" + discdeb.getFDISCDEB_REF_NO() + "' AND " + FDISCDEB_DEB_CODE + " = '" + discdeb.getFDISCDEB_DEB_CODE() + "'";
                    cursor = dB.rawQuery(selectQuery, null);

                    if (cursor.getCount() > 0) {
                        count = (int) dB.update(TABLE_FDISCDEB, values, dbHelper.REFNO + "='" + discdeb.getFDISCDEB_REF_NO() + "' AND " + FDISCDEB_DEB_CODE + " = '" + discdeb.getFDISCDEB_DEB_CODE() + "'", null);
                    } else {
                        count = (int) dB.insert(TABLE_FDISCDEB, null, values);
                    }

                } else {
                    count = (int) dB.insert(TABLE_FDISCDEB, null, values);
                }

            }
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

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FDISCDEB, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FDISCDEB, null, null);
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
