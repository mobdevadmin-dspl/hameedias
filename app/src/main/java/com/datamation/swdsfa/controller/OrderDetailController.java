package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.FInvRDet;
import com.datamation.swdsfa.model.InvDet;
import com.datamation.swdsfa.model.OrderDetail;
import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.OrderDisc;
import com.datamation.swdsfa.model.PreProduct;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rashmi
 */

public class OrderDetailController {
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    private String TAG = "Order Detail";
    // rashmi - 2019-12-17 move from database_helper , because of reduce coding in database helper*******************************************************************************

    // table
    public static final String TABLE_ORDER_DETAIL = "OrderDetail";
    // table attributes
    public static final String ORDDET_ID = "OrderDetId";
    public static final String ORDDET_AMT = "Amt";
    public static final String ORDDET_ITEM_CODE = "Itemcode";
    public static final String ORDDET_PRIL_CODE = "PriLCode";
    public static final String ORDDET_QTY = "Qty";
    public static final String ORDDET_PRICE = "Price";
    public static final String ORDDET_IS_ACTIVE = "isActive";
    public static final String ORDDET_ITEM_NAME = "ItemName";
    public static final String ORDDET_BAL_QTY = "BalQty";
    public static final String ORDDET_BAMT = "BAmt";
    public static final String ORDDET_BDIS_AMT = "BDisAmt";
    public static final String ORDDET_BPDIS_AMT = "BPDisAmt";
    public static final String ORDDET_BTAX_AMT = "BTaxAmt";
    public static final String ORDDET_TAX_AMT = "TaxAmt";
    public static final String ORDDET_DIS_AMT = "DisAmt";
    public static final String ORDDET_SCHDISPER = "SchDisPer";
    public static final String ORDDET_BRAND_DISPER = "BrandDisPer";
    public static final String ORDDET_BRAND_DISC = "BrandDisc";
    public static final String ORDDET_COMP_DISC = "CompDis";
    public static final String ORDDET_COST_PRICE = "CostPrice";
    public static final String ORDDET_PIECE_QTY = "PieceQty";
    public static final String ORDDET_SELL_PRICE = "SellPrice";
    public static final String ORDDET_BSELL_PRICE = "BSellPrice";
    public static final String ORDDET_SEQ_NO = "SeqNo";
    public static final String ORDDET_TAX_COM_CODE = "TaxComCode";
    public static final String ORDDET_BTSELL_PRICE = "BTSellPrice";
    public static final String ORDDET_TSELL_PRICE = "TSellPrice";
    public static final String ORDDET_TXN_TYPE = "TxnType";
    public static final String ORDDET_LOC_CODE = "LocCode";
    public static final String ORDDET_TXN_DATE = "TxnDate";
    public static final String ORDDET_RECORD_ID = "RecordId";
    public static final String ORDDET_PDIS_AMT = "PDisAmt";
    public static final String ORDDET_IS_SYNCED = "IsSynced";
    public static final String ORDDET_QOH = "Qoh";
    public static final String ORDDET_TYPE = "Type";
    public static final String ORDDET_SCHDISC = "SchDisc";
    public static final String ORDDET_DIS_TYPE = "DisType";
    public static final String ORDDET_QTY_SLAB_DISC = "QtySlabDisc";
    public static final String ORDDET_ORG_PRICE = "OrgPrice";
    public static final String ORDDET_DIS_FLAG = "DisFlag";
    public static final String REFNO = "RefNo";
    public static final String TXNDATE = "TxnDate";
    public static final String REPCODE = "RepCode";
    public static final String DEALCODE = "DealCode";
    public static final String DEBCODE = "DebCode";
    //----------------------------------------------------------------------------------------------------------------------------
// table
    public static final String TABLE_FORDDET = "FOrddet";
    // table attributes
    public static final String FORDDET_ID = "stodet_id";
    public static final String FORDDET_AMT = "Amt";
    public static final String FORDDET_BAL_QTY = "BalQty";
    public static final String FORDDET_B_AMT = "BAmt";
    public static final String FORDDET_B_DIS_AMT = "BDisAmt";
    public static final String FORDDET_BP_DIS_AMT = "BPDisAmt";
    public static final String FORDDET_B_SELL_PRICE = "BSellPrice";
    public static final String FORDDET_BT_TAX_AMT = "BTaxAmt";
    public static final String FORDDET_BT_SELL_PRICE = "BTSellPrice";
    public static final String FORDDET_CASE = "Cases";
    public static final String FORDDET_CASE_QTY = "CaseQty";
    public static final String FORDDET_DIS_AMT = "DisAmt";
    public static final String FORDDET_DIS_PER = "DisPer";
    public static final String FORDDET_FREE_QTY = "freeqty";
    public static final String FORDDET_ITEM_CODE = "Itemcode";
    public static final String FORDDET_P_DIS_AMT = "PDisAmt";
    public static final String FORDDET_PRIL_CODE = "PrilCode";
    public static final String FORDDET_QTY = "Qty";
    public static final String FORDDET_DIS_VAL_AMT = "DisValAmt";
    public static final String FORDDET_PICE_QTY = "PiceQty";
    public static final String FORDDET_REA_CODE = "ReaCode";
    public static final String FORDDET_TYPE = "Types";
    public static final String FORDDET_RECORD_ID = "RecordId";
    public static final String FORDDET_DISCTYPE = "DiscType";
    public static final String FORDDET_SELL_PRICE = "SellPrice";
    public static final String FORDDET_SEQNO = "SeqNo";
    public static final String FORDDET_TAX_AMT = "TaxAmt";
    public static final String FORDDET_TAX_COM_CODE = "TaxComCode";
    public static final String FORDDET_TIMESTAMP_COLUMN = "timestamp_column";
    public static final String FORDDET_T_SELL_PRICE = "TSellPrice";
    public static final String FORDDET_DISFLAG = "DisFlag";
    public static final String FORDDET_TXN_TYPE = "TxnType";
    public static final String FORDDET_IS_ACTIVE = "isActive";
    public static final String FORDDET_SCH_DISPER = "SchDisPer";
    public static final String FORDDET_ITEMNAME = "ItemName";
    public static final String FORDDET_PACKSIZE = "PackSize";
    public static final String FORDDET_COSTPRICE = "CostPrice";
    /////
    // create String
    public static final String CREATE_FORDDET_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FORDDET + " (" + FORDDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FORDDET_AMT + " TEXT, " + FORDDET_BAL_QTY + " TEXT, " + FORDDET_B_AMT + " TEXT, " + FORDDET_B_DIS_AMT + " TEXT, " + FORDDET_BP_DIS_AMT + " TEXT, " + FORDDET_B_SELL_PRICE + " TEXT, " + FORDDET_BT_TAX_AMT + " TEXT, " + FORDDET_BT_SELL_PRICE + " TEXT, " + FORDDET_CASE + " TEXT, " + FORDDET_CASE_QTY + " TEXT, " + FORDDET_DIS_AMT + " TEXT, " + FORDDET_DIS_PER + " TEXT, " + FORDDET_FREE_QTY + " TEXT, " + FORDDET_ITEM_CODE + " TEXT, " + FORDDET_P_DIS_AMT + " TEXT, " + FORDDET_PRIL_CODE + " TEXT, " + FORDDET_QTY + " TEXT, " + FORDDET_DIS_VAL_AMT + " TEXT, " + FORDDET_PICE_QTY + " TEXT, " + FORDDET_REA_CODE + " TEXT, " + FORDDET_TYPE + " TEXT, " + FORDDET_RECORD_ID + " TEXT, " + REFNO + " TEXT, " + FORDDET_SELL_PRICE + " TEXT, " + FORDDET_SEQNO + " TEXT, " + FORDDET_TAX_AMT + " TEXT, " + FORDDET_TAX_COM_CODE + " TEXT, " + FORDDET_TIMESTAMP_COLUMN + " TEXT, " + FORDDET_T_SELL_PRICE + " TEXT, "
            + FORDDET_ITEMNAME + " TEXT, "
            + FORDDET_PACKSIZE + " TEXT, "
            + FORDDET_COSTPRICE + " TEXT, "
            + FORDDET_DISCTYPE + " TEXT, " + FORDDET_SCH_DISPER + " TEXT, "+ FORDDET_DISFLAG + " TEXT, " + TXNDATE + " TEXT, " + FORDDET_IS_ACTIVE + " TEXT, "+ FORDDET_TXN_TYPE + " TEXT); ";

