package com.datamation.hmdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.model.PaymentAllocate;

import java.util.ArrayList;

public class PaymentAllocateController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = " PaymentAllocateController";

    // --------------------------------------------------- Receipt Payment Allocate ---------------kaveesha 07/10/2020--------------------

    public static final String TABLE_FPAYMENT_ALLOCATE = "fPaymentAllocate";
    public static final String FPAYMENT_ALLOCATE_ID = "Id";
    public static final String FPAYMENT_ALLOCATE_REFNO = "AllocRefNo";
    public static final String FPAYMENT_ALLOCATE_COMMON_REFNO = "AllocComRefNo";
    public static final String FPAYMENT_ALLOCATE_FDD_REFNO = "AllocFddRefNo";
    public static final String FPAYMENT_ALLOCATE_FDD_TXN_DATE = "AllocTxnDate";
    public static final String FPAYMENT_ALLOCATE_FDD_PAID_AMT = "AllocFddPaidAmt";
    public static final String FPAYMENT_ALLOCATE_FDD_TOTAL_BAL = "AllocFddTotalBal";
    public static final String FPAYMENT_ALLOCATE_PAY_REF_NO = "AllocPayRefNo";
    public static final String FPAYMENT_ALLOCATE_PAY_MODE = "AllocPayMode";
    public static final String FPAYMENT_ALLOCATE_PAY_DATE = "AllocPayDate";
    public static final String FPAYMENT_ALLOCATE_PAY_AMT = "AllocPayAmt";
    public static final String FPAYMENT_ALLOCATE_PAY_REM_AMT = "AllocPayRemAmt";
    public static final String FPAYMENT_ALLOCATE_PAY_ALLO_AMT = "AllocPayAllocAmt";
    public static final String FPAYMENT_ALLOCATE_PAY_BANK = "AllocPayBank";
    public static final String FPAYMENT_ALLOCATE_PAY_CHEQUE_NO = "AllocPayCheqNo";
    public static final String FPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO = "AllocPayCardNo";
    public static final String FPAYMENT_ALLOCATE_PAY_SLIP_NO = "AllocPaySlipNo";
    public static final String FPAYMENT_ALLOCATE_PAY_DRAFT_NO = "AllocPayDraftNo";
    public static final String FPAYMENT_ALLOCATE_PAY_CHEQUE_DATE = "AllocPayCheqDate";

    public static final String CREATE_TABLE_FPAYMENT_ALLOCATE = "CREATE TABLE IF NOT EXISTS " + TABLE_FPAYMENT_ALLOCATE + " (" + FPAYMENT_ALLOCATE_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + FPAYMENT_ALLOCATE_REFNO + " TEXT, " + FPAYMENT_ALLOCATE_COMMON_REFNO + " TEXT, " +
            FPAYMENT_ALLOCATE_FDD_REFNO + " TEXT, " + FPAYMENT_ALLOCATE_FDD_TXN_DATE + " TEXT, " + FPAYMENT_ALLOCATE_FDD_PAID_AMT + " TEXT, " +
            FPAYMENT_ALLOCATE_FDD_TOTAL_BAL + " TEXT, " + FPAYMENT_ALLOCATE_PAY_REF_NO + " TEXT, " + FPAYMENT_ALLOCATE_PAY_MODE + " TEXT, " +
            FPAYMENT_ALLOCATE_PAY_DATE + " TEXT, " + FPAYMENT_ALLOCATE_PAY_AMT + " TEXT, " + FPAYMENT_ALLOCATE_PAY_REM_AMT + " TEXT, " +
            FPAYMENT_ALLOCATE_PAY_ALLO_AMT + " TEXT, " + FPAYMENT_ALLOCATE_PAY_BANK + " TEXT, " + FPAYMENT_ALLOCATE_PAY_CHEQUE_NO + " TEXT, " +
            FPAYMENT_ALLOCATE_PAY_CHEQUE_DATE + " TEXT, " + FPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO + " TEXT, " +
            FPAYMENT_ALLOCATE_PAY_SLIP_NO + " TEXT, " + FPAYMENT_ALLOCATE_PAY_DRAFT_NO + " TEXT ); ";



    public PaymentAllocateController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdatePaymentAllocate(ArrayList<PaymentAllocate> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try
        {
            for (PaymentAllocate paymentAllocate : list)
            {
                ContentValues values = new ContentValues();

//                String selectQuery = "SELECT * FROM " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + FPAYMENT_ALLOCATE_REFNO + " = '" + paymentAllocate.getFPAYMENT_ALLOCATE_REFNO() + "' AND "
//                        +FPAYMENT_ALLOCATE_FDD_REFNO +"= '"+paymentAllocate.getFPAYMENT_ALLOCATE_FDD_REFNO()+"'";
                String selectQuery = "SELECT * FROM " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + FPAYMENT_ALLOCATE_ID + " = '" + paymentAllocate.getFPAYMENT_ALLOCATE_ID() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                values.put(FPAYMENT_ALLOCATE_ID, paymentAllocate.getFPAYMENT_ALLOCATE_ID());
                values.put(FPAYMENT_ALLOCATE_REFNO, paymentAllocate.getFPAYMENT_ALLOCATE_REFNO());
                values.put(FPAYMENT_ALLOCATE_FDD_REFNO, paymentAllocate.getFPAYMENT_ALLOCATE_FDD_REFNO());
                values.put(FPAYMENT_ALLOCATE_FDD_TXN_DATE, paymentAllocate.getFPAYMENT_ALLOCATE_FDD_TXN_DATE());
                values.put(FPAYMENT_ALLOCATE_FDD_PAID_AMT, paymentAllocate.getFPAYMENT_ALLOCATE_FDD_PAID_AMT());
                values.put(FPAYMENT_ALLOCATE_FDD_TOTAL_BAL, paymentAllocate.getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL());
                values.put(FPAYMENT_ALLOCATE_PAY_REF_NO, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_REF_NO());
                values.put(FPAYMENT_ALLOCATE_PAY_MODE, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_MODE());
                values.put(FPAYMENT_ALLOCATE_PAY_DATE, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_DATE());
                values.put(FPAYMENT_ALLOCATE_PAY_AMT, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_AMT());
                values.put(FPAYMENT_ALLOCATE_PAY_REM_AMT, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_REM_AMT());
                values.put(FPAYMENT_ALLOCATE_PAY_ALLO_AMT, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_ALLO_AMT());
                values.put(FPAYMENT_ALLOCATE_PAY_BANK, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_BANK());
                values.put(FPAYMENT_ALLOCATE_PAY_CHEQUE_NO, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_NO());
                values.put(FPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO());
                values.put(FPAYMENT_ALLOCATE_PAY_SLIP_NO, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_SLIP_NO());
                values.put(FPAYMENT_ALLOCATE_PAY_DRAFT_NO, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_DRAFT_NO());
                values.put(FPAYMENT_ALLOCATE_COMMON_REFNO, paymentAllocate.getFPAYMENT_ALLOCATE_COMMON_REFNO());
                values.put(FPAYMENT_ALLOCATE_PAY_CHEQUE_DATE, paymentAllocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE());

                int cn = cursor.getCount();

                if (cn > 0) {

//                    count = dB.update(TABLE_FPAYMENT_ALLOCATE, values, FPAYMENT_ALLOCATE_REFNO + " =? AND "+FPAYMENT_ALLOCATE_FDD_REFNO+" = ?", new String[]{String.valueOf(paymentAllocate.getFPAYMENT_ALLOCATE_REFNO()),String.valueOf(paymentAllocate.getFPAYMENT_ALLOCATE_FDD_REFNO())});
                    count = dB.update(TABLE_FPAYMENT_ALLOCATE, values, FPAYMENT_ALLOCATE_ID + " =?", new String[]{String.valueOf(paymentAllocate.getFPAYMENT_ALLOCATE_ID())});
                } else {
                    count = (int) dB.insert(TABLE_FPAYMENT_ALLOCATE, null, values);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;

    }

    public ArrayList<PaymentAllocate> getAllPaidRecords(String refNo, String comRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " trim(AllocFddRefNo) ='" + refNo + "'" + " AND " + " trim(AllocComRefNo) ='" + comRefNo + "'" ;

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TOTAL_BAL(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_TOTAL_BAL)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_ALLO_AMT(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_ALLO_AMT)));

                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public String getAllocAmtByRefNo(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocRefNo ='" + refNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_ALLO_AMT));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    public Double getFDDTotBalAmtByAllRefNos(String fddRefNo, String comRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Double fddTotBal = 0.00;
        try {
            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + comRefNo + "'" + " AND " + " AllocFddRefNo ='" + fddRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount()>0)
            {
                cursor.moveToLast();
                for (int i=0; i<cursor.getCount();i++)
                {
//                    Double tot = Double.parseDouble(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_TOTAL_BAL)).replaceAll(",",""));
                    //fddTotBal += tot;

                    fddTotBal = Double.parseDouble(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_TOTAL_BAL)).replaceAll(",",""));

                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return fddTotBal;
    }

    public ArrayList<PaymentAllocate> getAllPaidRecordsByTwoRefNo(String fddRefNo, String payComRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocFddRefNo ='" + fddRefNo + "'" + " AND " + " AllocComRefNo ='" + payComRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_ID(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_ID)));
                paymentAllocate.setFPAYMENT_ALLOCATE_REFNO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_REFNO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_COMMON_REFNO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_COMMON_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REF_NO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_REF_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_MODE(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_MODE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_AMT(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REM_AMT(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_REM_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_ALLO_AMT(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_ALLO_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TXN_DATE(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_TXN_DATE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_PAID_AMT(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_PAID_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_DATE(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_DATE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_CHEQUE_DATE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_BANK(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_BANK)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_NO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_CHEQUE_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_SLIP_NO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_SLIP_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_DRAFT_NO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_DRAFT_NO)));

                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public ArrayList<PaymentAllocate> getPaidRecordsByCommonRef(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + refNo + "'" + " GROUP BY " + FPAYMENT_ALLOCATE_FDD_REFNO;

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_REFNO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_PAID_AMT(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_PAID_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TOTAL_BAL(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_TOTAL_BAL)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TXN_DATE(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_TXN_DATE)));

                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public ArrayList<PaymentAllocate> getPaidModesByCommonRef(String comRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + comRefNo + "'" + " GROUP BY " + FPAYMENT_ALLOCATE_PAY_REF_NO;

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_MODE(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_MODE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_NO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_CHEQUE_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_DATE(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_DATE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_AMT(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_CHEQUE_DATE)));

                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public ArrayList<PaymentAllocate> getPaidAllRecordsByCommonRef(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + refNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_REFNO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_REFNO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_COMMON_REFNO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_COMMON_REFNO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REF_NO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_REF_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_MODE(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_MODE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_AMT(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REM_AMT(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_REM_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_ALLO_AMT(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_ALLO_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_PAID_AMT(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_PAID_AMT)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TOTAL_BAL(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_TOTAL_BAL)));
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TXN_DATE(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_TXN_DATE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_CHEQUE_DATE)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_NO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_CHEQUE_NO)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_BANK(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_BANK)));


                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public ArrayList<PaymentAllocate> getDueAmtByCommonRef(String refNo, String comRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + comRefNo + "'" + " AND " + " AllocFddRefNo ='" + refNo + "'" ;

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext())
            {
                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TOTAL_BAL(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_TOTAL_BAL)));
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_ALLO_AMT(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_ALLO_AMT)));

                list.add(paymentAllocate);
            }

            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public void updateAllocAmount(String allocRefNo, String allocAmt) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(FPAYMENT_ALLOCATE_PAY_ALLO_AMT, allocAmt);
//            values.put(DatabaseHelper.FPAYMENT_ALLOCATE_FDD_PAID_AMT, allocAmt);
            dB.update(TABLE_FPAYMENT_ALLOCATE, values, FPAYMENT_ALLOCATE_REFNO + " =?", new String[]{String.valueOf(allocRefNo)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public void updateRemAmount(String refNo, String remAmt) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(FPAYMENT_ALLOCATE_PAY_REM_AMT, remAmt);
            dB.update(TABLE_FPAYMENT_ALLOCATE, values, FPAYMENT_ALLOCATE_REFNO + " =?", new String[]{String.valueOf(refNo)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public void updatePaidAmount(String refNo, String paidAmt) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(FPAYMENT_ALLOCATE_FDD_PAID_AMT, paidAmt);
            dB.update(TABLE_FPAYMENT_ALLOCATE, values, FPAYMENT_ALLOCATE_FDD_REFNO + " =?", new String[]{String.valueOf(refNo)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public String getCurrentRemAmt(String refNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocFddRefNo ='" + refNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_REM_AMT));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    public String getPayRefNoByAllocRefNo(String allocRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocRefNo ='" + allocRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_REF_NO));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    public String getFddRefNoByAllocRefNo(String allocRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocRefNo ='" + allocRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_REFNO));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    public String getRemAmtByAllocRefNo(String allocRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocRefNo ='" + allocRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_REM_AMT));

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return "";
    }

    public String getTotalPaidAmtByComRefNo(String comRefNo)
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        int total =0;

        Cursor cursor = null;
        try {

            String selectQuery = "SELECT AllocPayAllocAmt FROM " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + comRefNo + "'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount()>0)
            {
                cursor.moveToFirst();
                for (int i=0; i<cursor.getCount();i++)
                {
                    Double tot = Double.parseDouble(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_ALLO_AMT)).replaceAll(",",""));
                    total += tot;

                    cursor.moveToNext();
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
        return String.valueOf(total);
    }

    public ArrayList<PaymentAllocate> getRefNoByCommonRef(String comRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + comRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_REFNO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_REFNO)));

                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public ArrayList<PaymentAllocate> getPayRefNoByCommonRef(String comRefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<PaymentAllocate> list = new ArrayList<PaymentAllocate>();
        try {

            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocComRefNo ='" + comRefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                PaymentAllocate paymentAllocate = new PaymentAllocate();

                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REF_NO(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_REF_NO)));

                list.add(paymentAllocate);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    public int clearPaymentAlloc(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        int result = 0;
        try {
            result = dB.delete(TABLE_FPAYMENT_ALLOCATE, FPAYMENT_ALLOCATE_REFNO + "=?",
                    new String[] { Refno });

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return result;
    }

    //--------------------------kaveesha ----------14/10/2020-----------------------------------------------------------------------------------------------------
    public Double getcurrentAllocateBalance(String fddRefNo, String RefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Double fddPaidAmt = 0.00;
        try {
            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocFddRefNo ='" + fddRefNo + "'" + " AND " + " AllocRefNo ='" + RefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount()>0)
            {
                cursor.moveToLast();
                for (int i=0; i<cursor.getCount();i++)
                {
                    fddPaidAmt = Double.parseDouble(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_ALLO_AMT)).replaceAll(",",""));

                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return fddPaidAmt;
    }

    public Double getcurrentRemainBalance(String fddRefNo, String RefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Double fddPaidAmt = 0.00;
        try {
            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocFddRefNo ='" + fddRefNo + "'" + " AND " + " AllocRefNo ='" + RefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount()>0)
            {
                cursor.moveToLast();
                for (int i=0; i<cursor.getCount();i++)
                {
                    fddPaidAmt = Double.parseDouble(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_PAY_REM_AMT)).replaceAll(",",""));

                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return fddPaidAmt;
    }

    public String getcurrentAlloID(String fddRefNo, String RefNo) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        String allo_ID = "";
        try {
            String selectQuery;

            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocFddRefNo ='" + fddRefNo + "'" + " AND " + " AllocRefNo ='" + RefNo + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount()>0)
            {
                cursor.moveToLast();
                for (int i=0; i<cursor.getCount();i++)
                {
                    allo_ID = cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_ID)).replaceAll(",","");

                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return allo_ID;
    }
//    //--------------------------kaveesha ----------14/10/2020-----------------------------------------------------------------------------------------------------
//    public Double getcurrentAllocateBalanceByCommnRefNo(String fddRefNo, String RefNo1) {
//        if (dB == null) {
//            open();
//        } else if (!dB.isOpen()) {
//            open();
//        }
//        Double fddPaidAmt = 0.00;
//        try {
//            String selectQuery;
//
//            selectQuery = "select * from " + TABLE_FPAYMENT_ALLOCATE + " WHERE " + " AllocFddRefNo ='" + fddRefNo + "'" + " AND " + " AllocComRefNo ='" + RefNo1 + "'";
//
//            Cursor cursor = dB.rawQuery(selectQuery, null);
//
//            if (cursor.getCount()>0)
//            {
//                cursor.moveToLast();
//                for (int i=0; i<cursor.getCount();i++)
//                {
//                    fddPaidAmt = Double.parseDouble(cursor.getString(cursor.getColumnIndex(FPAYMENT_ALLOCATE_FDD_PAID_AMT)).replaceAll(",",""));
//
//                    cursor.moveToNext();
//                }
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.v(TAG, e.toString());
//
//        } finally {
//            dB.close();
//        }
//
//        return fddPaidAmt;
//    }
}
