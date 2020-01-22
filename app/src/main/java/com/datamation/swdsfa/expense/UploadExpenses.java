package com.datamation.swdsfa.expense;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.datamation.swdsfa.controller.DayExpHedController;
import com.datamation.swdsfa.helpers.NetworkFunctions;
import com.datamation.swdsfa.helpers.UploadTaskListener;
import com.datamation.swdsfa.model.DayExpHed;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UploadExpenses extends AsyncTask<ArrayList<DayExpHed>, Integer, ArrayList<DayExpHed>> {

    // Shared Preferences variables
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    ProgressDialog dialog;
    UploadTaskListener taskListener;
    NetworkFunctions networkFunctions;
    int totalRecords;

    public UploadExpenses(Context context, UploadTaskListener taskListener) {

        this.context = context;
        this.taskListener = taskListener;

        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Uploading expense records");
        dialog.show();
    }

    @Override
    protected ArrayList<DayExpHed> doInBackground(ArrayList<DayExpHed>... params) {

        int recordCount = 0;
        publishProgress(recordCount);networkFunctions = new NetworkFunctions(context);
        ArrayList<DayExpHed> RCSList = params[0];
        totalRecords = RCSList.size();
        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;

        for (DayExpHed c : RCSList) {

            try {

                List<String> List = new ArrayList<String>();
                String sJsonHed = new Gson().toJson(c);
                List.add(sJsonHed);
                boolean bStatus = NetworkFunctions.mHttpManager(networkFunctions.syncDayExp(),List.toString());

                if (bStatus) {
                    c.setEXP_IS_SYNCED("1");
                } else {
                    c.setEXP_IS_SYNCED("0");
                }

            } catch (Exception e) {
                e.getStackTrace();
            }
            ++recordCount;
            publishProgress(recordCount);
        }
        return RCSList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        dialog.setMessage("Uploading.. Expense Record " + values[0] + "/" + totalRecords);
    }

    @Override
    protected void onPostExecute(ArrayList<DayExpHed> RCSList) {

        super.onPostExecute(RCSList);

        List<String> list = new ArrayList<>();

        if (RCSList.size() > 0)
        {
            list.add("\nDAILYEXPENSE");
            list.add("------------------------------------\n");
        }

        int i = 1;
        for (DayExpHed c : RCSList) {
            new DayExpHedController(context).updateIsSynced(c);

            if (c.getEXP_IS_SYNCED().equals("1")) {
                list.add(i + ". " + c.getEXP_REFNO()+ " --> Success\n");
            } else {
                list.add(i + ". " + c.getEXP_REFNO() + " --> Failed\n");
            }
            i++;
        }
        dialog.dismiss();
        taskListener.onTaskCompleted(list);
    }

}
