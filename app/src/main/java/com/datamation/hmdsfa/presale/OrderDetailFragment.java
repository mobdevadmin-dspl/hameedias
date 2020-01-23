package com.datamation.hmdsfa.presale;


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
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.FreeIssueAdapter;
import com.datamation.hmdsfa.adapter.OrderDetailsAdapterNew;
import com.datamation.hmdsfa.adapter.OrderFreeItemAdapter;
import com.datamation.hmdsfa.adapter.PreOrderAdapter;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.ItemPriController;
import com.datamation.hmdsfa.controller.OrdFreeIssueController;
import com.datamation.hmdsfa.controller.OrderController;
import com.datamation.hmdsfa.controller.OrderDetailController;
import com.datamation.hmdsfa.controller.OrderDiscController;
import com.datamation.hmdsfa.controller.PreProductController;
import com.datamation.hmdsfa.controller.ProductController;
import com.datamation.hmdsfa.controller.ReasonController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.discount.Discount;
import com.datamation.hmdsfa.freeissue.FreeIssueModified;
import com.datamation.hmdsfa.helpers.PreSalesResponseListener;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.FreeItemDetails;
import com.datamation.hmdsfa.model.ItemFreeIssue;
import com.datamation.hmdsfa.model.OrdFreeIssue;
import com.datamation.hmdsfa.model.Order;
import com.datamation.hmdsfa.model.OrderDetail;
import com.datamation.hmdsfa.model.PreProduct;
import com.datamation.hmdsfa.model.Reason;
import com.datamation.hmdsfa.settings.ReferenceNum;
import com.datamation.hmdsfa.utils.GPSTracker;
import com.datamation.hmdsfa.view.PreSalesActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OrderDetailFragment extends Fragment{

    int totPieces = 0;
    int seqno = 0;
    ListView lv_order_det, lvFree;
    ArrayList<PreProduct> productList = null, selectedItemList = null;
    ImageButton ibtProduct;
    Button ibtDiscount;
    LinearLayout reasonLayout;
    SweetAlertDialog pDialog;
    private static final String TAG = "OrderDetailFragment";
    public View view;
    public SharedPref mSharedPref;
    private  String RefNo, locCoe;
    private  MyReceiver r;
    private Order tmpsoHed=null;  //from re oder creation
    PreSalesResponseListener preSalesResponseListener;
    int clickCount = 0;
    private double totAmt = 0.0;
    private Customer debtor;
    PreSalesActivity mainActivity;
    ArrayList<OrderDetail> orderList;
    private Spinner spnTxn,spnReason,spnQOH;
    String address = "No Address";
    double latitude = 0.0;
    double longitude = 0.0;
    GPSTracker gpsTracker;
    ArrayList<OrderDetail> exOrderDet;
    String isQohZeroAllow = "0", qohStatus = "0";//qohStatus = 1 (show qoh > 0) else  all items check only isQohZeroAllow = 1 (2019-12-20 rashmi)

    //PreSalesResponseListener preSalesResponseListener;
    public OrderDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.pre_sale_details_new_responsive_layout, container, false);
        //view = inflater.inflate(R.layout.sales_management_pre_sales_details_new, container, false);
        seqno = 0;
        totPieces = 0;
        mSharedPref =SharedPref.getInstance(getActivity());
        lv_order_det = (ListView) view.findViewById(R.id.lvProducts_pre);
        lvFree = (ListView) view.findViewById(R.id.lvFreeIssue_Inv);
        ibtDiscount = (Button) view.findViewById(R.id.ibtDisc);
        ibtProduct = (ImageButton) view.findViewById(R.id.ibtProduct);
        mainActivity = (PreSalesActivity)getActivity();
        reasonLayout = (LinearLayout) view.findViewById(R.id.reason_layout);
        RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.NumVal));
        spnTxn = (Spinner) view.findViewById(R.id.spnTxn);
        spnQOH= (Spinner) view.findViewById(R.id.spnQOH);
        spnReason = (Spinner) view.findViewById(R.id.spnReason);
