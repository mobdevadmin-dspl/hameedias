package com.datamation.hmdsfa.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import com.datamation.hmdsfa.model.SalRep;
import com.datamation.hmdsfa.model.User;

/**
 * Functions to access shared preferences.
 */
public class SharedPref {

    private static final String LOG_TAG = SharedPref.class.getSimpleName();

    //    private Context context;
    private SharedPreferences sharedPref;

    private static SharedPref pref;
    private boolean isGPSSwitched = false;

    public boolean isGPSSwitched() {
        return isGPSSwitched;
    }

    public void setGPSSwitched(boolean GPSSwitched) {
        isGPSSwitched = GPSSwitched;
    }

    public SharedPref() {
    }

    public static SharedPref getInstance(Context context) {
        if (pref == null) {
            pref = new SharedPref(context);
        }

        return pref;
    }

//    public boolean isSelectedDebtorStart() {
//        return selectedDebtorStart;
//    }
//
//    public void setSelectedDebtorStart(boolean selectedDebtorStart) {
//        this.selectedDebtorStart = selectedDebtorStart;
//    }
//
//    public boolean isSelectedDebtorEnd() {
//        return selectedDebtorEnd;
//    }
//
//    public void setSelectedDebtorEnd(boolean selectedDebtorEnd) {
//        this.selectedDebtorEnd = selectedDebtorEnd;
//    }

    public SharedPref(Context context) {
//        this.context = context;
        sharedPref = context.getSharedPreferences("app_data", Context.MODE_PRIVATE);
    }

    public void setLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("login_status", status).apply();
    }

    public void setValidateStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("validate_status", status).apply();
    }

    public boolean isLoggedIn() {
        return sharedPref.getBoolean("login_status", false);
    }

    public boolean isValidate() {
        return sharedPref.getBoolean("validate_status", false);
    }
    public  void  setTMReturn(String ordSale){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TMReturn", ordSale);
        editor.apply();
    }

    public  String getTMReturn(){
        return  sharedPref.getString("TMReturn","0");
    }
    public  void  setPMReturn(String ordSale){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Return_PM", ordSale);
        editor.apply();
    }

    public  String getPMReturn(){
        return  sharedPref.getString("Return_PM","0");
    }
    public  void  setTMOrdSale(String ordSale){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Order_Sale_TM", ordSale);
        editor.apply();
    }

    public  String getTMOrdSale(){
        return  sharedPref.getString("Order_Sale_TM","0");
    }
    public  void  setPMOrdSale(String ordSale){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Order_Sale_PM", ordSale);
        editor.apply();
    }

    public  String getPMOrdSale(){
        return  sharedPref.getString("Order_Sale_PM","0");
    }

    public  void  setTMInvSale(String invSale){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Invoice_Sale", invSale);
        editor.apply();
    }

    public  String getTMInvSale(){
        return  sharedPref.getString("Invoice_Sale","0");
    }

    public  void  setPMInvSale(String invSale){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("PMInvoice_Sale", invSale);
        editor.apply();
    }

    public  String getPMInvSale(){
        return  sharedPref.getString("PMInvoice_Sale","0");
    }


    public void storeLoginUser(SalRep user) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_id", user.getRepCode());
        editor.putString("user_name", user.getNAME());
        editor.putString("user_password", user.getPASSWORD());
        editor.putString("user_prefix", user.getPREFIX());
        editor.putString("user_type", user.getRepType());

        editor.apply();
    }

    public SalRep getLoginUser() {

        SalRep user = new SalRep();
        user.setRepCode(sharedPref.getString("user_id", ""));
        user.setNAME(sharedPref.getString("user_name", ""));
        user.setPASSWORD(sharedPref.getString("user_password", ""));
        user.setPREFIX(sharedPref.getString("user_prefix", ""));
        user.setRepType(sharedPref.getString("user_type", ""));

        if (user.getRepCode().equals("")) {
            return null;
        } else {
            return user;
        }
    }

    public void setGlobalVal(String mKey, String mValue) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(mKey, mValue);
        editor.commit();
    }

    public String getGlobalVal(String mKey) {
        return sharedPref.getString(mKey, "");
    }


    public long generateOrderId() {
        long time = System.currentTimeMillis();
        Log.wtf("ID", String.valueOf(sharedPref.getInt("user_location_id", 0)) + String.valueOf(time));
        long order_id = Long.parseLong(String.valueOf(sharedPref.getInt("user_location_id", 0)) + String.valueOf(time));
        return (order_id < 0 ? -order_id : order_id);
    }


