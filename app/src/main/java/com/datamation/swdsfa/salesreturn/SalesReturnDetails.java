package com.datamation.swdsfa.salesreturn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.datamation.swdsfa.R;
import com.datamation.swdsfa.adapter.ProductAdapter;
import com.datamation.swdsfa.adapter.SalesReturnDetailsAdapter;
import com.datamation.swdsfa.controller.ItemController;
import com.datamation.swdsfa.controller.ReasonController;
import com.datamation.swdsfa.controller.SalRepController;
import com.datamation.swdsfa.controller.SalesReturnController;
import com.datamation.swdsfa.controller.SalesReturnDetController;
import com.datamation.swdsfa.dialog.CustomKeypadDialog;
import com.datamation.swdsfa.helpers.SalesReturnResponseListener;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.FInvRDet;
import com.datamation.swdsfa.model.FInvRHed;
import com.datamation.swdsfa.model.Item;
import com.datamation.swdsfa.settings.ReferenceNum;
import com.datamation.swdsfa.view.SalesReturnActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SalesReturnDetails extends Fragment implements View.OnClickListener{

    View view;
    Button itemSearch, bAdd, bFreeIssue;
    EditText lblItemName, txtQty, editTotDisc,lblNou;
    TextView lblPrice;
    int totPieces = 0;
    double amount = 0.00, changedPrice = 0.0, price = 0.0;
    double values = 0.00, iQoh;
    // TextView lblNou, lblPrice;
    Item selectedItem = null;
    //Reason selectedReason = null;
    int seqno = 0, index_id = 0;
    ListView lv_return_det;
    ArrayList<FInvRDet> returnList;
    //SharedPref mSharedPref;
    boolean hasChanged = false;
    String locCode;
    double brandDisPer;
    Spinner returnType;
    ArrayList<Item> list = null;
    //ArrayList<Reason> reasonList = null;
    SalesReturnActivity activity;
    SweetAlertDialog pDialog;
    String RefNo;
    SalesReturnResponseListener salesReturnResponseListener;
    MyReceiver r;
    SharedPref sharedPref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sales_return_details, container, false);
        activity = (SalesReturnActivity)getActivity();
        seqno = 0;
        totPieces = 0;
        sharedPref = SharedPref.getInstance(getActivity());
        itemSearch = (Button) view.findViewById(R.id.btn_item_search);
        bAdd = (Button) view.findViewById(R.id.btn_add);
        bFreeIssue = (Button) view.findViewById(R.id.btn_free);
        lblItemName = (EditText) view.findViewById(R.id.et_item);
        lv_return_det = (ListView) view.findViewById(R.id.lv_return_det);
        editTotDisc = (EditText) view.findViewById(R.id.et_TotalDisc);
        lblNou = (EditText) view.findViewById(R.id.tv_unit);
        lblPrice = (TextView) view.findViewById(R.id.tv_price);
        txtQty = (EditText) view.findViewById(R.id.et_pieces);
        returnType = (Spinner) view.findViewById(R.id.spinner_return_Type);

        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));

        if (activity.selectedReturnHed == null)
        {
            activity.selectedReturnHed = new SalesReturnController(getActivity()).getActiveReturnHed(RefNo);
        }

        ArrayList<String> strList = new ArrayList<String>();
        strList.add("Select Return type to continue ...");
        strList.add("SA");
        strList.add("FR");

        final ArrayAdapter<String> returnTypeAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.return_spinner_item, strList);
        returnTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        returnType.setAdapter(returnTypeAdapter);

        itemSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSearch.setEnabled(false);
                new LoadingReturnProductFromDB().execute();
            }
        });

        // -------------------------------------------------------------------------------------------------------------------------
        //reasonSearch.setOnClickListener(this);
        bAdd.setOnClickListener(this);
//        bFreeIssue.setOnClickListener(this);
//        lblPrice.setOnClickListener(this);

