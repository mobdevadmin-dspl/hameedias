package com.datamation.hmdsfa.utils;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
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
import com.datamation.hmdsfa.controller.BankController;
import com.datamation.hmdsfa.controller.CompanyDetailsController;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.ExpenseController;
import com.datamation.hmdsfa.controller.FItenrDetController;
import com.datamation.hmdsfa.controller.FItenrHedController;
import com.datamation.hmdsfa.controller.FreeDebController;
import com.datamation.hmdsfa.controller.FreeDetController;
import com.datamation.hmdsfa.controller.FreeHedController;
import com.datamation.hmdsfa.controller.FreeItemController;
import com.datamation.hmdsfa.controller.FreeMslabController;
import com.datamation.hmdsfa.controller.FreeSlabController;
import com.datamation.hmdsfa.controller.ItemBundleController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.ReasonController;
import com.datamation.hmdsfa.controller.ReferenceDetailDownloader;
import com.datamation.hmdsfa.controller.ReferenceSettingController;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.controller.SalesPriceController;
import com.datamation.hmdsfa.controller.VATController;
import com.datamation.hmdsfa.helpers.SQLiteBackUp;
import com.datamation.hmdsfa.helpers.SQLiteRestore;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.SalRep;
import com.datamation.hmdsfa.model.apimodel.ReadJsonList;
import com.datamation.hmdsfa.settings.TaskType;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * common functions
 */

public class UtilityContainerOld {


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

        View promptView = LayoutInflater.from(context).inflate(R.layout.settings_printer_layout, null);
        final EditText serverURL = (EditText) promptView.findViewById(R.id.et_mac_address);

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

                            if (validate(serverURL.getText().toString().toUpperCase()))
                            {
                                SharedPref.getInstance(context).setMacAddress(serverURL.getText().toString().toUpperCase());
                                dialog.dismiss();
                            }

                            else
                            {
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
    public static void download(final Context context, Call<ReadJsonList> resultCall, TaskType task) {

        switch (task) {
            case ItenrDeb: {
                try {
                            resultCall.enqueue(new Callback<ReadJsonList>() {
                            @Override
                            public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                                if(response.body() != null) {
//                                    final ArrayList<Control> controlList = new ArrayList<Control>();
//                                    for (int i = 0; i < response.body().getControlResult().size(); i++) {
//                                        controlList.add(response.body().getControlResult().get(i));
//                                    }
                                    final CompanyDetailsController companyController = new CompanyDetailsController(context);
                                            companyController.createOrUpdateFControl(response.body().getControlResult());
                                }else{
                                    //errors.add("Control response is null");
                                }
                            }
                            @Override
                            public void onFailure(Call<ReadJsonList> call, Throwable t) {
                              //  errors.add(t.toString());
                            }
                        });
                } catch (Exception e) {
                    Log.e("networkFunctions ->", "IOException -> " + e.toString());
                    throw e;
                }
            }

            break;
            case Controllist:{
                try {
                        resultCall.enqueue(new Callback<ReadJsonList>() {
                            @Override
                            public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                                if(response.body() != null) {
//                                    final ArrayList<Debtor> debtorList = new ArrayList<Debtor>();
//                                    for (int i = 0; i < response.body().getDebtorResult().size(); i++) {
//                                        debtorList.add(response.body().getDebtorResult().get(i));
//                                    }
                                    final CustomerController customerController = new CustomerController(context);
                                            customerController.InsertOrReplaceDebtor(response.body().getDebtorResult());
                                }else{
                                   // errors.add("Control response is null");
                                }
                            }
                            @Override
                            public void onFailure(Call<ReadJsonList> call, Throwable t) {
                              //  errors.add(t.toString());
                            }
                        });
                    } catch (Exception e) {
                      //  errors.add(e.toString());
                        throw e;
                    }
            }
            break;
            case Customers:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {

                        if(response.body() != null) {
//                            final ArrayList<CompanySetting> settingList = new ArrayList<CompanySetting>();
//                            for (int i = 0; i < response.body().getCompanySettingResult().size(); i++) {
//                                settingList.add(response.body().getCompanySettingResult().get(i));
//                            }
                            final ReferenceSettingController settingController = new ReferenceSettingController(context);
                                    settingController.createOrUpdateFCompanySetting(response.body().getCompanySettingResult());
                        }else {
                            //errors.add("CompanySetting response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                      //  errors.add(t.toString());
                    }
                });
            }
            break;
            case Settings:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
