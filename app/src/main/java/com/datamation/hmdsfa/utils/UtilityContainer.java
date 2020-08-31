package com.datamation.hmdsfa.utils;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;

import com.datamation.hmdsfa.controller.BankController;
import com.datamation.hmdsfa.controller.BarcodeVarientController;
import com.datamation.hmdsfa.controller.CompanyDetailsController;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.DiscountController;
import com.datamation.hmdsfa.controller.ExpenseController;
import com.datamation.hmdsfa.controller.FItenrDetController;
import com.datamation.hmdsfa.controller.FItenrHedController;
import com.datamation.hmdsfa.controller.FreeDebController;
import com.datamation.hmdsfa.controller.FreeDetController;
import com.datamation.hmdsfa.controller.FreeHedController;
import com.datamation.hmdsfa.controller.FreeItemController;
import com.datamation.hmdsfa.controller.FreeMslabController;
import com.datamation.hmdsfa.controller.FreeSlabController;
import com.datamation.hmdsfa.controller.IteaneryDebController;
import com.datamation.hmdsfa.controller.ItemBundleController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.ItemLocController;
import com.datamation.hmdsfa.controller.OutstandingController;
import com.datamation.hmdsfa.controller.ReasonController;
import com.datamation.hmdsfa.controller.ReferenceDetailDownloader;
import com.datamation.hmdsfa.controller.ReferenceSettingController;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.controller.SalesPriceController;
import com.datamation.hmdsfa.controller.VATController;
import com.datamation.hmdsfa.controller.VanStockController;
import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.model.Bank;
import com.datamation.hmdsfa.model.BarcodeVariant;
import com.datamation.hmdsfa.model.CompanyBranch;
import com.datamation.hmdsfa.model.CompanySetting;
import com.datamation.hmdsfa.model.Control;
import com.datamation.hmdsfa.model.Debtor;
import com.datamation.hmdsfa.model.Discount;
import com.datamation.hmdsfa.model.Expense;
import com.datamation.hmdsfa.model.FItenrDet;
import com.datamation.hmdsfa.model.FItenrHed;
import com.datamation.hmdsfa.model.FddbNote;
import com.datamation.hmdsfa.model.FreeDeb;
import com.datamation.hmdsfa.model.FreeDet;
import com.datamation.hmdsfa.model.FreeHed;
import com.datamation.hmdsfa.model.FreeItem;
import com.datamation.hmdsfa.model.FreeMslab;
import com.datamation.hmdsfa.model.FreeSlab;
import com.datamation.hmdsfa.model.Item;
import com.datamation.hmdsfa.model.ItemBundle;
import com.datamation.hmdsfa.model.ItemLoc;
import com.datamation.hmdsfa.model.ItenrDeb;
import com.datamation.hmdsfa.model.Reason;
import com.datamation.hmdsfa.model.Route;
import com.datamation.hmdsfa.model.RouteDet;
import com.datamation.hmdsfa.model.SalesPrice;
import com.datamation.hmdsfa.model.VanStock;
import com.datamation.hmdsfa.model.VatMaster;
import com.datamation.hmdsfa.settings.TaskTypeDownload;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.helpers.SQLiteBackUp;
import com.datamation.hmdsfa.helpers.SQLiteRestore;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.SalRep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * common functions
 */

public class UtilityContainer {


    //---------------------------------------------------------------------------------------------------------------------------------------------------

    public static void  showSnackBarError(View v, String message, Context context) {
        Snackbar snack = Snackbar.make(v, "" + message, Snackbar.LENGTH_SHORT);
        View view = snack.getView();
        view.setBackgroundColor(Color.parseColor("#CB4335"));
        TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.CENTER;
        view.setLayoutParams(params);
        snack.show();

    }

  /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public static void mLoadFragment(Fragment fragment, Context context) {

        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //ft.setCustomAnimations(R.anim.enter, R.anim.exit_to_right);
        ft.replace(R.id.fragmentContainer, fragment, fragment.getClass().getSimpleName());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }
    protected static void sacaSnackbar (Context context, View view, String s)
    {

    }

