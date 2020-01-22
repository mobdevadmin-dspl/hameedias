package com.datamation.swdsfa.receipt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.controller.ReceiptController;
import com.datamation.swdsfa.helpers.NetworkFunctions;
import com.datamation.swdsfa.helpers.UploadTaskListener;
import com.datamation.swdsfa.model.ReceiptHed;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UploadReceipt extends AsyncTask<ArrayList<ReceiptHed>, Integer, ArrayList<ReceiptHed>> {

	Context context;
	ProgressDialog dialog;
	UploadTaskListener taskListener;
	NetworkFunctions networkFunctions;
	int totalRecords;

	public static final String SETTINGS = "SETTINGS";
	public static SharedPreferences localSP;

	public UploadReceipt(Context context, UploadTaskListener taskListener) {

		this.context = context;
		this.taskListener = taskListener;
		localSP = context.getSharedPreferences(SETTINGS, 0);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(context);
		dialog.show();
	}

	@Override
	protected ArrayList<ReceiptHed> doInBackground(ArrayList<ReceiptHed>... params) {

		int recordCount = 0;
		publishProgress(recordCount);
		
		ArrayList<ReceiptHed> RCSList = params[0];
		totalRecords = RCSList.size();
		networkFunctions = new NetworkFunctions(context);
		final String sp_url =localSP.getString("URL", "").toString();
		String URL="http://"+sp_url;

		for(ReceiptHed c : RCSList){
			
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
			
//			try {
//			// PDADBWebServiceMO
//			HttpPost requestfDam = new HttpPost(URL+ context.getResources().getString(R.string.ConnectionURL) +"/insertFrecHed");
//			StringEntity entityfDam = new StringEntity( List.toString(), "UTF-8");
//			entityfDam.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//			entityfDam.setContentType("application/json");
//			requestfDam.setEntity(entityfDam);
//			// Send request to WCF service
//			DefaultHttpClient httpClientfDamRec = new DefaultHttpClient();
//
//
//			HttpResponse responsefDamRec = httpClientfDamRec.execute(requestfDam);
//			HttpEntity entityfDamEntity = responsefDamRec.getEntity();
//			InputStream is = entityfDamEntity.getContent();
//
//			//StatusLine statusLine = responsefDamRec.getStatusLine();
//		    //int statusCode = statusLine.getStatusCode();
//
//				if (is != null)
//				{
//					BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
//				    StringBuilder sb = new StringBuilder();
//
//				    String line = null;
//				    while ((line = reader.readLine()) != null){
//				        sb.append(line + "\n");
//				    }
//
//				   is.close();
//
//				   String result = sb.toString();
//				   String result_fDamRec = result.replace("\"", "");
//
//				   Log.e("response", "connect:" + result_fDamRec);
//
//				   if (result_fDamRec.trim().equals("200")){
//						 c.setSynced(true);
//					}else {
//						c.setSynced(false);
//					}
//				}
//				}catch(Exception e){
//
//					e.getStackTrace();
//				}
				
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

}