//                            final ArrayList<CompanyBranch> settingList = new ArrayList<CompanyBranch>();
//                            for (int i = 0; i < response.body().getCompanyBranchResult().size(); i++) {
//                                settingList.add(response.body().getCompanyBranchResult().get(i));
//                            }
                            final ReferenceDetailDownloader settingController = new ReferenceDetailDownloader(context);
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (references)...");
                                    settingController.createOrUpdateFCompanyBranch(response.body().getCompanyBranchResult());
//                                    pdialog.setMessage("Processed (references)...");
//
//                                }
//                            });

                        }else{
                            //errors.add("CompanyBranch response is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                      //  errors.add(t.toString());
                    }
                });
            }
            break;
            case Reference:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
//                            final ArrayList<ItemBundle> bundleList = new ArrayList<ItemBundle>();
//                            for (int i = 0; i < response.body().getItemBundleResult().size(); i++) {
//                                bundleList.add(response.body().getItemBundleResult().get(i));
//                            }
                            final ItemBundleController bundleController = new ItemBundleController(context);
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (Item bundle)...");
                                    bundleController.InsertOrReplaceItemBundle(response.body().getItemBundleResult());
//                                    pdialog.setMessage("Processed (Item bundle)...");
//
//                                }
                           // });

                        }else{
                            Log.d(">>ItmBundleRes", ">> is null");

                           // errors.add("ItemBundle response is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                      //  errors.add(t.toString());
                    }
                });
            }
            break;
            case ItemBundle:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
//                            final ArrayList<VatMaster> vatList = new ArrayList<VatMaster>();
//                            for (int i = 0; i < response.body().getVatMasterList().size(); i++) {
//                                vatList.add(response.body().getVatMasterList().get(i));
//                            }
                            final VATController vatController = new VATController(context);

//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (VAT)...");
                                    vatController.InsertOrReplaceVAT(response.body().getVatMasterList());
//                                    pdialog.setMessage("Processed (VAT)...");
//
//                                }
//                            });
                        }else{
                            Log.d(">>vat Res", ">> is null");

                            //errors.add("vat response is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                       // errors.add(t.toString());
                    }
                });
            }
            break;
            case VAT:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
//                            final ArrayList<Item> itemList = new ArrayList<Item>();
//                            for (int i = 0; i < response.body().getItemsResult().size(); i++) {
//                                itemList.add(response.body().getItemsResult().get(i));
//                            }
                            final ItemController itemController = new ItemController(context);

//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (Items)...");
                                    itemController.InsertOrReplaceItems(response.body().getItemsResult());
//                                    pdialog.setMessage("Processed (Items)...");
//
//                                }
//                            });
                        }else{
                            //errors.add("Item response is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                        //errors.add(t.toString());
                    }
                });
            }
            break;
            case Items:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
//                            final ArrayList<Reason> reasonList = new ArrayList<Reason>();
//                            for (int i = 0; i < response.body().getReasonResult().size(); i++) {
//                                reasonList.add(response.body().getReasonResult().get(i));
//                            }
                            final ReasonController reasonController = new ReasonController(context);

//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (Reasons)...");
                                    reasonController.createOrUpdateReason(response.body().getReasonResult());
//                                    pdialog.setMessage("Processed (Reasons)...");
//
//                                }
//                            });
                        }else{
                            //errors.add("Reason response is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                        //errors.add(t.toString());
                    }
                });
            }
            break;
            case Reason:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
                            final BankController bankController = new BankController(context);
//                            final ArrayList<Bank> bankList = new ArrayList<Bank>();
//                            for (int i = 0; i < response.body().getBankResult().size(); i++) {
//                                bankList.add(response.body().getBankResult().get(i));
//                            }
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (Banks)...");
                                    bankController.createOrUpdateBank(response.body().getBankResult());
//                                    pdialog.setMessage("Processed (Banks)...");
//
//                                }
//                            });

                        }else{
                            //errors.add("Bank response is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                      //  errors.add(t.toString());
                    }
                });
            }
            break;
            case Bank: {
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
//                            final ArrayList<Expense> expensesList = new ArrayList<Expense>();
//                            for (int i = 0; i < response.body().getExpenseResult().size(); i++) {
//                                expensesList.add(response.body().getExpenseResult().get(i));
//                            }
                            final ExpenseController expenseController = new ExpenseController(context);

//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (Expenses)...");
                                    expenseController.createOrUpdateFExpense(response.body().getExpenseResult());
//                                    pdialog.setMessage("Processed (Expenses)...");
//
//                                }
//                            });
                        }else{
                            //errors.add("Expense response is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                       // errors.add(t.toString());
                    }
                });
            }
            break;
            case Expense:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
