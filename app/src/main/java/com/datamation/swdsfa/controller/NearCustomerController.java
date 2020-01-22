package com.datamation.swdsfa.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.location.Location;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Customer;
import com.datamation.swdsfa.model.NearDebtor;

import java.util.ArrayList;

import static com.datamation.swdsfa.helpers.DatabaseHelper.DEBCODE;

public class NearCustomerController
{
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    private String TAG = "NearCustomerController";
    // rashmi - 2019-12-17 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FNEARDEBTOR = "fNearDebtor";
    // table attributes
    public static final String FNEARDEBTOR_ID = "id";
    public static final String FNEARDEBTOR_AREA = "NDArea";
    public static final String FNEARDEBTOR_TERRITORY = "NDTeri";
    public static final String FNEARDEBTOR_DESCRIPTION = "NDDesc";
    public static final String FNEARDEBTOR_RETAILER = "NDRet";
    public static final String FNEARDEBTOR_RETCATEGORY = "NDRetCat";
    public static final String FNEARDEBTOR_ADDRESS = "NDAdd";
    public static final String FNEARDEBTOR_LONGITUDE = "NDLongi";
    public static final String FNEARDEBTOR_LATITUDE = "NDLati";

    // create String
    public static final String CREATE_FNEARDEBTOR_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FNEARDEBTOR + " ("
            + FNEARDEBTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FNEARDEBTOR_AREA + " TEXT, "
            + FNEARDEBTOR_TERRITORY + " TEXT, "
            + FNEARDEBTOR_DESCRIPTION + " TEXT, "
            + FNEARDEBTOR_RETAILER + " TEXT, "
            + FNEARDEBTOR_RETCATEGORY + " TEXT, "
            + FNEARDEBTOR_ADDRESS + " TEXT, "
            + FNEARDEBTOR_LONGITUDE + " TEXT, "
            + FNEARDEBTOR_LATITUDE + " TEXT); ";

    public NearCustomerController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public void InsertOrReplaceNearDebtor(ArrayList<NearDebtor> list) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_FNEARDEBTOR + " (NDArea,NDTeri,NDDesc,NDRet,NDRetCat,NDAdd,NDLongi,NDLati) " + " VALUES (?,?,?,?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (NearDebtor ndebtor : list) {

                stmt.bindString(1, ndebtor.getFNEARDEBTOR_AREA());
                stmt.bindString(2, ndebtor.getFNEARDEBTOR_TERRITORY());
                stmt.bindString(3, ndebtor.getFNEARDEBTOR_DESCRIPTION());
                stmt.bindString(4, ndebtor.getFNEARDEBTOR_RETAILER());
                stmt.bindString(5, ndebtor.getFNEARDEBTOR_RETCATEGORY());
                stmt.bindString(6, ndebtor.getFNEARDEBTOR_ADDRESS());
                stmt.bindString(7, ndebtor.getFNEARDEBTOR_LONGI());
                stmt.bindString(8, ndebtor.getFNEARDEBTOR_LATI());
                stmt.execute();
                stmt.clearBindings();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dB.setTransactionSuccessful();
            dB.endTransaction();
            dB.close();
        }

    }

    public ArrayList<NearDebtor> getAllGPSNearCustomer(Double lat, Double lon) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Double debLat = 0.0;
        Double debLon = 0.0;

        ArrayList<NearDebtor> list = new ArrayList<NearDebtor>();
        Cursor cursor = null;
        try {
            String selectQuery = "select * from " + TABLE_FNEARDEBTOR;

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                NearDebtor nDeb = new NearDebtor();

                if ((cursor.getString(cursor.getColumnIndex(FNEARDEBTOR_LATITUDE))!= null) && (cursor.getString(cursor.getColumnIndex(FNEARDEBTOR_LONGITUDE))!=null))
                {
                    debLat = Double.parseDouble(cursor.getString(cursor.getColumnIndex(FNEARDEBTOR_LATITUDE)));
                    debLon = Double.parseDouble(cursor.getString(cursor.getColumnIndex(FNEARDEBTOR_LONGITUDE)));

                    float[] results = new float[1];
                    Location.distanceBetween(lat, lon, debLat, debLon, results);
//                    Location.distanceBetween(6.8885905, 79.8559534, 6.888781, 79.855777, results);
                    if(String.valueOf(lat).equals("")&&String.valueOf(lon).equals("")){
                        lat = 0.0;
                        lon = 0.0;
                    }

                    float distanceInMeters = results[0];
                    boolean isWithin100m = distanceInMeters < 150;

                    if (isWithin100m)
                    {
                        nDeb.setFNEARDEBTOR_AREA(cursor.getString(cursor.getColumnIndex(FNEARDEBTOR_AREA)));
                        nDeb.setFNEARDEBTOR_TERRITORY(cursor.getString(cursor.getColumnIndex(FNEARDEBTOR_TERRITORY)));
                        nDeb.setFNEARDEBTOR_DESCRIPTION(cursor.getString(cursor.getColumnIndex(FNEARDEBTOR_DESCRIPTION)));
                        nDeb.setFNEARDEBTOR_RETAILER(cursor.getString(cursor.getColumnIndex(FNEARDEBTOR_RETAILER)));
                        nDeb.setFNEARDEBTOR_RETCATEGORY(cursor.getString(cursor.getColumnIndex(FNEARDEBTOR_RETCATEGORY)));
                        nDeb.setFNEARDEBTOR_ADDRESS(cursor.getString(cursor.getColumnIndex(FNEARDEBTOR_ADDRESS)));
                        nDeb.setFNEARDEBTOR_LATI(cursor.getString(cursor.getColumnIndex(FNEARDEBTOR_LATITUDE)));
                        nDeb.setFNEARDEBTOR_LONGI(cursor.getString(cursor.getColumnIndex(FNEARDEBTOR_LONGITUDE)));

                        list.add(nDeb);
                    }
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

        return list;
    }

    public int deleteAll() {
        int success = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FNEARDEBTOR, null);

            if (cursor.getCount() > 0) {
                success = dB.delete(TABLE_FNEARDEBTOR, null, null);
                Log.d("Success", success + "");
            }
            else
            {
                success = 1;
            }
        } catch (Exception e) {

            Log.d(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return success;

    }
}
