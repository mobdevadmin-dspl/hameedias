package com.datamation.swdsfa.freeissue;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.datamation.swdsfa.controller.FreeDebController;
import com.datamation.swdsfa.controller.FreeDetController;
import com.datamation.swdsfa.controller.FreeHedController;
import com.datamation.swdsfa.controller.FreeMslabController;
import com.datamation.swdsfa.controller.FreeSlabController;
import com.datamation.swdsfa.helpers.DatabaseHelper;
import com.datamation.swdsfa.helpers.SharedPref;
import com.datamation.swdsfa.model.FreeHed;
import com.datamation.swdsfa.model.FreeItemDetails;
import com.datamation.swdsfa.model.FreeMslab;
import com.datamation.swdsfa.model.FreeSlab;
import com.datamation.swdsfa.model.OrderDetail;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

/*created by rashmi-2019-09-10*/
public class FreeIssueModified_old {
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbHelper;

    public FreeIssueModified_old(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }
    public void open() throws SQLException {
        dB = dbHelper.getWritableDatabase();
    }
    //filter free items from order details
    public ArrayList<OrderDetail> filterFreeItemsFromOrder(ArrayList<OrderDetail> OrderList) {
        //rashmi for new swadeshi sfa - 2019-09-09
        ArrayList<OrderDetail> newOrderList = new ArrayList<OrderDetail>();
        for (OrderDetail det: OrderList) {

            if(det.getFORDERDET_ITEMCODE().equals(getSaleItemByItemCode(det.getFORDERDET_ITEMCODE()))){
                newOrderList.add(det);
            }
        }

        for (OrderDetail order : newOrderList) {

            Log.d("has free items", order.getFORDERDET_ITEMCODE() + " - " + order.getFORDERDET_QTY());

        }
        return newOrderList;

    }

