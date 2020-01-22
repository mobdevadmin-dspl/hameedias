package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.R;
import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.DayExpHed;
import com.datamation.swdsfa.model.DayNPrdHed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DayExpHedController {
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "Ebony";

    public DayExpHedController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

	/*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*/

    public int createOrUpdateDayExpHed(ArrayList<DayExpHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (DayExpHed exphed : list) {
                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.REFNO, exphed.getEXP_REFNO());
                values.put(DatabaseHelper.TXNDATE, exphed.getEXP_TXNDATE());
                values.put(DatabaseHelper.REPCODE, exphed.getEXP_REPCODE());
                values.put(DayNPrdHed.FDAYEXPHED_REMARKS, exphed.getEXP_REMARK());
                values.put(DayNPrdHed.FDAYEXPHED_ADDDATE, exphed.getEXP_ADDDATE());
                values.put(DayNPrdHed.FDAYEXPHED_ISSYNC, exphed.getEXP_IS_SYNCED());
                values.put(DayNPrdHed.FDAYEXPHED_TOTAMT, exphed.getEXP_TOTAMT());
                values.put(DayNPrdHed.FDAYEXPHED_ACTIVESTATE, exphed.getEXP_ACTIVESTATE());
                values.put(DayNPrdHed.FDAYEXPHED_LATITUDE, exphed.getEXP_LATITUDE());
                values.put(DayNPrdHed.FDAYEXPHED_LONGITUDE, exphed.getEXP_LONGITUDE());
                values.put(DayNPrdHed.FDAYEXPHED_REPNAME, exphed.getEXP_REPNAME());
                values.put(DayNPrdHed.FDAYEXPHED_COSTCODE, exphed.getEXP_COSTCODE());
                values.put(DayNPrdHed.FDAYEXPHED_ADDMATCH, exphed.getEXP_ADDMACH());
                values.put(DayNPrdHed.FDAYEXPHED_AREACODE, exphed.getEXP_AREACODE());

                count = (int) dB.insert(DayNPrdHed.TABLE_DAYEXPHED, null, values);

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

	/*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*/

    public ArrayList<DayExpHed> getAllExpHedDetails(String newTExt) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<DayExpHed> list = new ArrayList<DayExpHed>();
        String selectQuery = "select * from " + DayNPrdHed.TABLE_DAYEXPHED + " WHERE TxnDate LIKE '%" + newTExt + "%'";
        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            DayExpHed fdayexpset = new DayExpHed();
            fdayexpset.setEXP_REFNO(cursor.getString(cursor.getColumnIndex(DayNPrdHed.REFNO)));
            fdayexpset.setEXP_TXNDATE(cursor.getString(cursor.getColumnIndex(DayNPrdHed.TXNDATE)));
            fdayexpset.setEXP_TOTAMT(cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_TOTAMT)));
            list.add(fdayexpset);
        }

        return list;
    }

	/*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*/

    public int undoExpHedByID(String RefNo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + DayNPrdHed.TABLE_DAYEXPHED + " WHERE " + DatabaseHelper.REFNO + "='" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(DayNPrdHed.TABLE_DAYEXPHED, DatabaseHelper.REFNO + "='" + RefNo + "'", null);

            }
        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return count;

    }

	/*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*/

