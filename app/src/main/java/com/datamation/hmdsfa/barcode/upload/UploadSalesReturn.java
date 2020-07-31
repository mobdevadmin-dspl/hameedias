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
import com.datamation.hmdsfa.controller.SalesReturnController;
import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.helpers.UploadTaskListener;
import com.datamation.hmdsfa.model.FInvRHed;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadSalesReturn extends AsyncTask<ArrayList<FInvRHed>, Integer, ArrayList<FInvRHed>> {

    Context context;
    ProgressDialog dialog;
    UploadTaskListener taskListener;
    NetworkFunctions networkFunctions;
    int totalRecords;
    private Handler mHandler;
    List<String> resultListSalesReturn;
    TaskTypeUpload taskType;

    public UploadSalesReturn(Context context, UploadTaskListener taskListener, TaskTypeUpload taskType) {
        resultListSalesReturn = new ArrayList<>();
        this.context = context;
        this.taskListener = taskListener;
        mHandler = new Handler(Looper.getMainLooper());
        this.taskType = taskType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Uploading return records");
        dialog.show();
    }

    @Override
    protected ArrayList<FInvRHed> doInBackground(ArrayList<FInvRHed>... params) {

        int recordCount = 0;
        publishProgress(recordCount);
        networkFunctions = new NetworkFunctions(context);
        final ArrayList<FInvRHed> RCSList = params[0];
        totalRecords = RCSList.size();

        for (final FInvRHed c : RCSList) {

            try {
                String content_type = "application/json";
                ApiInterface apiInterface = ApiCllient.getClient(context).create(ApiInterface.class);
                JsonParser jsonParser = new JsonParser();
                String orderJson = new Gson().toJson(c);
                JsonObject objectFromString = jsonParser.parse(orderJson).getAsJsonObject();
                JsonArray jsonArray = new JsonArray();
                jsonArray.add(objectFromString);
                Call<String> resultCall = apiInterface.uploadReturns(jsonArray, content_type);
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
                                        c.setFINVRHED_IS_SYNCED("1");
                                        addRefNoResults(c.getFINVRHED_REFNO() + " --> Success\n", RCSList.size());
                                        new SalesReturnController(context).updateIsSynced(c.getFINVRHED_REFNO(), "1");
                                    }
                                });
                            } else {
                                Log.d(">>response" + status, "" + c.getFINVRHED_REFNO());
                                c.setFINVRHED_IS_SYNCED("0");
                                new SalesReturnController(context).updateIsSynced(c.getFINVRHED_REFNO(), "0");
                                addRefNoResults(c.getFINVRHED_REFNO() + " --> Failed\n", RCSList.size());
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
        dialog.setMessage("Uploading.. Sales Return Record " + values[0] + "/" + totalRecords);
    }

    @Override
    protected void onPostExecute(ArrayList<FInvRHed> RCSList) {

        super.onPostExecute(RCSList);
        dialog.dismiss();
        taskListener.onTaskCompleted(taskType,resultListSalesReturn);
    }
    private void addRefNoResults(String ref, int count) {
        resultListSalesReturn.add(ref);
        if(count == resultListSalesReturn.size()) {
            mUploadResult(resultListSalesReturn);
        }
    }

    public void mUploadResult(List<String> messages) {
        String msg = "";
        for (String s : messages) {
            msg += s;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setTitle("Upload Return Summary");

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

