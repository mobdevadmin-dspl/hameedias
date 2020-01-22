package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderController {
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    private String TAG = "OrderController";

    // Shared Preferences variables
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    // rashmi - 2019-12-17 move from database_helper , because of reduce coding in database helper*******************************************************************************
    // table ORDER
    public static final String TABLE_ORDER = "OrderHeader";
    // table attributes
    public static final String ORDER_ID = "OrderId";
    public static final String ORDER_CUSCODE = "CustomerCode";
    public static final String ORDER_START_TIME = "StartTime";
    public static final String ORDER_END_TIME = "EndTime";
    public static final String ORDER_LONGITUDE = "Longitude";
    public static final String ORDER_LATITUDE = "Latitude";
    public static final String ORDER_MANU_REF = "ManuRef";
    public static final String ORDER_REMARKS = "Remarks";
    public static final String ORDER_TOTAL_AMT = "TotalAmt";
    public static final String ORDER_ADDDATE = "AddDate";
    public static final String ORDER_IS_SYNCED = "isSynced";
    public static final String ORDER_IS_ACTIVE = "isActive";
    public static final String ORDER_ROUTE_CODE = "RouteCode";
    public static final String ORDER_DELIV_DATE = "DeliverDate";
    public static final String REFNO = "RefNo";
    public static final String TXNDATE = "TxnDate";
    public static final String REPCODE = "RepCode";
    public static final String DEALCODE = "DealCode";
    public static final String DEBCODE = "DebCode";

    public static final String TABLE_FORDHED = "FOrdHed";
    // table attributes
    public static final String FORDHED_ID = "FOrdHed_id";
    public static final String FORDHED_ADD_DATE = "AddDate";
    public static final String FORDHED_ADD_MACH = "AddMach";
    public static final String FORDHED_ADD_USER = "AddUser";
    public static final String FORDHED_APP_DATE = "Appdate";
    public static final String FORDHED_APPSTS = "Appsts";
    public static final String FORDHED_APP_USER = "AppUser";
    public static final String FORDHED_BP_TOTAL_DIS = "BPTotalDis";
    public static final String FORDHED_B_TOTAL_AMT = "BTotalAmt";
    public static final String FORDHED_B_TOTAL_DIS = "BTotalDis";
    public static final String FORDHED_B_TOTAL_TAX = "BTotalTax";
    public static final String FORDHED_COST_CODE = "CostCode";
    public static final String FORDHED_CUR_CODE = "CurCode";
    public static final String FORDHED_CUR_RATE = "CurRate";
    public static final String FORDHED_DIS_PER = "DisPer";
    public static final String FORDHED_START_TIME_SO = "startTimeSO";
    public static final String FORDHED_END_TIME_SO = "endTimeSO";
    public static final String FORDHED_LONGITUDE = "Longitude";
    public static final String FORDHED_LATITUDE = "Latitude";
    public static final String FORDHED_LOC_CODE = "LocCode";
    public static final String FORDHED_MANU_REF = "ManuRef";
    public static final String FORDHED_RECORD_ID = "RecordId";
    public static final String FORDHED_REMARKS = "Remarks";
    public static final String FORDHED_TAX_REG = "TaxReg";
    public static final String FORDHED_TIMESTAMP_COLUMN = "Timestamp_column";
    public static final String FORDHED_TOTAL_AMT = "TotalAmt";
    public static final String FORDHED_TOTALDIS = "TotalDis";
    public static final String FORDHED_TOTAL_TAX = "TotalTax";
    public static final String FORDHED_TXN_TYPE = "TxnType";

    public static final String FORDHED_ADDRESS = "gpsAddress";
    public static final String FORDHED_TOTAL_ITM_DIS = "TotalItemDis";
    public static final String FORDHED_TOT_MKR_AMT = "TotMkrAmt";
    public static final String FORDHED_IS_SYNCED = "isSynced";
    public static final String FORDHED_IS_ACTIVE = "isActive";
    public static final String FORDHED_DELV_DATE = "DelvDate";
    public static final String FORDHED_HED_DIS_VAL = "HedDisVal";
    public static final String FORDHED_HED_DIS_PER_VAL = "HedDisPerVal";
    public static final String FORDHED_ROUTE_CODE = "RouteCode";
    public static final String FORDHED_AREA_CODE = "AreaCode";
    public static final String FORDHED_PAYMENT_TYPE = "PaymentType";
    public static final String FORDHED_UPLOAD_TIME = "UploadTime";
    public static final String FORDHED_FEEDBACK = "Feedback";
    // create String
    public static final String CREATE_FORDHED_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FORDHED + " (" + FORDHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FORDHED_ADD_MACH + " TEXT, " +
            FORDHED_ADD_DATE + " TEXT," +
            FORDHED_ADD_USER + " TEXT, "  + DEALCODE + " TEXT, " + FORDHED_AREA_CODE + " TEXT, " +
            FORDHED_APP_DATE + " TEXT, " + FORDHED_ADDRESS + " TEXT, " + FORDHED_APPSTS + " TEXT, " + FORDHED_APP_USER +
            " TEXT, " + FORDHED_BP_TOTAL_DIS + " TEXT, " + FORDHED_B_TOTAL_AMT + " TEXT, " +
            FORDHED_B_TOTAL_DIS + " TEXT, " + FORDHED_B_TOTAL_TAX + " TEXT, " + FORDHED_COST_CODE + " TEXT, " +
            FORDHED_CUR_CODE + " TEXT, " + FORDHED_CUR_RATE + " TEXT, " + DEBCODE + " TEXT, " +
            FORDHED_LOC_CODE + " TEXT, " + FORDHED_MANU_REF + " TEXT, " + FORDHED_DIS_PER + " TEXT, " + FORDHED_RECORD_ID +
            " TEXT, " + REFNO + " TEXT, " + FORDHED_REMARKS + " TEXT, " + REPCODE + " TEXT, " +
            FORDHED_TAX_REG + " TEXT, " + FORDHED_TIMESTAMP_COLUMN + " TEXT, " + FORDHED_TOTAL_TAX + " TEXT, " +
            FORDHED_TOTAL_AMT + " TEXT, " + FORDHED_TOTALDIS + " TEXT, " + FORDHED_TOTAL_ITM_DIS + " TEXT, " +
            FORDHED_TOT_MKR_AMT + " TEXT, " + FORDHED_TXN_TYPE + " TEXT, " + TXNDATE + " TEXT, " + FORDHED_LONGITUDE +
            " TEXT, " + FORDHED_LATITUDE + " TEXT, " + FORDHED_START_TIME_SO + " TEXT, " + FORDHED_IS_SYNCED + " TEXT, " +
            FORDHED_IS_ACTIVE + " TEXT, " + FORDHED_DELV_DATE + " TEXT, " + FORDHED_ROUTE_CODE + " TEXT, " +
            FORDHED_HED_DIS_VAL + " TEXT, " + FORDHED_HED_DIS_PER_VAL + " TEXT," + FORDHED_PAYMENT_TYPE + " TEXT," +
            FORDHED_FEEDBACK + " TEXT," +FORDHED_END_TIME_SO + " TEXT," + FORDHED_UPLOAD_TIME + " TEXT); ";
    // create String

    public static final String CREATE_TABLE_ORDER = " CREATE  TABLE IF NOT EXISTS " + TABLE_ORDER + " (" +
            ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ORDER_ADDDATE + " TEXT, " +
            ORDER_CUSCODE + " TEXT, " +
            ORDER_MANU_REF + " TEXT, " +
            REFNO + " TEXT, " +
            ORDER_REMARKS + " TEXT, " +
            REPCODE + " TEXT, " +
            ORDER_TOTAL_AMT + " TEXT, " +
            TXNDATE + " TEXT, " +
            ORDER_LONGITUDE + " TEXT, " +
            ORDER_LATITUDE + " TEXT, " +
            ORDER_START_TIME + " TEXT, " +
            ORDER_IS_SYNCED + " TEXT, " +
            ORDER_IS_ACTIVE + " TEXT, " +
            ORDER_ROUTE_CODE + " TEXT, " +
            ORDER_DELIV_DATE + " TEXT, " +
            ORDER_END_TIME + " TEXT) ";

    // rashmi - 2019-12-17 *******************************************************************************
    public OrderController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateOrdHed(ArrayList<Order> list) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            for (Order ordHed : list) {

                String selectQuery = "SELECT * FROM " + TABLE_FORDHED + " WHERE " + REFNO
                        + " = '" + ordHed.getORDER_REFNO() + "'";

                cursor = dB.rawQuery(selectQuery, null);

                ContentValues values = new ContentValues();

                values.put(REFNO, ordHed.getORDER_REFNO());
                values.put(FORDHED_ADD_DATE, ordHed.getORDER_ADDDATE());
                values.put(FORDHED_ADD_MACH, ordHed.getORDER_ADDMACH());
                values.put(FORDHED_ADD_USER, ordHed.getORDER_ADDUSER());
                values.put(DEBCODE, ordHed.getORDER_DEBCODE());
                values.put(FORDHED_START_TIME_SO, ordHed.getORDER_START_TIMESO());
                values.put(FORDHED_LONGITUDE, ordHed.getORDER_LONGITUDE());
                values.put(FORDHED_LATITUDE, ordHed.getORDER_LATITUDE());
                values.put(FORDHED_MANU_REF, ordHed.getORDER_MANUREF());
                values.put(FORDHED_REMARKS, ordHed.getORDER_REMARKS());
                values.put(REPCODE, ordHed.getORDER_REPCODE());
                values.put(FORDHED_TOTAL_AMT, ordHed.getORDER_TOTALAMT());
                values.put(TXNDATE, ordHed.getORDER_TXNDATE());
                values.put(FORDHED_ROUTE_CODE, ordHed.getORDER_ROUTECODE());
                values.put(FORDHED_IS_SYNCED, "0");
                values.put(FORDHED_IS_ACTIVE, ordHed.getORDER_IS_ACTIVE());
                values.put(FORDHED_TOTALDIS, ordHed.getORDER_TOTALDIS());
                values.put(FORDHED_DELV_DATE, ordHed.getORDER_DELIVERY_DATE());
                values.put(FORDHED_PAYMENT_TYPE, ordHed.getORDER_PAYTYPE());
                values.put(FORDHED_LOC_CODE, ordHed.getORDER_LOCCODE());
                values.put(FORDHED_AREA_CODE, ordHed.getORDER_AREACODE());
                values.put(DEALCODE, ordHed.getORDER_DEALCODE());


                int cn = cursor.getCount();
                if (cn > 0) {
                    count = dB.update(TABLE_FORDHED, values, REFNO + " =?",
                            new String[] { String.valueOf(ordHed.getORDER_REFNO()) });
                } else {
                    count = (int) dB.insert(TABLE_FORDHED, null, values);
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

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public ArrayList<Order> getTodayOrders() {
        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<Order> list = new ArrayList<Order>();

        try {
            //String selectQuery = "select DebCode, RefNo from fordHed " +
            String selectQuery = "select DebCode, RefNo, isSynced, TxnDate, TotalAmt from fordHed " +
                    //			" fddbnote fddb where hed.refno = det.refno and det.FPRECDET_REFNO1 = fddb.refno and hed.txndate = '2019-04-12'";
                    "  where txndate = '" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate) +"' and isActive = '" +"0"+"'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                Order recDet = new Order();

//
                recDet.setORDER_REFNO(cursor.getString(cursor.getColumnIndex(REFNO)));
                recDet.setORDER_DEBCODE(cursor.getString(cursor.getColumnIndex(DEBCODE)));
                recDet.setORDER_IS_SYNCED(cursor.getString(cursor.getColumnIndex(FORDHED_IS_SYNCED)));
                recDet.setORDER_TXNTYPE("Order");
                recDet.setORDER_TXNDATE(cursor.getString(cursor.getColumnIndex(TXNDATE)));
                recDet.setORDER_TOTALAMT(cursor.getString(cursor.getColumnIndex(FORDHED_TOTAL_AMT)));
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

            String selectQuery = "SELECT * FROM " + TABLE_FORDHED + " WHERE " + REFNO + " = '"
                    + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);
            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.delete(TABLE_FORDHED, REFNO + " ='" + refno + "'", null);
                Log.v("Success", count + "");
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
    public int InactiveStatusUpdate(String refno) {

        int count = 0;
        String UploadDate = "";
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UploadDate = sdf.format(cal.getTime());
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_FORDHED + " WHERE " + REFNO + " = '"
                    + refno + "'";

            cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(FORDHED_IS_ACTIVE, "0");
            values.put(FORDHED_END_TIME_SO, UploadDate);
            values.put(FORDHED_ADD_DATE, UploadDate);
            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.update(TABLE_FORDHED, values, REFNO + " =?",
                        new String[] { String.valueOf(refno) });
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
    public int updateIsSynced(Order hed) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {
            ContentValues values = new ContentValues();

            values.put(FORDHED_IS_SYNCED, "1");

            if (hed.getORDER_IS_SYNCED().equals("1")) {
                count = dB.update(TABLE_FORDHED, values, REFNO + " =?", new String[] { String.valueOf(hed.getORDER_REFNO()) });
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
    public int updateFeedback(String feedback,String refno) {

            int count = 0;

            if (dB == null) {
                open();
            } else if (!dB.isOpen()) {
                open();
            }
            Cursor cursor = null;

            try {

                    String selectQuery = "SELECT * FROM " + TABLE_FORDHED + " WHERE " + REFNO
                            + " = '" + refno + "'";

                    cursor = dB.rawQuery(selectQuery, null);

                    ContentValues values = new ContentValues();

                    values.put(FORDHED_FEEDBACK, feedback);


                    int cn = cursor.getCount();
                    if (cn > 0) {
                        count = dB.update(TABLE_FORDHED, values, REFNO + " =?",
                                new String[] { String.valueOf(refno) });
                    } else {
                        count = (int) dB.insert(TABLE_FORDHED, null, values);
                    }


            } catch (Exception e) {

                Log.v(TAG + " ExcptnInFeedbackUpdate", e.toString());

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                dB.close();
            }
            return count;

        }


    @SuppressWarnings("static-access")
    public ArrayList<Order> getAllUnSyncOrdHed() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Order> list = new ArrayList<Order>();

        @SuppressWarnings("static-access")
        String selectQuery = "select * from " + TABLE_FORDHED + " Where " + FORDHED_IS_ACTIVE
                + "='0' and " + FORDHED_IS_SYNCED + "='0'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);

        while (cursor.moveToNext()) {

            Order order = new Order();
            OrderDetailController detDS = new OrderDetailController(context);
            ReferenceDetailDownloader branchDS = new ReferenceDetailDownloader(context);

            order.setNextNumVal(new ReferenceController(context).getCurrentNextNumVal(context.getResources().getString(R.string.NumVal)));
            order.setDistDB(SharedPref.getInstance(context).getDistDB().trim());
            order.setConsoleDB(SharedPref.getInstance(context).getConsoleDB().trim());
            //order.setNextNumVal(branchDS.getCurrentNextNumVal(context.getResources().getString(R.string.NumVal)));

            order.setORDER_ID(cursor.getString(cursor.getColumnIndex(FORDHED_ID)));
            order.setORDER_REFNO(cursor.getString(cursor.getColumnIndex(REFNO)));
            order.setORDER_LONGITUDE(cursor.getString(cursor.getColumnIndex(FORDHED_LONGITUDE)));
            order.setORDER_LATITUDE(cursor.getString(cursor.getColumnIndex(FORDHED_LATITUDE)));
            order.setORDER_MANUREF(cursor.getString(cursor.getColumnIndex(FORDHED_MANU_REF)));
            order.setORDER_REMARKS(cursor.getString(cursor.getColumnIndex(FORDHED_REMARKS)));
            order.setORDER_REPCODE(cursor.getString(cursor.getColumnIndex(REPCODE)));
            order.setORDER_TOTALAMT(cursor.getString(cursor.getColumnIndex(FORDHED_TOTAL_AMT)));
            order.setORDER_TXNDATE(cursor.getString(cursor.getColumnIndex(TXNDATE)));
            order.setORDER_ADDDATE(cursor.getString(cursor.getColumnIndex(FORDHED_ADD_DATE)));
            order.setORDER_ADDMACH(cursor.getString(cursor.getColumnIndex(FORDHED_ADD_MACH)));
            order.setORDER_ADDUSER(cursor.getString(cursor.getColumnIndex(FORDHED_ADD_USER)));
            order.setORDER_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FORDHED_IS_ACTIVE)));
            order.setORDER_DEBCODE(cursor.getString(cursor.getColumnIndex(DEBCODE)));
            order.setORDER_ROUTECODE(cursor.getString(cursor.getColumnIndex(FORDHED_ROUTE_CODE)));
            order.setORDER_DELIVERY_DATE(cursor.getString(cursor.getColumnIndex(FORDHED_DELV_DATE)));
            order.setORDER_PAYTYPE(cursor.getString(cursor.getColumnIndex(FORDHED_PAYMENT_TYPE)));
            order.setORDER_LOCCODE(cursor.getString(cursor.getColumnIndex(FORDHED_LOC_CODE)));
            order.setORDER_AREACODE(cursor.getString(cursor.getColumnIndex(FORDHED_AREA_CODE)));
            order.setORDER_DEALCODE(cursor.getString(cursor.getColumnIndex(DEALCODE)));
            order.setORDER_START_TIMESO(cursor.getString(cursor.getColumnIndex(FORDHED_START_TIME_SO)));
            order.setORDER_END_TIMESO(cursor.getString(cursor.getColumnIndex(FORDHED_END_TIME_SO)));
            order.setORDER_ADDRESS(cursor.getString(cursor.getColumnIndex(FORDHED_ADDRESS)));
            order.setORDER_FEEDBACK(cursor.getString(cursor.getColumnIndex(FORDHED_FEEDBACK)));

            order.setOrdDet(detDS.getAllUnSync(cursor.getString(cursor.getColumnIndex(REFNO))));
////            preSalesMapper.setIssuList(
////                    issueDS.getActiveIssues(cursor.getString(cursor.getColumnIndex(ORDER_CUSCODE))));

            String RefNo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO));

            order.setTaxDTs(new PreSaleTaxDTController(context).getAllTaxDT(RefNo));
            order.setTaxRGs(new PreSaleTaxRGController(context).getAllTaxRG(RefNo));
            order.setOrdDisc(new OrderDiscController(context).getAllOrderDiscs(RefNo));
            order.setFreeIssues(new OrdFreeIssueController(context).getAllFreeIssues(RefNo));

            list.add(order);

        }

        return list;
    }
    public int IsSavedHeader(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;


        try {

            String selectQuery = "SELECT * FROM " + TABLE_FORDHED + " WHERE " + DatabaseHelper.REFNO + " = '" + refno + "'";

            cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            //  values.put(DatabaseHelper.FINVHED_IS_ACTIVE, "0");

            int cn = cursor.getCount();
            count = cn;

//            if (cn > 0) {
//                count = dB.update(DatabaseHelper.TABLE_FINVHED, values, DatabaseHelper.REFNO + " =?", new String[]{String.valueOf(refno)});
//            } else {
//                count = (int) dB.insert(DatabaseHelper.TABLE_FINVHED, null, values);
//            }

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


    public Order getAllActiveOrdHed() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        @SuppressWarnings("static-access")
        String selectQuery = "select * from " + TABLE_FORDHED + " Where " + ORDER_IS_ACTIVE
                + "='1' and " + ORDER_IS_SYNCED + "='0'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);

        Order presale = new Order();

        while (cursor.moveToNext()) {

            OrderDetailController detDS = new OrderDetailController(context);

            ReferenceDetailDownloader branchDS = new ReferenceDetailDownloader(context);
            presale.setORDER_REFNO(cursor.getString(cursor.getColumnIndex(REFNO)));
            presale.setORDER_ADDDATE(cursor.getString(cursor.getColumnIndex(FORDHED_ADD_DATE)));
            presale.setORDER_ADDMACH(cursor.getString(cursor.getColumnIndex(FORDHED_ADD_MACH)));
            presale.setORDER_ADDUSER(cursor.getString(cursor.getColumnIndex(FORDHED_ADD_USER)));
            presale.setORDER_DEBCODE(cursor.getString(cursor.getColumnIndex(DEBCODE)));
           // presale.setORDER_ADDTIME(cursor.getString(cursor.getColumnIndex(FORDHED_START_TIME_SO)));
            presale.setORDER_START_TIMESO(cursor.getString(cursor.getColumnIndex(FORDHED_START_TIME_SO)));
            presale.setORDER_END_TIMESO(cursor.getString(cursor.getColumnIndex(FORDHED_END_TIME_SO)));
            presale.setORDER_AREACODE(cursor.getString(cursor.getColumnIndex(FORDHED_AREA_CODE)));
            presale.setORDER_DEALCODE(cursor.getString(cursor.getColumnIndex(DEALCODE)));
            presale.setORDER_LONGITUDE(cursor.getString(cursor.getColumnIndex(FORDHED_LONGITUDE)));
            presale.setORDER_LATITUDE(cursor.getString(cursor.getColumnIndex(FORDHED_LATITUDE)));
            presale.setORDER_MANUREF(cursor.getString(cursor.getColumnIndex(FORDHED_MANU_REF)));
            presale.setORDER_REMARKS(cursor.getString(cursor.getColumnIndex(FORDHED_REMARKS)));
            presale.setORDER_REPCODE(cursor.getString(cursor.getColumnIndex(REPCODE)));
            presale.setORDER_TOTALAMT(cursor.getString(cursor.getColumnIndex(FORDHED_TOTAL_AMT)));
            presale.setORDER_TXNDATE(cursor.getString(cursor.getColumnIndex(TXNDATE)));
            presale.setORDER_IS_ACTIVE(cursor.getString(cursor.getColumnIndex(FORDHED_IS_ACTIVE)));
            presale.setORDER_ROUTECODE(cursor.getString(cursor.getColumnIndex(FORDHED_ROUTE_CODE)));
            presale.setORDER_DELIVERY_DATE(cursor.getString(cursor.getColumnIndex(FORDHED_DELV_DATE)));
            presale.setORDER_PAYTYPE(cursor.getString(cursor.getColumnIndex(FORDHED_PAYMENT_TYPE)));
            presale.setORDER_FEEDBACK(cursor.getString(cursor.getColumnIndex(FORDHED_FEEDBACK)));
            presale.setOrdDet(detDS.getAllActives(cursor.getString(cursor.getColumnIndex(REFNO))));

        }

        return presale;

    }
    public String getRefnoByDebcode(String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + TABLE_FORDHED + " WHERE " + REFNO + "='"
                + refno + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(DEBCODE));

        }
        return "";

    }

    @SuppressWarnings("static-access")
    public int DeleteOldOrders(String DateFrom, String DateTo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_ORDER + " WHERE " +TXNDATE + " BETWEEN '"+ DateFrom + "' AND '" + DateTo + "' AND " + ORDER_IS_ACTIVE + "= '0' AND " + ORDER_IS_SYNCED + " ='1' " ;
            cursor = dB.rawQuery(selectQuery, null);
            int cn = cursor.getCount();

            if (cn > 0) {
                int success = dB.delete(TABLE_ORDER, TXNDATE + " BETWEEN '"+ DateFrom + "' AND '" + DateTo + "' AND " + ORDER_IS_ACTIVE + "= '0' AND " + ORDER_IS_SYNCED + " ='1' ", null);
                count = success;
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

    public String getRefnoToDelete(String refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM " + TABLE_ORDER + " WHERE " + REFNO + "='"
                + refno + "'";

        Cursor cursor = null;
        cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            return cursor.getString(cursor.getColumnIndex(ORDER_IS_SYNCED));

        }

        return "";
    }

    public int undoOrdHedByID(String RefNo) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_ORDER + " WHERE " + REFNO + "='" + RefNo + "'", null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_ORDER, REFNO + "='" + RefNo + "'", null);

            }
        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return count;

    }

    public Order getDetailsForPrint(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Order SOHed = new Order();

        try {
            String selectQuery = "SELECT TxnDate,DebCode,Remarks,RouteCode,TotalAmt,TotalDis FROM " + TABLE_FORDHED + " WHERE " + DatabaseHelper.REFNO + " = '" + Refno + "'";

            Cursor cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                SOHed.setORDER_TXNDATE(cursor.getString(cursor.getColumnIndex(TXNDATE)));
                SOHed.setORDER_DEBCODE(cursor.getString(cursor.getColumnIndex(DEBCODE)));
                SOHed.setORDER_REMARKS(cursor.getString(cursor.getColumnIndex(FORDHED_REMARKS)));
//                SOHed.setFINVHED_TOURCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.FINVHED_TOURCODE)));
                SOHed.setORDER_ROUTECODE(cursor.getString(cursor.getColumnIndex(FORDHED_ROUTE_CODE)));
                SOHed.setORDER_TOTALAMT(cursor.getString(cursor.getColumnIndex(FORDHED_TOTAL_AMT)));
                SOHed.setORDER_TOTALDIS(cursor.getString(cursor.getColumnIndex(FORDHED_TOTALDIS)));
            }
            cursor.close();

        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            dB.close();
        }

        return SOHed;

    }

    public boolean isAnyActiveOrderHed(String RefNo) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        boolean res = false;

        Cursor cursor = null;
        try {
            String selectQuery = "SELECT * FROM " + TABLE_FORDHED + " WHERE " + FORDHED_IS_ACTIVE + "='1'" + " AND " + REFNO + " = '" + RefNo + "'";
            cursor = dB.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0)
                res = true;
            else
                res = false;

        } catch (Exception e) {
            Log.v(TAG, e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return res;

    }

    public String getActiveRefNoFromOrders()
    {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String refNo = "";

        String selectQuery = "select * from " + TABLE_FORDHED + " WHERE " + FORDHED_IS_ACTIVE + "='" + "1" + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            if (cursor.getCount()>0)
            {
                while(cursor.moveToNext())
                {
                    refNo = cursor.getString(cursor.getColumnIndex(REFNO));
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

        return refNo;
    }


}