    // create String
    public static final String CREATE_ORDDET_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER_DETAIL +
            " (" + ORDDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ORDDET_AMT + " TEXT, " +
            ORDDET_ITEM_CODE + " TEXT, " +
            ORDDET_PRIL_CODE + " TEXT, " +
            ORDDET_QTY + " TEXT, " +
            REFNO + " TEXT, " +
            ORDDET_PRICE + " TEXT, " +
            ORDDET_ITEM_NAME + " TEXT, " +
            ORDDET_BAL_QTY + " TEXT, " +
            ORDDET_BAMT + " TEXT, " +
            ORDDET_BDIS_AMT + " TEXT, " +
            ORDDET_BPDIS_AMT + " TEXT, " +
            ORDDET_BTAX_AMT + " TEXT, " +
            ORDDET_TAX_AMT + " TEXT, " +
            ORDDET_DIS_AMT + " TEXT, " +
            ORDDET_SCHDISPER + " TEXT, " +
            ORDDET_BRAND_DISPER + " TEXT, " +
            ORDDET_BRAND_DISC + " TEXT, " +
            ORDDET_COMP_DISC + " TEXT, " +
            ORDDET_COST_PRICE + " TEXT, " +
            ORDDET_PIECE_QTY + " TEXT, " +
            ORDDET_SELL_PRICE + " TEXT, " +
            ORDDET_BSELL_PRICE + " TEXT, " +
            ORDDET_SEQ_NO + " TEXT, " +
            ORDDET_TAX_COM_CODE + " TEXT, " +
            ORDDET_TSELL_PRICE + " TEXT, " +
            ORDDET_BTSELL_PRICE + " TEXT, " +
            ORDDET_TXN_TYPE + " TEXT, " +
            ORDDET_LOC_CODE + " TEXT, " +
            ORDDET_TXN_DATE + " TEXT, " +
            ORDDET_RECORD_ID + " TEXT, " +
            ORDDET_PDIS_AMT + " TEXT, " +
            ORDDET_IS_SYNCED + " TEXT, " +
            ORDDET_QOH + " TEXT, " +
            ORDDET_TYPE + " TEXT, " +
            ORDDET_SCHDISC + " TEXT, " +
            ORDDET_DIS_TYPE + " TEXT, " +
            ORDDET_QTY_SLAB_DISC + " TEXT, " +
            ORDDET_ORG_PRICE + " TEXT, " +
            ORDDET_DIS_FLAG + " TEXT, " +
            ORDDET_IS_ACTIVE  + " TEXT); ";

    public OrderDetailController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {

        dB = dbHelper.getWritableDatabase();

    }