//                            final ArrayList<Route> routeList = new ArrayList<Route>();
//                            for (int i = 0; i < response.body().getRouteResult().size(); i++) {
//                                routeList.add(response.body().getRouteResult().get(i));
//                            }
                            final RouteController routeController = new RouteController(context);
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (Routes)...");
                                    routeController.createOrUpdateFRoute(response.body().getRouteResult());
//                                    pdialog.setMessage("Processed (Routes)...");
//
//                                }
//                            });

                        }else{
                          //  errors.add("Route response is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                       // errors.add(t.toString());
                    }
                });
            }
            break;
            case Route:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
//                            final ArrayList<RouteDet> routeList = new ArrayList<RouteDet>();
//                            for (int i = 0; i < response.body().getRouteDetResult().size(); i++) {
//                                routeList.add(response.body().getRouteDetResult().get(i));
//                            }
                            final RouteDetController routeController = new RouteDetController(context);
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (Routes)...");
                                    routeController.InsertOrReplaceRouteDet(response.body().getRouteDetResult());
//                                    pdialog.setMessage("Processed (Routes)...");
//
//                                }
//                            });

                        }else{
                           // errors.add("RouteDetail response is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                      //  errors.add(t.toString());
                    }
                });
            }
            break;
            case RouteDet:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
                            final FreeSlabController freeslabController = new FreeSlabController(context);
                            freeslabController.deleteAll();
//                            final ArrayList<FreeSlab> freeslabList = new ArrayList<FreeSlab>();
//                            for (int i = 0; i < response.body().getFreeSlabResult().size(); i++) {
//                                freeslabList.add(response.body().getFreeSlabResult().get(i));
//                            }

//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (Freeslab)...");
                                    freeslabController.createOrUpdateFreeSlab(response.body().getFreeSlabResult());
//                                    pdialog.setMessage("Processed (Freeslab)...");
//
//                                }
//                            });

                        }else{
                           // errors.add("FreeSlab response is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                      //  errors.add(t.toString());
                    }
                });
            }
            break;
            case Freeslab:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
                            final FreeMslabController freeMslabController = new FreeMslabController(context);
                            freeMslabController.deleteAll();
//                            final ArrayList<FreeMslab> freeMslabList = new ArrayList<FreeMslab>();
//                            for (int i = 0; i < response.body().getFreeMslabResult().size(); i++) {
//                                freeMslabList.add(response.body().getFreeMslabResult().get(i));
//                            }

//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (Freemslab)...");
                                    freeMslabController.createOrUpdateFreeMslab(response.body().getFreeMslabResult());
//                                    pdialog.setMessage("Processed (Freemslab)...");
//
//                                }
//                            });
                        }else{
                          //  errors.add("FreeMslab response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                      //  errors.add(t.toString());
                    }
                });
            }
            break;
            case Freemslab:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
                            final FreeHedController freeHedController = new FreeHedController(context);
                            freeHedController.deleteAll();
//                            final ArrayList<FreeHed> freeHedList = new ArrayList<FreeHed>();
//                            for (int i = 0; i < response.body().getFreeHedResult().size(); i++) {
//                                freeHedList.add(response.body().getFreeHedResult().get(i));
//                            }

//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (Freehed)...");
                                    freeHedController.createOrUpdateFreeHed(response.body().getFreeHedResult());
