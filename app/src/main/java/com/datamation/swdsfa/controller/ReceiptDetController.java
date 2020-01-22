package com.datamation.swdsfa.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.ReceiptDet;

public class ReceiptDetController {

	private SQLiteDatabase dB;
	private DatabaseHelper dbHelper;
	Context context;
	private String TAG = "Swadeshi";

	// rashmi - 2019-12-19 move from database_helper , because of reduce coding in database helper*******************************************************************************

	// table attributes
	// create String
	// --------------------------------------------------------------------------------------------------------------
	public static final String TABLE_FPRECDET = "fpRecDet";
	public static final String FPRECDET_ID = "Id";
	public static final String FPRECDET_REFNO1 = "RefNo1";
	public static final String FPRECDET_REFNO2 = "RefNo2";
	public static final String FPRECDET_SALEREFNO = "SaleRefNo";
	public static final String FPRECDET_MANUREF = "ManuRef";
	public static final String FPRECDET_TXNTYPE = "TxnType";

	public static final String FPRECDET_DTXNDATE = "DtxnDate";
	public static final String FPRECDET_DTXNTYPE = "DtxnType";
	public static final String FPRECDET_DCURCODE = "DCurCode";
	public static final String FPRECDET_DCURRATE = "DCurRate";
	public static final String FPRECDET_OCURRATE = "OCurRate";
	public static final String FPRECDET_AMT = "Amt";
	public static final String FPRECDET_BAMT = "BAmt";
	public static final String FPRECDET_ALOAMT = "AloAmt";
	public static final String FPRECDET_OVPAYAMT = "OvPayAmt";
	public static final String FPRECDET_OVPAYBAL = "OvPayBal";
	public static final String FPRECDET_RECORDID = "RecordId";
	public static final String FPRECDET_TIMESTAMP = "timestamp_column";
	public static final String FPRECDET_ISDELETE = "IsDelete";
	public static final String FPRECDET_REMARK = "Remark";
	public static final String CREATE_FPRECDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FPRECDET + " (" + FPRECDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + FPRECDET_REFNO1 + " TEXT, " + FPRECDET_REFNO2 + " TEXT, " + DatabaseHelper.DEBCODE + " TEXT, " + FPRECDET_SALEREFNO + " TEXT, "

			+ FPRECDET_MANUREF + " TEXT, " + FPRECDET_TXNTYPE + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT, "

			+ FPRECDET_DTXNDATE + " TEXT, " + FPRECDET_DTXNTYPE + " TEXT, " + FPRECDET_DCURCODE + " TEXT, " + FPRECDET_DCURRATE + " TEXT, "

			+ FPRECDET_OCURRATE + " TEXT, " + DatabaseHelper.REPCODE + " TEXT, " + FPRECDET_AMT + " TEXT, " + FPRECDET_BAMT + " TEXT, "

			+ FPRECDET_ALOAMT + " TEXT, " + FPRECDET_OVPAYAMT + " TEXT, " + FPRECDET_REMARK + " TEXT, " + FPRECDET_OVPAYBAL + " TEXT, " + FPRECDET_RECORDID + " TEXT, " + FPRECDET_ISDELETE + " TEXT, " + FPRECDET_TIMESTAMP + " TEXT );";