    public static void ClearReturnSharedPref(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("returnKeyRoute");
        editor.remove("returnKeyCostCode");
        editor.remove("returnKeyLocCode");
        editor.remove("returnKeyTouRef");
        editor.remove("returnKeyAreaCode");
        editor.remove("returnKeyRouteCode");
        editor.remove("returnKeyTourPos");
        editor.remove("returnkeyCustomer");
        editor.remove("returnkeyReasonCode");
        editor.remove("returnKeyRepCode");
        editor.remove("returnKeyDriverCode");
        editor.remove("returnKeyHelperCode");
        editor.remove("returnKeyLorryCode");
        editor.remove("returnKeyReason");
        editor.commit();
    }
    public static void ClearReceiptSharedPref(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("ReckeyPayModePos");
        editor.remove("ReckeyPayMode");
        editor.remove("isHeaderComplete");
        editor.remove("ReckeyHeader");
        editor.remove("ReckeyRecAmt");
        editor.remove("ReckeyRemnant");
        editor.remove("ReckeyCHQNo");
        editor.remove("Rec_Start_Time");
        editor.commit();
    }
    public static void ClearCustomerSharedPref(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("selected_out_id");
        editor.remove("selected_out_name");
        editor.remove("selected_out_route_code");
        editor.remove("selected_pril_code");
        editor.commit();
    }
    public static void ClearDBName(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("Dist_DB");
        editor.commit();
    }



    //-----------------------------------------------------------------------------------------------------------------------------------------------------

    public static void mPrinterDialogbox(final Context context) {

        SharedPref mSharedPref;
        mSharedPref = new SharedPref(context);

        View promptView = LayoutInflater.from(context).inflate(R.layout.settings_printer_layout, null);
        final EditText serverURL = (EditText) promptView.findViewById(R.id.et_mac_address);

        String printer_mac_shared_pref = "";
        printer_mac_shared_pref = new SharedPref(context).getGlobalVal("printer_mac_address");

        if(!TextUtils.isEmpty(printer_mac_shared_pref))
        {
            serverURL.setText(printer_mac_shared_pref);
            Toast.makeText(context, "MAC Address Already Exists", Toast.LENGTH_LONG).show();
        }

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(promptView)
                .setTitle("Printer MAC Address")
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {
                Button bOk = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                Button bClose = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);



                bOk.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        if (serverURL.length() > 0) {

                            if (validate(serverURL.getText().toString().toUpperCase())) {
                                //SharedPreferencesClass.setLocalSharedPreference(context, "printer_mac_address", serverURL.getText().toString().toUpperCase());
                                new SharedPref(context).setGlobalVal("printer_mac_address", serverURL.getText().toString().toUpperCase());
                                Toast.makeText(context, "Saved Successfully", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                            else {
                                Toast.makeText(context, "Enter Valid MAC Address", Toast.LENGTH_LONG).show();
                            }
                        } else
                            Toast.makeText(context, "Type in the MAC Address", Toast.LENGTH_LONG).show();
                    }
                });

                bClose.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }

    public static boolean validate(String mac) {
        Pattern p = Pattern.compile("^([a-fA-F0-9]{2}[:-]){5}[a-fA-F0-9]{2}$");
        Matcher m = p.matcher(mac);
        return m.find();
    }

    public static void mRepsDetailsDialogBox(final Context context) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.settings_reps_details_layout, null);

        final EditText etUserName = (EditText) promptView.findViewById(R.id.et_rep_username);
        final EditText etRepCode = (EditText) promptView.findViewById(R.id.et_rep_code);
        final EditText etPreFix = (EditText) promptView.findViewById(R.id.et_rep_prefix);
        final EditText etLocCode = (EditText) promptView.findViewById(R.id.et_rep_loc_code);
        final EditText etAreaCode = (EditText) promptView.findViewById(R.id.et_rep_area_code);
        final EditText etDealerCode = (EditText) promptView.findViewById(R.id.et_rep_deal_code);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Sales Executive");
        alertDialogBuilder.setView(promptView);
        SalRep Vre = new SalRepController(context).getSaleRepDet(new SalRepController(context).getCurrentRepCode());

        //for (SalRep salRep : Vre) {
            etUserName.setText(Vre.getNAME());
            etRepCode.setText(Vre.getRepCode());
            etPreFix.setText(Vre.getPREFIX());

        //}

        alertDialogBuilder.setCancelable(false).setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    public static void mSQLiteDatabase(final Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.settings_sqlite_database_layout);
        dialog.setTitle("SQLite Backup/Restore");

        final Button b_backups = (Button) dialog.findViewById(R.id.b_backups);
        final Button b_restore = (Button) dialog.findViewById(R.id.b_restore);

        b_backups.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SQLiteBackUp backUp = new SQLiteBackUp(context);
                backUp.exportDB();
                dialog.dismiss();
            }
        });

        b_restore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mLoadFragment(new SQLiteRestore(), context);
