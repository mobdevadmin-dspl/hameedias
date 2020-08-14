package com.datamation.hmdsfa.view.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.ReceiptController;
import com.datamation.hmdsfa.controller.ReceiptDetController;
import com.datamation.hmdsfa.dialog.ReceiptPreviewAlertBox;
import com.datamation.hmdsfa.model.ReceiptDet;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Used to show the user a list of recorded payments.
 */
public class PaymentDetailsFragment extends Fragment  {

    private static final String LOG_TAG = PaymentDetailsFragment.class.getSimpleName();
    private List<ReceiptDet> pinHolders;
    private PaymentListAdapter adapter;
    private TextView tvDate;

    //    private DatabaseHandler dbHandler;
//

    //
//    private CalendarDatePickerDialog calendarDatePickerDialog;
    private int mYear, mMonth, mDay;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private NumberFormat numberFormat = NumberFormat.getInstance();

    //    private Calendar /*calendarBegin, calendarEnd, */nowCalendar;
    private long timeInMillis;

    private TextView tvGrossAmountTotal;
    private TextView tvNetAmountTotal;
    private TextView tvOutstandingAmountTotal;
    private TextView tvCashPaymentTotal;
    private TextView tvChequeAmountTotal;
    StickyListHeadersListView pinnedSectionListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_payment_details, container, false);

        timeInMillis = System.currentTimeMillis();

        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setGroupingUsed(true);

        tvDate = (TextView) rootView.findViewById(R.id.fragment_payment_details_select_date);

        tvGrossAmountTotal = (TextView) rootView.findViewById(R.id.item_payment_details_tv_gross_amount_total);
        tvNetAmountTotal = (TextView) rootView.findViewById(R.id.item_payment_details_tv_net_amount_total);
        tvOutstandingAmountTotal = (TextView) rootView.findViewById(R.id.item_payment_details_tv_outstanding_amount_total);
        tvCashPaymentTotal = (TextView) rootView.findViewById(R.id.item_payment_details_tv_cash_amount_total);
        tvChequeAmountTotal = (TextView) rootView.findViewById(R.id.item_payment_details_tv_cheque_amount_total);

        pinnedSectionListView = (StickyListHeadersListView) rootView.findViewById(R.id.fragment_payment_details_pslv);



        pinHolders = new ReceiptDetController(getActivity()).getTodayPayments();

        adapter = new PaymentListAdapter(getActivity(), pinHolders);
        pinnedSectionListView.setAdapter(adapter);

        tvDate.setText(dateFormat.format(new Date(timeInMillis)));


        return rootView;
    }

    public void refresh() {
           if (adapter != null) adapter.notifyDataSetChanged();
    }


    private static class HeaderViewHolder {
        TextView pinLabel;
    }

    private static class ViewHolder {
        TextView tvInvoiceDetails;
        TextView tvGrossAmount;
        TextView tvNetAmount;
        TextView tvOutstandingAmount;
        TextView tvCashPayment;
        TextView tvChequeAmount;
        ImageView deleteReceipt;
        ImageView printReceipt;
    }

    private class PaymentListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        private LayoutInflater inflater;
         private List<ReceiptDet> paymentPinHolders;

        private PaymentListAdapter(Context context, List<ReceiptDet> paymentPinHolders) {
            this.paymentPinHolders = paymentPinHolders;
            this.inflater = LayoutInflater.from(context);
        }

        @SuppressLint("InflateParams")
        @Override
        public View getHeaderView(int position, View view, ViewGroup viewGroup) {

            HeaderViewHolder headerViewHolder;
            if (view == null) {
                view = inflater.inflate(R.layout.item_payment_details_header, null, false);

                headerViewHolder = new HeaderViewHolder();
                // headerViewHolder.pinLabel = (TextView) view.findViewById(R.id.item_payment_details_tv_pin_txt);

                view.setTag(headerViewHolder);
            } else {
                headerViewHolder = (HeaderViewHolder) view.getTag();
            }



            return view;
        }

        @Override
        public long getHeaderId(int position) {
            return 0;
        }

