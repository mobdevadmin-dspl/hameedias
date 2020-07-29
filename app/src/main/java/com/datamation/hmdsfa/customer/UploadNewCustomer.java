package com.datamation.hmdsfa.customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.helpers.UploadTaskListener;
import com.google.gson.Gson;
import com.datamation.hmdsfa.controller.NewCustomerController;
import com.datamation.hmdsfa.model.NewCustomer;
import com.datamation.hmdsfa.settings.TaskTypeDownload;

import java.util.ArrayList;
import java.util.List;


public class UploadNewCustomer extends AsyncTask<ArrayList<NewCustomer>, Integer, ArrayList<NewCustomer>> {

    Context context;
    public static final String SETTINGS = "SETTINGS";

    UploadTaskListener taskListener;
    TaskTypeDownload taskType;
    ProgressDialog pDialog;
    int totalRecords;
    NetworkFunctions networkFunctions;
    ArrayList<NewCustomer> fNewCustomerslist = new ArrayList<>();
    public static SharedPreferences localSP;
    private String customerID;
    NewCustomerController newCustomerDS;

    public UploadNewCustomer(Context context, UploadTaskListener taskListener, ArrayList<NewCustomer> ordList) {

        this.context = context;
        this.taskListener = taskListener;
        this.taskType = taskType;
        fNewCustomerslist.addAll(ordList);
        newCustomerDS = new NewCustomerController(context);

//        localSP = context.getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Uploading New Customers...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<NewCustomer> fNewCustomers) {
        super.onPostExecute(fNewCustomers);
        List<String> list = new ArrayList<>();
        pDialog.dismiss();
    }

    @Override
    protected ArrayList<NewCustomer> doInBackground(ArrayList<NewCustomer>... arrayLists) {

        int recordCount = 0;
        publishProgress(recordCount);
        networkFunctions = new NetworkFunctions(context);

        // ArrayList<fNewCustomer> fNewCustomersList = arrayLists[0];
        totalRecords = fNewCustomerslist.size();

        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;
        Log.v("## Json ##", URL.toString());

        //create json list
        for (NewCustomer fnc : fNewCustomerslist) {

            customerID = fnc.getCUSTOMER_ID();
            ArrayList<String> jsonlist = new ArrayList<>();
            String sJsonHed = new Gson().toJson(fnc);

            jsonlist.add(sJsonHed);
            Log.v("## Json ##", jsonlist.toString());

            try {

                boolean bStatus = NetworkFunctions.mHttpManager(networkFunctions.syncNewCustomers(),jsonlist.toString());
                if(bStatus){
                    newCustomerDS.updateIsSynced(customerID, "1");
                    pDialog.dismiss();
                }else {
                    newCustomerDS.updateIsSynced(customerID, "0");
                    Toast.makeText(context,"Customer not uploaded",Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.getStackTrace();
            }

//            ++recordCount;
//            publishProgress(recordCount);

        }
        return null;
    }
}





