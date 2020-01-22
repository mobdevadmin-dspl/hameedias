package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.location.Location;
import android.util.Log;

import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.Customer;
import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Debtor;
import com.datamation.swdsfa.model.FddbNote;
import com.datamation.swdsfa.model.HistoryDetail;
import com.datamation.swdsfa.model.Invoice;
import com.datamation.swdsfa.model.NearDebtor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.datamation.swdsfa.helpers.DatabaseHelper.DEBCODE;

public class CustomerController {

    private SQLiteDatabase dB;
    private DatabaseHelper DbHelper;
    Context context;
    private String TAG = "CustomerController";

    public CustomerController(Context context) {
        this.context = context;
        DbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = DbHelper.getWritableDatabase();
    }

    public void InsertOrReplaceDebtor(ArrayList<Debtor> list) {
        Log.d("InsertOrReplaceDebtor", "" + list.size());
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + Customer.TABLE_FDEBTOR + " (DebCode,DebName,DebAdd1,DebAdd2,DebAdd3,DebTele,DebMob,DebEMail,TownCode,AreaCode,DbGrCode,Status,CrdPeriod,ChkCrdPrd,CrdLimit,ChkCrdLmt,RepCode,PrillCode,TaxReg,RankCode,Latitude,Longitude,CusImage) " + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//            String sql = "INSERT OR REPLACE INTO " + DatabaseHelper.TABLE_FDEBTOR + " (DebCode,DebName,DebAdd1,DebAdd2,DebAdd3,DebTele,DebMob,DebEMail,TownCode,AreaCode,DbGrCode,Status,CrdPeriod,ChkCrdPrd,CrdLimit,ChkCrdLmt,RepCode,PrillCode,TaxReg,RankCode,Latitude,Longitude) " + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (Debtor debtor : list) {

                stmt.bindString(1, debtor.getFDEBTOR_CODE());
                stmt.bindString(2, debtor.getFDEBTOR_NAME());
                stmt.bindString(3, debtor.getFDEBTOR_ADD1());
                stmt.bindString(4, debtor.getFDEBTOR_ADD2());
                stmt.bindString(5, debtor.getFDEBTOR_ADD3());
                stmt.bindString(6, debtor.getFDEBTOR_TELE());
                stmt.bindString(7, debtor.getFDEBTOR_MOB());
                stmt.bindString(8, debtor.getFDEBTOR_EMAIL());
                stmt.bindString(9, debtor.getFDEBTOR_TOWN_CODE());
                stmt.bindString(10, debtor.getFDEBTOR_AREA_CODE());
                stmt.bindString(11, debtor.getFDEBTOR_DBGR_CODE());
                stmt.bindString(12, debtor.getFDEBTOR_STATUS());
                stmt.bindString(13, debtor.getFDEBTOR_CRD_PERIOD());
                stmt.bindString(14, debtor.getFDEBTOR_CHK_CRD_PRD());
                stmt.bindString(15, debtor.getFDEBTOR_CRD_LIMIT());
                stmt.bindString(16, debtor.getFDEBTOR_CHK_CRD_LIMIT());
                stmt.bindString(17, debtor.getFDEBTOR_REPCODE());
                stmt.bindString(18, debtor.getFDEBTOR_PRILLCODE());
                stmt.bindString(19, debtor.getFDEBTOR_TAX_REG());
                stmt.bindString(20, debtor.getFDEBTOR_RANK_CODE());
                stmt.bindString(21, debtor.getFDEBTOR_LATITUDE());
                stmt.bindString(22, debtor.getFDEBTOR_LONGITUDE());
                stmt.bindString(23, debtor.getFDEBTOR_IMG_URL());

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

            cursor = dB.rawQuery("SELECT * FROM " + Customer.TABLE_FDEBTOR, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(Customer.TABLE_FDEBTOR, null, null);
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

    public ArrayList<FddbNote> getOutStandingList(String debCode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<FddbNote> list = new ArrayList<FddbNote>();
        Cursor cursor = null;
        try {
            String selectQuery = "select outs.*, deb.crdperiod from fddbnote outs , fdebtor deb where deb.debcode = outs.debcode and outs.debcode = '"+ debCode + "'";


            cursor = dB.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {

                FddbNote fddbNote = new FddbNote();
//
                fddbNote.setRefNo(cursor.getString(cursor.getColumnIndex(Customer.REFNO)));
                fddbNote.setTxnDate(cursor.getString(cursor.getColumnIndex(Customer.TXNDATE)));
                fddbNote.setAmt(cursor.getString(cursor.getColumnIndex(OutstandingController.FDDBNOTE_AMT)));
                fddbNote.setCreditPeriod(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CRD_PERIOD)));

                list.add(fddbNote);

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

    public ArrayList<Customer> getAllCustomers() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Customer> list = new ArrayList<Customer>();
        Cursor cursor = null;
        try {
            String selectQuery = "select * from " + Customer.TABLE_FDEBTOR;

            cursor = dB.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {

                Customer customer = new Customer();

                customer.setCusCode(cursor.getString(cursor.getColumnIndex(Customer.DEBCODE)));
                customer.setCusName(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_NAME)));
                customer.setCusAdd1(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD1)));
                customer.setCusAdd2(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD2)));
                customer.setCusMob(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_MOB)));
                customer.setCusEmail(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_EMAIL)));
                customer.setCusStatus(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_STATUS)));
                customer.setCreditLimit(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CRD_LIMIT)));
                customer.setCreditPeriod(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CRD_PERIOD)));
                customer.setCreditStatus(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CHK_CRD_LIMIT)));
                customer.setCusPrilCode(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_PRILLCODE)));
                customer.setLatitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)));
                customer.setLongitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LONGITUDE)));

                list.add(customer);

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


    public ArrayList<Debtor> getAllDebtorsToCordinatesUpdate() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Debtor> list = new ArrayList<Debtor>();
        Cursor cursor = null;
        try {
            String selectQuery = "select * from " + Customer.TABLE_FDEBTOR + " where " + Customer.FDEBTOR_IS_CORDINATE_UPDATE + " = '1' and (" + Customer.FDEBTOR_IS_SYNC + " = '3' or " + Customer.FDEBTOR_IS_SYNC + " = '4' )";

            cursor = dB.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {

                Debtor debtor = new Debtor();
                debtor.setCONSOLE_DB(SharedPref.getInstance(context).getConsoleDB().trim());
                debtor.setDISTRIBUTE_DB(SharedPref.getInstance(context).getDistDB().trim());
                debtor.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(Customer.DEBCODE)));
                debtor.setFDEBTOR_LATITUDE(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)));
                debtor.setFDEBTOR_LONGITUDE(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LONGITUDE)));
                debtor.setFDEBTOR_IS_SYNC(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_IS_SYNC)));
                debtor.setFDEBTOR_IS_CORDINATE_UPDATE(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_IS_CORDINATE_UPDATE)));

                list.add(debtor);

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

    public ArrayList<Debtor> getAllImagUpdatedDebtors() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Debtor> list = new ArrayList<Debtor>();
        Cursor cursor = null;
        try {
            String selectQuery = "select * from " + Customer.TABLE_FDEBTOR + " where " + Customer.FDEBTOR_IMAGE + " <> '' and (" + Customer.FDEBTOR_IS_SYNC + " <> '1' )";

            cursor = dB.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                Debtor debtor = new Debtor();
                debtor.setCONSOLE_DB(SharedPref.getInstance(context).getConsoleDB().trim());
                debtor.setDISTRIBUTE_DB(SharedPref.getInstance(context).getDistDB().trim());
                debtor.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(Customer.DEBCODE)));
                debtor.setFDEBTOR_IS_SYNC(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_IS_SYNC)));
                debtor.setFDEBTOR_IMG_URL(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_IMAGE)));
                list.add(debtor);
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
    public ArrayList<Debtor> getAllUpdatedDebtors() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Debtor> list = new ArrayList<Debtor>();
        Cursor cursor = null;
        try {
            String selectQuery = "select * from " + Customer.TABLE_FDEBTOR + " where  (" + Customer.FDEBTOR_IS_SYNC + " = '3' or " + Customer.FDEBTOR_IS_SYNC + " = '4' )";

            cursor = dB.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                Debtor debtor = new Debtor();
                debtor.setCONSOLE_DB(SharedPref.getInstance(context).getConsoleDB().trim());
                debtor.setDISTRIBUTE_DB(SharedPref.getInstance(context).getDistDB().trim());
                debtor.setFDEBTOR_CODE(cursor.getString(cursor.getColumnIndex(Customer.DEBCODE)));
                debtor.setFDEBTOR_IS_SYNC(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_IS_SYNC)));
                debtor.setFDEBTOR_ADD1(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD1)));
                debtor.setFDEBTOR_ADD2(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD2)));
                debtor.setFDEBTOR_TELE(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_TELE)));
                debtor.setFDEBTOR_NAME(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_NAME)));
                list.add(debtor);
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
    public ArrayList<Customer> getAllCustomersForSelectedRepCode(String RepCode, String key) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Customer> list = new ArrayList<Customer>();
        Cursor cursor = null;
        try {
            String selectQuery = "select * from " + Customer.TABLE_FDEBTOR + " Where " + Customer.REPCODE + "='"
                    + RepCode + "' and DebCode || DebName LIKE '%" + key + "%'";

            cursor = dB.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {

                Customer customer = new Customer();

                customer.setCusCode(cursor.getString(cursor.getColumnIndex(Customer.DEBCODE)));
                customer.setCusName(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_NAME)));
                customer.setCusAdd1(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD1)));
                customer.setCusAdd2(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD2)));
                customer.setCusMob(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_MOB)));

