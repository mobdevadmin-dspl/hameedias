package com.datamation.hmdsfa.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.api.ApiCllient;
import com.datamation.hmdsfa.api.ApiInterface;
import com.datamation.hmdsfa.controller.BankController;
import com.datamation.hmdsfa.controller.CompanyDetailsController;
import com.datamation.hmdsfa.controller.CustomerController;
//import com.datamation.sfa.controller.ItemController;
import com.datamation.hmdsfa.controller.DiscdebController;
import com.datamation.hmdsfa.controller.DiscdetController;
import com.datamation.hmdsfa.controller.DischedController;
import com.datamation.hmdsfa.controller.DiscslabController;
import com.datamation.hmdsfa.controller.ExpenseController;
import com.datamation.hmdsfa.controller.FItenrDetController;
import com.datamation.hmdsfa.controller.FItenrHedController;
import com.datamation.hmdsfa.controller.FreeHedController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.ItemLocController;
import com.datamation.hmdsfa.controller.ItemPriceController;
import com.datamation.hmdsfa.controller.LocationsController;
import com.datamation.hmdsfa.controller.NearCustomerController;
import com.datamation.hmdsfa.controller.ReasonController;
import com.datamation.hmdsfa.controller.ReferenceDetailDownloader;
import com.datamation.hmdsfa.controller.ReferenceSettingController;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.controller.TownController;
import com.datamation.hmdsfa.dialog.CustomProgressDialog;
import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Bank;
import com.datamation.hmdsfa.model.CompanyBranch;
import com.datamation.hmdsfa.model.CompanySetting;
import com.datamation.hmdsfa.model.Control;
import com.datamation.hmdsfa.model.Debtor;
import com.datamation.hmdsfa.model.Discdeb;
import com.datamation.hmdsfa.model.Discdet;
import com.datamation.hmdsfa.model.Disched;
import com.datamation.hmdsfa.model.Discslab;
import com.datamation.hmdsfa.model.Expense;
import com.datamation.hmdsfa.model.FirebaseData;
import com.datamation.hmdsfa.model.FItenrDet;
import com.datamation.hmdsfa.model.FItenrHed;
import com.datamation.hmdsfa.model.FreeHed;
import com.datamation.hmdsfa.model.Item;
import com.datamation.hmdsfa.model.ItemLoc;
import com.datamation.hmdsfa.model.ItemPri;
import com.datamation.hmdsfa.model.Locations;
import com.datamation.hmdsfa.model.NearDebtor;
import com.datamation.hmdsfa.model.Reason;
import com.datamation.hmdsfa.model.Route;
import com.datamation.hmdsfa.model.RouteDet;
import com.datamation.hmdsfa.model.SalRep;
import com.datamation.hmdsfa.model.Town;
import com.datamation.hmdsfa.model.User;
import com.datamation.hmdsfa.model.apimodel.ReadJsonList;
import com.datamation.hmdsfa.utils.NetworkUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;
    TextView txtver;
    SharedPref pref;
    SalRep loggedUser;
    NetworkFunctions networkFunctions;
    private static String spURL = "";
    int tap;
    SalRep salRep;
    RelativeLayout loginlayout;
    private long timeInMillis;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private Context context = this;
    DatabaseReference rootRef;
    ArrayList<FirebaseData> imgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        networkFunctions = new NetworkFunctions(this);
        pref = SharedPref.getInstance(this);
        username = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);
        Button login = (Button) findViewById(R.id.btnlogin);
        txtver = (TextView) findViewById(R.id.textVer);
        loginlayout = (RelativeLayout) findViewById(R.id.loginLayout);
        txtver.setText("Version " + getVersionCode());
        loggedUser = pref.getLoginUser();
        timeInMillis = System.currentTimeMillis();

        login.setOnClickListener(this);

        txtver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tap += 1;
                // StartTimer(3000);
                if (tap >= 7) {
                    validateDialog();
                }
            }
        });

        rootRef = FirebaseDatabase.getInstance().getReference();

    }


    private void validateDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View promptView = layoutInflater.inflate(R.layout.ip_connection_dailog_login, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        alertDialogBuilder.setView(promptView);
        final EditText input = (EditText) promptView.findViewById(R.id.txt_Enter_url);
        final EditText inputConsole = (EditText) promptView.findViewById(R.id.txt_console_db);
        final EditText inputDistributor = (EditText) promptView.findViewById(R.id.txt_dist_db);

        input.setText(pref.getBaseURL().substring(7));
        inputConsole.setText(pref.getConsoleDB());
        inputDistributor.setText(pref.getDistDB());

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String URL = "http://" + input.getText().toString().trim();
                pref.setBaseURL(URL);
                if (URL.length() != 0) {
                    //   pref.setDBNAME(DBNAME);
//                    if (Patterns.WEB_URL.matcher(URL).matches()&& URL.length()== 26)
                    if (Patterns.WEB_URL.matcher(URL).matches()) {
                        if (NetworkUtil.isNetworkAvailable(ActivityLogin.this)) {
                            pref.setBaseURL(URL);
                            pref.setDistDB(inputDistributor.getText().toString().trim());
                            Log.d("myapp", inputDistributor.getText().toString().trim());
                            //pref.setConsoleDB(inputConsole.getText().toString().trim());
                            new Validate(pref.getMacAddress().trim(), URL).execute();
                            //TODO: validate uname pwd with server details
//                            String debtorURL = getResources().getString(R.string.ConnURL) + "/fSalrep/mobile123/"+pref.getDBNAME() +"/"+ pref.getMacAddress().replace(":", "");
//                            // String URL = getResources().getString(R.string.ConnectionURL) + "/femployee/mobile123/" + databaseName + "/" + UIdStr.toString() + "/" + UserNameStr.toString();
//                            new Downloader(SplashActivity.this, SplashActivity.this, FSALREP, URL, debtorURL).execute();
                            //me tika wenna one username pwd server yawala validate unata passe..

                            //pref.setFirstTimeLaunch(true);


                        } else {
                            Snackbar snackbar = Snackbar.make(promptView, R.string.txt_msg, Snackbar.LENGTH_LONG);
                            View snackbarLayout = snackbar.getView();
                            snackbarLayout.setBackgroundColor(Color.RED);
                            TextView textView = (TextView) snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_signal_wifi_off_black_24dp, 0, 0, 0);
                            textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.body_size));
                            textView.setTextColor(Color.WHITE);
                            snackbar.show();
                            reCallActivity();
                        }

                    } else {
                        Toast.makeText(ActivityLogin.this, "Invalid URL Entered. Please Enter Valid URL.", Toast.LENGTH_LONG).show();
                        reCallActivity();
                    }

                } else {
                    Toast.makeText(ActivityLogin.this, "Please fill informations", Toast.LENGTH_LONG).show();
                    validateDialog();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                ActivityLogin.this.finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void reCallActivity() {
        Intent mainActivity = new Intent(ActivityLogin.this, ActivityLogin.class);
        startActivity(mainActivity);
        finish();
    }

    public void StartTimer(int timeout) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tap = 0;
            }
        }, timeout);

    }

    public String getVersionCode() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "0";

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogin: {
                salRep = new SalRepController(getApplicationContext()).getSalRepCredentials();

                if (pref.isLoggedIn() || SharedPref.getInstance(ActivityLogin.this).getLoginUser() != null) {
                    if ((username.getText().toString().equalsIgnoreCase(salRep.getRepCode())) && (password.getText().toString().equalsIgnoreCase(salRep.getREPTCODE()))) {
                        pref.setLoginStatus(true);
                        Log.d(">>>", "Validation :: " + username.getText().toString());
                        Log.d(">>>", "Validation :: " + salRep.getRepCode());
                        Log.d(">>>", "Validation :: " + password.getText().toString());
                        Log.d(">>>", "Validation :: " + salRep.getREPTCODE());
                        Intent intent = new Intent(ActivityLogin
                                .this, ActivityHome
                                .class);
                        startActivity(intent);
                        finish();
                    }

                } else if ((username.getText().toString().equalsIgnoreCase(salRep.getRepCode())) && (password.getText().toString().equalsIgnoreCase(salRep.getREPTCODE()))) {
                    //temparary for datamation
                    Log.d(">>>", "Validation :: " + username.getText().toString());
                    Log.d(">>>", "Validation :: " + salRep.getRepCode());
                    Log.d(">>>", "Validation :: " + password.getText().toString());
                    Log.d(">>>", "Validation :: " + salRep.getREPTCODE());

                    SharedPref sharedPref = SharedPref.getInstance(context);
                    if (sharedPref.getGlobalVal("SyncDate").equalsIgnoreCase(dateFormat.format(new Date(timeInMillis))) || sharedPref.getGlobalVal("FirstTimeSyncDate").equalsIgnoreCase(dateFormat.format(new Date(timeInMillis)))) {
                        pref.setLoginStatus(true);
                        Intent intent = new Intent(ActivityLogin
                                .this, ActivityHome
                                .class);
                        startActivity(intent);
                        finish();
                    } else {
                        new Authenticate(SharedPref.getInstance(this).getLoginUser().getRepCode()).execute();
                    }
//                    Intent intent = new Intent(ActivityLogin
//                            .this, ActivityHome
//                            .class);
//                    startActivity(intent);
//                    finish();


//                    String decrepted = getMD5HashVal(password.getText().toString());
//                    String logged = loggedUser.getPassword();
//                   if(!(username.getText().toString().equals(loggedUser.getUserName())) || !(decrepted.equals(loggedUser.getPassword()))){
//                       Toast.makeText(this,"Invalid Username or Password",Toast.LENGTH_LONG).show();
//
//                    }else{
//                       Toast.makeText(this,"Username and Password are correct",Toast.LENGTH_LONG).show();
//
//                       new Authenticate(username.getText().toString(), password.getText().toString(), loggedUser.getCode()).execute();
//                    }

                } else {
                    Log.d(">>>", "Validation :: " + username.getText().toString());
                    Log.d(">>>", "Validation :: " + salRep.getRepCode());
                    Log.d(">>>", "Validation :: " + password.getText().toString());
                    Log.d(">>>", "Validation :: " + salRep.getREPTCODE());
                    Toast.makeText(this, "Please fill the valid credentials", Toast.LENGTH_LONG).show();
                    username.setText("");
                    password.setText("");
                }


            }
            break;

            default:
                break;
        }
    }

    //--
