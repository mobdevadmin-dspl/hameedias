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
import android.widget.Toast;

import com.datamation.hmdsfa.api.ApiCllient;
import com.datamation.hmdsfa.api.ApiInterface;
import com.datamation.hmdsfa.api.TaskTypeUpload;
import com.datamation.hmdsfa.controller.DayNPrdHedController;
import com.datamation.hmdsfa.controller.InvHedController;
import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.helpers.UploadTaskListener;
import com.datamation.hmdsfa.model.DayNPrdHed;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        final ArrayList<DayNPrdHed> RCSList = params[0];
        totalRecords = RCSList.size();

        for (final DayNPrdHed c : RCSList) {
            try {
                String content_type = "application/json";
                ApiInterface apiInterface = ApiCllient.getClient(context).create(ApiInterface.class);
                JsonParser jsonParser = new JsonParser();
                String orderJson = new Gson().toJson(c);
                JsonObject objectFromString = jsonParser.parse(orderJson).getAsJsonObject();
                JsonArray jsonArray = new JsonArray();
                jsonArray.add(objectFromString);
                Call<String> resultCall = apiInterface.uploadNonProd(jsonArray, content_type);
                resultCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response != null && response.body() != null) {
                            int status = response.code();
                            Log.d(">>>response code", ">>>res " + status);
                            Log.d(">>>response message", ">>>res " + response.message());
                            Log.d(">>>response body", ">>>res " + response.body().toString());
                            int resLength = response.body().toString().trim().length();
                            String resmsg = "" + response.body().toString();
                            if (status == 200 && !resmsg.equals("") && !resmsg.equals(null)) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        c.setNONPRDHED_IS_SYNCED("1");
                                        addRefNoResults(c.getNONPRDHED_REFNO() + " --> Success\n", RCSList.size());
                                        new InvHedController(context).updateIsSynced(c.getNONPRDHED_REFNO(), "1");
                                    }
                                });
                            } else {
                                Log.d(">>response" + status, "" + c.getNONPRDHED_REFNO());
                                c.setNONPRDHED_IS_SYNCED("0");
                                new InvHedController(context).updateIsSynced(c.getNONPRDHED_REFNO(), "0");
                                addRefNoResults(c.getNONPRDHED_REFNO() + " --> Failed\n", RCSList.size());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context, "Error response "+t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
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
