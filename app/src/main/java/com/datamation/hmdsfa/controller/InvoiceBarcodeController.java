package com.datamation.hmdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.helpers.ValueHolder;
import com.datamation.hmdsfa.model.InvHed;
import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceBarcodeController {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "INVOICEHED";
    public static final String TABLE_BCINCOICEHED = "bcInvHed";
    public static final String BCINCOICEHED_ID = "Id";
    public static final String BCINCOICEHED_CUSTOMER_CODE = "CusCode";
    public static final String BCINCOICEHED_LOCATION_CODE = "LocCode";
    public static final String BCINCOICEHED_AREA_CODE     = "AreaCode";
    public static final String BCINCOICEHED_IS_ACTIVE     = "IsActive";
    public static final String BCINCOICEHED_IS_SYNC     = "IsSync";



    public static final String CREATE_TABLE_BCINCOICEHED = "CREATE TABLE IF NOT EXISTS "
            + TABLE_BCINCOICEHED + " (" + BCINCOICEHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ValueHolder.REFNO + " TEXT, " + BCINCOICEHED_CUSTOMER_CODE + " TEXT, "
            + ValueHolder.TXNDATE + " TEXT, " + BCINCOICEHED_LOCATION_CODE + " TEXT, "
            + BCINCOICEHED_IS_ACTIVE + " TEXT, " + BCINCOICEHED_IS_SYNC + " TEXT, "
            + ValueHolder.REPCODE + " TEXT, "+ BCINCOICEHED_AREA_CODE + " TEXT); ";

    public InvoiceBarcodeController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int insertOrUpdateBCInvHed(ArrayList<InvHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (InvHed invHed : list) {

                String selectQuery = "SELECT * FROM " + TABLE_BCINCOICEHED + " WHERE " + ValueHolder.REFNO + " = '" + invHed.getFINVHED_REFNO() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                ContentValues values = new ContentValues();

                values.put(ValueHolder.REFNO, invHed.getFINVHED_REFNO());
                values.put(ValueHolder.REPCODE, invHed.getFINVHED_REPCODE());
                values.put(ValueHolder.TXNDATE, invHed.getFINVHED_TXNDATE());
                values.put(BCINCOICEHED_CUSTOMER_CODE, invHed.getFINVHED_DEBCODE());
                values.put(BCINCOICEHED_LOCATION_CODE, invHed.getFINVHED_LOCCODE());
                values.put(BCINCOICEHED_AREA_CODE, invHed.getFINVHED_AREACODE());
                values.put(BCINCOICEHED_IS_SYNC, "0");
                values.put(BCINCOICEHED_IS_ACTIVE, invHed.getFINVHED_IS_ACTIVE());

                int cn = cursor.getCount();
                if (cn > 0) {
                    count = dB.update(TABLE_BCINCOICEHED, values, ValueHolder.REFNO + " =?", new String[]{String.valueOf(invHed.getFINVHED_REFNO())});
                } else {
                    count = (int) dB.insert(TABLE_BCINCOICEHED, null, values);
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
    public ArrayList<InvHed> getAllUnsynced() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<InvHed> list = new ArrayList<InvHed>();

        String selectQuery = "select * from " + TABLE_BCINCOICEHED + " Where " + BCINCOICEHED_IS_ACTIVE + "='0' AND " + BCINCOICEHED_IS_SYNC + "='0'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        localSP = context.getSharedPreferences(SETTINGS, 0);

        while (cursor.moveToNext()) {

            InvHed vanSalesMapper = new InvHed();

            vanSalesMapper.setNextNumVal(new ReferenceController(context).getCurrentNextNumVal(context.getResources().getString(R.string.VanNumVal)));

            vanSalesMapper.setDistDB(SharedPref.getInstance(context).getDistDB().trim());
            vanSalesMapper.setConsoleDB(SharedPref.getInstance(context).getConsoleDB().trim());

            vanSalesMapper.setFINVHED_REPCODE(cursor.getString(cursor.getColumnIndex(ValueHolder.REPCODE)));
            vanSalesMapper.setFINVHED_REFNO(cursor.getString(cursor.getColumnIndex(ValueHolder.REFNO)));
            vanSalesMapper.setFINVHED_LOCCODE(cursor.getString(cursor.getColumnIndex(BCINCOICEHED_LOCATION_CODE)));
            vanSalesMapper.setFINVHED_AREACODE(cursor.getString(cursor.getColumnIndex(BCINCOICEHED_AREA_CODE)));
            vanSalesMapper.setFINVHED_DEBCODE(cursor.getString(cursor.getColumnIndex(BCINCOICEHED_CUSTOMER_CODE)));
            vanSalesMapper.setFINVHED_TXNDATE(cursor.getString(cursor.getColumnIndex(ValueHolder.TXNDATE)));


            String RefNo = cursor.getString(cursor.getColumnIndex(ValueHolder.REFNO));

           // vanSalesMapper.setInvDets(new InvoiceDetBarcodeController(context).getAllInvDet(RefNo));
            vanSalesMapper.setInvDets(new InvDetController(context).getAllInvDet(RefNo));
            vanSalesMapper.setInvTaxDTs(new InvTaxDTController(context).getAllTaxDT(RefNo));
            vanSalesMapper.setInvTaxRGs(new InvTaxRGController(context).getAllTaxRG(RefNo));
            vanSalesMapper.setOrderDiscs(new OrderDiscController(context).getAllOrderDiscs(RefNo));
            vanSalesMapper.setFreeIssues(new OrdFreeIssueController(context).getAllFreeIssues(RefNo));
            // vanSalesMapper.setStkIsses(new StkIssController(context).getUploadData(RefNo));
            vanSalesMapper.setDispHeds(new DispHedController(context).getUploadData(RefNo));
            vanSalesMapper.setDispDets(new DispDetController(context).getUploadData(RefNo));
            vanSalesMapper.setDispIsses(new DispIssController(context).getUploadData(RefNo));


            list.add(vanSalesMapper);

        }

        return list;
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

            String selectQuery = "SELECT * FROM " + TABLE_BCINCOICEHED + " WHERE " + ValueHolder.REFNO + " = '" + refno + "'";

            cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(BCINCOICEHED_IS_ACTIVE, "0");

            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.update(TABLE_BCINCOICEHED, values, ValueHolder.REFNO + " =?", new String[]{String.valueOf(refno)});
            } else {
                count = (int) dB.insert(TABLE_BCINCOICEHED, null, values);
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

}
