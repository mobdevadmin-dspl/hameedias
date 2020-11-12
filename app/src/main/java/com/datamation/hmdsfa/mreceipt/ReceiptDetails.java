package com.datamation.hmdsfa.mreceipt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.PayModeAdapter;
import com.datamation.hmdsfa.adapter.ReceiptAdapter1;
import com.datamation.hmdsfa.controller.BankController;
import com.datamation.hmdsfa.controller.OutstandingController;
import com.datamation.hmdsfa.controller.PayModeController;
import com.datamation.hmdsfa.controller.PaymentAllocateController;
import com.datamation.hmdsfa.controller.ReceiptController;
import com.datamation.hmdsfa.controller.ReceiptDetController;
import com.datamation.hmdsfa.helpers.ReceiptResponseListener;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Bank;
import com.datamation.hmdsfa.model.FddbNote;
import com.datamation.hmdsfa.model.PayMode;
import com.datamation.hmdsfa.model.PaymentAllocate;
import com.datamation.hmdsfa.model.ReceiptDet;
import com.datamation.hmdsfa.model.ReceiptHed;
import com.datamation.hmdsfa.settings.ReferenceNum;
import com.datamation.hmdsfa.view.ReceiptActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ReceiptDetails extends Fragment {

    View view;
    Button btnUpdate, bCancel, btnPayMode, btnDone;
    int seqno = 0, index_id = 0;
    public static SharedPreferences localSP;
    public static final String SETTINGS = "SETTINGS";
    ListView lv_order_det, lv_pay_mode;
    ArrayList<FddbNote> orderList;
    ArrayList<PayMode>selectedPayList;
    FddbNote selectedInvoice;
    ArrayList<PayMode>payModeArrayList;
    SharedPref mSharedPref;
    String RefNo, RefNo1;
    FddbNote selectedItem;
    double ReceivedAmt;
    ReceiptActivity mainActivity;
    boolean isAllocated = false;
    MyReceiver r;
    //FloatingActionButton fb;
    String payModePos;
    String fddListRefNo, fddListTxnDate, fddListTotBal, fddListPaidAmt, fdListDays;
    SweetAlertDialog pDialog;
    ProgressDialog progressDialog;
    private PayModeAdapter payModeAdapter;
    ArrayList<PaymentAllocate>paymentAllocateArrayList, isPaymentAvailableList;
    ArrayList<FddbNote>finalFddbList;
    boolean isInvoiceSelected;
    ReceiptResponseListener listener;
    Double totalPaidAmt=0.0;
    int i=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_multiple_receipt_details, container, false);
        mSharedPref = new SharedPref(getActivity());

        seqno = 0;
        btnUpdate = (Button) view.findViewById(R.id.btn_add);
        bCancel = (Button) view.findViewById(R.id.btnCancel);
        lv_order_det = (ListView) view.findViewById(R.id.lv_order_det);
        lv_pay_mode = (ListView)view.findViewById(R.id.lv_paymode_det);
        //RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.RecNumVal));
        btnPayMode = (Button)view.findViewById(R.id.btn_add_pay_mode);
        btnDone = (Button)view.findViewById(R.id.btn_save);
        mainActivity = (ReceiptActivity) getActivity();
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.ReceiptNumVal));
        RefNo1 = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.RecNumValCom));
        isInvoiceSelected = false;

        btnPayMode.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v)
            {
                createPaymentModeDialog();
            }

        });

        bCancel.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                clearTextFields();
                lv_order_det.setEnabled(true);
                isInvoiceSelected = false;
                FetchData();
            }
        });

        // --------------------------------------- Nuwan ------ 07/09/2018 -----------------------------------------------------
        lv_order_det.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {

                view2.setBackgroundColor(getResources().getColor(R.color.blue_semi_transparent));
                isInvoiceSelected = true;
                FetchPayModeData();

                FddbNote fdDbNote = orderList.get(position);
                fddListRefNo = fdDbNote.getFDDBNOTE_REFNO();
                fddListTxnDate = fdDbNote.getFDDBNOTE_TXN_DATE();
                fddListTotBal = fdDbNote.getFDDBNOTE_TOT_BAL();
                fddListPaidAmt = fdDbNote.getFDDBNOTE_ENTER_AMT();
                Log.d("RECEIPT_ORDER_DETAILS", "SELECTED_ITEM_IS: " + fddListRefNo);

                btnUpdate.setEnabled(true);
                bCancel.setEnabled(true);

                lv_order_det.setEnabled(false);
                lv_pay_mode.setEnabled(true);
            }
        });

        btnUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                totalPaidAmt = Double.parseDouble(new PayModeController(getActivity()).getTotalAllocAmt());

                //if (totalPaidAmt>0.0&& Double.parseDouble(fddListTotBal)>=totalPaidAmt)
                // comment by Rashmi- for allow Over Payment
                if (totalPaidAmt > 0.0)
                {
                    isInvoiceSelected = false;

                    FddbNote fddbNote = new FddbNote();
                    fddbNote.setFDDBNOTE_REFNO(fddListRefNo);
                    fddbNote.setFDDBNOTE_TXN_DATE(fddListTxnDate);
                    fddbNote.setFDDBNOTE_TOT_BAL(fddListTotBal);
                    fddbNote.setFDDBNOTE_ENTER_AMT(String.valueOf(totalPaidAmt));

                    selectedPayList = new PayModeController(getActivity()).getPaidDetails();

                    if (selectedPayList.size()>0)
                    {
                        savePaymentAllocate(selectedPayList, fddbNote);
                    }
                }
                else
                {
                    clearTextFields();
                    lv_order_det.setEnabled(true);
                    FetchData();
                    Toast.makeText(getActivity(), "Please enter valid amount", Toast.LENGTH_LONG).show();
                }

                btnUpdate.setEnabled(false);
                bCancel.setEnabled(false);



