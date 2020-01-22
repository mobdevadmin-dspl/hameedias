package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.SalRep;

import java.util.ArrayList;

public class SalRepController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FSALREP";
    // rashmi - 2019-12-18 move from database_helper , because of reduce coding in database helper*******************************************************************************

    /**
     * ############################ fSalRep table Details
     * ################################
     */

    // table
    public static final String TABLE_FSALREP = "fSalRep";
    // table attributes
    public static final String FSALREP_ID = "fsalrep_id";
    public static final String FSALREP_ASE_CODE = "ASECode";
    public static final String FSALREP_AREA_CODE = "AreaCode";
    public static final String FSALREP_RECORD_ID = "RecordId";
    public static final String FSALREP_REP_PREFIX = "RepPrefix";
    public static final String FSALREP_REP_TCODE = "RepTCode";
    public static final String FSALREP_REP_PHONE_NO = "repPhoneNo";
    public static final String FSALREP_REP_NAME = "RepName";
    public static final String FSALREP_REP_EMAIL = "RepEMail";
    public static final String FSALREP_REP_MOB = "RepMob";
    public static final String FSALREP_COSTCODE = "CostCode";// Password
    public static final String FSALREP_ADDMACH = "AddMach";
    public static final String FSALREP_ADDUSER = "AddUser";
    public static final String FSALREP_RECORDID = "RecordId";
    public static final String FSALREP_EMAIL = "Email";
    public static final String FSALREP_REPID = "RepId";
    public static final String FSALREP_MOBILE = "Mobile";
    public static final String FSALREP_LOCCODE = "LocCode";
    public static final String FSALREP_MACID = "macId";
    public static final String FSALREP_NAME = "name";
    public static final String FSALREP_PREFIX = "prefix";
    public static final String FSALREP_STATUS = "status";
    public static final String FSALREP_TELE = "telephone";
    public static final String FSALREP_PRILCODE = "prillcode";
    public static final String FSALREP_ISSYNC = "Issync";
    public static final String FSALREP_IS_ZERO_QOH_ALLOW = "IsZeroQOHAllow";
    public static final String FSALREP_IS_FIRE_QOH_EXCED_VALIDATION= "IsFireQohExcdValidation";



    // create String
    public static final String CREATE_FSALREP_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FSALREP + " (" + FSALREP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FSALREP_ADDMACH + " TEXT, " + FSALREP_ADDUSER + " TEXT, " + FSALREP_REP_TCODE+ " TEXT, " + FSALREP_RECORDID + " TEXT, " + DatabaseHelper.REPCODE + " TEXT, " + FSALREP_EMAIL + " TEXT, " + FSALREP_REPID + " TEXT, " + FSALREP_MOBILE + " TEXT, " + FSALREP_NAME + " TEXT, " + FSALREP_PREFIX + " TEXT, " + FSALREP_TELE + " TEXT, "
            + FSALREP_STATUS + " TEXT, "
            + FSALREP_LOCCODE + " TEXT, "
            + FSALREP_AREA_CODE + " TEXT, "
            + DatabaseHelper.DEALCODE + " TEXT, "
            + FSALREP_MACID + " TEXT,"
            + FSALREP_IS_ZERO_QOH_ALLOW + " TEXT,"
            + FSALREP_IS_FIRE_QOH_EXCED_VALIDATION + " TEXT,"
            + FSALREP_ISSYNC + " TEXT); ";

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

                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FSALREP + " WHERE " + DatabaseHelper.REPCODE + "='" + rep.getRepCode() + "'", null);

                ContentValues values = new ContentValues();

                values.put(FSALREP_ADDMACH, rep.getADDMACH());
                values.put(FSALREP_ADDUSER, rep.getADDUSER());
                values.put(FSALREP_EMAIL, rep.getEMAIL());
                values.put(FSALREP_MOBILE, rep.getMOBILE());
                values.put(FSALREP_NAME, rep.getNAME());
                values.put(FSALREP_REP_TCODE, rep.getREPTCODE());
                values.put(FSALREP_PREFIX, rep.getPREFIX());
                values.put(DatabaseHelper.REPCODE, rep.getRepCode());
                values.put(FSALREP_REPID, rep.getREPID());
                values.put(FSALREP_STATUS, rep.getSTATUS());
                values.put(FSALREP_TELE, rep.getTELE());
                values.put(FSALREP_LOCCODE, rep.getLOCCODE());
                values.put(DatabaseHelper.DEALCODE, rep.getDEALCODE());
                values.put(FSALREP_MACID, rep.getMACID());
                values.put(FSALREP_ISSYNC, rep.getISSYNC());
                values.put(FSALREP_IS_ZERO_QOH_ALLOW,rep.getIS_ZERO_QOH_ALLOW());
                values.put(FSALREP_IS_FIRE_QOH_EXCED_VALIDATION,rep.getIsApplyQOHexdVldtn());


                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FSALREP, values, DatabaseHelper.REPCODE + "=?", new String[]{rep.getRepCode()});
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

        String selectQuery = "SELECT " + DatabaseHelper.REPCODE + " FROM " + TABLE_FSALREP;

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE));


            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return "";
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String isQohZeroAllow() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT " + FSALREP_IS_ZERO_QOH_ALLOW + " FROM " + TABLE_FSALREP;

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(FSALREP_IS_ZERO_QOH_ALLOW));


            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return "";
    }
    public String isFireQohExdValidation() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT " + FSALREP_IS_FIRE_QOH_EXCED_VALIDATION + " FROM " + TABLE_FSALREP;

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(FSALREP_IS_FIRE_QOH_EXCED_VALIDATION));


            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return "";
    }
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public String getDealCode() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT " + DatabaseHelper.DEALCODE + " FROM " + TABLE_FSALREP;

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEALCODE));
            }
            cursor.close();
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

        String selectRep = "SELECT * FROM " + TABLE_FSALREP + " WHERE " + DatabaseHelper.REPCODE + " = '" + Repcode + "'";

        try {

            cursor = dB.rawQuery(selectRep, null);

            while (cursor.moveToNext()) {

                newRep = new SalRep();

                newRep.setMOBILE(cursor.getString(cursor.getColumnIndex(FSALREP_REP_MOB)));
                newRep.setNAME(cursor.getString(cursor.getColumnIndex(FSALREP_REP_NAME)));
                newRep.setREPTCODE(cursor.getString(cursor.getColumnIndex(FSALREP_REP_TCODE)));
                newRep.setPREFIX(cursor.getString(cursor.getColumnIndex(FSALREP_REP_PREFIX)));
                newRep.setRepCode(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
                newRep.setLOCCODE(cursor.getString(cursor.getColumnIndex(FSALREP_LOCCODE)));
                newRep.setEMAIL(cursor.getString(cursor.getColumnIndex(FSALREP_REP_EMAIL)));
                newRep.setTELE(cursor.getString(cursor.getColumnIndex(FSALREP_REP_PHONE_NO)));
                newRep.setDEALCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEALCODE)));

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
            values.put(FSALREP_REP_EMAIL, email);
            dB.update(TABLE_FSALREP, values, DatabaseHelper.REPCODE + " =?", new String[]{String.valueOf(repcode)});

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

                newRep.setADDMACH(curRep.getString(curRep.getColumnIndex(FSALREP_ADDMACH)));
                newRep.setADDUSER(curRep.getString(curRep.getColumnIndex(FSALREP_ADDUSER)));
                newRep.setEMAIL(curRep.getString(curRep.getColumnIndex(FSALREP_EMAIL)));
                newRep.setMOBILE(curRep.getString(curRep.getColumnIndex(FSALREP_MOBILE)));
                newRep.setNAME(curRep.getString(curRep.getColumnIndex(FSALREP_NAME)));
                newRep.setREPTCODE(curRep.getString(curRep.getColumnIndex(FSALREP_REP_TCODE)));
                newRep.setPREFIX(curRep.getString(curRep.getColumnIndex(FSALREP_PREFIX)));
                newRep.setRepCode(curRep.getString(curRep.getColumnIndex(DatabaseHelper.REPCODE)));
                newRep.setREPID(curRep.getString(curRep.getColumnIndex(FSALREP_REPID)));
                newRep.setSTATUS(curRep.getString(curRep.getColumnIndex(FSALREP_STATUS)));
                newRep.setTELE(curRep.getString(curRep.getColumnIndex(FSALREP_TELE)));
                newRep.setLOCCODE(curRep.getString(curRep.getColumnIndex(FSALREP_LOCCODE)));
                newRep.setDEALCODE(curRep.getString(curRep.getColumnIndex(DatabaseHelper.DEALCODE)));
                newRep.setMACID(curRep.getString(curRep.getColumnIndex(FSALREP_MACID)));
                newRep.setIS_ZERO_QOH_ALLOW(curRep.getString(curRep.getColumnIndex(FSALREP_IS_ZERO_QOH_ALLOW)));


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

                newRep.setADDMACH(curRep.getString(curRep.getColumnIndex(FSALREP_ADDMACH)));
                newRep.setADDUSER(curRep.getString(curRep.getColumnIndex(FSALREP_ADDUSER)));
                newRep.setEMAIL(curRep.getString(curRep.getColumnIndex(FSALREP_EMAIL)));
                newRep.setMOBILE(curRep.getString(curRep.getColumnIndex(FSALREP_MOBILE)));
                newRep.setNAME(curRep.getString(curRep.getColumnIndex(FSALREP_NAME)));
                newRep.setREPTCODE(curRep.getString(curRep.getColumnIndex(FSALREP_REP_TCODE)));
                newRep.setPREFIX(curRep.getString(curRep.getColumnIndex(FSALREP_PREFIX)));
                newRep.setRepCode(curRep.getString(curRep.getColumnIndex(DatabaseHelper.REPCODE)));
                newRep.setREPID(curRep.getString(curRep.getColumnIndex(FSALREP_REPID)));
                newRep.setSTATUS(curRep.getString(curRep.getColumnIndex(FSALREP_STATUS)));
                newRep.setTELE(curRep.getString(curRep.getColumnIndex(FSALREP_TELE)));

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

    public String getCurrentLocCode() {

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

    public int updateIsSynced(String repcode, String res) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(FSALREP_ISSYNC, "1");
            if (res.equalsIgnoreCase("1")) {
                count = dB.update(TABLE_FSALREP, values, FSALREP_ID + " =?", new String[]{String.valueOf(repcode)});
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;

    }


    public ArrayList<SalRep> getAllUnsyncSalrep(String Repcode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectRep = "SELECT * FROM fSalRep where Issync = '0' AND RepCode='" + Repcode + "'";

        Cursor curRep = null;
        curRep = dB.rawQuery(selectRep, null);
        ArrayList<SalRep> salreplist = new ArrayList<SalRep>();
        try {
            while (curRep.moveToNext()) {

                SalRep newRep = new SalRep();

                newRep.setCONSOLE_DB(SharedPref.getInstance(context).getConsoleDB().trim());
                newRep.setDIST_DB(SharedPref.getInstance(context).getDistDB().trim());
                newRep.setEMAIL(curRep.getString(curRep.getColumnIndex(FSALREP_EMAIL)));
                newRep.setRepCode(curRep.getString(curRep.getColumnIndex(DatabaseHelper.REPCODE)));
                newRep.setREPID(curRep.getString(curRep.getColumnIndex(FSALREP_ISSYNC)));
                newRep.setIS_ZERO_QOH_ALLOW(curRep.getString(curRep.getColumnIndex(FSALREP_IS_ZERO_QOH_ALLOW)));
                newRep.setIsApplyQOHexdVldtn(curRep.getString(curRep.getColumnIndex(FSALREP_IS_FIRE_QOH_EXCED_VALIDATION)));


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

                newRep.setREPTCODE(curRep.getString(curRep.getColumnIndex(FSALREP_REP_TCODE)));
                newRep.setRepCode(curRep.getString(curRep.getColumnIndex(DatabaseHelper.REPCODE)));

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
