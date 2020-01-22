package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Bank {
    private String FBANK_ID;
    private String FBANK_RECORD_ID;
    private String FBANK_BANK_CODE;
    private String FBANK_BANK_NAME;
    private String FBANK_BANK_ACC_NO;
    private String FBANK_BRANCH;
    private String FBANK_ADD1;
    private String FBANK_ADD2;
    private String FBANK_ADD_DATE;
    private String FBANK_ADD_MACH;
    private String FBANK_ADD_USER;

    public String getFBANK_ADD_MACH() {
        return FBANK_ADD_MACH;
    }

    public void setFBANK_ADD_MACH(String fBANK_ADD_MACH) {
        FBANK_ADD_MACH = fBANK_ADD_MACH;
    }

    public String getFBANK_ADD_USER() {
        return FBANK_ADD_USER;
    }

    public void setFBANK_ADD_USER(String fBANK_ADD_USER) {
        FBANK_ADD_USER = fBANK_ADD_USER;
    }

    public String getFBANK_ID() {
        return FBANK_ID;
    }

    public void setFBANK_ID(String fBANK_ID) {
        FBANK_ID = fBANK_ID;
    }

    public String getFBANK_RECORD_ID() {
        return FBANK_RECORD_ID;
    }

    public void setFBANK_RECORD_ID(String fBANK_RECORD_ID) {
        FBANK_RECORD_ID = fBANK_RECORD_ID;
    }

    public String getFBANK_BANK_CODE() {
        return FBANK_BANK_CODE;
    }

    public void setFBANK_BANK_CODE(String fBANK_BANK_CODE) {
        FBANK_BANK_CODE = fBANK_BANK_CODE;
    }

    public String getFBANK_BANK_NAME() {
        return FBANK_BANK_NAME;
    }

    public void setFBANK_BANK_NAME(String fBANK_BANK_NAME) {
        FBANK_BANK_NAME = fBANK_BANK_NAME;
    }

    public String getFBANK_BANK_ACC_NO() {
        return FBANK_BANK_ACC_NO;
    }

    public void setFBANK_BANK_ACC_NO(String fBANK_BANK_ACC_NO) {
        FBANK_BANK_ACC_NO = fBANK_BANK_ACC_NO;
    }

    public String getFBANK_BRANCH() {
        return FBANK_BRANCH;
    }

    public void setFBANK_BRANCH(String fBANK_BRANCH) {
        FBANK_BRANCH = fBANK_BRANCH;
    }

    public String getFBANK_ADD1() {
        return FBANK_ADD1;
    }

    public void setFBANK_ADD1(String fBANK_ADD1) {
        FBANK_ADD1 = fBANK_ADD1;
    }

    public String getFBANK_ADD2() {
        return FBANK_ADD2;
    }

    public void setFBANK_ADD2(String fBANK_ADD2) {
        FBANK_ADD2 = fBANK_ADD2;
    }

    public String getFBANK_ADD_DATE() {
        return FBANK_ADD_DATE;
    }

    public void setFBANK_ADD_DATE(String fBANK_ADD_DATE) {
        FBANK_ADD_DATE = fBANK_ADD_DATE;
    }

    @Override
    public String toString() {
        return FBANK_BANK_NAME;
    }

    public static Bank parseBank(JSONObject instance) throws JSONException {

        if (instance != null) {
            Bank bank = new Bank();

            bank.setFBANK_BANK_CODE(instance.getString("Bankcode"));
            bank.setFBANK_BANK_NAME(instance.getString("Bankname"));
            bank.setFBANK_BANK_ACC_NO(instance.getString("Bankaccno"));
            bank.setFBANK_BRANCH(instance.getString("Branch"));
            bank.setFBANK_ADD1(instance.getString("Bankadd1"));
            bank.setFBANK_ADD2(instance.getString("Bankadd2"));
            bank.setFBANK_ADD_DATE(instance.getString("AddDate"));
            bank.setFBANK_ADD_MACH(instance.getString("AddMach"));
            bank.setFBANK_ADD_USER(instance.getString("AddUser"));

            return bank;
        }

        return null;
    }
}
