package com.datamation.hmdsfa.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import com.datamation.hmdsfa.helpers.BluetoothConnectionHelper;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.api.ApiCllient;
import com.datamation.hmdsfa.api.ApiInterface;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.dialog.CustomProgressDialog;
import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.helpers.SharedPref;

import com.datamation.hmdsfa.model.DbNames;
import com.datamation.hmdsfa.model.SalRep;
import com.datamation.hmdsfa.model.apimodel.ReadJsonList;
import com.datamation.hmdsfa.utils.NetworkUtil;
import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.utils.GetMacAddress;
import com.karan.churi.PermissionManager.PermissionManager;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySplash extends AppCompatActivity{

    private ImageView logo;
    private static int SPLASH_TIME_OUT = 4000;
    private static String spURL = "";
  //  public static String DBNAME = "";
    DatabaseHelper db;
    private SharedPref pref;
    private NetworkFunctions networkFunctions;
    PermissionManager permissionManager;
    private String TAG = "ActivitySplash";
    private TextView tryAgain;
    private SearchableSpinner DBList;
    ArrayAdapter<String> dataAdapter;
    private boolean isValidate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_splash, null);
        setContentView(v);
        permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);
        db=new DatabaseHelper(getApplicationContext());
        SQLiteDatabase SFA;
        SFA = db.getWritableDatabase();
        pref = SharedPref.getInstance(this);
        db.onUpgrade(SFA, 1, 2);

        logo = (ImageView) findViewById(R.id.logo);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //logo.setImageDrawable(getResources().getDrawable(R.drawable.dm_logo));
        tryAgain = (TextView) findViewById(R.id.tryAgain);
        networkFunctions = new NetworkFunctions(this);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_up);
        logo.startAnimation(animation1);

        final boolean connectionStatus = NetworkUtil.isNetworkAvailable(ActivitySplash.this);
        GetMacAddress macAddress = new GetMacAddress();
        if (android.os.Build.VERSION.SDK_INT < 23) {
            pref.setMacAddress(macAddress.getMacAddress(getApplicationContext()).toString().replace(":", ""));
        } else {
            pref.setMacAddress(macAddress.getMacAddressNewApi(getApplicationContext()).toString().replace(":", ""));
        }


        if(pref.getLoginUser()==null) {
            validateDialog();

        }else{
           // if(pref.isLoggedIn() || !pref.getLoginUser().equals(null)){
            if(pref.isLoggedIn()){
                goToHome();
           //     pref.setLoginStatus(true);
            }else {
                goToLogin();
            }
        }

//        tryAgain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (NetworkUtil.isNetworkAvailable(ActivitySplash.this)) {
//                new Validate(pref.getMacAddress().trim()).execute();
//                } else {
//
//                    Snackbar snackbar = Snackbar.make(v, R.string.txt_msg, Snackbar.LENGTH_LONG);
//                    View snackbarLayout = snackbar.getView();
//                    snackbarLayout.setBackgroundColor(Color.RED);
//                    TextView textView = (TextView) snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
//                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_signal_wifi_off_black_24dp, 0, 0, 0);
//                    textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.body_size));
//                    textView.setTextColor(Color.WHITE);
//                    snackbar.show();
//                    tryAgain.setVisibility(View.VISIBLE);
//
//                }
//            }
//        });
        new BluetoothConnectionHelper(this).enableBluetooth(ActivitySplash.this);
    }

    //with console database - commented rashmi 2020-02-28 {this is need for distributor systems like magnes , swadeshi..hide for hameedia}
