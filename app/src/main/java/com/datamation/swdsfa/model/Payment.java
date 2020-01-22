package com.datamation.swdsfa.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Payment implements Serializable {

    private long orderId;
    private CashPayment cashPayment = new CashPayment();
    private Cheque cheque = new Cheque();
    private boolean cash,cheq;

    public Payment() {
    }

    public Payment(CashPayment cashPayment, long orderId) {
        this.cashPayment = cashPayment;
        this.orderId = orderId;
        cash=true;
        cheq=false;
    }

    public Payment(long orderId, Cheque cheque) {
        this.orderId = orderId;
        this.cheque = cheque;
        cheq =true;
        cash=false;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public CashPayment getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(CashPayment cashPayment) {
        this.cashPayment = cashPayment;
    }

    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    public JSONObject getPaymentAsJSON() {
        Map<String, Object> map = new HashMap<>();

        map.put("order_id", orderId);

        if(isCash()) {
            map.put("cash_amount", String.valueOf(cashPayment.getPaymentAmount()));
            map.put("cash_datetime", String.valueOf(cashPayment.getPaymentTime()));
        } else {
            map.put("check_amount", String.valueOf(cheque.getAmount()));
            map.put("check_datetime", String.valueOf(cheque.getChequeDate()));
            map.put("check_no", String.valueOf(cheque.getChequeNo()));
            map.put("check_bank_id", String.valueOf(cheque.getBankId()));
            map.put("check_branch_id", String.valueOf(cheque.getBranchid()));
        }

        return new JSONObject(map);
    }

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }

    public boolean isCheq() {
        return cheq;
    }

    public void setCheq(boolean cheq) {
        this.cheq = cheq;
    }
}
