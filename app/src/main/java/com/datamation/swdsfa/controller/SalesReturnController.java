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
import com.datamation.swdsfa.model.FInvRHed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SalesReturnController
{
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "SFA";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FINVRHED = "FInvRHed";
    public static final String FINVRHED_ID = "id";// 1

    public static final String FINVRHED_MANUREF = "ManuRef";

    public static final String FINVRHED_COSTCODE = "CostCode";
    public static final String FINVRHED_REMARKS = "Remarks";
    public static final String FINVRHED_TXNTYPE = "TxnType";
    public static final String FINVRHED_INV_REFNO= "InvRefNo";
    public static final String FINVRHED_ORD_REFNO= "OrdRefNo";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FINVRHED_LOCCODE = "LOCCode";
    public static final String FINVRHED_REASON_CODE = "ReasonCode";
    public static final String FINVRHED_TOTAL_TAX = "TotalTax";
    public static final String FINVRHED_TOTAL_AMT = "TotalAmt";
    public static final String FINVRHED_TOTAL_DIS = "TotalDis";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FINVRHED_TAX_REG = "TaxReg";
    public static final String FINVRHED_ADD_DATE = "AddDate";
    public static final String FINVRHED_ADD_MACH = "AddMach";
    public static final String FINVRHED_ADD_USER = "AddUser";
    public static final String FINVRHED_ROUTE_CODE = "RouteCode";
    public static final String FINVRHED_LONGITUDE = "Longitude";
    public static final String FINVRHED_LATITUDE = "Latitude";
    public static final String FINVRHED_IS_ACTIVE = "IsActive";
    public static final String FINVRHED_IS_SYNCED = "IsSync";
    public static final String FINVRHED_ADDRESS = "Address";
    public static final String FINVRHED_START_TIME = "StartTime";
    public static final String FINVRHED_END_TIME = "EndTime";
    public static final String CREATE_FINVRHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FINVRHED + " ("
            + FINVRHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //1
            + DatabaseHelper.REFNO + " TEXT, "
            + DatabaseHelper.TXNDATE + " TEXT, "
            + FINVRHED_MANUREF + " TEXT, "
            + FINVRHED_COSTCODE + " TEXT, "
            + DatabaseHelper.DEBCODE + " TEXT, "
            + FINVRHED_REMARKS + " TEXT, "
            + FINVRHED_TXNTYPE + " TEXT, "
            + FINVRHED_INV_REFNO + " TEXT, "
            + FINVRHED_ORD_REFNO + " TEXT, "
            + FINVRHED_ADD_DATE + " TEXT, "
            + FINVRHED_ADD_MACH + " TEXT, "
            + FINVRHED_ADD_USER + " TEXT, "
            + FINVRHED_LOCCODE + " TEXT, "
            + FINVRHED_IS_ACTIVE + " TEXT, "
            + FINVRHED_IS_SYNCED + " TEXT, "
            + DatabaseHelper.REPCODE + " TEXT, "
            + FINVRHED_REASON_CODE + " TEXT, "
            + FINVRHED_TAX_REG + " TEXT, "
            + FINVRHED_TOTAL_AMT + " TEXT, "
            + FINVRHED_TOTAL_TAX + " TEXT, "
            + FINVRHED_TOTAL_DIS + " TEXT, "
            + FINVRHED_ROUTE_CODE + " TEXT, "
            + FINVRHED_LONGITUDE + " TEXT, "
            + FINVRHED_LATITUDE + " TEXT, "
            + FINVRHED_ADDRESS + " TEXT, "
            + FINVRHED_START_TIME + " TEXT, "
            + FINVRHED_END_TIME + " TEXT); ";
    public SalesReturnController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateInvRHed(ArrayList<FInvRHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (FInvRHed invrHed : list) {

                String selectQuery = "SELECT * FROM " + TABLE_FINVRHED + " WHERE " + dbHelper.REFNO + " = '" + invrHed.getFINVRHED_REFNO() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                ContentValues values = new ContentValues();

                values.put(dbHelper.REFNO, invrHed.getFINVRHED_REFNO());
                values.put(dbHelper.TXNDATE, invrHed.getFINVRHED_TXN_DATE());
                values.put(FINVRHED_REMARKS, invrHed.getFINVRHED_REMARKS());
                values.put(dbHelper.DEBCODE, invrHed.getFINVRHED_DEBCODE());
                values.put(FINVRHED_TOTAL_AMT, invrHed.getFINVRHED_TOTAL_AMT());
                values.put(FINVRHED_ADD_DATE, invrHed.getFINVRHED_ADD_DATE());
                values.put(FINVRHED_ADD_MACH, invrHed.getFINVRHED_ADD_MACH());
                values.put(FINVRHED_ADD_USER, invrHed.getFINVRHED_ADD_USER());
                values.put(FINVRHED_MANUREF, invrHed.getFINVRHED_MANUREF());
                values.put(FINVRHED_IS_ACTIVE, invrHed.getFINVRHED_IS_ACTIVE());
                values.put(FINVRHED_IS_SYNCED, invrHed.getFINVRHED_IS_SYNCED());
                values.put(FINVRHED_COSTCODE, invrHed.getFINVRHED_COSTCODE());
                values.put(FINVRHED_LOCCODE, invrHed.getFINVRHED_LOCCODE());
                values.put(FINVRHED_REASON_CODE, invrHed.getFINVRHED_REASON_CODE());
                values.put(FINVRHED_TAX_REG, invrHed.getFINVRHED_TAX_REG());
                values.put(FINVRHED_TOTAL_TAX, invrHed.getFINVRHED_TOTAL_TAX());
                values.put(FINVRHED_TOTAL_DIS, invrHed.getFINVRHED_TOTAL_DIS());
                values.put(FINVRHED_LONGITUDE, invrHed.getFINVRHED_LONGITUDE());
                values.put(FINVRHED_LATITUDE, invrHed.getFINVRHED_LATITUDE());
                values.put(FINVRHED_START_TIME, invrHed.getFINVRHED_START_TIME());
                values.put(FINVRHED_END_TIME, invrHed.getFINVRHED_END_TIME());
                values.put(dbHelper.REPCODE, invrHed.getFINVRHED_REP_CODE());
                values.put(FINVRHED_ORD_REFNO, invrHed.getFINVRHED_ORD_REFNO());
                values.put(FINVRHED_TXNTYPE, invrHed.getFINVRHED_TXNTYPE());
                values.put(FINVRHED_INV_REFNO, invrHed.getFINVRHED_INV_REFNO());

//                values.put(FINVRHED_RETURN_TYPE, invrHed.getFINVRHED_RETURN_TYPE());
//                values.put(FINVRHED_TOURCODE, invrHed.getFINVRHED_TOURCODE());
//                values.put(FINVRHED_AREACODE, invrHed.getFINVRHED_AREACODE());
//                values.put(FINVRHED_DRIVERCODE, invrHed.getFINVRHED_DRIVERCODE());
//                values.put(FINVRHED_HELPERCODE, invrHed.getFINVRHED_HELPERCODE());
//                values.put(FINVRHED_LORRYCODE, invrHed.getFINVRHED_LORRYCODE());

                int cn = cursor.getCount();
                if (cn > 0) {

                    count = dB.update(TABLE_FINVRHED, values, dbHelper.REFNO + " =?",
                            new String[]{String.valueOf(invrHed.getFINVRHED_REFNO())});
                } else {

                    count = (int) dB.insert(TABLE_FINVRHED, null, values);

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
        return count;

    }

    public boolean isAnyActive() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        boolean res = false;

        Cursor cursor = null;
        try {
//            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_FINVRHED + " WHERE " + DatabaseHelper.FINVRHED_IS_ACTIVE + "='1'";
            String selectQuery = "select * from FInvRHed where IsActive = 1 and OrdRefNo IS NULL and InvRefNo IS NULL";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0)
                res = true;
            else
                res = false;

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return res;

    }

    public String getCurRefNoOfRetWitInv(String invRefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        String refNo = "";

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + TABLE_FINVRHED + " WHERE " + FINVRHED_INV_REFNO + " = '" + invRefNo + "'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0)
            {
                while(cursor.moveToNext())
                {
                    refNo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO));
                }
            }
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return refNo;

    }
    public String getActiveInnerReturnRefNoByOrderRefNo(String ordRefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        String refNo = "";

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + TABLE_FINVRHED + " WHERE " + FINVRHED_ORD_REFNO + " = '" + ordRefNo + "'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0)
            {
                while(cursor.moveToNext())
                {
                    refNo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO));
                }
            }
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return refNo;

    }
    public ArrayList<FInvRHed> getAllActiveInvrhed() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FInvRHed> list = new ArrayList<FInvRHed>();

        @SuppressWarnings("static-access")
        String selectQuery = "select * from " + TABLE_FINVRHED + " Where " + FINVRHED_IS_ACTIVE
                + "='1' and " + FINVRHED_IS_SYNCED + "='0'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            FInvRHed invrHed = new FInvRHed();

            // invHed.setFINVHED_ID(cursor.getString(cursor.getColumnIndex(FINVRHED_ID)));
            invrHed.setFINVRHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
            invrHed.setFINVRHED_TXN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
            invrHed.setFINVRHED_ROUTE_CODE(cursor.getString(cursor.getColumnIndex(FINVRHED_ROUTE_CODE)));
            invrHed.setFINVRHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FINVRHED_TXNTYPE)));
            invrHed.setFINVRHED_ADD_MACH(cursor.getString(cursor.getColumnIndex(FINVRHED_ADD_MACH)));
            invrHed.setFINVRHED_ADD_USER(cursor.getString(cursor.getColumnIndex(FINVRHED_ADD_USER)));
            invrHed.setFINVRHED_MANUREF(cursor.getString(cursor.getColumnIndex(FINVRHED_MANUREF)));
            invrHed.setFINVRHED_REMARKS(cursor.getString(cursor.getColumnIndex(FINVRHED_REMARKS)));
            invrHed.setFINVRHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
            invrHed.setFINVRHED_TOTAL_AMT(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_AMT)));
            invrHed.setFINVRHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(FINVRHED_IS_SYNCED)));
            invrHed.setFINVRHED_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FINVRHED_IS_ACTIVE)));
            invrHed.setFINVRHED_ADD_DATE(cursor.getString(cursor.getColumnIndex(FINVRHED_ADD_DATE)));
            invrHed.setFINVRHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_COSTCODE)));
            invrHed.setFINVRHED_LOCCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_LOCCODE)));
            invrHed.setFINVRHED_ADDRESS(cursor.getString(cursor.getColumnIndex(FINVRHED_ADDRESS)));
            invrHed.setFINVRHED_REASON_CODE(cursor.getString(cursor.getColumnIndex(FINVRHED_REASON_CODE)));
            invrHed.setFINVRHED_TAX_REG(cursor.getString(cursor.getColumnIndex(FINVRHED_TAX_REG)));
            invrHed.setFINVRHED_TOTAL_TAX(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_TAX)));
            invrHed.setFINVRHED_TOTAL_DIS(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_DIS)));
            invrHed.setFINVRHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(FINVRHED_LONGITUDE)));
            invrHed.setFINVRHED_LATITUDE(cursor.getString(cursor.getColumnIndex(FINVRHED_LATITUDE)));
            invrHed.setFINVRHED_START_TIME(cursor.getString(cursor.getColumnIndex(FINVRHED_START_TIME)));
            invrHed.setFINVRHED_END_TIME(cursor.getString(cursor.getColumnIndex(FINVRHED_END_TIME)));
            invrHed.setFINVRHED_REP_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
