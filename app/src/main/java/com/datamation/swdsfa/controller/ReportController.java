package com.datamation.swdsfa.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.DayExpDet;
import com.datamation.swdsfa.model.OrderDetail;
import com.datamation.swdsfa.model.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportController {
    /**
     * Developed for write report functions by rashmi
     *
     * **/
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    private String TAG = "ReportController";

    // Shared Preferences variables
    public static final String SETTINGS = "SETTINGS";


    public ReportController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }
///////////////////////////////////////////get target vs actual data //////////////////////////////////////////////////
    public ArrayList<Target> getTargetVsActuals(String from, String to) {//developed by rashmi

        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Target> list = new ArrayList<Target>();

        // String selectQuery = "select * from " + dbHelper.TABLE_ORDER_DETAIL + " WHERE "
        String selectQuery = "SELECT itd.txndate, itd.Rdtarget, ifnull((sum(a.Amt)),0)  as totAmt " +
                "FROM FItenrDet itd " +
                "LEFT JOIN FOrddet a " +
                "ON itd.txndate = a.txndate " +
                "where itd.txndate between '" + from + "' and " +
                "'" + to + "' group by itd.txndate";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                Target target = new Target();
                target.setDate(cursor.getString(cursor.getColumnIndex("TxnDate")));
                target.setTargetAmt(Double.parseDouble(cursor.getString(cursor.getColumnIndex("RDTarget"))));
                target.setAchieveAmt(Double.parseDouble(cursor.getString(cursor.getColumnIndex("totAmt"))));

                list.add(target);

            }
        } catch (Exception e) {

            Log.v(TAG + " Except getTargtVsActul", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return list;
    }

    ///////////////////////////////////////////get expense report data//////////////////////////////////////////////////////////////

    public ArrayList<DayExpDet> getDaExpenseData(String from, String to) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<DayExpDet> list = new ArrayList<DayExpDet>();

        String selectQuery = "SELECT d.ExpCode,r.ExpName, d.Amt from fExpense r, DayExpDet d  where d.expcode = r.expcode  and d.txndate between '" + from + "' and "
                +"'" + to + "'";
        // String selectQuery = "select * from DayExpDet WHERE " + dbHelper.REFNO + "='" + refno + "' and  TxnDate = '" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate) +"'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                DayExpDet deDet = new DayExpDet();

                deDet.setEXPDET_EXPCODE(cursor.getString(cursor.getColumnIndex(DayExpDetController.DAYEXPDET_EXPCODE)));
                deDet.setEXPDET_DESCRIPTION(cursor.getString(cursor.getColumnIndex(ExpenseController.FEXPENSE_NAME)));
                deDet.setEXPDET_AMOUNT(cursor.getString(cursor.getColumnIndex(DayExpDetController.DAYEXPDET_AMT)));

                list.add(deDet);

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return list;
    }
    public ArrayList<OrderDetail> getPreSaleData(String from, String to) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();

        String selectQuery = "select d.ItemCode,d.txndate,i.NOUCase, ifnull((sum(d.CaseQty)),0) as cases ,ifnull((sum(d.PiceQty)),0) as pieses ,ifnull((sum(d.Amt)),0) as Amt , count(distinct h.debcode)as reach ,i.ItemName from fItem i, Forddet d, fordhed h  where d.ItemCode = i.ItemCode and h.refno = d.refno and d.txndate between '" + from + "' and "
                +"'" + to + "' group by d.ItemCode";
        // String selectQuery = "select * from DayExpDet WHERE " + dbHelper.REFNO + "='" + refno + "' and  TxnDate = '" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate) +"'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                OrderDetail ordDet = new OrderDetail();

                ordDet.setFORDERDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(OrderDetailController.FORDDET_ITEM_CODE)));
                ordDet.setFORDERDET_TXNDATE(cursor.getString(cursor.getColumnIndex(dbHelper.TXNDATE)));
                ordDet.setFORDERDET_QOH(cursor.getString(cursor.getColumnIndex(ItemController.FITEM_NOU_CASE)));
                ordDet.setFORDERDET_CASES(cursor.getString(cursor.getColumnIndex("cases")));
                ordDet.setFORDERDET_PICE_QTY(cursor.getString(cursor.getColumnIndex("pieses")));
                ordDet.setFORDERDET_AMT(cursor.getString(cursor.getColumnIndex(OrderDetailController.FORDDET_AMT)));
                ordDet.setFORDERDET_ITEMNAME(cursor.getString(cursor.getColumnIndex(ItemController.FITEM_ITEM_NAME)));
                ordDet.setFORDERDET_SEQNO(""+cursor.getInt(cursor.getColumnIndex("reach")));

                list.add(ordDet);

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return list;
    }
}
