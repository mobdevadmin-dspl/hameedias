package com.datamation.hmdsfa.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.model.ItenrDeb;
import com.datamation.hmdsfa.model.VatMaster;

import java.util.ArrayList;

public class IteaneryDebController {
	private SQLiteDatabase dB;
	private DatabaseHelper dbHelper;
	Context context;
	private String TAG = "IteaneryDeb ";
	// rashmi - 2019-12-24 move from database_helper , because of reduce coding in database helper*******************************************************************************

	public static final String TABLE_ITEANERY_DEB = "IteaneryDeb";
	// table attributes
	public static final String  ID = "Id";
	public static final String DEBCODE = "DebCode";
	public static final String REFNO = "RefNo";
	public static final String ROUTECODE = "RouteCode";
	public static final String TXNDATE = "TxnDate";


	// create String
	public static final String CREATE_TABLE_ITEDEB = "CREATE  TABLE IF NOT EXISTS " + TABLE_ITEANERY_DEB + " (" +
			ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DEBCODE + " TEXT, " +
			REFNO + " TEXT, " + ROUTECODE + " TEXT, "  + TXNDATE + " TEXT); ";

	//public static final String TESTINVDETL3 = "CREATE UNIQUE INDEX IF NOT EXISTS idxinvdetl3_something ON " + TABLE_FINVDETL3 + " (" + DatabaseHelper.REFNO + "," + FINVDETL3_ITEM_CODE + ")";


	public IteaneryDebController(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		dB = dbHelper.getWritableDatabase();
	}

	public void InsertOrReplaceItenrDeb(ArrayList<ItenrDeb> list) {
		Log.d("InsertOrReplaceItenrDeb", "" + list.size());
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		try {
			dB.beginTransactionNonExclusive();
			String sql = "INSERT OR REPLACE INTO " + TABLE_ITEANERY_DEB + " (DebCode,RefNo,RouteCode,TxnDate) " + " VALUES (?,?,?,?)";

			SQLiteStatement stmt = dB.compileStatement(sql);
			for (ItenrDeb deb : list) {

				stmt.bindString(1, deb.getDebCode());
				stmt.bindString(2, deb.getRefNo());
				stmt.bindString(3, deb.getRouteCode());
				stmt.bindString(4, deb.getTxnDate());

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
			cursor = dB.rawQuery("SELECT * FROM " + TABLE_ITEANERY_DEB, null);
			count = cursor.getCount();
			if (count > 0) {
				int success = dB.delete(TABLE_ITEANERY_DEB, null, null);
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
}
