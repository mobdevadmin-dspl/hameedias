package com.datamation.swdsfa.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.swdsfa.controller.AttendanceController;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.Attendance;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.utils.GPSTracker;
import com.datamation.swdsfa.utils.LocationProvider;
import com.datamation.swdsfa.utils.UtilityContainer;
import com.datamation.swdsfa.view.ActivityHome;
import com.datamation.swdsfa.view.DebtorListActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class FragmentMarkAttendance extends Fragment implements View.OnClickListener {

    private View view;
    private EditText editTextDate, editTextStime, editTextVehicle, editTextDriver, editTextAsst, editTextSkm, editTextEtime, editTextEkm, editTextDistance;

    private TextView editTextRoute;
    public LinearLayout dayEnd;
    ArrayList<Attendance> oTours = null;
    private LocationProvider locationProvider;
    GPSTracker gpsTracker;
    private long timeInMillis;
    SharedPref sharedPref;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.day_info, container, false);

        getActivity().setTitle("Mark Attendance");
        setHasOptionsMenu(true);
        //initializations
        gpsTracker = new GPSTracker(getActivity());

        timeInMillis = System.currentTimeMillis();
        editTextDate = (EditText) view.findViewById(R.id.editTextDate);
        editTextStime = (EditText) view.findViewById(R.id.editTextStime);
        editTextVehicle = (EditText) view.findViewById(R.id.editTextVehicle);
        editTextDriver = (EditText) view.findViewById(R.id.editTextDriver);
        editTextAsst = (EditText) view.findViewById(R.id.editTextAsst);
        editTextSkm = (EditText) view.findViewById(R.id.editTextSkm);
        editTextRoute = (TextView) view.findViewById(R.id.editTextRoute);
        dayEnd = (LinearLayout) view.findViewById(R.id.dayEnd);

        editTextEtime = (EditText) view.findViewById(R.id.editTextEtime);
        editTextEkm = (EditText) view.findViewById(R.id.editTextEkm);
        editTextDistance = (EditText) view.findViewById(R.id.editTextDistance);

        editTextDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        editTextStime.setText(new SimpleDateFormat("hh:mm a").format(new Date()));

        Button buttonStart = (Button) view.findViewById(R.id.buttonStart);
        Button buttonEnd = (Button) view.findViewById(R.id.buttonEnd);

        buttonStart.setOnClickListener(this);
        buttonEnd.setOnClickListener(this);

        sharedPref = SharedPref.getInstance(getActivity());
        Log.v("Lati in Nonprdctv>>>>>",sharedPref.getGlobalVal("Latitude"));
        Log.v("Longi in Nonprdctv>>>>",sharedPref.getGlobalVal("Longitude"));
        oTours = new AttendanceController(getActivity()).getIncompleteRecord();
        //show day end after validation
        // editTextRoute.setText(SharedPref.getInstance(getActivity()).getLoginUser().getRoute());
        if (oTours.size() > 0) {

            Attendance tour = oTours.get(0);
            if (tour == null) {

            } else {

                dayEnd.setVisibility(View.VISIBLE);

                editTextDate.setText(tour.getFTOUR_DATE());

                editTextStime.setText(tour.getFTOUR_S_TIME());

                editTextVehicle.setText(tour.getFTOUR_VEHICLE());
                editTextVehicle.setEnabled(false);

                editTextDriver.setText(tour.getFTOUR_DRIVER());
                editTextDriver.setEnabled(false);

                editTextAsst.setText(tour.getFTOUR_ASSIST());
                editTextAsst.setEnabled(false);

                editTextSkm.setText(tour.getFTOUR_S_KM());
                editTextSkm.setEnabled(false);

                editTextRoute.setText(tour.getFTOUR_ROUTE());
                editTextRoute.setEnabled(false);

                editTextEtime.setText(new SimpleDateFormat("hh:mm a").format(new Date()));
            }
        } else {
            Toast.makeText(getActivity(), "Please Enter day end data", Toast.LENGTH_LONG).show();
        }

        editTextEkm.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (editTextEkm.getText().length() > 0 && TextUtils.isDigitsOnly(editTextEkm.getText()))
                    editTextDistance.setText(String.format("%.1f", Double.parseDouble(editTextEkm.getText().toString()) - Double.parseDouble(editTextSkm.getText().toString())));
                else
                    Toast.makeText(getActivity(), "Please Enter valid distance", Toast.LENGTH_LONG).show();

            }
        });

        System.out.println(">>>" + new AttendanceController(getActivity()).isDayEnd(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        if (new AttendanceController(getActivity()).isDayEnd(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {

        } else {

        }

        //DISABLED BACK NAVIGATION
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("", "keyCode: " + keyCode);
                ActivityHome.navigation.setVisibility(View.VISIBLE);

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Toast.makeText(getActivity(), "Back button disabled!", Toast.LENGTH_SHORT).show();
                    return true;
                } else if ((keyCode == KeyEvent.KEYCODE_HOME)) {

                    getActivity().finish();

                    return true;

                } else {
                    return false;
                }
            }
        });


        return view;
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.buttonStart:
                if (TextUtils.isDigitsOnly(editTextEkm.getText())) {
                    if (editTextDate.length() > 0 && editTextStime.length() > 0 && editTextVehicle.length() > 0 && editTextDriver.length() > 0 && editTextAsst.length() > 0) {
                        AttendanceController tourDS = new AttendanceController(getActivity());

                        Attendance tour = new Attendance();
                        tour.setFTOUR_DATE(editTextDate.getText().toString());
                        tour.setFTOUR_S_TIME(editTextStime.getText().toString());
                        tour.setFTOUR_VEHICLE(editTextVehicle.getText().toString());
                        tour.setFTOUR_DRIVER(editTextDriver.getText().toString());
                        tour.setFTOUR_ASSIST(editTextAsst.getText().toString());
                        tour.setFTOUR_S_KM(editTextSkm.getText().toString());
                        tour.setFTOUR_ROUTE(editTextRoute.getText().toString());
                        tour.setFTOUR_IS_SYNCED("0");
                        tour.setFTOUR_MAC(SharedPref.getInstance(getActivity()).getGlobalVal("MAC_Address").toString());
                        tour.setFTOUR_STLATITIUDE(SharedPref.getInstance(getActivity()).getGlobalVal("Longitude").equals("") ? "0.00" : sharedPref.getGlobalVal("Longitude"));
                        tour.setFTOUR_STLONGTITIUDE(SharedPref.getInstance(getActivity()).getGlobalVal("Latitude").equals("") ? "0.00" : sharedPref.getGlobalVal("Latitude"));
                        //   tour.setFTOUR_REPCODE(SharedPref.getInstance(getActivity()).getLoginUser().getCode());

                        //insert into db
                        long result = tourDS.InsertUpdateTourData(tour,0);
                        System.out.println("long" + result);
                        if (result > 0) {

                            SharedPref sharedPref = SharedPref.getInstance(getActivity());
                            sharedPref.setGlobalVal("dayStart", "Y");
                            sharedPref.setGlobalVal("DayStartDate", ""+dateFormat.format(new Date(timeInMillis)));

                            Toast.makeText(getActivity(), "Day start info saved! ", Toast.LENGTH_SHORT).show();
                            clearTextFields();
                            Intent intent = new Intent(getActivity(), DebtorListActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            //UtilityContainer.mLoadFragment(new SalesManagementFragment(), getActivity());
                        }
                    } else {
                        Toast.makeText(getActivity(), "Fill in the fields!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter valid values!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.buttonEnd:
                if (!(editTextEkm.getText().toString().equals("")) && !(editTextSkm.getText().toString().equals("")) && TextUtils.isDigitsOnly(editTextEkm.getText()) && TextUtils.isDigitsOnly(editTextSkm.getText())) {
                    if (Double.parseDouble(editTextEkm.getText().toString()) < Double.parseDouble(editTextSkm.getText().toString())) {
                        Toast.makeText(getActivity(), "Invalid end time!Unable to calculate distance ", Toast.LENGTH_SHORT).show();
                    } else {
                        if (editTextEkm.getText().toString().length() > 0 && TextUtils.isDigitsOnly(editTextEkm.getText())) {
                            Attendance tour = new Attendance();
                            Attendance oTour = oTours.get(0);
                            tour.setFTOUR_DATE(oTour.getFTOUR_DATE());
                            tour.setFTOUR_ROUTE(oTour.getFTOUR_ROUTE());
                            tour.setFTOUR_S_KM(oTour.getFTOUR_S_KM());
                            tour.setFTOUR_S_TIME(oTour.getFTOUR_S_TIME());
                            tour.setFTOUR_VEHICLE(oTour.getFTOUR_VEHICLE());
                            tour.setFTOUR_F_TIME(new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(new Date()));
                            tour.setFTOUR_F_KM(editTextEkm.getText().toString());
                            tour.setFTOUR_DISTANCE(editTextDistance.getText().toString());
                            tour.setFTOUR_DRIVER(oTour.getFTOUR_DRIVER());
                            tour.setFTOUR_ASSIST(oTour.getFTOUR_ASSIST());

                            tour.setFTOUR_IS_SYNCED("0");
                            tour.setFTOUR_MAC(SharedPref.getInstance(getActivity()).getGlobalVal("MAC_Address").toString());
                            tour.setFTOUR_REPCODE(SharedPref.getInstance(getActivity()).getLoginUser().getCode());
                            tour.setFTOUR_ENDLATITIUDE(SharedPref.getInstance(getActivity()).getGlobalVal("Longitude").equals("") ? "0.00" : sharedPref.getGlobalVal("Longitude"));
                            tour.setFTOUR_ENDLONGTITIUDE(SharedPref.getInstance(getActivity()).getGlobalVal("Latitude").equals("") ? "0.00" : sharedPref.getGlobalVal("Latitude"));

                            new AttendanceController(getActivity()).InsertUpdateTourData(tour,1);
                            clearTextFields();
                            Toast.makeText(getActivity(), "Tour End info saved! ", Toast.LENGTH_SHORT).show();
                            // UtilityContainer.mLoadFragment(new FragmentHome(), getActivity());
                            Intent intent = new Intent(getActivity(), ActivityHome.class);
                            startActivity(intent);
                            getActivity().finish();

                        } else {
                            Toast.makeText(getActivity(), "Fill in the fields!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter valid values!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    private void clearTextFields() {

//        editTextDate.setText("");
        editTextEkm.setText("");
        editTextVehicle.setText("");
        editTextDriver.setText("");
        editTextAsst.setText("");
        editTextSkm.setText("");
        editTextRoute.setText("");
        editTextEtime.setText("");
        editTextDistance.setText("");
        editTextRoute.setText("");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sync, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sync:
                uploadAttendance();
                //Toast.makeText(getActivity(),"Synchronize attendance should be developed",Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uploadAttendance() {
        MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                .content("Do you want to upload attendance details?")
                .positiveText("Yes")
                .positiveColor(getResources().getColor(R.color.material_alert_positive_button))
                .negativeText("No")
                .negativeColor(getResources().getColor(R.color.material_alert_negative_button))

                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        Toast.makeText(getActivity(), "Synchronize attendance should be developed", Toast.LENGTH_LONG).show();
                        //TODO:: asynctask for upload attendance
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
}
