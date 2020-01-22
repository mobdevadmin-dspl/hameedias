package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.PreProduct;

import java.util.ArrayList;

public class PreProductController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    // Pre Product Table
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    public static final String TABLE_FPRODUCT_PRE = "fProducts_pre";
    public static final String FPRODUCT_ID_PRE = "id";
    public static final String FPRODUCT_ITEMCODE_PRE = "itemcode_pre";
    public static final String FPRODUCT_ITEMNAME_PRE = "itemname_pre";
    public static final String FPRODUCT_PRICE_PRE = "price_pre";
    public static final String FPRODUCT_QOH_PRE = "qoh_pre";
    public static final String FPRODUCT_QTY_PRE = "qty_pre";
    public static final String FPRODUCT_BAL_QTY = "BalQty";
    public static final String FPRODUCT_REACODE = "ReaCode";
    public static final String FPRODUCT_CASE_PRE = "case_pre";
    public static final String FPRODUCT_UNITS = "units";
    public static final String FPRODUCT_TYPE = "type";

    public static final String CREATE_FPRODUCT_PRE_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FPRODUCT_PRE + " ("
            + FPRODUCT_ID_PRE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FPRODUCT_ITEMCODE_PRE + " TEXT, "
            + FPRODUCT_ITEMNAME_PRE + " TEXT, "
            + FPRODUCT_PRICE_PRE + " TEXT, "
            + ProductController.FPRODUCT_CHANGED_PRICE + " TEXT, "
            + FPRODUCT_QOH_PRE + " TEXT, "
            + FPRODUCT_CASE_PRE + " TEXT, "
            + FPRODUCT_UNITS + " TEXT, "
            + FPRODUCT_REACODE + " TEXT, "
            + FPRODUCT_TYPE + " TEXT, "
            + FPRODUCT_QTY_PRE + " TEXT); ";
    public static final String INDEX_PRODUCTS = "CREATE UNIQUE INDEX IF NOT EXISTS ui_fProducts_pre ON fProducts_pre (itemcode_pre,itemname_pre,type);";

    public PreProductController(Context context) {
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public boolean tableHasRecords(String type) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        boolean result = false;
        Cursor cursor = null;

        try {
            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FPRODUCT_PRE+ " where type = '"+type+"'", null);
            Log.d("1017 - Table has "+type,">>>>>>"+cursor.getCount());
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

    public ArrayList<PreProduct> getAllItems(String newText, String type, String isAllowQohZero, String qohstatus) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<PreProduct> list = new ArrayList<>();
        try {
           // if(isAllowQohZero.equals("1")) {
                //cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FPRODUCT_PRE + " WHERE itemcode || itemname LIKE '%" + newText + "%' and TxnType = '"+txntype+"' ORDER BY QOH DESC", null);
               // cursor = dB.rawQuery("SELECT * FROM " + TABLE_FPRODUCT_PRE + " WHERE itemcode_pre || itemname_pre LIKE '%" + newText + "%' and type = '" + type + "' group by itemcode_pre order by CAST(qoh_pre AS FLOAT) desc", null);
                if(qohstatus.equals("0")){
                    cursor = dB.rawQuery("SELECT * FROM " + TABLE_FPRODUCT_PRE + " WHERE itemcode_pre || itemname_pre LIKE '%" + newText + "%' and type = '" + type + "'  " +
                            " group by itemcode_pre order by CAST(qoh_pre AS FLOAT) desc", null);

                }else{
                    cursor = dB.rawQuery("SELECT * FROM " + TABLE_FPRODUCT_PRE + " WHERE itemcode_pre || itemname_pre LIKE '%" + newText + "%' and type = '" + type + "' and CAST(qoh_pre AS FLOAT) > 0 group by itemcode_pre order by CAST(qoh_pre AS FLOAT) desc", null);

                }

       //     }
//            else{
//                if(qohstatus.equals("0")){
//                    cursor = dB.rawQuery("SELECT * FROM " + TABLE_FPRODUCT_PRE + " WHERE itemcode_pre || itemname_pre LIKE '%" + newText + "%' and type = '" + type + "'  group by itemcode_pre order by CAST(qoh_pre AS FLOAT) desc", null);
//
//                }else{
//                    cursor = dB.rawQuery("SELECT * FROM " + TABLE_FPRODUCT_PRE + " WHERE itemcode_pre || itemname_pre LIKE '%" + newText + "%' and type = '" + type + "' and CAST(qoh_pre AS FLOAT) > 0 group by itemcode_pre order by CAST(qoh_pre AS FLOAT) desc", null);
//
//                }
//
//            }
            while (cursor.moveToNext()) {
                PreProduct product = new PreProduct();
                product.setPREPRODUCT_ID(cursor.getString(cursor.getColumnIndex(FPRODUCT_ID_PRE)));
                product.setPREPRODUCT_ITEMCODE(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMCODE_PRE)));
                product.setPREPRODUCT_ITEMNAME(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMNAME_PRE)));
                product.setPREPRODUCT_PRICE(cursor.getString(cursor.getColumnIndex(FPRODUCT_PRICE_PRE)));
                product.setPREPRODUCT_QOH(cursor.getString(cursor.getColumnIndex(FPRODUCT_QOH_PRE)));
                product.setPREPRODUCT_QTY(cursor.getString(cursor.getColumnIndex(FPRODUCT_QTY_PRE)));
                product.setPREPRODUCT_CASE(cursor.getString(cursor.getColumnIndex(FPRODUCT_CASE_PRE)));
                product.setPREPRODUCT_REACODE(cursor.getString(cursor.getColumnIndex(FPRODUCT_REACODE)));
                product.setPREPRODUCT_UNIT(cursor.getString(cursor.getColumnIndex(FPRODUCT_UNITS)));
                product.setPREPRODUCT_TXN_TYPE(cursor.getString(cursor.getColumnIndex(FPRODUCT_TYPE)));
                product.setPREPRODUCT_CHANGED_PRICE(cursor.getString(cursor.getColumnIndex(ProductController.FPRODUCT_CHANGED_PRICE)));
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

    public void updateProductQty(String itemCode, String qty, String type) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(FPRODUCT_QTY_PRE, qty);
            dB.update(TABLE_FPRODUCT_PRE, values,  FPRODUCT_ITEMCODE_PRE
                    + " = '" + itemCode + "' and "+FPRODUCT_TYPE + " = '" + type + "' ", null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }
    public void updateReason(String itemCode, String reason, String type) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(FPRODUCT_REACODE, reason);
            dB.update(TABLE_FPRODUCT_PRE, values,  FPRODUCT_ITEMCODE_PRE
                    + " = '" + itemCode + "' and "+FPRODUCT_TYPE + " = '" + type + "' ", null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }
    public void updateProductCase(String itemCode, String cases, String type) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(FPRODUCT_CASE_PRE, cases);
            dB.update(TABLE_FPRODUCT_PRE, values,  FPRODUCT_ITEMCODE_PRE
                    + " = '" + itemCode + "' and "+FPRODUCT_TYPE + " = '" + type + "' ",null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }
    public int updateQuantities(String itemCode,String itemname, String price, String qoh, String qty, String cases, String refno, String units, String type) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + TABLE_FPRODUCT_PRE + " WHERE " + FPRODUCT_ITEMCODE_PRE
                    + " = '" + itemCode + "' and "+FPRODUCT_TYPE + " = '" + type + "' ";

            cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();
            values.put(FPRODUCT_ITEMCODE_PRE, itemCode);
            values.put(FPRODUCT_ITEMNAME_PRE, itemname);
            values.put(FPRODUCT_PRICE_PRE, price);
            values.put(FPRODUCT_QOH_PRE, qoh);
            values.put(FPRODUCT_QTY_PRE, qty);
            values.put(FPRODUCT_CASE_PRE, cases);
            values.put(DatabaseHelper.REFNO, refno);
            values.put(FPRODUCT_UNITS, units);
            values.put(FPRODUCT_TYPE, type);


            int cn = cursor.getCount();
            if (cn > 0) {
                count = dB.update(TABLE_FPRODUCT_PRE, values, FPRODUCT_ITEMCODE_PRE
                        + " = '" + itemCode + "' and "+FPRODUCT_TYPE + " = '" + type + "'",
                        null);
            } else {
                count = (int) dB.insert(TABLE_FPRODUCT_PRE, null, values);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
        return count;
    }
    public int updateReason(String itemCode,String itemname, String price, String qoh, String qty, String cases, String refno, String units, String type, String code) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + TABLE_FPRODUCT_PRE + " WHERE " + FPRODUCT_ITEMCODE_PRE
                    + " = '" + itemCode + "' and "+FPRODUCT_TYPE + " = '" + type + "' and "+FPRODUCT_REACODE + " = '" + code + "' ";

            cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();
            values.put(FPRODUCT_ITEMCODE_PRE, itemCode);
            values.put(FPRODUCT_ITEMNAME_PRE, itemname);
            values.put(FPRODUCT_PRICE_PRE, price);
            values.put(FPRODUCT_QOH_PRE, qoh);
            values.put(FPRODUCT_QTY_PRE, qty);
            values.put(FPRODUCT_CASE_PRE, cases);
            values.put(DatabaseHelper.REFNO, refno);
            values.put(FPRODUCT_UNITS, units);
            values.put(FPRODUCT_TYPE, type);
            values.put(FPRODUCT_REACODE, code);



            int cn = cursor.getCount();
            if (cn > 0) {
                count = dB.update(TABLE_FPRODUCT_PRE, values, FPRODUCT_ITEMCODE_PRE
                                + " = '" + itemCode + "' and "+FPRODUCT_TYPE + " = '" + type + "' and "+FPRODUCT_REACODE + " = '" + code + "'",
                        null);
            } else {
                count = (int) dB.insert(TABLE_FPRODUCT_PRE, null, values);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
        return count;
    }
    public ArrayList<PreProduct> getSelectedItems(String type) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<PreProduct> list = new ArrayList<>();
        try {
            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FPRODUCT_PRE + " WHERE  type = '"+type+"' and qty_pre<>'0' or case_pre<>'0' ", null);

            while (cursor.moveToNext()) {
                PreProduct product = new PreProduct();
                product.setPREPRODUCT_ID(cursor.getString(cursor.getColumnIndex(FPRODUCT_ID_PRE)));
                product.setPREPRODUCT_ITEMCODE(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMCODE_PRE)));
                product.setPREPRODUCT_ITEMNAME(cursor.getString(cursor.getColumnIndex(FPRODUCT_ITEMNAME_PRE)));
                product.setPREPRODUCT_PRICE(cursor.getString(cursor.getColumnIndex(FPRODUCT_PRICE_PRE)));
                product.setPREPRODUCT_QOH(cursor.getString(cursor.getColumnIndex(FPRODUCT_QOH_PRE)));
                product.setPREPRODUCT_QTY(cursor.getString(cursor.getColumnIndex(FPRODUCT_QTY_PRE)));
                product.setPREPRODUCT_CASE(cursor.getString(cursor.getColumnIndex(FPRODUCT_CASE_PRE)));
                product.setPREPRODUCT_REACODE(cursor.getString(cursor.getColumnIndex(FPRODUCT_REACODE)));
                product.setPREPRODUCT_UNIT(cursor.getString(cursor.getColumnIndex(FPRODUCT_UNITS)));
                product.setPREPRODUCT_TXN_TYPE(cursor.getString(cursor.getColumnIndex(FPRODUCT_TYPE)));
                product.setPREPRODUCT_CHANGED_PRICE(cursor.getString(cursor.getColumnIndex(ProductController.FPRODUCT_CHANGED_PRICE)));

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
    public void mClearTables() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {
            dB.delete(TABLE_FPRODUCT_PRE, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public void insertIntoProductAsBulkForPre(String LocCode, String prillcode, String type)
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try
        {

                String insertQuery2;
                insertQuery2 = "INSERT INTO fProducts_pre (itemcode_pre,itemname_pre,price_pre,ChangedPrice,qoh_pre,qty_pre,case_pre,ReaCode,units,type) " +
                        "SELECT  itm.ItemCode AS ItemCode , itm.ItemName AS ItemName ,  \n" +
                        "                        IFNULL(pri.Price,0.0) AS Price , \n" +
                        " '0' AS ChangedPrice , \n" +
                        "                        loc.QOH AS QOH , '0' AS Qty, '0' AS Cases, '' AS ReaCode, itm.NouCase AS units, '"+type+"' AS type " +
                        " FROM fItem itm\n" +
                        "                        INNER JOIN fItemLoc loc ON loc.ItemCode = itm.ItemCode \n" +
                        "                        LEFT JOIN fItemPri pri ON pri.ItemCode = itm.ItemCode  \n" +
                        "                        WHERE loc.LocCode = '"+LocCode+"' AND pri.PrilCode = '"+prillcode+"'\n" +
                        "                        Group by  itm.ItemCode ORDER BY CAST(QOH AS FLOAT) DESC ";

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
