package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.DispDet;
import com.datamation.swdsfa.model.InvDet;

import java.util.ArrayList;

public class DispDetController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "DispHedDS ";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************
    public static final String TABLE_FDISPDET = "FDispDet";
    public static final String FDISPDET_ID = "Id";
    public static final String FDISPDET_TXNTYPE = "TxnType";
    public static final String FDISPDET_ITEMCODE = "ItemCode";
    public static final String FDISPDET_QTY = "Qty";
    public static final String FDISPDET_BALQTY = "BalQty";
    public static final String FDISPDET_SAQTY = "SAQty";
    public static final String FDISPDET_BALSAQTY = "BalSAQty";
    public static final String FDISPDET_FIQTY = "FIQty";
    public static final String FDISPDET_BALFIQTY = "BalFIQty";
    public static final String FDISPDET_COSTPRICE = "CostPrice";
    public static final String FDISPDET_AMT = "Amt";
    public static final String FDISPDET_SEQNO = "SeqNo";
    public static final String FDISPDET_REFNO1 = "RefNo1";
    public static final String CREATE_FDISPDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISPDET + " (" + FDISPDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT ," + DatabaseHelper.TXNDATE + " TEXT," + FDISPDET_TXNTYPE + " TEXT," + FDISPDET_ITEMCODE + " TEXT," + FDISPDET_QTY + " TEXT DEFAULT '0'," + FDISPDET_BALQTY + " TEXT DEFAULT '0'," + FDISPDET_SAQTY + " TEXT DEFAULT '0'," + FDISPDET_BALSAQTY + " TEXT DEFAULT '0'," + FDISPDET_FIQTY + " TEXT DEFAULT '0'," + FDISPDET_BALFIQTY + " TEXT DEFAULT '0'," + FDISPDET_COSTPRICE + " TEXT," + FDISPDET_AMT + " TEXT," + FDISPDET_REFNO1 + " TEXT," + FDISPDET_SEQNO + " TEXT);";

    public DispDetController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int updateDispDet(ArrayList<InvDet> list, String dispRefno) {

        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (InvDet invDet : list) {

                count = 0;

                ContentValues values = new ContentValues();

                values.put(FDISPDET_AMT, invDet.getFINVDET_AMT());
                values.put(FDISPDET_COSTPRICE, invDet.getFINVDET_SELL_PRICE());

                if (invDet.getFINVDET_TYPE().equals("SA")) {
                    values.put(FDISPDET_BALFIQTY, invDet.getFINVDET_QTY());
                    values.put(FDISPDET_BALQTY, invDet.getFINVDET_QTY());
                    values.put(FDISPDET_BALSAQTY, invDet.getFINVDET_QTY());
                    values.put(FDISPDET_QTY, invDet.getFINVDET_QTY());
                    values.put(FDISPDET_SAQTY, invDet.getFINVDET_QTY());
                } else
                    values.put(FDISPDET_FIQTY, invDet.getFINVDET_QTY());

                values.put(FDISPDET_ITEMCODE, invDet.getFINVDET_ITEM_CODE());
                values.put(DatabaseHelper.REFNO, dispRefno);
                values.put(FDISPDET_REFNO1, invDet.getFINVDET_REFNO());
                values.put(FDISPDET_SEQNO, invDet.getFINVDET_SEQNO());
                values.put(DatabaseHelper.TXNDATE, invDet.getFINVDET_TXN_DATE());
                values.put(FDISPDET_TXNTYPE, "23");

                count = (int) dB.insert(TABLE_FDISPDET, null, values);
            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
            return 0;
        } finally {
            dB.close();
        }

        return count;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int clearTable(String refNo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            count = dB.delete(TABLE_FDISPDET, FDISPDET_REFNO1 + " = '" + refNo + "'", null);

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return count;

    }


    public ArrayList<DispDet> getUploadData(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<DispDet> list = new ArrayList<DispDet>();

        Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FDISPDET + " WHERE refno1='" + Refno + "'", null);

        while (cursor.moveToNext()) {

            DispDet dispDet = new DispDet();

            dispDet.setFDISPDET_AMT(cursor.getString(cursor.getColumnIndex(FDISPDET_AMT)));
            dispDet.setFDISPDET_BALFIQTY(cursor.getString(cursor.getColumnIndex(FDISPDET_BALFIQTY)));
            dispDet.setFDISPDET_BALQTY(cursor.getString(cursor.getColumnIndex(FDISPDET_BALQTY)));
            dispDet.setFDISPDET_BALSAQTY(cursor.getString(cursor.getColumnIndex(FDISPDET_BALSAQTY)));
            dispDet.setFDISPDET_COSTPRICE(cursor.getString(cursor.getColumnIndex(FDISPDET_COSTPRICE)));
            dispDet.setFDISPDET_FIQTY(cursor.getString(cursor.getColumnIndex(FDISPDET_FIQTY)));
            dispDet.setFDISPDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(FDISPDET_ITEMCODE)));
            dispDet.setFDISPDET_QTY(cursor.getString(cursor.getColumnIndex(FDISPDET_QTY)));
            dispDet.setFDISPDET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
            dispDet.setFDISPDET_SAQTY(cursor.getString(cursor.getColumnIndex(FDISPDET_SAQTY)));
            dispDet.setFDISPDET_SEQNO(cursor.getString(cursor.getColumnIndex(FDISPDET_SEQNO)));
            dispDet.setFDISPDET_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
            dispDet.setFDISPDET_TXNTYPE(cursor.getString(cursor.getColumnIndex(FDISPDET_TXNTYPE)));
            list.add(dispDet);
        }

        cursor.close();
        dB.close();
        return list;
    }


}
