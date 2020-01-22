package com.datamation.swdsfa.controller;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.ReceiptHed;


public class ReceiptController {

	private SQLiteDatabase dB;
	private DatabaseHelper dbHelper;
	Context context;
	private String TAG = "SMACKCERA";
// rashmi - 2019-12-18 move from database_helper , because of reduce coding in database helper*******************************************************************************

	public static final String SETTINGS = "SETTINGS";
	public static SharedPreferences localSP;
	public static final String TABLE_FPRECHED = "fpRecHed";
	public static final String FPRECHED_ID = "Id";
	public static final String FPRECHED_REFNO1 = "RefNo1";
	public static final String FPRECHED_MANUREF = "ManuRef";
	public static final String FPRECHED_SALEREFNO = "SaleRefNo";
	public static final String FPRECHED_TXNTYPE = "TxnType";
	public static final String FPRECHED_CHQNO = "Chqno";
	public static final String FPRECHED_CHQDATE = "ChqDate";

	public static final String FPRECHED_CURCODE = "CurCode";
	public static final String FPRECHED_CURRATE1 = "CurRate1";
	public static final String FPRECHED_TOTALAMT = "TotalAmt";
	public static final String FPRECHED_BTOTALAMT = "BTotalAmt";
	public static final String FPRECHED_PAYTYPE = "PayType";
	public static final String FPRECHED_PRTCOPY = "PrtCopy";
	public static final String FPRECHED_REMARKS = "Remarks";
	public static final String FPRECHED_ADDUSER = "AddUser";
	public static final String FPRECHED_ADDMACH = "AddMach";
	public static final String FPRECHED_ADDDATE = "AddDate";
	public static final String FPRECHED_RECORDID = "RecordId";
	public static final String FPRECHED_TIMESTAMP = "timestamp_column";
	public static final String FPRECHED_CURRATE = "CurRate";
	public static final String FPRECHED_CUSBANK = "CusBank";
	public static final String FPRECHED_COST_CODE = "CostCode";
	public static final String FPRECHED_LONGITUDE = "Longitude";
	public static final String FPRECHED_LATITUDE = "Latitude";
	public static final String FPRECHED_ADDRESS = "Address";
	public static final String FPRECHED_START_TIME = "StartTime";
	public static final String FPRECHED_END_TIME = "EndTime";
	public static final String FPRECHED_ISACTIVE = "IsActive";
	public static final String FPRECHED_ISSYNCED = "IsSynced";
	public static final String FPRECHED_ISDELETE = "IsDelete";
	public static final String FPRECHED_BANKCODE = "BankCode";
	public static final String FPRECHED_BRANCHCODE = "BranchCode";
	public static final String CREATE_FPRECHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FPRECHED + " (" + FPRECHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

			DatabaseHelper.REFNO + " TEXT, " + FPRECHED_REFNO1 + " TEXT, " + FPRECHED_MANUREF + " TEXT, " + FPRECHED_SALEREFNO + " TEXT, " +

			DatabaseHelper.REPCODE + " TEXT, " + FPRECHED_TXNTYPE + " TEXT, " + FPRECHED_CHQNO + " TEXT, " + FPRECHED_CHQDATE + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT, " + FPRECHED_CURCODE + " TEXT, " +

			FPRECHED_CURRATE1 + " TEXT, " + DatabaseHelper.DEBCODE + " TEXT, " + FPRECHED_TOTALAMT + " TEXT, " + FPRECHED_BANKCODE + " TEXT, " + FPRECHED_BRANCHCODE + " TEXT, " +

			FPRECHED_BTOTALAMT + " TEXT, " + FPRECHED_PAYTYPE + " TEXT, " + FPRECHED_PRTCOPY + " TEXT, " + FPRECHED_REMARKS + " TEXT, " + FPRECHED_ADDUSER + " TEXT, " + FPRECHED_ADDMACH + " TEXT, " + FPRECHED_ADDDATE + " TEXT, " +

			FPRECHED_RECORDID + " TEXT, " + FPRECHED_TIMESTAMP + " TEXT, " + FPRECHED_ISDELETE + " TEXT, " + FPRECHED_COST_CODE + " TEXT, " +

			FPRECHED_LONGITUDE + " TEXT, " + FPRECHED_LATITUDE + " TEXT, " + FPRECHED_ADDRESS + " TEXT, " + FPRECHED_START_TIME + " TEXT, " + FPRECHED_END_TIME + " TEXT, " + FPRECHED_ISACTIVE + " TEXT, " + FPRECHED_ISSYNCED + " TEXT, " + FPRECHED_CURRATE + " TEXT, " + FPRECHED_CUSBANK + " TEXT);";

