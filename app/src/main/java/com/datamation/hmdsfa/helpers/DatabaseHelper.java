package com.datamation.hmdsfa.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.datamation.hmdsfa.controller.BankController;
import com.datamation.hmdsfa.controller.BarcodeVarientController;
import com.datamation.hmdsfa.controller.CompanyDetailsController;
import com.datamation.hmdsfa.controller.DayExpDetController;
import com.datamation.hmdsfa.controller.DayNPrdDetController;
import com.datamation.hmdsfa.controller.DayNPrdHedController;
import com.datamation.hmdsfa.controller.DebItemPriController;
import com.datamation.hmdsfa.controller.DiscdebController;
import com.datamation.hmdsfa.controller.DiscdetController;
import com.datamation.hmdsfa.controller.DischedController;
import com.datamation.hmdsfa.controller.DiscountController;
import com.datamation.hmdsfa.controller.DiscslabController;
import com.datamation.hmdsfa.controller.DispDetController;
import com.datamation.hmdsfa.controller.DispHedController;
import com.datamation.hmdsfa.controller.DispIssController;
import com.datamation.hmdsfa.controller.ExpenseController;
import com.datamation.hmdsfa.controller.FInvhedL3Controller;
import com.datamation.hmdsfa.controller.FItenrDetController;
import com.datamation.hmdsfa.controller.FItenrHedController;
import com.datamation.hmdsfa.controller.FinvDetL3Controller;
import com.datamation.hmdsfa.controller.FirebaseMediaController;
import com.datamation.hmdsfa.controller.FreeDebController;
import com.datamation.hmdsfa.controller.FreeDetController;
import com.datamation.hmdsfa.controller.FreeHedController;
import com.datamation.hmdsfa.controller.FreeItemController;
import com.datamation.hmdsfa.controller.FreeMslabController;
import com.datamation.hmdsfa.controller.FreeSlabController;
import com.datamation.hmdsfa.controller.InvDetController;
import com.datamation.hmdsfa.controller.InvHedController;
import com.datamation.hmdsfa.controller.InvTaxDTController;
import com.datamation.hmdsfa.controller.InvTaxRGController;
import com.datamation.hmdsfa.controller.InvoiceBarcodeController;
import com.datamation.hmdsfa.controller.InvoiceDetBarcodeController;
import com.datamation.hmdsfa.controller.IteaneryDebController;
import com.datamation.hmdsfa.controller.ItemBundleController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.ItemLocController;
import com.datamation.hmdsfa.controller.ItemPriceController;
import com.datamation.hmdsfa.controller.LocationsController;
import com.datamation.hmdsfa.controller.MainStockController;
import com.datamation.hmdsfa.controller.MonthlyTargetController;
import com.datamation.hmdsfa.controller.NearCustomerController;
import com.datamation.hmdsfa.controller.NewCustomerController;
import com.datamation.hmdsfa.controller.OrdFreeIssueController;
import com.datamation.hmdsfa.controller.OrderController;
import com.datamation.hmdsfa.controller.OrderDetailController;
import com.datamation.hmdsfa.controller.OrderDiscController;
import com.datamation.hmdsfa.controller.OutstandingController;
import com.datamation.hmdsfa.controller.PayModeController;
import com.datamation.hmdsfa.controller.PaymentAllocateController;
import com.datamation.hmdsfa.controller.PreProductController;
import com.datamation.hmdsfa.controller.PreSaleTaxDTController;
import com.datamation.hmdsfa.controller.PreSaleTaxRGController;
import com.datamation.hmdsfa.controller.ProductController;
import com.datamation.hmdsfa.controller.ReasonController;
import com.datamation.hmdsfa.controller.ReceiptController;
import com.datamation.hmdsfa.controller.ReceiptDetController;
import com.datamation.hmdsfa.controller.ReferenceSettingController;
import com.datamation.hmdsfa.controller.RouteController;
import com.datamation.hmdsfa.controller.RouteDetController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.controller.SalesPriceController;
import com.datamation.hmdsfa.controller.SalesReturnController;
import com.datamation.hmdsfa.controller.SalesReturnDetController;
import com.datamation.hmdsfa.controller.SalesReturnTaxDTController;
import com.datamation.hmdsfa.controller.SalesReturnTaxRGController;
import com.datamation.hmdsfa.controller.TaxController;
import com.datamation.hmdsfa.controller.TaxDetController;
import com.datamation.hmdsfa.controller.TaxHedController;
import com.datamation.hmdsfa.controller.TourController;
import com.datamation.hmdsfa.controller.TownController;
import com.datamation.hmdsfa.controller.VATController;
import com.datamation.hmdsfa.controller.VanStockController;
import com.datamation.hmdsfa.model.Attendance;
import com.datamation.hmdsfa.model.CompanyBranch;
import com.datamation.hmdsfa.model.CompanySetting;
import com.datamation.hmdsfa.model.Customer;
import com.datamation.hmdsfa.model.DayNPrdHed;

