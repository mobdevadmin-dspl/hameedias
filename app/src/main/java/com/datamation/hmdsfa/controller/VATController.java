package com.datamation.hmdsfa.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.model.FinvDetL3;
import com.datamation.hmdsfa.model.TaxDet;
import com.datamation.hmdsfa.model.VatMaster;

import java.math.BigDecimal;
import java.util.ArrayList;

public class VATController {
	private SQLiteDatabase dB;
	private DatabaseHelper dbHelper;
	Context context;
	private String TAG = "VAT ";
	// rashmi - 2019-12-24 move from database_helper , because of reduce coding in database helper*******************************************************************************

	public static final String TABLE_VAT = "VAT";
	// table attributes
	public static final String  ID = "Id";
	public static final String VATCALTYPE = "VatCalType";
	public static final String VATCODE = "VatCode";
	public static final String VATDESCRIPTION = "VatDesciption";
	public static final String VATPER = "VatPer";


	// create String
	public static final String CREATE_TABLE_VAT = "CREATE  TABLE IF NOT EXISTS " + TABLE_VAT + " (" +
			ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + VATCALTYPE + " TEXT, " +
			VATCODE + " TEXT, " + VATDESCRIPTION + " TEXT, "  + VATPER + " TEXT); ";

	//public static final String TESTINVDETL3 = "CREATE UNIQUE INDEX IF NOT EXISTS idxinvdetl3_something ON " + TABLE_FINVDETL3 + " (" + DatabaseHelper.REFNO + "," + FINVDETL3_ITEM_CODE + ")";


	public VATController(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		dB = dbHelper.getWritableDatabase();
	}

	public void InsertOrReplaceVAT(ArrayList<VatMaster> list) {
		Log.d("InsrtOrReplceFinvHedL3", "" + list.size());
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		try {
			dB.beginTransactionNonExclusive();
			String sql = "INSERT OR REPLACE INTO " + TABLE_VAT + " (VatCalType,VatCode,VatDesciption,VatPer) " + " VALUES (?,?,?,?)";

			SQLiteStatement stmt = dB.compileStatement(sql);
			for (VatMaster vat : list) {

				stmt.bindString(1, vat.getVatCalType());
				stmt.bindString(2, vat.getVatCode());
				stmt.bindString(3, vat.getVatDesciption());
				stmt.bindLong(4, vat.getVatPer());

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
	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*developed for hameedias 20200615 by rashmi*/

	public ArrayList<TaxDet> getTaxInfoByTaxCode(String taxCode) {

		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<TaxDet> list = new ArrayList<TaxDet>();

		String selectQuery = "select * from " + TABLE_VAT + " WHERE " + VATCODE + "='" + taxCode + "' and VatPer <> '0'";
		try {
			Cursor cursor = dB.rawQuery(selectQuery, null);

			while (cursor.moveToNext()) {
				TaxDet det = new TaxDet();

				det.setTAXCOMCODE(cursor.getString(cursor.getColumnIndex(VATCODE)));
				det.setTAXVAL(cursor.getString(cursor.getColumnIndex(VATPER)));

				list.add(det);
			}
			cursor.close();
		} catch (Exception e) {

			Log.v(TAG + " Exception", e.toString());

		} finally {
			dB.close();
		}

		return list;

	}
	public String calculateReverse(String taxcode,BigDecimal price)
	{


		ArrayList<TaxDet> list = new VATController(context).getTaxInfoByTaxCode(taxcode);
		BigDecimal tax = new BigDecimal("0");

		if (list.size() > 0) {

			for (TaxDet det : list) {
				tax = tax.add(new BigDecimal(det.getTAXVAL()).multiply(price.divide(new BigDecimal(det.getTAXVAL()).add(new BigDecimal("100")), 4, BigDecimal.ROUND_HALF_EVEN)));
			    price = new BigDecimal("100").multiply(price.divide(new BigDecimal(det.getTAXVAL()).add(new BigDecimal("100")), 8, BigDecimal.ROUND_HALF_EVEN));

			}
		}
		//return String.format("%.2f", tax);
		return String.valueOf(tax);
		//return String.format("%.2f", amt);
	}
	public String[] calculateTaxForward(String taxcode, double price) {

		ArrayList<TaxDet> list = new VATController(context).getTaxInfoByTaxCode(taxcode);
		double tax = 0;
		String sArray[] = new String[2];

		if (list.size() > 0) {

			for (int i = list.size() - 1; i > -1; i--) {
				tax += Double.parseDouble(list.get(i).getTAXVAL()) * (price / 100);
				price = (Double.parseDouble(list.get(i).getTAXVAL()) + 100) * (price / 100);
			}
		}

		sArray[0] = String.format("%.2f", price);
		sArray[1] = String.format("%.2f", tax);
		return sArray;
	}

	public ArrayList<String> getVatDetails() {
		if (dB == null) {
			open();
		} else if (!dB.isOpen()) {
			open();
		}

		ArrayList<String> list = new ArrayList<String>();

		String selectQuery = "SELECT * FROM " + TABLE_VAT + " WHERE trim(" + VATCODE + ") <> ''";

		Cursor cursor = dB.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {

			String vat = "";

			vat =  cursor.getString(cursor.getColumnIndex(VATCODE))+" - "+cursor.getString(cursor.getColumnIndex(VATDESCRIPTION))+"("+cursor.getString(cursor.getColumnIndex(VATPER))+"%)";

			list.add(vat);

		}

		return list;
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
			cursor = dB.rawQuery("SELECT * FROM " + TABLE_VAT, null);
			count = cursor.getCount();
			if (count > 0) {
				int success = dB.delete(TABLE_VAT, null, null);
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
