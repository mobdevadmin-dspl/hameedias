package com.datamation.swdsfa.fragment.debtorlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.adapter.CustomerAdapter;
import com.datamation.swdsfa.adapter.PreOrderAdapter;
import com.datamation.swdsfa.controller.CustomerController;
import com.datamation.swdsfa.controller.PreProductController;
import com.datamation.swdsfa.controller.RouteDetController;
import com.datamation.swdsfa.controller.SalRepController;
import com.datamation.swdsfa.customer.NewCustomerActivity;
import com.datamation.swdsfa.dialog.CustomProgressDialog;
import com.datamation.swdsfa.helpers.NetworkFunctions;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.Customer;
import com.datamation.swdsfa.utils.GPSTracker;
import com.datamation.swdsfa.view.DebtorDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class AllCustomerFragment extends Fragment {

    View view;
    ListView lvCustomers;
    SharedPref mSharedPref;
    private NetworkFunctions networkFunctions;
    ArrayList<Customer> customerList;
    CustomerAdapter customerAdapter;
    GPSTracker gpsTracker;
    private Customer debtor;
    String routeCode = "";
    Switch mySwitch;
    SearchView mSearchDeb;
    double lati = 0.0;
    double longi = 0.0;
    private boolean isGPSSwitched;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_all_customer, container, false);

        lvCustomers = (ListView) view.findViewById(R.id.all_cus_lv);
        mSharedPref = new SharedPref(getActivity());
        gpsTracker = new GPSTracker(getActivity());
        mySwitch = (Switch) view.findViewById(R.id.gps_switch);