	/*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
	public static final String TABLE_FPRECHEDS = "fpRecHedS";

	public static final String CREATE_FPRECHEDS_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FPRECHEDS + " (" + FPRECHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

			DatabaseHelper.REFNO + " TEXT, " + FPRECHED_REFNO1 + " TEXT, " + FPRECHED_MANUREF + " TEXT, " + FPRECHED_SALEREFNO + " TEXT, " +

			DatabaseHelper.REPCODE + " TEXT, " + FPRECHED_TXNTYPE + " TEXT, " + FPRECHED_CHQNO + " TEXT, " + FPRECHED_CHQDATE + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT, " + FPRECHED_CURCODE + " TEXT, " +

			FPRECHED_CURRATE1 + " TEXT, " + DatabaseHelper.DEBCODE + " TEXT, " + FPRECHED_TOTALAMT + " TEXT, " + FPRECHED_BANKCODE + " TEXT, " + FPRECHED_BRANCHCODE + " TEXT, " +

			FPRECHED_BTOTALAMT + " TEXT, " + FPRECHED_PAYTYPE + " TEXT, " + FPRECHED_PRTCOPY + " TEXT, " + FPRECHED_REMARKS + " TEXT, " + FPRECHED_ADDUSER + " TEXT, " + FPRECHED_ADDMACH + " TEXT, " + FPRECHED_ADDDATE + " TEXT, " +

			FPRECHED_RECORDID + " TEXT, " + FPRECHED_TIMESTAMP + " TEXT, " + FPRECHED_ISDELETE + " TEXT, " + FPRECHED_COST_CODE + " TEXT, "

			+ FPRECHED_LONGITUDE + " TEXT, " + FPRECHED_LATITUDE + " TEXT, " + FPRECHED_ADDRESS + " TEXT, " + FPRECHED_START_TIME + " TEXT, " + FPRECHED_END_TIME + " TEXT, " + FPRECHED_ISACTIVE + " TEXT, " + FPRECHED_ISSYNCED + " TEXT, " + FPRECHED_CURRATE + " TEXT, " + FPRECHED_CUSBANK + " TEXT);";

	public ReceiptController(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		dB = dbHelper.getWritableDatabase();
	}

	public int createOrUpdateRecHed(ArrayList<ReceiptHed> list) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			for (ReceiptHed recHed : list) {

				String selectQuery = "SELECT * FROM " + TABLE_FPRECHED + " WHERE "
						+ DatabaseHelper.REFNO + " = '" + recHed.getFPRECHED_REFNO() + "'";

				cursor = dB.rawQuery(selectQuery, null);

				ContentValues values = new ContentValues();

				values.put(FPRECHED_ID, recHed.getFPRECHED_ID());
				values.put(DatabaseHelper.REFNO, recHed.getFPRECHED_REFNO());
				values.put(FPRECHED_REFNO1, recHed.getFPRECHED_REFNO1());
				//values.put(DatabaseHelper.REFNO2, recHed.getFPRECHED_REFNO2());
				values.put(FPRECHED_MANUREF, recHed.getFPRECHED_MANUREF());
				values.put(FPRECHED_SALEREFNO, recHed.getFPRECHED_SALEREFNO());
				values.put(DatabaseHelper.REPCODE, recHed.getFPRECHED_REPCODE());
				values.put(FPRECHED_TXNTYPE, recHed.getFPRECHED_TXNTYPE());
				values.put(FPRECHED_CHQNO, recHed.getFPRECHED_CHQNO());
				values.put(FPRECHED_CHQDATE, recHed.getFPRECHED_CHQDATE());
				values.put(DatabaseHelper.TXNDATE, recHed.getFPRECHED_TXNDATE());
				values.put(FPRECHED_CURCODE, recHed.getFPRECHED_CURCODE());
				values.put(FPRECHED_CURRATE1, "");

				values.put(DatabaseHelper.DEBCODE, recHed.getFPRECHED_DEBCODE());
				values.put(FPRECHED_TOTALAMT, recHed.getFPRECHED_TOTALAMT());
				values.put(FPRECHED_BTOTALAMT, recHed.getFPRECHED_BTOTALAMT());
				values.put(FPRECHED_PAYTYPE, recHed.getFPRECHED_PAYTYPE());
				values.put(FPRECHED_PRTCOPY, "");
				values.put(FPRECHED_REMARKS, recHed.getFPRECHED_REMARKS());
				values.put(FPRECHED_ADDUSER, recHed.getFPRECHED_ADDUSER());
				values.put(FPRECHED_ADDMACH, recHed.getFPRECHED_ADDMACH());
				values.put(FPRECHED_ADDDATE, recHed.getFPRECHED_ADDDATE());
				values.put(FPRECHED_RECORDID, "");
				values.put(FPRECHED_TIMESTAMP, "");
				values.put(FPRECHED_CURRATE, recHed.getFPRECHED_CURRATE());
				values.put(FPRECHED_CUSBANK, recHed.getFPRECHED_CUSBANK());
				values.put(FPRECHED_LONGITUDE, recHed.getFPRECHED_LONGITUDE());
				values.put(FPRECHED_LATITUDE, recHed.getFPRECHED_LATITUDE());
				values.put(FPRECHED_ADDRESS, recHed.getFPRECHED_ADDRESS());
				values.put(FPRECHED_START_TIME, recHed.getFPRECHED_START_TIME());
				values.put(FPRECHED_END_TIME, recHed.getFPRECHED_END_TIME());
				values.put(FPRECHED_ISSYNCED, recHed.getFPRECHED_ISSYNCED());
				values.put(FPRECHED_ISACTIVE, recHed.getFPRECHED_ISACTIVE());
				values.put(FPRECHED_ISDELETE, recHed.getFPRECHED_ISDELETE());

				int cn = cursor.getCount();
				if (cn > 0) {
					count = dB.update(TABLE_FPRECHED, values, DatabaseHelper.REFNO + " =?",
							new String[] { String.valueOf(recHed.getFPRECHED_REFNO()) });
				} else {
					count = (int) dB.insert(TABLE_FPRECHED, null, values);
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

	/*
	 * create for store singlr receipt
	 */
	public int createOrUpdateRecHedS(ArrayList<ReceiptHed> list) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			for (ReceiptHed recHed : list) {

				String selectQuery = "SELECT * FROM " + TABLE_FPRECHEDS + " WHERE "
						+ DatabaseHelper.REFNO + " = '" + recHed.getFPRECHED_REFNO() + "'";

				cursor = dB.rawQuery(selectQuery, null);

				ContentValues values = new ContentValues();

				//values.put(FPRECHED_ID, recHed.getFPRECHED_ID());
				values.put(DatabaseHelper.REFNO, recHed.getFPRECHED_REFNO());
				values.put(ReceiptDetController.FPRECDET_REFNO1, recHed.getFPRECHED_REFNO1());
				values.put(FPRECHED_MANUREF, recHed.getFPRECHED_MANUREF());
				values.put(FPRECHED_SALEREFNO, recHed.getFPRECHED_SALEREFNO());
				values.put(DatabaseHelper.REPCODE, recHed.getFPRECHED_REPCODE());
				values.put(FPRECHED_TXNTYPE, recHed.getFPRECHED_TXNTYPE());
				values.put(FPRECHED_CHQNO, recHed.getFPRECHED_CHQNO());
				values.put(FPRECHED_CHQDATE, recHed.getFPRECHED_CHQDATE());
				values.put(DatabaseHelper.TXNDATE, recHed.getFPRECHED_TXNDATE());
				values.put(FPRECHED_CURCODE, recHed.getFPRECHED_CURCODE());
				values.put(FPRECHED_CURRATE1, "");
				values.put(DatabaseHelper.DEBCODE, recHed.getFPRECHED_DEBCODE());
				values.put(FPRECHED_TOTALAMT, recHed.getFPRECHED_TOTALAMT());
				values.put(FPRECHED_BTOTALAMT, recHed.getFPRECHED_BTOTALAMT());
				values.put(FPRECHED_PAYTYPE, recHed.getFPRECHED_PAYTYPE());
				values.put(FPRECHED_PRTCOPY, "");
				values.put(FPRECHED_REMARKS, recHed.getFPRECHED_REMARKS());
				values.put(FPRECHED_ADDUSER, recHed.getFPRECHED_ADDUSER());
				values.put(FPRECHED_ADDMACH, recHed.getFPRECHED_ADDMACH());
				values.put(FPRECHED_ADDDATE, recHed.getFPRECHED_ADDDATE());
				values.put(FPRECHED_RECORDID, "");
				values.put(FPRECHED_TIMESTAMP, "");
				values.put(FPRECHED_CURRATE, recHed.getFPRECHED_CURRATE());
				values.put(FPRECHED_CUSBANK, recHed.getFPRECHED_CUSBANK());
				values.put(FPRECHED_LONGITUDE, recHed.getFPRECHED_LONGITUDE());
				values.put(FPRECHED_LATITUDE, recHed.getFPRECHED_LATITUDE());
				values.put(FPRECHED_ADDRESS, recHed.getFPRECHED_ADDRESS());
				values.put(FPRECHED_START_TIME, recHed.getFPRECHED_START_TIME());
				values.put(FPRECHED_END_TIME, recHed.getFPRECHED_END_TIME());
				values.put(FPRECHED_ISSYNCED, recHed.getFPRECHED_ISSYNCED());
				values.put(FPRECHED_ISACTIVE, recHed.getFPRECHED_ISACTIVE());
				values.put(FPRECHED_ISDELETE, recHed.getFPRECHED_ISDELETE());
				values.put(FPRECHED_BANKCODE, recHed.getFPRECHED_BANKCODE());
				values.put(FPRECHED_BRANCHCODE, recHed.getFPRECHED_BRANCHCODE());
				//	values.put(FPRECHED_ADDUSER_NEW, recHed.getFPRECHED_ADDUSER_NEW());

				int cn = cursor.getCount();
				if (cn > 0) {
					count = dB.update(TABLE_FPRECHEDS, values, DatabaseHelper.REFNO + " =?",
							new String[] { String.valueOf(recHed.getFPRECHED_REFNO()) });
				} else {
					count = (int) dB.insert(TABLE_FPRECHEDS, null, values);
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

	/*
	 * create for single receipt
	 */
	public ArrayList<ReceiptHed> getAllCompletedRecHedS(String refno) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<ReceiptHed> list = new ArrayList<ReceiptHed>();
		String selectQuery;

		try {
			if (refno.equals("")) {

				selectQuery = "select * from " + TABLE_FPRECHEDS + " Where "
						+ FPRECHED_ISDELETE + "='0' and "+ FPRECHED_ISACTIVE
						+ "='0' Order by "+FPRECHED_ISSYNCED + "," +DatabaseHelper.REFNO + " DESC";

			} else {

				selectQuery = "select * from " + TABLE_FPRECHEDS + " Where "
						+ FPRECHED_ISDELETE + "='0' and "+ FPRECHED_ISACTIVE + "='0' and " + DatabaseHelper.REFNO + "='" + refno
						+ "' Order by "+FPRECHED_ISSYNCED + "," +DatabaseHelper.REFNO + " DESC";
			}

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				ReceiptHed recHed = new ReceiptHed();

				recHed.setFPRECHED_ADDDATE(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDDATE)));
				recHed.setFPRECHED_ADDMACH(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDMACH)));
				recHed.setFPRECHED_ADDRESS(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDRESS)));
				recHed.setFPRECHED_ADDUSER(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDUSER)));
				recHed.setFPRECHED_BANKCODE(cursor.getString(cursor.getColumnIndex(FPRECHED_BANKCODE)));
				recHed.setFPRECHED_BRANCHCODE(cursor.getString(cursor.getColumnIndex(FPRECHED_BRANCHCODE)));
				recHed.setFPRECHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(FPRECHED_BTOTALAMT)));
				recHed.setFPRECHED_CHQDATE(cursor.getString(cursor.getColumnIndex(FPRECHED_CHQDATE)));
				recHed.setFPRECHED_CHQNO(cursor.getString(cursor.getColumnIndex(FPRECHED_CHQNO)));
				recHed.setFPRECHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FPRECHED_COST_CODE)));
				recHed.setFPRECHED_CURCODE(cursor.getString(cursor.getColumnIndex(FPRECHED_CURCODE)));
				recHed.setFPRECHED_CURRATE(cursor.getString(cursor.getColumnIndex(FPRECHED_CURRATE)));
				recHed.setFPRECHED_CURRATE1(cursor.getString(cursor.getColumnIndex(FPRECHED_CURRATE1)));
				recHed.setFPRECHED_CUSBANK(cursor.getString(cursor.getColumnIndex(FPRECHED_CUSBANK)));
				recHed.setFPRECHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
				recHed.setFPRECHED_END_TIME(cursor.getString(cursor.getColumnIndex(FPRECHED_END_TIME)));
				recHed.setFPRECHED_ID(cursor.getString(cursor.getColumnIndex(FPRECHED_ID)));
				recHed.setFPRECHED_ISACTIVE(cursor.getString(cursor.getColumnIndex(FPRECHED_ISACTIVE)));
				recHed.setFPRECHED_ISSYNCED(cursor.getString(cursor.getColumnIndex(FPRECHED_ISSYNCED)));
				recHed.setFPRECHED_LATITUDE(cursor.getString(cursor.getColumnIndex(FPRECHED_LATITUDE)));
				recHed.setFPRECHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(FPRECHED_LONGITUDE)));
				recHed.setFPRECHED_MANUREF(cursor.getString(cursor.getColumnIndex(FPRECHED_MANUREF)));
				recHed.setFPRECHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(FPRECHED_PAYTYPE)));
				recHed.setFPRECHED_PRTCOPY(cursor.getString(cursor.getColumnIndex(FPRECHED_PRTCOPY)));
				recHed.setFPRECHED_RECORDID(cursor.getString(cursor.getColumnIndex(FPRECHED_RECORDID)));
				recHed.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
				recHed.setFPRECHED_REFNO1(cursor.getString(cursor.getColumnIndex(ReceiptDetController.FPRECDET_REFNO1)));
				recHed.setFPRECHED_REMARKS(cursor.getString(cursor.getColumnIndex(FPRECHED_REMARKS)));
				recHed.setFPRECHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
				recHed.setFPRECHED_SALEREFNO(cursor.getString(cursor.getColumnIndex(FPRECHED_SALEREFNO)));
				recHed.setFPRECHED_START_TIME(cursor.getString(cursor.getColumnIndex(FPRECHED_START_TIME)));
				recHed.setFPRECHED_TIMESTAMP(cursor.getString(cursor.getColumnIndex(FPRECHED_TIMESTAMP)));
				recHed.setFPRECHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(FPRECHED_TOTALAMT)));
				recHed.setFPRECHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
				recHed.setFPRECHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FPRECHED_TXNTYPE)));
				//	recHed.setFPRECHED_ADDUSER_NEW(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDUSER_NEW)));

				list.add(recHed);

			}
			cursor.close();
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return list;
	}

	/*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public ArrayList<ReceiptHed> getAllCompletedRecHed(String refno) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<ReceiptHed> list = new ArrayList<ReceiptHed>();
		String selectQuery;

		try {
			if (refno.equals("")) {

				selectQuery = "select * from " + TABLE_FPRECHED + " Where "
						+ FPRECHED_ISDELETE + "='0' and "+ FPRECHED_TOTALAMT
						+ ">'0'";

			} else {

				selectQuery = "select * from " + TABLE_FPRECHED + " Where "
						+ FPRECHED_ISDELETE + "='0' and " + FPRECHED_ISACTIVE
						+ "='0' and " + FPRECHED_TOTALAMT
						+ ">'0' and " + DatabaseHelper.REFNO + "='" + refno + "'";
			}

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				ReceiptHed recHed = new ReceiptHed();

				recHed.setFPRECHED_ADDDATE(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDDATE)));
				recHed.setFPRECHED_ADDMACH(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDMACH)));
				recHed.setFPRECHED_ADDRESS(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDRESS)));
				recHed.setFPRECHED_ADDUSER(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDUSER)));
				recHed.setFPRECHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(FPRECHED_BTOTALAMT)));
				recHed.setFPRECHED_CHQDATE(cursor.getString(cursor.getColumnIndex(FPRECHED_CHQDATE)));
				recHed.setFPRECHED_CHQNO(cursor.getString(cursor.getColumnIndex(FPRECHED_CHQNO)));
				recHed.setFPRECHED_CURCODE(cursor.getString(cursor.getColumnIndex(FPRECHED_CURCODE)));
				recHed.setFPRECHED_CURRATE(cursor.getString(cursor.getColumnIndex(FPRECHED_CURRATE)));
				recHed.setFPRECHED_CURRATE1(cursor.getString(cursor.getColumnIndex(FPRECHED_CURRATE1)));
				recHed.setFPRECHED_CUSBANK(cursor.getString(cursor.getColumnIndex(FPRECHED_CUSBANK)));
				recHed.setFPRECHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
				recHed.setFPRECHED_END_TIME(cursor.getString(cursor.getColumnIndex(FPRECHED_END_TIME)));
				recHed.setFPRECHED_ID(cursor.getString(cursor.getColumnIndex(FPRECHED_ID)));
				recHed.setFPRECHED_ISACTIVE(cursor.getString(cursor.getColumnIndex(FPRECHED_ISACTIVE)));
				recHed.setFPRECHED_ISSYNCED(cursor.getString(cursor.getColumnIndex(FPRECHED_ISSYNCED)));
				recHed.setFPRECHED_LATITUDE(cursor.getString(cursor.getColumnIndex(FPRECHED_LATITUDE)));
				recHed.setFPRECHED_LONGITUDE(
						cursor.getString(cursor.getColumnIndex(FPRECHED_LONGITUDE)));
				recHed.setFPRECHED_MANUREF(cursor.getString(cursor.getColumnIndex(FPRECHED_MANUREF)));
				recHed.setFPRECHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(FPRECHED_PAYTYPE)));
				recHed.setFPRECHED_PRTCOPY(cursor.getString(cursor.getColumnIndex(FPRECHED_PRTCOPY)));
				recHed.setFPRECHED_RECORDID(cursor.getString(cursor.getColumnIndex(FPRECHED_RECORDID)));
				recHed.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
				recHed.setFPRECHED_REFNO1(cursor.getString(cursor.getColumnIndex(FPRECHED_REFNO1)));
				recHed.setFPRECHED_REMARKS(cursor.getString(cursor.getColumnIndex(FPRECHED_REMARKS)));
				recHed.setFPRECHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
				recHed.setFPRECHED_SALEREFNO(
						cursor.getString(cursor.getColumnIndex(FPRECHED_SALEREFNO)));
				recHed.setFPRECHED_START_TIME(
						cursor.getString(cursor.getColumnIndex(FPRECHED_START_TIME)));
				recHed.setFPRECHED_TIMESTAMP(
						cursor.getString(cursor.getColumnIndex(FPRECHED_TIMESTAMP)));
				recHed.setFPRECHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(FPRECHED_TOTALAMT)));
				recHed.setFPRECHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
				recHed.setFPRECHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FPRECHED_TXNTYPE)));
				recHed.setFPRECHED_ISDELETE(cursor.getString(cursor.getColumnIndex(FPRECHED_ISDELETE)));

				list.add(recHed);

			}
			cursor.close();
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return list;
	}

	public ReceiptHed getReceiptByRefno(String refno) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery;

		ReceiptHed recHed = new ReceiptHed();
		try {

			selectQuery = "select * from " + TABLE_FPRECHEDS + " Where " + DatabaseHelper.REFNO
					+ "='" + refno + "'";

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				recHed.setFPRECHED_ADDDATE(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDDATE)));
				recHed.setFPRECHED_ADDMACH(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDMACH)));
				recHed.setFPRECHED_ADDRESS(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDRESS)));
				recHed.setFPRECHED_ADDUSER(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDUSER)));
				recHed.setFPRECHED_BANKCODE(cursor.getString(cursor.getColumnIndex(FPRECHED_BANKCODE)));
				recHed.setFPRECHED_BRANCHCODE(cursor.getString(cursor.getColumnIndex(FPRECHED_BRANCHCODE)));
				recHed.setFPRECHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(FPRECHED_BTOTALAMT)));
				recHed.setFPRECHED_CHQDATE(cursor.getString(cursor.getColumnIndex(FPRECHED_CHQDATE)));
				recHed.setFPRECHED_CHQNO(cursor.getString(cursor.getColumnIndex(FPRECHED_CHQNO)));
				recHed.setFPRECHED_CURCODE(cursor.getString(cursor.getColumnIndex(FPRECHED_CURCODE)));
				recHed.setFPRECHED_CURRATE(cursor.getString(cursor.getColumnIndex(FPRECHED_CURRATE)));
				recHed.setFPRECHED_CURRATE1(cursor.getString(cursor.getColumnIndex(FPRECHED_CURRATE1)));
				recHed.setFPRECHED_CUSBANK(cursor.getString(cursor.getColumnIndex(FPRECHED_CUSBANK)));
				recHed.setFPRECHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
				recHed.setFPRECHED_END_TIME(cursor.getString(cursor.getColumnIndex(FPRECHED_END_TIME)));
				recHed.setFPRECHED_ID(cursor.getString(cursor.getColumnIndex(FPRECHED_ID)));
				recHed.setFPRECHED_ISACTIVE(cursor.getString(cursor.getColumnIndex(FPRECHED_ISACTIVE)));
				recHed.setFPRECHED_ISSYNCED(cursor.getString(cursor.getColumnIndex(FPRECHED_ISSYNCED)));
				recHed.setFPRECHED_LATITUDE(cursor.getString(cursor.getColumnIndex(FPRECHED_LATITUDE)));
				recHed.setFPRECHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(FPRECHED_LONGITUDE)));
				recHed.setFPRECHED_MANUREF(cursor.getString(cursor.getColumnIndex(FPRECHED_MANUREF)));
				recHed.setFPRECHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(FPRECHED_PAYTYPE)));
				recHed.setFPRECHED_PRTCOPY(cursor.getString(cursor.getColumnIndex(FPRECHED_PRTCOPY)));
				recHed.setFPRECHED_RECORDID(cursor.getString(cursor.getColumnIndex(FPRECHED_RECORDID)));
				recHed.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
				recHed.setFPRECHED_REMARKS(cursor.getString(cursor.getColumnIndex(FPRECHED_REMARKS)));
				recHed.setFPRECHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
				recHed.setFPRECHED_SALEREFNO(cursor.getString(cursor.getColumnIndex(FPRECHED_SALEREFNO)));
				recHed.setFPRECHED_START_TIME(cursor.getString(cursor.getColumnIndex(FPRECHED_START_TIME)));
				recHed.setFPRECHED_TIMESTAMP(cursor.getString(cursor.getColumnIndex(FPRECHED_TIMESTAMP)));
				recHed.setFPRECHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(FPRECHED_TOTALAMT)));
				recHed.setFPRECHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
				recHed.setFPRECHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FPRECHED_TXNTYPE)));
				//	recHed.setFPRECHED_ADDUSER_NEW(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDUSER_NEW)));

			}
			cursor.close();
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return recHed;
	}

	/*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public int CancelReceipt(String Refno) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		int result = 0;
		try {
			result = dB.delete(TABLE_FPRECHED, DatabaseHelper.REFNO + "=?",
					new String[] { Refno });

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return result;
	}

	public int CancelReceiptS(String Refno) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		int result = 0;
		try {
			result = dB.delete(TABLE_FPRECHEDS, DatabaseHelper.REFNO + "=?",
					new String[] { Refno });

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return result;
	}

	/*-*-*-*-*-*-**-*-*--*-*-*-*-*-*-*--**-*-*-*-*-*---*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public int InactiveStatusUpdate(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + TABLE_FPRECHEDS + " WHERE "
					+ DatabaseHelper.REFNO + " = '" + refno + "'";

			cursor = dB.rawQuery(selectQuery, null);

			ContentValues values = new ContentValues();

			values.put(FPRECHED_ISACTIVE, "0");

			int cn = cursor.getCount();

			if (cn > 0) {
				count = dB.update(TABLE_FPRECHEDS, values, DatabaseHelper.REFNO + " =?",
						new String[] { String.valueOf(refno) });
			} else {
				count = (int) dB.insert(TABLE_FPRECHEDS, null, values);
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

	public int InactiveStatusUpdateForMultiREceipt(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + TABLE_FPRECHED + " WHERE "
					+ FPRECHED_REFNO1 + " = '" + refno + "'";

			cursor = dB.rawQuery(selectQuery, null);

			ContentValues values = new ContentValues();

			values.put(FPRECHED_ISACTIVE, "0");

			int cn = cursor.getCount();

			if (cn > 0) {
				count = dB.update(TABLE_FPRECHED, values, FPRECHED_REFNO1 + " =?",
						new String[] { String.valueOf(refno) });
			} else {
				count = (int) dB.insert(TABLE_FPRECHED, null, values);
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

	/***********************************************************************/
	public int DeleteStatusUpdateForMultiREceipt(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + TABLE_FPRECHED + " WHERE "
					+ FPRECHED_REFNO1 + " = '" + refno + "'";

			cursor = dB.rawQuery(selectQuery, null);

			ContentValues values = new ContentValues();

			values.put(FPRECHED_ISDELETE, "1");

			int cn = cursor.getCount();

			if (cn > 0) {
				count = dB.update(TABLE_FPRECHED, values, FPRECHED_REFNO1 + " =?",
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
	/*-*-*-*-*-*-**-*-*--*-*-*-*-*-*-*--**-*-*-*-*-*---*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public int DeleteStatusUpdateForEceipt(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + TABLE_FPRECHEDS + " WHERE "
					+ DatabaseHelper.REFNO + " = '" + refno + "'";

			cursor = dB.rawQuery(selectQuery, null);

			ContentValues values = new ContentValues();

			values.put(FPRECHED_ISDELETE, "1");

			int cn = cursor.getCount();

			if (cn > 0) {
				count = dB.update(TABLE_FPRECHEDS, values, DatabaseHelper.REFNO + " =?",
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
	/********************************************************************************/
	public void UpdateRecHedForMultiReceipt(ReceiptHed recHed, String Refno) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ContentValues values = new ContentValues();

		try {
			values.put(FPRECHED_LATITUDE, recHed.getFPRECHED_LATITUDE());
			values.put(FPRECHED_LONGITUDE, recHed.getFPRECHED_LONGITUDE());
			values.put(FPRECHED_START_TIME, recHed.getFPRECHED_START_TIME());
			values.put(FPRECHED_END_TIME, recHed.getFPRECHED_END_TIME());
			values.put(FPRECHED_ADDRESS, recHed.getFPRECHED_ADDRESS());

			dB.update(TABLE_FPRECHED, values, FPRECHED_REFNO1 + " =?",
					new String[] { String.valueOf(Refno) });

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}
	}

	public void UpdateRecHed(ReceiptHed recHed, String Refno) {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ContentValues values = new ContentValues();

		try {
			values.put(FPRECHED_LATITUDE, recHed.getFPRECHED_LATITUDE());
			values.put(FPRECHED_LONGITUDE, recHed.getFPRECHED_LONGITUDE());
			values.put(FPRECHED_START_TIME, recHed.getFPRECHED_START_TIME());
			values.put(FPRECHED_END_TIME, recHed.getFPRECHED_END_TIME());
			values.put(FPRECHED_ADDRESS, recHed.getFPRECHED_ADDRESS());
			values.put(FPRECHED_COST_CODE, recHed.getFPRECHED_COSTCODE());

			dB.update(TABLE_FPRECHEDS, values, DatabaseHelper.REFNO + " =?",
					new String[] { String.valueOf(Refno) });

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}
	}


	/*-*-*-*-*-*-**-*-*--*-*-*-*-*-*-*--**-*-*-*-*-*---*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/


	/**
	 *
	 * @return RefNo2ForRecDet
	 */

	@SuppressWarnings("static-access")
	public String getFPRECHED_REFNO2ForRecDet(String code, String status) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "SELECT * FROM " + TABLE_FPRECHED + " WHERE " + DatabaseHelper.DEBCODE
				+ " = '" + code + "' AND " + FPRECHED_ISACTIVE + " = '" + status + "'";

		Cursor cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {

			return cursor.getString(cursor.getColumnIndex(dbHelper.REFNO));

		}

		return "";
	}

	@SuppressWarnings("static-access")
	public String getChequeDate(String refno) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "SELECT * FROM " + TABLE_FPRECHEDS + " WHERE " + DatabaseHelper.REFNO
				+ " = '" + refno + "'";

		Cursor cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {

			return cursor.getString(cursor.getColumnIndex(FPRECHED_CHQDATE));

		}

		return "";
	}

	@SuppressWarnings("static-access")
	public String getChequeNo(String refno) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "SELECT * FROM " + TABLE_FPRECHEDS + " WHERE " + DatabaseHelper.REFNO
				+ " = '" + refno + "'";

		Cursor cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {

			return cursor.getString(cursor.getColumnIndex(FPRECHED_CHQNO));

		}

		return "";
	}

	public int getHeaderCountForNnumVal(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + TABLE_FPRECHED + " WHERE "
					+ ReceiptDetController.FPRECDET_REFNO1 + " = '" + refno + "'";

			cursor = dB.rawQuery(selectQuery, null);

			count = cursor.getCount();

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
	public int deleteData(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + TABLE_FPRECHED + " WHERE " + ReceiptDetController.FPRECDET_REFNO1
					+ " = '" + refno + "'";
			cursor = dB.rawQuery(selectQuery, null);
			int cn = cursor.getCount();

			if (cn > 0) {
				int success = dB.delete(TABLE_FPRECHED, ReceiptDetController.FPRECDET_REFNO1 + " ='" + refno + "'", null);
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

	public void UpdateRecHeadTotalAmount(String refNo) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;
		try {
			String selectQuery = "SELECT * FROM " + TABLE_FPRECHED + " WHERE "
					+ FPRECHED_REFNO1 + " = '" + refNo + "'";

			cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {
				String refno = cursor.getString(cursor.getColumnIndex(dbHelper.REFNO));

				String query = "Update " + TABLE_FPRECHED
						+ " set TotalAmt = (select sum(AloAmt) Aloamt from fprecdet where refno='" + refno
						+ "' ) where refno='" + refno + "'";
				dB.execSQL(query);
			}


		} catch (Exception e) {
			Log.v(TAG + " Exception", e.toString());
		} finally {
			dB.close();
		}

	}

	public int DeleteHedUnnessary() {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;
		int result = 0;
		int result2 = 0;
		try {
			String selectQuery = "SELECT * FROM " + TABLE_FPRECHED + " WHERE totalamt is null ";


			cursor = dB.rawQuery(selectQuery, null);

			cursor .moveToFirst();
			//int raws = cursor.getCount();

			//cursor.

			while(!cursor.isAfterLast()) {

				String refno = cursor.getString(cursor.getColumnIndex(dbHelper.REFNO));
				result = dB.delete(TABLE_FPRECHED, DatabaseHelper.REFNO + "=?",
						new String[] { refno });

				result2=result2+1;

				cursor.moveToNext();
			}

//			while (cursor.moveToNext()) {
//				String refno = cursor.getString(cursor.getColumnIndex(dbHelper.REFNO));
//				
//				result = dB.delete(DatabaseHelper.TABLE_FPRECHED, DatabaseHelper.REFNO + "=?",
//						new String[] { refno });
//				result2=result2+1;
//				//String query = "Delete FROM " + DatabaseHelper.TABLE_FPRECHED + " where refno='" + refno + "'";
//				//dB.execSQL(query);
//			}


		}
//		try {
//
//				String query = "Delete from " + DatabaseHelper.TABLE_FPRECHED+ " where totalamt is null ";
//				dB.execSQL(query);						
//
//		}
		catch (Exception e) {
			Log.v(TAG + " Exception", e.toString());
		} finally {
			dB.close();
		}
		return result2;
	}

	public int updateIsSyncedReceipt(ReceiptHed mapper) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {
			ContentValues values = new ContentValues();

			values.put(FPRECHED_ISSYNCED, "1");

			if (mapper.getFPRECHED_ISSYNCED().equals("1")) {
				count = dB.update(TABLE_FPRECHED, values, dbHelper.REFNO + " =?",
						new String[] { String.valueOf(mapper.getFPRECHED_REFNO()) });
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

	public ArrayList<ReceiptHed> getAllUnsyncedRecHed() {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<ReceiptHed> list = new ArrayList<ReceiptHed>();
		String selectQuery;

		try {
			selectQuery = "select * from " + TABLE_FPRECHEDS + " Where "
					+ FPRECHED_ISACTIVE + "='0' and " + FPRECHED_ISSYNCED + "='0' and "
					+ FPRECHED_ISDELETE + "='0'";

			localSP = context.getSharedPreferences(SETTINGS,
					0);

			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				ReceiptHed mapper = new ReceiptHed();

				mapper.setNextNumVal(new ReferenceController(context)
						.getCurrentNextNumVal(context.getResources().getString(R.string.ReceiptNumVal)));

				mapper.setDistDB(SharedPref.getInstance(context).getDistDB().trim());
				mapper.setConsoleDB(SharedPref.getInstance(context).getConsoleDB().trim());

//				mapper.setSALEREP_DEALCODE(new SalRepDS(context).getDealCode());
//				mapper.setSALEREP_AREACODE(new SalRepDS(context).getAreaCode());

				mapper.setFPRECHED_ADDDATE(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDDATE)));
				mapper.setFPRECHED_ADDMACH(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDMACH)));
				mapper.setFPRECHED_ADDRESS(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDRESS)));
				mapper.setFPRECHED_ADDUSER(cursor.getString(cursor.getColumnIndex(FPRECHED_ADDUSER)));
				mapper.setFPRECHED_BTOTALAMT(cursor.getString(cursor.getColumnIndex(FPRECHED_BTOTALAMT)));
				mapper.setFPRECHED_CHQDATE(cursor.getString(cursor.getColumnIndex(FPRECHED_CHQDATE)));
				mapper.setFPRECHED_CHQNO(cursor.getString(cursor.getColumnIndex(FPRECHED_CHQNO)));
				mapper.setFPRECHED_CURCODE(cursor.getString(cursor.getColumnIndex(FPRECHED_CURCODE)));
				mapper.setFPRECHED_CURRATE(cursor.getString(cursor.getColumnIndex(FPRECHED_CURRATE)));
				mapper.setFPRECHED_CURRATE1(cursor.getString(cursor.getColumnIndex(FPRECHED_CURRATE1)));
				mapper.setFPRECHED_CUSBANK(cursor.getString(cursor.getColumnIndex(FPRECHED_CUSBANK)));
				mapper.setFPRECHED_COSTCODE(cursor.getString(cursor.getColumnIndex(FPRECHED_COST_CODE)));
				mapper.setFPRECHED_DEBCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBCODE)));
				mapper.setFPRECHED_END_TIME(cursor.getString(cursor.getColumnIndex(FPRECHED_END_TIME)));
				mapper.setFPRECHED_ID(cursor.getString(cursor.getColumnIndex(FPRECHED_ID)));
				mapper.setFPRECHED_ISACTIVE(cursor.getString(cursor.getColumnIndex(FPRECHED_ISACTIVE)));
				mapper.setFPRECHED_ISSYNCED(cursor.getString(cursor.getColumnIndex(FPRECHED_ISSYNCED)));
				mapper.setFPRECHED_LATITUDE(cursor.getString(cursor.getColumnIndex(FPRECHED_LATITUDE)));
				mapper.setFPRECHED_LONGITUDE(cursor.getString(cursor.getColumnIndex(FPRECHED_LONGITUDE)));
				mapper.setFPRECHED_MANUREF(cursor.getString(cursor.getColumnIndex(FPRECHED_MANUREF)));
				mapper.setFPRECHED_PAYTYPE(cursor.getString(cursor.getColumnIndex(FPRECHED_PAYTYPE)));
				mapper.setFPRECHED_PRTCOPY(cursor.getString(cursor.getColumnIndex(FPRECHED_PRTCOPY)));
				mapper.setFPRECHED_RECORDID(cursor.getString(cursor.getColumnIndex(FPRECHED_RECORDID)));
				mapper.setFPRECHED_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
				mapper.setFPRECHED_REMARKS(cursor.getString(cursor.getColumnIndex(FPRECHED_REMARKS)));
				mapper.setFPRECHED_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
				mapper.setFPRECHED_SALEREFNO(cursor.getString(cursor.getColumnIndex(FPRECHED_SALEREFNO)));
				mapper.setFPRECHED_START_TIME(cursor.getString(cursor.getColumnIndex(FPRECHED_START_TIME)));
				mapper.setFPRECHED_TIMESTAMP(cursor.getString(cursor.getColumnIndex(FPRECHED_TIMESTAMP)));
				mapper.setFPRECHED_TOTALAMT(cursor.getString(cursor.getColumnIndex(FPRECHED_TOTALAMT)));
				mapper.setFPRECHED_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
				mapper.setFPRECHED_TXNTYPE(cursor.getString(cursor.getColumnIndex(FPRECHED_TXNTYPE)));
				mapper.setFPRECHED_BANKCODE(cursor.getString(cursor.getColumnIndex(FPRECHED_BANKCODE)));
				mapper.setFPRECHED_BRANCHCODE(cursor.getString(cursor.getColumnIndex(FPRECHED_BRANCHCODE)));

				mapper.setRecDetList(new ReceiptDetController(context).GetReceiptByRefno(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO))));

				list.add(mapper);

			}
			cursor.close();
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return list;
	}
}
