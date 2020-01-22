package com.datamation.swdsfa.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Control {

    private String FCONTROL_ID;
    private String FCONTROL_COM_NAME;
    private String FCONTROL_COM_ADD1;
    private String FCONTROL_COM_ADD2;
    private String FCONTROL_COM_ADD3;
    private String FCONTROL_COM_TEL1;
    private String FCONTROL_COM_TEL2;
    private String FCONTROL_COM_FAX;
    private String FCONTROL_COM_EMAIL;
    private String FCONTROL_COM_WEB;
    private String FCONTROL_FYEAR;
    private String FCONTROL_TYEAR;
    private String FCONTROL_COM_REGNO;
    private String FCONTROL_FTXN;
    private String FCONTROL_TTXN;
    private String FCONTROL_CRYSTALPATH;
    private String FCONTROL_VATCMTAXNO;
    private String FCONTROL_NBTCMTAXNO;
    private String FCONTROL_SYSTYPE;
    private String FCONTROL_DEALCODE;
    private String FCONTROL_BASECUR;
    private String FCONTROL_BALGCRLM;
    private String FCONTROL_CONAGE1;
    private String FCONTROL_CONAGE2;
    private String FCONTROL_CONAGE3;
    private String FCONTROL_CONAGE4;
    private String FCONTROL_CONAGE5;
    private String FCONTROL_CONAGE6;
    private String FCONTROL_CONAGE7;
    private String FCONTROL_CONAGE8;
    private String FCONTROL_CONAGE9;
    private String FCONTROL_CONAGE10;
    private String FCONTROL_CONAGE11;
    private String FCONTROL_CONAGE12;
    private String FCONTROL_CONAGE13;
    private String FCONTROL_CONAGE14;
    private String FCONTROL_SALESACC;
    private String FCONTROL_COMDISPER;

    public String getFCONTROL_SALESACC() {
        return FCONTROL_SALESACC;
    }

    public void setFCONTROL_SALESACC(String fCONTROL_SALESACC) {
        FCONTROL_SALESACC = fCONTROL_SALESACC;
    }

    public String getFCONTROL_SYSTYPE() {
        return FCONTROL_SYSTYPE;
    }

    public void setFCONTROL_SYSTYPE(String fCONTROL_SYSTYPE) {
        FCONTROL_SYSTYPE = fCONTROL_SYSTYPE;
    }

    public String getFCONTROL_ID() {
        return FCONTROL_ID;
    }

    public void setFCONTROL_ID(String fCONTROL_ID) {
        FCONTROL_ID = fCONTROL_ID;
    }

    public String getFCONTROL_COM_NAME() {
        return FCONTROL_COM_NAME;
    }

    public void setFCONTROL_COM_NAME(String fCONTROL_COM_NAME) {
        FCONTROL_COM_NAME = fCONTROL_COM_NAME;
    }

    public String getFCONTROL_COM_ADD1() {
        return FCONTROL_COM_ADD1;
    }

    public void setFCONTROL_COM_ADD1(String fCONTROL_COM_ADD1) {
        FCONTROL_COM_ADD1 = fCONTROL_COM_ADD1;
    }

    public String getFCONTROL_COM_ADD2() {
        return FCONTROL_COM_ADD2;
    }

    public void setFCONTROL_COM_ADD2(String fCONTROL_COM_ADD2) {
        FCONTROL_COM_ADD2 = fCONTROL_COM_ADD2;
    }

    public String getFCONTROL_COM_ADD3() {
        return FCONTROL_COM_ADD3;
    }

    public void setFCONTROL_COM_ADD3(String fCONTROL_COM_ADD3) {
        FCONTROL_COM_ADD3 = fCONTROL_COM_ADD3;
    }

    public String getFCONTROL_COM_TEL1() {
        return FCONTROL_COM_TEL1;
    }

    public void setFCONTROL_COM_TEL1(String fCONTROL_COM_TEL1) {
        FCONTROL_COM_TEL1 = fCONTROL_COM_TEL1;
    }

    public String getFCONTROL_COM_TEL2() {
        return FCONTROL_COM_TEL2;
    }

    public void setFCONTROL_COM_TEL2(String fCONTROL_COM_TEL2) {
        FCONTROL_COM_TEL2 = fCONTROL_COM_TEL2;
    }

    public String getFCONTROL_COM_FAX() {
        return FCONTROL_COM_FAX;
    }

    public void setFCONTROL_COM_FAX(String fCONTROL_COM_FAX) {
        FCONTROL_COM_FAX = fCONTROL_COM_FAX;
    }

    public String getFCONTROL_COM_EMAIL() {
        return FCONTROL_COM_EMAIL;
    }

    public void setFCONTROL_COM_EMAIL(String fCONTROL_COM_EMAIL) {
        FCONTROL_COM_EMAIL = fCONTROL_COM_EMAIL;
    }

    public String getFCONTROL_COM_WEB() {
        return FCONTROL_COM_WEB;
    }

    public void setFCONTROL_COM_WEB(String fCONTROL_COM_WEB) {
        FCONTROL_COM_WEB = fCONTROL_COM_WEB;
    }

    public String getFCONTROL_FYEAR() {
        return FCONTROL_FYEAR;
    }

    public void setFCONTROL_FYEAR(String fCONTROL_FYEAR) {
        FCONTROL_FYEAR = fCONTROL_FYEAR;
    }

    public String getFCONTROL_TYEAR() {
        return FCONTROL_TYEAR;
    }

    public void setFCONTROL_TYEAR(String fCONTROL_TYEAR) {
        FCONTROL_TYEAR = fCONTROL_TYEAR;
    }

    public String getFCONTROL_COM_REGNO() {
        return FCONTROL_COM_REGNO;
    }

    public void setFCONTROL_COM_REGNO(String fCONTROL_COM_REGNO) {
        FCONTROL_COM_REGNO = fCONTROL_COM_REGNO;
    }

    public String getFCONTROL_FTXN() {
        return FCONTROL_FTXN;
    }

    public void setFCONTROL_FTXN(String fCONTROL_FTXN) {
        FCONTROL_FTXN = fCONTROL_FTXN;
    }

    public String getFCONTROL_TTXN() {
        return FCONTROL_TTXN;
    }

    public void setFCONTROL_TTXN(String fCONTROL_TTXN) {
        FCONTROL_TTXN = fCONTROL_TTXN;
    }

    public String getFCONTROL_CRYSTALPATH() {
        return FCONTROL_CRYSTALPATH;
    }

    public void setFCONTROL_CRYSTALPATH(String fCONTROL_CRYSTALPATH) {
        FCONTROL_CRYSTALPATH = fCONTROL_CRYSTALPATH;
    }

    public String getFCONTROL_VATCMTAXNO() {
        return FCONTROL_VATCMTAXNO;
    }

    public void setFCONTROL_VATCMTAXNO(String fCONTROL_VATCMTAXNO) {
        FCONTROL_VATCMTAXNO = fCONTROL_VATCMTAXNO;
    }

    public String getFCONTROL_NBTCMTAXNO() {
        return FCONTROL_NBTCMTAXNO;
    }

    public void setFCONTROL_NBTCMTAXNO(String fCONTROL_NBTCMTAXNO) {
        FCONTROL_NBTCMTAXNO = fCONTROL_NBTCMTAXNO;
    }

    public String getFCONTROL_DEALCODE() {
        return FCONTROL_DEALCODE;
    }

    public void setFCONTROL_DEALCODE(String fCONTROL_DEALCODE) {
        FCONTROL_DEALCODE = fCONTROL_DEALCODE;
    }

    public String getFCONTROL_BASECUR() {
        return FCONTROL_BASECUR;
    }

    public void setFCONTROL_BASECUR(String fCONTROL_BASECUR) {
        FCONTROL_BASECUR = fCONTROL_BASECUR;
    }

    public String getFCONTROL_BALGCRLM() {
        return FCONTROL_BALGCRLM;
    }

    public void setFCONTROL_BALGCRLM(String fCONTROL_BALGCRLM) {
        FCONTROL_BALGCRLM = fCONTROL_BALGCRLM;
    }

    public String getFCONTROL_CONAGE1() {
        return FCONTROL_CONAGE1;
    }

    public void setFCONTROL_CONAGE1(String fCONTROL_CONAGE1) {
        FCONTROL_CONAGE1 = fCONTROL_CONAGE1;
    }

    public String getFCONTROL_CONAGE2() {
        return FCONTROL_CONAGE2;
    }

    public void setFCONTROL_CONAGE2(String fCONTROL_CONAGE2) {
        FCONTROL_CONAGE2 = fCONTROL_CONAGE2;
    }

    public String getFCONTROL_CONAGE3() {
        return FCONTROL_CONAGE3;
    }

    public void setFCONTROL_CONAGE3(String fCONTROL_CONAGE3) {
        FCONTROL_CONAGE3 = fCONTROL_CONAGE3;
    }

    public String getFCONTROL_CONAGE4() {
        return FCONTROL_CONAGE4;
    }

    public void setFCONTROL_CONAGE4(String fCONTROL_CONAGE4) {
        FCONTROL_CONAGE4 = fCONTROL_CONAGE4;
    }

    public String getFCONTROL_CONAGE5() {
        return FCONTROL_CONAGE5;
    }

    public void setFCONTROL_CONAGE5(String fCONTROL_CONAGE5) {
        FCONTROL_CONAGE5 = fCONTROL_CONAGE5;
    }

    public String getFCONTROL_CONAGE6() {
        return FCONTROL_CONAGE6;
    }

    public void setFCONTROL_CONAGE6(String fCONTROL_CONAGE6) {
        FCONTROL_CONAGE6 = fCONTROL_CONAGE6;
    }

    public String getFCONTROL_CONAGE7() {
        return FCONTROL_CONAGE7;
    }

    public void setFCONTROL_CONAGE7(String fCONTROL_CONAGE7) {
        FCONTROL_CONAGE7 = fCONTROL_CONAGE7;
    }

    public String getFCONTROL_CONAGE8() {
        return FCONTROL_CONAGE8;
    }

    public void setFCONTROL_CONAGE8(String fCONTROL_CONAGE8) {
        FCONTROL_CONAGE8 = fCONTROL_CONAGE8;
    }

    public String getFCONTROL_CONAGE9() {
        return FCONTROL_CONAGE9;
    }

    public void setFCONTROL_CONAGE9(String fCONTROL_CONAGE9) {
        FCONTROL_CONAGE9 = fCONTROL_CONAGE9;
    }

    public String getFCONTROL_CONAGE10() {
        return FCONTROL_CONAGE10;
    }

    public void setFCONTROL_CONAGE10(String fCONTROL_CONAGE10) {
        FCONTROL_CONAGE10 = fCONTROL_CONAGE10;
    }

    public String getFCONTROL_CONAGE11() {
        return FCONTROL_CONAGE11;
    }

    public void setFCONTROL_CONAGE11(String fCONTROL_CONAGE11) {
        FCONTROL_CONAGE11 = fCONTROL_CONAGE11;
    }

    public String getFCONTROL_CONAGE12() {
        return FCONTROL_CONAGE12;
    }

    public void setFCONTROL_CONAGE12(String fCONTROL_CONAGE12) {
        FCONTROL_CONAGE12 = fCONTROL_CONAGE12;
    }

    public String getFCONTROL_CONAGE13() {
        return FCONTROL_CONAGE13;
    }

    public void setFCONTROL_CONAGE13(String fCONTROL_CONAGE13) {
        FCONTROL_CONAGE13 = fCONTROL_CONAGE13;
    }

    public String getFCONTROL_CONAGE14() {
        return FCONTROL_CONAGE14;
    }

    public void setFCONTROL_CONAGE14(String fCONTROL_CONAGE14) {
        FCONTROL_CONAGE14 = fCONTROL_CONAGE14;
    }


    public String getFCONTROL_COMDISPER() {
        return FCONTROL_COMDISPER;
    }

    public void setFCONTROL_COMDISPER(String fCONTROL_COMDISPER) {
        FCONTROL_COMDISPER = fCONTROL_COMDISPER;
    }
    public static Control parseControlDetails(JSONObject instance) throws JSONException, NumberFormatException {

        if(instance != null) {

                Control ctrl = new Control();

                ctrl.setFCONTROL_COM_ADD1(instance.getString("ComAdd1"));
                ctrl.setFCONTROL_COM_ADD2(instance.getString("ComAdd2"));
                ctrl.setFCONTROL_COM_ADD3(instance.getString("ComAdd3"));
                ctrl.setFCONTROL_COM_NAME(instance.getString("ComName"));
                ctrl.setFCONTROL_BASECUR(instance.getString("basecur"));
                ctrl.setFCONTROL_COM_EMAIL(instance.getString("comemail"));
                ctrl.setFCONTROL_COM_TEL1(instance.getString("comtel1"));
                ctrl.setFCONTROL_COM_TEL2(instance.getString("comtel2"));
                ctrl.setFCONTROL_COM_FAX(instance.getString("comfax1"));
                ctrl.setFCONTROL_COM_WEB(instance.getString("comweb"));
                ctrl.setFCONTROL_CONAGE1(instance.getString("conage1"));
                ctrl.setFCONTROL_CONAGE2(instance.getString("conage2"));
                ctrl.setFCONTROL_CONAGE3(instance.getString("conage3"));
                ctrl.setFCONTROL_CONAGE4(instance.getString("conage4"));
                ctrl.setFCONTROL_CONAGE5(instance.getString("conage5"));
                ctrl.setFCONTROL_CONAGE6(instance.getString("conage6"));
                ctrl.setFCONTROL_CONAGE7(instance.getString("conage7"));
                ctrl.setFCONTROL_CONAGE8(instance.getString("conage8"));
                ctrl.setFCONTROL_CONAGE9(instance.getString("conage9"));
                ctrl.setFCONTROL_CONAGE10(instance.getString("conage10"));
                ctrl.setFCONTROL_CONAGE11(instance.getString("conage11"));
                ctrl.setFCONTROL_CONAGE12(instance.getString("conage12"));
                ctrl.setFCONTROL_CONAGE13(instance.getString("conage13"));
                ctrl.setFCONTROL_CONAGE14(instance.getString("conage14"));
                ctrl.setFCONTROL_COM_REGNO(instance.getString("comRegNo"));
                ctrl.setFCONTROL_SYSTYPE("");
                // ctrl.setFCONTROL_COMDISPER(jObject.getString("DisPer"));
                // ctrl.setFCONTROL_VATCMTAXNO(jObject.getString("vatcmtaxno"));

                // Type = jObject.getString("SysType");

                return ctrl;

        }
            return null;

        }
}
