package com.datamation.hmdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.model.BarcodenvoiceDet;
import com.datamation.hmdsfa.model.InvDet;
import com.datamation.hmdsfa.model.OrderDisc;
import com.datamation.hmdsfa.settings.ReferenceNum;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceDetBarcodeController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbeHelper;
    private String TAG = "INVOICEDET";

    public static final String TABLE_BCINCOICEDET = "bcInvDet";
    public static final String BCINCOICEDET_TYPE = "Type";
    public static final String BCINCOICEDET_ITEMNO = "ItemNo";
    public static final String BCINCOICEDET_BARCODE = "BarCode";
    public static final String BCINCOICEDET_VARIANT_CODE     = "VariantCode";
    public static final String BCINCOICEDET_ARTICLE_NO     = "ArticleNo";
    public static final String BCINCOICEDET_QUANTITY     = "Quantity";
    public static final String BCINCOICEDET_PRICE     = "Price";
    public static final String BCINCOICEDET_ISACTIVE     = "IsActive";


    public static final String CREATE_TABLE_BCINCOICEDET = "CREATE TABLE IF NOT EXISTS "
            + TABLE_BCINCOICEDET + " (" + DatabaseHelper.REFNO + " TEXT, " +
            BCINCOICEDET_TYPE + " TEXT, "
            + BCINCOICEDET_ITEMNO + " TEXT, " + BCINCOICEDET_BARCODE + " TEXT, "
            + BCINCOICEDET_QUANTITY + " TEXT, " + BCINCOICEDET_PRICE + " TEXT, "
            + BCINCOICEDET_VARIANT_CODE + " TEXT, "
            + BCINCOICEDET_ISACTIVE + " TEXT, "
            + BCINCOICEDET_ARTICLE_NO + " TEXT); ";

    public InvoiceDetBarcodeController(Context context) {
        this.context = context;
        dbeHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbeHelper.getWritableDatabase();
    }
    public int InactiveStatusUpdate(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_BCINCOICEDET + " WHERE " + DatabaseHelper.REFNO + " = '" + refno + "'";

            cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(BCINCOICEDET_ISACTIVE, "0");

            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.update(TABLE_BCINCOICEDET, values, DatabaseHelper.REFNO + " =?", new String[]{String.valueOf(refno)});
            } else {
                count = (int) dB.insert(TABLE_BCINCOICEDET, null, values);
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;

    }
    public int getItemCount(String refNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            String selectQuery = "SELECT count(RefNo) as RefNo FROM " + TABLE_BCINCOICEDET + " WHERE  " + DatabaseHelper.REFNO + "='" + refNo + "'";
            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                return Integer.parseInt(cursor.getString(cursor.getColumnIndex("RefNo")));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dB.close();
        }
        return 0;

    }
    public int insertOrUpdateBCInvDet(ArrayList<BarcodenvoiceDet> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (BarcodenvoiceDet invDet : list) {

                ContentValues values = new ContentValues();

                String selectQuery = "SELECT * FROM " + TABLE_BCINCOICEDET + " WHERE " + DatabaseHelper.REFNO
                        + " = '" + invDet.getRefno() + "' and "+BCINCOICEDET_BARCODE+" = '"+invDet.getBarcodeNo()+"'";
                cursor = dB.rawQuery(selectQuery, null);

                values.put(BCINCOICEDET_TYPE, invDet.getType());
                values.put(BCINCOICEDET_ITEMNO, invDet.getItemNo());
                values.put(BCINCOICEDET_BARCODE, invDet.getBarcodeNo());
                values.put(BCINCOICEDET_QUANTITY, invDet.getQty());
                values.put(BCINCOICEDET_PRICE, invDet.getPrice());
                values.put(BCINCOICEDET_VARIANT_CODE, invDet.getVariantCode());
                values.put(BCINCOICEDET_ARTICLE_NO, invDet.getArticleNo());
                values.put(BCINCOICEDET_ISACTIVE, invDet.getIsActive());
                values.put(DatabaseHelper.REFNO, invDet.getRefno());

                int cn = cursor.getCount();
                if (cn > 0) {

                    count = dB.update(TABLE_BCINCOICEDET, values, DatabaseHelper.REFNO + " = '"+invDet.getRefno()+"' and "+BCINCOICEDET_BARCODE+ " = '"+invDet.getBarcodeNo()+"'",null);

                } else {
                    count = (int) dB.insert(TABLE_BCINCOICEDET, null, values);
                }

            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;

    }
    public void mDeleteRecords(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {
            dB.delete(TABLE_BCINCOICEDET, DatabaseHelper.REFNO + " ='" + RefNo + "'", null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }


    public void mUpdateInvoice(String refno, String type, String itemNo, String barcodeNo, String variantCode, String articleNo,int qty, double price) {

        ArrayList<BarcodenvoiceDet> arrList = new ArrayList<BarcodenvoiceDet>();

        BarcodenvoiceDet invDet = new BarcodenvoiceDet();
        invDet.setRefno(refno);
        invDet.setType(type);
        invDet.setItemNo(itemNo);
        invDet.setBarcodeNo(barcodeNo);
        invDet.setVariantCode(variantCode);
        invDet.setArticleNo(articleNo);
        invDet.setQty(qty);
        invDet.setPrice(price);

        arrList.add(invDet);
        new InvoiceDetBarcodeController(context).insertOrUpdateBCInvDet(arrList);
    }

}