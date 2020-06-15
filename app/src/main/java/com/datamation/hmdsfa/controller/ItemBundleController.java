package com.datamation.hmdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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
    private String TAG_1 = "BarCodeVarientController";

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
    public static final String ArticleNo = "ArticleNo";
    public static final String CREATE_ITEMBUNDLE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ITEMBUNDLE + " (" + Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Barcode + " TEXT, " +
            DocumentNo + " TEXT, " +
            ItemNo + " TEXT, " +
            VariantCode + " TEXT, " +
            VariantColour + " TEXT, " +
            VariantSize + " TEXT, " +
            Description + " TEXT, " +
            ArticleNo + " TEXT, " +
            Quantity + " TEXT ); ";

    //table - kaveesha - 12-06-2020
    public static final String TABLE_BAR_CODE_VARIENT = "BarCodeVarient";

    //table attributes
    public static final String BARCODE_ID = "Id";
    public static final String BARCODE_NO = "Barcode_No";
    public static final String DOCUMENT_NO = "Description";
    public static final String ITEM_NO = "Item_No";
    public static final String VARIANT_SIZE = "Size";
    public static final String VARIANT_CODE = "Variant_Code";

    public static final String  CREATE_TABLE_BAR_CODE_VARIENT = "CREATE TABLE IF NOT EXISTS " + TABLE_BAR_CODE_VARIENT + " (" + BARCODE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BARCODE_NO + " TEXT, " + DOCUMENT_NO + " TEXT, " + ITEM_NO + " TEXT, " +
            VARIANT_SIZE + " TEXT, "+ VARIANT_CODE + " TEXT ); ";

    public ItemBundleController(Context context) {
        this.context = context;
        dbeHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbeHelper.getWritableDatabase();
    }

