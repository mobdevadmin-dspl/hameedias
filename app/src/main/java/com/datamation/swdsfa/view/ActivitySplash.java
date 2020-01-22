package com.datamation.swdsfa.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.SalRepController;
import com.datamation.swdsfa.dialog.CustomProgressDialog;
import com.datamation.swdsfa.helpers.NetworkFunctions;
import com.datamation.swdsfa.helpers.SharedPref;

import com.datamation.swdsfa.model.DbNames;
import com.datamation.swdsfa.model.ItemFreeIssue;
import com.datamation.swdsfa.model.SalRep;
import com.datamation.swdsfa.utils.NetworkUtil;
import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.model.User;
import com.datamation.swdsfa.utils.GetMacAddress;
import com.datamation.swdsfa.utils.UtilityContainer;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ActivitySplash extends AppCompatActivity{

    private ImageView logo;
    private static int SPLASH_TIME_OUT = 4000;
    private static String spURL = "";
  //  public static String DBNAME = "";
    DatabaseHelper db;
    private SharedPref pref;
    private NetworkFunctions networkFunctions;
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

    }
    private void validateDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View promptView = layoutInflater.inflate(R.layout.ip_connection_dailog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        alertDialogBuilder.setView(promptView);
        final EditText input = (EditText) promptView.findViewById(R.id.txt_Enter_url);
        final NetworkFunctions networkFunctions = new NetworkFunctions(this);
        DBList = (SearchableSpinner) promptView.findViewById(R.id.spinner2);
        Button btn_validate = (Button)promptView.findViewById(R.id.btn_validate);

        DBList.setTitle("Select or search Database");

        // to disable alert OK button due to crash on without validate ok button pressed ------------------ Nuwan ------------------- 11/10/2019 ---------------
//        final AlertDialog alertDialog = alertDialogBuilder.create();

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isNetworkAvailable(ActivitySplash.this)){
                    spURL = input.getText().toString().trim();
                    String URL = "http://" + input.getText().toString().trim();
                    if (Patterns.WEB_URL.matcher(URL).matches()) {
                        // pref.setBaseURL(spURL);
                        new getDatabaseNames().execute(URL);
                        //alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        Toast.makeText(ActivitySplash.this, "URL config success." + spURL, Toast.LENGTH_LONG).show();

                    } else {
                        //alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                        Toast.makeText(ActivitySplash.this, "Invalid URL Entered. Please Enter Valid URL.", Toast.LENGTH_LONG).show();
                        reCallActivity();
                    }
                }else{
                    //alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    Toast.makeText(ActivitySplash.this, "Please check your internet connection.. !", Toast.LENGTH_LONG).show();

                    }
                }
        });

        DBList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //DBNAME = DBList.getSelectedItem().toString();
                UtilityContainer.ClearDBName(ActivitySplash.this);
                pref.setDistDB(DBList.getSelectedItem().toString());

                Log.d("Inside DB Select" , pref.getDistDB());

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String URL = "http://" + input.getText().toString().trim();

                if(URL.length()!=0)
                {
                  //  (DBNAME);

//                    if (Patterns.WEB_URL.matcher(URL).matches()&& URL.length()== 26)
                    if (Patterns.WEB_URL.matcher(URL).matches())
                    {
                        if (NetworkUtil.isNetworkAvailable(ActivitySplash.this))
                        {
                            pref.setBaseURL(URL);
                           // pref.setDistDB(DBList.getSelectedItem().toString());
//                            if (DBList.getSelectedItem().toString().trim().equals("") || DBList.getSelectedItem().toString().trim().equals("null")) // to check url validation
                            if (isValidate)
                            {
                                Toast.makeText(ActivitySplash.this, "Mac ID for Test. "+pref.getMacAddress().trim() , Toast.LENGTH_LONG).show();

                                new Validate(pref.getMacAddress().trim(),URL,DBList.getSelectedItem().toString().trim()).execute();
                                isValidate = false;
                                //TODO: validate uname pwd with server details
                            }
                            else
                            {
                                Toast.makeText(ActivitySplash.this, "Please Validate the URL or Check Response from Server when getting DB List.", Toast.LENGTH_LONG).show();
                                reCallActivity();
                            }
                        }
                        else
                        {
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
//
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
        private String macId,url,db;

        public Validate(String macId,String url,String db){
            this.macId = macId;
            this.url = url;
            this.db = db;
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
                int recordCount = 0;
                int totalBytes  = 0;
                String validateResponse = null;
                JSONObject validateJSON;
                try {
          //          Toast.makeText(ActivitySplash.this, "Before get response"+url+""+macId.toString()+""+ db, Toast.LENGTH_LONG).show();
                    Log.d(">>>>>>>>",">>Before get response"+url+""+macId.toString()+""+ db);
                    validateResponse = networkFunctions.validate(ActivitySplash.this,macId,url,db);
                    Log.d("validateResponse",validateResponse);

                    validateJSON = new JSONObject(validateResponse);


                if (validateJSON != null) {

                    pref = SharedPref.getInstance(ActivitySplash.this);
                   // Toast.makeText(ActivitySplash.this, "validateJSON for Test. "+validateJSON.toString() , Toast.LENGTH_LONG).show();
                    Log.d(">>validateJSON for Test",validateJSON.toString()+"");
                    //dbHandler.clearTables();
                    // Login successful. Proceed to download other items

                    JSONArray repArray = validateJSON.getJSONArray("fSalRepResult");
                    ArrayList<SalRep> salRepList = new ArrayList<>();
                    for (int i = 0; i<repArray.length(); i++){
                        JSONObject expenseJSON = repArray.getJSONObject(i);
                        salRepList.add(SalRep.parseUser(expenseJSON));
                    }
                    new SalRepController(getApplicationContext()).createOrUpdateSalRep(salRepList);
                    User user = User.parseUser(repArray.getJSONObject(0));
                    networkFunctions.setUser(user);
                    pref.storeLoginUser(user);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Authenticated...");
                        }
                    });

                    return true;
                }else{
                   // Toast.makeText(ActivitySplash.this,"Invalid response from server when getting sales rep data",Toast.LENGTH_SHORT).show();

                    return false;
                }

                } catch (IOException e) {
                    Log.d("networkFunctions ->","IOException -> "+e.toString());
                    Log.e("networkFunctions ->","IOException -> "+e.toString());
                    throw e;
                } catch (JSONException e) {
                   // Toast.makeText(ActivitySplash.this, "networkFunctions ->JSONException -> "+e.toString(), Toast.LENGTH_LONG).show();

                    Log.e("networkFunctions ->","JSONException -> "+e.toString());
                    Log.d("networkFunctions ->","JSONException -> "+e.toString());
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
                //set user details to shared prefferences
                //Intent mainActivity = new Intent(ActivitySplash.this, SettingsActivity.class);
                // .................. Nuwan ....... commented due to run home activity .............. 19/06/2019
                Intent loginActivity = new Intent(ActivitySplash.this, ActivityLogin.class);
              //  Intent loginActivity = new Intent(ActivitySplash.this, ActivityHome.class);
                // ..............................................................................................
//
                startActivity(loginActivity);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Invalid Mac Id", Toast.LENGTH_LONG).show();
               // tryAgain.setVisibility(View.VISIBLE);
                reCallActivity();
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
            ArrayList<DbNames> DBArrayList = null;
            try {
                URL json = new URL(params[0] + getResources().getString(R.string.connection_string) +"/GetdatabaseNames/mobile123");
                URLConnection jc = json.openConnection();

                BufferedReader readerfdblist = new BufferedReader(new InputStreamReader(jc.getInputStream()));
                String line = readerfdblist.readLine();
                JSONObject jsonResponse = new JSONObject(line);
                JSONArray jsonArray = jsonResponse.getJSONArray("GetdatabaseNamesResult");
                DBArrayList = new ArrayList<DbNames>();
                DBArrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    DbNames DB_list = new DbNames();
                    DB_list.setDbName(object.getString("Name"));
                    DBArrayList.add(DB_list);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return DBArrayList;
        }

        protected void onPostExecute(ArrayList<DbNames> result) {
            super.onPostExecute(result);

            ArrayList<String> lst_ =  new ArrayList<String>();
            lst_.add(0,"Select Server");

            if(result != null) {


                for (DbNames lst : result) {
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
            dialog.cancel();

        }


    }
}
