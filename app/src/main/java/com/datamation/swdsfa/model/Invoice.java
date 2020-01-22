package com.datamation.swdsfa.model;

import com.datamation.swdsfa.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Invoice implements Serializable {

    private int invoiceType;
    private long invoiceId, invoiceTime;
    private double totalAmount;
    private double totalDiscount;
    private double returnAmount;
    private List<Cheque> chequePayments;
    private List<CashPayment> cashPayments;
    private List<Batch> batches;
    private boolean hasFree;
    private double totalReturnChecks;

    public static final int UNPRODUCTIVE = 0;
    public static final int SALES_ORDER = 1;
    public static final int OPEN_BALANCE = 2;

    // TODO : Add location details to this as well.

    public Invoice() {}

    /**
     * Create an {@link Invoice} object without payments.
     *
     * @param invoiceId     The Invoice ID
     * @param invoiceTime   The time in millis
     * @param totalAmount   The total amount
     * @param totalDiscount The total discount amount
     * @param returnAmount  The total return amount
     * @param hasFree       TRUE if order has free
     */
    public Invoice(long invoiceId, long invoiceTime, double totalAmount, double totalDiscount,
                   int invoiceType, double returnAmount, boolean hasFree) {
        this.invoiceId = invoiceId;
        this.invoiceTime = invoiceTime;
        this.totalAmount = totalAmount;
        this.totalDiscount = totalDiscount;
        this.returnAmount = returnAmount;
        this.hasFree = hasFree;
        this.invoiceType = invoiceType;
    }

    /**
     * Create an {@link Invoice} object with payments.
     *
     * @param invoiceId      The Invoice ID
     * @param invoiceTime    The time in millis
     * @param totalAmount    The total amount
     * @param totalDiscount  The total discount amount
     * @param returnAmount   The total return amount
     * @param cashPayments   List of {@link CashPayment} objects
     * @param chequePayments List of {@link Cheque} objects
     * @param hasFree        TRUE if order has free
     */
    public Invoice(long invoiceId, long invoiceTime, double totalAmount, double totalDiscount, int invoiceType,
                   double returnAmount, List<CashPayment> cashPayments, List<Cheque> chequePayments,
                   boolean hasFree) {
        this.invoiceId = invoiceId;
        this.invoiceTime = invoiceTime;
        this.totalAmount = totalAmount;
        this.cashPayments = cashPayments;
        this.chequePayments = chequePayments;
        this.totalDiscount = totalDiscount;
        this.returnAmount = returnAmount;
        this.hasFree = hasFree;
        this.invoiceType = invoiceType;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(long invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public List<CashPayment> getCashPayments() {
        return cashPayments;
    }

    public void setCashPayments(List<CashPayment> cashPayments) {
        this.cashPayments = cashPayments;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

    public List<Cheque> getChequePayments() {
        return chequePayments;
    }

    public void setChequePayments(List<Cheque> chequePayments) {
        this.chequePayments = chequePayments;
    }

    public boolean hasFree() {
        return hasFree;
    }

    public void setHasFree(boolean hasFree) {
        this.hasFree = hasFree;
    }

    public void addChequePayment(Cheque cheque) {
        if (chequePayments == null) {
            chequePayments = new ArrayList<>();
        }
        chequePayments.add(cheque);
    }

    public void addCashPayment(CashPayment cashPayment) {
        if (cashPayments == null) {
            cashPayments = new ArrayList<>();
        }
        cashPayments.add(cashPayment);
    }

    public static Invoice parseInvoice(JSONObject instance) throws JSONException {

        if (instance != null) {
            Invoice invoice = new Invoice();
            invoice.setInvoiceId(instance.getLong("id_invoice"));

            String sales_order_type = instance.getString("sales_order_type");

            switch (sales_order_type) {
                case "SO": {
                    invoice.setInvoiceType(Invoice.SALES_ORDER);
                    break;
                }
                case "OPBL": {
                    invoice.setInvoiceType(Invoice.OPEN_BALANCE);
                    break;
                }
                case "UNP": {
                    invoice.setInvoiceType(Invoice.UNPRODUCTIVE);
                    break;
                }
                default: {
                    invoice.setInvoiceType(SALES_ORDER);
                }
            }

            long extractedTime = TimeUtils.extractTimeFromInvoiceId(invoice.getInvoiceId());

            if(extractedTime > 0) {
                invoice.setInvoiceTime(extractedTime);
            } else {
                if(instance.has("added_date") && instance.getString("added_date").length() > 0) {
                    // Added date available.
                    String date = instance.getString("added_date");

                    if(instance.has("added_time") && instance.getString("added_time").length() > 0) {
                        // Added time also available.
                        String time = instance.getString("added_time");

                        try {
                            // Parse date
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            Date convertedDate = sdf.parse(date + " " + time);
                            //need to get both date and time for an example,
                            //added_date = "2015-06-17"
                            //added_time = "16:25:45"
                            //and then retrieve this date and time value as a long value
                            invoice.setInvoiceTime(convertedDate.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            invoice.setInvoiceTime(System.currentTimeMillis());
                        }
                    } else {
                        // Added time not available.

                        try {
                            // Parse date
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            Date convertedDate = sdf.parse(date);
                            //need to get both date and time for an example,
                            //added_date = "2015-06-17"
                            //added_time = "16:25:45"
                            //and then retrieve this date and time value as a long value
                            invoice.setInvoiceTime(convertedDate.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            invoice.setInvoiceTime(System.currentTimeMillis());
                        }
                    }

                }
            }

            invoice.setTotalAmount(instance.getDouble("gross_amount"));
            invoice.setTotalDiscount(instance.getDouble("discount"));

            invoice.setReturnAmount(instance.getDouble("sales_return") + instance.getDouble("market_return"));

//            List<CashPayment> cashPaymentList = new ArrayList<CashPayment>();
//            List<Cheque> chequeList = new ArrayList<Cheque>();

            if (instance.getDouble("total_cash") > 0) {
                CashPayment cashPayment = new CashPayment(invoice.getInvoiceTime(), instance.getDouble("total_cash"));
                invoice.addCashPayment(cashPayment);
            }
            JSONArray cheqs = instance.getJSONArray("cheques");
            if (cheqs.length()>0) {
                for(int i=0;i<cheqs.length();i++){
//                    Cheque cheque = Cheque.parseCheque((JSONObject) cheqs.get(i));
                    JSONObject obj = (JSONObject) cheqs.get(i);
                    String  chq_no = obj.getString("cheque_no");

                    int chq_stetus = 0;
                    if(obj.get("cheque_status").equals("pd")){
                        chq_stetus = 0;
                    }
                    else if(obj.get("cheque_status").equals("realized")){
                        chq_stetus = 1;
                    }
                    else{
                        chq_stetus=2;
                    }
                    Cheque cheque = new Cheque(chq_no, invoice.getInvoiceTime(), invoice.getInvoiceTime(),obj.getDouble("amount"),obj.getInt("id_bank") , 1,chq_stetus);
                    invoice.addChequePayment(cheque);
                }

            }
            invoice.setTotalReturnChecks(instance.getDouble("total_return_cheque"));

            try {
                invoice.setHasFree(instance.getInt("has_free") == 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            JSONArray batch = instance.getJSONArray("batch");
            ArrayList<Batch> batches = new ArrayList<>();
            if(batch.length()>0){
                for (int i=0;i<batch.length();i++) {
                    Batch tmp = new Batch();
                    tmp.setProduct_id(batch.getJSONObject(i).getInt("product_id"));
                    tmp.setPrice(batch.getJSONObject(i).getDouble("pice_price"));
                    tmp.setQty(batch.getJSONObject(i).getInt("qty"));
                    tmp.setBatchId(batch.getJSONObject(i).getInt("batch_id"));
                    tmp.setBatchNo(batch.getJSONObject(i).getString("batch_no"));
                    batches.add(tmp);
                }
                invoice.setBatches(batches);
            }

//            for(int i=0; i<instance.getJSONArray("cash").length(); i++) {
//                cashPaymentList.add(CashPayment.parseCashPayment(instance.getJSONArray("cash").getJSONObject(i)));
//            }
//
//            for(int i=0; i<instance.getJSONArray("cheque").length(); i++) {
//                chequeList.add(Cheque.parseCheque(instance.getJSONArray("cheque").getJSONObject(i)));
//            }

//            invoice.setCashPayments(cashPaymentList);
//            invoice.setChequePayments(chequeList);

            return invoice;
        }

        return null;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Invoice && (object.hashCode() == this.hashCode() || ((Invoice) object).getInvoiceId() == this.getInvoiceId());
    }

    public boolean isOutstandingInvoice() {

        return getTotalPaidAmount() < totalAmount - totalDiscount - returnAmount;

    }

    public double getTotalPaidAmount() {
        double paid = 0;

        if (cashPayments != null) {
            for (CashPayment cashPayment : cashPayments) {
                paid += cashPayment.getPaymentAmount();
            }
        }

        if (chequePayments != null) {
            for (Cheque cheque : chequePayments) {
                paid += cheque.getAmount();
            }
        }

        return paid;
    }

    public double getTotalCashPayments() {
        double paid = 0;

        if (cashPayments != null) {
            for (CashPayment cashPayment : cashPayments) {
                paid += cashPayment.getPaymentAmount();
            }
        }

        return paid;
    }

    public double getTotalChequePayments() {
        double paid = 0;

        if (chequePayments != null) {
            for (Cheque cheque : chequePayments) {
                paid += cheque.getAmount();
            }
        }

        return paid;
    }

    public double getNetAmount() {
        return totalAmount - totalDiscount - returnAmount;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Invoice No : ").append(invoiceId).append("\n");
        sb.append("Invoice Time : ").append(invoiceTime).append("\n");
        sb.append("Invoice Total : ").append(totalAmount).append("\n");
        sb.append("Invoice Discount : ").append(totalDiscount).append("\n");

        sb.append("Cash Payment(s)").append("\n");

        if (cashPayments != null) {
            for (CashPayment cashPayment : cashPayments) {
                sb.append(cashPayment.getPaymentTime()).append("-").append(cashPayment.getPaymentAmount()).append("\n");
            }
        }

        if (chequePayments != null) {
            for (Cheque cheque : chequePayments) {
                sb.append(cheque.getChequeNo()).append("-").append(cheque.getAmount()).append("\n");
            }
        }

        return sb.toString();
    }

    public double getTotalReturnChecks() {
        if (chequePayments!=null) {
            totalReturnChecks=0;
            for (Cheque cheq:chequePayments
                    ) {
                if(cheq.getChequeStatus()==2){
                    totalReturnChecks+=cheq.getAmount();
                }
            }
        }
        return totalReturnChecks;
    }

    public void setTotalReturnChecks(double totalReturnChecks) {
        this.totalReturnChecks = totalReturnChecks;
    }

    public boolean hasReturnedCheques(){
        for(Cheque cheque:chequePayments){
            return cheque.getChequeStatus()==2;
        }
        return false;
    }

}