//            invrHed.setFINVRHED_RETURN_TYPE(cursor.getString(cursor.getColumnIndex(FINVRHED_RETURN_TYPE)));
//            invrHed.setFINVRHED_TOURCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_TOURCODE)));
//            invrHed.setFINVRHED_AREACODE(cursor.getString(cursor.getColumnIndex(FINVRHED_AREACODE)));
//            invrHed.setFINVRHED_DRIVERCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_DRIVERCODE)));
//            invrHed.setFINVRHED_HELPERCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_HELPERCODE)));
//            invrHed.setFINVRHED_LORRYCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_LORRYCODE)));

            list.add(invrHed);

        }

        return list;
    }

    public FInvRHed getActiveReturnHed(String RefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        FInvRHed invrHed = new FInvRHed();

        @SuppressWarnings("static-access")
        String selectQuery = "select * from " + TABLE_FINVRHED + " Where " + FINVRHED_IS_ACTIVE + "='1' and " + FINVRHED_IS_SYNCED + "='0'" + " AND " + DatabaseHelper.REFNO + " = '" + RefNo + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            // invHed.setFINVHED_ID(cursor.getString(cursor.getColumnIndex(FINVRHED_ID)));
            invrHed.setFINVRHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
            invrHed.setFINVRHED_TXN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
            invrHed.setFINVRHED_ROUTE_CODE(cursor.getString(cursor.getColumnIndex(FINVRHED_ROUTE_CODE)));
            invrHed.setFINVRHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FINVRHED_TXNTYPE)));
            invrHed.setFINVRHED_ADD_MACH(cursor.getString(cursor.getColumnIndex(FINVRHED_ADD_MACH)));
            invrHed.setFINVRHED_ADD_USER(cursor.getString(cursor.getColumnIndex(FINVRHED_ADD_USER)));
            invrHed.setFINVRHED_MANUREF(cursor.getString(cursor.getColumnIndex(FINVRHED_MANUREF)));
            invrHed.setFINVRHED_REMARKS(cursor.getString(cursor.getColumnIndex(FINVRHED_REMARKS)));
            invrHed.setFINVRHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
            invrHed.setFINVRHED_TOTAL_AMT(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_AMT)));
            invrHed.setFINVRHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(FINVRHED_IS_SYNCED)));
            invrHed.setFINVRHED_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FINVRHED_IS_ACTIVE)));
            invrHed.setFINVRHED_ADD_DATE(cursor.getString(cursor.getColumnIndex(FINVRHED_ADD_DATE)));
            invrHed.setFINVRHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_COSTCODE)));
            invrHed.setFINVRHED_LOCCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_LOCCODE)));
            invrHed.setFINVRHED_ADDRESS(cursor.getString(cursor.getColumnIndex(FINVRHED_ADDRESS)));
            invrHed.setFINVRHED_REASON_CODE(cursor.getString(cursor.getColumnIndex(FINVRHED_REASON_CODE)));
            invrHed.setFINVRHED_TAX_REG(cursor.getString(cursor.getColumnIndex(FINVRHED_TAX_REG)));
            invrHed.setFINVRHED_TOTAL_TAX(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_TAX)));
            invrHed.setFINVRHED_TOTAL_DIS(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_DIS)));
            invrHed.setFINVRHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(FINVRHED_LONGITUDE)));
            invrHed.setFINVRHED_LATITUDE(cursor.getString(cursor.getColumnIndex(FINVRHED_LATITUDE)));
            invrHed.setFINVRHED_START_TIME(cursor.getString(cursor.getColumnIndex(FINVRHED_START_TIME)));
            invrHed.setFINVRHED_END_TIME(cursor.getString(cursor.getColumnIndex(FINVRHED_END_TIME)));
            invrHed.setFINVRHED_REP_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
