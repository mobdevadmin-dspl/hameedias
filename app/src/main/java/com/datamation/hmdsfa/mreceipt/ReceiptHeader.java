package com.datamation.hmdsfa.mreceipt;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.OutstandingController;
import com.datamation.hmdsfa.controller.ReceiptController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.helpers.ReceiptResponseListener;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.ReceiptHed;
import com.datamation.hmdsfa.settings.ReferenceNum;
import com.datamation.hmdsfa.view.ReceiptActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ReceiptHeader extends Fragment {

    View view;
    TextView customerName, outStandingAmt, txtCompDisc;
    //EditText InvoiceNo, currnentDate, manual, remarks, txtReceAmt, txtCHQNO, txtCardNo, txtSlipNo, txtDraftNo;
    EditText InvoiceNo, currnentDate, manual, remarks;
    //TextView txtCHQDate, txtRecExpireDate;
    //TableRow chequeRow, cardRow, cardTypeRow, exDateRow, chequeNoRow, bankRow, depositRow, draftRow;
    //Spinner spnPayMode, spnBank, spnCardType;
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
//    String RefNo, payModePos;
    String RefNo;
    SharedPref mSharedPref;
    ReceiptActivity mainActivity;
    MyReceiver r;
    FloatingActionButton fb;
    ReceiptResponseListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_multiple_receipt_header, container, false);
        mainActivity = (ReceiptActivity) getActivity();
        localSP = getActivity().getSharedPreferences(SETTINGS, 0);
        mSharedPref = new SharedPref(getActivity());
        customerName = (TextView) view.findViewById(R.id.customerName);
        outStandingAmt = (TextView) view.findViewById(R.id.rec_outstanding_amt);
        InvoiceNo = (EditText) view.findViewById(R.id.txtRecNo);
        currnentDate = (EditText) view.findViewById(R.id.txtRecDate);
        manual = (EditText) view.findViewById(R.id.txtRecManualNo);
        remarks = (EditText) view.findViewById(R.id.txtRecRemarks);
        fb = (FloatingActionButton) view.findViewById(R.id.fab1);

        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.ReceiptNumVal));

        customerName.setText(new CustomerController(getActivity()).getCusNameByCode(mSharedPref.getSelectedDebCode()));
        outStandingAmt.setText(String.format("%,.2f", new OutstandingController(getActivity()).getDebtorBalance(SharedPref.getInstance(getActivity()).getSelectedDebCode())));
        manual.setEnabled(true);
        remarks.setEnabled(true);

        currentDate();
        InvoiceNo.setText(RefNo);

        fb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                SaveReceiptHeader();
                listener.moveToFragments(1);
            }
        });

        ReceiptHed recHed = new ReceiptController(getActivity()).getActiveRecHed();
        if (new ReceiptController(getActivity()).isAnyActiveRecHed())
        {
            customerName.setText(new CustomerController(getActivity()).getCusNameByCode(recHed.getFPRECHED_DEBCODE()));
            outStandingAmt.setText(String.format("%,.2f", new OutstandingController(getActivity()).getDebtorBalance(recHed.getFPRECHED_DEBCODE())));
            manual.setEnabled(true);
            remarks.setEnabled(true);

        }

        return view;
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    /* current date */
    private void currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        currnentDate.setText(dateFormat.format(date));
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    /* Current time */
    private String currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(cal.getTime());
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void SaveReceiptHeader() {

        ReceiptActivity activity = (ReceiptActivity) getActivity();
        mSharedPref.setGlobalVal("ReckeyRemnant", "0");

        new OutstandingController(getActivity()).ClearFddbNoteData();
        ReceiptHed recHed = new ReceiptHed();


        recHed.setFPRECHED_REFNO(RefNo);
        recHed.setFPRECHED_DEBCODE(mSharedPref.getSelectedDebCode());
        recHed.setFPRECHED_ADDDATE(currnentDate.getText().toString());
        recHed.setFPRECHED_MANUREF(manual.getText().toString());
        recHed.setFPRECHED_REMARKS(remarks.getText().toString());
        recHed.setFPRECHED_ADDMACH(localSP.getString("MAC_Address", "No MAC Address").toString());
        recHed.setFPRECHED_ADDUSER(new SalRepController(getActivity()).getCurrentRepCode().trim());
        recHed.setFPRECHED_CURCODE("LKR");
        recHed.setFPRECHED_CURRATE("1.00");
        recHed.setFPRECHED_COSTCODE("1.00");
        recHed.setFPRECHED_ISACTIVE("1");
        recHed.setFPRECHED_ISDELETE("0");
        recHed.setFPRECHED_ISSYNCED("0");
        recHed.setFPRECHED_REPCODE(new SalRepController(getActivity()).getCurrentRepCode().trim());
        recHed.setFPRECHED_TXNDATE(currnentDate.getText().toString());
        recHed.setFPRECHED_TXNTYPE("42");
        //recHed.setFPRECHED_TOTALAMT(String.valueOf(Double.parseDouble(txtReceAmt.getText().toString().replaceAll(",", ""))));
        //recHed.setFPRECHED_BTOTALAMT(String.valueOf(Double.parseDouble(txtReceAmt.getText().toString().replaceAll(",", ""))));
        recHed.setFPRECHED_SALEREFNO("");

//
        //activity.ReceivedAmt = Double.parseDouble(txtReceAmt.getText().toString().replace(",", ""));
        SharedPref.getInstance(getActivity()).setGlobalVal("Rec_Start_Time", currentTime());
        ArrayList<ReceiptHed> RecHedList = new ArrayList<ReceiptHed>();
        RecHedList.add(recHed);

        new ReceiptController(getActivity()).createOrUpdateRecHedS(RecHedList);
    }

    /*------------------------------------------------------------------------------------------*/
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ReceiptHeader.this.mRefreshHeader();
        }
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

   	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_HEADER"));
    }

	/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mRefreshHeader() {

    ReceiptHed recHed = new ReceiptController(getActivity()).getActiveRecHed();

        if(new ReceiptController(getActivity()).isAnyActiveRecHed()){
            customerName.setText(new CustomerController(getActivity()).getCusNameByCode(recHed.getFPRECHED_DEBCODE()));
            outStandingAmt.setText(String.format("%,.2f", new OutstandingController(getActivity()).getDebtorBalance(recHed.getFPRECHED_DEBCODE())));
            manual.setText(recHed.getFPRECHED_MANUREF());
            remarks.setText(recHed.getFPRECHED_REMARKS());
//            customerName.setText(new CustomerController(getActivity()).getCusNameByCode(mSharedPref.getSelectedDebCode()));
//            outStandingAmt.setText(String.format("%,.2f", new OutstandingController(getActivity()).getDebtorBalance(mSharedPref.getSelectedDebCode())));
//            manual.setEnabled(true);
//            remarks.setEnabled(true);
        }
        else{
            customerName.setText(new CustomerController(getActivity()).getCusNameByCode(mSharedPref.getSelectedDebCode()));
            outStandingAmt.setText(String.format("%,.2f", new OutstandingController(getActivity()).getDebtorBalance(mSharedPref.getSelectedDebCode())));
            manual.setEnabled(true);
            remarks.setEnabled(true);

            InvoiceNo.setText(new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.ReceiptNumVal)));
            SaveReceiptHeader();
        }

    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (ReceiptResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }
}