    //filter free items from order details
//    public ArrayList<OrderDetail> filterFreeItemsFromScheme(FreeHed free) {
//        //rashmi for new swadeshi sfa - 2019-09-09
//        ArrayList<OrderDetail> newOrderList = new ArrayList<OrderDetail>();
//        for (OrderDetail det: OrderList) {
//
//            if(det.getFORDERDET_ITEMCODE().equals(getSaleItemByItemCode(det.getFORDERDET_ITEMCODE()))){
//                newOrderList.add(det);
//            }
//        }
// if(free.getFFREEHED_REFNO().equals(getSaleItemByRefNo()))
//        for (OrderDetail order : newOrderList) {
//
//            Log.d("Rashmi", order.getFORDERDET_ITEMCODE() + " - " + order.getFORDERDET_QTY());
//
//        }
//        return newOrderList;
//
//    }
    /**************************************************get free issue itemcode (sale item) ******************************************************************/
    public String getSaleItemByItemCode(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery =  "select itemcode from ffreedet  where refno in (select refno from ffreehed where date('now') between vdatef and vdatet ) and itemcode='" + itemCode + "'";
        String saleItemToFree = null;
        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                saleItemToFree = cursor.getString(cursor.getColumnIndex(FreeDetController.FFREEDET_ITEM_CODE));

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return saleItemToFree;
    }
    /**************************************************get free issue itemcode (sale item) ******************************************************************/
    public String getSaleItemByRefNo(String refno) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery =  "select itemcode from ffreedet  where refno in (select refno from ffreehed where date('now') between vdatef and vdatet ) and refno='" + refno + "'";
        String saleItemToFree = null;
        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                saleItemToFree = cursor.getString(cursor.getColumnIndex(FreeDetController.FFREEDET_ITEM_CODE));

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return saleItemToFree;
    }
    /**************************************************get priority for sort ******************************************************************/

    public String getPriority(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "select distinct Priority from ffreehed where refno in (select refno from ffreedet where itemcode='" + itemCode + "')  AND date('now') between vdatef and vdatet order by Priority asc";

        String priority = null;
        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                priority = cursor.getString(cursor.getColumnIndex(FreeHedController.FFREEHED_PRIORITY));

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return priority;
    }

    /**************************************************By Rashmi******************************************************************/

    public ArrayList<FreeItemDetails> getFreeItemsBySalesItem(ArrayList<OrderDetail> newOrderList) {

        ArrayList<FreeItemDetails> freeList = new ArrayList<FreeItemDetails>();
        int slabassorted = 0, flatAssort = 0, mixAssort = 0;
        FreeHedController freeHedDS = new FreeHedController(context);

        ArrayList<OrderDetail> dets = filterFreeItemsFromOrder(newOrderList);// rashmi -2019-09-09 :

      //  ArrayList<FreeHed> freeSchemes = removeDuplicates(freeHedDS.filterFreeSchemesFromOrder(dets));

            for (OrderDetail det : dets) {
                Log.v("Itemcode ", det.getFORDERDET_ITEMCODE() + " has free");
                ArrayList<FreeHed> freeSchemes = freeHedDS.getFreeIssueItemDetailByItem(det.getFORDERDET_ITEMCODE());
                for (FreeHed freeHed : freeSchemes) {
                    Log.d("Inside ", freeHed.getFFREEHED_REFNO() + " filter");
                int entedTotQty = Integer.parseInt(det.getFORDERDET_QTY());
                if (freeHed.getFFREEHED_FTYPE().equals("Flat")) {
                    Log.d("Inside ", freeHed.getFFREEHED_REFNO() + " FLAT");
                    // only-----
                    // flat scheme item qty => 12:1
                    int itemQty = (int) Float.parseFloat(freeHed.getFFREEHED_ITEM_QTY());
                    Log.d("FLAT itemQty", freeHed.getFFREEHED_ITEM_QTY());
                    // Debtor code from selected debtor
                    String debCode = SharedPref.getInstance(context).getSelectedDebCode();
                    Log.d("FLAT debCode", debCode);
                    // get debtor count from FIS no
                    int debCount = new FreeDebController(context).getRefnoByDebCount(freeHed.getFFREEHED_REFNO());
                    Log.d("FLAT debCount", debCount+"");
                    // select debtor from FIS no
                    int IsValidDeb = new FreeDebController(context).isValidDebForFreeIssue(freeHed.getFFREEHED_REFNO(), debCode);
                    if(IsValidDeb==1)
                        Log.d("IsValidDeb", " TRUE");
                        else
                        Log.d("IsValidDeb",  "TRUE");
                    // get assort count from FIS no
                    int assortCount = new FreeDetController(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());
                    Log.d("assortCount",  freeHed.getFFREEHED_REFNO()+" - "+assortCount);
                    // if its assorted
                    if (assortCount > 1) {

                        flatAssort = flatAssort + entedTotQty;
                        Log.d("inside assort count>1 ",  " flat assort -> "+flatAssort);
                        int index = 0;
                        boolean assortUpdate = false;


                        if (!assortUpdate) {// When 1st time running
                            Log.d("inside assort !update ",  " when false");
                            // if ref no is in FreeDeb
                            if (debCount > 0) {
                                Log.d("inside assort !update ",  " when debcount > 0");
                                // if debtor eligible for free issues
                                if (IsValidDeb == 1) {
                                    Log.d("inside assort !update ",  " when ValidDeb");
                                    if ((int) Math.round(entedTotQty / itemQty) > 0) {

                                        FreeItemDetails details = new FreeItemDetails();
                                        // details.setFreeItem(new
                                        // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
                                        details.setRefno(freeHed.getFFREEHED_REFNO());
                                        details.setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                        details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                        freeList.add(details);
                                        Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");
                                        entedTotQty = (int) Math.round(entedTotQty % itemQty);

                                    }
                                }

                                // if ref no is NOT in FreeDeb =>
                                // available for everyone
                            } else {

                                // IF entered qty/scheme qty is more
                                // than 0
                                if ((int) Math.round(entedTotQty / itemQty) > 0) {

                                    FreeItemDetails details = new FreeItemDetails();
                                    // details.setFreeItem(new
                                    // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
                                    details.setRefno(freeHed.getFFREEHED_REFNO());
                                    details.setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                    details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                    freeList.add(details);

                                    Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");
                                    entedTotQty = (int) Math.round(entedTotQty % itemQty);
                                }

                            }
                        }

                        for (FreeItemDetails freeItemDetails : freeList) {

                            if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {

                                freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
                                freeList.get(index).setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                freeList.get(index).setFreeQty((int) Math.round(flatAssort / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                assortUpdate = true;
                            }

                            index++;
                        }



                        // if not assorted
                    } else {

                        // if ref no is in FreeDeb
                        if (debCount > 0) {

                            // if debtor eligible for free issues
                            if (IsValidDeb == 1) {

                                if ((int) Math.round(entedTotQty / itemQty) > 0) {

                                    FreeItemDetails details = new FreeItemDetails();
                                    // details.setFreeItem(new
                                    // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
                                    details.setRefno(freeHed.getFFREEHED_REFNO());
                                    details.setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                    details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                    freeList.add(details);

                                    Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");
                                    entedTotQty = (int) Math.round(entedTotQty % itemQty);
                                }
                            }

                            // if ref no is NOT in FreeDeb
                        } else {

                            if ((int) Math.round(entedTotQty / itemQty) > 0) {

                                FreeItemDetails details = new FreeItemDetails();
                                // details.setFreeItem(new
                                // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
                                details.setRefno(freeHed.getFFREEHED_REFNO());
                                details.setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                freeList.add(details);

                                Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");
                                entedTotQty = (int) Math.round(entedTotQty % itemQty);
                            }
                        }
                    }
                } else if (freeHed.getFFREEHED_FTYPE().equals("Slab")) {
                    Log.d("Inside ", freeHed.getFFREEHED_REFNO() + "Slab");
                    FreeSlabController freeSlabDS = new FreeSlabController(context);
                    final ArrayList<FreeSlab> slabList;
                    int assortCount = new FreeDetController(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());
                    if (assortCount > 1) {

                        slabassorted = slabassorted + entedTotQty;
                        slabList = freeSlabDS.getSlabdetails(freeHed.getFFREEHED_REFNO(), slabassorted);

                    } else {

                        slabList = freeSlabDS.getSlabdetails(freeHed.getFFREEHED_REFNO(), entedTotQty);

                    }

                    for (FreeSlab freeSlab : slabList) {

                        String debCode = SharedPref.getInstance(context).getSelectedDebCode();
                        int debCount = new FreeDebController(context).getRefnoByDebCount(freeHed.getFFREEHED_REFNO());
                        int IsValidDeb = new FreeDebController(context).isValidDebForFreeIssue(freeHed.getFFREEHED_REFNO(), debCode);

                        if (debCount > 0) {// selected debtors

                            if (IsValidDeb == 1) {

                                if (assortCount > 1) {

                                    int index = 0;
                                    boolean assortUpdate = false;

                                    for (FreeItemDetails freeItemDetails : freeList) {
                                        if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {
                                            // details=freeItemDetails;
                                            freeList.get(index).setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
                                            freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
                                            freeList.get(index).setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                            assortUpdate = true;
                                        }

                                        index++;
                                    }

                                    if (!assortUpdate) {

                                        Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
                                        FreeItemDetails details = new FreeItemDetails();
                                        details.setRefno(freeHed.getFFREEHED_REFNO());
                                        details.setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                        details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
                                        freeList.add(details);
                                    }

                                } else {
                                    Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
                                    FreeItemDetails details = new FreeItemDetails();
                                    details.setRefno(freeHed.getFFREEHED_REFNO());
                                    details.setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                    details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
                                    freeList.add(details);
                                }
                            }

                        } else {// all debtor for freeissues

                            if (assortCount > 1) {
                                int index = 0;
                                boolean assortUpdate = false;
                                for (FreeItemDetails freeItemDetails : freeList) {
                                    if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {
                                        // details=freeItemDetails;
                                        freeList.get(index).setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
                                        freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
                                        freeList.get(index).setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                        assortUpdate = true;
                                    }

                                    index++;
                                }

                                if (!assortUpdate) {

                                    Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
                                    FreeItemDetails details = new FreeItemDetails();
                                    details.setRefno(freeHed.getFFREEHED_REFNO());
                                    details.setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                    details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
                                    freeList.add(details);
                                }

                            } else {
                                Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
                                FreeItemDetails details = new FreeItemDetails();
                                details.setRefno(freeHed.getFFREEHED_REFNO());
                                details.setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
                                freeList.add(details);
                            }
                        }
                    }
                } else if (freeHed.getFFREEHED_FTYPE().equals("Mix")) {
                    Log.d("Inside ", freeHed.getFFREEHED_REFNO() + " Mix");
                    FreeMslabController freeMslabDS = new FreeMslabController(context);
                    final ArrayList<FreeMslab> mixList;
                    int assortCount = new FreeDetController(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());
                    if (assortCount > 1) {// if assorted
                        mixAssort = mixAssort + entedTotQty;
                        mixList = freeMslabDS.getMixDetails(freeHed.getFFREEHED_REFNO(), mixAssort);
                        if(mixList.size() == 0){
                            mixAssort = 0;
                        }
                    } else {
                        mixList = freeMslabDS.getMixDetails(freeHed.getFFREEHED_REFNO(), entedTotQty);
                    }

                    for (FreeMslab freeMslab : mixList) {

                        String debCode = SharedPref.getInstance(context).getSelectedDebCode();
                        int debCount = new FreeDebController(context).getRefnoByDebCount(freeHed.getFFREEHED_REFNO());
                        int IsValidDeb = new FreeDebController(context).isValidDebForFreeIssue(freeHed.getFFREEHED_REFNO(), debCode);

                        if (debCount > 0) {// selected debtors

                            if (IsValidDeb == 1) {

                                if (assortCount > 1) {

                                    int index = 0;
                                    boolean assortUpdate = false;

                                    for (FreeItemDetails freeItemDetails : freeList) {
                                        if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {

                                            int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                            freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
                                            freeList.get(index).setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                            freeList.get(index).setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));

                                            assortUpdate = true;
                                        }
                                        index++;
                                    }

                                    if (!assortUpdate) {

                                        FreeItemDetails details = new FreeItemDetails();
                                        int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                        details.setRefno(freeHed.getFFREEHED_REFNO());
                                        details.setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                        details.setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
                                        freeList.add(details);
                                    }

                                } else {

                                    FreeItemDetails details = new FreeItemDetails();
                                    int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                    details.setRefno(freeHed.getFFREEHED_REFNO());
                                    details.setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                    details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
                                    freeList.add(details);

                                }
                            }

                        } else {// all debtor for freeissues

                            if (assortCount > 1) {
                                int index = 0;
                                boolean assortUpdate = false;
                                for (FreeItemDetails freeItemDetails : freeList) {
                                    if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {

                                        int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                        freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
                                        freeList.get(index).setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                        freeList.get(index).setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));

                                        assortUpdate = true;
                                    }

                                    index++;
                                }

                                if (!assortUpdate) {

                                    FreeItemDetails details = new FreeItemDetails();
                                    int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                    details.setRefno(freeHed.getFFREEHED_REFNO());
                                    details.setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                    details.setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
                                    freeList.add(details);
                                }

                            } else {
                                FreeItemDetails details = new FreeItemDetails();
                                int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                details.setRefno(freeHed.getFFREEHED_REFNO());
                                details.setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
                                freeList.add(details);
                            }
                        }
                    }
                }
            }//end free scheme loop
        }//end free items loop

           //     }

//            }else{
//                Toast.makeText(context,"cannot proceed free calculation for zero quantities",Toast.LENGTH_LONG).show();
//            }
       // }
        return freeList;

    }
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

}
