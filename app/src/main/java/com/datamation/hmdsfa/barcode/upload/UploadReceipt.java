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
import com.datamation.hmdsfa.controller.ReceiptController;
import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.helpers.UploadTaskListener;
import com.datamation.hmdsfa.model.ReceiptHed;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadReceipt extends AsyncTask<ArrayList<ReceiptHed>, Integer, ArrayList<ReceiptHed>> {

	Context context;
	ProgressDialog dialog;
	UploadTaskListener taskListener;
	NetworkFunctions networkFunctions;
	int totalRecords;
	private Handler mHandler;
	List<String> resultListNonproctives;
	TaskTypeUpload taskType;

	public UploadReceipt(Context context, UploadTaskListener taskListener, TaskTypeUpload taskType) {

		this.context = context;
		this.taskListener = taskListener;
		mHandler = new Handler(Looper.getMainLooper());
		this.taskType = taskType;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(context);
		dialog.setTitle("Uploading receipt records");
		dialog.show();
	}

	@Override
	protected ArrayList<ReceiptHed> doInBackground(ArrayList<ReceiptHed>... params) {

		int recordCount = 0;
		publishProgress(recordCount);
		
		final ArrayList<ReceiptHed> RCSList = params[0];
		totalRecords = RCSList.size();
		networkFunctions = new NetworkFunctions(context);

		for(final ReceiptHed c : RCSList){
			String content_type = "application/json";
			ApiInterface apiInterface = ApiCllient.getClient(context).create(ApiInterface.class);
			JsonParser jsonParser = new JsonParser();
			String orderJson = new Gson().toJson(c);
			JsonObject objectFromString = jsonParser.parse(orderJson).getAsJsonObject();
			JsonArray jsonArray = new JsonArray();
			jsonArray.add(objectFromString);
			Call<String> resultCall = apiInterface.uploadReceipt(jsonArray, content_type);
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
									c.setFPRECHED_ISSYNCED("1");
									addRefNoResults(c.getFPRECHED_REFNO() + " --> Success\n", RCSList.size());
									new ReceiptController(context).updateIsSynced(c.getFPRECHED_REFNO(), "1");
								}
							});
						} else {
							Log.d(">>response" + status, "" + c.getFPRECHED_REFNO());
							c.setFPRECHED_ISSYNCED("0");
							new ReceiptController(context).updateIsSynced(c.getFPRECHED_REFNO(), "0");
							addRefNoResults(c.getFPRECHED_REFNO() + " --> Failed\n", RCSList.size());
						}
					}
				}

				@Override
				public void onFailure(Call<String> call, Throwable t) {
					Toast.makeText(context, "Error response "+t.toString(), Toast.LENGTH_SHORT).show();
				}
			});
			List<String> List = new ArrayList<String>();
			
			String sJsonHed = new Gson().toJson(c);
			
			List.add(sJsonHed);
//			String sURL = URL + context.getResources().getString(R.string.ConnectionURL) + "/insertFrecHed";
			boolean bStatus = false;
			try {
				bStatus = NetworkFunctions.mHttpManager(networkFunctions.syncReceipt(),List.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			// boolean bStatus = UtilityContainer.mHttpManager(sURL, new Gson().toJson(c));

			if (bStatus) {
				c.setFPRECHED_ISSYNCED("1");
			} else {
				c.setFPRECHED_ISSYNCED("0");
			}
			
			
			Log.v("## Json ##",  List.toString());
			

				
				++recordCount;
				publishProgress(recordCount);
			}

		return RCSList;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		dialog.setMessage("Uploading.. Receipt Record " + values[0] + "/" + totalRecords);
	}

	@Override
	protected void onPostExecute(ArrayList<ReceiptHed> RCSList) {
		super.onPostExecute(RCSList);
		List<String> list = new ArrayList<>();

		if (RCSList.size() > 0) {
			list.add("\nRECEIPT");
			list.add("------------------------------------\n");
		}

		int i = 1;
		for (ReceiptHed c : RCSList) {
			new ReceiptController(context).updateIsSyncedReceipt(c);

			if (c.getFPRECHED_ISSYNCED().equals("1")) {
				list.add(i + ". " + c.getFPRECHED_REFNO()+ " --> Success\n");
			} else {
				list.add(i + ". " + c.getFPRECHED_REFNO() + " --> Failed\n");
			}
			i++;
		}

		dialog.dismiss();
		taskListener.onTaskCompleted(list);
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
		alertDialogBuilder.setTitle("Upload receipt summary");

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
