package com.datamation.swdsfa.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class OrderDetail implements Serializable {
    private String FORDERDET_ID;
    private String FORDERDET_REFNO;
    private String FORDERDET_TXNDATE;
    private String FORDERDET_LOCCODE;
    private String FORDERDET_TXNTYPE;
    private String FORDERDET_SEQNO;
    private String FORDERDET_ITEMCODE;
    private String FORDERDET_QTY;
    private String FORDERDET_COSTPRICE;
    private String FORDERDET_AMT;
    private String FORDERDET_BALQTY;
    private String FORDERDET_TAXCOMCODE;
    private String FORDERDET_BSELLPRICE;
    private String FORDERDET_SELLPRICE;
    private String FORDERDET_BTSELLPRICE;
    private String FORDERDET_TSELLPRICE;
    private String FORDERDET_PRILCODE;
    private String FORDERDET_SCHDISPER;
    private String FORDERDET_BDISAMT;
    private String FORDERDET_DISAMT;
    private String FORDERDET_DIS_VAL_AMT;
    private String FORDERDET_PDISAMT;
    private String FORDERDET_BPDISAMT;
    private String FORDERDET_BTAXAMT;
    private String FORDERDET_TAXAMT;
    private String FORDERDET_BAMT;
    private String FORDERDET_LINKREC;
    private String FORDERDET_RECORDID;
    private String FORDERDET_TIMESTAMP_COLUMN;
    private String FORDERDET_PICE_QTY;
    private String FORDERDET_IS_ACTIVE;
    private String FORDERDET_IS_SYNCED;
    private String FORDERDET_QOH;
    private String FORDERDET_DISC_REF;
    private String FORDERDET_COMP_DISC;
    private String FORDERDET_BRAND_DISC;
    private boolean FLAG;
    private String FORDERDET_TYPE;
    private String FORDERDET_FREEQTY;
    private String FORDERDET_COMP_DISPER;
    private String FORDERDET_BRAND_DISPER;
    private String FORDERDET_DISPER;
    private String FORDERDET_SCHDISC;
    private String FORDERDET_PRICE;
    private String FORDERDET_DISCTYPE;
    private String FORDERDET_QTY_SLAB_DISC;
    private String FORDERDET_ORG_PRICE;
    private String FORDERDET_DISFLAG;
    private String FORDERDET_ITEMNAME;
    private String FORDERDET_CASES;
    private String FORDERDET_REACODE;
    private boolean assortItem;
    private int assortQty;

    public String getFORDERDET_DIS_VAL_AMT() {
        return FORDERDET_DIS_VAL_AMT;
    }

    public void setFORDERDET_DIS_VAL_AMT(String FORDERDET_DIS_VAL_AMT) {
        this.FORDERDET_DIS_VAL_AMT = FORDERDET_DIS_VAL_AMT;
    }

    public String getFORDERDET_REACODE() {
        return FORDERDET_REACODE;
    }

    public void setFORDERDET_REACODE(String FORDERDET_REACODE) {
        this.FORDERDET_REACODE = FORDERDET_REACODE;
    }

    public int getAssortQty() {
        return assortQty;
    }

    public void setAssortQty(int assortQty) {
        this.assortQty = assortQty;
    }

    public boolean isAssortItem() {
        return assortItem;
    }

    public void setAssortItem(boolean assortItem) {
        this.assortItem = assortItem;
    }

    public String getFORDERDET_CASES() {
        return FORDERDET_CASES;
    }

    public void setFORDERDET_CASES(String FORDERDET_CASES) {
        this.FORDERDET_CASES = FORDERDET_CASES;
    }

    public String getFORDERDET_ITEMNAME() {
        return FORDERDET_ITEMNAME;
    }

    public void setFORDERDET_ITEMNAME(String FORDERDET_ITEMNAME) {
        this.FORDERDET_ITEMNAME = FORDERDET_ITEMNAME;
    }

    public String getFORDERDET_ID() {
        return FORDERDET_ID;
    }

    public void setFORDERDET_ID(String FORDERDET_ID) {
        this.FORDERDET_ID = FORDERDET_ID;
    }

    public String getFORDERDET_REFNO() {
        return FORDERDET_REFNO;
    }

    public void setFORDERDET_REFNO(String FORDERDET_REFNO) {
        this.FORDERDET_REFNO = FORDERDET_REFNO;
    }

    public String getFORDERDET_TXNDATE() {
        return FORDERDET_TXNDATE;
    }

    public void setFORDERDET_TXNDATE(String FORDERDET_TXNDATE) {
        this.FORDERDET_TXNDATE = FORDERDET_TXNDATE;
    }

    public String getFORDERDET_DISPER() {
        return FORDERDET_DISPER;
    }

    public void setFORDERDET_DISPER(String FORDERDET_DISPER) {
        this.FORDERDET_DISPER = FORDERDET_DISPER;
    }

    public String getFORDERDET_LOCCODE() {
        return FORDERDET_LOCCODE;
    }

    public void setFORDERDET_LOCCODE(String FORDERDET_LOCCODE) {
        this.FORDERDET_LOCCODE = FORDERDET_LOCCODE;
    }

    public String getFORDERDET_TXNTYPE() {
        return FORDERDET_TXNTYPE;
    }

    public void setFORDERDET_TXNTYPE(String FORDERDET_TXNTYPE) {
        this.FORDERDET_TXNTYPE = FORDERDET_TXNTYPE;
    }

    public String getFORDERDET_SEQNO() {
        return FORDERDET_SEQNO;
    }

    public void setFORDERDET_SEQNO(String FORDERDET_SEQNO) {
        this.FORDERDET_SEQNO = FORDERDET_SEQNO;
    }

    public String getFORDERDET_ITEMCODE() {
        return FORDERDET_ITEMCODE;
    }

    public void setFORDERDET_ITEMCODE(String FORDERDET_ITEMCODE) {
        this.FORDERDET_ITEMCODE = FORDERDET_ITEMCODE;
    }

    public String getFORDERDET_QTY() {
        return FORDERDET_QTY;
    }

    public void setFORDERDET_QTY(String FORDERDET_QTY) {
        this.FORDERDET_QTY = FORDERDET_QTY;
    }

    public String getFORDERDET_COSTPRICE() {
        return FORDERDET_COSTPRICE;
    }

    public void setFORDERDET_COSTPRICE(String FORDERDET_COSTPRICE) {
        this.FORDERDET_COSTPRICE = FORDERDET_COSTPRICE;
    }

    public String getFORDERDET_AMT() {
        return FORDERDET_AMT;
    }

    public void setFORDERDET_AMT(String FORDERDET_AMT) {
        this.FORDERDET_AMT = FORDERDET_AMT;
    }

    public String getFORDERDET_BALQTY() {
        return FORDERDET_BALQTY;
    }

    public void setFORDERDET_BALQTY(String FORDERDET_BALQTY) {
        this.FORDERDET_BALQTY = FORDERDET_BALQTY;
    }

    public String getFORDERDET_TAXCOMCODE() {
        return FORDERDET_TAXCOMCODE;
    }

    public void setFORDERDET_TAXCOMCODE(String FORDERDET_TAXCOMCODE) {
        this.FORDERDET_TAXCOMCODE = FORDERDET_TAXCOMCODE;
    }

    public String getFORDERDET_BSELLPRICE() {
        return FORDERDET_BSELLPRICE;
    }

    public void setFORDERDET_BSELLPRICE(String FORDERDET_BSELLPRICE) {
        this.FORDERDET_BSELLPRICE = FORDERDET_BSELLPRICE;
    }

    public String getFORDERDET_SELLPRICE() {
        return FORDERDET_SELLPRICE;
    }

    public void setFORDERDET_SELLPRICE(String FORDERDET_SELLPRICE) {
        this.FORDERDET_SELLPRICE = FORDERDET_SELLPRICE;
    }

    public String getFORDERDET_BTSELLPRICE() {
        return FORDERDET_BTSELLPRICE;
    }

    public void setFORDERDET_BTSELLPRICE(String FORDERDET_BTSELLPRICE) {
        this.FORDERDET_BTSELLPRICE = FORDERDET_BTSELLPRICE;
    }

    public String getFORDERDET_TSELLPRICE() {
        return FORDERDET_TSELLPRICE;
    }

    public void setFORDERDET_TSELLPRICE(String FORDERDET_TSELLPRICE) {
        this.FORDERDET_TSELLPRICE = FORDERDET_TSELLPRICE;
    }

    public String getFORDERDET_PRILCODE() {
        return FORDERDET_PRILCODE;
    }

    public void setFORDERDET_PRILCODE(String FORDERDET_PRILCODE) {
        this.FORDERDET_PRILCODE = FORDERDET_PRILCODE;
    }

    public String getFORDERDET_SCHDISPER() {
        return FORDERDET_SCHDISPER;
    }

    public void setFORDERDET_SCHDISPER(String FORDERDET_SCHDISPER) {
        this.FORDERDET_SCHDISPER = FORDERDET_SCHDISPER;
    }

    public String getFORDERDET_BDISAMT() {
        return FORDERDET_BDISAMT;
    }

    public void setFORDERDET_BDISAMT(String FORDERDET_BDISAMT) {
        this.FORDERDET_BDISAMT = FORDERDET_BDISAMT;
    }

    public String getFORDERDET_DISAMT() {
        return FORDERDET_DISAMT;
    }

    public void setFORDERDET_DISAMT(String FORDERDET_DISAMT) {
        this.FORDERDET_DISAMT = FORDERDET_DISAMT;
    }

    public String getFORDERDET_PDISAMT() {
        return FORDERDET_PDISAMT;
    }

    public void setFORDERDET_PDISAMT(String FORDERDET_PDISAMT) {
        this.FORDERDET_PDISAMT = FORDERDET_PDISAMT;
    }

    public String getFORDERDET_BPDISAMT() {
        return FORDERDET_BPDISAMT;
    }

    public void setFORDERDET_BPDISAMT(String FORDERDET_BPDISAMT) {
        this.FORDERDET_BPDISAMT = FORDERDET_BPDISAMT;
    }

    public String getFORDERDET_BTAXAMT() {
        return FORDERDET_BTAXAMT;
    }

    public void setFORDERDET_BTAXAMT(String FORDERDET_BTAXAMT) {
        this.FORDERDET_BTAXAMT = FORDERDET_BTAXAMT;
    }

    public String getFORDERDET_TAXAMT() {
        return FORDERDET_TAXAMT;
    }

    public void setFORDERDET_TAXAMT(String FORDERDET_TAXAMT) {
        this.FORDERDET_TAXAMT = FORDERDET_TAXAMT;
    }

    public String getFORDERDET_BAMT() {
        return FORDERDET_BAMT;
    }

    public void setFORDERDET_BAMT(String FORDERDET_BAMT) {
        this.FORDERDET_BAMT = FORDERDET_BAMT;
    }

    public String getFORDERDET_LINKREC() {
        return FORDERDET_LINKREC;
    }

    public void setFORDERDET_LINKREC(String FORDERDET_LINKREC) {
        this.FORDERDET_LINKREC = FORDERDET_LINKREC;
    }

    public String getFORDERDET_RECORDID() {
        return FORDERDET_RECORDID;
    }

    public void setFORDERDET_RECORDID(String FORDERDET_RECORDID) {
        this.FORDERDET_RECORDID = FORDERDET_RECORDID;
    }

    public String getFORDERDET_TIMESTAMP_COLUMN() {
        return FORDERDET_TIMESTAMP_COLUMN;
    }

    public void setFORDERDET_TIMESTAMP_COLUMN(String FORDERDET_TIMESTAMP_COLUMN) {
        this.FORDERDET_TIMESTAMP_COLUMN = FORDERDET_TIMESTAMP_COLUMN;
    }

    public String getFORDERDET_PICE_QTY() {
        return FORDERDET_PICE_QTY;
    }

    public void setFORDERDET_PICE_QTY(String FORDERDET_PICE_QTY) {
        this.FORDERDET_PICE_QTY = FORDERDET_PICE_QTY;
    }

    public String getFORDERDET_IS_ACTIVE() {
        return FORDERDET_IS_ACTIVE;
    }

    public void setFORDERDET_IS_ACTIVE(String FORDERDET_IS_ACTIVE) {
        this.FORDERDET_IS_ACTIVE = FORDERDET_IS_ACTIVE;
    }

    public String getFORDERDET_IS_SYNCED() {
        return FORDERDET_IS_SYNCED;
    }

    public void setFORDERDET_IS_SYNCED(String FORDERDET_IS_SYNCED) {
        this.FORDERDET_IS_SYNCED = FORDERDET_IS_SYNCED;
    }

    public String getFORDERDET_QOH() {
        return FORDERDET_QOH;
    }

    public void setFORDERDET_QOH(String FORDERDET_QOH) {
        this.FORDERDET_QOH = FORDERDET_QOH;
    }

    public String getFORDERDET_DISC_REF() {
        return FORDERDET_DISC_REF;
    }

    public void setFORDERDET_DISC_REF(String FORDERDET_DISC_REF) {
        this.FORDERDET_DISC_REF = FORDERDET_DISC_REF;
    }

    public String getFORDERDET_COMP_DISC() {
        return FORDERDET_COMP_DISC;
    }

    public void setFORDERDET_COMP_DISC(String FORDERDET_COMP_DISC) {
        this.FORDERDET_COMP_DISC = FORDERDET_COMP_DISC;
    }

    public String getFORDERDET_BRAND_DISC() {
        return FORDERDET_BRAND_DISC;
    }

    public void setFORDERDET_BRAND_DISC(String FORDERDET_BRAND_DISC) {
        this.FORDERDET_BRAND_DISC = FORDERDET_BRAND_DISC;
    }

    public boolean isFLAG() {
        return FLAG;
    }

    public void setFLAG(boolean FLAG) {
        this.FLAG = FLAG;
    }

    public String getFORDERDET_TYPE() {
        return FORDERDET_TYPE;
    }

    public void setFORDERDET_TYPE(String FORDERDET_TYPE) {
        this.FORDERDET_TYPE = FORDERDET_TYPE;
    }

    public String getFORDERDET_FREEQTY() {
        return FORDERDET_FREEQTY;
    }

    public void setFORDERDET_FREEQTY(String FORDERDET_FREEQTY) {
        this.FORDERDET_FREEQTY = FORDERDET_FREEQTY;
    }

    public String getFORDERDET_COMP_DISPER() {
        return FORDERDET_COMP_DISPER;
    }

    public void setFORDERDET_COMP_DISPER(String FORDERDET_COMP_DISPER) {
        this.FORDERDET_COMP_DISPER = FORDERDET_COMP_DISPER;
    }

    public String getFORDERDET_BRAND_DISPER() {
        return FORDERDET_BRAND_DISPER;
    }

    public void setFORDERDET_BRAND_DISPER(String FORDERDET_BRAND_DISPER) {
        this.FORDERDET_BRAND_DISPER = FORDERDET_BRAND_DISPER;
    }

    public String getFORDERDET_SCHDISC() {
        return FORDERDET_SCHDISC;
    }

    public void setFORDERDET_SCHDISC(String FORDERDET_SCHDISC) {
        this.FORDERDET_SCHDISC = FORDERDET_SCHDISC;
    }

    public String getFORDERDET_PRICE() {
        return FORDERDET_PRICE;
    }

    public void setFORDERDET_PRICE(String FORDERDET_PRICE) {
        this.FORDERDET_PRICE = FORDERDET_PRICE;
    }

    public String getFORDERDET_DISCTYPE() {
        return FORDERDET_DISCTYPE;
    }

    public void setFORDERDET_DISCTYPE(String FORDERDET_DISCTYPE) {
        this.FORDERDET_DISCTYPE = FORDERDET_DISCTYPE;
    }

    public String getFORDERDET_QTY_SLAB_DISC() {
        return FORDERDET_QTY_SLAB_DISC;
    }

    public void setFORDERDET_QTY_SLAB_DISC(String FORDERDET_QTY_SLAB_DISC) {
        this.FORDERDET_QTY_SLAB_DISC = FORDERDET_QTY_SLAB_DISC;
    }

    public String getFORDERDET_ORG_PRICE() {
        return FORDERDET_ORG_PRICE;
    }

    public void setFORDERDET_ORG_PRICE(String FORDERDET_ORG_PRICE) {
        this.FORDERDET_ORG_PRICE = FORDERDET_ORG_PRICE;
    }

    public String getFORDERDET_DISFLAG() {
        return FORDERDET_DISFLAG;
    }

    public void setFORDERDET_DISFLAG(String FORDERDET_DISFLAG) {
        this.FORDERDET_DISFLAG = FORDERDET_DISFLAG;
    }


    @Override
    public String toString() {
        return "OrderDetail{" +
                "FORDERDET_REFNO='" + FORDERDET_REFNO + '\'' +
                ", FORDERDET_QTY='" + FORDERDET_QTY + '\'' +
                ", FORDERDET_AMT='" + FORDERDET_AMT + '\'' +
                ", FORDERDET_REACODE='" + FORDERDET_REACODE + '\'' +
                '}';
    }
}
