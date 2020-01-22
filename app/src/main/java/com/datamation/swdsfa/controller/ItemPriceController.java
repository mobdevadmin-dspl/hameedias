package com.datamation.swdsfa.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.swdsfa.model.DayNPrdHed;
import com.datamation.swdsfa.model.ItemPri;
import com.datamation.swdsfa.helpers.DatabaseHelper;

import java.util.ArrayList;

public class ItemPriceController {

	private SQLiteDatabase dB;
	private DatabaseHelper dbHelper;
	Context context;
	private String TAG = "ItemPriceController";
	// rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

	// table
	public static final String TABLE_FITEMPRI = "fItemPri";
	// table attributes
	public static final String FITEMPRI_ID = "fItemPri_id";
	public static final String FITEMPRI_ADD_MACH = "AddMach";
	public static final String FITEMPRI_ADD_USER = "AddUser";
	public static final String FITEMPRI_ITEM_CODE = "ItemCode";
	public static final String FITEMPRI_PRICE = "Price";
	public static final String FITEMPRI_PRIL_CODE = "PrilCode";
	public static final String FITEMPRI_TXN_MACH = "TxnMach";
	public static final String FITEMPRI_TXN_USER = "Txnuser";
	public static final String FITEMPRI_COST_CODE = "CostCode";

	// create String
	public static final String CREATE_FITEMPRI_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FITEMPRI + " (" + FITEMPRI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FITEMPRI_ADD_MACH + " TEXT, " + FITEMPRI_ADD_USER + " TEXT, " + FITEMPRI_ITEM_CODE + " TEXT, " + FITEMPRI_PRICE + " TEXT, " + FITEMPRI_PRIL_CODE + " TEXT, " + FITEMPRI_TXN_MACH + " TEXT, " + FITEMPRI_TXN_USER + " TEXT, " + FITEMPRI_COST_CODE + " TEXT); ";

	public static final String TESTITEMPRI = "CREATE UNIQUE INDEX IF NOT EXISTS idxitempri_something ON " + TABLE_FITEMPRI + " (" + FITEMPRI_ITEM_CODE + "," + FITEMPRI_PRIL_CODE + "," + FITEMPRI_COST_CODE + ")";

	public ItemPriceController(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		dB = dbHelper.getWritableDatabase();
	}

	@SuppressWarnings("static-access")
	public void InsertOrReplaceItemPri(ArrayList<ItemPri> list) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		try {
			dB.beginTransactionNonExclusive();
			String sql = "INSERT OR REPLACE INTO " + TABLE_FITEMPRI + " (AddMach,AddUser,ItemCode,Price,PrilCode,TxnMach,Txnuser) VALUES (?,?,?,?,?,?,?)";

			SQLiteStatement stmt = dB.compileStatement(sql);

			for (ItemPri itemPri : list) {

				stmt.bindString(1, itemPri.getFITEMPRI_ADD_MACH());
				stmt.bindString(2, itemPri.getFITEMPRI_ADD_USER());
				stmt.bindString(3, itemPri.getFITEMPRI_ITEM_CODE());
				stmt.bindString(4, itemPri.getFITEMPRI_PRICE());
				stmt.bindString(5, itemPri.getFITEMPRI_PRIL_CODE());
				stmt.bindString(6, itemPri.getFITEMPRI_TXN_MACH());
				stmt.bindString(7, itemPri.getFITEMPRI_TXN_USER());

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
	public int deleteAllItemPri() {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;
		try {

			cursor = dB.rawQuery("SELECT * FROM " + TABLE_FITEMPRI, null);
			count = cursor.getCount();
			if (count > 0) {
				int success = dB.delete(TABLE_FITEMPRI, null, null);
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

	public String getProductPriceByCode(String code, String prilcode) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;
		try {
			String selectQuery = "SELECT * FROM " + TABLE_FITEMPRI + " WHERE " + FITEMPRI_ITEM_CODE + "='" + code + "' AND " + FITEMPRI_PRIL_CODE + "='" + prilcode + "'";


			cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				return cursor.getString(cursor.getColumnIndex(FITEMPRI_PRICE));

			}
		}catch (Exception e) {

			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			dB.close();
		}
		return "";

	}

}
