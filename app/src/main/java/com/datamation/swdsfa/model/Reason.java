package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Reason {

	private String FREASON_ID;
	private String FREASON_ADD_DATE;
	private String FREASON_ADD_MACH;
	private String FREASON_ADD_USER;
	private String FREASON_CODE;
	private String FREASON_NAME;
	private String FREASON_REATCODE;
	private String FREASON_RECORD_ID;
	private String FREASON_TYPE;

	public String getFREASON_ID() {
		return FREASON_ID;
	}

	public void setFREASON_ID(String fREASON_ID) {
		FREASON_ID = fREASON_ID;
	}

	public String getFREASON_ADD_DATE() {
		return FREASON_ADD_DATE;
	}

	public void setFREASON_ADD_DATE(String fREASON_ADD_DATE) {
		FREASON_ADD_DATE = fREASON_ADD_DATE;
	}

	public String getFREASON_ADD_MACH() {
		return FREASON_ADD_MACH;
	}

	public void setFREASON_ADD_MACH(String fREASON_ADD_MACH) {
		FREASON_ADD_MACH = fREASON_ADD_MACH;
	}

	public String getFREASON_ADD_USER() {
		return FREASON_ADD_USER;
	}

	public void setFREASON_ADD_USER(String fREASON_ADD_USER) {
		FREASON_ADD_USER = fREASON_ADD_USER;
	}

	public String getFREASON_CODE() {
		return FREASON_CODE;
	}

	public void setFREASON_CODE(String fREASON_CODE) {
		FREASON_CODE = fREASON_CODE;
	}

	public String getFREASON_NAME() {
		return FREASON_NAME;
	}

	public void setFREASON_NAME(String fREASON_NAME) {
		FREASON_NAME = fREASON_NAME;
	}

	public String getFREASON_REATCODE() {
		return FREASON_REATCODE;
	}

	public void setFREASON_REATCODE(String fREASON_REATCODE) {
		FREASON_REATCODE = fREASON_REATCODE;
	}

	public String getFREASON_RECORD_ID() {
		return FREASON_RECORD_ID;
	}

	public void setFREASON_RECORD_ID(String fREASON_RECORD_ID) {
		FREASON_RECORD_ID = fREASON_RECORD_ID;
	}

	public String getFREASON_TYPE() {
		return FREASON_TYPE;
	}

	public void setFREASON_TYPE(String fREASON_TYPE) {
		FREASON_TYPE = fREASON_TYPE;
	}



	public static Reason parseReason(JSONObject instance) throws JSONException {

		if (instance != null) {
			Reason reason = new Reason();
			reason.setFREASON_ADD_DATE(instance.getString("AddDate"));
			reason.setFREASON_ADD_MACH(instance.getString("AddMach"));
			reason.setFREASON_ADD_USER(instance.getString("AddUser"));
			reason.setFREASON_CODE(instance.getString("ReaCode"));
			reason.setFREASON_NAME(instance.getString("ReaName"));
			reason.setFREASON_REATCODE(instance.getString("ReaTcode"));
			reason.setFREASON_TYPE(instance.getString("Type"));
			return reason;
		}

		return null;
	}
}