    @SuppressWarnings("static-access")
    public int createOrUpdateOrdDet(ArrayList<OrderDetail> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (OrderDetail ordDet : list) {

                Cursor cursor = null;
                ContentValues values = new ContentValues();

//                String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FORDDET + " WHERE " + dbHelper.FORDDET_ITEM_CODE
//                        + " = '" + ordDet.getFORDERDET_ITEMCODE() + "' and "+ dbHelper.FORDDET_TYPE
//                        + " = '" + ordDet.getFORDERDET_TYPE() +  "' ";

                String selectQuery = "SELECT * FROM " + TABLE_FORDDET + " WHERE " + FORDDET_ITEM_CODE
                        + " = '" + ordDet.getFORDERDET_ITEMCODE() + "' and "+ FORDDET_TYPE
                        + " = '" + ordDet.getFORDERDET_TYPE() +  "' and "+ REFNO
                        + " = '" + ordDet.getFORDERDET_REFNO() + "' ";

                cursor = dB.rawQuery(selectQuery, null);
                // commented due to table changed

                values.put(FORDDET_AMT, ordDet.getFORDERDET_AMT());
                values.put(FORDDET_ITEM_CODE, ordDet.getFORDERDET_ITEMCODE());
                values.put(FORDDET_PRIL_CODE, ordDet.getFORDERDET_PRILCODE());
                values.put(FORDDET_QTY, ordDet.getFORDERDET_QTY());
                values.put(REFNO, ordDet.getFORDERDET_REFNO());
                values.put(FORDDET_SELL_PRICE, ordDet.getFORDERDET_PRICE());
                values.put(FORDDET_IS_ACTIVE, ordDet.getFORDERDET_IS_ACTIVE());
                values.put(FORDDET_ITEMNAME, ordDet.getFORDERDET_ITEMNAME());
                values.put(FORDDET_BAL_QTY, ordDet.getFORDERDET_BALQTY());
                values.put(FORDDET_B_AMT, ordDet.getFORDERDET_BAMT());
                values.put(FORDDET_B_DIS_AMT, ordDet.getFORDERDET_BDISAMT());
                values.put(FORDDET_BP_DIS_AMT, ordDet.getFORDERDET_BPDISAMT());
                values.put(FORDDET_BT_TAX_AMT, ordDet.getFORDERDET_BTAXAMT());
                values.put(FORDDET_TAX_AMT, ordDet.getFORDERDET_TAXAMT());
                values.put(FORDDET_DIS_AMT, ordDet.getFORDERDET_DISAMT());
                values.put(FORDDET_DIS_PER, ordDet.getFORDERDET_SCHDISPER());
                values.put(FORDDET_PICE_QTY, ordDet.getFORDERDET_PICE_QTY());
                values.put(FORDDET_SELL_PRICE, ordDet.getFORDERDET_SELLPRICE());
                values.put(FORDDET_B_SELL_PRICE, ordDet.getFORDERDET_BSELLPRICE());
                values.put(FORDDET_SEQNO, ordDet.getFORDERDET_SEQNO());
                values.put(FORDDET_TAX_COM_CODE, ordDet.getFORDERDET_TAXCOMCODE());
                values.put(FORDDET_T_SELL_PRICE, ordDet.getFORDERDET_TSELLPRICE());
                values.put(FORDDET_BT_SELL_PRICE, ordDet.getFORDERDET_BTSELLPRICE());
                values.put(FORDDET_TXN_TYPE, ordDet.getFORDERDET_TXNTYPE());
                values.put(dbHelper.TXNDATE, ordDet.getFORDERDET_TXNDATE());
                values.put(FORDDET_RECORD_ID, ordDet.getFORDERDET_RECORDID());
                values.put(FORDDET_P_DIS_AMT, ordDet.getFORDERDET_PDISAMT());
                values.put(FORDDET_TYPE, ordDet.getFORDERDET_TYPE());
                values.put(FORDDET_PICE_QTY, ordDet.getFORDERDET_PICE_QTY());
                values.put(FORDDET_CASE_QTY, ordDet.getFORDERDET_CASES());
                values.put(FORDDET_COSTPRICE, ordDet.getFORDERDET_COSTPRICE());
                values.put(FORDDET_DISFLAG, ordDet.getFORDERDET_DISFLAG());
                values.put(FORDDET_REA_CODE, ordDet.getFORDERDET_REACODE());
                values.put(FORDDET_DIS_VAL_AMT, ordDet.getFORDERDET_DIS_VAL_AMT());

                int cn = cursor.getCount();

                if (cn > 0) {
                    count = dB.update(TABLE_FORDDET, values, FORDDET_TYPE+ " = '"+ordDet.getFORDERDET_TYPE()+"' and "+REFNO+ " = '"+ordDet.getFORDERDET_REFNO()+"' and "+FORDDET_ITEM_CODE+ " = '"+ordDet.getFORDERDET_ITEMCODE()+"'", null);
                } else {
                    count = (int) dB.insert(TABLE_FORDDET, null, values);
                }

                cursor.close();
            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }
        return count;

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public ArrayList<OrderDetail> getTodayOrders() {
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();

        try {
            String selectQuery = "select det.ItemCode, det.Amt,det.Qty from OrderDetail det" +
                    //			" fddbnote fddb where hed.refno = det.refno and det.FPRECDET_REFNO1 = fddb.refno and hed.txndate = '2019-04-12'";
                    "  where det.txndate = '" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate) +"'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                OrderDetail recDet = new OrderDetail();

//
                recDet.setFORDERDET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
                recDet.setFORDERDET_AMT(cursor.getString(cursor.getColumnIndex(InvDetController.FINVDET_AMT)));
                recDet.setFORDERDET_QTY(cursor.getString(cursor.getColumnIndex(InvDetController.FINVDET_QTY)));
               // recDet.setFORDERDET_LOCCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_DEBCODE)));
                //TODO :set  discount, free

                list.add(recDet);
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
    public int getItemCountForUpload(String refNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            String selectQuery = "SELECT count(oh.RefNo) as RefNo FROM " + TABLE_ORDER_DETAIL + " od, OrderHeader oh WHERE  od." + DatabaseHelper.REFNO + "='" + refNo + "' and oh.isSynced = '0' and od.refNo = oh.refNo";
            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                return Integer.parseInt(cursor.getString(cursor.getColumnIndex("RefNo")));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dB.close();
        }
        return 0;

    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public int getItemCountForSave(String refNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            String selectQuery = "SELECT count(oh.RefNo) as RefNo FROM " + TABLE_ORDER_DETAIL + " od, OrderHeader oh WHERE  od." + DatabaseHelper.REFNO + "='" + refNo + "' and od."+ORDDET_IS_ACTIVE + " = '1' and oh.isSynced = '0' and od.refNo = oh.refNo";
            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                return Integer.parseInt(cursor.getString(cursor.getColumnIndex("RefNo")));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dB.close();
        }
        return 0;

    }
    // *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

    @SuppressWarnings("static-access")
    public int restData(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_FORDDET + " WHERE " + dbHelper.REFNO + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);
            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.delete(TABLE_FORDDET, dbHelper.REFNO + " ='" + refno + "'", null);
                Log.v("Success Stauts", count + "");
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

    @SuppressWarnings("static-access")
    public int restFreeIssueData(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_FORDDET + " WHERE " + dbHelper.REFNO + " ='" + refno + "' AND " + FORDDET_TYPE + " = 'FD'";
            cursor = dB.rawQuery(selectQuery, null);
            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.delete(TABLE_FORDDET, dbHelper.REFNO + " ='" + refno + "' AND " + FORDDET_TYPE + " = 'FD'", null);
                Log.v("Success Stauts", count + "");
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

	/*-*-**-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<OrderDetail> getAllOrderDetails(String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();

        String selectQuery = "select * from " + TABLE_FORDDET + " WHERE "
                + dbHelper.REFNO + " = '" + refno + "' and "
                + FORDDET_TYPE + " <> 'FD' and "
                + FORDDET_IS_ACTIVE +" = '1'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                OrderDetail ordDet = new OrderDetail();


                //commented due to table changed

                ordDet.setFORDERDET_ID(cursor.getString(cursor.getColumnIndex(FORDDET_ID)));
                ordDet.setFORDERDET_AMT(cursor.getString(cursor.getColumnIndex(FORDDET_AMT)));
                ordDet.setFORDERDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_ITEM_CODE)));
                ordDet.setFORDERDET_PRILCODE(cursor.getString(cursor.getColumnIndex(FORDDET_PRIL_CODE)));
                ordDet.setFORDERDET_QTY(cursor.getString(cursor.getColumnIndex(FORDDET_QTY)));
                ordDet.setFORDERDET_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
                ordDet.setFORDERDET_PRICE(cursor.getString(cursor.getColumnIndex(FORDDET_SELL_PRICE)));
                ordDet.setFORDERDET_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FORDDET_IS_ACTIVE)));
                ordDet.setFORDERDET_ITEMNAME(cursor.getString(cursor.getColumnIndex(FORDDET_ITEMNAME)));
                ordDet.setFORDERDET_TAXCOMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_TAX_COM_CODE)));
                ordDet.setFORDERDET_CASES(cursor.getString(cursor.getColumnIndex(FORDDET_CASE_QTY)));
                ordDet.setFORDERDET_PICE_QTY(cursor.getString(cursor.getColumnIndex(FORDDET_PICE_QTY)));
                ordDet.setFORDERDET_TYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TYPE)));
                ordDet.setFORDERDET_TXNTYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TXN_TYPE)));

                list.add(ordDet);

            }
            cursor.close();

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return list;
    }

    public ArrayList<OrderDetail> getAllOrderDetailsForTaxUpdate(String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();

        String selectQuery = "select * from " + TABLE_FORDDET + " WHERE "
                + dbHelper.REFNO + "='" + refno + "' and "
                + FORDDET_TYPE + "<>'" + "FD" + "' and "
                + FORDDET_IS_ACTIVE +" = '0'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                OrderDetail ordDet = new OrderDetail();


                //commented due to table changed

                ordDet.setFORDERDET_ID(cursor.getString(cursor.getColumnIndex(FORDDET_ID)));
                ordDet.setFORDERDET_AMT(cursor.getString(cursor.getColumnIndex(FORDDET_AMT)));
                ordDet.setFORDERDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_ITEM_CODE)));
                ordDet.setFORDERDET_PRILCODE(cursor.getString(cursor.getColumnIndex(FORDDET_PRIL_CODE)));
                ordDet.setFORDERDET_QTY(cursor.getString(cursor.getColumnIndex(FORDDET_QTY)));
                ordDet.setFORDERDET_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
                ordDet.setFORDERDET_PRICE(cursor.getString(cursor.getColumnIndex(FORDDET_SELL_PRICE)));
                ordDet.setFORDERDET_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FORDDET_IS_ACTIVE)));
                ordDet.setFORDERDET_ITEMNAME(cursor.getString(cursor.getColumnIndex(FORDDET_ITEMNAME)));
                ordDet.setFORDERDET_TAXCOMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_TAX_COM_CODE)));
                ordDet.setFORDERDET_TYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TYPE)));
                ordDet.setFORDERDET_TXNTYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TXN_TYPE)));

                list.add(ordDet);

            }
            cursor.close();

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return list;
    }


    public ArrayList<OrderDetail> getSAForFreeIssueCalc(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();

        String selectQuery = "select * from " + TABLE_FORDDET + " WHERE " + FORDDET_TYPE + "='SA' AND " + dbHelper.REFNO + "='" + refno + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                OrderDetail ordDet = new OrderDetail();

                ordDet.setFORDERDET_ID(cursor.getString(cursor.getColumnIndex(FORDDET_ID)));
                ordDet.setFORDERDET_AMT(cursor.getString(cursor.getColumnIndex(FORDDET_AMT)));
                ordDet.setFORDERDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_ITEM_CODE)));
                ordDet.setFORDERDET_PRILCODE(cursor.getString(cursor.getColumnIndex(FORDDET_PRIL_CODE)));
                ordDet.setFORDERDET_QTY(cursor.getString(cursor.getColumnIndex(FORDDET_QTY)));
                ordDet.setFORDERDET_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
                ordDet.setFORDERDET_PRICE(cursor.getString(cursor.getColumnIndex(FORDDET_SELL_PRICE)));
                ordDet.setFORDERDET_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FORDDET_IS_ACTIVE)));
                ordDet.setFORDERDET_TYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TYPE)));
                ordDet.setFORDERDET_TXNTYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TXN_TYPE)));

                // this line add due to SortDiscount needs SCHDISPER for the calculation line no 123
                ordDet.setFORDERDET_SCHDISPER(cursor.getString(cursor.getColumnIndex(FORDDET_DIS_PER)));
                //added by rashmi because due to SortDiscount needs - 2019-11-14
                ordDet.setFORDERDET_SCHDISC(cursor.getString(cursor.getColumnIndex(FORDDET_DIS_AMT)));
                // this line add due to no txndate set
                ordDet.setFORDERDET_TXNDATE(cursor.getString(cursor.getColumnIndex(dbHelper.TXNDATE)));

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

    public ArrayList<OrderDetail> getTodayOrderDets(String refno) {

        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();

       // String selectQuery = "select * from " + dbHelper.TABLE_ORDER_DETAIL + " WHERE "
        String selectQuery = "select * from FOrddet WHERE "
                + dbHelper.REFNO + "='" + refno + "' and  txndate = '" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate) +"'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                OrderDetail ordDet = new OrderDetail();

                ordDet.setFORDERDET_ID(cursor.getString(cursor.getColumnIndex(FORDDET_ID)));
                ordDet.setFORDERDET_AMT(cursor.getString(cursor.getColumnIndex(FORDDET_AMT)));
                ordDet.setFORDERDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_ITEM_CODE)));
                ordDet.setFORDERDET_PRILCODE(cursor.getString(cursor.getColumnIndex(FORDDET_PRIL_CODE)));
                ordDet.setFORDERDET_QTY(cursor.getString(cursor.getColumnIndex(FORDDET_QTY)));
                ordDet.setFORDERDET_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
                ordDet.setFORDERDET_PRICE(cursor.getString(cursor.getColumnIndex(FORDDET_SELL_PRICE)));
                ordDet.setFORDERDET_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FORDDET_IS_ACTIVE)));
                ordDet.setFORDERDET_TYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TYPE)));
                ordDet.setFORDERDET_TXNTYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TXN_TYPE)));

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
    @SuppressWarnings("static-access")
    public int InactiveStatusUpdate(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_FORDDET + " WHERE " + dbHelper.REFNO + " = '" + refno + "'";

            cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(FORDDET_IS_ACTIVE, "0");

            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.update(TABLE_FORDDET, values, dbHelper.REFNO + " =?", new String[] { String.valueOf(refno) });
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

    @SuppressWarnings("static-access")
    public int deleteOrdDetByID(String id) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_ORDER_DETAIL + " WHERE " + ORDDET_ID + "='" + id + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_ORDER_DETAIL, ORDDET_ID + "='" + id + "'", null);
                Log.v("OrdDet Deleted ", success + "");
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


    public ArrayList<OrderDetail> getAllActives(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();

        String selectQuery = "select * from " + TABLE_FORDDET + " WHERE " + dbHelper.REFNO
                + "='" + refno + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                OrderDetail ordDet = new OrderDetail();
                //commented due to table changed

                ordDet.setFORDERDET_ID(cursor.getString(cursor.getColumnIndex(FORDDET_ID)));
                ordDet.setFORDERDET_AMT(cursor.getString(cursor.getColumnIndex(FORDDET_AMT)));
                ordDet.setFORDERDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_ITEM_CODE)));
                ordDet.setFORDERDET_PRILCODE(cursor.getString(cursor.getColumnIndex(FORDDET_PRIL_CODE)));
                ordDet.setFORDERDET_QTY(cursor.getString(cursor.getColumnIndex(FORDDET_QTY)));
                ordDet.setFORDERDET_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
                ordDet.setFORDERDET_PRICE(cursor.getString(cursor.getColumnIndex(FORDDET_SELL_PRICE)));
                ordDet.setFORDERDET_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FORDDET_IS_ACTIVE)));
                ordDet.setFORDERDET_TYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TYPE)));
                ordDet.setFORDERDET_TXNTYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TXN_TYPE)));

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

    public boolean isAnyActiveOrders()
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "select * from " + TABLE_FORDDET + " WHERE " + FORDDET_IS_ACTIVE + "='" + "1" + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            if (cursor.getCount()>0)
            {
                return true;
            }
            else
            {
                return false;
            }

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return false;
    }

    public ArrayList<OrderDetail> getAllUnSync(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();

        String selectQuery = "select * from " + TABLE_FORDDET + " WHERE " + REFNO
                + "='" + refno + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                OrderDetail ordDet = new OrderDetail();

                ordDet.setFORDERDET_ID(cursor.getString(cursor.getColumnIndex(FORDDET_ID)));
                ordDet.setFORDERDET_AMT(cursor.getString(cursor.getColumnIndex(FORDDET_AMT)));
                ordDet.setFORDERDET_BAMT(cursor.getString(cursor.getColumnIndex(FORDDET_B_AMT)));
                ordDet.setFORDERDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_ITEM_CODE)));
                ordDet.setFORDERDET_PRILCODE(cursor.getString(cursor.getColumnIndex(FORDDET_PRIL_CODE)));
                ordDet.setFORDERDET_QTY(cursor.getString(cursor.getColumnIndex(FORDDET_QTY)));
                ordDet.setFORDERDET_REFNO(cursor.getString(cursor.getColumnIndex(dbHelper.REFNO)));
                ordDet.setFORDERDET_SELLPRICE(cursor.getString(cursor.getColumnIndex(FORDDET_SELL_PRICE)));
                ordDet.setFORDERDET_BSELLPRICE(cursor.getString(cursor.getColumnIndex(FORDDET_B_SELL_PRICE)));
                ordDet.setFORDERDET_TSELLPRICE(cursor.getString(cursor.getColumnIndex(FORDDET_T_SELL_PRICE)));
                ordDet.setFORDERDET_BTSELLPRICE(cursor.getString(cursor.getColumnIndex(FORDDET_BT_SELL_PRICE)));
                ordDet.setFORDERDET_COSTPRICE(cursor.getString(cursor.getColumnIndex(FORDDET_COSTPRICE)));
                ordDet.setFORDERDET_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FORDDET_IS_ACTIVE)));
                ordDet.setFORDERDET_TAXCOMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_TAX_COM_CODE)));
                ordDet.setFORDERDET_TYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TYPE)));
                ordDet.setFORDERDET_TXNTYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TXN_TYPE)));
                ordDet.setFORDERDET_TXNDATE(cursor.getString(cursor.getColumnIndex(dbHelper.TXNDATE)));
                ordDet.setFORDERDET_SEQNO(cursor.getString(cursor.getColumnIndex(FORDDET_SEQNO)));
                ordDet.setFORDERDET_CASES(cursor.getString(cursor.getColumnIndex(FORDDET_CASE_QTY)));
                ordDet.setFORDERDET_PICE_QTY(cursor.getString(cursor.getColumnIndex(FORDDET_PICE_QTY)));
                ordDet.setFORDERDET_BALQTY(cursor.getString(cursor.getColumnIndex(FORDDET_BAL_QTY)));
                ordDet.setFORDERDET_DISPER(cursor.getString(cursor.getColumnIndex(FORDDET_DIS_PER)));//send dis per
                ordDet.setFORDERDET_TAXAMT(cursor.getString(cursor.getColumnIndex(FORDDET_TAX_AMT)));
                ordDet.setFORDERDET_BTAXAMT(cursor.getString(cursor.getColumnIndex(FORDDET_BT_TAX_AMT)));
                ordDet.setFORDERDET_DISAMT(cursor.getString(cursor.getColumnIndex(FORDDET_DIS_AMT)));
                ordDet.setFORDERDET_DISFLAG(cursor.getString(cursor.getColumnIndex(FORDDET_DISFLAG)));
                ordDet.setFORDERDET_DIS_VAL_AMT(cursor.getString(cursor.getColumnIndex(FORDDET_DIS_VAL_AMT)));
                ordDet.setFORDERDET_REACODE(cursor.getString(cursor.getColumnIndex(FORDDET_REA_CODE)));

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

    public void mDeleteRecords(String RefNo, String itemCode, String type) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {
            dB.delete(TABLE_FORDDET, REFNO + " = '" + RefNo + "'  AND " + FORDDET_ITEM_CODE + " ='" + itemCode + "' AND "+ FORDDET_TYPE + " ='" + type + "'", null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public void deleteRecords(String RefNo, String type) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {
            dB.delete(TABLE_FORDDET, REFNO + " ='" + RefNo + "' and "+FORDDET_TYPE+ " ='" + type + "'" , null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
    }

    public String getLastSequnenceNo(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        try {
            String selectQuery = "SELECT Max(seqno) as seqno FROM " + TABLE_FORDDET + " WHERE " + dbHelper.REFNO + "='" + RefNo + "'";
            Cursor cursor = dB.rawQuery(selectQuery, null);
            cursor.moveToFirst();

            return (cursor.getInt(cursor.getColumnIndex("seqno")) + 1) + "";
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        } finally {
            dB.close();
        }
    }

    public int getItemCount(String refNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            String selectQuery = "SELECT count(RefNo) as RefNo FROM " + TABLE_FORDDET + " WHERE  " + REFNO + "='" + refNo + "'";
            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {
                return Integer.parseInt(cursor.getString(cursor.getColumnIndex("RefNo")));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dB.close();
        }
        return 0;

    }

    public ArrayList<OrderDetail> getAllItemsAddedInCurrentSale(String refNo,String type,String type2)
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();
        String selectQuery = "select ItemCode,Qty,Amt,ReaCode from " + TABLE_FORDDET + " WHERE " + REFNO + "='" + refNo + "' AND ("+ FORDDET_TYPE + "= '"+type+"' OR "+ FORDDET_TYPE +" = '"+type2+"')" ;
        Cursor cursor = dB.rawQuery(selectQuery, null);

        try
        {
            while (cursor.moveToNext())
            {
                OrderDetail orderDetail = new OrderDetail();
                // commented due to table changed
                orderDetail.setFORDERDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_ITEM_CODE)));
                orderDetail.setFORDERDET_QTY(cursor.getString(cursor.getColumnIndex(FORDDET_QTY)));
                orderDetail.setFORDERDET_AMT(cursor.getString(cursor.getColumnIndex(FORDDET_AMT)));
                orderDetail.setFORDERDET_REACODE(cursor.getString(cursor.getColumnIndex(FORDDET_REA_CODE)));
                list.add(orderDetail);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.v(TAG + " Exception", ex.toString());
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
            dB.close();
        }
        return list;
    }



    public ArrayList<OrderDetail> getAllItemsForPrint(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();

        String selectQuery = "select Itemcode,Qty,Amt,TSellPrice,Types from " + TABLE_FORDDET + " WHERE " + REFNO + "='" + refno + "'";
     try {

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                OrderDetail order = new OrderDetail();
                // commented due to changed table

                order.setFORDERDET_AMT(cursor.getString(cursor.getColumnIndex(FORDDET_AMT)));
                order.setFORDERDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_ITEM_CODE)));
                order.setFORDERDET_QTY(cursor.getString(cursor.getColumnIndex(FORDDET_QTY)));
                order.setFORDERDET_TYPE(cursor.getString(cursor.getColumnIndex(FORDDET_TYPE)));
                order.setFORDERDET_TSELLPRICE(cursor.getString(cursor.getColumnIndex(FORDDET_T_SELL_PRICE)));
                order.setFORDERDET_REFNO(refno);

                list.add(order);
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




    public void UpdateItemTaxInfoWithDiscount(ArrayList<OrderDetail> list, String debtorCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        double totTax = 0, totalAmt = 0, sellPrice = 0, tSellPrice = 0, totDisc=0, disR=0;

        try {

            for (OrderDetail soDet : list) {

                /* Calculate only for MR or UR */
                if (soDet.getFORDERDET_TYPE().equals("SA")) {

                    String sArray[] = new TaxDetController(context).calculateTaxForwardFromDebTax(debtorCode, soDet.getFORDERDET_ITEMCODE(), Double.parseDouble(soDet.getFORDERDET_AMT()));

                    sellPrice = Double.parseDouble(sArray[0])/Double.parseDouble(soDet.getFORDERDET_QTY());
                    if (Double.parseDouble(soDet.getFORDERDET_SCHDISC())>0.0)
                    {
                        tSellPrice = (Double.parseDouble(sArray[0])+ Double.parseDouble(soDet.getFORDERDET_SCHDISC()))/Double.parseDouble(soDet.getFORDERDET_QTY());
                        disR = ((Double.parseDouble(soDet.getFORDERDET_SCHDISC()))/(Double.parseDouble(sArray[0])+ Double.parseDouble(soDet.getFORDERDET_SCHDISC())))* 100;
                        totDisc += Double.parseDouble(soDet.getFORDERDET_SCHDISC());
                    }
                    else
                    {
                        tSellPrice = Double.parseDouble(sArray[0])/Double.parseDouble(soDet.getFORDERDET_QTY());
                    }

                    totTax += Double.parseDouble(sArray[1]);
                    totalAmt += Double.parseDouble(sArray[0]);

                    String updateQuery = "UPDATE FOrddet SET TaxAmt='" + sArray[1] + "', Amt='" + sArray[0] + "', BAmt='" + sArray[0] + "', DisAmt='" + String.valueOf(disR) + "', TSellPrice='" + tSellPrice + "', BTSellPrice ='" + tSellPrice + "' where ItemCode ='" + soDet.getFORDERDET_ITEMCODE() + "' AND refno='" + soDet.getFORDERDET_REFNO() + "' AND Type = 'SA' ";
                    dB.execSQL(updateQuery);
                }
            }
            /* Update sales return Header TotalTax */
            dB.execSQL("UPDATE FOrdHed SET TotalTax='" + totTax + "',TotalDis='" + totDisc + "',TotalAmt='" + totalAmt + "' WHERE refno='" + list.get(0).getFORDERDET_REFNO() + "'");

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

    }
    public int updateProductPrice(String itemCode, String price,String amt, String type) {
        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(FORDDET_SELL_PRICE, price);
            values.put(FORDDET_AMT, amt);
            count=(int)dB.update(TABLE_FORDDET, values, FORDDET_ITEM_CODE
                    + " = '" + itemCode + "' and "+FORDDET_TYPE + " = '" + type + "' ", null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
        return  count;
    }

    public void updateDiscountSO(OrderDetail ordDet, double discount, String discType, String debCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            OrderDisc orderDisc = new OrderDisc();
            orderDisc.setRefNo(ordDet.getFORDERDET_REFNO());
            orderDisc.setTxnDate(ordDet.getFORDERDET_TXNDATE());
            orderDisc.setItemCode(ordDet.getFORDERDET_ITEMCODE());
            orderDisc.setDisAmt(String.format("%.2f", discount));
            String disFlag = "1";

            new OrderDiscController(context).UpdateOrderDiscount(orderDisc, ordDet.getFORDERDET_DISC_REF(), ordDet.getFORDERDET_SCHDISPER());
            //String amt = String.format(String.format("%.2f", (Double.parseDouble(ordDet.getFORDERDET_AMT()) + Double.parseDouble(ordDet.getFORDERDET_SCHDISC())) - discount));
            String forwardAmt[] = new TaxDetController(context).calculateTaxForwardFromDebTax(debCode, ordDet.getFORDERDET_ITEMCODE(),Double.parseDouble(ordDet.getFORDERDET_AMT()));
            String forwardAmtWithoutDis = String.valueOf(Double.parseDouble(forwardAmt[0]) - discount);
            String reverseAmtWithoutDis = new TaxDetController(context).calculateReverseTaxFromDebTax(debCode, ordDet.getFORDERDET_ITEMCODE(),new BigDecimal(forwardAmtWithoutDis));
            String forwardSellPrice = String.valueOf((Double.parseDouble(reverseAmtWithoutDis)+ discount)/Double.parseDouble(ordDet.getFORDERDET_QTY()));
            String updateQuery = "UPDATE FOrddet SET " +
                    FORDDET_SCH_DISPER + "='" +
                    ordDet.getFORDERDET_SCHDISPER() + "'," + FORDDET_DIS_VAL_AMT + " ='" + String.format("%.2f", discount) + "'," + FORDDET_AMT + "='" + reverseAmtWithoutDis + "'," + FORDDET_DISFLAG + "='" + disFlag + "'," + FORDDET_SELL_PRICE + "='" + forwardSellPrice + "'," + FORDDET_BT_SELL_PRICE + "='" + forwardSellPrice + "'," + FORDDET_B_AMT + "='" + reverseAmtWithoutDis + "'," + FORDDET_DISCTYPE + "='" + discType + "' WHERE Itemcode ='" + ordDet.getFORDERDET_ITEMCODE() + "' AND types='SA' AND RefNo ='" + ordDet.getFORDERDET_REFNO() + "'";
            dB.execSQL(updateQuery);

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

    }

//    public void updateDiscount(OrderDetail ordDet, double discount, String discType) {
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
//            OrderDisc orderDisc = new OrderDisc();
//            orderDisc.setRefNo(ordDet.getFORDERDET_REFNO());
//            orderDisc.setTxnDate(ordDet.getFORDERDET_TXNDATE());
//            orderDisc.setItemCode(ordDet.getFORDERDET_ITEMCODE());
//            orderDisc.setDisAmt(String.format("%.2f", discount));
//
//            new OrderDiscController(context).UpdateOrderDiscount(orderDisc, ordDet.getFORDERDET_DISC_REF(), ordDet.getFORDERDET_SCHDISPER());
//            String amt = String.format(String.format("%.2f", (Double.parseDouble(ordDet.getFORDERDET_AMT()) + Double.parseDouble(ordDet.getFORDERDET_SCHDISC())) - discount));
//
//            String updateQuery = "UPDATE FTranSODet SET " +
//                    FORDDET_SCH_DISPER + "='" + ordDet.getFORDERDET_SCHDISPER() + "',"
//                    + FORDDET_DIS_VAL_AMT + " ='" + String.format("%.2f", discount) + "',"
//                    + FORDDET_AMT + "='" + amt + "',"
//                    + FORDDET_B_AMT + "='" + amt + "',"
//                    + FORDDET_DISCTYPE + "='" + discType + "' WHERE Itemcode ='" + ordDet.getFORDERDET_ITEMCODE() + "' AND type='SA' AND RefNo ='" + ordDet.getFORDERDET_REFNO() + "'";
//            dB.execSQL(updateQuery);
//
//        } catch (Exception e) {
//            Log.v(TAG + " Exception", e.toString());
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            dB.close();
//        }
//
//    }

    public void updateDiscount(OrderDetail ordDet, double discount, String discType) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            OrderDisc orderDisc = new OrderDisc();
            orderDisc.setRefNo(ordDet.getFORDERDET_REFNO());
            orderDisc.setTxnDate(ordDet.getFORDERDET_TXNDATE());
            orderDisc.setItemCode(ordDet.getFORDERDET_ITEMCODE());
            orderDisc.setDisAmt(String.format("%.2f", discount));

            new OrderDiscController(context).UpdateOrderDiscount(orderDisc, ordDet.getFORDERDET_DISC_REF(), ordDet.getFORDERDET_SCHDISPER());
            String amt = String.format(String.format("%.2f", (Double.parseDouble(ordDet.getFORDERDET_AMT())) - discount));

            String updateQuery = "UPDATE FOrddet SET " +
                    FORDDET_SCH_DISPER + "='" + ordDet.getFORDERDET_SCHDISPER() + "',"
                    + FORDDET_DIS_VAL_AMT + " ='" + String.format("%.2f", discount) + "',"
                    + FORDDET_AMT + "='" + amt + "',"
                    + FORDDET_B_AMT + "='" + amt + "',"
                    + FORDDET_DISCTYPE + "='" + discType + "' WHERE Itemcode ='" + ordDet.getFORDERDET_ITEMCODE() + "' AND types='SA' AND RefNo ='" + ordDet.getFORDERDET_REFNO() + "'";
            dB.execSQL(updateQuery);

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

    }

