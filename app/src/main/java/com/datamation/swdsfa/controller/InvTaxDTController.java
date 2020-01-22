package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.InvDet;
import com.datamation.swdsfa.model.InvTaxDt;
import com.datamation.swdsfa.model.TaxDet;

import java.math.BigDecimal;
import java.util.ArrayList;

public class InvTaxDTController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "InvTaxRG ";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_INVTAXDT = "fInvTaxDT";
    public static final String INVTAXDT_ID = "Id";

    public static final String INVTAXDT_ITEMCODE = "ItemCode";
    public static final String INVTAXDT_TAXCOMCODE = "TaxComCode";
    public static final String INVTAXDT_TAXCODE = "TaxCode";
    public static final String INVTAXDT_TAXPER = "TaxPer";
    public static final String INVTAXDT_RATE = "TaxRate";
    public static final String INVTAXDT_SEQ = "TaxSeq";
    public static final String INVTAXDT_DETAMT = "TaxDetAmt";
    public static final String INVTAXDT_BDETAMT = "BTaxDetAmt";
    public static final String INVTAXDT_TAXTYPE = "TaxType";

    public static final String CREATE_FINVTAXDT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_INVTAXDT + " (" + INVTAXDT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + INVTAXDT_ITEMCODE + " TEXT, " + INVTAXDT_TAXCOMCODE + " TEXT, " + INVTAXDT_TAXCODE + " TEXT, " + INVTAXDT_TAXPER + " TEXT, " + INVTAXDT_RATE + " TEXT, " + INVTAXDT_SEQ + " TEXT, " + INVTAXDT_DETAMT + " TEXT, " + INVTAXDT_TAXTYPE + " TEXT, " + INVTAXDT_BDETAMT + " TEXT ); ";

    public InvTaxDTController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int UpdateInvTaxDT(ArrayList<InvDet> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (InvDet invDet : list) {

                if (invDet.getFINVDET_TYPE().equals("SA")) {

                    BigDecimal amt = new BigDecimal(invDet.getFINVDET_AMT());

                    ArrayList<TaxDet> taxcodelist = new TaxDetController(context).getTaxInfoByComCode(invDet.getFINVDET_TAX_COM_CODE());

                    for (int i = taxcodelist.size() - 1; i > -1; i--) {

                        BigDecimal tax = new BigDecimal("0");

                        ContentValues values = new ContentValues();

                        values.put(INVTAXDT_ITEMCODE, invDet.getFINVDET_ITEM_CODE());
                        values.put(INVTAXDT_RATE, taxcodelist.get(i).getRATE());
                        values.put(DatabaseHelper.REFNO, invDet.getFINVDET_REFNO());
                        values.put(INVTAXDT_SEQ, taxcodelist.get(i).getSEQ());
                        values.put(INVTAXDT_TAXCODE, taxcodelist.get(i).getTAXCODE());
                        values.put(INVTAXDT_TAXCOMCODE, taxcodelist.get(i).getTAXCOMCODE());
                        values.put(INVTAXDT_TAXPER, taxcodelist.get(i).getTAXVAL());
                        values.put(INVTAXDT_TAXTYPE, taxcodelist.get(i).getTAXTYPE());

                        tax = new BigDecimal(taxcodelist.get(i).getTAXVAL()).multiply(amt.divide(new BigDecimal("100"), 3, BigDecimal.ROUND_HALF_EVEN));
                        amt = new BigDecimal(taxcodelist.get(i).getTAXVAL()).add(new BigDecimal("100")).multiply((amt.divide(new BigDecimal("100"), 3, BigDecimal.ROUND_HALF_EVEN)));

                        values.put(INVTAXDT_BDETAMT, String.format("%.2f", tax));
                        values.put(INVTAXDT_DETAMT, String.format("%.2f", tax));

                        count = (int) dB.insert(TABLE_INVTAXDT, null, values);

                    }
                }
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }
        return count;

    }

    public int UpdatePreTaxDT(ArrayList<InvDet> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (InvDet invDet : list) {

                if (invDet.getFINVDET_TYPE().equals("SA")) {

                    BigDecimal amt = new BigDecimal(invDet.getFINVDET_AMT());

                    ArrayList<TaxDet> taxcodelist = new TaxDetController(context).getTaxInfoByComCode(invDet.getFINVDET_TAX_COM_CODE());

                    for (int i = taxcodelist.size() - 1; i > -1; i--) {

                        BigDecimal tax = new BigDecimal("0");

                        ContentValues values = new ContentValues();

                        values.put(INVTAXDT_ITEMCODE, invDet.getFINVDET_ITEM_CODE());
                        values.put(INVTAXDT_RATE, taxcodelist.get(i).getRATE());
                        values.put(DatabaseHelper.REFNO, invDet.getFINVDET_REFNO());
                        values.put(INVTAXDT_SEQ, taxcodelist.get(i).getSEQ());
                        values.put(INVTAXDT_TAXCODE, taxcodelist.get(i).getTAXCODE());
                        values.put(INVTAXDT_TAXCOMCODE, taxcodelist.get(i).getTAXCOMCODE());
                        values.put(INVTAXDT_TAXPER, taxcodelist.get(i).getTAXVAL());
                        values.put(INVTAXDT_TAXTYPE, taxcodelist.get(i).getTAXTYPE());

                        tax = new BigDecimal(taxcodelist.get(i).getTAXVAL()).multiply(amt.divide(new BigDecimal("100"), 3, BigDecimal.ROUND_HALF_EVEN));
                        amt = new BigDecimal(taxcodelist.get(i).getTAXVAL()).add(new BigDecimal("100")).multiply((amt.divide(new BigDecimal("100"), 3, BigDecimal.ROUND_HALF_EVEN)));

                        values.put(INVTAXDT_BDETAMT, String.format("%.2f", tax));
                        values.put(INVTAXDT_DETAMT, String.format("%.2f", tax));

                        count = (int) dB.insert(TABLE_INVTAXDT, null, values);

                    }
                }
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }
        return count;

    }

    public ArrayList<InvTaxDt> getAllTaxDT(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<InvTaxDt> list = new ArrayList<InvTaxDt>();
        try {
            String selectQuery = "select * from " + TABLE_INVTAXDT + " WHERE RefNo='" + RefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                InvTaxDt tax = new InvTaxDt();

                tax.setREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                tax.setTAXCODE(cursor.getString(cursor.getColumnIndex(INVTAXDT_TAXCODE)));
                tax.setBTAXDETAMT(cursor.getString(cursor.getColumnIndex(INVTAXDT_BDETAMT)));
                tax.setTAXCOMCODE(cursor.getString(cursor.getColumnIndex(INVTAXDT_TAXCOMCODE)));
                tax.setTAXDETAMT(cursor.getString(cursor.getColumnIndex(INVTAXDT_DETAMT)));
                tax.setTAXPER(cursor.getString(cursor.getColumnIndex(INVTAXDT_TAXPER)));
                tax.setTAXRATE(cursor.getString(cursor.getColumnIndex(INVTAXDT_RATE)));
                tax.setTAXSEQ(cursor.getString(cursor.getColumnIndex(INVTAXDT_SEQ)));
                tax.setITEMCODE(cursor.getString(cursor.getColumnIndex(INVTAXDT_ITEMCODE)));
                tax.setTAXTYPE(cursor.getString(cursor.getColumnIndex(INVTAXDT_TAXTYPE)));

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

    public ArrayList<InvTaxDt> getTaxDTSummery(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<InvTaxDt> list = new ArrayList<InvTaxDt>();
        try {
            String selectQuery = "select TaxType,TaxPer,TaxSeq,SUM(TaxDetAmt) as TotTax FROM " + TABLE_INVTAXDT + " WHERE RefNo='" + RefNo + "' GROUP BY TaxType ORDER BY TaxSeq ASC";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                InvTaxDt tax = new InvTaxDt();

                tax.setTAXDETAMT(cursor.getString(cursor.getColumnIndex("TotTax")));
                tax.setTAXPER(cursor.getString(cursor.getColumnIndex(INVTAXDT_TAXPER)));
                tax.setTAXSEQ(cursor.getString(cursor.getColumnIndex(INVTAXDT_SEQ)));
                tax.setTAXTYPE(cursor.getString(cursor.getColumnIndex(INVTAXDT_TAXTYPE)));

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

            dB.delete(TABLE_INVTAXDT, DatabaseHelper.REFNO + "='" + RefNo + "'", null);
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

    }

}
