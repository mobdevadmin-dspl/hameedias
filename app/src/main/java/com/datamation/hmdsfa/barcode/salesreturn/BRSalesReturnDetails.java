package com.datamation.hmdsfa.barcode.salesreturn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.BundleAdapter;
import com.datamation.hmdsfa.adapter.ProductAdapter;
import com.datamation.hmdsfa.adapter.SalesReturnDetailsAdapter;
import com.datamation.hmdsfa.barcode.order.BROrderDetailFragment;
import com.datamation.hmdsfa.controller.BarcodeVarientController;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.ItemBundleController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.OrderController;
import com.datamation.hmdsfa.controller.ProductController;
import com.datamation.hmdsfa.controller.ReasonController;
import com.datamation.hmdsfa.controller.SalesReturnController;
import com.datamation.hmdsfa.controller.SalesReturnDetController;
import com.datamation.hmdsfa.controller.VATController;
import com.datamation.hmdsfa.dialog.CustomKeypadDialog;
import com.datamation.hmdsfa.helpers.BluetoothConnectionHelper;
import com.datamation.hmdsfa.helpers.PreSalesResponseListener;
import com.datamation.hmdsfa.helpers.SalesReturnResponseListener;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.FInvRDet;
import com.datamation.hmdsfa.model.FInvRHed;
import com.datamation.hmdsfa.model.Item;
import com.datamation.hmdsfa.model.ItemBundle;
import com.datamation.hmdsfa.model.Product;
import com.datamation.hmdsfa.settings.ReferenceNum;
import com.datamation.hmdsfa.view.SalesReturnActivity;
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

public class BRSalesReturnDetails extends Fragment{

