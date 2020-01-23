package com.datamation.hmdsfa.OtherUploads;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.helpers.UploadTaskListener;
import com.datamation.hmdsfa.model.SalRep;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UploadFirebaseTokenKey extends AsyncTask<ArrayList<SalRep>, Integer, ArrayList<SalRep>>  {
    Context context;
    public static final String SETTINGS = "SETTINGS";

    ArrayList<SalRep> fbsalRepList = new ArrayList<>();
    UploadTaskListener taskListener;
    NetworkFunctions networkFunctions;

    ProgressDialog pDialog;
    public static SharedPreferences localSP;

    public UploadFirebaseTokenKey(Context context, UploadTaskListener taskListener,ArrayList<SalRep> salRepList) {
        this.context = context;
        this.taskListener = taskListener;
        fbsalRepList.addAll(salRepList);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(ArrayList<SalRep> salRep) {
        super.onPostExecute(salRep);
    }

    @Override
    protected ArrayList<SalRep> doInBackground(ArrayList<SalRep>... arrayLists) {
        networkFunctions = new NetworkFunctions(context);

        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;
        Log.v("## Json ##", URL.toString());

        for (SalRep salRep : fbsalRepList) {
            ArrayList<String> jsonList = new ArrayList<>();
            String jObject = new Gson().toJson(salRep);
            jsonList.add(jObject);

            try {
                boolean status = NetworkFunctions.mHttpManager(networkFunctions.syncFirebasetoken(), jsonList.toString());
                if (status) {
                    Toast.makeText(context,"Firebase registerID upload success",Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                } else {
                    Toast.makeText(context,"Firebase registerID Failed..!",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
