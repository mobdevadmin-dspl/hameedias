package com.datamation.swdsfa.discount;

import android.content.Context;
import android.util.Log;

import com.datamation.swdsfa.controller.DiscdetController;
import com.datamation.swdsfa.controller.DischedController;
import com.datamation.swdsfa.controller.DiscslabController;
import com.datamation.swdsfa.controller.InvDetController;
import com.datamation.swdsfa.controller.OrderController;
import com.datamation.swdsfa.controller.OrderDetailController;
import com.datamation.swdsfa.controller.TaxDetController;
import com.datamation.swdsfa.model.Discdeb;
import com.datamation.swdsfa.model.Disched;
import com.datamation.swdsfa.model.Discslab;
import com.datamation.swdsfa.model.InvDet;
import com.datamation.swdsfa.model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class Discount {
    Context context;

    public Discount(Context context) {
        this.context = context;
    }

//    public ArrayList<ArrayList<OrderDetail>> SortDiscount(ArrayList<OrderDetail> newOrderList, String debCode) {
//
//        DischedController discHeadDS = new DischedController(context);
//        // sort assort item list
//        ArrayList<ArrayList<OrderDetail>> MetaTranSODetList = sortAssortDiscount(newOrderList);
//        ArrayList<ArrayList<OrderDetail>> newMetaList = new ArrayList<ArrayList<OrderDetail>>();
//
//        /* For each ArrayList inside meta ArrayList "MetaTranSODetList" */
//        for (ArrayList<OrderDetail> ordArrList : MetaTranSODetList) {
//
//            /* For each TranSODet object inside ordeArrList ArrayList */
//            for (OrderDetail mTranSODet : ordArrList) {
//
//                Disched discHed = discHeadDS.getSchemeByItemCode(mTranSODet.getFORDERDET_ITEMCODE());
//
//                /* If discount scheme exists */
//                if (discHed.getFDISCHED_DIS_TYPE() != null) {
//
//                    ArrayList<Discdeb> discDebList = discHeadDS.getDebterList(discHed.getFDISCHED_REF_NO());
//
//                    /* Found debtors */
//                    if (discDebList.size() >= 1) {
//                        /* Get debtor code from order header by order ref no */
//                        String OrdHedRefNo = new OrderController(context).getRefnoByDebcode(mTranSODet.getFORDERDET_REFNO());
//                        /* Going through the list for a match */
//                        for (Discdeb discDeb : discDebList) {
//                            /* If match found */
//                            if (discDeb.getFDISCDEB_REF_NO().equals(OrdHedRefNo)) {
//
//                                /* Discount type: value based */
//                                if (discHed.getFDISCHED_DIS_TYPE().equals("V")) {
//                                    /* If assorted exist */
//
//                                    double TotalValue = 0;
//                                    int totalQTY = 0;
//                                    if (ordArrList.size() > 1) {
//                                        /*
//                                         * If assorted then, get total qty and
//                                         * total value of assorted list
//                                         */
//                                        for (OrderDetail iTranSODet : ordArrList) {
//                                            TotalValue = TotalValue + (Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + Double.parseDouble(iTranSODet.getFORDERDET_SCHDISC()));
//                                            totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFORDERDET_QTY());
//                                        }
//
//                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
//
//                                        /* if any discount slabs exist */
//                                        if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {
//
//                                            double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
//                                            mTranSODet.setFORDERDET_BAMT(String.valueOf(discValue));
//                                            mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
//                                            mTranSODet.setFORDERDET_SCHDISPER("0.00");
//                                            mTranSODet.setFORDERDET_DISCTYPE("V");
//                                            newMetaList.add(ordArrList);
//                                            Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
//                                            break;
//                                        }
//
//                                        /* If not assorted */
//                                    } else {
//                                        totalQTY = Integer.parseInt(mTranSODet.getFORDERDET_QTY());
//                                        TotalValue = Double.parseDouble(mTranSODet.getFORDERDET_AMT()) + Double.parseDouble(mTranSODet.getFORDERDET_SCHDISC());
//                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
//
//                                        if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {
//
//                                            double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
//                                            mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
//                                            mTranSODet.setFORDERDET_DISAMT(String.valueOf(discValue));
//                                            mTranSODet.setFORDERDET_SCHDISPER("0.00");
//                                            new OrderDetailController(context).updateDiscount(mTranSODet, discValue, "V");
//                                            Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
//                                        }
//
//                                    }
//
//                                }
//                                /* if percentage based discount */
//                                else if (discHed.getFDISCHED_DIS_TYPE().equals("P")) {
//                                    int totalQTY = 0;
//                                    double TotalValue = 0;
//
//                                    if (ordArrList.size() > 1) {
//
//                                        for (OrderDetail iTranSODet : ordArrList) {
//                                            totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFORDERDET_QTY());
//                                            TotalValue = TotalValue + (Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + Double.parseDouble(iTranSODet.getFORDERDET_SCHDISC()));
////                                            if (iTranSODet.getFORDERDET_SCHDISC()== null || iTranSODet.getFORDERDET_SCHDISPER()== null) // null check for schdisc
////                                            {
////                                                TotalValue = TotalValue + (Double.parseDouble(iTranSODet.getFORDERDET_AMT()));
////                                            }
////                                            else
////                                            {
////                                                TotalValue = TotalValue + ((Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + (Double.parseDouble(iTranSODet.getFORDERDET_AMT())*(Double.parseDouble(iTranSODet.getFORDERDET_SCHDISPER())/100))));
////                                            }
//                                        }
//
//                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
//                                        /* if any discount slabs exist */
//                                        if (discSlab.getFDISCSLAB_DIS_PER() != null) {
//                                            /*
//                                             * Get discount value & pass it to
//                                             * first instance and add it to meta
//                                             * list
//                                             */
//                                            double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
//                                            mTranSODet.setFORDERDET_BAMT(String.valueOf(discValue));
//                                            mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
//                                            mTranSODet.setFORDERDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
//                                            mTranSODet.setFORDERDET_DISCTYPE("P");
//                                            newMetaList.add(ordArrList);
//                                            Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
//                                            break;
//                                        }
//
//                                    } else {
//                                        /* If just 1 item, just calculate the discount according to percentage */
//                                        totalQTY = Integer.parseInt(mTranSODet.getFORDERDET_QTY());
//                                        TotalValue = Double.parseDouble(mTranSODet.getFORDERDET_AMT()) + Double.parseDouble(mTranSODet.getFORDERDET_SCHDISC());
//                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
//
//                                        if (discSlab.getFDISCSLAB_DIS_PER() != null) {
//                                            /* Update table directly cuz it's not assorted */
//                                            double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
//                                            mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
//                                            mTranSODet.setFORDERDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
//                                            mTranSODet.setFORDERDET_DISAMT(String.valueOf(((TotalValue / 100) * Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER()))));
//                                            new OrderDetailController(context).updateDiscount(mTranSODet, discValue, "P");
//                                            Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                    } else {/* No debtors found */
//
//                        /* Discount type: value based */
//                        if (discHed.getFDISCHED_DIS_TYPE().equals("V")) {
//                            /* If assorted exist */
//
//                            double TotalValue = 0;
//                            int totalQTY = 0;
//                            if (ordArrList.size() > 1) {
//                                /*
//                                 * if assorted then, get total qty and total
//                                 * value of assorted list
//                                 */
//                                for (OrderDetail iTranSODet : ordArrList) {
//
//                                    TotalValue = TotalValue + (Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + Double.parseDouble(iTranSODet.getFORDERDET_SCHDISC()));
//                                    totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFORDERDET_QTY());
//                                }
//
//                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
//                                /* if any discount slabs exist */
//                                if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {
//                                    /*
//                                     * Get discount value & pass it to first
//                                     * instance and add it to meta list
//                                     */
//                                    double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
//                                    mTranSODet.setFORDERDET_BAMT(String.valueOf(discValue));
//                                    mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
//                                    mTranSODet.setFORDERDET_SCHDISPER("0.00");
//                                    mTranSODet.setFORDERDET_DISCTYPE("V");
//                                    newMetaList.add(ordArrList);
//                                    Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
//                                    break;
//                                }
//
//                                /* If not assorted */
//                            } else {
//
//                                totalQTY = Integer.parseInt(mTranSODet.getFORDERDET_QTY());
//
//                                TotalValue = Double.parseDouble(mTranSODet.getFORDERDET_AMT()) + Double.parseDouble(mTranSODet.getFORDERDET_SCHDISC());
//                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
//
//                                if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {
//                                    /*
//                                     * Update table directly as it's not
//                                     * assorted
//                                     */
//                                    double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
//                                    mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
//                                    mTranSODet.setFORDERDET_DISAMT(String.valueOf(discValue));
//                                    mTranSODet.setFORDERDET_SCHDISPER("0.00");
//                                    new OrderDetailController(context).updateDiscount(mTranSODet, discValue, "V");
//                                    Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
//                                }
//
//                            }
//
//                        }
//                        /* if percentage based discount */
//                        else if (discHed.getFDISCHED_DIS_TYPE().equals("P"))
//                        {
//                            int totalQTY = 0;
//                            double TotalValue = 0;
//                            String itemPrice;
//
//                            if (ordArrList.size() > 1)
//                            {
//                                for (OrderDetail iTranSODet : ordArrList)
//                                {
//                                    totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFORDERDET_QTY());
//                                    //TotalValue = TotalValue + ((Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + Double.parseDouble(iTranSODet.getFORDERDET_SCHDISC())));
//
//                                    if (iTranSODet.getFORDERDET_SCHDISC()== null || iTranSODet.getFORDERDET_SCHDISPER()== null) // null check for schdisc
//                                    {
//                                        TotalValue = TotalValue + (Double.parseDouble(iTranSODet.getFORDERDET_AMT()));
//                                    }
//                                    else
//                                    {
//                                        TotalValue = TotalValue + ((Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + (Double.parseDouble(iTranSODet.getFORDERDET_AMT())*(Double.parseDouble(iTranSODet.getFORDERDET_SCHDISPER())/100))));
//                                    }
//
////                                    Discslab discSlab = new DiscslabDS(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
////
////                                    iTranSODet.setFTRANSODET_DISCTYPE("P");
////                                    iTranSODet.setFTRANSODET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
////                                    iTranSODet.setFTRANSODET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
////
////                                    new TranSODetDS(context).updateDiscountSO(iTranSODet, 0.00, "P", debCode);
//                                }
//
//                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
//
//                                /* if any discount slabs exist */
//                                if (discSlab.getFDISCSLAB_DIS_PER() != null) {
//
//                                    /* Get discount value & pass it to first instance and add it to meta list */
//                                    double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
//                                    mTranSODet.setFORDERDET_BAMT(String.valueOf(discValue));
//                                    mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
//                                    mTranSODet.setFORDERDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
//                                    mTranSODet.setFORDERDET_DISCTYPE("P");
//                                    /* add to ArrayList as more than one item for dialog box selection */
//                                    newMetaList.add(ordArrList);
//                                    //new OrderDetailController(context).updateDiscountSO(mTranSODet, discValue, "P", debCode);
//                                    Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
//                                    break;
//                                }
//
//                            }
////                            if (ordArrList.size() > 1) {
////
////                                for (TranSODet iTranSODet : ordArrList)
////                                {
////                                    totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFTRANSODET_QTY());
////                                    TotalValue = TotalValue + ((Double.parseDouble(iTranSODet.getFTRANSODET_AMT()) + Double.parseDouble(iTranSODet.getFTRANSODET_SCHDISC())));
////                                }
////
////                                Discslab discSlab = new DiscslabDS(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
////
////								/* if any discount slabs exist */
////                                if (discSlab.getFDISCSLAB_DIS_PER() != null) {
////
////									/* Get discount value & pass it to first instance and add it to meta list */
////                                    double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
////                                    mTranSODet.setFTRANSODET_BAMT(String.valueOf(discValue));
////                                    mTranSODet.setFTRANSODET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
////                                    mTranSODet.setFTRANSODET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
////                                    mTranSODet.setFTRANSODET_DISCTYPE("P");
////									/* add to ArrayList as more than one item for dialog box selection */
////                                    newMetaList.add(ordArrList);
////                                    new TranSODetDS(context).updateDiscountSO(mTranSODet, discValue, "P", debCode);
////                                    Log.v("DISCOUNT", mTranSODet.getFTRANSODET_ITEMCODE() + " * " + (TotalValue - discValue));
////                                    break;
////                                }
////
////                            }
//                            else { /*
//                             * If just 1 item, just calculate the
//                             * discount according to percentage
//                             */
//                                totalQTY = Integer.parseInt(mTranSODet.getFORDERDET_QTY());
//
//                                if (mTranSODet.getFORDERDET_SCHDISC()== null && mTranSODet.getFORDERDET_SCHDISPER()== null) // null check for schdisc
//                                {
//                                    TotalValue = TotalValue + (Double.parseDouble(mTranSODet.getFORDERDET_AMT()));
//                                }
//                                else
//                                {
//                                    TotalValue = TotalValue + ((Double.parseDouble(mTranSODet.getFORDERDET_AMT()) + (Double.parseDouble(mTranSODet.getFORDERDET_AMT())*(Double.parseDouble(mTranSODet.getFORDERDET_SCHDISPER())/100))));
//                                }
//                                // commented due to null value for schdisc
//                                //TotalValue = Double.parseDouble(mTranSODet.getFORDERDET_AMT()) + Double.parseDouble(mTranSODet.getFORDERDET_SCHDISC());
//
//                                // ----------------------------------------- Nuwan --------------------------14/09/2018 ----------------Due to reverse tax for discount--------------------------
//                                itemPrice = String.valueOf(Double.parseDouble(mTranSODet.getFORDERDET_AMT())/(double)totalQTY);
//                                //String oneItemTaxReversePrice = new TaxDetDS(context).calculateReverseTaxFromDebTax(debCode,mTranSODet.getFTRANSODET_ITEMCODE(), new BigDecimal(itemPrice));
//                                String oneItemTaxReversePrice[] = new TaxDetController(context).calculateTaxForwardFromDebTax(debCode,mTranSODet.getFORDERDET_ITEMCODE(), Double.parseDouble(itemPrice));
//
//                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
//
//                                if (discSlab.getFDISCSLAB_DIS_PER() != null) {
//
//                                    /*Update table directly as it's not assorted*/
//                                    double reverseItemDiscValue = ((Double.parseDouble(oneItemTaxReversePrice[0]))/100 * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
//                                    double totalReverseDiscValue = reverseItemDiscValue * (double)totalQTY;
//                                    //double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
//                                    mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
//                                    mTranSODet.setFORDERDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
//                                    mTranSODet.setFORDERDET_DISAMT(String.valueOf(((TotalValue / 100) * Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER()))));
//
////                                    new TranSODetDS(context).updateDiscount(mTranSODet, discValue, "P");
////                                    Log.v("DISCOUNT", mTranSODet.getFTRANSODET_ITEMCODE() + " * " + (TotalValue - discValue));
//                                    //new OrderDetailController(context).updateDiscountSO(mTranSODet, totalReverseDiscValue, "P", debCode);
//                                    Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - totalReverseDiscValue));
//                                }
//
//                            }
//
//                        }
//
//                    }
//
//                }
//
//            }
//
//        }
//        return newMetaList;
//
//    }

    public ArrayList<ArrayList<OrderDetail>> SortDiscount(ArrayList<OrderDetail> newOrderList, String debCode) {

        DischedController discHeadDS = new DischedController(context);
        // sort assort item list
        ArrayList<ArrayList<OrderDetail>> MetaTranSODetList = sortAssortDiscount(newOrderList);
        ArrayList<ArrayList<OrderDetail>> newMetaList = new ArrayList<ArrayList<OrderDetail>>();

        /* For each ArrayList inside meta ArrayList "MetaTranSODetList" */
        for (ArrayList<OrderDetail> ordArrList : MetaTranSODetList) {

            /* For each TranSODet object inside ordeArrList ArrayList */
            for (OrderDetail mTranSODet : ordArrList) {

                Disched discHed = discHeadDS.getSchemeByItemCode(mTranSODet.getFORDERDET_ITEMCODE());

                /* If discount scheme exists */
                if (discHed.getFDISCHED_DIS_TYPE() != null) {

                    ArrayList<Discdeb> discDebList = discHeadDS.getDebterList(discHed.getFDISCHED_REF_NO());

                    /* Found debtors */
                    if (discDebList.size() >= 1) {
                        /* Get debtor code from order header by order ref no */ //nuwan
                        //String OrdHedRefNo = new OrderController(context).getRefnoByDebcode(mTranSODet.getFORDERDET_REFNO());
                        //rashmi 2019-11-14
                        String OrdHedDebCode = new OrderController(context).getRefnoByDebcode(mTranSODet.getFORDERDET_REFNO());
                        /* Get debtor code from order header by order ref no */ //nuwan

                        /* Going through the list for a match */
                        for (Discdeb discDeb : discDebList) {
                            /* If match found */
                           // if (discDeb.getFDISCDEB_REF_NO().equals(OrdHedRefNo)) { commented by rashmi 2019-11-14 because not matching
                            if (discDeb.getFDISCDEB_DEB_CODE().equals(OrdHedDebCode)) {

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
                                        for (OrderDetail iTranSODet : ordArrList) {
                                            TotalValue = TotalValue + (Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + Double.parseDouble(iTranSODet.getFORDERDET_SCHDISC()));
                                            totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFORDERDET_QTY());
                                        }

                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                        /* if any discount slabs exist */
                                        if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {

                                            double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
                                            mTranSODet.setFORDERDET_BAMT(String.valueOf(discValue));
                                            mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                            mTranSODet.setFORDERDET_SCHDISPER("0.00");
                                            mTranSODet.setFORDERDET_DISCTYPE("V");
                                            newMetaList.add(ordArrList);
                                            Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
                                            break;
                                        }

                                        /* If not assorted */
                                    } else {
                                        totalQTY = Integer.parseInt(mTranSODet.getFORDERDET_QTY());
                                        TotalValue = Double.parseDouble(mTranSODet.getFORDERDET_AMT()) + Double.parseDouble(mTranSODet.getFORDERDET_SCHDISC());
                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                        if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {

                                            double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
                                            mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                            mTranSODet.setFORDERDET_DISAMT(String.valueOf(discValue));
                                            mTranSODet.setFORDERDET_SCHDISPER("0.00");
                                            new OrderDetailController(context).updateDiscount(mTranSODet, discValue, "V");
                                            Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
                                        }

                                    }

                                }
                                /* if percentage based discount */
                                else if (discHed.getFDISCHED_DIS_TYPE().equals("P")) {
                                    int totalQTY = 0;
                                    double TotalValue = 0;

                                    if (ordArrList.size() > 1) {

                                        for (OrderDetail iTranSODet : ordArrList) {
                                            totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFORDERDET_QTY());
                                            TotalValue = TotalValue + (Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + Double.parseDouble(iTranSODet.getFORDERDET_SCHDISC()));
//                                            if (iTranSODet.getFORDERDET_SCHDISC()== null || iTranSODet.getFORDERDET_SCHDISPER()== null) // null check for schdisc
//                                            {
//                                                TotalValue = TotalValue + (Double.parseDouble(iTranSODet.getFORDERDET_AMT()));
//                                            }
//                                            else
//                                            {
//                                                TotalValue = TotalValue + ((Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + (Double.parseDouble(iTranSODet.getFORDERDET_AMT())*(Double.parseDouble(iTranSODet.getFORDERDET_SCHDISPER())/100))));
//                                            }


                                            Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
                                        /* if any discount slabs exist */
                                            if (discSlab.getFDISCSLAB_DIS_PER() != null) {
                                                /*
                                                 * Get discount value & pass it to
                                                 * first instance and add it to meta
                                                 * list
                                                 */
                                                double discValue = (((Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + Double.parseDouble(iTranSODet.getFORDERDET_SCHDISC())) / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
    //                                            mTranSODet.setFORDERDET_BAMT(String.valueOf(discValue));
    //                                            mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
    //                                            mTranSODet.setFORDERDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
    //                                            mTranSODet.setFORDERDET_DISCTYPE("P");
    //                                            newMetaList.add(ordArrList);
    //                                            Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));

                                                // newly updated due to two itemscodes has same discount scheme disord not inserted records

    //                                            if(!mTranSODet.getFORDERDET_SCHDISC().equals("")) {
    //                                                TotalValue = Double.parseDouble(mTranSODet.getFORDERDET_AMT()) + Double.parseDouble(mTranSODet.getFORDERDET_SCHDISC());
    //                                            }else{
    //                                                TotalValue = Double.parseDouble(mTranSODet.getFORDERDET_AMT());
    //                                            }

    //                                            if (discSlab.getFDISCSLAB_DIS_PER() != null) {
                                                    /* Update table directly cuz it's not assorted */

                                                    mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                                    mTranSODet.setFORDERDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
                                                    mTranSODet.setFORDERDET_DISAMT(String.valueOf(((TotalValue / 100) * Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER()))));
                                                    new OrderDetailController(context).updateDiscount(mTranSODet, discValue, "P");
                                                    Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
    //                                            }

                                                // ------------------------------ Nuwan 10.12.2019 ----------------------------------
                                            }
                                        }

                                    } else {
                                        /* If just 1 item, just calculate the discount according to percentage */
                                        totalQTY = Integer.parseInt(mTranSODet.getFORDERDET_QTY());
                                        //rashmi-2019-11-14 check null of schdisc, because cannot parse null to double
                                        if(!mTranSODet.getFORDERDET_SCHDISC().equals(null)) {
                                            TotalValue = Double.parseDouble(mTranSODet.getFORDERDET_AMT()) + Double.parseDouble(mTranSODet.getFORDERDET_SCHDISC());
                                        }else{
                                            TotalValue = Double.parseDouble(mTranSODet.getFORDERDET_AMT());
                                        }
                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                        if (discSlab.getFDISCSLAB_DIS_PER() != null) {
                                            /* Update table directly cuz it's not assorted */
                                            double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                            mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                            mTranSODet.setFORDERDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
                                            mTranSODet.setFORDERDET_DISAMT(String.valueOf(((TotalValue / 100) * Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER()))));
                                            new OrderDetailController(context).updateDiscount(mTranSODet, discValue, "P");
                                            Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
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
                                for (OrderDetail iTranSODet : ordArrList) {

                                    TotalValue = TotalValue + (Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + Double.parseDouble(iTranSODet.getFORDERDET_SCHDISC()));
                                    totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFORDERDET_QTY());
                                }

                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
                                /* if any discount slabs exist */
                                if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {
                                    /*
                                     * Get discount value & pass it to first
                                     * instance and add it to meta list
                                     */
                                    double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
                                    mTranSODet.setFORDERDET_BAMT(String.valueOf(discValue));
                                    mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                    mTranSODet.setFORDERDET_SCHDISPER("0.00");
                                    mTranSODet.setFORDERDET_DISCTYPE("V");
                                    newMetaList.add(ordArrList);
                                    Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
                                    break;
                                }

                                /* If not assorted */
                            } else {

                                totalQTY = Integer.parseInt(mTranSODet.getFORDERDET_QTY());

                                TotalValue = Double.parseDouble(mTranSODet.getFORDERDET_AMT()) + Double.parseDouble(mTranSODet.getFORDERDET_SCHDISC());
                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {
                                    /*
                                     * Update table directly as it's not
                                     * assorted
                                     */
                                    double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
                                    mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                    mTranSODet.setFORDERDET_DISAMT(String.valueOf(discValue));
                                    mTranSODet.setFORDERDET_SCHDISPER("0.00");
                                    new OrderDetailController(context).updateDiscount(mTranSODet, discValue, "V");
                                    Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
                                }

                            }

                        }
                        /* if percentage based discount */
                        else if (discHed.getFDISCHED_DIS_TYPE().equals("P"))
                        {
                            int totalQTY = 0;
                            double TotalValue = 0;
                            String itemPrice;

                            if (ordArrList.size() > 1)
                            {
                                for (OrderDetail iTranSODet : ordArrList)
                                {
                                    totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFORDERDET_QTY());
                                    //TotalValue = TotalValue + ((Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + Double.parseDouble(iTranSODet.getFORDERDET_SCHDISC())));

                                    if (iTranSODet.getFORDERDET_SCHDISC()== null || iTranSODet.getFORDERDET_SCHDISPER()== null) // null check for schdisc
                                    {
                                        TotalValue = (Double.parseDouble(iTranSODet.getFORDERDET_AMT()));
                                    }
                                    else
                                    {
                                        TotalValue = ((Double.parseDouble(iTranSODet.getFORDERDET_AMT()) + (Double.parseDouble(iTranSODet.getFORDERDET_AMT())*(Double.parseDouble(iTranSODet.getFORDERDET_SCHDISPER())/100))));
                                    }

                                    Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                    /* if any discount slabs exist */
                                    if (discSlab.getFDISCSLAB_DIS_PER() != null) {

                                        /* Get discount value & pass it to first instance and add it to meta list */
                                        double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                        mTranSODet.setFORDERDET_BAMT(String.valueOf(discValue));
                                        mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                        mTranSODet.setFORDERDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
                                        mTranSODet.setFORDERDET_DISCTYPE("P");
                                        /* add to ArrayList as more than one item for dialog box selection */
                                        newMetaList.add(ordArrList);
                                        new OrderDetailController(context).updateDiscountSO(mTranSODet, discValue, "P", debCode);
                                        Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - discValue));
                                        break;
                                    }
                                }

                            }
//                            if (ordArrList.size() > 1) {
//
//                                for (TranSODet iTranSODet : ordArrList)
//                                {
//                                    totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFTRANSODET_QTY());
//                                    TotalValue = TotalValue + ((Double.parseDouble(iTranSODet.getFTRANSODET_AMT()) + Double.parseDouble(iTranSODet.getFTRANSODET_SCHDISC())));
//                                }
//
//                                Discslab discSlab = new DiscslabDS(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
//
//								/* if any discount slabs exist */
//                                if (discSlab.getFDISCSLAB_DIS_PER() != null) {
//
//									/* Get discount value & pass it to first instance and add it to meta list */
//                                    double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
//                                    mTranSODet.setFTRANSODET_BAMT(String.valueOf(discValue));
//                                    mTranSODet.setFTRANSODET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
//                                    mTranSODet.setFTRANSODET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
//                                    mTranSODet.setFTRANSODET_DISCTYPE("P");
//									/* add to ArrayList as more than one item for dialog box selection */
//                                    newMetaList.add(ordArrList);
//                                    new TranSODetDS(context).updateDiscountSO(mTranSODet, discValue, "P", debCode);
//                                    Log.v("DISCOUNT", mTranSODet.getFTRANSODET_ITEMCODE() + " * " + (TotalValue - discValue));
//                                    break;
//                                }
//
//                            }
                            else { /*
                             * If just 1 item, just calculate the
                             * discount according to percentage
                             */
                                totalQTY = Integer.parseInt(mTranSODet.getFORDERDET_QTY());

                                if (mTranSODet.getFORDERDET_SCHDISC()== null && mTranSODet.getFORDERDET_SCHDISPER()== null) // null check for schdisc
                                {
                                    TotalValue = (Double.parseDouble(mTranSODet.getFORDERDET_AMT()));
                                }
                                else
                                {
                                    TotalValue = ((Double.parseDouble(mTranSODet.getFORDERDET_AMT()) + (Double.parseDouble(mTranSODet.getFORDERDET_AMT())*(Double.parseDouble(mTranSODet.getFORDERDET_SCHDISPER())/100))));
                                }
                                // commented due to null value for schdisc
                                //TotalValue = Double.parseDouble(mTranSODet.getFORDERDET_AMT()) + Double.parseDouble(mTranSODet.getFORDERDET_SCHDISC());

                                // ----------------------------------------- Nuwan --------------------------14/09/2018 ----------------Due to reverse tax for discount--------------------------
                                itemPrice = String.valueOf(Double.parseDouble(mTranSODet.getFORDERDET_AMT())/(double)totalQTY);
                                //String oneItemTaxReversePrice = new TaxDetDS(context).calculateReverseTaxFromDebTax(debCode,mTranSODet.getFTRANSODET_ITEMCODE(), new BigDecimal(itemPrice));
                                String oneItemTaxReversePrice[] = new TaxDetController(context).calculateTaxForwardFromDebTax(debCode,mTranSODet.getFORDERDET_ITEMCODE(), Double.parseDouble(itemPrice));

                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                if (discSlab.getFDISCSLAB_DIS_PER() != null) {

                                    /*Update table directly as it's not assorted*/
                                    double reverseItemDiscValue = ((Double.parseDouble(oneItemTaxReversePrice[0]))/100 * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                    double totalReverseDiscValue = reverseItemDiscValue * (double)totalQTY;
                                    //double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                    mTranSODet.setFORDERDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                    mTranSODet.setFORDERDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
                                    mTranSODet.setFORDERDET_DISAMT(String.valueOf(((TotalValue / 100) * Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER()))));

//                                    new TranSODetDS(context).updateDiscount(mTranSODet, discValue, "P");
//                                    Log.v("DISCOUNT", mTranSODet.getFTRANSODET_ITEMCODE() + " * " + (TotalValue - discValue));
                                    new OrderDetailController(context).updateDiscountSO(mTranSODet, totalReverseDiscValue, "P", debCode);
                                    Log.v("DISCOUNT", mTranSODet.getFORDERDET_ITEMCODE() + " * " + (TotalValue - totalReverseDiscValue));
                                }

                            }

                        }

                    }

                }

            }

        }
        return newMetaList;

    }

    public ArrayList<ArrayList<InvDet>> SortInvDiscount(ArrayList<InvDet> newOrderList, String debCode) {

        DischedController discHeadDS = new DischedController(context);
        // sort assort item list
        ArrayList<ArrayList<InvDet>> MetaTranSODetList = sortAssortInvDiscount(newOrderList);
        ArrayList<ArrayList<InvDet>> newMetaList = new ArrayList<ArrayList<InvDet>>();

        /* For each ArrayList inside meta ArrayList "MetaTranSODetList" */
        for (ArrayList<InvDet> ordArrList : MetaTranSODetList) {

            /* For each TranSODet object inside ordeArrList ArrayList */
            for (InvDet mTranSODet : ordArrList) {

                Disched discHed = discHeadDS.getSchemeByItemCode(mTranSODet.getFINVDET_ITEM_CODE());

                /* If discount scheme exists */
                if (discHed.getFDISCHED_DIS_TYPE() != null) {

                    ArrayList<Discdeb> discDebList = discHeadDS.getDebterList(discHed.getFDISCHED_REF_NO());

                    /* Found debtors */
                    if (discDebList.size() >= 1) {
                        /* Get debtor code from order header by order ref no */
                        String OrdHedRefNo = new OrderController(context).getRefnoByDebcode(mTranSODet.getFINVDET_REFNO());
                        /* Going through the list for a match */
                        for (Discdeb discDeb : discDebList) {
                            /* If match found */
                            if (discDeb.getFDISCDEB_REF_NO().equals(OrdHedRefNo)) {

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
                                        for (InvDet iTranSODet : ordArrList) {
                                            TotalValue = TotalValue + (Double.parseDouble(iTranSODet.getFINVDET_AMT()) + Double.parseDouble(iTranSODet.getFINVDET_DIS_AMT()));
                                            totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFINVDET_QTY());
                                        }

                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                        /* if any discount slabs exist */
                                        if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {

                                            double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
                                            mTranSODet.setFINVDET_B_AMT(String.valueOf(discValue));
                                            mTranSODet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                            mTranSODet.setFINVDET_SCHDISPER("0.00");
                                            mTranSODet.setFINVDET_DISCTYPE("V");
                                            newMetaList.add(ordArrList);
                                            Log.v("DISCOUNT", mTranSODet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                            break;
                                        }

                                        /* If not assorted */
                                    } else {
                                        totalQTY = Integer.parseInt(mTranSODet.getFINVDET_QTY());
                                        TotalValue = Double.parseDouble(mTranSODet.getFINVDET_AMT()) + Double.parseDouble(mTranSODet.getFINVDET_DIS_AMT());
                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                        if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {

                                            double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
                                            mTranSODet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                            mTranSODet.setFINVDET_DIS_AMT(String.valueOf(discValue));
                                            mTranSODet.setFINVDET_SCHDISPER("0.00");
                                            new InvDetController(context).updateDiscount(mTranSODet, discValue, "V");
                                            Log.v("DISCOUNT", mTranSODet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
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
                                            TotalValue = TotalValue + ((Double.parseDouble(iTranSODet.getFINVDET_AMT()) + Double.parseDouble(iTranSODet.getFINVDET_DIS_AMT())));
                                        }

                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
                                        /* if any discount slabs exist */
                                        if (discSlab.getFDISCSLAB_DIS_PER() != null) {
                                            /*
                                             * Get discount value & pass it to
                                             * first instance and add it to meta
                                             * list
                                             */
                                            double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                            mTranSODet.setFINVDET_B_AMT(String.valueOf(discValue));
                                            mTranSODet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                            mTranSODet.setFINVDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
                                            mTranSODet.setFINVDET_DISCTYPE("P");
                                            newMetaList.add(ordArrList);
                                            Log.v("DISCOUNT", mTranSODet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                            break;
                                        }

                                    } else {
                                        /* If just 1 item, just calculate the discount according to percentage */
                                        totalQTY = Integer.parseInt(mTranSODet.getFINVDET_QTY());
                                        TotalValue = Double.parseDouble(mTranSODet.getFINVDET_AMT()) + Double.parseDouble(mTranSODet.getFINVDET_DIS_AMT());
                                        Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                        if (discSlab.getFDISCSLAB_DIS_PER() != null) {
                                            /* Update table directly cuz it's not assorted */
                                            double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                            mTranSODet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                            mTranSODet.setFINVDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
                                            mTranSODet.setFINVDET_DIS_AMT(String.valueOf(((TotalValue / 100) * Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER()))));
                                            new InvDetController(context).updateDiscount(mTranSODet, discValue, "P");
                                            Log.v("DISCOUNT", mTranSODet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
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

                                    TotalValue = TotalValue + (Double.parseDouble(iTranSODet.getFINVDET_AMT()) + Double.parseDouble(iTranSODet.getFINVDET_DIS_AMT()));
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
                                    mTranSODet.setFINVDET_B_AMT(String.valueOf(discValue));
                                    mTranSODet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                    mTranSODet.setFINVDET_SCHDISPER("0.00");
                                    mTranSODet.setFINVDET_DISCTYPE("V");
                                    newMetaList.add(ordArrList);
                                    Log.v("DISCOUNT", mTranSODet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                    break;
                                }

                                /* If not assorted */
                            } else {

                                totalQTY = Integer.parseInt(mTranSODet.getFINVDET_QTY());

                                TotalValue = Double.parseDouble(mTranSODet.getFINVDET_AMT()) + Double.parseDouble(mTranSODet.getFINVDET_DIS_AMT());

                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                if (discSlab.getFDISCSLAB_DIS_AMUT() != null) {
                                    /*
                                     * Update table directly as it's not
                                     * assorted
                                     */
                                    double discValue = Double.parseDouble(discSlab.getFDISCSLAB_DIS_AMUT());
                                    mTranSODet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                    mTranSODet.setFINVDET_DIS_AMT(String.valueOf(discValue));
                                    mTranSODet.setFINVDET_SCHDISPER("0.00");
                                    new InvDetController(context).updateDiscount(mTranSODet, discValue, "V");
                                    Log.v("DISCOUNT", mTranSODet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                }

                            }

                        }
                        /* if percentage based discount */
                        else if (discHed.getFDISCHED_DIS_TYPE().equals("P"))
                        {
                            int totalQTY = 0;
                            double TotalValue = 0;
                            String itemPrice;

                            if (ordArrList.size() > 1)
                            {
                                for (InvDet iTranSODet : ordArrList)
                                {
                                    totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFINVDET_QTY());
                                    TotalValue = TotalValue + ((Double.parseDouble(iTranSODet.getFINVDET_AMT()) + Double.parseDouble(iTranSODet.getFINVDET_DIS_AMT())));

//                                    Discslab discSlab = new DiscslabDS(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
//
//                                    iTranSODet.setFTRANSODET_DISCTYPE("P");
//                                    iTranSODet.setFTRANSODET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
//                                    iTranSODet.setFTRANSODET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
//
//                                    new TranSODetDS(context).updateDiscountSO(iTranSODet, 0.00, "P", debCode);
                                }

                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                /* if any discount slabs exist */
                                if (discSlab.getFDISCSLAB_DIS_PER() != null) {

                                    /* Get discount value & pass it to first instance and add it to meta list */
                                    double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                    mTranSODet.setFINVDET_B_AMT(String.valueOf(discValue));
                                    mTranSODet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                    mTranSODet.setFINVDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
                                    mTranSODet.setFINVDET_DISCTYPE("P");
                                    /* add to ArrayList as more than one item for dialog box selection */
                                    newMetaList.add(ordArrList);
                                    new InvDetController(context).updateDiscountInvoice(mTranSODet, discValue, "P", debCode);
                                    Log.v("DISCOUNT", mTranSODet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - discValue));
                                    break;
                                }

                            }
//                            if (ordArrList.size() > 1) {
//
//                                for (TranSODet iTranSODet : ordArrList)
//                                {
//                                    totalQTY = totalQTY + Integer.parseInt(iTranSODet.getFTRANSODET_QTY());
//                                    TotalValue = TotalValue + ((Double.parseDouble(iTranSODet.getFTRANSODET_AMT()) + Double.parseDouble(iTranSODet.getFTRANSODET_SCHDISC())));
//                                }
//
//                                Discslab discSlab = new DiscslabDS(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);
//
//								/* if any discount slabs exist */
//                                if (discSlab.getFDISCSLAB_DIS_PER() != null) {
//
//									/* Get discount value & pass it to first instance and add it to meta list */
//                                    double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
//                                    mTranSODet.setFTRANSODET_BAMT(String.valueOf(discValue));
//                                    mTranSODet.setFTRANSODET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
//                                    mTranSODet.setFTRANSODET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
//                                    mTranSODet.setFTRANSODET_DISCTYPE("P");
//									/* add to ArrayList as more than one item for dialog box selection */
//                                    newMetaList.add(ordArrList);
//                                    new TranSODetDS(context).updateDiscountSO(mTranSODet, discValue, "P", debCode);
//                                    Log.v("DISCOUNT", mTranSODet.getFTRANSODET_ITEMCODE() + " * " + (TotalValue - discValue));
//                                    break;
//                                }
//
//                            }
                            else { /*
                             * If just 1 item, just calculate the
                             * discount according to percentage
                             */
                                totalQTY = Integer.parseInt(mTranSODet.getFINVDET_QTY());
                                TotalValue = Double.parseDouble(mTranSODet.getFINVDET_AMT()) + Double.parseDouble(mTranSODet.getFINVDET_DIS_AMT());

                                // ----------------------------------------- Nuwan --------------------------14/09/2018 ----------------Due to reverse tax for discount--------------------------
                                itemPrice = String.valueOf(Double.parseDouble(mTranSODet.getFINVDET_AMT())/(double)totalQTY);
                                //String oneItemTaxReversePrice = new TaxDetDS(context).calculateReverseTaxFromDebTax(debCode,mTranSODet.getFTRANSODET_ITEMCODE(), new BigDecimal(itemPrice));
                                String oneItemTaxReversePrice[] = new TaxDetController(context).calculateTaxForwardFromDebTax(debCode,mTranSODet.getFINVDET_ITEM_CODE(), Double.parseDouble(itemPrice));

                                Discslab discSlab = new DiscslabController(context).getDiscountSlabInfo(discHed.getFDISCHED_REF_NO(), totalQTY);

                                if (discSlab.getFDISCSLAB_DIS_PER() != null) {

                                    /*Update table directly as it's not assorted*/
                                    double reverseItemDiscValue = ((Double.parseDouble(oneItemTaxReversePrice[0]))/100 * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                    double totalReverseDiscValue = reverseItemDiscValue * (double)totalQTY;
                                    //double discValue = ((TotalValue / 100) * (Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER())));
                                    mTranSODet.setFINVDET_DISC_REF(discSlab.getFDISCSLAB_REF_NO());
                                    mTranSODet.setFINVDET_SCHDISPER(discSlab.getFDISCSLAB_DIS_PER());
                                    mTranSODet.setFINVDET_DIS_AMT(String.valueOf(((TotalValue / 100) * Double.parseDouble(discSlab.getFDISCSLAB_DIS_PER()))));

//                                    new TranSODetDS(context).updateDiscount(mTranSODet, discValue, "P");
//                                    Log.v("DISCOUNT", mTranSODet.getFTRANSODET_ITEMCODE() + " * " + (TotalValue - discValue));
                                    new InvDetController(context).updateDiscountInvoice(mTranSODet, totalReverseDiscValue, "P", debCode);
                                    Log.v("DISCOUNT", mTranSODet.getFINVDET_ITEM_CODE() + " * " + (TotalValue - totalReverseDiscValue));
                                }

                            }

                        }

                    }

                }

            }

        }
        return newMetaList;

    }

    public ArrayList<ArrayList<InvDet>> sortAssortInvDiscount(ArrayList<InvDet> OrderList) {

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

        /* For each ArrayList inside meta ArrayList "nodelist" */
        for (ArrayList<InvDet> ordArrList : nodeList) {

            /* For each TranSODet object inside ordeArrList ArrayList */
            for (InvDet mTranSODet : ordArrList) {
                Log.v("ITEM **", mTranSODet.getFINVDET_ITEM_CODE());
            }
        }

        return nodeList;

    }

    public ArrayList<ArrayList<OrderDetail>> sortAssortDiscount(ArrayList<OrderDetail> OrderList) {

        DiscdetController discDetDS = new DiscdetController(context);
        ArrayList<ArrayList<OrderDetail>> nodeList = new ArrayList<ArrayList<OrderDetail>>();
        List<String> AssortList = new ArrayList<String>();

        /* Looping through the order items */
        for (int x = 0; x <= OrderList.size() - 1; x++) {

            ArrayList<OrderDetail> mTranSODet = new ArrayList<OrderDetail>();
            /* If checked already, don't check it again */

            if (!OrderList.get(x).isFLAG()) {
                mTranSODet.add(OrderList.get(x));
                /* Get assort item list for a item code */
                AssortList = discDetDS.getAssortByItemCode(OrderList.get(x).getFORDERDET_ITEMCODE());

                if (AssortList.size() > 0) {
                    for (int y = x + 1; y <= OrderList.size() - 1; y++) {
                        for (int i = 0; i <= AssortList.size() - 1; i++) {
                            /* If found a matching item */
                            if (OrderList.get(y).getFORDERDET_ITEMCODE().equals(AssortList.get(i))) {
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

        /* For each ArrayList inside meta ArrayList "nodelist" */
        for (ArrayList<OrderDetail> ordArrList : nodeList) {

            /* For each TranSODet object inside ordeArrList ArrayList */
            for (OrderDetail mTranSODet : ordArrList) {
                Log.v("ITEM **", mTranSODet.getFORDERDET_ITEMCODE());
            }
        }

        return nodeList;

    }
}
