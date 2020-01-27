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
                    if (Patterns.WEB_URL.matcher(URL).matches()) {
                        if (NetworkUtil.isNetworkAvailable(ActivityLogin.this)) {
                            pref.setBaseURL(URL);
                            pref.setDistDB(inputDistributor.getText().toString().trim());
                            Log.d("myapp", inputDistributor.getText().toString().trim());
                            new Validate(pref.getMacAddress().trim(), URL).execute();

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

    private class Authenticate extends AsyncTask<String, Integer, Boolean> {
        int totalRecords = 0;
        CustomProgressDialog pdialog;
        private String uname, pwd, repcode;
        private List<String> errors = new ArrayList<>();

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


                // Processing Company details
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getControlResult(pref.getDistDB());
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getControlResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Control> controlList = new ArrayList<Control>();
                            for (int i = 0; i < response.body().getControlResult().size(); i++) {
                                controlList.add(response.body().getControlResult().get(i));
                            }
                            CompanyDetailsController companyController = new CompanyDetailsController(ActivityLogin.this);

                            companyController.createOrUpdateFControl(controlList);

                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                } catch (Exception e) {
                    errors.add(e.toString());
                    throw e;
                }
/*****************end  Company details**********************************************************************/

/*****************Customers**********************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Control downloaded\nDownloading Customers...");
                    }
                });

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

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getDebtorResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getDebtorResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Debtor> debtorList = new ArrayList<Debtor>();
                            for (int i = 0; i < response.body().getDebtorResult().size(); i++) {
                                debtorList.add(response.body().getDebtorResult().get(i));
                            }
                            CustomerController customerController = new CustomerController(ActivityLogin.this);

                            customerController.InsertOrReplaceDebtor(debtorList);

                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {

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



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (near customer details)...");
                    }
                });

                // Processing outlets
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getNearDebtorResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getNearDebtorResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<NearDebtor> nDebList = new ArrayList<NearDebtor>();
                            for (int i = 0; i < response.body().getNearDebtorResult().size(); i++) {
                                nDebList.add(response.body().getNearDebtorResult().get(i));
                            }
                            NearCustomerController nCustomerController = new NearCustomerController(ActivityLogin.this);
                            nCustomerController.InsertOrReplaceNearDebtor(nDebList);

                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
                    throw e;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Near Customers downloaded\nDownloading Company Settings...");
                    }
                });
                /*****************Settings*****************************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (setting details)...");
                    }
                });

                // Processing company settings
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getCompanySettingResult(pref.getDistDB());
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getCompanySettingResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<CompanySetting> settingList = new ArrayList<CompanySetting>();
                            for (int i = 0; i < response.body().getCompanySettingResult().size(); i++) {
                                settingList.add(response.body().getCompanySettingResult().get(i));
                            }
                            ReferenceSettingController settingController = new ReferenceSettingController(ActivityLogin.this);
                            settingController.createOrUpdateFCompanySetting(settingList);

                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
                    throw e;
                }

                /*****************end Settings**********************************************************************/
/*****************Branches*****************************************************************************/
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (setting details)...");
                    }
                });

                // Processing company settings
                try {
                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getCompanyBranchResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getCompanyBranchResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<CompanyBranch> settingList = new ArrayList<CompanyBranch>();
                            for (int i = 0; i < response.body().getCompanyBranchResult().size(); i++) {
                                settingList.add(response.body().getCompanyBranchResult().get(i));
                            }
                            ReferenceDetailDownloader settingController = new ReferenceDetailDownloader(ActivityLogin.this);
                            settingController.createOrUpdateFCompanyBranch(settingList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    throw e;
                }

                /*****************end Settings**********************************************************************/
                /*****************Item Loc*****************************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (item location details)...");
                    }
                });

                // Processing itemLocations
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getItemLocResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getItemLocResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<ItemLoc> itemLocList = new ArrayList<ItemLoc>();
                            for (int i = 0; i < response.body().getItemLocResult().size(); i++) {
                                itemLocList.add(response.body().getItemLocResult().get(i));
                            }
                            ItemLocController locController = new ItemLocController(ActivityLogin.this);
                            locController.InsertOrReplaceItemLoc(itemLocList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
                    throw e;
                }

                /*****************end Item Loc**********************************************************************/
                /*****************Locations*****************************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (location details)...");
                    }
                });
                // Processing itemLocations
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getLocationsResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getLocationsResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Locations> locList = new ArrayList<Locations>();
                            for (int i = 0; i < response.body().getLocationsResult().size(); i++) {
                                locList.add(response.body().getLocationsResult().get(i));
                            }
                            LocationsController locController = new LocationsController(ActivityLogin.this);
                            locController.createOrUpdateFLocations(locList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
                    throw e;
                }
                /*****************ItemPrice*****************************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (item price details)...");
                    }
                });

                // Processing itemLocations
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getItemPriResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getItemPriResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<ItemPri> itemPriceList = new ArrayList<ItemPri>();
                            for (int i = 0; i < response.body().getItemPriResult().size(); i++) {
                                itemPriceList.add(response.body().getItemPriResult().get(i));
                            }
                            ItemPriceController priceController = new ItemPriceController(ActivityLogin.this);
                            priceController.InsertOrReplaceItemPri(itemPriceList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
                    throw e;
                }
                /*****************end item prices**********************************************************************/
                /*****************Item*****************************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (item price details)...");
                    }
                });

                // Processing item price
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getItemsResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getItemsResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Item> itemList = new ArrayList<Item>();
                            for (int i = 0; i < response.body().getItemsResult().size(); i++) {
                                itemList.add(response.body().getItemsResult().get(i));
                            }
                            ItemController itemController = new ItemController(ActivityLogin.this);
                            itemController.InsertOrReplaceItems(itemList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {

                    errors.add(e.toString());

                    throw e;
                }
                /*****************end item **********************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (reasons)...");
                    }
                });

                // Processing reasons
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getReasonResult(pref.getDistDB());
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getReasonResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Reason> reasonList = new ArrayList<Reason>();
                            for (int i = 0; i < response.body().getReasonResult().size(); i++) {
                                reasonList.add(response.body().getReasonResult().get(i));
                            }
                            ReasonController reasonController = new ReasonController(ActivityLogin.this);
                            reasonController.createOrUpdateReason(reasonList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
                    throw e;
                }
                /*****************end reasons**********************************************************************/
                // Processing expense
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (expenses)...");
                    }
                });

                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getExpenseResult(pref.getDistDB());
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getExpenseResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Expense> expensesList = new ArrayList<Expense>();
                            for (int i = 0; i < response.body().getExpenseResult().size(); i++) {
                                expensesList.add(response.body().getExpenseResult().get(i));
                            }
                            ExpenseController expenseController = new ExpenseController(ActivityLogin.this);
                            expenseController.createOrUpdateFExpense(expensesList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (routes)...");
                    }
                });

                // Processing route
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getRouteResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getRouteResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Route> routeList = new ArrayList<Route>();
                            for (int i = 0; i < response.body().getRouteResult().size(); i++) {
                                routeList.add(response.body().getRouteResult().get(i));
                            }
                            RouteController routeController = new RouteController(ActivityLogin.this);
                            routeController.createOrUpdateFRoute(routeList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
                    throw e;
                }
                /*****************end route**********************************************************************/

                /*****************Route det**********************************************************************/


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (routes)...");
                    }
                });

                // Processing route
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getRouteDetResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getRouteDetResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<RouteDet> routeList = new ArrayList<RouteDet>();
                            for (int i = 0; i < response.body().getRouteDetResult().size(); i++) {
                                routeList.add(response.body().getRouteDetResult().get(i));
                            }
                            RouteDetController routeController = new RouteDetController(ActivityLogin.this);
                            routeController.InsertOrReplaceRouteDet(routeList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());

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


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (towns)...");
                    }
                });

                // Processing towns
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getTownResult(pref.getDistDB());
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            System.out.println("test responce 01 " + response.body().getTownResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Town> townList = new ArrayList<Town>();
                            for (int i = 0; i < response.body().getTownResult().size(); i++) {
                                townList.add(response.body().getTownResult().get(i));
                            }
                            TownController townController = new TownController(ActivityLogin.this);
                            townController.createOrUpdateFTown(townList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
                    throw e;
                }
                /*****************end towns**********************************************************************/


                /*****************FreeHed**********************************************************************/


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (free)...");
                    }
                });

                // Processing freehed
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getFreehedResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            FreeHedController freeHedController = new FreeHedController(ActivityLogin.this);
                            freeHedController.deleteAll();
                            System.out.println("test responce 01 " + response.body().getFreeHedResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<FreeHed> freeHedList = new ArrayList<FreeHed>();
                            for (int i = 0; i < response.body().getFreeHedResult().size(); i++) {
                                freeHedList.add(response.body().getFreeHedResult().get(i));
                            }

                            freeHedController.createOrUpdateFreeHed(freeHedList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
                    throw e;
                }
                /*****************end freeHed**********************************************************************/


                /*****************Banks**********************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (banks)...");
                    }
                });

                // Processing route
                try {
                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getBankResult(pref.getDistDB());
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            BankController bankController = new BankController(ActivityLogin.this);
                            System.out.println("test responce 01 " + response.body().getBankResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Bank> bankList = new ArrayList<Bank>();
                            for (int i = 0; i < response.body().getBankResult().size(); i++) {
                                bankList.add(response.body().getBankResult().get(i));
                            }

                            bankController.createOrUpdateBank(bankList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());

                    throw e;
                }
                /*****************end banks**********************************************************************/

                /*****************discdeb**********************************************************************/
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (discount)...");
                    }
                });

                // Processing discdeb

                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getDiscDebResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            DiscdebController discdebController = new DiscdebController(ActivityLogin.this);
                            discdebController.deleteAll();
                            System.out.println("test responce 01 " + response.body().getDiscDebResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Discdeb> discdebList = new ArrayList<Discdeb>();
                            for (int i = 0; i < response.body().getDiscDebResult().size(); i++) {
                                discdebList.add(response.body().getDiscDebResult().get(i));
                            }

                            discdebController.createOrUpdateDiscdeb(discdebList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    throw e;
                }
                /*****************end discdeb**********************************************************************/
                /*****************discdet**********************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (discount)...");
                    }
                });

                // Processing discdeb
                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getDiscDetResult(pref.getDistDB());
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            DiscdetController discdetController = new DiscdetController(ActivityLogin.this);
                            discdetController.deleteAll();
                            System.out.println("test responce 01 " + response.body().getDiscDetResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Discdet> discdetList = new ArrayList<Discdet>();
                            for (int i = 0; i < response.body().getDiscDetResult().size(); i++) {
                                discdetList.add(response.body().getDiscDetResult().get(i));
                            }

                            discdetController.createOrUpdateDiscdet(discdetList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());

                    throw e;
                }
                /*****************end discdet**********************************************************************/
                /*****************discshed**********************************************************************/
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (discount)...");
                    }
                });

                // Processing discdeb


                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getDiscHedResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            DischedController dischedController = new DischedController(ActivityLogin.this);
                            dischedController.deleteAll();
                            System.out.println("test responce 01 " + response.body().getDiscHedResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Disched> dischedList = new ArrayList<Disched>();
                            for (int i = 0; i < response.body().getDiscHedResult().size(); i++) {
                                dischedList.add(response.body().getDiscHedResult().get(i));
                            }
                            dischedController.createOrUpdateDisched(dischedList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
                    throw e;
                }
                /*****************end discdet**********************************************************************/
                /*****************discslab*************************************************************************/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (discount)...");
                    }
                });

                // Processing discslab

                try {

                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getDiscSlabResult(pref.getDistDB());
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            DiscslabController discslabController = new DiscslabController(ActivityLogin.this);
                            discslabController.deleteAll();
                            System.out.println("test responce 01 " + response.body().getDiscSlabResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<Discslab> discslabList = new ArrayList<Discslab>();
                            for (int i = 0; i < response.body().getDiscSlabResult().size(); i++) {
                                discslabList.add(response.body().getDiscSlabResult().get(i));
                            }
                            discslabController.createOrUpdateDiscslab(discslabList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
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


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (Iteanery)...");
                    }
                });

                // Processing itenarydet
                try {
                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getItenrDetResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            FItenrDetController itenaryDetController = new FItenrDetController(ActivityLogin.this);
                            itenaryDetController.deleteAll();
                            System.out.println("test responce 01 " + response.body().getItenrDetResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<FItenrDet> itenaryDetList = new ArrayList<FItenrDet>();
                            for (int i = 0; i < response.body().getItenrDetResult().size(); i++) {
                                itenaryDetList.add(response.body().getItenrDetResult().get(i));
                            }
                            itenaryDetController.createOrUpdateFItenrDet(itenaryDetList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdialog.setMessage("Processing downloaded data (Iteanery)...");
                    }
                });

                // Processing itenaryhed
                try {
                    ApiInterface apiInterface = ApiCllient.getClient(ActivityLogin.this).create(ApiInterface.class);
                    Call<ReadJsonList> resultCall = apiInterface.getItenrHedResult(pref.getDistDB(),repcode);
                    resultCall.enqueue(new Callback<ReadJsonList>() {
                        @Override
                        public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                            FItenrHedController itenaryHedController = new FItenrHedController(ActivityLogin.this);
                            System.out.println("test responce 01 " + response.body().getItenrHedResult().size());
                            //  System.out.println(response.body().getInvDetResult().get(1));
                            ArrayList<FItenrHed> itenaryHedList = new ArrayList<FItenrHed>();
                            for (int i = 0; i < response.body().getItenrHedResult().size(); i++) {
                                itenaryHedList.add(response.body().getItenrHedResult().get(i));
                            }
                            itenaryHedController.createOrUpdateFItenrHed(itenaryHedList);
                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    errors.add(e.toString());
                    throw e;
                }
                /*****************end itenaryhed**********************************************************************/

                return true;

            } catch (Exception e) {
                e.printStackTrace();
                errors.add(e.toString());
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

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (pdialog.isShowing())
                pdialog.cancel();
            if (result) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                pref.setValidateStatus(true);
                Intent loginActivity = new Intent(ActivityLogin.this, ActivityLogin.class);
                startActivity(loginActivity);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Invalid response from server", Toast.LENGTH_LONG).show();
            }
        }
    }
}
