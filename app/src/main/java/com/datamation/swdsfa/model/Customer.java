package com.datamation.swdsfa.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.datamation.swdsfa.helpers.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable{

	public Customer() {

	}

	private String cusCode;
	private String cusName;
	private String cusNIC;
	private String cusAdd1;
	private String cusAdd2;
	private String cusMob;
	private String cusRoute;
	private String cusStatus;
	private String cusEmail;
	private boolean selectedOnList;
	private String creditLimit;
	private String creditStatus;
	private String creditPeriod;
	private String cusPrilCode;
	private String cusImage;
	private String latitude,longitude;

	/**
	 * ############################ fDebtor table Details
	 * ################################
	 */

	public static final String TABLE_FDEBTOR = "fDebtor";
	//common string
	public static final String REFNO = "RefNo";
	public static final String TXNDATE = "TxnDate";
	public static final String REPCODE = "RepCode";
	public static final String DEALCODE = "DealCode";
	public static final String DEBCODE = "DebCode";
	// table attributes
	public static final String FDEBTOR_ID = "id";
	public static final String FDEBTOR_NAME = "DebName";
	public static final String FDEBTOR_ADD1 = "DebAdd1";
	public static final String FDEBTOR_ADD2 = "DebAdd2";
	public static final String FDEBTOR_ADD3 = "DebAdd3";
	public static final String FDEBTOR_TELE = "DebTele";
	public static final String FDEBTOR_MOB = "DebMob";
	public static final String FDEBTOR_EMAIL = "DebEMail";
	public static final String FDEBTOR_CREATEDATE = "CretDate";
	public static final String FDEBTOR_REM_DIS = "RemDis";
	public static final String FDEBTOR_TOWN_CODE = "TownCode";
	public static final String FDEBTOR_AREA_CODE = "AreaCode";
	public static final String FDEBTOR_DEB_CAT_CODE = "DebCatCode";
	public static final String FDEBTOR_DBGR_CODE = "DbGrCode";
	public static final String FDEBTOR_DEB_CLS_CODE = "DebClsCode";
	public static final String FDEBTOR_STATUS = "Status";
	public static final String FDEBTOR_LYLTY = "DebLylty";
	public static final String FDEBTOR_ADD_USER = "AddUser";
	public static final String FDEBTOR_ADD_DATE_DEB = "AddDateDEB";
	public static final String FDEBTOR_ADD_MACH = "AddMach";
	public static final String FDEBTOR_RECORD_ID = "RecordId";
	public static final String FDEBTOR_TIME_STAMP = "timestamp_column";
	public static final String FDEBTOR_CRD_PERIOD = "CrdPeriod";
	public static final String FDEBTOR_CHK_CRD_PRD = "ChkCrdPrd";
	public static final String FDEBTOR_CRD_LIMIT = "CrdLimit";
	public static final String FDEBTOR_CHK_CRD_LIMIT = "ChkCrdLmt";
	public static final String FDEBTOR_RANK_CODE = "RankCode";
	public static final String FDEBTOR_TRAN_DATE = "txndate";
	public static final String FDEBTOR_TRAN_BATCH = "TranBatch";
	public static final String FDEBTOR_SUMMARY = "DebSumary";
	public static final String FDEBTOR_OUT_DIS = "OutDis";
	public static final String FDEBTOR_DEB_FAX = "DebFax";
	public static final String FDEBTOR_DEB_WEB = "DebWeb";
	public static final String FDEBTOR_DEBCT_NAM = "DebCTNam";
	public static final String FDEBTOR_DEBCT_ADD1 = "DebCTAdd1";
	public static final String FDEBTOR_DEBCT_ADD2 = "DebCTAdd2";
	public static final String FDEBTOR_DEBCT_ADD3 = "DebCTAdd3";
	public static final String FDEBTOR_DEBCT_TELE = "DebCTTele";
	public static final String FDEBTOR_DEBCT_FAX = "DebCTFax";
	public static final String FDEBTOR_DEBCT_EMAIL = "DebCTEmail";
	public static final String FDEBTOR_DEL_PERSN = "DelPersn";
	public static final String FDEBTOR_DEL_ADD1 = "DelAdd1";
	public static final String FDEBTOR_DEL_ADD2 = "DelAdd2";
	public static final String FDEBTOR_DEL_ADD3 = "DelAdd3";
	public static final String FDEBTOR_DEL_TELE = "DelTele";
	public static final String FDEBTOR_DEL_FAX = "DelFax";
	public static final String FDEBTOR_DEL_EMAIL = "DelEmail";
	public static final String FDEBTOR_DATE_OFB = "DateOfB";
	public static final String FDEBTOR_TAX_REG = "TaxReg";
	public static final String FDEBTOR_CUSDISPER = "CusDIsPer";
	public static final String FDEBTOR_PRILLCODE = "PrillCode";
	public static final String FDEBTOR_CUSDISSTAT = "CusDisStat";
	public static final String FDEBTOR_BUS_RGNO = "BusRgNo";
	public static final String FDEBTOR_POSTCODE = "PostCode";
	public static final String FDEBTOR_GEN_REMARKS = "GenRemarks";
	public static final String FDEBTOR_BRANCODE = "BranCode";
	public static final String FDEBTOR_BANK = "Bank";
	public static final String FDEBTOR_BRANCH = "Branch";
	public static final String FDEBTOR_ACCTNO = "AcctNo";
	public static final String FDEBTOR_CUS_VATNO = "CusVatNo";
	public static final String FDEBTOR_LATITUDE = "Latitude";
	public static final String FDEBTOR_LONGITUDE = "Longitude";
	public static final String FDEBTOR_NIC = "NIC";
	public static final String FDEBTOR_BIS_REG = "BisRegNo";
	public static final String FDEBTOR_IS_SYNC = "IsSync";
	public static final String FDEBTOR_IS_CORDINATE_UPDATE = "IsCordinateUpd";
	public static final String FDEBTOR_IMAGE = "CusImage";

	// create String
	public static final String CREATE_FDEBTOR_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FDEBTOR + " (" + FDEBTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"" + DEBCODE + " TEXT, " + FDEBTOR_NAME + " TEXT, " + FDEBTOR_ADD1 + " TEXT, " + FDEBTOR_ADD2 + " TEXT, " +
			"" + FDEBTOR_ADD3 + " TEXT, " + FDEBTOR_TELE + " TEXT, " + FDEBTOR_MOB + " TEXT, " + FDEBTOR_EMAIL + " TEXT," +
			" " + FDEBTOR_CREATEDATE + " TEXT, " + FDEBTOR_REM_DIS + " TEXT, " + FDEBTOR_TOWN_CODE + " TEXT, " + FDEBTOR_AREA_CODE + " TEXT," +
			" " + FDEBTOR_DEB_CAT_CODE + " TEXT, " + FDEBTOR_DBGR_CODE + " TEXT, " + FDEBTOR_DEB_CLS_CODE + " TEXT, " +
			"" + FDEBTOR_STATUS + " TEXT, " + FDEBTOR_LYLTY + " TEXT, " + DEALCODE + " TEXT, " + FDEBTOR_ADD_USER + " TEXT," +
			" " + FDEBTOR_ADD_DATE_DEB + " TEXT, " + FDEBTOR_ADD_MACH + " TEXT, " + FDEBTOR_RECORD_ID + " TEXT, " + FDEBTOR_TIME_STAMP + " TEXT," +
			" " + FDEBTOR_CRD_PERIOD + " TEXT, " + FDEBTOR_CHK_CRD_PRD + " TEXT, " + FDEBTOR_CRD_LIMIT + " TEXT, " + FDEBTOR_CHK_CRD_LIMIT + " TEXT," +
			" " + REPCODE + " TEXT, " + FDEBTOR_RANK_CODE + " TEXT, " + FDEBTOR_TRAN_DATE + " TEXT, " + FDEBTOR_TRAN_BATCH + " TEXT," +
			" " + FDEBTOR_OUT_DIS + " TEXT, " + FDEBTOR_DEB_FAX + " TEXT, " + FDEBTOR_DEB_WEB + " TEXT, " + FDEBTOR_DEBCT_NAM + " TEXT, " + FDEBTOR_DEBCT_ADD1 + " TEXT," +
			" " + FDEBTOR_DEBCT_ADD2 + " TEXT, " + FDEBTOR_DEBCT_ADD3 + " TEXT, " + FDEBTOR_DEBCT_TELE + " TEXT, " + FDEBTOR_DEBCT_FAX + " TEXT," +
			" " + FDEBTOR_DEBCT_EMAIL + " TEXT, " + FDEBTOR_DEL_PERSN + " TEXT, " + FDEBTOR_DEL_ADD1 + " TEXT, " + FDEBTOR_DEL_ADD2 + " TEXT, " +
			"" + FDEBTOR_DEL_ADD3 + " TEXT, " + FDEBTOR_DEL_TELE + " TEXT, " + FDEBTOR_DEL_FAX + " TEXT, " + FDEBTOR_DEL_EMAIL + " TEXT," +
			" " + FDEBTOR_DATE_OFB + " TEXT, " + FDEBTOR_TAX_REG + " TEXT, " + FDEBTOR_CUSDISPER + " TEXT, " + FDEBTOR_PRILLCODE + " TEXT," +
			" " + FDEBTOR_CUSDISSTAT + " TEXT, " + FDEBTOR_BUS_RGNO + " TEXT, " + FDEBTOR_POSTCODE + " TEXT, " + FDEBTOR_GEN_REMARKS + " TEXT," +
			" " + FDEBTOR_BRANCODE + " TEXT, " + FDEBTOR_BANK + " TEXT, " + FDEBTOR_BRANCH + " TEXT, " + FDEBTOR_ACCTNO + " TEXT," +
			" " + FDEBTOR_LATITUDE + " TEXT, " + FDEBTOR_LONGITUDE + " TEXT, " + FDEBTOR_CUS_VATNO + " TEXT, " + FDEBTOR_NIC + " TEXT, " +
			"" + FDEBTOR_BIS_REG + " TEXT, "  + FDEBTOR_SUMMARY + " TEXT, " + FDEBTOR_IS_SYNC + " TEXT ," + FDEBTOR_IS_CORDINATE_UPDATE + " TEXT, " + FDEBTOR_IMAGE + " TEXT  ); ";


	public static final String INDEX_DEBTOR = "CREATE UNIQUE INDEX IF NOT EXISTS ui_debtor ON " + TABLE_FDEBTOR + " (DebCode);";
	public static final String TABLE_TEMP_FDEBTOR = "FTempDebtor";
	public static final String CREATE_TABLE_TEMP_FDEBTOR = "CREATE  TABLE IF NOT EXISTS " + TABLE_TEMP_FDEBTOR + " (" + FDEBTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DEBCODE + " TEXT, " + FDEBTOR_NAME + " TEXT); ";

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getCusPrilCode() {
		return cusPrilCode;
	}

	public void setCusPrilCode(String cusPrilCode) {
		this.cusPrilCode = cusPrilCode;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getCreditStatus() {
		return creditStatus;
	}

	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}

	public String getCreditPeriod() {
		return creditPeriod;
	}

	public void setCreditPeriod(String creditPeriod) {
		this.creditPeriod = creditPeriod;
	}

	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusAdd1() {
		return cusAdd1;
	}

	public void setCusAdd1(String cusAdd1) {
		this.cusAdd1 = cusAdd1;
	}

	public String getCusAdd2() {
		return cusAdd2;
	}

	public void setCusAdd2(String cusAdd2) {
		this.cusAdd2 = cusAdd2;
	}

	public String getCusMob() {
		return cusMob;
	}

	public void setCusMob(String cusMob) {
		this.cusMob = cusMob;
	}

	public String getCusRoute() {
		return cusRoute;
	}

	public void setCusRoute(String cusRoute) {
		this.cusRoute = cusRoute;
	}

	public String getCusStatus() {
		return cusStatus;
	}

	public void setCusStatus(String cusStatus) {
		this.cusStatus = cusStatus;
	}

	public String getCusEmail() {
		return cusEmail;
	}

	public void setCusEmail(String cusEmail) {
		this.cusEmail = cusEmail;
	}

	public boolean isSelectedOnList() {
		return selectedOnList;
	}
	
	public void setSelectedOnList(boolean selectedOnList) {
		this.selectedOnList = selectedOnList;
	}

	public String getCusImage() {
		return cusImage;
	}

	public void setCusImage(String cusImage) {
		this.cusImage = cusImage;
	}

	public static Customer parseOutlet(JSONObject instance) throws JSONException {

		if (instance != null) {
			Customer outlet = new Customer();
			String outletIdString;
			outlet.setCusCode(instance.getString("cuscode"));
			outlet.setCusName(instance.getString("cusname"));
			outlet.setCusRoute(instance.getString("routecode"));
			outlet.setCusAdd1(instance.getString("address"));
			//outlet.setCusAdd2(instance.getString("addressline2"));
			outlet.setCusMob(instance.getString("mobile"));
			outlet.setCusEmail(instance.getString("email"));
			outlet.setCusStatus(instance.getString("status"));
//			outlet.setCusImage(instance.getString("cusImage"));
			return outlet;
		}

		return null;
	}

//	public ArrayList<Customer> getRouteCustomersByCodeAndName(String RouteCode, String newText) {
//
//		ArrayList<Customer> list = new ArrayList<Customer>();
//
//		return list;
//	}
//
//	public ArrayList<Customer> getRouteCustomers(String RouteCode, String newText) {
//
//
//		ArrayList<Customer> list = new ArrayList<Customer>();
//
//		return list;
//	}
		
}
