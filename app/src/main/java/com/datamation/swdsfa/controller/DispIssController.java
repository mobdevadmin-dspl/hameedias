package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.DispIss;
import com.datamation.swdsfa.model.StkIss;

import java.util.ArrayList;

public class DispIssController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "DispHedDS";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FDISPISS = "FDispIss";
    public static final String FDISPISS_ID = "Id";
    public static final String FDISPISS_LOCCODE = "LocCode";
    public static final String FDISPISS_STKRECNO = "StkRecNo";
    ;
    public static final String FDISPISS_STKRECDATE = "StkRecDate";
    public static final String FDISPISS_STKTXNNO = "StkTxnNo";
    public static final String FDISPISS_STKTXNDATE = "StkTxnDate";
    public static final String FDISPISS_STKTXNTYPE = "StkTxnType";

    // --------------------------------------------------------------------------------------------------------------
    public static final String FDISPISS_ITEMCODE = "ItemCode";
    public static final String FDISPISS_QTY = "Qty";
    public static final String FDISPISS_BALQTY = "BalQty";
    public static final String FDISPISS_COSTPRICE = "CostPrice";
    public static final String FDISPISS_AMT = "Amt";
    public static final String FDISPISS_OTHCOST = "OthCost";
    public static final String FDISPISS_REFNO1 = "Refno1";

    public static final String CREATE_FDISPISS_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISPISS + " (" + FDISPISS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT," + DatabaseHelper.TXNDATE + " TEXT," + FDISPISS_LOCCODE + " TEXT," + FDISPISS_STKRECNO + " TEXT," + FDISPISS_STKRECDATE + " TEXT," + FDISPISS_STKTXNNO + " TEXT," + FDISPISS_STKTXNDATE + " TEXT," + FDISPISS_STKTXNTYPE + " TEXT," + FDISPISS_ITEMCODE + " TEXT," + FDISPISS_QTY + " TEXT," + FDISPISS_BALQTY + " TEXT," + FDISPISS_COSTPRICE + " TEXT," + FDISPISS_AMT + " TEXT," + FDISPISS_REFNO1 + " TEXT," + FDISPISS_OTHCOST + " TEXT);";

    public DispIssController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int updateDispIss(ArrayList<StkIss> list, String disRefno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (StkIss iss : list) {

                ContentValues values = new ContentValues();

                values.put(FDISPISS_AMT, String.format("%.2f", Double.parseDouble(iss.getQTY()) * Double.parseDouble(iss.getCOSTPRICE())));
                values.put(FDISPISS_BALQTY, iss.getQTY());
                values.put(FDISPISS_COSTPRICE, iss.getCOSTPRICE());
                values.put(FDISPISS_QTY, iss.getQTY());
                values.put(FDISPISS_ITEMCODE, iss.getITEMCODE());
                values.put(FDISPISS_OTHCOST, "0");
                values.put(DatabaseHelper.REFNO, disRefno);
                values.put(FDISPISS_STKRECNO, iss.getSTKRECNO());
                values.put(FDISPISS_STKRECDATE, iss.getSTKRECDATE());
                values.put(FDISPISS_LOCCODE, iss.getLOCCODE());
                values.put(FDISPISS_STKTXNDATE, iss.getSTKTXNDATE());
                values.put(FDISPISS_STKTXNTYPE, iss.getSTKTXNTYPE());
                values.put(FDISPISS_STKTXNNO, iss.getSTKTXNNO());
                values.put(DatabaseHelper.TXNDATE, iss.getTXN_DATE());
                values.put(FDISPISS_REFNO1, iss.getREFNO());

                count = (int) dB.insert(TABLE_FDISPISS, null, values);

            }

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

            count = dB.delete(TABLE_FDISPISS, FDISPISS_REFNO1 + " = '" + refNo + "'", null);

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return count;

    }


    public ArrayList<DispIss> getUploadData(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<DispIss> list = new ArrayList<DispIss>();

        Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FDISPISS + " WHERE RefNo1='" + Refno + "'", null);

        while (cursor.moveToNext()) {

            DispIss dispIss = new DispIss();

            dispIss.setFDISPISS_AMT(cursor.getString(cursor.getColumnIndex(FDISPISS_AMT)));
            dispIss.setFDISPISS_BALQTY(cursor.getString(cursor.getColumnIndex(FDISPISS_BALQTY)));
            dispIss.setFDISPISS_COSTPRICE(cursor.getString(cursor.getColumnIndex(FDISPISS_COSTPRICE)));
            dispIss.setFDISPISS_ITEMCODE(cursor.getString(cursor.getColumnIndex(FDISPISS_ITEMCODE)));
            dispIss.setFDISPISS_LOCCODE(cursor.getString(cursor.getColumnIndex(FDISPISS_LOCCODE)));
            dispIss.setFDISPISS_OTHCOST(cursor.getString(cursor.getColumnIndex(FDISPISS_OTHCOST)));
            dispIss.setFDISPISS_QTY(cursor.getString(cursor.getColumnIndex(FDISPISS_QTY)));
            dispIss.setFDISPISS_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
            dispIss.setFDISPISS_STKRECDATE(cursor.getString(cursor.getColumnIndex(FDISPISS_STKRECDATE)));
            dispIss.setFDISPISS_STKRECNO(cursor.getString(cursor.getColumnIndex(FDISPISS_STKRECNO)));
            dispIss.setFDISPISS_STKTXNDATE(cursor.getString(cursor.getColumnIndex(FDISPISS_STKTXNDATE)));
            dispIss.setFDISPISS_STKTXNNO(cursor.getString(cursor.getColumnIndex(FDISPISS_STKTXNNO)));
            dispIss.setFDISPISS_STKTXNTYPE(cursor.getString(cursor.getColumnIndex(FDISPISS_STKTXNTYPE)));
            dispIss.setFDISPISS_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));

            list.add(dispIss);
        }

        cursor.close();
        dB.close();
        return list;
    }


}
