package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemPri {

	private String FITEMPRI_ID;
	private String FITEMPRI_ADD_MACH;
	private String FITEMPRI_ADD_USER;
	private String FITEMPRI_ITEM_CODE;
	private String FITEMPRI_PRICE;
	private String FITEMPRI_PRIL_CODE;
	private String FITEMPRI_SKU_CODE;
	private String FITEMPRI_TXN_MACH;
	private String FITEMPRI_TXN_USER;
	private String FITEMPRI_MIN_PRICE;
	private String FITEMPRI_MAX_PRICE;
	public String getFITEMPRI_ID() {
		return FITEMPRI_ID;
	}

	public void setFITEMPRI_ID(String fITEMPRI_ID) {
		FITEMPRI_ID = fITEMPRI_ID;
	}

	public String getFITEMPRI_ADD_MACH() {
		return FITEMPRI_ADD_MACH;
	}

	public void setFITEMPRI_ADD_MACH(String fITEMPRI_ADD_MACH) {
		FITEMPRI_ADD_MACH = fITEMPRI_ADD_MACH;
	}

	public String getFITEMPRI_ADD_USER() {
		return FITEMPRI_ADD_USER;
	}

	public void setFITEMPRI_ADD_USER(String fITEMPRI_ADD_USER) {
		FITEMPRI_ADD_USER = fITEMPRI_ADD_USER;
	}

	public String getFITEMPRI_ITEM_CODE() {
		return FITEMPRI_ITEM_CODE;
	}

	public void setFITEMPRI_ITEM_CODE(String fITEMPRI_ITEM_CODE) {
		FITEMPRI_ITEM_CODE = fITEMPRI_ITEM_CODE;
	}

	public String getFITEMPRI_PRICE() {
		return FITEMPRI_PRICE;
	}

	public void setFITEMPRI_PRICE(String fITEMPRI_PRICE) {
		FITEMPRI_PRICE = fITEMPRI_PRICE;
	}

	public String getFITEMPRI_PRIL_CODE() {
		return FITEMPRI_PRIL_CODE;
	}

	public void setFITEMPRI_PRIL_CODE(String fITEMPRI_PRIL_CODE) {
		FITEMPRI_PRIL_CODE = fITEMPRI_PRIL_CODE;
	}

	public String getFITEMPRI_SKU_CODE() {
		return FITEMPRI_SKU_CODE;
	}

	public void setFITEMPRI_SKU_CODE(String fITEMPRI_SKU_CODE) {
		FITEMPRI_SKU_CODE = fITEMPRI_SKU_CODE;
	}

	public String getFITEMPRI_TXN_MACH() {
		return FITEMPRI_TXN_MACH;
	}

	public void setFITEMPRI_TXN_MACH(String fITEMPRI_TXN_MACH) {
		FITEMPRI_TXN_MACH = fITEMPRI_TXN_MACH;
	}

	public String getFITEMPRI_TXN_USER() {
		return FITEMPRI_TXN_USER;
	}

	public void setFITEMPRI_TXN_USER(String fITEMPRI_TXN_USER) {
		FITEMPRI_TXN_USER = fITEMPRI_TXN_USER;
	}

	public String getFITEMPRI_MIN_PRICE() {
		return FITEMPRI_MIN_PRICE;
	}

	public void setFITEMPRI_MIN_PRICE(String FITEMPRI_MIN_PRICE) {
		this.FITEMPRI_MIN_PRICE = FITEMPRI_MIN_PRICE;
	}

	public String getFITEMPRI_MAX_PRICE() {
		return FITEMPRI_MAX_PRICE;
	}

	public void setFITEMPRI_MAX_PRICE(String FITEMPRI_MAX_PRICE) {
		this.FITEMPRI_MAX_PRICE = FITEMPRI_MAX_PRICE;
	}
	public static ItemPri parseItemPrices(JSONObject instance) throws JSONException {

		if (instance != null) {
			ItemPri pri = new ItemPri();

			pri.setFITEMPRI_ADD_MACH(instance.getString("AddMach"));
			pri.setFITEMPRI_ADD_USER(instance.getString("AddUser"));
			pri.setFITEMPRI_ITEM_CODE(instance.getString("ItemCode"));
			pri.setFITEMPRI_PRICE(instance.getString("Price"));
			pri.setFITEMPRI_PRIL_CODE(instance.getString("PrilCode"));
			pri.setFITEMPRI_TXN_MACH(instance.getString("TxnMach"));
			pri.setFITEMPRI_TXN_USER(instance.getString("Txnuser"));

			return pri;
		}

		return null;
	}

}
