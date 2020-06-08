package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class ItenrDeb {

	@SerializedName("DebCode")
	private String DebCode;
	@SerializedName("RefNo")
	private String RefNo;
	@SerializedName("RouteCode")
	private String RouteCode;
	@SerializedName("TxnDate")
	private String TxnDate;

	public String getDebCode() {
		return DebCode;
	}

	public void setDebCode(String debCode) {
		DebCode = debCode;
	}

	public String getRefNo() {
		return RefNo;
	}

	public void setRefNo(String refNo) {
		RefNo = refNo;
	}

	public String getRouteCode() {
		return RouteCode;
	}

	public void setRouteCode(String routeCode) {
		RouteCode = routeCode;
	}

	public String getTxnDate() {
		return TxnDate;
	}

	public void setTxnDate(String txnDate) {
		TxnDate = txnDate;
	}

	public static ItenrDeb parseIteDebDet(JSONObject instance) throws JSONException {

		if (instance != null) {
			ItenrDeb itenrDeb = new ItenrDeb();
			itenrDeb.setDebCode(instance.getString("DebCode"));
			itenrDeb.setRefNo(instance.getString("RefNo"));
			itenrDeb.setRouteCode(instance.getString("RouteCode"));
			itenrDeb.setTxnDate(instance.getString("TxnDate"));
			return itenrDeb;
		}

		return null;
	}
}