//        RefNo = mainActivity.selectedPreHed.getORDER_REFNO();
        tmpsoHed = new Order();
        ArrayList<Reason> mrReasons;
        showData();
        gpsTracker = new GPSTracker(getActivity());
        Log.v("Latitude>>>>>",mSharedPref.getGlobalVal("Latitude"));
        Log.v("Longi>>>>>",mSharedPref.getGlobalVal("Longitude"));
        mSharedPref.setGlobalVal("preKeyIsFreeClicked", "0");
        ArrayList<String> strList = new ArrayList<String>();
        strList.add("SA-PRE SALE");
        strList.add("FI-FREE ISSUE");
        strList.add("UR-USABLE RETURN");
        strList.add("MR-MARKET RETURN");

        ArrayList<String> qohSttsList = new ArrayList<String>();

        qohSttsList.add("VIEW ALL ");
        qohSttsList.add("VIEW QOH ITEMS");

        mSharedPref.setDiscountClicked("0");
        clickCount = 0;

        Log.d("order_detail", "clicked_count" + clickCount);

        isQohZeroAllow = new SalRepController(getActivity()).isQohZeroAllow();

        final ArrayAdapter<String> txnTypeAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.return_spinner_item, strList);
        txnTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTxn.setAdapter(txnTypeAdapter);
        final ArrayAdapter<String> qohAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.return_spinner_item, qohSttsList);
        qohAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnQOH.setAdapter(qohAdapter);
        spnTxn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 3){//if market return selected
                    reasonLayout.setVisibility(View.VISIBLE);
                    ArrayList<String> mrReasons  = new ReasonController(getActivity()).getAllReasonsByType("RT01");
                    final ArrayAdapter<String> reasonAdapter = new ArrayAdapter<String>(getActivity(),
                            R.layout.return_spinner_item, mrReasons);
                    reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnReason.setAdapter(reasonAdapter);
                }else{
                    reasonLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
spnQOH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            qohStatus = "0";
        }else{
            qohStatus = "1";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
        spnReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SharedPref.getInstance(getActivity()).setGlobalVal("reason",spnReason.getSelectedItem().toString().split("-")[0]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ibtProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(new OrderController(getActivity()).IsSavedHeader(RefNo)>0){
                    mSharedPref.setGlobalVal("preKeyIsFreeClicked", "0");
                    mSharedPref.setDiscountClicked("0");
                    clickCount = 0;

                    int position = spnTxn.getSelectedItemPosition();
                    Toast.makeText(getActivity(),
                            spnTxn.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    if(position == 0){
                        new LoardingProductFromDB("SA").execute();
                    }else if(position == 1){
                    //show separate dialog to enter free issues
                        new LoardingProductFromDB("FI").execute();
                    }else if(position == 2){
                        new LoardingProductFromDB("UR").execute();
                    }else{
                        if(spnReason.getSelectedItem().toString().equals("")){
                            Toast.makeText(getActivity(),
                                    "Please select reason to continue..", Toast.LENGTH_SHORT).show();
                        }else {
                            new LoardingProductFromDB("MR").execute();
                        }
                    }

                }else{
                    preSalesResponseListener.moveBackToCustomer_pre(0);
                    Toast.makeText(getActivity(), "Cannot proceed,Please click arrow button to save header details...", Toast.LENGTH_LONG).show();
                }

            }
        });

        ibtDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSharedPref.setDiscountClicked("1");
                showData();

                mSharedPref.setGlobalVal("preKeyIsFreeClicked", ""+clickCount);
                if(clickCount == 0) {

                    calculateFreeIssue(mSharedPref.getSelectedDebCode());
                    calculateDiscounts(mSharedPref.getSelectedDebCode());
                    clickCount++;
                }else{
                    Toast.makeText(getActivity(),"Already clicked",Toast.LENGTH_LONG).show();
                    Log.v("Freeclick Count", mSharedPref.getGlobalVal("preKeyIsFreeClicked"));
                }

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

