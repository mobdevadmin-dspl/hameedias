package com.datamation.hmdsfa.helpers;

import android.content.Context;
import android.util.Log;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.DashboardController;
import com.datamation.hmdsfa.model.CustomNameValuePair;
import com.datamation.hmdsfa.model.SalRep;
import com.datamation.hmdsfa.model.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * /***@Auther - rashmi
 * Created by Rashmi on 1/11/2018.
 * Handles network based functions.
 */
public class NetworkFunctions {

    private final String LOG_TAG = NetworkFunctions.class.getSimpleName();

    private final SharedPref pref;

    private Context context;

    /**
     * The base URL to POST/GET the parameters to. The function names will be appended to this
     */
    private String baseURL, restOfURL;

    private SalRep user;
    private String dbname;

    public NetworkFunctions(Context contextt) {
        this.context = contextt;
        pref = SharedPref.getInstance(context);
        String domain = pref.getBaseURL();
        Log.d("baseURL>>>>>>>>>", domain);
        baseURL = domain + context.getResources().getString(R.string.connection_string);
        dbname = pref.getDistDB();
        restOfURL = "/mobile123/" + dbname;

        // Log.d(LOG_TAG, "testing : " + baseURL + "login" + restOfURL);
        user = pref.getLoginUser();
    }


    public void setUser(SalRep user) {
        this.user = user;
    }
//with console database - commented by rashmi - 2020-02-28
//    public String validate(Context context, String macId, String url, String db) throws IOException {
//
//        List<CustomNameValuePair> params = new ArrayList<>();
//
//        Log.d(LOG_TAG, "Validating : " + url + context.getResources().getString(R.string.connection_string) + "fSalRep" + restOfURL + "/" + macId);
//
//        return getFromServer(url + context.getResources().getString(R.string.connection_string) + "fSalRep/mobile123/" + db + "/" + macId, params);
//    }
//without console database -  by rashmi - 2020-02-28
    public String validate(Context context,String macId, String url) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Validating : " + url +context.getResources().getString(R.string.connection_string) + "fSalRep" +restOfURL+"/"+ macId);

        return getFromServer(url+context.getResources().getString(R.string.connection_string) + "fSalRep"+restOfURL+"/"+macId, params);
    }
    /**
     * This function will POST repCode will return a the response JSON
     * from the server.
     *
     * @param repCode The string of the logged user's code
     * @return The response as a String
     * @throws IOException Throws if unable to reach the server
     */
    public String getCompanyDetails(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting company details : " + baseURL + "fControl" + restOfURL + params);

        return getFromServer(baseURL + "fControl" + restOfURL, params);

    }

    public String getCustomer(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting customer : " + baseURL + "Fdebtor" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "Fdebtor" + restOfURL + "/" + repCode, params);

    }
    // --------------------------------- Nuwan ----------------------- 17/10/2019 ------------------------

    public String getNearCustomer() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting near customer : " + baseURL + "FnearDebtor" + restOfURL + "/" + params);

        return getFromServer(baseURL + "FnearDebtor" + restOfURL, params);

    }

    // ---------------------------------------------------------------------------------------------------
    public String getItemLocations(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting itemlocs : " + baseURL + "fItemLoc" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "fItemLoc" + restOfURL + "/" + repCode, params);

    }

    public String getItemPrices(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fItemPri : " + baseURL + "fItemPri" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "fItemPri" + restOfURL + "/" + repCode, params);

    }

    public String getItems(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fItems : " + baseURL + "fItems" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "fItems" + restOfURL + "/" + repCode, params);

    }

    public String getLocations(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fLocations : " + baseURL + "fLocations" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "fLocations" + restOfURL + "/" + repCode, params);

    }

    public String getTax() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting Ftax : " + baseURL + "Ftax" + restOfURL + params);

        return getFromServer(baseURL + "Ftax" + restOfURL, params);

    }

    public String getTaxHed() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting Ftaxhed : " + baseURL + "Ftaxhed" + restOfURL + params);

        return getFromServer(baseURL + "Ftaxhed" + restOfURL, params);

    }

    public String getTaxDet() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting Ftaxdet : " + baseURL + "Ftaxdet" + restOfURL + params);

        return getFromServer(baseURL + "Ftaxdet" + restOfURL, params);

    }

    //    public String getTourHed(String repCode) throws IOException {
