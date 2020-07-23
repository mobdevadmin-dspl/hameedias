package com.datamation.hmdsfa.barcode.salesreturn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.ProductAdapter;
import com.datamation.hmdsfa.adapter.SalesReturnDetailsAdapter;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.ReasonController;
import com.datamation.hmdsfa.controller.SalesReturnController;
import com.datamation.hmdsfa.controller.SalesReturnDetController;
import com.datamation.hmdsfa.dialog.CustomKeypadDialog;
import com.datamation.hmdsfa.helpers.SalesReturnResponseListener;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.FInvRDet;
import com.datamation.hmdsfa.model.FInvRHed;
import com.datamation.hmdsfa.model.Item;
import com.datamation.hmdsfa.settings.ReferenceNum;
import com.datamation.hmdsfa.view.SalesReturnActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class BRSalesReturnDetails extends Fragment{

    View view;
    ListView lv_return_det;
    ArrayList<FInvRDet> returnList;
    Spinner returnType;
    ArrayList<Item> list = null;
    SalesReturnActivity activity;
    SweetAlertDialog pDialog;
    String RefNo;
    SalesReturnResponseListener salesReturnResponseListener;
    MyReceiver r;
    SharedPref sharedPref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_bar_code_reader_return, container, false);
        activity = (SalesReturnActivity)getActivity();

        sharedPref = SharedPref.getInstance(getActivity());

        returnType = (Spinner) view.findViewById(R.id.spinner_return_Type);
        lv_return_det = (ListView) view.findViewById(R.id.lvProducts_Inv);

        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));

        if (activity.selectedReturnHed == null)
        {
            activity.selectedReturnHed = new SalesReturnController(getActivity()).getActiveReturnHed(RefNo);
        }

        ArrayList<String> strList = new ArrayList<String>();
        strList.add("Select Return type to continue ...");
        strList.add("MR");
        strList.add("UR");

        final ArrayAdapter<String> returnTypeAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.return_spinner_item, strList);
        returnTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        returnType.setAdapter(returnTypeAdapter);

        FetchData();

        /*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lv_return_det.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteReturnDialog(position);
                return true;
            }
        });

        /*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lv_return_det.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {

            }
        });

        return view;
    }

    private void deleteReturnDialog(final int position) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Are you sure you want to delete this entry?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setTitle("Return Details");
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                int count = new SalesReturnDetController(getActivity()).mDeleteRetDet(returnList.get(position).getFINVRDET_ITEMCODE(),returnList.get(position).getFINVRDET_REFNO());

                if (count > 0)
                {
                    Toast.makeText(getActivity(), "Deleted successfully", Toast.LENGTH_LONG).show();
                    FetchData();
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }



    public void FetchData()
    {
        if (new SalesReturnController(getActivity()).getDirectSalesReturnRefNo().equals("") || new SalesReturnController(getActivity()).getDirectSalesReturnRefNo().equals(""))
        {
            RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        }
        else
        {
            RefNo = new SalesReturnController(getActivity()).getDirectSalesReturnRefNo();
        }

        Log.d("SALES_RETRUN", "DETAILS_FROM_FETCH_DATA: " + RefNo);
        try {
            lv_return_det.setAdapter(null);
            returnList = new SalesReturnDetController(getActivity()).getAllInvRDetForSalesReturn(RefNo);
            lv_return_det.setAdapter(new SalesReturnDetailsAdapter(getActivity(), returnList));

        } catch (NullPointerException e) {
            Log.v(" Error", e.toString());
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

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            BRSalesReturnDetails.this.mRefreshData();
        }
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_RET_DETAILS"));
    }

    public void mRefreshData()
    {
        if (new SalesReturnController(getActivity()).getDirectSalesReturnRefNo().equals(""))
        {
            RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        }
        else
        {
            RefNo = new SalesReturnController(getActivity()).getDirectSalesReturnRefNo();
        }

        Log.d("SALES_RETRUN", "DETAILS_FROM_FETCH_DATA" + RefNo);

        FetchData();
    }
}