//                FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.main_container, new FragmentTools());
//                ft.addToBackStack(null);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                ft.commit();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public static void download(final Context context, TaskTypeDownload task , String jsonString) {
        NetworkFunctions networkFunctions = new NetworkFunctions(context);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);

         } catch (JSONException e) {
            Log.e("JSON ERROR>>>>>",e.toString());
        }
        switch (task) {
            case ItenrDeb: {

                // Processing itenarydetdeb
                try {

                    final IteaneryDebController itenaryDebController = new IteaneryDebController(context);
                    JSONArray jsonArray = jsonObject.getJSONArray("fIteDebDetResult");

                    ArrayList<ItenrDeb> downloadedList = new ArrayList<ItenrDeb>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        downloadedList.add(ItenrDeb.parseIteDebDet(jsonArray.getJSONObject(i)));
                    }
                    itenaryDebController.InsertOrReplaceItenrDeb(downloadedList);
                    Log.d("InsertOrReplaceDebtor", "succes");


                } catch (Exception e) {
                  //  errors.add(e.toString());

                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }

            break;
            case Controllist:{

                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("fControlResult");
                        ArrayList<Control> downloadedList = new ArrayList<Control>();
                        CompanyDetailsController companyController = new CompanyDetailsController(context);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            downloadedList.add(Control.parseControlDetails(jsonArray.getJSONObject(i)));
                        }
                        companyController.createOrUpdateFControl(downloadedList);
                    } catch (JSONException | NumberFormatException e) {

                        try {
                            throw e;
                        } catch (JSONException e1) {
                            Log.e("JSON ERROR>>>>>",e.toString());
                        }
                    }
            }
            break;
            case Customers:{
                CustomerController customerController = new CustomerController(context);
                try {

                    JSONArray jsonArray = jsonObject.getJSONArray("FdebtorResult");
                    ArrayList<Debtor> downloadedList = new ArrayList<Debtor>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        downloadedList.add(Debtor.parseOutlet(jsonArray.getJSONObject(i)));
                    }
                    customerController.InsertOrReplaceDebtor(downloadedList);
                    Log.d("InsertOrReplaceDebtor", "succes");

                } catch (JSONException | NumberFormatException e) {

                   // errors.add(e.toString());
//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Settings:{
                ReferenceSettingController settingController = new ReferenceSettingController(context);
                try {

                    JSONArray jsonArray = jsonObject.getJSONArray("fCompanySettingResult");
                    ArrayList<CompanySetting> downloadedList = new ArrayList<CompanySetting>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        downloadedList.add(CompanySetting.parseSettings(jsonArray.getJSONObject(i)));
                    }
                    settingController.createOrUpdateFCompanySetting(downloadedList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Reference:{
                ReferenceDetailDownloader branchController = new ReferenceDetailDownloader(context);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("FCompanyBranchResult");
                    ArrayList<CompanyBranch> downloadedList = new ArrayList<CompanyBranch>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        downloadedList.add(CompanyBranch.parseSettings(jsonArray.getJSONObject(i)));
                    }
                    branchController.createOrUpdateFCompanyBranch(downloadedList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }

            }
            break;
            case ItemBundle:{

                try {
                    ItemBundleController bundleController = new ItemBundleController(context);
                    JSONArray jsonArray = jsonObject.getJSONArray("BundleBarCodeResult");
                    ArrayList<ItemBundle> downloadedList = new ArrayList<ItemBundle>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        downloadedList.add(ItemBundle.parseItemBundle(jsonArray.getJSONObject(i)));
                    }
                    bundleController.InsertOrReplaceItemBundle(downloadedList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case VAT:{
            final VATController vatController = new VATController(context);

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("VATMasterResult");
                    ArrayList<VatMaster> downloadedList = new ArrayList<VatMaster>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        downloadedList.add(VatMaster.parseVAT(jsonArray.getJSONObject(i)));
                    }
                    vatController.InsertOrReplaceVAT(downloadedList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }

            }
            break;
            case Items:{


                try {
                    ItemController itemController = new ItemController(context);
                    JSONArray jsonArray = jsonObject.getJSONArray("fItemsResult");
                    ArrayList<Item> downloadedList = new ArrayList<Item>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        downloadedList.add(Item.parseItem(jsonArray.getJSONObject(i)));
                    }
                    itemController.InsertOrReplaceItems(downloadedList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Reason:{
                ReasonController reasonController = new ReasonController(context);
                // Processing reasons
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("fReasonResult");
                    ArrayList<Reason> arrayList = new ArrayList<Reason>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(Reason.parseReason(jsonArray.getJSONObject(i)));
                    }
                    Log.d("befor add reason tbl>>>", arrayList.toString());
                    reasonController.createOrUpdateReason(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Bank: {
                BankController bankController = new BankController(context);
                // Processing route
                try {

                    JSONArray jsonArray = jsonObject.getJSONArray("fbankResult");
                    ArrayList<Bank> arrayList = new ArrayList<Bank>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(Bank.parseBank(jsonArray.getJSONObject(i)));
                    }
                    bankController.createOrUpdateBank(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Expense:{
                ExpenseController expenseController = new ExpenseController(context);
                // Processing expense
                try {

                    JSONArray jsonArray = jsonObject.getJSONArray("fExpenseResult");
                    ArrayList<Expense> arrayList = new ArrayList<Expense>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(Expense.parseExpense(jsonArray.getJSONObject(i)));
                    }
                    expenseController.createOrUpdateFExpense(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Route:{

                RouteController routeController = new RouteController(context);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("fRouteResult");
                    ArrayList<Route> arrayList = new ArrayList<Route>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(Route.parseRoute(jsonArray.getJSONObject(i)));
                    }
                    routeController.createOrUpdateFRoute(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case RouteDet:{
                RouteDetController routeDetController = new RouteDetController(context);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("fRouteDetResult");
                    ArrayList<RouteDet> arrayList = new ArrayList<RouteDet>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(RouteDet.parseRoute(jsonArray.getJSONObject(i)));
                    }
                    routeDetController.InsertOrReplaceRouteDet(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Freeslab:{
                FreeSlabController freeSlabController = new FreeSlabController(context);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("FfreeslabResult");
                    ArrayList<FreeSlab> arrayList = new ArrayList<FreeSlab>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(FreeSlab.parseFreeSlab(jsonArray.getJSONObject(i)));
                    }
                    freeSlabController.createOrUpdateFreeSlab(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Freemslab:{
                FreeMslabController freemSlabController = new FreeMslabController(context);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("fFreeMslabResult");
                    ArrayList<FreeMslab> arrayList = new ArrayList<FreeMslab>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(FreeMslab.parseFreeMslab(jsonArray.getJSONObject(i)));
                    }
                    freemSlabController.createOrUpdateFreeMslab(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Freehed:{
                FreeHedController freeHedController = new FreeHedController(context);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("FfreehedResult");
                    ArrayList<FreeHed> arrayList = new ArrayList<FreeHed>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(FreeHed.parseFreeHed(jsonArray.getJSONObject(i)));
                    }
                    freeHedController.createOrUpdateFreeHed(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Freedet:{
                FreeDetController freeDetController = new FreeDetController(context);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("FfreedetResult");
                    ArrayList<FreeDet> arrayList = new ArrayList<FreeDet>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(FreeDet.parseFreeDet(jsonArray.getJSONObject(i)));
                    }
                    freeDetController.createOrUpdateFreeDet(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Freedeb:{
                FreeDebController freeDebController = new FreeDebController(context);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("FfreedebResult");
                    ArrayList<FreeDeb> arrayList = new ArrayList<FreeDeb>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(FreeDeb.parseFreeDeb(jsonArray.getJSONObject(i)));
                    }
                    freeDebController.createOrUpdateFreeDeb(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Freeitem:{
                FreeItemController freeItemController = new FreeItemController(context);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("fFreeItemResult");
                    ArrayList<FreeItem> arrayList = new ArrayList<FreeItem>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(FreeItem.parseFreeItem(jsonArray.getJSONObject(i)));
                    }
                    freeItemController.createOrUpdateFreeItem(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Iteneryhed:{
                FItenrHedController fItenrHedController = new FItenrHedController(context);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("fItenrHedResult");
                    ArrayList<FItenrHed> arrayList = new ArrayList<FItenrHed>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(FItenrHed.parseIteanaryHed(jsonArray.getJSONObject(i)));
                    }
                    fItenrHedController.createOrUpdateFItenrHed(arrayList);
                    Log.d("InsertIhed ", "succes");
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }
            }
            break;
            case Itenerydet:{
                FItenrDetController fItenrDetController = new FItenrDetController(context);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("fItenrDetResult");
                    ArrayList<FItenrDet> arrayList = new ArrayList<FItenrDet>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d(">>>", ">>>" + i);
                        arrayList.add(FItenrDet.parseIteanaryDet(jsonArray.getJSONObject(i)));
                    }
                    fItenrDetController.createOrUpdateFItenrDet(arrayList);
                    Log.d("Insertitenrydet", "succes");

                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.d(">>>", "error in fragment" + e.toString());
                    }
                }
            }
            break;
            case Stock:{
                ItemLocController itemLocController = new ItemLocController(context);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("fItemLocResult");
                    ArrayList<ItemLoc> arrayList = new ArrayList<ItemLoc>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d(">>>", ">>>" + i);
                        arrayList.add(ItemLoc.parseItemLocs(jsonArray.getJSONObject(i)));
                    }
                    itemLocController.InsertOrReplaceItemLoc(arrayList);
                    Log.d("InsertItemLoc", "succes");

                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.d(">>>", "error in fragment" + e.toString());
                    }
                }
            }
            break;
            case Salesprice:{
                try {
                    SalesPriceController salesPriceController = new SalesPriceController(context);
                    JSONArray jsonArray = jsonObject.getJSONArray("SalesPriceResult");
                    ArrayList<SalesPrice> arrayList = new ArrayList<SalesPrice>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //  Log.d(">>>", ">>>" + i);
                        arrayList.add(SalesPrice.parseSalespri(jsonArray.getJSONObject(i)));
                    }
                    // Log.d(">>>", "size :" + salesPriList.size());
                    salesPriceController.InsertOrReplaceSalesPrice(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>",e.toString());
                    }
                }


            }
            break;
            case Discount: {
                DiscountController discountController = new DiscountController(context);
                try {

                    JSONArray jsonArray = jsonObject.getJSONArray("CusProductDisResult");
                    ArrayList<Discount> arrayList = new ArrayList<Discount>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(Discount.parseDiscounts(jsonArray.getJSONObject(i)));
                    }
                    discountController.InsertOrReplaceDiscount(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>", e.toString());
                    }
                }
            }
            break;
            case fddbnote: {
                OutstandingController outstandingController = new OutstandingController(context);
                try {

                    JSONArray jsonArray = jsonObject.getJSONArray("fDdbNoteWithConditionResult");
                    ArrayList<FddbNote> arrayList = new ArrayList<FddbNote>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayList.add(FddbNote.parseFddbnote(jsonArray.getJSONObject(i)));
                    }
                    outstandingController.createOrUpdateFDDbNote(arrayList);
                } catch (JSONException | NumberFormatException e) {
                    try {
                        throw e;
                    } catch (JSONException e1) {
                        Log.e("JSON ERROR>>>>>", e.toString());
                    }
                }
            }
                break;
                case VanStock : {
                    VanStockController vanStockController = new VanStockController(context);
                    try {

                        JSONArray jsonArray = jsonObject.getJSONArray("VanStockResult");
                        ArrayList<VanStock> arrayList = new ArrayList<VanStock>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            arrayList.add(VanStock.parseVanStock(jsonArray.getJSONObject(i)));
                        }
                        vanStockController.InsertOrReplaceVanStock(arrayList);
                    } catch (JSONException | NumberFormatException e) {
                        try {
                            throw e;
                        } catch (JSONException e1) {
                            Log.e("JSON ERROR>>>>>", e.toString());
                        }
                    }
                }
                    break;
                    case Barcodevarient: {

                        try {
                            BarcodeVarientController barcodeController = new BarcodeVarientController(context);
                            JSONArray jsonArray = jsonObject.getJSONArray("BarCodeVarientResult");
                            ArrayList<BarcodeVariant> arrayList = new ArrayList<BarcodeVariant>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                 // Log.d(">>BarcodeVariant", ">>>" + i);
                                arrayList.add(BarcodeVariant.parseBarcodevarient(jsonArray.getJSONObject(i)));
                            }
                             Log.d(">>BarcodeVariant", "size :" + arrayList.size());
                            barcodeController.InsertOrReplaceBarcodeVariant(arrayList);

                        } catch (JSONException | NumberFormatException e) {
                            try {
                                throw e;
                            } catch (JSONException e1) {
                                Log.e("JSON ERROR>>>>>", e.toString());
                            }
                        }

                Thread thread = new Thread(){
                    public void run(){
                        Looper.prepare();//Call looper.prepare()

                        Handler mHandler = new Handler() {
                            public void handleMessage(Message msg) {
                                Toast.makeText(context,"Download Completed",Toast.LENGTH_SHORT).show();
                            }
                        };

                        Looper.loop();
                    }
                };
                thread.start();
            }
            break;
            default:
                break;
        }








    }
}
