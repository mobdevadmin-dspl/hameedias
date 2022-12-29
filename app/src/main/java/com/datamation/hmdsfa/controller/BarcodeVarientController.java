package com.datamation.hmdsfa.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.BarcodeVariant;
import com.datamation.hmdsfa.model.InvDet;
import com.datamation.hmdsfa.model.ItemBundle;
import com.datamation.hmdsfa.model.Product;
import com.datamation.hmdsfa.settings.ReferenceNum;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BarcodeVarientController {

/*rashmi - hameedias barcode scan modification - 2020-03-02*/
    Context context;
    private SQLiteDatabase dB;
    private DatabaseHelper dbeHelper;
    private SharedPref mSharedPref;
    private String TAG = "ItemBundleController";
    private String TAG_1 = "BarCodeVarientController";

    public static final String TABLE_ITEMBUNDLE = "ItemBundle";
    public static final String Id = "Id";
    public static final String Barcode = "Barcode";
    public static final String DocumentNo = "DocumentNo";
    public static final String ItemNo = "ItemNo";
    public static final String VariantCode = "VariantCode";
    public static final String VariantColour = "VariantColour";
    public static final String VariantSize = "VariantSize";
    public static final String Quantity = "Quantity";
    public static final String Description = "Description";
    public static final String ArticleNo = "ArticleNo";


    //table - kaveesha - 12-06-2020
    public static final String TABLE_BAR_CODE_VARIENT = "BarCodeVarient";

    //table attributes
    public static final String BARCODE_ID = "Id";
    public static final String BARCODE_NO = "Barcode_No";
    public static final String DOCUMENT_NO = "Description";
    public static final String ITEM_NO = "Item_No";
    public static final String ARTICLE_NO = "Article_No";
    public static final String VARIANT_SIZE = "Size";
    public static final String VARIANT_CODE = "Variant_Code";

    public static final String  CREATE_TABLE_BAR_CODE_VARIENT = "CREATE TABLE IF NOT EXISTS " + TABLE_BAR_CODE_VARIENT + " (" + BARCODE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ARTICLE_NO + " TEXT, " +   BARCODE_NO + " TEXT, " + DOCUMENT_NO + " TEXT, " + ITEM_NO + " TEXT, " +
            VARIANT_SIZE + " TEXT, "+ VARIANT_CODE + " TEXT ); ";

    public BarcodeVarientController(Context context) {
        this.context = context;
        dbeHelper = new DatabaseHelper(context);
        mSharedPref = SharedPref.getInstance(context);
    }

    public void open() throws SQLException {
        dB = dbeHelper.getWritableDatabase();
    }

//    public int createOrUpdateItemBundle(ArrayList<ItemBundle> list) {
//
//        int count = 0;
//
//        if (dB == null) {
//            open();
//        } else if (!dB.isOpen()) {
//            open();
//        }
//        Cursor cursor = null;
//
//        try {
//
//            for (ItemBundle itemBndl : list) {
//
//                ContentValues values = new ContentValues();
//
////                String selectQuery = "SELECT * FROM " + TABLE_ITEMBUNDLE + " WHERE " + FTAX_TAXCODE + " = '" + tax.getTAXCODE() + "'";
////
////                cursor = dB.rawQuery(selectQuery, null);
//
//                values.put(Barcode, itemBndl.getBarcode());
//                values.put(DocumentNo, itemBndl.getDocumentNo());
//                values.put(ItemNo, itemBndl.getItemNo());
//                values.put(VariantCode, itemBndl.getVariantCode());
//                values.put(VariantColour, itemBndl.getVariantColour());
//                values.put(VariantSize, itemBndl.getVariantSize());
//                values.put(Quantity, itemBndl.getQuantity());
//                values.put(Description, itemBndl.getDescription());
//                values.put(ArticleNo, itemBndl.getArticleNo());
//
////                int cn = cursor.getCount();
////                if (cn > 0)
//               //     count = dB.update(TABLE_FTAX, values, FTAX_TAXCODE + " =?", new String[]{String.valueOf(tax.getTAXCODE())});
//               // else
//                    count = (int) dB.insert(TABLE_ITEMBUNDLE, null, values);
//
//            }
//        } catch (Exception e) {
//
//            Log.v(TAG + " Exception", e.toString());
//
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            dB.close();
//        }
//        return count;
//
//    }

    public void InsertOrReplaceBarcodeVariant(ArrayList<BarcodeVariant> list) {

       // deleteAll();
        Log.d(">>InsrtOrRepBarcodeVari", ">>" + list.size());

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_BAR_CODE_VARIENT + " (Barcode_No,Description,Item_No,Size,Variant_Code,Article_No) " + " VALUES (?,?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (BarcodeVariant barcodevariant : list) {

               // Log.d(">>check item",">>"+barcodevariant.toString());
                stmt.bindString(1, barcodevariant.getBarcode());
                stmt.bindString(2, barcodevariant.getDescription());
                stmt.bindString(3, barcodevariant.getItemNo());
                stmt.bindString(4, barcodevariant.getVariantSize());
                stmt.bindString(5, barcodevariant.getVariantCode());
                stmt.bindString(6, barcodevariant.getArticleNo());

                stmt.execute();
                stmt.clearBindings();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dB.setTransactionSuccessful();
            dB.endTransaction();
            dB.close();
        }

    }
    public ArrayList<ItemBundle> getItemsInBundle(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<ItemBundle> list = new ArrayList<>();
        try {

        //  String selectQuery = "SELECT *  FROM fItem WHERE ItemCode LIKE '%"+itemCode+"%'cccc";
//  Org Rashmi 16-09-2020      String selectQuery =  "SELECT * FROM BarCodeVarient WHERE  Barcode_No = '" + itemCode + "' ";
            String selectQuery =  "SELECT * FROM BarCodeVarient WHERE  Barcode_No = '" + itemCode + "' and Item_No not in (select itemcode from fitem where substr(GroupCode,1,2)=='FB')";

      cursor = dB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()) {

            ItemBundle items = new ItemBundle();

            items.setBarcode(cursor.getString(cursor.getColumnIndex(BARCODE_NO)));
            items.setDocumentNo("");
            items.setItemNo(cursor.getString(cursor.getColumnIndex(ITEM_NO)));
            items.setVariantCode(cursor.getString(cursor.getColumnIndex(VARIANT_CODE)));
            items.setVariantColour("");
            items.setVariantSize(cursor.getString(cursor.getColumnIndex(VARIANT_SIZE)));
            items.setQuantity(1);
            items.setDescription(DOCUMENT_NO);
            items.setArticleNo(cursor.getString(cursor.getColumnIndex(ARTICLE_NO)));


            list.add(items);
        }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            dB.close();
        }

        return list;
    }
    public void mUpdateInvoice(String barcode, String itemCode, String Qty, String price, String variantcode, String qoh, String aricleno, String documentNo,int seqno) {

        ArrayList<InvDet> arrList = new ArrayList<>();
        InvDet invDet = new InvDet();
        double unitprice = 0.0;
        // String taxamt = new VATController(getActivity()).calculateTax(mSharedPref.getGlobalVal("KeyVat"),new BigDecimal(amt));
        String taxRevValue = new VATController(context).calculateReverse(mSharedPref.getGlobalVal("KeyVat"),new BigDecimal(price));
        // unitprice = Double.parseDouble(price) - Double.parseDouble(taxRevValue);


//by rashmi 2020/06/22 according to meeting minute(2020/06/17) point 02
        if(new CustomerController(context).getCustomerVatStatus(mSharedPref.getSelectedDebCode()).trim().equals("VAT")){
            unitprice = Double.parseDouble(price) - Double.parseDouble(taxRevValue);
            //by rashmi 2020/06/23
            //BSell price get for tax forward, if customer vat, set b sell price reversing tax
            invDet.setFINVDET_B_SELL_PRICE(String.format("%.2f", unitprice));
        }else if(new CustomerController(context).getCustomerVatStatus(mSharedPref.getSelectedDebCode()).trim().equals("NOVAT")){
            unitprice = Double.parseDouble(price);
            //by rashmi 2020/06/23
            //if customer novat, pass unit price without reversing tax, but b sell price set reversing tax for use to forward tax
            invDet.setFINVDET_B_SELL_PRICE(String.format("%.2f", (unitprice- Double.parseDouble(taxRevValue))));
        }else{
            Toast.makeText(context,"This customer doesn't have VAT status(VAT?/NOVAT?)",Toast.LENGTH_SHORT).show();
        }
        double amt = unitprice * Double.parseDouble(Qty);
        invDet.setFINVDET_B_AMT(String.format("%.2f", amt));
        invDet.setFINVDET_SELL_PRICE(String.format("%.2f", unitprice));

        invDet.setFINVDET_BT_SELL_PRICE(String.format("%.2f", unitprice));
        invDet.setFINVDET_DIS_AMT("0");
        invDet.setFINVDET_DIS_PER("0");
        invDet.setFINVDET_ITEM_CODE(itemCode);
        // invDet.setFINVDET_PRIL_CODE(SharedPref.getInstance(getActivity()).getSelectedDebtorPrilCode());
        invDet.setFINVDET_QTY(Qty);
        invDet.setFINVDET_PICE_QTY(Qty);
        invDet.setFINVDET_TYPE("Invoice");
        invDet.setFINVDET_BT_TAX_AMT("0");
        invDet.setFINVDET_TAX_AMT("0");
        invDet.setFINVDET_RECORD_ID("");
        invDet.setFINVDET_SEQNO(seqno + "");
        invDet.setFINVDET_T_SELL_PRICE(price);
        invDet.setFINVDET_REFNO(new ReferenceNum(context).getCurrentRefNo(context.getResources().getString(R.string.VanNumVal)));
        invDet.setFINVDET_BRAND_DISCPER("0");
        invDet.setFINVDET_BRAND_DISC("0");
        invDet.setFINVDET_COMDISC("0");
        invDet.setFINVDET_TXN_DATE(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        invDet.setFINVDET_TXN_TYPE("22");
        invDet.setFINVDET_IS_ACTIVE("1");
        invDet.setFINVDET_QOH(qoh);
        invDet.setFINVDET_DISVALAMT("0");
        invDet.setFINVDET_PRICE(price);
        invDet.setFINVDET_CHANGED_PRICE("0");
        invDet.setFINVDET_AMT(String.format("%.2f", amt));
        invDet.setFINVDET_BAL_QTY(Qty);
        invDet.setFINVDET_BARCODE(barcode);
        invDet.setFINVDET_ARTICLENO(aricleno);
        invDet.setFINVDET_VARIANTCODE(variantcode);
        invDet.setFINVDET_PRIL_CODE(documentNo);
        arrList.add(invDet);
        new InvDetController(context).createOrUpdateBCInvDet(arrList);
    }

    public ArrayList<ItemBundle> getFabricItems(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<ItemBundle> list = new ArrayList<>();
        try {

            //  String selectQuery = "SELECT *  FROM fItem WHERE ItemCode LIKE '%"+itemCode+"%'";
            String selectQuery =  "SELECT * FROM BarCodeVarient WHERE  trim(Barcode_No) = '" + itemCode + "' and trim(Item_No) in (select trim(itemcode) from fitem where substr(trim(GroupCode),1,2)=='FB')";
          //  String selectQuery =  "SELECT * FROM BarCodeVarient WHERE  Barcode_No = '3191200165' and Item_No in (select itemcode from fitem where substr(GroupCode,1,2)=='FB')";


            cursor = dB.rawQuery(selectQuery, null);
            while(cursor.moveToNext()) {

                ItemBundle items = new ItemBundle();

                items.setBarcode(cursor.getString(cursor.getColumnIndex(BARCODE_NO)));
                items.setDocumentNo("");
                items.setItemNo(cursor.getString(cursor.getColumnIndex(ITEM_NO)));
                items.setVariantCode(cursor.getString(cursor.getColumnIndex(VARIANT_CODE)));
                items.setVariantColour("");
                items.setVariantSize(cursor.getString(cursor.getColumnIndex(VARIANT_SIZE)));
                items.setQuantity(0);
                items.setDescription(DOCUMENT_NO);
                items.setArticleNo(cursor.getString(cursor.getColumnIndex(ARTICLE_NO)));


                list.add(items);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            dB.close();
        }

        return list;
    }
    public ArrayList<ItemBundle> getItemsByArticleNo(String articleno) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<ItemBundle> list = new ArrayList<>();
        try {
            //cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FPRODUCT_PRE + " WHERE itemcode || itemname LIKE '%" + newText + "%' and TxnType = '"+txntype+"' ORDER BY QOH DESC", null);
            cursor = dB.rawQuery("SELECT * FROM  BarCodeVarient WHERE Article_No LIKE '%" + articleno + "%'", null);

            while (cursor.moveToNext()) {
                ItemBundle items=new ItemBundle();

                items.setBarcode(cursor.getString(cursor.getColumnIndex(BARCODE_NO)));
                items.setDocumentNo("");
                items.setItemNo(cursor.getString(cursor.getColumnIndex(ITEM_NO)));
                items.setVariantCode(cursor.getString(cursor.getColumnIndex(VARIANT_CODE)));
                items.setVariantColour("");
                items.setVariantSize(cursor.getString(cursor.getColumnIndex(VARIANT_SIZE)));
                items.setQuantity(1);
                items.setDescription(DOCUMENT_NO);
                items.setArticleNo(cursor.getString(cursor.getColumnIndex(ARTICLE_NO)));


                list.add(items);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            dB.close();
        }

        return list;
    }
    public ArrayList<String> getItems() {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            //cursor = dB.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FPRODUCT_PRE + " WHERE itemcode || itemname LIKE '%" + newText + "%' and TxnType = '"+txntype+"' ORDER BY QOH DESC", null);
            cursor = dB.rawQuery("SELECT * FROM  BarCodeVarient ", null);

            while (cursor.moveToNext()) {

                String item = cursor.getString(cursor.getColumnIndex(BARCODE_NO))+" - "+cursor.getString(cursor.getColumnIndex(ARTICLE_NO));
//                items.setBarcode(cursor.getString(cursor.getColumnIndex(BARCODE_NO)));
//                items.setDocumentNo("");
//                items.setItemNo(cursor.getString(cursor.getColumnIndex(ITEM_NO)));
//                items.setVariantCode(cursor.getString(cursor.getColumnIndex(VARIANT_CODE)));
//                items.setVariantColour("");
//                items.setVariantSize(cursor.getString(cursor.getColumnIndex(VARIANT_SIZE)));
//                items.setQuantity(1);
//                items.setDescription(DOCUMENT_NO);
//                items.setArticleNo(cursor.getString(cursor.getColumnIndex(ARTICLE_NO)));


                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            dB.close();
        }

        return list;
    }
    public ArrayList<Product> getScannedtems(ItemBundle itembundle) {


        ArrayList<Product> list = new ArrayList<>();
        try {
            Product product = new Product();
            String price = new SalesPriceController(context).getPrice(itembundle.getItemNo(),itembundle.getVariantCode());
            //   product.setFPRODUCT_ID(cursor.getString(cursor.getColumnIndex(FPRODUCT_ID)));
            product.setFPRODUCT_ITEMCODE(itembundle.getItemNo());
            product.setFPRODUCT_ITEMNAME(itembundle.getDescription());
            product.setFPRODUCT_Barcode(itembundle.getBarcode());
            product.setFPRODUCT_DocumentNo(itembundle.getDocumentNo());
            product.setFPRODUCT_VariantCode(itembundle.getVariantCode());
            product.setFPRODUCT_VariantColour(itembundle.getVariantColour());
            product.setFPRODUCT_VariantSize(itembundle.getVariantSize());
            product.setFPRODUCT_QTY(String.valueOf(itembundle.getQuantity()));
            product.setFPRODUCT_Price(price);
//                product.setFPRODUCT_QOH(cursor.getString(cursor.getColumnIndex(FPRODUCT_QOH)));
            product.setFPRODUCT_ArticleNo(itembundle.getArticleNo());
            product.setFPRODUCT_IsScan("1");

            list.add(product);



        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(">>ScannedList",">>"+list.toString());
        return list;
    }
    public ItemBundle getItem(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }


        ItemBundle items=new ItemBundle();
        //  String selectQuery = "SELECT *  FROM fItem WHERE ItemCode LIKE '%"+itemCode+"%'";
        String selectQuery =  "SELECT * FROM ItemBundle WHERE  Barcode = '" + itemCode + "' ";


        Cursor cursor = dB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){

            items.setBarcode(cursor.getString(cursor.getColumnIndex(Barcode)));
            items.setDocumentNo(cursor.getString(cursor.getColumnIndex(DocumentNo)));
            items.setItemNo(cursor.getString(cursor.getColumnIndex(ItemNo)));
            items.setVariantCode(cursor.getString(cursor.getColumnIndex(VariantCode)));
            items.setVariantColour(cursor.getString(cursor.getColumnIndex(VariantColour)));
            items.setVariantSize(cursor.getString(cursor.getColumnIndex(VariantSize)));
            items.setQuantity(cursor.getDouble(cursor.getColumnIndex(Quantity)));
            items.setDescription(cursor.getString(cursor.getColumnIndex(Description)));
            items.setArticleNo(cursor.getString(cursor.getColumnIndex(ArticleNo)));

        }

        return items;
    }


    public ArrayList<ItemBundle> getItemWiseScanDetails(String itemCode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        ArrayList<ItemBundle> list = new ArrayList<ItemBundle>();

        //  String selectQuery = "SELECT *  FROM fItem WHERE ItemCode LIKE '%"+itemCode+"%'";
        String selectQuery =  "SELECT * FROM ItemBundle WHERE  Barcode = '" + itemCode + "' ";


        Cursor cursor = dB.rawQuery(selectQuery, null);
        while(cursor.moveToNext()){

            ItemBundle items=new ItemBundle();

            items.setBarcode(cursor.getString(cursor.getColumnIndex(Barcode)));
            items.setDocumentNo(cursor.getString(cursor.getColumnIndex(DocumentNo)));
            items.setItemNo(cursor.getString(cursor.getColumnIndex(ItemNo)));
            items.setVariantCode(cursor.getString(cursor.getColumnIndex(VariantCode)));
            items.setVariantColour(cursor.getString(cursor.getColumnIndex(VariantColour)));
            items.setVariantSize(cursor.getString(cursor.getColumnIndex(VariantSize)));
            items.setQuantity(cursor.getDouble(cursor.getColumnIndex(Quantity)));
            items.setDescription(cursor.getString(cursor.getColumnIndex(Description)));
            items.setArticleNo(cursor.getString(cursor.getColumnIndex(ArticleNo)));


            list.add(items);

        }

        return list;
    }
    public String getItemNameByCode(String code) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        String selectQuery = "SELECT * FROM ItemBundle WHERE ItemNo ='" + code + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        try {
            while (cursor.moveToNext()) {

                return cursor.getString(cursor.getColumnIndex(Description));

            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }
        return "";
    }
    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_ITEMBUNDLE, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_ITEMBUNDLE, null, null);
                Log.v("Success", success + "");
            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return count;

    }

    public int deleteAll_BarcodeVariant() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_BAR_CODE_VARIENT, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_BAR_CODE_VARIENT, null, null);
                Log.v("Success", success + "");
            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return count;

    }

    public String getItemNameByArticleNo(String articleNo)
    {
        String ArticleNo = null;

        if (dB == null)
        {
            open();
        }
        else if (!dB.isOpen())
        {
            open();
        }


        try
        {
            String selectQuery = "SELECT Description FROM '" + TABLE_BAR_CODE_VARIENT +"' WHERE Article_No = '"+ articleNo.trim() +"' ";
            Cursor cursor = dB.rawQuery(selectQuery,null);
            cursor.moveToFirst();

            if(cursor.getCount() > 0)
            {
                ArticleNo = cursor.getString(cursor.getColumnIndex("Description"));

                if(!ArticleNo.isEmpty())
                {
                    return ArticleNo;
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(dB.isOpen())
            {
                dB.close();
            }
        }

        return ArticleNo;
    }



}
