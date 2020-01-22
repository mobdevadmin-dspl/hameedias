package com.datamation.swdsfa.customer;

import android.app.Activity;
import android.app.Dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import com.datamation.swdsfa.adapter.CustomerRegListAdapter;
import com.datamation.swdsfa.adapter.RouteAdapter;
import com.datamation.swdsfa.controller.ReferenceDetailDownloader;
import com.datamation.swdsfa.controller.CustomerController;
import com.datamation.swdsfa.controller.NewCustomerController;
import com.datamation.swdsfa.controller.RouteController;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.utils.UtilityContainer;
import com.datamation.swdsfa.view.ActivityHome;
import com.datamation.swdsfa.model.NewCustomer;
import com.datamation.swdsfa.model.Route;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.settings.GPSTracker;
import com.datamation.swdsfa.utils.NetworkUtil;
import com.datamation.swdsfa.settings.ReferenceNum;
import com.datamation.swdsfa.settings.TaskType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.datamation.swdsfa.settings.TaskType.UPLOAD_NEW_CUSTOMER;

/**
 * Created by Rashmi on 10/20/2018.
 */

public class AddNewCusRegistration extends Fragment implements AsyncTaskListener {
    public EditText customerCode,
            customerName, editTextCNic, OtherCode, businessRegno, district,
            town, route, addressline1, addressline2, city, mobile, phone, fax, emailaddress;
    public ImageButton btn_Route, btn_District, btn_Town, CustomerbtnSearch;
    private ArrayList<Route> routeArrayList;
    private ArrayList<NewCustomer> newCustomerArrayList;
    ArrayList<Uri> uris = new ArrayList<>();
    SharedPref mSharedPref;
    Switch mySwitch;
    private ReferenceNum referenceNum;
    private String jsonstr;
    private ProgressDialog progressDialog;
    public static SharedPreferences localSP;
    public static final String SETTINGS = "SETTINGS";
    ActivityHome home;
    FloatingActionButton fab;
    int CUSFLG = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.finac_new_customer_registration, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        //hide bottom navigation
        home = new ActivityHome();
        home.bottomNav(true);