//        lv_order_det.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view2, final int position, long id)
//            {
//                final OrderDetail orderDet=orderList.get(position);
//                mSharedPref.setGlobalVal("preKeyIsFreeClicked", "0");
//                if(orderDet.getFORDERDET_TYPE().equals("MR")){
//                     CustomKeypadDialogReturnAmt keypadPrice = new CustomKeypadDialogReturnAmt(getActivity(), true, new CustomKeypadDialogReturnAmt.IOnOkClickListener() {
//                        @Override
//                        public void okClicked(double value) {
//                            //price cannot be changed less than gross profit
//                            if((Double.parseDouble(orderDet.getFORDERDET_AMT())*(-1)) > value) {
//                                Log.d("EDIT>>>","EDIT>>>"+orderDet.getFORDERDET_TYPE()+" - "+orderDet.getFORDERDET_ITEMCODE()+"-"+orderDet.getFORDERDET_QTY());
//                                new ProductController(getActivity()).updateProductPrice(orderDet.getFORDERDET_ITEMCODE(), String.valueOf((-value/Double.parseDouble(orderDet.getFORDERDET_QTY()))),orderDet.getFORDERDET_TYPE());
//                                //new OrderDetailController(getActivity()).updateProductPrice(orderDet.getFORDERDET_ITEMCODE(), String.valueOf((-value/Double.parseDouble(orderDet.getFORDERDET_QTY()))),String.valueOf(value),orderDet.getFORDERDET_TYPE());
//                                //new OrderDetailController(getActivity()).updateProductPrice(orderDet.getFORDERDET_ITEMCODE(), String.valueOf((-value/Double.parseDouble(orderDet.getFORDERDET_QTY()))),orderDet.getFORDERDET_AMT(),orderDet.getFORDERDET_TYPE());
//                                showData();
//                            }else{
//                                Toast.makeText(getActivity(),"Amount cannot be change..",Toast.LENGTH_LONG).show();
//                            }
//
//                        }
//                    });
//                    keypadPrice.show();
//
//                    keypadPrice.setHeader("CHANGE AMOUNT");
////                if(preProduct.getPREPRODUCT_CHANGED_PRICE().equals("0")){
//                    keypadPrice.loadValue(Double.parseDouble(orderDet.getFORDERDET_AMT()));
//
//                }else {
//                    Log.d("EDIT>>>", "EDIT>>>" + orderDet.getFORDERDET_TYPE() + " - " + orderDet.getFORDERDET_ITEMCODE() + "-" + orderDet.getFORDERDET_QTY());
//                }
//            }
//        });

        return view;
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

    public class LoardingProductFromDB extends AsyncTask<Object, Object, ArrayList<PreProduct>> {

        private String type;

        public LoardingProductFromDB(String type) {
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Fetch Data Please Wait.");
            pDialog.setCancelable(false);
            //pDialog.show();
        }

        @Override
        protected ArrayList<PreProduct> doInBackground(Object... objects) {

            if (new PreProductController(getActivity()).tableHasRecords(type)) {
                productList = new PreProductController(getActivity()).getAllItems("",type,isQohZeroAllow,qohStatus);
                } else {
                //productList =new ItemController(getActivity()).getAllItemForPreSales("","",RefNo, mSharedPref.getSelectedDebtorPrilCode());
                new PreProductController(getActivity()).insertIntoProductAsBulkForPre("MS", "WSP001", type);//prilcode, loccode hardcode for swadeshi
                //productList = new PreProductController(getActivity()).getAllItems("");

                if(tmpsoHed!=null) {

                    ArrayList<OrderDetail> orderDetailArrayList = tmpsoHed.getOrdDet();
                    if (orderDetailArrayList != null) {
                        for (int i = 0; i < orderDetailArrayList.size(); i++) {
                            String tmpItemcode = orderDetailArrayList.get(i).getFORDERDET_ITEMCODE();
                            String tmpItemname = orderDetailArrayList.get(i).getFORDERDET_ITEMNAME();
                            String tmpprice = orderDetailArrayList.get(i).getFORDERDET_PRICE();
                            String tmpqoh = orderDetailArrayList.get(i).getFORDERDET_QOH();
                            String tmpQty = orderDetailArrayList.get(i).getFORDERDET_QTY();
                            String tmpcase = orderDetailArrayList.get(i).getFORDERDET_CASES();
                            String tmprefno = orderDetailArrayList.get(i).getFORDERDET_REFNO();
                            String tmpunits = new ItemController(getActivity()).getUnits(tmpItemcode);

                            //Update Qty in  fProducts_pre table
                            int count = new PreProductController(getActivity()).updateQuantities(tmpItemcode,tmpItemname,tmpprice,tmpqoh, tmpQty, tmpcase,tmprefno, tmpunits,type);
                            if (count > 0) {
                                Log.d("InsertOrUpdate", "success");
                            } else {
                                Log.d("InsertOrUpdate", "Failed");
                            }

                        }
                    }
                }
                //----------------------------------------------------------------------------
            }
            productList = new PreProductController(getActivity()).getAllItems("",type,isQohZeroAllow,qohStatus);//rashmi -2018-10-26
            return productList;
        }


        @Override
        protected void onPostExecute(ArrayList<PreProduct> products) {
            super.onPostExecute(products);

            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
            ProductDialogBox(type);
        }
    }

    public void mToggleTextbox()
    {
        gpsTracker = new GPSTracker(getActivity());
        Log.v("Latitude>>>>>",mSharedPref.getGlobalVal("Latitude"));
        Log.v("Longi>>>>>",mSharedPref.getGlobalVal("Longitude"));
        showData();


    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(r);

        Log.d("order_detail", "clicked_count" + clickCount);

    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(r, new IntentFilter("TAG_DETAILS"));
        Log.d("order_detail", "clicked_count" + clickCount);
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            OrderDetailFragment.this.mToggleTextbox();

            Log.d("order_detail", "clicked_count" + clickCount);
        }
    }

    public void showData() {
        try
        {
            lv_order_det.setAdapter(null);
            orderList = new OrderDetailController(getActivity()).getAllOrderDetails(RefNo);
            lv_order_det.setAdapter(new OrderDetailsAdapterNew(getActivity(), orderList, mSharedPref.getSelectedDebCode()));//2019-07-07 till error free

            lvFree.setAdapter(null);
            ArrayList<OrderDetail> freeList=new OrderDetailController(getActivity()).getAllFreeIssue(RefNo);
            lvFree.setAdapter(new OrderFreeItemAdapter(getActivity(), freeList));

            Log.d("order_detail", "clicked_count" + clickCount);


        } catch (NullPointerException e) {
            Log.v("SA Error", e.toString());
        }
    }

    public void ProductDialogBox(final String typeInProductDialog) {

        final LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.product_dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final ListView lvProducts = (ListView) promptView.findViewById(R.id.lv_product_list);
        final SearchView search = (SearchView) promptView.findViewById(R.id.et_search);


        lvProducts.clearTextFilter();
        productList.clear();
        productList = new PreProductController(getActivity()).getAllItems("",typeInProductDialog,isQohZeroAllow,qohStatus);
        lvProducts.setAdapter(new PreOrderAdapter(getActivity(), productList, typeInProductDialog, SharedPref.getInstance(getActivity()).getGlobalVal("reason")));

        alertDialogBuilder.setCancelable(false).setNegativeButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                selectedItemList = new PreProductController(getActivity()).getSelectedItems(typeInProductDialog);
//2019-10-18 rashmi
                    updateOrderDet(selectedItemList,typeInProductDialog);

                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                productList = new PreProductController(getActivity()).getAllItems(query,typeInProductDialog,isQohZeroAllow,qohStatus);//Rashmi 2018-10-26
                lvProducts.setAdapter(new PreOrderAdapter(getActivity(), productList,typeInProductDialog,SharedPref.getInstance(getActivity()).getGlobalVal("reason")));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                productList.clear();
                productList = new PreProductController(getActivity()).getAllItems(newText,typeInProductDialog,isQohZeroAllow,qohStatus);//rashmi-2018-10-26
                lvProducts.setAdapter(new PreOrderAdapter(getActivity(), productList,typeInProductDialog,SharedPref.getInstance(getActivity()).getGlobalVal("reason")));
                return true;
            }
        });
    }

    public void updateOrderDet(final ArrayList<PreProduct> list, final String updateOrderDetType) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