//    private void validateDialog() {
//        LayoutInflater layoutInflater = LayoutInflater.from(this);
//        final View promptView = layoutInflater.inflate(R.layout.ip_connection_dailog, null);
//        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
//        alertDialogBuilder.setView(promptView);
//        final EditText input = (EditText) promptView.findViewById(R.id.txt_Enter_url);
//        final NetworkFunctions networkFunctions = new NetworkFunctions(this);
//        DBList = (SearchableSpinner) promptView.findViewById(R.id.spinner2);
//        Button btn_validate = (Button)promptView.findViewById(R.id.btn_validate);
//
//        DBList.setTitle("Select or search Database");
//
//        // to disable alert OK button due to crash on without validate ok button pressed ------------------ Nuwan ------------------- 11/10/2019 ---------------
////        final AlertDialog alertDialog = alertDialogBuilder.create();
//
//        btn_validate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (NetworkUtil.isNetworkAvailable(ActivitySplash.this)){
//                    spURL = input.getText().toString().trim();
//                    String URL = "http://" + input.getText().toString().trim();
//                    if (Patterns.WEB_URL.matcher(URL).matches()) {
//                        // pref.setBaseURL(spURL);
//                        new getDatabaseNames().execute(URL);
//                        //alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
//                        Toast.makeText(ActivitySplash.this, "URL config success." + spURL, Toast.LENGTH_LONG).show();
//
//                    } else {
//                        //alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//                        Toast.makeText(ActivitySplash.this, "Invalid URL Entered. Please Enter Valid URL.", Toast.LENGTH_LONG).show();
//                        reCallActivity();
//                    }
//                }else{
//                    //alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//                    Toast.makeText(ActivitySplash.this, "Please check your internet connection.. !", Toast.LENGTH_LONG).show();
//
//                    }
//                }
//        });
//
//        DBList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //DBNAME = DBList.getSelectedItem().toString();
//                UtilityContainer.ClearDBName(ActivitySplash.this);
//                pref.setDistDB(DBList.getSelectedItem().toString());
//
//                Log.d("Inside DB Select" , pref.getDistDB());
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                String URL = "http://" + input.getText().toString().trim();
//
//                if(URL.length()!=0)
//                {
//                  //  (DBNAME);
//
////                    if (Patterns.WEB_URL.matcher(URL).matches()&& URL.length()== 26)
//                    if (Patterns.WEB_URL.matcher(URL).matches())
//                    {
//                        if (NetworkUtil.isNetworkAvailable(ActivitySplash.this))
//                        {
//                            pref.setBaseURL(URL);
//                           // pref.setDistDB(DBList.getSelectedItem().toString());
////                            if (DBList.getSelectedItem().toString().trim().equals("") || DBList.getSelectedItem().toString().trim().equals("null")) // to check url validation
//                            if (isValidate)
//                            {
//                                Toast.makeText(ActivitySplash.this, "Mac ID for Test. "+pref.getMacAddress().trim() , Toast.LENGTH_LONG).show();
//
//                                new Validate(pref.getMacAddress().trim(),URL,DBList.getSelectedItem().toString().trim()).execute();
//                                isValidate = false;
//                                //TODO: validate uname pwd with server details
//                            }
//                            else
//                            {
//                                Toast.makeText(ActivitySplash.this, "Please Validate the URL or Check Response from Server when getting DB List.", Toast.LENGTH_LONG).show();
//                                reCallActivity();
//                            }
//                        }
//                        else
//                        {
//                            Snackbar snackbar = Snackbar.make(promptView, R.string.txt_msg, Snackbar.LENGTH_LONG);
//                            View snackbarLayout = snackbar.getView();
//                            snackbarLayout.setBackgroundColor(Color.RED);
//                            TextView textView = (TextView) snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
//                            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_signal_wifi_off_black_24dp, 0, 0, 0);
//                            textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.body_size));
//                            textView.setTextColor(Color.WHITE);
//                            snackbar.show();
//                            reCallActivity();
//                        }
//
//                    } else {
//                        Toast.makeText(ActivitySplash.this, "Invalid URL Entered. Please Enter Valid URL.", Toast.LENGTH_LONG).show();
//                        reCallActivity();
//                    }
//
//                }else
//                {
//                    Toast.makeText(ActivitySplash.this, "Please fill informations", Toast.LENGTH_LONG).show();
//                    validateDialog();
//                }
//            }
//        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//
//                ActivitySplash.this.finish();
//            }
//        });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//
//
//        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
//                | ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//    }
//


    //without console database- implement 2020-02-28 by rashmi for hameedia
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        permissionManager.checkResult(requestCode,permissions,grantResults);
    }
    private void validateDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View promptView = layoutInflater.inflate(R.layout.ip_connection_dailog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        alertDialogBuilder.setView(promptView);
        final EditText input = (EditText) promptView.findViewById(R.id.txt_Enter_url);




        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String URL = "http://" + input.getText().toString().trim();

                if(URL.length()!=0)
                {
                    //   pref.setDBNAME(DBNAME);
//                    if (Patterns.WEB_URL.matcher(URL).matches()&& URL.length()== 26)
                    if (Patterns.WEB_URL.matcher(URL).matches())
                    {
                        if (NetworkUtil.isNetworkAvailable(ActivitySplash.this))
                        {
                            pref.setBaseURL(URL);
                            Toast.makeText(ActivitySplash.this, "Inside Validate Dialog", Toast.LENGTH_LONG).show();
                            new Validate(pref.getMacAddress().trim(),URL).execute();
//                            Validate("942DDCCCD4C6");
                           // Validate(pref.getMacAddress().trim());
                            //TODO: validate uname pwd with server details


                        }
                        else
                        {
                            Snackbar snackbar = Snackbar.make(promptView, R.string.txt_msg, Snackbar.LENGTH_LONG);
                            View snackbarLayout = snackbar.getView();
                            snackbarLayout.setBackgroundColor(Color.RED);
                            TextView textView = (TextView) snackbarLayout.findViewById(com.google.android.material.R.id.snackbar_text);
                            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_signal_wifi_off_black_24dp, 0, 0, 0);
                            textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.body_size));
                            textView.setTextColor(Color.WHITE);
                            snackbar.show();
                            reCallActivity();
                        }

                    } else {
                        Toast.makeText(ActivitySplash.this, "Invalid URL Entered. Please Enter Valid URL.", Toast.LENGTH_LONG).show();
                        reCallActivity();
                    }

                }else
                {
                    Toast.makeText(ActivitySplash.this, "Please fill informations", Toast.LENGTH_LONG).show();
                    validateDialog();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

                ActivitySplash.this.finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    public void reCallActivity(){
        Intent mainActivity = new Intent(ActivitySplash.this, ActivitySplash.class);
        startActivity(mainActivity);
        finish();
    }
//already checked macId
    public void goToLogin(){

        // .................. Nuwan ....... commented due to run home activity .............. 19/06/2019
        Intent mainActivity = new Intent(ActivitySplash.this, ActivityLogin.class);
      //  Intent mainActivity = new Intent(ActivitySplash.this, ActivityHome.class);
        // ..............................................................................................
        startActivity(mainActivity);
        finish();
    }
    public void goToHome(){
        Intent mainActivity = new Intent(ActivitySplash.this, ActivityHome.class);
        startActivity(mainActivity);
        finish();
    }
    // write get mac address code

    private class Validate extends AsyncTask<String, Integer, Boolean> {
        int totalRecords=0;
        CustomProgressDialog pdialog;
        private String macId,url;

        public Validate(String macId,String url){
            this.macId = macId;
            this.url = url;
            this.pdialog = new CustomProgressDialog(ActivitySplash.this);
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


                    ApiInterface apiInterface = ApiCllient.getClient(ActivitySplash.this).create(ApiInterface.class);
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
                            new SalRepController(ActivitySplash.this).createOrUpdateSalRep(repList);
                            if(repList.size()>0){
                                pref.setValidateStatus(true);
                                networkFunctions.setUser(repList.get(0));
                                pref.storeLoginUser(repList.get(0));
                            }
                            Log.d(">>>REP","Rep List " + repList.toString());

                        }

                        @Override
                        public void onFailure(Call<ReadJsonList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                    pref = SharedPref.getInstance(ActivitySplash.this);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Authenticated...");
                        }
                    });

                    return true;


                } catch (Exception e) {
                    Log.d("networkFunctions ->","IOException -> "+e.toString());
                    Log.e("networkFunctions ->","IOException -> "+e.toString());
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
            if(pdialog.isShowing())
                pdialog.cancel();
           // pdialog.cancel();
            if(result){
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                pref.setValidateStatus(true);
                tryAgain.setVisibility(View.INVISIBLE);
                Intent loginActivity = new Intent(ActivitySplash.this, ActivityLogin.class);
                startActivity(loginActivity);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Invalid Mac Id", Toast.LENGTH_LONG).show();
                reCallActivity();
            }
        }
    }

    private void Validate(String macId){//2020-03-19- by rashmi
        Toast.makeText(ActivitySplash.this, "Validate "+macId, Toast.LENGTH_LONG).show();
        try{
            ApiInterface apiInterface = ApiCllient.getClient(ActivitySplash.this).create(ApiInterface.class);
            Call<ReadJsonList> resultCall = apiInterface.getSalRepResult(pref.getDistDB(),macId);
            resultCall.enqueue(new Callback<ReadJsonList>() {
                @Override
                public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                    CustomProgressDialog pdialog = new CustomProgressDialog(ActivitySplash.this);
                    pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    pdialog.setMessage("Validating...");
                    pdialog.show();
                    System.out.println("test responce 01 " + response.body().getSalRepResult().size());
                    Toast.makeText(ActivitySplash.this, "test responce 01 "+response.body().getSalRepResult(), Toast.LENGTH_LONG).show();
                    //  System.out.println(response.body().getInvDetResult().get(1));
                    ArrayList<SalRep> repList = new ArrayList<SalRep>();
                    for (int i = 0; i < response.body().getSalRepResult().size(); i++) {
                        repList.add(response.body().getSalRepResult().get(i));
                    }
                    new SalRepController(ActivitySplash.this).createOrUpdateSalRep(repList);
                    if(repList.size()>0){
                        if(pdialog.isShowing())
                            pdialog.cancel();
                        pref.setValidateStatus(true);
                        networkFunctions.setUser(repList.get(0));
                        pref.storeLoginUser(repList.get(0));
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        tryAgain.setVisibility(View.INVISIBLE);
                        Intent loginActivity = new Intent(ActivitySplash.this, ActivityLogin.class);
                        startActivity(loginActivity);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid Mac Id", Toast.LENGTH_LONG).show();
                        reCallActivity();
                    }
                    Log.d(">>>REP","Rep List " + repList.toString());

                }

                @Override
                public void onFailure(Call<ReadJsonList> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }catch (Exception e){
            Log.d(">>>ERROR Validate",">>>"+e.toString());
        }
    }
    public class getDatabaseNames extends AsyncTask<Object, Object, ArrayList<DbNames>> {
        private AlertDialog dialog;
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new SpotsDialog(ActivitySplash.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();
        }

        @Override
        protected ArrayList<DbNames> doInBackground(Object... params) {
             ArrayList<DbNames> DBArrayList1 = null;
            try {

                ApiInterface apiInterface = ApiCllient.getClient(ActivitySplash.this).create(ApiInterface.class);
                Call<ReadJsonList> resultCall = apiInterface.getDatabaseResult();
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        ArrayList<DbNames>  DBArrayList = new ArrayList<DbNames>();
                        if(response.body().getDbResult()!= null) {
                            System.out.println("test responce 01 " + response.body().getDbResult().size());

                            //  System.out.println(response.body().getInvDetResult().get(1));
                            if(response.body().getDbResult().size()>0) {
                                for (int i = 0; i < response.body().getDbResult().size(); i++) {
                                    DBArrayList.add(response.body().getDbResult().get(i));
                                }

                                fetchDatabases(DBArrayList);//change after apply retrofit - rashmi -2020-02-24
                            }else{
                                Toast.makeText(ActivitySplash.this,"Error response from getting database list",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(ActivitySplash.this,"Error response from getting database list",Toast.LENGTH_LONG).show();
                        }
                     //   DBArrayList1 = DBArrayList;
                    }

                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            } catch (Exception e) {
                Toast.makeText(ActivitySplash.this,"Error response from getting database list",Toast.LENGTH_LONG).show();
            }
            return DBArrayList1;
        }

        protected void onPostExecute(ArrayList<DbNames> result) {
            super.onPostExecute(result);
            dialog.cancel();

        }


    }

    private void fetchDatabases(ArrayList<DbNames> dbNames){
        ArrayList<String> lst_ =  new ArrayList<String>();
        lst_.add(0,"Select Server");

        if(dbNames != null) {

            for (DbNames lst : dbNames) {
                lst_.add(lst.getDbName());
                isValidate = true;
            }
        }
        else {
            Toast.makeText(ActivitySplash.this, "Invalid Response from server when getting DB List.", Toast.LENGTH_LONG).show();
        }
        dataAdapter = new ArrayAdapter<String>(ActivitySplash.this, android.R.layout.simple_spinner_item, lst_);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DBList.setAdapter(dataAdapter);
    }
}
