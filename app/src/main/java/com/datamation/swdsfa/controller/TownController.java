package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Route;
import com.datamation.swdsfa.model.Town;

import java.util.ArrayList;

public class TownController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "TownDS";
    //  town table
    public static final String TABLE_FTOWN = "fTown";
    // table attributes
    public static final String FTOWN_ID = "townre_id";
    public static final String FTOWN_RECORDID = "RecordId";
    public static final String FTOWN_CODE = "TownCode";
    public static final String FTOWN_NAME = "TownName";
    public static final String FTOWN_DISTR_CODE = "DistrCode";
    public static final String FTOWN_ADDDATE = "AddDate";
    public static final String FTOWN_ADD_MACH = "AddMach";
    public static final String FTOWN_ADD_USER = "AddUser";

    // create String
    public static final String CREATE_FTOWN_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FTOWN + " (" + FTOWN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTOWN_RECORDID + " TEXT, " + FTOWN_CODE + " TEXT, " + FTOWN_NAME + " TEXT, " + FTOWN_DISTR_CODE + " TEXT, " + FTOWN_ADDDATE + " TEXT, " + FTOWN_ADD_MACH + " TEXT, " + FTOWN_ADD_USER + " TEXT); ";

    public TownController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    /*
     * insert code
     */
    @SuppressWarnings("static-access")
    public int createOrUpdateFTown(ArrayList<Town> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            for (Town town : list) {
                cursor = dB.rawQuery("SELECT * FROM " + TABLE_FTOWN+ " WHERE " + FTOWN_CODE + "='" + town.getTownCode() + "'", null);


                ContentValues values = new ContentValues();
                values.put(FTOWN_CODE, town.getTownCode());
                values.put(FTOWN_NAME,town.getTownName());

                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FTOWN, values, FTOWN_CODE + "=?", new String[]{town.getTownCode().toString()});
                    Log.v("TABLE_TOWN : ", "Updated "+count);
                } else {
                    count = (int) dB.insert(TABLE_FTOWN, null, values);
                    Log.v("TABLE_TOWN : ", "Inserted " + count);
                }
            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();        }
        return count;
    }
    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FTOWN, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FTOWN, null, null);
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
    public ArrayList<Town> getAllTowns() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        ArrayList<Town> list = new ArrayList<Town>();

        String selectQuery = "select * from fTown";
        Cursor cursor = null;
        try {
            cursor = dB.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                Town town = new Town();
//                town.setTownID(cursor.getString(cursor.getColumnIndex(FTOWN_ID)));
                town.setTownCode(cursor.getString(cursor.getColumnIndex(FTOWN_CODE)));
                town.setTownName(cursor.getString(cursor.getColumnIndex(FTOWN_NAME)));

                list.add(town);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