        mSharedPref = SharedPref.getInstance(getActivity());
        referenceNum = new ReferenceNum(getActivity());
        localSP = getActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab) ;
        customerCode = (EditText) rootView.findViewById(R.id.editTextCustomer_Code);
        customerName = (EditText) rootView.findViewById(R.id.editText2);
        editTextCNic = (EditText) rootView.findViewById(R.id.editTextCNic);
        businessRegno = (EditText) rootView.findViewById(R.id.editText3);
        addressline1 = (EditText) rootView.findViewById(R.id.editText7);
        addressline2 = (EditText) rootView.findViewById(R.id.editText8);
        city = (EditText) rootView.findViewById(R.id.editText9);
        mobile = (EditText) rootView.findViewById(R.id.editText10);
        phone = (EditText) rootView.findViewById(R.id.editText11);
        fax = (EditText) rootView.findViewById(R.id.editText12);
        emailaddress = (EditText) rootView.findViewById(R.id.editText20);
        route = (EditText) rootView.findViewById(R.id.spinner4);
        district = (EditText) rootView.findViewById(R.id.spinner5);
        town = (EditText) rootView.findViewById(R.id.spinner3);



        btn_Town = (ImageButton) rootView.findViewById(R.id.btn_T);
        btn_Route = (ImageButton) rootView.findViewById(R.id.btn_R);
        btn_District = (ImageButton) rootView.findViewById(R.id.btn_D);
        CustomerbtnSearch = (ImageButton) rootView.findViewById(R.id.btn_C6);

        mySwitch = (Switch) rootView.findViewById(R.id.switch1);
        //-----------------------------------------------------------------------------------------------------------------

        //show new customer ref no
        if (mySwitch.isChecked() == true) {
//            customerName.requestFocus();
//            customerCode.setText(referenceNum.getCurrentRefNo(getResources().getString(R.string.newCusVal)));
//            CustomerbtnSearch.setEnabled(false);
        } else {
            CustomerbtnSearch.setEnabled(true);
        }


        btn_District.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodcutDetailsDialogbox(1);

            }
        });
        btn_Town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodcutDetailsDialogbox(2);
            }
        });
        btn_Route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodcutDetailsDialogbox(3);
            }
        });


        //get old customers
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    referenceNum = new ReferenceNum(getActivity());
//                    try {
//                        nCustomerNo = referenceNum.getCurrentRefNo(getResources().getString(R.string.NCNumVal));
//                        customerCode.setText(nCustomerNo);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }

                    CUSFLG = 1;
                    customerName.setFocusable(true);
                    customerName.setEnabled(true);
                    customerName.setClickable(true);
                    customerName.setFocusableInTouchMode(true);

                    CustomerController TW = new CustomerController(getActivity());

                    customerName.setEnabled(true);
                    editTextCNic.setEnabled(true);
                    OtherCode.setEnabled(true);
                    businessRegno.setEnabled(true);
                    addressline1.setEnabled(true);
                    addressline2.setEnabled(true);
                    mobile.setEnabled(true);
                    phone.setEnabled(true);
                    fax.setEnabled(true);
                    emailaddress.setEnabled(true);
                    route.setEnabled(true);
                    district.setEnabled(true);
                    town.setEnabled(true);

                    city.setEnabled(true);
                    btn_Town.setEnabled(true);
                    btn_Route.setEnabled(true);
                    btn_District.setEnabled(true);

                    customerName.setText("");
                    editTextCNic.setText("");
                    //OtherCode.setText("");
                    businessRegno.setText("");
                    addressline1.setText("");
                    addressline2.setText("");
                    city.setText("");
                    mobile.setText("");
                    phone.setText("");
                    fax.setText("");
                    emailaddress.setText("");
                    route.setText("");
                    district.setText("");
                    town.setText("");


                } else {
                    //WHEN NEW CUSTOMER MODE OFF
                    customerName.setFocusable(false);
                    customerName.setEnabled(false);
                    customerName.setClickable(false);
                    customerName.setFocusableInTouchMode(false);


                    customerName.setEnabled(false);
                    editTextCNic.setEnabled(false);
                    OtherCode.setEnabled(false);
                    businessRegno.setEnabled(false);
                    addressline1.setEnabled(false);
                    addressline2.setEnabled(false);
                    mobile.setEnabled(false);
                    phone.setEnabled(false);
                    fax.setEnabled(false);
                    emailaddress.setEnabled(false);
                    route.setEnabled(false);
                    district.setEnabled(false);
                    town.setEnabled(false);
                    city.setEnabled(false);
                    btn_Town.setEnabled(false);
                    btn_Route.setEnabled(false);
                    btn_District.setEnabled(false);
                    OtherCode.setText("");
                    CUSFLG = 0;
                    customerCode.setText("");
                }
            }
        });

        //save new customer
        rootView.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (CUSFLG == 1) {

                    // if (customerName.getText().length() != 0 && addressline1.getText().length() != 0 && addressline2.getText().length() != 0 && mobile.getText().length() != 0 && town.getText().length() != 0 && route.getText().length() != 0 && city.getText().length() != 0) {
                    if (customerName.getText().length() != 0 && addressline1.getText().length() != 0 && addressline2.getText().length() != 0 && mobile.getText().length() != 0) {

                        if (isEmailValid(emailaddress.getText().toString()) == false) {
                            Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
                        }  else {
                            //submit form


                            RouteController RO = new RouteController(getActivity());

                            DateFormat Dformat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();

                            //SalRepDS fSalRepDS = new SalRepDS(getActivity());
                            ReferenceDetailDownloader branchDS = new ReferenceDetailDownloader(getActivity());

                            GPSTracker gpsTracker = new GPSTracker(getActivity());
                            referenceNum = new ReferenceNum(getActivity());

                            NewCustomer customer = new NewCustomer();
                            customer.setC_REGNUM(businessRegno.getText().toString());
                            customer.setNAME(customerName.getText().toString());
                            customer.setCUSTOMER_NIC(editTextCNic.getText().toString());
                            customer.setCUSTOMER_ID(customerCode.getText().toString());
                            // customer.setROUTE_ID(SharedPref.getInstance(getActivity()).getLoginUser().getRoute());
                            customer.setADDRESS1(addressline1.getText().toString());
                            customer.setADDRESS2(addressline2.getText().toString());
                            customer.setCITY(city.getText().toString());
                            customer.setMOBILE(mobile.getText().toString());
                            customer.setPHONE(phone.getText().toString());
                            customer.setFAX(fax.getText().toString());
                            customer.setE_MAIL(emailaddress.getText().toString());
                            customer.setC_SYNCSTATE("N");
                            customer.setAddMac("NA");
                            customer.setC_ADDDATE(Dformat.format(date));
                            customer.setC_LATITUDE("" + gpsTracker.getLatitude());
                            customer.setC_LONGITUDE("" + gpsTracker.getLongitude());
                            // customer.setnNumVal(referenceNum.getCurrentRefNo(getResources().getString(R.string.newCusVal)));
                            customer.setnNumVal("1");
                            customer.setTxnDate(Dformat.format(date));
                            customer.setCONSOLE_DB(localSP.getString("Console_DB", "").toString());


                            ArrayList<NewCustomer> cusList = new ArrayList<>();
                            cusList.add(customer);


                            NewCustomerController customerDS = new NewCustomerController(getActivity());
                            int result = customerDS.createOrUpdateCustomer(cusList);

                            if (result > 0) {

                                Toast.makeText(getActivity(), "New Customer saved", Toast.LENGTH_SHORT).show();
                                ClearFiled();

                                //insert current NC number for next num generation
                                referenceNum = new ReferenceNum(getActivity());
                                referenceNum.NumValueUpdate(getResources().getString(R.string.newCusVal));
                                try {
                                    if (NetworkUtil.isNetworkAvailable(getActivity())) {
                                        //upload to master
                                        // new UploadNewCustomer(getActivity(), AddNewCusRegistration.this, UPLOAD_NEW_CUSTOMER, cusList).execute();
                                    }else{
                                        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                                    }
                                }catch (Exception e){
                                    Log.v("CUSTOMER REG>>>>","ERROR.....");
                                    e.printStackTrace();
                                }
                            }

                        }

                    } else {
                        Toast.makeText(getActivity(), "Please fill all compulsory fields", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


//        fabExit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                exitData();
//            }
//        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (customerName.getText().length() != 0
//                        && addressline1.getText().length() != 0 && addressline2.getText().length() != 0
//                        && mobile.getText().length() != 0
//                        && town.getText().length() != 0 && district.getText().length() != 0 && route.getText().length() != 0 && city.getText().length() != 0) {
//                    SaveAndUplord();
//
//                } else {
//                    Snackbar snackbar = Snackbar.make(v, "Fill All the Fields",
//                            Snackbar.LENGTH_LONG);
//                    View snackBarView = snackbar.getView();
//                    snackBarView.setBackgroundColor(Color.RED);
//                    snackbar.show();
//                }
//
//            }
//        });
        CustomerbtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodcutDetailsDialogbox(4);
            }
        });
        //get old customer for update record
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    customerName.requestFocus();
                    customerCode.setText(referenceNum.getCurrentRefNo(getResources().getString(R.string.newCusVal)));
                    CustomerbtnSearch.setEnabled(false);

                    editTextCNic.setEnabled(true);
                    customerCode.setEnabled(true);
                    customerName.setEnabled(true);
