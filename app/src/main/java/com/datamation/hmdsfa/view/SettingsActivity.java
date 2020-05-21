package com.datamation.hmdsfa.view;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.datamation.hmdsfa.model.SalRep;
import com.datamation.hmdsfa.settings.ContentItem;
import com.datamation.hmdsfa.settings.ImportActivity;
import com.datamation.hmdsfa.adapter.ListViewDataAdapter;
import com.datamation.hmdsfa.controller.OrderController;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.helpers.SQLiteBackUp;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsActivity extends AppCompatActivity {

    ArrayList<ContentItem> objects;

    // Shared Preferences variables
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;

    // web service connection URL (SVC)
   // public static String connURLsvc = "/KFDWebServices/KFDWebServicesRest.svc";
    private Button btncontinue;
    private Context context = this;

    @SuppressLint({"Recycle", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // getActionBar().setIcon(new
        // ColorDrawable(getResources().getColor(android.R.color.transparent)));
        // set title

        btncontinue = (Button) findViewById(R.id.btncont);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.in, R.anim.exit);
            }
        });
        setTitleBarColor();

        localSP = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        // getActionBar().setDisplayHomeAsUpEnabled(true);

        TypedArray icons = getResources().obtainTypedArray(R.array.listView_icons_for_settingd);

        objects = new ArrayList<ContentItem>();

        objects.add(new ContentItem("Sync Configuration", "config url, server database and header database configuration", icons.getResourceId(0, -1)));
        objects.add(new ContentItem("Printer Configuration", "Enter your MAC address to connect", icons.getResourceId(1, -1)));
        objects.add(new ContentItem("Reset Order Details", "Please enter From date and To date", icons.getResourceId(2, -1)));
        objects.add(new ContentItem("SQLite Database", "DB backups and restore", icons.getResourceId(3, -1)));
        objects.add(new ContentItem("Sales Representative Details", "Reps informations", icons.getResourceId(4, -1)));
        objects.add(new ContentItem("Sales Rep Route", "Route area and code", icons.getResourceId(5, -1)));

        ListViewDataAdapter adapter = new ListViewDataAdapter(getApplicationContext(), objects);

        ListView lv = (ListView) findViewById(R.id.settings_list_view);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {

                String title = objects.get(position).getName().toString();
                Context context = SettingsActivity.this;

                switch (position) {
                    case 0: // Sync Configuration

//					syncDialogbox(context, title);
//					Toast.makeText(getApplicationContext(), objects.get(position).getName() + " selected",
//							Toast.LENGTH_SHORT).show();

                        if (localSP.getString("Sync_Status", "").toString().equals("Success")) {
                            LayoutInflater layoutInflater = LayoutInflater.from(context);

                            View promptView = layoutInflater.inflate(R.layout.settings_sqlite_password_layout, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                            alertDialogBuilder.setView(promptView);

                            final EditText password = (EditText) promptView.findViewById(R.id.et_password);

                            alertDialogBuilder
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            if (password.getText().toString().toString().equals("admin@dspl")) {
                                                //Intent myIntent = new Intent(context,ImportActivity.class);
                                                //startActivity(myIntent);
                                                //syncDialogbox(SettingsActivity.this, "Sync Configuration");

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Invalid Password.", Toast.LENGTH_LONG).show();

                                                //dialog.cancel();
                                            }

                                        }
                                    })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();


                                                }
                                            });

                            AlertDialog alertD = alertDialogBuilder.create();

                            alertD.show();
                        } else {
                            //syncDialogbox(context, title);
                            Toast.makeText(getApplicationContext(), objects.get(position).getName() + " selected",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case 1: // Printer Configuration
                    {
                        if (localSP.getString("Sync_Status", "").toString().equals("Success")) {
                            printerDialogbox(context, title);
                            Toast.makeText(getApplicationContext(), objects.get(position).getName() + " selected",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please do the Initial sync to activate this app",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                    case 2: // Reschedule Uploading Details
                    {
                        if (localSP.getString("Sync_Status", "").toString().equals("Success")) {
                            rescheduleUploadDialogbox(context, title);
                            Toast.makeText(getApplicationContext(), objects.get(position).getName() + " selected",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please do the Initial sync to activate this app",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                    case 3: // SQLite Database
                    {
                        if (localSP.getString("Sync_Status", "").toString().equals("Success")) {
                            sqliteDatabaseDialogbox(context, title);
                            Toast.makeText(getApplicationContext(), objects.get(position).getName() + " selected",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please do the Initial sync to activate this app",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                    case 4: // Sales Representative Details
                    {
                        if (localSP.getString("Sync_Status", "").toString().equals("Success")) {
                            repsDetailsDialogbox(context, title);
                            Toast.makeText(getApplicationContext(), objects.get(position).getName() + " selected",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please do the Initial sync to activate this app",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                    case 5: // Sales Rep Route
                    {
                        if (localSP.getString("Sync_Status", "").toString().equals("Success")) {
                            routeAreaDialogbox(context, title);
                            Toast.makeText(getApplicationContext(), objects.get(position).getName() + " selected",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please do the Initial sync to activate this app",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                    break;
                }

            }
        });
    }

    public boolean validate(String mac) {
        Pattern p = Pattern.compile("^([a-fA-F0-9]{2}[:-]){5}[a-fA-F0-9]{2}$");
        Matcher m = p.matcher(mac);
        return m.find();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.act_settings_menu_next, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // next
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

//            case R.id.action_next:
//                // here want chk conditions
//                SalRepDS ds = new SalRepDS(getApplicationContext());
//                if (!ds.getCurrentRepCode().equals("")
//                        && localSP.getString("Sync_Status", "").toString().equals("Success")) {
//
//                    if (localSP.getString("first_login", "").toString().equals("Success")) {
//
//                        Intent mainActivity = new Intent(SettingsActivity.this, MainActivity.class);
//                        startActivity(mainActivity);
//                        finish();
//
//                    } else {
//                        Intent mainActivity = new Intent(SettingsActivity.this, LoginActivity.class);
//                        startActivity(mainActivity);
//                        finish();
//                    }
//                } else { // if(localSP.getString("Sync_Status",
//                    // "").toString().equals(""))
//                    Toast.makeText(getApplicationContext(), "Please do the Initial sync to activate this app.",
//                            Toast.LENGTH_LONG).show();
//                }
            // return true;
        }
        return false;
    }

    // Title bar color
    @SuppressLint("NewApi")
    private void setTitleBarColor() {
        ActionBar bar = getActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4682B4")));
        // title
        //  createCutomActionBarTitle("System Configuration");
    }

    @SuppressLint("NewApi")
    private void createCutomActionBarTitle(String title) {

//        this.getActionBar().setDisplayShowCustomEnabled(true);
//        this.getActionBar().setDisplayShowTitleEnabled(false);
//
//        LayoutInflater inflator = LayoutInflater.from(this);
//        View v = inflator.inflate(R.layout.custom_action_bar, null);
//
//        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/coopbl.ttf");
//
//        TextView tvTitle = (TextView) v.findViewById(R.id.titleFragment1);
//        tvTitle.setText(title);
//        tvTitle.setTypeface(tf);
//
//        // assign the view to the actionbar
//        this.getActionBar().setCustomView(v);
    }




    /**
     * Printer com.datamation.swdsfa.Config alert dialog box
     */
    @SuppressWarnings("unused")
    private void printerDialogbox(final Context context, String title) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View promptView = layoutInflater.inflate(R.layout.settings_printer_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setView(promptView);

        final EditText serverURL = (EditText) promptView.findViewById(R.id.et_mac_address);

        serverURL.setText(localSP.getString("printer_mac_address", "").toString());

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Do something
                if (validate(serverURL.getText().toString().toUpperCase())) {
                 //   SharedPreferencesClass.setLocalSharedPreference(context, "printer_mac_address",
                  //          serverURL.getText().toString().toUpperCase());
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Invalid MAC Address Entered. Please Enter Valid MAC Address.", Toast.LENGTH_LONG).show();

                    // dialog.cancel();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
    }

    /**
     * reschedule upload com.datamation.swdsfa.Config alert dialogbox
     */
    @SuppressWarnings("unused")
    private void rescheduleUploadDialogbox(final Context context, String title) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View promptView = layoutInflater.inflate(R.layout.settings_reschedule_uploading_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setView(promptView);

        EditText serverURL = (EditText) promptView.findViewById(R.id.et_mac_address);
        final DatePicker fromDatePicker = (DatePicker) promptView.findViewById(R.id.datePicker1);
        final DatePicker ToDatePicker = (DatePicker) promptView.findViewById(R.id.datePicker2);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        Log.v("Date", "From " + getDateFromDatePicker(fromDatePicker) + " To " + getDateFromDatePicker(ToDatePicker));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        try {

                            Date date1 = dateFormat.parse(getDateFromDatePicker(fromDatePicker));
                            Date date2 = dateFormat.parse(getDateFromDatePicker(ToDatePicker));

                            if (date1.before(date2)) {
                                int hed = new OrderController(context).DeleteOldOrders(getDateFromDatePicker(fromDatePicker), getDateFromDatePicker(ToDatePicker));

                                if (hed > 0) {
                                   // int det = new OrderDetailController(context).DeleteOldOrders(getDateFromDatePicker(fromDatePicker), getDateFromDatePicker(ToDatePicker));
                                    Toast.makeText(getApplicationContext(), hed + " Orders Deleted Successfully", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), "No Orders To Delete For The Selected Date Range", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid Date Range", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception ex) {

                        } finally {

                        }


                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();


                            }
                        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
    }


//        Calendar c = Calendar.getInstance();
//        int cyear = c.get(Calendar.YEAR);
//        int cmonth = c.get(Calendar.MONTH) + 1;
//        DecimalFormat df_month = new DecimalFormat("00");
//


    @SuppressWarnings({"deprecation", "unused"})
    public String getDateFromDatePicker(DatePicker datePicker) {

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear() - 1900;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = sdf.format(new Date(year, month, day));

        return formatedDate;

    }

    /**
     * SQLite Database com.datamation.swdsfa.Config alert dialogbox
     */
    @SuppressWarnings("unused")
    private void sqliteDatabaseDialogbox(final Context context, String title) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View promptView = layoutInflater.inflate(R.layout.settings_sqlite_database_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setView(promptView);

        final Button b_backups = (Button) promptView.findViewById(R.id.b_backups);
        final Button b_restore = (Button) promptView.findViewById(R.id.b_restore);

        b_backups.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("Backups", "OnClick");
                SQLiteBackUp backUp = new SQLiteBackUp(getApplicationContext());
                backUp.exportDB();
            }
        });

        b_restore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("Restore", "OnClick");
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View promptView = layoutInflater.inflate(R.layout.settings_sqlite_password_layout, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptView);

                final EditText password = (EditText) promptView.findViewById(R.id.et_password);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if (password.getText().toString().toString().equals("adminrs@dspl")) {
                                    Intent myIntent = new Intent(context, ImportActivity.class);
                                    startActivity(myIntent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Invalid Password.", Toast.LENGTH_LONG).show();

                                    //dialog.cancel();
                                }

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();


                                    }
                                });

                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();
                /*Intent myIntent = new Intent(context,ImportActivity.class);
                startActivity(myIntent);*/

            }
        });

        alertDialogBuilder.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
    }

    /**
     * Reps details alert dialogbox
     */
    @SuppressWarnings("unused")
    private void repsDetailsDialogbox(final Context context, String title) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View promptView = layoutInflater.inflate(R.layout.settings_reps_details_layout, null);

        final EditText etUserName = (EditText) promptView.findViewById(R.id.et_rep_username);
        final EditText etRepCode = (EditText) promptView.findViewById(R.id.et_rep_code);
        final EditText etPreFix = (EditText) promptView.findViewById(R.id.et_rep_prefix);
        final EditText etLocCode = (EditText) promptView.findViewById(R.id.et_rep_loc_code);
        final EditText etAreaCode = (EditText) promptView.findViewById(R.id.et_rep_area_code);
        final EditText etDealCode = (EditText) promptView.findViewById(R.id.et_rep_deal_code);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setView(promptView);


        SalRep salRep = SharedPref.getInstance(this).getLoginUser();


            etUserName.setText(salRep.getRepCode());
            etRepCode.setText(salRep.getNAME());
          //  etPreFix.setText(salRep.getRoute());


        alertDialogBuilder.setCancelable(false)
                // .setPositiveButton("OK", new
                // DialogInterface.OnClickListener() {
                // public void onClick(DialogInterface dialog, int id) {
                //
                //
                //
                //
                //
                // }
                // })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
    }

    /**
     * Reps details alert dialogbox
     */
    @SuppressWarnings("unused")
    private void routeAreaDialogbox(final Context context, String title) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View promptView = layoutInflater.inflate(R.layout.settings_route_plan, null);
        final Spinner sp_route_area = (Spinner) promptView.findViewById(R.id.sp_route_area);
        final EditText route_code = (EditText) promptView.findViewById(R.id.route_code);

        final RouteController ds = new RouteController(getApplicationContext());


       // List<String> list = ds.getAllRouteByRepCode(repDS.getCurrentRepCode());

//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        sp_route_area.setAdapter(dataAdapter);
//
//        String currentRoute = sp_route_area.getSelectedItem().toString();



//        if (!SharedPref.getInstance(this).getLoginUser().getRoute().equals(""))
//            route_code.setText(SharedPref.getInstance(this).getLoginUser().getRoute());

        sp_route_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentRoute = sp_route_area.getSelectedItem().toString();

//                if (!currentRoute.equals(""))
//                    route_code.setText(SharedPref.getInstance(SettingsActivity.this).getLoginUser().getRoute());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                //SharedPreferencesClass.setLocalSharedPreference(getApplicationContext(), "Current_Route",
                 //       sp_route_area.getSelectedItem().toString());

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

}
