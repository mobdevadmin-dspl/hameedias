package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.OrderDisc;

import java.util.ArrayList;

public class OrderDiscController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi orderDisc";
    // rashmi - 2019-12-18 move from database_helper , because of reduce coding in database helper*******************************************************************************

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-FOrdDisc table details*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public static final String TABLE_FORDDISC = "FOrdDisc";

    public static final String FORDDISC_REFNO1 = "RefNo1";
    public static final String FORDDISC_ITEMCODE = "itemcode";
    public static final String FORDDISC_DISAMT = "DisAmt";
    public static final String FORDDISC_DISPER = "DisPer";



    public OrderDiscController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }
    public static final String CREATE_FORDDISC_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FORDDISC + " (" + DatabaseHelper.REFNO + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT, " + FORDDISC_REFNO1 + " TEXT, " + FORDDISC_ITEMCODE + " TEXT, " + FORDDISC_DISAMT + " TEXT, " + FORDDISC_DISPER + " TEXT ); ";

    @SuppressWarnings("static-access")
    public int createOrUpdateOrdDisc(ArrayList<OrderDisc> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (OrderDisc orderDisc : list) {

                ContentValues values = new ContentValues();

                String selectQuery = "SELECT * FROM " + TABLE_FORDDISC + " WHERE " + DatabaseHelper.REFNO + " = '" + orderDisc.getRefNo() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                values.put(DatabaseHelper.REFNO, orderDisc.getRefNo());
                values.put(DatabaseHelper.TXNDATE, orderDisc.getTxnDate());
                values.put(FORDDISC_REFNO1, orderDisc.getRefNo1());
                values.put(FORDDISC_ITEMCODE, orderDisc.getItemCode());
                values.put(FORDDISC_DISAMT, orderDisc.getDisAmt());
                values.put(FORDDISC_DISPER, orderDisc.getDisPer());

                int cn = cursor.getCount();
                if (cn > 0) {

                    count = dB.update(TABLE_FORDDISC, values, DatabaseHelper.REFNO + " =?", new String[]{String.valueOf(orderDisc.getRefNo())});

                } else {
                    count = (int) dB.insert(TABLE_FORDDISC, null, values);
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

	/*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*--*-Update order discount table-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void UpdateOrderDiscount(OrderDisc orderDisc, String DiscRef, String DiscPer) {

		/* Remove record using order discount ref no & item code */
        RemoveOrderDiscRecord(orderDisc.getRefNo(), orderDisc.getItemCode());

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.REFNO, orderDisc.getRefNo());
            values.put(DatabaseHelper.TXNDATE, orderDisc.getTxnDate());
            values.put(FORDDISC_REFNO1, DiscRef);
            values.put(FORDDISC_ITEMCODE, orderDisc.getItemCode());
            values.put(FORDDISC_DISAMT, orderDisc.getDisAmt());
            values.put(FORDDISC_DISPER, DiscPer);
            dB.insert(TABLE_FORDDISC, null, values);

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {

            dB.close();
        }

    }

	/*-*-*-*-*-*-*-*-*Check record availability using RefNo and Itemcode in FORDDISC*-*-*-*-*-*-*-*-*-*-*-*/

    public boolean isRecordAvailable(String RefNo, String ItemCode) {

        int count = 0;
        boolean Result = false;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            String selectQuery = "SELECT * FROM " + TABLE_FORDDISC + " WHERE " + DatabaseHelper.REFNO + " = '" + RefNo + "' AND " + FORDDISC_ITEMCODE + " = '" + ItemCode + "'";
            cursor = dB.rawQuery(selectQuery, null);
            count = cursor.getCount();

            if (count > 0) {
                Result = true;
            } else {
                Result = false;
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return Result;
    }

	/*-*-*-*-*-*-*-*- Remove particular record from Order Discount table -*-*-* *-*-*-*-*-*/

    public void RemoveOrderDiscRecord(String RefNo, String ItemCode) {

        int count;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FORDDISC + " WHERE " + DatabaseHelper.REFNO + " = '" + RefNo + "' AND " + FORDDISC_ITEMCODE + " = '" + ItemCode + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                dB.delete(TABLE_FORDDISC, DatabaseHelper.REFNO + " = '" + RefNo + "' AND " + FORDDISC_ITEMCODE + " = '" + ItemCode + "'", null);
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

    }


    public void clearData(String RefNo) {

        int count;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FORDDISC + " WHERE " + DatabaseHelper.REFNO + " = '" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                dB.delete(TABLE_FORDDISC, DatabaseHelper.REFNO + " = '" + RefNo + "'", null);
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

    }

	

	/* Delete all records */

    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FORDDISC, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FORDDISC, null, null);
                Log.v("Success", success + "");
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

	/*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**--*--*-*-*-*-*-*-*-*-*-*-*-*-**/

    public ArrayList<OrderDisc> getAllOrderDiscs(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<OrderDisc> list = new ArrayList<OrderDisc>();
        try {

            Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FORDDISC + " WHERE RefNo='" + Refno + "'", null);

            while (cursor.moveToNext()) {
                OrderDisc orderDisc = new OrderDisc();

                orderDisc.setDisAmt(cursor.getString(cursor.getColumnIndex(FORDDISC_DISAMT)));
                orderDisc.setDisPer(cursor.getString(cursor.getColumnIndex(FORDDISC_DISPER)));
                orderDisc.setItemCode(cursor.getString(cursor.getColumnIndex(FORDDISC_ITEMCODE)));
                orderDisc.setRefNo(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                orderDisc.setRefNo1(cursor.getString(cursor.getColumnIndex(FORDDISC_REFNO1)));
                orderDisc.setTxnDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
                list.add(orderDisc);
            }

            cursor.close();

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return list;
    }

    public void ClearDiscountForPreSale(String RefNo) { // ORDER DET REFNO

        int count;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;

        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FORDDISC + " WHERE " + DatabaseHelper.REFNO + " = '" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                dB.delete(TABLE_FORDDISC, DatabaseHelper.REFNO + " = '" + RefNo + "'", null);
            }

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

    }

}
