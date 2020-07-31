package com.datamation.hmdsfa.barcode.upload;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

import com.datamation.hmdsfa.api.TaskTypeUpload;
import com.datamation.hmdsfa.controller.DayNPrdHedController;
import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.helpers.UploadTaskListener;
import com.datamation.hmdsfa.model.DayNPrdHed;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UploadNonProd extends AsyncTask<ArrayList<DayNPrdHed>, Integer, ArrayList<DayNPrdHed>> {

    Context context;
    ProgressDialog dialog;
    UploadTaskListener taskListener;
    NetworkFunctions networkFunctions;
    int totalRecords;
    private Handler mHandler;
    List<String> resultListNonproctives;
    TaskTypeUpload taskType;

    public UploadNonProd(Context context, UploadTaskListener taskListener, TaskTypeUpload taskType) {
        this.context = context;
        this.taskListener = taskListener;
        mHandler = new Handler(Looper.getMainLooper());
        this.taskType = taskType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Uploading nonproductive records");
        dialog.show();
    }

    @Override
    protected ArrayList<DayNPrdHed> doInBackground(ArrayList<DayNPrdHed>... params) {

        int recordCount = 0;
        publishProgress(recordCount);
        networkFunctions = new NetworkFunctions(context);

        ArrayList<DayNPrdHed> RCSList = params[0];
        totalRecords = RCSList.size();

        for (DayNPrdHed c : RCSList) {



            ++recordCount;
            publishProgress(recordCount);
        }

        return RCSList;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        dialog.setMessage("Uploading.. Non Prod Record " + values[0] + "/" + totalRecords);
    }

    @Override
    protected void onPostExecute(ArrayList<DayNPrdHed> NonPrdList) {

        super.onPostExecute(NonPrdList);
        dialog.dismiss();
        taskListener.onTaskCompleted(taskType,resultListNonproctives);
    }
    private void addRefNoResults(String ref, int count) {
        resultListNonproctives.add(ref);
        if(count == resultListNonproctives.size()) {
            mUploadResult(resultListNonproctives);
        }
    }

    public void mUploadResult(List<String> messages) {
        String msg = "";
        for (String s : messages) {
            msg += s;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setTitle("Upload nonproductive summary");

        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
