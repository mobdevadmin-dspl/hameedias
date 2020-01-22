package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.DayExpDet;
import com.datamation.swdsfa.model.DayNPrdDet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DayExpDetController {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "DayExpDetController";
    // rashmi - 2019-12-24 move from database_helper , because of reduce coding in database helper*******************************************************************************

     public static final String TABLE_FDAYEXPDET = "FDayExpDet";
     // table attributes
     public static final String FDAYEXPDET_ID = "FDayExpDet_id";

     public static final String FDAYEXPDET_SEQNO = "SeqNo";
     public static final String FDAYEXPDET_EXPCODE = "ExpCode";
     public static final String FDAYEXPDET_AMT = "Amt";
     // create String
     public static final String CREATE_FDAYEXPDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDAYEXPDET + " (" + FDAYEXPDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT, " + FDAYEXPDET_SEQNO + " TEXT, " + FDAYEXPDET_EXPCODE + " TEXT, " + FDAYEXPDET_AMT + " TEXT); ";

    public static final String TABLE_DAYEXPDET = "DayExpDet";
    // table attributes
    public static final String DAYEXPDET_ID = "DayExpDet_id";
    public static final String DAYEXPDET_TXNDATE = "TxnDate";
    public static final String DAYEXPDET_EXPCODE = "ExpCode";
    public static final String DAYEXPDET_AMT = "Amt";
    public static final String REFNO = "RefNo";
    public static final String TXNDATE = "TxnDate";
    public static final String REPCODE = "RepCode";
    // create String
    public static final String CREATE_DAYEXPDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_DAYEXPDET + " (" + DAYEXPDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + REFNO +  " TEXT, " + DAYEXPDET_EXPCODE + " TEXT, "+ DAYEXPDET_TXNDATE + " TEXT, " + DAYEXPDET_AMT + " TEXT); ";

    public DayExpDetController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    /*
     * insert code
     */
    @SuppressWarnings("static-access")
    public int createOrUpdateExpenseDet(ArrayList<DayExpDet> list) {

        // int count =0;
        int serverdbID = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (DayExpDet expdet : list) {
                ContentValues values = new ContentValues();

                String selectQuery = "SELECT * FROM " + TABLE_DAYEXPDET + " WHERE " + REFNO + " = '" + expdet.getEXPDET_REFNO() + "'" + " AND " + DAYEXPDET_EXPCODE + " = '" + expdet.getEXPDET_EXPCODE() + "'";
                cursor = dB.rawQuery(selectQuery, null);

                values.put(REFNO, expdet.getEXPDET_REFNO());
                values.put(DAYEXPDET_EXPCODE, expdet.getEXPDET_EXPCODE());
                values.put(DAYEXPDET_AMT, expdet.getEXPDET_AMOUNT());
                values.put(DAYEXPDET_TXNDATE, expdet.getEXPDET_TXNDATE());

                int count = cursor.getCount();
                if (count > 0) {
                    serverdbID = dB.update(TABLE_DAYEXPDET, values, REFNO + " =?" + " AND " + DAYEXPDET_EXPCODE + " =?", new String[]{String.valueOf(expdet.getEXPDET_REFNO()), String.valueOf(expdet.getEXPDET_EXPCODE())});

                } else {
                    serverdbID = (int) dB.insert(TABLE_DAYEXPDET, null, values);
                }

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return serverdbID;

    }

    // Load Detail records of FdayExpdet for selected Reference number.
    public ArrayList<DayExpDet> getAllExpDetails(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<DayExpDet> list = new ArrayList<DayExpDet>();

        String selectQuery = "select * from " + TABLE_DAYEXPDET + " WHERE " + REFNO + "='" + refno + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            DayExpDet fdayexpset = new DayExpDet();

            fdayexpset.setEXPDET_REFNO(cursor.getString(cursor.getColumnIndex(REFNO)));
            fdayexpset.setEXPDET_TXNDATE(cursor.getString(cursor.getColumnIndex(DAYEXPDET_TXNDATE)));
            fdayexpset.setEXPDET_EXPCODE(cursor.getString(cursor.getColumnIndex(DAYEXPDET_EXPCODE)));
            fdayexpset.setEXPDET_AMOUNT(cursor.getString(cursor.getColumnIndex(DAYEXPDET_AMT)));

            list.add(fdayexpset);

        }

        return list;
    }

    public String getDuplicate(String code, String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + TABLE_DAYEXPDET + " WHERE " + DAYEXPDET_EXPCODE + "='" + code + "' AND " + REFNO + "='" + RefNo + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(DAYEXPDET_EXPCODE));

        }

        return "";
    }

    @SuppressWarnings("static-access")
    public int deleteOrdDetByID(String id) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_DAYEXPDET + " WHERE " + DAYEXPDET_ID + "='" + id + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_DAYEXPDET, DAYEXPDET_ID + "='" + id + "'", null);
                Log.v("FExpDet Deleted ", success + "");
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
    public int getExpenceCount(String refNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            String selectQuery = "SELECT count(RefNo) as RefNo FROM " + TABLE_DAYEXPDET +  " WHERE  " + REFNO + "='" + refNo + "'";
            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                return Integer.parseInt(cursor.getString(cursor.getColumnIndex("RefNo")));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dB.close();
        }
        return 0;

    }
    @SuppressWarnings("static-access")
    public int getAllExpDetail(String RefNo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_DAYEXPDET + " WHERE " + REFNO + "='" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_DAYEXPDET, REFNO + "='" + RefNo + "'", null);
                Log.v("FtranDet Deleted ", success + "");
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

    @SuppressWarnings("static-access")
    public int ExpDetByID(String RefNo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_DAYEXPDET + " WHERE " + REFNO + "='" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_DAYEXPDET, REFNO + "='" + RefNo + "'", null);
                Log.v("FtranDet Deleted ", success + "");
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

    public String getTotalExpenseSumReturns(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        // String selectQuery = "SELECT * FROM " + TABLE_REASON
        // +" WHERE "+REASON_NAME+"='"+name+"'";
        String selectQuery = "select sum(FD.Amt) as TotSum from DayExpDet FD where FD.RefNo = '" + refno + "'";
        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex("TotSum")) != null)
                return cursor.getString(cursor.getColumnIndex("TotSum"));
            else
                return "0";

        }

        return "0";
    }

    // Delete Record from Fdayexpdet
    @SuppressWarnings("static-access")
    public int DeleteRec(String RefNo, String expcode) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_DAYEXPDET + " WHERE " + REFNO + "='" + RefNo + "' AND " + DAYEXPDET_EXPCODE + "='" + expcode + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_DAYEXPDET, REFNO + "='" + RefNo + "' AND " + DAYEXPDET_EXPCODE + "='" + expcode + "' ", null);
                Log.v("FtranDet Deleted ", success + "");
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

    @SuppressWarnings("static-access")
    public int restDataExp(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_DAYEXPDET + " WHERE " + REFNO + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);
            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.delete(TABLE_DAYEXPDET, REFNO + " ='" + refno + "'", null);
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

    public ArrayList<DayExpDet> getTodayDEDets(String refno) {

        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<DayExpDet> list = new ArrayList<DayExpDet>();

        String selectQuery = "select * from DayExpDet WHERE " + REFNO + "='" + refno + "' and  TxnDate = '" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate) +"'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                DayExpDet deDet = new DayExpDet();

                deDet.setEXPDET_EXPCODE(cursor.getString(cursor.getColumnIndex(DAYEXPDET_EXPCODE)));
                deDet.setEXPDET_AMOUNT(cursor.getString(cursor.getColumnIndex(DAYEXPDET_AMT)));

                list.add(deDet);

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

    /*develop by kaveesha*/




}