//                if (emptycheck > 0) {
//
//                    dueamount = Double.parseDouble(selectedItem.getFDDBNOTE_TOT_BAL());
//                    int check = Double.compare(enteramount, dueamount);
//
//                    if (check == 0 || (!et_remark.getText().toString().equals(""))) {
//
//                        FDDbNote fdDbNote = new FDDbNote();
//                        fdDbNote.setFDDBNOTE_ADD_DATE(selectedItem.getFDDBNOTE_ADD_DATE());
//                        fdDbNote.setFDDBNOTE_ADD_MACH(selectedItem.getFDDBNOTE_ADD_MACH());
//                        fdDbNote.setFDDBNOTE_ADD_USER(selectedItem.getFDDBNOTE_ADD_USER());
//                        fdDbNote.setFDDBNOTE_AMT(selectedItem.getFDDBNOTE_AMT());
//                        fdDbNote.setFDDBNOTE_B_AMT(selectedItem.getFDDBNOTE_B_AMT());
//                        fdDbNote.setFDDBNOTE_B_TAX_AMT(selectedItem.getFDDBNOTE_B_TAX_AMT());
//                        fdDbNote.setFDDBNOTE_CR_ACC(selectedItem.getFDDBNOTE_CR_ACC());
//                        fdDbNote.setFDDBNOTE_CUR_CODE(selectedItem.getFDDBNOTE_CUR_CODE());
//                        fdDbNote.setFDDBNOTE_CUR_RATE(selectedItem.getFDDBNOTE_CUR_RATE());
//                        fdDbNote.setFDDBNOTE_DEB_CODE(selectedItem.getFDDBNOTE_DEB_CODE());
//
//                        if (Double.parseDouble(et_enterAmt.getText().toString().replaceAll(",", "")) > 0)
//                            fdDbNote.setFDDBNOTE_ENTER_AMT(String.format("%.2f", Double.parseDouble(et_enterAmt.getText().toString().replaceAll(",", ""))));
//                        else
//                            fdDbNote.setFDDBNOTE_ENTER_AMT("");
//
//                        fdDbNote.setFDDBNOTE_GL_BATCH(selectedItem.getFDDBNOTE_GL_BATCH());
//                        fdDbNote.setFDDBNOTE_GL_POST(selectedItem.getFDDBNOTE_GL_POST());
//                        fdDbNote.setFDDBNOTE_ID(index_id + "");
//                        fdDbNote.setFDDBNOTE_MANU_REF(selectedItem.getFDDBNOTE_MANU_REF());
//                        fdDbNote.setFDDBNOTE_OV_PAY_AMT(selectedItem.getFDDBNOTE_OV_PAY_AMT());
//                        fdDbNote.setFDDBNOTE_PRT_COPY(selectedItem.getFDDBNOTE_PRT_COPY());
//                        fdDbNote.setFDDBNOTE_RECORD_ID(selectedItem.getFDDBNOTE_PRT_COPY());
//                        fdDbNote.setFDDBNOTE_REF_INV(selectedItem.getFDDBNOTE_REF_INV());
//                        fdDbNote.setFDDBNOTE_REFNO(selectedItem.getFDDBNOTE_REFNO());
//                        fdDbNote.setFDDBNOTE_REFNO1(selectedItem.getFDDBNOTE_REFNO1());
//                        fdDbNote.setFDDBNOTE_REP_CODE(selectedItem.getFDDBNOTE_REP_CODE());
//                        fdDbNote.setFDDBNOTE_SALE_REF_NO(selectedItem.getFDDBNOTE_SALE_REF_NO());
//                        fdDbNote.setFDDBNOTE_TAX_AMT(selectedItem.getFDDBNOTE_TAX_AMT());
//                        fdDbNote.setFDDBNOTE_TAX_COM_CODE(selectedItem.getFDDBNOTE_TAX_COM_CODE());
//                        fdDbNote.setFDDBNOTE_TOT_BAL(selectedItem.getFDDBNOTE_TOT_BAL());
//                        fdDbNote.setFDDBNOTE_TOT_BAL1(selectedItem.getFDDBNOTE_TOT_BAL1());
//                        fdDbNote.setFDDBNOTE_TXN_DATE(selectedItem.getFDDBNOTE_TXN_DATE());
//                        fdDbNote.setFDDBNOTE_TXN_TYPE(selectedItem.getFDDBNOTE_TXN_TYPE());
//                        list.add(fdDbNote);
//
//                        new FDDbNoteDS(getActivity()).createOrUpdateFDDbNote(list);
//                        Toast.makeText(getActivity(), "Updated successfully !", Toast.LENGTH_SHORT).show();
//                        clearTextFields();
//                        lv_order_det.setEnabled(true);
//                        FetchData();
//                    } else {
//                        Toast.makeText(getActivity(), "Please Enter Remark !", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Please Enter Valid Enter Amount !", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        //FetchPayModeData();


//        lv_pay_mode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Context context = getActivity();
//                Toast.makeText(context, "item " + position + " was clicked", Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//        String size = String.valueOf(lv_pay_mode.getAdapter().getCount());
//        Log.d("RECEIPT_DETAILS", "LIST_SIZE_IS: " + size);

//        if (payModeAdapter.isAllocatedFinished())
//        {
//            btnUpdate.setEnabled(true);
//            bCancel.setEnabled(true);
//        }

//        btnUpdate.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (lv_pay_mode.getAdapter().getCount()==0)
//                {
//                    Toast.makeText(getActivity(),"Please add at least one payment Method.", Toast.LENGTH_LONG).show();
//                }
//                else
//                {
//                    View view1 = null;
//                    view1 = lv_pay_mode.getRootView();
//                    EditText alAmt = (EditText)view1.findViewById(R.id.editAlloAmount);
//                    TextView pay = (TextView)view1.findViewById(R.id.txtAmount);
//                    if (Double.parseDouble(alAmt.getText().toString())==0)
//                    {
//                        Toast.makeText(getActivity(), "Please enter allocated amount", Toast.LENGTH_LONG).show();
//                    }
//                    else if (Double.parseDouble(pay.getText().toString())>= Double.parseDouble(alAmt.getText().toString()))
//                    {
//                        Toast.makeText(getActivity(), "You can update the table", Toast.LENGTH_LONG).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(getActivity(), "You can't update the table", Toast.LENGTH_LONG).show();
//                        alAmt.setText("");
//                    }
//                }
//            }
//        });
//

//        lv_pay_mode.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                view.setBackgroundColor(getResources().getColor(R.color.blue_semi_transparent));
//            }
//        });

//        lv_pay_mode.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
//            {
//                view.setBackgroundColor(getResources().getColor(R.color.blue_semi_transparent));
//            }
//        });

        isPaymentAvailableList = new PaymentAllocateController(getActivity()).getPaidRecordsByCommonRef(RefNo1);
        if (isPaymentAvailableList.size()>0)
        {
            btnDone.setEnabled(true);
        }

        btnDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //btnDone.setEnabled(false);

