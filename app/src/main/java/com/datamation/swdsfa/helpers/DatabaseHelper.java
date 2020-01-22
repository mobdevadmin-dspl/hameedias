package com.datamation.swdsfa.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.datamation.swdsfa.controller.BankController;
import com.datamation.swdsfa.controller.CompanyDetailsController;
import com.datamation.swdsfa.controller.DayExpDetController;
import com.datamation.swdsfa.controller.DayNPrdDetController;
import com.datamation.swdsfa.controller.DayNPrdHedController;
import com.datamation.swdsfa.controller.DebItemPriController;
import com.datamation.swdsfa.controller.DiscdebController;
import com.datamation.swdsfa.controller.DiscdetController;
import com.datamation.swdsfa.controller.DischedController;
import com.datamation.swdsfa.controller.DiscslabController;
import com.datamation.swdsfa.controller.DispDetController;
import com.datamation.swdsfa.controller.DispHedController;
import com.datamation.swdsfa.controller.DispIssController;
import com.datamation.swdsfa.controller.ExpenseController;
import com.datamation.swdsfa.controller.FInvhedL3Controller;
import com.datamation.swdsfa.controller.FItenrDetController;
import com.datamation.swdsfa.controller.FItenrHedController;
import com.datamation.swdsfa.controller.FinvDetL3Controller;
import com.datamation.swdsfa.controller.FirebaseMediaController;
import com.datamation.swdsfa.controller.FreeDebController;
import com.datamation.swdsfa.controller.FreeDetController;
import com.datamation.swdsfa.controller.FreeHedController;
import com.datamation.swdsfa.controller.FreeItemController;
import com.datamation.swdsfa.controller.FreeMslabController;
import com.datamation.swdsfa.controller.FreeSlabController;
import com.datamation.swdsfa.controller.InvDetController;
import com.datamation.swdsfa.controller.InvHedController;
import com.datamation.swdsfa.controller.InvTaxDTController;
import com.datamation.swdsfa.controller.InvTaxRGController;
import com.datamation.swdsfa.controller.ItemController;
import com.datamation.swdsfa.controller.ItemLocController;
import com.datamation.swdsfa.controller.ItemPriceController;
import com.datamation.swdsfa.controller.LocationsController;
import com.datamation.swdsfa.controller.NearCustomerController;
import com.datamation.swdsfa.controller.NewCustomerController;
import com.datamation.swdsfa.controller.OrdFreeIssueController;
import com.datamation.swdsfa.controller.OrderController;
import com.datamation.swdsfa.controller.OrderDetailController;
import com.datamation.swdsfa.controller.OrderDiscController;
import com.datamation.swdsfa.controller.OutstandingController;
import com.datamation.swdsfa.controller.PreProductController;
import com.datamation.swdsfa.controller.PreSaleTaxDTController;
import com.datamation.swdsfa.controller.PreSaleTaxRGController;
import com.datamation.swdsfa.controller.ProductController;
import com.datamation.swdsfa.controller.ReasonController;
import com.datamation.swdsfa.controller.ReceiptController;
import com.datamation.swdsfa.controller.ReceiptDetController;
import com.datamation.swdsfa.controller.ReferenceSettingController;
import com.datamation.swdsfa.controller.RouteController;
import com.datamation.swdsfa.controller.RouteDetController;
import com.datamation.swdsfa.controller.SalRepController;
import com.datamation.swdsfa.controller.SalesReturnController;
import com.datamation.swdsfa.controller.SalesReturnDetController;
import com.datamation.swdsfa.controller.SalesReturnTaxDTController;
import com.datamation.swdsfa.controller.SalesReturnTaxRGController;
import com.datamation.swdsfa.controller.TaxController;
import com.datamation.swdsfa.controller.TaxDetController;
import com.datamation.swdsfa.controller.TaxHedController;
import com.datamation.swdsfa.controller.TourController;
import com.datamation.swdsfa.controller.TownController;
import com.datamation.swdsfa.model.Attendance;
import com.datamation.swdsfa.model.CompanyBranch;
import com.datamation.swdsfa.model.CompanySetting;
import com.datamation.swdsfa.model.Customer;
import com.datamation.swdsfa.model.DayNPrdHed;