//
//    public void storePreviousRoute(Route route) {
//        if (route != null) {
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putInt("previous_route_id", route.getRouteId());
//            editor.putString("previous_route_name", route.getRouteName());
//            editor.putFloat("previous_route_fixed_target", (float) route.getFixedTarget());
//            editor.putFloat("previous_route_selected_target", (float) route.getSelectedTarget());
//            editor.apply();
//        }
//    }

//    public Route getPreviousRoute() {
//
//        Route route = new Route(sharedPref.getInt("previous_route_id", 0), sharedPref.getString("previous_route_name", null),
//                sharedPref.getFloat("previous_route_fixed_target", 0), sharedPref.getFloat("previous_route_selected_target", 0));
//        if (route.getRouteId() != 0) {
//            return route;
//        }
//
//        return null;
//    }

    public void clearPref() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("selected_out_id", "");
        editor.putString("selected_out_name", "");
        editor.putString("selected_out_route_code", "");
        editor.putString("selected_pril_code", "");
        editor.apply();
    }

    public int getSelectedOutletId() {
        return sharedPref.getInt("selected_out_id", 0);
    }

    public void setSelectedOutletId(int outletId) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("selected_out_id", outletId);
        editor.apply();
    }

    public String getSelectedDebCode() {
        return sharedPref.getString("selected_out_id", "0");
    }

    public void setSelectedDebCode(String code) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("selected_out_id", code);
        editor.apply();
    }

    // to maintain image flag from firebase ----------------------------

    public String getImageFlag() {
        return sharedPref.getString("img_flag", "0");
    }

    public void setImageFlag(String code) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("img_flag", code);
        editor.apply();
    }
    //-------------------------------------------------------


    // to maintain image flag from firebase ----------------------------

    public String getFirebaseTokenKey() {
        return sharedPref.getString("tokenKey", "a1b2c3");
    }

    public void setFirebaseTokenKey(String token) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("tokenKey", token);
        editor.apply();
    }
    //-------------------------------------------------------

    public String getSelectedDebName() {
        return sharedPref.getString("selected_out_name", "0");
    }

    public void setSelectedDebName(String name) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("selected_out_name", name);
        editor.apply();
    }

    public String getGPSDebtor() {
        return sharedPref.getString("IS_GPS_DEBTOR", "0");
    }

    public void setGPSDebtor(String val) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("IS_GPS_DEBTOR", val);
        editor.apply();
    }

    public String getGPSUpdated() {
        return sharedPref.getString("IS_GPS_UPDATED", "0");
    }

    public void setGPSUpdated(String val) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("IS_GPS_UPDATED", val);
        editor.apply();
    }

    public String getDiscountClicked() {
        return sharedPref.getString("IS_DISCOUNT_CLICKED", "0");
    }

    public void setDiscountClicked(String val) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("IS_DISCOUNT_CLICKED", val);
        editor.apply();
    }

    public String getSelectedDebRouteCode() {
        return sharedPref.getString("selected_out_route_code", "0");
    }

    public void setSelectedDebRouteCode(String code) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("selected_out_route_code", code);
        editor.apply();
    }

    public String getSelectedDebtorPrilCode() {
        return sharedPref.getString("selected_pril_code", "0");
    }

    public void setSelectedDebtorPrilCode(String prilCode) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("selected_pril_code", prilCode);
        editor.apply();
    }


    //    public int startDay() {
//
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putBoolean("day_status", true);
//
//        int session = sharedPref.getInt("local_session", 0) + 1;
//        editor.putInt("local_session", session);
//
//        long timeOut = TimeUtils.getDayEndTime(System.currentTimeMillis());
//
//        Log.d(LOG_TAG, "Setting timeout time at " + TimeUtils.formatDateTime(timeOut));
//
//        editor.putLong("login_timeout", timeOut);
//
//        editor.apply();
//
//        return session;
//    }
    public void setMacAddress(String MacAddress) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("MAC_Address", MacAddress);
        editor.apply();
    }
    public void setPrinterMacAddress(String MacAddress) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Print_MAC_Address", MacAddress);
        editor.apply();
    }

    public String getMacAddress() {
        return sharedPref.getString("MAC_Address", "");
    }
    public String getPrinterMacAddress() {
        return sharedPref.getString("Print_MAC_Address", "");
    }


    public long getLoginTimeout() {
        return sharedPref.getLong("login_timeout", 0);
    }

    public void endDay() {
        Log.d(LOG_TAG, "Ending Day");
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("day_status", false);
        //   storePreviousRoute(getSelectedRoute());
        editor.putInt("selected_route_id", 0);
        editor.putString("selected_route_name", null);
        editor.putFloat("selected_route_fixed_target", 0);
        editor.putFloat("selected_route_selected_target", 0);
        editor.apply();
    }

    public int getLocalSessionId() {
        return sharedPref.getInt("local_session", 0);
    }

