package com.datamation.swdsfa.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.FinvDetL3;

import java.util.ArrayList;

public class FinvDetL3Controller {
	private SQLiteDatabase dB;
	private DatabaseHelper dbHelper;
	Context context;
	private String TAG = "FinvDetL3DS ";
	// rashmi - 2019-12-24 move from database_helper , because of reduce coding in database helper*******************************************************************************

	public static final String TABLE_FINVDETL3 = "FinvDetL3";
	// table attributes
	public static final String FINVDETL3_ID = "FinvDetL3_id";
	public static final String FINVDETL3_AMT = "Amt";
	public static final String FINVDETL3_ITEM_CODE = "ItemCode";
	public static final String FINVDETL3_QTY = "Qty";

	public static final String FINVDETL3_SEQ_NO = "SeqNo";
	public static final String FINVDETL3_TAX_AMT = "TaxAmt";
	public static final String FINVDETL3_TAX_COM_CODE = "TaxComCode";


	// create String
	public static final String CREATE_FINVDETL3_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FINVDETL3 + " (" + FINVDETL3_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FINVDETL3_AMT + " TEXT, " + FINVDETL3_ITEM_CODE + " TEXT, " + FINVDETL3_QTY + " TEXT, " + DatabaseHelper.REFNO + " TEXT, " + FINVDETL3_SEQ_NO + " TEXT, " + FINVDETL3_TAX_AMT + " TEXT, " + FINVDETL3_TAX_COM_CODE + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT); ";

	public static final String TESTINVDETL3 = "CREATE UNIQUE INDEX IF NOT EXISTS idxinvdetl3_something ON " + TABLE_FINVDETL3 + " (" + DatabaseHelper.REFNO + "," + FINVDETL3_ITEM_CODE + ")";


	public FinvDetL3Controller(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		dB = dbHelper.getWritableDatabase();
	}

	public void createOrUpdateFinvDetL3(ArrayList<FinvDetL3> list) {
		Log.d("InsrtOrReplceFinvHedL3", "" + list.size());
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		try {
			dB.beginTransactionNonExclusive();
			String sql = "INSERT OR REPLACE INTO " + TABLE_FINVDETL3 + " (Amt,ItemCode,Qty,RefNo,SeqNo,TaxAmt,TaxComCode,TxnDate) " + " VALUES (?,?,?,?,?,?,?,?)";

			SQLiteStatement stmt = dB.compileStatement(sql);
			for (FinvDetL3 finvDetL3 : list) {

				stmt.bindString(1, finvDetL3.getFINVDETL3_AMT());
				stmt.bindString(2, finvDetL3.getFINVDETL3_ITEM_CODE());
				stmt.bindString(3, finvDetL3.getFINVDETL3_QTY());
				stmt.bindString(4, finvDetL3.getFINVDETL3_REF_NO());
				stmt.bindString(5, finvDetL3.getFINVDETL3_SEQ_NO());
				stmt.bindString(6, finvDetL3.getFINVDETL3_TAX_AMT());
				stmt.bindString(7, finvDetL3.getFINVDETL3_TAX_COM_CODE());
				stmt.bindString(8, finvDetL3.getFINVDETL3_TXN_DATE());

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
			cursor = dB.rawQuery("SELECT * FROM " + TABLE_FINVDETL3, null);
			count = cursor.getCount();
			if (count > 0) {
				int success = dB.delete(TABLE_FINVDETL3, null, null);
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