//    public int createOrUpdateItemBundle(ArrayList<ItemBundle> list) {
//
//        int count = 0;
//
//        if (dB == null) {
//            open();
//        } else if (!dB.isOpen()) {
//            open();
//        }
//        Cursor cursor = null;
//
//        try {
//
//            for (ItemBundle itemBndl : list) {
//
//                ContentValues values = new ContentValues();
//
////                String selectQuery = "SELECT * FROM " + TABLE_ITEMBUNDLE + " WHERE " + FTAX_TAXCODE + " = '" + tax.getTAXCODE() + "'";
////
////                cursor = dB.rawQuery(selectQuery, null);
//
//                values.put(Barcode, itemBndl.getBarcode());
//                values.put(DocumentNo, itemBndl.getDocumentNo());
//                values.put(ItemNo, itemBndl.getItemNo());
//                values.put(VariantCode, itemBndl.getVariantCode());
//                values.put(VariantColour, itemBndl.getVariantColour());
//                values.put(VariantSize, itemBndl.getVariantSize());
//                values.put(Quantity, itemBndl.getQuantity());
//                values.put(Description, itemBndl.getDescription());
//                values.put(ArticleNo, itemBndl.getArticleNo());
//
////                int cn = cursor.getCount();
////                if (cn > 0)
//               //     count = dB.update(TABLE_FTAX, values, FTAX_TAXCODE + " =?", new String[]{String.valueOf(tax.getTAXCODE())});
//               // else
//                    count = (int) dB.insert(TABLE_ITEMBUNDLE, null, values);
//
//            }
//        } catch (Exception e) {
//
//            Log.v(TAG + " Exception", e.toString());
//
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            dB.close();
//        }
//        return count;
//
//    }
    public void InsertOrReplaceItemBundle(ArrayList<ItemBundle> list) {
        deleteAll();
        //Log.d(">>InsrtOrRepItemBundle", ">>" + list.size());
        Log.d(">>itemBundle", ">>InsrtOrRepItemBundle" + list.size());
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_ITEMBUNDLE + " (Barcode,DocumentNo,ItemNo,VariantCode,VariantColour,VariantSize,Quantity,Description,ArticleNo) " + " VALUES (?,?,?,?,?,?,?,?,?)";
//            String sql = "INSERT OR REPLACE INTO " + DatabaseHelper.TABLE_FDEBTOR + " (DebCode,DebName,DebAdd1,DebAdd2,DebAdd3,DebTele,DebMob,DebEMail,TownCode,AreaCode,DbGrCode,Status,CrdPeriod,ChkCrdPrd,CrdLimit,ChkCrdLmt,RepCode,PrillCode,TaxReg,RankCode,Latitude,Longitude) " + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Log.d(">>itembundle", "sql" + sql);
            SQLiteStatement stmt = dB.compileStatement(sql);

            for (ItemBundle itemBndl : list) {
                //Log.d(">>check item",">>"+itemBndl.toString());
               // Log.d(">>itembundle",">>"+itemBndl.toString());
                stmt.bindString(1, itemBndl.getBarcode());
               // Log.d(">>itembundle","itembundle_Barcode"+itemBndl.getBarcode());
                stmt.bindString(2, itemBndl.getDocumentNo());
             //   Log.d(">>itembundle","itembundle_Document"+itemBndl.getDocumentNo());
                stmt.bindString(3, itemBndl.getItemNo());
             //   Log.d(">>itembundle","itembundle_ItemNo"+itemBndl.getItemNo());
                stmt.bindString(4, itemBndl.getVariantCode());
            //    Log.d(">>itembundle","itembundle"+itemBndl.getVariantCode());
                stmt.bindString(5, itemBndl.getVariantColour());
           //     Log.d(">>itembundle","itembundle_Vari_colour"+itemBndl.getVariantColour());
                stmt.bindString(6, itemBndl.getVariantSize());
            //   Log.d(">>itembundle","itembundle_size"+itemBndl.getVariantSize());
                stmt.bindLong(7,   itemBndl.getQuantity());
            //    Log.d(">>itembundle","itembundle_quanity"+itemBndl.getQuantity());
                stmt.bindString(8, itemBndl.getDescription());
            //   Log.d(">>itembundle","itembundle_description"+itemBndl.getDescription());
                stmt.bindString(9, itemBndl.getArticleNo());
            //    Log.d(">>itembundle","itembundle_Artical"+itemBndl.getArticleNo());


                stmt.execute();
                stmt.clearBindings();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(">>itembundle", "error in fragment :" + e.toString());
        } finally {
            dB.setTransactionSuccessful();
            dB.endTransaction();
            dB.close();
        }

    }

    public void InsertOrReplaceBarcodeVariant(ArrayList<ItemBundle> list) {

        deleteAll();
        Log.d(">>InsrtOrRepBarcodeVari", ">>" + list.size());

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_BAR_CODE_VARIENT + " (Barcode_No,Description,Item_No,Size,Variant_Code) " + " VALUES (?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (ItemBundle barcodevariant : list) {

                Log.d(">>check item",">>"+barcodevariant.toString());
                stmt.bindString(1, barcodevariant.getBarcode());
                stmt.bindString(2, barcodevariant.getDescription());
                stmt.bindString(3, barcodevariant.getItemNo());
                stmt.bindString(4, barcodevariant.getVariantSize());
                stmt.bindString(5, barcodevariant.getVariantCode());

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

    public ItemBundle getItem(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }


        ItemBundle items=new ItemBundle();
        //  String selectQuery = "SELECT *  FROM fItem WHERE ItemCode LIKE '%"+itemCode+"%'";
        String selectQuery =  "SELECT * FROM ItemBundle WHERE  ItemNo = '" + itemCode + "' ";


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
            items.setArticleNo(cursor.getString(cursor.getColumnIndex(ArticleNo)));

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
            items.setArticleNo(cursor.getString(cursor.getColumnIndex(ArticleNo)));


            list.add(items);

        }

        return list;
    }
    public ArrayList<ItemBundle> getItemWiseScanDetails(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<ItemBundle> list = new ArrayList<ItemBundle>();

        //  String selectQuery = "SELECT *  FROM fItem WHERE ItemCode LIKE '%"+itemCode+"%'";
        String selectQuery =  "SELECT * FROM ItemBundle WHERE  Barcode = '" + itemCode + "' ";


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
            items.setArticleNo(cursor.getString(cursor.getColumnIndex(ArticleNo)));


            list.add(items);

        }

        return list;
    }
    public String getItemNameByCode(String code) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM ItemBundle WHERE ItemNo ='" + code + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(Description));

            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return "";
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

    public int deleteAll_BarcodeVariant() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_BAR_CODE_VARIENT, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_BAR_CODE_VARIENT, null, null);
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