    public ArrayList<OrderDetail> getAllFreeIssue(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();

        String selectQuery = "select * from " + TABLE_FORDDET + " WHERE " + REFNO + "='" + refno + "' AND " + FORDDET_TYPE  + "='FD'" ;

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {

            while (cursor.moveToNext()) {

                OrderDetail tranSODet=new OrderDetail();
                tranSODet.setFORDERDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_ITEM_CODE)));
                tranSODet.setFORDERDET_QTY(cursor.getString(cursor.getColumnIndex(FORDDET_QTY)));
                list.add(tranSODet);
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
//set order details in order by pass the order list
///rashmi -2019-10-21
    public ArrayList<OrderDetail> mUpdatePrsSales(ArrayList<PreProduct> list,String refno)
    {

        ArrayList<OrderDetail> SOList = new ArrayList<OrderDetail>();

        String UnitPrice = "";
        double amt = 0.0;
        String qty = "";
         for (PreProduct tempOrder : list) {
             if (tempOrder.getPREPRODUCT_TXN_TYPE().equals("SA")){
                 OrderDetail SODet = new OrderDetail();
                 qty = "" + (Integer.parseInt(tempOrder.getPREPRODUCT_QTY()) + (Integer.parseInt(tempOrder.getPREPRODUCT_CASE()) * Integer.parseInt(tempOrder.getPREPRODUCT_UNIT())));
                 UnitPrice = ""+(Double.parseDouble(tempOrder.getPREPRODUCT_PRICE())/Integer.parseInt(tempOrder.getPREPRODUCT_UNIT()));
                 amt = Double.parseDouble(UnitPrice) * Double.parseDouble(qty);
                String TaxedAmt = "0.00";
                SODet.setFORDERDET_AMT(String.valueOf(amt));
                SODet.setFORDERDET_ITEMCODE(tempOrder.getPREPRODUCT_ITEMCODE());
                SODet.setFORDERDET_PRILCODE("WSP001");
                SODet.setFORDERDET_QTY(qty);
                SODet.setFORDERDET_REFNO(refno);
                SODet.setFORDERDET_PRICE("0.00");
                SODet.setFORDERDET_IS_ACTIVE("1");
                SODet.setFORDERDET_BALQTY(qty);
                SODet.setFORDERDET_BAMT(String.valueOf(amt));
                SODet.setFORDERDET_BDISAMT("0");
                SODet.setFORDERDET_BPDISAMT("0");
                SODet.setFORDERDET_BTAXAMT(TaxedAmt);
                SODet.setFORDERDET_TAXAMT(TaxedAmt);
                SODet.setFORDERDET_DISAMT("0");
                SODet.setFORDERDET_SCHDISPER("0");
                //SODet.setFORDERDET_COMP_DISPER(new ControlDS(getActivity()).getCompanyDisc() + "");
                SODet.setFORDERDET_BRAND_DISPER("0");
                SODet.setFORDERDET_BRAND_DISC("0");
                SODet.setFORDERDET_COMP_DISC("0");
                SODet.setFORDERDET_COSTPRICE(new ItemController(context).getCostPriceItemCode(tempOrder.getPREPRODUCT_ITEMCODE()));
                SODet.setFORDERDET_PICE_QTY(tempOrder.getPREPRODUCT_QTY());
                SODet.setFORDERDET_SELLPRICE(String.valueOf(UnitPrice));
                SODet.setFORDERDET_BSELLPRICE(String.valueOf(UnitPrice));
                SODet.setFORDERDET_SEQNO(new OrderDetailController(context).getLastSequnenceNo(refno));
                SODet.setFORDERDET_TAXCOMCODE("0");
               // SODet.setFORDERDET_TAXCOMCODE(new ItemController(context).getTaxComCodeByItemCodeBeforeDebTax(tempOrder.getPREPRODUCT_ITEMCODE(), SharedPref.getInstance(context).getSelectedDebCode()));
                SODet.setFORDERDET_BTSELLPRICE(String.valueOf(UnitPrice));
                SODet.setFORDERDET_TSELLPRICE(String.valueOf(UnitPrice));
                SODet.setFORDERDET_TXNTYPE("22");
                SODet.setFORDERDET_LOCCODE("MS");
                SODet.setFORDERDET_TXNDATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                SODet.setFORDERDET_RECORDID("");
                SODet.setFORDERDET_PDISAMT("0");
                SODet.setFORDERDET_IS_SYNCED("0");
                SODet.setFORDERDET_QOH(tempOrder.getPREPRODUCT_QOH());
                SODet.setFORDERDET_TYPE("SA");
                SODet.setFORDERDET_SCHDISC("0");
                SODet.setFORDERDET_DISCTYPE("");
                SODet.setFORDERDET_QTY_SLAB_DISC("0");
                SODet.setFORDERDET_ORG_PRICE(String.valueOf(UnitPrice));
                SODet.setFORDERDET_DISFLAG("0");
                SODet.setFORDERDET_CASES("" + tempOrder.getPREPRODUCT_CASE());
                SODet.setFORDERDET_PICE_QTY("" + tempOrder.getPREPRODUCT_QTY());
                SODet.setFORDERDET_REACODE("");

                SOList.add(SODet);
            }  else if(tempOrder.getPREPRODUCT_TXN_TYPE().equals("FI")){
                 OrderDetail SODet = new OrderDetail();
                 qty = "" + (Integer.parseInt(tempOrder.getPREPRODUCT_QTY()) + (Integer.parseInt(tempOrder.getPREPRODUCT_CASE()) * Integer.parseInt(tempOrder.getPREPRODUCT_UNIT())));
                 SODet.setFORDERDET_ID("0");
                 SODet.setFORDERDET_AMT("0");
                 SODet.setFORDERDET_BALQTY(qty);
                 SODet.setFORDERDET_BAMT("0");
                 SODet.setFORDERDET_BDISAMT("0");
                 SODet.setFORDERDET_BPDISAMT("0");
                 SODet.setFORDERDET_BSELLPRICE("0");
                 SODet.setFORDERDET_BTSELLPRICE("0.00");
                 SODet.setFORDERDET_DISAMT("0");
                 SODet.setFORDERDET_SCHDISPER("0");
                 SODet.setFORDERDET_FREEQTY("0");
                 SODet.setFORDERDET_ITEMCODE(tempOrder.getPREPRODUCT_ITEMCODE());
                 SODet.setFORDERDET_PDISAMT("0");
                 SODet.setFORDERDET_PRILCODE("WSP001");
                 SODet.setFORDERDET_QTY(qty);
                 SODet.setFORDERDET_PICE_QTY(tempOrder.getPREPRODUCT_QTY());
                 SODet.setFORDERDET_TYPE("FI");
                 SODet.setFORDERDET_RECORDID("");
                 SODet.setFORDERDET_REFNO(refno);
                 SODet.setFORDERDET_SELLPRICE("0");
                 SODet.setFORDERDET_SEQNO(new OrderDetailController(context).getLastSequnenceNo(refno));
                 SODet.setFORDERDET_TAXAMT("0");
                 SODet.setFORDERDET_TAXCOMCODE("0");   //ordDet.setFTRANSODET_TAXCOMCODE(new ItemsDS(getActivity()).getTaxComCodeByItemCodeFromDebTax(itemFreeIssue.getItems().getFITEM_ITEM_CODE(), mainActivity.selectedDebtor.getFDEBTOR_CODE()));
                 SODet.setFORDERDET_TIMESTAMP_COLUMN("");
                 SODet.setFORDERDET_TSELLPRICE("0.00");
                 SODet.setFORDERDET_TXNDATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                 SODet.setFORDERDET_TXNTYPE("27");
                 SODet.setFORDERDET_IS_ACTIVE("1");
                 SODet.setFORDERDET_LOCCODE("MS");
                 SODet.setFORDERDET_COSTPRICE(new ItemController(context).getCostPriceItemCode(tempOrder.getPREPRODUCT_ITEMCODE()));
                 SODet.setFORDERDET_BTAXAMT("0");
                 SODet.setFORDERDET_IS_SYNCED("0");
                 SODet.setFORDERDET_QOH("0");
                 SODet.setFORDERDET_SCHDISC("0");
                 SODet.setFORDERDET_BRAND_DISC("0");
                 SODet.setFORDERDET_BRAND_DISPER("0");
                 SODet.setFORDERDET_COMP_DISC("0");
                 SODet.setFORDERDET_COMP_DISPER("0");
                 SODet.setFORDERDET_DISCTYPE("");
                 SODet.setFORDERDET_PRICE("0.00");
                 SODet.setFORDERDET_ORG_PRICE("0");
                 SODet.setFORDERDET_DISFLAG("0");
                 SODet.setFORDERDET_REACODE("");
                 SODet.setFORDERDET_CASES("" + tempOrder.getPREPRODUCT_CASE());
                 SODet.setFORDERDET_PICE_QTY("" + tempOrder.getPREPRODUCT_QTY());
                 SOList.add(SODet);
             }else if(tempOrder.getPREPRODUCT_TXN_TYPE().equals("UR")){
                 OrderDetail SODet = new OrderDetail();
                 UnitPrice = ""+(Double.parseDouble(tempOrder.getPREPRODUCT_PRICE())/Integer.parseInt(tempOrder.getPREPRODUCT_UNIT()));
                 qty = "" + (Integer.parseInt(tempOrder.getPREPRODUCT_QTY()) + (Integer.parseInt(tempOrder.getPREPRODUCT_CASE()) * Integer.parseInt(tempOrder.getPREPRODUCT_UNIT())));
                 amt = Double.parseDouble(UnitPrice) * Double.parseDouble(qty);
                 String TaxedAmt = "0.00";
                 SODet.setFORDERDET_AMT("-"+String.valueOf(amt));
                 SODet.setFORDERDET_ITEMCODE(tempOrder.getPREPRODUCT_ITEMCODE());
                 SODet.setFORDERDET_PRILCODE("WSP001");
                 SODet.setFORDERDET_QTY(qty);
                 SODet.setFORDERDET_REFNO(refno);
                 SODet.setFORDERDET_PRICE("0.00");
                 SODet.setFORDERDET_IS_ACTIVE("1");
                 SODet.setFORDERDET_BALQTY(qty);
                 SODet.setFORDERDET_BAMT("-"+String.valueOf(amt));
                 SODet.setFORDERDET_BDISAMT("0");
                 SODet.setFORDERDET_BPDISAMT("0");
                 SODet.setFORDERDET_BTAXAMT(TaxedAmt);
                 SODet.setFORDERDET_TAXAMT(TaxedAmt);
                 SODet.setFORDERDET_DISAMT("0");
                 SODet.setFORDERDET_SCHDISPER("0");
                 //SODet.setFORDERDET_COMP_DISPER(new ControlDS(getActivity()).getCompanyDisc() + "");
                 SODet.setFORDERDET_BRAND_DISPER("0");
                 SODet.setFORDERDET_BRAND_DISC("0");
                 SODet.setFORDERDET_COMP_DISC("0");
                 SODet.setFORDERDET_COSTPRICE(new ItemController(context).getCostPriceItemCode(tempOrder.getPREPRODUCT_ITEMCODE()));
                 SODet.setFORDERDET_PICE_QTY(tempOrder.getPREPRODUCT_QTY());
                 SODet.setFORDERDET_SELLPRICE(String.valueOf((amt) / Double.parseDouble(SODet.getFORDERDET_QTY())));
                 SODet.setFORDERDET_BSELLPRICE(String.valueOf((amt) / Double.parseDouble(SODet.getFORDERDET_QTY())));
                 SODet.setFORDERDET_SEQNO(new OrderDetailController(context).getLastSequnenceNo(refno));
                 SODet.setFORDERDET_TAXCOMCODE("0");
                 SODet.setFORDERDET_BTSELLPRICE(String.valueOf(UnitPrice));
                 SODet.setFORDERDET_TSELLPRICE(String.valueOf(UnitPrice));
                 SODet.setFORDERDET_TXNTYPE("25");
                 SODet.setFORDERDET_LOCCODE("MS");
                 SODet.setFORDERDET_TXNDATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                 SODet.setFORDERDET_RECORDID("");
                 SODet.setFORDERDET_PDISAMT("0");
                 SODet.setFORDERDET_IS_SYNCED("0");
                 SODet.setFORDERDET_QOH(tempOrder.getPREPRODUCT_QOH());
                 SODet.setFORDERDET_TYPE("UR");
                 SODet.setFORDERDET_SCHDISC("0");
                 SODet.setFORDERDET_DISCTYPE("");
                 SODet.setFORDERDET_QTY_SLAB_DISC("0");
                 SODet.setFORDERDET_ORG_PRICE(String.valueOf(UnitPrice));
                 SODet.setFORDERDET_DISFLAG("0");
                 SODet.setFORDERDET_CASES("" + tempOrder.getPREPRODUCT_CASE());
                 SODet.setFORDERDET_PICE_QTY("" + tempOrder.getPREPRODUCT_QTY());
                 SODet.setFORDERDET_REACODE("");
                 SOList.add(SODet);
             }else{//MR type
                 OrderDetail SODet = new OrderDetail();
                // should check
                 if(tempOrder.getPREPRODUCT_CHANGED_PRICE().equals("0")) {
                     UnitPrice = ""+(Double.parseDouble(tempOrder.getPREPRODUCT_PRICE())/Integer.parseInt(tempOrder.getPREPRODUCT_UNIT()));
                 }else{
                     UnitPrice = "" + (Double.parseDouble(tempOrder.getPREPRODUCT_CHANGED_PRICE()));
                 }
                 qty = "" + (Integer.parseInt(tempOrder.getPREPRODUCT_QTY()) + (Integer.parseInt(tempOrder.getPREPRODUCT_CASE()) * Integer.parseInt(tempOrder.getPREPRODUCT_UNIT())));
                 amt = Double.parseDouble(UnitPrice) * Double.parseDouble(qty);
                 String TaxedAmt = "0.00";
                 SODet.setFORDERDET_AMT("-"+String.valueOf(amt));
                 SODet.setFORDERDET_ITEMCODE(tempOrder.getPREPRODUCT_ITEMCODE());
                 SODet.setFORDERDET_PRILCODE("WSP001");
                 SODet.setFORDERDET_QTY(qty);
                 SODet.setFORDERDET_REFNO(refno);
                 SODet.setFORDERDET_PRICE("0.00");
                 SODet.setFORDERDET_IS_ACTIVE("1");
                 SODet.setFORDERDET_BALQTY(qty);
                 SODet.setFORDERDET_BAMT("-"+String.valueOf(amt));
                 SODet.setFORDERDET_BDISAMT("0");
                 SODet.setFORDERDET_BPDISAMT("0");
                 SODet.setFORDERDET_BTAXAMT(TaxedAmt);
                 SODet.setFORDERDET_TAXAMT(TaxedAmt);
                 SODet.setFORDERDET_DISAMT("0");
                 SODet.setFORDERDET_SCHDISPER("0");
                 //SODet.setFORDERDET_COMP_DISPER(new ControlDS(getActivity()).getCompanyDisc() + "");
                 SODet.setFORDERDET_BRAND_DISPER("0");
                 SODet.setFORDERDET_BRAND_DISC("0");
                 SODet.setFORDERDET_COMP_DISC("0");
                 SODet.setFORDERDET_COSTPRICE(new ItemController(context).getCostPriceItemCode(tempOrder.getPREPRODUCT_ITEMCODE()));
                 SODet.setFORDERDET_PICE_QTY(tempOrder.getPREPRODUCT_QTY());
                 SODet.setFORDERDET_SELLPRICE(String.valueOf(String.valueOf(UnitPrice)));
                 SODet.setFORDERDET_BSELLPRICE(String.valueOf(String.valueOf(UnitPrice)));
                 SODet.setFORDERDET_SEQNO(new OrderDetailController(context).getLastSequnenceNo(refno));
                 SODet.setFORDERDET_TAXCOMCODE("0");
                 SODet.setFORDERDET_BTSELLPRICE(String.valueOf(String.valueOf(UnitPrice)));
                 SODet.setFORDERDET_TSELLPRICE(String.valueOf(String.valueOf(UnitPrice)));
                 SODet.setFORDERDET_TXNTYPE("31");
                 SODet.setFORDERDET_LOCCODE("MS");
                 SODet.setFORDERDET_TXNDATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                 SODet.setFORDERDET_RECORDID("");
                 SODet.setFORDERDET_PDISAMT("0");
                 SODet.setFORDERDET_IS_SYNCED("0");
                 SODet.setFORDERDET_QOH(tempOrder.getPREPRODUCT_QOH());
                 SODet.setFORDERDET_TYPE("MR");
                 SODet.setFORDERDET_SCHDISC("0");
                 SODet.setFORDERDET_DISCTYPE("");
                 SODet.setFORDERDET_QTY_SLAB_DISC("0");
                 SODet.setFORDERDET_ORG_PRICE(String.valueOf(String.valueOf(UnitPrice)));
                 SODet.setFORDERDET_DISFLAG("0");
                 SODet.setFORDERDET_CASES("" + tempOrder.getPREPRODUCT_CASE());
                 SODet.setFORDERDET_PICE_QTY("" + tempOrder.getPREPRODUCT_QTY());
                 SODet.setFORDERDET_REACODE(""+tempOrder.getPREPRODUCT_REACODE());

                 SOList.add(SODet);
             }
        }

        return SOList;
    }

