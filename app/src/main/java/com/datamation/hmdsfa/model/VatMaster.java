package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class VatMaster {

	@SerializedName("VatCalType")
	private String VatCalType;
	@SerializedName("VatCode")
	private String VatCode;
	@SerializedName("VatDesciption")
	private String VatDesciption;
	@SerializedName("VatPer")
	private int VatPer;

	public String getVatCalType() {
		return VatCalType;
	}

	public void setVatCalType(String vatCalType) {
		VatCalType = vatCalType;
	}

	public String getVatCode() {
		return VatCode;
	}

	public void setVatCode(String vatCode) {
		VatCode = vatCode;
	}

	public String getVatDesciption() {
		return VatDesciption;
	}

	public void setVatDesciption(String vatDesciption) {
		VatDesciption = vatDesciption;
	}

	public int getVatPer() {
		return VatPer;
	}

	public void setVatPer(int vatPer) {
		VatPer = vatPer;
	}

	public static VatMaster parseVAT(JSONObject instance) throws JSONException {

		if (instance != null) {
			VatMaster vat = new VatMaster();
			vat.setVatCalType(instance.getString("VatCalType"));
			vat.setVatCode(instance.getString("VatCode"));
			vat.setVatDesciption(instance.getString("VatDesciption"));
			vat.setVatPer(instance.getInt("VatPer"));

			return vat;
		}

		return null;
	}
}
