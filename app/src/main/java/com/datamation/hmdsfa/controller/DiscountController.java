package com.datamation.hmdsfa.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.model.Discount;
import com.datamation.hmdsfa.model.SalesPrice;

import java.util.ArrayList;

public class DiscountController {

    private SQLiteDatabase dB;
    private DatabaseHelper DbHelper;
    Context context;
    private String TAG = "DiscountController";

    // table
    public static final String TABLE_DISCOUNT  = "discount";
    // table attributes
    public static final String  DISCOUNT_DEBCODE = "DebCode";
    public static final String  DISCOUNT_DEBNAME= "DebName";
    public static final String  DISCOUNT_LOCCODE = "LocCode";
    public static final String  DISCOUNT_PRODUCT_DIS = "ProductDis";
    public static final String  DISCOUNT_PRODUCT_GROUP= "ProductGroup";
    public static final String  DISCOUNT_REPCODE = "RepCode";

    // create String
    public static final String CREATE_TABLE_DISCOUNT = "CREATE  TABLE IF NOT EXISTS " + TABLE_DISCOUNT + " ("

            + DISCOUNT_REPCODE + " TEXT, " + DISCOUNT_PRODUCT_GROUP + " TEXT, "      + DISCOUNT_DEBCODE + " TEXT, " + DISCOUNT_DEBNAME + " TEXT, " + DISCOUNT_LOCCODE + " TEXT, " + DISCOUNT_PRODUCT_DIS + " TEXT); ";



    public DiscountController(Context context) {
        this.context = context;
        DbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException
    {
        dB = DbHelper.getWritableDatabase();
    }

    public void InsertOrReplaceDiscount(ArrayList<Discount> list) {
        Log.d("InsertOrReplaceSalesPri", "" + list.size());
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_DISCOUNT + " (DebCode,DebName,LocCode,ProductDis,ProductGroup,RepCode) " + " VALUES (?,?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (Discount discount : list) {
                stmt.bindString(1, discount.getDebCode());
                stmt.bindString(2, discount.getDebName());
                stmt.bindString(3, discount.getLocCode());
                stmt.bindString(4, discount.getProductDis());
                stmt.bindString(5, discount.getProductGroup());
                stmt.bindString(6, discount.getRepCode());



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

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_DISCOUNT, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_DISCOUNT, null, null);
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
