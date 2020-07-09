package com.datamation.hmdsfa.barcode;


import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.BundleAdapter;
import com.datamation.hmdsfa.adapter.FreeIssueAdapter;
import com.datamation.hmdsfa.adapter.OrderDetailsAdapter;
import com.datamation.hmdsfa.adapter.OrderFreeItemAdapter;
import com.datamation.hmdsfa.adapter.PreOrderAdapter;
import com.datamation.hmdsfa.controller.BarcodeVarientController;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.ItemBundleController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.ItemPriController;
import com.datamation.hmdsfa.controller.OrdFreeIssueController;
import com.datamation.hmdsfa.controller.OrderController;
import com.datamation.hmdsfa.controller.OrderDetailController;
import com.datamation.hmdsfa.controller.OrderDiscController;
import com.datamation.hmdsfa.controller.PreProductController;
import com.datamation.hmdsfa.controller.ProductController;
import com.datamation.hmdsfa.controller.TaxDetController;
import com.datamation.hmdsfa.controller.VATController;
import com.datamation.hmdsfa.discount.Discount;
import com.datamation.hmdsfa.freeissue.FreeIssueModified;
import com.datamation.hmdsfa.helpers.PreSalesResponseListener;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.FreeItemDetails;
import com.datamation.hmdsfa.model.ItemBundle;
import com.datamation.hmdsfa.model.ItemFreeIssue;
import com.datamation.hmdsfa.model.OrdFreeIssue;
import com.datamation.hmdsfa.model.Order;
import com.datamation.hmdsfa.model.OrderDetail;
import com.datamation.hmdsfa.model.PreProduct;
import com.datamation.hmdsfa.model.Product;
import com.datamation.hmdsfa.settings.ReferenceNum;
import com.datamation.hmdsfa.view.PreSalesActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class BROrderDetailFragment extends Fragment{
    int totPieces = 0;
    int seqno = 0;
    ListView lv_order_det, lvFree;
    ArrayList<Product> productList = null, selectedItemList = null, selectedItem = null;
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

    FloatingActionButton btnDiscount;
    public BROrderDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_bar_code_reader, container, false);
        seqno = 0;
        totPieces = 0;
        mSharedPref =SharedPref.getInstance(getActivity());
        lv_order_det = (ListView) view.findViewById(R.id.lvProducts_Inv);
        lvFree = (ListView) view.findViewById(R.id.lvFreeIssue_Inv);
        mainActivity = (PreSalesActivity)getActivity();
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.NumVal));

        etSearchField = view.findViewById(R.id.etSearchField);
        spnScanType = (Spinner) view.findViewById(R.id.spnScan);
        btnDiscount = (FloatingActionButton)  view.findViewById(R.id.btn_discount);
        itemArrayList = new ArrayList<>();
        tmpsoHed = new Order();
        showData();
        ArrayList<String> strList = new ArrayList<String>();
        strList.add("ITEM WISE");
        strList.add("BUNDLE WISE");
        final ArrayAdapter<String> txnTypeAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.return_spinner_item, strList);
        txnTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnScanType.setAdapter(txnTypeAdapter);
        showData();
        if(new OrderController(getActivity()).IsSavedHeader(RefNo)>0){
            etSearchField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    clickCount = 0;
                    mSharedPref.setDiscountClicked("0");
                    if(spnScanType.getSelectedItemPosition() == 0) {
                        ArrayList<ItemBundle> itemBundle = new BarcodeVarientController(getActivity())
                                .getItemsInBundle(etSearchField.getText().toString());
                        Log.v("ENTERED CODE", "itemcode " + etSearchField.getText().toString());
                        if(itemBundle.size()==1) {
                            selectedItem = new ProductController(getActivity()).getScannedtems(itemBundle.get(0));
                            updateOrderDet(selectedItem);
                            showData();
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


        }else{
            preSalesResponseListener.moveBackToCustomer_pre(0);
            Toast.makeText(getActivity(), "Cannot proceed,Please click arrow button to save header details...", Toast.LENGTH_LONG).show();
        }

//        ibtDiscount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSharedPref.setDiscountClicked("1");
//                showData();
//
//                mSharedPref.setGlobalVal("preKeyIsFreeClicked", ""+clickCount);
//                if(clickCount == 0) {
//
//                    calculateFreeIssue(mSharedPref.getSelectedDebCode());
//                    calculateDiscounts(mSharedPref.getSelectedDebCode());
//                    clickCount++;
//                }else{
//                    Toast.makeText(getActivity(),"Already clicked",Toast.LENGTH_LONG).show();
//                    Log.v("Freeclick Count", mSharedPref.getGlobalVal("preKeyIsFreeClicked"));
//                }
//
//            }
//        });

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

                updateOrderDet(selectedItemList);
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

    public void calculateDiscounts(String debCode){

        // get active order details to reset before calculate the discount
        exOrderDet = new OrderDetailController(getActivity()).getExOrderDetails(RefNo);

        // update the order detail with active order details
        new OrderDetailController(getActivity()).resetOrderDetWithoutDiscountData(RefNo, exOrderDet);

        //Clear discount in Orddisc
        new OrderDiscController(getActivity()).ClearDiscountForPreSale(RefNo);
        ArrayList<OrderDetail> dets = new OrderDetailController(getActivity()).getSAForFreeIssueCalc(RefNo);

        Discount issue = new Discount(getActivity());

        /* Get discounts for assorted items */
        ArrayList<ArrayList<OrderDetail>> metaOrdList = issue.SortDiscount(dets, debCode);

        Log.d("PRE_SALES_ORDER_DETAILS", "LIST_SIZE: " + metaOrdList.size());

        /* Iterate through for discounts for items */
        for (ArrayList<OrderDetail> OrderList : metaOrdList) {

            double totAmt = 0;
            String discPer,discType,discRef;
            double freeVal = Double.parseDouble(OrderList.get(0).getFORDERDET_BAMT());
            if(OrderList.get(0).getFORDERDET_SCHDISPER() != null)
                discPer = OrderList.get(0).getFORDERDET_SCHDISPER();
            else
                discPer = "";
            if(OrderList.get(0).getFORDERDET_DISCTYPE() != null)
                discType = OrderList.get(0).getFORDERDET_DISCTYPE();
            else
                discType = "";
            if(OrderList.get(0).getFORDERDET_DISC_REF() != null)
                discRef = OrderList.get(0).getFORDERDET_DISC_REF();
            else
                discRef = "";


            OrderList.get(0).setFORDERDET_BAMT("0");

            for (OrderDetail det : OrderList)
                totAmt += Double.parseDouble(det.getFORDERDET_PRICE()) * (Double.parseDouble(det.getFORDERDET_QTY()));
            // commented cue to getFTRANSODET_PRICE() is not set
            //totAmt += Double.parseDouble(det.getFTRANSODET_PRICE()) * (Double.parseDouble(det.getFTRANSODET_QTY()));

            for (OrderDetail det : OrderList) {
                det.setFORDERDET_SCHDISPER(discPer);
                det.setFORDERDET_DISCTYPE(discType);
                det.setFORDERDET_DISC_REF(discRef);

                double disc;
                /*
                 * For value, calculate amount portion & for percentage ,
                 * calculate percentage portion
                 */
                disc = (freeVal / totAmt) * Double.parseDouble(det.getFORDERDET_PRICE()) * (Double.parseDouble(det.getFORDERDET_QTY()));

                //commented due to
                // disc = (Double.parseDouble(det.getFTRANSODET_AMT()) / 100) * disc not correct

                /* Calculate discount amount from disc percentage portion */
//					if (discType != null)
//                    {
//                        if (discType.equals("P"))
////                            disc = (Double.parseDouble(det.getFTRANSODET_AMT()) / 100) * disc;
//                            disc = (Double.parseDouble(det.getFTRANSODET_AMT()) / 100);
//                    }


                //new OrderDetailController(getActivity()).updateDiscount(det, disc, det.getFORDERDET_DISCTYPE());

            }
        }
        //amount is update , after the tap free issue button
        showData();
    }

    public void calculateFreeIssue(String debCode) {
        /* GET CURRENT ORDER DETAILS FROM TABLE */
        ArrayList<OrderDetail> dets = new OrderDetailController(getActivity()).getSAForFreeIssueCalc(RefNo);
        /* CLEAR ORDERDET TABLE RECORD IF FREE ITEMS ARE ALREADY ADDED. */
        new OrderDetailController(getActivity()).restFreeIssueData(RefNo);
        /* Clear free issues in OrdFreeIss */
        new OrdFreeIssueController(getActivity()).ClearFreeIssuesForPreSale(RefNo);
        // // Menaka on 09-12-2019
        FreeIssueModified freeIssue = new FreeIssueModified(getActivity());
        // GET ARRAY OF FREE ITEMS BY PASSING IN ORDER DETAILS
        //ArrayList<FreeItemDetails> list = issue.getFreeItemsBySalesItem(dets);
        ArrayList<FreeItemDetails> list = freeIssue.getFreeItemsBySalesItem(dets);
        //ArrayList<FreeItemDetails> list = issue.getFreeItemsBySalesItem(dets, new SharedPref(getActivity()).getGlobalVal("preKeyCostCode"));
        // PASS EACH ITEM IN TO DIALOG BOX FOR USER SELECTION
        //       if(count == 1) {
        // Log.v("Click count before loop", ">>>"+count);
        for (FreeItemDetails freeItemDetails : list) {
            if(freeItemDetails.getFreeQty()>0) {
                freeIssueDialogBox(freeItemDetails);
            }
        }
//        NewFreeIssue free = new NewFreeIssue(getActivity());
//
//        ArrayList<FreeItemDetails> newlist = free.New_FreeIssue();
//                for (FreeItemDetails freeItemDetails : newlist) {
//            freeIssueDialogBox(freeItemDetails);
//
//        }

        Log.v("Click count after loop", ">>>"+clickCount);
//        }

    }
    //------------------------------------------------------------show ---------------Free issue Dailog box--------------------------------------------------------------------------

    private boolean freeIssueDialogBox(final FreeItemDetails itemDetails) {

        final ArrayList<ItemFreeIssue> itemFreeIssues;
        final ArrayList<String> saleItemList;
        final String FIRefNo = itemDetails.getRefno();
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.free_issues_items_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Free Issue Schemes");
        alertDialogBuilder.setView(promptView);

        final ListView listView = (ListView) promptView.findViewById(R.id.lv_free_issue);
        final ListView listViewsale = (ListView) promptView.findViewById(R.id.lv_free_issue_items);
        final TextView itemName = (TextView) promptView.findViewById(R.id.tv_free_issue_item_name);
        final TextView itemNamefree = (TextView) promptView.findViewById(R.id.tv_free_issued_item_name);
        final TextView freeQty = (TextView) promptView.findViewById(R.id.tv_free_qty);

        freeQty.setText("Free Quantity : " + itemDetails.getFreeQty());
        //itemName.setText("Products : " + new ItemController(getActivity()).getItemNameByCode(itemDetails.getFreeIssueSelectedItem()));
        // itemName.setText("Products : ");
        //itemNamefree.setText("Free Products : " + new ItemController(getActivity()).getItemNameByCode(itemDetails.getFreeIssueSelectedItem()));
        itemNamefree.setText("Free Products : ");

        final ItemController itemsDS = new ItemController(getActivity());
        itemFreeIssues = itemsDS.getAllFreeItemNameByRefno(itemDetails.getRefno());
        saleItemList = itemsDS.getFreeIssueItemDetailsByItem(itemDetails.getSaleItemList());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, saleItemList);
        listViewsale.setAdapter(adapter);
        listView.setAdapter(new FreeIssueAdapter(getActivity(), itemFreeIssues,itemDetails));


        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                for (ItemFreeIssue itemFreeIssue : itemFreeIssues) {

                    if (Integer.parseInt(itemFreeIssue.getAlloc()) > 0) {

                        seqno++;
                        OrderDetail ordDet = new OrderDetail();
                        OrderDetailController detDS = new OrderDetailController(getActivity());
                        ArrayList<OrderDetail> ordList = new ArrayList<OrderDetail>();

                        ordDet.setFORDERDET_ID("0");
                        ordDet.setFORDERDET_AMT("0");
                        ordDet.setFORDERDET_BALQTY(itemFreeIssue.getAlloc());
                        ordDet.setFORDERDET_BAMT("0");
                        ordDet.setFORDERDET_BDISAMT("0");
                        ordDet.setFORDERDET_BPDISAMT("0");
                        String unitPrice = new ItemPriController(getActivity()).getProductPriceByCode(itemFreeIssue.getItems().getFITEM_ITEM_CODE(), mSharedPref.getSelectedDebtorPrilCode());
                        ordDet.setFORDERDET_BSELLPRICE("0");
                        ordDet.setFORDERDET_BTSELLPRICE("0.00");
                        ordDet.setFORDERDET_DISAMT("0");
                        ordDet.setFORDERDET_SCHDISPER("0");
                        ordDet.setFORDERDET_FREEQTY("0");
                        ordDet.setFORDERDET_ITEMCODE(itemFreeIssue.getItems().getFITEM_ITEM_CODE());
                        ordDet.setFORDERDET_PDISAMT("0");
                        ordDet.setFORDERDET_PRILCODE("WSP001");
                        ordDet.setFORDERDET_QTY(itemFreeIssue.getAlloc());
                        ordDet.setFORDERDET_PICE_QTY(itemFreeIssue.getAlloc());
                        ordDet.setFORDERDET_CASES("0");//because free issue not issued cases wise-2019-10-21 menaka said
                        ordDet.setFORDERDET_TYPE("FD");
                        ordDet.setFORDERDET_RECORDID("");
                        ordDet.setFORDERDET_REFNO(RefNo);
                        ordDet.setFORDERDET_SELLPRICE("0");
                        ordDet.setFORDERDET_SEQNO(seqno + "");
                        ordDet.setFORDERDET_TAXAMT("0");
                        ordDet.setFORDERDET_TAXCOMCODE(new ItemController(getActivity()).getTaxComCodeByItemCode(itemFreeIssue.getItems().getFITEM_ITEM_CODE()));
                        //ordDet.setFTRANSODET_TAXCOMCODE(new ItemsDS(getActivity()).getTaxComCodeByItemCodeFromDebTax(itemFreeIssue.getItems().getFITEM_ITEM_CODE(), mainActivity.selectedDebtor.getFDEBTOR_CODE()));
                        ordDet.setFORDERDET_TIMESTAMP_COLUMN("");
                        ordDet.setFORDERDET_TSELLPRICE("0.00");
                        ordDet.setFORDERDET_TXNDATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        ordDet.setFORDERDET_TXNTYPE("27");
                        ordDet.setFORDERDET_IS_ACTIVE("1");
                        ordDet.setFORDERDET_LOCCODE(mSharedPref.getGlobalVal("PrekeyLocCode").trim());
                        ordDet.setFORDERDET_COSTPRICE(new ItemController(getActivity()).getCostPriceItemCode(itemFreeIssue.getItems().getFITEM_ITEM_CODE()));
                        ordDet.setFORDERDET_BTAXAMT("0");
                        ordDet.setFORDERDET_IS_SYNCED("0");
                        ordDet.setFORDERDET_QOH("0");
                        ordDet.setFORDERDET_SCHDISC("0");
                        ordDet.setFORDERDET_BRAND_DISC("0");
                        ordDet.setFORDERDET_BRAND_DISPER("0");
                        ordDet.setFORDERDET_COMP_DISC("0");
                        ordDet.setFORDERDET_COMP_DISPER("0");
                        ordDet.setFORDERDET_DISCTYPE("");
                        ordDet.setFORDERDET_PRICE("0.00");
                        ordDet.setFORDERDET_ORG_PRICE("0");
                        ordDet.setFORDERDET_DISFLAG("0");
                        ordDet.setFORDERDET_REACODE("");

                        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*OrdFreeIssue table update*-*-*-*-*-*-*-*-*-*-*-*-*-*/

                        OrdFreeIssue ordFreeIssue = new OrdFreeIssue();
                        ordFreeIssue.setOrdFreeIssue_ItemCode(itemFreeIssue.getItems().getFITEM_ITEM_CODE());
                        ordFreeIssue.setOrdFreeIssue_Qty(itemFreeIssue.getAlloc());
                        ordFreeIssue.setOrdFreeIssue_RefNo(FIRefNo);
                        ordFreeIssue.setOrdFreeIssue_RefNo1(RefNo);
                        ordFreeIssue.setOrdFreeIssue_TxnDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        new OrdFreeIssueController(getActivity()).UpdateOrderFreeIssue(ordFreeIssue);

                        /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*/

                        ordList.add(ordDet);

                        if (detDS.createOrUpdateOrdDet(ordList) > 0) {
                            Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_SHORT).show();
                            showData();

//                            lvFree.setAdapter(null);
//                            ArrayList<OrderDetail> freeList=new OrderDetailController(getActivity()).getAllFreeIssue(RefNo);
//                            lvFree.setAdapter(new OrderFreeItemAdapter(getActivity(), freeList));
                        }
                    }
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mSharedPref.setGlobalVal("preKeyIsFreeClicked", "0");
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
        return true;
    }


    public void mToggleTextbox()
    {
        showData();
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_DETAILS"));
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



    public void updateOrderDet(final ArrayList<Product> list) {
                int i = 0;
                RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.NumVal));
              //  new OrderDetailController(getActivity()).deleteRecords(RefNo);

                for (Product product : list) {
                    i++;
                    mUpdatePrsSales(product.getFPRODUCT_Barcode(), product.getFPRODUCT_ITEMCODE(), product.getFPRODUCT_QTY(), product.getFPRODUCT_Price(), product.getFPRODUCT_VariantCode(), product.getFPRODUCT_QTY(), product.getFPRODUCT_ArticleNo(), product.getFPRODUCT_DocumentNo());
                }



    }

    public void newDeleteOrderDialog(final int position) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Confirm Deletion !");
        alertDialogBuilder.setMessage("Do you want to delete this item ?");
        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                new PreProductController(getActivity()).updateProductQty(orderList.get(position).getFORDERDET_ITEMCODE(), "0");
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
    }
}
