package com.datamation.hmdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.NewCustomer;
import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.helpers.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Rashmi on 25/12/2018.
 */

public class NewCustomerController {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    private String TAG = "Finac New";
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;

    // rashmi - 2019-12-17 move from database_helper , because of reduce coding in database helper*******************************************************************************

    //NEW CUSTOMER REGISTRATION
    public static final String TABLE_NEW_CUSTOMER = "NewCustomer";
    public static final String TABLE_REC_ID = "recID"; //1
    public static final String CUSTOMER_ID = "customerID"; //2
    public static final String CUSTOMER_OTHER_CODE = "otherCode";//3
    public static final String COMPANY_REG_CODE = "comRegCode"; //4
    public static final String REG_DATE = "RegDate";
    public static final String NAME = "Name"; //5
    public static final String NIC = "Nic"; //6
    public static final String ADDRESS1 = "Address1"; //7
    public static final String ADDRESS2 = "Address2"; //8
    public static final String CITY = "City"; //9
    public static final String CONTACT_PERSON = "ContactPerson";
    public static final String PHONE = "Phone"; //10
    public static final String MOBILE = "Mobile"; //27
    public static final String FAX = "Fax"; //11
    public static final String E_MAIL = "Email"; //12
    public static final String C_TOWN = "customer_Town";  //13
    public static final String DISTRICT = "District"; //15
    public static final String OLD_CODE = "old_Code"; //16

    public static final String C_IMAGE = "Image"; //18
    public static final String C_IMAGE1 = "Image1";  //19
    public static final String C_IMAGE2 = "Image2"; //20
    public static final String C_IMAGE3 = "Image3";  //21
    public static final String C_LONGITUDE = "lng";  //22
    public static final String C_LATITUDE = "lat"; //23
    public static final String C_ADD_DATE = "AddDate"; //24
    public static final String C_ADD_MACH = "AddMach"; //25
    public static final String C_ACCNO = "AccNo"; //26
    public static final String C_CRDTLMT = "CreditLmt"; //27
    public static final String C_CRDTPRD = "CreditPeriod"; //28
    public static final String C_BANK = "Bank"; //28
    public static final String C_IS_SYNCED = "isSynced"; //29
    public static final String ROUTE_ID = "RouteID";
    public static final String CREATE_NEW_CUSTOMER = "CREATE  TABLE IF NOT EXISTS " + TABLE_NEW_CUSTOMER + " ("
            + TABLE_REC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CUSTOMER_ID + " TEXT, "
            + NAME + " TEXT, "
            + NIC + " TEXT, "
            + CUSTOMER_OTHER_CODE + " TEXT, "
            + COMPANY_REG_CODE + " TEXT, "
            + REG_DATE + " TEXT, "
            + DISTRICT + " TEXT, "
            + C_TOWN + " TEXT, "
            + ROUTE_ID + " TEXT, "
            + ADDRESS1 + " TEXT, "
            + ADDRESS2 + " TEXT, "
            + CITY + " TEXT, "
            + CONTACT_PERSON + " TEXT, "
            + MOBILE + " TEXT, "
            + PHONE + " TEXT, "
            + FAX + " TEXT, "
            + E_MAIL + " TEXT, "
            + OLD_CODE + " TEXT, "
            + C_IMAGE + " TEXT, "
            + C_IMAGE1 + " TEXT, "
            + C_IMAGE2 + " TEXT, "
            + C_IMAGE3 + " TEXT, "
            + C_LONGITUDE + " TEXT, "
            + C_LATITUDE + " TEXT, "
            + C_ADD_DATE + " TEXT, "
            + C_ADD_MACH + " TEXT, "
            + C_CRDTLMT + " TEXT, "
            + C_CRDTPRD + " TEXT, "
            + C_BANK + " TEXT, "
            + C_ACCNO + " TEXT, "
            + C_IS_SYNCED + " TEXT); ";

