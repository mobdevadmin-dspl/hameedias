package com.datamation.swdsfa.freeissue;

import android.content.Context;
import android.util.Log;

import com.datamation.swdsfa.controller.DiscdetController;
import com.datamation.swdsfa.controller.DischedController;
import com.datamation.swdsfa.controller.DiscslabController;
import com.datamation.swdsfa.controller.FreeDebController;
import com.datamation.swdsfa.controller.FreeDetController;
import com.datamation.swdsfa.controller.FreeHedController;
import com.datamation.swdsfa.controller.FreeMslabController;
import com.datamation.swdsfa.controller.FreeSlabController;
import com.datamation.swdsfa.controller.InvDetController;
import com.datamation.swdsfa.controller.InvHedController;
import com.datamation.swdsfa.controller.OrderController;
import com.datamation.swdsfa.model.Discdeb;
import com.datamation.swdsfa.model.Disched;
import com.datamation.swdsfa.model.Discslab;
import com.datamation.swdsfa.model.FreeHed;
import com.datamation.swdsfa.model.FreeItemDetails;
import com.datamation.swdsfa.model.FreeMslab;
import com.datamation.swdsfa.model.FreeSlab;
import com.datamation.swdsfa.model.InvDet;
import com.datamation.swdsfa.model.OrderDetail;

import java.util.ArrayList;
import java.util.List;


public class FreeIssue {
    Context context;

    public FreeIssue(Context context) {
        this.context = context;
    }

