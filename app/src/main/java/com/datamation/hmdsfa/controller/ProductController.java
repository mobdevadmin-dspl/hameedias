package com.datamation.hmdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.model.InvDet;
import com.datamation.hmdsfa.model.ItemBundle;
import com.datamation.hmdsfa.model.Product;

import java.util.ArrayList;

import static com.datamation.hmdsfa.controller.SalesPriceController.FSALES_PRI_UNITPRICE;
import static com.datamation.hmdsfa.controller.SalesPriceController.TABLE_FSALESPRICE;

/**
 * rashmi
 */

public class ProductController {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;

    public ProductController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }
    // rashmi - 2019-12-18 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FPRODUCT = "fProducts";
    public static final String FPRODUCT_ID = "id";
    public static final String FPRODUCT_ITEMCODE = "itemcode";
    public static final String FPRODUCT_ITEMNAME = "itemname";
    public static final String FPRODUCT_Barcode = "Barcode";
    public static final String FPRODUCT_DocumentNo = "DocumentNo";
    public static final String FPRODUCT_VariantCode = "VariantCode";
    public static final String FPRODUCT_VariantColour = "VariantColour";
    public static final String FPRODUCT_VariantSize = "VariantSize";
    public static final String FPRODUCT_Quantity = "Quantity";
    public static final String FPRODUCT_Price = "Price";
    public static final String FPRODUCT_IsScan = "IsScan";
    public static final String FPRODUCT_QOH = "QOH";



    public static final String CREATE_FPRODUCT_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FPRODUCT + " ("
            + FPRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FPRODUCT_ITEMCODE + " TEXT, "
            + FPRODUCT_ITEMNAME + " TEXT, "
            + FPRODUCT_Barcode + " TEXT, "
            + FPRODUCT_DocumentNo + " TEXT, "
            + FPRODUCT_VariantCode + " TEXT, "
            + FPRODUCT_VariantColour + " TEXT, "
            + FPRODUCT_VariantSize + " TEXT, "
            + FPRODUCT_Quantity + " TEXT, "
            + FPRODUCT_Price + " TEXT, "
            + FPRODUCT_QOH+ " TEXT, "
            + FPRODUCT_IsScan + " TEXT); ";

    public void insertOrUpdateProducts(ArrayList<Product> list) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_FPRODUCT + " (itemcode,itemname,Barcode,DocumentNo,VariantCode,VariantColour,VariantSize,Quantity,Price,QOH,IsScan) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (Product items : list) {

                stmt.bindString(1, items.getFPRODUCT_ITEMCODE());
                stmt.bindString(2, items.getFPRODUCT_ITEMNAME());
                stmt.bindString(3, items.getFPRODUCT_Barcode());
                stmt.bindString(4, items.getFPRODUCT_DocumentNo());
                stmt.bindString(5, items.getFPRODUCT_VariantCode());
                stmt.bindString(6, items.getFPRODUCT_VariantColour());
                stmt.bindString(7, items.getFPRODUCT_VariantSize());
                stmt.bindString(8, items.getFPRODUCT_QTY());
                stmt.bindString(9, items.getFPRODUCT_Price());
                stmt.bindString(10, items.getFPRODUCT_QOH());
                stmt.bindString(11, "0");

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


    public boolean tableHasRecords() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        boolean result = false;
        Cursor cursor = null;

        try {
            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FPRODUCT, null);

            if (cursor.getCount() > 0)
                result = true;
            else
                result = false;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            dB.close();

        }

        return result;

    }

    public ArrayList<Product> getAllItems(String newText) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<Product> list = new ArrayList<>();
        try {
            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FPRODUCT + " WHERE itemcode || itemname LIKE '%" + newText + "%'", null);

            while (cursor.moveToNext()) {
                Product product = new Product();
                product.setFPRODUCT_ID(cursor.getString(cursor.getColumnIndex(FPRODUCT_ID)));
                product.setFPRODUCT_ITEMCODE(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMCODE)));
                product.setFPRODUCT_ITEMNAME(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMNAME)));
                product.setFPRODUCT_Barcode(cursor.getString(cursor.getColumnIndex(FPRODUCT_Barcode)));
                product.setFPRODUCT_DocumentNo(cursor.getString(cursor.getColumnIndex(FPRODUCT_DocumentNo)));
                product.setFPRODUCT_VariantCode(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantCode)));
                product.setFPRODUCT_VariantColour(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantColour)));
                product.setFPRODUCT_QTY(cursor.getString(cursor.getColumnIndex(FPRODUCT_Quantity)));
                product.setFPRODUCT_Price(cursor.getString(cursor.getColumnIndex(FPRODUCT_Price)));
                product.setFPRODUCT_QOH(cursor.getString(cursor.getColumnIndex(FPRODUCT_QOH)));
                product.setFPRODUCT_IsScan(cursor.getString(cursor.getColumnIndex(FPRODUCT_IsScan)));

                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            dB.close();
        }

        return list;
    }
    //2018-10-26 create by rashmi -because mega has inner return detail in invoice
    public ArrayList<Product> getAllItems(String newText,String txntype) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<Product> list = new ArrayList<>();
        try {
          //  cursor = dB.rawQuery("SELECT * FROM " +TABLE_FPRODUCT + " WHERE itemcode || itemname LIKE '%" + newText + "%' and TxnType = '"+txntype+"' ORDER BY QOH DESC", null);
            cursor = dB.rawQuery("SELECT * FROM " +TABLE_FPRODUCT + " WHERE itemcode || itemname LIKE '%" + newText + "%'", null);

            while (cursor.moveToNext()) {
                Product product = new Product();
                product.setFPRODUCT_ID(cursor.getString(cursor.getColumnIndex(FPRODUCT_ID)));
                product.setFPRODUCT_ITEMCODE(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMCODE)));
                product.setFPRODUCT_ITEMNAME(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMNAME)));
                product.setFPRODUCT_DocumentNo(cursor.getString(cursor.getColumnIndex(FPRODUCT_DocumentNo)));
                product.setFPRODUCT_Barcode(cursor.getString(cursor.getColumnIndex(FPRODUCT_Barcode)));
                product.setFPRODUCT_VariantCode(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantCode)));
                product.setFPRODUCT_VariantColour(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantColour)));
                product.setFPRODUCT_VariantSize(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantSize)));
                product.setFPRODUCT_QTY(cursor.getString(cursor.getColumnIndex(FPRODUCT_Quantity)));
                product.setFPRODUCT_Price(cursor.getString(cursor.getColumnIndex(FPRODUCT_Price)));
                product.setFPRODUCT_QOH(cursor.getString(cursor.getColumnIndex(FPRODUCT_QOH)));
                product.setFPRODUCT_IsScan(cursor.getString(cursor.getColumnIndex(FPRODUCT_IsScan)));


                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            dB.close();
        }

        return list;
    }


    public void updateBarCode(String itemCode, String isScan) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(FPRODUCT_IsScan, isScan);
            dB.update(TABLE_FPRODUCT, values,FPRODUCT_Barcode + " =?", new String[]{String.valueOf(itemCode)});

        } catch (Exception e) {
            Log.v(" Exception", e.toString());
        } finally {
            dB.close();
        }
    }
    public void updateBarCodeInDelete(String itemCode, String isScan) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(FPRODUCT_IsScan, isScan);
            dB.update(TABLE_FPRODUCT, values,FPRODUCT_Barcode + " =?", new String[]{String.valueOf(itemCode)});

        } catch (Exception e) {
            Log.v(" Exception", e.toString());
        } finally {
            dB.close();
        }
    }



    public ArrayList<Product> getSelectedItems() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<Product> list = new ArrayList<>();
        try {
            cursor = dB.rawQuery("SELECT * FROM " +TABLE_FPRODUCT + " WHERE  qty<>'0'", null);

            while (cursor.moveToNext()) {
                Product product = new Product();
                product.setFPRODUCT_ID(cursor.getString(cursor.getColumnIndex(FPRODUCT_ID)));
                product.setFPRODUCT_ITEMCODE(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMCODE)));
                product.setFPRODUCT_ITEMNAME(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMNAME)));
                product.setFPRODUCT_Barcode(cursor.getString(cursor.getColumnIndex(FPRODUCT_Barcode)));
                product.setFPRODUCT_DocumentNo(cursor.getString(cursor.getColumnIndex(FPRODUCT_DocumentNo)));
                product.setFPRODUCT_VariantCode(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantCode)));
                product.setFPRODUCT_VariantColour(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantColour)));
                product.setFPRODUCT_VariantSize(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantSize)));
                product.setFPRODUCT_QTY(cursor.getString(cursor.getColumnIndex(FPRODUCT_Quantity)));
                product.setFPRODUCT_Price(cursor.getString(cursor.getColumnIndex(FPRODUCT_Price)));
                product.setFPRODUCT_QOH(cursor.getString(cursor.getColumnIndex(FPRODUCT_QOH)));
                product.setFPRODUCT_IsScan(cursor.getString(cursor.getColumnIndex(FPRODUCT_IsScan)));

                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            dB.close();
        }

        return list;
    }
    public ArrayList<ItemBundle> getScannedItems() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<ItemBundle> list = new ArrayList<ItemBundle>();
        try {
            cursor = dB.rawQuery("SELECT * FROM " +TABLE_FPRODUCT + " WHERE  IsScan<>'0'", null);

            while (cursor.moveToNext()) {
                ItemBundle items=new ItemBundle();

                items.setBarcode(cursor.getString(cursor.getColumnIndex(FPRODUCT_Barcode)));
                items.setDocumentNo(cursor.getString(cursor.getColumnIndex(FPRODUCT_DocumentNo)));
                items.setItemNo(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMCODE)));
                items.setVariantCode(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantCode)));
                items.setVariantColour(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantColour)));
                items.setVariantSize(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantSize)));
                items.setQuantity(cursor.getInt(cursor.getColumnIndex(FPRODUCT_Quantity)));
                items.setDescription(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMNAME)));

                list.add(items);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            dB.close();
        }

        return list;
    }
    public ArrayList<Product> getScannedtems(String txntype) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<Product> list = new ArrayList<>();
        try {
            cursor = dB.rawQuery("SELECT * FROM " +TABLE_FPRODUCT + " WHERE  IsScan<>'0' ", null);

            while (cursor.moveToNext()) {
                Product product = new Product();
                product.setFPRODUCT_ID(cursor.getString(cursor.getColumnIndex(FPRODUCT_ID)));
                product.setFPRODUCT_ITEMCODE(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMCODE)));
                product.setFPRODUCT_ITEMNAME(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMNAME)));
                product.setFPRODUCT_Barcode(cursor.getString(cursor.getColumnIndex(FPRODUCT_Barcode)));
                product.setFPRODUCT_DocumentNo(cursor.getString(cursor.getColumnIndex(FPRODUCT_DocumentNo)));
                product.setFPRODUCT_VariantCode(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantCode)));
                product.setFPRODUCT_VariantColour(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantColour)));
                product.setFPRODUCT_VariantSize(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantSize)));
                product.setFPRODUCT_QTY(cursor.getString(cursor.getColumnIndex(FPRODUCT_Quantity)));