	/*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

	public static final String TABLE_FPRECDETS = "fpRecDetS";

	public static final String CREATE_FPRECDETS_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FPRECDETS + " (" + FPRECDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DatabaseHelper.REFNO + " TEXT, " + FPRECDET_REFNO1 + " TEXT, " + FPRECDET_REFNO2 + " TEXT, " + DatabaseHelper.DEBCODE + " TEXT, " + FPRECDET_SALEREFNO + " TEXT, "

			+ FPRECDET_MANUREF + " TEXT, " + FPRECDET_TXNTYPE + " TEXT, " + DatabaseHelper.TXNDATE + " TEXT, "

			+ FPRECDET_DTXNDATE + " TEXT, " + FPRECDET_DTXNTYPE + " TEXT, " + FPRECDET_DCURCODE + " TEXT, " + FPRECDET_DCURRATE + " TEXT, "

			+ FPRECDET_OCURRATE + " TEXT, " + DatabaseHelper.REPCODE + " TEXT, " + FPRECDET_AMT + " TEXT, " + FPRECDET_BAMT + " TEXT, " + FPRECDET_ISDELETE + " TEXT, "

			+ FPRECDET_REMARK + " TEXT, " + FPRECDET_ALOAMT + " TEXT, " + FPRECDET_OVPAYAMT + " TEXT, " + FPRECDET_OVPAYBAL + " TEXT, " + FPRECDET_RECORDID + " TEXT, " + FPRECDET_TIMESTAMP + " TEXT );";

	public ReceiptDetController(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		dB = dbHelper.getWritableDatabase();
	}
	//for new sfa by rashmi

	public ArrayList<ReceiptDet> getTodayPayments() {
		int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
		int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
		int curDate = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;
		ArrayList<ReceiptDet> list = new ArrayList<ReceiptDet>();

		try {
			String selectQuery = "select hed.refno, det.RefNo1, hed.paytype,det.aloamt, fddb.totbal, det.dtxndate from fprecheds hed, fprecdets det," +
					//			" fddbnote fddb where hed.refno = det.refno and det.FPRECDET_REFNO1 = fddb.refno and hed.txndate = '2019-04-12'";
					" fddbnote fddb where hed.refno = det.refno and det.RefNo1 = fddb.refno and hed.txndate = '" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDate) +"'";

			cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				ReceiptDet recDet = new ReceiptDet();

//
				recDet.setFPRECDET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
				recDet.setFPRECDET_REFNO1(cursor.getString(cursor.getColumnIndex(FPRECDET_REFNO1)));
				recDet.setFPRECDET_REPCODE(cursor.getString(cursor.getColumnIndex(ReceiptController.FPRECHED_PAYTYPE)));
				recDet.setFPRECDET_ALOAMT(cursor.getString(cursor.getColumnIndex(FPRECDET_ALOAMT)));
				recDet.setFPRECDET_AMT(cursor.getString(cursor.getColumnIndex(OutstandingController.FDDBNOTE_TOT_BAL)));
				recDet.setFPRECDET_TXNDATE(cursor.getString(cursor.getColumnIndex(FPRECDET_DTXNDATE)));

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

	//------------------------------------------------------------old functions
	public int createOrUpdateRecDet(ArrayList<ReceiptDet> list) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			for (ReceiptDet recDet : list) {

				String selectQuery = "SELECT * FROM " + TABLE_FPRECDET + " WHERE " + FPRECDET_ID + " = '" + recDet.getFPRECDET_ID() + "'";

				cursor = dB.rawQuery(selectQuery, null);

				ContentValues values = new ContentValues();

				values.put(FPRECDET_ALOAMT, recDet.getFPRECDET_ALOAMT());
				values.put(FPRECDET_AMT, recDet.getFPRECDET_AMT());
				values.put(FPRECDET_BAMT, recDet.getFPRECDET_BAMT());
				values.put(FPRECDET_DCURCODE, recDet.getFPRECDET_DCURCODE());
				values.put(FPRECDET_DCURRATE, recDet.getFPRECDET_DCURRATE());
				values.put(FPRECDET_DTXNDATE, recDet.getFPRECDET_DTXNDATE());
				values.put(FPRECDET_DTXNTYPE, recDet.getFPRECDET_DTXNTYPE());
				values.put(FPRECDET_MANUREF, recDet.getFPRECDET_MANUREF());
				values.put(FPRECDET_OCURRATE, recDet.getFPRECDET_OCURRATE());
				values.put(FPRECDET_OVPAYAMT, recDet.getFPRECDET_OVPAYAMT());
				values.put(FPRECDET_OVPAYBAL, recDet.getFPRECDET_OVPAYBAL());
				values.put(FPRECDET_RECORDID, recDet.getFPRECDET_RECORDID());
				values.put(DatabaseHelper.REFNO, recDet.getFPRECDET_REFNO());
				values.put(FPRECDET_REFNO1, recDet.getFPRECDET_REFNO1());
				values.put(DatabaseHelper.REPCODE, recDet.getFPRECDET_REPCODE());
				values.put(FPRECDET_SALEREFNO, recDet.getFPRECDET_SALEREFNO());
				values.put(FPRECDET_TIMESTAMP, recDet.getFPRECDET_TIMESTAMP());
				values.put(DatabaseHelper.TXNDATE, recDet.getFPRECDET_TXNDATE());
				values.put(FPRECDET_TXNTYPE, recDet.getFPRECDET_TXNTYPE());
				values.put(DatabaseHelper.DEBCODE, recDet.getFPRECDET_DEBCODE());
				values.put(FPRECDET_REFNO2, recDet.getFPRECDET_REFNO2());
				values.put(FPRECDET_ISDELETE, recDet.getFPRECDET_ISDELETE());
				values.put(FPRECDET_REMARK, recDet.getFPRECDET_REMARK());

				int cn = cursor.getCount();
				if (cn > 0) {
					count = dB.update(TABLE_FPRECDET, values, FPRECDET_ID + " =?", new String[] { String.valueOf(recDet.getFPRECDET_ID()) });
				} else {
					count = (int) dB.insert(TABLE_FPRECDET, null, values);
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
	create for single receipt save
	 */