//        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//            mySwitch.setText("SWITCH");
//        } else {
//            mySwitch.setTextOff("OFF");
//            mySwitch.setTextOn("ON");
//        }

        mSearchDeb = (SearchView) view.findViewById(R.id.et_all_deb_search);

        lvCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {

                if (!(gpsTracker.canGetLocation())) {
                    gpsTracker.showSettingsAlert();
                }

                /* Check whether automatic date time option checked or not */
                int i = android.provider.Settings.Global.getInt(getActivity().getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0);
                /* If option is selected */
                if (i > 0) {
                    debtor = customerList.get(position);

                    if (isGPSSwitched) {
                        mSharedPref.setGPSDebtor("AY");
                    } else {
                        mSharedPref.setGPSDebtor("AN");
                    }

                    try {
                        if (!debtor.getCusImage().isEmpty()) {
                            Intent intent = new Intent(getActivity(), DebtorDetailsActivity.class);
                            intent.putExtra("outlet", debtor);
                            mSharedPref.clearPref();
                            mSharedPref.setSelectedDebCode(debtor.getCusCode());
                            mSharedPref.setSelectedDebName(debtor.getCusName());
                            mSharedPref.setSelectedDebRouteCode(new RouteDetController(getActivity()).getRouteCodeByDebCode(debtor.getCusCode()));
                            mSharedPref.setSelectedDebtorPrilCode(debtor.getCusPrilCode());
                            startActivity(intent);
                            getActivity().finish();

                        } else {
                            mSharedPref.clearPref();
                            mSharedPref.setSelectedDebCode(debtor.getCusCode());
                            mSharedPref.setSelectedDebName(debtor.getCusName());
                            mSharedPref.setSelectedDebRouteCode(new RouteDetController(getActivity()).getRouteCodeByDebCode(debtor.getCusCode()));
                            mSharedPref.setSelectedDebtorPrilCode(debtor.getCusPrilCode());

                            MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                                    .content("This debtor have not set business image. Do you want to update image first?")
                                    .positiveColor(ContextCompat.getColor(getActivity(), R.color.material_alert_positive_button))
                                    .positiveText("Yes")
                                    .negativeColor(ContextCompat.getColor(getActivity(), R.color.material_alert_negative_button))
                                    .negativeText("No, Continue to order.")
                                    .callback(new MaterialDialog.ButtonCallback() {

                                        @Override
                                        public void onPositive(MaterialDialog dialog) {
                                            super.onPositive(dialog);

                                            Intent intent = new Intent(getActivity(), NewCustomerActivity.class);
                                            intent.putExtra("outlet", debtor);
                                            intent.putExtra("allCusFrag",88);
                                            startActivity(intent);
                                            getActivity().finish();

                                        }

                                        @Override
                                        public void onNegative(MaterialDialog dialog) {
                                            super.onNegative(dialog);
                                            Intent intent = new Intent(getActivity(), DebtorDetailsActivity.class);
                                            intent.putExtra("outlet", debtor);
                                            mSharedPref.clearPref();
                                            mSharedPref.setSelectedDebCode(debtor.getCusCode());
                                            mSharedPref.setSelectedDebName(debtor.getCusName());
                                            mSharedPref.setSelectedDebRouteCode(new RouteDetController(getActivity()).getRouteCodeByDebCode(debtor.getCusCode()));
                                            mSharedPref.setSelectedDebtorPrilCode(debtor.getCusPrilCode());
                                            dialog.dismiss();
                                            startActivity(intent);
                                            getActivity().finish();



                                        }
                                    })
                                    .build();
                            materialDialog.setCanceledOnTouchOutside(false);
                            materialDialog.show();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please tick the 'Automatic Date and Time' option to continue..", Toast.LENGTH_LONG).show();
                    /* Show Date/time settings dialog */
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }
            }
        });

        isGPSSwitched = false;

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    gpsTracker = new GPSTracker(getActivity());

                    if (gpsTracker.canGetLocation()) {
                        isGPSSwitched = true;
                        if (!mSharedPref.getGlobalVal("Latitude").equals("") && !mSharedPref.getGlobalVal("Longitude").equals("")) {
                            lati = Double.parseDouble(mSharedPref.getGlobalVal("Latitude"));
                            longi = Double.parseDouble(mSharedPref.getGlobalVal("Longitude"));

                            new getAllGPSCustomer().execute();
                        } else {
                            startActivityForResult(new Intent(Settings.ACTION_LOCALE_SETTINGS), 0);
                        }
                    } else {
                        gpsTracker.showSettingsAlert();
                    }

                    mSearchDeb.setQuery("", false);
                    mSearchDeb.clearFocus();

                } else {
                    isGPSSwitched = false;
                    lati = 0.0;
                    longi = 0.0;

                    new getAllCustomer("").execute();
                    mSearchDeb.setQuery("", false);
                    mSearchDeb.clearFocus();
                }
            }
        });

        mSearchDeb.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!isGPSSwitched) {
                    customerList.clear();
                    customerList = new CustomerController(getActivity()).getAllCustomersForSelectedRepCode(new SalRepController(getActivity()).getCurrentRepCode(), query);
                    lvCustomers.setAdapter(new CustomerAdapter(getActivity(), customerList));
                } else {
                    customerList.clear();
                    customerList = new CustomerController(getActivity()).getAllGPSCustomersForSelectedRepCode(new SalRepController(getActivity()).getCurrentRepCode(), lati, longi, query);
                    lvCustomers.setAdapter(new CustomerAdapter(getActivity(), customerList));
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (!isGPSSwitched) {
                    customerList.clear();
                    customerList = new CustomerController(getActivity()).getAllCustomersForSelectedRepCode(new SalRepController(getActivity()).getCurrentRepCode(), newText);
                    lvCustomers.setAdapter(new CustomerAdapter(getActivity(), customerList));
                } else {
                    customerList.clear();
                    customerList = new CustomerController(getActivity()).getAllGPSCustomersForSelectedRepCode(new SalRepController(getActivity()).getCurrentRepCode(), lati, longi, newText);
                    lvCustomers.setAdapter(new CustomerAdapter(getActivity(), customerList));
                }

                return true;
            }
        });

        new getAllCustomer("").execute();
        //mSharedPref.setGPSDebtor("AN");

        return view;
    }

    private class getAllGPSCustomer extends AsyncTask<String, Integer, Boolean> {
        int totalRecords = 0;
        CustomProgressDialog pdialog;

        public getAllGPSCustomer() {
            this.pdialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new CustomProgressDialog(getActivity());
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Authenticating...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            try {
                customerList = new CustomerController(getActivity()).getAllGPSCustomersForSelectedRepCode(new SalRepController(getActivity()).getCurrentRepCode(), lati, longi, "");

                return true;

            } catch (Exception e) {
                e.printStackTrace();

                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);

            if (result) {
                customerAdapter = new CustomerAdapter(getActivity(), customerList);
                lvCustomers.setAdapter(customerAdapter);
            }
            pdialog.setMessage("Finalizing data");
            pdialog.setMessage("Download Completed..");
            if (result) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }
            } else {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            }
        }
    }

    private class getAllCustomer extends AsyncTask<String, Integer, Boolean> {
        int totalRecords = 0;
        CustomProgressDialog pdialog;
        private String keyWord;

        public getAllCustomer(String keyWord) {
            this.keyWord = keyWord;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new CustomProgressDialog(getActivity());
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Authenticating...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            try {
                customerList = new CustomerController(getActivity()).getAllCustomersForSelectedRepCode(new SalRepController(getActivity()).getCurrentRepCode(), "");

                return true;

            } catch (Exception e) {
                e.printStackTrace();

                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);

            if (result) {
                customerAdapter = new CustomerAdapter(getActivity(), customerList);
                lvCustomers.setAdapter(customerAdapter);
            }
            pdialog.setMessage("Finalizing data");
            pdialog.setMessage("Download Completed..");
            if (result) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }
            } else {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            }
        }
    }

    public boolean isValidateCustomer(Customer customer) {
//        if (customer.getCusRoute().equals(""))
//        {
//            errorDialog("Route Error", "Selected debtor has no route to continue...");
//            return false;
//        }
//        else
        if (customer.getCusStatus().equals("I")) {
            errorDialog("Status Error", "Selected debtor is inactive to continue...");
            return false;
        } else if (customer.getCreditPeriod().equals("N")) {
            errorDialog("Period Error", "Credit period expired for Selected debtor...");
            return false;
        } else if (Double.parseDouble(customer.getCreditLimit()) == 0.00) {
            errorDialog("Limit Error", "Credit limit exceed for Selected debtor...");
            return false;
        } else if (customer.getCreditStatus().equals("N")) {
            errorDialog("Credit Status Error", "Credit status not valid for Selected debtor...");
            return false;
        } else {
            return true;
        }
    }

    public void errorDialog(String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(R.drawable.info);


        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