//    public void setDayStatus(boolean isDayStarted) {
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putBoolean("day_status", isDayStarted);
//        editor.apply();
//    }

    public boolean isDayStarted() {
        return sharedPref.getBoolean("day_status", false);
    }

    public void setTransferToDealerList(boolean flag) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("transfer_to_dlist", flag);
        editor.apply();
    }

    public boolean getTransferToDealerList(boolean inverse) {

        boolean result = sharedPref.getBoolean("transfer_to_dlist", false);

        if (inverse) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("transfer_to_dlist", false);
            editor.apply();
        }

        return result;
    }

    public boolean validForLogin(int outletId) {
        String key = "outlet_changed_".concat(String.valueOf(outletId));
        int updatedCount = sharedPref.getInt(key, 0);

        return updatedCount <= 2;
    }

    public void notifyOutletHasChanged(int outletId) {
        String key = "outlet_changed_".concat(String.valueOf(outletId));
        int updatedCount = sharedPref.getInt(key, 0);
        updatedCount++;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, updatedCount);
        editor.apply();
    }

    //<editor-fold desc="Time Management">
    public void createInitialTimeVariables(long correctTime) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("app_start_time", correctTime);
        editor.putLong("app_start_elapsed_time", SystemClock.elapsedRealtime());
        editor.putLong("time_differential", 0);
        editor.apply();
    }

    public void calculateTimeDifferential(long changedTime, long nowElapsedTime) {
        long initialTime = sharedPref.getLong("app_start_time", 0);
        long initialElapsedTime = sharedPref.getLong("app_start_elapsed_time", 0);
//        long initialDifferential = sharedPref.getLong("time_differential", 0);

        long currentCorrectTime = initialTime + (nowElapsedTime - initialElapsedTime);

        // The difference between the correct time and the changed time
        long differential = changedTime - currentCorrectTime;

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("time_differential", differential);

        // Don't apply, commit. We need immediate change in the file.
        editor.commit();

    }

    public void compensateForDeviceReboot() {

        long newCorrectTime = System.currentTimeMillis() + sharedPref.getLong("time_differential", 0);
        long newElapsedTime = SystemClock.elapsedRealtime();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("app_start_time", newCorrectTime);
        editor.putLong("app_start_elapsed_time", newElapsedTime);

        // Don't apply, commit. We need immediate change in the file.
        editor.commit();
    }

    public long getRealTimeInMillis() {
        return System.currentTimeMillis() + sharedPref.getLong("time_differential", 0);
    }
    //</editor-fold>

    public void setPointingLocationIndex(int index) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("pointing_location", index);
        editor.apply();
    }

    public int getPointingLocationIndex() {
        return sharedPref.getInt("pointing_location", 0);
    }

    public void setBaseURL(String baseURL) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("baseURL", baseURL);
        editor.apply();
    }

    public String getBaseURL() {
        //return sharedPref.getString("baseURL", "https://19920502.000webhostapp.com");
        //return sharedPref.getString("baseURL", "http://203.143.21.121:8080");
        return sharedPref.getString("baseURL", "http://13.76.45.176:1010");
       // return sharedPref.getString("baseURL", "http://123.231.13.199:1025");
        //return sharedPref.getString("baseURL", "http://192.168.43.62");
        //return sharedPref.getString("baseURL", "http://192.168.43.62");

    }

    public void setConsoleDB(String console) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Console_DB", console);
        editor.apply();
    }

    public String getConsoleDB() {
        //return sharedPref.getString("baseURL", "https://19920502.000webhostapp.com");E2956_SWD
        return sharedPref.getString("Console_DB", "Console_SWD");
        //return sharedPref.getString("Console_DB", "LHD_PDA_TEST");
        //return sharedPref.getString("baseURL", "http://192.168.43.62");

    }

    public void setDistDB(String dist) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Dist_DB", dist);
        editor.apply();
    }

    public String getDistDB() {
        return sharedPref.getString("Dist_DB", "SFA_INTEGRATION_TEST");
//        return sharedPref.getString("Dist_DB", "SFA_Integration");
        //return sharedPref.getString("Console_DB", "LHD_PDA_TEST");
        //return sharedPref.getString("baseURL", "http://192.168.43.62");
    }

    public void setCurrentMillage(double millage) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("millage", (float) millage);
        editor.apply();
    }

    public double getPrevoiusMillage() {
        return sharedPref.getFloat("millage", 0);
    }


    public void setVersionName(String versionName) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("app_version_name", versionName).commit();
    }

    public String getPayMode() {
        return sharedPref.getString("paymode", "");
    }

    public void setPayMode(String payMode) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("paymode", payMode).commit();
    }

    public String getVersionName() {
        return sharedPref.getString("app_version_name", "0.0.0");
    }
}