    View view;
    ListView lv_return_det;
    ArrayList<FInvRDet> returnList;
    Spinner returnType;
    ArrayList<Item> list = null;
    SalesReturnActivity activity;
    SweetAlertDialog pDialog;
    String RefNo;
    SalesReturnResponseListener salesReturnResponseListener;
    MyReceiver r;
    Spinner spnScanType;
    SharedPref sharedPref;
    ArrayList<ItemBundle> itemArrayList = null;
    EditText etSearchField;
    SearchableSpinner textSearchField;
    ThreadConnectBTdevice threadConnectBTdevice;
    FloatingActionButton btnDiscount;
    ArrayList<Product>  selectedItemList = null,selectedItem = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_bar_code_reader_return, container, false);
        activity = (SalesReturnActivity)getActivity();

        sharedPref = SharedPref.getInstance(getActivity());
        etSearchField = (EditText) view.findViewById(R.id.etSearchField);
        textSearchField = (SearchableSpinner) view.findViewById(R.id.txtSearchField);
        spnScanType = (Spinner) view.findViewById(R.id.spnScan);
        btnDiscount = (FloatingActionButton)  view.findViewById(R.id.btn_discount);
        returnType = (Spinner) view.findViewById(R.id.spinner_return_Type);
        lv_return_det = (ListView) view.findViewById(R.id.lvProducts_Inv);
        itemArrayList = new ArrayList<>();
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));

        if (activity.selectedReturnHed == null)
        {
            activity.selectedReturnHed = new SalesReturnController(getActivity()).getActiveReturnHed(RefNo);
        }

        ArrayList<String> strList = new ArrayList<String>();
        strList.add("Select Return type to continue ...");
        strList.add("MR");
        strList.add("UR");

        final ArrayAdapter<String> returnTypeAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.return_spinner_item, strList);
        returnTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        returnType.setAdapter(returnTypeAdapter);

        FetchData();
        ArrayList<String> searchtypeList = new ArrayList<String>();//@rashmi - arraylist for transaction selection spinner
        searchtypeList.add("ITEM WISE");
        searchtypeList.add("BUNDLE WISE");
        searchtypeList.add("SEARCH BY ARTICLE NO");
        textSearchField.setTitle("SEARCH BY ARTICLE NO");
        final ArrayAdapter<String> txnTypeAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.return_spinner_item, searchtypeList);
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
                if(itemBundle.size()==1) {
                    selectedItem = new ProductController(getActivity()).getScannedtems(itemBundle.get(0));
                    updateReturnDet(selectedItem);
                    FetchData();
                }else{
                    Toast.makeText(getActivity(),"No matching item or duplicate items",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        if(new SalesReturnController(getActivity()).IsSavedHeader(RefNo)>0){

            etSearchField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {//@rashmi-itemwise or bundle wise scan via edittext

                    if(spnScanType.getSelectedItemPosition() == 0) {
                        ArrayList<ItemBundle> itemBundle = new BarcodeVarientController(getActivity())
                                .getItemsInBundle(etSearchField.getText().toString());
                        Log.v("ENTERED CODE", "itemcode " + etSearchField.getText().toString());
                        if(itemBundle.size()==1) {
                            selectedItem = new ProductController(getActivity()).getScannedtems(itemBundle.get(0));
                            updateReturnDet(selectedItem);
                            FetchData();
                        }else{
                            Toast.makeText(getActivity(),"No matching item",Toast.LENGTH_LONG).show();
                        }
                        etSearchField.setText("");
                        etSearchField.setFocusable(true);
                    }else{
                        ArrayList<ItemBundle> itemBundle = new ItemBundleController(getActivity())
                                .getItemsInBundle(etSearchField.getText().toString());
                        if(itemBundle.size()>0) {
                            BundleItemsDialogBox(itemBundle);
                        }else{
                            Toast.makeText(getActivity(),"No matching bundle",Toast.LENGTH_LONG).show();
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

//        }else{
//            salesReturnResponseListener.moveBackTo_ret(0);
//            Toast.makeText(getActivity(), "Cannot proceed,Please click arrow button to save header details...", Toast.LENGTH_LONG).show();
//        }
        /*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/


        lv_return_det.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteReturnDialog(position);
                return true;
            }
        });

        /*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lv_return_det.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {

            }
        });

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
                selectedItemList = new ProductController(getActivity()).getBundleScannedtems(itemDetails);

                updateReturnDet(selectedItemList);
                FetchData();

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
    private void deleteReturnDialog(final int position) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Are you sure you want to delete this entry?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setTitle("Return Details");
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                int count = new SalesReturnDetController(getActivity()).mDeleteRetDet(returnList.get(position).getFINVRDET_ITEMCODE(),returnList.get(position).getFINVRDET_REFNO());

                if (count > 0)
                {
                    Toast.makeText(getActivity(), "Deleted successfully", Toast.LENGTH_LONG).show();
                    FetchData();
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }



    public void FetchData()
    {
        if (new SalesReturnController(getActivity()).getDirectSalesReturnRefNo().equals("") || new SalesReturnController(getActivity()).getDirectSalesReturnRefNo().equals(""))
        {
            RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        }
        else
        {
            RefNo = new SalesReturnController(getActivity()).getDirectSalesReturnRefNo();
        }

        Log.d("SALES_RETRUN", "DETAILS_FROM_FETCH_DATA: " + RefNo);
        try {
            lv_return_det.setAdapter(null);
            returnList = new SalesReturnDetController(getActivity()).getAllInvRDetForSalesReturn(RefNo);
            lv_return_det.setAdapter(new SalesReturnDetailsAdapter(getActivity(), returnList));

        } catch (NullPointerException e) {
            Log.v(" Error", e.toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        new BluetoothConnectionHelper(getActivity()).enableBluetooth(getActivity());
        try {
            //MAC ADDRESS BT AUTO CONNECT.
            threadConnectBTdevice = new ThreadConnectBTdevice(new BluetoothConnectionHelper(getActivity()).getDevice());
            threadConnectBTdevice.start();

        }catch ( Exception ex ){
            Toast.makeText(getActivity(),ex.toString(), Toast.LENGTH_LONG).show();
            connectionError();
        }
        try {
            salesReturnResponseListener = (SalesReturnResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            BRSalesReturnDetails.this.mRefreshData();
        }
    }
    public void updateReturnDet(final ArrayList<Product> list) {
        int i = 0;
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        //  new OrderDetailController(getActivity()).deleteRecords(RefNo);

        for (Product product : list) {
            i++;
            mUpdatereturns(product.getFPRODUCT_Barcode(), product.getFPRODUCT_ITEMCODE(), product.getFPRODUCT_QTY(), product.getFPRODUCT_Price(), product.getFPRODUCT_VariantCode(), product.getFPRODUCT_QTY(), product.getFPRODUCT_ArticleNo(), product.getFPRODUCT_DocumentNo());
        }



    }
    public void mUpdatereturns(String barcode, String itemCode, String Qty, String price, String variantcode, String qoh, String aricleno, String documentNo)
    {
        //by rashmi 2020/07/08
        FInvRDet ReturnDet = new FInvRDet();
        ArrayList<FInvRDet> SOList = new ArrayList<FInvRDet>();
        double unitprice = 0.0;
        // String taxamt = new VATController(getActivity()).calculateTax(mSharedPref.getGlobalVal("KeyVat"),new BigDecimal(amt));
        String taxRevValue = new VATController(getActivity()).calculateReverse(sharedPref.getGlobalVal("KeyVat"),new BigDecimal(price));
        // unitprice = Double.parseDouble(price) - Double.parseDouble(taxRevValue);
        if(new CustomerController(getActivity()).getCustomerVatStatus(sharedPref.getSelectedDebCode()).trim().equals("VAT")){
            unitprice = Double.parseDouble(price) - Double.parseDouble(taxRevValue);
            //BSell price get for tax forward, if customer vat, set b sell price reversing tax
            ReturnDet.setFINVRDET_SELL_PRICE(String.format("%.2f", unitprice));
        }else if(new CustomerController(getActivity()).getCustomerVatStatus(sharedPref.getSelectedDebCode()).trim().equals("NOVAT")){
            unitprice = Double.parseDouble(price);
            //if customer novat, pass unit price without reversing tax, but b sell price set reversing tax for use to forward tax
            ReturnDet.setFINVRDET_SELL_PRICE(String.format("%.2f", (unitprice- Double.parseDouble(taxRevValue))));
        }else{
            Toast.makeText(getActivity(),"This customer doesn't have VAT status(VAT?/NOVAT?)",Toast.LENGTH_SHORT).show();
        }
        double amt = unitprice * Double.parseDouble(Qty);

        ReturnDet.setFINVRDET_AMT(String.valueOf(amt));
        ReturnDet.setFINVRDET_ITEMCODE(itemCode);
        ReturnDet.setFINVRDET_PRILCODE(documentNo);
        ReturnDet.setFINVRDET_QTY(Qty);
        ReturnDet.setFINVRDET_REFNO(RefNo);
        ReturnDet.setFINVRDET_IS_ACTIVE("1");
        ReturnDet.setFINVRDET_BAL_QTY(Qty);

        ReturnDet.setFINVRDET_COST_PRICE(new ItemController(getActivity()).getCostPriceItemCode(itemCode));
        ReturnDet.setFINVRDET_SELL_PRICE(String.format("%.2f", unitprice));
        ReturnDet.setFINVRDET_SEQNO("1");
        ReturnDet.setFINVRDET_TAXCOMCODE(new ItemController(getActivity()).getTaxComCodeByItemCodeBeforeDebTax(itemCode, sharedPref.getSelectedDebCode()));
        ReturnDet.setFINVRDET_T_SELL_PRICE(String.format("%.2f", unitprice));
        ReturnDet.setFINVRDET_TXN_TYPE("21");
        ReturnDet.setFINVRDET_TXN_DATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        ReturnDet.setFINVRDET_RETURN_REASON(new ReasonController(getActivity()).getReaNameByCode(activity.selectedReturnHed.getFINVRHED_REASON_CODE()));
        ReturnDet.setFINVRDET_RETURN_REASON_CODE(activity.selectedReturnHed.getFINVRHED_REASON_CODE());
        ReturnDet.setFINVRDET_REFNO(RefNo);
        ReturnDet.setFINVRDET_ITEMCODE(itemCode);
        ReturnDet.setFINVRDET_PRILCODE(documentNo);
        ReturnDet.setFINVRDET_DIS_AMT("0");
        ReturnDet.setFINVRDET_IS_ACTIVE("1");
        ReturnDet.setFINVRDET_TAXCOMCODE("VAT15");
        ReturnDet.setFINVRDET_TXN_DATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        ReturnDet.setFINVRDET_TXN_TYPE("25");
        ReturnDet.setFINVRDET_RETURN_TYPE(returnType.getSelectedItem().toString());
        ReturnDet.setFINVRDET_BARCODE(barcode);
        ReturnDet.setFINVRDET_ARTICLENO(aricleno);
        ReturnDet.setFINVRDET_VARIANTCODE(variantcode);
        SOList.add(ReturnDet);

        if (new SalesReturnDetController(getActivity()).createOrUpdateInvRDet(SOList)>0)
        {
            Log.d("RETURN_DETAILS", "return det saved successfully...");
        }
        else
        {
            Log.d("RETURN_DETAILS", "return det saved unsuccess...");
        }
    }
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*--*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_RET_DETAILS"));
    }

    public void mRefreshData()
    {
        if (new SalesReturnController(getActivity()).getDirectSalesReturnRefNo().equals(""))
        {
            RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
        }
        else
        {
            RefNo = new SalesReturnController(getActivity()).getDirectSalesReturnRefNo();
        }

        Log.d("SALES_RETRUN", "DETAILS_FROM_FETCH_DATA" + RefNo);

        FetchData();
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
}
