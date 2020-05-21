package com.datamation.hmdsfa.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

//import com.datamation.sfa.controller.ItemController;
import com.datamation.hmdsfa.controller.AttendanceController;
import com.datamation.hmdsfa.fragment.FragmentHome;
import com.datamation.hmdsfa.fragment.FragmentMarkAttendance;
import com.datamation.hmdsfa.fragment.FragmentTools;
import com.datamation.hmdsfa.helpers.NetworkFunctions;

import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Attendance;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.model.SalRep;
//import com.datamation.sfa.presale.OrderMainFragment;
import com.datamation.hmdsfa.R;

import com.datamation.hmdsfa.settings.UserSessionManager;
import com.datamation.hmdsfa.utils.NetworkUtil;
import com.datamation.hmdsfa.utils.UtilityContainer;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivityHome extends AppCompatActivity {

    public static final String SETTINGS = "SETTINGS";
    public Customer selectedDebtor = null;
    public int cusPosition = 0;
    private Context context = this;
    public String TAG = "ActivityHome.class";
    NetworkFunctions networkFunctions;
    List<String> resultList;
    SharedPref pref;
    SalRep loggedUser;
    private long timeInMillis;
    ArrayList<Attendance> tours;
    String currentVersion = null;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment(0);
                    return true;
                case R.id.navigation_sales:
                    SharedPref sharedPref = SharedPref.getInstance(context);
                    Log.d("Current Date>>>>", dateFormat.format(new Date(timeInMillis)));
                    Log.d("Last Sync Date>>>>", sharedPref.getGlobalVal("SyncDate"));
                    tours = new AttendanceController(context).getIncompleteRecord();
                   // if (sharedPref.getGlobalVal("SyncDate").equalsIgnoreCase(dateFormat.format(new Date(timeInMillis)))) {
                        Log.d("Test SecondarySync", "Secondary sync done");
                        if(tours.size()>0 && !(sharedPref.getGlobalVal("DayStartDate").equalsIgnoreCase(dateFormat.format(new Date(timeInMillis))))){
                            UtilityContainer.mLoadFragment(new FragmentMarkAttendance(), ActivityHome.this);
                            Toast.makeText(getApplicationContext(), "Please do the day end", Toast.LENGTH_LONG).show();
                            Log.d("Test Attendance", "Day start without previous day end");
                        }else{
                            if(new AttendanceController(context).hasTodayRecord() < 1) {
                                if ((sharedPref.getGlobalVal("dayStart").equalsIgnoreCase("Y")) && (sharedPref.getGlobalVal("DayStartDate").equalsIgnoreCase(dateFormat.format(new Date(timeInMillis))))) {
                                    Log.d("Test Attendance", "Day start ok for today");
                                    Intent intent = new Intent(getApplicationContext(), DebtorListActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Log.d("Test Attendance", "Do day start for today");
                                    UtilityContainer.mLoadFragment(new FragmentMarkAttendance(), ActivityHome.this);
                                    Toast.makeText(getApplicationContext(), "Please enter attendance details", Toast.LENGTH_LONG).show();

                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Day end is done for today. You can't continue, Please contact help-desk", Toast.LENGTH_LONG).show();

                            }
                        }


//                    } else {
//                        Toast.makeText(getApplicationContext(), "Please do the secondary sync", Toast.LENGTH_LONG).show();
//                        //UtilityContainer.mLoadFragment(new FragmentMarkAttendance(), ActivityHome.this);
//                    }

                    return true;
                case R.id.navigation_tools:
                    UtilityContainer.mLoadFragment(new FragmentTools(), ActivityHome.this);
//                    UtilityContainer.mLoadFragment(new FragmentTools_new(), ActivityHome.this);
                    return true;
                case R.id.navigation_logout:
                    Logout();
                    return true;
            }
            return false;
        }
    };

    public static BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pref = SharedPref.getInstance(this);
        networkFunctions = new NetworkFunctions(this);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        resultList = new ArrayList<>();
        loggedUser = pref.getLoginUser();
        currentVersion = getVersionCode();
        timeInMillis = System.currentTimeMillis();
        //set home frgament
        changeFragment(0);
        if(NetworkUtil.isNetworkAvailable(context)) {
            new checkVersion().execute();
        }else{
            Toast.makeText(context, "No internet connection for validate app version", Toast.LENGTH_LONG).show();
        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        pref.setImageFlag("1");

        String token = FirebaseInstanceId.getInstance().getToken();
        if(token!=null ) {
            pref.setFirebaseTokenKey(token);
            Log.d("**##token", "onCreate: " + pref.getFirebaseTokenKey());
        }

    }


    @Override
    public void onBackPressed() {
      //nothing (disable backbutton)
    }
    public String getVersionCode() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "0";

    }
    public class checkVersion extends AsyncTask<String, String, String> {

        private SweetAlertDialog dialog;

        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new SweetAlertDialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String version = "0";
            try {
                URL json = new URL("" + pref.getBaseURL()+getResources().getString(R.string.connection_string) +"fControl/mobile123/" + pref.getDistDB());
                URLConnection jc = json.openConnection();

                BufferedReader readerfdblist = new BufferedReader(new InputStreamReader(jc.getInputStream()));
                String line = readerfdblist.readLine();
                JSONObject jsonResponse = new JSONObject(line);
                JSONArray jsonArray = jsonResponse.getJSONArray("fControlResult");
                JSONObject jObject = (JSONObject) jsonArray.get(0);
                version = jObject.getString("AppVersion");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return version;
        }

        protected void onPostExecute(String newVersion) {
            super.onPostExecute(newVersion);
            if(dialog.isShowing())
                dialog.dismiss();

            if (newVersion != null && !newVersion.isEmpty()) {
                int New = 0;
                if(!newVersion.trim().equals("")) {
                    New = Integer.parseInt(newVersion.trim().replace(".", ""));
                }

                int Current = Integer.parseInt(currentVersion.replace(".", ""));
                Log.v("New Version", ">>>>>>"+New);
                Log.v("old Version", ">>>>>>"+Current);
                //163>162
                if (New > Current) {
                    //show dialog
                    Log.v("UPDATE AVAILABLESSSS", "USSPDATE");
                    // Create custom dialog object

                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("New Update Available.")
                            .setContentText("Vesrion : " + newVersion)
                            .setConfirmText("Yes,Update!")
                            .setCancelText("No,cancel!")
                            .showCancelButton(false)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    //Loading();
                                    sDialog.cancel();
                                    finish();

                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

//                                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.datamation.dss"));
//                                    startActivity(intent);
                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                }
                            })

                            .show();

                } else {
                    Toast.makeText(context, "Your application is up to date", Toast.LENGTH_LONG).show();

                }

            } else
            {
                Toast.makeText(context, "Invalid response from server when check version", Toast.LENGTH_LONG).show();

            }


        }


    }
    public void Logout() {

        final Dialog Ldialog = new Dialog(ActivityHome.this);
        Ldialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Ldialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Ldialog.setContentView(R.layout.logout);


        //logout
        Ldialog.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSessionManager sessionManager = new UserSessionManager(context);
                sessionManager.Logout();
                finish();
                pref.setLoginStatus(false);
                pref.setValidateStatus(false);
                pref.clearPref();
            }
        });
        Ldialog.show();
    }

    public void viewRouteInfo() {
        final Dialog repDialog = new Dialog(context);
        repDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        repDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        repDialog.setCancelable(false);
        repDialog.setCanceledOnTouchOutside(false);
        repDialog.setContentView(R.layout.rep_route_profile);

        //initializations
        RouteController routeDS = new RouteController(context);
        //  String routes = routeDS.getRouteNameByCode(loggedUser.getRoute());

        TextView routeName = (TextView) repDialog.findViewById(R.id.routeName);
        // routeName.setText(routes);
        TextView routeCode = (TextView) repDialog.findViewById(R.id.routeCode);
        //   routeCode.setText(loggedUser.getRoute());


        //close
        repDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repDialog.dismiss();
            }
        });

        repDialog.show();
    }



    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    /**
     * To load fragments for sample
     *
     * @param position menu index
     */
    private void changeFragment(int position) {


        if (position == 0) {
            //   newFragment = new FragHome();
            Log.d(">>>>>>", "position0");
            UtilityContainer.mLoadFragment(new FragmentHome(), ActivityHome.this);
        } else if (position == 1) {
            Log.d(">>>>>>", "position1");
        }
    }

    public void bottomNav(Boolean cmd) {

        if (cmd == true) {

            navigation.setVisibility(View.GONE);
        } else {
            navigation.setVisibility(View.VISIBLE);
        }
    }

}
