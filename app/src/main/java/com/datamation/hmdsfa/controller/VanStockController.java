package com.datamation.hmdsfa.controller;

//********kaveesha - 12-06-2020

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.FddbNote;
import com.datamation.hmdsfa.model.StockInfo;
import com.datamation.hmdsfa.model.VanStock;

import java.util.ArrayList;

import static com.datamation.hmdsfa.controller.ItemController.FGROUP_CODE;
import static com.datamation.hmdsfa.controller.ItemController.FITEM_GROUP_CODE;
import static com.datamation.hmdsfa.controller.ItemController.FITEM_ITEM_CODE;
import static com.datamation.hmdsfa.controller.ItemController.FITEM_ITEM_NAME;
import static com.datamation.hmdsfa.controller.ItemController.FITEM_RE_ORDER_QTY;

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

    //----------------------kaveesha -----27/08/2020-----------To Stcok Inquiry-----------------------
    public String getTotalQOH(String LocCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        String selectQuery = "SELECT sum(Quantity_Issued) as totQty from fVanStock where To_Location_Code = '" + LocCode + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {
                return cursor.getString(cursor.getColumnIndex("totQty"));
            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return "0";
    }

    //----------kaveesha ---------27/08/2020------------------To get Stock-------------------------------------
    public ArrayList<StockInfo> getVanStocks(String LocCode, String newText) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<StockInfo> list = new ArrayList<StockInfo>();

        //String selectQuery = "SELECT itm.* ,vstock.To_Location_Code, sum(vstock.Quantity_Issued) as totqty FROM fitem itm, fvanstock vstock WHERE vstock.To_Location_Code = '" + LocCode + "' AND vstock.Item_No=itm.itemcode GROUP BY Item_No ORDER BY Item_No ";
        String selectQuery = "SELECT itm.* ,vstock.To_Location_Code, sum(vstock.Quantity_Issued) as totqty FROM fitem itm, fvanstock vstock WHERE itm.ItemCode || itm.ItemName LIKE '%" + newText + "%' AND vstock.To_Location_Code = '" + LocCode + "' AND vstock.Item_No=itm.itemcode GROUP BY Item_No ORDER BY Item_No ";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {

            while (cursor.moveToNext()) {

                StockInfo items = new StockInfo();
                double qoh = Double.parseDouble(cursor.getString(cursor.getColumnIndex("totqty")));
                if (qoh > 0) {
                    items.setStock_Itemcode(cursor.getString(cursor.getColumnIndex(FITEM_ITEM_CODE)));
                    items.setStock_Itemname(cursor.getString(cursor.getColumnIndex(FITEM_ITEM_NAME)));
                    //items.setStock_Itemname(cursor.getString(cursor.getColumnIndex(VanStockController.FVAN_TO_LOCATION_CODE)) + " - " + cursor.getString(cursor.getColumnIndex(FITEM_ITEM_NAME)));
                    items.setStock_Qoh(((int) qoh) + "");
                    list.add(items);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return list;
    }

    //----------kaveesha ---------28/08/2020------------------To get Product Group wise Stock-------------------------------------
    public ArrayList<StockInfo> getGwiseVanStocks(String LocCode, String newText) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<StockInfo> list = new ArrayList<StockInfo>();

       //String selectQuery = "SELECT itm.* , vstock.To_Location_Code, sum(vstock.Quantity_Issued)as totqty FROM fitem itm, fvanstock vstock WHERE  vstock.To_Location_Code ='" + LocCode + "' AND vstock.Item_No=itm.itemcode  GROUP BY GroupCode ORDER BY GroupCode ";
        String selectQuery = "SELECT itm.* , vstock.To_Location_Code, sum(vstock.Quantity_Issued)as totqty FROM fitem itm, fvanstock vstock WHERE ReOrderQty LIKE '%" + newText + "%' AND vstock.To_Location_Code ='" + LocCode + "' AND vstock.Item_No=itm.itemcode  GROUP BY GroupCode ORDER BY GroupCode ";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {

            while (cursor.moveToNext()) {

                StockInfo items = new StockInfo();
                double qoh = Double.parseDouble(cursor.getString(cursor.getColumnIndex("totqty")));
                //double qoh = Double.parseDouble(cursor.getString(cursor.getColumnIndex(VanStockController.FVAN_QUANTITY_ISSUED)));
                if (qoh > 0) {
                    items.setStock_Itemcode(cursor.getString(cursor.getColumnIndex(FITEM_GROUP_CODE)));
                   // items.setStock_Itemname(cursor.getString(cursor.getColumnIndex(FITEM_ITEM_NAME)));
                    items.setStock_Itemname(cursor.getString(cursor.getColumnIndex(FITEM_RE_ORDER_QTY)));
                    items.setStock_Qoh(((int) qoh) + "");

                    list.add(items);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (cursor != null) {
                    cursor.close();
                }
                dB.close();
            }
        return list;
    }

    public ArrayList<VanStock> getStockDetailList(String locCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<VanStock> list = new ArrayList<VanStock>();
        Cursor cursor = null;
        try {

            String selectQuery = "SELECT v.Item_No,v.Barcode,v.Quantity_Issued,v.Variant_Code, b.Description, b.Article_No as ArticleNo, s.UnitPrice ,(s.UnitPrice * v.Quantity_Issued) as Amount from fVanStock v, fSalesPrice s, BarCodeVarient b  WHERE  v.To_Location_Code  = '" + locCode + "' AND v.Barcode = b.Barcode_No AND v.Item_No = s.ItemNo GROUP BY v.Barcode";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                VanStock vanStock = new VanStock();

                vanStock.setItem_No(cursor.getString(cursor.getColumnIndex(FVAN_ITEM_NO)));
                vanStock.setBarcode(cursor.getString(cursor.getColumnIndex(FVAN_BARCODE)));
                vanStock.setVariant_Code(cursor.getString(cursor.getColumnIndex(FVAN_VARIANT_CODE)));
                vanStock.setDescription(cursor.getString(cursor.getColumnIndex(BarcodeVarientController.Description)));
                vanStock.setArticleNo(cursor.getString(cursor.getColumnIndex("ArticleNo")));
                vanStock.setQuantity_Issued(cursor.getString(cursor.getColumnIndex(FVAN_QUANTITY_ISSUED)));
                vanStock.setUnitPrice(cursor.getString(cursor.getColumnIndex(SalesPriceController.FSALES_PRI_UNITPRICE)));
                vanStock.setAmount(cursor.getString(cursor.getColumnIndex("Amount")));
                list.add(vanStock);

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

    public ArrayList<VanStock> getQtyByGroupWise(String locCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<VanStock> list = new ArrayList<VanStock>();
        Cursor cursor = null;
        try {

            String selectQuery = "Select sum(v.Quantity_Issued) as totQty, itm.ItemName as Description FROM fVanStock v, fItem itm WHERE v.Item_No = itm.ItemCode AND v.To_Location_Code = '"+ locCode + "' GROUP BY itm.GroupCode";


            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                VanStock vanStock = new VanStock();

                vanStock.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
                vanStock.setTotQty(cursor.getString(cursor.getColumnIndex("totQty")));
                list.add(vanStock);

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
