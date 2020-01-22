package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.OrderDetail;
import com.datamation.swdsfa.model.TaxDT;
import com.datamation.swdsfa.model.TaxDet;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PreSaleTaxDTController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "PreTaxRG ";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_PRETAXDT = "fPreTaxDT";
    public static final String PRETAXDT_ID = "Id";
    public static final String PRETAXDT_ITEMCODE = "ItemCode";
    public static final String PRETAXDT_TAXCOMCODE = "TaxComCode";
    public static final String PRETAXDT_TAXCODE = "TaxCode";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-PRETaxRG-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String PRETAXDT_TAXPER = "TaxPer";
    public static final String PRETAXDT_RATE = "TaxRate";
    public static final String PRETAXDT_SEQ = "TaxSeq";
    public static final String PRETAXDT_DETAMT = "TaxDetAmt";
    public static final String PRETAXDT_BDETAMT = "BTaxDetAmt";
    public static final String PRETAXDT_TAXTYPE = "TaxType";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-pre TAX DT-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*/
    public static final String CREATE_FPRETAXDT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRETAXDT + " (" + PRETAXDT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + PRETAXDT_ITEMCODE + " TEXT, " + PRETAXDT_TAXCOMCODE + " TEXT, " + PRETAXDT_TAXCODE + " TEXT, " + PRETAXDT_TAXPER + " TEXT, " + PRETAXDT_RATE + " TEXT, " + PRETAXDT_SEQ + " TEXT, " + PRETAXDT_DETAMT + " TEXT, " + PRETAXDT_TAXTYPE + " TEXT, " + PRETAXDT_BDETAMT + " TEXT ); ";


    public PreSaleTaxDTController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int UpdateSalesTaxDT(ArrayList<OrderDetail> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (OrderDetail invDet : list) {

                if (invDet.getFORDERDET_TYPE().equals("SA")) {

                    ArrayList<TaxDet> taxcodelist = new TaxDetController(context).getTaxInfoByComCode(invDet.getFORDERDET_TAXCOMCODE());
                    BigDecimal amt = new BigDecimal(invDet.getFORDERDET_AMT());

                    if (taxcodelist.size() > 0) {

                        for (int i = taxcodelist.size() - 1; i > -1; i--) {

                            BigDecimal tax = new BigDecimal("0");
                            ContentValues values = new ContentValues();

                            values.put(PRETAXDT_ITEMCODE, invDet.getFORDERDET_ITEMCODE());
                            values.put(PRETAXDT_RATE, taxcodelist.get(i).getRATE());
                            values.put(DatabaseHelper.REFNO, invDet.getFORDERDET_REFNO());
                            values.put(PRETAXDT_SEQ, taxcodelist.get(i).getSEQ());
                            values.put(PRETAXDT_TAXCODE, taxcodelist.get(i).getTAXCODE());
                            values.put(PRETAXDT_TAXCOMCODE, taxcodelist.get(i).getTAXCOMCODE());
                            values.put(PRETAXDT_TAXPER, taxcodelist.get(i).getTAXVAL());
                            values.put(PRETAXDT_TAXTYPE, taxcodelist.get(i).getTAXTYPE());

                            tax = new BigDecimal(taxcodelist.get(i).getTAXVAL()).multiply(amt.divide(new BigDecimal("100"), 3, BigDecimal.ROUND_HALF_EVEN));
                            amt = new BigDecimal(taxcodelist.get(i).getTAXVAL()).add(new BigDecimal("100")).multiply((amt.divide(new BigDecimal("100"), 3, BigDecimal.ROUND_HALF_EVEN)));

                            values.put(PRETAXDT_BDETAMT, String.format("%.2f", tax));
                            values.put(PRETAXDT_DETAMT, String.format("%.2f", tax));

                            count = (int) dB.insert(TABLE_PRETAXDT, null, values);

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
            String selectQuery = "select * from " + TABLE_PRETAXDT + " WHERE RefNo='" + RefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                TaxDT tax = new TaxDT();

                tax.setREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                tax.setTAXCODE(cursor.getString(cursor.getColumnIndex(PRETAXDT_TAXCODE)));
                tax.setBTAXDETAMT(cursor.getString(cursor.getColumnIndex(PRETAXDT_BDETAMT)));
                tax.setTAXCOMCODE(cursor.getString(cursor.getColumnIndex(PRETAXDT_TAXCOMCODE)));
                tax.setTAXDETAMT(cursor.getString(cursor.getColumnIndex(PRETAXDT_DETAMT)));
                tax.setTAXPER(cursor.getString(cursor.getColumnIndex(PRETAXDT_TAXPER)));
                tax.setTAXRATE(cursor.getString(cursor.getColumnIndex(PRETAXDT_RATE)));
                tax.setTAXSEQ(cursor.getString(cursor.getColumnIndex(PRETAXDT_SEQ)));
                tax.setITEMCODE(cursor.getString(cursor.getColumnIndex(PRETAXDT_ITEMCODE)));

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
//
//    public ArrayList<TaxDT> getTaxDTSummery(String RefNo) {
//
//        if (dB == null) {
//            open();
//        } else if (!dB.isOpen()) {
//            open();
//        }
//
//        ArrayList<TaxDT> list = new ArrayList<TaxDT>();
//        try {
//            String selectQuery = "select TaxType,TaxPer,TaxSeq,SUM(TaxDetAmt) as TotTax FROM " + TABLE_PRETAXDT + " WHERE RefNo='" + RefNo + "' GROUP BY TaxType ORDER BY TaxSeq ASC";
//
//            Cursor cursor = dB.rawQuery(selectQuery, null);
//
//            while (cursor.moveToNext()) {
//                TaxDT tax = new TaxDT();
//
//                tax.setTAXDETAMT(cursor.getString(cursor.getColumnIndex("TotTax")));
//                tax.setTAXPER(cursor.getString(cursor.getColumnIndex(PRETAXDT_TAXPER)));
//                tax.setTAXSEQ(cursor.getString(cursor.getColumnIndex(PRETAXDT_SEQ)));
//                tax.setTAXTYPE(cursor.getString(cursor.getColumnIndex(PRETAXDT_TAXTYPE)));
//
//                list.add(tax);
//            }
//            cursor.close();
//
//        } catch (Exception e) {
//            Log.v("Erorr ", e.toString());
//
//        } finally {
//            dB.close();
//        }
//
//        return list;
//
//    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void ClearTable(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            dB.delete(TABLE_PRETAXDT, DatabaseHelper.REFNO + "='" + RefNo + "'", null);
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

    }

}
