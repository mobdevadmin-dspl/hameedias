package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Bank;

import java.util.ArrayList;

public class BankController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "BankDS ";
    // rashmi - 2019-12-18 move from database_helper , because of reduce coding in database helper*******************************************************************************

    // table
    public static final String TABLE_FBANK = "fBank";
    // table attributes
    public static final String FBANK_ID = "bankre_id";
    public static final String FBANK_RECORD_ID = "RecordId";
    public static final String FBANK_BANK_CODE = "bankcode";
    public static final String FBANK_BANK_NAME = "bankname";
    public static final String FBANK_BANK_ACC_NO = "bankaccno";
    public static final String FBANK_BRANCH = "Branch";
    public static final String FBANK_ADD1 = "bankadd1";
    public static final String FBANK_ADD2 = "bankadd2";
    public static final String FBANK_ADD_DATE = "AddDate";
    public static final String FBANK_ADD_MACH = "AddMach";
    public static final String FBANK_ADD_USER = "AddUser";

    // create String
    public static final String CREATE_FBANK_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FBANK + " (" + FBANK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FBANK_RECORD_ID + " TEXT, " + FBANK_BANK_CODE + " TEXT, " + FBANK_BANK_NAME + " TEXT, " + FBANK_BANK_ACC_NO + " TEXT, " + FBANK_BRANCH + " TEXT, " + FBANK_ADD1 + " TEXT, " + FBANK_ADD2 + " TEXT, " + FBANK_ADD_MACH + " TEXT, " + FBANK_ADD_USER + " TEXT, " + FBANK_ADD_DATE + " TEXT); ";

    public static final String TESTBANK = "CREATE UNIQUE INDEX IF NOT EXISTS idxbank_something ON " + TABLE_FBANK + " (" + FBANK_BANK_CODE + ")";

    public BankController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateBank(ArrayList<Bank> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (Bank bank : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FBANK + " WHERE " + FBANK_BANK_CODE + "='" + bank.getFBANK_BANK_CODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(FBANK_BANK_CODE, bank.getFBANK_BANK_CODE());
                values.put(FBANK_BANK_NAME, bank.getFBANK_BANK_NAME());
                values.put(FBANK_BANK_ACC_NO, bank.getFBANK_BANK_ACC_NO());
                values.put(FBANK_BRANCH, bank.getFBANK_BRANCH());
                values.put(FBANK_ADD1, bank.getFBANK_ADD1());
                values.put(FBANK_ADD2, bank.getFBANK_ADD2());
                values.put(FBANK_ADD_DATE, bank.getFBANK_ADD_DATE());
                values.put(FBANK_ADD_MACH, bank.getFBANK_ADD_MACH());
                values.put(FBANK_ADD_USER, bank.getFBANK_ADD_USER());

                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FBANK, values, FBANK_BANK_CODE + "=?", new String[]{bank.getFBANK_BANK_CODE().toString()});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(TABLE_FBANK, null, values);
                    Log.v(TAG, "Inserted " + count);
                }
                cursor.close();
            }

        } catch (Exception e) {
            Log.v(TAG, e.toString());
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

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FBANK, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FBANK, null, null);
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
    /*-----------------------------------------------------------------------*/
    public ArrayList<Bank> getBanks() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Bank> list = new ArrayList<Bank>();

        String selectQuery = "select * from " + TABLE_FBANK;

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                Bank bank = new Bank();

                bank.setFBANK_BANK_CODE(cursor.getString(cursor.getColumnIndex(FBANK_BANK_CODE)));
                bank.setFBANK_BANK_NAME(cursor.getString(cursor.getColumnIndex(FBANK_BANK_NAME))+" - "+cursor.getString(cursor.getColumnIndex(FBANK_BRANCH)));


                list.add(bank);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();

        }

        return list;
    }


    public String getBankCodeAndBranchCodeByBankName(String bankName,String branchName)
    {
        String bankCode = null;

        if (dB == null)
        {
            open();
        }
        else if (!dB.isOpen())
        {
            open();
        }


        try
        {
            String selectQuery = "SELECT bankcode FROM '"+TABLE_FBANK+"' WHERE BankName = '"+bankName.trim()+"' AND Branch = '"+branchName.trim()+"' ";
            Cursor cursor = dB.rawQuery(selectQuery,null);
            cursor.moveToFirst();

            if(cursor.getCount() > 0)
            {
                bankCode = cursor.getString(cursor.getColumnIndex("bankcode"));

                if(!bankCode.isEmpty())
                {
                    return bankCode;
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(dB.isOpen())
            {
                dB.close();
            }
        }

        return bankCode;
    }

}
