package com.datamation.hmdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.helpers.ValueHolder;
import com.datamation.hmdsfa.model.FddbNote;
import com.datamation.hmdsfa.model.ReceiptDet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OutstandingController {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "FDDbNoteDS ";
    // table
    public static final String TABLE_FDDBNOTE = "FDDbNote";
    // table attributes
    public static final String FDDBNOTE_ID = "recinv_id";
    public static final String FDDBNOTE_RECORD_ID = "RecordId";
    public static final String REFNO = "RefNo";
    public static final String FDDBNOTE_REF_INV = "RefInv";
    public static final String FDDBNOTE_SALE_REF_NO = "SaleRefNo";
    public static final String FDDBNOTE_MANU_REF = "ManuRef";
    public static final String FDDBNOTE_TXN_TYPE = "TxnType";
    public static final String FDDBNOTE_DEB_CODE = "DebCode";
    public static final String FDDBNOTE_CUR_CODE = "CurCode";
    public static final String FDDBNOTE_CUR_RATE = "CurRate";
    public static final String FDDBNOTE_TAX_COM_CODE = "TaxComCode";
    public static final String FDDBNOTE_TAX_AMT = "TaxAmt";
    public static final String FDDBNOTE_B_TAX_AMT = "BTaxAmt";
    public static final String FDDBNOTE_AMT = "Amt";
    public static final String FDDBNOTE_B_AMT = "BAmt";
    public static final String FDDBNOTE_TOT_BAL = "TotBal";
    public static final String FDDBNOTE_TOT_BAL1 = "TotBal1";
    public static final String FDDBNOTE_OV_PAY_AMT = "OvPayAmt";
    public static final String FDDBNOTE_REMARKS = "Remarks";
    public static final String FDDBNOTE_CR_ACC = "CrAcc";
    public static final String FDDBNOTE_PRT_COPY = "PrtCopy";
    public static final String FDDBNOTE_GL_POST = "GlPost";
    public static final String FDDBNOTE_GL_BATCH = "glbatch";
    public static final String FDDBNOTE_ADD_USER = "AddUser";
    public static final String FDDBNOTE_ADD_DATE = "AddDate";
    public static final String FDDBNOTE_ADD_MACH = "AddMach";
    public static final String FDDBNOTE_REFNO1 = "RefNo1";
    public static final String FDDBNOTE_REFNO2 = "RefNo2";
    public static final String FDDBNOTE_REPNAME = "RepName";
    public static final String FDDBNOTE_ENTER_AMT = "EnterAmt";
    public static final String FDDBNOTE_REMARK = "Remark";
    public static final String FDDBNOTE_ENT_REMARK = "EntRemark";
    public static final String FDDBNOTE_PDAAMT = "PdaAmt";
    public static final String FDDBNOTE_RECEIPT_TYPE = "ReceiptType";
    public static final String FDDBNOTE_REP_CODE = "RepCode";


    // create String
    public static final String CREATE_FDDBNOTE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FDDBNOTE + " (" + FDDBNOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDDBNOTE_RECORD_ID + " TEXT, " +
            ValueHolder.REFNO + " TEXT, " + FDDBNOTE_REFNO2 + " TEXT, " + FDDBNOTE_REPNAME + " TEXT, " +
            FDDBNOTE_RECEIPT_TYPE + " TEXT, " + FDDBNOTE_REMARK + " TEXT, " + FDDBNOTE_ENT_REMARK + " TEXT, " + FDDBNOTE_PDAAMT + " TEXT, " + FDDBNOTE_REF_INV + " TEXT, " + FDDBNOTE_ENTER_AMT + " TEXT, " + FDDBNOTE_SALE_REF_NO + " TEXT, " + FDDBNOTE_MANU_REF + " TEXT, " + FDDBNOTE_TXN_TYPE + " TEXT, " + ValueHolder.TXNDATE + " TEXT, " + FDDBNOTE_CUR_CODE + " TEXT, " + FDDBNOTE_CUR_RATE + " TEXT, " + ValueHolder.DEBCODE + " TEXT, " + ValueHolder.REPCODE + " TEXT, " + FDDBNOTE_TAX_COM_CODE + " TEXT, " + FDDBNOTE_TAX_AMT + " TEXT, " + FDDBNOTE_B_TAX_AMT + " TEXT, " + FDDBNOTE_AMT + " TEXT, " + FDDBNOTE_B_AMT + " TEXT, " + FDDBNOTE_TOT_BAL + " TEXT, " + FDDBNOTE_TOT_BAL1 + " TEXT, " + FDDBNOTE_OV_PAY_AMT + " TEXT, " + FDDBNOTE_REMARKS + " TEXT, " + FDDBNOTE_CR_ACC + " TEXT, " + FDDBNOTE_PRT_COPY + " TEXT, " + FDDBNOTE_GL_POST + " TEXT, " + FDDBNOTE_GL_BATCH + " TEXT, " + FDDBNOTE_ADD_USER + " TEXT, " + FDDBNOTE_ADD_DATE + " TEXT, " + FDDBNOTE_ADD_MACH + " TEXT, " + FDDBNOTE_REFNO1 + " TEXT); ";

    public static final String TESTDDBNOTE = "CREATE UNIQUE INDEX IF NOT EXISTS idxddbnote_something ON " + TABLE_FDDBNOTE + " (" + ValueHolder.REFNO + ")";

    public OutstandingController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }
