package com.datamation.hmdsfa.controller;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.datamation.hmdsfa.helpers.DatabaseHelper;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Discdeb;
import com.datamation.hmdsfa.model.Disched;
import com.datamation.hmdsfa.model.Discount;
import com.datamation.hmdsfa.model.Discslab;
import com.datamation.hmdsfa.model.InvDet;
import com.datamation.hmdsfa.model.ItemBundle;
import com.datamation.hmdsfa.model.SalesPrice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscountController {

    private SQLiteDatabase dB;
    private DatabaseHelper DbHelper;
    Context context;
    private String TAG = "DiscountController";

    // table
    public static final String TABLE_DISCOUNT  = "discount";
    // table attributes
    public static final String  DISCOUNT_DEBCODE = "DebCode";
    public static final String  DISCOUNT_DEBNAME= "DebName";
    public static final String  DISCOUNT_LOCCODE = "LocCode";
    public static final String  DISCOUNT_PRODUCT_DIS = "ProductDis";
    public static final String  DISCOUNT_PRODUCT_GROUP= "ProductGroup";
    public static final String  DISCOUNT_REPCODE = "RepCode";

    // create String
    public static final String CREATE_TABLE_DISCOUNT = "CREATE  TABLE IF NOT EXISTS " + TABLE_DISCOUNT + " ("

            + DISCOUNT_REPCODE + " TEXT, " + DISCOUNT_PRODUCT_GROUP + " TEXT, "      + DISCOUNT_DEBCODE + " TEXT, " + DISCOUNT_DEBNAME + " TEXT, " + DISCOUNT_LOCCODE + " TEXT, " + DISCOUNT_PRODUCT_DIS + " TEXT); ";



    public DiscountController(Context context) {
        this.context = context;
        DbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException
    {
        dB = DbHelper.getWritableDatabase();
    }

    public void InsertOrReplaceDiscount(ArrayList<Discount> list) {
        Log.d("InsertOrReplaceSalesPri", "" + list.size());
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        try {
            dB.beginTransactionNonExclusive();
            String sql = "INSERT OR REPLACE INTO " + TABLE_DISCOUNT + " (DebCode,DebName,LocCode,ProductDis,ProductGroup,RepCode) " + " VALUES (?,?,?,?,?,?)";

            SQLiteStatement stmt = dB.compileStatement(sql);

            for (Discount discount : list) {
                stmt.bindString(1, discount.getDebCode());
                stmt.bindString(2, discount.getDebName());
                stmt.bindString(3, discount.getLocCode());
                stmt.bindString(4, discount.getProductDis());
                stmt.bindString(5, discount.getProductGroup());
                stmt.bindString(6, discount.getRepCode());
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
    public int IsDiscountCustomer(String debcode) {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;


        try {

            String selectQuery = "SELECT * FROM " + TABLE_DISCOUNT + " WHERE " + DISCOUNT_DEBCODE + " = '" + debcode + "'";

            cursor = dB.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            //  values.put(DatabaseHelper.FINVHED_IS_ACTIVE, "0");

            int cn = cursor.getCount();
            count = cn;

//            if (cn > 0) {
//                count = dB.update(DatabaseHelper.TABLE_FINVHED, values, DatabaseHelper.REFNO + " =?", new String[]{String.valueOf(refno)});
//            } else {
//                count = (int) dB.insert(DatabaseHelper.TABLE_FINVHED, null, values);
//            }

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
    public int deleteAll() {

        int count = 0;

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }
        Cursor cursor = null;
        try {

            cursor = dB.rawQuery("SELECT * FROM " + TABLE_DISCOUNT, null);
            count = cursor.getCount();
            if (count > 0) {
                int success = dB.delete(TABLE_DISCOUNT, null, null);
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

    public ArrayList<Discount> getDiscountInfo(String debcode) {

        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }

        Discount discount = new Discount();

        String selectQuery = "select * from discount where DebCode = '" + debcode + "'";

        Cursor cursor = dB.rawQuery(selectQuery, null);
        ArrayList<Discount> discounts = new ArrayList<>();
        try {

            while (cursor.moveToNext()) {
                discount.setProductDis(cursor.getString(cursor.getColumnIndex(DISCOUNT_PRODUCT_DIS)));
                discount.setProductGroup(cursor.getString(cursor.getColumnIndex(DISCOUNT_PRODUCT_GROUP)));
                discounts.add(discount);
            }
        } catch (Exception e) {
            Log.v(TAG + " Exception", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return discounts;
    }
    public Discount getSchemeByItemCode(String itemCode,String DocumentNo, String barcode,String debcode) {
        if (dB == null) {
            open();
        } else if (!dB.isOpen()) {
            open();
        }


        // commented due to date format issue and M:D:Y format is available in DB
        //String selectQuery = "select * from fdisched where refno in (select refno from fdiscdet where itemcode='" + itemCode + "') and date('now') between vdatef and vdatet";
        String selectQuery = "select * from discount where ProductGroup in (select VariantColour from ItemBundle where ItemNo = '" + itemCode + "' and Barcode = '"+barcode+"' and DocumentNo = '"+DocumentNo+"') and DebCode = '" + debcode + "'";

        Discount discount = new Discount();
        Cursor cursor = dB.rawQuery(selectQuery, null);

        try {
            while (cursor.moveToNext()) {

                discount.setProductDis(cursor.getString(cursor.getColumnIndex(DISCOUNT_PRODUCT_DIS)));

            }
        } catch (Exception e) {

            Log.v(TAG + " Exception", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dB.close();
        }

        return discount;
    }

    //rashmi 20200615
    public ArrayList<InvDet> updateInvDiscount(ArrayList<InvDet> ordArrList, String debcode) {

        ArrayList<InvDet> newMetaList = new ArrayList<InvDet>();

            /* For each invoice object inside ordeArrList ArrayList */
            for (InvDet mTranSODet : ordArrList) {
                ItemBundle item = new ItemBundleController(context).getItem(mTranSODet.getFINVDET_ITEM_CODE());

                Discount discountdets = getSchemeByItemCode(mTranSODet.getFINVDET_ITEM_CODE(),item.getDocumentNo(),item.getBarcode(),debcode);


                if (discountdets.getProductDis() != null) {
                                            /* Update table directly */
                        double discPrice = ((Double.parseDouble(mTranSODet.getFINVDET_SELL_PRICE()) / 100) * (Double.parseDouble(discountdets.getProductDis())));
                        mTranSODet.setFINVDET_SCHDISPER(discountdets.getProductDis());
                        mTranSODet.setFINVDET_DIS_PER(discountdets.getProductDis());
                        mTranSODet.setFINVDET_DIS_AMT(String.valueOf(discPrice* (Double.parseDouble(mTranSODet.getFINVDET_QTY()))));
                        if( new SharedPref(context).getGlobalVal("KeyVat").equals("VAT"))
                        mTranSODet.setFINVDET_B_SELL_PRICE(String.valueOf((Double.parseDouble(mTranSODet.getFINVDET_B_SELL_PRICE())) - discPrice));//pass for calculate tax forqow
                        else
                        mTranSODet.setFINVDET_B_SELL_PRICE(String.valueOf(Double.parseDouble(mTranSODet.getFINVDET_B_SELL_PRICE())));//pass for calculate tax forqow
                    }else{
                        mTranSODet.setFINVDET_SCHDISPER("0");
                        mTranSODet.setFINVDET_DIS_PER("0");
                        mTranSODet.setFINVDET_DIS_AMT("0");
                        mTranSODet.setFINVDET_B_SELL_PRICE(mTranSODet.getFINVDET_B_SELL_PRICE());//pass for calculate tax forqow

                }
                    newMetaList.add(mTranSODet);

                }




        return newMetaList;

    }


}
