package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.FInvhedL3;
import com.datamation.swdsfa.model.Last3Invoice;

import java.util.ArrayList;

public class FInvhedL3Controller {

    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    // rashmi - 2019-12-24 move from database_helper , because of reduce coding in database helper*******************************************************************************

    // table
    public static final String TABLE_FINVHEDL3 = "FinvHedL3";
    // table attributes
    public static final String FINVHEDL3_ID = "FinvHedL3_id";
    public static final String FINVHEDL3_REF_NO1 = "RefNo1";

    public static final String FINVHEDL3_TOTAL_AMT = "TotalAmt";
    public static final String FINVHEDL3_TOTAL_TAX = "TotalTax";


    // create String
    public static final String CREATE_FINVHEDL3_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FINVHEDL3 + " (" + FINVHEDL3_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.DEBCODE + " TEXT, " + DatabaseHelper.REFNO + " TEXT, " + FINVHEDL3_REF_NO1 + " TEXT, " + FINVHEDL3_TOTAL_AMT + " TEXT, " + FINVHEDL3_TOTAL_TAX + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT); ";

    public static final String TESTINVHEDL3 = "CREATE UNIQUE INDEX IF NOT EXISTS idxinvhedl3_something ON " + TABLE_FINVHEDL3 + " (" + DatabaseHelper.REFNO + ")";

    public FInvhedL3Controller(Context context){
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public void createOrUpdateFinvHedL3(ArrayList<FInvhedL3> list) {
        Log.d("InsrtOrReplceFinvHedL3", "" + list.size());
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_FINVHEDL3 + " (DebCode,RefNo,RefNo1,TotalAmt,TotalTax,TxnDate) " + " VALUES (?,?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);
            for (FInvhedL3 finvHedL3 : list) {

                stmt.bindString(1, finvHedL3.getFINVHEDL3_DEB_CODE());
                stmt.bindString(2, finvHedL3.getFINVHEDL3_REF_NO());
                stmt.bindString(3, finvHedL3.getFINVHEDL3_REF_NO1());
                stmt.bindString(4, finvHedL3.getFINVHEDL3_TOTAL_AMT());
                stmt.bindString(5, finvHedL3.getFINVHEDL3_TOTAL_TAX());
                stmt.bindString(6, finvHedL3.getFINVHEDL3_TXN_DATE());

                stmt.execute();
                stmt.clearBindings();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dB.setTransactionSuccessful();
            dB.endTransaction();
            dB.close();
        }
    }

    public int deleteAll() {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FINVHEDL3, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FINVHEDL3, null, null);
                Log.v("Success", success + "");
            }
        } catch (Exception e) {

            Log.v("FINVHEDL3"+ " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return count;

    }


    public ArrayList<FInvhedL3> getLast3InvoiceHed(String debcode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FInvhedL3> list = new ArrayList<FInvhedL3>();

        String selectQuery = "select * from FInvhedL3 where DebCode ='" + debcode + "' ORDER by TxnDate DESC limit 3 ";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){
            FInvhedL3 invhedL3 =new FInvhedL3();

            invhedL3.setFINVHEDL3_ID(cursor.getString(cursor.getColumnIndex(FINVHEDL3_ID)));
            invhedL3.setFINVHEDL3_DEB_CODE(cursor.getString(cursor.getColumnIndex(dbHelper.DEBCODE)));
            invhedL3.setFINVHEDL3_REF_NO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
            invhedL3.setFINVHEDL3_REF_NO1(cursor.getString(cursor.getColumnIndex(FINVHEDL3_REF_NO1)));
            invhedL3.setFINVHEDL3_TOTAL_AMT(cursor.getString(cursor.getColumnIndex(FINVHEDL3_TOTAL_AMT)));
            invhedL3.setFINVHEDL3_TOTAL_TAX(cursor.getString(cursor.getColumnIndex(FINVHEDL3_TOTAL_TAX)));
            invhedL3.setFINVHEDL3_TXN_DATE(cursor.getString(cursor.getColumnIndex(dbHelper.TXNDATE)));

            list.add(invhedL3);
        }

        return list;
    }


    public ArrayList<Last3Invoice> getLast3InvoiceDetails(String debcode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Last3Invoice> list = new ArrayList<Last3Invoice>();

        String selectQuery = "SELECT AA.ItemName,AA.ItemCode,AA.Qty,AA.Amt,AA.Qty1,AA.Amt1,ifnull(BB.Qty,0) As Qty2,ifnull(BB.Amt,0) As Amt2\n" +
                "\n" +
                "FROM (SELECT A.ItemName,A.ItemCode,A.Qty,A.Amt,ifnull(B.Qty,0) As Qty1,ifnull(B.Amt,0) As Amt1\n" +
                "\t\t\n" +
                "\t\tFROM (select h.RefNo1,h.TxnDate,i.ItemName, d.ItemCode,d.Qty,d.Amt\n" +
                "\t\tfrom FInvdetL3 d, fItem i, FinvHedL3 h\n" +
                "\t\twhere d.RefNo = (select RefNo from FInvhedL3 where DebCode = '"+debcode+"' ORDER by TxnDate DESC limit 1 OFFSET 0) AND i.ItemCode=TRIM(d.ItemCode,' ') AND h.RefNo = d.RefNo\n" +
                "\t\tORDER By d.ItemCode) A\n" +
                "\t\t\n" +
                "\t\tLEFT JOIN \n" +
                "\t\t\n" +
                "\t\t(select h.RefNo1,h.TxnDate,i.ItemName, d.ItemCode,d.Qty,d.Amt\n" +
                "\t\tfrom FInvdetL3 d, fItem i, FinvHedL3 h\n" +
                "\t\twhere d.RefNo = (select RefNo from FInvhedL3 where DebCode = '"+debcode+"' ORDER by TxnDate DESC limit 1 OFFSET 1) AND i.ItemCode=TRIM(d.ItemCode,' ') AND h.RefNo = d.RefNo\n" +
                "\t\tORDER By d.ItemCode) B\n" +
                "\t\t\n" +
                "\t\tON A.ItemCode = B.ItemCode\n" +
                "\t\t\n" +
                "\t\tUNION ALL\n" +
                "\t\t\n" +
                "\t\tSELECT B.ItemName,B.ItemCode,ifnull(A.Qty,0),ifnull(A.Amt,0),B.Qty As Qty1,B.Amt As Amt1\n" +
                "\t\t\n" +
                "\t\tFROM (select h.RefNo1,h.TxnDate,i.ItemName, d.ItemCode,d.Qty,d.Amt\n" +
                "\t\tfrom FInvdetL3 d, fItem i, FinvHedL3 h\n" +
                "\t\twhere d.RefNo = (select RefNo from FInvhedL3 where DebCode = '"+debcode+"' ORDER by TxnDate DESC limit 1 OFFSET 1) AND i.ItemCode=TRIM(d.ItemCode,' ') AND h.RefNo = d.RefNo\n" +
                "\t\tORDER By d.ItemCode) B\n" +
                "\t\t\n" +
                "\t\tLEFT JOIN \n" +
                "\t\t\n" +
                "\t\t(select h.RefNo1,h.TxnDate,i.ItemName, d.ItemCode,d.Qty,d.Amt\n" +
                "\t\tfrom FInvdetL3 d, fItem i, FinvHedL3 h\n" +
                "\t\twhere d.RefNo = (select RefNo from FInvhedL3 where DebCode = '"+debcode+"' ORDER by TxnDate DESC limit 1 OFFSET 0) AND i.ItemCode=TRIM(d.ItemCode,' ') AND h.RefNo = d.RefNo\n" +
                "\t\tORDER By d.ItemCode) A\n" +
                "\t\t\n" +
                "\t\tON A.ItemCode = B.ItemCode\n" +
                "\t\t\n" +
                "\t\tWhere A.ItemCode is null) AA \n" +
                "\n" +
                "LEFT JOIN (select h.RefNo1,h.TxnDate,i.ItemName, d.ItemCode,d.Qty,d.Amt\n" +
                "\t\tfrom FInvdetL3 d, fItem i, FinvHedL3 h\n" +
                "\t\twhere d.RefNo = (select RefNo from FInvhedL3 where DebCode = '"+debcode+"' ORDER by TxnDate DESC limit 1 OFFSET 2) AND i.ItemCode=TRIM(d.ItemCode,' ') AND h.RefNo = d.RefNo\n" +
                "\t\tORDER By d.ItemCode) BB\n" +
                "\t\t\n" +
                "\tON AA.ItemCode = BB.ItemCode\n" +
                "\n" +
                "UNION ALL\n" +
                "\t\n" +
                "\tSELECT BB.ItemName,BB.ItemCode,AA.Qty,AA.Amt,AA.Qty1,AA.Amt1,ifnull(BB.Qty,0) As Qty2,ifnull(BB.Amt,0) As Amt2\n" +
                "\tFROM (select h.RefNo1,h.TxnDate,i.ItemName, d.ItemCode,d.Qty,d.Amt\n" +
                "\t\tfrom FInvdetL3 d, fItem i, FinvHedL3 h\n" +
                "\t\twhere d.RefNo = (select RefNo from FInvhedL3 where DebCode = '"+debcode+"' ORDER by TxnDate DESC limit 1 OFFSET 2) AND i.ItemCode=TRIM(d.ItemCode,' ') AND h.RefNo = d.RefNo\n" +
                "\t\tORDER By d.ItemCode) BB\n" +
                "\t\n" +
                "\tLEFT JOIN (SELECT A.ItemName,A.ItemCode,A.Qty,A.Amt,ifnull(B.Qty,0) As Qty1,ifnull(B.Amt,0) As Amt1\n" +
                "\t\t\n" +
                "\t\tFROM (select h.RefNo1,h.TxnDate,i.ItemName, d.ItemCode,d.Qty,d.Amt\n" +
                "\t\tfrom FInvdetL3 d, fItem i, FinvHedL3 h\n" +
                "\t\twhere d.RefNo = (select RefNo from FInvhedL3 where DebCode = '"+debcode+"' ORDER by TxnDate DESC limit 1 OFFSET 0) AND i.ItemCode=TRIM(d.ItemCode,' ') AND h.RefNo = d.RefNo\n" +
                "\t\tORDER By d.ItemCode) A\n" +
                "\t\t\n" +
                "\t\tLEFT JOIN \n" +
                "\t\t\n" +
                "\t\t(select h.RefNo1,h.TxnDate,i.ItemName, d.ItemCode,d.Qty,d.Amt\n" +
                "\t\tfrom FInvdetL3 d, fItem i, FinvHedL3 h\n" +
                "\t\twhere d.RefNo = (select RefNo from FInvhedL3 where DebCode = '"+debcode+"' ORDER by TxnDate DESC limit 1 OFFSET 1) AND i.ItemCode=TRIM(d.ItemCode,' ') AND h.RefNo = d.RefNo\n" +
                "\t\tORDER By d.ItemCode) B\n" +
                "\t\t\n" +
                "\t\tON A.ItemCode = B.ItemCode\n" +
                "\t\t\n" +
                "\t\tUNION ALL\n" +
                "\t\t\n" +
                "\t\tSELECT B.ItemName,B.ItemCode,ifnull(A.Qty,0),ifnull(A.Amt,0),B.Qty As Qty1,B.Amt As Amt1\n" +
                "\t\t\n" +
                "\t\tFROM (select h.RefNo1,h.TxnDate,i.ItemName, d.ItemCode,d.Qty,d.Amt\n" +
                "\t\tfrom FInvdetL3 d, fItem i, FinvHedL3 h\n" +
                "\t\twhere d.RefNo = (select RefNo from FInvhedL3 where DebCode = '"+debcode+"' ORDER by TxnDate DESC limit 1 OFFSET 1) AND i.ItemCode=TRIM(d.ItemCode,' ') AND h.RefNo = d.RefNo\n" +
                "\t\tORDER By d.ItemCode) B\n" +
                "\t\t\n" +
                "\t\tLEFT JOIN \n" +
                "\t\t\n" +
                "\t\t(select h.RefNo1,h.TxnDate,i.ItemName, d.ItemCode,d.Qty,d.Amt\n" +
                "\t\tfrom FInvdetL3 d, fItem i, FinvHedL3 h\n" +
                "\t\twhere d.RefNo = (select RefNo from FInvhedL3 where DebCode = '"+debcode+"' ORDER by TxnDate DESC limit 1 OFFSET 0) AND i.ItemCode=TRIM(d.ItemCode,' ') AND h.RefNo = d.RefNo\n" +
                "\t\tORDER By d.ItemCode) A\n" +
                "\t\t\n" +
                "\t\tON A.ItemCode = B.ItemCode\n" +
                "\t\t\n" +
                "\t\tWhere A.ItemCode is null) AA \t\n" +
                "\t\t\n" +
                "\tON AA.ItemCode = BB.ItemCode\n" +
                "\t\t\n" +
                "\tWhere AA.ItemCode is null";

        Log.v("Query",selectQuery);

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){

            Last3Invoice l3 =new Last3Invoice();

            l3.setItemName(cursor.getString(0));
            l3.setItemCode(cursor.getString(1));
            l3.setQty1(cursor.getInt(2));
            l3.setVal1(cursor.getDouble(3));

            l3.setQty2(cursor.getInt(4));
            l3.setVal2(cursor.getDouble(5));

            l3.setQty3(cursor.getInt(6));
            l3.setVal3(cursor.getDouble(7));

            list.add(l3);
        }

        return list;
    }

}
