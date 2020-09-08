package com.datamation.hmdsfa.barcode.order;


import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.BundleAdapter;
import com.datamation.hmdsfa.adapter.OrderDetailsAdapter;
import com.datamation.hmdsfa.adapter.OrderFreeItemAdapter;
import com.datamation.hmdsfa.adapter.VarientItemsAdapter;
import com.datamation.hmdsfa.controller.BarcodeVarientController;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.DiscountController;
import com.datamation.hmdsfa.controller.ItemBundleController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.ItemLocController;
import com.datamation.hmdsfa.controller.OrderController;
import com.datamation.hmdsfa.controller.OrderDetailController;
import com.datamation.hmdsfa.controller.PreProductController;
import com.datamation.hmdsfa.controller.ProductController;
import com.datamation.hmdsfa.controller.VATController;
import com.datamation.hmdsfa.controller.VanStockController;
import com.datamation.hmdsfa.dialog.CustomProgressDialog;
import com.datamation.hmdsfa.helpers.BluetoothConnectionHelper;
import com.datamation.hmdsfa.helpers.PreSalesResponseListener;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.ItemBundle;
import com.datamation.hmdsfa.model.Order;
import com.datamation.hmdsfa.model.OrderDetail;
import com.datamation.hmdsfa.model.PreProduct;
import com.datamation.hmdsfa.model.Product;
import com.datamation.hmdsfa.settings.ReferenceNum;
import com.datamation.hmdsfa.view.PreSalesActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class BROrderDetailFragment extends Fragment{
    int totPieces = 0;
    int seqno = 0;
    ListView lv_order_det, lvFree;
    ArrayList<PreProduct> productList = null, selectedItemList = null, selectedItem = null, selectedVarientItems = null;;
    Spinner spnScanType;
    SweetAlertDialog pDialog;
    private static final String TAG = "OrderDetailFragment";
    public View view;
    public SharedPref mSharedPref;
    private  String RefNo;
    private  MyReceiver r;
    private Order tmpsoHed=null;  //from re oder creation
    PreSalesResponseListener preSalesResponseListener;
    int clickCount = 0;
    private double totAmt = 0.0;
    private Customer debtor;
    PreSalesActivity mainActivity;
    ArrayList<OrderDetail> orderList;
    ArrayList<OrderDetail> exOrderDet;
    ArrayList<ItemBundle> itemArrayList = null;
    EditText etSearchField;
    SearchableSpinner textSearchField;
    ThreadConnectBTdevice threadConnectBTdevice;
    FloatingActionButton btnDiscount;
    public BROrderDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_bar_code_reader, container, false);
        ///****************initializations @rashmi*****************************************************//
        seqno = 0;
        totPieces = 0;
        mSharedPref =SharedPref.getInstance(getActivity());
        lv_order_det = (ListView) view.findViewById(R.id.lvProducts_Inv);
        lvFree = (ListView) view.findViewById(R.id.lvFreeIssue_Inv);
        mainActivity = (PreSalesActivity)getActivity();
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.NumVal));
        etSearchField = (EditText) view.findViewById(R.id.etSearchField);
        textSearchField = (SearchableSpinner) view.findViewById(R.id.txtSearchField);
        spnScanType = (Spinner) view.findViewById(R.id.spnScan);
        btnDiscount = (FloatingActionButton)  view.findViewById(R.id.btn_discount);
        itemArrayList = new ArrayList<>();
        tmpsoHed = new Order();
        etSearchField.setFocusable(true);
        setHasOptionsMenu(true);
        ///*****************************@rashmi******************************************************//
        showData();//@rashmi - show data when oncreate
        ArrayList<String> strList = new ArrayList<String>();//@rashmi - arraylist for transaction selection spinner
        strList.add("ITEM WISE");
        strList.add("BUNDLE WISE");
        strList.add("SEARCH BY ARTICLE NO");
        textSearchField.setTitle("SEARCH BY ARTICLE NO");
        final ArrayAdapter<String> txnTypeAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.return_spinner_item, strList);
        txnTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnScanType.setAdapter(txnTypeAdapter);
        //@rashmi - first time spinner selection layout visibility handle
        if(spnScanType.getSelectedItemPosition() == 0) {
            etSearchField.setVisibility(View.VISIBLE);
            textSearchField.setVisibility(View.GONE);

        }else if(spnScanType.getSelectedItemPosition() == 1){
            etSearchField.setVisibility(View.VISIBLE);
            textSearchField.setVisibility(View.GONE);
        }else{
            etSearchField.setVisibility(View.GONE);
            textSearchField.setVisibility(View.VISIBLE);
        }
        //@rashmi - when change spinner selection layout visibility handle
        spnScanType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnScanType.getSelectedItemPosition() == 0) {
                    etSearchField.setVisibility(View.VISIBLE);
                    textSearchField.setVisibility(View.GONE);

                }else if(spnScanType.getSelectedItemPosition() == 1){
                    etSearchField.setVisibility(View.VISIBLE);
                    textSearchField.setVisibility(View.GONE);
                }else{
                    etSearchField.setVisibility(View.GONE);
                    textSearchField.setVisibility(View.VISIBLE);
                    ArrayList<String> itemBundle = new BarcodeVarientController(getActivity()).getItems();
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, itemBundle);
                    itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    textSearchField.setAdapter(itemAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //////////////@rashmi search by article no********************************************//////////

        textSearchField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<ItemBundle> itemBundle = new BarcodeVarientController(getActivity())
                        .getItemsInBundle(textSearchField.getSelectedItem().toString().split("-")[0].trim());
                Log.v("ENTERED CODE", "itemcode " + etSearchField.getText().toString());
                if (itemBundle.size() == 1) {
                    selectedItem = new PreProductController(getActivity()).getScannedtems(itemBundle.get(0));
                    double qoh = Double.parseDouble(new ItemLocController(getActivity()).getQOH(selectedItem.get(0).getPREPRODUCT_Barcode()));
                if (qoh >= Double.parseDouble(selectedItem.get(0).getPREPRODUCT_QTY())) {
                    //rashmi-2020-08-21
                    updateOrderDet(selectedItem);
                } else {
                    Toast.makeText(getActivity(), "Not enough stock", Toast.LENGTH_LONG).show();
                }
                    showData();
                }else{
                    Toast.makeText(getActivity(),"No matching item or duplicate items",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ///*****************************@rashmi******************************************************//

            etSearchField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {//@rashmi-itemwise or bundle wise scan via edittext
                    clickCount = 0;
                    mSharedPref.setDiscountClicked("0");
                    if (spnScanType.getSelectedItemPosition() == 0) {
//                        ArrayList<ItemBundle> itemBundle = new BarcodeVarientController(getActivity())
//                                .getItemsInBundle(etSearchField.getText().toString());
//                        Log.v("ENTERED CODE", "itemcode " + etSearchField.getText().toString());
//                        if (itemBundle.size() == 1) {
//                            selectedItem = new PreProductController(getActivity()).getScannedtems(itemBundle.get(0));
//                            //rashmi-2020-08-21
//                            double qoh = Double.parseDouble(new ItemLocController(getActivity()).getQOH(selectedItem.get(0).getPREPRODUCT_Barcode()));
//                            if (qoh >= Double.parseDouble(selectedItem.get(0).getPREPRODUCT_QTY())) {
//                                updateOrderDet(selectedItem);
//                            } else {
//                                Toast.makeText(getActivity(), "Not enough stock", Toast.LENGTH_LONG).show();
//                            }
//                            showData();
//                        } else {
//                            Toast.makeText(getActivity(), "No matching item", Toast.LENGTH_LONG).show();
//                        }
//                        etSearchField.setText("");
//                        etSearchField.setFocusable(true);
                        ArrayList<ItemBundle> itemBundle = new BarcodeVarientController(getActivity())
                                .getItemsInBundle(etSearchField.getText().toString());
                        Log.v("ENTERED CODE", "itemcode " + etSearchField.getText().toString());
                        // for (Item item: aList ) {
                        // Log.v("code :", ">> " + item.getItemNo());
                        if(itemBundle.size()==1) {
                            //when deduct qoh update qoh also
                            // new ProductController(getActivity()).updateBarCode(itemBundle.get(0).getBarcode(),"1");
                            if(itemBundle.size()>0) {
                                if (new PreProductController(getActivity()).tableHasRecords()) {
                                    //productList = new ProductDS(getActivity()).getAllItems("");
                                    productList = new PreProductController(getActivity()).getAllItems();//rashmi 20200907
                                } else {
                                    selectedVarientItems = new PreProductController(getActivity()).getVarientItems(itemBundle.get(0).getBarcode());

                                    new PreProductController(getActivity()).createOrUpdateProducts(selectedVarientItems);
                                }
                                productList = new PreProductController(getActivity()).getAllItems();//rashmi 20200907
                                VarientItemsDialogBox(productList);
                            }else{
                                Toast.makeText(getActivity(),"No matching Item",Toast.LENGTH_LONG).show();
                            }
                            showData();

                        }else{
                            Toast.makeText(getActivity(),"No matching item",Toast.LENGTH_LONG).show();
                        }
                        etSearchField.setText("");
                        etSearchField.setFocusable(true);
                    } else {
                        ArrayList<ItemBundle> itemBundle = new ItemBundleController(getActivity())
                                .getItemsInBundle(etSearchField.getText().toString());
                        if (itemBundle.size() > 0) {
                            BundleItemsDialogBox(itemBundle);
                        } else {
                            Toast.makeText(getActivity(), "No matching bundle", Toast.LENGTH_LONG).show();
                        }
                        etSearchField.setText("");
                        etSearchField.setFocusable(true);
                    }
                    return false;
                }
            });

            etSearchField.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER)
                            || keyCode == KeyEvent.KEYCODE_TAB) {
                        // handleInputScan();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (etSearchField != null) {
                                    etSearchField.requestFocus();
                                }
                            }
                        }, 10); // Remove this Delay Handler IF requestFocus(); works just fine without delay
                        return true;
                    }
                    return false;
                }
            });

