package com.datamation.swdsfa.nonproductive;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.adapter.CustomerAdapter;
import com.datamation.swdsfa.controller.CustomerController;
import com.datamation.swdsfa.helpers.IResponseListener;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.Customer;
import com.datamation.swdsfa.model.DayNPrdHed;
import com.datamation.swdsfa.view.ActivityHome;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Rashmi**/

public class NonProductiveCustomer extends Fragment{
    View view;
    ListView lvCustomers;
    ArrayList<Customer> customerList;
    Customer customer;
    CustomerAdapter customerAdapter;
    SharedPref mSharedPref;
    IResponseListener listener;
    TextView txtCusName;
    private DayNPrdHed tmpNonPrdHed;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frag_promo_sale_customer, container, false);
        mSharedPref = SharedPref.getInstance(getActivity());
        lvCustomers = (ListView) view.findViewById(R.id.cus_lv);
        ActivityHome home = new ActivityHome();
        //-----------------------------------from Re order Only-----------------------------------------------------------------------------------
        Bundle mBundle = getArguments();

        if (mBundle != null) {
            tmpNonPrdHed = (DayNPrdHed) mBundle.getSerializable("nonPrdHed");
            if (tmpNonPrdHed != null) {
                home.selectedDebtor = new CustomerController(getActivity()).getSelectedCustomerByCode(tmpNonPrdHed.getNONPRDHED_DEBCODE());
                Log.d("<>*non prod cus****", "" + tmpNonPrdHed.getNONPRDHED_DEBCODE());
                mSharedPref.setGlobalVal("NonkeyCustomer", "Y");
                mSharedPref.setGlobalVal("NonkeyCusCode",  home.selectedDebtor.getCusCode());
            }

        }

           /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
        customerList = new CustomerController(getActivity()).getAllCustomers();
        customerAdapter = new CustomerAdapter(getActivity(), customerList);
            lvCustomers.setAdapter(customerAdapter);

        if (home.selectedDebtor != null)
            txtCusName.setText(home.selectedDebtor.getCusName());

        /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lvCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {

				/* Check whether automatic date time option checked or not */
                int x = android.provider.Settings.Global.getInt(getActivity().getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0);
                /* If option is selected */
                if (x > 0) {
                    ActivityHome activity = (ActivityHome) getActivity();
                    customer = customerList.get(position);
                    activity.selectedDebtor = customer;
                    activity.cusPosition = position;
                   // txtCusName.setText(activity.selectedDebtor.getCusName());
                    lvCustomers.setAdapter(null);
                    mSharedPref.setGlobalVal("NonkeyCustomer", "Y");
                    mSharedPref.setGlobalVal("NonkeyCusCode", customer.getCusCode());

                    Log.d("NONKEYCUSTOMER", SharedPref.getInstance(getActivity()).getGlobalVal("NonkeyCustomer"));
                    Log.d("NONKEYCUSTOMERCODE", SharedPref.getInstance(getActivity()).getGlobalVal("NonkeyCusCode"));
                    navigateToHeader(position);

					/* if not selected */
                } else {
                    android.widget.Toast.makeText(getActivity(), "Please tick the 'Automatic Date and Time' option to continue..", android.widget.Toast.LENGTH_SHORT).show();
                    /* Show Date/time settings dialog */
                    Log.d("NONKEYCUSTOMER", "???????");
                    Log.d("NONKEYCUSTOMERCODE", "????????");
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }
            }
        });

		/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        return view;
    }
/*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void navigateToHeader(int position) {

        ActivityHome activity = (ActivityHome) getActivity();
        customer = customerList.get(position);
        activity.selectedDebtor = customer;
        android.widget.Toast.makeText(getActivity(), customer.getCusName() + " selected", android.widget.Toast.LENGTH_LONG).show();
        mSharedPref.setGlobalVal("NonkeyCustomer", "Y");
        listener.moveNextFragment_NonProd();
    }

      /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (IResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/


}
