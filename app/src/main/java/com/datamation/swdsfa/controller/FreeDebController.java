package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Customer;
import com.datamation.swdsfa.model.FreeDeb;

import java.util.ArrayList;

public class FreeDebController {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    // table
    public static final String TABLE_FFREEDEB = "Ffreedeb";
    // table attributes
    public static final String FFREEDEB_ID = "Ffreedeb_id";

    public static final String FFREEDEB_ADD_USER = "AddUser";
    public static final String FFREEDEB_ADD_DATE = "AddDate";
    public static final String FFREEDEB_ADD_MACH = "AddMach";
    public static final String FFREEDEB_RECORD_ID = "RecordId";
    public static final String FFREEDEB_TIMESTAMP_COLUMN = "timestamp_column";

    // create String
    public static final String CREATE_FFREEDEB_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FFREEDEB + " (" + FFREEDEB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + DatabaseHelper.DEBCODE + " TEXT, " + FFREEDEB_ADD_USER + " TEXT, " + FFREEDEB_ADD_DATE + " TEXT, " + FFREEDEB_ADD_MACH + " TEXT, " + FFREEDEB_RECORD_ID + " TEXT, " + FFREEDEB_TIMESTAMP_COLUMN + " TEXT); ";

    public static final String TESTFREEDEB = "CREATE UNIQUE INDEX IF NOT EXISTS idxfreedeb_something ON " + TABLE_FFREEDEB + " (" + DatabaseHelper.REFNO + "," + DatabaseHelper.DEBCODE + ")";


    public FreeDebController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public void createOrUpdateFreeDeb(ArrayList<FreeDeb> list) {
        Log.d("InsrtOrReplceFinvHedL3", "" + list.size());
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_FFREEDEB + " (RefNo,DebCode) " + " VALUES (?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);
            for (FreeDeb freeDeb : list) {

                stmt.bindString(1, freeDeb.getFFREEDEB_REFNO());
                stmt.bindString(2, freeDeb.getFFREEDEB_DEB_CODE());

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
    public ArrayList<FreeDeb> getFreeIssueDebtors(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FreeDeb> list = new ArrayList<FreeDeb>();

        // String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "') AND costcode='" + costCode + "' AND date('now') between vdatef and vdatet";
        // inoshi--Mine**CostCode change//
        String selectQuery = "select deb.debcode , deb.debname from fdebtor deb, ffreedeb fdeb where fdeb.debcode = deb.debcode and fdeb.refno ='" + refno + "'";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                FreeDeb freeHed = new FreeDeb();

                freeHed.setFFREEDEB_DEB_CODE(cursor.getString(cursor.getColumnIndex(dbHelper.DEBCODE))+" - "+cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_NAME)));

                list.add(freeHed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return list;
    }
    public int getRefnoByDebCount(String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT count(*) FROM " + TABLE_FFREEDEB + " WHERE " + dbHelper.REFNO + "='" + refno + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getInt(0);

        }
        return 0;

    }

    public int isValidDebForFreeIssue(String refno, String currDeb) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT count(*) FROM " + TABLE_FFREEDEB + " WHERE " + dbHelper.REFNO + "='" + refno + "' AND " + dbHelper.DEBCODE + "='" + currDeb + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getInt(0);

        }
        return 0;

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

            cursor = dB.rawQuery("SELECT * FROM " +TABLE_FFREEDEB, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FFREEDEB, null, null);
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
