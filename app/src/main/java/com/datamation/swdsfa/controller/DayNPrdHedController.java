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
import com.datamation.swdsfa.model.DayNPrdHed;
import com.datamation.swdsfa.model.FInvRHed;
import com.datamation.swdsfa.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DayNPrdHedController {
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "NONPROD DS";
    // rashmi - 2019-12-24 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_NONPRDHED = "FDaynPrdHed";
    // table attributes
    public static final String NONPRDHED_ID = "FNonprdHed_id";
    public static final String NONPRDHED_REFNO = "RefNo";
    public static final String NONPRDHED_TXNDAET = "TxnDate";
    public static final String NONPRDHED_REMARK = "Remarks";
    public static final String NONPRDHED_COSTCODE = "CostCode";
    public static final String NONPRDHED_ADDUSER = "AddUser";
    public static final String NONPRDHED_ADDDATE = "AddDate";
    public static final String NONPRDHED_ADDMACH = "AddMach";
    public static final String NONPRDHED_TRANSBATCH = "TranBatch";
    public static final String NONPRDHED_IS_SYNCED = "ISsync";
    public static final String NONPRDHED_ADDRESS = "Address";
    public static final String NONPRDHED_REASON = "Reason";
    public static final String NONPRDHED_LONGITUDE = "Longitude";
    public static final String NONPRDHED_LATITUDE = "Latitude";
    public static final String NONPRDHED_IS_ACTIVE = "ISActive";

    public static final String CREATE_TABLE_NONPRDHED = "CREATE  TABLE IF NOT EXISTS " + TABLE_NONPRDHED + " (" + NONPRDHED_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + NONPRDHED_REFNO +
            " TEXT, " + NONPRDHED_TXNDAET + " TEXT, " +
            DatabaseHelper.DEALCODE + " TEXT, " +
            DatabaseHelper.REPCODE + " TEXT, " +
            NONPRDHED_REMARK + " TEXT, " +
            NONPRDHED_COSTCODE + " TEXT, " +
            NONPRDHED_LONGITUDE + " TEXT, " +
            NONPRDHED_LATITUDE + " TEXT, " +
            NONPRDHED_IS_ACTIVE + " TEXT, " +
            NONPRDHED_REASON + " TEXT, " +
            DatabaseHelper.DEBCODE + " TEXT, " +
            NONPRDHED_ADDUSER + " TEXT, " + NONPRDHED_ADDDATE + " TEXT," + NONPRDHED_ADDMACH + " TEXT," + NONPRDHED_TRANSBATCH + " TEXT, " + NONPRDHED_IS_SYNCED + " TEXT," + NONPRDHED_ADDRESS + " TEXT); ";

    public DayNPrdHedController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateNonPrdHed(ArrayList<DayNPrdHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (DayNPrdHed nonhed : list) {
                ContentValues values = new ContentValues();

                values.put(dbHelper.REFNO, nonhed.getNONPRDHED_REFNO());
                values.put(dbHelper.TXNDATE, nonhed.getNONPRDHED_TXNDATE());
                values.put(dbHelper.REPCODE, nonhed.getNONPRDHED_REPCODE());
                values.put(NONPRDHED_REMARK, nonhed.getNONPRDHED_REMARKS());
                values.put(NONPRDHED_ADDDATE, nonhed.getNONPRDHED_ADDDATE());
                values.put(NONPRDHED_COSTCODE, nonhed.getNONPRDHED_COSTCODE());
                values.put(NONPRDHED_ADDUSER, nonhed.getNONPRDHED_ADDUSER());
                values.put(NONPRDHED_IS_SYNCED, nonhed.getNONPRDHED_IS_SYNCED());
                values.put(NONPRDHED_LONGITUDE,nonhed.getNONPRDHED_LONGITUDE());
                values.put(NONPRDHED_LATITUDE,nonhed.getNONPRDHED_LATITUDE());
                values.put(dbHelper.DEBCODE,nonhed.getNONPRDHED_DEBCODE());
                values.put(NONPRDHED_IS_ACTIVE,nonhed.getNONPRDHED_IS_ACTIVE());
                values.put(NONPRDHED_ADDMACH,nonhed.getNONPRDHED_ADDMACH());
                values.put(NONPRDHED_ADDRESS,nonhed.getNONPRDHED_ADDRESS());

                count = (int) dB.insert(TABLE_NONPRDHED, null, values);

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

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public ArrayList<DayNPrdHed> getTodayNPHeds() {
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<DayNPrdHed> list = new ArrayList<DayNPrdHed>();

        try {
            String selectQuery = "select DebCode, RefNo, TxnDate, Reason, ISsync from FDaynPrdHed " + "  where TxnDate = '" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate) +"'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                DayNPrdHed npHed = new DayNPrdHed();
//
                npHed.setNONPRDHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                npHed.setNONPRDHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
                npHed.setNONPRDHED_TXNDATE(cursor.getString(cursor.getColumnIndex(NONPRDHED_TXNDAET)));
                npHed.setNONPRDHED_REASON(cursor.getString(cursor.getColumnIndex(NONPRDHED_REASON)));
                npHed.setNONPRDHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(NONPRDHED_IS_SYNCED)));
                //TODO :set  discount, free

                list.add(npHed);
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

//    public boolean validateActiveNonPrd()
//    {
//
//        if (dB == null) {
//            open();
//        } else if (!dB.isOpen()) {
//            open();
//        }
//
//        boolean res = false;
//
//        String toDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        Cursor cursor = null;
//        try {
//            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_NONPRDHED + " WHERE " + NONPRDHED_IS_ACTIVE+ "='1'";
//            cursor = dB.rawQuery(selectQuery, null);
//
//            /*Active invoice available*/
//            if (cursor.getCount() > 0) {
//                cursor.moveToFirst();
//
//                String txndate = cursor.getString(cursor.getColumnIndex(NONPRDHED_TXNDATE));
//
//                /*txndate is equal to current date*/
//                if (txndate.equals(toDay))
//                    res = true;
//                /*if invoice is older, then reset data*/
//                else {
//                    String Refno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FTRANSOHED_REFNO));
//                    restData(Refno);
////                    new InvDetDS(context).restData(Refno);
////                    new OrderDiscDS(context).clearData(Refno);
////                    new OrdFreeIssueDS(context).ClearFreeIssues(Refno);
//                    UtilityContainer.ClearNonSharedPref(context);
//                }
//
//            } else
//                res = false;
//
//        } catch (Exception e) {
//            Log.v(TAG, e.toString());
//
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            dB.close();
//        }
//
//        return res;
//
//    }

	/*-*-*-*--*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*--*-*-*-*-*-*-*/

    /**
     * -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
     * *-*-*-*-*-*-*-*-*-*-*-*-
     */

    public boolean restData(String refno) {

        boolean Result = false;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_NONPRDHED + " WHERE " + DatabaseHelper.REFNO + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                String status = cursor.getString(cursor.getColumnIndex(NONPRDHED_IS_SYNCED));
                /* if order already synced, can't delete */
                if (status.equals("1"))
                    Result = false;
                else {
                    int success = dB.delete(TABLE_NONPRDHED, DatabaseHelper.REFNO + " ='" + refno + "'", null);
                    Log.v("Success", success + "");
                    Result = true;
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
        return Result;

    }
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*/

    public ArrayList<DayNPrdHed> getAllnonprdHedDetails(String newText) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<DayNPrdHed> list = new ArrayList<DayNPrdHed>();

        String selectQuery = "select * from " + TABLE_NONPRDHED + " WHERE AddDate LIKE '%" + newText + "%'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            DayNPrdHed fnonset = new DayNPrdHed();
            fnonset.setNONPRDHED_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
            fnonset.setNONPRDHED_ADDDATE(cursor.getString(cursor.getColumnIndex(NONPRDHED_ADDDATE)));
            fnonset.setNONPRDHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(NONPRDHED_IS_SYNCED)));

            list.add(fnonset);

        }

        return list;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*/

    @SuppressWarnings("static-access")
    public int undoOrdHedByID(String RefNo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_NONPRDHED + " WHERE " + dbHelper.REFNO + "='" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_NONPRDHED, dbHelper.REFNO + "='" + RefNo + "'", null);

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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*/