//                paymentAllocateArrayList = new ArrayList<>();
                paymentAllocateArrayList = new PaymentAllocateController(getActivity()).getPaidAllRecordsByCommonRef(RefNo1);

                if (paymentAllocateArrayList.size()>0)
                {
                    saveRecDet(paymentAllocateArrayList);
                }
            }
        });

        return view;
    }


    public void savePaymentAllocate(ArrayList<PayMode>payModeArrayList, FddbNote fdDbNote)
    {
        if (payModeArrayList.size()>0)
        {
            ArrayList<PaymentAllocate>paymentAllocateArrayList = new ArrayList<>();

            Double remTotBal = Double.parseDouble(fdDbNote.getFDDBNOTE_TOT_BAL());
            Double remTotBalFromAlloc = 0.00;
            Double cuAllocateAmt ,TotAllowAmt, RemainAmt = 0.00;
            String Allo_ID="";
            for (PayMode payMode : payModeArrayList )
            {
                PaymentAllocate paymentAllocate = new PaymentAllocate();
//                RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.RecNumVal));
//                RefNo = new ReferenceNum(getActivity()).getLoopRefNo(getResources().getString(R.string.RecNumVal), i);
                remTotBalFromAlloc = new PaymentAllocateController(getActivity()).getFDDTotBalAmtByAllRefNos(fdDbNote.getFDDBNOTE_REFNO(),payMode.getFPAYMODE_REF_NO());
                cuAllocateAmt = new PaymentAllocateController(getActivity()).getcurrentAllocateBalance(fdDbNote.getFDDBNOTE_REFNO(),payMode.getFPAYMODE_REF_NO());
                RemainAmt = new PaymentAllocateController(getActivity()).getcurrentRemainBalance(fdDbNote.getFDDBNOTE_REFNO(),payMode.getFPAYMODE_REF_NO());
                Allo_ID = new PaymentAllocateController(getActivity()).getcurrentAlloID(fdDbNote.getFDDBNOTE_REFNO(),payMode.getFPAYMODE_REF_NO());


                paymentAllocate.setFPAYMENT_ALLOCATE_REFNO(payMode.getFPAYMODE_REF_NO());
                paymentAllocate.setFPAYMENT_ALLOCATE_COMMON_REFNO(RefNo1);
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_REFNO(fdDbNote.getFDDBNOTE_REFNO());
                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TXN_DATE(fdDbNote.getFDDBNOTE_TXN_DATE());
//                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_PAID_AMT(fdDbNote.getFDDBNOTE_ENTER_AMT());
//                paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TOTAL_BAL(fdDbNote.getFDDBNOTE_TOT_BAL());

                if (remTotBalFromAlloc>0.00)
                {
                    paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TOTAL_BAL(String.valueOf(remTotBalFromAlloc -= Double.parseDouble(payMode.getFPAYMODE_PAID_ALLOAMT())));
                }
                else
                {
                    paymentAllocate.setFPAYMENT_ALLOCATE_FDD_TOTAL_BAL(String.valueOf(remTotBal -= Double.parseDouble(payMode.getFPAYMODE_PAID_ALLOAMT())));
                }

                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REF_NO(payMode.getFPAYMODE_REF_NO());
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_MODE(payMode.getFPAYMODE_PAID_TYPE());
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_DATE(payMode.getFPAYMODE_PAID_DATE());
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE(payMode.getFPAYMODE_PAID_CHEQUE_DATE());
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_AMT(payMode.getFPAYMODE_PAID_AMOUNT());
//                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REM_AMT(payMode.getFPAYMODE_PAID_REMAMT());

                if(cuAllocateAmt >0.00){
                    TotAllowAmt = cuAllocateAmt + Double.parseDouble(payMode.getFPAYMODE_PAID_ALLOAMT());
                    paymentAllocate.setFPAYMENT_ALLOCATE_PAY_ALLO_AMT(String.valueOf(TotAllowAmt));
                    paymentAllocate.setFPAYMENT_ALLOCATE_FDD_PAID_AMT(String.valueOf(TotAllowAmt));
                    paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REM_AMT(String.valueOf(RemainAmt - Double.parseDouble(payMode.getFPAYMODE_PAID_ALLOAMT())));
                    //paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REM_AMT(String.valueOf(RemainAmt - Double.parseDouble(payMode.getFPAYMODE_PAID_AMOUNT())));
                    paymentAllocate.setFPAYMENT_ALLOCATE_ID(Allo_ID);
                }
                else{
                    paymentAllocate.setFPAYMENT_ALLOCATE_PAY_ALLO_AMT(payMode.getFPAYMODE_PAID_ALLOAMT());
                    paymentAllocate.setFPAYMENT_ALLOCATE_FDD_PAID_AMT(fdDbNote.getFDDBNOTE_ENTER_AMT());
                    paymentAllocate.setFPAYMENT_ALLOCATE_PAY_REM_AMT(payMode.getFPAYMODE_PAID_REMAMT());
                }
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_BANK(payMode.getFPAYMODE_PAID_BANK());
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CHEQUE_NO(payMode.getFPAYMODE_PAID_CHEQUE_NO());
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO(payMode.getFPAYMODE_PAID_CREDIT_CARD_NO());
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_SLIP_NO(payMode.getFPAYMODE_PAID_SLIP_NO());
                paymentAllocate.setFPAYMENT_ALLOCATE_PAY_DRAFT_NO(payMode.getFPAYMODE_PAID_DRAFT_NO());

                Log.d("receipt_details", "invoice_no: " + paymentAllocate.getFPAYMENT_ALLOCATE_FDD_REFNO());

                paymentAllocateArrayList.add(paymentAllocate);
                // create a new reference no for receipt
//                new ReferenceNum(getActivity()).nNumValueInsertOrUpdate(getResources().getString(R.string.RecNumVal));
                //new ReferenceNum(getActivity()).NumValueUpdate(getResources().getString(R.string.RecNumVal));

//                // create a new reference no for common payment allocate
//                new ReferenceNum(getActivity()).nNumValueInsertOrUpdate(getResources().getString(R.string.RecNumValCom));

                // update PaymodeDS after allocating amount
                payMode.setFPAYMODE_PAID_ALLOAMT("0.00");

                PayMode payMode1 = payMode;
                ArrayList<PayMode>list = new ArrayList<>();
                list.add(payMode1);

                new PayModeController(getActivity()).createOrUpdatePayMode(list);

                //i++;
            }

            if (paymentAllocateArrayList.size()>0)
            {
                new PaymentAllocateController(getActivity()).createOrUpdatePaymentAllocate(paymentAllocateArrayList);

                clearTextFields();
                lv_order_det.setEnabled(true);
                FetchData();
                btnDone.setEnabled(true);
            }
            else
            {
                Toast.makeText(getActivity(), "Selected invoice not matched.", Toast.LENGTH_LONG).show();
            }

            //saveRecDet(paymentAllocateArrayList);
        }
    }

    public void saveRecDet(ArrayList<PaymentAllocate>list)
    {
        if (list.size()>0)
        {
            ArrayList<ReceiptDet>recDetList = new ArrayList<>();
            ReceiptHed recHed = new ReceiptController(getActivity()).getActiveRecHed();

            for (PaymentAllocate allocate : list )
            {
                ReceiptDet recDet = new ReceiptDet();

                recDet.setFPRECDET_REFNO(allocate.getFPAYMENT_ALLOCATE_REFNO());
                recDet.setFPRECDET_REFNO1(allocate.getFPAYMENT_ALLOCATE_FDD_REFNO());
                recDet.setFPRECDET_REFNO2(allocate.getFPAYMENT_ALLOCATE_COMMON_REFNO());
                recDet.setFPRECDET_MANUREF(recHed.getFPRECHED_MANUREF());
                //recDet.setFPRECDET_TXNDATE(recHed.getFPRECHED_TXNDATE());
                recDet.setFPRECDET_TXNDATE(allocate.getFPAYMENT_ALLOCATE_FDD_TXN_DATE());
                recDet.setFPRECDET_TXNTYPE(recHed.getFPRECHED_TXNTYPE());
                recDet.setFPRECDET_DCURCODE(recHed.getFPRECHED_CURCODE());
                recDet.setFPRECDET_DCURRATE(recHed.getFPRECHED_CURRATE());
                recDet.setFPRECDET_REPCODE(recHed.getFPRECHED_REPCODE());
                recDet.setFPRECDET_REMARK(recHed.getFPRECHED_REMARKS());
                recDet.setFPRECDET_SALEREFNO(recHed.getFPRECHED_SALEREFNO());
                recDet.setFPRECDET_ISDELETE("0");
                //recDet.setFPRECDET_AMT(String.valueOf(balDueAmt -= Double.parseDouble(allocate.getFPAYMENT_ALLOCATE_PAY_ALLO_AMT())));

                // PAID AMT CAN NOT BE '0' .................. NUWAN 20/02/2019 ........................
//                recDet.setFPRECDET_AMT(allocate.getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL());
                recDet.setFPRECDET_AMT(allocate.getFPAYMENT_ALLOCATE_PAY_ALLO_AMT());
                recDet.setFPRECDET_ALOAMT(allocate.getFPAYMENT_ALLOCATE_PAY_ALLO_AMT());
                recDet.setFPRECDET_DEBCODE(recHed.getFPRECHED_DEBCODE());
                recDet.setFPRECDET_BAMT(allocate.getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL());
                if(Double.parseDouble(allocate.getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL()) < 0) {
                    recDet.setFPRECDET_OVPAYAMT(allocate.getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL());
                    recDet.setFPRECDET_OVPAYBAL(allocate.getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL());
                }else{
                    recDet.setFPRECDET_OVPAYAMT("0.00");
                    recDet.setFPRECDET_OVPAYBAL("0.00");
                }

                recDetList.add(recDet);
            }
            if (recDetList.size()>0)
            {
                new ReceiptDetController(getActivity()).createOrUpdateRecDetS(recDetList);
                saveReceiptHeader(list);
                Toast.makeText(getActivity(), "RecDet saved successfully.", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity(), "RecDet not saved.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void saveReceiptHeader(ArrayList<PaymentAllocate>list) {

        if (list.size()>0)
        {
            ArrayList<ReceiptHed>recHedList = new ArrayList<>();
            ReceiptHed recHed = new ReceiptController(getActivity()).getActiveRecHed();

            for (PaymentAllocate allocate : list )
            {

                ReceiptHed recHed1 = new ReceiptHed();

                recHed1.setFPRECHED_REFNO(allocate.getFPAYMENT_ALLOCATE_REFNO());
                recHed1.setFPRECHED_REFNO1(allocate.getFPAYMENT_ALLOCATE_FDD_REFNO());
                recHed1.setFPRECHED_MANUREF(recHed.getFPRECHED_MANUREF());
                recHed1.setFPRECHED_SALEREFNO(recHed.getFPRECHED_SALEREFNO());
                recHed1.setFPRECHED_REPCODE(recHed.getFPRECHED_REPCODE());
                recHed1.setFPRECHED_TXNTYPE(recHed.getFPRECHED_TXNTYPE());
                recHed1.setFPRECHED_TXNDATE(recHed.getFPRECHED_TXNDATE());
                recHed1.setFPRECHED_DEBCODE(recHed.getFPRECHED_DEBCODE());
                recHed1.setFPRECHED_ADDDATE(recHed.getFPRECHED_ADDDATE());
                recHed1.setFPRECHED_REMARKS(recHed.getFPRECHED_REMARKS());
                recHed1.setFPRECHED_ADDMACH(recHed.getFPRECHED_ADDMACH());
                recHed1.setFPRECHED_ADDUSER(recHed.getFPRECHED_ADDUSER());
                recHed1.setFPRECHED_CURCODE(recHed.getFPRECHED_CURCODE());
                recHed1.setFPRECHED_CURRATE(recHed.getFPRECHED_CURRATE());
                recHed1.setFPRECHED_COSTCODE(recHed.getFPRECHED_COSTCODE());
                recHed1.setFPRECHED_ISACTIVE(recHed.getFPRECHED_ISACTIVE());
                recHed1.setFPRECHED_ISSYNCED(recHed.getFPRECHED_ISSYNCED());
                recHed1.setFPRECHED_ISDELETE(recHed.getFPRECHED_ISDELETE());

                Double balAmt = Double.parseDouble(allocate.getFPAYMENT_ALLOCATE_FDD_TOTAL_BAL());
                recHed1.setFPRECHED_CHQNO(allocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_NO());
                recHed1.setFPRECHED_CHQDATE(allocate.getFPAYMENT_ALLOCATE_PAY_CHEQUE_DATE());
                recHed1.setFPRECHED_TOTALAMT(String.valueOf(balAmt));
                recHed1.setFPRECHED_BTOTALAMT(String.valueOf(balAmt));
                recHed1.setFPRECHED_PAYTYPE(allocate.getFPAYMENT_ALLOCATE_PAY_MODE());
                recHed1.setFPRECHED_CUSBANK(allocate.getFPAYMENT_ALLOCATE_PAY_BANK());
                recHed1.setFPRECHED_BANKCODE(allocate.getFPAYMENT_ALLOCATE_PAY_BANK());
                recHed1.setFPRECHED_COMMON_RENNO(allocate.getFPAYMENT_ALLOCATE_COMMON_REFNO());

//                mainActivity.selectedRecHed = recHed1;
//                mainActivity.ReceivedAmt = Double.parseDouble(mainActivity.selectedRecHed.getFPRECHED_TOTALAMT());
                SharedPref.getInstance(getActivity()).setGlobalVal("Rec_Start_Time", currentTime());
                recHedList.add(recHed1);
                // recHedList.add(mainActivity.selectedRecHed);

                if (recHedList.size()>0)
                {
                    new ReceiptController(getActivity()).createOrUpdateRecHedS(recHedList);
                    listener.moveToFragments(2);
                    Toast.makeText(getActivity(), "RecHed saved successfully.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "RecHed not saved.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private String currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(cal.getTime());
    }


    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public double getTotalSale(ArrayList<FddbNote> list) {

        double d = 0;

        if (list.size() > 0) {
            for (FddbNote fnote : list) {
                if (fnote.getFDDBNOTE_ENTER_AMT() != null && !fnote.getFDDBNOTE_ENTER_AMT().equals(""))
                    d += Double.parseDouble(fnote.getFDDBNOTE_ENTER_AMT().toString());
            }
        }
        return d;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void FetchData() {

        //  if (mainActivity.selectedDebtor != null) {

        //lv_order_det.setAdapter(null);
        orderList = new OutstandingController(getActivity()).getAllRecords(mSharedPref.getSelectedDebCode(), false);
        lv_order_det.setAdapter(new ReceiptAdapter1(getActivity(), orderList, RefNo1));
//            double rem = (ReceivedAmt - getTotalSale(orderList));
//            //et_remnant.setText(String.format("%,.2f", (rem)));
//
//            if (rem <= 0)
//                isAllocated = true;
//            else
//                isAllocated = false;

        FetchPayModeData();

        //mSharedPref.setGlobalVal("ReckeyRemnant", et_remnant.getText().toString().replaceAll(",", ""));
        //       }
    }

    public void FetchPayModeData()
    {
        lv_pay_mode.setAdapter(null);

        payModeArrayList = new PayModeController(getActivity()).getAllPayModeDetails();
        payModeAdapter = new PayModeAdapter(getActivity(), payModeArrayList, isInvoiceSelected);
        //lv_pay_mode.setAdapter(new PayModeAdapter(getActivity(), payModeArrayList));
        //payModeAdapter.notifyDataSetChanged();
        lv_pay_mode.setAdapter(payModeAdapter);

        isInvoiceSelected = false;

    }

    private void payModeListViewOnClick()
    {
        lv_pay_mode.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                PayMode payMode = (PayMode) adapterView.getAdapter().getItem(position);
                int selectedItemId = Integer.parseInt(payMode.getFPAYMODE_PAID_ID());
                Log.d("RECEIPT_ORDER_DETAILS", "SELECTED_ITEM_IS: " + selectedItemId);
//                EditText alloAmt = (EditText)view.findViewById(R.id.editAlloAmount);
//                alloAmt.setText("500.00");
//
//                if (alloAmt.getText().toString().equalsIgnoreCase(""))
//                {
//                    double allocatedAmt = Double.parseDouble(alloAmt.getText().toString());
//
//                    Log.d("RECEIPT_ORDER_DETAILS", "SELECTED_ITEM_IS: " + allocatedAmt);
//                }

            }
        });
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private void clearTextFields() {
        index_id = 0;
//        et_BalAmt.setText("0.00");
//        et_date.setText("N/A");
//        et_dueAmt.setText("0.00");
//        et_enterAmt.setText("0.00");
//        et_remark.setText("");
//        et_RefNo.setText("N/A");
        selectedItem = null;
        btnUpdate.setEnabled(false);
        bCancel.setEnabled(false);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//    @Override
//    public void onClick(View arg0) {
//    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ReceiptDetails.this.mRefreshHeaderDetails();
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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_DETAILS"));
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mRefreshHeaderDetails(){
        ReceivedAmt = mainActivity.ReceivedAmt;
        FetchData();

    }

    // ---------------------------------------------------- Nuwan 04/09/2018 ------------------------------------------------------

    public void createPaymentModeDialog()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.payment_mode_dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final Spinner spnPayMode = (Spinner)promptView.findViewById(R.id.spnRecPayMode);
        final SearchableSpinner spnBank = (SearchableSpinner) promptView.findViewById(R.id.spnRecBank);
        final Spinner spnCardType = (Spinner) promptView.findViewById(R.id.spnCardType);

        final TableRow chequeNoRow = (TableRow) promptView.findViewById(R.id.chequeNoRow);
        final TableRow exDateRow = (TableRow) promptView.findViewById(R.id.exDateRow);
        final TableRow bankRow = (TableRow) promptView.findViewById(R.id.bankRow);
        final TableRow cardRow = (TableRow) promptView.findViewById(R.id.cardRow);
        final TableRow cardTypeRow = (TableRow) promptView.findViewById(R.id.cardTypeRow);
        final TableRow depositRow = (TableRow) promptView.findViewById(R.id.depositRow);
        final TableRow draftRow = (TableRow) promptView.findViewById(R.id.draftRow);
        final TableRow recDateRow = (TableRow)promptView.findViewById(R.id.recDateRow);

        final TextView reciptDate = (TextView) promptView.findViewById(R.id.txtRecDate);
        final EditText txtReceAmt = (EditText) promptView.findViewById(R.id.txtRecAmt);
        final EditText txtCHQNO = (EditText) promptView.findViewById(R.id.txtRecCheque);
        final EditText txtCardNo = (EditText) promptView.findViewById(R.id.txtCardNo);
        final EditText txtSlipNo = (EditText) promptView.findViewById(R.id.txtSlipNo);
        final EditText txtDraftNo = (EditText) promptView.findViewById(R.id.txtDraftNo);

        //final TextView txtCHQDate = (TextView) promptView.findViewById(R.id.txtRecChequeDate);
        final TextView txtRecExpireDate = (TextView) promptView.findViewById(R.id.txtRecExpireDate);

        List<String> payModeList = new ArrayList<String>();
        payModeList.add("-SELECT-");
        payModeList.add("CASH");
        payModeList.add("CHEQUE");
//        payModeList.add("CREDIT CARD");
//        payModeList.add("DIRECT DEPOSIT");
//        payModeList.add("BANK DRAFT");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, payModeList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPayMode.setAdapter(dataAdapter1);

        BankController bankDS = new BankController(getActivity());
        List<Bank> bankList = new ArrayList<>();
        bankList = bankDS.getBanks();
        ArrayAdapter<Bank> bankAdapter = new ArrayAdapter<Bank>(getActivity(), android.R.layout.simple_spinner_item, bankList);
//        List<String>bankList = new ArrayList<>();
//        bankList.add("-SELECT-");
//        bankList.add("ABC");
//        bankList.add("HNB");
//        bankList.add("NTB");
        //ArrayAdapter<String> bankAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, bankList);
        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBank.setAdapter(bankAdapter);

        List<String> cardTypeList = new ArrayList<String>();
        cardTypeList.add("-SELECT-");
        cardTypeList.add("VISA");
        cardTypeList.add("MASTER");
        cardTypeList.add("OTHER");
        ArrayAdapter<String> cardListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cardTypeList);
        cardListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCardType.setAdapter(cardListAdapter);

        spnPayMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                payModePos = mSharedPref.getGlobalVal("ReckeyPayModePos");

                if (!(payModePos.equals(String.valueOf(spnPayMode.getSelectedItemPosition())))) {

                    mSharedPref.setGlobalVal("ReckeyPayModePos", String.valueOf(spnPayMode.getSelectedItemPosition()));
                    mSharedPref.setGlobalVal("ReckeyPayMode", spnPayMode.getSelectedItem().toString());

                    if (spnPayMode.getSelectedItem().toString().equalsIgnoreCase("cheque"))
                    {

                        chequeNoRow.setVisibility(View.VISIBLE);
                        bankRow.setVisibility(View.VISIBLE);
                        recDateRow.setVisibility(View.VISIBLE);

                        cardRow.setVisibility(View.GONE);
                        cardTypeRow.setVisibility(View.GONE);
                        exDateRow.setVisibility(View.GONE);
                        depositRow.setVisibility(View.GONE);
                        draftRow.setVisibility(View.GONE);


                    }
                    else if (spnPayMode.getSelectedItem().toString().equalsIgnoreCase("credit card"))
                    {
                        cardRow.setVisibility(View.VISIBLE);
                        cardTypeRow.setVisibility(View.VISIBLE);
                        exDateRow.setVisibility(View.VISIBLE);
                        recDateRow.setVisibility(View.VISIBLE);

                        chequeNoRow.setVisibility(View.GONE);
                        bankRow.setVisibility(View.GONE);
                        depositRow.setVisibility(View.GONE);
                        draftRow.setVisibility(View.GONE);

                    }
                    else if (spnPayMode.getSelectedItem().toString().equalsIgnoreCase("direct deposit"))
                    {
                        depositRow.setVisibility(View.VISIBLE);
                        recDateRow.setVisibility(View.VISIBLE);

                        chequeNoRow.setVisibility(View.GONE);
                        bankRow.setVisibility(View.GONE);
                        cardRow.setVisibility(View.GONE);
                        cardTypeRow.setVisibility(View.GONE);
                        exDateRow.setVisibility(View.GONE);
                        draftRow.setVisibility(View.GONE);

                    }
                    else if (spnPayMode.getSelectedItem().toString().equalsIgnoreCase("bank draft"))
                    {
                        draftRow.setVisibility(View.VISIBLE);
                        recDateRow.setVisibility(View.VISIBLE);

                        chequeNoRow.setVisibility(View.GONE);
                        bankRow.setVisibility(View.GONE);
                        cardRow.setVisibility(View.GONE);
                        cardTypeRow.setVisibility(View.GONE);
                        exDateRow.setVisibility(View.GONE);
                        depositRow.setVisibility(View.GONE);
                    }
                    else
                    {
                        chequeNoRow.setVisibility(View.GONE);
                        bankRow.setVisibility(View.GONE);
                        cardRow.setVisibility(View.GONE);
                        cardTypeRow.setVisibility(View.GONE);
                        exDateRow.setVisibility(View.GONE);
                        depositRow.setVisibility(View.GONE);
                        draftRow.setVisibility(View.GONE);
                        recDateRow.setVisibility(View.GONE);
                    }
                }
                mSharedPref.setGlobalVal("ReckeyPayModePos", String.valueOf(spnPayMode.getSelectedItemPosition()));
                mSharedPref.setGlobalVal("ReckeyPayMode", spnPayMode.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        reciptDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                getDateTimePicker(reciptDate);
            }
        });

        txtRecExpireDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                getDateTimePicker(txtRecExpireDate);
            }
        });


        if (!txtReceAmt.getText().toString().equals(""))
        {
            txtReceAmt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.showSoftInput(txtReceAmt, InputMethodManager.SHOW_IMPLICIT);
                        }

                        txtReceAmt.selectAll();
                        txtReceAmt.setText(txtReceAmt.getText().toString().replaceAll(",", ""));
                    } else {
                        if (txtReceAmt.getText().length() > 0) {
                            txtReceAmt.setText(String.format("%,.2f", Double.parseDouble(txtReceAmt.getText().toString().replaceAll(",", ""))));
                        }
                    }
                }
            });

            txtReceAmt.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    txtReceAmt.clearFocus();
                    txtReceAmt.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(txtReceAmt, InputMethodManager.SHOW_IMPLICIT);
                    }

                    txtReceAmt.selectAll();
                }
            });
        }

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if ((!txtReceAmt.getText().toString().equalsIgnoreCase(""))&&(!txtReceAmt.getText().toString().equals("0")) && (Double.parseDouble(txtReceAmt.getText().toString().replaceAll(",","")))>1)
                {
                    if (!spnPayMode.getSelectedItem().toString().equalsIgnoreCase("-select-"))
                    {
                        ArrayList<PayMode> list = new ArrayList<>();
                        PayMode payMode = new PayMode();
                        payMode.setFPAYMODE_PAID_ALLOAMT("0.00");
                        payMode.setFPAYMODE_PAID_REMAMT(txtReceAmt.getText().toString());
                        payMode.setFPAYMODE_PAID_BANK("");
                        String payModeRefNo,PayModeCommnRefNo;

                        payModeRefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.ReceiptNumVal));

                        PayModeCommnRefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.RecNumValCom));

                        if (spnPayMode.getSelectedItem().toString().equalsIgnoreCase("cash"))
                        {
                            payMode.setFPAYMODE_REF_NO(payModeRefNo);
                            payMode.setFPAYMODE_PAID_COMMONREFNO(PayModeCommnRefNo);
//                        payMode.setFPAYMODE_PAID_TYPE(spnPayMode.getSelectedItem().toString());
                            payMode.setFPAYMODE_PAID_TYPE("CA");
                            payMode.setFPAYMODE_PAID_AMOUNT(txtReceAmt.getText().toString());
                            payMode.setFPAYMODE_PAID_DATE(setCurrentDate());
                            payMode.setFPAYMODE_PAID_BANK("");
                            list.add(payMode);
                            //new ReferenceNum(getActivity()).nNumValueInsertOrUpdate(getResources().getString(R.string.RecPayCash));
                        }
                        else if (spnPayMode.getSelectedItem().toString().equalsIgnoreCase("cheque") )
                        {

                            String chqNo = new PayModeController(getActivity()).getPaidChequeNo(payModeRefNo);

                            if (txtCHQNO.getText().toString().equals("") ||txtCHQNO.getText().toString().equals(chqNo))
                            {
                                Toast.makeText(getActivity(), "Please enter valid Cheque Number", Toast.LENGTH_LONG).show();
                            }
                            else if (spnBank.getSelectedItem().toString().equalsIgnoreCase("-select-"))
                            {
                                Toast.makeText(getActivity(), "Please select the Bank Name", Toast.LENGTH_LONG).show();
                            }
                            else if (reciptDate.getText().toString().equalsIgnoreCase("-SELECT DATE-"))
                            {
                                Toast.makeText(getActivity(), "Please enter valid date", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                //payModeRefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.RecPayCheq));

                                payMode.setFPAYMODE_REF_NO(payModeRefNo);
                                payMode.setFPAYMODE_PAID_COMMONREFNO(RefNo1);
//                        payMode.setFPAYMODE_PAID_TYPE(spnPayMode.getSelectedItem().toString());
                                payMode.setFPAYMODE_PAID_TYPE("CH");
                                payMode.setFPAYMODE_PAID_AMOUNT(txtReceAmt.getText().toString());
                                payMode.setFPAYMODE_PAID_DATE(setCurrentDate());
                                payMode.setFPAYMODE_PAID_BANK(new BankController(getActivity()).getBankbyCode(spnBank.getSelectedItem().toString().split("-")[0].trim()));
                                payMode.setFPAYMODE_PAID_CHEQUE_DATE(reciptDate.getText().toString());
                                payMode.setFPAYMODE_PAID_CHEQUE_NO(txtCHQNO.getText().toString());

                                list.add(payMode);
                                //new ReferenceNum(getActivity()).nNumValueInsertOrUpdate(getResources().getString(R.string.RecPayCheq));
                            }

                        }
                        else if (spnPayMode.getSelectedItem().toString().equalsIgnoreCase("credit card"))
                        {
                            if (txtCardNo.getText().toString().equals(""))
                            {
                                Toast.makeText(getActivity(), "Please enter Card Number", Toast.LENGTH_LONG).show();
                            }
                            else if (spnCardType.getSelectedItem().toString().equalsIgnoreCase("-select-"))
                            {
                                Toast.makeText(getActivity(), "Please select the Card Type", Toast.LENGTH_LONG).show();
                            }
                            else if (txtRecExpireDate.getText().toString().equalsIgnoreCase("-SELECT DATE-"))
                            {
                                Toast.makeText(getActivity(), "Please enter valid date", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                //payModeRefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.RecPayCard));

                                payMode.setFPAYMODE_REF_NO(payModeRefNo);
                                payMode.setFPAYMODE_PAID_COMMONREFNO(RefNo1);
                                //payMode.setFPAYMODE_PAID_TYPE(spnPayMode.getSelectedItem().toString());
                                payMode.setFPAYMODE_PAID_TYPE("CC");
                                payMode.setFPAYMODE_PAID_AMOUNT(txtReceAmt.getText().toString());
                                payMode.setFPAYMODE_PAID_DATE(setCurrentDate());
                                payMode.setFPAYMODE_PAID_CREDIT_CARD_NO(txtCardNo.getText().toString());
                                payMode.setFPAYMODE_PAID_CARD_EXP_DATE(txtRecExpireDate.getText().toString());
                                payMode.setFPAYMODE_PAID_BANK(new BankController(getActivity()).getBankbyCode(spnBank.getSelectedItem().toString().split("-")[0].trim()));

                            }

                            if (!spnCardType.getSelectedItem().toString().equalsIgnoreCase("-select-"))
                            {
                                payMode.setFPAYMODE_PAID_CREDIT_CARD_TYPE(spnCardType.getSelectedItem().toString());
                            }

                            else
                            {
                                payMode.setFPAYMODE_PAID_CREDIT_CARD_TYPE("");
                            }

                            list.add(payMode);
                            //new ReferenceNum(getActivity()).nNumValueInsertOrUpdate(getResources().getString(R.string.RecPayCard));
                        }
                        else if (spnPayMode.getSelectedItem().toString().equalsIgnoreCase("Direct Deposit"))
                        {
                            if (reciptDate.getText().toString().equalsIgnoreCase("-SELECT DATE-"))
                            {
                                Toast.makeText(getActivity(), "Please enter valid date", Toast.LENGTH_LONG).show();
                            }
                            else if (txtSlipNo.getText().toString().equalsIgnoreCase("0"))
                            {
                                Toast.makeText(getActivity(), "Please enter slip number", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                //payModeRefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.RecPayCard));

                                payMode.setFPAYMODE_REF_NO(payModeRefNo);
                                payMode.setFPAYMODE_PAID_COMMONREFNO(RefNo1);
//                        payMode.setFPAYMODE_PAID_TYPE(spnPayMode.getSelectedItem().toString());
                                payMode.setFPAYMODE_PAID_TYPE("DD");
                                payMode.setFPAYMODE_PAID_AMOUNT(txtReceAmt.getText().toString());
                                payMode.setFPAYMODE_PAID_DATE(setCurrentDate());
                                payMode.setFPAYMODE_PAID_BANK(new BankController(getActivity()).getBankbyCode(spnBank.getSelectedItem().toString().split("-")[0].trim()));

                                payMode.setFPAYMODE_PAID_SLIP_NO(txtSlipNo.getText().toString());

                                list.add(payMode);
                                //new ReferenceNum(getActivity()).nNumValueInsertOrUpdate(getResources().getString(R.string.RecPayCard));
                            }
                        }
                        else if (spnPayMode.getSelectedItem().toString().equalsIgnoreCase("Bank Draft"))
                        {
                            if (reciptDate.getText().toString().equalsIgnoreCase("-SELECT DATE-"))
                            {
                                Toast.makeText(getActivity(), "Please enter valid date", Toast.LENGTH_LONG).show();
                            }
                            else if (txtDraftNo.getText().toString().equalsIgnoreCase("0"))
                            {
                                Toast.makeText(getActivity(), "Please enter draft number", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                //payModeRefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.RecPayCard));

                                payMode.setFPAYMODE_REF_NO(payModeRefNo);
                                payMode.setFPAYMODE_PAID_COMMONREFNO(RefNo1);
//                        payMode.setFPAYMODE_PAID_TYPE(spnPayMode.getSelectedItem().toString());
                                payMode.setFPAYMODE_PAID_TYPE("BD");
                                payMode.setFPAYMODE_PAID_AMOUNT(txtReceAmt.getText().toString());
                                payMode.setFPAYMODE_PAID_DATE(setCurrentDate());
                                payMode.setFPAYMODE_PAID_DRAFT_NO(txtDraftNo.getText().toString());
                                payMode.setFPAYMODE_PAID_BANK(new BankController(getActivity()).getBankbyCode(spnBank.getSelectedItem().toString().split("-")[0].trim()));


                                list.add(payMode);

                            }

                        }

                        mSharedPref.setGlobalVal("ReckeyHeader", "1");
                        if (new PayModeController(getActivity()).createOrUpdatePayMode(list)>0)
                        {
                            new ReferenceNum(getActivity()).nNumValueInsertOrUpdate(getResources().getString(R.string.ReceiptNumVal));
                        }
                        ReceiptDetails.this.mRefreshHeaderDetails();
                    }
                }

                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                dialog.dismiss();
                // dialog.cancel();
            }
        }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    private String setCurrentDate() {
        String setDate ="";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        setDate = dateFormat.format(date);

        return setDate;
    }

    public void getDateTimePicker(final TextView textView) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.date_picker);
        final DatePicker dtp = (DatePicker) dialog.findViewById(R.id.dpResult);
        dtp.setCalendarViewShown(false);
        dialog.setCancelable(true);
        Button button = (Button) dialog.findViewById(R.id.btnok);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int month = dtp.getMonth() + 1;
                int year = dtp.getYear();
                int date = dtp.getDayOfMonth();
                String chdate = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", date);
                Date date1 = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date2 = new Date();
                try {
                    Date date11 = new Date();
                    date1 = (Date) dateFormat.parse(dateFormat.format(date11));
                    date2 = (Date) dateFormat.parse(chdate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();

                cal1.setTime(date1);
                cal2.setTime(date2);

                if ((cal2.before(cal1))) {
                    System.out.println("Date1 is before Date2 detail");
                    Toast.makeText(getActivity(), "Invalid Date.Please select a Future Date than Current Date.", Toast.LENGTH_SHORT).show();
                    textView.setText("");

                } else {

                    textView.setText(chdate);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void getDateTimePickerForCardExpire(final TextView textView) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.date_picker);
        final DatePicker dtp = (DatePicker) dialog.findViewById(R.id.dpResult);
        dtp.setCalendarViewShown(false);
        dialog.setCancelable(true);
        Button button = (Button) dialog.findViewById(R.id.btnok);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int month = dtp.getMonth() + 1;
                int year = dtp.getYear();
                int date = dtp.getDayOfMonth();
                String chdate = String.format("%02d", month) + "/" + year;
                Date date1 = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date2 = new Date();
                try {
                    Date date11 = new Date();
                    date1 = (Date) dateFormat.parse(dateFormat.format(date11));
                    date2 = (Date) dateFormat.parse(chdate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();

                cal1.setTime(date1);
                cal2.setTime(date2);

                if ((cal2.before(cal1))) {
                    System.out.println("Date1 is before Date2 detail");
                    Toast.makeText(getActivity(), "Invalid Date.Please select a Future Date than Current Date.", Toast.LENGTH_SHORT).show();
                    textView.setText("");

                } else {

                    textView.setText(chdate);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        Context context = getActivity();
        Toast.makeText(context, "item " + position + " was clicked", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (ReceiptResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }

    private void spinnerValidation()
    {
        //if ((Double.parseDouble(txtReceAmt.getText().toString().replaceAll(",", "")) > 0) && !spnPayMode.getSelectedItem().toString().equalsIgnoreCase("-SELECT-")){
//                    if (spnPayMode.getSelectedItemPosition() == 1) {
//                        if (Double.parseDouble(txtReceAmt.getText().toString().replaceAll(",", "")) > 0) {
//                            SaveReceiptHeader();
//                            mSharedPref.setGlobalVal("ReckeyHeader", "1");
//                        } else {
//                            Toast.makeText(getActivity(), "Please fill in Received amount", Toast.LENGTH_LONG).show();
//                            txtReceAmt.requestFocus();
//                        }
//                    } else if (spnPayMode.getSelectedItemPosition() == 2) {
//                        if (txtCHQNO.getText().length() > 0 && txtCHQDate.getText().length() > 0 && Double.parseDouble(txtReceAmt.getText().toString().replaceAll(",", "")) > 0) {
//                            if (txtCHQNO.getText().length() < 6) {
//                                Toast.makeText(getActivity(), "Invalid Cheque No!", Toast.LENGTH_LONG).show();
//                                txtCHQNO.requestFocus();
//                            } else {
//                                SaveReceiptHeader();
//                                mSharedPref.setGlobalVal("ReckeyHeader", "1");
//
//                            }
//                        } else {
//                            Toast.makeText(getActivity(), "Fill in Received amount, Chq date, Chq no..!", Toast.LENGTH_LONG).show();
//                            txtCHQNO.requestFocus();
//                        }
//
//                    } else if (spnPayMode.getSelectedItemPosition() == 3) {
//                        if (txtCardNo.getText().length() > 0 && txtRecExpireDate.getText().length() > 0 && Double.parseDouble(txtReceAmt.getText().toString().replaceAll(",", "")) > 0) {
//                            if (txtCardNo.getText().length() < 0) {
//                                Toast.makeText(getActivity(), "Invalid Credit Card No!", Toast.LENGTH_LONG).show();
//                                txtCardNo.requestFocus();
//                            } else {
//                                SaveReceiptHeader();
//                                mSharedPref.setGlobalVal("ReckeyHeader", "1");
//
//                            }
//                        } else {
//                            Toast.makeText(getActivity(), "Fill in Received amount, Credit Card No, Expire Date..!", Toast.LENGTH_LONG).show();
//                            txtCardNo.requestFocus();
//                        }
//
//                    } else if (spnPayMode.getSelectedItemPosition() == 4) {
//                        if (txtSlipNo.getText().length() > 0 && Double.parseDouble(txtReceAmt.getText().toString().replaceAll(",", "")) > 0) {
//                            if (txtSlipNo.getText().length() < 0) {
//                                Toast.makeText(getActivity(), "Invalid Slip No!", Toast.LENGTH_LONG).show();
//                                txtSlipNo.requestFocus();
//                            } else {
//                                SaveReceiptHeader();
//                                mSharedPref.setGlobalVal("ReckeyHeader", "1");
//
//                            }
//
//                        } else {
//
//                        }
//
//                    } else {
//                        if (txtDraftNo.getText().length() < 0) {
//                            Toast.makeText(getActivity(), "Invalid Draft No!", Toast.LENGTH_LONG).show();
//                            txtDraftNo.requestFocus();
//                        } else {
//                            SaveReceiptHeader();
//                            mSharedPref.setGlobalVal("ReckeyHeader", "1");
//
//                        }
//                    }
//
//                listener.moveToDetailsRece();
//            }else{
//                    Toast.makeText(getActivity(), "Please fill received amount and payment mode", Toast.LENGTH_LONG).show();
//                }

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//        txtReceAmt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    if (imm != null) {
//                        imm.showSoftInput(txtReceAmt, InputMethodManager.SHOW_IMPLICIT);
//                    }
//
//                    txtReceAmt.selectAll();
//                    txtReceAmt.setText(txtReceAmt.getText().toString().replaceAll(",", ""));
//                } else {
//                    if (txtReceAmt.getText().length() > 0) {
//                        txtReceAmt.setText(String.format("%,.2f", Double.parseDouble(txtReceAmt.getText().toString().replaceAll(",", ""))));
//                    }
//                }
//            }
//        });

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//        txtReceAmt.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                txtReceAmt.clearFocus();
//                txtReceAmt.requestFocus();
//                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    imm.showSoftInput(txtReceAmt, InputMethodManager.SHOW_IMPLICIT);
//                }
//
//                txtReceAmt.selectAll();
//            }
//        });

        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/




        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//        txtCHQDate.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if (spnPayMode.getSelectedItemPosition() == 2) {
//                    datetimepicker();
//                }
//            }
//        });
//
//        txtRecExpireDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (spnPayMode.getSelectedItemPosition() == 3) {
//                    datetimepickerForCardExpire();
//                }
//            }
//        });

        //mSharedPref.setGlobalVal("ReckeyRecAmt", txtReceAmt.getText().toString());
//
//        if (spnPayMode.getSelectedItemPosition() == 1) {
//            recHed.setFPRECHED_PAYTYPE("CA");
//            recHed.setFPRECHED_BANKCODE("");
//            recHed.setFPRECHED_BRANCHCODE("");
//            recHed.setFPRECHED_CUSBANK("");
//            recHed.setFPRECHED_CHQNO("");
//            recHed.setFPRECHED_CHQDATE("");
//        } else if (spnPayMode.getSelectedItemPosition() == 2) {
//            recHed.setFPRECHED_CUSBANK(spnBank.getSelectedItem().toString());
//            recHed.setFPRECHED_PAYTYPE("CH");
//            recHed.setFPRECHED_BANKCODE("");
//            recHed.setFPRECHED_BRANCHCODE("");
//            recHed.setFPRECHED_CHQNO(txtCHQNO.getText().toString());
        //recHed.setFPRECHED_CHQDATE(currnentDate.getText().toString());
//            mSharedPref.setGlobalVal("ReckeyCHQNo", txtCHQNO.getText().toString());
//        } else if (spnPayMode.getSelectedItemPosition() == 3) {
//            recHed.setFPRECHED_CUSBANK(spnCardType.getSelectedItem().toString());
//            recHed.setFPRECHED_PAYTYPE("CC");
//            recHed.setFPRECHED_BANKCODE("");
//            recHed.setFPRECHED_BRANCHCODE("");
//            recHed.setFPRECHED_CHQNO(txtCardNo.getText().toString());
//            recHed.setFPRECHED_CHQDATE(txtRecExpireDate.getText().toString());
//            mSharedPref.setGlobalVal("ReckeyCHQNo", txtCardNo.getText().toString());
//        } else if (spnPayMode.getSelectedItemPosition() == 4) {
//            recHed.setFPRECHED_CUSBANK("");
//            recHed.setFPRECHED_PAYTYPE("DD");
//            recHed.setFPRECHED_BANKCODE("");
//            recHed.setFPRECHED_BRANCHCODE("");
//            recHed.setFPRECHED_CHQNO(txtSlipNo.getText().toString());
//            recHed.setFPRECHED_CHQDATE("");
//            mSharedPref.setGlobalVal("ReckeyCHQNo", txtSlipNo.getText().toString());
//        } else {
//            recHed.setFPRECHED_CUSBANK("");
//            recHed.setFPRECHED_PAYTYPE("BD");
//            recHed.setFPRECHED_BANKCODE("");
//            recHed.setFPRECHED_BRANCHCODE("");
//            recHed.setFPRECHED_CHQNO(txtDraftNo.getText().toString());
//            recHed.setFPRECHED_CHQDATE("");
//            mSharedPref.setGlobalVal("ReckeyCHQNo", txtDraftNo.getText().toString());
//        }
    }

    //---------------------------------LoardingPreProductFromDB----------------------------------------------------------------------------------------


}
