package com.datamation.hmdsfa.controller;

//********kaveesha - 12-06-2020

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.model.VanStock;

import java.util.ArrayList;

public class VanStockController {

    private SQLiteDatabase dB;
    private DatabaseHelper DbHelper;
    Context context;
    private String TAG = "VanStockController";

    //table
    public static final String TABLE_FVANSTOCK = "fVanStock";

    //table attributes
    public static final String FVAN_STOCK_ID = "fVanStock_id";
    public static final String FVAN_BARCODE = "Barcode";
    public static final String FVAN_ITEM_NO = "Item_No";
    public static final String FVAN_QUANTITY_ISSUED = "Quantity_Issued";
    public static final String FVAN_SALESPERSON_CODE = "Salesperson_Code";
    public static final String FVAN_TO_LOCATION_CODE = "To_Location_Code";
    public static final String FVAN_VARIANT_CODE = "Variant_Code";

    // create String
    public static final String CREATE_TABLE_FVANSTOCK = "CREATE  TABLE IF NOT EXISTS " + TABLE_FVANSTOCK + " (" + FVAN_STOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FVAN_BARCODE + " TEXT, " + FVAN_ITEM_NO + " TEXT, " + FVAN_QUANTITY_ISSUED + " TEXT, "
            + FVAN_SALESPERSON_CODE + " TEXT, " + FVAN_TO_LOCATION_CODE + " TEXT, "  + FVAN_VARIANT_CODE + " TEXT); ";

    public VanStockController(Context context)
    {
        this.context = context;
        DbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException
    {
        dB = DbHelper.getWritableDatabase();
    }

    public void InsertOrReplaceVanStock(ArrayList<VanStock> list)
    {
        Log.d("InsertOrReplaceVanStock", "" + list.size());

        if(dB == null)
        {
            open();
        }
        else if(!dB.isOpen())
        {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_FVANSTOCK + " (Barcode,Item_No,Quantity_Issued,Salesperson_Code,To_Location_Code,Variant_Code) " + " VALUES (?,?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for(VanStock vanStock : list)
            {
                stmt.bindString(1,vanStock.getBarcode());
                stmt.bindString(2,vanStock.getItem_No());
                stmt.bindString(3,vanStock.getQuantity_Issued());
                stmt.bindString(4,vanStock.getSalesperson_Code());
                stmt.bindString(5,vanStock.getTo_Location_Code());
                stmt.bindString(6,vanStock.getVariant_Code());

                stmt.execute();
                stmt.clearBindings();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
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
            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FVANSTOCK, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FVANSTOCK, null, null);
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
    public String getQOH(String LocCode,String barcode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "select * from fVanStock where Barcode = '"+barcode+"' and To_Location_Code = '" + LocCode + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {
                return cursor.getString(cursor.getColumnIndex("Quantity_Issued"));
            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }
        return "0";
    }

}