//				customer.setCusRoute(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_RO)));
                customer.setCusEmail(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_EMAIL)));
                customer.setCusStatus(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_STATUS)));
                customer.setCreditLimit(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CRD_LIMIT)));
                customer.setCreditPeriod(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CRD_PERIOD)));
                customer.setCreditStatus(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CHK_CRD_LIMIT)));
                customer.setCusPrilCode(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_PRILLCODE)));
                customer.setLatitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)));
                customer.setLongitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LONGITUDE)));
                customer.setCusImage(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_IMAGE)));

                list.add(customer);

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

    public ArrayList<Customer> getAllGPSCustomersForSelectedRepCode(String RepCode, Double lat, Double lon, String key) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Double debLat = 0.0;
        Double debLon = 0.0;

        ArrayList<Customer> list = new ArrayList<Customer>();
        Cursor cursor = null;
        try {
            String selectQuery = "select * from " + Customer.TABLE_FDEBTOR + " Where " + Customer.REPCODE + "='"
                    + RepCode + "' and DebCode || DebName LIKE '%" + key + "%'";

            cursor = dB.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {

                Customer customer = new Customer();

                if ((cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)) != null) && (cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LONGITUDE)) != null)) {
                    debLat = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)));
                    debLon = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LONGITUDE)));

                    float[] results = new float[1];
                    Location.distanceBetween(lat, lon, debLat, debLon, results);
                    float distanceInMeters = results[0];
                    boolean isWithin100m = distanceInMeters < 150;

                    if (isWithin100m) {
                        customer.setCusCode(cursor.getString(cursor.getColumnIndex(DEBCODE)));
                        customer.setCusName(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_NAME)));
                        customer.setCusAdd1(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD1)));
                        customer.setCusAdd2(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD2)));
                        customer.setCusMob(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_MOB)));
                        customer.setCusEmail(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_EMAIL)));
                        customer.setCusStatus(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_STATUS)));
                        customer.setCreditLimit(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CRD_LIMIT)));
                        customer.setCreditPeriod(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CRD_PERIOD)));
                        customer.setCreditStatus(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CHK_CRD_LIMIT)));
                        customer.setCusPrilCode(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_PRILLCODE)));
                        customer.setLatitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)));
                        customer.setLongitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LONGITUDE)));
                        customer.setCusImage(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_IMAGE)));
                        list.add(customer);
                    }
                }

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

    public ArrayList<Customer> getAllCustomersByRoute(String key) {

        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));


        String curdate = curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate);
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<Customer> list = new ArrayList<Customer>();
        Cursor cursor = null;
        try {

            //cursor = dB.rawQuery("select * from fDebtor where DebCode in (select Debcode from fRouteDet where RouteCode in (select RouteCode from fTourHed where '"+curdate+"' between DateFrom And DateTo and RepCode = '"+repCode+"'));", null);
            cursor = dB.rawQuery("select * from fDebtor where DebCode in (select Debcode from fRouteDet where RouteCode in (select RouteCode from FItenrDet where TxnDate = '" + curdate + "')) and DebCode || DebName LIKE '%" + key + "%'", null);

            while (cursor.moveToNext()) {

                Customer customer = new Customer();

                customer.setCusCode(cursor.getString(cursor.getColumnIndex(Customer.DEBCODE)));
                customer.setCusName(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_NAME)));
                customer.setCusAdd1(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD1)));
                customer.setCusAdd2(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD2)));
                customer.setCusMob(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_MOB)));
                customer.setCusEmail(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_EMAIL)));
                customer.setCusStatus(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_STATUS)));
                customer.setCreditLimit(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CRD_LIMIT)));
                customer.setCreditPeriod(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CRD_PERIOD)));
                customer.setCreditStatus(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CHK_CRD_LIMIT)));
                customer.setCusPrilCode(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_PRILLCODE)));
                customer.setLatitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)));
                customer.setLongitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LONGITUDE)));
                customer.setCusImage(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_IMAGE)));

                list.add(customer);

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

    public ArrayList<Customer> getGPSCustomersByRoute(Double lat, Double lon, String key) {

        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));

        String curdate = curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate);
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Double debLat = 0.0;
        Double debLon = 0.0;

        ArrayList<Customer> list = new ArrayList<Customer>();
        Cursor cursor = null;
        try {

            //cursor = dB.rawQuery("select * from fDebtor where DebCode in (select Debcode from fRouteDet where RouteCode in (select RouteCode from fTourHed where '"+curdate+"' between DateFrom And DateTo and RepCode = '"+repCode+"'));", null);
            cursor = dB.rawQuery("select * from fDebtor where DebCode in (select Debcode from fRouteDet where RouteCode in (select RouteCode from FItenrDet where TxnDate = '" + curdate + "')) and DebCode || DebName LIKE '%" + key + "%'", null);

            while (cursor.moveToNext()) {

                Customer customer = new Customer();

                if ((cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)) != null) && (cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)) != null)) {
                    debLat = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)));
                    debLon = Double.parseDouble(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LONGITUDE)));

                    float[] results = new float[1];
                    Location.distanceBetween(lat, lon, debLat, debLon, results);
                    float distanceInMeters = results[0];
                    boolean isWithin100m = distanceInMeters < 150;

                    if (isWithin100m) {
                        customer.setCusCode(cursor.getString(cursor.getColumnIndex(DEBCODE)));
                        customer.setCusName(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_NAME)));
                        customer.setCusAdd1(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD1)));
                        customer.setCusAdd2(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD2)));
                        customer.setCusMob(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_MOB)));
                        customer.setCusEmail(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_EMAIL)));
                        customer.setCusStatus(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_STATUS)));
                        customer.setCreditLimit(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CRD_LIMIT)));
                        customer.setCreditPeriod(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CRD_PERIOD)));
                        customer.setCreditStatus(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_CHK_CRD_LIMIT)));
                        customer.setCusPrilCode(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_PRILLCODE)));
                        customer.setLatitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)));
                        customer.setLongitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LONGITUDE)));
                        customer.setCusImage(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_IMAGE)));


                        list.add(customer);
                    }
                }
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

    public Customer getSelectedCustomerByCode(String code) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        try {
            String selectQuery = "select * from " + Customer.TABLE_FDEBTOR + " Where " + Customer.DEBCODE + "='"
                    + code + "'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                Customer customer = new Customer();

                customer.setCusName(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_NAME)));
                customer.setCusCode(cursor.getString(cursor.getColumnIndex(Customer.DEBCODE)));
                customer.setCusAdd1(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD1)));
                customer.setCusAdd2(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD2)));
                customer.setCusMob(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_MOB)));
                customer.setCusStatus(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_STATUS)));
                customer.setCusImage(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_IMAGE)));
                customer.setLatitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)));
                customer.setLongitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LONGITUDE)));

                return customer;

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return null;

    }
    public Customer getCustomerGPS(String code) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        try {
            String selectQuery = "select * from " + Customer.TABLE_FDEBTOR + " Where " + Customer.DEBCODE + "='"
                    + code + "'";

            cursor = dB.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

                Customer customer = new Customer();

                customer.setLatitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LATITUDE)));
                customer.setLongitude(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_LONGITUDE)));
                customer.setCusCode(cursor.getString(cursor.getColumnIndex(Customer.DEBCODE)));
                customer.setCusAdd1(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD1)));
                customer.setCusAdd2(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD2)));
                customer.setCusMob(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_MOB)));
                customer.setCusStatus(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_STATUS)));
                customer.setCusImage(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_IMAGE)));


                return customer;

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return null;

    }
    public String getAreaByDebCode(String debcode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT " + Customer.FDEBTOR_AREA_CODE + " FROM " + Customer.TABLE_FDEBTOR + " where " + DatabaseHelper.DEBCODE + " = '" + debcode + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_AREA_CODE));

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception get area", e.toString());

        } finally {
            dB.close();
        }

        return "";
    }
    public String getCusNameByCode(String debcode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT " + Customer.FDEBTOR_NAME + " FROM " + Customer.TABLE_FDEBTOR + " where " + Customer.DEBCODE + " = '" + debcode + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_NAME));

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception get Name", e.toString());

        } finally {
            dB.close();
        }

        return "";
    }
    public int updateCustomerLocationByNeDebtor(String debCode, NearDebtor nDeb) {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(Customer.FDEBTOR_LATITUDE, nDeb.getFNEARDEBTOR_LATI());
            values.put(Customer.FDEBTOR_LONGITUDE, nDeb.getFNEARDEBTOR_LONGI());
            values.put(Customer.FDEBTOR_IS_CORDINATE_UPDATE, "1");
            values.put(Customer.FDEBTOR_IS_SYNC, "3");
            count = (int) dB.update(Customer.TABLE_FDEBTOR, values, Customer.DEBCODE + " =?", new String[]{String.valueOf(debCode)});
            Log.d("", "");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
        return count;
    }

    public int updateIsSynced(String debCode, String res) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            ContentValues values = new ContentValues();
            values.put(Customer.FDEBTOR_IS_SYNC, res);
            values.put(Customer.FDEBTOR_IS_CORDINATE_UPDATE, "1");

            if (res.equalsIgnoreCase("1")) {
                count = dB.update(Customer.TABLE_FDEBTOR, values, DatabaseHelper.DEBCODE + " =?", new String[]{String.valueOf(debCode)});
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            dB.close();
        }
        return count;
    }

    public int updateforCusImageUrl(Customer cus) {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            ContentValues values = new ContentValues();
            values.put(Customer.FDEBTOR_IMAGE, cus.getCusImage());
            values.put(Customer.FDEBTOR_IS_SYNC, 0);

            if (!cus.equals(null)) {
                count = dB.update(Customer.TABLE_FDEBTOR, values, DatabaseHelper.DEBCODE + " =?", new String[]{String.valueOf(cus.getCusCode())});
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
        return count;
    }
    public int updateforCus(Customer cus) {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            ContentValues values = new ContentValues();
            values.put(Customer.FDEBTOR_NAME, cus.getCusName());
            values.put(Customer.FDEBTOR_ADD1, cus.getCusAdd1());
            values.put(Customer.FDEBTOR_ADD2, cus.getCusAdd2());
            values.put(Customer.FDEBTOR_TELE, cus.getCusMob());
            values.put(Customer.FDEBTOR_IS_SYNC, "4");

            if (!cus.equals(null)) {
                count = dB.update(Customer.TABLE_FDEBTOR, values, DatabaseHelper.DEBCODE + " =?", new String[]{String.valueOf(cus.getCusCode())});
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
        return count;
    }
    public int updateIsSyncedCustomerTxn(String debCode,String status) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            ContentValues values = new ContentValues();
            values.put(Customer.FDEBTOR_IS_SYNC, status);
            count = dB.update(Customer.TABLE_FDEBTOR, values, DatabaseHelper.DEBCODE + " =?", new String[]{String.valueOf(debCode)});

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
        return count;
    }


    public int updateCustomerLocationByCurrentCordinates(String debCode, Double lat, Double lon) {
        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(Customer.FDEBTOR_LATITUDE, lat);
            values.put(Customer.FDEBTOR_LONGITUDE, lon);
            values.put(Customer.FDEBTOR_IS_CORDINATE_UPDATE, "1");
            values.put(Customer.FDEBTOR_IS_SYNC, "3");
            count = (int) dB.update(Customer.TABLE_FDEBTOR, values, DatabaseHelper.DEBCODE + " =?", new String[]{String.valueOf(debCode)});
            Log.d("", "");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dB.close();
        }
        return count;
    }
}