//    private void LoginValidation() {
//        SalRepDS ds = new SalRepDS(getApplicationContext());
//        ArrayList<SalRep> list = ds.getSaleRepDetails();
//        for (SalRep salRep : list) {
//
//            if (salRep.getREPCODE().equals(username.getText().toString().toUpperCase()) && salRep.getNAME().equals(password.getText().toString().toUpperCase())) {
//
//                StartApp();
//
//            } else {
//                Toast.makeText(getApplicationContext(), "Invalid username or password.", Toast.LENGTH_LONG).show();
//
//            }
//        }
//    }


    private class Authenticate extends AsyncTask<String, Integer, Boolean> {
        int totalRecords = 0;
        CustomProgressDialog pdialog;
        private String uname, pwd, repcode;
        private List<String> errors = new ArrayList<>();

        //        public Authenticate(String uname, String pwd, String repCode){
//            this.uname = uname;
//            this.pwd = pwd;
//            this.repcode = repCode;
//            this.pdialog = new CustomProgressDialog(ActivityLogin.this);
//        }
        public Authenticate(String repCode) {
            this.repcode = repCode;
            this.pdialog = new CustomProgressDialog(ActivityLogin.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new CustomProgressDialog(ActivityLogin.this);
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Authenticating...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            int totalBytes = 0;

            try {
//                if ((username.getText().toString().equals(uname)) && (password.getText().toString().equals(pwd))
//                        ) {



/*****************Customers**********************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Authenticated\nDownloading company control data...");
                    }
                });

                String controls = "";
                try {
                    controls = networkFunctions.getCompanyDetails(repcode);
                    // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                } catch (IOException e) {
                    e.printStackTrace();
                    errors.add(e.toString());
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (Company details)...");
                    }
                });

                // Processing outlets
                try {
                    JSONObject controlJSON = new JSONObject(controls);
                    JSONArray controlJSONArray = controlJSON.getJSONArray("fControlResult");
                    ArrayList<Control> controlList = new ArrayList<Control>();
                    CompanyDetailsController companyController = new CompanyDetailsController(ActivityLogin.this);
                    for (int i = 0; i < controlJSONArray.length(); i++) {
                        controlList.add(Control.parseControlDetails(controlJSONArray.getJSONObject(i)));
                    }
                    companyController.createOrUpdateFControl(controlList);
                } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);
                    errors.add(e.toString());
                    throw e;
                }
/*****************end Customers**********************************************************************/

/*****************Customers**********************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Control downloaded\nDownloading Customers...");
                    }
                });

                String outlets = "";
                try {
                    outlets = networkFunctions.getCustomer(repcode);
                    // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                } catch (IOException e) {
                    e.printStackTrace();
                    errors.add(e.toString());
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (customer details)...");
                    }
                });
                CustomerController customerController = new CustomerController(ActivityLogin.this);
                customerController.deleteAll();
                // Processing outlets
                try {
                    JSONObject customersJSON = new JSONObject(outlets);
                    JSONArray customersJSONArray = customersJSON.getJSONArray("FdebtorResult");
                    ArrayList<Debtor> customerList = new ArrayList<Debtor>();

                    for (int i = 0; i < customersJSONArray.length(); i++) {
                        customerList.add(Debtor.parseOutlet(customersJSONArray.getJSONObject(i)));
                    }
                    customerController.InsertOrReplaceDebtor(customerList);

                } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);
                    errors.add(e.toString());
                    throw e;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Customers downloaded\nDownloading Company Settings...");
                    }
                });
                /*****************end Customers**********************************************************************/

                // ----------------Near Customer-------------------- Nuwan ------------- 17/10/2019--------------------------

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Downloading Near Customers...");
                    }
                });

                String nearOutlets = "";
                try {
                    nearOutlets = networkFunctions.getNearCustomer();

                } catch (IOException e) {
                    e.printStackTrace();
                    errors.add(e.toString());
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (near customer details)...");
                    }
                });

                // Processing outlets
                try {
                    JSONObject nCustomersJSON = new JSONObject(nearOutlets);
                    JSONArray nCustomersJSONArray = nCustomersJSON.getJSONArray("FnearDebtorResult");
                    ArrayList<NearDebtor> nDebList = new ArrayList<NearDebtor>();
                    NearCustomerController nCustomerController = new NearCustomerController(ActivityLogin.this);
                    for (int i = 0; i < nCustomersJSONArray.length(); i++) {
                        nDebList.add(NearDebtor.parseNearOutlet(nCustomersJSONArray.getJSONObject(i)));
                    }

                    nCustomerController.InsertOrReplaceNearDebtor(nDebList);

                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
                    throw e;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Near Customers downloaded\nDownloading Company Settings...");
                    }
                });

                // --------------------------------------------------------------------------------------------------


                /*****************Settings*****************************************************************************/

                String comSettings = "";
                try {
                    comSettings = networkFunctions.getReferenceSettings();
                    // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                } catch (IOException e) {
                    e.printStackTrace();
                    errors.add(e.toString());
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (setting details)...");
                    }
                });

                // Processing company settings
                try {
                    JSONObject settingJSON = new JSONObject(comSettings);
                    JSONArray settingsJSONArray = settingJSON.getJSONArray("fCompanySettingResult");
                    ArrayList<CompanySetting> settingList = new ArrayList<CompanySetting>();
                    ReferenceSettingController settingController = new ReferenceSettingController(ActivityLogin.this);
                    for (int i = 0; i < settingsJSONArray.length(); i++) {
                        settingList.add(CompanySetting.parseSettings(settingsJSONArray.getJSONObject(i)));
                    }
                    settingController.createOrUpdateFCompanySetting(settingList);
                } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);
                    errors.add(e.toString());
                    throw e;
                }

                /*****************end Settings**********************************************************************/
