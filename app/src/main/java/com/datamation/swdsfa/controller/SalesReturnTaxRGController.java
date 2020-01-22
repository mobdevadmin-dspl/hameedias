package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.FInvRDet;
import com.datamation.swdsfa.model.TaxDet;
import com.datamation.swdsfa.model.TaxRG;

import java.util.ArrayList;

public class SalesReturnTaxRGController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "SalesReturnTaxRGDS ";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_INVRTAXRG = "fInvRTaxRg";
    public static final String INVRTAXRG_ID = "Id";
    public static final String INVRTAXRG_TAXCODE = "TaxCode";
    public static final String INVRTAXRG_RGNO = "RGNo";

    public static final String CREATE_FINVRTAXRG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_INVRTAXRG + " (" + INVRTAXRG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + INVRTAXRG_TAXCODE + " TEXT, " + INVRTAXRG_RGNO + " TEXT ); ";

    // ------------------------------------------------ SalesReturnTaxDt -------------------------------------

    public SalesReturnTaxRGController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int UpdateReturnTaxRG(ArrayList<FInvRDet> list, String debtorCode) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            Cursor cursor = null;
            for (FInvRDet invRDet : list) {

                if (invRDet.getFINVRDET_RETURN_TYPE().equals("MR") || invRDet.getFINVRDET_RETURN_TYPE().equals("UR")) {

                    ArrayList<TaxDet> taxcodelist = new TaxDetController(context).getTaxInfoByComCode(invRDet.getFINVRDET_TAXCOMCODE());

                    for (TaxDet taxDet : taxcodelist) {

                        String s = "SELECT * FROM " + SalesReturnTaxRGController.TABLE_INVRTAXRG + " WHERE " + DatabaseHelper.REFNO + "='" + invRDet.getFINVRDET_REFNO() + "' AND " + SalesReturnTaxRGController.INVRTAXRG_TAXCODE + "='" + taxDet.getTAXCODE() + "'";

                        cursor = dB.rawQuery(s, null);

                        ContentValues values = new ContentValues();
                        values.put(DatabaseHelper.REFNO, invRDet.getFINVRDET_REFNO());
                        values.put(PreSaleTaxRGController.PRETAXRG_RGNO, new TaxController(context).getTaxRGNo(taxDet.getTAXCODE()));
                        //values.put(DatabaseHelper.INVRTAXRG_RGNO, new FDebTaxDS(context).getTaxRegNo(debtorCode));
                        values.put(SalesReturnTaxRGController.INVRTAXRG_TAXCODE, taxDet.getTAXCODE());

                        if (cursor.getCount() <= 0)
                            count = (int) dB.insert(SalesReturnTaxRGController.TABLE_INVRTAXRG, null, values);

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
            String selectQuery = "select * from " + TABLE_INVRTAXRG + " WHERE RefNo='" + RefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                TaxRG tax = new TaxRG();

                tax.setREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                tax.setTAXCODE(cursor.getString(cursor.getColumnIndex(INVRTAXRG_TAXCODE)));
                tax.setRGNO(cursor.getString(cursor.getColumnIndex(INVRTAXRG_RGNO)));
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

            dB.delete(TABLE_INVRTAXRG, DatabaseHelper.REFNO + "='" + RefNo + "'", null);
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

    }

}