//            textSearchField.setOnClickListener(new View.OnClickListener() {//@rashmi-item select by searching via article no
//
//                @Override
//                    public void onClick(View v) {
//
//                    }
//            });

        btnDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSharedPref.setDiscountClicked("1");
                if(clickCount == 0) {

                    if(new DiscountController(getActivity()).IsDiscountCustomer(mSharedPref.getSelectedDebCode())>0)
                    {
                        new CalculateDiscounts(mSharedPref.getSelectedDebCode()).execute();
                    }else{
                        UpdateTaxDetails(RefNo);
                        Toast.makeText(getActivity(),"Discount not allow for this customer",Toast.LENGTH_SHORT).show();
                    }
                    clickCount++;
                }else{
                    Toast.makeText(getActivity(),"Already clicked",Toast.LENGTH_LONG).show();
                    Log.v("Freeclick Count", mSharedPref.getGlobalVal("preKeyIsFreeClicked"));
                }
                showData();

            }
        });

        lv_order_det.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mSharedPref.setDiscountClicked("0");
                clickCount = 0;
                mSharedPref.setGlobalVal("preKeyIsFreeClicked", "0");
                new OrderDetailController(getActivity()).restFreeIssueData(RefNo);
                newDeleteOrderDialog(position);
                return true;
            }
        });
        if(!new BluetoothConnectionHelper(getActivity()).isSupportBluetooth()){
            Toast.makeText(getActivity(),"FEATURE_BLUETOOTH NOT SUPPORTED", Toast.LENGTH_LONG).show();
            connectionError();
        }

        if(!new BluetoothConnectionHelper(getActivity()).isBluetoothHardwareSupport()){
            Toast.makeText(getActivity(),"Bluetooth is not supported on this hardware platform", Toast.LENGTH_LONG).show();
            connectionError();
        }
        return view;
    }
    private boolean BundleItemsDialogBox(final ArrayList<ItemBundle> itemDetails) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.bundle_items_popup, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Items Inside Bundle");
        alertDialogBuilder.setView(promptView);
        final ListView listView = (ListView) promptView.findViewById(R.id.lv_free_issue);

        listView.setAdapter(new BundleAdapter(getActivity(), itemDetails));

        alertDialogBuilder.setCancelable(false).setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int count = 0;
                selectedItemList = new PreProductController(getActivity()).getBundleScannedtems(itemDetails);
                for (PreProduct product : selectedItemList) {

                    double qoh = Double.parseDouble(new ItemLocController(getActivity()).getQOH(product.getPREPRODUCT_Barcode()));
                    // Log.d("QOH>>>",">>>listsize"+list.size()+"count>>>"+qoh);
                    if(qoh >= Double.parseDouble(product.getPREPRODUCT_QTY())) {
                        count++;
                        //    Log.d("QOH>>>","insideqohvalidation>>>listsize"+list.size()+"count>>>"+count);
                    }
                    //  Log.d("QOH>>>","first prdct loop>>>listsize"+list.size()+"count>>>"+count);
                }
                // Log.d("QOH>>>","before scnd for loop listsize>>>"+list.size()+"count>>>"+count);
                if(count == selectedItemList.size()) {
                    //rashmi-2020-08-21
                    updateOrderDet(selectedItemList);
                }else{
                    Toast.makeText(getActivity(),"Not enough stock",Toast.LENGTH_LONG).show();
                }

                showData();

                dialog.cancel();//2020-03-10
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
        return true;
    }
    private boolean VarientItemsDialogBox(final ArrayList<PreProduct> itemDetails) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.bundle_items_popup, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Varient Items");
        alertDialogBuilder.setView(promptView);
        final ListView listView = (ListView) promptView.findViewById(R.id.lv_free_issue);

        listView.setAdapter(new VarientItemsAdapter(getActivity(), itemDetails));
        alertDialogBuilder.setCancelable(false).setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                selectedVarientItems = new PreProductController(getActivity()).getScannedVarientItems();

                updateOrderDet(selectedVarientItems);
                showData();

                dialog.cancel();//2020-03-10
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
        return true;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_barcode, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;

            case R.id.menu_item2: { // REFRESH BLUETOOTH CONNECTION
                try {
                    threadConnectBTdevice.cancel();
                }catch ( Exception e ){
                    // e.printStackTrace();
                }finally {
                    try {
                        threadConnectBTdevice = new ThreadConnectBTdevice(new BluetoothConnectionHelper(getActivity()).getDevice());
                        threadConnectBTdevice.start();
                    } catch (Exception e) {
                        // e.printStackTrace();
                    }


                }

            }

            return true;


        }
        return(super.onOptionsItemSelected(item));
    }

    public void mToggleTextbox()
    {
        if(new OrderController(getActivity()).IsSavedHeader(RefNo)>0) {
            if (!new BluetoothConnectionHelper(getActivity()).isSupportBluetooth()) {
                Toast.makeText(getActivity(), "FEATURE_BLUETOOTH NOT SUPPORTED", Toast.LENGTH_LONG).show();
                connectionError();
            }

            if (!new BluetoothConnectionHelper(getActivity()).isBluetoothHardwareSupport()) {
                Toast.makeText(getActivity(), "Bluetooth is not supported on this hardware platform", Toast.LENGTH_LONG).show();
                connectionError();
            }
            showData();
        }else{
            preSalesResponseListener.moveBackToCustomer_pre(0);
            Toast.makeText(getActivity(), "Cannot proceed,Please click arrow button to save header details...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        new BluetoothConnectionHelper(getActivity()).enableBluetooth(getActivity());
        try {
            //MAC ADDRESS BT AUTO CONNECT.
            threadConnectBTdevice = new ThreadConnectBTdevice(new BluetoothConnectionHelper(getActivity()).getDevice());
            threadConnectBTdevice.start();

        }catch ( Exception ex ){
            Toast.makeText(getActivity(),ex.toString(), Toast.LENGTH_LONG).show();
            connectionError();
        }
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
        if(threadConnectBTdevice!=null){
            threadConnectBTdevice.cancel();
        }
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_PRE_DETAILS"));
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            BROrderDetailFragment.this.mToggleTextbox();
        }
    }

    public void showData() {

        try
        {
            lv_order_det.setAdapter(null);
            orderList = new OrderDetailController(getActivity()).getAllOrderDetails(RefNo);
            lv_order_det.setAdapter(new OrderDetailsAdapter(getActivity(), orderList, mSharedPref.getSelectedDebCode()));//2019-07-07 till error free

            lvFree.setAdapter(null);
            ArrayList<OrderDetail> freeList=new OrderDetailController(getActivity()).getAllFreeIssue(RefNo);
            lvFree.setAdapter(new OrderFreeItemAdapter(getActivity(), freeList));

        } catch (NullPointerException e) {
            Log.v("SA Error", e.toString());
        }
    }



    public void updateOrderDet(final ArrayList<PreProduct> list) {
                int i = 0;
                RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.NumVal));
              //  new OrderDetailController(getActivity()).deleteRecords(RefNo);

                for (PreProduct product : list) {
                    i++;
                    mUpdatePrsSales(product.getPREPRODUCT_Barcode(), product.getPREPRODUCT_ITEMCODE(), product.getPREPRODUCT_QTY(), product.getPREPRODUCT_PRICE(), product.getPREPRODUCT_VariantCode(), product.getPREPRODUCT_QTY(), product.getPREPRODUCT_ArticleNo(), product.getPREPRODUCT_DocumentNo());
                }



    }

    public void newDeleteOrderDialog(final int position) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Confirm Deletion !");
        alertDialogBuilder.setMessage("Do you want to delete this item ?");
        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

              //  new PreProductController(getActivity()).updateProductQty(orderList.get(position).getFORDERDET_ITEMCODE(), "0");
                if(orderList.get(position).getFORDERDET_PRILCODE().equals("")) {
                    new OrderDetailController(getActivity()).mDeleteProduct(orderList.get(position).getFORDERDET_REFNO(), orderList.get(position).getFORDERDET_ITEMCODE(), orderList.get(position).getFORDERDET_BARCODE());
                }else{
                    new OrderDetailController(getActivity()).mDeleteBundle(orderList.get(position).getFORDERDET_REFNO(),  orderList.get(position).getFORDERDET_PRILCODE());
                }
                new OrderDetailController(getActivity()).mDeleteRecords(RefNo, orderList.get(position).getFORDERDET_ITEMCODE());
                Toast.makeText(getActivity(), "Deleted successfully!", Toast.LENGTH_SHORT).show();
                showData();

            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    public void mUpdatePrsSales(String barcode, String itemCode, String Qty, String price, String variantcode, String qoh, String aricleno, String documentNo)
    {
        //by rashmi 2020/07/08
        OrderDetail SODet = new OrderDetail();
        ArrayList<OrderDetail> SOList = new ArrayList<OrderDetail>();
        double unitprice = 0.0;
        // String taxamt = new VATController(getActivity()).calculateTax(mSharedPref.getGlobalVal("KeyVat"),new BigDecimal(amt));
        String taxRevValue = new VATController(getActivity()).calculateReverse(mSharedPref.getGlobalVal("KeyVat"),new BigDecimal(price));
        // unitprice = Double.parseDouble(price) - Double.parseDouble(taxRevValue);
        if(new CustomerController(getActivity()).getCustomerVatStatus(mSharedPref.getSelectedDebCode()).trim().equals("VAT")){
            unitprice = Double.parseDouble(price) - Double.parseDouble(taxRevValue);
            //BSell price get for tax forward, if customer vat, set b sell price reversing tax
            SODet.setFORDERDET_BSELLPRICE(String.format("%.2f", unitprice));
        }else if(new CustomerController(getActivity()).getCustomerVatStatus(mSharedPref.getSelectedDebCode()).trim().equals("NOVAT")){
            unitprice = Double.parseDouble(price);
            //if customer novat, pass unit price without reversing tax, but b sell price set reversing tax for use to forward tax
            SODet.setFORDERDET_BSELLPRICE(String.format("%.2f", (unitprice- Double.parseDouble(taxRevValue))));
        }else{
            Toast.makeText(getActivity(),"This customer doesn't have VAT status(VAT?/NOVAT?)",Toast.LENGTH_SHORT).show();
        }
        double amt = unitprice * Double.parseDouble(Qty);

        SODet.setFORDERDET_AMT(String.valueOf(amt));
        SODet.setFORDERDET_ITEMCODE(itemCode);
        SODet.setFORDERDET_PRILCODE(documentNo);
        SODet.setFORDERDET_QTY(Qty);
        SODet.setFORDERDET_REFNO(RefNo);
        SODet.setFORDERDET_PRICE("0.00");
        SODet.setFORDERDET_IS_ACTIVE("1");
        SODet.setFORDERDET_BALQTY(Qty);
        SODet.setFORDERDET_BAMT(String.valueOf(amt));
        SODet.setFORDERDET_BDISAMT("0");
        SODet.setFORDERDET_BPDISAMT("0");
        SODet.setFORDERDET_BTAXAMT("0");
        SODet.setFORDERDET_TAXAMT("0");
        SODet.setFORDERDET_DISAMT("0");
        SODet.setFORDERDET_SCHDISPER("0");
        //SODet.setFORDERDET_COMP_DISPER(new ControlDS(getActivity()).getCompanyDisc() + "");
        SODet.setFORDERDET_BRAND_DISPER("0");
        SODet.setFORDERDET_BRAND_DISC("0");
        SODet.setFORDERDET_COMP_DISC("0");
        SODet.setFORDERDET_COSTPRICE(new ItemController(getActivity()).getCostPriceItemCode(itemCode));
        SODet.setFORDERDET_PICE_QTY(Qty);
        SODet.setFORDERDET_SELLPRICE(String.format("%.2f", unitprice));
        SODet.setFORDERDET_SEQNO(new OrderDetailController(getActivity()).getLastSequnenceNo(RefNo));
        SODet.setFORDERDET_TAXCOMCODE(new ItemController(getActivity()).getTaxComCodeByItemCodeBeforeDebTax(itemCode, mSharedPref.getSelectedDebCode()));
        SODet.setFORDERDET_BTSELLPRICE(String.format("%.2f", unitprice));
        SODet.setFORDERDET_TSELLPRICE(String.format("%.2f", unitprice));
        SODet.setFORDERDET_TXNTYPE("21");
        SODet.setFORDERDET_LOCCODE("MS");
        SODet.setFORDERDET_TXNDATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        SODet.setFORDERDET_RECORDID("");
        SODet.setFORDERDET_PDISAMT("0");
        SODet.setFORDERDET_IS_SYNCED("0");
        SODet.setFORDERDET_QOH(qoh);
        SODet.setFORDERDET_TYPE("SA");
        SODet.setFORDERDET_SCHDISC("0");
        SODet.setFORDERDET_DISCTYPE("");
        SODet.setFORDERDET_QTY_SLAB_DISC("0");
        SODet.setFORDERDET_ORG_PRICE(price);
        SODet.setFORDERDET_DISFLAG("0");
        SODet.setFORDERDET_BARCODE(barcode);
        SODet.setFORDERDET_ARTICLENO(aricleno);
        SODet.setFORDERDET_VARIANTCODE(variantcode);
        SOList.add(SODet);

        if (new OrderDetailController(getActivity()).createOrUpdateOrdDet(SOList)>0)
        {
            Log.d("ORDER_DETAILS", "Order det saved successfully...");
        }
        else
        {
            Log.d("ORDER_DETAILS", "Order det saved unsuccess...");
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            preSalesResponseListener = (PreSalesResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
        new BluetoothConnectionHelper(getActivity()).enableBluetooth(getActivity());
        try {
            //MAC ADDRESS BT AUTO CONNECT.
            threadConnectBTdevice = new BROrderDetailFragment.ThreadConnectBTdevice(new BluetoothConnectionHelper(getActivity()).getDevice());
            threadConnectBTdevice.start();

        }catch ( Exception ex ){
            Toast.makeText(getActivity(),ex.toString(), Toast.LENGTH_LONG).show();
            connectionError();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(threadConnectBTdevice!=null){
            threadConnectBTdevice.cancel();
        }
    }

    private class ThreadConnectBTdevice extends Thread {

        private BluetoothSocket bluetoothSocket = null;
        private final BluetoothDevice bluetoothDevice;


        private ThreadConnectBTdevice(BluetoothDevice device) {
            bluetoothDevice = device;
            try {
                Method m = device.getClass().getMethod("createRfcommSocket", new Class[] { int.class });
                bluetoothSocket = (BluetoothSocket) m.invoke(device, 1);
            }

            catch (NoSuchMethodException e) {
                //e.printStackTrace();
            } catch (IllegalAccessException e) {
                //  e.printStackTrace();
            } catch (InvocationTargetException e) {
                // e.printStackTrace();
            }
        }

        @Override
        public void run() {
            boolean success = false;
            try {
                bluetoothSocket.connect();
                success = true;
            } catch (IOException e) {
                //e.printStackTrace();

//                final String eMessage = e.getMessage();
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
                connectionError();
//                    }
//                });

                try {
                    bluetoothSocket.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    //textStatus.setText("STATUS : CONNECTION ERROR!");
                    e1.printStackTrace();
                }

            }

            if(success){
                //connect successful
                final String msgconnected = "Connected.";
                Log.d("Bluetooth scanner",msgconnected);

            }else{
                //fail
                Log.e("Bltth scanr<<ERROR>>", "Failed to connect.");
//                getActivity().runOnUiThread(new Runnable(){
//                    @Override
//                    public void run() {
                connectionError();
                //     }});
                //connectionError();
            }
        }

        public void cancel() {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }

        }
    }

    private void connectionError(){
        //textStatus.setText("STATUS : BLUETOOTH CONNECTION ERROR!");
        Log.e("BLUETOOTH CONNECTION>>",">>>ERROR");
        // textStatus.setBackgroundResource(R.color.blue_c);
    }
    public class CalculateDiscounts extends AsyncTask<Object, Object, Boolean> {
        CustomProgressDialog pdialog;
        private String debcode;

        public CalculateDiscounts(String debcode) {
            this.pdialog = new CustomProgressDialog(getActivity());
            this.debcode = debcode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pdialog.setMessage("Calculating discounts.. Please Wait.");
            pdialog.show();
        }

        @Override
        protected Boolean doInBackground(Object... objects) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pdialog.setMessage("Calculating Discounts...");
                }
            });
            //getting current order
            ArrayList<OrderDetail> dets = new OrderDetailController(getActivity()).getSAForDiscountCalc(RefNo);
            DiscountController issue = new DiscountController(getActivity());
            //getting discount list by debcode
            ArrayList<OrderDetail> metaOrdList = issue.updateOrdDiscount(dets,debcode);
            Log.d("PRE_SALES_ORDER_DETAILS", "LIST_SIZE: " + dets.size());

            /* Iterate through for discounts for items */
            for(OrderDetail orderDetail : metaOrdList){

                new OrderDetailController(getActivity()).updateDiscount(orderDetail);
                String disper = orderDetail.getFORDERDET_SCHDISPER();

                String sArray[] = new VATController(getActivity()).calculateTaxForward( mSharedPref.getGlobalVal("KeyVat"), Double.parseDouble(orderDetail.getFORDERDET_BSELLPRICE()));
                String amt = String.format("%.2f",Double.parseDouble(sArray[0])* Double.parseDouble(orderDetail.getFORDERDET_QTY()));
                String tax = String.format("%.2f",Double.parseDouble(sArray[1])* Double.parseDouble(orderDetail.getFORDERDET_QTY()));
                String dis = String.format("%.2f",Double.parseDouble( orderDetail.getFORDERDET_DISAMT()));
                String barcode = orderDetail.getFORDERDET_BARCODE();
                new OrderDetailController(getActivity()).UpdateItemTaxInfo(tax,amt, RefNo,barcode,dis,disper);



            }


            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pdialog.setMessage("Calculed Discounts...");
                }
            });
            return true;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(pdialog.isShowing()){
                pdialog.dismiss();
            }

            showData();

        }
    }
    public void UpdateTaxDetails(String refNo) {
        ArrayList<OrderDetail> list = new OrderDetailController(getActivity()).getAllOrderDetails(refNo);
        new OrderDetailController(getActivity()).UpdateItemTax(list);
    }
}