	public int createOrUpdateRecDetS(ArrayList<ReceiptDet> list) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			for (ReceiptDet recDet : list) {

				String selectQuery = "SELECT * FROM " + TABLE_FPRECDETS + " WHERE " + FPRECDET_ID + " = '" + recDet.getFPRECDET_ID() + "'";

				cursor = dB.rawQuery(selectQuery, null);

				ContentValues values = new ContentValues();

				values.put(FPRECDET_ALOAMT, recDet.getFPRECDET_ALOAMT());
				values.put(FPRECDET_AMT, recDet.getFPRECDET_AMT());
				values.put(FPRECDET_BAMT, recDet.getFPRECDET_BAMT());
				values.put(FPRECDET_DCURCODE, recDet.getFPRECDET_DCURCODE());
				values.put(FPRECDET_DCURRATE, recDet.getFPRECDET_DCURRATE());
				values.put(FPRECDET_DTXNDATE, recDet.getFPRECDET_DTXNDATE());
				values.put(FPRECDET_DTXNTYPE, recDet.getFPRECDET_DTXNTYPE());
				values.put(FPRECDET_MANUREF, recDet.getFPRECDET_MANUREF());
				values.put(FPRECDET_OCURRATE, recDet.getFPRECDET_OCURRATE());
				values.put(FPRECDET_OVPAYAMT, recDet.getFPRECDET_OVPAYAMT());
				values.put(FPRECDET_OVPAYBAL, recDet.getFPRECDET_OVPAYBAL());
				values.put(FPRECDET_RECORDID, recDet.getFPRECDET_RECORDID());
				values.put(DatabaseHelper.REFNO, recDet.getFPRECDET_REFNO());
				values.put(FPRECDET_REFNO1, recDet.getFPRECDET_REFNO1());
				values.put(DatabaseHelper.REPCODE, recDet.getFPRECDET_REPCODE());
				values.put(FPRECDET_SALEREFNO, recDet.getFPRECDET_SALEREFNO());
				values.put(FPRECDET_TIMESTAMP, recDet.getFPRECDET_TIMESTAMP());
				values.put(DatabaseHelper.TXNDATE, recDet.getFPRECDET_TXNDATE());
				values.put(FPRECDET_TXNTYPE, recDet.getFPRECDET_TXNTYPE());
				values.put(DatabaseHelper.DEBCODE, recDet.getFPRECDET_DEBCODE());
				values.put(FPRECDET_REFNO2, recDet.getFPRECDET_REFNO2());
				values.put(FPRECDET_ISDELETE, recDet.getFPRECDET_ISDELETE());
				values.put(FPRECDET_REMARK, recDet.getFPRECDET_REMARK());
				int cn = cursor.getCount();
				if (cn > 0) {
					count = dB.update(TABLE_FPRECDETS, values, FPRECDET_ID + " =?", new String[] { String.valueOf(recDet.getFPRECDET_ID()) });
				} else {
					count = (int) dB.insert(TABLE_FPRECDETS, null, values);
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
	public boolean isAnyActiveReceipt()
	{
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "select * from " + ReceiptController.TABLE_FPRECHEDS + " WHERE " + ReceiptController.FPRECHED_ISACTIVE + "='" + "1" + "'";

		Cursor cursor = dB.rawQuery(selectQuery, null);

		try {
			if (cursor.getCount()>0)
			{
				return true;
			}
			else
			{
				return false;
			}

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			dB.close();
		}

		return false;
	}
	public boolean isAnyActiveReceipts()
	{
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		String selectQuery = "select * from " + ReceiptController.TABLE_FPRECHEDS + " WHERE " + ReceiptController.FPRECHED_ISACTIVE + "='" + "1" + "'";

		Cursor cursor = dB.rawQuery(selectQuery, null);

		try {
			if (cursor.getCount()>0)
			{
				return true;
			}
			else
			{
				return false;
			}

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			dB.close();
		}

		return false;
	}
	/*-----------------------------------------------------------------------------------*/
	public int getItemCount(String refNo) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		try {
			String selectQuery = "SELECT count(RefNo) as RefNo FROM " + TABLE_FPRECDETS + " WHERE  " + DatabaseHelper.REFNO + "='" + refNo + "'";
			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {
				return Integer.parseInt(cursor.getString(cursor.getColumnIndex("RefNo")));
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			dB.close();
		}
		return 0;

	}
	//only can use for single receipt
	public ArrayList<ReceiptDet> GetReceiptByRefno(String Refno) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;
		ArrayList<ReceiptDet> list = new ArrayList<ReceiptDet>();

		try {
			String selectQuery = "SELECT * FROM " + TABLE_FPRECDETS + " WHERE " + DatabaseHelper.REFNO + " = '" + Refno + "' and "
					+ ReceiptController.FPRECHED_ISDELETE + "='0'";

			cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				ReceiptDet recDet = new ReceiptDet();

				recDet.setFPRECDET_ALOAMT(cursor.getString(cursor.getColumnIndex(FPRECDET_ALOAMT)));
				recDet.setFPRECDET_AMT(cursor.getString(cursor.getColumnIndex(FPRECDET_AMT)));
				recDet.setFPRECDET_BAMT(cursor.getString(cursor.getColumnIndex(FPRECDET_BAMT)));
				recDet.setFPRECDET_DCURCODE(cursor.getString(cursor.getColumnIndex(FPRECDET_DCURCODE)));
				recDet.setFPRECDET_DCURRATE(cursor.getString(cursor.getColumnIndex(FPRECDET_DCURRATE)));
				recDet.setFPRECDET_DTXNDATE(cursor.getString(cursor.getColumnIndex(FPRECDET_DTXNDATE)));
				recDet.setFPRECDET_DTXNTYPE(cursor.getString(cursor.getColumnIndex(FPRECDET_DTXNTYPE)));
				recDet.setFPRECDET_ID(cursor.getString(cursor.getColumnIndex(FPRECDET_ID)));
				recDet.setFPRECDET_MANUREF(cursor.getString(cursor.getColumnIndex(FPRECDET_MANUREF)));
				recDet.setFPRECDET_OCURRATE(cursor.getString(cursor.getColumnIndex(FPRECDET_OCURRATE)));
				recDet.setFPRECDET_OVPAYAMT(cursor.getString(cursor.getColumnIndex(FPRECDET_OVPAYAMT)));
				recDet.setFPRECDET_OVPAYBAL(cursor.getString(cursor.getColumnIndex(FPRECDET_OVPAYBAL)));
				recDet.setFPRECDET_RECORDID(cursor.getString(cursor.getColumnIndex(FPRECDET_RECORDID)));
				recDet.setFPRECDET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
				recDet.setFPRECDET_REFNO1(cursor.getString(cursor.getColumnIndex(FPRECDET_REFNO1)));
				recDet.setFPRECDET_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
				recDet.setFPRECDET_SALEREFNO(cursor.getString(cursor.getColumnIndex(FPRECDET_SALEREFNO)));
				recDet.setFPRECDET_TIMESTAMP(cursor.getString(cursor.getColumnIndex(FPRECDET_TIMESTAMP)));
				recDet.setFPRECDET_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
				recDet.setFPRECDET_TXNTYPE(cursor.getString(cursor.getColumnIndex(FPRECDET_TXNTYPE)));
				recDet.setFPRECDET_REMARK(cursor.getString(cursor.getColumnIndex(FPRECDET_REMARK)));
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

	public ArrayList<ReceiptDet> GetMReceiptByRefno(String Refno) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;
		ArrayList<ReceiptDet> list = new ArrayList<ReceiptDet>();

		try {
			String selectQuery = "SELECT * FROM " + TABLE_FPRECDET + " WHERE " + DatabaseHelper.REFNO + " = '" + Refno + "'";

			cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {

				ReceiptDet recDet = new ReceiptDet();

				recDet.setFPRECDET_ALOAMT(cursor.getString(cursor.getColumnIndex(FPRECDET_ALOAMT)));
				recDet.setFPRECDET_AMT(cursor.getString(cursor.getColumnIndex(FPRECDET_AMT)));
				recDet.setFPRECDET_BAMT(cursor.getString(cursor.getColumnIndex(FPRECDET_BAMT)));
				recDet.setFPRECDET_DCURCODE(cursor.getString(cursor.getColumnIndex(FPRECDET_DCURCODE)));
				recDet.setFPRECDET_DCURRATE(cursor.getString(cursor.getColumnIndex(FPRECDET_DCURRATE)));
				recDet.setFPRECDET_DTXNDATE(cursor.getString(cursor.getColumnIndex(FPRECDET_DTXNDATE)));
				recDet.setFPRECDET_DTXNTYPE(cursor.getString(cursor.getColumnIndex(FPRECDET_DTXNTYPE)));
				recDet.setFPRECDET_ID(cursor.getString(cursor.getColumnIndex(FPRECDET_ID)));
				recDet.setFPRECDET_MANUREF(cursor.getString(cursor.getColumnIndex(FPRECDET_MANUREF)));
				recDet.setFPRECDET_OCURRATE(cursor.getString(cursor.getColumnIndex(FPRECDET_OCURRATE)));
				recDet.setFPRECDET_OVPAYAMT(cursor.getString(cursor.getColumnIndex(FPRECDET_OVPAYAMT)));
				recDet.setFPRECDET_OVPAYBAL(cursor.getString(cursor.getColumnIndex(FPRECDET_OVPAYBAL)));
				recDet.setFPRECDET_RECORDID(cursor.getString(cursor.getColumnIndex(FPRECDET_RECORDID)));
				recDet.setFPRECDET_REFNO(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REFNO)));
				recDet.setFPRECDET_REFNO1(cursor.getString(cursor.getColumnIndex(FPRECDET_REFNO1)));
				recDet.setFPRECDET_REFNO2(cursor.getString(cursor.getColumnIndex(FPRECDET_REFNO2)));
				recDet.setFPRECDET_REPCODE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REPCODE)));
				recDet.setFPRECDET_SALEREFNO(cursor.getString(cursor.getColumnIndex(FPRECDET_SALEREFNO)));
				recDet.setFPRECDET_TIMESTAMP(cursor.getString(cursor.getColumnIndex(FPRECDET_TIMESTAMP)));
				recDet.setFPRECDET_TXNDATE(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TXNDATE)));
				recDet.setFPRECDET_TXNTYPE(cursor.getString(cursor.getColumnIndex(FPRECDET_TXNTYPE)));
				recDet.setFPRECDET_REMARK(cursor.getString(cursor.getColumnIndex(FPRECDET_REMARK)));
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
	public int restDataForMR(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + TABLE_FPRECDET + " WHERE " + FPRECDET_REFNO2 + " = '" + refno + "'";
			cursor = dB.rawQuery(selectQuery, null);
			int cn = cursor.getCount();

			if (cn > 0) {
				count = dB.delete(TABLE_FPRECDET, FPRECDET_REFNO2 + " ='" + refno + "'", null);
				Log.v("Success Stauts", count + "");
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
	public int UpdateDeleteStatusMR(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + TABLE_FPRECDET + " WHERE " + FPRECDET_REFNO2 + " = '" + refno + "'";
			cursor = dB.rawQuery(selectQuery, null);


			ContentValues values = new ContentValues();

			values.put(FPRECDET_ISDELETE, "1");

			int cn = cursor.getCount();

			if (cn > 0) {
				count = dB.update(TABLE_FPRECDET, values,FPRECDET_REFNO2 + " ='" + refno + "'", null);
				Log.v("Success Stauts", count + "");
			}
			else {
				count = (int) dB.insert(TABLE_FPRECDET, null, values);
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
	public int UpdateDeleteStatus(String refno) {

		int count = 0;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT * FROM " + TABLE_FPRECDETS + " WHERE " + dbHelper.REFNO + " = '" + refno + "'";
			cursor = dB.rawQuery(selectQuery, null);


			ContentValues values = new ContentValues();

			values.put(FPRECDET_ISDELETE, "1");

			int cn = cursor.getCount();

			if (cn > 0) {
				count = dB.update(TABLE_FPRECDETS, values,dbHelper.REFNO + " ='" + refno + "'", null);
				Log.v("Success Stauts", count + "");
			}
			else {
				count = (int) dB.insert(TABLE_FPRECDETS, null, values);
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
	public String getTotal(String refNo) {

		String sum = null;

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}
		Cursor cursor = null;

		try {

			String selectQuery = "SELECT SUM("+FPRECDET_ALOAMT+") FROM " + TABLE_FPRECDET + " WHERE "
					+ DatabaseHelper.REFNO + " = '" + refNo + "'";

			cursor = dB.rawQuery(selectQuery, null);

			sum = cursor.getString(0);

		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			dB.close();
		}
		return sum;

	}

}