    public ArrayList<OrderDetail> getExOrderDetails(String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<OrderDetail> list = new ArrayList<OrderDetail>();

        String selectQuery = "select * from " + TABLE_FORDDET + " WHERE "
                + REFNO + "='" + refno + "' and "
                + FORDDET_TYPE + " = 'SA' and "
                + FORDDET_IS_ACTIVE +" = '1'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                OrderDetail ordDet = new OrderDetail();

                ordDet.setFORDERDET_ID(cursor.getString(cursor.getColumnIndex(FORDDET_ID)));
                ordDet.setFORDERDET_ITEMCODE(cursor.getString(cursor.getColumnIndex(FORDDET_ITEM_CODE)));
                ordDet.setFORDERDET_QTY(cursor.getString(cursor.getColumnIndex(FORDDET_QTY)));
                ordDet.setFORDERDET_REFNO(cursor.getString(cursor.getColumnIndex(REFNO)));
                ordDet.setFORDERDET_PRICE(cursor.getString(cursor.getColumnIndex(FORDDET_B_SELL_PRICE)));

                list.add(ordDet);
            }
            cursor.close();

        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            dB.close();
        }

        return list;
    }

    public int resetOrderDetWithoutDiscountData(String refno, ArrayList<OrderDetail>list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Double price = 0.0;
        Double qty = 0.0;


        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_FORDDET + " WHERE " + REFNO + " ='" + refno + "' AND " + FORDDET_TYPE + " = 'SA'";
            cursor = dB.rawQuery(selectQuery, null);
            int cn = cursor.getCount();

            for (int i=0; i<list.size(); i++)
            {
                String amt = String.valueOf((Double.parseDouble(list.get(i).getFORDERDET_PRICE()) * (Double.parseDouble(list.get(i).getFORDERDET_QTY()))));

                if (cn > 0) {
                    String updateQuery = "UPDATE FOrddet SET " +
                            FORDDET_SCH_DISPER + "='" +
                            "" + "'," + FORDDET_DIS_VAL_AMT + " =''," + FORDDET_AMT + "='" + amt + "'," + FORDDET_DISFLAG + "='0'," + FORDDET_SELL_PRICE + "='" + list.get(i).getFORDERDET_PRICE() + "'," + FORDDET_BT_SELL_PRICE + "='" + list.get(i).getFORDERDET_PRICE() + "'," + FORDDET_B_AMT + "='" + amt + "'," + FORDDET_DISCTYPE + "='' WHERE Itemcode ='" + list.get(i).getFORDERDET_ITEMCODE() + "' AND types='SA' AND RefNo ='" + list.get(i).getFORDERDET_REFNO() + "'";
                    dB.execSQL(updateQuery);
                }
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
