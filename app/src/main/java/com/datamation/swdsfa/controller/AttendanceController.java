package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.Attendance;
import com.datamation.swdsfa.model.TourMapper;
import com.datamation.swdsfa.helpers.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rashmi on 12/9/2018.
 */

public class AttendanceController {
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    private String TAG = "AttendanceController";

    public AttendanceController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public long InsertUpdateTourData(Attendance tour, int val) {

        long result = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {
            String selectQuery = "SELECT * FROM " + Attendance.TABLE_ATTENDANCE + " WHERE " + Attendance.ATTENDANCE_DATE + " = '" + tour.getFTOUR_DATE() + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(Attendance.ATTENDANCE_DATE, tour.getFTOUR_DATE());
            values.put(Attendance.ATTENDANCE_F_KM, tour.getFTOUR_F_KM());
            values.put(Attendance.ATTENDANCE_F_TIME, tour.getFTOUR_F_TIME());
            values.put(Attendance.ATTENDANCE_ROUTE, tour.getFTOUR_ROUTE());
            values.put(Attendance.ATTENDANCE_S_KM, tour.getFTOUR_S_KM());
            values.put(Attendance.ATTENDANCE_S_TIME, tour.getFTOUR_S_TIME());
            values.put(Attendance.ATTENDANCE_VEHICLE, tour.getFTOUR_VEHICLE());
            values.put(Attendance.ATTENDANCE_DISTANCE, tour.getFTOUR_DISTANCE());
            values.put(Attendance.ATTENDANCE_IS_SYNCED, tour.getFTOUR_IS_SYNCED());
            values.put(Attendance.REPCODE, tour.getFTOUR_REPCODE());
            values.put(Attendance.ATTENDANCE_MAC, tour.getFTOUR_MAC());
            values.put(Attendance.ATTENDANCE_DRIVER, tour.getFTOUR_DRIVER());
            values.put(Attendance.ATTENDANCE_ASSIST, tour.getFTOUR_ASSIST());
            if (val == 0) {
                values.put(Attendance.ATTENDANCE_STLATITUDE, tour.getFTOUR_STLATITIUDE());
                values.put(Attendance.ATTENDANCE_STLONGTITUDE, tour.getFTOUR_STLONGTITIUDE());
            } else if (val == 1) {
                values.put(Attendance.ATTENDANCE_ENDLATITUDE, tour.getFTOUR_ENDLATITIUDE());
                values.put(Attendance.ATTENDANCE_ENDLONGTITUDE, tour.getFTOUR_ENDLONGTITIUDE());
            }

            if (cursor.getCount() > 0) {
                result = dB.update(Attendance.TABLE_ATTENDANCE, values, Attendance.ATTENDANCE_DATE + " =?", new String[]{String.valueOf(tour.getFTOUR_DATE())});
            } else {
                result = dB.insert(Attendance.TABLE_ATTENDANCE, null, values);
            }

            cursor.close();

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return result;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<Attendance> getIncompleteRecord() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + Attendance.TABLE_ATTENDANCE + " WHERE " + Attendance.ATTENDANCE_F_TIME + " IS NULL AND " + Attendance.ATTENDANCE_F_KM + " IS NULL AND " + Attendance.ATTENDANCE_DATE + " IS NOT NULL";


        Cursor cursor = dB.rawQuery(selectQuery, null);

        ArrayList<Attendance> tours = new ArrayList<>();
        try {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Attendance tour = new Attendance();
                    tour.setFTOUR_DATE(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_DATE)));
                    tour.setFTOUR_F_KM(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_F_KM)));
                    tour.setFTOUR_F_TIME(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_F_TIME)));
                    tour.setFTOUR_ID(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_ID)));
                    tour.setFTOUR_ROUTE(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_ROUTE)));
                    tour.setFTOUR_S_KM(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_S_KM)));
                    tour.setFTOUR_S_TIME(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_S_TIME)));
                    tour.setFTOUR_VEHICLE(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_VEHICLE)));
                    tour.setFTOUR_DISTANCE(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_DISTANCE)));
                    tour.setFTOUR_DRIVER(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_DRIVER)));
                    tour.setFTOUR_ASSIST(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_ASSIST)));

                    tours.add(tour);
                }

                return tours;
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }
        return tours;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int hasTodayRecord() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {
            String selectQuery = "SELECT * FROM " + Attendance.TABLE_ATTENDANCE + " WHERE " + Attendance.ATTENDANCE_F_TIME + " IS NOT NULL AND " + Attendance.ATTENDANCE_F_KM + " IS NOT NULL AND " + Attendance.ATTENDANCE_DATE + "='" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "' AND " + Attendance.ATTENDANCE_S_KM + " IS NOT NULL AND " + Attendance.ATTENDANCE_S_TIME + " IS NOT NULL";
            Cursor cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0)
                return cursor.getCount();

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return 0;

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int updateIsSynced() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            ContentValues values = new ContentValues();

            values.put(Attendance.ATTENDANCE_IS_SYNCED, "1");