//                product.setFPRODUCT_Price(cursor.getString(cursor.getColumnIndex(FPRODUCT_Price)));
//                product.setFPRODUCT_QOH(cursor.getString(cursor.getColumnIndex(FPRODUCT_QOH)));
                product.setFPRODUCT_IsScan(cursor.getString(cursor.getColumnIndex(FPRODUCT_IsScan)));

                list.add(product);


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            dB.close();
        }
Log.d(">>ScannedList",">>"+list.toString());
        return list;
    }

    //get scanned items for itemwise scan
    public ArrayList<Product> getScannedtems(ItemBundle itembundle) {


        ArrayList<Product> list = new ArrayList<>();
        try {
                Product product = new Product();
                String price = new SalesPriceController(context).getPrice(itembundle.getItemNo(),itembundle.getVariantCode());
             //   product.setFPRODUCT_ID(cursor.getString(cursor.getColumnIndex(FPRODUCT_ID)));
                product.setFPRODUCT_ITEMCODE(itembundle.getItemNo());
                product.setFPRODUCT_ITEMNAME(itembundle.getDescription());
                product.setFPRODUCT_Barcode(itembundle.getBarcode());
                product.setFPRODUCT_DocumentNo(itembundle.getDocumentNo());
                product.setFPRODUCT_VariantCode(itembundle.getVariantCode());
                product.setFPRODUCT_VariantColour(itembundle.getVariantColour());
                product.setFPRODUCT_VariantSize(itembundle.getVariantSize());
                product.setFPRODUCT_QTY(String.valueOf(itembundle.getQuantity()));
                product.setFPRODUCT_Price(price);
//                product.setFPRODUCT_QOH(cursor.getString(cursor.getColumnIndex(FPRODUCT_QOH)));
                product.setFPRODUCT_ArticleNo(itembundle.getArticleNo());
                product.setFPRODUCT_IsScan("1");

                list.add(product);



        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(">>ScannedList",">>"+list.toString());
        return list;
    }
    //get scanned items for itembundle scan
    public ArrayList<Product> getBundleScannedtems(ArrayList<ItemBundle> itembundleList) {


        ArrayList<Product> list = new ArrayList<>();
        try {
            for(ItemBundle itembundle : itembundleList) {
                Product product = new Product();
                String price = new SalesPriceController(context).getPrice(itembundle.getItemNo(), itembundle.getVariantCode());
                //   product.setFPRODUCT_ID(cursor.getString(cursor.getColumnIndex(FPRODUCT_ID)));
                product.setFPRODUCT_ITEMCODE(itembundle.getItemNo());
                product.setFPRODUCT_ITEMNAME(itembundle.getDescription());
                product.setFPRODUCT_Barcode(itembundle.getBarcode());
                product.setFPRODUCT_DocumentNo(itembundle.getDocumentNo());
                product.setFPRODUCT_VariantCode(itembundle.getVariantCode());
                product.setFPRODUCT_VariantColour(itembundle.getVariantColour());
                product.setFPRODUCT_VariantSize(itembundle.getVariantSize());
                product.setFPRODUCT_QTY(String.valueOf(itembundle.getQuantity()));
                product.setFPRODUCT_Price(price);
                product.setFPRODUCT_ArticleNo(itembundle.getArticleNo());
//                product.setFPRODUCT_QOH(cursor.getString(cursor.getColumnIndex(FPRODUCT_QOH)));
                product.setFPRODUCT_IsScan("1");

                list.add(product);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(">>ScannedList",">>"+list.toString());
        return list;
    }
    public ArrayList<Product> getSelectedItems(String txntype) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<Product> list = new ArrayList<>();
        try {
            cursor = dB.rawQuery("SELECT * FROM " +TABLE_FPRODUCT + " WHERE  qty<>'0' and TxnType = '"+txntype+"' ", null);

            while (cursor.moveToNext()) {
                Product product = new Product();
                product.setFPRODUCT_ID(cursor.getString(cursor.getColumnIndex(FPRODUCT_ID)));
                product.setFPRODUCT_ITEMCODE(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMCODE)));
                product.setFPRODUCT_ITEMNAME(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMNAME)));
                product.setFPRODUCT_Barcode(cursor.getString(cursor.getColumnIndex(FPRODUCT_Barcode)));
                product.setFPRODUCT_DocumentNo(cursor.getString(cursor.getColumnIndex(FPRODUCT_DocumentNo)));
                product.setFPRODUCT_VariantCode(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantCode)));
                product.setFPRODUCT_VariantColour(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantColour)));
                product.setFPRODUCT_VariantSize(cursor.getString(cursor.getColumnIndex(FPRODUCT_VariantSize)));
                product.setFPRODUCT_QTY(cursor.getString(cursor.getColumnIndex(FPRODUCT_Quantity)));
                product.setFPRODUCT_Price(cursor.getString(cursor.getColumnIndex(FPRODUCT_Price)));
                product.setFPRODUCT_QOH(cursor.getString(cursor.getColumnIndex(FPRODUCT_QOH)));
                product.setFPRODUCT_IsScan(cursor.getString(cursor.getColumnIndex(FPRODUCT_IsScan)));

                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            dB.close();
        }

         return list;
    }



    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*/

    public void mClearTables() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {
            dB.delete(TABLE_FPRODUCT, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }
    public void updateProductQty(String itemCode, String qty) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(FPRODUCT_Quantity, qty);
            dB.update(TABLE_FPRODUCT, values,FPRODUCT_ITEMCODE + " =?", new String[]{String.valueOf(itemCode)});

        } catch (Exception e) {
            Log.v(" Exception", e.toString());
        } finally {
            dB.close();
        }
    }



    public int updateProductQtyfor(String itemCode, String qty) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(FPRODUCT_Quantity, qty);
            count=(int)dB.update(TABLE_FPRODUCT, values,FPRODUCT_ITEMCODE + " =?", new String[]{String.valueOf(itemCode)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
        return  count;
    }

    public void insertIntoProductAsBulk(String LocCode, String prillcode)
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try
        {
                String insertQuery2;
                insertQuery2 = "INSERT INTO fProducts (itemcode,itemname,Barcode,DocumentNo,VariantCode,VariantColour,VariantSize,Quantity,Price,QOH,IsScan)\n" +
                        "SELECT \n" +
                        "itm.ItemNo AS ItemCode , itm.Description AS ItemName ,  \n" +
                        "itm.Barcode AS Barcode , " +
                        "itm.DocumentNo AS DocumentNo , " +
                        "itm.VariantCode AS VariantCode , " +
                        "itm.VariantColour AS VariantColour , " +
                        "itm.VariantSize AS VariantSize , " +
                        "itm.Quantity AS Quantity , " +
                        " \"0.0\" AS Price , " +
                        " \"0.0\" AS QOH , " +
                        " \"0\" AS IsScan " +
                        "FROM ItemBundle itm\n";
//                        " \"0\" AS IsScan FROM ItemBundle itm\n" +
//                        "GROUP BY itm.ItemCode";

                dB.execSQL(insertQuery2);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(dB.isOpen())
            {
                dB.close();
            }
        }
    }



}
