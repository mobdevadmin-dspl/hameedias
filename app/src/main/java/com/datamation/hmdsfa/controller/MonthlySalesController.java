package com.datamation.hmdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.model.MonthlySales;
import com.datamation.hmdsfa.model.MonthlyTarget;

import java.util.ArrayList;

/**
 * Created by MMS on 22/3/2022.
 */

public class MonthlySalesController {
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    private String TAG = "MonthlySalesController";


    // table
    public static final String TABLE_MON_SALES = "fMonthlySales";
    // table attributes
    public static final String FID = "ID";
    public static final String FREP_CODE = "RepCode";
    public static final String FSAL_MONTH = "SalesMonth";
    public static final String FSAL_VALUE = "SalesValue";
    public static final String FSAL_YEAR = "SalesYear";



    // create String
    public static final String CREATE_FMONTH_SALES_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_MON_SALES + " (" + FID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FREP_CODE + " TEXT, " + FSAL_MONTH + " TEXT, " + FSAL_VALUE + " TEXT, " + FSAL_YEAR + " TEXT ); ";


    public MonthlySalesController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public long InsertUpdateMonthlySalesData(ArrayList<MonthlySales> salList) {

        long result = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            for(MonthlySales sal : salList){
                String selectQuery = "SELECT * FROM " + TABLE_MON_SALES + " WHERE " + FSAL_YEAR + " = '" + sal.getSalYear() + "' AND "+ FSAL_MONTH + " = '"+sal.getSalMonth()+"'";

                Cursor cursor = dB.rawQuery(selectQuery, null);

                ContentValues values = new ContentValues();

                values.put(FREP_CODE, sal.getRepCode());
                values.put(FSAL_MONTH, sal.getSalMonth());
                values.put(FSAL_YEAR, sal.getSalYear());
                values.put(FSAL_VALUE, sal.getSalValue());


                if (cursor.getCount() > 0) {
                    result = dB.update(TABLE_MON_SALES, values, FSAL_YEAR + " =?" + " AND " + FSAL_MONTH + " =?", new String[]{String.valueOf(sal.getSalYear()), String.valueOf(sal.getSalMonth())});

                } else {
                    result = dB.insert(TABLE_MON_SALES, null, values);
                }
                cursor.close();
            }



        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return result;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_MON_SALES, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_MON_SALES, null, null);
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


}
