package com.datamation.swdsfa.salesreturn;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.swdsfa.R;
import com.datamation.swdsfa.adapter.ReturnReasonAdapter;
import com.datamation.swdsfa.controller.ReasonController;
import com.datamation.swdsfa.controller.RouteController;
import com.datamation.swdsfa.controller.SalRepController;
import com.datamation.swdsfa.controller.SalesReturnController;
import com.datamation.swdsfa.helpers.SalesReturnResponseListener;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.FInvRHed;
import com.datamation.swdsfa.model.Reason;
import com.datamation.swdsfa.settings.ReferenceNum;
import com.datamation.swdsfa.utils.LocationProvider;
import com.datamation.swdsfa.view.SalesReturnActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SalesReturnHeader extends Fragment implements View.OnClickListener{
    public static final String SETTINGS = "SETTINGS";
    View view;
    private FloatingActionButton next;
    public static EditText ordno, date, mNo, deldate, remarks, lblReason;
    public String LOG_TAG = "OrderHeaderFragment";
    public TextView route, costcenter, cusName;
    private LocationProvider locationProvider;
    private Location finalLocation;
    private Button reasonSearch;
    SharedPref pref;
    public static SharedPreferences localSP;
    SalesReturnResponseListener salesReturnResponseListener;
    MyReceiver r;
    //private Spinner spnReason;
    SalesReturnActivity activity;
    ArrayList<Reason> reasonList = null;
    Reason selectedReason = null;
    private String directRetRefNo;
    //SharedPreferencesClass localSP;

    public SalesReturnHeader()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sales_retrun_header, container, false);
        localSP = getActivity().getSharedPreferences(SETTINGS, 0);
        activity = (SalesReturnActivity)getActivity();
        next = (FloatingActionButton) view.findViewById(R.id.fab);
        pref = SharedPref.getInstance(getActivity());

        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy"); //change this
        String formattedDate = simpleDateFormat.format(d);
        ReferenceNum referenceNum = new ReferenceNum(getActivity());

        ordno = (EditText) view.findViewById(R.id.editTextRtnOrdno);
        date = (EditText) view.findViewById(R.id.editTextRtnDate);
        mNo        = (EditText) view.findViewById(R.id.editTextRtnManualNo);
        remarks    = (EditText) view.findViewById(R.id.editTextRtnRemarks);
        route = (TextView) view.findViewById(R.id.editTextRtnRoute);
        cusName = (TextView) view.findViewById(R.id.textViewrRtnCustomer);
        reasonSearch = (Button)view.findViewById(R.id.btn_reason_search);
        lblReason = (EditText) view.findViewById(R.id.editTextReason);

        reasonSearch.setOnClickListener(this);
        //spnReason = (Spinner)view.findViewById(R.id.spinnerRtnReason);

        cusName.setText(pref.getSelectedDebName());
        route.setText(new RouteController(getActivity()).getRouteNameByCode(pref.getSelectedDebRouteCode()));
        date.setText(formattedDate);

        if (new SalesReturnController(getActivity()).getDirectSalesReturnRefNo().equals(""))
        {
            directRetRefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        }
        else
        {
            directRetRefNo = new SalesReturnController(getActivity()).getDirectSalesReturnRefNo();
        }

        ordno.setText(directRetRefNo);

        if (new SalesReturnController(getActivity()).isAnyActive())
        {
//            FInvRHed hed = new SalesReturnController(getActivity()).getActiveReturnHed(referenceNum.getCurrentRefNo(getResources().getString(R.string.salRet)));
            FInvRHed hed = new SalesReturnController(getActivity()).getActiveReturnHed(directRetRefNo);

            mNo.setText(hed.getFINVRHED_MANUREF());
            remarks.setText(hed.getFINVRHED_REMARKS());
            lblReason.setText(new ReasonController(getActivity()).getReasonByReaCode(hed.getFINVRHED_REASON_CODE()));
        }

        ArrayList<String> reasonList = new ArrayList<String>();
        reasonList = new ReasonController(getActivity()).getReasonName();

        final ArrayAdapter<String> reasonAdapter = new ArrayAdapter<String>(getActivity(),R.layout.reason_spinner_item, reasonList);
        reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spnReason.setAdapter(reasonAdapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cusName.getText().toString().equals("")|| ordno.getText().toString().equals("")||date.getText().toString().equals(""))
                {
                    salesReturnResponseListener.moveBackTo_ret(0);
                    Toast.makeText(getActivity(), "Can not proceed without Route...", Toast.LENGTH_LONG).show();
                }
                else
                {
                    SaveReturnHeader();
                }

            }
        });

        locationProvider = new LocationProvider((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE),
                new LocationProvider.ICustomLocationListener() {

                    @Override
                    public void onProviderEnabled(String provider) {
                        Log.d(LOG_TAG, "Provider enabled");
                        locationProvider.startLocating();
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Log.d(LOG_TAG, "Provider disabled");
                        locationProvider.stopLocating();
                    }

                    @Override
                    public void onUnableToGetLocation() {
                        Toast.makeText(getActivity(), "Unable to get location", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onGotLocation(Location location, int locationType) {
                        if (location != null) {
                            finalLocation = location;

                            SharedPref.getInstance(getActivity()).setGlobalVal("startLongitude", String.valueOf(finalLocation.getLongitude()));
                            SharedPref.getInstance(getActivity()).setGlobalVal("startLatitude", String.valueOf(finalLocation.getLatitude()));
                            System.currentTimeMillis();
                        }
                    }

                    @Override
                    public void onProgress(int type) {
                        if (type == LocationProvider.LOCATION_TYPE_GPS) {
                            Toast.makeText(getActivity(),"Getting location (GPS)",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(),"Getting location (Network)",Toast.LENGTH_LONG).show();

                        }
                    }
                });
        try {
            locationProvider.setOnGPSTimeoutListener(new LocationProvider.OnGPSTimeoutListener() {
                @Override
                public void onGPSTimeOut() {

                    MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                            .content("Please move to a more clear location to get GPS")
                            .positiveText("Try Again")
                            .positiveColor(getResources().getColor(R.color.material_alert_neutral_button))
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    locationProvider.startLocating();
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
            }, 0);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }


        return view;
    }

    private String currentTime() {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }


    public void SaveReturnHeader() {

        if (ordno.getText().length() > 0 && !lblReason.getText().toString().equals(""))
//        if (ordno.getText().length() > 0 )
        {
            FInvRHed hed =new FInvRHed();

            hed.setFINVRHED_REFNO(ordno.getText().toString());
            hed.setFINVRHED_DEBCODE(pref.getSelectedDebCode());
            hed.setFINVRHED_TXN_DATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            hed.setFINVRHED_ROUTE_CODE(pref.getSelectedDebRouteCode());
            hed.setFINVRHED_MANUREF(mNo.getText().toString());
            hed.setFINVRHED_REMARKS(remarks.getText().toString());
//            hed.setFINVRHED_TXNTYPE("Return");
            hed.setFINVRHED_IS_ACTIVE("1");
            hed.setFINVRHED_IS_SYNCED("0");
            hed.setFINVRHED_ADD_DATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            hed.setFINVRHED_ADD_MACH(localSP.getString("MAC_Address", "No MAC Address").toString());
            hed.setFINVRHED_REP_CODE(new SalRepController(getActivity()).getCurrentRepCode().trim());
            hed.setFINVRHED_LONGITUDE(SharedPref.getInstance(getActivity()).getGlobalVal("startLongitude"));
            hed.setFINVRHED_LATITUDE(SharedPref.getInstance(getActivity()).getGlobalVal("startLatitude"));
            hed.setFINVRHED_START_TIME(currentTime());
            hed.setFINVRHED_REASON_CODE(new ReasonController(getActivity()).getReaCodeByName(lblReason.getText().toString()));

            activity.selectedReturnHed = hed;
            ArrayList<FInvRHed> ordHedList=new ArrayList<FInvRHed>();
            ordHedList.add(hed);

            if (new SalesReturnController(getActivity()).createOrUpdateInvRHed(ordHedList) > 0)
            {
                salesReturnResponseListener.moveNextTo_ret(1);
                Toast.makeText(getActivity(),"Return Header Saved...", Toast.LENGTH_LONG).show();
                Log.d("SALES_RETRUN", "HEADER_IS:" + activity.selectedReturnHed);
            }

//            if (returnHed.createOrUpdateInvRHed(ordHedList)>0)
//            {
//                salesReturnResponseListener.moveNextTo_ret(1);
//                Toast.makeText(getActivity(),"Return Header Saved...", Toast.LENGTH_LONG).show();
//            }
        }
        else
        {
            Toast.makeText(getActivity(), "Please select a reason", Toast.LENGTH_LONG).show();
        }
    }

    public void mRefreshHeader() {

        Log.d("SALES_RETRUN", "HEADER_FROM_FETCH_DATA");

        if (SharedPref.getInstance(getActivity()).getGlobalVal("PrekeyCustomer").equals("Y")) {

            date.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            //  route.setText(SharedPref.getInstance(getActivity()).getLoginUser().getRoute()+" - "+new RouteController(getActivity()).getRouteNameByCode(SharedPref.getInstance(getActivity()).getLoginUser().getRoute()));
            deldate.setEnabled(true);
            remarks.setEnabled(true);
            mNo.setEnabled(true);
            cusName.setText(SharedPref.getInstance(getActivity()).getGlobalVal("PrekeyCusName"));
            ordno.setText(new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet)));

//            if (home.selectedOrdHed != null) {
//                //if (home.selectedDebtor == null)
//                // home.selectedDebtor = new FmDebtorDS(getActivity()).getSelectedCustomerByCode(home.selectedOrdHed.getFORDHED_DEB_CODE());
//
////                cusName.setText(home.selectedDebtor.getCusName());
////                ordno.setText(home.selectedOrdHed.getORDHED_REFNO());
////                deldate.setText(home.selectedOrdHed.getORDHED_DELV_DATE());
////                mNo.setText(home.selectedOrdHed.getORDHED_MANU_REF());
////                remarks.setText(home.selectedOrdHed.getORDHED_REMARKS());
//
//            } else {
//
//                ordno.setText(new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet)));
//                deldate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//                SaveReturnHeader();
//            }

        } else {
            Toast.makeText(getActivity(), "Select a customer to continue...", Toast.LENGTH_SHORT).show();
            remarks.setEnabled(false);
            mNo.setEnabled(false);
            deldate.setEnabled(false);
        }

    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_HEADER"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_reason_search:
                reasonsDialogbox();
                break;
            default:
                break;
        }
    }

    public void reasonsDialogbox() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.return_reason_item);
        final ListView reasonListView = (ListView) dialog.findViewById(R.id.lv_reason_list);
        dialog.setCancelable(true);
        reasonListView.clearTextFilter();

        reasonList = new ReasonController(getActivity()).getAllReasons();

        reasonListView.setAdapter(new ReturnReasonAdapter(getActivity(), reasonList));

        reasonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedReason = reasonList.get(position);
                lblReason.setText(selectedReason.getFREASON_NAME());
                dialog.dismiss();

            }
        });

        dialog.show();

    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            SalesReturnHeader.this.mRefreshHeader();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            salesReturnResponseListener = (SalesReturnResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }

}
