package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Control;

import java.util.ArrayList;

public class CompanyDetailsController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "ControlDS";

    // rashmi - 2019-12-17 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String REFNO = "RefNo";
    public static final String TXNDATE = "TxnDate";
    public static final String REPCODE = "RepCode";
    public static final String DEALCODE = "DealCode";
    public static final String DEBCODE = "DebCode";
    // table
    public static final String TABLE_FCONTROL = "fControl";
    // table attributes
    public static final String FCONTROL_ID = "fcontrol_id";
    public static final String FCONTROL_COM_NAME = "ComName";
    public static final String FCONTROL_COM_ADD1 = "ComAdd1";
    public static final String FCONTROL_COM_ADD2 = "ComAdd2";
    public static final String FCONTROL_COM_ADD3 = "ComAdd3";
    public static final String FCONTROL_COM_TEL1 = "comtel1";
    public static final String FCONTROL_COM_TEL2 = "comtel2";
    public static final String FCONTROL_COM_FAX = "comfax1";
    public static final String FCONTROL_COM_EMAIL = "comemail";
    public static final String FCONTROL_COM_WEB = "comweb";
    public static final String FCONTROL_FYEAR = "confyear";
    public static final String FCONTROL_TYEAR = "contyear";
    public static final String FCONTROL_COM_REGNO = "comRegNo";
    public static final String FCONTROL_FTXN = "ConfTxn";
    public static final String FCONTROL_TTXN = "ContTxn";
    public static final String FCONTROL_CRYSTALPATH = "Crystalpath";
    public static final String FCONTROL_VATCMTAXNO = "VatCmTaxNo";
    public static final String FCONTROL_NBTCMTAXNO = "NbtCmTaxNo";
    public static final String FCONTROL_SYSTYPE = "SysType";
    public static final String FCONTROL_BASECUR = "basecur";
    public static final String FCONTROL_BALGCRLM = "BalgCrLm";
    public static final String FCONTROL_CONAGE1 = "conage1";
    public static final String FCONTROL_CONAGE2 = "conage2";
    public static final String FCONTROL_CONAGE3 = "conage3";
    public static final String FCONTROL_CONAGE4 = "conage4";
    public static final String FCONTROL_CONAGE5 = "conage5";
    public static final String FCONTROL_CONAGE6 = "conage6";
    public static final String FCONTROL_CONAGE7 = "conage7";
    public static final String FCONTROL_CONAGE8 = "conage8";
    public static final String FCONTROL_CONAGE9 = "conage9";
    public static final String FCONTROL_CONAGE10 = "conage10";
    public static final String FCONTROL_CONAGE11 = "conage11";
    public static final String FCONTROL_CONAGE12 = "conage12";
    public static final String FCONTROL_CONAGE13 = "conage13";
    public static final String FCONTROL_CONAGE14 = "conage14";
    public static final String FCONTROL_SALESACC = "salesacc";

    // create String
    public static final String CREATE_FCONTROL_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FCONTROL + " (" + FCONTROL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FCONTROL_COM_NAME + " TEXT, " + FCONTROL_COM_ADD1 + " TEXT, " + FCONTROL_COM_ADD2 + " TEXT, " + FCONTROL_COM_ADD3 + " TEXT, " + FCONTROL_COM_TEL1 + " TEXT, " + FCONTROL_COM_TEL2 + " TEXT, " + FCONTROL_COM_FAX + " TEXT, " + FCONTROL_COM_EMAIL + " TEXT, " + FCONTROL_COM_WEB + " TEXT, " + FCONTROL_FYEAR + " TEXT, " + FCONTROL_TYEAR + " TEXT, " + FCONTROL_COM_REGNO + " TEXT, " + FCONTROL_FTXN + " TEXT, " + FCONTROL_TTXN + " TEXT, " + FCONTROL_CRYSTALPATH + " TEXT, " + FCONTROL_VATCMTAXNO + " TEXT, " + FCONTROL_NBTCMTAXNO + " TEXT, " + FCONTROL_SYSTYPE + " TEXT, " + DEALCODE + " TEXT, " + FCONTROL_BASECUR + " TEXT, " + FCONTROL_BALGCRLM + " TEXT, " + FCONTROL_CONAGE1 + " TEXT, " + FCONTROL_CONAGE2 + " TEXT, " + FCONTROL_CONAGE3 + " TEXT, " + FCONTROL_CONAGE4 + " TEXT, " + FCONTROL_CONAGE5 + " TEXT, " + FCONTROL_CONAGE6 + " TEXT, " + FCONTROL_CONAGE7 + " TEXT, " + FCONTROL_CONAGE8 + " TEXT, " + FCONTROL_CONAGE9 + " TEXT, " + FCONTROL_CONAGE10 + " TEXT, " + FCONTROL_CONAGE11 + " TEXT, " + FCONTROL_CONAGE12 + " TEXT, " + FCONTROL_CONAGE13 + " TEXT, " + FCONTROL_CONAGE14 + " TEXT, " + FCONTROL_SALESACC + " TEXT); ";


    public CompanyDetailsController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateFControl(ArrayList<Control> coList) {
        int serverdbID = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (Control control : coList) {
                Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FCONTROL, null);

                ContentValues values = new ContentValues();
                values.put(FCONTROL_COM_NAME, control.getFCONTROL_COM_NAME());
                values.put(FCONTROL_COM_ADD1, control.getFCONTROL_COM_ADD1());
                values.put(FCONTROL_COM_ADD2, control.getFCONTROL_COM_ADD2());
                values.put(FCONTROL_COM_ADD3, control.getFCONTROL_COM_ADD3());
                values.put(FCONTROL_COM_TEL1, control.getFCONTROL_COM_TEL1());
                values.put(FCONTROL_COM_TEL2, control.getFCONTROL_COM_TEL2());
                values.put(FCONTROL_COM_FAX, control.getFCONTROL_COM_FAX());
                values.put(FCONTROL_COM_EMAIL, control.getFCONTROL_COM_EMAIL());
                values.put(FCONTROL_COM_WEB, control.getFCONTROL_COM_WEB());
                values.put(FCONTROL_FYEAR, control.getFCONTROL_FYEAR());
                values.put(FCONTROL_TYEAR, control.getFCONTROL_TYEAR());
                values.put(FCONTROL_COM_REGNO, control.getFCONTROL_COM_REGNO());
                values.put(FCONTROL_FTXN, control.getFCONTROL_FTXN());
                values.put(FCONTROL_TTXN, control.getFCONTROL_TTXN());
                values.put(FCONTROL_CRYSTALPATH, control.getFCONTROL_CRYSTALPATH());
                values.put(FCONTROL_VATCMTAXNO, control.getFCONTROL_VATCMTAXNO());
                values.put(FCONTROL_NBTCMTAXNO, control.getFCONTROL_NBTCMTAXNO());
                values.put(FCONTROL_SYSTYPE, control.getFCONTROL_SYSTYPE());
                values.put(DEALCODE, control.getFCONTROL_DEALCODE());
                values.put(FCONTROL_BASECUR, control.getFCONTROL_BASECUR());
                values.put(FCONTROL_BALGCRLM, control.getFCONTROL_BALGCRLM());
                values.put(FCONTROL_CONAGE1, control.getFCONTROL_CONAGE1());
                values.put(FCONTROL_CONAGE2, control.getFCONTROL_CONAGE2());
                values.put(FCONTROL_CONAGE3, control.getFCONTROL_CONAGE3());
                values.put(FCONTROL_CONAGE4, control.getFCONTROL_CONAGE4());
                values.put(FCONTROL_CONAGE5, control.getFCONTROL_CONAGE5());
                values.put(FCONTROL_CONAGE6, control.getFCONTROL_CONAGE6());
                values.put(FCONTROL_CONAGE7, control.getFCONTROL_CONAGE7());
                values.put(FCONTROL_CONAGE8, control.getFCONTROL_CONAGE8());
                values.put(FCONTROL_CONAGE9, control.getFCONTROL_CONAGE9());
                values.put(FCONTROL_CONAGE10, control.getFCONTROL_CONAGE10());
                values.put(FCONTROL_CONAGE11, control.getFCONTROL_CONAGE11());
                values.put(FCONTROL_CONAGE12, control.getFCONTROL_CONAGE12());
                values.put(FCONTROL_CONAGE13, control.getFCONTROL_CONAGE13());
            //    values.pur.FCONTROL_COMDISPER, control.getFCONTROL_COMDISPER());
                values.put(FCONTROL_CONAGE14, control.getFCONTROL_CONAGE14());

                if (cursor.getCount() > 0) {
                    dB.update(TABLE_FCONTROL, values, null, new String[]{});
                    Log.v(TAG, " Updated");
                } else {
                    serverdbID = (int) dB.insert(TABLE_FCONTROL, null, values);
                    Log.v(TAG, " Inserted " + serverdbID);
                }

                cursor.close();
            }

        } catch (Exception e) {
            Log.v(TAG, e.toString());
        } finally {
            dB.close();
        }
        return serverdbID;

    }

	/*-*-*-*-*-**-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<Control> getAllControl() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Control> list = new ArrayList<Control>();

        String selectQuery = "select * from " + TABLE_FCONTROL;

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                Control aControl = new Control();

                aControl.setFCONTROL_COM_NAME(cursor.getString(cursor.getColumnIndex(FCONTROL_COM_NAME)));
                aControl.setFCONTROL_COM_ADD1(cursor.getString(cursor.getColumnIndex(FCONTROL_COM_ADD1)));
                aControl.setFCONTROL_COM_ADD2(cursor.getString(cursor.getColumnIndex(FCONTROL_COM_ADD2)));
                aControl.setFCONTROL_COM_ADD3(cursor.getString(cursor.getColumnIndex(FCONTROL_COM_ADD3)));
                aControl.setFCONTROL_COM_TEL1(cursor.getString(cursor.getColumnIndex(FCONTROL_COM_TEL1)));
                aControl.setFCONTROL_COM_TEL2(cursor.getString(cursor.getColumnIndex(FCONTROL_COM_TEL2)));
                aControl.setFCONTROL_COM_FAX(cursor.getString(cursor.getColumnIndex(FCONTROL_COM_FAX)));
                aControl.setFCONTROL_COM_EMAIL(cursor.getString(cursor.getColumnIndex(FCONTROL_COM_EMAIL)));
                aControl.setFCONTROL_COM_WEB(cursor.getString(cursor.getColumnIndex(FCONTROL_COM_WEB)));
                aControl.setFCONTROL_DEALCODE(cursor.getString(cursor.getColumnIndex(DEALCODE)));
                aControl.setFCONTROL_VATCMTAXNO(cursor.getString(cursor.getColumnIndex(FCONTROL_VATCMTAXNO)));

                list.add(aControl);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();

        }

        return list;
    }

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public int getSysType() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + TABLE_FCONTROL;

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getInt(cursor.getColumnIndex(FCONTROL_SYSTYPE));

        }
        cursor.close();
        dB.close();

        return 0;

    }


//    public double getCompanyDisc() {
//
//        if (dB == null) {
//            open();
//        } else if (!dB.isOpen()) {
//            open();
//        }
//
//        String selectQuery = "SELECT comdisper FROM " + TABLE_FCONTROL;
//
//        Cursor cursor = null;
//        cursor = dB.rawQuery(selectQuery, null);
//
//        while (cursor.moveToNext()) {
//
//            return cursor.getDouble(cursor.getColumnIndex(FCONTROL_COMDISPER));
//
//        }
//        cursor.close();
//        dB.close();
//
//        return 0;
//
//    }
//--------------------------------getFlag status--------------------------------------------------------------

    public int getCurrentStatus() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT " + FCONTROL_CONAGE1 + " FROM " + TABLE_FCONTROL;

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getInt(cursor.getColumnIndex(FCONTROL_CONAGE1));

            }
        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            cursor.close();
            dB.close();

        }

        return 0;
    }

}