//    public ArrayList<NonProdMapper> getUnSyncedData() {
//
//        if (dB == null) {
//            open();
//        } else if (!dB.isOpen()) {
//            open();
//        }
//
//        ArrayList<NonProdMapper> list = new ArrayList<NonProdMapper>();
//
//        try {
//
//            String selectQuery = "SELECT * FROM " + TABLE_NONPRDHED + " WHERE " + NONPRDHED_IS_SYNCED + "='0'";
//            Cursor cursor = dB.rawQuery(selectQuery, null);
//            localSP = context.getSharedPreferences(SETTINGS, 0);
//
//            while (cursor.moveToNext()) {
//
//                NonProdMapper mapper = new NonProdMapper();
//                mapper.setNextNumVal(new CompanyBranchDS(context).getCurrentNextNumVal(context.getResources().getString(R.string.NonProd)));
//                mapper.setConsoleDB(localSP.getString("Console_DB", "").toString());
//
//                mapper.setNONPRDHED_ADDDATE(cursor.getString(cursor.getColumnIndex(NONPRDHED_ADDDATE)));
//                mapper.setNONPRDHED_ADDMACH(cursor.getString(cursor.getColumnIndex(NONPRDHED_ADDMACH)));
//                mapper.setNONPRDHED_ADDRESS(cursor.getString(cursor.getColumnIndex(NONPRDHED_ADDRESS)));
//                mapper.setNONPRDHED_ADDUSER(cursor.getString(cursor.getColumnIndex(NONPRDHED_ADDUSER)));
//                mapper.setNONPRDHED_COSTCODE("000");
//                // mapper.setNONPRDHED_DEALCODE(cursor.getString(cursor.getColumnIndex(NONPRDHED_DEALCODE)));
//                mapper.setNONPRDHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(NONPRDHED_IS_SYNCED)));
//                mapper.setREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
//                mapper.setNONPRDHED_REMARK(cursor.getString(cursor.getColumnIndex(NONPRDHED_REMARK)));
//                mapper.setNONPRDHED_REPCODE(cursor.getString(cursor.getColumnIndex(NONPRDHED_REPCODE)));
//                mapper.setNONPRDHED_TRANSBATCH(cursor.getString(cursor.getColumnIndex(NONPRDHED_TRANSBATCH)));
//                mapper.setNONPRDHED_TXNDATE(cursor.getString(cursor.getColumnIndex(NONPRDHED_TXNDATE)));
//                mapper.setNONPRDHED_DEBCODE(cursor.getString(cursor.getColumnIndex(NONPRDHED_DEBCODE)));
//                mapper.setNONPRDHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(NONPRDHED_LONGITUDE)));
//                mapper.setNONPRDHED_LATITUDE(cursor.getString(cursor.getColumnIndex(NONPRDHED_LATITUDE)));
//                mapper.setNonPrdDet(new DayNPrdDetController(context).getAllnonprdDetails(mapper.getREFNO()));
//
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*/