//            if (mapper.isSynced()) {
//                count = dB.update(Attendance.TABLE_ATTENDANCE, values, Attendance.ATTENDANCE_DATE + " =?", new String[]{String.valueOf(mapper.getFTOUR_DATE())});
//            }
            count = dB.update(Attendance.TABLE_ATTENDANCE, values, Attendance.ATTENDANCE_IS_SYNCED + " =?", new String[]{String.valueOf(0)});


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

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<Attendance> getUnsyncedTourData() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Attendance> list = new ArrayList<Attendance>();
        try {
            String s = "SELECT * FROM " + Attendance.TABLE_ATTENDANCE + " WHERE " + Attendance.ATTENDANCE_IS_SYNCED + "='0'";
            Cursor cursor = dB.rawQuery(s, null);
            while (cursor.moveToNext()) {

                Attendance tour = new Attendance();
                tour.setCONSOLE_DB(SharedPref.getInstance(context).getConsoleDB().trim());
                tour.setDISTRIBUTE_DB(SharedPref.getInstance(context).getDistDB().trim());
                tour.setFTOUR_DATE(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_DATE)));
                tour.setFTOUR_F_KM(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_F_KM)));
                tour.setFTOUR_F_TIME(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_F_TIME)));
                tour.setFTOUR_ID(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_ID)));
                tour.setFTOUR_ROUTE(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_ROUTE)));
                tour.setFTOUR_S_KM(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_S_KM)));
                tour.setFTOUR_S_TIME(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_S_TIME)));
                tour.setFTOUR_VEHICLE(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_VEHICLE)));
                tour.setFTOUR_DISTANCE(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_DISTANCE)));
                tour.setFTOUR_DRIVER(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_DRIVER)));
                tour.setFTOUR_ASSIST(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_ASSIST)));
                tour.setFTOUR_REPCODE(cursor.getString(cursor.getColumnIndex(Attendance.REPCODE)));
                tour.setFTOUR_MAC(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_MAC)));
                tour.setFTOUR_STLATITIUDE(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_STLATITUDE)));
                tour.setFTOUR_STLONGTITIUDE(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_STLONGTITUDE)));
                tour.setFTOUR_ENDLATITIUDE(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_ENDLATITUDE)));
                tour.setFTOUR_ENDLONGTITIUDE(cursor.getString(cursor.getColumnIndex(Attendance.ATTENDANCE_ENDLONGTITUDE)));

                list.add(tour);
            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }
        return list;
    }

    public boolean isDayEnd(String Ydate) {
        String[] dates = Ydate.split("-");
        int day = Integer.parseInt(dates[2].toString());
        day = day - 1;
        String oldDate = dates[0] + "-" + dates[1] + "-" + day;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {
            String selectQuery = "SELECT * FROM " + Attendance.TABLE_ATTENDANCE + " WHERE EndTime IS NOT NULL AND EndKm IS NOT NULL AND tDate=" + oldDate;
            Cursor cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0)
                return true;

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }
        return false;
    }
}
