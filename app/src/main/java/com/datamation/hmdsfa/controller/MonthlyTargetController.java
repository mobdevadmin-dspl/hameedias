package com.datamation.hmdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.helpers.ValueHolder;
import com.datamation.hmdsfa.model.Attendance;
import com.datamation.hmdsfa.model.MonthlyTarget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MMS on 22/3/2022.
 */

public class MonthlyTargetController {
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    private String TAG = "MonthlyTargetController";


    // table
    public static final String TABLE_MON_TARGET = "fMonthlyTarget";
    // table attributes
    public static final String FID = "ID";
    public static final String FREP_CODE = "Rep Code";
    public static final String FTAR_MONTH = "Month";
    public static final String FTAR_VALUE = "Value";
    public static final String FTAR_YEAR = "Year";



    // create String
    public static final String CREATE_FMONTH_TARGET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_MON_TARGET + " (" + FID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FREP_CODE + " TEXT, " + FTAR_MONTH + " TEXT, " + FTAR_VALUE + " TEXT, " + FTAR_YEAR + " TEXT ); ";


    public MonthlyTargetController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public long InsertUpdateMonthlyTargetData(ArrayList<MonthlyTarget> tarList) {

        long result = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {

            for(MonthlyTarget tar : tarList){
                String selectQuery = "SELECT * FROM " + TABLE_MON_TARGET + " WHERE " + FTAR_YEAR + " = '" + tar.getTarYear() + "' AND "+ FTAR_MONTH + " = '"+tar.getTarMonth()+"'";

                Cursor cursor = dB.rawQuery(selectQuery, null);

                ContentValues values = new ContentValues();

                values.put(FREP_CODE, tar.getRepCode());
                values.put(FTAR_MONTH, tar.getRepCode());
                values.put(FTAR_YEAR, tar.getRepCode());
                values.put(FTAR_VALUE, tar.getRepCode());


                if (cursor.getCount() > 0) {
                    result = dB.update(TABLE_MON_TARGET, values, FTAR_YEAR + " =?" + " AND " + FTAR_MONTH + " =?", new String[]{String.valueOf(tar.getTarYear()), String.valueOf(tar.getTarYear())});

                } else {
                    result = dB.insert(TABLE_MON_TARGET, null, values);
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

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_MON_TARGET, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_MON_TARGET, null, null);
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