//    public int updateIsSynced(NonProdMapper mapper) {
//
//        int count = 0;
//
//        if (dB == null) {
//            open();
//        } else if (!dB.isOpen()) {
//            open();
//        }
//        Cursor cursor = null;
//
//        try {
//            ContentValues values = new ContentValues();
//            values.put(NONPRDHED_IS_SYNCED, "1");
//            if (mapper.isSynced()) {
//                count = dB.update(TABLE_NONPRDHED, values, DatabaseHelper.REFNO + " =?", new String[]{String.valueOf(mapper.getREFNO())});
//            }
//
//        } catch (Exception e) {
//
//            Log.v(TAG + " Exception", e.toString());
//
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            dB.close();
//        }
//        return count;
//
//    }

    public boolean isEntrySynced(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("select ISsync from FDaynPrdHed where refno ='" + Refno + "'", null);

        while (cursor.moveToNext()) {

            String result = cursor.getString(cursor.getColumnIndex(NONPRDHED_IS_SYNCED));

            if (result.equals("1"))
                return true;

        }
        cursor.close();
        dB.close();
        return false;

    }

    public ArrayList<DayNPrdHed> getUnSyncedData() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<DayNPrdHed> list = new ArrayList<DayNPrdHed>();

        try {

            String selectQuery = "SELECT * FROM " + TABLE_NONPRDHED + " WHERE " + NONPRDHED_IS_SYNCED + "='0'";
            Cursor cursor = dB.rawQuery(selectQuery, null);
//            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
            localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

            while (cursor.moveToNext()) {

                DayNPrdHed mapper = new DayNPrdHed();
                //mapper.setNextNumVal(new CompanyBranchDS(context).getCurrentNextNumVal(context.getResources().getString(R.string.NonProd)));
//                mapper.setConsoleDB(localSP.getString("ConsoleDB", "").toString());

                mapper.setNextNumVal(new ReferenceController(context).getCurrentNextNumVal(context.getResources().getString(R.string.nonprdVal)));
                mapper.setDistDB(SharedPref.getInstance(context).getDistDB().trim());
                mapper.setConsoleDB(SharedPref.getInstance(context).getConsoleDB().trim());

                mapper.setNONPRDHED_REFNO(cursor.getString(cursor.getColumnIndex(NONPRDHED_REFNO)));
                mapper.setNONPRDHED_TXNDATE(cursor.getString(cursor.getColumnIndex(NONPRDHED_TXNDAET)));
                mapper.setNONPRDHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
                mapper.setNONPRDHED_REMARKS(cursor.getString(cursor.getColumnIndex(NONPRDHED_REMARK)));
                mapper.setNONPRDHED_COSTCODE(cursor.getString(cursor.getColumnIndex(NONPRDHED_COSTCODE)));
                mapper.setNONPRDHED_ADDUSER(cursor.getString(cursor.getColumnIndex(NONPRDHED_ADDUSER)));
                mapper.setNONPRDHED_ADDDATE(cursor.getString(cursor.getColumnIndex(NONPRDHED_ADDDATE)));
                mapper.setNONPRDHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
                mapper.setNONPRDHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(NONPRDHED_LONGITUDE)));
                mapper.setNONPRDHED_LATITUDE(cursor.getString(cursor.getColumnIndex(NONPRDHED_LATITUDE)));
                mapper.setNONPRDHED_ADDRESS(cursor.getString(cursor.getColumnIndex(NONPRDHED_ADDRESS)));
                mapper.setNONPRDHED_ADDMACH(cursor.getString(cursor.getColumnIndex(NONPRDHED_ADDMACH)));
                mapper.setNONPRDHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(NONPRDHED_IS_SYNCED)));

                mapper.setNonPrdDet(new DayNPrdDetController(context).getAllnonprdDetails(mapper.getNONPRDHED_REFNO(),mapper.getNONPRDHED_DEBCODE()));

                list.add(mapper);
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return list;

    }

    public int updateIsSynced(DayNPrdHed hed) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            ContentValues values = new ContentValues();

            values.put(NONPRDHED_IS_SYNCED, "1");

            if (hed.getNONPRDHED_IS_SYNCED().equals("1")) {
                count = dB.update(TABLE_NONPRDHED, values, dbHelper.REFNO + " =?", new String[] { String.valueOf(hed.getNONPRDHED_REFNO()) });
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
