package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.FirebaseData;

import java.util.ArrayList;

public class FirebaseMediaController {

    //MANOJ - 2019/12/30 create firebase media table for store imges and vedio links
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "Finac New";
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;

    public static final String TABLE_FIREBASE_MEDIA = "FirebaseData";
    public static final String MEDIA_URL = "URL";
    public static final String MEDIA_FLAG = "Flag";
    public static final String MEDIA_TYPE = "Type";


    public static final String CREATETABLE_FIREBASE_MEDIA = "CREATE  TABLE IF NOT EXISTS " + TABLE_FIREBASE_MEDIA + " ("
//            + MEDIA_URL + " TEXT PRIMARY KEY, "
            + MEDIA_URL + " TEXT, "
            + MEDIA_FLAG + " TEXT, "
            + MEDIA_TYPE + " TEXT) ";

    public FirebaseMediaController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateFirebaseData(ArrayList<FirebaseData> list,int val) {

        int serverdbID = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        try {

            for (FirebaseData fD : list) {

                ContentValues contentValues = new ContentValues();
                String selectQuery = "SELECT * FROM " + TABLE_FIREBASE_MEDIA + " WHERE " + MEDIA_TYPE + " = '" + fD.getMEDIA_TYPE() + "' and " + MEDIA_URL + " = '" + fD.getMEDIA_URL() + "'";
                cursor = dB.rawQuery(selectQuery, null);
                contentValues.put(MEDIA_URL, fD.getMEDIA_URL());
                contentValues.put(MEDIA_TYPE, fD.getMEDIA_TYPE());

                int cn = cursor.getCount();
                if (cn > 0) {
                    if(val == 1) {
                        contentValues.put(MEDIA_FLAG, 1);
                        serverdbID = dB.update(TABLE_FIREBASE_MEDIA, contentValues, MEDIA_URL + " = '" + fD.getMEDIA_URL() + "'", null);
                    }
                } else {
                    contentValues.put(MEDIA_FLAG, 0);
                    serverdbID = (int) dB.insert(TABLE_FIREBASE_MEDIA, null, contentValues);

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
        return serverdbID;

    }

    public ArrayList<FirebaseData> getAllMediafromDb(String type) {
        if (dB == null)
            open();
        else if (!dB.isOpen())
            open();

        ArrayList<FirebaseData> urlList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FIREBASE_MEDIA + " WHERE " + MEDIA_TYPE + " = '" + type + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {
                FirebaseData fd = new FirebaseData();
                fd.setMEDIA_URL(cursor.getString(cursor.getColumnIndex(MEDIA_URL)));
                urlList.add(fd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlList;
    }

    public int getAllMediaforCheckIfIsExist(String type) {
        if (dB == null)
            open();
        else if (!dB.isOpen())
            open();

        String selectQuery = "SELECT * FROM " + TABLE_FIREBASE_MEDIA + " WHERE " + MEDIA_TYPE + " = '" + type + "' AND "+MEDIA_FLAG+" = '"+0+"'";
//        String selectQuery = "SELECT * FROM " + TABLE_FIREBASE_MEDIA + " WHERE " + MEDIA_TYPE + " = '" + fd.getMEDIA_TYPE() + "' AND " + MEDIA_URL + " = '" + fd.getMEDIA_URL() + "' AND "+MEDIA_FLAG+" = '"+0+"'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        int count = cursor.getCount();
        return count;
    }

    public int getAllIfIsExist(ArrayList<FirebaseData> list) {
        if (dB == null)
            open();
        else if (!dB.isOpen())
            open();
        int count = 0;

        for(FirebaseData fd : list) {
            String selectQuery = "SELECT * FROM " + TABLE_FIREBASE_MEDIA + " WHERE " + MEDIA_TYPE + " = '" + fd.getMEDIA_TYPE() + "' AND " + MEDIA_URL + " = '" + fd.getMEDIA_URL() + "'";
            Cursor cursor = dB.rawQuery(selectQuery, null);
            count = cursor.getCount();
        }
        return count;
    }

    public void deleteAll(String type) {
        if (dB == null)
            open();
        else if (!dB.isOpen())
            open();

        dB.execSQL("delete from "+ TABLE_FIREBASE_MEDIA + " where "+MEDIA_TYPE+" = '"+type+"'");
    }
}
