package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FItenrHed {
	
	private String FITENRHED_ID;
	private String FITENRHED_COST_CODE;
	private String FITENRHED_DEAL_CODE;
	private String FITENRHED_MONTH;
	private String FITENRHED_REF_NO;
	private String FITENRHED_REMARKS1;
	private String FITENRHED_REP_CODE;
	private String FITENRHED_YEAR;
	
	public String getFITENRHED_ID() {
		return FITENRHED_ID;
	}
	public void setFITENRHED_ID(String fITENRHED_ID) {
		FITENRHED_ID = fITENRHED_ID;
	}
	public String getFITENRHED_COST_CODE() {
		return FITENRHED_COST_CODE;
	}
	public void setFITENRHED_COST_CODE(String fITENRHED_COST_CODE) {
		FITENRHED_COST_CODE = fITENRHED_COST_CODE;
	}
	public String getFITENRHED_DEAL_CODE() {
		return FITENRHED_DEAL_CODE;
	}
	public void setFITENRHED_DEAL_CODE(String fITENRHED_DEAL_CODE) {
		FITENRHED_DEAL_CODE = fITENRHED_DEAL_CODE;
	}
	public String getFITENRHED_MONTH() {
		return FITENRHED_MONTH;
	}
	public void setFITENRHED_MONTH(String fITENRHED_MONTH) {
		FITENRHED_MONTH = fITENRHED_MONTH;
	}
	public String getFITENRHED_REF_NO() {
		return FITENRHED_REF_NO;
	}
	public void setFITENRHED_REF_NO(String fITENRHED_REF_NO) {
		FITENRHED_REF_NO = fITENRHED_REF_NO;
	}
	public String getFITENRHED_REMARKS1() {
		return FITENRHED_REMARKS1;
	}
	public void setFITENRHED_REMARKS1(String fITENRHED_REMARKS1) {
		FITENRHED_REMARKS1 = fITENRHED_REMARKS1;
	}
	public String getFITENRHED_REP_CODE() {
		return FITENRHED_REP_CODE;
	}
	public void setFITENRHED_REP_CODE(String fITENRHED_REP_CODE) {
		FITENRHED_REP_CODE = fITENRHED_REP_CODE;
	}
	public String getFITENRHED_YEAR() {
		return FITENRHED_YEAR;
	}
	public void setFITENRHED_YEAR(String fITENRHED_YEAR) {
		FITENRHED_YEAR = fITENRHED_YEAR;
	}

	public static FItenrHed parseIteanaryHed(JSONObject jObject) throws JSONException {

		if (jObject != null) {
			FItenrHed fItenrHed = new FItenrHed();
						fItenrHed.setFITENRHED_COST_CODE(jObject.getString("CostCode"));
                        fItenrHed.setFITENRHED_DEAL_CODE(jObject.getString("DealCode"));
                        fItenrHed.setFITENRHED_MONTH(jObject.getString("Month"));
                        fItenrHed.setFITENRHED_REF_NO(jObject.getString("RefNo"));
                        fItenrHed.setFITENRHED_REMARKS1(jObject.getString("Remarks1"));
                        fItenrHed.setFITENRHED_REP_CODE(jObject.getString("RepCode"));
                        fItenrHed.setFITENRHED_YEAR(jObject.getString("Year"));


			return fItenrHed;
		}

		return null;
	}
}