//                                    pdialog.setMessage("Processed (Freehed)...");
//
//                                }
//                            });
                        }else{
                           // errors.add("FreeHed response is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                      //  errors.add(t.toString());
                    }
                });
            }
            break;
            case Freehed:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {

                        if(response.body() != null) {
                            final FreeDetController freedetController = new FreeDetController(context);
                            freedetController.deleteAll();
//                            final ArrayList<FreeDet> freedetList = new ArrayList<FreeDet>();
//                            for (int i = 0; i < response.body().getFreeDetResult().size(); i++) {
//                                freedetList.add(response.body().getFreeDetResult().get(i));
//                            }
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (Freedet)...");
                                    freedetController.createOrUpdateFreeDet(response.body().getFreeDetResult());
//                                    pdialog.setMessage("Processed (Freedet)...");
//
//                                }
//                            });

                        }else{
                          //  errors.add("FreeDetail response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                      //  errors.add(t.toString());
                    }
                });
            }
            break;
            case Freedet:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {

                        if(response.body() != null) {
                            final FreeDebController freedebController = new FreeDebController(context);
                            freedebController.deleteAll();
//                            final ArrayList<FreeDeb> freedebList = new ArrayList<FreeDeb>();
//                            for (int i = 0; i < response.body().getFreeDebResult().size(); i++) {
//                                freedebList.add(response.body().getFreeDebResult().get(i));
//                            }
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (freedeb)...");
                                    freedebController.createOrUpdateFreeDeb(response.body().getFreeDebResult());
//                                    pdialog.setMessage("Processed (freedeb)...");
//
//                                }
//                            });

                        }else{
                           // errors.add("FreeDeb response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                      //  errors.add(t.toString());
                    }
                });
            }
            break;
            case Freedeb:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
                            final FreeItemController freeitemController = new FreeItemController(context);
                            freeitemController.deleteAll();
//                            final ArrayList<FreeItem> freeitemList = new ArrayList<FreeItem>();
//                            for (int i = 0; i < response.body().getFreeItemResult().size(); i++) {
//                                freeitemList.add(response.body().getFreeItemResult().get(i));
//                            }
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (freeitem)...");
                                    freeitemController.createOrUpdateFreeItem(response.body().getFreeItemResult());
//                                    pdialog.setMessage("Processed (freeitem)...");
//
//                                }
//                            });

                        }else{
                            //errors.add("FreeItem response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                       // errors.add(t.toString());
                    }
                });
            }
            break;
            case Freeitem:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {

                        if(response.body() != null) {
                            final FItenrHedController itenaryHedController = new FItenrHedController(context);
//                            final ArrayList<FItenrHed> itenaryHedList = new ArrayList<FItenrHed>();
//                            for (int i = 0; i < response.body().getItenrHedResult().size(); i++) {
//                                itenaryHedList.add(response.body().getItenrHedResult().get(i));
//                            }
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (itenaryhed)...");
                                    itenaryHedController.createOrUpdateFItenrHed(response.body().getItenrHedResult());
//                                    pdialog.setMessage("Processed (itenaryhed)...");
//
//                                }
//                            });

                        }else{
                           // errors.add("ItenrHed response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                      //  errors.add(t.toString());
                    }
                });
            }
            break;
            case Iteneryhed:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {


                        if(response.body() != null) {
                            final FItenrDetController itenaryDetController = new FItenrDetController(context);
                            itenaryDetController.deleteAll();
//                            final ArrayList<FItenrDet> itenaryDetList = new ArrayList<FItenrDet>();
//                            for (int i = 0; i < response.body().getItenrDetResult().size(); i++) {
//                                itenaryDetList.add(response.body().getItenrDetResult().get(i));
//                            }
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (itenarydet)...");
                                    itenaryDetController.createOrUpdateFItenrDet(response.body().getItenrDetResult());
//                                    pdialog.setMessage("Processed (itenarydet)...");
//
//                                }
//                            });

                        }else{
                           // errors.add("ItenrDet response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                        //errors.add(t.toString());
                    }
                });
            }
            break;
            case Itenerydet:{
                resultCall.enqueue(new Callback<ReadJsonList>() {
                    @Override
                    public void onResponse(Call<ReadJsonList> call, Response<ReadJsonList> response) {
                        if(response.body() != null) {
//                            final ArrayList<SalesPrice> salesPri_list = new ArrayList<SalesPrice>();
//                            for (int i = 0; i < response.body().getSalesPriceResult().size(); i++) {
//                                salesPri_list.add(response.body().getSalesPriceResult().get(i));
//                            }
                            final SalesPriceController salepriController = new SalesPriceController(context);
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    pdialog.setMessage("Processing downloaded data (salesprice)...");
                                    salepriController.InsertOrReplaceSalesPrice(response.body().getSalesPriceResult());
//                                    pdialog.setMessage("Processed (salesprice)...");
//
//                                }
//                            });

                        }else{
                            //errors.add("SalesPrice response is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<ReadJsonList> call, Throwable t) {
                        //errors.add(t.toString());
                    }
                });
            }
            break;
            case Salesprice:{
                Toast.makeText(context,"Download Completed",Toast.LENGTH_SHORT).show();

            }
            break;
            case Discount:{

            }
            break;
            default:
                break;
        }








    }
}