//            ArrayList<FInvRHed> getReturnHed = new FInvRHedDS(getActivity()).getAllActiveInvrhed();
//
//            if (!getReturnHed.isEmpty()) {
//
//                for (FInvRHed returnHed : getReturnHed) {
//                    activity.selectedReturnHed = returnHed;
//
//                    if (activity.selectedRetDebtor == null) {
//                        DebtorDS debtorDS = new DebtorDS(getActivity());
//                        activity.selectedRetDebtor = debtorDS.getSelectedCustomerByCode(returnHed.getFINVRHED_DEBCODE());
//                    }
//                }
//            }


        FetchData();

        /*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        txtQty.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if ((txtQty.length() > 0)) {
                    totPieces = Integer.parseInt(txtQty.getText().toString());
                    hasChanged = true;
                    amount = Double.parseDouble(txtQty.getText().toString())
                            * Double.parseDouble(lblPrice.getText().toString());
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        /*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        txtQty.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtQty.setText("");
            }
        });

        /*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lv_return_det.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //FInvRDet returnDet = returnList.get(position);
                deleteReturnDialog(position);
                return true;
            }
        });

        /*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

        lv_return_det.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {
//                FInvRDet returnDet = returnList.get(position);
//                FetchData();
//                bAdd.setText("EDIT");
//                selectedItem = new Items();
//                selectedItem.setFITEM_ITEM_CODE(returnDet.getFINVRDET_ITEMCODE());
//                index_id = Integer.parseInt(returnDet.getFINVRDET_ID());
//                lblItemName.setText(new ItemsDS(getActivity()).getItemNameByCode(returnDet.getFINVRDET_ITEMCODE()));
//                txtQty.setText(returnDet.getFINVRDET_QTY());
////
//                if (returnType.getSelectedItem().toString().equalsIgnoreCase("FR"))
//                {
//                    lblPrice.setText("0.00");
//                }
//                else
//                {
//                    //lblPrice.setText("10000.00");
//                    lblPrice.setText(new ItemPriDS(getActivity()).getProductPriceByCode(selectedItem.getFITEM_ITEM_CODE(), activity.selectedRetDebtor.getFDEBTOR_PRILLCODE()));
//
//                }
//                hasChanged = false;
//                editTotDisc.setText(returnDet.getFINVRDET_DIS_AMT());
//                //lblReason.setText(returnDet.getFINVRDET_RETURN_REASON());
            }
        });
        lblPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomKeypadDialog keypadPrice = new CustomKeypadDialog(getActivity(), true, new CustomKeypadDialog.IOnOkClickListener() {
                    @Override
                    public void okClicked(double value) {

                        //new FInvRDetDS(getActivity()).updateProductPrice(selectedItem.getFITEM_ITEM_CODE(), String.valueOf(price));

                        changedPrice = value;
                        lblPrice.setText(""+changedPrice);

                    }
                });
                keypadPrice.show();

                keypadPrice.setHeader("CHANGE PRICE");
//                if(preProduct.getPREPRODUCT_CHANGED_PRICE().equals("0")){
                keypadPrice.loadValue(changedPrice);
            }
        });

        return view;
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
                    clearTextFields();
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

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_add:
            {
                if (!(lblItemName.getText().toString().equals(""))) {

                    if (values >= 0.0 && totPieces > 0) {

                        itemSearch.setEnabled(true);

                        if (activity.selectedReturnHed != null)
                        {
                            //RefNo = activity.selectedReturnHed.getFINVRHED_REFNO();

                            if (new SalesReturnController(getActivity()).getDirectSalesReturnRefNo().equals(""))
                            {
                                RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));
                            }
                            else
                            {
                                RefNo = new SalesReturnController(getActivity()).getDirectSalesReturnRefNo();
                            }

                            if (RefNo != null)
                            {
                                FInvRDet ReturnDet = new FInvRDet();
                                ArrayList<FInvRDet> ReturnList = new ArrayList<FInvRDet>();
                                ArrayList<FInvRHed> returnHedList = new ArrayList<FInvRHed>();

                                String TaxedAmt = "0.0";

                                activity.selectedReturnHed.setFINVRHED_COSTCODE("000");
                                activity.selectedReturnHed.setFINVRHED_LOCCODE("0001");
                                activity.selectedReturnHed.setFINVRHED_RETURN_TYPE(returnType.getSelectedItem().toString());
                                activity.selectedReturnHed.setFINVRHED_TOTAL_TAX(TaxedAmt);

                                returnHedList.add(activity.selectedReturnHed);

                                if (new SalesReturnController(getActivity()).createOrUpdateInvRHed(returnHedList) > 0) {
                                    seqno++;
                                    ReturnDet.setFINVRDET_ID(index_id + "");
                                    ReturnDet.setFINVRDET_SEQNO(seqno + "");
                                    ReturnDet.setFINVRDET_COST_PRICE("0.00");
                                    ReturnDet.setFINVRDET_SELL_PRICE(lblPrice.getText().toString());
                                    double price = Double.parseDouble(lblPrice.getText().toString());
                                    double disc = Double.parseDouble(editTotDisc.getText().toString());
                                    double qty = Double.parseDouble(txtQty.getText().toString());
                                    double tax = Double.parseDouble(TaxedAmt);
                                    //String unitPrice = new TaxDetDS(getActivity()).calculateReverseTaxFromDebTax(activity.selectedReturnHed.getFINVRHED_DEBCODE(),selectedItem.getFITEM_ITEM_CODE(), new BigDecimal(price));
                                    double amt = price * qty;
                                    String sellPrice = String.format("%.2f",price);
                                    String tSellPrice = String.format("%.2f",amt);

                                    ReturnDet.setFINVRDET_CHANGED_PRICE(String.format("%.2f",changedPrice));
                                    ReturnDet.setFINVRDET_COST_PRICE(lblPrice.getText().toString());
                                    ReturnDet.setFINVRDET_SELL_PRICE(""+price);
                                    ReturnDet.setFINVRDET_T_SELL_PRICE(""+price);
                                    ReturnDet.setFINVRDET_DIS_AMT(editTotDisc.getText().toString());
                                    ReturnDet.setFINVRDET_AMT(
                                            String.format("%.2f", amt));
                                    ReturnDet.setFINVRDET_TAX_AMT(TaxedAmt);
                                    ReturnDet.setFINVRDET_QTY(totPieces + "");
                                    ReturnDet.setFINVRDET_BAL_QTY(totPieces + "");
                                    ReturnDet.setFINVRDET_RETURN_REASON(new ReasonController(getActivity()).getReaNameByCode(activity.selectedReturnHed.getFINVRHED_REASON_CODE()));
                                    ReturnDet.setFINVRDET_RETURN_REASON_CODE(activity.selectedReturnHed.getFINVRHED_REASON_CODE());
                                    ReturnDet.setFINVRDET_REFNO(RefNo);
                                    ReturnDet.setFINVRDET_ITEMCODE(selectedItem.getFITEM_ITEM_CODE());
                                    ReturnDet.setFINVRDET_PRILCODE("");
                                    ReturnDet.setFINVRDET_IS_ACTIVE("1");
                                    ReturnDet.setFINVRDET_TAXCOMCODE("VAT15");
                                    ReturnDet.setFINVRDET_TXN_DATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                                    ReturnDet.setFINVRDET_TXN_TYPE("25");
                                    ReturnDet.setFINVRDET_RETURN_TYPE(returnType.getSelectedItem().toString());

                                    ReturnList.add(ReturnDet);

                                    if (new SalesReturnDetController(getActivity()).createOrUpdateInvRDet(ReturnList)>0)
                                    {
                                        //if (bAdd.getText().equals("EDIT"))
                                        //Toast.makeText(getActivity(), "Edited successfully !", Toast.LENGTH_LONG).show();
                                        //else
                                        Toast.makeText(getActivity(), "Added successfully !", Toast.LENGTH_LONG).show();
                                    }

                                    FetchData();

                                    clearTextFields();
                                }
                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Invalid sales return Ref no..." , Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Invalid sales return head..." , Toast.LENGTH_LONG).show();
                            salesReturnResponseListener.moveBackTo_ret(0);

                        }
                    }
                }
            }
            break;
            default:
                break;
        }

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

    public class LoadingReturnProductFromDB extends AsyncTask<Object, Object, ArrayList<Item>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Fetch Data Please Wait.");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected ArrayList<Item> doInBackground(Object... objects) {

            list = new ItemController(getActivity()).getAllItemForSalesReturn("","","",new SalRepController(getActivity()).getCurrentLocCode().trim(),sharedPref.getSelectedDebtorPrilCode());
            Log.v("Return Item count", ">>>>>"+list.size());
            return list;
        }


        @Override
        protected void onPostExecute(ArrayList<Item> items) {
            super.onPostExecute(items);

            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
            returnProductDialogBox(list);
            itemSearch.setEnabled(true);

        }
    }

    private void clearTextFields() {
        values = 0.0;
        index_id = 0;
        totPieces = 0;
        lblItemName.setText("");
        txtQty.setText("0");
        txtQty.clearFocus();
        lblNou.setText("0");
        lblPrice.setText("0.00");
        editTotDisc.setText("0.00");
        bAdd.setText("ADD");
    }

    private void returnProductDialogBox(ArrayList<Item>itemList)
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_details_dialog);

        final SearchView search = (SearchView) dialog.findViewById(R.id.et_search);
        final ListView productList = (ListView) dialog.findViewById(R.id.lv_product_list);

        clearTextFields();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        productList.clearTextFilter();

        Log.v("Return Itms bfr adapter", ">>>>>"+itemList.size());
        productList.setAdapter(new ProductAdapter(getActivity(), itemList));
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = list.get(position);
                //brandDisPer = new DebItemPriDS(getActivity()).getBrandDiscount(selectedItem.getITEM_CODE(),activity.selectedRetDebtor.getFDEBTOR_CODE());
                lblItemName.setText(selectedItem.getFITEM_ITEM_NAME());
                //lblNou.setText(selectedItem.get());
//
                if (returnType.getSelectedItem().toString().equalsIgnoreCase("FR"))
                {
                    lblPrice.setText("0.00");
                }
                else
                {
                    //lblPrice.setText("1000.00");
//                    if(new ItemController(getActivity()).(selectedItem.getFITEM_ITEM_CODE(), activity.selectedRetDebtor.getFDEBTOR_PRILLCODE()).equals("") || new ItemPriDS(getActivity()).getProductPriceByCode(selectedItem.getFITEM_ITEM_CODE(), activity.selectedRetDebtor.getFDEBTOR_PRILLCODE()).equals(null)) {
//                        price = 0.0;
//                    }else{
//                        price = Double.parseDouble(new ItemPriDS(getActivity()).getProductPriceByCode(selectedItem.getFITEM_ITEM_CODE(), activity.selectedRetDebtor.getFDEBTOR_PRILLCODE()));
//                        changedPrice = price;
//                    }
//                    lblPrice.setText(""+price);
                    lblPrice.setText(selectedItem.getFITEM_AVGPRICE());
                }


                //lblPrice.setText("10000.00");
                //iQoh = Double.parseDouble(selectedItem.getFITEM_QOH());
                txtQty.requestFocus();
                dialog.dismiss();

            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                list.clear();
                list = new ItemController(getActivity()).getAllItemForSalesReturn("","","",new SalRepController(getActivity()).getCurrentLocCode().trim(),sharedPref.getSelectedDebtorPrilCode());
                Log.v("Return Item count", ">>>>>"+list.size());
                productList.clearTextFilter();
                productList.setAdapter(new ProductAdapter(getActivity(), list));

                return false;
            }
        });
        dialog.show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            salesReturnResponseListener = (SalesReturnResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onButtonPressed");
        }
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            SalesReturnDetails.this.mRefreshData();
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
        //RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.salRet));

        Log.d("SALES_RETRUN", "DETAILS_FROM_FETCH_DATA" + RefNo);

        FetchData();
    }
}