//        @Override
//        public long getHeaderId(int position) {
//            if (paymentPinHolders != null) return paymentPinHolders.get(position).getType();
//            return 0;
//        }

        @Override
        public int getCount() {
            if (paymentPinHolders != null) return paymentPinHolders.size();
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_payment_details, null, false);

                viewHolder = new ViewHolder();
                viewHolder.tvInvoiceDetails = (TextView) convertView.findViewById(R.id.item_payment_details_tv_invoice);
                viewHolder.tvGrossAmount = (TextView) convertView.findViewById(R.id.item_payment_details_tv_gross_amount);
                viewHolder.tvNetAmount = (TextView) convertView.findViewById(R.id.item_payment_details_tv_net_amount);
                viewHolder.tvOutstandingAmount = (TextView) convertView.findViewById(R.id.item_payment_details_tv_outstanding_amount);
                viewHolder.tvCashPayment = (TextView) convertView.findViewById(R.id.item_payment_details_tv_cash_amount);
                viewHolder.tvChequeAmount = (TextView) convertView.findViewById(R.id.item_payment_details_tv_cheque_amount);
                viewHolder.deleteReceipt = (ImageView) convertView.findViewById(R.id.delete);
                viewHolder.printReceipt  = (ImageView) convertView.findViewById(R.id.print);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }




           // paymentPinHolders.get(position)

            if (paymentPinHolders != null) {


                viewHolder.tvInvoiceDetails.setText(paymentPinHolders.get(position).getFPRECDET_REFNO());
                viewHolder.tvGrossAmount.setText(paymentPinHolders.get(position).getFPRECDET_REFNO1());
//                viewHolder.tvNetAmount.setText(numberFormat.format(invoice.getTotalAmount()
//                        - invoice.getTotalDiscount()
//                        - invoice.getReturnAmount()));
            if(paymentPinHolders.get(position).getFPRECDET_AMT()!= null)
            viewHolder.tvOutstandingAmount.setText(numberFormat.format(Double.parseDouble(paymentPinHolders.get(position).getFPRECDET_AMT())));
            else
            viewHolder.tvOutstandingAmount.setText("0.0");

            viewHolder.tvNetAmount.setText(paymentPinHolders.get(position).getFPRECDET_TXNDATE());

                if(paymentPinHolders.get(position).getFPRECDET_AMT()!= null)
                    viewHolder.tvOutstandingAmount.setText(numberFormat.format(Double.parseDouble(paymentPinHolders.get(position).getFPRECDET_AMT())));
                else
                    viewHolder.tvOutstandingAmount.setText("0.0");
                if (paymentPinHolders.get(position).getFPRECDET_REPCODE().equals("CA")) {
                    if(paymentPinHolders.get(position).getFPRECDET_ALOAMT()!=null)
                        viewHolder.tvCashPayment.setText(numberFormat.format(Double.parseDouble(paymentPinHolders.get(position).getFPRECDET_ALOAMT())));
                    else
                        viewHolder.tvCashPayment.setText("0.0");
                } else {
                    viewHolder.tvCashPayment.setText("0.0");
                }

                if (paymentPinHolders.get(position).getFPRECDET_REPCODE().equals("CH")) {

                    if(paymentPinHolders.get(position).getFPRECDET_ALOAMT()!=null)
                        viewHolder.tvChequeAmount.setText(numberFormat.format(Double.parseDouble(paymentPinHolders.get(position).getFPRECDET_ALOAMT())));
                    else
                        viewHolder.tvChequeAmount.setText("0.0");



                } else {
                    viewHolder.tvChequeAmount.setText("0.0");
                }

                viewHolder.deleteReceipt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(paymentPinHolders.get(position).getFPRECDET_ISDELETE().equals("0")){

                            deleteReceipt(paymentPinHolders.get(position).getFPRECDET_REFNO());

                            refresh();
                        }else{
                            Toast.makeText(getActivity(),"Cannot delete synced receipts",Toast.LENGTH_LONG).show();

                        }

                    }
                });

                viewHolder.printReceipt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        printReceipt(paymentPinHolders.get(position).getFPRECDET_REFNO());
                    }
                });
            }

            return convertView;
        }

        public void setPaymentPinHolders(List<ReceiptDet> pinHolderList) {
            this.paymentPinHolders = pinHolderList;
            notifyDataSetChanged();
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();

            double grossAmount = 0;
            double netAmount = 0;
            double outstandingAmount = 0;
            double cashAmount = 0;
            double chequeAmount = 0;

            for(ReceiptDet pinHolder : paymentPinHolders) {


                    outstandingAmount += Double.parseDouble(pinHolder.getFPRECDET_AMT());
                    if(pinHolder.getFPRECDET_REPCODE().equals("CA")){
                        cashAmount += Double.parseDouble(pinHolder.getFPRECDET_ALOAMT());
                    }else {
                        chequeAmount += Double.parseDouble(pinHolder.getFPRECDET_ALOAMT());
                    }

            }

//            tvGrossAmountTotal.setText(numberFormat.format(grossAmount));
//            tvNetAmountTotal.setText(numberFormat.format(netAmount));
            tvOutstandingAmountTotal.setText(numberFormat.format(outstandingAmount));
            tvCashPaymentTotal.setText(numberFormat.format(cashAmount));
            tvChequeAmountTotal.setText(numberFormat.format(chequeAmount));

        }
    }
    public void deleteReceipt(final String RefNo) {

        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Do you want to delete this receipt ?")
                .positiveColor(ContextCompat.getColor(getActivity(), R.color.material_alert_positive_button))
                .positiveText("Yes")
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.material_alert_negative_button))
                .negativeText("No, Exit")
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);

                        int result = new ReceiptController(getActivity()).deleteReceipts(RefNo);

                        if (result>0) {
                            new ReceiptDetController(getActivity()).restData(RefNo);
                            pinHolders = new ReceiptDetController(getActivity()).getTodayPayments();

                            adapter = new PaymentListAdapter(getActivity(), pinHolders);
                            pinnedSectionListView.setAdapter(adapter);
                            Toast.makeText(getActivity(), "Receipt deleted successfully..!", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Receipt delete unsuccess..!", Toast.LENGTH_SHORT).show();
                        }



                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);

                        dialog.dismiss();


                    }
                })
                .build();
        materialDialog.setCanceledOnTouchOutside(false);
        materialDialog.show();
    }

    public void printReceipt(final String RefNo) {

        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Do you want to print this receipt ?")
                .positiveColor(ContextCompat.getColor(getActivity(), R.color.material_alert_positive_button))
                .positiveText("Yes")
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.material_alert_negative_button))
                .negativeText("No, Exit")
                .callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);

                        int a = new ReceiptPreviewAlertBox(getActivity()).PrintDetailsDialogbox(getActivity(), "Print preview", RefNo);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);

                        dialog.dismiss();


                    }
                })
                .build();
        materialDialog.setCanceledOnTouchOutside(false);
        materialDialog.show();
    }
}
