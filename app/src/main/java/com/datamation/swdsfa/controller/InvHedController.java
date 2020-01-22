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
import com.datamation.swdsfa.model.InvHed;
import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvHedController {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "INVHED";
    public static final String TABLE_FINVHED = "finvHed";
    public static final String FINVHED_ID = "Id";

    public static final String FINVHED_REFNO1 = "RefNo1";

    public static final String FINVHED_MANUREF = "ManuRef";
    public static final String FINVHED_COSTCODE = "CostCode";
    public static final String FINVHED_CURCODE = "CurCode";
    public static final String FINVHED_CURRATE = "CurRate";
    public static final String FINVHED_REMARKS = "Remarks";
    public static final String FINVHED_TXNTYPE = "TxnType";
    public static final String FINVHED_LOCCODE = "LocCode";
    public static final String FINVHED_PAYTYPE = "PayType";
    public static final String FINVHED_SETTING_CODE = "SettingCode";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-order discount table-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*/
    public static final String FINVHED_CONTACT = "Contact";
    public static final String FINVHED_CUSADD1 = "CusAdd1";
    public static final String FINVHED_CUSADD2 = "CusAdd2";
    public static final String FINVHED_CUSADD3 = "CusAdd3";
    public static final String FINVHED_CUSTELE = "CusTele";
    public static final String FINVHED_TOTALDIS = "TotalDis";
    public static final String FINVHED_TOTALTAX = "TotalTax";
    public static final String FINVHED_TOTALAMT = "TotalAmt";
    public static final String FINVHED_TAXREG = "TaxReg";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-fSMS table Details-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*/
    public static final String FINVHED_ADDUSER = "AddUser";
    public static final String FINVHED_ADDDATE = "AddDate";
    public static final String FINVHED_ADDMACH = "AddMach";
    public static final String FINVHED_START_TIME_SO = "startTime";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-finvHed table Details-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*/
    public static final String FINVHED_END_TIME_SO = "endTime";
    public static final String FINVHED_LONGITUDE = "Longitude";
    public static final String FINVHED_LATITUDE = "Latitude";
    public static final String FINVHED_ADDRESS = "Address";
    public static final String FINVHED_IS_SYNCED = "isSynced";
    public static final String FINVHED_IS_ACTIVE = "isActive";
    public static final String FINVHED_AREACODE = "areacode";
    public static final String FINVHED_ROUTECODE = "routecode";
    public static final String FINVHED_TOURCODE = "tourcode";

    public static final String CREATE_FINVHED_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FINVHED + " (" + FINVHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + FINVHED_REFNO1 + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT, " + FINVHED_PAYTYPE + " TEXT, " + FINVHED_SETTING_CODE + " TEXT, "+ FINVHED_MANUREF + " TEXT, " + FINVHED_COSTCODE + " TEXT, " + FINVHED_CURCODE + " TEXT, " + FINVHED_CURRATE + " TEXT, " + DatabaseHelper.DEBCODE + " TEXT, " + FINVHED_REMARKS + " TEXT, " + FINVHED_TXNTYPE + " TEXT, " + FINVHED_LOCCODE + " TEXT, " + DatabaseHelper.REPCODE + " TEXT, " + FINVHED_CONTACT + " TEXT, " + FINVHED_CUSADD1 + " TEXT, " + FINVHED_CUSADD2 + " TEXT, " + FINVHED_CUSADD3 + " TEXT, " + FINVHED_CUSTELE + " TEXT, " + FINVHED_TOTALDIS + " TEXT, " + FINVHED_TOTALTAX + " TEXT, " + FINVHED_TAXREG + " TEXT, " + FINVHED_ADDUSER + " TEXT, " + FINVHED_ADDDATE + " TEXT, " + FINVHED_ADDMACH + " TEXT, " + FINVHED_START_TIME_SO + " TEXT, " + FINVHED_END_TIME_SO + " TEXT, " + FINVHED_TOTALAMT + " TEXT, " + FINVHED_LONGITUDE + " TEXT, " + FINVHED_LATITUDE + " TEXT, " + FINVHED_ADDRESS + " TEXT, " + FINVHED_IS_SYNCED + " TEXT, " + FINVHED_AREACODE + " TEXT, " + FINVHED_ROUTECODE + " TEXT, " + FINVHED_TOURCODE + " TEXT, " + FINVHED_IS_ACTIVE + " TEXT); ";

    public InvHedController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateInvHed(ArrayList<InvHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (InvHed invHed : list) {

                String selectQuery = "SELECT * FROM " + TABLE_FINVHED + " WHERE " + DatabaseHelper.REFNO + " = '" + invHed.getFINVHED_REFNO() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                ContentValues values = new ContentValues();

                values.put(DatabaseHelper.REFNO, invHed.getFINVHED_REFNO());
                values.put(FINVHED_ADDDATE, invHed.getFINVHED_ADDDATE());
                values.put(FINVHED_ADDMACH, invHed.getFINVHED_ADDMACH());
                values.put(FINVHED_ADDUSER, invHed.getFINVHED_ADDUSER());
                values.put(FINVHED_COSTCODE, invHed.getFINVHED_COSTCODE());
                values.put(FINVHED_CURCODE, invHed.getFINVHED_CURCODE());
                values.put(FINVHED_CURRATE, invHed.getFINVHED_CURRATE());
                values.put(DatabaseHelper.DEBCODE, invHed.getFINVHED_DEBCODE());
                values.put(FINVHED_START_TIME_SO, invHed.getFINVHED_START_TIME_SO());
                values.put(FINVHED_END_TIME_SO, invHed.getFINVHED_END_TIME_SO());
                values.put(FINVHED_LONGITUDE, invHed.getFINVHED_LONGITUDE());
                values.put(FINVHED_LATITUDE, invHed.getFINVHED_LATITUDE());
                values.put(FINVHED_LOCCODE, invHed.getFINVHED_LOCCODE());
                values.put(FINVHED_MANUREF, invHed.getFINVHED_MANUREF());
                values.put(FINVHED_REMARKS, invHed.getFINVHED_REMARKS());
                values.put(DatabaseHelper.REPCODE, invHed.getFINVHED_REPCODE());
                values.put(FINVHED_TAXREG, invHed.getFINVHED_TAXREG());
                values.put(FINVHED_TOTALAMT, invHed.getFINVHED_TOTALAMT());
                values.put(FINVHED_TOTALDIS, invHed.getFINVHED_TOTALDIS());
                values.put(FINVHED_TOTALTAX, invHed.getFINVHED_TOTALTAX());
                values.put(FINVHED_TXNTYPE, invHed.getFINVHED_TXNTYPE());
                values.put(DatabaseHelper.TXNDATE, invHed.getFINVHED_TXNDATE());
                // values.put(DatabaseHelper.FINVHED_ADDRESS, invHed.getFINVHED_ADDRESS());
                values.put(FINVHED_IS_SYNCED, "0");
                values.put(FINVHED_IS_ACTIVE, invHed.getFINVHED_IS_ACTIVE());
                values.put(FINVHED_REFNO1, invHed.getFINVHED_REFNO1());
                // values.put(FINVHED_AREACODE, invHed.getFINVHED_AREACODE());
                values.put(FINVHED_ROUTECODE, invHed.getFINVHED_ROUTECODE());
                values.put(FINVHED_TOURCODE, invHed.getFINVHED_TOURCODE());
                values.put(FINVHED_PAYTYPE, invHed.getFINVHED_PAYTYPE());
                values.put(FINVHED_SETTING_CODE, invHed.getFINVHED_SETTING_CODE());

                int cn = cursor.getCount();
                if (cn > 0) {
                    count = dB.update(TABLE_FINVHED, values, DatabaseHelper.REFNO + " =?", new String[]{String.valueOf(invHed.getFINVHED_REFNO())});
                } else {
                    count = (int) dB.insert(TABLE_FINVHED, null, values);
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

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public ArrayList<InvHed> getTodayOrders() {
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<InvHed> list = new ArrayList<InvHed>();

        try {
            //String selectQuery = "select DebCode, RefNo from fordHed " +
            String selectQuery = "select DebCode, RefNo, isSynced, TxnDate, TotalAmt from finvhed " +
                    //			" fddbnote fddb where hed.refno = det.refno and det.FPRECDET_REFNO1 = fddb.refno and hed.txndate = '2019-04-12'";
                    "  where txndate = '" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate) +"'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                InvHed recDet = new InvHed();

//
                recDet.setFINVHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                recDet.setFINVHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
                recDet.setFINVHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(FINVHED_IS_SYNCED)));
                recDet.setFINVHED_TXNTYPE("Invoice");
                recDet.setFINVHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
                recDet.setFINVHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALAMT)));
                //TODO :set  discount, free

                list.add(recDet);
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
    public InvHed getActiveInvhed() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        InvHed invHed = new InvHed();

        String selectQuery = "select * from " + TABLE_FINVHED + " Where " + FINVHED_IS_ACTIVE + "='1' and " + FINVHED_IS_SYNCED + "='0'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {

            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    invHed.setFINVHED_ID(cursor.getString(cursor.getColumnIndex(FINVHED_ID)));
                    invHed.setFINVHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                    invHed.setFINVHED_REFNO1(cursor.getString(cursor.getColumnIndex(FINVHED_REFNO1)));
                    invHed.setFINVHED_ADDDATE(cursor.getString(cursor.getColumnIndex(FINVHED_ADDDATE)));
                    invHed.setFINVHED_ADDMACH(cursor.getString(cursor.getColumnIndex(FINVHED_ADDMACH)));
                    invHed.setFINVHED_ADDUSER(cursor.getString(cursor.getColumnIndex(FINVHED_ADDUSER)));
                    invHed.setFINVHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FINVHED_COSTCODE)));
                    invHed.setFINVHED_CURCODE(cursor.getString(cursor.getColumnIndex(FINVHED_CURCODE)));
                    invHed.setFINVHED_CURRATE(cursor.getString(cursor.getColumnIndex(FINVHED_CURRATE)));
                    invHed.setFINVHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
                    invHed.setFINVHED_LOCCODE(cursor.getString(cursor.getColumnIndex(FINVHED_LOCCODE)));
                    invHed.setFINVHED_MANUREF(cursor.getString(cursor.getColumnIndex(FINVHED_MANUREF)));
                    invHed.setFINVHED_REMARKS(cursor.getString(cursor.getColumnIndex(FINVHED_REMARKS)));
                    invHed.setFINVHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
                    invHed.setFINVHED_TAXREG(cursor.getString(cursor.getColumnIndex(FINVHED_TAXREG)));
                    invHed.setFINVHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALAMT)));
                    invHed.setFINVHED_TOTALDIS(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALDIS)));
                    invHed.setFINVHED_TOTALTAX(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALTAX)));
                    invHed.setFINVHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FINVHED_TXNTYPE)));
                    invHed.setFINVHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
                    invHed.setFINVHED_SETTING_CODE(cursor.getString(cursor.getColumnIndex(FINVHED_SETTING_CODE)));

