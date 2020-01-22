package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Expense;


import java.util.ArrayList;

public class ExpenseController {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "ExpenseDS ";
    public static final String TABLE_FEXPENSE = "fExpense";

    public static final String FEXPENSE_ID = "uexp_id";
    public static final String FEXPENSE_CODE = "ExpCode";
    public static final String FEXPENSE_GRP_CODE = "ExpGrpCode";
    public static final String FEXPENSE_NAME = "ExpName";
    public static final String FEXPENSE_RECORDID = "RecordId";
    public static final String FEXPENSE_STATUS = "Status";
    public static final String FEXPENSE_ADD_MACH = "AddMach";
    public static final String FEXPENSE_ADD_USER = "AddUser";
    public static final String FEXPENSE_ADD_DATE = "AddDate";

    // create String
    public static final String CREATE_FEXPENSE_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FEXPENSE + " (" + FEXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FEXPENSE_CODE + " TEXT, " + FEXPENSE_GRP_CODE + " TEXT, " + FEXPENSE_NAME + " TEXT, " + FEXPENSE_RECORDID + " TEXT, " + FEXPENSE_STATUS + " TEXT, " + FEXPENSE_ADD_MACH + " TEXT, " + FEXPENSE_ADD_DATE + " TEXT, " + FEXPENSE_ADD_USER + " TEXT); ";

    public ExpenseController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateFExpense(ArrayList<Expense> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (Expense expense : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " +TABLE_FEXPENSE + " WHERE " + FEXPENSE_CODE + "='" + expense.getFEXPENSE_CODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(FEXPENSE_CODE, expense.getFEXPENSE_CODE());
                values.put(FEXPENSE_GRP_CODE, expense.getFEXPENSE_GRP_CODE());
                values.put(FEXPENSE_NAME, expense.getFEXPENSE_NAME());
                values.put(FEXPENSE_RECORDID, expense.getFEXPENSE_RECORDID());
                values.put(FEXPENSE_ADD_MACH, expense.getFEXPENSE_ADD_MACH());
                values.put(FEXPENSE_STATUS, expense.getFEXPENSE_STATUS());
                values.put(FEXPENSE_ADD_USER, expense.getFEXPENSE_ADD_USER());
                values.put(FEXPENSE_ADD_DATE, expense.getFEXPENSE_ADD_DATE());

                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FEXPENSE, values, FEXPENSE_CODE + "=?", new String[]{expense.getFEXPENSE_CODE().toString()});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(TABLE_FEXPENSE, null, values);
                    Log.v(TAG, "Inserted " + count);
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }
        return count;

    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FEXPENSE, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FEXPENSE, null, null);
                Log.v("Success", success + "");
            }
        } catch (Exception e) {

            Log.v(TAG + " Exception ", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return count;

    }

    public ArrayList<Expense> getAllExpense(String searchword) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Expense> list = new ArrayList<Expense>();

        String selectQuery = "SELECT * FROM " + TABLE_FEXPENSE + " WHERE ExpName LIKE '%" + searchword + "%' ";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {

            Expense expense = new Expense();

            expense.setFEXPENSE_CODE(cursor.getString(cursor.getColumnIndex(FEXPENSE_CODE)));
            expense.setFEXPENSE_NAME(cursor.getString(cursor.getColumnIndex(FEXPENSE_NAME)));

            list.add(expense);

        }

        return list;
    }


    public String getReasonByCode(String sCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }


        String selectQuery = "SELECT ExpName FROM " + TABLE_FEXPENSE + " WHERE ExpCode='" + sCode + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex(FEXPENSE_NAME));
        }

        cursor.close();
        dB.close();

        return null;

    }


}
