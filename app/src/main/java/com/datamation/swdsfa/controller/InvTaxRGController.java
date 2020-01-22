package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.InvDet;
import com.datamation.swdsfa.model.InvTaxRg;
import com.datamation.swdsfa.model.OrderDetail;
import com.datamation.swdsfa.model.TaxDet;

import java.util.ArrayList;

public class InvTaxRGController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "InvTaxRGDS ";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_INVTAXRG = "fInvTaxRg";
    public static final String INVTAXRG_ID = "Id";
    public static final String INVTAXRG_TAXCODE = "TaxCode";
    public static final String INVTAXRG_RGNO = "RGNo";
    public static final String CREATE_FINVTAXRG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_INVTAXRG + " (" + INVTAXRG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + INVTAXRG_TAXCODE + " TEXT, " + INVTAXRG_RGNO + " TEXT ); ";

    public InvTaxRGController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int UpdateInvTaxRG(ArrayList<InvDet> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            Cursor cursor = null;
            for (InvDet invDet : list) {

                if (invDet.getFINVDET_TYPE().equals("SA")) {

                    ArrayList<TaxDet> taxcodelist = new TaxDetController(context).getTaxInfoByComCode(invDet.getFINVDET_TAX_COM_CODE());

                    for (TaxDet taxDet : taxcodelist) {

                        String s = "SELECT * FROM " + TABLE_INVTAXRG + " WHERE " + DatabaseHelper.REFNO + "='" + invDet.getFINVDET_REFNO() + "' AND " + INVTAXRG_TAXCODE + "='" + taxDet.getTAXCODE() + "'";

                        cursor = dB.rawQuery(s, null);

                        ContentValues values = new ContentValues();
                        values.put(DatabaseHelper.REFNO, invDet.getFINVDET_REFNO());
                        values.put(INVTAXRG_RGNO, new TaxController(context).getTaxRGNo(taxDet.getTAXCODE()));
                        values.put(INVTAXRG_TAXCODE, taxDet.getTAXCODE());

                        if (cursor.getCount() <= 0)
                            count = (int) dB.insert(TABLE_INVTAXRG, null, values);

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

    public int UpdatePreTaxRG(ArrayList<OrderDetail> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            Cursor cursor = null;
            for (OrderDetail invDet : list) {

                if (invDet.getFORDERDET_TYPE().equals("SA")) {

                    ArrayList<TaxDet> taxcodelist = new TaxDetController(context).getTaxInfoByComCode(invDet.getFORDERDET_TAXCOMCODE());

                    for (TaxDet taxDet : taxcodelist) {

                        String s = "SELECT * FROM " + TABLE_INVTAXRG + " WHERE " + DatabaseHelper.REFNO + "='" + invDet.getFORDERDET_REFNO() + "' AND " + INVTAXRG_TAXCODE + "='" + taxDet.getTAXCODE() + "'";

                        cursor = dB.rawQuery(s, null);

                        ContentValues values = new ContentValues();
                        values.put(DatabaseHelper.REFNO, invDet.getFORDERDET_REFNO());
                        values.put(INVTAXRG_RGNO, new TaxController(context).getTaxRGNo(taxDet.getTAXCODE()));
                        values.put(INVTAXRG_TAXCODE, taxDet.getTAXCODE());

                        if (cursor.getCount() <= 0)
                            count = (int) dB.insert(TABLE_INVTAXRG, null, values);

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

    public ArrayList<InvTaxRg> getAllTaxRG(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<InvTaxRg> list = new ArrayList<InvTaxRg>();
        try {
            String selectQuery = "select * from " + TABLE_INVTAXRG + " WHERE RefNo='" + RefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                InvTaxRg tax = new InvTaxRg();

                tax.setREFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                tax.setTAXCODE(cursor.getString(cursor.getColumnIndex(INVTAXRG_TAXCODE)));
                tax.setTAXREGNO(cursor.getString(cursor.getColumnIndex(INVTAXRG_RGNO)));
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

            dB.delete(TABLE_INVTAXRG, DatabaseHelper.REFNO + "='" + RefNo + "'", null);
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

    }

}
