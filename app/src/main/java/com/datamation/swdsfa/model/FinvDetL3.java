package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FinvDetL3 {
	
	private String FINVDETL3_ID;
	private String FINVDETL3_AMT;
	private String FINVDETL3_ITEM_CODE;
	private String FINVDETL3_BRAND_CODE;
	private String FINVDETL3_QTY;
	private String FINVDETL3_REF_NO;
	private String FINVDETL3_SEQ_NO;
	private String FINVDETL3_TAX_AMT;
	private String FINVDETL3_TAX_COM_CODE;
	private String FINVDETL3_TXN_DATE;
	private String FINVDETL3_COST_CODE;
	
	public String getFINVDETL3_ID() {
		return FINVDETL3_ID;
	}
	public void setFINVDETL3_ID(String fINVDETL3_ID) {
		FINVDETL3_ID = fINVDETL3_ID;
	}
	public String getFINVDETL3_AMT() {
		return FINVDETL3_AMT;
	}
	public void setFINVDETL3_AMT(String fINVDETL3_AMT) {
		FINVDETL3_AMT = fINVDETL3_AMT;
	}
	public String getFINVDETL3_ITEM_CODE() {
		return FINVDETL3_ITEM_CODE;
	}
	public void setFINVDETL3_ITEM_CODE(String fINVDETL3_ITEM_CODE) {
		FINVDETL3_ITEM_CODE = fINVDETL3_ITEM_CODE;
	}
	public String getFINVDETL3_QTY() {
		return FINVDETL3_QTY;
	}
	public void setFINVDETL3_QTY(String fINVDETL3_QTY) {
		FINVDETL3_QTY = fINVDETL3_QTY;
	}
	public String getFINVDETL3_REF_NO() {
		return FINVDETL3_REF_NO;
	}
	public void setFINVDETL3_REF_NO(String fINVDETL3_REF_NO) {
		FINVDETL3_REF_NO = fINVDETL3_REF_NO;
	}
	public String getFINVDETL3_SEQ_NO() {
		return FINVDETL3_SEQ_NO;
	}
	public void setFINVDETL3_SEQ_NO(String fINVDETL3_SEQ_NO) {
		FINVDETL3_SEQ_NO = fINVDETL3_SEQ_NO;
	}
	public String getFINVDETL3_TAX_AMT() {
		return FINVDETL3_TAX_AMT;
	}
	public void setFINVDETL3_TAX_AMT(String fINVDETL3_TAX_AMT) {
		FINVDETL3_TAX_AMT = fINVDETL3_TAX_AMT;
	}
	public String getFINVDETL3_TAX_COM_CODE() {
		return FINVDETL3_TAX_COM_CODE;
	}
	public void setFINVDETL3_TAX_COM_CODE(String fINVDETL3_TAX_COM_CODE) {
		FINVDETL3_TAX_COM_CODE = fINVDETL3_TAX_COM_CODE;
	}
	public String getFINVDETL3_TXN_DATE() {
		return FINVDETL3_TXN_DATE;
	}
	public void setFINVDETL3_TXN_DATE(String fINVDETL3_TXN_DATE) {
		FINVDETL3_TXN_DATE = fINVDETL3_TXN_DATE;
	}
	public String getFINVDETL3_BRAND_CODE() {
		return FINVDETL3_BRAND_CODE;
	}
	public void setFINVDETL3_BRAND_CODE(String fINVDETL3_BRAND_CODE) {
		FINVDETL3_BRAND_CODE = fINVDETL3_BRAND_CODE;
	}
	public String getFINVDETL3_COST_CODE() {
		return FINVDETL3_COST_CODE;
	}
	public void setFINVDETL3_COST_CODE(String fINVDETL3_COST_CODE) {
		FINVDETL3_COST_CODE = fINVDETL3_COST_CODE;
	}
	public static FinvDetL3 parseInvoiceDets(JSONObject jObject) throws JSONException {

		if (jObject != null) {
			FinvDetL3 finvDetL3 = new FinvDetL3();


			finvDetL3.setFINVDETL3_AMT(jObject.getString("Amt"));
		//	finvDetL3.setFINVDETL3_BRAND_CODE(jObject.getString("BrandCode"));
			finvDetL3.setFINVDETL3_ITEM_CODE(jObject.getString("ItemCode"));
			finvDetL3.setFINVDETL3_QTY(jObject.getString("Qty"));
			finvDetL3.setFINVDETL3_REF_NO(jObject.getString("RefNo"));
			finvDetL3.setFINVDETL3_SEQ_NO(jObject.getString("SeqNo"));
			finvDetL3.setFINVDETL3_TAX_AMT(jObject.getString("TaxAmt"));
			finvDetL3.setFINVDETL3_TAX_COM_CODE(jObject.getString("TaxComCode"));
			finvDetL3.setFINVDETL3_TXN_DATE(jObject.getString("TxnDate"));


			return finvDetL3;
		}
		return null;
	}
}
