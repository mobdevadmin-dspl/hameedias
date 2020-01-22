package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.model.Expense;
import com.datamation.swdsfa.model.Reason;
import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.Reason;

import java.util.ArrayList;

public class ReasonController {

	private SQLiteDatabase dB;
	private DatabaseHelper dbHelper;
	Context context;
	private String TAG = "ReasonController";
	// rashmi - 2019-12-18 move from database_helper , because of reduce coding in database helper*******************************************************************************

	public static final String TABLE_FREASON = "fReason";
	// table attributes
	public static final String FREASON_ID = "freason_id";
	public static final String FREASON_ADD_DATE = "AddDate";
	public static final String FREASON_ADD_MACH = "AddMach";
	public static final String FREASON_ADD_USER = "AddUser";
	public static final String FREASON_CODE = "ReaCode";
	public static final String FREASON_NAME = "ReaName";
	public static final String FREASON_REATCODE = "ReaTcode";
	public static final String FREASON_RECORD_ID = "RecordId";
	public static final String FREASON_TYPE = "Type";

	// create String
	public static final String CREATE_FREASON_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FREASON + " (" + FREASON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FREASON_ADD_DATE + " TEXT, " + FREASON_ADD_MACH + " TEXT, " + FREASON_ADD_USER + " TEXT, " + FREASON_CODE + " TEXT, " + FREASON_NAME + " TEXT, " + FREASON_REATCODE + " TEXT, " + FREASON_RECORD_ID + " TEXT, " + FREASON_TYPE + " TEXT); ";

	public ReasonController(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		dB = dbHelper.getWritableDatabase();
	}

	/*
	 * insert code
	 */
	@SuppressWarnings("static-access")