//                    invHed.setFINVHED_CONTACT(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_CONTACT)));
//                    invHed.setFINVHED_CUSADD1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_CUSADD1)));
//                    invHed.setFINVHED_CUSADD2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_CUSADD2)));
//                    invHed.setFINVHED_CUSADD3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_CUSADD3)));
//                    invHed.setFINVHED_CUSTELE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_CUSTELE)));

                    //invHed.setFINVHED_ROUTECODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_ROUTECODE)));
                    //  invHed.setFINVHED_TOURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_TOURCODE)));
                    ///  invHed.setFINVHED_AREACODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_AREACODE)));
                    //invHed.setFINVHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_PAYTYPE)));
                    //  invHed.setFINVHED_START_TIME_SO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_START_TIME_SO)));
                    return invHed;
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

        return null;
    }



    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
//
    public int updateIsSynced(InvHed mapper) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            ContentValues values = new ContentValues();

            values.put(FINVHED_IS_SYNCED, "1");

            if (mapper.getFINVHED_IS_SYNCED().equals("1")) {
                count = dB.update(TABLE_FINVHED, values, DatabaseHelper.REFNO + " =?", new String[]{String.valueOf(mapper.getFINVHED_REFNO())});
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

    public int InactiveStatusUpdate(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_FINVHED + " WHERE " + DatabaseHelper.REFNO + " = '" + refno + "'";

            cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(FINVHED_IS_ACTIVE, "0");

            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.update(TABLE_FINVHED, values, DatabaseHelper.REFNO + " =?", new String[]{String.valueOf(refno)});
            } else {
                count = (int) dB.insert(TABLE_FINVHED, null, values);
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


    /*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public InvHed getDetailsforPrint(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        InvHed SOHed = new InvHed();

        try {
            String selectQuery = "SELECT TxnDate,DebCode,Remarks,routecode,tourcode,TotalAmt,TotalDis FROM " + TABLE_FINVHED + " WHERE " + DatabaseHelper.REFNO + " = '" + Refno + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                SOHed.setFINVHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
                SOHed.setFINVHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
                SOHed.setFINVHED_REMARKS(cursor.getString(cursor.getColumnIndex(FINVHED_REMARKS)));
                SOHed.setFINVHED_TOURCODE(cursor.getString(cursor.getColumnIndex(FINVHED_TOURCODE)));
                SOHed.setFINVHED_ROUTECODE(cursor.getString(cursor.getColumnIndex(FINVHED_ROUTECODE)));
                SOHed.setFINVHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALAMT)));
                SOHed.setFINVHED_TOTALDIS(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALDIS)));
            }
            cursor.close();

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return SOHed;

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Reset invoice headers-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String restData(String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        String locCode = "";

        try {
            String selectQuery = "SELECT * FROM " + TABLE_FINVHED + " WHERE " + DatabaseHelper.REFNO + "='" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                String status = cursor.getString(cursor.getColumnIndex(FINVHED_IS_SYNCED));
                /* if invoice already synced, can't delete */
                if (status.equals("1")) {
                    locCode = "";
                } else {
                    locCode = cursor.getString(cursor.getColumnIndex(FINVHED_LOCCODE));
                    int success = dB.delete(TABLE_FINVHED, DatabaseHelper.REFNO + "='" + refno + "'", null);
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
        return locCode;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<InvHed> getAllUnsyncedInvHed(String newText, String uploaded) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<InvHed> list = new ArrayList<InvHed>();
        Cursor cursor = null;
        try {

            String selectQuery;

            if (uploaded.equals("U"))
                selectQuery = "select * from finvHed sa, fdebtor cu where sa.isActive='0' AND sa.isSynced ='1' and sa.DebCode=cu.DebCode and sa.RefNo || sa.AddDate || cu.DebName  like '%" + newText + "%'";
            else
                selectQuery = "select * from finvHed sa, fdebtor cu where sa.isActive='0'AND sa.isSynced ='0' and sa.DebCode=cu.DebCode and sa.RefNo || sa.AddDate || cu.DebName  like '%" + newText + "%'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                InvHed invHed = new InvHed();

                invHed.setFINVHED_ID(cursor.getString(cursor.getColumnIndex(FINVHED_ID)));
                invHed.setFINVHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                invHed.setFINVHED_ADDDATE(cursor.getString(cursor.getColumnIndex(FINVHED_ADDDATE)));
                invHed.setFINVHED_ADDMACH(cursor.getString(cursor.getColumnIndex(FINVHED_ADDMACH)));
                invHed.setFINVHED_ADDUSER(cursor.getString(cursor.getColumnIndex(FINVHED_ADDUSER)));
                invHed.setFINVHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FINVHED_COSTCODE)));
                invHed.setFINVHED_CURCODE(cursor.getString(cursor.getColumnIndex(FINVHED_CURCODE)));
                invHed.setFINVHED_CURRATE(cursor.getString(cursor.getColumnIndex(FINVHED_CURRATE)));
                invHed.setFINVHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
                invHed.setFINVHED_START_TIME_SO(cursor.getString(cursor.getColumnIndex(FINVHED_START_TIME_SO)));
                invHed.setFINVHED_END_TIME_SO(cursor.getString(cursor.getColumnIndex(FINVHED_END_TIME_SO)));
                invHed.setFINVHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(FINVHED_LONGITUDE)));
                invHed.setFINVHED_LATITUDE(cursor.getString(cursor.getColumnIndex(FINVHED_LATITUDE)));
                invHed.setFINVHED_LOCCODE(cursor.getString(cursor.getColumnIndex(FINVHED_LOCCODE)));
                invHed.setFINVHED_MANUREF(cursor.getString(cursor.getColumnIndex(FINVHED_MANUREF)));
                invHed.setFINVHED_REMARKS(cursor.getString(cursor.getColumnIndex(FINVHED_REMARKS)));
                invHed.setFINVHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
                invHed.setFINVHED_TAXREG(cursor.getString(cursor.getColumnIndex(FINVHED_TAXREG)));
                invHed.setFINVHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALAMT)));
                invHed.setFINVHED_TOTALDIS(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALDIS)));
                invHed.setFINVHED_TOTALTAX(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALTAX)));
                invHed.setFINVHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FINVHED_TXNTYPE)));
                invHed.setFINVHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
                invHed.setFINVHED_ADDRESS(cursor.getString(cursor.getColumnIndex(FINVHED_ADDRESS)));
                invHed.setFINVHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(FINVHED_IS_SYNCED)));
                invHed.setFINVHED_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FINVHED_IS_ACTIVE)));
                invHed.setFINVHED_CONTACT(cursor.getString(cursor.getColumnIndex(FINVHED_CONTACT)));
                invHed.setFINVHED_CUSADD1(cursor.getString(cursor.getColumnIndex(FINVHED_CUSADD1)));
                invHed.setFINVHED_CUSADD2(cursor.getString(cursor.getColumnIndex(FINVHED_CUSADD2)));
                invHed.setFINVHED_CUSADD3(cursor.getString(cursor.getColumnIndex(FINVHED_CUSADD3)));
                invHed.setFINVHED_CUSTELE(cursor.getString(cursor.getColumnIndex(FINVHED_CUSTELE)));
                invHed.setFINVHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(FINVHED_PAYTYPE)));

                list.add(invHed);

            }

        } catch (Exception e) {
            Log.v("Erorr :- ", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return list;
    }
    public ArrayList<InvHed> getAllUnsyncedInvHed() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<InvHed> list = new ArrayList<InvHed>();
        Cursor cursor = null;
        try {

            String selectQuery;

            selectQuery = "select * from finvHed sa where sa.isActive='0' AND sa.isSynced ='0'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                InvHed invHed = new InvHed();

                invHed.setFINVHED_ID(cursor.getString(cursor.getColumnIndex(FINVHED_ID)));
                invHed.setFINVHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                invHed.setFINVHED_ADDDATE(cursor.getString(cursor.getColumnIndex(FINVHED_ADDDATE)));
                invHed.setFINVHED_ADDMACH(cursor.getString(cursor.getColumnIndex(FINVHED_ADDMACH)));
                invHed.setFINVHED_ADDUSER(cursor.getString(cursor.getColumnIndex(FINVHED_ADDUSER)));
                invHed.setFINVHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FINVHED_COSTCODE)));
                invHed.setFINVHED_CURCODE(cursor.getString(cursor.getColumnIndex(FINVHED_CURCODE)));
                invHed.setFINVHED_CURRATE(cursor.getString(cursor.getColumnIndex(FINVHED_CURRATE)));
                invHed.setFINVHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
                invHed.setFINVHED_START_TIME_SO(cursor.getString(cursor.getColumnIndex(FINVHED_START_TIME_SO)));
                invHed.setFINVHED_END_TIME_SO(cursor.getString(cursor.getColumnIndex(FINVHED_END_TIME_SO)));
                invHed.setFINVHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(FINVHED_LONGITUDE)));
                invHed.setFINVHED_LATITUDE(cursor.getString(cursor.getColumnIndex(FINVHED_LATITUDE)));
                invHed.setFINVHED_LOCCODE(cursor.getString(cursor.getColumnIndex(FINVHED_LOCCODE)));
                invHed.setFINVHED_MANUREF(cursor.getString(cursor.getColumnIndex(FINVHED_MANUREF)));
                invHed.setFINVHED_REMARKS(cursor.getString(cursor.getColumnIndex(FINVHED_REMARKS)));
                invHed.setFINVHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
                invHed.setFINVHED_TAXREG(cursor.getString(cursor.getColumnIndex(FINVHED_TAXREG)));
                invHed.setFINVHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALAMT)));
                invHed.setFINVHED_TOTALDIS(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALDIS)));
                invHed.setFINVHED_TOTALTAX(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALTAX)));
                invHed.setFINVHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FINVHED_TXNTYPE)));
                invHed.setFINVHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
                invHed.setFINVHED_ADDRESS(cursor.getString(cursor.getColumnIndex(FINVHED_ADDRESS)));
                invHed.setFINVHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(FINVHED_IS_SYNCED)));
                invHed.setFINVHED_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FINVHED_IS_ACTIVE)));
                invHed.setFINVHED_CONTACT(cursor.getString(cursor.getColumnIndex(FINVHED_CONTACT)));
                invHed.setFINVHED_CUSADD1(cursor.getString(cursor.getColumnIndex(FINVHED_CUSADD1)));
                invHed.setFINVHED_CUSADD2(cursor.getString(cursor.getColumnIndex(FINVHED_CUSADD2)));
                invHed.setFINVHED_CUSADD3(cursor.getString(cursor.getColumnIndex(FINVHED_CUSADD3)));
                invHed.setFINVHED_CUSTELE(cursor.getString(cursor.getColumnIndex(FINVHED_CUSTELE)));
                invHed.setFINVHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(FINVHED_PAYTYPE)));

                list.add(invHed);

            }

        } catch (Exception e) {
            Log.v("Erorr :- ", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return list;
    }

    /* Check if all records are synced */

    public boolean isAllSynced() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        boolean res = false;

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + TABLE_FINVHED + " WHERE " + FINVHED_IS_SYNCED + "='0'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0)
                res = false;
            else
                res = true;

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



    public String getActiveInvoiceRef()
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String refNo = "";

        String selectQuery = "select * from " + TABLE_FINVHED + " WHERE " + FINVHED_IS_ACTIVE + "= '1'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            if (cursor.getCount()>0)
            {
                while(cursor.moveToNext())
                {
                    refNo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO));
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

        return refNo;
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public double getLastBillAmount() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT TotalAmt FROM " + TABLE_FINVHED;
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {

                cursor.moveToLast();
                return Double.parseDouble(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALAMT)));

            } else
                return 0.00;

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return 0.00;

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<Entry> getMonthlySales(int iMonth) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Entry> list = new ArrayList<Entry>();

        try {

            int j = 0;
            for (int i = (iMonth - 2); i < (iMonth + 1); i++) {

                int iYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
                int temp;
                if (i == 0) {
                    temp = 12;
                    iYear -= 1;
                } else if (i == -1) {
                    temp = 11;
                    iYear -= 1;
                } else {
                    temp = i;
                }

                String selectQuery = "SELECT SUM(totalamt) as totsum FROM " + TABLE_FINVHED + " WHERE  txndate LIKE '" + iYear + "-" + String.format("%02d", temp) + "-_%'";
                Cursor cursor = dB.rawQuery(selectQuery, null);

                while (cursor.moveToNext()) {

                    if (cursor.getString(cursor.getColumnIndex("totsum")) == null)
                        list.add(new Entry(0, j++));
                    else
                        list.add(new Entry(Float.parseFloat(cursor.getString(cursor.getColumnIndex("totsum"))) / 1000, j++));
                }
                cursor.close();
            }

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getRefnoByDebcode(String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + TABLE_FINVHED + " WHERE " + dbHelper.REFNO + "='" + refno + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(dbHelper.DEBCODE));
        }
        return "";

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getMonthlySales(String costCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        double invSum = 0, retSum = 0;
        try {

            int iYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
            int iMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

            String selectQuery = "SELECT SUM(totalamt) as totsum FROM " + TABLE_FINVHED + " WHERE CostCode='" + costCode + "' AND txndate LIKE '" + iYear + "-" + String.format("%02d", iMonth) + "-_%'";
            Cursor curInvHed = dB.rawQuery(selectQuery, null);

            while (curInvHed.moveToNext())
                invSum = Double.parseDouble(curInvHed.getString(curInvHed.getColumnIndex("totsum")));

            String selectQuery1 = "SELECT SUM(totalamt) as totsum FROM " + SalesReturnController.TABLE_FINVRHED + " WHERE CostCode='" + costCode + "' AND txndate LIKE '" + iYear + "-" + String.format("%02d", iMonth) + "-_%'";
            Cursor curRetHed = dB.rawQuery(selectQuery1, null);

            while (curRetHed.moveToNext())
                retSum = Double.parseDouble(curRetHed.getString(curRetHed.getColumnIndex("totsum")));

            curInvHed.close();
            curRetHed.close();

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return String.format("%,.2f", invSum - retSum);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getMonthlyVisits(String costCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int invVisit = 0, retVisit = 0;
        try {

            int iYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
            int iMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

            String selectQuery = "SELECT count(*) as totvisit FROM " + TABLE_FINVHED + " WHERE CostCode='" + costCode + "' AND txndate LIKE '" + iYear + "-" + String.format("%02d", iMonth) + "-_%'";
            Cursor curInvHed = dB.rawQuery(selectQuery, null);

            while (curInvHed.moveToNext())
                invVisit = curInvHed.getInt(curInvHed.getColumnIndex("totvisit"));

            String selectQuery1 = "SELECT count(*) as totvisit FROM " + SalesReturnController.TABLE_FINVRHED + " WHERE CostCode='" + costCode + "' AND txndate LIKE '" + iYear + "-" + String.format("%02d", iMonth) + "-_%'";
            Cursor curRetHed = dB.rawQuery(selectQuery1, null);

            while (curRetHed.moveToNext())
                retVisit = curRetHed.getInt(curRetHed.getColumnIndex("totvisit"));

            curInvHed.close();
            curRetHed.close();

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return String.valueOf(invVisit + retVisit);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getCurrentMonthlySales(String costCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int invVisit = 0;
        try {

            int iYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
            int iMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

            String selectQuery = "SELECT count(*) as totvisit FROM " + TABLE_FINVHED + " WHERE CostCode='" + costCode + "' AND txndate LIKE '" + iYear + "-" + String.format("%02d", iMonth) + "-_%'";
            Cursor curInvHed = dB.rawQuery(selectQuery, null);

            while (curInvHed.moveToNext())
                invVisit = curInvHed.getInt(curInvHed.getColumnIndex("totvisit"));

            curInvHed.close();

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return String.valueOf(invVisit);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getTodaySales(String costCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        double invSum = 0, retSum = 0;
        try {

            String sDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            String selectQuery = "SELECT SUM(totalamt) as totsum FROM " + TABLE_FINVHED + " WHERE CostCode='" + costCode + "' AND txndate='" + sDate + "'";
            Cursor curInvHed = dB.rawQuery(selectQuery, null);

            while (curInvHed.moveToNext())
                invSum = Double.parseDouble(curInvHed.getString(curInvHed.getColumnIndex("totsum")));

            String selectQuery1 = "SELECT SUM(totalamt) as totsum FROM " + SalesReturnController.TABLE_FINVRHED + " WHERE CostCode='" + costCode + "' AND txndate='" + sDate + "'";
            Cursor curRetHed = dB.rawQuery(selectQuery1, null);

            while (curRetHed.moveToNext())
                retSum = Double.parseDouble(curRetHed.getString(curRetHed.getColumnIndex("totsum")));

            curInvHed.close();
            curRetHed.close();

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return String.format("%,.2f", invSum - retSum);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getTodaySalesVisit(String costCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int invSum = 0, retSum = 0;
        try {

            String sDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            String selectQuery = "SELECT count(*) as totvisit FROM " + TABLE_FINVHED + " WHERE CostCode='" + costCode + "' AND txndate='" + sDate + "'";
            Cursor curInvHed = dB.rawQuery(selectQuery, null);

            while (curInvHed.moveToNext())
                invSum = curInvHed.getInt(curInvHed.getColumnIndex("totvisit"));

            String selectQuery1 = "SELECT count(*) as totvisit FROM " + SalesReturnController.TABLE_FINVRHED + " WHERE CostCode='" + costCode + "' AND txndate='" + sDate + "'";
            Cursor curRetHed = dB.rawQuery(selectQuery1, null);

            while (curRetHed.moveToNext())
                retSum = curRetHed.getInt(curRetHed.getColumnIndex("totvisit"));

            curInvHed.close();
            curRetHed.close();

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return String.valueOf(invSum + retSum);
    }

    public String getTodayProductivity(String costCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int invSum = 0;

        try {

            String sDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            String selectQuery = "SELECT count(*) as totvisit FROM " + TABLE_FINVHED + " WHERE CostCode='" + costCode + "' AND txndate='" + sDate + "'";
            Cursor curInvHed = dB.rawQuery(selectQuery, null);

            while (curInvHed.moveToNext())
                invSum = curInvHed.getInt(curInvHed.getColumnIndex("totvisit"));

            curInvHed.close();

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return String.valueOf(invSum);
    }

    public ArrayList<InvHed> getAllUnsynced() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<InvHed> list = new ArrayList<InvHed>();

        String selectQuery = "select * from " + TABLE_FINVHED + " Where " + FINVHED_IS_ACTIVE + "='0' AND " + FINVHED_IS_SYNCED + "='0'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        localSP = context.getSharedPreferences(SETTINGS, 0);

        while (cursor.moveToNext()) {

            InvHed vanSalesMapper = new InvHed();

            vanSalesMapper.setNextNumVal(new ReferenceController(context).getCurrentNextNumVal(context.getResources().getString(R.string.VanNumVal)));

            vanSalesMapper.setDistDB(SharedPref.getInstance(context).getDistDB().trim());
            vanSalesMapper.setConsoleDB(SharedPref.getInstance(context).getConsoleDB().trim());

            vanSalesMapper.setFINVHED_ID(cursor.getString(cursor.getColumnIndex(FINVHED_ID)));
            vanSalesMapper.setFINVHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
            vanSalesMapper.setFINVHED_ADDDATE(cursor.getString(cursor.getColumnIndex(FINVHED_ADDDATE)));
            vanSalesMapper.setFINVHED_ADDMACH(cursor.getString(cursor.getColumnIndex(FINVHED_ADDMACH)));
            vanSalesMapper.setFINVHED_ADDUSER(cursor.getString(cursor.getColumnIndex(FINVHED_ADDUSER)));
            vanSalesMapper.setFINVHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FINVHED_COSTCODE)));
            vanSalesMapper.setFINVHED_CURCODE(cursor.getString(cursor.getColumnIndex(FINVHED_CURCODE)));
            vanSalesMapper.setFINVHED_CURRATE(cursor.getString(cursor.getColumnIndex(FINVHED_CURRATE)));
            vanSalesMapper.setFINVHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
            vanSalesMapper.setFINVHED_START_TIME_SO(cursor.getString(cursor.getColumnIndex(FINVHED_START_TIME_SO)));
            vanSalesMapper.setFINVHED_END_TIME_SO(cursor.getString(cursor.getColumnIndex(FINVHED_END_TIME_SO)));
            vanSalesMapper.setFINVHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(FINVHED_LONGITUDE)));
            vanSalesMapper.setFINVHED_LATITUDE(cursor.getString(cursor.getColumnIndex(FINVHED_LATITUDE)));
            vanSalesMapper.setFINVHED_LOCCODE(cursor.getString(cursor.getColumnIndex(FINVHED_LOCCODE)));
            vanSalesMapper.setFINVHED_MANUREF(cursor.getString(cursor.getColumnIndex(FINVHED_MANUREF)));
            vanSalesMapper.setFINVHED_REMARKS(cursor.getString(cursor.getColumnIndex(FINVHED_REMARKS)));
            vanSalesMapper.setFINVHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
            vanSalesMapper.setFINVHED_TAXREG(cursor.getString(cursor.getColumnIndex(FINVHED_TAXREG)));
            vanSalesMapper.setFINVHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALAMT)));
            vanSalesMapper.setFINVHED_TOTALDIS(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALDIS)));
            vanSalesMapper.setFINVHED_TOTALTAX(cursor.getString(cursor.getColumnIndex(FINVHED_TOTALTAX)));
            vanSalesMapper.setFINVHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FINVHED_TXNTYPE)));
            vanSalesMapper.setFINVHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
            vanSalesMapper.setFINVHED_ADDRESS(cursor.getString(cursor.getColumnIndex(FINVHED_ADDRESS)));
            vanSalesMapper.setFINVHED_IS_SYNCED(cursor.getString(cursor.getColumnIndex(FINVHED_IS_SYNCED)));
            vanSalesMapper.setFINVHED_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FINVHED_IS_ACTIVE)));
            vanSalesMapper.setFINVHED_CONTACT(cursor.getString(cursor.getColumnIndex(FINVHED_CONTACT)));
            vanSalesMapper.setFINVHED_CUSADD1(cursor.getString(cursor.getColumnIndex(FINVHED_CUSADD1)));
            vanSalesMapper.setFINVHED_CUSADD2(cursor.getString(cursor.getColumnIndex(FINVHED_CUSADD2)));
            vanSalesMapper.setFINVHED_CUSADD3(cursor.getString(cursor.getColumnIndex(FINVHED_CUSADD3)));
            vanSalesMapper.setFINVHED_CUSTELE(cursor.getString(cursor.getColumnIndex(FINVHED_CUSTELE)));
            vanSalesMapper.setFINVHED_TOURCODE(cursor.getString(cursor.getColumnIndex(FINVHED_TOURCODE)));
            vanSalesMapper.setFINVHED_ROUTECODE(cursor.getString(cursor.getColumnIndex(FINVHED_ROUTECODE)));
            vanSalesMapper.setFINVHED_AREACODE(cursor.getString(cursor.getColumnIndex(FINVHED_AREACODE)));
            vanSalesMapper.setFINVHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(FINVHED_PAYTYPE)));
            vanSalesMapper.setFINVHED_SETTING_CODE(cursor.getString(cursor.getColumnIndex(FINVHED_SETTING_CODE)));


            String RefNo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO));

            vanSalesMapper.setInvDets(new InvDetController(context).getAllInvDet(RefNo));
            vanSalesMapper.setInvTaxDTs(new InvTaxDTController(context).getAllTaxDT(RefNo));
            vanSalesMapper.setInvTaxRGs(new InvTaxRGController(context).getAllTaxRG(RefNo));
            vanSalesMapper.setOrderDiscs(new OrderDiscController(context).getAllOrderDiscs(RefNo));
            vanSalesMapper.setFreeIssues(new OrdFreeIssueController(context).getAllFreeIssues(RefNo));
           // vanSalesMapper.setStkIsses(new StkIssController(context).getUploadData(RefNo));
            vanSalesMapper.setDispHeds(new DispHedController(context).getUploadData(RefNo));
            vanSalesMapper.setDispDets(new DispDetController(context).getUploadData(RefNo));
            vanSalesMapper.setDispIsses(new DispIssController(context).getUploadData(RefNo));


            list.add(vanSalesMapper);

        }

        return list;
    }
}
