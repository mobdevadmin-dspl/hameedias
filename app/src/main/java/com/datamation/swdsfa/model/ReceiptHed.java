package com.datamation.swdsfa.model;

import java.util.ArrayList;

public class ReceiptHed {
	
	private String FPRECHED_ID ;
	private String FPRECHED_REFNO ;
	private String FPRECHED_REFNO1 ;
	//private String FPRECHED_REFNO2 ;
	private String FPRECHED_MANUREF ;
	private String FPRECHED_SALEREFNO ;
	private String FPRECHED_REPCODE ;
	private String FPRECHED_TXNTYPE ;
	private String FPRECHED_CHQNO ;
	private String FPRECHED_CHQDATE ;
	private String FPRECHED_TXNDATE ;
	private String FPRECHED_CURCODE ;
	private String FPRECHED_CURRATE1;
	private String FPRECHED_COSTCODE;
	private String FPRECHED_DEBCODE ;
	private String FPRECHED_TOTALAMT;
	private String FPRECHED_BTOTALAMT;
	private String FPRECHED_PAYTYPE ;
	private String FPRECHED_PRTCOPY ;
	private String FPRECHED_REMARKS;
	private String FPRECHED_ADDUSER ;
	private String FPRECHED_ADDMACH ;
	private String FPRECHED_ADDDATE ;
	private String FPRECHED_RECORDID ;
	private String FPRECHED_TIMESTAMP ;
	private String FPRECHED_CURRATE ;
	private String FPRECHED_CUSBANK;
	private String FPRECHED_LONGITUDE;
	private String FPRECHED_LATITUDE;
	private String FPRECHED_ADDRESS;
	private String FPRECHED_START_TIME;
	private String FPRECHED_END_TIME ;
	private String FPRECHED_ISACTIVE ;
	private String FPRECHED_ISSYNCED;
	private String FPRECHED_ISDELETE;
	private String FPRECHED_BANKCODE;
	private String FPRECHED_BRANCHCODE;
	private String FPRECHED_ADDUSER_NEW;
	private String ConsoleDB;
	private String DistDB;
	private String NextNumVal;
	private ArrayList<ReceiptDet> RecDetList;

	public ArrayList<ReceiptDet> getRecDetList() {
		return RecDetList;
	}

	public void setRecDetList(ArrayList<ReceiptDet> recDetList) {
		RecDetList = recDetList;
	}

	public String getConsoleDB() {
		return ConsoleDB;
	}

	public void setConsoleDB(String consoleDB) {
		ConsoleDB = consoleDB;
	}

	public String getDistDB() {
		return DistDB;
	}

	public void setDistDB(String distDB) {
		DistDB = distDB;
	}

	public String getNextNumVal() {
		return NextNumVal;
	}

	public void setNextNumVal(String nextNumVal) {
		NextNumVal = nextNumVal;
	}

