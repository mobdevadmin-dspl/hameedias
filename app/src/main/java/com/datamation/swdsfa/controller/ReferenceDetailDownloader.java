package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.model.CompanyBranch;
import com.datamation.swdsfa.model.ReferenceDetail;
import com.datamation.swdsfa.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Sathiyaraja on 6/20/2018.
 */

public class ReferenceDetailDownloader {
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;
    Context context;
    private String TAG="ReferenceDetailDownloader";


    public ReferenceDetailDownloader(Context context){
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }

    @SuppressWarnings("static-access")
    public int createOrUpdateFCompanyBranch(ArrayList<CompanyBranch> list) {

        int count = 0;
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {

            for (CompanyBranch branch : list) {

                Cursor cursor = dB.rawQuery("SELECT * FROM " + CompanyBranch.TABLE_FCOMPANYBRANCH + " WHERE " + CompanyBranch.CSETTINGS_CODE + "='" + branch.getFCOMPANYBRANCH_CSETTINGS_CODE() + "' AND " + CompanyBranch.BRANCH_CODE + "='" + branch.getFCOMPANYBRANCH_BRANCH_CODE() + "' AND nYear='" + branch.getNYEAR() + "' AND nMonth='" + branch.getNMONTH() + "'", null);

                ContentValues values = new ContentValues();

                values.put(CompanyBranch.BRANCH_CODE, branch.getFCOMPANYBRANCH_BRANCH_CODE());
                values.put(CompanyBranch.RECORD_ID, "");
                values.put(CompanyBranch.CSETTINGS_CODE, branch.getFCOMPANYBRANCH_CSETTINGS_CODE());
                values.put(CompanyBranch.NNUM_VAL, branch.getFCOMPANYBRANCH_NNUM_VAL());
                values.put(CompanyBranch.FCOMPANYBRANCH_YEAR, branch.getNYEAR());
                values.put(CompanyBranch.FCOMPANYBRANCH_MONTH, branch.getNMONTH());

                if (cursor.getCount() > 0) {
                    dB.update(CompanyBranch.TABLE_FCOMPANYBRANCH, values, CompanyBranch.CSETTINGS_CODE + "=? AND " + CompanyBranch.BRANCH_CODE + "=? AND " + CompanyBranch.FCOMPANYBRANCH_YEAR + "=? AND " + CompanyBranch.FCOMPANYBRANCH_MONTH + "=?", new String[]{branch.getFCOMPANYBRANCH_CSETTINGS_CODE().toString(), branch.getFCOMPANYBRANCH_BRANCH_CODE().toString(), branch.getNYEAR().toString(), branch.getNMONTH().toString()});
                    Log.v(TAG, "Updated");
                } else {
                    count = (int) dB.insert(CompanyBranch.TABLE_FCOMPANYBRANCH, null, values);
                    Log.v(TAG, "Inserted" + count);
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

    @SuppressWarnings("static-access")
    public String getCurrentNextNumVal(String cSettingsCode ){

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Calendar c = Calendar.getInstance();

        String selectQuery = "SELECT * FROM " + CompanyBranch.TABLE_FCOMPANYBRANCH +" WHERE "+CompanyBranch.CSETTINGS_CODE +" ='"+cSettingsCode + "' AND nYear='" + String.valueOf(c.get(Calendar.YEAR)) + "' AND nMonth='" + String.valueOf(c.get(Calendar.MONTH) + 1) + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);

        while(cursor.moveToNext()){

            return cursor.getString(cursor.getColumnIndex(CompanyBranch.NNUM_VAL));

        }

        return "0";
    }

    @SuppressWarnings("static-access")
    public int deleteAll(){

        int count =0;

        if(dB == null){
            open();
        }else if(!dB.isOpen()){
            open();
        }
        Cursor cursor = null;
        try{

            cursor = dB.rawQuery("SELECT * FROM " + CompanyBranch.TABLE_FCOMPANYBRANCH, null);
            count =cursor.getCount();
            if(count>0){
                int success = dB.delete(CompanyBranch.TABLE_FCOMPANYBRANCH, null, null);
                Log.v("Success", success+"");
            }
        }catch (Exception e){

            Log.v(TAG+" Exception", e.toString());

        }finally{
            if (cursor !=null) {
                cursor.close();
            }
            dB.close();
        }

        return count;

    }




}