	public int createOrUpdateReason(ArrayList<Reason> list) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		try {

			for (Reason reason : list) {

				Cursor cursor = dB.rawQuery("SELECT * FROM " + TABLE_FREASON + " WHERE " + FREASON_CODE + "='" + reason.getFREASON_CODE()+ "'", null);

				ContentValues values = new ContentValues();

				values.put(FREASON_NAME, reason.getFREASON_NAME());
				values.put(FREASON_CODE, reason.getFREASON_CODE());
				values.put(FREASON_TYPE, reason.getFREASON_TYPE());
				if (cursor.getCount() > 0) {
					dB.update(TABLE_FREASON, values, FREASON_CODE + "=?", new String[]{reason.getFREASON_CODE().toString()});
					Log.v(TAG, "Updated");
				} else {
					count = (int) dB.insert(TABLE_FREASON, null, values);
					Log.v(TAG, "Inserted " + count);
				}
				cursor.close();
			}

		} catch (Exception e) {
			Log.v(TAG, e.toString());
		} finally {
			dB.close();
		}
		return count;

	}
	/*
	 * delete code
	 */
	@SuppressWarnings("static-access")
	public int deleteAll() {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;
		try {

			cursor = dB.rawQuery("SELECT * FROM " + TABLE_FREASON, null);
			count = cursor.getCount();
			if (count > 0) {
				int success = dB.delete(TABLE_FREASON, null, null);
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
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//	public ArrayList<Reason> getAllReasonsByType(String code) {
//		if (dB == null) {
//			open();
//		} else if (!dB.isOpen()) {
//			open();
//		}
//
//		ArrayList<Reason> list = new ArrayList<Reason>();
//
//		String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FREASON + " WHERE trim(" + dbHelper.FREASON_TYPE + ") ='" + code + "'";
//
//		Cursor cursor = dB.rawQuery(selectQuery, null);
//		while (cursor.moveToNext()) {
//
//			Reason res = new Reason();
//			res.setFREASON_CODE(cursor.getString(cursor.getColumnIndex(dbHelper.FREASON_CODE)));
//			res.setFREASON_NAME(cursor.getString(cursor.getColumnIndex(dbHelper.FREASON_NAME)));
//
//			list.add(res);
//
//		}
//
//		return list;
//	}

	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public ArrayList<String> getAllReasonsByType(String code) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<String> list = new ArrayList<String>();

		String selectQuery = "SELECT * FROM " + TABLE_FREASON + " WHERE trim(" + FREASON_TYPE + ") ='" + code + "'";

		Cursor cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {

			String reason = "";

			reason =  cursor.getString(cursor.getColumnIndex(FREASON_CODE))+" - "+cursor.getString(cursor.getColumnIndex(FREASON_NAME));

			list.add(reason);

		}

		return list;
	}
	public ArrayList<Reason> getAllReasons() {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<Reason> list = new ArrayList<Reason>();

		String selectQuery = "SELECT * FROM " + TABLE_FREASON;

		Cursor cursor = dB.rawQuery(selectQuery, null);
		while (cursor.moveToNext()) {

			Reason reason = new Reason();

			reason.setFREASON_CODE(cursor.getString(cursor.getColumnIndex(FREASON_CODE)));
			reason.setFREASON_NAME(cursor.getString(cursor.getColumnIndex(FREASON_NAME)));

			list.add(reason);

		}

		return list;
	}
	public ArrayList<String> getReasonName() {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<String> FREASON = new ArrayList<String>();

		//String selectQuery = "SELECT * FROM " + dbHelper.TABLE_FREASON + " WHERE " + dbHelper.FREASON_CODE + "='RT01' OR dbHelper.FREASON_CODE='RT02'";
		String selectQuery = "SELECT * FROM " + TABLE_FREASON;

		Cursor cursor = null;
		cursor = dB.rawQuery(selectQuery, null);
		FREASON.add("Tap to select a Reason");

		while (cursor.moveToNext()) {

			FREASON.add(cursor.getString(cursor.getColumnIndex(FREASON_NAME)));

		}

		return FREASON;
	}

	public String getReaCodeByName(String name) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "SELECT * FROM " + TABLE_FREASON + " WHERE " + FREASON_NAME + "='" + name + "'";

		Cursor cursor = null;
		cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {
			return cursor.getString(cursor.getColumnIndex(FREASON_CODE));
		}

		return "";
	}

//	public ArrayList<Reason> getAllExpense(String excode) {
//		if (dB == null) {
//			open();
//		} else if (!dB.isOpen()) {
//			open();
//		}
//
//		ArrayList<Reason> list = new ArrayList<Reason>();
//		String selectQuery = null;
//		if(excode.equals(""))
//			selectQuery = "SELECT * FROM " + dbHelper.TABLE_FREASON ;
//		else
//			selectQuery = "SELECT * FROM " + dbHelper.TABLE_FREASON + " WHERE " + dbHelper.FREASON_CODE + "='" + excode + "'";
//
//		Cursor cursor = dB.rawQuery(selectQuery, null);
//		while (cursor.moveToNext()) {
//
//			Reason expense = new Reason();
//
//			expense.setFREASON_CODE(cursor.getString(cursor.getColumnIndex(dbHelper.FREASON_CODE)));
//			expense.setFREASON_NAME(cursor.getString(cursor.getColumnIndex(dbHelper.FREASON_NAME)));
//
//			list.add(expense);
//
//		}
//
//		return list;
//	}


	public String getReasonByReaCode(String reaCode) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "SELECT * FROM " + TABLE_FREASON + " WHERE " + FREASON_CODE + "='" + reaCode + "'";

		Cursor cursor = null;
		cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {
			return cursor.getString(cursor.getColumnIndex(FREASON_NAME));
		}

		return "";
	}

	public ArrayList<Reason> getAllNonPrdReasons() {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<Reason> list = new ArrayList<Reason>();

//		String selectQuery = "SELECT * FROM " + TABLE_FREASON + " WHERE " + FREASON_TYPE + "='np'";
		String selectQuery = "SELECT * FROM " + TABLE_FREASON;

		Cursor cursor = dB.rawQuery(selectQuery, null);
		while (cursor.moveToNext()) {

			Reason FREASON = new Reason();

			FREASON.setFREASON_CODE(cursor.getString(cursor.getColumnIndex(FREASON_CODE)));
			FREASON.setFREASON_NAME(cursor.getString(cursor.getColumnIndex(FREASON_NAME)));

			list.add(FREASON);

		}

		return list;
	}

	public ArrayList<Reason> getDebDetails(String searchword) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<Reason> Itemname = new ArrayList<Reason>();

		String selectQuery = "select * from fReason where ReaTcode='RT02' AND ReaCode LIKE '%" + searchword + "%' OR ReaName LIKE '%" + searchword + "%' ";

		Cursor cursor = null;
		cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {
			Reason items = new Reason();

			items.setFREASON_CODE(cursor.getString(cursor.getColumnIndex(FREASON_NAME)));
			items.setFREASON_NAME(cursor.getString(cursor.getColumnIndex(FREASON_CODE)));
			Itemname.add(items);
		}

		return Itemname;
	}

	@SuppressWarnings("static-access")
	public String getReaNameByCode(String code) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "SELECT * FROM " + TABLE_FREASON + " WHERE " + FREASON_CODE + "='" + code + "'";

		Cursor cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {

			return cursor.getString(cursor.getColumnIndex(FREASON_NAME));

		}

		return "";
	}

}
