package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.DispHed;
import com.datamation.swdsfa.model.InvHed;

import java.util.ArrayList;

public class DispHedController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "DispHedDS";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FDISPHED = "FDispHed";
    public static final String FDISPHED_ID = "Id";

    public static final String FDISPHED_REFNO1 = "RefNo1";
    public static final String FDISPHED_MANUREF = "ManuRef";
    public static final String FDISPHED_TOTALAMT = "TotalAmt";
    public static final String FDISPHED_LOCCODE = "LocCode";
    public static final String FDISPHED_COSTCODE = "CostCode";
    public static final String FDISPHED_REMARKS = "Remarks";
    public static final String FDISPHED_TXNTYPE = "TxnType";
    public static final String FDISPHED_INVOICE = "Invoice";
    public static final String FDISPHED_CONTACT = "Contact";
    public static final String FDISPHED_CUSADD1 = "CusAdd1";
    public static final String FDISPHED_CUSADD2 = "CusAdd2";
    public static final String FDISPHED_CUSADD3 = "CusAdd3";
    public static final String FDISPHED_CUSTELE = "CusTele";
    public static final String FDISPHED_ADDUSER = "AddUser";
    public static final String FDISPHED_ADDDATE = "AddDate";
    public static final String FDISPHED_ADDMACH = "AddMach";

    public static final String CREATE_FDISPHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISPHED + " (" + FDISPHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT," + DatabaseHelper.TXNDATE + " TEXT," + FDISPHED_REFNO1 + " TEXT," + FDISPHED_MANUREF + " TEXT," + FDISPHED_TOTALAMT + " TEXT," + FDISPHED_LOCCODE + " TEXT," + FDISPHED_COSTCODE + " TEXT," + DatabaseHelper.DEBCODE + " TEXT," + DatabaseHelper.REPCODE + " TEXT," + FDISPHED_REMARKS + " TEXT," + FDISPHED_TXNTYPE + " TEXT," + FDISPHED_INVOICE + " TEXT," + FDISPHED_CONTACT + " TEXT," + FDISPHED_CUSADD1 + " TEXT," + FDISPHED_CUSADD2 + " TEXT," + FDISPHED_CUSADD3 + " TEXT," + FDISPHED_CUSTELE + " TEXT," + FDISPHED_ADDUSER + " TEXT," + FDISPHED_ADDDATE + " TEXT," + FDISPHED_ADDMACH + " TEXT);";

    public DispHedController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int updateHeader(InvHed invHed, String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int count = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(FDISPHED_ADDDATE, invHed.getFINVHED_ADDDATE());
            values.put(FDISPHED_ADDMACH, invHed.getFINVHED_ADDMACH());
            values.put(FDISPHED_ADDUSER, invHed.getFINVHED_ADDUSER());
            values.put(FDISPHED_CONTACT, invHed.getFINVHED_CONTACT());
            values.put(FDISPHED_COSTCODE, invHed.getFINVHED_COSTCODE());
            values.put(FDISPHED_CUSADD1, invHed.getFINVHED_CUSADD1());
            values.put(FDISPHED_CUSADD2, invHed.getFINVHED_CUSADD2());
            values.put(FDISPHED_CUSADD3, invHed.getFINVHED_CUSADD3());
            values.put(FDISPHED_CUSTELE, invHed.getFINVHED_CUSTELE());
            values.put(DatabaseHelper.DEBCODE, invHed.getFINVHED_DEBCODE());
            values.put(FDISPHED_INVOICE, "1");
            values.put(FDISPHED_LOCCODE, invHed.getFINVHED_LOCCODE());
            values.put(FDISPHED_MANUREF, invHed.getFINVHED_MANUREF());
            values.put(DatabaseHelper.REFNO, Refno);
            values.put(FDISPHED_REFNO1, invHed.getFINVHED_REFNO());
            values.put(FDISPHED_REMARKS, invHed.getFINVHED_REMARKS());
            values.put(DatabaseHelper.REPCODE, invHed.getFINVHED_REPCODE());
            values.put(FDISPHED_TOTALAMT, invHed.getFINVHED_TOTALAMT());
            values.put(DatabaseHelper.TXNDATE, invHed.getFINVHED_TXNDATE());
            values.put(FDISPHED_TXNTYPE, "23");

            count = (int) dB.insert(TABLE_FDISPHED, null, values);

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
            return 0;
        } finally {
            dB.close();
        }

        return count;
    }

    public int clearTable(String refNo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            count = dB.delete(TABLE_FDISPHED, FDISPHED_REFNO1 + " = '" + refNo + "'", null);

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return count;

    }

    public ArrayList<DispHed> getUploadData(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<DispHed> list = new ArrayList<DispHed>();

        Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FDISPHED + " WHERE refno1='" + Refno + "'", null);

        while (cursor.moveToNext()) {

            DispHed dispHed = new DispHed();

            dispHed.setFDISPHED_ADDDATE(cursor.getString(cursor.getColumnIndex(FDISPHED_ADDDATE)));
            dispHed.setFDISPHED_ADDMACH(cursor.getString(cursor.getColumnIndex(FDISPHED_ADDMACH)));
            dispHed.setFDISPHED_ADDUSER(cursor.getString(cursor.getColumnIndex(FDISPHED_ADDUSER)));
            dispHed.setFDISPHED_CONTACT(cursor.getString(cursor.getColumnIndex(FDISPHED_CONTACT)));
            dispHed.setFDISPHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FDISPHED_COSTCODE)));
            dispHed.setFDISPHED_CUSADD1(cursor.getString(cursor.getColumnIndex(FDISPHED_CUSADD1)));
            dispHed.setFDISPHED_CUSADD2(cursor.getString(cursor.getColumnIndex(FDISPHED_CUSADD2)));
            dispHed.setFDISPHED_CUSADD3(cursor.getString(cursor.getColumnIndex(FDISPHED_CUSADD3)));
            dispHed.setFDISPHED_CUSTELE(cursor.getString(cursor.getColumnIndex(FDISPHED_CUSTELE)));
            dispHed.setFDISPHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
            dispHed.setFDISPHED_INVOICE(cursor.getString(cursor.getColumnIndex(FDISPHED_INVOICE)));
            dispHed.setFDISPHED_LOCCODE(cursor.getString(cursor.getColumnIndex(FDISPHED_LOCCODE)));
            dispHed.setFDISPHED_MANUREF(cursor.getString(cursor.getColumnIndex(FDISPHED_MANUREF)));
            dispHed.setFDISPHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
            dispHed.setFDISPHED_REFNO1(cursor.getString(cursor.getColumnIndex(FDISPHED_REFNO1)));
            dispHed.setFDISPHED_REMARKS(cursor.getString(cursor.getColumnIndex(FDISPHED_REMARKS)));
            dispHed.setFDISPHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
            dispHed.setFDISPHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(FDISPHED_TOTALAMT)));
            dispHed.setFDISPHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
            dispHed.setFDISPHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FDISPHED_TXNTYPE)));
            list.add(dispHed);
        }

        cursor.close();
        dB.close();
        return list;

    }

}
