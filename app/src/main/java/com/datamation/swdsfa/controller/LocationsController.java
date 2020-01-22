package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Locations;


import java.util.ArrayList;

public class LocationsController {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";
    // rashmi - 2019-12-24 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FLOCATIONS = "fLocations";
    // table attributes
    public static final String FLOCATIONS_ID = "fLocations_id";
    public static final String FLOCATIONS_ADD_MACH = "AddMach";
    public static final String FLOCATIONS_ADD_USER = "AddUser";
    public static final String FLOCATIONS_LOC_CODE = "LocCode";
    public static final String FLOCATIONS_LOC_NAME = "LocName";
    public static final String FLOCATIONS_LOC_T_CODE = "LoctCode";
    public static final String FLOCATIONS_COST_CODE = "CostCode";

    // create String
    public static final String CREATE_FLOCATIONS_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FLOCATIONS + " (" + FLOCATIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FLOCATIONS_ADD_MACH + " TEXT, " + FLOCATIONS_ADD_USER + " TEXT, " + FLOCATIONS_LOC_CODE + " TEXT, " + FLOCATIONS_LOC_NAME + " TEXT, " + FLOCATIONS_LOC_T_CODE + " TEXT, " + DatabaseHelper.REPCODE + " TEXT, " + FLOCATIONS_COST_CODE + " TEXT); ";

    public LocationsController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateFLocations(ArrayList<Locations> list) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (Locations locations : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FLOCATIONS + " WHERE " + FLOCATIONS_LOC_CODE + "='" + locations.getFLOCATIONS_LOC_CODE() + "'", null);

                ContentValues values = new ContentValues();

                values.put(FLOCATIONS_ADD_MACH, locations.getFLOCATIONS_ADD_MACH());
                values.put(FLOCATIONS_ADD_USER, locations.getFLOCATIONS_ADD_USER());
                values.put(FLOCATIONS_LOC_CODE, locations.getFLOCATIONS_LOC_CODE());
                values.put(FLOCATIONS_LOC_NAME, locations.getFLOCATIONS_LOC_NAME());
                values.put(FLOCATIONS_LOC_T_CODE, locations.getFLOCATIONS_LOC_T_CODE());
                values.put(DatabaseHelper.REPCODE, locations.getFLOCATIONS_REP_CODE());

                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FLOCATIONS, values, FLOCATIONS_LOC_CODE + "=?", new String[]{locations.getFLOCATIONS_LOC_CODE().toString()});
                    Log.v("TABLE FLOCATIONS : ", "Updated");
                } else {
                    count = (int) dB.insert(TABLE_FLOCATIONS, null, values);
                    Log.v("TABLE FLOCATIONS : ", "Inserted " + count);
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

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<String> getlocDetails(String loctcode, String searchword) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<String> Itemname = new ArrayList<String>();

        String selectQuery = "select LocName,LocCode from fLocations where LoctCode = '" + loctcode + "' AND ( LocCode LIKE '%" + searchword + "%' OR LocName LIKE '%" + searchword + "%')";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            String Itemname1 = cursor.getString(0) + "-:-" + cursor.getString(1);
            Itemname.add(Itemname1);

        }

        return Itemname;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*/

    public String GetLocCodebyRepCode(String RepCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String LocCode = null;
        try {
            Cursor cursor = dB.rawQuery("SELECT LocCode FROM fLocations WHERE RepCode = '" + RepCode + "'", null);

            while (cursor.moveToNext()) {

                LocCode = cursor.getString(cursor.getColumnIndex(FLOCATIONS_LOC_CODE));

            }

            cursor.close();

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {

            dB.close();
        }

        return LocCode;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public String GetReserveCode(String locCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            Cursor cursor = dB.rawQuery("SELECT LocCode FROM fLocations WHERE LoctCode = '" + locCode + "'", null);

            while (cursor.moveToNext()) {
                return cursor.getString(cursor.getColumnIndex(FLOCATIONS_LOC_CODE));
            }

            cursor.close();

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {

            dB.close();
        }

        return null;
    }
	
	
	
	

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*/

    // public ArrayList<Locations> getlocDetails(String loctcode)
    // {
    // if (dB == null)
    // {
    // open();
    // } else if (!dB.isOpen())
    // {
    // open();
    // }
    //
    // ArrayList<Locations> list = new ArrayList<Locations>();
    //
    // String selectQuery =
    // "select LocName,LocCode from fLocation where LoctCode = '"+loctcode+"'";
    //
    // Cursor cursor = dB.rawQuery(selectQuery, null);
    // while(cursor.moveToNext()){
    //
    // Locations Locationsdata=new Locations();
    //
    // Locationsdata.setFLOCATIONS_LOC_NAME(cursor.getString(cursor.getColumnIndex(FLOCATIONS_LOC_NAME)));
    // Locationsdata.setFLOCATIONS_LOC_CODE(cursor.getString(cursor.getColumnIndex(FLOCATIONS_LOC_CODE)));
    //
    //
    // list.addLocationsdatat);
    //
    // }
    //
    // return list;
    // }

    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FLOCATIONS, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FLOCATIONS, null, null);
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

    public String getItemNameByCode(String code) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + TABLE_FLOCATIONS + " WHERE " + FLOCATIONS_LOC_CODE + "='" + code + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(FLOCATIONS_LOC_NAME));

        }

        return "";
    }
}
