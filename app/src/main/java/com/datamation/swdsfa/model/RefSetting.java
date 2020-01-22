package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class RefSetting {
	

	private String REF_SETTINGS_CODE;
	private String REFSETTING_CHAR_VAL;
	private String REFSETTING_REMARKS;

	public String getREF_SETTINGS_CODE() {
		return REF_SETTINGS_CODE;
	}

	public void setREF_SETTINGS_CODE(String REF_SETTINGS_CODE) {
		this.REF_SETTINGS_CODE = REF_SETTINGS_CODE;
	}

	public String getREFSETTING_CHAR_VAL() {
		return REFSETTING_CHAR_VAL;
	}

	public void setREFSETTING_CHAR_VAL(String REFSETTING_CHAR_VAL) {
		this.REFSETTING_CHAR_VAL = REFSETTING_CHAR_VAL;
	}

	public String getREFSETTING_REMARKS() {
		return REFSETTING_REMARKS;
	}

	public void setREFSETTING_REMARKS(String REFSETTING_REMARKS) {
		this.REFSETTING_REMARKS = REFSETTING_REMARKS;
	}

	public static RefSetting parseSetting(JSONObject instance) throws JSONException {

		if (instance != null) {
			RefSetting setting = new RefSetting();
			setting.setREF_SETTINGS_CODE(instance.getString("settingCode"));
			setting.setREFSETTING_CHAR_VAL(instance.getString("charVal"));
			setting.setREFSETTING_REMARKS(instance.getString("remarks"));

			return setting;
		}

		return null;
	}
}
