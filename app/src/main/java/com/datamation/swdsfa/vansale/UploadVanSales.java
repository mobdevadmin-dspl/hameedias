package com.datamation.swdsfa.vansale;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.datamation.swdsfa.controller.InvHedController;
import com.datamation.swdsfa.helpers.NetworkFunctions;
import com.datamation.swdsfa.helpers.UploadTaskListener;
import com.datamation.swdsfa.model.InvHed;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class UploadVanSales extends AsyncTask<ArrayList<InvHed>, Integer, ArrayList<InvHed>> {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    ProgressDialog dialog;
    UploadTaskListener taskListener;
    NetworkFunctions networkFunctions;
    int totalRecords;

    public UploadVanSales(Context context, UploadTaskListener taskListener) {

        this.context = context;
        this.taskListener = taskListener;
        localSP = context.getSharedPreferences(SETTINGS, 0);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Uploading records");
        dialog.show();
    }

    @Override
    protected ArrayList<InvHed> doInBackground(ArrayList<InvHed>... params) {

        int recordCount = 0;
        publishProgress(recordCount);
        networkFunctions = new NetworkFunctions(context);
        ArrayList<InvHed> RCSList = params[0];
        totalRecords = RCSList.size();
        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;

        for (InvHed c : RCSList) {

            try {
                List<String> List = new ArrayList<String>();
                String sJsonHed = new Gson().toJson(c);
                List.add(sJsonHed);
               // String sURL = URL + context.getResources().getString(R.string.ConnectionURL) + "/insertFInvHed";
                //boolean bStatus = UtilityContainer.mHttpManager(sURL, new Gson().toJson(c));
                boolean bStatus = NetworkFunctions.mHttpManager(networkFunctions.syncInvoice(),List.toString());

                if (bStatus)
                    c.setFINVHED_IS_SYNCED("1");
                else
                    c.setFINVHED_IS_SYNCED("0");

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
        dialog.setMessage("Uploading.. Van Sales Record " + values[0] + "/" + totalRecords);
    }

    @Override
    protected void onPostExecute(ArrayList<InvHed> RCSList) {

        super.onPostExecute(RCSList);
        List<String> list = new ArrayList<>();

        if (RCSList.size() > 0) {
            list.add("\nVAN SALES");
            list.add("------------------------------------\n");
        }

        int i = 1;
        for (InvHed c : RCSList) {
            new InvHedController(context).updateIsSynced(c);

            if (c.getFINVHED_IS_SYNCED().equals("1")) {
                list.add(i + ". " + c.getFINVHED_REFNO() + " --> Success\n");
            } else {
                list.add(i + ". " + c.getFINVHED_REFNO() + " --> Failed\n");
            }
            i++;
        }

        dialog.dismiss();
        taskListener.onTaskCompleted( list);
    }
}