//                    OtherCode.setEnabled(true);
                    businessRegno.setEnabled(true);
                    btn_District.setEnabled(true);
                    btn_Town.setEnabled(true);
                    btn_Route.setEnabled(true);
                    addressline1.setEnabled(true);
                    addressline2.setEnabled(true);
                    city.setEnabled(true);
                    mobile.setEnabled(true);
                    phone.setEnabled(true);
                    fax.setEnabled(true);
                    emailaddress.setEnabled(true);
                } else {
                    CustomerbtnSearch.setEnabled(true);

                    customerCode.setText("");
                    customerCode.setEnabled(false);
                    customerName.setEnabled(false);
                    editTextCNic.setEnabled(false);
                    businessRegno.setEnabled(false);
                    btn_District.setEnabled(false);
                    btn_Town.setEnabled(false);
                    btn_Route.setEnabled(false);
                    addressline1.setEnabled(false);
                    addressline2.setEnabled(false);
                    city.setEnabled(false);
                    mobile.setEnabled(false);
                    phone.setEnabled(false);
                    fax.setEnabled(false);
                    emailaddress.setEnabled(false);
                }
            }
        });

//        fabDiscard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ClearFiled();
//            }
//        });

        //DISABLED BACK NAVIGATION
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("", "keyCode: " + keyCode);


                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Toast.makeText(getActivity(), "Back button disabled!", Toast.LENGTH_SHORT).show();
                    ActivityHome.navigation.setVisibility(View.VISIBLE);
                    return true;
                } else if ((keyCode == KeyEvent.KEYCODE_HOME)) {

                    getActivity().finish();

                    return true;

                } else {
                    return false;
                }
            }
        });


        return rootView;
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    //---------------------------------Get Details from db---------------------------------------
    public void prodcutDetailsDialogbox(final int Flag) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.details_search_item);
        dialog.setCancelable(true);

        final SearchView search = (SearchView) dialog.findViewById(R.id.et_search);
        final ListView Detlist = (ListView) dialog.findViewById(R.id.lv_product_list);

        final RouteController routeDS = new RouteController(getActivity());
        final NewCustomerController newCustomerDS = new NewCustomerController(getActivity());

        Detlist.clearTextFilter();
        if (Flag == 1) {
            //Detlist.setAdapter(new DistrictAdapter(getActivity(), fDistrictArrayList));
            town.setText("");
            route.setText("");
        } else if (Flag == 2) {
            //Detlist.setAdapter(new TownAdapter(getActivity(), townArrayList));
            route.setText("");
        } else if (Flag == 3) {
            routeArrayList = routeDS.getRoute();
            Detlist.setAdapter(new RouteAdapter(getActivity(), routeArrayList));

        } else if (Flag == 4) {
            newCustomerArrayList = newCustomerDS.getAllNewCusDetailsForEdit("");
            Detlist.setAdapter(new CustomerRegListAdapter(getActivity(), newCustomerArrayList));

        }