/*****************Branches*****************************************************************************/

                String comBranches = "";
                try {
                    comBranches = networkFunctions.getReferences(repcode);
                    // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                } catch (IOException e) {
                    e.printStackTrace();
                    errors.add(e.toString());

                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (setting details)...");
                    }
                });

                // Processing company settings
                try {
                    JSONObject settingJSON = new JSONObject(comBranches);
                    JSONArray settingsJSONArray = settingJSON.getJSONArray("FCompanyBranchResult");
                    ArrayList<CompanyBranch> settingList = new ArrayList<CompanyBranch>();
                    ReferenceDetailDownloader settingController = new ReferenceDetailDownloader(ActivityLogin.this);
                    for (int i = 0; i < settingsJSONArray.length(); i++) {
                        settingList.add(CompanyBranch.parseSettings(settingsJSONArray.getJSONObject(i)));
                    }
                    settingController.createOrUpdateFCompanyBranch(settingList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }

                /*****************end Settings**********************************************************************/
                /*****************Item Loc*****************************************************************************/

                String itemLocs = "";
                try {
                    itemLocs = networkFunctions.getItemLocations(repcode);
                    // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (item location details)...");
                    }
                });

                // Processing itemLocations
                try {
                    JSONObject itemLocJSON = new JSONObject(itemLocs);
                    JSONArray settingsJSONArray = itemLocJSON.getJSONArray("fItemLocResult");
                    ArrayList<ItemLoc> itemLocList = new ArrayList<ItemLoc>();
                    ItemLocController locController = new ItemLocController(ActivityLogin.this);
                    for (int i = 0; i < settingsJSONArray.length(); i++) {
                        itemLocList.add(ItemLoc.parseItemLocs(settingsJSONArray.getJSONObject(i)));
                    }
                    locController.InsertOrReplaceItemLoc(itemLocList);
                } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);
                    errors.add(e.toString());
                    throw e;
                }

                /*****************end Item Loc**********************************************************************/
                /*****************Locations*****************************************************************************/

                String locations = "";
                try {
                    locations = networkFunctions.getLocations(repcode);
                    // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                } catch (IOException e) {
                    e.printStackTrace();
                    errors.add(e.toString());
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (location details)...");
                    }
                });

                // Processing itemLocations
                try {
                    JSONObject locJSON = new JSONObject(locations);
                    JSONArray locJSONArray = locJSON.getJSONArray("fLocationsResult");
                    ArrayList<Locations> locList = new ArrayList<Locations>();
                    LocationsController locController = new LocationsController(ActivityLogin.this);
                    for (int i = 0; i < locJSONArray.length(); i++) {
                        locList.add(Locations.parseLocs(locJSONArray.getJSONObject(i)));
                    }
                    locController.createOrUpdateFLocations(locList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************ItemPrice*****************************************************************************/

                String itemPrices = "";
                try {
                    itemPrices = networkFunctions.getItemPrices(repcode);
                    // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (item price details)...");
                    }
                });

                // Processing itemLocations
                try {
                    JSONObject itemPriceJSON = new JSONObject(itemPrices);
                    JSONArray itemPriceJSONArray = itemPriceJSON.getJSONArray("fItemPriResult");
                    ArrayList<ItemPri> itemPriceList = new ArrayList<ItemPri>();
                    ItemPriceController priceController = new ItemPriceController(ActivityLogin.this);
                    for (int i = 0; i < itemPriceJSONArray.length(); i++) {
                        itemPriceList.add(ItemPri.parseItemPrices(itemPriceJSONArray.getJSONObject(i)));
                    }
                    priceController.InsertOrReplaceItemPri(itemPriceList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end item prices**********************************************************************/
                /*****************Item*****************************************************************************/

                String item = "";
                try {
                    item = networkFunctions.getItems(repcode);
                    // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (item price details)...");
                    }
                });

                // Processing item price
                try {
                    JSONObject itemJSON = new JSONObject(item);
                    JSONArray itemJSONArray = itemJSON.getJSONArray("fItemsResult");
                    ArrayList<Item> itemList = new ArrayList<Item>();
                    ItemController itemController = new ItemController(ActivityLogin.this);
                    for (int i = 0; i < itemJSONArray.length(); i++) {
                        itemList.add(Item.parseItem(itemJSONArray.getJSONObject(i)));
                    }
                    itemController.InsertOrReplaceItems(itemList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end item **********************************************************************/
                String reasons = "";
                try {
                    reasons = networkFunctions.getReasons();
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (reasons)...");
                    }
                });

                // Processing reasons
                try {
                    JSONObject reasonJSON = new JSONObject(reasons);
                    JSONArray reasonJSONArray = reasonJSON.getJSONArray("fReasonResult");
                    ArrayList<Reason> reasonList = new ArrayList<Reason>();
                    ReasonController reasonController = new ReasonController(ActivityLogin.this);
                    for (int i = 0; i < reasonJSONArray.length(); i++) {
                        reasonList.add(Reason.parseReason(reasonJSONArray.getJSONObject(i)));
                    }
                    reasonController.createOrUpdateReason(reasonList);

                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end reasons**********************************************************************/
                // Processing expense
                String expenses = "";
                try {
                    expenses = networkFunctions.getExpenses();
                } catch (IOException e) {
                    e.printStackTrace();
                    errors.add(e.toString());
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (expenses)...");
                    }
                });

                try {
                    JSONObject expenseJSON = new JSONObject(expenses);
                    JSONArray expenseJSONArray = expenseJSON.getJSONArray("fExpenseResult");
                    ArrayList<Expense> expensesList = new ArrayList<Expense>();
                    ExpenseController expenseController = new ExpenseController(ActivityLogin.this);
                    for (int i = 0; i < expenseJSONArray.length(); i++) {
                        expensesList.add(Expense.parseExpense(expenseJSONArray.getJSONObject(i)));
                    }
                    expenseController.createOrUpdateFExpense(expensesList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end expenses**********************************************************************/
                /*****************Route**********************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Expenses downloaded\nDownloading route details...");
                    }
                });

                String route = "";
                try {
                    route = networkFunctions.getRoutes(repcode);
                } catch (IOException e) {
                    e.printStackTrace();
                    errors.add(e.toString());
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (routes)...");
                    }
                });

                // Processing route
                try {
                    JSONObject routeJSON = new JSONObject(route);
                    JSONArray routeJSONArray = routeJSON.getJSONArray("fRouteResult");
                    ArrayList<Route> routeList = new ArrayList<Route>();
                    RouteController routeController = new RouteController(ActivityLogin.this);
                    for (int i = 0; i < routeJSONArray.length(); i++) {
                        routeList.add(Route.parseRoute(routeJSONArray.getJSONObject(i)));
                    }

                    routeController.createOrUpdateFRoute(routeList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end route**********************************************************************/

                /*****************Route det**********************************************************************/

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pdialog.setMessage("Expenses downloaded\nDownloading route details...");
//                    }
//                });

                String routedet = "";
                try {
                    routedet = networkFunctions.getRouteDets(repcode);
                } catch (IOException e) {
                    e.printStackTrace();
                    errors.add(e.toString());
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (routes)...");
                    }
                });

                // Processing route
                try {
                    JSONObject routeJSON = new JSONObject(routedet);
                    JSONArray routeJSONArray = routeJSON.getJSONArray("fRouteDetResult");
                    ArrayList<RouteDet> routeList = new ArrayList<RouteDet>();
                    RouteDetController routeController = new RouteDetController(ActivityLogin.this);
                    for (int i = 0; i < routeJSONArray.length(); i++) {
                        routeList.add(RouteDet.parseRoute(routeJSONArray.getJSONObject(i)));
                    }
                    routeController.InsertOrReplaceRouteDet(routeList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end route det**********************************************************************/

                /*****************towns**********************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Expenses downloaded\nDownloading town details...");
                    }
                });

                String town = "";
                try {
                    town = networkFunctions.getTowns(repcode);
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (towns)...");
                    }
                });

                // Processing towns
                try {
                    JSONObject townJSON = new JSONObject(town);
                    JSONArray townJSONArray = townJSON.getJSONArray("fTownResult");
                    ArrayList<Town> townList = new ArrayList<Town>();
                    TownController townController = new TownController(ActivityLogin.this);
                    for (int i = 0; i < townJSONArray.length(); i++) {
                        townList.add(Town.parseTown(townJSONArray.getJSONObject(i)));
                    }
                    townController.createOrUpdateFTown(townList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end towns**********************************************************************/


                /*****************FreeHed**********************************************************************/
                String freehed = "";
                try {
                    freehed = networkFunctions.getFreeHed(repcode);
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (free)...");
                    }
                });
                FreeHedController freeHedController = new FreeHedController(ActivityLogin.this);
                freeHedController.deleteAll();
                // Processing freehed
                try {
                    JSONObject freeHedJSON = new JSONObject(freehed);
                    JSONArray freeHedJSONArray = freeHedJSON.getJSONArray("FfreehedResult");
                    ArrayList<FreeHed> freeHedList = new ArrayList<FreeHed>();
                    for (int i = 0; i < freeHedJSONArray.length(); i++) {
                        freeHedList.add(FreeHed.parseFreeHed(freeHedJSONArray.getJSONObject(i)));
                    }
                    freeHedController.createOrUpdateFreeHed(freeHedList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end freeHed**********************************************************************/


                /*****************Banks**********************************************************************/
                String banks = "";
                try {
                    banks = networkFunctions.getBanks();
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (banks)...");
                    }
                });

                // Processing route
                try {
                    JSONObject bankJSON = new JSONObject(banks);
                    JSONArray bankJSONArray = bankJSON.getJSONArray("fbankResult");
                    ArrayList<Bank> bankList = new ArrayList<Bank>();
                    BankController bankController = new BankController(ActivityLogin.this);
                    for (int i = 0; i < bankJSONArray.length(); i++) {
                        bankList.add(Bank.parseBank(bankJSONArray.getJSONObject(i)));
                    }
                    bankController.createOrUpdateBank(bankList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end banks**********************************************************************/

                /*****************discdeb**********************************************************************/
                String debdisc = "";
                try {
                    debdisc = networkFunctions.getDiscDeb(repcode);
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (discount)...");
                    }
                });

                // Processing discdeb
                DiscdebController discdebController = new DiscdebController(ActivityLogin.this);
                discdebController.deleteAll();
                try {
                    JSONObject discdebPriJSON = new JSONObject(debdisc);
                    JSONArray discdebJSONArray = discdebPriJSON.getJSONArray("FdiscdebResult");
                    ArrayList<Discdeb> discdebList = new ArrayList<Discdeb>();

                    for (int i = 0; i < discdebJSONArray.length(); i++) {
                        discdebList.add(Discdeb.parseDiscDeb(discdebJSONArray.getJSONObject(i)));
                    }
                    discdebController.createOrUpdateDiscdeb(discdebList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end discdeb**********************************************************************/
                /*****************discdet**********************************************************************/
                String discdet = "";
                try {
                    discdet = networkFunctions.getDiscDet();
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (discount)...");
                    }
                });


                DiscdetController discdetController = new DiscdetController(ActivityLogin.this);
                discdetController.deleteAll();
                // Processing discdeb
                try {
                    JSONObject discdetJSON = new JSONObject(discdet);
                    JSONArray discdetJSONArray = discdetJSON.getJSONArray("FdiscdetResult");
                    ArrayList<Discdet> discdetList = new ArrayList<Discdet>();
                    for (int i = 0; i < discdetJSONArray.length(); i++) {
                        discdetList.add(Discdet.parseDiscDet(discdetJSONArray.getJSONObject(i)));
                    }
                    discdetController.createOrUpdateDiscdet(discdetList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end discdet**********************************************************************/
                /*****************discshed**********************************************************************/
                String disched = "";
                try {
                    disched = networkFunctions.getDiscHed(repcode);
                } catch (IOException e) {
                    e.printStackTrace();
                    errors.add(e.toString());
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (discount)...");
                    }
                });

                // Processing discdeb

                DischedController dischedController = new DischedController(ActivityLogin.this);
                dischedController.deleteAll();
                try {
                    JSONObject dischedJSON = new JSONObject(disched);
                    JSONArray dischedJSONArray = dischedJSON.getJSONArray("FDischedResult");
                    ArrayList<Disched> dischedList = new ArrayList<Disched>();
                    for (int i = 0; i < dischedJSONArray.length(); i++) {
                        dischedList.add(Disched.parseDisched(dischedJSONArray.getJSONObject(i)));
                    }
                    dischedController.createOrUpdateDisched(dischedList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end discdet**********************************************************************/
                /*****************discslab*************************************************************************/
                String discslab = "";
                try {
                    discslab = networkFunctions.getDiscSlab();
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (discount)...");
                    }
                });

                // Processing discslab
                DiscslabController discslabController = new DiscslabController(ActivityLogin.this);
                discslabController.deleteAll();
                try {
                    JSONObject discslabJSON = new JSONObject(discslab);
                    JSONArray discslabJSONArray = discslabJSON.getJSONArray("FdiscslabResult");
                    ArrayList<Discslab> discslabList = new ArrayList<Discslab>();

                    for (int i = 0; i < discslabJSONArray.length(); i++) {
                        discslabList.add(Discslab.parseDiscslab(discslabJSONArray.getJSONObject(i)));
                    }
                    discslabController.createOrUpdateDiscslab(discslabList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end discslab**********************************************************************/
                /*****************itenary det**********************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Prices downloaded\nDownloading iteanery details...");
                    }
                });

                String itenarydet = "";
                try {
                    itenarydet = networkFunctions.getItenaryDet(repcode);
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (Iteanery)...");
                    }
                });

                // Processing itenarydet
                try {
                    FItenrDetController itenaryDetController = new FItenrDetController(ActivityLogin.this);
                    itenaryDetController.deleteAll();
                    JSONObject itenaryDetJSON = new JSONObject(itenarydet);
                    JSONArray itenaryDetJSONArray = itenaryDetJSON.getJSONArray("fItenrDetResult");
                    ArrayList<FItenrDet> itenaryDetList = new ArrayList<FItenrDet>();
                    for (int i = 0; i < itenaryDetJSONArray.length(); i++) {
                        itenaryDetList.add(FItenrDet.parseIteanaryDet(itenaryDetJSONArray.getJSONObject(i)));
                    }
                    itenaryDetController.createOrUpdateFItenrDet(itenaryDetList);
                } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);
                    errors.add(e.toString());
                    throw e;
                }
                /*****************end iteanerydet**********************************************************************/
                /*****************itenary hed**********************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Prices downloaded\nDownloading iteanery details...");
                    }
                });

                String itenaryhed = "";
                try {
                    itenaryhed = networkFunctions.getItenaryHed(repcode);
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (Iteanery)...");
                    }
                });

                // Processing itenaryhed
                try {
                    JSONObject itenaryHedJSON = new JSONObject(itenaryhed);
                    JSONArray itenaryHedJSONArray = itenaryHedJSON.getJSONArray("fItenrHedResult");
                    ArrayList<FItenrHed> itenaryHedList = new ArrayList<FItenrHed>();
                    FItenrHedController itenaryHedController = new FItenrHedController(ActivityLogin.this);
                    for (int i = 0; i < itenaryHedJSONArray.length(); i++) {
                        itenaryHedList.add(FItenrHed.parseIteanaryHed(itenaryHedJSONArray.getJSONObject(i)));
                    }
                    itenaryHedController.createOrUpdateFItenrHed(itenaryHedList);
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end itenaryhed**********************************************************************/

                /*****************invoice sale**********************************************************************/
/////////////////MMS2019-11-14////////////////
                String tmInvSale = "";
                try {
                    tmInvSale = networkFunctions.getTMInvoiceSale(repcode);
                } catch (IOException e) {
                    e.printStackTrace();
                    errors.add(e.toString());
                    throw e;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (Invoice sale)...");
                    }
                });

                try {
                    pref = SharedPref.getInstance(ActivityLogin.this);
                    pref.setTMInvSale("");

                    JSONObject invoiceSaleJSON = new JSONObject(tmInvSale);
                    String tminvoiceSale = invoiceSaleJSON.getString("invoiceSaleResult");
                    pref.setTMInvSale(tminvoiceSale);

                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
                    throw e;
                }

                String pmInvSale = "";
                try {
                    pmInvSale = networkFunctions.getPMInvoiceSale(repcode);
                } catch (IOException e) {
                    errors.add(e.toString());
                    e.printStackTrace();
                    throw e;
                }

                try {
                    SharedPref.getInstance(ActivityLogin.this).setPMInvSale("");

                    JSONObject invoiceSaleJSON = new JSONObject(pmInvSale);
                    String pminvoiceSale = invoiceSaleJSON.getString("invoiceSaleResult");
                    SharedPref.getInstance(ActivityLogin.this).setPMInvSale(pminvoiceSale);
                    /////////////////MMS2019-11-14////////////////
                } catch (JSONException | NumberFormatException e) {
                    errors.add(e.toString());
                    throw e;
                }

                /*****************invoice sale**********************************************************************/

                return true;
