package com.datamation.swdsfa.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CashPayment implements Serializable {

    private long invoiceId;
    private int outletId;
    private long paymentTime;
    private double paymentAmount;
    private boolean isSynced;


    public CashPayment() {}

    public CashPayment(long paymentTime, double paymentAmount) {
        this.paymentTime = paymentTime;
        this.paymentAmount = paymentAmount;
    }

    public long getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(long paymentTime) {
        this.paymentTime = paymentTime;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean isSynced) {
        this.isSynced = isSynced;
    }



    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }

    public static CashPayment parseCashPayment(JSONObject instance) throws JSONException {

        if(instance != null) {
            CashPayment cashPayment = new CashPayment();
            cashPayment.setPaymentAmount(instance.getDouble("pm_amount"));

            try {
                // Parse date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
//                String tempVar = instance.getString("i_date") + " " + instance.getString("i_time");
                Date convertedDate = sdf.parse(instance.getString("i_date") + " 12:00:00");
                cashPayment.setPaymentTime(convertedDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return cashPayment;

        }

        return null;
    }

    public JSONObject getCashPaymentAsJSON() {

//        "check_branch_id": "0",
//                "check_amount": "0.0",
//                "check_datetime": "0",
//                "cash_amount": "24999.8",
//                "check_bank_id": "0",
//                "order_id": -757541071,
//                "check_no": "null",
//                "cash_datetime": "1427181400343"

        Map<String, Object> map = new HashMap<>();

        map.put("order_id", String.valueOf(invoiceId));
        map.put("check_amount", "null");
        map.put("check_datetime", "null");
        map.put("cash_amount", paymentAmount);
        map.put("check_bank_id", "null");
        map.put("check_branch_id", "null");
        map.put("check_no", "null");
        map.put("cash_datetime", paymentTime);
        map.put("check_receive_datetime", "null");

        return new JSONObject(map);
    }

}
