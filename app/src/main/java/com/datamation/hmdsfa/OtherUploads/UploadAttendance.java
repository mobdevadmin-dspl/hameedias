package com.datamation.hmdsfa.OtherUploads;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.datamation.hmdsfa.api.ApiCllient;
import com.datamation.hmdsfa.api.ApiInterface;
import com.datamation.hmdsfa.api.TaskTypeUpload;
import com.datamation.hmdsfa.controller.AttendanceController;
import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.helpers.UploadTaskListener;
import com.datamation.hmdsfa.model.Attendance;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadAttendance extends AsyncTask<ArrayList<Attendance>, Integer, ArrayList<Attendance>> {

    Context context;
    public static final String SETTINGS = "SETTINGS";

    ArrayList<Attendance> attendList = new ArrayList<>();
    int totalRecords;
    ArrayList<String> resultList;
    private Handler mHandler;
    UploadTaskListener taskListener;
    NetworkFunctions networkFunctions;
    TaskTypeUpload taskType;
    ProgressDialog pDialog;
    public static SharedPreferences localSP;
    AttendanceController attendanceController;

    public UploadAttendance(Context context, UploadTaskListener taskListener, ArrayList<Attendance> attList, TaskTypeUpload tasktype) {
        this.context = context;
        this.taskListener = taskListener;
        mHandler = new Handler(Looper.getMainLooper());
        this.taskType = tasktype;
        attendList.addAll(attList);
        attendanceController = new AttendanceController(context);
       resultList = new ArrayList<>();
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Uploading attendance...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected void onPostExecute(ArrayList<Attendance> attendances) {
        super.onPostExecute(attendances);
        taskListener.onTaskCompleted(taskType,resultList);
        pDialog.dismiss();
    }

    @Override
    protected ArrayList<Attendance> doInBackground(ArrayList<Attendance>... arrayLists) {
        networkFunctions = new NetworkFunctions(context);
        totalRecords = attendList.size();

        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;
        Log.v("## Json ##", URL.toString());

        try {
            for (final Attendance a : attendList) {
                try {
                    String content_type = "application/json";
                    ApiInterface apiInterface = ApiCllient.getClient(context).create(ApiInterface.class);
                    JsonParser jsonParser = new JsonParser();
                    String attendenceJson = new Gson().toJson(a);
                    JsonObject objectFromString = jsonParser.parse(attendenceJson).getAsJsonObject();
                    JsonArray jsonArray = new JsonArray();
                    jsonArray.add(objectFromString);

                    Call<String> resultCall = apiInterface.uploadAttendence(jsonArray, content_type);

                    resultCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            int status = response.code();
                            int reslength = response.body().toString().trim().length();
                            String resmsg = ""+response.body().toString();
                            if (status == 200 && !resmsg.equals("") && !resmsg.equals(null))
                            {
                                a.setFTOUR_IS_SYNCED("1");
                                Log.d( ">>response"+status,""+a.getFTOUR_ID());
                                new AttendanceController(context).updateIsSynced();
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context,"Attendence uploaded Successfully" , Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else
                            {
                                Log.d( ">>response"+status,""+a.getFTOUR_ID()  );
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context,"Attendence upload failed" , Toast.LENGTH_SHORT).show();
                                    }
                                }); }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                            Toast.makeText(context, "Error response attendance" + t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            }catch(Exception e)
            {
                throw e;
            }
            Log.v(">>upload>>", "Upload Attendence execute finish");//
            return null;
        }

}