//
//        List<CustomNameValuePair> params = new ArrayList<>();
//
//        Log.d(LOG_TAG, "Getting ftourhed : " + baseURL + "ftourhed"+restOfURL+"/"+repCode+ params);
//
//        return getFromServer(baseURL + "ftourhed"+restOfURL+"/"+repCode, params);
//
//    }
//    public String getStkIn(String repCode) throws IOException {
//
//        List<CustomNameValuePair> params = new ArrayList<>();
//
//        Log.d(LOG_TAG, "Getting fstkin : " + baseURL + "fstkin"+restOfURL+"/"+repCode+ params);
//
//        return getFromServer(baseURL + "fstkin"+restOfURL+"/"+repCode, params);
//
//    }
    public String getReferences(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting FCompanyBranch : " + baseURL + "FCompanyBranch" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "FCompanyBranch" + restOfURL + "/" + repCode, params);
    }

    public String getReferenceSettings() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fCompanySetting : " + baseURL + "fCompanySetting" + restOfURL + params);

        return getFromServer(baseURL + "fCompanySetting" + restOfURL, params);
    }

    public String getReasons() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting freason  : " + baseURL + "freason " + restOfURL + params);

        return getFromServer(baseURL + "freason" + restOfURL, params);
    }

    public String getExpenses() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fexpense   : " + baseURL + "fexpense  " + restOfURL + params);

        return getFromServer(baseURL + "fexpense" + restOfURL, params);
    }

    public String getFreeSlab() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fFreeslab   : " + baseURL + "fFreeslab  " + restOfURL + params);

        return getFromServer(baseURL + "fFreeslab" + restOfURL, params);
    }

    public String getFreeDet() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fFreedet   : " + baseURL + "fFreedet  " + restOfURL + params);

        return getFromServer(baseURL + "fFreedet" + restOfURL, params);
    }

    public String getFreeDebs() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fFreedeb : " + baseURL + "fFreedeb" + restOfURL + params);

        return getFromServer(baseURL + "fFreedeb" + restOfURL, params);
    }

    public String getFreeItems() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting ffreeitem : " + baseURL + "ffreeitem" + restOfURL + params);

        return getFromServer(baseURL + "ffreeitem" + restOfURL, params);
    }

    //    public String getDebItemPrices() throws IOException {
