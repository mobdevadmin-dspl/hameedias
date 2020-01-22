package com.datamation.swdsfa.model;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class HistoryDetail implements Serializable {

    private int outletId;
    private long date;
    private String remark;
    private Invoice invoice;

    private boolean selectedOnList;

    public HistoryDetail() {}

    /**
     * This is to create a history without an invoice. Basically an unproductive call.
     * Call this constructor and it will automatically set historyType to 0.
     * @param remark remark of the history item (Eg : Shop Closed, Owner Dead, etc.)
     */
    public HistoryDetail(int outletId, String remark, long date) {
        this.outletId = outletId;
        this.remark = remark;
        this.date = date;
    }
    public HistoryDetail(int outletId, Invoice invoice, String remark, long date) {
        this(outletId, remark, date);
        this.outletId = outletId;
        this.invoice = invoice;
        this.remark = remark;
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Nullable
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }

    public static HistoryDetail parseHistoryDetail(JSONObject instance) throws JSONException {

        // For now, we only consider invoices as Outlet history details.
        // Have to figure out some way to add return cheques, unproductive calls to it.
//        if(instance != null) {
//            Invoice inv = Invoice.parseInvoice(instance);
//            if(inv != null) {
//                HistoryDetail detail = new HistoryDetail();
//                detail.setInvoice(inv);
//                detail.setDate(inv.getInvoiceTime());
//
//                return detail;
//            }
//        }

        return null;
    }

    public boolean isSelectedOnList() {
        return selectedOnList;
    }

    public void setSelectedOnList(boolean selectedOnList) {
        this.selectedOnList = selectedOnList;
    }

//    public boolean isOutstandingHistory() {
//        return invoice != null && invoice.isOutstandingInvoice()/* && invoice.getInvoiceType() == Invoice.SALES_ORDER*/;
//    }

}