public class DatabaseHelper extends SQLiteOpenHelper {
    // database information
    public static final String DATABASE_NAME = "swdsfa_database.db";
    public static final int DATABASE_VERSION = 8;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //common string
    public static final String REFNO = "RefNo";
    public static final String TXNDATE = "TxnDate";
    public static final String REPCODE = "RepCode";
    public static final String DEALCODE = "DealCode";
    public static final String DEBCODE = "DebCode";

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL(InvTaxDTController.CREATE_FINVTAXDT_TABLE);
        arg0.execSQL(InvTaxRGController.CREATE_FINVTAXRG_TABLE);
        arg0.execSQL(DispDetController.CREATE_FDISPDET_TABLE);
        arg0.execSQL(DispIssController.CREATE_FDISPISS_TABLE);
        arg0.execSQL(DispHedController.CREATE_FDISPHED_TABLE);
        arg0.execSQL(DebItemPriController.CREATE_DEBITEMPRI_TABLE);
        arg0.execSQL(Customer.CREATE_FDEBTOR_TABLE);
        arg0.execSQL(CompanyDetailsController.CREATE_FCONTROL_TABLE);
        arg0.execSQL(CompanySetting.CREATE_FCOMPANYSETTING_TABLE);
        arg0.execSQL(RouteController.CREATE_FROUTE_TABLE);
        arg0.execSQL(BankController.CREATE_FBANK_TABLE);
        arg0.execSQL(ReasonController.CREATE_FREASON_TABLE);
        arg0.execSQL(ExpenseController.CREATE_FEXPENSE_TABLE);
        arg0.execSQL(TownController.CREATE_FTOWN_TABLE);
        arg0.execSQL(ItemController.CREATE_FGROUP_TABLE);
        arg0.execSQL(OrderController.CREATE_FORDHED_TABLE);
        arg0.execSQL(OrderDetailController.CREATE_FORDDET_TABLE);
        arg0.execSQL(CompanyBranch.CREATE_FCOMPANYBRANCH_TABLE);
        arg0.execSQL(SalRepController.CREATE_FSALREP_TABLE);
        arg0.execSQL(OutstandingController.CREATE_FDDBNOTE_TABLE);
        arg0.execSQL(FreeDebController.CREATE_FFREEDEB_TABLE);
        arg0.execSQL(FreeDetController.CREATE_FFREEDET_TABLE);
        arg0.execSQL(FreeHedController.CREATE_FFREEHED_TABLE);
        arg0.execSQL(FreeSlabController.CREATE_FFREESLAB_TABLE);
        arg0.execSQL(FreeItemController.CREATE_FFREEITEM_TABLE);
        arg0.execSQL(ItemController.CREATE_FITEM_TABLE);
        arg0.execSQL(ItemLocController.CREATE_FITEMLOC_TABLE);
        arg0.execSQL(ItemPriceController.CREATE_FITEMPRI_TABLE);
        arg0.execSQL(LocationsController.CREATE_FLOCATIONS_TABLE);
        arg0.execSQL(FreeMslabController.CREATE_FFREEMSLAB_TABLE);
        arg0.execSQL(RouteDetController.CREATE_FROUTEDET_TABLE);
        arg0.execSQL(DischedController.CREATE_FDISCHED_TABLE);
        arg0.execSQL(DiscdetController.CREATE_FDISCDET_TABLE);
        arg0.execSQL(DiscdebController.CREATE_FDISCDEB_TABLE);
        arg0.execSQL(DiscslabController.CREATE_FDISCSLAB_TABLE);
        arg0.execSQL(FItenrHedController.CREATE_FITENRHED_TABLE);
        arg0.execSQL(FItenrDetController.CREATE_FITENRDET_TABLE);
        arg0.execSQL(FInvhedL3Controller.CREATE_FINVHEDL3_TABLE);
        arg0.execSQL(FinvDetL3Controller.CREATE_FINVDETL3_TABLE);
        arg0.execSQL(DayNPrdHedController.CREATE_TABLE_NONPRDHED);
        arg0.execSQL(DayNPrdDetController.CREATE_TABLE_NONPRDDET);
        arg0.execSQL(DayExpDetController.CREATE_FDAYEXPDET_TABLE);
        arg0.execSQL(OrderDiscController.CREATE_FORDDISC_TABLE);
        arg0.execSQL(OrdFreeIssueController.CREATE_FORDFREEISS_TABLE);
        arg0.execSQL(ItemController.TESTITEM);
        arg0.execSQL(ItemLocController.TESTITEMLOC);
        arg0.execSQL(ItemPriceController.TESTITEMPRI);
        arg0.execSQL(FInvhedL3Controller.TESTINVHEDL3);
        arg0.execSQL(FinvDetL3Controller.TESTINVDETL3);
        arg0.execSQL(RouteDetController.TESTROUTEDET);
        arg0.execSQL(FreeDebController.TESTFREEDEB);
        arg0.execSQL(Customer.INDEX_DEBTOR);
        arg0.execSQL(OutstandingController.TESTDDBNOTE);
        arg0.execSQL(BankController.TESTBANK);
        arg0.execSQL(ReferenceSettingController.IDXCOMSETT);
        arg0.execSQL(FreeHedController.IDXFREEHED);
        arg0.execSQL(FreeDetController.IDXFREEDET);
        arg0.execSQL(FreeItemController.IDXFREEITEM);
        arg0.execSQL(FreeSlabController.IDXFREESLAB);
        arg0.execSQL(SalesReturnController.CREATE_FINVRHED_TABLE);
        arg0.execSQL(SalesReturnDetController.CREATE_FINVRDET_TABLE);
        arg0.execSQL(Customer.CREATE_TABLE_TEMP_FDEBTOR);
        arg0.execSQL(ReceiptController.CREATE_FPRECHED_TABLE);
        arg0.execSQL(ReceiptDetController.CREATE_FPRECDET_TABLE);
        arg0.execSQL(ReceiptController.CREATE_FPRECHEDS_TABLE);
        arg0.execSQL(ReceiptDetController.CREATE_FPRECDETS_TABLE);
        arg0.execSQL(ProductController.CREATE_FPRODUCT_TABLE);
        arg0.execSQL(Attendance.CREATE_ATTENDANCE_TABLE);
        arg0.execSQL(TaxController.CREATE_FTAX_TABLE);
        arg0.execSQL(TaxHedController.CREATE_FTAXHED_TABLE);
        arg0.execSQL(PreProductController.CREATE_FPRODUCT_PRE_TABLE);
        arg0.execSQL(PreProductController.INDEX_PRODUCTS);
        arg0.execSQL(TourController.CREATE_FTOURHED_TABLE);
        arg0.execSQL(ItemController.CREATE_FDEBTAX_TABLE);
        arg0.execSQL(TaxDetController.CREATE_FTAXDET_TABLE);
        arg0.execSQL(OrderDetailController.CREATE_ORDDET_TABLE);
        arg0.execSQL(OrderController.CREATE_TABLE_ORDER);
        arg0.execSQL(DayExpDetController.CREATE_DAYEXPDET_TABLE);
        arg0.execSQL(DayNPrdHed.CREATE_DAYEXPHED_TABLE);
        arg0.execSQL(NewCustomerController.CREATE_NEW_CUSTOMER);
        arg0.execSQL(PreSaleTaxRGController.CREATE_FPRETAXRG_TABLE);
        arg0.execSQL(PreSaleTaxDTController.CREATE_FPRETAXDT_TABLE);
        arg0.execSQL(SalesReturnTaxRGController.CREATE_FINVRTAXRG_TABLE);
        arg0.execSQL(SalesReturnTaxDTController.CREATE_FINVRTAXDT_TABLE);
        arg0.execSQL(NearCustomerController.CREATE_FNEARDEBTOR_TABLE);
        arg0.execSQL(FirebaseMediaController.CREATETABLE_FIREBASE_MEDIA);

    }
    // --------------------------------------------------------------------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

        this.onCreate(arg0);
        try {
            arg0.execSQL("ALTER TABLE fSalRep ADD COLUMN IsFireQohExcdValidation TEXT DEFAULT 1");
        } catch (SQLiteException e) {
            Log.v("SQLiteException", e.toString());
        }
        try {

            arg0.execSQL(InvTaxDTController.CREATE_FINVTAXDT_TABLE);
            arg0.execSQL(InvTaxRGController.CREATE_FINVTAXRG_TABLE);
            arg0.execSQL(DispDetController.CREATE_FDISPDET_TABLE);
            arg0.execSQL(DispIssController.CREATE_FDISPISS_TABLE);
            arg0.execSQL(DebItemPriController.CREATE_DEBITEMPRI_TABLE);
            arg0.execSQL(DispHedController.CREATE_FDISPHED_TABLE);
            arg0.execSQL(TaxController.CREATE_FTAX_TABLE);
            arg0.execSQL(TaxHedController.CREATE_FTAXHED_TABLE);
            arg0.execSQL(Customer.CREATE_FDEBTOR_TABLE);
            arg0.execSQL(ProductController.CREATE_FPRODUCT_TABLE);
            arg0.execSQL(InvHedController.CREATE_FINVHED_TABLE);
            arg0.execSQL(InvDetController.CREATE_FINVDET_TABLE);
            arg0.execSQL(SalesReturnController.CREATE_FINVRHED_TABLE);
            arg0.execSQL(SalesReturnDetController.CREATE_FINVRDET_TABLE);
            arg0.execSQL(OrderDetailController.CREATE_ORDDET_TABLE);
            arg0.execSQL(Attendance.CREATE_ATTENDANCE_TABLE);
            arg0.execSQL(OrderController.CREATE_TABLE_ORDER);
            arg0.execSQL(TourController.CREATE_FTOURHED_TABLE);
            arg0.execSQL(DayExpDetController.CREATE_DAYEXPDET_TABLE);
            arg0.execSQL(DayNPrdHed.CREATE_DAYEXPHED_TABLE);
            arg0.execSQL(NewCustomerController.CREATE_NEW_CUSTOMER);
            arg0.execSQL(PreSaleTaxRGController.CREATE_FPRETAXRG_TABLE);
            arg0.execSQL(PreSaleTaxDTController.CREATE_FPRETAXDT_TABLE);
            arg0.execSQL(SalesReturnTaxRGController.CREATE_FINVRTAXRG_TABLE);
            arg0.execSQL(SalesReturnTaxDTController.CREATE_FINVRTAXDT_TABLE);
            arg0.execSQL(PreProductController.CREATE_FPRODUCT_PRE_TABLE);
            arg0.execSQL(NearCustomerController.CREATE_FNEARDEBTOR_TABLE);
            arg0.execSQL(FirebaseMediaController.CREATETABLE_FIREBASE_MEDIA);

        } catch (SQLiteException e) {
        }

    }
}