//    public ArrayList<ExpenseMapper> getUnSyncedData() {
//
//        if (dB == null) {
//            open();
//        } else if (!dB.isOpen()) {
//            open();
//        }
//
//        ArrayList<ExpenseMapper> list = new ArrayList<ExpenseMapper>();
//
//        try {
//
//            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_DAYEXPHED + " WHERE " + DatabaseHelper.FDAYEXPHED_ACTIVESTATE + "='0' AND " + DatabaseHelper.FDAYEXPHED_ISSYNC + "='0'";
//            Cursor cursor = dB.rawQuery(selectQuery, null);
//            localSP = context.getSharedPreferences(SETTINGS, 0);
//
//            while (cursor.moveToNext()) {
//
//                ExpenseMapper mapper = new ExpenseMapper();
//                mapper.setNextNumVal(new CompanyBranchDS(context).getCurrentNextNumVal(context.getResources().getString(R.string.ExpenseNumVal)));
//                mapper.setConsoleDB(localSP.getString("Console_DB", "").toString());
//
//                mapper.setEXP_ACTIVESTATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ACTIVESTATE)));
//                mapper.setEXP_ADDDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ADDDATE)));
//                mapper.setEXP_ADDMACH(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ADDMATCH)));
//                mapper.setEXP_ADDRESS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ADDRESS)));
//                mapper.setEXP_ADDUSER(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ADDUSER)));
//                // mapper.setEXP_AREACODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_AREACODE)));
//                // mapper.setEXP_DEALCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_DEALCODE)));
//                mapper.setEXP_COSTCODE("000");
//                mapper.setEXP_IS_SYNCED(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_ISSYNC)));
//                mapper.setEXP_LATITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_LATITUDE)));
//                mapper.setEXP_LONGITUDE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_LONGITUDE)));
//                mapper.setEXP_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
//                mapper.setEXP_REMARK(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_REMARKS)));
//                mapper.setEXP_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
//                mapper.setEXP_REPNAME(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_REPNAME)));
//                mapper.setEXP_TOTAMT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_TOTAMT)));
//                mapper.setEXP_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FDAYEXPHED_TXNDATE)));
//
//                mapper.setExpnseDetList(new FDayExpDetDS(context).getAllExpDetails(mapper.getEXP_REFNO()));
//                list.add(mapper);
//            }
//
//        } catch (Exception e) {
//            Log.v(TAG + " Exception", e.toString());
//        } finally {
//            dB.close();
//        }
//
//        return list;
//
//    }

	/*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*/

    public int restDataExp(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + DayNPrdHed.TABLE_DAYEXPHED + " WHERE " + DatabaseHelper.REFNO + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);
            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.delete(DayNPrdHed.TABLE_DAYEXPHED, DatabaseHelper.REFNO + " ='" + refno + "'", null);
                Log.v("Success Stauts", count + "");
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


    public boolean isEntrySynced(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("select issync from FDayExpHed where refno ='" + Refno + "'", null);

        while (cursor.moveToNext()) {

            String result = cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_ISSYNC));

            if (result.equals("1"))
                return true;

        }
        cursor.close();
        dB.close();
        return false;

    }

    public ArrayList<DayExpHed> getTodayDEHeds()
    {
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<DayExpHed> list = new ArrayList<DayExpHed>();

        try {
            String selectQuery = "select RepCode, RefNo, txndate, issync from DayExpHed " + "  where TxnDate = '" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate) +"'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                DayExpHed deHed = new DayExpHed();
//
                deHed.setEXP_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                deHed.setEXP_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
                deHed.setEXP_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
                deHed.setEXP_IS_SYNCED(cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_ISSYNC)));
                //TODO :set  discount, free

                list.add(deHed);
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return list;

    }

    public int updateIsSynced(DayExpHed hed) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            ContentValues values = new ContentValues();

            values.put(DayNPrdHed.FDAYEXPHED_ISSYNC, "1");

            if (hed.getEXP_IS_SYNCED().equals("1")) {
                count = dB.update(DayNPrdHed.TABLE_DAYEXPHED, values, dbHelper.REFNO + " =?", new String[] { String.valueOf(hed.getEXP_REFNO()) });
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

    public ArrayList<DayExpHed> getUnSyncedData() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<DayExpHed> list = new ArrayList<DayExpHed>();

        try {

            String selectQuery = "SELECT * FROM " + DayNPrdHed.TABLE_DAYEXPHED + " WHERE " + DayNPrdHed.FDAYEXPHED_ISSYNC + "='0'";
            Cursor cursor = dB.rawQuery(selectQuery, null);
//            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

            while (cursor.moveToNext()) {

                DayExpHed mapper = new DayExpHed();
                //mapper.setNextNumVal(new CompanyBranchDS(context).getCurrentNextNumVal(context.getResources().getString(R.string.NonProd)));
//                mapper.setConsoleDB(localSP.getString("ConsoleDB", "").toString());
                mapper.setNextNumVal(new ReferenceController(context).getCurrentNextNumVal(context.getResources().getString(R.string.ExpenseNumVal)));
                mapper.setDistDB(SharedPref.getInstance(context).getDistDB().trim());
                mapper.setConsoleDB(SharedPref.getInstance(context).getConsoleDB().trim());

                mapper.setEXP_REFNO(cursor.getString(cursor.getColumnIndex(DayNPrdHed.REFNO)));
                mapper.setEXP_TXNDATE(cursor.getString(cursor.getColumnIndex(DayNPrdHed.TXNDATE)));
                mapper.setEXP_REPCODE(cursor.getString(cursor.getColumnIndex(DayNPrdHed.REPCODE)));
                mapper.setEXP_REPNAME(cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_REPNAME)));
                mapper.setEXP_COSTCODE(cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_COSTCODE)));
                mapper.setEXP_REMARK(cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_REMARKS)));
                mapper.setEXP_ADDUSER(cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_ADDUSER)));
                mapper.setEXP_ADDMACH(cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_ADDMATCH)));
                mapper.setEXP_IS_SYNCED(cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_ISSYNC)));
                mapper.setEXP_LONGITUDE(cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_LONGITUDE)));
                mapper.setEXP_LATITUDE(cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_LATITUDE)));
                mapper.setEXP_TOTAMT(cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_TOTAMT)));
                mapper.setEXP_AREACODE(cursor.getString(cursor.getColumnIndex(DayNPrdHed.FDAYEXPHED_AREACODE)));

                mapper.setExpnseDetList(new DayExpDetController(context).getAllExpDetails(mapper.getEXP_REFNO()));

                list.add(mapper);
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return list;

    }


}
