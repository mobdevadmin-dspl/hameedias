package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.OrderDetail;
import com.datamation.swdsfa.model.TaxDet;
import com.datamation.swdsfa.model.TaxRG;

import java.util.ArrayList;

public class PreSaleTaxRGController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "PreSaleTaxRGDS ";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_PRETAXRG = "fPreTaxRg";
    public static final String PRETAXRG_ID = "Id";
    public static final String PRETAXRG_TAXCODE = "TaxCode";
    public static final String PRETAXRG_RGNO = "RGNo";

    public static final String CREATE_FPRETAXRG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRETAXRG + " (" + PRETAXRG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + PRETAXRG_TAXCODE + " TEXT, " + PRETAXRG_RGNO + " TEXT ); ";

    public PreSaleTaxRGController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int UpdateSalesTaxRG(ArrayList<OrderDetail> list, String debtorCode) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            Cursor cursor = null;
            for (OrderDetail ordDet : list) {

                if (ordDet.getFORDERDET_TYPE().equals("SA")) {

                    ArrayList<TaxDet> taxcodelist = new TaxDetController(context).getTaxInfoByComCode(ordDet.getFORDERDET_TAXCOMCODE());

                    for (TaxDet taxDet : taxcodelist) {

                        String s = "SELECT * FROM " + TABLE_PRETAXRG + " WHERE " + DatabaseHelper.REFNO + "='" + ordDet.getFORDERDET_REFNO() + "' AND " + PRETAXRG_TAXCODE + "='" + taxDet.getTAXCODE() + "'";

                        cursor = dB.rawQuery(s, null);

                        ContentValues values = new ContentValues();
                        values.put(DatabaseHelper.REFNO, ordDet.getFORDERDET_REFNO());
                        values.put(PRETAXRG_RGNO, new TaxController(context).getTaxRGNo(taxDet.getTAXCODE()));
                        //values.put(PRETAXRG_RGNO, new FDebTaxDS(context).getTaxRegNo(debtorCode));
                        values.put(PRETAXRG_TAXCODE, taxDet.getTAXCODE());

                        if (cursor.getCount() <= 0)
                            count = (int) dB.insert(TABLE_PRETAXRG, null, values);

                    }
                }
            }
            cursor.close();
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }
        return count;

    }

    public ArrayList<TaxRG> getAllTaxRG(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<TaxRG> list = new ArrayList<TaxRG>();
        try {
            String selectQuery = "select * from " + TABLE_PRETAXRG + " WHERE RefNo='" + RefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                TaxRG tax = new TaxRG();

                tax.setREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                tax.setTAXCODE(cursor.getString(cursor.getColumnIndex(PRETAXRG_TAXCODE)));
                tax.setRGNO(cursor.getString(cursor.getColumnIndex(PRETAXRG_RGNO)));
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

            dB.delete(TABLE_PRETAXRG, DatabaseHelper.REFNO + "='" + RefNo + "'", null);
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

    }

}
