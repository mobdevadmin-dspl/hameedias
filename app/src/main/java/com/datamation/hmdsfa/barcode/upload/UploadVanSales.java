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
import com.datamation.hmdsfa.controller.InvHedController;
import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.helpers.UploadTaskListener;
import com.datamation.hmdsfa.model.InvHed;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UploadVanSales extends AsyncTask<ArrayList<InvHed>, Integer, ArrayList<InvHed>> {

    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    Context context;
    ProgressDialog dialog;
    TaskTypeUpload taskType;
    private Handler mHandler;
    List<String> resultListVanSale;
    UploadTaskListener taskListener;
    NetworkFunctions networkFunctions;
    int totalRecords;

    public UploadVanSales(Context context, UploadTaskListener taskListener, TaskTypeUpload taskType) {
        resultListVanSale = new ArrayList<>();
        this.taskType = taskType;
        this.context = context;
        mHandler = new Handler(Looper.getMainLooper());
        this.taskListener = taskListener;
        localSP = context.getSharedPreferences(SETTINGS, 0);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Uploading invoices");
        dialog.show();
    }

   @Override
    protected ArrayList<InvHed> doInBackground(ArrayList<InvHed>... params) {
        int recordCount = 0;
        publishProgress(recordCount);
        networkFunctions = new NetworkFunctions(context);
        final ArrayList<InvHed> RCSList = params[0];
        totalRecords = RCSList.size();
        for (final InvHed c : RCSList)
        {
            try {
                String content_type = "application/json";
                ApiInterface apiInterface = ApiCllient.getClient(context).create(ApiInterface.class);
                JsonParser jsonParser = new JsonParser();
                String orderJson = new Gson().toJson(c);
                JsonObject objectFromString = jsonParser.parse(orderJson).getAsJsonObject();
                JsonArray jsonArray = new JsonArray();
                jsonArray.add(objectFromString);
                Call<String> resultCall = apiInterface.uploadInvoice(jsonArray, content_type);
                resultCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        int status = response.code();
                        Log.d(">>>response code", ">>>res " + status);
                        Log.d(">>>response message", ">>>res " + response.message());
                        Log.d(">>>response body", ">>>res " + response.body().toString());
                        int resLength = response.body().toString().trim().length();
                        String resmsg = ""+response.body().toString();
                        if (status == 200 && !resmsg.equals("") && !resmsg.equals(null)) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // resultListNonProduct.add(np.getNONPRDHED_REFNO()+ "--->SUCCESS");
                                    //    addRefNoResults_Non(np.getNONPRDHED_REFNO() + " --> Success\n",RCSList.size());
                                    //  Log.d( ">>response"+status,""+c.getORDER_REFNO() );
                                    c.setFINVHED_IS_SYNCED("1");
                                    addRefNoResults(c.getFINVHED_REFNO() +" --> Success\n",RCSList.size());
                                    // new OrderController(context).updateIsSynced(c);
                                    new InvHedController(context).updateIsSynced(c.getFINVHED_REFNO(),"1");
                                    //  Toast.makeText(context,np.getNONPRDHED_REFNO()+"-Non-productive uploded Successfully" , Toast.LENGTH_SHORT).show();
                                }
                            });
                            //addRefNoResults(c.getORDER_REFNO() +" --> Success\n",RCSList.size());

                            //  Toast.makeText(context, c.getORDER_REFNO()+" - Order uploded Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d( ">>response"+status,""+c.getFINVHED_REFNO() );
                            c.setFINVHED_IS_SYNCED("0");
                            new InvHedController(context).updateIsSynced(c.getFINVHED_REFNO(),"0");
                            addRefNoResults(c.getFINVHED_REFNO() +" --> Failed\n",RCSList.size());
                            //   Toast.makeText(context, c.getORDER_REFNO()+" - Order uplod Failed", Toast.LENGTH_SHORT).show();
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
        //  taskListener.onTaskCompleted(taskType,resultListPreSale);

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
        dialog.dismiss();
        taskListener.onTaskCompleted(taskType,resultListVanSale);
    }
    private void addRefNoResults(String ref, int count) {
        resultListVanSale.add(ref);
        if(count == resultListVanSale.size()) {
            mUploadResult(resultListVanSale);
        }
    }
    public void mUploadResult(List<String> messages) {
        String msg = "";
        for (String s : messages) {
            msg += s;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setTitle("Upload Invoice Summary");

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