public class DatabaseHelper extends SQLiteOpenHelper {
    // database information
    public static final String DATABASE_NAME = "hmdsfa_database.db";
    public static final int DATABASE_VERSION = 8;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL(InvoiceBarcodeController.CREATE_TABLE_BCINCOICEHED);
        arg0.execSQL(InvoiceDetBarcodeController.CREATE_TABLE_BCINCOICEDET);
        arg0.execSQL(InvTaxDTController.CREATE_FINVTAXDT_TABLE);
        arg0.execSQL(InvTaxRGController.CREATE_FINVTAXRG_TABLE);
        arg0.execSQL(DispDetController.CREATE_FDISPDET_TABLE);
        arg0.execSQL(DispIssController.CREATE_FDISPISS_TABLE);
        arg0.execSQL(DispHedController.CREATE_FDISPHED_TABLE);
        arg0.execSQL(DebItemPriController.CREATE_DEBITEMPRI_TABLE);
        arg0.execSQL(Customer.CREATE_FDEBTOR_TABLE);
        arg0.execSQL(CompanyDetailsController.CREATE_FCONTROL_TABLE);
        arg0.execSQL(CompanyDetailsController.CREATE_DOWNLOAD_TABLE);
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
        arg0.execSQL(ItemBundleController.CREATE_ITEMBUNDLE_TABLE);
        arg0.execSQL(VATController.CREATE_TABLE_VAT);
        arg0.execSQL(IteaneryDebController.CREATE_TABLE_ITEDEB);
        arg0.execSQL(SalesPriceController.CREATE_TABLE_FSALESPRICE);
        arg0.execSQL(DiscountController.CREATE_TABLE_DISCOUNT);
        arg0.execSQL(BarcodeVarientController.CREATE_TABLE_BAR_CODE_VARIENT);
        arg0.execSQL(VanStockController.CREATE_TABLE_FVANSTOCK);
        arg0.execSQL(InvHedController.CREATE_FINVHED_TABLE);
        arg0.execSQL(InvHedController.CREATE_FINVHED_TABLE_LOG);
        arg0.execSQL(InvDetController.CREATE_FINVDET_TABLE);
        arg0.execSQL(InvDetController.CREATE_FINVDET_TABLE_LOG);
        arg0.execSQL(PayModeController.CREATE_TABLE_FPAYMODE);
        arg0.execSQL(PaymentAllocateController.CREATE_TABLE_FPAYMENT_ALLOCATE);
        arg0.execSQL(MainStockController.CREATE_FMAINSTOCK_TABLE);
        arg0.execSQL(MonthlyTargetController.CREATE_FMONTH_TARGET_TABLE);


    }

    // --------------------------------------------------------------------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        this.onCreate(arg0);

        try {
            arg0.execSQL("ALTER TABLE FInvRDet ADD COLUMN Barcode TEXT DEFAULT ''");
        } catch (SQLiteException e) {
            Log.v("SQLiteException", e.toString());
        }
        try {
            arg0.execSQL("ALTER TABLE FInvRDet ADD COLUMN Article_No TEXT DEFAULT ''");
        } catch (SQLiteException e) {
            Log.v("SQLiteException", e.toString());
        }
        try {
            arg0.execSQL("ALTER TABLE FInvRDet ADD COLUMN Variantcode TEXT DEFAULT ''");
        } catch (SQLiteException e) {
            Log.v("SQLiteException", e.toString());
        }
        try {
            arg0.execSQL("ALTER TABLE fpRecHedS ADD COLUMN MRefNo TEXT DEFAULT ''");
        } catch (SQLiteException e) {
            Log.v("SQLiteException", e.toString());
        }
//        try {
//            arg0.execSQL("ALTER TABLE fProducts ADD COLUMN IsScan TEXT DEFAULT '0'");
//        } catch (SQLiteException e) {
//            Log.v("SQLiteException", e.toString());
//        }
//        try {
//            arg0.execSQL("ALTER TABLE fProducts ADD COLUMN Price TEXT DEFAULT '0.0'");
//        } catch (SQLiteException e) {
//            Log.v("SQLiteException", e.toString());
//        }
//        try {
//            arg0.execSQL("ALTER TABLE fProducts ADD COLUMN VariantColour TEXT DEFAULT ''");
//        } catch (SQLiteException e) {
//            Log.v("SQLiteException", e.toString());
//        }
//        try {
//            arg0.execSQL("ALTER TABLE fProducts ADD COLUMN VariantSize TEXT DEFAULT '0.0'");
//        } catch (SQLiteException e) {
//            Log.v("SQLiteException", e.toString());
//        }try {
//            arg0.execSQL("ALTER TABLE fProducts ADD COLUMN Quantity TEXT DEFAULT '0.0'");
//        } catch (SQLiteException e) {
//            Log.v("SQLiteException", e.toString());
//        }
//
        try {
            arg0.execSQL("DROP INDEX IF EXISTS idxitemloc_something ");
            arg0.execSQL(ItemLocController.TESTITEMLOC);
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
            arg0.execSQL(InvHedController.CREATE_FINVHED_TABLE_LOG);
            arg0.execSQL(InvDetController.CREATE_FINVDET_TABLE);
            arg0.execSQL(InvDetController.CREATE_FINVDET_TABLE_LOG);
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
            arg0.execSQL(ItemBundleController.CREATE_ITEMBUNDLE_TABLE);
            arg0.execSQL(VATController.CREATE_TABLE_VAT);
            arg0.execSQL(IteaneryDebController.CREATE_TABLE_ITEDEB);
            arg0.execSQL(SalesPriceController.CREATE_TABLE_FSALESPRICE);
            arg0.execSQL(DiscountController.CREATE_TABLE_DISCOUNT);
            arg0.execSQL(BarcodeVarientController.CREATE_TABLE_BAR_CODE_VARIENT);
            arg0.execSQL(VanStockController.CREATE_TABLE_FVANSTOCK);
            arg0.execSQL(CompanyDetailsController.CREATE_DOWNLOAD_TABLE);
            arg0.execSQL(PayModeController.CREATE_TABLE_FPAYMODE);
            arg0.execSQL(PaymentAllocateController.CREATE_TABLE_FPAYMENT_ALLOCATE);
            arg0.execSQL(ItemLocController.TESTITEMLOC);
            arg0.execSQL(MainStockController.CREATE_FMAINSTOCK_TABLE);
            arg0.execSQL(MonthlyTargetController.CREATE_FMONTH_TARGET_TABLE);


        } catch (SQLiteException e) {
        }

    }
}