//================================================
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (Flag == 1) {
                        //Detlist.setAdapter(new DistrictAdapter(getActivity(), fDistrictArrayList));

                    } else if (Flag == 3) {
                        routeArrayList = routeDS.getRoute();
                        Detlist.setAdapter(new RouteAdapter(getActivity(), routeArrayList));
                    } else if (Flag == 4) {
                        newCustomerArrayList = newCustomerDS.getAllNewCusDetailsForEdit("");
                        Detlist.setAdapter(new CustomerRegListAdapter(getActivity(), newCustomerArrayList));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }
        });
        //-=========================================
        Detlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Flag == 3) {
                    route.setText(routeArrayList.get(position).getFROUTE_ROUTECODE());

                } else if (Flag == 4) {
                    editTextCNic.setEnabled(true);
                    customerName.setEnabled(true);
//                    OtherCode.setEnabled(true);
                    businessRegno.setEnabled(true);
                    addressline1.setEnabled(true);
                    addressline2.setEnabled(true);
                    city.setEnabled(true);
                    mobile.setEnabled(true);
                    phone.setEnabled(true);
                    fax.setEnabled(true);
                    emailaddress.setEnabled(true);

                    btn_District.setEnabled(true);
                    btn_Town.setEnabled(true);
                    btn_Route.setEnabled(true);


                    customerCode.setText(newCustomerArrayList.get(position).getCUSTOMER_ID());
                    customerName.setText(newCustomerArrayList.get(position).getNAME());
//                    OtherCode.setText(newCustomerArrayList.get(position).getC_OTHERCODE());
                    businessRegno.setText(newCustomerArrayList.get(position).getC_REGNUM());
                    editTextCNic.setText(newCustomerArrayList.get(position).getCUSTOMER_NIC());
                    addressline1.setText(newCustomerArrayList.get(position).getADDRESS1());
                    addressline2.setText(newCustomerArrayList.get(position).getADDRESS2());


                    RouteController routeDS1 = new RouteController(getActivity());
                    route.setText(newCustomerArrayList.get(position).getROUTE_ID());

                    if (newCustomerArrayList.get(position).getCITY().isEmpty()) {
                        city.setText("N/A");
                    } else {
                        city.setText(newCustomerArrayList.get(position).getCITY());
                    }

                    if (newCustomerArrayList.get(position).getMOBILE().isEmpty()) {
                        mobile.setText("-");
                    } else {
                        mobile.setText(newCustomerArrayList.get(position).getMOBILE());
                    }

                    if (newCustomerArrayList.get(position).getPHONE().isEmpty()) {
                        phone.setText("N/A");
                    } else {
                        phone.setText(newCustomerArrayList.get(position).getPHONE());
                    }

                    if (newCustomerArrayList.get(position).getFAX().isEmpty()) {
                        fax.setText("N/A");
                    } else {
                        fax.setText(newCustomerArrayList.get(position).getFAX());
                    }

                    if (newCustomerArrayList.get(position).getE_MAIL().isEmpty()) {
                        emailaddress.setText("N/A");
                    } else {
                        emailaddress.setText(newCustomerArrayList.get(position).getE_MAIL());
                    }

                }


                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void SaveAndUplord() {
        ArrayList<NewCustomer> cusList = new ArrayList<NewCustomer>();
        cusList.clear();
        NewCustomerController newCustomerDS = new NewCustomerController(getActivity());
        NewCustomer newCustomer = new NewCustomer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        ///  SalRepDS fSalRe = new SalRepDS(getActivity());

        ReferenceDetailDownloader branchDS = new ReferenceDetailDownloader(getActivity());

        newCustomer.setnNumVal(branchDS.getCurrentNextNumVal(getActivity().getResources().getString(R.string.newCusVal)));
        newCustomer.setCONSOLE_DB(localSP.getString("Console_DB", "").toString());
        //     newCustomer.setC_REPCODE(fSalRe.getCurrentRepCode());
        newCustomer.setCUSTOMER_ID(customerCode.getText().toString());
//        newCustomer.setC_OTHERCODE(OtherCode.getText().toString());
        newCustomer.setNAME(customerName.getText().toString());
        newCustomer.setCUSTOMER_NIC(editTextCNic.getText().toString());
        newCustomer.setADDRESS1(addressline1.getText().toString());
        newCustomer.setADDRESS2(addressline2.getText().toString());
        newCustomer.setCITY(city.getText().toString());

        newCustomer.setPHONE(phone.getText().toString());
        newCustomer.setMOBILE(mobile.getText().toString());
        newCustomer.setFAX(fax.getText().toString());
        newCustomer.setE_MAIL(emailaddress.getText().toString());
        newCustomer.setROUTE_ID(route.getText().toString());//
        newCustomer.setOLD_CODE("NA");

        newCustomer.setCUSTOMER_ID(customerCode.getText().toString());
//        newCustomer.setC_OTHERCODE(OtherCode.getText().toString());



        if (businessRegno.getText().toString().isEmpty()) {
            newCustomer.setC_REGNUM("NA");
        } else {
            newCustomer.setC_REGNUM(businessRegno.getText().toString());
        }

        newCustomer.setC_ADDDATE(dateFormat.format(date));
        newCustomer.setC_LATITUDE("0.000");
        newCustomer.setC_LONGITUDE("0.000");
        newCustomer.setAddMac("0");
        newCustomer.setC_SYNCSTATE("0");

        cusList.add(newCustomer);
        if (newCustomerDS.createOrUpdateCustomer(cusList) > 0) {
            referenceNum.NumValueUpdate(getResources().getString(R.string.newCusVal));

            android.widget.Toast.makeText(getActivity(), " saved successfully..!", android.widget.Toast.LENGTH_SHORT).show();
        } else {
            android.widget.Toast.makeText(getActivity(), "Failed..", android.widget.Toast.LENGTH_SHORT).show();
        }
    }


    //--------------------------------------------------------------------------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }



    public void ClearFiled() {
        customerName.setText("");
        editTextCNic.setText("");
//        OtherCode.setText("");
        businessRegno.setText("");
        district.setText("");
        town.setText("");
        route.setText("");
        addressline1.setText("");
        addressline2.setText("");
        city.setText("");
        mobile.setText("");
        phone.setText("");
        fax.setText("");
        emailaddress.setText("");

    }


    private void exitData() {
        // UtilityContainer.mLoadFragment(new CustomerRegMain(), getActivity());
        android.widget.Toast.makeText(getActivity(), "Success", android.widget.Toast.LENGTH_LONG).show();
    }


    @Override
    public void onDetach() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("OnResume", "oo");

    }

    @Override
    public void onTaskCompleted(TaskType taskType) {
        Toast.makeText(getActivity(), "New Customer Uploaded Successfuly", Toast.LENGTH_SHORT).show();


//        NewCustomerController customerDS = new NewCustomerController(getActivity());
//        UtilityContainer.mLoadFragment(new CustomerRegMain(), getActivity());
//        CustomerRegMain registration = new CustomerRegMain();
//        getFragmentManager().beginTransaction().replace(
//                R.id.fragmentContainer, registration)
//                .commit();

    }
}