//    public void createOrUpdateFDDbNote(ArrayList<FddbNote> list) {
//        Log.d("InsertOrReplaceFDDbNote", "" + list.size());
//        if (dB == null) {
//            open();
//        } else if (!dB.isOpen()) {
//            open();
//        }
//
//        try {
//            dB.beginTransactionNonExclusive();
//            String sql = "INSERT OR REPLACE INTO " + TABLE_FDDBNOTE + " (RefNo,RefInv,AddMach," +
//                    "SaleRefNo," +
//                    "ManuRef," +
//                    "TxnType," +
//                    "TxnDate," +
//                    "CurCode," +
//                    "CurRate," +
//                    "DebCode," +
//                    "RepCode," +
//                    "TaxComCode," +
//                    "TaxAmt," +
//                    "BTaxAmt," +
//                    "Amt," +
//                    "BAmt," +
//                    "TotBal," +
//                    "TotBal1," +
//                    "OvPayAmt," +
//                    "Remarks," +
//                    "AddUser,AddDate,EnterAmt,PdaAmt) " + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//
//            SQLiteStatement stmt = dB.compileStatement(sql);
//
//            for (FddbNote fdDbNote : list) {
//
//
//                stmt.bindString(1, fdDbNote.getFDDBNOTE_REFNO());
//                stmt.bindString(2, fdDbNote.getFDDBNOTE_REF_INV());
//                stmt.bindString(3, fdDbNote.getFDDBNOTE_ADD_MACH());
//                stmt.bindString(4, fdDbNote.getFDDBNOTE_SALE_REF_NO());
//                stmt.bindString(5, fdDbNote.getFDDBNOTE_MANU_REF());
//                stmt.bindString(6, fdDbNote.getFDDBNOTE_TXN_TYPE());
//                stmt.bindString(7, fdDbNote.getFDDBNOTE_TXN_DATE());
//                stmt.bindString(8, fdDbNote.getFDDBNOTE_CUR_CODE());
//                stmt.bindString(9, fdDbNote.getFDDBNOTE_CUR_RATE());
//                stmt.bindString(10, fdDbNote.getFDDBNOTE_DEB_CODE());
//                stmt.bindString(11, fdDbNote.getFDDBNOTE_REP_CODE());
//                stmt.bindString(12, fdDbNote.getFDDBNOTE_TAX_COM_CODE());
//                stmt.bindString(13, fdDbNote.getFDDBNOTE_TAX_AMT());
//                stmt.bindString(14, fdDbNote.getFDDBNOTE_B_TAX_AMT());
//                stmt.bindString(15, fdDbNote.getFDDBNOTE_AMT());
//                stmt.bindString(16, fdDbNote.getFDDBNOTE_B_AMT());
//                stmt.bindString(17, fdDbNote.getFDDBNOTE_TOT_BAL());
//                stmt.bindString(18, fdDbNote.getFDDBNOTE_TOT_BAL1());
//                stmt.bindString(19, fdDbNote.getFDDBNOTE_OV_PAY_AMT());
//                stmt.bindString(20, fdDbNote.getFDDBNOTE_REMARKS());
//                //stmt.bindString(20, fdDbNote.getFDDBNOTE_REMARKS());
//                stmt.bindString(21, fdDbNote.getFDDBNOTE_CR_ACC());
//                stmt.bindString(22, fdDbNote.getFDDBNOTE_PRT_COPY());
//                stmt.bindString(23, fdDbNote.getFDDBNOTE_GL_POST());
//                stmt.bindString(24, fdDbNote.getFDDBNOTE_GL_BATCH());
//                stmt.bindString(21, fdDbNote.getFDDBNOTE_ADD_USER());
//                stmt.bindString(22, fdDbNote.getFDDBNOTE_ADD_DATE());
//                stmt.bindString(23, fdDbNote.getFDDBNOTE_ENTER_AMT());
//                stmt.bindString(24, fdDbNote.getFDDBNOTE_PDA_AMT());
//
//
//                stmt.execute();
//                stmt.clearBindings();
//            }
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            dB.setTransactionSuccessful();
//            dB.endTransaction();
//            dB.close();
//        }
//
//    }

    public int createOrUpdateFDDbNote(ArrayList<FddbNote> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            for (FddbNote fdDbNote : list) {

                cursor = dB.rawQuery("SELECT * FROM " + TABLE_FDDBNOTE + " WHERE " + REFNO + "='" + fdDbNote.getFDDBNOTE_REFNO() + "'", null);

                ContentValues values = new ContentValues();

                values.put(FDDBNOTE_RECORD_ID, fdDbNote.getFDDBNOTE_RECORD_ID());
                values.put(REFNO, fdDbNote.getFDDBNOTE_REFNO());
                values.put(FDDBNOTE_REF_INV, fdDbNote.getFDDBNOTE_REF_INV());
                values.put(FDDBNOTE_SALE_REF_NO, fdDbNote.getFDDBNOTE_SALE_REF_NO());
                values.put(FDDBNOTE_MANU_REF, fdDbNote.getFDDBNOTE_MANU_REF());
                values.put(FDDBNOTE_TXN_TYPE, fdDbNote.getFDDBNOTE_TXN_TYPE());
                values.put(ValueHolder.TXNDATE, fdDbNote.getFDDBNOTE_TXN_DATE());
                values.put(FDDBNOTE_CUR_CODE, fdDbNote.getFDDBNOTE_CUR_CODE());
                values.put(FDDBNOTE_CUR_RATE, fdDbNote.getFDDBNOTE_CUR_RATE());
                values.put(FDDBNOTE_DEB_CODE, fdDbNote.getFDDBNOTE_DEB_CODE());
                values.put(FDDBNOTE_REP_CODE, fdDbNote.getFDDBNOTE_REP_CODE());
                values.put(FDDBNOTE_TAX_COM_CODE, fdDbNote.getFDDBNOTE_TAX_COM_CODE());
                values.put(FDDBNOTE_TAX_AMT, fdDbNote.getFDDBNOTE_TAX_AMT());
                values.put(FDDBNOTE_B_TAX_AMT, fdDbNote.getFDDBNOTE_B_TAX_AMT());
                values.put(FDDBNOTE_AMT, fdDbNote.getFDDBNOTE_AMT());
                values.put(FDDBNOTE_B_AMT, fdDbNote.getFDDBNOTE_B_AMT());
                values.put(FDDBNOTE_TOT_BAL, fdDbNote.getFDDBNOTE_TOT_BAL());
                values.put(FDDBNOTE_TOT_BAL1, fdDbNote.getFDDBNOTE_TOT_BAL1());
                values.put(FDDBNOTE_OV_PAY_AMT, fdDbNote.getFDDBNOTE_OV_PAY_AMT());
                values.put(FDDBNOTE_REMARKS, fdDbNote.getFDDBNOTE_REMARKS());
                values.put(FDDBNOTE_CR_ACC, fdDbNote.getFDDBNOTE_CR_ACC());
                values.put(FDDBNOTE_PRT_COPY, fdDbNote.getFDDBNOTE_PRT_COPY());
                values.put(FDDBNOTE_GL_POST, fdDbNote.getFDDBNOTE_GL_POST());
                values.put(FDDBNOTE_GL_BATCH, fdDbNote.getFDDBNOTE_GL_BATCH());
                values.put(FDDBNOTE_ADD_USER, fdDbNote.getFDDBNOTE_ADD_USER());
                values.put(FDDBNOTE_ADD_DATE, fdDbNote.getFDDBNOTE_ADD_DATE());
                values.put(FDDBNOTE_ADD_MACH, fdDbNote.getFDDBNOTE_ADD_MACH());
                values.put(FDDBNOTE_REFNO1, fdDbNote.getFDDBNOTE_REFNO1());
                values.put(FDDBNOTE_ENTER_AMT, fdDbNote.getFDDBNOTE_ENTER_AMT());
                values.put(FDDBNOTE_PDAAMT, fdDbNote.getFDDBNOTE_PDA_AMT());

                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FDDBNOTE, values, REFNO + "=?", new String[]{fdDbNote.getFDDBNOTE_REFNO().toString()});
                } else {
                    count = (int) dB.insert(TABLE_FDDBNOTE, null, values);
                }

            }

        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;
    }
    /*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int deleteAll() {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FDDBNOTE, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FDDBNOTE, null, null);
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

    /*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public double getDebtorBalance(String DebCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        double totbal = 0, totbal1 = 0;
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT TotBal,TotBal1 FROM " + TABLE_FDDBNOTE + " WHERE DebCode ='" + DebCode + "'";
            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                totbal = totbal + Double.parseDouble(cursor.getString(cursor.getColumnIndex(FDDBNOTE_TOT_BAL)));
                totbal1 = totbal1 + Double.parseDouble(cursor.getString(cursor.getColumnIndex(FDDBNOTE_TOT_BAL1)));
            }

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return totbal - totbal1;

    }

    /*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<FddbNote> getDebtInfo(String DebCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FddbNote> list = new ArrayList<FddbNote>();
        try {
            String selectQuery;

            if (DebCode.equals(""))
                selectQuery = "SELECT refno,totbal,totbal1,txndate FROM " + TABLE_FDDBNOTE;
            else
                selectQuery = "SELECT refno,totbal,totbal1,txndate FROM " + TABLE_FDDBNOTE + " WHERE DebCode ='" + DebCode + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                FddbNote dbNote = new FddbNote();
                dbNote.setFDDBNOTE_REFNO(cursor.getString(cursor.getColumnIndex(ValueHolder.REFNO)));
                dbNote.setFDDBNOTE_TXN_DATE(cursor.getString(cursor.getColumnIndex(ValueHolder.TXNDATE)));
                dbNote.setFDDBNOTE_TOT_BAL(cursor.getString(cursor.getColumnIndex(FDDBNOTE_TOT_BAL)));
                dbNote.setFDDBNOTE_TOT_BAL1(cursor.getString(cursor.getColumnIndex(FDDBNOTE_TOT_BAL1)));
                list.add(dbNote);
            }

            cursor.close();

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;

    }

    /*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String getHighestPaymentDue(String DebCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            String selectQuery = "SELECT txndate FROM " + TABLE_FDDBNOTE + " WHERE DebCode ='" + DebCode + "' ORDER BY txndate ASC";
            cursor = dB.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(ValueHolder.TXNDATE));

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            cursor.close();
            dB.close();
        }

        return null;
    }

    /*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<String[]> getCustomerCreditInfo(String routeCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<String[]> list = new ArrayList<String[]>();

        String selectQuery = "select sum(f.totbal) as totbal, sum(f.totbal1) as totbal1,f.txndate,f.debcode,d.debcode,d.debname,t.townname,t.towncode from ftown t,fddbnote  f, fdebtor  d where f.debcode=d.debcode and t.towncode=d.towncode  and d.debcode in (select debcode from froutedet where routecode='" + routeCode + "') group by f.debcode,d.debcode,d.debname,t.townname,t.towncode";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            String[] arr = new String[6];

            arr[0] = cursor.getString(cursor.getColumnIndex("DebName"));
            arr[1] = cursor.getString(cursor.getColumnIndex("TownName"));
            arr[2] = cursor.getString(cursor.getColumnIndex("totbal"));
            arr[3] = cursor.getString(cursor.getColumnIndex("totbal1"));
            arr[4] = cursor.getString(cursor.getColumnIndex("TxnDate"));
            arr[5] = cursor.getString(cursor.getColumnIndex("DebCode"));

            if (Daybetween(arr[4]) > 60) {
                list.add(arr);
            }

        }

        cursor.close();
        dB.close();
        return list;

    }

    /*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int Daybetween(String sDate) {

        long lDate1 = 0;
        long lDate2 = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = sdf1.format(new Date());

            Calendar c1 = Calendar.getInstance();
            Date Date1 = sdf1.parse(currentDate);
            c1.setTime(Date1);

            Calendar c2 = Calendar.getInstance();
            Date Date2 = sdf.parse(sDate);
            c2.setTime(Date2);

            lDate1 = c1.getTimeInMillis();
            lDate2 = c2.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (int) ((lDate1 - lDate2) / (24 * 60 * 60 * 1000));
    }

    /*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<FddbNote> getCreditBreakup(String debCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        ArrayList<FddbNote> list = new ArrayList<FddbNote>();

        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FDDBNOTE + " WHERE " + ValueHolder.DEBCODE + "='" + debCode + "'", null);

            while (cursor.moveToNext()) {

                FddbNote fdDbNote = new FddbNote();

                fdDbNote.setFDDBNOTE_TXN_DATE(cursor.getString(cursor.getColumnIndex(ValueHolder.TXNDATE)));
                fdDbNote.setFDDBNOTE_REFNO(cursor.getString(cursor.getColumnIndex(ValueHolder.REFNO)));
                fdDbNote.setFDDBNOTE_AMT(cursor.getString(cursor.getColumnIndex(FDDBNOTE_AMT)));
                fdDbNote.setFDDBNOTE_TOT_BAL(cursor.getString(cursor.getColumnIndex(FDDBNOTE_TOT_BAL)));
                fdDbNote.setFDDBNOTE_REFNO(cursor.getString(cursor.getColumnIndex(ValueHolder.REFNO)));
                fdDbNote.setFDDBNOTE_ADD_DATE(Daybetween(cursor.getString(cursor.getColumnIndex(ValueHolder.TXNDATE))) + "");
                fdDbNote.setFDDBNOTE_B_AMT(String.format("%,.2f", Double.parseDouble(fdDbNote.getFDDBNOTE_AMT()) - Double.parseDouble(fdDbNote.getFDDBNOTE_TOT_BAL())));
                list.add(fdDbNote);
            }

        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return list;

    }

    /*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<FddbNote> getAllRecords(String debcode, boolean isSummery) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FddbNote> list = new ArrayList<FddbNote>();
        try {

            String selectQuery;

            if (isSummery)
                selectQuery = "select * from " + TABLE_FDDBNOTE + " WHERE " + " debcode='" + debcode + "' AND EnterAmt<>'' AND CAST(TotBal AS INT) > 0.0 Order By " + ValueHolder.TXNDATE;
            else
                selectQuery = "select * from " + TABLE_FDDBNOTE + " WHERE " + " debcode='" + debcode + "' AND CAST(TotBal AS INT) > 0.0 Order By " + ValueHolder.TXNDATE;

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                FddbNote fdDbNote = new FddbNote();

                fdDbNote.setFDDBNOTE_ADD_DATE(cursor.getString(cursor.getColumnIndex(FDDBNOTE_ADD_DATE)));
                fdDbNote.setFDDBNOTE_ADD_MACH(cursor.getString(cursor.getColumnIndex(FDDBNOTE_ADD_MACH)));
                fdDbNote.setFDDBNOTE_ADD_USER(cursor.getString(cursor.getColumnIndex(FDDBNOTE_ADD_USER)));
                fdDbNote.setFDDBNOTE_AMT(cursor.getString(cursor.getColumnIndex(FDDBNOTE_AMT)));
                fdDbNote.setFDDBNOTE_B_AMT(cursor.getString(cursor.getColumnIndex(FDDBNOTE_B_AMT)));
                fdDbNote.setFDDBNOTE_B_TAX_AMT(cursor.getString(cursor.getColumnIndex(FDDBNOTE_B_TAX_AMT)));
               // fdDbNote.setFDDBNOTE_CR_ACC(cursor.getString(cursor.getColumnIndex(FDDBNOTE_CR_ACC)));
                fdDbNote.setFDDBNOTE_CUR_CODE(cursor.getString(cursor.getColumnIndex(FDDBNOTE_CUR_CODE)));
                fdDbNote.setFDDBNOTE_CUR_RATE(cursor.getString(cursor.getColumnIndex(FDDBNOTE_CUR_RATE)));
                fdDbNote.setFDDBNOTE_DEB_CODE(cursor.getString(cursor.getColumnIndex(ValueHolder.DEBCODE)));
                fdDbNote.setFDDBNOTE_ENTER_AMT(cursor.getString(cursor.getColumnIndex(FDDBNOTE_ENTER_AMT)));
//                fdDbNote.setFDDBNOTE_GL_BATCH(cursor.getString(cursor.getColumnIndex(FDDBNOTE_GL_BATCH)));
//                fdDbNote.setFDDBNOTE_GL_POST(cursor.getString(cursor.getColumnIndex(FDDBNOTE_GL_POST)));
                fdDbNote.setFDDBNOTE_ID(cursor.getString(cursor.getColumnIndex(FDDBNOTE_ID)));
                fdDbNote.setFDDBNOTE_MANU_REF(cursor.getString(cursor.getColumnIndex(FDDBNOTE_MANU_REF)));
                fdDbNote.setFDDBNOTE_OV_PAY_AMT(cursor.getString(cursor.getColumnIndex(FDDBNOTE_OV_PAY_AMT)));
  //              fdDbNote.setFDDBNOTE_PRT_COPY(cursor.getString(cursor.getColumnIndex(FDDBNOTE_PRT_COPY)));
                fdDbNote.setFDDBNOTE_RECORD_ID(cursor.getString(cursor.getColumnIndex(FDDBNOTE_RECORD_ID)));
                fdDbNote.setFDDBNOTE_REF_INV(cursor.getString(cursor.getColumnIndex(FDDBNOTE_REF_INV)));
                fdDbNote.setFDDBNOTE_REFNO(cursor.getString(cursor.getColumnIndex(ValueHolder.REFNO)));
                fdDbNote.setFDDBNOTE_REFNO1(cursor.getString(cursor.getColumnIndex(FDDBNOTE_REFNO1)));
                fdDbNote.setFDDBNOTE_REP_CODE(cursor.getString(cursor.getColumnIndex(ValueHolder.REPCODE)));
                fdDbNote.setFDDBNOTE_SALE_REF_NO(cursor.getString(cursor.getColumnIndex(FDDBNOTE_SALE_REF_NO)));
                fdDbNote.setFDDBNOTE_TAX_AMT(cursor.getString(cursor.getColumnIndex(FDDBNOTE_TAX_AMT)));
                fdDbNote.setFDDBNOTE_TAX_COM_CODE(cursor.getString(cursor.getColumnIndex(FDDBNOTE_TAX_COM_CODE)));
                fdDbNote.setFDDBNOTE_TOT_BAL(cursor.getString(cursor.getColumnIndex(FDDBNOTE_TOT_BAL)));
                fdDbNote.setFDDBNOTE_TOT_BAL1(cursor.getString(cursor.getColumnIndex(FDDBNOTE_TOT_BAL1)));
                fdDbNote.setFDDBNOTE_TXN_DATE(cursor.getString(cursor.getColumnIndex(ValueHolder.TXNDATE)));
                fdDbNote.setFDDBNOTE_TXN_TYPE(cursor.getString(cursor.getColumnIndex(FDDBNOTE_TXN_TYPE)));
                fdDbNote.setFDDBNOTE_REMARKS(cursor.getString(cursor.getColumnIndex(FDDBNOTE_REMARKS)));
                fdDbNote.setFDDBNOTE_REPNAME(cursor.getString(cursor.getColumnIndex(FDDBNOTE_REPNAME)));
                // fdDbNote.setFDDBNOTE_ENTREMARK(cursor.getString(cursor.getColumnIndex(FDDBNOTE_ENT_REMARK)));

                list.add(fdDbNote);

            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            dB.close();
        }

        return list;
    }

    /*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int ClearFddbNoteData() {

        int result = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {
            ContentValues values = new ContentValues();
            values.put(FDDBNOTE_ENTER_AMT, "");
            result = dB.update(TABLE_FDDBNOTE, values, null, null);
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }
        return result;
    }

    /*-*-**-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void UpdateFddbNoteBalance(ArrayList<FddbNote> list) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            for (FddbNote fddb : list) {
                ContentValues values = new ContentValues();
               // values.put(FDDBNOTE_TOT_BAL, Double.parseDouble(fddb.getFDDBNOTE_TOT_BAL()) - Double.parseDouble(fddb.getFDDBNOTE_ENTER_AMT()));
//                values.put(FDDBNOTE_ENTER_AMT, "");
//                values.put(FDDBNOTE_REMARKS, "");
                values.put(FDDBNOTE_TOT_BAL, fddb.getFDDBNOTE_TOT_BAL());
                dB.update(TABLE_FDDBNOTE, values, ValueHolder.REFNO + "=?", new String[] { fddb.getFDDBNOTE_REFNO().toString() });
            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

    }


    public void UpdateFddbNoteBalanceForReceipt(ArrayList<ReceiptDet> list) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            for (ReceiptDet recDet : list) {
                ContentValues values = new ContentValues();
                values.put(FDDBNOTE_TOT_BAL, Double.parseDouble(recDet.getFPRECDET_BAMT()) + Double.parseDouble(recDet.getFPRECDET_ALOAMT()));
                values.put(FDDBNOTE_ENTER_AMT, "");
                dB.update(TABLE_FDDBNOTE, values, ValueHolder.REFNO + "=?", new String[] { recDet.getFPRECDET_REFNO1().toString() });
            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

    }

}
