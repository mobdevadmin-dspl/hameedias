package com.datamation.hmdsfa.barcode.invoce;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.datamation.hmdsfa.adapter.DiscountAdapter;
import com.datamation.hmdsfa.adapter.InvDetAdapter;
import com.datamation.hmdsfa.controller.DiscountController;
import com.datamation.hmdsfa.controller.InvDetController;
import com.datamation.hmdsfa.controller.ItemLocController;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.controller.VATController;
import com.datamation.hmdsfa.model.Discount;
import com.datamation.hmdsfa.model.InvDet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.CustomerDebtAdapter;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.InvHedController;
import com.datamation.hmdsfa.controller.InvoiceBarcodeController;
import com.datamation.hmdsfa.controller.InvoiceDetBarcodeController;
import com.datamation.hmdsfa.controller.OutstandingController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.helpers.VanSalesResponseListener;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.FddbNote;
import com.datamation.hmdsfa.model.InvHed;
import com.datamation.hmdsfa.settings.ReferenceNum;
import com.datamation.hmdsfa.view.ActivityVanSalesBR;
import com.datamation.hmdsfa.view.DebtorDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import com.bit.sfa.Settings.SharedPreferencesClass;

public class BRInvoiceHeaderFragment extends Fragment implements View.OnClickListener{
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    View view;
    SharedPref mSharedPref;
    private FloatingActionButton next;
    TextView lblCustomerName, outStandingAmt, lastBillAmt,lblInvRefno,viewDiscount;
    EditText  currnentDate,txtManual,txtRemakrs;
    Spinner spnPayMethod,spnVat;
    InvHed selectedInvHed;
    Customer selectedDebtor;
    ActivityVanSalesBR activity;
    VanSalesResponseListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_management_van_sales_header, container, false);
        setHasOptionsMenu(true);
        localSP = getActivity().getSharedPreferences(SETTINGS, 0);
        activity = (ActivityVanSalesBR) getActivity();
        mSharedPref = new SharedPref(getActivity());
        next = (FloatingActionButton) view.findViewById(R.id.fab);
        lblCustomerName = (TextView) view.findViewById(R.id.customerName);
        outStandingAmt = (TextView) view.findViewById(R.id.lbl_Inv_outstanding_amt);
        lastBillAmt = (TextView) view.findViewById(R.id.lbl_inv_lastbill);
        lblInvRefno = (TextView) view.findViewById(R.id.invoice_no);
        viewDiscount = (TextView) view.findViewById(R.id.view_discount);
        currnentDate = (EditText) view.findViewById(R.id.lbl_InvDate);
        txtManual = (EditText) view.findViewById(R.id.txt_InvManual);
        txtRemakrs = (EditText) view.findViewById(R.id.txt_InvRemarks);
        spnPayMethod = (Spinner) view.findViewById(R.id.spnnerPayment);
        spnVat = (Spinner) view.findViewById(R.id.spnnervat);

        lblCustomerName.setText(SharedPref.getInstance(getActivity()).getSelectedDebName());
        selectedInvHed = new InvHedController(getActivity()).getActiveInvhed();
        selectedDebtor = new CustomerController(getActivity()).getSelectedCustomerByCode(SharedPref.getInstance(getActivity()).getSelectedDebCode());

        currnentDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        outStandingAmt.setText(String.format("%,.2f", new OutstandingController(getActivity()).getDebtorBalance(SharedPref.getInstance(getActivity()).getSelectedDebCode())));
        txtRemakrs.setEnabled(true);
        txtManual.setEnabled(true);

        /*already a header exist*/
        if (selectedInvHed != null) {
            txtManual.setText(selectedInvHed.getFINVHED_MANUREF());
            txtRemakrs.setText(selectedInvHed.getFINVHED_REMARKS());
            lblInvRefno.setText(selectedInvHed.getFINVHED_REFNO());
        } else { /*No header*/
            lblInvRefno.setText(new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.VanNumVal)));
        }


        outStandingAmt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View promptView = layoutInflater.inflate(R.layout.customer_debtor, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Customer outstanding...");
                alertDialogBuilder.setView(promptView);

                final ListView listView = (ListView) promptView.findViewById(R.id.lvCusDebt);
                ArrayList<FddbNote> list = new OutstandingController(getActivity()).getDebtInfo(SharedPref.getInstance(getActivity()).getSelectedDebCode());
                listView.setAdapter(new CustomerDebtAdapter(getActivity(), list));

                alertDialogBuilder.setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        List<String> listPayType = new ArrayList<String>();
        listPayType.add("CASH");
        listPayType.add("CREDIT");
        listPayType.add("OTHER");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, listPayType);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPayMethod.setAdapter(dataAdapter1);

        spnPayMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                new SharedPref(getActivity()).setGlobalVal("KeyPayType", spnPayMethod.getSelectedItem().toString());
