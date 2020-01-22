package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.FInvRDet;
import com.datamation.swdsfa.model.TaxDT;
import com.datamation.swdsfa.model.TaxDet;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SalesReturnTaxDTController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "SalesReturnTaxRG ";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_INVRTAXDT = "fInvRTaxDT";
    public static final String INVRTAXDT_ID = "Id";
    public static final String INVRTAXDT_ITEMCODE = "ItemCode";
    public static final String INVRTAXDT_TAXCOMCODE = "TaxComCode";
    public static final String INVRTAXDT_TAXCODE = "TaxCode";
    public static final String INVRTAXDT_TAXPER = "TaxPer";
    public static final String INVRTAXDT_RATE = "TaxRate";
    public static final String INVRTAXDT_SEQ = "TaxSeq";
    public static final String INVRTAXDT_DETAMT = "TaxDetAmt";
    public static final String INVRTAXDT_BDETAMT = "BTaxDetAmt";
    public static final String INVRTAXDT_TAXTYPE = "TaxType";

    public static final String CREATE_FINVRTAXDT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_INVRTAXDT + " (" + INVRTAXDT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + INVRTAXDT_ITEMCODE + " TEXT, " + INVRTAXDT_TAXCOMCODE + " TEXT, " + INVRTAXDT_TAXCODE + " TEXT, " + INVRTAXDT_TAXPER + " TEXT, " + INVRTAXDT_RATE + " TEXT, " + INVRTAXDT_SEQ + " TEXT, " + INVRTAXDT_DETAMT + " TEXT, " + INVRTAXDT_TAXTYPE + " TEXT, " + INVRTAXDT_BDETAMT + " TEXT ); ";

    public SalesReturnTaxDTController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int UpdateReturnTaxDT(ArrayList<FInvRDet> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (FInvRDet invRDet : list) {

                if (invRDet.getFINVRDET_RETURN_TYPE().equals("MR")|| invRDet.getFINVRDET_RETURN_TYPE().equals("UR")) {

                    ArrayList<TaxDet> taxcodelist = new TaxDetController(context).getTaxInfoByComCode(invRDet.getFINVRDET_TAXCOMCODE());
                    BigDecimal amt = new BigDecimal(invRDet.getFINVRDET_AMT());

                    if (taxcodelist.size() > 0) {

                        for (int i = taxcodelist.size() - 1; i > -1; i--) {

                            BigDecimal tax = new BigDecimal("0");
                            ContentValues values = new ContentValues();

                            values.put(INVRTAXDT_ITEMCODE, invRDet.getFINVRDET_ITEMCODE());
                            values.put(INVRTAXDT_RATE, taxcodelist.get(i).getRATE());
                            values.put(DatabaseHelper.REFNO, invRDet.getFINVRDET_REFNO());
                            values.put(INVRTAXDT_SEQ, taxcodelist.get(i).getSEQ());
                            values.put(INVRTAXDT_TAXCODE, taxcodelist.get(i).getTAXCODE());
                            values.put(INVRTAXDT_TAXCOMCODE, taxcodelist.get(i).getTAXCOMCODE());
                            values.put(INVRTAXDT_TAXPER, taxcodelist.get(i).getTAXVAL());
                            values.put(INVRTAXDT_TAXTYPE, taxcodelist.get(i).getTAXTYPE());

                            tax = new BigDecimal(taxcodelist.get(i).getTAXVAL()).multiply(amt.divide(new BigDecimal("100"), 3, BigDecimal.ROUND_HALF_EVEN));
                            amt = new BigDecimal(taxcodelist.get(i).getTAXVAL()).add(new BigDecimal("100")).multiply((amt.divide(new BigDecimal("100"), 3, BigDecimal.ROUND_HALF_EVEN)));

                            values.put(INVRTAXDT_BDETAMT, String.format("%.2f", tax));
                            values.put(INVRTAXDT_DETAMT, String.format("%.2f", tax));

                            count = (int) dB.insert(TABLE_INVRTAXDT, null, values);

                        }
                    }
                }
            }
        } catch (

                Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }
        return count;

    }

    public ArrayList<TaxDT> getAllTaxDT(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<TaxDT> list = new ArrayList<TaxDT>();
        try {
            String selectQuery = "select * from " + TABLE_INVRTAXDT + " WHERE RefNo='" + RefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                TaxDT tax = new TaxDT();

                tax.setREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                tax.setTAXCODE(cursor.getString(cursor.getColumnIndex(INVRTAXDT_TAXCODE)));
                tax.setBTAXDETAMT(cursor.getString(cursor.getColumnIndex(INVRTAXDT_BDETAMT)));
                tax.setTAXCOMCODE(cursor.getString(cursor.getColumnIndex(INVRTAXDT_TAXCOMCODE)));
                tax.setTAXDETAMT(cursor.getString(cursor.getColumnIndex(INVRTAXDT_DETAMT)));
                tax.setTAXPER(cursor.getString(cursor.getColumnIndex(INVRTAXDT_TAXPER)));
                tax.setTAXRATE(cursor.getString(cursor.getColumnIndex(INVRTAXDT_RATE)));
                tax.setTAXSEQ(cursor.getString(cursor.getColumnIndex(INVRTAXDT_SEQ)));
                tax.setITEMCODE(cursor.getString(cursor.getColumnIndex(INVRTAXDT_ITEMCODE)));

                list.add(tax);
            }
            cursor.close();

        } catch (Exception e) {
            Log.v("Erorr ", e.toString());

        } finally {
            dB.close();
        }

        return list;

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void ClearTable(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            dB.delete(TABLE_INVRTAXDT, DatabaseHelper.REFNO + "='" + RefNo + "'", null);
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

    }

}
