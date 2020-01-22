package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.TourHed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class  TourController
{
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "DebtorDS ";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FTOURHED = "fTourHed";
    public static final String TOURHED_ID = "Id";

    public static final String TOURHED_MANUREF = "ManuRef";
    public static final String TOURHED_LORRYCODE = "LorryCode";
    public static final String TOURHED_ROUTECODE = "RouteCode";
    public static final String TOURHED_AREACODE = "AreaCode";
    public static final String TOURHED_COSTCODE = "CostCode";
    public static final String TOURHED_REMARKS = "Remarks";
    public static final String TOURHED_LOCCODEF = "LocCodeF";
    public static final String TOURHED_LOCCODE = "LocCode";
    public static final String TOURHED_HELPERCODE = "HelperCode";
    public static final String TOURHED_ADDUSER = "AddUser";
    public static final String TOURHED_ADDMACH = "AddMach";
    public static final String TOURHED_DRIVERCODE = "DriverCode";
    public static final String TOURHED_VANLOADFLG = "VanLoadFlg";
    public static final String TOURHED_CLSFLG = "Clsflg";
    public static final String TOURHED_TOURTYPE = "TourType";
    public static final String TOURHED_FROMDATE = "DateFrom";
    public static final String TOURHED_TODATE = "DateTo";

    public static final String CREATE_FTOURHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FTOURHED + " (" + TOURHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + TOURHED_MANUREF + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT, " + TOURHED_LORRYCODE + " TEXT, " + TOURHED_ROUTECODE + " TEXT, " + TOURHED_AREACODE + " TEXT, " + TOURHED_COSTCODE + " TEXT, " + TOURHED_REMARKS + " TEXT, " + TOURHED_LOCCODEF + " TEXT, " + TOURHED_LOCCODE + " TEXT, " + DatabaseHelper.REPCODE + " TEXT, " + TOURHED_HELPERCODE + " TEXT, " + TOURHED_ADDUSER + " TEXT, " + TOURHED_ADDMACH + " TEXT, " + TOURHED_DRIVERCODE + " TEXT, " + TOURHED_VANLOADFLG + " TEXT, " + TOURHED_CLSFLG + " TEXT, " + TOURHED_FROMDATE + " TEXT, " + TOURHED_TODATE + " TEXT, " + TOURHED_TOURTYPE + " TEXT); ";


    public TourController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateTourHed(ArrayList<TourHed> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (TourHed hed : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FTOURHED + " WHERE " + DatabaseHelper.REFNO + "='" + hed.getTOURHED_REFNO() + "'", null);
                ContentValues values = new ContentValues();

                values.put(TOURHED_ADDMACH, hed.getTOURHED_ADDMACH());
                values.put(TOURHED_ADDUSER, hed.getTOURHED_ADDUSER());
                values.put(TOURHED_AREACODE, hed.getTOURHED_AREACODE());
                values.put(TOURHED_CLSFLG, hed.getTOURHED_CLSFLG());
                values.put(TOURHED_COSTCODE, hed.getTOURHED_COSTCODE());
                values.put(TOURHED_DRIVERCODE, hed.getTOURHED_DRIVERCODE());
                values.put(TOURHED_HELPERCODE, hed.getTOURHED_HELPERCODE());
//                values.put(TOURHED_ID, hed.getTOURHED_ID());
                values.put(TOURHED_LOCCODE, hed.getTOURHED_LOCCODE());
                values.put(TOURHED_LOCCODEF, hed.getTOURHED_LOCCODEF());
                values.put(TOURHED_LORRYCODE, hed.getTOURHED_LORRYCODE());
                values.put(TOURHED_MANUREF, hed.getTOURHED_MANUREF());
                values.put(DatabaseHelper.REFNO, hed.getTOURHED_REFNO());
                values.put(TOURHED_REMARKS, hed.getTOURHED_REMARKS());
                values.put(DatabaseHelper.REPCODE, hed.getTOURHED_REPCODE());
                values.put(TOURHED_ROUTECODE, hed.getTOURHED_ROUTECODE());
                values.put(TOURHED_TOURTYPE, hed.getTOURHED_TOURTYPE());
                values.put(DatabaseHelper.TXNDATE, hed.getTOURHED_TXNDATE());
                values.put(TOURHED_VANLOADFLG, hed.getTOURHED_VANLOADFLG());
                values.put(TOURHED_FROMDATE, hed.getTOURHED_DATEFROM());
                values.put(TOURHED_TODATE, hed.getTOURHED_DATETO());

                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FTOURHED, values, DatabaseHelper.REFNO + "=?", new String[]{hed.getTOURHED_REFNO()});
                } else {
                    count = (int) dB.insert(TABLE_FTOURHED, null, values);
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

    public ArrayList<TourHed> getTourDetails() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<TourHed> list = new ArrayList<TourHed>();

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String selectQuery;

        //if (sType.equals(""))
            //removed by pubudu 2017-10-31
            //selectQuery = "SELECT * FROM fTourHed a,froute b WHERE a.txndate='" + currentDate + "' AND a.routecode=b.routecode";
            selectQuery = "SELECT * FROM fTourHed a,froute b WHERE a.routecode=b.routecode";
        //else
            //removed by pubudu 2017-10-31
            //selectQuery = "SELECT * FROM fTourHed a,froute b WHERE a.txndate='" + currentDate + "' AND a.routecode=b.routecode AND tourtype='" + sType + "'";
            //selectQuery = "SELECT * FROM fTourHed a,froute b WHERE a.routecode=b.routecode AND tourtype='" + sType + "'";

        try {
            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                TourHed hed = new TourHed();

                hed.setTOURHED_ADDMACH(cursor.getString(cursor.getColumnIndex(TOURHED_ADDMACH)));
                hed.setTOURHED_ADDUSER(cursor.getString(cursor.getColumnIndex(TOURHED_ADDUSER)));
                hed.setTOURHED_AREACODE(cursor.getString(cursor.getColumnIndex(TOURHED_AREACODE)));
                hed.setTOURHED_CLSFLG(cursor.getString(cursor.getColumnIndex(TOURHED_CLSFLG)));
                hed.setTOURHED_COSTCODE(cursor.getString(cursor.getColumnIndex(TOURHED_COSTCODE)));
                hed.setTOURHED_DRIVERCODE(cursor.getString(cursor.getColumnIndex(TOURHED_DRIVERCODE)));
                hed.setTOURHED_HELPERCODE(cursor.getString(cursor.getColumnIndex(TOURHED_HELPERCODE)));
                hed.setTOURHED_ID(cursor.getString(cursor.getColumnIndex(RouteController.FROUTE_ROUTE_NAME)));
                hed.setTOURHED_LOCCODE(cursor.getString(cursor.getColumnIndex(TOURHED_LOCCODE)));
                hed.setTOURHED_LOCCODEF(cursor.getString(cursor.getColumnIndex(TOURHED_LOCCODEF)));
                hed.setTOURHED_LORRYCODE(cursor.getString(cursor.getColumnIndex(TOURHED_LORRYCODE)));
                hed.setTOURHED_MANUREF(cursor.getString(cursor.getColumnIndex(TOURHED_MANUREF)));
                hed.setTOURHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                hed.setTOURHED_REMARKS(cursor.getString(cursor.getColumnIndex(TOURHED_REMARKS)));
                hed.setTOURHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
                hed.setTOURHED_ROUTECODE(cursor.getString(cursor.getColumnIndex(TOURHED_ROUTECODE)));
                hed.setTOURHED_TOURTYPE(cursor.getString(cursor.getColumnIndex(TOURHED_TOURTYPE)));
                hed.setTOURHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
                hed.setTOURHED_VANLOADFLG(cursor.getString(cursor.getColumnIndex(TOURHED_VANLOADFLG)));


                list.add(hed);
            }

            cursor.close();

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return list;

    }
}
