package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.CompanyBranch;
import com.datamation.swdsfa.model.CompanySetting;
import com.datamation.swdsfa.model.Reference;
import com.datamation.swdsfa.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class ReferenceController {
	private SQLiteDatabase dB;
	private DatabaseHelper dbHelper;
	Context context;
	private String TAG = "ReferenceController";

	public ReferenceController(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		dB = dbHelper.getWritableDatabase();
	}

	public void isNewMonth(String cCode) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		try {

			//String RepCode = new SalRepDS(context).getCurrentRepCode();

			Calendar c = Calendar.getInstance();
			Cursor cursor = dB.rawQuery("SELECT * FROM " + CompanyBranch.TABLE_FCOMPANYBRANCH + " WHERE cSettingsCode='" + cCode + "' AND nYear='" + String.valueOf(c.get(Calendar.YEAR)) + "' AND nMonth='" + String.valueOf(c.get(Calendar.MONTH) + 1) + "'", null);

			if (cursor.getCount() == 0) {

				ContentValues values = new ContentValues();
			//	values.put(DatabaseHelper.REFERENCE_REPCODE, RepCode);
			//	values.put(DatabaseHelper.FCOMPANYBRANCH_ID, "");
				values.put(CompanyBranch.CSETTINGS_CODE, cCode);
				values.put(CompanyBranch.NNUM_VAL, "1");
				values.put(CompanyBranch.FCOMPANYBRANCH_YEAR, String.valueOf(c.get(Calendar.YEAR)));
				values.put(CompanyBranch.FCOMPANYBRANCH_MONTH, String.valueOf(c.get(Calendar.MONTH) + 1));

				dB.insert(CompanyBranch.TABLE_FCOMPANYBRANCH, null, values);

			}
		} catch (Exception e) {
			Log.v(TAG, e.toString());
		} finally {
			dB.close();
		}

	}

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
	public String getCurrentNextNumVal(String cSettingsCode) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		Calendar c = Calendar.getInstance();

		String selectQuery = "SELECT * FROM " + CompanyBranch.TABLE_FCOMPANYBRANCH + " WHERE " + CompanyBranch.BRANCH_CODE + "='" + new SalRepController(context).getCurrentRepCode() + "' AND " + CompanyBranch.CSETTINGS_CODE + "='" + cSettingsCode + "' AND nYear='" + String.valueOf(c.get(Calendar.YEAR)) + "' AND nMonth='" + String.valueOf(c.get(Calendar.MONTH) + 1) + "'";

		Cursor cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {

			return cursor.getString(cursor.getColumnIndex(CompanyBranch.NNUM_VAL));

		}

		return "0";
	}

	public String getNextNumVal(String cSettingsCode,String repcode) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String nextNumVal = "";

		Calendar c = Calendar.getInstance();

		try {
			String query = "SELECT " + CompanyBranch.NNUM_VAL +" from "+ CompanyBranch.TABLE_FCOMPANYBRANCH + " WHERE " + CompanyBranch.BRANCH_CODE + " = '" + repcode + "'  AND cSettingsCode = '" + cSettingsCode + "' AND nYear='" + String.valueOf(c.get(Calendar.YEAR)) + "' AND nMonth='" + String.valueOf(c.get(Calendar.MONTH) + 1) + "'";
			Cursor cursor = dB.rawQuery(query, null);
			int count = cursor.getCount();
			if (count > 0) {
				while (cursor.moveToNext()) {
					nextNumVal = cursor.getString(cursor.getColumnIndex(CompanyBranch.NNUM_VAL));
				}
			} else {
				nextNumVal = "1";
			}
		} catch (Exception e) {
			Log.v(TAG, e.toString());
		} finally {
			dB.close();
		}

		return nextNumVal;
	}

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public ArrayList<Reference> getCurrentPreFix(String cSettingsCode, String repPrefix) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<Reference> list = new ArrayList<Reference>();

		try {
			String selectRep = "select cCharVal from fCompanySetting where cSettingsCode ='" + cSettingsCode + "'";

			Cursor cursor = null;
			cursor = dB.rawQuery(selectRep, null);

			while (cursor.moveToNext()) {

				Reference reference = new Reference();

				reference.setCharVal(cursor.getString(cursor.getColumnIndex(CompanySetting.CHAR_VAL)));
				reference.setRepPrefix(repPrefix);
				list.add(reference);

			}
		} catch (Exception e) {
			Log.v(TAG, e.toString());
		} finally {
			dB.close();
		}
		return list;
	}

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public int getCount(String cSettingsCode,String repcode) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		int count = 0;

		try {
			count = 0;

			String query = "SELECT " + CompanyBranch.NNUM_VAL + " FROM " + CompanyBranch.TABLE_FCOMPANYBRANCH + " WHERE " + CompanyBranch.BRANCH_CODE + " ='" + repcode + "' AND " + CompanyBranch.CSETTINGS_CODE + "='" + cSettingsCode + "'";
			Cursor cursor = dB.rawQuery(query, null);
			count = cursor.getCount();

		} catch (Exception e) {
			Log.v(TAG, e.toString());
		} finally {
			dB.close();
		}

		return count;

	}

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public int InsetOrUpdate(String code, int nextNumVal) {
		int count = 0;
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		try {

			Calendar c = Calendar.getInstance();

		//	SalRepDS repDS = new SalRepDS(context);

			ContentValues values = new ContentValues();

			values.put(CompanyBranch.NNUM_VAL, String.valueOf(nextNumVal));

			//String query = "SELECT " + dbHelper.REFERENCE_NNUM_VAL + " FROM " + dbHelper.TABLE_REFERENCE + " WHERE " + dbHelper.REFERENCE_REPCODE + "='" + repDS.getCurrentRepCode() + "' AND " + dbHelper.REFERENCE_SETTINGS_CODE + "='" + code + "' AND nYear='" + String.valueOf(c.get(Calendar.YEAR)) + "' AND nMonth='" + String.valueOf(c.get(Calendar.MONTH) + 1) + "'";
			String query = "SELECT " + CompanyBranch.NNUM_VAL + " FROM " + CompanyBranch.TABLE_FCOMPANYBRANCH + " WHERE "  + CompanyBranch.CSETTINGS_CODE + "='" + code + "' AND nYear='" + String.valueOf(c.get(Calendar.YEAR)) + "' AND nMonth='" + String.valueOf(c.get(Calendar.MONTH) + 1) + "'";
			Cursor cursor = dB.rawQuery(query, null);

			if (cursor.getCount() > 0) {
				count = (int) dB.update(CompanyBranch.TABLE_FCOMPANYBRANCH, values,  CompanyBranch.CSETTINGS_CODE + "='" + code + "' AND nYear='" + String.valueOf(c.get(Calendar.YEAR)) + "' AND nMonth='" + String.valueOf(c.get(Calendar.MONTH) + 1) + "'", null);
			} else {
				values.put(CompanyBranch.BRANCH_CODE, SharedPref.getInstance(context).getLoginUser().getCode());
				values.put(CompanyBranch.CSETTINGS_CODE, code);
				values.put(CompanyBranch.FCOMPANYBRANCH_YEAR, String.valueOf(c.get(Calendar.YEAR)));
				values.put(CompanyBranch.FCOMPANYBRANCH_MONTH, String.valueOf(c.get(Calendar.MONTH) + 1));

				count = (int) dB.insert(CompanyBranch.TABLE_FCOMPANYBRANCH, null, values);
			}

		} catch (Exception e) {
			Log.v(TAG, e.toString());
		} finally {
			dB.close();
		}
		return count;

	}

}
