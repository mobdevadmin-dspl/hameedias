package com.datamation.hmdsfa.model;

import com.google.gson.annotations.SerializedName;

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
}