//            invrHed.setFINVRHED_RETURN_TYPE(cursor.getString(cursor.getColumnIndex(FINVRHED_RETURN_TYPE)));
//            invrHed.setFINVRHED_TOURCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_TOURCODE)));
//            invrHed.setFINVRHED_AREACODE(cursor.getString(cursor.getColumnIndex(FINVRHED_AREACODE)));
//            invrHed.setFINVRHED_DRIVERCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_DRIVERCODE)));
//            invrHed.setFINVRHED_HELPERCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_HELPERCODE)));
//            invrHed.setFINVRHED_LORRYCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_LORRYCODE)));



        }

        return invrHed;
    }

    public int restData(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_FINVRHED + " WHERE "
                    + DatabaseHelper.REFNO + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);
            count = cursor.getCount();

            if (count > 0) {
                int success = dB.delete(TABLE_FINVRHED,
                        DatabaseHelper.REFNO + " ='" + refno + "'", null);
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

    public int restDataForDirectSalesReturnHed(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        String isRefExisting = "";

        try {

            //String selectQuery = "SELECT * FROM " + TABLE_FINVRHED + " WHERE "  + DatabaseHelper.REFNO + " = '" + refno + "'";
            String selectQuery = "select * from FInvRHed where IsActive = 1 and OrdRefNo IS NULL and InvRefNo IS NULL";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount()>0)
            {
                while (cursor.moveToNext())
                {
                    isRefExisting = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO));

                    if (isRefExisting.equals(refno))
                    {
                        count = dB.delete(TABLE_FINVRHED,
                                DatabaseHelper.REFNO + " ='" + refno + "'", null);
                        Log.v("Success", count + "");
                    }
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
        return count;

    }

    public String getDirectSalesReturnRefNo() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        String isRefExisting = "";

        try {

            //String selectQuery = "SELECT * FROM " + TABLE_FINVRHED + " WHERE "  + DatabaseHelper.REFNO + " = '" + refno + "'";
            String selectQuery = "select * from FInvRHed where IsActive = 1 and OrdRefNo IS NULL and InvRefNo IS NULL ";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount()>0)
            {
                while (cursor.moveToNext())
                {
                    isRefExisting = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO));
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
        return isRefExisting;

    }

    public String getDirectSalesReturnRefNoForPrint() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        String isRefExisting = "";

        try {

            String selectQuery = "select * from FInvRHed where IsActive = 0 and OrdRefNo IS NULL and InvRefNo IS NULL";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount()>0)
            {
                while (cursor.moveToNext())
                {
                    isRefExisting = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO));
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
        return isRefExisting;

    }

    public int restDataForOrders(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        String isRefExisting = "";

        try {

            //String selectQuery = "SELECT * FROM " + TABLE_FINVRHED + " WHERE "  + DatabaseHelper.REFNO + " = '" + refno + "'";
            String selectQuery = "select * from FInvRHed where IsActive = 1 and OrdRefNo IS NOT NULL and InvRefNo IS NULL";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount()>0)
            {
                while (cursor.moveToNext())
                {
                    isRefExisting = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO));

                    if (isRefExisting.equals(refno))
                    {
                        count = dB.delete(TABLE_FINVRHED,
                                DatabaseHelper.REFNO + " ='" + refno + "'", null);
                        Log.v("Success", count + "");
                    }
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
        return count;

    }

    public FInvRHed getDetailsforPrint(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        FInvRHed SRHed = new FInvRHed();

        try {
            String selectQuery = "SELECT RefNo,TotalAmt FROM " + TABLE_FINVRHED + " WHERE " + DatabaseHelper.REFNO + " = '" + Refno + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                SRHed.setFINVRHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                SRHed.setFINVRHED_TOTAL_AMT(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_AMT)));

            }
            cursor.close();

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return SRHed;

    }
    public int InactiveStatusUpdate(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_FINVRHED + " WHERE "
                    + DatabaseHelper.REFNO + " = '" + refno + "'";

            cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(FINVRHED_IS_ACTIVE, "0");

            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.update(TABLE_FINVRHED, values, DatabaseHelper.REFNO + " =?",
                        new String[]{String.valueOf(refno)});
            } else {
                count = (int) dB.insert(TABLE_FINVRHED, null, values);
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

    public FInvRHed getReturnDetailsForPrint(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        FInvRHed REHed = new FInvRHed();

        try {
            String selectQuery = "SELECT * FROM " + TABLE_FINVRHED + " WHERE " + DatabaseHelper.REFNO + " = '" + Refno + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                REHed.setFINVRHED_TXN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
                REHed.setFINVRHED_TOTAL_AMT(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_AMT)));
                REHed.setFINVRHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
                REHed.setFINVRHED_REMARKS(cursor.getString(cursor.getColumnIndex(FINVRHED_REMARKS)));
                REHed.setFINVRHED_TAX_REG(cursor.getString(cursor.getColumnIndex(FINVRHED_TAX_REG)));
                REHed.setFINVRHED_TOTAL_TAX(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_TAX)));
                REHed.setFINVRHED_TOTAL_DIS(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_DIS)));