//
//        List<CustomNameValuePair> params = new ArrayList<>();
//
//        Log.d(LOG_TAG, "Getting fdebitempri : " + baseURL + "fdebitempri"+restOfURL+ params);
//
//        return getFromServer(baseURL + "fdebitempri"+restOfURL, params);
//    }
    public String getBanks() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fbank : " + baseURL + "fbank" + restOfURL + params);

        return getFromServer(baseURL + "fbank" + restOfURL, params);
    }

    public String getDiscDet() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fdiscdet : " + baseURL + "fdiscdet" + restOfURL + params);

        return getFromServer(baseURL + "fdiscdet" + restOfURL, params);
    }

    public String getDiscSlab() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fdiscslab : " + baseURL + "fdiscslab" + restOfURL + params);

        return getFromServer(baseURL + "fdiscslab" + restOfURL, params);
    }

    public String getFreeMslab() throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting ffreemslab : " + baseURL + "ffreemslab" + restOfURL + params);

        return getFromServer(baseURL + "ffreemslab" + restOfURL, params);
    }

    public String getFreeHed(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting FreeHed : " + baseURL + "FreeHed" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "fFreehed" + restOfURL + "/" + repCode, params);
    }

    public String getRoutes(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting froute : " + baseURL + "froute" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "froute" + restOfURL + "/" + repCode, params);
    }

    public String getRouteDets(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting froutedet : " + baseURL + "froutedet" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "froutedet" + restOfURL + "/" + repCode, params);
    }

    public String getTowns(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting ftown : " + baseURL + "ftown" + restOfURL);

        return getFromServer(baseURL + "ftown" + restOfURL, params);
    }

    public String getDiscDeb(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fdiscdeb : " + baseURL + "fdiscdeb" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "fdiscdeb" + restOfURL + "/" + repCode, params);
    }

    public String getDiscHed(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fdisched : " + baseURL + "fdisched" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "fdisched" + restOfURL + "/" + repCode, params);
    }

    public String getFddbNotes(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting fDdbNoteWithCondition : " + baseURL + "fDdbNoteWithCondition" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "fDdbNoteWithCondition" + restOfURL + "/" + repCode, params);
    }

    public String getItenaryHed(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH) + 1;
        DecimalFormat df_month = new DecimalFormat("00");
        Log.d(LOG_TAG, "Getting FItenrHed : " + baseURL + "FItenrHed" + restOfURL);

        return getFromServer(baseURL + "FItenrHed" + restOfURL + "/" + repCode + "/" + cyear + "/" + df_month.format((double) cmonth), params);
    }

    public String getTMInvoiceSale(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH) + 1;
        DecimalFormat df_month = new DecimalFormat("00");
        Log.d(LOG_TAG, "Getting FTMInvoice sale : " + baseURL + "invoiceSale" + restOfURL);

        return getFromServer(baseURL + "invoiceSale" + restOfURL + "/" + repCode + "/" + cyear + "/" + df_month.format((double) cmonth), params);
    }

    public String getPMInvoiceSale(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        DecimalFormat df_month = new DecimalFormat("00");
        Log.d(LOG_TAG, "Getting FPMInvoice sale : " + baseURL + "invoiceSale" + restOfURL);

        return getFromServer(baseURL + "invoiceSale" + restOfURL + "/" + repCode + "/" + cyear + "/" + df_month.format((double) cmonth), params);
    }

    public String getTMOrderSale(String repCode) throws IOException {

        //rashmi - 2019-11-29

        int curYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new DashboardController(context).subtractDay(new Date())));
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new DashboardController(context).subtractDay(new Date())));
        int yesterday = Integer.parseInt(new SimpleDateFormat("dd").format(new DashboardController(context).subtractDay(new Date())));
        Log.d("Previous day", "" + curYear + "-" + curMonth + "-" + yesterday);
        Log.d("First day", "" + new DashboardController(context).getFirstDay());
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH) + 1;
        DecimalFormat df_month = new DecimalFormat("00");
        String firstday = new DashboardController(context).getFirstDay();
        String yesterDay = "" + curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", yesterday);

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting TMOrder sale : " + baseURL + "orderSale" + restOfURL);

        return getFromServer(baseURL + "orderSaleTillToday" + restOfURL + "/" + repCode + "/" + firstday + "/" + yesterDay, params);
    }

    public String getPMOrderSale(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        DecimalFormat df_month = new DecimalFormat("00");
        int curMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        Log.d(LOG_TAG, "Getting PMOrder sale : " + baseURL + "orderSale" + restOfURL);

        return getFromServer(baseURL + "orderSale" + restOfURL + "/" + repCode + "/" + cyear + "/" + String.format("%02d", curMonth - 1), params);
    }

    public String getTMReturns(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH) + 1;
        DecimalFormat df_month = new DecimalFormat("00");
        Log.d(LOG_TAG, "Getting getReturns TM : " + baseURL + "getReturns" + restOfURL);

        return getFromServer(baseURL + "getReturns" + restOfURL + "/" + repCode + "/" + cyear + "/" + df_month.format((double) cmonth), params);
    }

    public String getPMReturns(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        DecimalFormat df_month = new DecimalFormat("00");
        Log.d(LOG_TAG, "Getting getReturns PM : " + baseURL + "getReturns" + restOfURL);

        return getFromServer(baseURL + "getReturns" + restOfURL + "/" + repCode + "/" + cyear + "/" + df_month.format((double) cmonth), params);
    }

    public String getItenaryDet(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH) + 1;
        DecimalFormat df_month = new DecimalFormat("00");
        Log.d(LOG_TAG, "Getting FItenrDet : " + baseURL + "FItenrDet" + restOfURL);

        return getFromServer(baseURL + "FItenrDet" + restOfURL + "/" + repCode + "/" + cyear + "/" + df_month.format((double) cmonth), params);
    }

    public String getItems() throws IOException {

        Log.d(LOG_TAG, "Getting Items");

        return getFromServer(baseURL + "item.php", null);
    }

    public String getPrices() throws IOException {

        Log.d(LOG_TAG, "Getting ItemPrices");

        return getFromServer(baseURL + "itemprices.php", null);
    }

    public String getLastThreeInvHed(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting RepLastThreeInvHed : " + baseURL + "RepLastThreeInvHed" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "RepLastThreeInvHed" + restOfURL + "/" + repCode, params);
    }

    public String getLastThreeInvDet(String repCode) throws IOException {

        List<CustomNameValuePair> params = new ArrayList<>();

        Log.d(LOG_TAG, "Getting RepLastThreeInvDet : " + baseURL + "RepLastThreeInvDet" + restOfURL + "/" + repCode + params);

        return getFromServer(baseURL + "RepLastThreeInvDet" + restOfURL + "/" + repCode, params);
    }

    public String syncInvoice() {
        return baseURL + "insertFInvHed";
    }

    public String syncReceipt() {
        return baseURL + "insertFrecHed";
    }

    public String syncOrder() {
        return baseURL + "insertFOrdHed";
    }

    public String syncSalesReturn() {
        return baseURL;
    }

    public String syncNonProductive() {
        return baseURL + "insertFDaynPrdHed";
    }

    public String syncNewCustomers() {
        return baseURL + "insertCustomer";
    }

    public String syncDebtor() {
        return baseURL + "updateDebtorCordinates";
    }

    public String syncDebtorImgUpd() {
        return baseURL + "updateDebtorImageURL";
    }

    public String syncEditedDebtors() {
        return baseURL + "updateEditedDebtors";
    }

    public String syncAttendance() {
        return baseURL + "insertTourInfo";
    }

    public String syncFirebasetoken() {
        return baseURL + "updateFirebaseTokenID";
    }

    public String syncEmailUpdatedSalrep() {
        return baseURL + "updateEmailUpdatedSalRep";
    }

    public String syncDayExp() {
        return baseURL + "insertDayExpense";
    }

    public String fetchOrderDetails(long invoiceId) throws IOException {
        List<CustomNameValuePair> params = new ArrayList<>();
        //params.add(new CustomNameValuePair("position_id", String.valueOf(user.getLocationId())));
        params.add(new CustomNameValuePair("sales_order_code", String.valueOf(invoiceId)));

        Log.d(LOG_TAG, "Fetching order details");

        return postToServer(baseURL + "get_invoice_details", params);
    }

    /**
     * This function POSTs params to server and gets the response.
     *
     * @param url    The URL to POST to
     * @param params The list of {@link CustomNameValuePair} of params to POST
     * @return The response from the server as a String
     * @throws IOException Throws if unable to connect to the server
     */
    private String postToServer(String url, List<CustomNameValuePair> params) throws IOException {

        String response = "";

        URL postURL = new URL(url);
        HttpURLConnection con = (HttpURLConnection) postURL.openConnection();
        con.setConnectTimeout(60 * 1000);
        con.setReadTimeout(30 * 1000);
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);

        OutputStream os = con.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(generatePOSTParams(params));
        writer.flush();
        writer.close();
        os.close();

        con.connect();

        int status = con.getResponseCode();
        switch (status) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();

                response = sb.toString();
                Log.d(LOG_TAG, "Server Response : \n" + response);
        }

        return response;
    }