//                clickCount = 0;
                mSharedPref.setDiscountClicked("0");
                Log.v("PAYMENT TYPE", spnPayMethod.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                new SharedPref(getActivity()).setGlobalVal("KeyPayType", "");
                mSharedPref.setDiscountClicked("0");
                Log.v("PAYMENT TYPE", spnPayMethod.getSelectedItem().toString());

            }
        });

        ArrayList<String> vatDetails = new VATController(getActivity()).getVatDetails(new CustomerController(getContext()).getCustomerVatStatus(mSharedPref.getSelectedDebCode()));
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, vatDetails);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnVat.setAdapter(dataAdapter2);

        spnVat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                new SharedPref(getActivity()).setGlobalVal("KeyVat", spnVat.getSelectedItem().toString().split("-")[0].trim());
                mSharedPref.setDiscountClicked("0");
                Log.v("VAT", spnVat.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                new SharedPref(getActivity()).setGlobalVal("KeyVat", "");
                mSharedPref.setDiscountClicked("0");
                Log.v("VAT", spnVat.getSelectedItem().toString());
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lblCustomerName.getText().toString().equals("")|| lblInvRefno.getText().toString().equals("")||currnentDate.getText().toString().equals(""))
                {
                    listener.moveBackToCustomer(0);
                    Toast.makeText(getActivity(), "Can not proceed with empty fields...", Toast.LENGTH_LONG).show();
                }
                else
                {
                    listener.moveBackToCustomer(1);
                    mSaveInvoiceHeader();
                }
            }
        });

        viewDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDiscountDialog();
            }
        });

        return view;
    }

    public void viewDiscountDialog() {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View promptView = layoutInflater.inflate(R.layout.discount_dialog, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle("Discounts !!!!");
            alertDialogBuilder.setView(promptView);

        final ListView lvDiscountItems = (ListView) promptView.findViewById(R.id.lvDiscount_Summary_Dialog_Inv);
        ViewGroup.LayoutParams disLvparams = lvDiscountItems.getLayoutParams();
            ArrayList<Discount> discountItemList = null;
            discountItemList = new DiscountController(getActivity()).getDiscountItems(mSharedPref.getGlobalVal("KeyPayType"),mSharedPref.getSelectedDebCode());

        if(discountItemList.size()>0){
            disLvparams.height = 300;
        }else {
            disLvparams.height = 0;
        }
        lvDiscountItems.setLayoutParams(disLvparams);


        lvDiscountItems.setAdapter(new DiscountAdapter(getActivity(), discountItemList));
            alertDialogBuilder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                public void onClick(final DialogInterface dialog, int id) {

                    dialog.cancel();

                }
            });
            AlertDialog alertD = alertDialogBuilder.create();
            alertD.show();
    }


    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mSaveInvoiceHeader() {
        selectedDebtor = new CustomerController(getActivity()).getSelectedCustomerByCode(SharedPref.getInstance(getActivity()).getSelectedDebCode());

        if (lblInvRefno.getText().length() > 0) {

            InvHed hed = new InvHed();
            hed.setFINVHED_START_TIME_SO(currentTime());
            hed.setFINVHED_REFNO(lblInvRefno.getText().toString());
            hed.setFINVHED_ADDDATE(currnentDate.getText().toString());
            hed.setFINVHED_MANUREF(txtManual.getText().toString());
            hed.setFINVHED_REMARKS(txtRemakrs.getText().toString());
            hed.setFINVHED_ADDMACH(mSharedPref.getMacAddress());
            hed.setFINVHED_ADDUSER(new SalRepController(getActivity()).getCurrentRepCode());
            hed.setFINVHED_CURCODE("LKR");
            hed.setFINVHED_CURRATE("1.00");
            hed.setFINVHED_REPCODE(new SalRepController(getActivity()).getCurrentRepCode());

            if (selectedDebtor != null) {
                hed.setFINVHED_DEBCODE(new SharedPref(getActivity()).getSelectedDebCode());
                hed.setFINVHED_CONTACT("0"); // selectedDebtor.getCusMob()
                hed.setFINVHED_CUSADD1(selectedDebtor.getCusAdd1());
                hed.setFINVHED_CUSADD2(selectedDebtor.getCusAdd2());
                hed.setFINVHED_CUSADD3(selectedDebtor.getCusAdd1());
                //  hed.setFINVHED_CUSTELE(activity.selectedDebtor.getCus);
                    hed.setFINVHED_TAXREG(selectedDebtor.getTaxreg());
            }else{
                selectedDebtor  = new CustomerController(getActivity()).getSelectedCustomerByCode(new SharedPref(getActivity()).getSelectedDebCode());
                hed.setFINVHED_DEBCODE(new SharedPref(getActivity()).getSelectedDebCode());
                hed.setFINVHED_CONTACT(selectedDebtor.getCusMob());
                hed.setFINVHED_CUSADD1(selectedDebtor.getCusAdd1());
                hed.setFINVHED_CUSADD2(selectedDebtor.getCusAdd2());
                hed.setFINVHED_CUSADD3(selectedDebtor.getCusAdd1());
                hed.setFINVHED_TAXREG(selectedDebtor.getTaxreg());
            }

            hed.setFINVHED_TXNTYPE("22");
            hed.setFINVHED_TXNDATE(currnentDate.getText().toString());
            hed.setFINVHED_IS_ACTIVE("1");
            hed.setFINVHED_IS_SYNCED("0");
            hed.setFINVHED_TOURCODE(new SharedPref(getActivity()).getGlobalVal("KeyTouRef"));
            // hed.setFINVHED_AREACODE(new SharedPref(getActivity()).getGlobalVal("KeyAreaCode"));
           // hed.setFINVHED_AREACODE(SharedPref.getInstance(getActivity()).getSelectedDebName());
             hed.setFINVHED_LOCCODE(new SalRepController(getActivity()).getCurrentLoccode());
            hed.setFINVHED_ROUTECODE(new RouteDetController(getActivity()).getRouteCodeByDebCode(new SharedPref(getActivity()).getSelectedDebCode()));
            if(new SharedPref(getActivity()).getGlobalVal("KeyPayType").equals("")){
                hed.setFINVHED_PAYTYPE(spnPayMethod.getSelectedItem().toString());
                new SharedPref(getActivity()).setGlobalVal("KeyPayType",spnPayMethod.getSelectedItem().toString());
            }else{
                hed.setFINVHED_PAYTYPE(new SharedPref(getActivity()).getGlobalVal("KeyPayType"));
            }

            hed.setFINVHED_COSTCODE("");
            hed.setFINVHED_AREACODE(new RouteController(getActivity()).getAreaCodeByRouteCode(hed.getFINVHED_ROUTECODE()));


            if(new SharedPref(getActivity()).getGlobalVal("KeyVat").equals("")){
                hed.setFINVHED_VAT_CODE(spnVat.getSelectedItem().toString().split("-")[0].trim());
                new SharedPref(getActivity()).setGlobalVal("KeyVat",spnVat.getSelectedItem().toString().split("-")[0].trim());
            }else{
                hed.setFINVHED_VAT_CODE(new SharedPref(getActivity()).getGlobalVal("KeyVat"));
            }

            // SharedPreferencesClass.setLocalSharedPreference(activity, "Van_Start_Time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            ArrayList<InvHed> ordHedList = new ArrayList<>();
            ordHedList.add(hed);
            new InvHedController(getActivity()).createOrUpdateInvHed(ordHedList);

        }
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onPause() {
        super.onPause();

    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();

    }
    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_sync, menu);
    }
    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sync:
                if(new InvDetController(getActivity()).isAnyActiveOrders()){
                    MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                            .content("You have active invoices. Cannot back without complete.")
                            .positiveText("OK")
                            .positiveColor(getResources().getColor(R.color.material_alert_positive_button))

                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    super.onNegative(dialog);
                                }

                                @Override
                                public void onNeutral(MaterialDialog dialog) {
                                    super.onNeutral(dialog);
                                }
                            })
                            .build();
                    materialDialog.setCancelable(false);
                    materialDialog.setCanceledOnTouchOutside(false);
                    materialDialog.show();
                }else{
                    MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                            .content("Do you want to back?")
                            .positiveText("Yes")
                            .positiveColor(getResources().getColor(R.color.material_alert_positive_button))
                            .negativeText("No")
                            .negativeColor(getResources().getColor(R.color.material_alert_negative_button))

                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    Intent back = new Intent(getActivity(), DebtorDetailsActivity.class);
                                    back.putExtra("outlet",new CustomerController(getActivity()).getSelectedCustomerByCode(mSharedPref.getSelectedDebCode()));
                                    startActivity(back);
                                    getActivity().finish();
                                    dialog.dismiss();
                                }

                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    super.onNegative(dialog);
                                }

                                @Override
                                public void onNeutral(MaterialDialog dialog) {
                                    super.onNeutral(dialog);
                                }
                            })
                            .build();
                    materialDialog.setCancelable(false);
                    materialDialog.setCanceledOnTouchOutside(false);
                    materialDialog.show();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (VanSalesResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement onButtonPressed");
        }
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    private String currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    @Override
    public void onClick(View v) {

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/
}
