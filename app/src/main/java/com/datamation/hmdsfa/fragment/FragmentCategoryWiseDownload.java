package com.datamation.hmdsfa.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.api.ApiCllient;
import com.datamation.hmdsfa.api.ApiInterface;
import com.datamation.hmdsfa.controller.BarcodeVarientController;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.DayExpHedController;
import com.datamation.hmdsfa.controller.DayNPrdHedController;
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
import com.datamation.hmdsfa.controller.ItemPriceController;
import com.datamation.hmdsfa.controller.LocationsController;
import com.datamation.hmdsfa.controller.OrderController;
import com.datamation.hmdsfa.controller.OutstandingController;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.controller.SalesPriceController;
import com.datamation.hmdsfa.controller.VanStockController;
import com.datamation.hmdsfa.dialog.CustomProgressDialog;
import com.datamation.hmdsfa.helpers.NetworkFunctions;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.BarcodeVariant;
import com.datamation.hmdsfa.model.DayExpHed;
import com.datamation.hmdsfa.model.DayNPrdHed;
import com.datamation.hmdsfa.model.Debtor;
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
import com.datamation.hmdsfa.model.ItemPri;
import com.datamation.hmdsfa.model.ItenrDeb;
import com.datamation.hmdsfa.model.Locations;
import com.datamation.hmdsfa.model.Order;
import com.datamation.hmdsfa.model.Route;
import com.datamation.hmdsfa.model.RouteDet;
import com.datamation.hmdsfa.model.SalesPrice;
import com.datamation.hmdsfa.model.VanStock;
import com.datamation.hmdsfa.settings.TaskType;
import com.datamation.hmdsfa.utils.NetworkUtil;
import com.datamation.hmdsfa.utils.UtilityContainer;
import com.datamation.hmdsfa.view.ActivityHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FragmentCategoryWiseDownload extends Fragment {

    private View view;
    private TextView downItems, downFree, downRoute, downOutstanding, downPrice, downStock, downOthers,downCustomer;
    NetworkFunctions networkFunctions;
    ApiInterface apiInterface;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category_download, container, false);

        getActivity().setTitle("Category Wise Download");
        networkFunctions = new NetworkFunctions(getActivity());
        //initializations
        downItems = (TextView) view.findViewById(R.id.items_download);
        downFree = (TextView) view.findViewById(R.id.free_download);
        downRoute = (TextView) view.findViewById(R.id.route_download);
        downOutstanding = (TextView) view.findViewById(R.id.outstanding_download);
        downPrice = (TextView) view.findViewById(R.id.price_download);
        downStock = (TextView) view.findViewById(R.id.stock_download);
        downOthers = (TextView) view.findViewById(R.id.other_download);
        downCustomer = (TextView) view.findViewById(R.id.customer_download);

        downItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean connectionStatus = NetworkUtil.isNetworkAvailable(getActivity());
                if (connectionStatus == true) {

                    if (isAllUploaded(getActivity())) {

                        try {
                            new itemsDownload(SharedPref.getInstance(getActivity()).getLoginUser().getRepCode()).execute();
                        } catch (Exception e) {
                            Log.e("## ErrorInItemDown ##", e.toString());
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Upload All Transactions", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            }
        });

        downFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean connectionStatus = NetworkUtil.isNetworkAvailable(getActivity());
                if (connectionStatus == true) {

                    if (isAllUploaded(getActivity())) {

                        try {

                            new freeDownload(SharedPref.getInstance(getActivity()).getLoginUser().getRepCode()).execute();
                        } catch (Exception e) {
                            Log.e("## ErrorInItemDown ##", e.toString());
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Upload All Transactions", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        downRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean connectionStatus = NetworkUtil.isNetworkAvailable(getActivity());
                if (connectionStatus == true) {

                    if (isAllUploaded(getActivity())) {

                        try {
                            new routeDownload(SharedPref.getInstance(getActivity()).getLoginUser().getRepCode()).execute();
                        } catch (Exception e) {
                            Log.e("## ErrorInItemDown ##", e.toString());
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Upload All Transactions", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        downOutstanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean connectionStatus = NetworkUtil.isNetworkAvailable(getActivity());
                if (connectionStatus == true) {

                    if (isAllUploaded(getActivity())) {

                        try {
                            new outstandingDownload(SharedPref.getInstance(getActivity()).getLoginUser().getRepCode()).execute();
                        } catch (Exception e) {
                            Log.e("## ErrorInOutstanding##", e.toString());
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Upload All Transactions", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        downStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean connectionStatus = NetworkUtil.isNetworkAvailable(getActivity());

                if (connectionStatus == true) {
                    if (isAllUploaded(getActivity())) {
                        try {
                            new StockDownload(SharedPref.getInstance(getActivity()).getLoginUser().getRepCode()).execute();
                        } catch (Exception e) {
                            Log.e("## ErrorInStockDown ##", e.toString());
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Upload All Transactions", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        downPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean connectionStatus = NetworkUtil.isNetworkAvailable(getActivity());

                if (connectionStatus == true) {
                    if (isAllUploaded(getActivity())) {
                        try {
                            new salespriceDownload(SharedPref.getInstance(getActivity()).getLoginUser().getRepCode()).execute();
                        } catch (Exception e) {
                            Log.e("## ErrorInPriceDown ##", e.toString());
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Upload All Transactions", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        downCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean connectionStatus = NetworkUtil.isNetworkAvailable(getActivity());

                if (connectionStatus == true) {
                    if (isAllUploaded(getActivity())) {
                        try {
                            new CustomersDownload(SharedPref.getInstance(getActivity()).getLoginUser().getRepCode()).execute();
                        } catch (Exception e) {
                            Log.e("## ErrorInCustomer ##", e.toString());
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Upload All Transactions", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        downOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean connectionStatus = NetworkUtil.isNetworkAvailable(getActivity());

                if (connectionStatus == true) {
                    if (isAllUploaded(getActivity())) {
                        try {
                            new OtherDownload(SharedPref.getInstance(getActivity()).getLoginUser().getRepCode()).execute();
                        } catch (Exception e) {
                            Log.e("## ErrorInOtherDown ##", e.toString());
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please Upload All Transactions", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });


        //DISABLED BACK NAVIGATION
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("", "keyCode: " + keyCode);
                ActivityHome.navigation.setVisibility(View.VISIBLE);

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Toast.makeText(getActivity(), "Back button disabled!", Toast.LENGTH_SHORT).show();
                    return true;
                } else if ((keyCode == KeyEvent.KEYCODE_HOME)) {

                    getActivity().finish();

                    return true;

                } else {
                    return false;
                }
            }
        });


        return view;
    }


    private boolean isAllUploaded(Context context) {
        Boolean allUpload = false;

        OrderController orderHed = new OrderController(context);
        ArrayList<Order> ordHedList = orderHed.getAllUnSyncOrdHed();

        DayNPrdHedController npHed = new DayNPrdHedController(context);
        ArrayList<DayNPrdHed> npHedList = npHed.getUnSyncedData();

        DayExpHedController exHed = new DayExpHedController(context);
        ArrayList<DayExpHed> exHedList = exHed.getUnSyncedData();

        if (ordHedList.isEmpty() && npHedList.isEmpty() && exHedList.isEmpty()) {
            allUpload = true;
        } else {
            allUpload = false;
        }

        return allUpload;
    }

    //item download asynctask
    private class itemsDownload extends AsyncTask<String, Integer, Boolean> {
        CustomProgressDialog pdialog;
        private String repcode;

        public itemsDownload(String repCode) {
            this.repcode = repCode;
            this.pdialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new CustomProgressDialog(getActivity());
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Downloading Items...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            try {
                if (SharedPref.getInstance(getActivity()).getLoginUser() != null && SharedPref.getInstance(getActivity()).isLoggedIn()) {
                    BarcodeVarientController barcodeVarientController = new BarcodeVarientController(getActivity());
                    barcodeVarientController.deleteAll_BarcodeVariant();
                    ItemBundleController itemBundleController = new ItemBundleController(getActivity());
                    itemBundleController.deleteAll();
                    ItemController itemController = new ItemController(getActivity());
                    itemController.deleteAll();
                    /*****************Item *****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading item data...");
                        }
                    });

                    String item = "";
                    try {
                        item = networkFunctions.getItems(repcode);
                        // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    // Processing item
                    try {
                        JSONObject itemJSON = new JSONObject(item);
                        JSONArray itemJSONArray = itemJSON.getJSONArray("fItemsResult");
                        ArrayList<Item> itemList = new ArrayList<Item>();

                        for (int i = 0; i < itemJSONArray.length(); i++) {
                            itemList.add(Item.parseItem(itemJSONArray.getJSONObject(i)));
                        }
                        itemController.InsertOrReplaceItems(itemList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    /*****************end item **********************************************************************/

                    /*****************Item Bundle - kaveesha - 10-06-2020*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading item data...");
                        }
                    });

                    String itemBundle = "";
                    try {
                        itemBundle = networkFunctions.getItemBundles(repcode);
                        // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    // Processing itembundle
                    try {
                        JSONObject itemBundleJSON = new JSONObject(itemBundle);
                        JSONArray itemBundleJSONArray = itemBundleJSON.getJSONArray("BundleBarCodeResult");
                        ArrayList<ItemBundle> itemBundleList = new ArrayList<ItemBundle>();

                        for (int i = 0; i < itemBundleJSONArray.length(); i++) {
                            itemBundleList.add(ItemBundle.parseItemBundle(itemBundleJSONArray.getJSONObject(i)));
                        }
                        itemBundleController.InsertOrReplaceItemBundle(itemBundleList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }

                    /*****************Barcode Variant - kaveesha - 13-06-2020*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading Barcode Variant...");
                        }
                    });

                    String barcodeVariant = "";
                    try {
                        barcodeVariant = networkFunctions.getBarcodeVariant(repcode);
                        // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    // Processing barcodevariant
                    try {
                        JSONObject barcodeVJSON = new JSONObject(barcodeVariant);
                        JSONArray barcodeVJSONArray = barcodeVJSON.getJSONArray("BarCodeVarientResult");
                        ArrayList<BarcodeVariant> barcodeVList = new ArrayList<BarcodeVariant>();
                        for (int i = 0; i < barcodeVJSONArray.length(); i++) {
                            barcodeVList.add(BarcodeVariant.parseBarcodevarient(barcodeVJSONArray.getJSONObject(i)));
                        }
                        barcodeVarientController.InsertOrReplaceBarcodeVariant(barcodeVList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Download complete...");
                        }
                    });
                    return true;
                } else {
                    //errors.add("Please enter correct username and password");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();

                return false;
            } catch (JSONException e) {
                e.printStackTrace();

                return false;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);

            pdialog.setMessage("Finalizing itembundle data");
            pdialog.setMessage("Download Completed..");
            if (result) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            } else {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            }
        }
    }

    //salesprice download asynctask -- kaveesha -  03-06-2020
    private class salespriceDownload extends AsyncTask<String, Integer, Boolean> {
        CustomProgressDialog pdialog;
        private String repcode;

        public salespriceDownload(String repcode) {
            this.repcode = repcode;
            this.pdialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new CustomProgressDialog(getActivity());
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Downloading SalesPrice...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            try {
                if (SharedPref.getInstance(getActivity()).getLoginUser() != null && SharedPref.getInstance(getActivity()).isLoggedIn()) {

                    /*****************Sales Price****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading Sales Price Data...");
                        }
                    });

                    String salespri = "";
                    try {
                        salespri = networkFunctions.getSalesPrice(repcode);
                        // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }


                    try {
                        JSONObject salesPriceJSON = new JSONObject(salespri);
                        JSONArray salesPriJSONJSONArray = salesPriceJSON.getJSONArray("SalesPriceResult");
                        ArrayList<SalesPrice> salesPriList = new ArrayList<SalesPrice>();
                        SalesPriceController salesPriceController = new SalesPriceController(getActivity());
                        salesPriceController.deleteAll();
                        for (int i = 0; i < salesPriJSONJSONArray.length(); i++) {
                            //  Log.d(">>>", ">>>" + i);
                            salesPriList.add(SalesPrice.parseSalespri(salesPriJSONJSONArray.getJSONObject(i)));
                        }
                        // Log.d(">>>", "size :" + salesPriList.size());
                        salesPriceController.InsertOrReplaceSalesPrice(salesPriList);
                    } catch (JSONException | NumberFormatException e) {
                        // Log.d(">>>", "error in fragment :" + e.toString());

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    /*****************end sales price **********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Download complete...");
                        }
                    });
                    return true;
                } else {
                    //errors.add("Please enter correct username and password");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();

                return false;
            } catch (JSONException e) {
                e.printStackTrace();

                return false;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);

            pdialog.setMessage("Finalizing Sales Price data");
            pdialog.setMessage("Download Completed..");
            if (result) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            } else {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            }
        }
    }

    //free download asynctask

    private class freeDownload extends AsyncTask<String, Integer, Boolean> {
        CustomProgressDialog pdialog;
        private String repcode;

        public freeDownload(String repCode) {
            this.repcode = repCode;
            this.pdialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new CustomProgressDialog(getActivity());
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Downloading Free...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            try {
                if (SharedPref.getInstance(getActivity()).getLoginUser() != null && SharedPref.getInstance(getActivity()).isLoggedIn()) {

                    /*****************FreeHed**********************************************************************/
                    String freehed = "";
                    try {
                        freehed = networkFunctions.getFreeHed(repcode);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }


                    // Processing freehed
                    try {
                        JSONObject freeHedJSON = new JSONObject(freehed);
                        JSONArray freeHedJSONArray = freeHedJSON.getJSONArray("FfreehedResult");
                        ArrayList<FreeHed> freeHedList = new ArrayList<FreeHed>();
                        FreeHedController freeHedController = new FreeHedController(getActivity());
                        freeHedController.deleteAll();
                        for (int i = 0; i < freeHedJSONArray.length(); i++) {
                            freeHedList.add(FreeHed.parseFreeHed(freeHedJSONArray.getJSONObject(i)));
                        }
                        freeHedController.createOrUpdateFreeHed(freeHedList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    /*****************end freeHed**********************************************************************/
                    /*****************Freedet**********************************************************************/
                    String freedet = "";
                    try {
                        freedet = networkFunctions.getFreeDet();
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    try {
                        JSONObject freedetJSON = new JSONObject(freedet);
                        JSONArray freedetJSONArray = freedetJSON.getJSONArray("FfreedetResult");
                        ArrayList<FreeDet> freedetList = new ArrayList<FreeDet>();
                        FreeDetController freedetController = new FreeDetController(getActivity());
                        freedetController.deleteAll();
                        for (int i = 0; i < freedetJSONArray.length(); i++) {
                            freedetList.add(FreeDet.parseFreeDet(freedetJSONArray.getJSONObject(i)));
                        }
                        freedetController.createOrUpdateFreeDet(freedetList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    /*****************end freedet**********************************************************************/
                    /*****************freedeb**********************************************************************/
                    String freedeb = "";
                    try {
                        freedeb = networkFunctions.getFreeDebs();
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }


                    FreeDebController freedebController = new FreeDebController(getActivity());
                    freedebController.deleteAll();
                    // Processing freedeb
                    try {
                        JSONObject freedebJSON = new JSONObject(freedeb);
                        JSONArray freedebJSONArray = freedebJSON.getJSONArray("FfreedebResult");
                        ArrayList<FreeDeb> freedebList = new ArrayList<FreeDeb>();

                        for (int i = 0; i < freedebJSONArray.length(); i++) {
                            freedebList.add(FreeDeb.parseFreeDeb(freedebJSONArray.getJSONObject(i)));
                        }
                        freedebController.createOrUpdateFreeDeb(freedebList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    /*****************end freedeb**********************************************************************/
                    /*****************FreeItem*********************************************************************/
                    String freeitem = "";
                    try {
                        freeitem = networkFunctions.getFreeItems();
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    // Processing freeItem
                    try {
                        JSONObject freeitemJSON = new JSONObject(freeitem);
                        JSONArray freeitemJSONArray = freeitemJSON.getJSONArray("fFreeItemResult");
                        ArrayList<FreeItem> freeitemList = new ArrayList<FreeItem>();
                        FreeItemController freeitemController = new FreeItemController(getActivity());
                        freeitemController.deleteAll();
                        for (int i = 0; i < freeitemJSONArray.length(); i++) {
                            freeitemList.add(FreeItem.parseFreeItem(freeitemJSONArray.getJSONObject(i)));
                        }
                        freeitemController.createOrUpdateFreeItem(freeitemList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    /*****************end freeItem**********************************************************************/

                    /*****************Freemslab - kaveesha -11-06-2020 *********************************************************************/
                    String freemslab = "";
                    try {
                        freemslab = networkFunctions.getFreeMslab();
                    } catch (IOException e) {

                        throw e;
                    }

                    // Processing Freemslab
                    try {
                        JSONObject freemslabJSON = new JSONObject(freemslab);
                        JSONArray freemslabJSONArray = freemslabJSON.getJSONArray("fFreeMslabResult");
                        ArrayList<FreeMslab> freeMslabsList = new ArrayList<FreeMslab>();
                        FreeMslabController freeMslabController = new FreeMslabController(getActivity());
                        freeMslabController.deleteAll();
                        for (int i = 0; i < freemslabJSONArray.length(); i++) {
                            freeMslabsList.add(FreeMslab.parseFreeMslab(freemslabJSONArray.getJSONObject(i)));
                        }
                        freeMslabController.createOrUpdateFreeMslab(freeMslabsList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);
                        throw e;
                    }
                    /*****************end freemslab**********************************************************************/

                    /*****************freeslab kaveesha - 13-06-2020 *********************************************************************/
                    String freeslab = "";
                    try {
                        freeslab = networkFunctions.getFreeSlab();
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    // Processing freeslab
                    try {
                        JSONObject freeslabJSON = new JSONObject(freeslab);
                        JSONArray freeslabJSONArray = freeslabJSON.getJSONArray("FfreeslabResult");
                        ArrayList<FreeSlab> freeslabList = new ArrayList<FreeSlab>();
                        FreeSlabController freeSlabController = new FreeSlabController(getActivity());
                        freeSlabController.deleteAll();
                        for (int i = 0; i < freeslabJSONArray.length(); i++) {
                            freeslabList.add(FreeSlab.parseFreeSlab(freeslabJSONArray.getJSONObject(i)));
                        }
                        freeSlabController.createOrUpdateFreeSlab(freeslabList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    /*****************end freeslab**********************************************************************/

                    return true;
                } else {
                    //errors.add("Please enter correct username and password");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();

                return false;
            } catch (JSONException e) {
                e.printStackTrace();

                return false;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);

            pdialog.setMessage("Finalizing free data");
            pdialog.setMessage("Download Completed..");
            if (result) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            } else {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            }
        }
    }

    //route download asynctask
    private class routeDownload extends AsyncTask<String, Integer, Boolean> {
        CustomProgressDialog pdialog;
        private String repcode;

        public routeDownload(String repCode) {
            this.repcode = repCode;
            this.pdialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new CustomProgressDialog(getActivity());
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Downloading routes...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            try {
                if (SharedPref.getInstance(getActivity()).getLoginUser() != null && SharedPref.getInstance(getActivity()).isLoggedIn()) {

                    /*****************route*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading route data...");
                        }
                    });

                    String route = "";
                    try {
                        route = networkFunctions.getRoutes(repcode);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (routes)...");
                        }
                    });

                    // Processing route
                    try {
                        JSONObject routeJSON = new JSONObject(route);
                        JSONArray routeJSONArray = routeJSON.getJSONArray("fRouteResult");
                        ArrayList<Route> routeList = new ArrayList<Route>();
                        RouteController routeController = new RouteController(getActivity());
                        routeController.deleteAll();
                        for (int i = 0; i < routeJSONArray.length(); i++) {
                            routeList.add(Route.parseRoute(routeJSONArray.getJSONObject(i)));
                        }
                        routeController.createOrUpdateFRoute(routeList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    /*****************end route**********************************************************************/
                    /*****************Route det**********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Last invoices downloaded\nDownloading route details...");
                        }
                    });

                    String routedet = "";
                    try {
                        routedet = networkFunctions.getRouteDets(repcode);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (routes)...");
                        }
                    });

                    // Processing route
                    try {
                        JSONObject routeJSON = new JSONObject(routedet);
                        JSONArray routeJSONArray = routeJSON.getJSONArray("fRouteDetResult");
                        ArrayList<RouteDet> routeList = new ArrayList<RouteDet>();
                        RouteDetController routeController = new RouteDetController(getActivity());
                        routeController.deleteAll();
                        for (int i = 0; i < routeJSONArray.length(); i++) {
                            routeList.add(RouteDet.parseRoute(routeJSONArray.getJSONObject(i)));
                        }
                        routeController.InsertOrReplaceRouteDet(routeList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    /*****************end route det**********************************************************************/

                    /*****************Itenary hed - kaveesha - 10-06-2020  *********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("ItenaryHed\nDownloading route details...");
                        }
                    });

                    String itenaryHed = "";
                    try {
                        itenaryHed = networkFunctions.getItenaryHed(repcode);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (ItenaryHed)...");
                        }
                    });

                    // Processing itenaryHed
                    try {
                        JSONObject itenaryHedJSON = new JSONObject(itenaryHed);
                        JSONArray itenaryHedJSONJSONArray = itenaryHedJSON.getJSONArray("fItenrHedResult");
                        ArrayList<FItenrHed> itenaryHedList = new ArrayList<FItenrHed>();
                        FItenrHedController fItenrHedController = new FItenrHedController(getActivity());
                        fItenrHedController.deleteAll();
                        for (int i = 0; i < itenaryHedJSONJSONArray.length(); i++) {
                            itenaryHedList.add(FItenrHed.parseIteanaryHed(itenaryHedJSONJSONArray.getJSONObject(i)));
                        }
                        fItenrHedController.createOrUpdateFItenrHed(itenaryHedList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }

                    /*****************end itenaryHed**********************************************************************/

                    /*****************Itenary det -  kaveesha - 10-06-2020 ********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("ItenaryDet\nDownloading route details...");
                        }
                    });

                    String itenaryDet = "";
                    try {
                        itenaryDet = networkFunctions.getItenaryDet(repcode);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (ItenaryDet)...");
                        }
                    });

                    // Processing itenaryDet
                    try {
                        JSONObject itenaryDetJSON = new JSONObject(itenaryDet);
                        JSONArray itenaryDetJSONJSONArray = itenaryDetJSON.getJSONArray("fItenrDetResult");
                        ArrayList<FItenrDet> itenaryDetList = new ArrayList<FItenrDet>();
                        FItenrDetController fItenrDetController = new FItenrDetController(getActivity());
                        fItenrDetController.deleteAll();
                        for (int i = 0; i < itenaryDetJSONJSONArray.length(); i++) {
                            itenaryDetList.add(FItenrDet.parseIteanaryDet(itenaryDetJSONJSONArray.getJSONObject(i)));
                        }
                        fItenrDetController.createOrUpdateFItenrDet(itenaryDetList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }

                    /*****************end itenaryDet*********************************************************************/


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Download complete...");
                        }
                    });
                    return true;
                } else {
                    //errors.add("Please enter correct username and password");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();

                return false;
            } catch (JSONException e) {
                e.printStackTrace();

                return false;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);

            pdialog.setMessage("Finalizing item data");
            pdialog.setMessage("Download Completed..");
            if (result) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            } else {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            }
        }
    }

    //outstanding download asynctask
    private class outstandingDownload extends AsyncTask<String, Integer, Boolean> {
        CustomProgressDialog pdialog;
        private String repcode;

        public outstandingDownload(String repCode) {
            this.repcode = repCode;
            this.pdialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new CustomProgressDialog(getActivity());
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Downloading outstanding...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            try {
                if (SharedPref.getInstance(getActivity()).getLoginUser() != null && SharedPref.getInstance(getActivity()).isLoggedIn()) {

                    /*****************fDdbNoteWithCondition*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading outstanding data...");
                        }
                    });

                    /*****************fddbnote*****************************************************************************/

                    String fddbnote = "";
                    try {
                        fddbnote = networkFunctions.getFddbNotes(repcode);
                        // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (outstanding details)...");
                        }
                    });

                    // Processing fddbnote
                    try {
                        JSONObject fddbnoteJSON = new JSONObject(fddbnote);
                        JSONArray fddbnoteJSONArray = fddbnoteJSON.getJSONArray("fDdbNoteWithConditionResult");
                        ArrayList<FddbNote> fddbnoteList = new ArrayList<FddbNote>();
                        OutstandingController outstandingController = new OutstandingController(getActivity());
                        outstandingController.deleteAll();
                        for (int i = 0; i < fddbnoteJSONArray.length(); i++) {
                            fddbnoteList.add(FddbNote.parseFddbnote(fddbnoteJSONArray.getJSONObject(i)));
                        }
                        outstandingController.createOrUpdateFDDbNote(fddbnoteList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Download complete...");
                        }
                    });
                    return true;
                } else {
                    //errors.add("Please enter correct username and password");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();

                return false;
            } catch (JSONException e) {
                e.printStackTrace();

                return false;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);

            pdialog.setMessage("Finalizing item data");
            pdialog.setMessage("Download Completed..");
            if (result) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            } else {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            }
        }
    }

    //stock download - kaveesha - 10-06-2020
    private class StockDownload extends AsyncTask<String, Integer, Boolean> {

        CustomProgressDialog pdialog;
        private String repcode;

        public StockDownload(String repcode) {
            this.repcode = repcode;
            this.pdialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new CustomProgressDialog(getActivity());
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Downloading Stock..");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                if (SharedPref.getInstance(getActivity()).getLoginUser() != null && SharedPref.getInstance(getActivity()).isLoggedIn()) {

                    /*****************Stock*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading Stock data...");
                        }
                    });

                    String stock = "";
                    try {
                        stock = networkFunctions.getStock(repcode);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (stock)...");
                        }
                    });

                    // Processing stock
                    try {
                        JSONObject stockJSON = new JSONObject(stock);
                        JSONArray stockJSONArray = stockJSON.getJSONArray("fItemLocResult");
                        ArrayList<ItemLoc> stockList = new ArrayList<ItemLoc>();
                        ItemLocController itemLocController = new ItemLocController(getActivity());
                        itemLocController.deleteAll();
                        for (int i = 0; i < stockJSONArray.length(); i++) {
                            stockList.add(ItemLoc.parseItemLocs(stockJSONArray.getJSONObject(i)));
                        }
                        itemLocController.InsertOrReplaceItemLoc(stockList);

                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }

                    /*****************Van Stock  kaveesha - 13-06-2020 *****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading Van Stock data...");
                        }
                    });

                    String vanstock = "";
                    try {
                        vanstock = networkFunctions.getVanStock(repcode);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (Van stock)...");
                        }
                    });

                    // Processing vanstock
                    try {
                        JSONObject vanstockJSON = new JSONObject(vanstock);
                        JSONArray vanstockJSONArray = vanstockJSON.getJSONArray("VanStockResult");
                        ArrayList<VanStock> vanstockList = new ArrayList<VanStock>();
                        VanStockController vanStockController = new VanStockController(getActivity());
                        vanStockController.deleteAll();
                        for (int i = 0; i < vanstockJSONArray.length(); i++) {
                            vanstockList.add(VanStock.parseVanStock(vanstockJSONArray.getJSONObject(i)));
                        }
                        vanStockController.InsertOrReplaceVanStock(vanstockList);

                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Download complete...");
                        }
                    });
                    return true;
                } else {
                    //errors.add("Please enter correct username and password");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();

                return false;
            } catch (JSONException e) {
                e.printStackTrace();

                return false;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            pdialog.setMessage("Finalizing Stock data");
            pdialog.setMessage("Download Completed..");

            if (result) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }
            } else {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }
            }
        }
    }

    //Customer download asynctask -- kaveesha -  13-06-2020
    private class CustomersDownload extends AsyncTask<String, Integer, Boolean> {
        CustomProgressDialog pdialog;
        private String repcode;

        public CustomersDownload(String repcode) {
            this.repcode = repcode;
            this.pdialog = new CustomProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new CustomProgressDialog(getActivity());
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Downloading Customers...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            try {
                if (SharedPref.getInstance(getActivity()).getLoginUser() != null && SharedPref.getInstance(getActivity()).isLoggedIn()) {

                    /*****************Customers ****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading Customers...");
                        }
                    });

                    String customers = "";
                    try {
                        customers = networkFunctions.getCustomer(repcode);
                        // Log.d(LOG_TAG, "OUTLETS :: " + outlets);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }


                    try {
                        JSONObject customerJSON = new JSONObject(customers);
                        JSONArray customerJSONJSONArray = customerJSON.getJSONArray("FdebtorResult");
                        ArrayList<Debtor> customerList = new ArrayList<Debtor>();
                        CustomerController customerController = new CustomerController(getActivity());
                        customerController.deleteAll();
                        for (int i = 0; i < customerJSONJSONArray.length(); i++) {
                            customerList.add(Debtor.parseOutlet(customerJSONJSONArray.getJSONObject(i)));
                        }
                        customerController.InsertOrReplaceDebtor(customerList);
                    } catch (JSONException | NumberFormatException e) {
                        // Log.d(">>>", "error in fragment :" + e.toString());

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    /*****************end customers**********************************************************************/

                    /*****************Itenary debDet- kaveesha - 10-06-2020 ********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("ItenaryDebDet\nDownloading route details...");
                        }
                    });

                    String itenaryDeb = "";
                    try {
                        itenaryDeb = networkFunctions.getItenaryDebDet(repcode);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Processing downloaded data (ItenaryDeb)...");
                        }
                    });

                    // Processing itenaryDeb
                    try {
                        JSONObject itenaryDebJSON = new JSONObject(itenaryDeb);
                        JSONArray itenaryDebJSONJSONArray = itenaryDebJSON.getJSONArray("fIteDebDetResult");
                        ArrayList<ItenrDeb> itenaryDebList = new ArrayList<ItenrDeb>();
                        IteaneryDebController iteaneryDebController = new IteaneryDebController(getActivity());
                        iteaneryDebController.deleteAll();
                        for (int i = 0; i < itenaryDebJSONJSONArray.length(); i++) {
                            itenaryDebList.add(ItenrDeb.parseIteDebDet(itenaryDebJSONJSONArray.getJSONObject(i)));
                        }
                        iteaneryDebController.InsertOrReplaceItenrDeb(itenaryDebList);
                    } catch (JSONException | NumberFormatException e) {

//                        ErrorUtil.logException("LoginActivity -> Authenticate -> doInBackground() # Process Routes and Outlets",
//                                e, routes, BugReport.SEVERITY_HIGH);

                        throw e;
                    }
                    /*****************end itenaryDeb*********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Download complete...");
                        }
                    });
                    return true;
                } else {
                    //errors.add("Please enter correct username and password");
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();

                return false;
            } catch (JSONException e) {
                e.printStackTrace();

                return false;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);

            pdialog.setMessage("Finalizing Sales Price data");
            pdialog.setMessage("Download Completed..");
            if (result) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            } else {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }

            }
        }
    }

    //other download - 11-06-2020
    private class OtherDownload extends AsyncTask<String, Integer, Boolean> {

        private String repcode;
        CustomProgressDialog pdialog;
        private Handler mHandler;
        private List<String> errors = new ArrayList<>();

        public OtherDownload(String repcode) {
            this.repcode = repcode;
            this.pdialog = new CustomProgressDialog(getActivity());
            mHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new CustomProgressDialog(getActivity());
            mHandler = new Handler(Looper.getMainLooper());
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Authenticating...");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            apiInterface = ApiCllient.getClient(getActivity()).create(ApiInterface.class);
            mHandler = new Handler(Looper.getMainLooper());

            try {
                if (SharedPref.getInstance(getActivity()).getLoginUser() != null && SharedPref.getInstance(getActivity()).isLoggedIn()) {

                    /*****************company details**********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading data(Company details)...");
                        }
                    });

                    // Processing controls
                    try {
                        UtilityContainer.download(getActivity(), TaskType.Controllist, networkFunctions.getCompanyDetails(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************outlets**********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading Customers...");
                        }
                    });

                    // Processing outlets
                    try {
                        UtilityContainer.download(getActivity(), TaskType.Customers, networkFunctions.getCustomer(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************Settings*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading Settings...");
                        }
                    });

                    // Processing company settings
                    try {
                        UtilityContainer.download(getActivity(), TaskType.Settings, networkFunctions.getReferenceSettings());
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************Branches*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading data (reference details)...");
                        }
                    });

                    // Processing Branches

                    try {
                        UtilityContainer.download(getActivity(), TaskType.Reference, networkFunctions.getReferences(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************VAT*****************************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading data (VAT details)...");
                        }
                    });
                    // Processing VAT

                    try {
                        UtilityContainer.download(getActivity(), TaskType.VAT, networkFunctions.getVAT());
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    // ************************reasons**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading data (reasons)...");
                        }
                    });
                    // Processing reasons

                    try {
                        UtilityContainer.download(getActivity(), TaskType.Reason, networkFunctions.getReasons());
                    } catch (IOException e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************banks**********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading banks...");
                        }
                    });

                    // Processing banks
                    try {
                        UtilityContainer.download(getActivity(), TaskType.Bank, networkFunctions.getBanks());
                    } catch (IOException e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    /*****************expense**********************************************************************/

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading data (expenses)...");
                        }
                    });

                    // Processing expense
                    try {
                        UtilityContainer.download(getActivity(), TaskType.Expense, networkFunctions.getExpenses());
                    } catch (IOException e) {
                        errors.add(e.toString());
                        e.printStackTrace();
                        throw e;
                    }

                    /*****************discount**********************************************************************/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Downloading discount....");
                        }
                    });

                    // Processing discount
                    try {
                        UtilityContainer.download(getActivity(), TaskType.Discount, networkFunctions.getDiscounts(repcode));
                    } catch (Exception e) {
                        errors.add(e.toString());
                        throw e;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pdialog.setMessage("Completed...");
                        }
                    });

                    /*****************end sync**********************************************************************/

                    return true;
                } else {
                    errors.add("SharedPref.getInstance(getActivity()).getLoginUser() = null OR !SharedPref.getInstance(getActivity()).isLoggedIn()");
                    Log.d("ERROR>>>>>", "Login USer" + SharedPref.getInstance(getActivity()).getLoginUser().toString() + " IS LoggedIn --> " + SharedPref.getInstance(getActivity()).isLoggedIn());
                    return false;
                }
            } catch (Exception e) {

                e.printStackTrace();
                errors.add(e.toString());

                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            pdialog.setMessage("Finalizing data");
            pdialog.setMessage("Download Completed..");

            if (result) {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }
            } else {
                if (pdialog.isShowing()) {
                    pdialog.dismiss();
                }
            }
        }
    }
}
