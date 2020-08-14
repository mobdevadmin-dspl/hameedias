package com.datamation.hmdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.helpers.ValueHolder;
import com.datamation.hmdsfa.model.SalRep;

import java.util.ArrayList;

public class SalRepController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FSALREP";
    /**
     * ############################ fSalRep table Details
     * ################################
     */
    // table
    public static final String TABLE_FSALREP = "fSalRep";
    // table attributes
    public static final String FSALREP_ID = "fsalrep_id";
    public static final String FSALREP_MACID = "macId";
    public static final String FSALREP_NAME = "name";
    public static final String FSALREP_PREFIX = "prefix";
    public static final String FSALREP_PASSWORD = "password";
    public static final String FSALREP_EMAIL = "email";
    public static final String FSALREP_LOCCODE = "loccode";
    // create String
    public static final String CREATE_FSALREP_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FSALREP + " (" + FSALREP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FSALREP_LOCCODE + " TEXT, "     + ValueHolder.REPCODE + " TEXT, "+ FSALREP_NAME + " TEXT, "+ FSALREP_PASSWORD + " TEXT, " +FSALREP_EMAIL + " TEXT, " + FSALREP_PREFIX + " TEXT, "
            + FSALREP_MACID + " TEXT); ";

    public SalRepController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int createOrUpdateSalRep(ArrayList<SalRep> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (SalRep rep : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FSALREP + " WHERE " + ValueHolder.REPCODE + "='" + rep.getRepCode() + "'", null);

                ContentValues values = new ContentValues();

                values.put(FSALREP_EMAIL, rep.getEMAIL());
                values.put(FSALREP_NAME, rep.getNAME());
                values.put(FSALREP_PREFIX, rep.getPREFIX());
                values.put(FSALREP_PASSWORD, rep.getPASSWORD());
                values.put(ValueHolder.REPCODE, rep.getRepCode());
                values.put(FSALREP_MACID, rep.getMACID());
                values.put(FSALREP_LOCCODE, rep.getCurrentVanLoc());


                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FSALREP, values, ValueHolder.REPCODE + "=?", new String[]{rep.getRepCode()});
                    Log.v("FSALREP : ", "Updated");
                } else {
                    count = (int) dB.insert(TABLE_FSALREP, null, values);
                    Log.v("FSALREP : ", "Inserted " + count);
                }

                cursor.close();
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }
        return count;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getCurrentRepCode() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT " + ValueHolder.REPCODE + " FROM " + TABLE_FSALREP;

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(ValueHolder.REPCODE));


            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getCurrentLoccode() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT " + FSALREP_LOCCODE + " FROM " + TABLE_FSALREP;

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(FSALREP_LOCCODE));

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return "";
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public SalRep getSaleRep(String Repcode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        SalRep newRep = null;

        String selectRep = "SELECT * FROM " + TABLE_FSALREP + " WHERE " + ValueHolder.REPCODE + " = '" + Repcode + "'";

        try {

            cursor = dB.rawQuery(selectRep, null);

            while (cursor.moveToNext()) {

                newRep = new SalRep();

                newRep.setNAME(cursor.getString(cursor.getColumnIndex(FSALREP_NAME)));
                newRep.setPREFIX(cursor.getString(cursor.getColumnIndex(FSALREP_PREFIX)));
                newRep.setRepCode(cursor.getString(cursor.getColumnIndex(ValueHolder.REPCODE)));
                newRep.setEMAIL(cursor.getString(cursor.getColumnIndex(FSALREP_EMAIL)));
                newRep.setPASSWORD(cursor.getString(cursor.getColumnIndex(FSALREP_PASSWORD)));
                newRep.setCurrentVanLoc(cursor.getString(cursor.getColumnIndex(FSALREP_LOCCODE)));

            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {

            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return newRep;
    }

    public void updatemail(String email,String repcode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(FSALREP_EMAIL, email);
            dB.update(TABLE_FSALREP, values, ValueHolder.REPCODE + " =?", new String[]{String.valueOf(repcode)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public SalRep getSaleRepDet(String Repcode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectRep = "SELECT * FROM fSalRep WHERE RepCode='" + Repcode + "'";
        Cursor curRep = null;
        curRep = dB.rawQuery(selectRep, null);
        SalRep newRep = new SalRep();

        try {
            while (curRep.moveToNext()) {

                newRep.setEMAIL(curRep.getString(curRep.getColumnIndex(FSALREP_EMAIL)));
                newRep.setNAME(curRep.getString(curRep.getColumnIndex(FSALREP_NAME)));
                newRep.setPASSWORD(curRep.getString(curRep.getColumnIndex(FSALREP_PASSWORD)));
                newRep.setPREFIX(curRep.getString(curRep.getColumnIndex(FSALREP_PREFIX)));
                newRep.setRepCode(curRep.getString(curRep.getColumnIndex(ValueHolder.REPCODE)));
                newRep.setMACID(curRep.getString(curRep.getColumnIndex(FSALREP_MACID)));
                newRep.setCurrentVanLoc(curRep.getString(curRep.getColumnIndex(FSALREP_LOCCODE)));


            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            curRep.close();
            dB.close();
        }

        return newRep;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<SalRep> getSaleRepDetails() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectRep = "SELECT * FROM fSalRep";

        Cursor curRep = null;
        curRep = dB.rawQuery(selectRep, null);
        ArrayList<SalRep> salreplist = new ArrayList<SalRep>();
        try {
            while (curRep.moveToNext()) {

                SalRep newRep = new SalRep();

                newRep.setEMAIL(curRep.getString(curRep.getColumnIndex(FSALREP_EMAIL)));
                newRep.setNAME(curRep.getString(curRep.getColumnIndex(FSALREP_NAME)));
                newRep.setPASSWORD(curRep.getString(curRep.getColumnIndex(FSALREP_PASSWORD)));
                newRep.setPREFIX(curRep.getString(curRep.getColumnIndex(FSALREP_PREFIX)));
                newRep.setRepCode(curRep.getString(curRep.getColumnIndex(ValueHolder.REPCODE)));

                salreplist.add(newRep);

            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            curRep.close();
            dB.close();
        }
        return salreplist;

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

      public String getMacId() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT " + FSALREP_MACID+ " FROM " + TABLE_FSALREP;
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {
                return cursor.getString(cursor.getColumnIndex(FSALREP_MACID));
            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }
        return "";
    }

    public SalRep getSalRepCredentials() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectRep = "SELECT * FROM fSalRep";
        Cursor curRep = null;
        curRep = dB.rawQuery(selectRep, null);
        SalRep newRep = new SalRep();

        try {
            while (curRep.moveToNext()) {

                newRep.setPASSWORD(curRep.getString(curRep.getColumnIndex(FSALREP_PASSWORD)));
                newRep.setRepCode(curRep.getString(curRep.getColumnIndex(ValueHolder.REPCODE)));

            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            curRep.close();
            dB.close();
        }

        return newRep;
    }
}
