package com.datamation.hmdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.model.ItemBundle;
import com.datamation.hmdsfa.model.Tax;

import java.util.ArrayList;

public class ItemBundleController {

/*rashmi - hameedias barcode scan modification - 2020-03-02*/
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbeHelper;
    private String TAG = "ItemBundleController";

    public static final String TABLE_ITEMBUNDLE = "ItemBundle";
    public static final String Id = "Id";
    public static final String Barcode = "Barcode";
    public static final String DocumentNo = "DocumentNo";
    public static final String ItemNo = "ItemNo";
    public static final String VariantCode = "VariantCode";
    public static final String VariantColour = "VariantColour";
    public static final String VariantSize = "VariantSize";
    public static final String Quantity = "Quantity";
    public static final String Description = "Description";
    public static final String CREATE_ITEMBUNDLE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ITEMBUNDLE + " (" + Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Barcode + " TEXT, " +
            DocumentNo + " TEXT, " +
            ItemNo + " TEXT, " +
            VariantCode + " TEXT, " +
            VariantColour + " TEXT, " +
            VariantSize + " TEXT, " +
            Description + " TEXT, " +
            Quantity + " TEXT ); ";

    public ItemBundleController(Context context) {
        this.context = context;
        dbeHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbeHelper.getWritableDatabase();
    }

    public int createOrUpdateItemBundle(ArrayList<ItemBundle> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (ItemBundle itemBndl : list) {

                ContentValues values = new ContentValues();

//                String selectQuery = "SELECT * FROM " + TABLE_ITEMBUNDLE + " WHERE " + FTAX_TAXCODE + " = '" + tax.getTAXCODE() + "'";
//
//                cursor = dB.rawQuery(selectQuery, null);

                values.put(Barcode, itemBndl.getBarcode());
                values.put(DocumentNo, itemBndl.getDocumentNo());
                values.put(ItemNo, itemBndl.getItemNo());
                values.put(VariantCode, itemBndl.getVariantCode());
                values.put(VariantColour, itemBndl.getVariantColour());
                values.put(VariantSize, itemBndl.getVariantSize());
                values.put(Quantity, itemBndl.getQuantity());
                values.put(Description, itemBndl.getDescription());

//                int cn = cursor.getCount();
//                if (cn > 0)
               //     count = dB.update(TABLE_FTAX, values, FTAX_TAXCODE + " =?", new String[]{String.valueOf(tax.getTAXCODE())});
               // else
                    count = (int) dB.insert(TABLE_ITEMBUNDLE, null, values);

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

    public ItemBundle getItem(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }


        ItemBundle items=new ItemBundle();
        //  String selectQuery = "SELECT *  FROM fItem WHERE ItemCode LIKE '%"+itemCode+"%'";
        String selectQuery =  "SELECT * FROM ItemBundle WHERE  Barcode = '" + itemCode + "' ";


        Cursor cursor = dB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){

            items.setBarcode(cursor.getString(cursor.getColumnIndex(Barcode)));
            items.setDocumentNo(cursor.getString(cursor.getColumnIndex(DocumentNo)));
            items.setItemNo(cursor.getString(cursor.getColumnIndex(ItemNo)));
            items.setVariantCode(cursor.getString(cursor.getColumnIndex(VariantCode)));
            items.setVariantColour(cursor.getString(cursor.getColumnIndex(VariantColour)));
            items.setVariantSize(cursor.getString(cursor.getColumnIndex(VariantSize)));
            items.setQuantity(cursor.getInt(cursor.getColumnIndex(Quantity)));
            items.setDescription(cursor.getString(cursor.getColumnIndex(Description)));

        }

        return items;
    }

    public ArrayList<ItemBundle> getItemsInBundle(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<ItemBundle> list = new ArrayList<ItemBundle>();

        //  String selectQuery = "SELECT *  FROM fItem WHERE ItemCode LIKE '%"+itemCode+"%'";
        String selectQuery =  "SELECT * FROM ItemBundle WHERE  DocumentNo = '" + itemCode + "' ";


        Cursor cursor = dB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){

            ItemBundle items=new ItemBundle();

            items.setBarcode(cursor.getString(cursor.getColumnIndex(Barcode)));
            items.setDocumentNo(cursor.getString(cursor.getColumnIndex(DocumentNo)));
            items.setItemNo(cursor.getString(cursor.getColumnIndex(ItemNo)));
            items.setVariantCode(cursor.getString(cursor.getColumnIndex(VariantCode)));
            items.setVariantColour(cursor.getString(cursor.getColumnIndex(VariantColour)));
            items.setVariantSize(cursor.getString(cursor.getColumnIndex(VariantSize)));
            items.setQuantity(cursor.getInt(cursor.getColumnIndex(Quantity)));
            items.setDescription(cursor.getString(cursor.getColumnIndex(Description)));


            list.add(items);

        }

        return list;
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

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_ITEMBUNDLE, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_ITEMBUNDLE, null, null);
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