//                REHed.setFTRANSOHED_TOTQTY(cursor.getString(cursor.getColumnIndex(FTRANSOHED_TOTQTY)));
//                REHed.setFTRANSOHED_TOTFREE(cursor.getString(cursor.getColumnIndex(FTRANSOHED_TOTFREE)));
            }
            cursor.close();

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return REHed;
    }

    public int updateIsSynced(FInvRHed hed) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            ContentValues values = new ContentValues();

            values.put(FINVRHED_IS_SYNCED, "1");

            if (hed.getFINVRHED_IS_SYNCED().equals("1")) {
                count = dB.update(TABLE_FINVRHED, values, dbHelper.REFNO + " =?", new String[] { String.valueOf(hed.getFINVRHED_REFNO()) });
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

    // Sales Return with invoice Upload Method
// sales return without invoice(direct returns and returns with order)
    public ArrayList<FInvRHed> getUnSyncedReturnWithoutInvoice() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FInvRHed> list = new ArrayList<FInvRHed>();


        @SuppressWarnings("static-access")
        String selectQuery = "select * from " + TABLE_FINVRHED
                + " Where " + FINVRHED_IS_ACTIVE + "='0' and " +
                FINVRHED_IS_SYNCED + " = '0' and "+ FINVRHED_INV_REFNO+ " = 'NON' ";


        Cursor cursor = dB.rawQuery(selectQuery, null);

        localSP =
                context.getSharedPreferences(SETTINGS, 0);

        while (cursor.moveToNext()) {

            FInvRHed salesReturnMapper = new FInvRHed();



            ReferenceController branchDS = new ReferenceController(context);
            salesReturnMapper.setNextNumVal(branchDS.getCurrentNextNumVal(context.getResources().getString(R.string.salRet)));

            salesReturnMapper.setDistDB(SharedPref.getInstance(context).getDistDB().trim());
            salesReturnMapper.setConsoleDB(SharedPref.getInstance(context).getConsoleDB().trim());

            salesReturnMapper.setFINVRHED_ID(cursor.getString(cursor.getColumnIndex(FINVRHED_ID)));
            salesReturnMapper.setFINVRHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
            salesReturnMapper.setFINVRHED_ADD_DATE(cursor.getString(cursor.getColumnIndex(FINVRHED_ADD_DATE)));
            salesReturnMapper.setFINVRHED_ADD_MACH(cursor.getString(cursor.getColumnIndex(FINVRHED_ADD_MACH)));
            salesReturnMapper.setFINVRHED_ADD_USER(cursor.getString(cursor.getColumnIndex(FINVRHED_ADD_USER)));
            salesReturnMapper.setFINVRHED_REMARKS(cursor.getString(cursor.getColumnIndex(FINVRHED_REMARKS)));
            salesReturnMapper.setFINVRHED_TOTAL_AMT(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_AMT)));
            salesReturnMapper.setFINVRHED_TOTAL_DIS(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_DIS)));
            salesReturnMapper.setFINVRHED_TOTAL_TAX(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_TAX)));
            salesReturnMapper.setFINVRHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_COSTCODE)));
            salesReturnMapper.setFINVRHED_REASON_CODE(cursor.getString(cursor.getColumnIndex(FINVRHED_REASON_CODE)));
            salesReturnMapper.setFINVRHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
            salesReturnMapper.setFINVRHED_START_TIME(cursor.getString(cursor.getColumnIndex(FINVRHED_START_TIME)));
            salesReturnMapper.setFINVRHED_END_TIME(cursor.getString(cursor.getColumnIndex(FINVRHED_END_TIME)));
            salesReturnMapper.setFINVRHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(FINVRHED_LONGITUDE)));
            salesReturnMapper.setFINVRHED_LATITUDE(cursor.getString(cursor.getColumnIndex(FINVRHED_LATITUDE)));
            salesReturnMapper.setFINVRHED_LOCCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_LOCCODE)));
            salesReturnMapper.setFINVRHED_MANUREF(cursor.getString(cursor.getColumnIndex(FINVRHED_MANUREF)));
            // salesReturnMapper.setFINVRHED_REPCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_REPCODE)));
            salesReturnMapper.setFINVRHED_TAX_REG(cursor.getString(cursor.getColumnIndex(FINVRHED_TAX_REG)));
            salesReturnMapper.setFINVRHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FINVRHED_TXNTYPE)));
            salesReturnMapper.setFINVRHED_TXN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
            salesReturnMapper.setFINVRHED_ADDRESS(cursor.getString(cursor.getColumnIndex(FINVRHED_ADDRESS)));
            salesReturnMapper.setFINVRHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(FINVRHED_IS_SYNCED)));
            salesReturnMapper.setFINVRHED_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FINVRHED_IS_ACTIVE)));
            salesReturnMapper.setFINVRHED_ROUTE_CODE(cursor.getString(cursor.getColumnIndex(FINVRHED_ROUTE_CODE)));
            salesReturnMapper.setFINVRHED_INV_REFNO(cursor.getString(cursor.getColumnIndex(FINVRHED_ORD_REFNO)));
            //salesReturnMapper.setFINVRHED_INV_REFNO(cursor.getString(cursor.getColumnIndex(FINVRHED_INV_REFNO)));

            salesReturnMapper.setFinvrtDets(new SalesReturnDetController(context).getAllInvRDet(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO))));

            list.add(salesReturnMapper);

        }

        return list;
    }
    public ArrayList<FInvRHed> getAllUnsyncedWithInvoice() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FInvRHed> list = new ArrayList<FInvRHed>();


        @SuppressWarnings("static-access")
        String selectQuery = "select * from " + TABLE_FINVRHED
                + " Where " + FINVRHED_IS_ACTIVE + "='0' and " +
                FINVRHED_ORD_REFNO + "='NON'"+" and "+FINVRHED_IS_SYNCED + "='0'";


        Cursor cursor = dB.rawQuery(selectQuery, null);

        //localSP =  context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);

        while (cursor.moveToNext()) {

            FInvRHed salesReturnMapper = new FInvRHed();

            salesReturnMapper.setNextNumVal(new ReferenceController(context).getCurrentNextNumVal(context.getResources().getString(R.string.salRet)));
            salesReturnMapper.setDistDB(SharedPref.getInstance(context).getDistDB().trim());
            salesReturnMapper.setConsoleDB(SharedPref.getInstance(context).getConsoleDB().trim());

//            salesReturnMapper.setDistDB(localSP.getString("DistDB", "").toString());
//            salesReturnMapper.setConsoleDB(localSP.getString("ConsoleDB",
//                    "").toString());

            salesReturnMapper.setFINVRHED_ID(cursor.getString(cursor.getColumnIndex(FINVRHED_ID)));
            salesReturnMapper.setFINVRHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
            salesReturnMapper.setFINVRHED_ADD_DATE(cursor.getString(cursor.getColumnIndex(FINVRHED_ADD_DATE)));
            salesReturnMapper.setFINVRHED_ADD_MACH(cursor.getString(cursor.getColumnIndex(FINVRHED_ADD_MACH)));
            salesReturnMapper.setFINVRHED_ADD_USER(cursor.getString(cursor.getColumnIndex(FINVRHED_ADD_USER)));
            salesReturnMapper.setFINVRHED_REMARKS(cursor.getString(cursor.getColumnIndex(FINVRHED_REMARKS)));
            salesReturnMapper.setFINVRHED_TOTAL_AMT(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_AMT)));
            salesReturnMapper.setFINVRHED_TOTAL_DIS(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_DIS)));
            salesReturnMapper.setFINVRHED_TOTAL_TAX(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_TAX)));
            salesReturnMapper.setFINVRHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_COSTCODE)));
            salesReturnMapper.setFINVRHED_REASON_CODE(cursor.getString(cursor.getColumnIndex(FINVRHED_REASON_CODE)));
            salesReturnMapper.setFINVRHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
            salesReturnMapper.setFINVRHED_START_TIME(cursor.getString(cursor.getColumnIndex(FINVRHED_START_TIME)));
            salesReturnMapper.setFINVRHED_END_TIME(cursor.getString(cursor.getColumnIndex(FINVRHED_END_TIME)));
            salesReturnMapper.setFINVRHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(FINVRHED_LONGITUDE)));
            salesReturnMapper.setFINVRHED_LATITUDE(cursor.getString(cursor.getColumnIndex(FINVRHED_LATITUDE)));
            salesReturnMapper.setFINVRHED_LOCCODE(cursor.getString(cursor.getColumnIndex(FINVRHED_LOCCODE)));
            salesReturnMapper.setFINVRHED_MANUREF(cursor.getString(cursor.getColumnIndex(FINVRHED_MANUREF)));
            salesReturnMapper.setFINVRHED_REP_CODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
            salesReturnMapper.setFINVRHED_TAX_REG(cursor.getString(cursor.getColumnIndex(FINVRHED_TAX_REG)));
            salesReturnMapper.setFINVRHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FINVRHED_TXNTYPE)));
            salesReturnMapper.setFINVRHED_TXN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
            salesReturnMapper.setFINVRHED_ADDRESS(cursor.getString(cursor.getColumnIndex(FINVRHED_ADDRESS)));
            salesReturnMapper.setFINVRHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(FINVRHED_IS_SYNCED)));
            salesReturnMapper.setFINVRHED_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FINVRHED_IS_ACTIVE)));
            salesReturnMapper.setFINVRHED_ROUTE_CODE(cursor.getString(cursor.getColumnIndex(FINVRHED_ROUTE_CODE)));
            salesReturnMapper.setFINVRHED_INV_REFNO(cursor.getString(cursor.getColumnIndex(FINVRHED_INV_REFNO)));



            String RefNo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO));

            salesReturnMapper.setFinvrtDets(new SalesReturnDetController(context).getAllInvRDet(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO))));
            //salesReturnMapper.setFinvrtDets(new FInvRDetDS(context).getAllInvRDet(RefNo));
            salesReturnMapper.setTaxDTs(new SalesReturnTaxDTController(context).getAllTaxDT(RefNo));
            salesReturnMapper.setTaxRGs(new SalesReturnTaxRGController(context).getAllTaxRG(RefNo));

            list.add(salesReturnMapper);

        }

        return list;
    }

    public ArrayList<FInvRHed> getTodayReturns() {
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<FInvRHed> list = new ArrayList<FInvRHed>();

        try {
            //String selectQuery = "select DebCode, RefNo from fordHed " +
            String selectQuery = "select DebCode, RefNo, IsSync, TxnDate, TotalAmt, TxnType from FInvRHed " + "  where txndate = '" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate) +"'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                FInvRHed retDet = new FInvRHed();
//
                retDet.setFINVRHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                retDet.setFINVRHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
                retDet.setFINVRHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(FINVRHED_IS_SYNCED)));
                retDet.setFINVRHED_TXNTYPE("Return");
                retDet.setFINVRHED_TXN_DATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
                retDet.setFINVRHED_TOTAL_AMT(cursor.getString(cursor.getColumnIndex(FINVRHED_TOTAL_AMT)));

                list.add(retDet);
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
}