//                pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                pDialog.setTitleText("Updating products...");
//                pDialog.setCancelable(false);
//                pDialog.show();
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {

                int i = 0;
                RefNo = new ReferenceNum(getActivity()).getCurrentRefNo(getResources().getString(R.string.NumVal));
                new OrderDetailController(getActivity()).deleteRecords(RefNo,updateOrderDetType);//commented by rashmi because check duplicate issue
                ArrayList<OrderDetail> toSaveOrderDetails = new OrderDetailController(getActivity()).mUpdatePrsSales(list,RefNo);

                if (new OrderDetailController(getActivity()).createOrUpdateOrdDet(toSaveOrderDetails)>0)
                {
                    Log.d("ORDER_DETAILS", "Order det saved successfully...");
                }
                else
                {
                    Log.d("ORDER_DETAILS", "Order det saved unsuccess...");
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                if(pDialog.isShowing()){
//                    pDialog.dismiss();
//                }

                showData();
            }

        }.execute();
    }


    public void newDeleteOrderDialog(final int dltPosition) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Confirm Deletion !");
        alertDialogBuilder.setMessage("Do you want to delete this item ?");
        alertDialogBuilder.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                new PreProductController(getActivity()).updateProductQty(orderList.get(dltPosition).getFORDERDET_ITEMCODE(), "0",orderList.get(dltPosition).getFORDERDET_TYPE());
                new PreProductController(getActivity()).updateProductCase(orderList.get(dltPosition).getFORDERDET_ITEMCODE(), "0",orderList.get(dltPosition).getFORDERDET_TYPE());
               // new PreProductController(getActivity()).updateProductp(orderList.get(dltPosition).getFORDERDET_ITEMCODE(), "0",orderList.get(dltPosition).getFORDERDET_TYPE());
             //   new PreProductController(getActivity()).updateBalQty(orderList.get(position).getFORDERDET_ITEMCODE(), "0");
              //  new PreProductController(getActivity()).updateRefNo(RefNo, "0",orderList.get(dltPosition).getFORDERDET_TYPE());
                if(orderList.get(dltPosition).getFORDERDET_TYPE().equals("MR")) {
                    new PreProductController(getActivity()).updateReason(orderList.get(dltPosition).getFORDERDET_ITEMCODE(), "", orderList.get(dltPosition).getFORDERDET_TYPE());
                    new ProductController(getActivity()).updateProductPrice(orderList.get(dltPosition).getFORDERDET_ITEMCODE(), "0", orderList.get(dltPosition).getFORDERDET_TYPE());
                }
                new OrderDetailController(getActivity()).mDeleteRecords(RefNo, orderList.get(dltPosition).getFORDERDET_ITEMCODE(),orderList.get(dltPosition).getFORDERDET_TYPE());
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
