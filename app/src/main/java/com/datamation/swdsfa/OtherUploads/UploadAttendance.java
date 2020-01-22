package com.datamation.swdsfa.OtherUploads;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.datamation.swdsfa.controller.AttendanceController;
import com.datamation.swdsfa.controller.CustomerController;
import com.datamation.swdsfa.helpers.NetworkFunctions;
import com.datamation.swdsfa.helpers.UploadTaskListener;
import com.datamation.swdsfa.model.Attendance;
import com.datamation.swdsfa.model.Debtor;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UploadAttendance extends AsyncTask<ArrayList<Attendance>, Integer, ArrayList<Attendance>> {

    Context context;
    public static final String SETTINGS = "SETTINGS";

    ArrayList<Attendance> attendList = new ArrayList<>();
    int totalRecords;

    UploadTaskListener taskListener;
    NetworkFunctions networkFunctions;

    ProgressDialog pDialog;
    public static SharedPreferences localSP;
    AttendanceController attendanceController;

    public UploadAttendance(Context context, UploadTaskListener taskListener, ArrayList<Attendance> attList) {
        this.context = context;
        this.taskListener = taskListener;
        attendList.addAll(attList);
        attendanceController = new AttendanceController(context);
//        localSP = context.getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
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
        pDialog.dismiss();
    }

    @Override
    protected ArrayList<Attendance> doInBackground(ArrayList<Attendance>... arrayLists) {
        networkFunctions = new NetworkFunctions(context);
        totalRecords = attendList.size();

        final String sp_url = localSP.getString("URL", "").toString();
        String URL = "http://" + sp_url;
        Log.v("## Json ##", URL.toString());

        for (Attendance attend : attendList) {
            ArrayList<String> jsonList = new ArrayList<>();
            String jObject = new Gson().toJson(attend);
            jsonList.add(jObject);

            try {
                boolean status = NetworkFunctions.mHttpManager(networkFunctions.syncAttendance(), jsonList.toString());
                if (status) {
                    attendanceController.updateIsSynced();
                    Toast.makeText(context,"Attendance upload success",Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                } else {
                    Toast.makeText(context,"Attendance upload Failed..!",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