//        } else {
//            //errors.add("Please enter correct username and password");
//            return false;
//        }
            } catch (IOException e) {
                e.printStackTrace();
                errors.add(e.toString());
                // errors.add("Unable to reach the server.");

//                ErrorUtil.logException(LoginActivity.this, "LoginActivity -> Authenticate -> doInBackground # Login",
//                        e, null, BugReport.SEVERITY_LOW);

                return false;
            } catch (JSONException e) {
                e.printStackTrace();
                errors.add(e.toString());
                // errors.add("Received an invalid response from the server.");

//                ErrorUtil.logException(LoginActivity.this, "LoginActivity -> Authenticate -> doInBackground # Login",
//                        e, loginResponse, BugReport.SEVERITY_HIGH);

                return false;
            } catch (NumberFormatException e) {
                errors.add(e.toString());
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);

            pdialog.setMessage("Finalizing data");
            pdialog.setMessage("Download Completed..");
            if (result) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }
                pref.setLoginStatus(true);

                SharedPref.getInstance(ActivityLogin
                        .this).setGlobalVal("FirstTimeSyncDate", dateFormat.format(new Date(timeInMillis)));

                Intent intent = new Intent(ActivityLogin
                        .this, ActivityHome
                        .class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(ActivityLogin.this, "Invalid Response from server", Toast.LENGTH_LONG);
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }
                StringBuilder sb = new StringBuilder();
                if (errors.size() == 1) {
                    sb.append(errors.get(0));
                } else {
                    sb.append("Following errors occurred");
                    for (String error : errors) {
                        sb.append("\n - ").append(error);
                    }
                }
                showErrorText(sb.toString());

            }
        }
    }

    private void showErrorText(String s) {
        Snackbar snackbar = Snackbar.make(loginlayout, R.string.txt_msg, Snackbar.LENGTH_LONG);
        View snackbarLayout = snackbar.getView();
        snackbarLayout.setBackgroundColor(Color.RED);
        TextView textView = (TextView) snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sync, 0, 0, 0);
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.body_size));
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private class Validate extends AsyncTask<String, Integer, Boolean> {
        int totalRecords = 0;
        CustomProgressDialog pdialog;
        private String macId, url;

        public Validate(String macId, String url) {
            this.macId = macId;
            this.url = url;
            this.pdialog = new CustomProgressDialog(ActivityLogin.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Validating...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            try {

                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getSalRepResult(pref.getDistDB(),macId);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getSalRepResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<SalRep> repList = new ArrayList<SalRep>();
                            for (int i = 0; i < response.body().getSalRepResult().size(); i++) {
                                repList.add(response.body().getSalRepResult().get(i));
                            }
                            new SalRepController(ActivityLogin.this).createOrUpdateSalRep(repList);
                            networkFunctions.setUser(repList.get(0));
                            pref.storeLoginUser(repList.get(0));
                            System.out.println("Rep List " + repList.toString());

                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                    pref = SharedPref.getInstance(ActivityLogin.this);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pdialog.setMessage("Authenticated...");
                            }
                        });

                        return true;

                } catch (Exception e) {
                    Log.e("networkFunctions ->", "IOException -> " + e.toString());
                    throw e;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

//        protected void onProgressUpdate(Integer... progress) {
//            super.onProgressUpdate(progress);
//            pDialog.setMessage("Prefetching data..." + progress[0] + "/" + totalRecords);
//
//        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (pdialog.isShowing())
                pdialog.cancel();
            // pdialog.cancel();
            if (result) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                pref.setValidateStatus(true);
                //tryAgain.setVisibility(View.INVISIBLE);
                //set user details to shared prefferences
                //Intent mainActivity = new Intent(ActivitySplash.this, SettingsActivity.class);
                // .................. Nuwan ....... commented due to run home activity .............. 19/06/2019
                Intent loginActivity = new Intent(ActivityLogin.this, ActivityLogin.class);
                //  Intent loginActivity = new Intent(ActivitySplash.this, ActivityHome.class);
                // ..............................................................................................
//
                startActivity(loginActivity);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Invalid response from server", Toast.LENGTH_LONG).show();
                //tryAgain.setVisibility(View.VISIBLE);
//temerary set for new SFA
                // .................. Nuwan ....... commented due to run home activity .............. 19/06/2019
                //Intent loginActivity = new Intent(ActivitySplash.this, ActivityLogin.class);
//                Intent loginActivity = new Intent(ActivitySplash.this, ActivityHome.class);
//                // ..............................................................................................
//                startActivity(loginActivity);
//                finish();

            }
        }
    }
}
