package com.datamation.swdsfa.salesreturn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.datamation.swdsfa.controller.SalesReturnController;
import com.datamation.swdsfa.helpers.NetworkFunctions;
import com.datamation.swdsfa.helpers.UploadTaskListener;
import com.datamation.swdsfa.model.FInvRHed;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UploadSalesReturn extends AsyncTask<ArrayList<FInvRHed>, Integer, ArrayList<FInvRHed>> {

    Context context;
    ProgressDialog dialog;
    UploadTaskListener taskListener;
    NetworkFunctions networkFunctions;
    int totalRecords;
    String functionName;
    // Shared Preferences variables
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;

    public UploadSalesReturn(Context context, UploadTaskListener taskListener, String function) {

        this.context = context;
        this.taskListener = taskListener;
        this.functionName = function;
        //localSP = context.getSharedPreferences(SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Uploading records");
        dialog.show();
    }

    @Override
    protected ArrayList<FInvRHed> doInBackground(ArrayList<FInvRHed>... params) {

        int recordCount = 0;
        publishProgress(recordCount);
        networkFunctions = new NetworkFunctions(context);
        ArrayList<FInvRHed> RCSList = params[0];
        totalRecords = RCSList.size();
        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;

        for (FInvRHed c : RCSList) {

            try {
                List<String> List = new ArrayList<String>();
                String sJsonHed = new Gson().toJson(c);
                List.add(sJsonHed);

                boolean bStatus = NetworkFunctions.mHttpManager(networkFunctions.syncSalesReturn()+functionName,List.toString());
                // boolean bStatus = UtilityContainer.mHttpManager(sURL, new Gson().toJson(c));

                if (bStatus) {
                    c.setFINVRHED_IS_SYNCED("1");
                } else {
                    c.setFINVRHED_IS_SYNCED("0");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            ++recordCount;
            publishProgress(recordCount);
        }

        return RCSList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        dialog.setMessage("Uploading.. Sales Return Record " + values[0] + "/" + totalRecords);
    }

    @Override
    protected void onPostExecute(ArrayList<FInvRHed> RCSList) {

        super.onPostExecute(RCSList);
        List<String> list = new ArrayList<>();

        if (RCSList.size() > 0) {
            list.add("SALES RETURN SUMMARY\n");
            list.add("------------------------------------\n");
        }
        int i = 1;
        for (FInvRHed c : RCSList) {
            new SalesReturnController(context).updateIsSynced(c);

            if (c.getFINVRHED_IS_SYNCED().equals("1")) {
                list.add(i + ". " + c.getFINVRHED_REFNO() + " --> Success\n");
            } else {
                list.add(i + ". " + c.getFINVRHED_REFNO() + " --> Failed\n");
            }
            i++;
        }
        dialog.dismiss();
        taskListener.onTaskCompleted(list);
    }

}