    public ArrayList<FreeItemDetails> getFreeItemsByVanSalesItem(ArrayList<InvDet> invDets, String costCode) {
        // final return array list
        ArrayList<FreeItemDetails> freeList = new ArrayList<FreeItemDetails>();

        FreeHedController freeHedDS = new FreeHedController(context);
        for (InvDet invDet : invDets) {// ---------------------- order list
            // --------------------

            Log.v("fTranSODet", invDet.getFINVDET_ITEM_CODE());

            ArrayList<FreeHed> arrayList = freeHedDS.getFreeIssueItemDetailByRefno(invDet.getFINVDET_ITEM_CODE(), costCode);

            int entedTotQty = Integer.parseInt(invDet.getFINVDET_QTY());

            if (entedTotQty > 0) {

                for (FreeHed freeHed : arrayList) {// --------Related free issue
                    // list------

                    if (freeHed.getFFREEHED_PRIORITY().equals("1")) {
                        // flat
                        if (freeHed.getFFREEHED_FTYPE().equals("Flat")) {// -------Flat
                            // only-----

                            int itemQty = (int) Float.parseFloat(freeHed.getFFREEHED_ITEM_QTY());

                            // Log.v("Total Enter Qty", entedTotQty+"");
                            // Log.v("Free Per Qty", itemQty+"");
                            // Log.v("Free Items Qty / ", (int)
                            // Math.round(entedTotQty / itemQty)+"");
                            // Log.v("Free Items Qty % ", (int)
                            // Math.round(entedTotQty % itemQty)+"");

                            if ((int) Math.round(entedTotQty / itemQty) > 0) {

                                FreeItemDetails details = new FreeItemDetails();
                                // details.setFreeItem(new
                                // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
                                details.setRefno(freeHed.getFFREEHED_REFNO());
                                details.setFreeIssueSelectedItem(invDet.getFINVDET_ITEM_CODE());
                                details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));

                                freeList.add(details);

                                Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");

                                entedTotQty = (int) Math.round(entedTotQty % itemQty);
                            }

                        }
                    }
                }
            }
        }

        return freeList;

    }

    public ArrayList<ArrayList<InvDet>> SortInvoiceDiscount(ArrayList<InvDet> newOrderList) {

        DischedController discHeadDS = new DischedController(context);
        // sort assort item list
        ArrayList<ArrayList<InvDet>> MetaTranSODetList = sortInvoiceAssortDiscount(newOrderList);
        ArrayList<ArrayList<InvDet>> newMetaList = new ArrayList<ArrayList<InvDet>>();

        /* For each ArrayList inside meta ArrayList "MetaTranSODetList" */
        for (ArrayList<InvDet> ordArrList : MetaTranSODetList) {

            /* For each TranSODet object inside ordeArrList ArrayList */
            for (InvDet mInvDet : ordArrList) {

                Disched discHed = discHeadDS.getSchemeByItemCode(mInvDet.getFINVDET_ITEM_CODE());

                /* If discount scheme exists */
                if (discHed.getFDISCHED_DIS_TYPE() != null) {

                    ArrayList<Discdeb> discDebList = discHeadDS.getDebterList(discHed.getFDISCHED_REF_NO());

                    /* Found debtors */
                    if (discDebList.size() >= 1) {
                        /* Get debtor code from order header by order ref no */
                        String OrdHedRefNo = new InvHedController(context).getRefnoByDebcode(mInvDet.getFINVDET_REFNO());
                        /* Going through the list for a match */
                        for (Discdeb discDeb : discDebList) {
                            /* If match found */
                            if (discDeb.getFDISCDEB_REF_NO().toString().equals(OrdHedRefNo)) {

                                /* Discount type: value based */
                                if (discHed.getFDISCHED_DIS_TYPE().equals("V")) {
                                    /* If assorted exist */

                                    double TotalValue = 0;
                                    int totalQTY = 0;
                                    if (ordArrList.size() > 1) {
                                        /*
                                         * If assorted then, get total qty and
                                         * total value of assorted list
                                         */
                                        for (InvDet invDet : ordArrList) {
                                            TotalValue = TotalValue + (Double.parseDouble(invDet.getFINVDET_AMT()) + Double.parseDouble(invDet.getFINVDET_DISVALAMT()));

                                            totalQTY = totalQTY + Integer.parseInt(invDet.getFINVDET_QTY());
                                        }

                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
                                        /* if any discount slabs exist */
                                        if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {
                                            /*
                                             * Get discount value & pass it to
                                             * first instance and add it to meta
                                             * list
                                             */
                                            double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
                                            mInvDet.setFINVDET_B_AMT(String.valueOf(discValue));
                                            mInvDet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                            mInvDet.setFINVDET_SCHDISPER("0.00");
                                            mInvDet.setFINVDET_DISCTYPE("V");
                                            newMetaList.add(ordArrList);
                                            Log.v("DISCOUNT", mInvDet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                            break;
                                        }
                                        /* If not assorted */
                                    } else {
                                        totalQTY = Integer.parseInt(mInvDet.getFINVDET_QTY());
                                        TotalValue = Double.parseDouble(mInvDet.getFINVDET_AMT()) + Double.parseDouble(mInvDet.getFINVDET_DISVALAMT());
                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                        if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {
                                            double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
                                            /*
                                             * Update table directly cuz it's
                                             * not assorted
                                             */
                                            mInvDet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                            mInvDet.setFINVDET_DIS_AMT(String.valueOf(discValue));
                                            mInvDet.setFINVDET_SCHDISPER("0.00");
                                            new InvDetController(context).updateDiscount(mInvDet, discValue, "V");
                                            Log.v("DISCOUNT", mInvDet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                        }

                                    }

                                }
                                /* if percentage based discount */
                                else if (discHed.getFDISCHED_DIS_TYPE().equals("P")) {
                                    int totalQTY = 0;
                                    double TotalValue = 0;

                                    if (ordArrList.size() > 1) {

                                        for (InvDet iTranSODet : ordArrList) {
                                            totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFINVDET_QTY());
                                            TotalValue = TotalValue + ((Double.parseDouble(iTranSODet.getFINVDET_AMT()) + Double.parseDouble(iTranSODet.getFINVDET_DISVALAMT())));

                                        }

                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
                                        /* if any discount slabs exist */
                                        if (discSlab.getFDISCSLAB_DIS_PER() != null) {
                                            /*
                                             * Get discount value & pass it to
                                             * first instance and add it to meta
                                             * list
                                             */
                                            double discValue = Math.round((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                            mInvDet.setFINVDET_B_AMT(String.valueOf(discValue));
                                            mInvDet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                            mInvDet.setFINVDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
                                            mInvDet.setFINVDET_DISCTYPE("P");

                                            newMetaList.add(ordArrList);
                                            Log.v("DISCOUNT", mInvDet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                            break;
                                        }

                                    } else { /*
                                     * If just 1 item, just
                                     * calculate the discount
                                     * according to percentage
                                     */
                                        totalQTY = Integer.parseInt(mInvDet.getFINVDET_QTY());
                                        TotalValue = Double.parseDouble(mInvDet.getFINVDET_AMT()) + Double.parseDouble(mInvDet.getFINVDET_DISVALAMT());

                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                        if (discSlab.getFDISCSLAB_DIS_PER() != null) {
                                            double discValue = Math.round((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                            /*
                                             * Update table directly cuz it's
                                             * not assorted
                                             */
                                            mInvDet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                            mInvDet.setFINVDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
                                            mInvDet.setFINVDET_DIS_AMT(String.valueOf(Math.round((TotalValue / 100) * Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER()))));
                                            new InvDetController(context).updateDiscount(mInvDet, discValue, "P");
                                            Log.v("DISCOUNT", mInvDet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                        }

                                    }

                                }

                            }
                        }

                    } else {/* No debtors found */

                        /* Discount type: value based */
                        if (discHed.getFDISCHED_DIS_TYPE().equals("V")) {
                            /* If assorted exist */

                            double TotalValue = 0;
                            int totalQTY = 0;
                            if (ordArrList.size() > 1) {
                                /*
                                 * if assorted then, get total qty and total
                                 * value of assorted list
                                 */
                                for (InvDet iTranSODet : ordArrList) {
                                    TotalValue = TotalValue + (Double.parseDouble(iTranSODet.getFINVDET_AMT()) + Double.parseDouble(iTranSODet.getFINVDET_DISVALAMT()));
                                    totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFINVDET_QTY());
                                }

                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
                                /* if any discount slabs exist */
                                if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {
                                    /*
                                     * Get discount value & pass it to first
                                     * instance and add it to meta list
                                     */
                                    double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
                                    mInvDet.setFINVDET_B_AMT(String.valueOf(discValue));
                                    mInvDet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                    mInvDet.setFINVDET_SCHDISPER("0.00");
                                    mInvDet.setFINVDET_DISCTYPE("V");

                                    newMetaList.add(ordArrList);
                                    Log.v("DISCOUNT", mInvDet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                    break;
                                }
                                /* If not assorted */
                            } else {
                                totalQTY = Integer.parseInt(mInvDet.getFINVDET_QTY());
                                TotalValue = Double.parseDouble(mInvDet.getFINVDET_AMT()) + Double.parseDouble(mInvDet.getFINVDET_DISVALAMT());
                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {
                                    double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
                                    /*
                                     * Update table directly as it's not
                                     * assorted
                                     */
                                    mInvDet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                    mInvDet.setFINVDET_DIS_AMT(String.valueOf(discValue));
                                    mInvDet.setFINVDET_SCHDISPER("0.00");
                                    new InvDetController(context).updateDiscount(mInvDet, discValue, "V");
                                    Log.v("DISCOUNT", mInvDet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                }

                            }

                        }
                        /* if percentage based discount */
                        else if (discHed.getFDISCHED_DIS_TYPE().equals("P")) {
                            int totalQTY = 0;
                            double TotalValue = 0;

                            if (ordArrList.size() > 1) {

                                for (InvDet iTranSODet : ordArrList) {
                                    totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFINVDET_QTY());
                                    TotalValue = TotalValue + ((Double.parseDouble(iTranSODet.getFINVDET_AMT()) + Double.parseDouble(iTranSODet.getFINVDET_DISVALAMT())));

                                }

                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
                                /* if any discount slabs exist */
                                if (discSlab.getFDISCSLAB_DIS_PER() != null) {
                                    /*
                                     * Get discount value & pass it to first
                                     * instance and add it to meta list
                                     */
                                    double discValue = Math.round((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                    mInvDet.setFINVDET_B_AMT(String.valueOf(discValue));
                                    mInvDet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                    mInvDet.setFINVDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
                                    mInvDet.setFINVDET_DISCTYPE("P");

                                    newMetaList.add(ordArrList);
                                    Log.v("DISCOUNT", mInvDet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                    break;
                                }

                            } else { /*
                             * If just 1 item, just calculate the
                             * discount according to percentage
                             */
                                totalQTY = Integer.parseInt(mInvDet.getFINVDET_QTY());

                                TotalValue = Double.parseDouble(mInvDet.getFINVDET_AMT()) + Double.parseDouble(mInvDet.getFINVDET_DISVALAMT());
                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                if (discSlab.getFDISCSLAB_DIS_PER() != null) {
                                    double discValue = Math.round((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                    /*
                                     * Update table directly as it's not
                                     * assorted
                                     */
                                    mInvDet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                    mInvDet.setFINVDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
                                    mInvDet.setFINVDET_DIS_AMT(String.valueOf(Math.round((TotalValue / 100) * Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER()))));
                                    new InvDetController(context).updateDiscount(mInvDet, discValue, "P");
                                    Log.v("DISCOUNT", mInvDet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                }

                            }

                        }

                    }

                }

            }

        }
        return newMetaList;

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<ArrayList<InvDet>> sortInvoiceAssortDiscount(ArrayList<InvDet> OrderList) {

        DiscdetController discDetDS = new DiscdetController(context);
        ArrayList<ArrayList<InvDet>> nodeList = new ArrayList<ArrayList<InvDet>>();
        List<String> AssortList = new ArrayList<String>();

        /* Looping through the order items */
        for (int x = 0; x <= OrderList.size() - 1; x++) {

            ArrayList<InvDet> mTranSODet = new ArrayList<InvDet>();
            /* If checked already, don't check it again */
            if (!OrderList.get(x).isFLAG()) {
                mTranSODet.add(OrderList.get(x));
                /* Get assort item list for a item code */
                AssortList = discDetDS.getAssortByItemCode(OrderList.get(x).getFINVDET_ITEM_CODE());

                if (AssortList.size() > 0) {
                    for (int y = x + 1; y <= OrderList.size() - 1; y++) {
                        for (int i = 0; i <= AssortList.size() - 1; i++) {
                            /* If found a matching item */
                            if (OrderList.get(y).getFINVDET_ITEM_CODE().equals(AssortList.get(i))) {
                                /* Add it to Array list */
                                mTranSODet.add(OrderList.get(y));
                                /*
                                 * Set flag to show this item has been checked
                                 * already
                                 */
                                OrderList.get(y).setFLAG(true);
                            }
                        }
                    }
                    nodeList.add(mTranSODet); /* Add item to 2D ArrayList */
                }
            }
        }

        return nodeList;

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<FreeItemDetails> getFreeItemsByInvoiceItem(ArrayList<InvDet> newOrderList, String costCode) {

        ArrayList<FreeItemDetails> freeList = new ArrayList<FreeItemDetails>();
        int slabassorted = 0, flatAssort = 0, mixAssort = 0;
        FreeHedController freeHedDS = new FreeHedController(context);

        ArrayList<InvDet> dets = sortInvoiceAssortItems(newOrderList);

        for (InvDet det : dets) {// ---------------------- order
            // list----------------------

            // crpb00001
            Log.v("FINVDET", det.getFINVDET_ITEM_CODE());
            // get record from freeHed about valid scheme
            ArrayList<FreeHed> arrayList = freeHedDS.getFreeIssueItemDetailByRefno(det.getFINVDET_ITEM_CODE(), costCode);
            // selected item qty
            int entedTotQty = Integer.parseInt(det.getFINVDET_QTY());

            if (entedTotQty > 0) {

                for (FreeHed freeHed : arrayList) {// --------Related free issue
                    // list------

                    if (freeHed.getFFREEHED_PRIORITY().equals("1")) {
                        // flat
                        if (freeHed.getFFREEHED_FTYPE().equals("Flat")) {// -------Flat
                            // only-----
                            // flat scheme item qty => 12:1
                            int itemQty = (int) Float.parseFloat(freeHed.getFFREEHED_ITEM_QTY());
                            // Debtor code from order header ref no ORASA/00006
                            String debCode = new OrderController(context).getRefnoByDebcode(det.getFINVDET_REFNO());
                            // get debtor count from FIS/098 no
                            int debCount = new FreeDebController(context).getRefnoByDebCount(freeHed.getFFREEHED_REFNO());
                            // select debtor from FIS no & R0001929
                            int IsValidDeb = new FreeDebController(context).isValidDebForFreeIssue(freeHed.getFFREEHED_REFNO(), debCode);
                            // get assort count from FIS no
                            int assortCount = new FreeDetController(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());

                            // if its assorted
                            if (assortCount > 1) {

                                flatAssort = flatAssort + entedTotQty;

                                int index = 0;
                                boolean assortUpdate = false;

                                for (FreeItemDetails freeItemDetails : freeList) {

                                    if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {

                                        freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
                                        freeList.get(index).setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                        freeList.get(index).setFreeQty((int) Math.round(flatAssort / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                        assortUpdate = true;
                                    }

                                    index++;
                                }

                                if (!assortUpdate) {// When 1st time running

                                    // if ref no is in FreeDeb
                                    if (debCount > 0) {

                                        // if debtor eligible for free issues
                                        if (IsValidDeb == 1) {

                                            if ((int) Math.round(entedTotQty / itemQty) > 0) {

                                                FreeItemDetails details = new FreeItemDetails();
                                                // details.setFreeItem(new
                                                // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
                                                details.setRefno(freeHed.getFFREEHED_REFNO());
                                                details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
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
                                            details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                            details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                            freeList.add(details);

                                            Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");
                                            entedTotQty = (int) Math.round(entedTotQty % itemQty);
                                        }

                                    }
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
                                            details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
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
                                        details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                        details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                        freeList.add(details);

                                        Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");
                                        entedTotQty = (int) Math.round(entedTotQty % itemQty);
                                    }
                                }
                            }

                        } else if (freeHed.getFFREEHED_FTYPE().equals("Slab")) {// -------Slab
                            // only-----

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

                                String debCode = new InvHedController(context).getRefnoByDebcode(det.getFINVDET_REFNO());
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
                                                    freeList.get(index).setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                                    assortUpdate = true;
                                                }

                                                index++;
                                            }

                                            if (!assortUpdate) {

                                                Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
                                                FreeItemDetails details = new FreeItemDetails();
                                                details.setRefno(freeHed.getFFREEHED_REFNO());
                                                details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                                details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
                                                freeList.add(details);
                                            }

                                        } else {
                                            Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
                                            FreeItemDetails details = new FreeItemDetails();
                                            details.setRefno(freeHed.getFFREEHED_REFNO());
                                            details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
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
                                                freeList.get(index).setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                                assortUpdate = true;
                                            }

                                            index++;
                                        }

                                        if (!assortUpdate) {

                                            Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
                                            FreeItemDetails details = new FreeItemDetails();
                                            details.setRefno(freeHed.getFFREEHED_REFNO());
                                            details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                            details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
                                            freeList.add(details);
                                        }

                                    } else {
                                        Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
                                        FreeItemDetails details = new FreeItemDetails();
                                        details.setRefno(freeHed.getFFREEHED_REFNO());
                                        details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                        details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
                                        freeList.add(details);
                                    }
                                }
                            }

                        } else if (freeHed.getFFREEHED_FTYPE().equals("Mix")) {
                            // slabAssort

                            FreeMslabController freeMslabDS = new FreeMslabController(context);
                            final ArrayList<FreeMslab> mixList;
                            int assortCount = new FreeDetController(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());
                            if (assortCount > 1) {// if assorted
                                mixAssort = mixAssort + entedTotQty;
                                mixList = freeMslabDS.getMixDetails(freeHed.getFFREEHED_REFNO(), mixAssort);
                            } else {
                                mixList = freeMslabDS.getMixDetails(freeHed.getFFREEHED_REFNO(), entedTotQty);
                            }

                            for (FreeMslab freeMslab : mixList) {

                                String debCode = new OrderController(context).getRefnoByDebcode(det.getFINVDET_REFNO());
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
                                                    freeList.get(index).setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                                    freeList.get(index).setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));

                                                    assortUpdate = true;
                                                }
                                                index++;
                                            }

                                            if (!assortUpdate) {

                                                FreeItemDetails details = new FreeItemDetails();
                                                int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                                details.setRefno(freeHed.getFFREEHED_REFNO());
                                                details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                                details.setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
                                                freeList.add(details);
                                            }

                                        } else {

                                            FreeItemDetails details = new FreeItemDetails();
                                            int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                            details.setRefno(freeHed.getFFREEHED_REFNO());
                                            details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
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
                                                freeList.get(index).setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                                freeList.get(index).setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));

                                                assortUpdate = true;
                                            }

                                            index++;
                                        }

                                        if (!assortUpdate) {

                                            FreeItemDetails details = new FreeItemDetails();
                                            int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                            details.setRefno(freeHed.getFFREEHED_REFNO());
                                            details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                            details.setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
                                            freeList.add(details);
                                        }

                                    } else {
                                        FreeItemDetails details = new FreeItemDetails();
                                        int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                        details.setRefno(freeHed.getFFREEHED_REFNO());
                                        details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                        details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
                                        freeList.add(details);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return freeList;

    }


    //-----------------------------dhnaushika---------------------------------------------------------------------------------
    public ArrayList<FreeItemDetails> getEligibleFreeItemsByInvoiceItem(InvDet det, String costCode) {

        ArrayList<FreeItemDetails> freeList = new ArrayList<FreeItemDetails>();
        int slabassorted = 0, flatAssort = 0, mixAssort = 0;
        FreeHedController freeHedDS = new FreeHedController(context);

        //   ArrayList<InvDet> dets = sortInvoiceAssortItems(newOrderList);

        //  for (InvDet det : newOrderList) {// ---------------------- order
        // list----------------------

        // crpb00001
        Log.v("FINVDET", det.getFINVDET_ITEM_CODE());
        // get record from freeHed about valid scheme
        ArrayList<FreeHed> arrayList = freeHedDS.getFreeIssueItemDetailByRefno(det.getFINVDET_ITEM_CODE(), costCode);
        // selected item qty
        int entedTotQty = Integer.parseInt(det.getFINVDET_QTY());

        if (entedTotQty > 0) {

            for (FreeHed freeHed : arrayList) {// --------Related free issue
                // list------

                if (freeHed.getFFREEHED_PRIORITY().equals("1")) {
                    // flat
                    if (freeHed.getFFREEHED_FTYPE().equals("Flat")) {// -------Flat
                        // only-----
                        // flat scheme item qty => 12:1
                        int itemQty = (int) Float.parseFloat(freeHed.getFFREEHED_ITEM_QTY());
                        // Debtor code from order header ref no ORASA/00006
                        String debCode = new OrderController(context).getRefnoByDebcode(det.getFINVDET_REFNO());
                        // get debtor count from FIS/098 no
                        int debCount = new FreeDebController(context).getRefnoByDebCount(freeHed.getFFREEHED_REFNO());
                        // select debtor from FIS no & R0001929
                        int IsValidDeb = new FreeDebController(context).isValidDebForFreeIssue(freeHed.getFFREEHED_REFNO(), debCode);
                        // get assort count from FIS no
                        int assortCount = new FreeDetController(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());

                        // if its assorted
                        if (assortCount > 1) {

                            flatAssort = flatAssort + entedTotQty;

                            int index = 0;
                            boolean assortUpdate = false;

                            for (FreeItemDetails freeItemDetails : freeList) {

                                if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {

                                    freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
                                    freeList.get(index).setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                    freeList.get(index).setFreeQty((int) Math.round(flatAssort / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                    assortUpdate = true;
                                }

                                index++;
                            }

                            if (!assortUpdate) {// When 1st time running

                                // if ref no is in FreeDeb
                                if (debCount > 0) {

                                    // if debtor eligible for free issues
                                    if (IsValidDeb == 1) {

                                        if ((int) Math.round(entedTotQty / itemQty) > 0) {

                                            FreeItemDetails details = new FreeItemDetails();
                                            // details.setFreeItem(new
                                            // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
                                            details.setRefno(freeHed.getFFREEHED_REFNO());
                                            details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
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
                                        details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                        details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                        freeList.add(details);

                                        Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");
                                        entedTotQty = (int) Math.round(entedTotQty % itemQty);
                                    }

                                }
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
                                        details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
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
                                    details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                    details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                    freeList.add(details);

                                    Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");
                                    entedTotQty = (int) Math.round(entedTotQty % itemQty);
                                }
                            }
                        }

                    } else if (freeHed.getFFREEHED_FTYPE().equals("Slab")) {// -------Slab
                        // only-----

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

                            String debCode = new InvHedController(context).getRefnoByDebcode(det.getFINVDET_REFNO());
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
                                                freeList.get(index).setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                                assortUpdate = true;
                                            }

                                            index++;
                                        }

                                        if (!assortUpdate) {

                                            Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
                                            FreeItemDetails details = new FreeItemDetails();
                                            details.setRefno(freeHed.getFFREEHED_REFNO());
                                            details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                            details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
                                            freeList.add(details);
                                        }

                                    } else {
                                        Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
                                        FreeItemDetails details = new FreeItemDetails();
                                        details.setRefno(freeHed.getFFREEHED_REFNO());
                                        details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
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
                                            freeList.get(index).setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                            assortUpdate = true;
                                        }

                                        index++;
                                    }

                                    if (!assortUpdate) {

                                        Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
                                        FreeItemDetails details = new FreeItemDetails();
                                        details.setRefno(freeHed.getFFREEHED_REFNO());
                                        details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                        details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
                                        freeList.add(details);
                                    }

                                } else {
                                    Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
                                    FreeItemDetails details = new FreeItemDetails();
                                    details.setRefno(freeHed.getFFREEHED_REFNO());
                                    details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                    details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
                                    freeList.add(details);
                                }
                            }
                        }

                    } else if (freeHed.getFFREEHED_FTYPE().equals("Mix")) {
                        // slabAssort

                        FreeMslabController freeMslabDS = new FreeMslabController(context);
                        final ArrayList<FreeMslab> mixList;
                        int assortCount = new FreeDetController(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());
                        if (assortCount > 1) {// if assorted
                            mixAssort = mixAssort + entedTotQty;
                            mixList = freeMslabDS.getMixDetails(freeHed.getFFREEHED_REFNO(), mixAssort);
                        } else {
                            mixList = freeMslabDS.getMixDetails(freeHed.getFFREEHED_REFNO(), entedTotQty);
                        }

                        for (FreeMslab freeMslab : mixList) {

                            String debCode = new OrderController(context).getRefnoByDebcode(det.getFINVDET_REFNO());
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
                                                freeList.get(index).setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                                freeList.get(index).setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));

                                                assortUpdate = true;
                                            }
                                            index++;
                                        }

                                        if (!assortUpdate) {

                                            FreeItemDetails details = new FreeItemDetails();
                                            int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                            details.setRefno(freeHed.getFFREEHED_REFNO());
                                            details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                            details.setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
                                            freeList.add(details);
                                        }

                                    } else {

                                        FreeItemDetails details = new FreeItemDetails();
                                        int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                        details.setRefno(freeHed.getFFREEHED_REFNO());
                                        details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
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
                                            freeList.get(index).setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                            freeList.get(index).setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));

                                            assortUpdate = true;
                                        }

                                        index++;
                                    }

                                    if (!assortUpdate) {

                                        FreeItemDetails details = new FreeItemDetails();
                                        int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                        details.setRefno(freeHed.getFFREEHED_REFNO());
                                        details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                        details.setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
                                        freeList.add(details);
                                    }

                                } else {
                                    FreeItemDetails details = new FreeItemDetails();
                                    int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
                                    details.setRefno(freeHed.getFFREEHED_REFNO());
                                    details.setFreeIssueSelectedItem(det.getFINVDET_ITEM_CODE());
                                    details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
                                    freeList.add(details);
                                }
                            }
                        }
                    }
                }
            }
        }
        // }
        return freeList;

    }


    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public ArrayList<InvDet> sortInvoiceAssortItems(ArrayList<InvDet> OrderList) {

        FreeHedController freeHedDS = new FreeHedController(context);
        FreeDetController freeDetDS = new FreeDetController(context);
        ArrayList<InvDet> newOrderList = new ArrayList<InvDet>();
        List<String> AssortList = new ArrayList<String>();

        for (int x = 0; x <= OrderList.size() - 1; x++) {
            // get Assort list for each Item code
            AssortList = freeDetDS.getAssortByRefno(freeHedDS.getRefoByItemCode(OrderList.get(x).getFINVDET_ITEM_CODE()));

            if (AssortList.size() > 0) {

                for (int y = x + 1; y <= OrderList.size() - 1; y++) {

                    for (int i = 0; i <= AssortList.size() - 1; i++) {

                        if (OrderList.get(y).getFINVDET_ITEM_CODE().equals(AssortList.get(i))) {

                            OrderList.get(x).setFINVDET_QTY(String.valueOf(Integer.parseInt(OrderList.get(x).getFINVDET_QTY()) + Integer.parseInt(OrderList.get(y).getFINVDET_QTY())));
                            OrderList.get(y).setFINVDET_QTY("0");
                        }

                    }
                }
            }
        }

        // Remove zero quantities from Arraylist
        for (int i = 0; i <= OrderList.size() - 1; i++) {

            if (!OrderList.get(i).getFINVDET_QTY().equals("0")) {

                newOrderList.add(OrderList.get(i));
            }

        }

        return newOrderList;

    }
