package com.datamation.swdsfa.customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.datamation.swdsfa.controller.OrderController;
import com.datamation.swdsfa.helpers.NetworkFunctions;
import com.datamation.swdsfa.helpers.UploadTaskListener;
import com.datamation.swdsfa.model.Order;
import com.google.gson.Gson;
import com.datamation.swdsfa.controller.NewCustomerController;
import com.datamation.swdsfa.model.NewCustomer;
import com.datamation.swdsfa.settings.TaskType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


public class UploadNewCustomer extends AsyncTask<ArrayList<NewCustomer>, Integer, ArrayList<NewCustomer>> {

    Context context;
    public static final String SETTINGS = "SETTINGS";

    UploadTaskListener taskListener;
    TaskType taskType;
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





