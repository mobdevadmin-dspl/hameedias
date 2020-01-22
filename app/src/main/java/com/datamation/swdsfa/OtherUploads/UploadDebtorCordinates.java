package com.datamation.swdsfa.OtherUploads;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.datamation.swdsfa.controller.CustomerController;
import com.datamation.swdsfa.helpers.NetworkFunctions;
import com.datamation.swdsfa.helpers.UploadTaskListener;
import com.datamation.swdsfa.model.Debtor;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UploadDebtorCordinates extends AsyncTask<ArrayList<Debtor>, Integer, ArrayList<Debtor>> {

    Context context;
    public static final String SETTINGS = "SETTINGS";

    ArrayList<Debtor> fDebtorList = new ArrayList<>();
    int totalRecords;
    private String debtorCode;

    UploadTaskListener taskListener;
    NetworkFunctions networkFunctions;

    ProgressDialog pDialog;
    public static SharedPreferences localSP;
    CustomerController customerController;

    public UploadDebtorCordinates(Context context, UploadTaskListener taskListener, ArrayList<Debtor> ordList) {
        this.context = context;
        this.taskListener = taskListener;
        fDebtorList.addAll(ordList);

//        localSP = context.getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Uploading dealer cordinates...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Debtor> debtors) {
        super.onPostExecute(debtors);
        pDialog.dismiss();
    }

    @Override
    protected ArrayList<Debtor> doInBackground(ArrayList<Debtor>... arrayLists) {
        networkFunctions = new NetworkFunctions(context);
        totalRecords = fDebtorList.size();

        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;
        Log.v("## Json ##", URL.toString());

        for (Debtor debtor : fDebtorList) {

            debtorCode = debtor.getFDEBTOR_CODE();
            ArrayList<String> jsonList = new ArrayList<>();
            String jObject = new Gson().toJson(debtor);
            jsonList.add(jObject);

            try {
                boolean status = NetworkFunctions.mHttpManager(networkFunctions.syncDebtor(), jsonList.toString());
                if (status) {
                    customerController.updateIsSynced(debtorCode, "2");
                    pDialog.dismiss();
                }else{
                    customerController.updateIsSynced(debtorCode, "3");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