    public NewCustomerController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    public int createOrUpdateCustomer(ArrayList<NewCustomer> list) {

        int serverdbID = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = null;
        try {

            for (NewCustomer fN : list) {

                ContentValues contentValues = new ContentValues();
                String selectQuery = "SELECT * FROM " + TABLE_NEW_CUSTOMER + " WHERE " + CUSTOMER_ID + " = '" + fN.getCUSTOMER_ID() + "'";
                cursor = dB.rawQuery(selectQuery, null);
                contentValues.put(CUSTOMER_ID, fN.getCUSTOMER_ID());
                contentValues.put(CUSTOMER_OTHER_CODE, fN.getC_OTHERCODE());
                contentValues.put(COMPANY_REG_CODE, fN.getC_REGNUM());
                contentValues.put(NAME, fN.getNAME());
                contentValues.put(NIC, fN.getCUSTOMER_NIC());
                contentValues.put(REG_DATE, fN.getC_DATE());
                contentValues.put(ADDRESS1, fN.getADDRESS1());
                contentValues.put(ADDRESS2, fN.getADDRESS2());
                contentValues.put(CITY, fN.getCITY());
                contentValues.put(CONTACT_PERSON, fN.getCONTACTPERSON());
                contentValues.put(PHONE, fN.getPHONE());
                contentValues.put(MOBILE, fN.getMOBILE());
                contentValues.put(FAX, fN.getFAX());
                contentValues.put(E_MAIL, fN.getE_MAIL());
                contentValues.put(C_TOWN, fN.getC_TOWN());
                contentValues.put(ROUTE_ID, fN.getROUTE_ID());
                contentValues.put(DISTRICT, fN.getDISTRICT());
                contentValues.put(OLD_CODE, fN.getOLD_CODE());
                contentValues.put(C_IMAGE, fN.getC_IMAGE());
                contentValues.put(C_LONGITUDE, fN.getC_LONGITUDE());
                contentValues.put(C_LATITUDE, fN.getC_LATITUDE());
                contentValues.put(C_ADD_DATE, fN.getC_ADDDATE());
                contentValues.put(C_ADD_MACH, fN.getAddMac());
                contentValues.put(C_IS_SYNCED, fN.getC_SYNCSTATE());
                contentValues.put(C_BANK, fN.getBANK());
                contentValues.put(C_ACCNO, fN.getACCNO());
                contentValues.put(C_CRDTLMT, fN.getCRDTLIMIT());
                contentValues.put(C_CRDTPRD, fN.getCRDTPERIOD());

                int cn = cursor.getCount();
                if (cn > 0) {
                    serverdbID = dB.update(TABLE_NEW_CUSTOMER, contentValues, CUSTOMER_ID + " = '" + fN.getCUSTOMER_ID() + "'", null);
                } else {
                    serverdbID = (int) dB.insert(TABLE_NEW_CUSTOMER, null, contentValues);
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
        return serverdbID;

    }
//----------------------------------------------------------------------------------------------------------------

    public ArrayList<NewCustomer> getAllNewCusDetails(String newTExt, String uploaded) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<NewCustomer> list = new ArrayList<NewCustomer>();
        String selectQuery;

        selectQuery = "select * from " + TABLE_NEW_CUSTOMER;

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            NewCustomer fCustomer = new NewCustomer();
            fCustomer.setCUSTOMER_ID(cursor.getString(cursor.getColumnIndex(CUSTOMER_ID)));
            fCustomer.setC_ADDDATE(cursor.getString(cursor.getColumnIndex(C_ADD_DATE)));
            fCustomer.setNAME(cursor.getString(cursor.getColumnIndex(NAME)));
            fCustomer.setC_SYNCSTATE(cursor.getString(cursor.getColumnIndex(C_IS_SYNCED)));
            fCustomer.setC_IMAGE(cursor.getString(cursor.getColumnIndex(C_IMAGE)));
            list.add(fCustomer);
        }

        return list;
    }

    //    get All customers for upload(sync)
    public ArrayList<NewCustomer> getAllNewCustomersForSync() {
        if (dB == null)
            open();
        else if (!dB.isOpen())
            open();

        ArrayList<NewCustomer> newCusList = new ArrayList<>();
        String selectQuery;
        selectQuery = "select * from " + TABLE_NEW_CUSTOMER+ " where "+C_IS_SYNCED+" = '0' ";
        Cursor cursor = dB.rawQuery(selectQuery,null);

        try {
            while (cursor.moveToNext()) {
                NewCustomer nCustomer = new NewCustomer();

                nCustomer.setnNumVal(new ReferenceController(context).getCurrentNextNumVal(context.getResources().getString(R.string.newCusVal)));
                nCustomer.setDISTRIBUTE_DB(SharedPref.getInstance(context).getDistDB().trim());
                nCustomer.setCONSOLE_DB(SharedPref.getInstance(context).getConsoleDB().trim());

                nCustomer.setC_REPCODE(new SalRepController(context).getCurrentRepCode());

                nCustomer.setC_IMAGE(cursor.getString(cursor.getColumnIndex(C_IMAGE)));
                nCustomer.setCUSTOMER_ID(cursor.getString(cursor.getColumnIndex(CUSTOMER_ID)));
                nCustomer.setNAME(cursor.getString(cursor.getColumnIndex(NAME)));
                nCustomer.setCUSTOMER_NIC(cursor.getString(cursor.getColumnIndex(NIC)));
                nCustomer.setC_REGNUM(cursor.getString(cursor.getColumnIndex(COMPANY_REG_CODE)));
                nCustomer.setC_ADDDATE(cursor.getString(cursor.getColumnIndex(C_ADD_DATE)));
                nCustomer.setC_TOWN(cursor.getString(cursor.getColumnIndex(C_TOWN)));
                nCustomer.setROUTE_ID(cursor.getString(cursor.getColumnIndex(ROUTE_ID)));
                nCustomer.setADDRESS1(cursor.getString(cursor.getColumnIndex(ADDRESS1)));
                nCustomer.setADDRESS2(cursor.getString(cursor.getColumnIndex(ADDRESS2)));
                nCustomer.setCITY(cursor.getString(cursor.getColumnIndex(CITY)));
                nCustomer.setCONTACTPERSON(cursor.getString(cursor.getColumnIndex(CONTACT_PERSON)));
                nCustomer.setMOBILE(cursor.getString(cursor.getColumnIndex(MOBILE)));
                nCustomer.setPHONE(cursor.getString(cursor.getColumnIndex(PHONE)));
                nCustomer.setFAX(cursor.getString(cursor.getColumnIndex(FAX)));
                nCustomer.setE_MAIL(cursor.getString(cursor.getColumnIndex(E_MAIL)));
                nCustomer.setAddMac(cursor.getString(cursor.getColumnIndex(C_ADD_MACH)));
                newCusList.add(nCustomer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return newCusList;
    }

//---------------------get all Customer for edit------------------------------------------------------------------------

    public ArrayList<NewCustomer> getAllNewCusDetailsForEdit(String newTExt) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<NewCustomer> list = new ArrayList<NewCustomer>();
        String selectQuery = "select * from " + TABLE_NEW_CUSTOMER + " WHERE Name LIKE '%" + newTExt + "%' and isSynced=0";
        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            NewCustomer fCustomer = new NewCustomer();
            fCustomer.setCUSTOMER_ID(cursor.getString(cursor.getColumnIndex(CUSTOMER_ID)));
            fCustomer.setC_OTHERCODE(cursor.getString(cursor.getColumnIndex(CUSTOMER_OTHER_CODE)));
            fCustomer.setC_REGNUM(cursor.getString(cursor.getColumnIndex(COMPANY_REG_CODE)));
            fCustomer.setNAME(cursor.getString(cursor.getColumnIndex(NAME)));
            fCustomer.setCUSTOMER_NIC(cursor.getString(cursor.getColumnIndex(NIC)));
            fCustomer.setADDRESS1(cursor.getString(cursor.getColumnIndex(ADDRESS1)));
            fCustomer.setADDRESS2(cursor.getString(cursor.getColumnIndex(ADDRESS2)));
            fCustomer.setCITY(cursor.getString(cursor.getColumnIndex(CITY)));
            fCustomer.setC_DATE(cursor.getString(cursor.getColumnIndex(REG_DATE)));
            fCustomer.setCONTACTPERSON(cursor.getString(cursor.getColumnIndex(CONTACT_PERSON)));
            fCustomer.setPHONE(cursor.getString(cursor.getColumnIndex(PHONE)));
            fCustomer.setMOBILE(cursor.getString(cursor.getColumnIndex(MOBILE)));
            fCustomer.setFAX(cursor.getString(cursor.getColumnIndex(FAX)));
            fCustomer.setE_MAIL(cursor.getString(cursor.getColumnIndex(E_MAIL)));
            fCustomer.setC_TOWN(cursor.getString(cursor.getColumnIndex(C_TOWN)));
            fCustomer.setROUTE_ID(cursor.getString(cursor.getColumnIndex(ROUTE_ID)));
            fCustomer.setDISTRICT(cursor.getString(cursor.getColumnIndex(DISTRICT)));
            fCustomer.setC_SYNCSTATE(cursor.getString(cursor.getColumnIndex(C_IS_SYNCED)));
            fCustomer.setC_IMAGE(cursor.getString(cursor.getColumnIndex(C_IMAGE)));
            fCustomer.setC_IMAGE1(cursor.getString(cursor.getColumnIndex(C_IMAGE1)));
            fCustomer.setC_IMAGE2(cursor.getString(cursor.getColumnIndex(C_IMAGE2)));
            fCustomer.setC_IMAGE3(cursor.getString(cursor.getColumnIndex(C_IMAGE3)));

            list.add(fCustomer);
        }

        return list;
    }
    public ArrayList<NewCustomer> getAllCusDetailsForEdit(String newTExt) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<NewCustomer> list = new ArrayList<NewCustomer>();
        String selectQuery = "select * from " + Customer.TABLE_FDEBTOR + " WHERE DebName LIKE '%" + newTExt + "%'";
        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            NewCustomer fCustomer = new NewCustomer();
            fCustomer.setCUSTOMER_ID(cursor.getString(cursor.getColumnIndex(Customer.DEBCODE)));
            fCustomer.setC_OTHERCODE(cursor.getString(cursor.getColumnIndex(Customer.DEBCODE)));
            fCustomer.setC_REGNUM(cursor.getString(cursor.getColumnIndex(Customer.DEBCODE)));
            fCustomer.setNAME(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_NAME)));
            fCustomer.setCUSTOMER_NIC(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_NIC)));
            fCustomer.setADDRESS1(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD1)));
            fCustomer.setADDRESS2(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_ADD2)));
            fCustomer.setCONTACTPERSON(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_NAME)));
            fCustomer.setPHONE(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_TELE)));
            fCustomer.setMOBILE(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_MOB)));
            fCustomer.setFAX(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_TELE)));
            fCustomer.setE_MAIL(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_EMAIL)));
            fCustomer.setC_TOWN(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_TOWN_CODE)));
            fCustomer.setCITY(cursor.getString(cursor.getColumnIndex(Customer.FDEBTOR_TOWN_CODE)));
            list.add(fCustomer);
        }

        return list;
    }
    public int updateIsSynced(String customerID, String res) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(C_IS_SYNCED, "1");
            if (res.equalsIgnoreCase("1")) {
                count = dB.update(TABLE_NEW_CUSTOMER, values, CUSTOMER_ID + " =?", new String[]{String.valueOf(customerID)});
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;
    }


    public boolean isEntrySynced(String Refno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Cursor cursor = dB.rawQuery("select isSynced from NewCustomer where customerID ='" + Refno + "'", null);

        while (cursor.moveToNext()) {

            String result = cursor.getString(cursor.getColumnIndex(C_IS_SYNCED));

            if (result.equals("1"))
                return true;

        }
        cursor.close();
        dB.close();
        return false;

    }

    public int deleteRecord(String refno) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;

        try {

            String selectQuery = "SELECT * FROM " + TABLE_NEW_CUSTOMER + " WHERE " + CUSTOMER_ID + " = '" + refno + "'";
            cursor = dB.rawQuery(selectQuery, null);
            int cn = cursor.getCount();

            if (cn > 0) {
                count = dB.delete(TABLE_NEW_CUSTOMER, CUSTOMER_ID + " ='" + refno + "'", null);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return count;

    }


    public ArrayList<NewCustomer> getUnsyncRecord() {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        ReferenceDetailDownloader branchDS = new ReferenceDetailDownloader(context);
        SalRepController fSalRe = new SalRepController(context);
        ArrayList<NewCustomer> list = new ArrayList<NewCustomer>();
        String selectQuery = "select * from " + TABLE_NEW_CUSTOMER + " WHERE  isSynced='N'";
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        Cursor cursor = dB.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            NewCustomer fCustomer = new NewCustomer();
            fCustomer.setCUSTOMER_ID(cursor.getString(cursor.getColumnIndex(CUSTOMER_ID)));
            fCustomer.setC_ADDDATE(cursor.getString(cursor.getColumnIndex(C_ADD_DATE)));
            fCustomer.setNAME(cursor.getString(cursor.getColumnIndex(NAME)));
            fCustomer.setC_SYNCSTATE(cursor.getString(cursor.getColumnIndex(C_IS_SYNCED)));
            fCustomer.setnNumVal(branchDS.getCurrentNextNumVal(context.getResources().getString(R.string.newCusVal)));
            fCustomer.setCONSOLE_DB(localSP.getString("Console_DB", "").toString());
            fCustomer.setC_REPCODE(fSalRe.getCurrentRepCode());
            fCustomer.setC_OTHERCODE(cursor.getString(cursor.getColumnIndex(CUSTOMER_OTHER_CODE)));
            fCustomer.setC_REGNUM(cursor.getString(cursor.getColumnIndex(COMPANY_REG_CODE)));
            fCustomer.setCUSTOMER_NIC(cursor.getString(cursor.getColumnIndex(NIC)));
            fCustomer.setFAX(cursor.getString(cursor.getColumnIndex(FAX)));
            fCustomer.setC_TOWN(cursor.getString(cursor.getColumnIndex(C_TOWN)));
            fCustomer.setROUTE_ID(cursor.getString(cursor.getColumnIndex(ROUTE_ID)));
            fCustomer.setDISTRICT(cursor.getString(cursor.getColumnIndex(DISTRICT)));
            fCustomer.setADDRESS1(cursor.getString(cursor.getColumnIndex(ADDRESS1)));
            fCustomer.setADDRESS2(cursor.getString(cursor.getColumnIndex(ADDRESS2)));
            fCustomer.setCITY(cursor.getString(cursor.getColumnIndex(CITY)));
            fCustomer.setMOBILE(cursor.getString(cursor.getColumnIndex(MOBILE)));
            fCustomer.setE_MAIL(cursor.getString(cursor.getColumnIndex(E_MAIL)));
            fCustomer.setC_IMAGE(cursor.getString(cursor.getColumnIndex(C_IMAGE)));
            fCustomer.setC_IMAGE1(cursor.getString(cursor.getColumnIndex(C_IMAGE1)));
            fCustomer.setC_IMAGE2(cursor.getString(cursor.getColumnIndex(C_IMAGE2)));
            fCustomer.setC_IMAGE3(cursor.getString(cursor.getColumnIndex(C_IMAGE3)));
            fCustomer.setC_LATITUDE(cursor.getString(cursor.getColumnIndex(C_LATITUDE)));
            fCustomer.setC_LONGITUDE(cursor.getString(cursor.getColumnIndex(C_LONGITUDE)));
            fCustomer.setAddMac("NA");
            fCustomer.setPHONE(cursor.getString(cursor.getColumnIndex(PHONE)));
            fCustomer.setOLD_CODE(cursor.getString(cursor.getColumnIndex(OLD_CODE)));


            list.add(fCustomer);
        }
        return list;
    }
}