	public String getFPRECHED_ADDUSER_NEW() {
		return FPRECHED_ADDUSER_NEW;
	}
	public void setFPRECHED_ADDUSER_NEW(String fPRECHED_ADDUSER_NEW) {
		FPRECHED_ADDUSER_NEW = fPRECHED_ADDUSER_NEW;
	}
	public String getFPRECHED_ISDELETE() {
		return FPRECHED_ISDELETE;
	}
	public void setFPRECHED_ISDELETE(String fPRECHED_ISDELETE) {
		FPRECHED_ISDELETE = fPRECHED_ISDELETE;
	}
	public String getFPRECHED_REFNO1() {
		return FPRECHED_REFNO1;
	}
	public void setFPRECHED_REFNO1(String fPRECHED_REFNO1) {
		FPRECHED_REFNO1 = fPRECHED_REFNO1;
	}
	public String getFPRECHED_ID() {
		return FPRECHED_ID;
	}
	public void setFPRECHED_ID(String fPRECHED_ID) {
		FPRECHED_ID = fPRECHED_ID;
	}
	public String getFPRECHED_REFNO() {
		return FPRECHED_REFNO;
	}
	public void setFPRECHED_REFNO(String fPRECHED_REFNO) {
		FPRECHED_REFNO = fPRECHED_REFNO;
	}
	public String getFPRECHED_MANUREF() {
		return FPRECHED_MANUREF;
	}
	public void setFPRECHED_MANUREF(String fPRECHED_MANUREF) {
		FPRECHED_MANUREF = fPRECHED_MANUREF;
	}
	public String getFPRECHED_SALEREFNO() {
		return FPRECHED_SALEREFNO;
	}
	public void setFPRECHED_SALEREFNO(String fPRECHED_SALEREFNO) {
		FPRECHED_SALEREFNO = fPRECHED_SALEREFNO;
	}
	public String getFPRECHED_REPCODE() {
		return FPRECHED_REPCODE;
	}
	public void setFPRECHED_REPCODE(String fPRECHED_REPCODE) {
		FPRECHED_REPCODE = fPRECHED_REPCODE;
	}
	public String getFPRECHED_TXNTYPE() {
		return FPRECHED_TXNTYPE;
	}
	public void setFPRECHED_TXNTYPE(String fPRECHED_TXNTYPE) {
		FPRECHED_TXNTYPE = fPRECHED_TXNTYPE;
	}
	public String getFPRECHED_CHQNO() {
		return FPRECHED_CHQNO;
	}
	public void setFPRECHED_CHQNO(String fPRECHED_CHQNO) {
		FPRECHED_CHQNO = fPRECHED_CHQNO;
	}
	public String getFPRECHED_CHQDATE() {
		return FPRECHED_CHQDATE;
	}
	public void setFPRECHED_CHQDATE(String fPRECHED_CHQDATE) {
		FPRECHED_CHQDATE = fPRECHED_CHQDATE;
	}
	public String getFPRECHED_TXNDATE() {
		return FPRECHED_TXNDATE;
	}
	public void setFPRECHED_TXNDATE(String fPRECHED_TXNDATE) {
		FPRECHED_TXNDATE = fPRECHED_TXNDATE;
	}
	public String getFPRECHED_CURCODE() {
		return FPRECHED_CURCODE;
	}
	public void setFPRECHED_CURCODE(String fPRECHED_CURCODE) {
		FPRECHED_CURCODE = fPRECHED_CURCODE;
	}
	public String getFPRECHED_CURRATE1() {
		return FPRECHED_CURRATE1;
	}
	public void setFPRECHED_CURRATE1(String fPRECHED_CURRATE1) {
		FPRECHED_CURRATE1 = fPRECHED_CURRATE1;
	}
	public String getFPRECHED_DEBCODE() {
		return FPRECHED_DEBCODE;
	}
	public void setFPRECHED_DEBCODE(String fPRECHED_DEBCODE) {
		FPRECHED_DEBCODE = fPRECHED_DEBCODE;
	}
	public String getFPRECHED_TOTALAMT() {
		return FPRECHED_TOTALAMT;
	}
	public void setFPRECHED_TOTALAMT(String fPRECHED_TOTALAMT) {
		FPRECHED_TOTALAMT = fPRECHED_TOTALAMT;
	}
	public String getFPRECHED_BTOTALAMT() {
		return FPRECHED_BTOTALAMT;
	}
	public void setFPRECHED_BTOTALAMT(String fPRECHED_BTOTALAMT) {
		FPRECHED_BTOTALAMT = fPRECHED_BTOTALAMT;
	}
	public String getFPRECHED_PAYTYPE() {
		return FPRECHED_PAYTYPE;
	}
	public void setFPRECHED_PAYTYPE(String fPRECHED_PAYTYPE) {
		FPRECHED_PAYTYPE = fPRECHED_PAYTYPE;
	}
	public String getFPRECHED_PRTCOPY() {
		return FPRECHED_PRTCOPY;
	}
	public void setFPRECHED_PRTCOPY(String fPRECHED_PRTCOPY) {
		FPRECHED_PRTCOPY = fPRECHED_PRTCOPY;
	}
	public String getFPRECHED_REMARKS() {
		return FPRECHED_REMARKS;
	}
	public void setFPRECHED_REMARKS(String fPRECHED_REMARKS) {
		FPRECHED_REMARKS = fPRECHED_REMARKS;
	}
	public String getFPRECHED_ADDUSER() {
		return FPRECHED_ADDUSER;
	}
	public void setFPRECHED_ADDUSER(String fPRECHED_ADDUSER) {
		FPRECHED_ADDUSER = fPRECHED_ADDUSER;
	}
	public String getFPRECHED_ADDMACH() {
		return FPRECHED_ADDMACH;
	}
	public void setFPRECHED_ADDMACH(String fPRECHED_ADDMACH) {
		FPRECHED_ADDMACH = fPRECHED_ADDMACH;
	}
	public String getFPRECHED_ADDDATE() {
		return FPRECHED_ADDDATE;
	}
	public void setFPRECHED_ADDDATE(String fPRECHED_ADDDATE) {
		FPRECHED_ADDDATE = fPRECHED_ADDDATE;
	}
	public String getFPRECHED_RECORDID() {
		return FPRECHED_RECORDID;
	}
	public void setFPRECHED_RECORDID(String fPRECHED_RECORDID) {
		FPRECHED_RECORDID = fPRECHED_RECORDID;
	}
	public String getFPRECHED_TIMESTAMP() {
		return FPRECHED_TIMESTAMP;
	}
	public void setFPRECHED_TIMESTAMP(String fPRECHED_TIMESTAMP) {
		FPRECHED_TIMESTAMP = fPRECHED_TIMESTAMP;
	}
	public String getFPRECHED_CURRATE() {
		return FPRECHED_CURRATE;
	}
	public void setFPRECHED_CURRATE(String fPRECHED_CURRATE) {
		FPRECHED_CURRATE = fPRECHED_CURRATE;
	}
	public String getFPRECHED_CUSBANK() {
		return FPRECHED_CUSBANK;
	}
	public void setFPRECHED_CUSBANK(String fPRECHED_CUSBANK) {
		FPRECHED_CUSBANK = fPRECHED_CUSBANK;
	}
	public String getFPRECHED_LONGITUDE() {
		return FPRECHED_LONGITUDE;
	}
	public void setFPRECHED_LONGITUDE(String fPRECHED_LONGITUDE) {
		FPRECHED_LONGITUDE = fPRECHED_LONGITUDE;
	}
	public String getFPRECHED_LATITUDE() {
		return FPRECHED_LATITUDE;
	}
	public void setFPRECHED_LATITUDE(String fPRECHED_LATITUDE) {
		FPRECHED_LATITUDE = fPRECHED_LATITUDE;
	}
	public String getFPRECHED_ADDRESS() {
		return FPRECHED_ADDRESS;
	}
	public void setFPRECHED_ADDRESS(String fPRECHED_ADDRESS) {
		FPRECHED_ADDRESS = fPRECHED_ADDRESS;
	}
	public String getFPRECHED_START_TIME() {
		return FPRECHED_START_TIME;
	}
	public void setFPRECHED_START_TIME(String fPRECHED_START_TIME) {
		FPRECHED_START_TIME = fPRECHED_START_TIME;
	}
	public String getFPRECHED_END_TIME() {
		return FPRECHED_END_TIME;
	}
	public void setFPRECHED_END_TIME(String fPRECHED_END_TIME) {
		FPRECHED_END_TIME = fPRECHED_END_TIME;
	}
	public String getFPRECHED_ISACTIVE() {
		return FPRECHED_ISACTIVE;
	}
	public void setFPRECHED_ISACTIVE(String fPRECHED_ISACTIVE) {
		FPRECHED_ISACTIVE = fPRECHED_ISACTIVE;
	}
	public String getFPRECHED_ISSYNCED() {
		return FPRECHED_ISSYNCED;
	}
	public void setFPRECHED_ISSYNCED(String fPRECHED_ISSYNCED) {
		FPRECHED_ISSYNCED = fPRECHED_ISSYNCED;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return FPRECHED_DEBCODE+" "+FPRECHED_REFNO;
	}
//	public String getFPRECHED_REFNO2() {
//		return FPRECHED_REFNO2;
//	}
//	public void setFPRECHED_REFNO2(String fPRECHED_REFNO2) {
//		FPRECHED_REFNO2 = fPRECHED_REFNO2;
//	}
	public String getFPRECHED_COSTCODE() {
		return FPRECHED_COSTCODE;
	}
	public void setFPRECHED_COSTCODE(String fPRECHED_COSTCODE) {
		FPRECHED_COSTCODE = fPRECHED_COSTCODE;
	}
	public String getFPRECHED_BANKCODE() {
		return FPRECHED_BANKCODE;
	}
	public void setFPRECHED_BANKCODE(String fPRECHED_BANKCODE) {
		FPRECHED_BANKCODE = fPRECHED_BANKCODE;
	}
	public String getFPRECHED_BRANCHCODE() {
		return FPRECHED_BRANCHCODE;
	}
	public void setFPRECHED_BRANCHCODE(String fPRECHED_BRANCHCODE) {
		FPRECHED_BRANCHCODE = fPRECHED_BRANCHCODE;
	}

}