//    private String postToServer(String url, List<CustomNameValuePair> params) throws IOException {
//
//        String response = "";
//
//        URL postURL = new URL(url);
//        HttpsURLConnection con = (HttpsURLConnection) postURL.openConnection();
//        // Create the SSL connection
//        SSLContext sc = null;
//        try {
//            sc = SSLContext.getInstance("TLS");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        try {
//            sc.init(null, null, new java.security.SecureRandom());
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }
//        con.setSSLSocketFactory(sc.getSocketFactory());
//
//        // Use this if you need SSL authentication
//       // String userpass = user + ":" + password;
//       // String basicAuth = "Basic " + Base64.encodeToString(userpass.getBytes(), Base64.DEFAULT);
//       // con.setRequestProperty("Authorization", basicAuth);
//        con.setConnectTimeout(60 * 1000);
//        con.setReadTimeout(30 * 1000);
//        con.setRequestMethod("POST");
//        con.setDoInput(true);
//        con.setDoOutput(true);
//
//        OutputStream os = con.getOutputStream();
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//        writer.write(generatePOSTParams(params));
//        writer.flush();
//        writer.close();
//        os.close();
//
//        con.connect();
//
//        int status = con.getResponseCode();
//        switch (status) {
//            case 200:
//            case 201:
//                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                StringBuilder sb = new StringBuilder();
//                String line;
//                while ((line = br.readLine()) != null) {
//                    sb.append(line).append("\n");
//                }
//                br.close();
//
//                response = sb.toString();
//                Log.d(LOG_TAG, "Server Response : \n" + response);
//        }
//
//        return response;
//    }
//

    /**
     * This function GETs params to server and returns the response.
     *
     * @param url    The URL to GET from
     * @param params The List<CustomNameValuePair></> of params to encode as GET parameters
     * @return The response string from the server
     * @throws IOException Throws if unable to connect to the server
     */
    private String getFromServer(String url, List<CustomNameValuePair> params) throws IOException {

        String response = "";

        URL postURL = new URL(url + generateGETParams(params));
//        Log.d(LOG_TAG, postURL.toString());
        HttpURLConnection con = (HttpURLConnection) postURL.openConnection();
        con.setConnectTimeout(60 * 1000);
        con.setReadTimeout(30 * 1000);
        con.setRequestMethod("GET");
        con.setDoInput(true);
//        con.setDoOutput(true);

        con.connect();

        int status = con.getResponseCode();
        switch (status) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();

                response = sb.toString();
                Log.d(LOG_TAG, "Server Response : \n" + response);
        }

        return response;
    }

    /**
     * This function will return the params as a queried String to POST to the server
     *
     * @param params The parameters to be POSTed
     * @return The formatted String
     * @throws UnsupportedEncodingException
     */
    private String generatePOSTParams(List<CustomNameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (CustomNameValuePair pair : params) {
            if (pair != null) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }
        }

        Log.d(LOG_TAG, "Server REQUEST : " + result.toString());
        Log.d(LOG_TAG, "Upload size : " + result.toString().getBytes().length + " bytes");

        return result.toString();
    }

    /**
     * This function will return the params as a queried String to GET from the server
     *
     * @param params The parameters to encode as GET params
     * @return The formatted String
     */
    private String generateGETParams(List<CustomNameValuePair> params) {

        StringBuilder result = new StringBuilder().append("");
        boolean first = true;

        if (params != null) {
            for (CustomNameValuePair pair : params) {
                if (pair != null) {
                    if (first) {
                        first = false;
                        result.append("?");
                    } else
                        result.append("&");

                    result.append(pair.getName());
                    result.append("=");
                    result.append(pair.getValue());
                }
            }
        }

        Log.d(LOG_TAG, "Upload size : " + result.toString().getBytes().length + " bytes");

        return result.toString();
    }

    public static boolean mHttpManager(String url, String sJsonObject) throws Exception {
        Log.v(url + "## Json ##", sJsonObject);
        HttpPost requestfDam = new HttpPost(url);
        StringEntity entityfDam = new StringEntity(sJsonObject, "UTF-8");
        entityfDam.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        entityfDam.setContentType("application/json");
        requestfDam.setEntity(entityfDam);
        DefaultHttpClient httpClientfDamRec = new DefaultHttpClient();

        HttpResponse responsefDamRec = httpClientfDamRec.execute(requestfDam);
        HttpEntity entityfDamEntity = responsefDamRec.getEntity();
        InputStream is = entityfDamEntity.getContent();
        try {
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                is.close();
                String result = sb.toString();
                String result_fDamRec = result.replace("\"", "");
                Log.e("response", "connect:" + result_fDamRec);
                if (result_fDamRec.trim().equals("200"))
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
