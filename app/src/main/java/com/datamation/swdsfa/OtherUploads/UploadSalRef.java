package com.datamation.swdsfa.OtherUploads;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.datamation.swdsfa.controller.SalRepController;
import com.datamation.swdsfa.helpers.NetworkFunctions;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.helpers.UploadTaskListener;
import com.datamation.swdsfa.model.NewCustomer;
import com.datamation.swdsfa.model.SalRep;
import com.datamation.swdsfa.settings.TaskType;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UploadSalRef extends AsyncTask<ArrayList<SalRep>, Integer, ArrayList<SalRep>> {

    Context context;
    public static final String SETTINGS = "SETTINGS";
    UploadTaskListener taskListener;
    TaskType taskType;
    ProgressDialog pDialog;
    int totalRecords;
    NetworkFunctions networkFunctions;
    ArrayList<SalRep> fSalReplist =  new ArrayList<>();
    public static SharedPreferences localSP;
    private String repcode;
    SalRepController salRepController;

    public UploadSalRef(Context context,UploadTaskListener taskListener,ArrayList<SalRep> repDeatil)
    {
        this.context = context;
        this.taskListener = taskListener;
        this.taskType = taskType;
        fSalReplist.addAll(repDeatil);
        salRepController = new SalRepController(context);

        localSP = context.getSharedPreferences(SETTINGS,Context.MODE_PRIVATE + Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Uploading Sales Representative Data.....");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<SalRep> salReps) {
        super.onPostExecute(salReps);

        List<String> list = new ArrayList<>();
        pDialog.dismiss();
    }

    @Override
    protected ArrayList<SalRep> doInBackground(ArrayList<SalRep>... arrayLists) {

        int recordCount = 0 ;
        publishProgress(recordCount);
        networkFunctions = new NetworkFunctions(context);

        totalRecords = fSalReplist.size();

        final String sp_url = localSP.getString("URL","").toString();
        String URL = "http://" +sp_url;
        Log.v("## Json ##", URL.toString());

        //create json list
        for(SalRep sList: fSalReplist)
        {
           repcode = sList.getRepCode();
           ArrayList<String> jsonlist = new ArrayList<>();
           String sJsonHed = new Gson().toJson(sList);
           jsonlist.add(sJsonHed);
           Log.v("## json ##" , jsonlist.toString());

            try
            {
                boolean bStatus = NetworkFunctions.mHttpManager(networkFunctions.syncEmailUpdatedSalrep(),jsonlist.toString());

                if(bStatus)
                {
                   salRepController.updateIsSynced(repcode,"1");
                    pDialog.dismiss();
                }
                else
                {
                    salRepController.updateIsSynced(repcode,"0");
                }
            }
            catch (Exception e)
            {
                    e.getStackTrace();
            }

        }

        return null;
    }
}