//-----------------------------------------getEligible Free Issue product for popup dialog box--------------------------------------------------


//    public ArrayList<FreeItemDetails> getEligibleFreeItemsBySalesItem(TranSODet det, String costCode) {
//
//        ArrayList<FreeItemDetails> freeList = new ArrayList<FreeItemDetails>();
//        int slabassorted = 0, flatAssort = 0, mixAssort = 0;
//        FreeHedDS freeHedDS = new FreeHedDS(context);
//
//        //    ArrayList<TranSODet> dets = sortAssortItems(newOrderList);
//
//        // for (TranSODet det : dets) {// ---------------------- order
//        // list----------------------
//
//        // crpb00001
//        Log.v("fTranSODet", det.getFTRANSODET_ITEMCODE());
//        // get record from freeHed about valid scheme
//        ArrayList<FreeHed> arrayList = freeHedDS.getFreeIssueItemDetailByRefno(det.getFTRANSODET_ITEMCODE(), costCode);
//        // selected item qty
//        int entedTotQty = Integer.parseInt(det.getFTRANSODET_QTY());
//
//        if (entedTotQty > 0) {
//
//            for (FreeHed freeHed : arrayList) {// --------Related free issue
//                // list------
//
//                if (freeHed.getFFREEHED_PRIORITY().equals("1")) {
//                    // flat
//                    if (freeHed.getFFREEHED_FTYPE().equals("Flat")) {// -------Flat
//                        // only-----
//                        // flat scheme item qty => 12:1
//                        int itemQty = (int) Float.parseFloat(freeHed.getFFREEHED_ITEM_QTY());
//                        // Debtor code from order header ref no ORASA/00006
//                        String debCode = new TranSOHedDS(context).getRefnoByDebcode(det.getFTRANSODET_REFNO());
//                        // get debtor count from FIS/098 no
//                        int debCount = new FreeDebDS(context).getRefnoByDebCount(freeHed.getFFREEHED_REFNO());
//                        // select debtor from FIS no & R0001929
//                        int IsValidDeb = new FreeDebDS(context).isValidDebForFreeIssue(freeHed.getFFREEHED_REFNO(), debCode);
//                        // get assort count from FIS no
//                        int assortCount = new FreeDetDS(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());
//
//                        // if its assorted
//                        if (assortCount > 1) {
//
//                            flatAssort = flatAssort + entedTotQty;
//
//                            int index = 0;
//                            boolean assortUpdate = false;
//
//                            for (FreeItemDetails freeItemDetails : freeList) {
//
//                                if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {
//
//                                    freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
//                                    freeList.get(index).setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                    freeList.get(index).setFreeQty((int) Math.round(flatAssort / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
//                                    assortUpdate = true;
//                                }
//
//                                index++;
//                            }
//
//                            if (!assortUpdate) {// When 1st time running
//
//                                // if ref no is in FreeDeb
//                                if (debCount > 0) {
//
//                                    // if debtor eligible for free issues
//                                    if (IsValidDeb == 1) {
//
//                                        if ((int) Math.round(entedTotQty / itemQty) > 0) {
//
//                                            FreeItemDetails details = new FreeItemDetails();
//                                            // details.setFreeItem(new
//                                            // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
//                                            details.setRefno(freeHed.getFFREEHED_REFNO());
//                                            details.setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                            details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
//                                            freeList.add(details);
//                                            Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");
//                                            entedTotQty = (int) Math.round(entedTotQty % itemQty);
//
//                                        }
//                                    }
//
//                                    // if ref no is NOT in FreeDeb =>
//                                    // available for everyone
//                                } else {
//
//                                    // IF entered qty/scheme qty is more
//                                    // than 0
//                                    if ((int) Math.round(entedTotQty / itemQty) > 0) {
//
//                                        FreeItemDetails details = new FreeItemDetails();
//                                        // details.setFreeItem(new
//                                        // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
//                                        details.setRefno(freeHed.getFFREEHED_REFNO());
//                                        details.setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                        details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
//                                        freeList.add(details);
//
//                                        Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");
//                                        entedTotQty = (int) Math.round(entedTotQty % itemQty);
//                                    }
//
//                                }
//                            }
//
//                            // if not assorted
//                        } else {
//
//                            // if ref no is in FreeDeb
//                            if (debCount > 0) {
//
//                                // if debtor eligible for free issues
//                                if (IsValidDeb == 1) {
//
//                                    if ((int) Math.round(entedTotQty / itemQty) > 0) {
//
//                                        FreeItemDetails details = new FreeItemDetails();
//                                        // details.setFreeItem(new
//                                        // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
//                                        details.setRefno(freeHed.getFFREEHED_REFNO());
//                                        details.setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                        details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
//                                        freeList.add(details);
//
//                                        Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");
//                                        entedTotQty = (int) Math.round(entedTotQty % itemQty);
//                                    }
//                                }
//
//                                // if ref no is NOT in FreeDeb
//                            } else {
//
//                                if ((int) Math.round(entedTotQty / itemQty) > 0) {
//
//                                    FreeItemDetails details = new FreeItemDetails();
//                                    // details.setFreeItem(new
//                                    // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
//                                    details.setRefno(freeHed.getFFREEHED_REFNO());
//                                    details.setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                    details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
//                                    freeList.add(details);
//
//                                    Log.v("Free Issues ", (int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()) + "");
//                                    entedTotQty = (int) Math.round(entedTotQty % itemQty);
//                                }
//                            }
//                        }
//
//                    } else if (freeHed.getFFREEHED_FTYPE().equals("Slab")) {// -------Slab
//                        // only-----
//
//                        FreeSlabDS freeSlabDS = new FreeSlabDS(context);
//                        final ArrayList<FreeSlab> slabList;
//                        int assortCount = new FreeDetDS(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());
//                        if (assortCount > 1) {
//
//                            slabassorted = slabassorted + entedTotQty;
//                            slabList = freeSlabDS.getSlabdetails(freeHed.getFFREEHED_REFNO(), slabassorted);
//
//                        } else {
//
//                            slabList = freeSlabDS.getSlabdetails(freeHed.getFFREEHED_REFNO(), entedTotQty);
//
//                        }
//
//                        for (FreeSlab freeSlab : slabList) {
//
//                            String debCode = new TranSOHedDS(context).getRefnoByDebcode(det.getFTRANSODET_REFNO());
//                            int debCount = new FreeDebDS(context).getRefnoByDebCount(freeHed.getFFREEHED_REFNO());
//                            int IsValidDeb = new FreeDebDS(context).isValidDebForFreeIssue(freeHed.getFFREEHED_REFNO(), debCode);
//
//                            if (debCount > 0) {// selected debtors
//
//                                if (IsValidDeb == 1) {
//
//                                    if (assortCount > 1) {
//
//                                        int index = 0;
//                                        boolean assortUpdate = false;
//
//                                        for (FreeItemDetails freeItemDetails : freeList) {
//                                            if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {
//                                                // details=freeItemDetails;
//                                                freeList.get(index).setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
//                                                freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
//                                                freeList.get(index).setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                                assortUpdate = true;
//                                            }
//
//                                            index++;
//                                        }
//
//                                        if (!assortUpdate) {
//
//                                            Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
//                                            FreeItemDetails details = new FreeItemDetails();
//                                            details.setRefno(freeHed.getFFREEHED_REFNO());
//                                            details.setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                            details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
//                                            freeList.add(details);
//                                        }
//
//                                    } else {
//                                        Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
//                                        FreeItemDetails details = new FreeItemDetails();
//                                        details.setRefno(freeHed.getFFREEHED_REFNO());
//                                        details.setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                        details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
//                                        freeList.add(details);
//                                    }
//                                }
//
//                            } else {// all debtor for freeissues
//
//                                if (assortCount > 1) {
//                                    int index = 0;
//                                    boolean assortUpdate = false;
//                                    for (FreeItemDetails freeItemDetails : freeList) {
//                                        if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {
//                                            // details=freeItemDetails;
//                                            freeList.get(index).setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
//                                            freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
//                                            freeList.get(index).setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                            assortUpdate = true;
//                                        }
//
//                                        index++;
//                                    }
//
//                                    if (!assortUpdate) {
//
//                                        Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
//                                        FreeItemDetails details = new FreeItemDetails();
//                                        details.setRefno(freeHed.getFFREEHED_REFNO());
//                                        details.setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                        details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
//                                        freeList.add(details);
//                                    }
//
//                                } else {
//                                    Log.v("Stab", freeSlab.getFFREESLAB_FREE_QTY());
//                                    FreeItemDetails details = new FreeItemDetails();
//                                    details.setRefno(freeHed.getFFREEHED_REFNO());
//                                    details.setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                    details.setFreeQty((int) (Float.parseFloat(freeSlab.getFFREESLAB_FREE_QTY())));
//                                    freeList.add(details);
//                                }
//                            }
//                        }
//
//                    } else if (freeHed.getFFREEHED_FTYPE().equals("Mix")) {
//                        // slabAssort
//
//                        FreeMslabDS freeMslabDS = new FreeMslabDS(context);
//                        final ArrayList<FreeMslab> mixList;
//                        int assortCount = new FreeDetDS(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());
//                        if (assortCount > 1) {// if assorted
//                            mixAssort = mixAssort + entedTotQty;
//                            mixList = freeMslabDS.getMixDetails(freeHed.getFFREEHED_REFNO(), mixAssort);
//                        } else {
//                            mixList = freeMslabDS.getMixDetails(freeHed.getFFREEHED_REFNO(), entedTotQty);
//                        }
//
//                        for (FreeMslab freeMslab : mixList) {
//
//                            String debCode = new TranSOHedDS(context).getRefnoByDebcode(det.getFTRANSODET_REFNO());
//                            int debCount = new FreeDebDS(context).getRefnoByDebCount(freeHed.getFFREEHED_REFNO());
//                            int IsValidDeb = new FreeDebDS(context).isValidDebForFreeIssue(freeHed.getFFREEHED_REFNO(), debCode);
//
//                            if (debCount > 0) {// selected debtors
//
//                                if (IsValidDeb == 1) {
//
//                                    if (assortCount > 1) {
//
//                                        int index = 0;
//                                        boolean assortUpdate = false;
//
//                                        for (FreeItemDetails freeItemDetails : freeList) {
//                                            if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {
//
//                                                int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
//                                                freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
//                                                freeList.get(index).setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                                freeList.get(index).setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
//
//                                                assortUpdate = true;
//                                            }
//                                            index++;
//                                        }
//
//                                        if (!assortUpdate) {
//
//                                            FreeItemDetails details = new FreeItemDetails();
//                                            int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
//                                            details.setRefno(freeHed.getFFREEHED_REFNO());
//                                            details.setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                            details.setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
//                                            freeList.add(details);
//                                        }
//
//                                    } else {
//
//                                        FreeItemDetails details = new FreeItemDetails();
//                                        int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
//                                        details.setRefno(freeHed.getFFREEHED_REFNO());
//                                        details.setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                        details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
//                                        freeList.add(details);
//
//                                    }
//                                }
//
//                            } else {// all debtor for freeissues
//
//                                if (assortCount > 1) {
//                                    int index = 0;
//                                    boolean assortUpdate = false;
//                                    for (FreeItemDetails freeItemDetails : freeList) {
//                                        if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {
//
//                                            int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
//                                            freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
//                                            freeList.get(index).setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                            freeList.get(index).setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
//
//                                            assortUpdate = true;
//                                        }
//
//                                        index++;
//                                    }
//
//                                    if (!assortUpdate) {
//
//                                        FreeItemDetails details = new FreeItemDetails();
//                                        int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
//                                        details.setRefno(freeHed.getFFREEHED_REFNO());
//                                        details.setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                        details.setFreeQty((int) Math.round(mixAssort / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
//                                        freeList.add(details);
//                                    }
//
//                                } else {
//                                    FreeItemDetails details = new FreeItemDetails();
//                                    int itemQty = (int) Float.parseFloat(freeMslab.getFFREEMSLAB_ITEM_QTY());
//                                    details.setRefno(freeHed.getFFREEHED_REFNO());
//                                    details.setFreeIssueSelectedItem(det.getFTRANSODET_ITEMCODE());
//                                    details.setFreeQty((int) Math.round(entedTotQty / itemQty) * (int) Float.parseFloat(freeMslab.getFFREEMSLAB_FREE_IT_QTY()));
//                                    freeList.add(details);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        // }
//        return freeList;
//
//    }
    /*
     * Categorize order items in to a separate array list, according to the
     * discount reference no
     */
