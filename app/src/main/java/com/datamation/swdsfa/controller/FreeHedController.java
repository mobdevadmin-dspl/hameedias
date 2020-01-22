package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.FreeHed;
import com.datamation.swdsfa.model.OrderDetail;
import com.datamation.swdsfa.model.free;

import java.util.ArrayList;
import java.util.List;

public class FreeHedController {

    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "swadeshi";
    // rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

    // table
    public static final String TABLE_FFREEHED = "Ffreehed";
    // table attributes
    public static final String FFREEHED_ID = "Ffreehed_id";

    public static final String FFREEHED_DISC_DESC = "DiscDesc";
    public static final String FFREEHED_PRIORITY = "Priority";
    public static final String FFREEHED_VDATEF = "Vdatef";
    public static final String FFREEHED_VDATET = "Vdatet";
    public static final String FFREEHED_REMARKS = "Remarks";
    public static final String FFREEHED_RECORD_ID = "RecordId";
    public static final String FFREEHED_ITEM_QTY = "ItemQty";
    public static final String FFREEHED_FREE_IT_QTY = "FreeItQty";
    public static final String FFREEHED_FTYPE = "Ftype";

    // create String
    public static final String CREATE_FFREEHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FFREEHED + " (" + FFREEHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT, " + FFREEHED_DISC_DESC + " TEXT, " + FFREEHED_PRIORITY + " TEXT, " + FFREEHED_VDATEF + " TEXT, " + FFREEHED_VDATET + " TEXT, " + FFREEHED_REMARKS + " TEXT, " + FFREEHED_RECORD_ID + " TEXT, " + FFREEHED_ITEM_QTY + " TEXT, " + FFREEHED_FREE_IT_QTY + " TEXT, " + FFREEHED_FTYPE + " TEXT); ";


    public static final String IDXFREEHED = "CREATE UNIQUE INDEX IF NOT EXISTS idxfreehed_something ON " + TABLE_FFREEHED + " (" + FFREEHED_ID + ")";

    public FreeHedController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFreeHed(ArrayList<FreeHed> list) {

        int returnID = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        Cursor cursor_ini = null;

        try {
            cursor_ini = dB.rawQuery("SELECT * FROM " + TABLE_FFREEHED, null);

            for (FreeHed freehed : list) {

                ContentValues values = new ContentValues();

                values.put(dbHelper.REFNO, freehed.getFFREEHED_REFNO());
                values.put(dbHelper.TXNDATE, freehed.getFFREEHED_TXNDATE());
                values.put(FFREEHED_DISC_DESC, freehed.getFFREEHED_DISC_DESC());
                values.put(FFREEHED_PRIORITY, freehed.getFFREEHED_PRIORITY());
                values.put(FFREEHED_VDATEF, freehed.getFFREEHED_VDATEF());
                values.put(FFREEHED_VDATET, freehed.getFFREEHED_VDATET());
                values.put(FFREEHED_REMARKS, freehed.getFFREEHED_REMARKS());
                values.put(FFREEHED_RECORD_ID, freehed.getFFREEHED_RECORD_ID());
                values.put(FFREEHED_ITEM_QTY, freehed.getFFREEHED_ITEM_QTY());
                values.put(FFREEHED_FREE_IT_QTY, freehed.getFFREEHED_FREE_IT_QTY());
                values.put(FFREEHED_FTYPE, freehed.getFFREEHED_FTYPE());
               // values.put(FFREEHED_COSTCODE, freehed.getFFREEHED_COSTCODE());

                if (cursor_ini.getCount() > 0) {
                    String selectQuery = "SELECT * FROM " + TABLE_FFREEHED + " WHERE " + dbHelper.REFNO + "='" + freehed.getFFREEHED_REFNO() + "'";
                    cursor = dB.rawQuery(selectQuery, null);

                    if (cursor.getCount() > 0) {
                        returnID = (int) dB.update(TABLE_FFREEHED, values, dbHelper.REFNO + "='" + freehed.getFFREEHED_REFNO() + "'", null);
                    } else {
                        returnID = (int) dB.insert(TABLE_FFREEHED, null, values);
                    }

                } else {
                    returnID = (int) dB.insert(TABLE_FFREEHED, null, values);
                }

            }
        } catch (Exception e) {

            Log.v("Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (cursor_ini != null) {
                cursor_ini.close();
            }
            dB.close();
        }
        return returnID;

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

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_FFREEHED, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_FFREEHED, null, null);
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
//get fredetails by orderlist

    //filter free items from order details
    public ArrayList<FreeHed>  filterFreeSchemesFromOrder(ArrayList<OrderDetail> OrderList) {
        //rashmi for new swadeshi sfa - 2019-09-09
        ArrayList<FreeHed> list = new ArrayList<FreeHed>();
        for (OrderDetail det: OrderList) {
            list = getFreeIssueItemDetailByItem(det.getFORDERDET_ITEMCODE());
           // list = getFreeIssueItemDetailWithQty(det.getFORDERDET_ITEMCODE(),det.getFORDERDET_QTY());//rashmi 2019-09-17
        }

        for (FreeHed order : list) {

            Log.d("Rashmi-filterschemes", order.getFFREEHED_REFNO()+"");

        }
        return list;

    }

    public static <T> ArrayList<T> getSchemeListWithQtySum(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
//rashmi-2019-09-12
    public FreeHed getFreeIssueItemDetailByItemCode(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        // String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "') AND costcode='" + costCode + "' AND date('now') between vdatef and vdatet";
        // inoshi--Mine**CostCode change//
        FreeHed freeHed = new FreeHed();
        String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "')  AND date('now') between vdatef and vdatet order by Priority asc";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {


                freeHed.setFFREEHED_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
                freeHed.setFFREEHED_TXNDATE(cursor.getString(cursor.getColumnIndex(dbHelper.TXNDATE)));
                freeHed.setFFREEHED_DISC_DESC(cursor.getString(cursor.getColumnIndex(FFREEHED_DISC_DESC)));
                freeHed.setFFREEHED_PRIORITY(cursor.getString(cursor.getColumnIndex(FFREEHED_PRIORITY)));
                freeHed.setFFREEHED_VDATEF(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATEF)));
                freeHed.setFFREEHED_VDATET(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATET)));
                freeHed.setFFREEHED_REMARKS(cursor.getString(cursor.getColumnIndex(FFREEHED_REMARKS)));
                freeHed.setFFREEHED_RECORD_ID(cursor.getString(cursor.getColumnIndex(FFREEHED_RECORD_ID)));
                freeHed.setFFREEHED_ITEM_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_ITEM_QTY)));
                freeHed.setFFREEHED_FREE_IT_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_FREE_IT_QTY)));
                freeHed.setFFREEHED_FTYPE(cursor.getString(cursor.getColumnIndex(FFREEHED_FTYPE)));
                //freeHed.setFFREEHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FFREEHED_COSTCODE)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return freeHed;
    }
    public ArrayList<FreeHed> getFreeIssueItemDetailByItem(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FreeHed> list = new ArrayList<FreeHed>();

        // String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "') AND costcode='" + costCode + "' AND date('now') between vdatef and vdatet";
        // inoshi--Mine**CostCode change//
        String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "')  AND date('now') between vdatef and vdatet order by Priority asc";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                FreeHed freeHed = new FreeHed();

                freeHed.setFFREEHED_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
                freeHed.setFFREEHED_TXNDATE(cursor.getString(cursor.getColumnIndex(dbHelper.TXNDATE)));
                freeHed.setFFREEHED_DISC_DESC(cursor.getString(cursor.getColumnIndex(FFREEHED_DISC_DESC)));
                freeHed.setFFREEHED_PRIORITY(cursor.getString(cursor.getColumnIndex(FFREEHED_PRIORITY)));
                freeHed.setFFREEHED_VDATEF(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATEF)));
                freeHed.setFFREEHED_VDATET(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATET)));
                freeHed.setFFREEHED_REMARKS(cursor.getString(cursor.getColumnIndex(FFREEHED_REMARKS)));
                freeHed.setFFREEHED_RECORD_ID(cursor.getString(cursor.getColumnIndex(FFREEHED_RECORD_ID)));
                freeHed.setFFREEHED_ITEM_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_ITEM_QTY)));
                freeHed.setFFREEHED_FREE_IT_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_FREE_IT_QTY)));
                freeHed.setFFREEHED_FTYPE(cursor.getString(cursor.getColumnIndex(FFREEHED_FTYPE)));
                //freeHed.setFFREEHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FFREEHED_COSTCODE)));

                list.add(freeHed);
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
    //2019-09-17-rashmi
    public ArrayList<FreeHed> getFreeIssueItemDetailByItem(ArrayList<OrderDetail> dets) {


        ArrayList<FreeHed> list = new ArrayList<FreeHed>();

        for(OrderDetail det : dets){
            if (dB == null) {
                open();
            } else if (!dB.isOpen()) {
                open();
            }
            String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + det.getFORDERDET_ITEMCODE()+ "')  AND date('now') between vdatef and vdatet order by Priority asc";
            Cursor cursor = dB.rawQuery(selectQuery, null);
            try {
                while (cursor.moveToNext()) {

                    FreeHed freeHed = new FreeHed();

                    freeHed.setFFREEHED_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
                    freeHed.setFFREEHED_TXNDATE(cursor.getString(cursor.getColumnIndex(dbHelper.TXNDATE)));
                    freeHed.setFFREEHED_DISC_DESC(cursor.getString(cursor.getColumnIndex(FFREEHED_DISC_DESC)));
                    freeHed.setFFREEHED_PRIORITY(cursor.getString(cursor.getColumnIndex(FFREEHED_PRIORITY)));
                    freeHed.setFFREEHED_VDATEF(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATEF)));
                    freeHed.setFFREEHED_VDATET(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATET)));
                    freeHed.setFFREEHED_REMARKS(cursor.getString(cursor.getColumnIndex(FFREEHED_REMARKS)));
                    freeHed.setFFREEHED_RECORD_ID(cursor.getString(cursor.getColumnIndex(FFREEHED_RECORD_ID)));
                    freeHed.setFFREEHED_ITEM_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_ITEM_QTY)));
                    freeHed.setFFREEHED_FREE_IT_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_FREE_IT_QTY)));
                    freeHed.setFFREEHED_FTYPE(cursor.getString(cursor.getColumnIndex(FFREEHED_FTYPE)));
                  //  freeHed.setITEMQTY(det.getFORDERDET_QTY());
                    list.add(freeHed);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                dB.close();
            }
        }


        // String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "') AND costcode='" + costCode + "' AND date('now') between vdatef and vdatet";
        // inoshi--Mine**CostCode change//

        return list;
    }
    public ArrayList<FreeHed> getFreeIssueItemDetailWithQty(String itemCode, String qty) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FreeHed> list = new ArrayList<FreeHed>();

        // String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "') AND costcode='" + costCode + "' AND date('now') between vdatef and vdatet";
        // inoshi--Mine**CostCode change//
        String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "')  AND date('now') between vdatef and vdatet order by Priority asc";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                FreeHed freeHed = new FreeHed();

                freeHed.setFFREEHED_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
                freeHed.setFFREEHED_TXNDATE(cursor.getString(cursor.getColumnIndex(dbHelper.TXNDATE)));
                freeHed.setFFREEHED_DISC_DESC(cursor.getString(cursor.getColumnIndex(FFREEHED_DISC_DESC)));
                freeHed.setFFREEHED_PRIORITY(cursor.getString(cursor.getColumnIndex(FFREEHED_PRIORITY)));
                freeHed.setFFREEHED_VDATEF(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATEF)));
                freeHed.setFFREEHED_VDATET(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATET)));
                freeHed.setFFREEHED_REMARKS(cursor.getString(cursor.getColumnIndex(FFREEHED_REMARKS)));
                freeHed.setFFREEHED_RECORD_ID(cursor.getString(cursor.getColumnIndex(FFREEHED_RECORD_ID)));
                freeHed.setFFREEHED_ITEM_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_ITEM_QTY)));
                freeHed.setFFREEHED_FREE_IT_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_FREE_IT_QTY)));
                freeHed.setFFREEHED_FTYPE(cursor.getString(cursor.getColumnIndex(FFREEHED_FTYPE)));
                freeHed.setITEMQTY(qty);
                //freeHed.setFFREEHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FFREEHED_COSTCODE)));

                list.add(freeHed);
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
    public ArrayList<FreeHed> getFreeIssueItemDetailByRefno(String itemCode, String costCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FreeHed> list = new ArrayList<FreeHed>();

       // String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "') AND costcode='" + costCode + "' AND date('now') between vdatef and vdatet";
    // inoshi--Mine**CostCode change//
        String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "')  AND date('now') between vdatef and vdatet order by Priority asc";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
        while (cursor.moveToNext()) {

            FreeHed freeHed = new FreeHed();

            freeHed.setFFREEHED_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
            freeHed.setFFREEHED_TXNDATE(cursor.getString(cursor.getColumnIndex(dbHelper.TXNDATE)));
            freeHed.setFFREEHED_DISC_DESC(cursor.getString(cursor.getColumnIndex(FFREEHED_DISC_DESC)));
            freeHed.setFFREEHED_PRIORITY(cursor.getString(cursor.getColumnIndex(FFREEHED_PRIORITY)));
            freeHed.setFFREEHED_VDATEF(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATEF)));
            freeHed.setFFREEHED_VDATET(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATET)));
            freeHed.setFFREEHED_REMARKS(cursor.getString(cursor.getColumnIndex(FFREEHED_REMARKS)));
            freeHed.setFFREEHED_RECORD_ID(cursor.getString(cursor.getColumnIndex(FFREEHED_RECORD_ID)));
            freeHed.setFFREEHED_ITEM_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_ITEM_QTY)));
            freeHed.setFFREEHED_FREE_IT_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_FREE_IT_QTY)));
            freeHed.setFFREEHED_FTYPE(cursor.getString(cursor.getColumnIndex(FFREEHED_FTYPE)));
            //freeHed.setFFREEHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FFREEHED_COSTCODE)));

            list.add(freeHed);
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

    public ArrayList<free> getFreeIssueDetails(String refno, String type) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<free> list = new ArrayList<free>();
        String selectQuery = "";
        Cursor cursor = null;

        // String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "') AND costcode='" + costCode + "' AND date('now') between vdatef and vdatet";
        // inoshi--Mine**CostCode change//
        if(type.trim().equals("Flat")) {
            selectQuery = "  select  hed.refno as Scheme , hed.ItemQty as ItemQty , hed.FreeItQty as FreeQty , 'click to see sale items' as SaleItem " +
                    "  , '0' as QtyFrom , '0' as QtyTo, 'click to see free items' as FreeItem " +
                    "  from Ffreehed hed  where " +
                    " hed.refno = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);
        }else{
            selectQuery = "select mslab.refno as Scheme , mslab.ItemQty as ItemQty , mslab.FreeItQty as FreeQty ,  mslab.Qtyf as QtyFrom , mslab.Qtyt as QtyTo, 'click to see sale items' as SaleItem" +
                    " , 'click to see free items' as FreeItem from Ffreemslab mslab  where" +
                    "  mslab.refno in (select refno from Ffreehed where refno = '" + refno + "')";
            cursor = dB.rawQuery(selectQuery, null);
        }
        try {
            while (cursor.moveToNext()) {

                free freeHed = new free();

                freeHed.setSaleItem(cursor.getString(cursor.getColumnIndex("SaleItem")));
                freeHed.setIssueItem(cursor.getString(cursor.getColumnIndex("FreeItem")));
                freeHed.setItemQty(cursor.getString(cursor.getColumnIndex("ItemQty")));
                freeHed.setFreeQty(cursor.getString(cursor.getColumnIndex("FreeQty")));
                freeHed.setFromQty(cursor.getString(cursor.getColumnIndex("QtyFrom")));
                freeHed.setToQty(cursor.getString(cursor.getColumnIndex("QtyTo")));

                list.add(freeHed);
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
    public int getRefnoByDebCount(String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT count(*) FROM " + FreeDebController.TABLE_FFREEDEB + " WHERE " + dbHelper.REFNO + "='" + refno + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getInt(0);

        }
        return 0;

    }
    public ArrayList<FreeHed> getFreeIssueHeaders() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FreeHed> list = new ArrayList<FreeHed>();

        // String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "') AND costcode='" + costCode + "' AND date('now') between vdatef and vdatet";
        // inoshi--Mine**CostCode change//
        String selectQuery = "select * from ffreehed where date('now') between vdatef and vdatet";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                FreeHed freeHed = new FreeHed();

                freeHed.setFFREEHED_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
                freeHed.setFFREEHED_TXNDATE(cursor.getString(cursor.getColumnIndex(dbHelper.TXNDATE)));
                freeHed.setFFREEHED_DISC_DESC(cursor.getString(cursor.getColumnIndex(FFREEHED_DISC_DESC)));
                freeHed.setFFREEHED_PRIORITY(cursor.getString(cursor.getColumnIndex(FFREEHED_PRIORITY)));
                freeHed.setFFREEHED_VDATEF(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATEF)));
                freeHed.setFFREEHED_VDATET(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATET)));
                freeHed.setFFREEHED_REMARKS(cursor.getString(cursor.getColumnIndex(FFREEHED_REMARKS)));
                freeHed.setFFREEHED_RECORD_ID(cursor.getString(cursor.getColumnIndex(FFREEHED_RECORD_ID)));
                freeHed.setFFREEHED_ITEM_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_ITEM_QTY)));
                freeHed.setFFREEHED_FREE_IT_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_FREE_IT_QTY)));
                freeHed.setFFREEHED_FTYPE(cursor.getString(cursor.getColumnIndex(FFREEHED_FTYPE)));
                //freeHed.setFFREEHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FFREEHED_COSTCODE)));

                list.add(freeHed);
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

    public ArrayList<FreeHed> getSaleItems(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FreeHed> list = new ArrayList<FreeHed>();

        // String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "') AND costcode='" + costCode + "' AND date('now') between vdatef and vdatet";
        // inoshi--Mine**CostCode change//
        String selectQuery = "select  item.itemcode as code ,item.itemname as SaleItem " +
                "                      from fItem item, Ffreedet free  where item.itemcode = free.itemcode " +
                "                    and free.refno "+ " = '" + refno + "'";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                FreeHed freeHed = new FreeHed();
                freeHed.setFFREEHED_REMARKS(cursor.getString(cursor.getColumnIndex("code"))+" - "+cursor.getString(cursor.getColumnIndex("SaleItem")));

                list.add(freeHed);
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
    public ArrayList<FreeHed> getFreeItems(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FreeHed> list = new ArrayList<FreeHed>();

        // String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "') AND costcode='" + costCode + "' AND date('now') between vdatef and vdatet";
        // inoshi--Mine**CostCode change//
        String selectQuery = "select  item.itemcode as code ,item.itemname as SaleItem " +
                "                      from fItem item, fFreeItem fritem  where item.itemcode = fritem.itemcode " +
                "                    and fritem.refno "+ " = '" + refno + "'";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                FreeHed freeHed = new FreeHed();
                freeHed.setFFREEHED_REMARKS(cursor.getString(cursor.getColumnIndex("code"))+" - "+cursor.getString(cursor.getColumnIndex("SaleItem")));

                list.add(freeHed);
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
    public FreeHed getFreeIssueItemSchema(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        FreeHed freeHed = new FreeHed();
        String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "')  AND date('now') between vdatef and vdatet";
        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
        while (cursor.moveToNext()) {



            freeHed.setFFREEHED_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
            freeHed.setFFREEHED_TXNDATE(cursor.getString(cursor.getColumnIndex(dbHelper.TXNDATE)));
            freeHed.setFFREEHED_DISC_DESC(cursor.getString(cursor.getColumnIndex(FFREEHED_DISC_DESC)));
            freeHed.setFFREEHED_PRIORITY(cursor.getString(cursor.getColumnIndex(FFREEHED_PRIORITY)));
            freeHed.setFFREEHED_VDATEF(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATEF)));
            freeHed.setFFREEHED_VDATET(cursor.getString(cursor.getColumnIndex(FFREEHED_VDATET)));
            freeHed.setFFREEHED_REMARKS(cursor.getString(cursor.getColumnIndex(FFREEHED_REMARKS)));
            freeHed.setFFREEHED_RECORD_ID(cursor.getString(cursor.getColumnIndex(FFREEHED_RECORD_ID)));
            freeHed.setFFREEHED_ITEM_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_ITEM_QTY)));
            freeHed.setFFREEHED_FREE_IT_QTY(cursor.getString(cursor.getColumnIndex(FFREEHED_FREE_IT_QTY)));
            freeHed.setFFREEHED_FTYPE(cursor.getString(cursor.getColumnIndex(FFREEHED_FTYPE)));
            //freeHed.setFFREEHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FFREEHED_COSTCODE)));
        }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return freeHed;
    }

    public String getRefoByItemCode(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FreeHed> list = new ArrayList<FreeHed>();

        String selectQuery = "select * from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "') and date('now') between vdatef and vdatet";

        String s = null;
        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                FreeHed freeHed = new FreeHed();

                s = cursor.getString(cursor.getColumnIndex(dbHelper.REFNO));
                list.add(freeHed);

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return s;
    }

}
