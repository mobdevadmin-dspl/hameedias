package com.datamation.hmdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.model.FInvRDet;
import com.datamation.hmdsfa.model.InvDet;
import com.datamation.hmdsfa.model.ItemLoc;
import com.datamation.hmdsfa.model.MainStock;
import com.datamation.hmdsfa.model.OrderDetail;

import java.util.ArrayList;

/*
    create by kaveesha - 15-06-2021
 */

public class MainStockController
{
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "hmdsfa";

   // table
    public static final String TABLE_FMAINSTOCK = "fMainStock";
    // table attributes
    public static final String FMAINSTOCK_ID = "fMainStock_id";
    public static final String FMAINSTOCK_BARCODE = "Barcode";
    public static final String FMAINSTOCK_ITEM_CODE = "ItemCode";
    public static final String FMAINSTOCK_ITEM_NAME = "ItemName";
    public static final String FMAINSTOCK_QUANTITY = "Quantity";
    public static final String FMAINSTOCK_VARIANTCODE = "VariantCode";

    // create String
    public static final String CREATE_FMAINSTOCK_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FMAINSTOCK +
            " (" + FMAINSTOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FMAINSTOCK_BARCODE + " TEXT, "
            + FMAINSTOCK_ITEM_CODE+ " TEXT, " + FMAINSTOCK_ITEM_NAME+ " TEXT, " + FMAINSTOCK_QUANTITY + " TEXT, " + FMAINSTOCK_VARIANTCODE+ " TEXT); ";


    public MainStockController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public void InsertOrReplaceMainStock(ArrayList<MainStock> list) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();

            String sql = "INSERT OR REPLACE INTO " + TABLE_FMAINSTOCK + " (Barcode,ItemCode,ItemName,Quantity,VariantCode) VALUES (?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (MainStock mainStock : list) {

                stmt.bindString(1, mainStock.getFMAINSTCOK_BARCODE());
                stmt.bindString(2, mainStock.getFMAINSTCOK_ITEM_CODE());
                stmt.bindString(3, mainStock.getFMAINSTCOK_ITEM_NAME());
                stmt.bindString(4, mainStock.getFMAINSTCOK_QUANTITY());
                stmt.bindString(5, mainStock.getFMAINSTCOK_VARIANT_CODE());

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

    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FMAINSTOCK, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FMAINSTOCK, null, null);
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

    public ArrayList<MainStock> getMainStockDetail() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<MainStock> list = new ArrayList<>();
        String selectQuery;

        selectQuery = "SELECT *  FROM " +TABLE_FMAINSTOCK;

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                MainStock mainStock = new MainStock();

                mainStock.setFMAINSTCOK_BARCODE(cursor.getString(cursor.getColumnIndex(FMAINSTOCK_BARCODE)));
                mainStock.setFMAINSTCOK_VARIANT_CODE(cursor.getString(cursor.getColumnIndex(FMAINSTOCK_VARIANTCODE)));
                mainStock.setFMAINSTCOK_QUANTITY(cursor.getString(cursor.getColumnIndex(FMAINSTOCK_QUANTITY)));

                list.add(mainStock);
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




}
