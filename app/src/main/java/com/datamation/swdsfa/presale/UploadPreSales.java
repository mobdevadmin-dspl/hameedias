package com.datamation.swdsfa.presale;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.InvHedController;
import com.datamation.swdsfa.controller.OrderController;
import com.datamation.swdsfa.helpers.NetworkFunctions;
import com.datamation.swdsfa.helpers.UploadTaskListener;
import com.datamation.swdsfa.model.InvHed;
import com.datamation.swdsfa.model.Order;
import com.datamation.swdsfa.utils.UtilityContainer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UploadPreSales extends AsyncTask<ArrayList<Order>, Integer, ArrayList<Order>> {

    // Shared Preferences variables
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    ProgressDialog dialog;
    UploadTaskListener taskListener;

    NetworkFunctions networkFunctions;
    int totalRecords;

    public UploadPreSales(Context context, UploadTaskListener taskListener) {

        this.context = context;
        this.taskListener = taskListener;
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Uploading order records");
        dialog.show();
    }

    @Override
    protected ArrayList<Order> doInBackground(ArrayList<Order>... params) {

        int recordCount = 0;
        publishProgress(recordCount);
        networkFunctions = new NetworkFunctions(context);
        ArrayList<Order> RCSList = params[0];
        totalRecords = RCSList.size();
        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;

        for (Order c : RCSList)
        {
            try {
                List<String> List = new ArrayList<String>();
                String sJsonHed = new Gson().toJson(c);
                List.add(sJsonHed);
//                String sURL = URL + context.getResources().getString(R.string.ConnectionURL) + "/insertFOrdHed";
                boolean bStatus = NetworkFunctions.mHttpManager(networkFunctions.syncOrder(),List.toString());

                if (bStatus) {
                    c.setORDER_IS_SYNCED("1");
                } else {
                    c.setORDER_IS_SYNCED("0");
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
        dialog.setMessage("Uploading.. PreSale Record " + values[0] + "/" + totalRecords);
    }

    @Override
    protected void onPostExecute(ArrayList<Order> RCSList) {

        super.onPostExecute(RCSList);
        List<String> list = new ArrayList<>();

        if (RCSList.size() > 0) {
            list.add("\nPRE SALES");
            list.add("------------------------------------\n");
        }

        int i = 1;
        for (Order c : RCSList) {
            new OrderController(context).updateIsSynced(c);

            if (c.getORDER_IS_SYNCED().equals("1")) {
                list.add(i + ". " + c.getORDER_REFNO() + " --> Success\n");
            } else {
                list.add(i + ". " + c.getORDER_REFNO() + " --> Failed\n");
            }
            i++;
        }

        dialog.dismiss();
        taskListener.onTaskCompleted(list);
    }

}
