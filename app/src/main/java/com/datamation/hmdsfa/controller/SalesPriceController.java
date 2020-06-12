package com.datamation.hmdsfa.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.model.SalesPrice;

import java.util.ArrayList;
//***********kaveesha - 01-06-2020
public class SalesPriceController {

    private SQLiteDatabase dB;
    private DatabaseHelper DbHelper;
    Context context;
    private String TAG = "SalesPriceController";

    // table
    public static final String TABLE_FSALESPRICE  = "fSalesPrice";
    // table attributes
    public static final String FSALES_PRI_ID = "fsalesPri_id";
    public static final String  FSALES_PRI_ALLOWLINEDIS= "AllowLineDis";
    public static final String  FSALES_PRI_ENDING_DATE = "EndingDate";
    public static final String  FSALES_PRI_ITEMNO = "ItemNo";
    public static final String  FSALES_PRI_MARKUP= "Markup";
    public static final String  FSALES_PRI_PRICEINCVAT = "PriceInclVat";
    public static final String  FSALES_PRI_PROFIT = "Profit";
    public static final String  FSALES_PRI_PROFITLCY = "ProfitLCY";
    public static final String  FSALES_PRI_SALESTYPE = "SalesType";
    public static final String  FSALES_PRI_STARTINGDATE = "StartingDate";
    public static final String  FSALES_PRI_UNITOFMEA = "UnitOfMea";
    public static final String  FSALES_PRI_UNITPRICE = "UnitPrice";
    public static final String  FSALES_PRI_UNITPRICEINCLVAT = "UnitPriceInclVat";
    public static final String  FSALES_PRI_VARIENTCODE = "VarientCode";

    // create String
    public static final String CREATE_TABLE_FSALESPRICE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FSALESPRICE + " (" + FSALES_PRI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FSALES_PRI_ALLOWLINEDIS + " TEXT, " + FSALES_PRI_ENDING_DATE + " TEXT, " + FSALES_PRI_ITEMNO + " TEXT, "
            + FSALES_PRI_MARKUP + " TEXT, " + FSALES_PRI_PRICEINCVAT + " TEXT, " + FSALES_PRI_PROFIT + " TEXT, "
            + FSALES_PRI_PROFITLCY + " TEXT, " + FSALES_PRI_SALESTYPE + " TEXT, " + FSALES_PRI_STARTINGDATE + " TEXT, "
            + FSALES_PRI_UNITOFMEA + " TEXT, " + FSALES_PRI_UNITPRICE + " TEXT, " + FSALES_PRI_UNITPRICEINCLVAT + " TEXT, " + FSALES_PRI_VARIENTCODE + " TEXT); ";



    public SalesPriceController(Context context) {
        this.context = context;
        DbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException
    {
        dB = DbHelper.getWritableDatabase();
    }

    public void InsertOrReplaceSalesPrice(ArrayList<SalesPrice> list) {
        Log.d("InsertOrReplaceSalesPri", "" + list.size());
        Log.d(">>>insert", ">>>insert" + list.size());
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_FSALESPRICE + " (AllowLineDis,EndingDate,ItemNo,Markup,PriceInclVat,Profit,ProfitLCY,SalesType,StartingDate,UnitOfMea,UnitPrice,UnitPriceInclVat,VarientCode) " + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Log.d(">>>", ">>>" + sql);
            SQLiteStatement stmt = dB.compileStatement(sql);

            for (SalesPrice salesprice : list) {

                stmt.bindString(1, salesprice.getAllowLineDis());
                //Log.d(">>>", ">>>" + salesprice.getAllowLineDis());
                stmt.bindString(2, salesprice.getEndingDate());
               // Log.d(">>>", ">>>" + salesprice.getEndingDate());
                stmt.bindString(3, salesprice.getItemNo());
               // Log.d(">>>", ">>>" + salesprice.getItemNo());
                stmt.bindString(4, salesprice.getMarkup());
              //  Log.d(">>>", ">>>" + salesprice.getMarkup());
                stmt.bindString(5, salesprice.getPriceInclVat());
               // Log.d(">>>", ">>>" + salesprice.getPriceInclVat());
                stmt.bindString(6, salesprice.getProfit());
             //   Log.d(">>>", ">>>" + salesprice.getProfit());
                stmt.bindString(7, salesprice.getProfitLCY());
             //   Log.d(">>>", ">>>" + salesprice.getProfitLCY());
                stmt.bindString(8, salesprice.getSalesType());
              //  Log.d(">>>", ">>>" + salesprice.getSalesType());
                stmt.bindString(9, salesprice.getStartingDate());
             //   Log.d(">>>", ">>>" + salesprice.getStartingDate());
                stmt.bindString(10, salesprice.getUnitOfMea());
             //   Log.d(">>>", ">>>" + salesprice.getUnitOfMea());
                stmt.bindString(11, salesprice.getUnitPrice());
              //  Log.d(">>>", ">>>" + salesprice.getUnitOfMea());
                stmt.bindString(12, salesprice.getUnitPriceInclVat());
              //  Log.d(">>>", ">>>" + salesprice.getUnitPriceInclVat());
                stmt.bindString(13, salesprice.getVarientCode());
               // Log.d(">>>", ">>>" + salesprice.getVarientCode());



                stmt.execute();
                stmt.clearBindings();
            }

        } catch (SQLException e) {
            Log.d(">>>", "error in fragment :" + e.toString());
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

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FSALESPRICE, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FSALESPRICE, null, null);
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
}