public ArrayList<FreeItemDetails> getFreeItemsBySalesItem(ArrayList<OrderDetail> newOrderList, String costCode) {

    ArrayList<FreeItemDetails> freeList = new ArrayList<FreeItemDetails>();
    int slabassorted = 0, flatAssort = 0, mixAssort = 0;
    FreeHedController freeHedDS = new FreeHedController(context);
    FreeIssueModified modified = new FreeIssueModified(context);

    ArrayList<OrderDetail> dets = modified.filterFreeItemsFromOrder(newOrderList);// rashmi -2019-09-09 :

    for (OrderDetail det : dets) {// ---------------------- order
        // list----------------------

        // crpb00001
        Log.v("fTranSODet", det.getFORDERDET_ITEMCODE());
        // get record from freeHed about valid scheme
        ArrayList<FreeHed> arrayList = freeHedDS.getFreeIssueItemDetailByRefno(det.getFORDERDET_ITEMCODE(), costCode);
        // selected item qty
        int entedTotQty = Integer.parseInt(det.getFORDERDET_QTY());

        if (entedTotQty > 0) {

            for (FreeHed freeHed : arrayList) {// --------Related free issue
                // list------

                if (freeHed.getFFREEHED_PRIORITY().equals("1")) {
                    // flat
                    if (freeHed.getFFREEHED_FTYPE().equals("Flat")) {
                        // only-----
                        // flat scheme item qty => 12:1
                        int itemQty = (int) Float.parseFloat(freeHed.getFFREEHED_ITEM_QTY());
                        // Debtor code from order header ref no ORASA/00006
                        String debCode = new OrderController(context).getRefnoByDebcode(det.getFORDERDET_REFNO());
                        // get debtor count from FIS/098 no
                        int debCount = new FreeDebController(context).getRefnoByDebCount(freeHed.getFFREEHED_REFNO());
                        // select debtor from FIS no & R0001929
                        int IsValidDeb = new FreeDebController(context).isValidDebForFreeIssue(freeHed.getFFREEHED_REFNO(), debCode);
                        // get assort count from FIS no
                        int assortCount = new FreeDetController(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());

                        // if its assorted
                        if (assortCount > 1) {

                            flatAssort = flatAssort + entedTotQty;

                            int index = 0;
                            boolean assortUpdate = false;

                            for (FreeItemDetails freeItemDetails : freeList) {

                                if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {

                                    freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
                                    freeList.get(index).setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                    freeList.get(index).setFreeQty((int) Math.round(flatAssort / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                    assortUpdate = true;
                                }

                                index++;
                            }

                            if (!assortUpdate) {// When 1st time running

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

                    } else if (freeHed.getFFREEHED_FTYPE().equals("Slab")) {// -------Slab
                        // only-----

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

                            String debCode = new OrderController(context).getRefnoByDebcode(det.getFORDERDET_REFNO());
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

                                            Log.v("Slab", freeSlab.getFFREESLAB_FREE_QTY());
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
                        // slabAssort

                        FreeMslabController freeMslabDS = new FreeMslabController(context);
                        final ArrayList<FreeMslab> mixList;
                        int assortCount = new FreeDetController(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());
                        if (assortCount > 1) {// if assorted
                            mixAssort = mixAssort + entedTotQty;
                            mixList = freeMslabDS.getMixDetails(freeHed.getFFREEHED_REFNO(), mixAssort);
                        } else {
                            mixList = freeMslabDS.getMixDetails(freeHed.getFFREEHED_REFNO(), entedTotQty);
                        }

                        for (FreeMslab freeMslab : mixList) {

                            String debCode = new OrderController(context).getRefnoByDebcode(det.getFORDERDET_REFNO());
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
                }


            }
        }
    }
    return freeList;

}





    public ArrayList<FreeItemDetails> getEligibleFreeItemsBySalesItem(OrderDetail det, String costCode) {

        ArrayList<FreeItemDetails> freeList = new ArrayList<FreeItemDetails>();
        int slabassorted = 0, flatAssort = 0, mixAssort = 0;
        FreeHedController freeHedDS = new FreeHedController(context);

        //    ArrayList<TranSODet> dets = sortAssortItems(newOrderList);

        // for (TranSODet det : dets) {// ---------------------- order
        // list----------------------

        // crpb00001
        Log.v("fTranSODet", det.getFORDERDET_ITEMCODE());
        // get record from freeHed about valid scheme
        ArrayList<FreeHed> arrayList = freeHedDS.getFreeIssueItemDetailByRefno(det.getFORDERDET_ITEMCODE(), costCode);
        // selected item qty
        int entedTotQty = Integer.parseInt(det.getFORDERDET_QTY());

        if (entedTotQty > 0) {

            for (FreeHed freeHed : arrayList) {// --------Related free issue
                // list------

                if (freeHed.getFFREEHED_PRIORITY().equals("1")) {
                    // flat
                    if (freeHed.getFFREEHED_FTYPE().equals("Flat")) {// -------Flat
                        // only-----
                        // flat scheme item qty => 12:1
                        int itemQty = (int) Float.parseFloat(freeHed.getFFREEHED_ITEM_QTY());
                        // Debtor code from order header ref no ORASA/00006
                        String debCode = new OrderController(context).getRefnoByDebcode(det.getFORDERDET_REFNO());
                        // get debtor count from FIS/098 no
                        int debCount = new FreeDebController(context).getRefnoByDebCount(freeHed.getFFREEHED_REFNO());
                        // select debtor from FIS no & R0001929
                        int IsValidDeb = new FreeDebController(context).isValidDebForFreeIssue(freeHed.getFFREEHED_REFNO(), debCode);
                        // get assort count from FIS no
                        int assortCount = new FreeDetController(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());

                        // if its assorted
                        if (assortCount > 1) {

                            flatAssort = flatAssort + entedTotQty;

                            int index = 0;
                            boolean assortUpdate = false;

                            for (FreeItemDetails freeItemDetails : freeList) {

                                if (freeItemDetails.getRefno().equals(freeHed.getFFREEHED_REFNO())) {

                                    freeList.get(index).setRefno(freeHed.getFFREEHED_REFNO());
                                    freeList.get(index).setFreeIssueSelectedItem(det.getFORDERDET_ITEMCODE());
                                    freeList.get(index).setFreeQty((int) Math.round(flatAssort / itemQty) * (int) Float.parseFloat(freeHed.getFFREEHED_FREE_IT_QTY()));
                                    assortUpdate = true;
                                }

                                index++;
                            }

                            if (!assortUpdate) {// When 1st time running

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

                    } else if (freeHed.getFFREEHED_FTYPE().equals("Slab")) {// -------Slab
                        // only-----

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

                            String debCode = new OrderController(context).getRefnoByDebcode(det.getFORDERDET_REFNO());
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
                        // slabAssort

                        FreeMslabController freeMslabDS = new FreeMslabController(context);
                        final ArrayList<FreeMslab> mixList;
                        int assortCount = new FreeDetController(context).getAssoCountByRefno(freeHed.getFFREEHED_REFNO());
                        if (assortCount > 1) {// if assorted
                            mixAssort = mixAssort + entedTotQty;
                            mixList = freeMslabDS.getMixDetails(freeHed.getFFREEHED_REFNO(), mixAssort);
                        } else {
                            mixList = freeMslabDS.getMixDetails(freeHed.getFFREEHED_REFNO(), entedTotQty);
                        }

                        for (FreeMslab freeMslab : mixList) {

                            String debCode = new OrderController(context).getRefnoByDebcode(det.getFORDERDET_REFNO());
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
                }
            }
        }
        // }
        return freeList;

    }
}
