package com.datamation.swdsfa.freeissue;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.datamation.swdsfa.R;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/*created by rashmi-2019-09-10*/
public class NewFreeIssue {
    Context context;
    private SQLiteDatabase mydb1;
    private DatabaseHelper dbHelper;
    int NoOfF;
    int qtyFreeVal = 0;
    int totalQTYFlat = 0;
    private String TEMPSTOCK_ORDER_DETAIL = "fProducts_pre";
    AlertDialog.Builder builder;
    View alertView11;
    public NewFreeIssue(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }
    public void open() throws SQLException {
        mydb1 = dbHelper.getWritableDatabase();
    }
    ArrayList<FreeItemDetails> freeList = new ArrayList<FreeItemDetails>();
    public ArrayList<FreeItemDetails> New_FreeIssue() {
        if (mydb1 == null) {
            open();
        } else if (!mydb1.isOpen()) {
            open();
        }
        int rem = 0;

        Cursor priority_cursor = null;
        Cursor freeScheme_cursor = null;
        Cursor freehed_cursor = null;//get for check free type
        Cursor freeissue_cursor = null;
        Cursor flatfree_cursor = null;
        Cursor debScheme_cursor = null;
        Cursor debCount_cursor = null;

        List<String> priorityArray = new ArrayList<String>();
        List<String> freeScheme_Array = new ArrayList<String>();
        List<String> F_CUR3_Array = new ArrayList<String>();
        List<String> F_ITEMCODE_Array = new ArrayList<String>();
        List<String> F_REFNO_Array = new ArrayList<String>();

        List<String> QTY_L_Array = new ArrayList<String>();

        String F_TYPE = "";
        String F_REFNO = "";
        String F_QTY = "";
        String FLAT_F_QTY = "";

        String F_QTY_REFNO = "";
        int F_ITEM_QTY = 0;
        int F_FREE_QTY = 0;
        String ITEM_CODE = "";
        String ORD_REFNO = "";
        int FLAT_QTY = 0;
        int FREEITQTY = 0;
        try{
        //GET FREE ISSUE PRIORITY LIST FOR ENTERD SALES ITEMS
        try {
            String query_priority = " select DISTINCT(c.Priority),substr(c.Vdatef,7,4)||'-'||substr(c.Vdatef,1,2)||'-'||substr(c.Vdatef,4,2)||'' as Vdateg,(SELECT date('now')) as Date, " +
                    " substr(c.Vdatet,7,4)||'-'||substr(c.Vdatet,1,2)||'-'||substr(c.Vdatet,4,2)||'' as Vdated from fProducts_pre as a " +
                    " inner join fFreeDet as b on a.ItemCode_pre = b.ItemCode " +
                    " inner join fFreeHed as c on c.RefNo = b.RefNo group by c.Priority ";
            priority_cursor = mydb1.rawQuery(query_priority, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (priority_cursor != null) {
            if (priority_cursor.getCount() > 0) {
                while (priority_cursor.moveToNext()) {
                    priorityArray.add(priority_cursor.getString(0));//1,2
                }
            }
        }
            priority_cursor.close();

        //LOOP THOROW PRIORITY LIST
        for (int i = 0; i < priorityArray.size(); i++)//1,2
        {
            //GET REFNOS FOR A PRIORITY
            try {
                freeScheme_Array.clear();

                String query_freeScheme_cursor = " select DISTINCT(b.Refno),substr(c.Vdatef,7,4)||'-'||substr(c.Vdatef,1,2)||'-'||substr(c.Vdatef,4,2)||'' as Vdateg,(SELECT date('now')) as Date," +
                        " substr(c.Vdatet,7,4)||'-'||substr(c.Vdatet,1,2)||'-'||substr(c.Vdatet,4,2)||'' as Vdated from fProducts_pre as a " +
                        " inner join fFreeDet as b on a.ItemCode_pre = b.ItemCode " +
                        " inner join fFreeHed as c on c.RefNo = b.RefNo where c.Priority = '" + priorityArray.get(i) + "' " +
                       // "and (SELECT date('now')) as Date between " +
                      //  " Vdateg and Vdated " +
                        " order by b.RefNo ";
                freeScheme_cursor = mydb1.rawQuery(query_freeScheme_cursor, null);
            } catch (Exception e) {
               e.printStackTrace();
            }
            if (freeScheme_cursor != null) {
                if (freeScheme_cursor.getCount() > 0) {
                    while (freeScheme_cursor.moveToNext()) {
                       // F_CUR2_Array.add(F_CUR2.getString(0));
                        try {
                            String QueryDebSchemes = "select DISTINCT(RefNo) from FfreeDeb where RefNo =  '" + freeScheme_cursor.getString(0) + "' group by RefNo";
                            debScheme_cursor = mydb1.rawQuery(QueryDebSchemes, null);//FIS/1702/011//FIS/1702/013//FIS/1702/029
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (debScheme_cursor != null) {
                            if (debScheme_cursor.getCount() > 0) {
                                while (debScheme_cursor.moveToNext()) {

                                    try {
                                        String QueryFreeDebtors = "select * from FfreeDeb where RefNo =  '" + freeScheme_cursor.getString(0) + "'AND DebCode in (select DebCode from FOrdHed where IsActive ='1' group by DebCode)";
                                       // String Q_12 = "select * from FfreeDeb where RefNo =  '"+F_CUR2.getString(0)+"' AND DebCode in (select DebCode from FOrdHed where IsActive ='1' group by DebCode)";

                                        debCount_cursor = mydb1.rawQuery(QueryFreeDebtors, null);


                                        if (debCount_cursor != null) {
                                            if (debCount_cursor.getCount() > 0) {
                                                while (debCount_cursor.moveToNext()) {
                                                    freeScheme_Array.add(freeScheme_cursor.getString(0));
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            } else {
                                freeScheme_Array.add(freeScheme_cursor.getString(0));
                            }
                        }


                    }
                }
            }
            freeScheme_cursor.close();

            //LOOP THORWO FREE REFNOS
            for (int l = 0; l < freeScheme_Array.size(); l++) {
                F_ITEMCODE_Array.clear();
                F_REFNO_Array.clear();
                QTY_L_Array.clear();
                try {


                    String QueryFreeHed = " select Ftype,RefNo,ItemQty,FreeItQty from fFreeHed where RefNo = '" + freeScheme_Array.get(l) + "' ";
                    freehed_cursor = mydb1.rawQuery(QueryFreeHed, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (freehed_cursor != null) {
                    if (freehed_cursor.getCount() > 0) {
                        while (freehed_cursor.moveToNext()) {
                            F_TYPE = freehed_cursor.getString(0).trim();
                            F_REFNO = freehed_cursor.getString(1).trim();
                            FLAT_QTY = freehed_cursor.getInt(2);
                            FREEITQTY = freehed_cursor.getInt(3);
                        }
                    }
                }


                freehed_cursor.close();

                if (F_TYPE.equals("Mix")) {
                    String PLUSVAL = "0";
                    try {

                        String query_mix = " select a.BalQty,b.ItemCode,b.Refno,c.Priority,a.RefNo as OrdNo ,substr(c.Vdatef,7,4)||'-'||substr(c.Vdatef,1,2)||'-'||substr(c.Vdatef,4,2)||'' as Vdateg,(SELECT date('now')) as Date, " +
                                " substr(c.Vdatet,7,4)||'-'||substr(c.Vdatet,1,2)||'-'||substr(c.Vdatet,4,2)||'' as Vdated from fProducts_pre as a " +
                                " inner join fFreeDet as b on a.ItemCode_pre = b.ItemCode " +
                                " inner join fFreeHed as c on c.RefNo = b.RefNo where b.RefNo = '" + F_REFNO + "' order by a.BalQty DESC";
                        freeissue_cursor = mydb1.rawQuery(query_mix, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (freeissue_cursor != null) {
                        if (freeissue_cursor.getCount() > 0) {
                            while (freeissue_cursor.moveToNext()) {
                                PLUSVAL = String.valueOf((Integer.parseInt(PLUSVAL) + freeissue_cursor.getInt(0)));
                                //F_QTY = F_CUR4.getString(0);
                                F_QTY_REFNO = freeissue_cursor.getString(2);
                                /*	ITEM_CODE = F_CUR4.getString(1);*/
                                ORD_REFNO = freeissue_cursor.getString(4);

                                F_ITEMCODE_Array.add(freeissue_cursor.getString(1));
                                F_REFNO_Array.add(ORD_REFNO);
                                QTY_L_Array.add(freeissue_cursor.getString(0));
                            }
                        }
                    }
                    freeissue_cursor.close();

                    if (rem >= 0) {

                        try {


                            String query_flat = " select MAX(Qtyf)Qtyf,ItemQty,FreeItQty from fFreeMslab where Refno = '" + F_QTY_REFNO + "' and Qtyf <= '" + PLUSVAL + "' ";
                            flatfree_cursor = mydb1.rawQuery(query_flat, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (flatfree_cursor != null) {
                            if (flatfree_cursor.getCount() > 0) {
                                while (flatfree_cursor.moveToNext()) {
                                    F_ITEM_QTY = flatfree_cursor.getInt(1);
                                    F_FREE_QTY = flatfree_cursor.getInt(2);

                                    try {

                                        if (F_ITEM_QTY != 0) {
                                            // Old int free = ((Integer.parseInt(PLUSVAL)/F_ITEM_QTY)*F_FREE_QTY);//8
                                            Double dd = new Double(Integer.parseInt(PLUSVAL));
                                            Double de = new Double(F_ITEM_QTY);
                                            Double df = new Double(dd / de);
                                            Double dg = new Double(F_FREE_QTY);
                                            int free = (int) ((df) * dg);//8
                                            rem = (rem % F_ITEM_QTY);//2

                                            int UpdateVal = free * F_ITEM_QTY;//144

                                            //UpdateVal =  free*F_ITEM_QTY
                                            NoOfF = free;

                                            FreeItemDetails details = new FreeItemDetails();
                                            // details.setFreeItem(new
                                            // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
                                            details.setRefno(F_QTY_REFNO);
                                            details.setFreeIssueSelectedItem("should be set");
                                            details.setFreeQty((int) Math.round(NoOfF));
                                            freeList.add(details);
                                            while (0 < UpdateVal) {
                                                for (int t = 0; t < F_REFNO_Array.size(); t++)//ItemCode01,144
                                                {

                                                    int LOc = Integer.parseInt(QTY_L_Array.get(t));
                                                    if (Integer.parseInt(QTY_L_Array.get(t)) > UpdateVal) {

                                                        LOc = LOc - UpdateVal;

                                                        UpdateVal = UpdateVal - Integer.parseInt(QTY_L_Array.get(t));

                                                        ContentValues update = new ContentValues();
                                                        update.put("BalQty", LOc);
                                                        mydb1.update(TEMPSTOCK_ORDER_DETAIL, update, "Itemcode_pre ='" + F_ITEMCODE_Array.get(t) + "' AND  Refno = '" + F_REFNO_Array.get(t) + "' ", null);

                                                    } else {

                                                        LOc = LOc - Integer.parseInt(QTY_L_Array.get(t));
                                                        UpdateVal = UpdateVal - Integer.parseInt(QTY_L_Array.get(t));

                                                        ContentValues update = new ContentValues();
                                                        update.put("BalQty", LOc);
                                                        mydb1.update(TEMPSTOCK_ORDER_DETAIL, update, "Itemcode_pre ='" + F_ITEMCODE_Array.get(t) + "' AND Refno = '" + F_REFNO_Array.get(t) + "' ", null);


                                                    }

                                                }


                                            }


                                            MultiplefreeIssue(F_QTY_REFNO, rem);


                                        } else {
                                            //rem = rem+143
                                        }


                                    } catch (Exception e) {
                                        Log.v("ERROR", e.toString());
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        flatfree_cursor.close();

                    }

                } else if (F_TYPE.equals("Flat")) {
                    F_QTY = "0";
                    F_QTY_REFNO = "0";
                    ITEM_CODE = "0";
                    ORD_REFNO = "0";

                    try {


                        String query_flat = " select Sum(a.BalQty),b.ItemCode,b.Refno,c.Priority,a.Refno,substr(c.Vdatef,7,4)||'-'||substr(c.Vdatef,1,2)||'-'||substr(c.Vdatef,4,2)||'' as Vdateg,(SELECT date('now')) as Date, " +
                                " substr(c.Vdatet,7,4)||'-'||substr(c.Vdatet,1,2)||'-'||substr(c.Vdatet,4,2)||'' as Vdated from fProducts_pre as a " +
                                " inner join fFreeDet as b on a.ItemCode_pre = b.ItemCode " +
                                " inner join fFreeHed as c on c.RefNo = b.RefNo where b.RefNo = '" + F_REFNO + "' ";
                        freeissue_cursor = mydb1.rawQuery(query_flat, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (freeissue_cursor != null) {
                        if (freeissue_cursor.getCount() > 0) {
                            while (freeissue_cursor.moveToNext()) {
                                F_QTY = freeissue_cursor.getString(0);
                                F_QTY_REFNO = freeissue_cursor.getString(2);
                                ITEM_CODE = freeissue_cursor.getString(1);
                                ORD_REFNO = freeissue_cursor.getString(4);
                            }
                        }
                    }
                    freeissue_cursor.close();

                    totalQTYFlat = totalQTYFlat + Integer.parseInt(F_QTY.trim());

                    //FlatQTYParts = Double.parseDouble(FLAT_QTY.trim());
                    //Flatfree = Double.parseDouble(FREEITQTY.trim());

                    int ww = FLAT_QTY;
                    int ll = FREEITQTY;

                    int freeissues = Integer.valueOf(String.valueOf(totalQTYFlat)) / ww;
                    int NoOffreeIssue = freeissues * ll;
                    NoOfF = (int) NoOffreeIssue;
                    FreeItemDetails details = new FreeItemDetails();
                    // details.setFreeItem(new
                    // FreeItemDS(context).getFreeItemsByRefno(freeHed.getFFREEHED_REFNO()));
                    details.setRefno(F_QTY_REFNO);
                    details.setFreeIssueSelectedItem("should be set");
                    details.setFreeQty((int) Math.round(NoOfF));
                    freeList.add(details);
                    MultiplefreeIssue(F_QTY_REFNO, 0);

                    totalQTYFlat = 0;
                    totalQTYFlat = 0;
                    ww = 0;
                    ll = 0;
                    NoOfF = 0;

                } else {
/////////////////////////////////////////////////////////// slab

                }

            }

        }

    }finally {

            if (priority_cursor != null) {
                priority_cursor.close();
            }
            if (freeScheme_cursor != null) {
                freeScheme_cursor.close();
            }
            if (freehed_cursor != null) {
                freehed_cursor.close();
            }

            if (freeissue_cursor != null) {
                freeissue_cursor.close();
            }
            if (flatfree_cursor != null) {
                flatfree_cursor.close();
            }
            if (debScheme_cursor != null) {
                debScheme_cursor.close();
            }
            if (debCount_cursor != null) {
                debCount_cursor.close();
            }
        mydb1.close();
    }

return freeList;

    }
    public void MultiplefreeIssue(String ref,int Rem) {
        qtyFreeVal = 0;
        Cursor chkDebtor111 = null;
        try {
            String query111 = "select ItemCode from FfreeItem Where RefNo='" + ref.trim() + "' group by ItemCode";
            chkDebtor111 = mydb1.rawQuery(query111, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // ItemCodeList = new ArrayList<String>();
        List<String> newItemCodeList = new ArrayList<String>();

        if (chkDebtor111 != null)
        {
            if (chkDebtor111.getCount() > 0)
            {
                while (chkDebtor111.moveToNext())
                {
                    String ICode = chkDebtor111.getString(0);
                    Log.d("FFREEItemCode", ICode);
                    newItemCodeList.add(ICode);
                }
            }
        }

        //chkDebtor111.close();

        // //////////////////////////////////////

        List<String> newItemDisList = new ArrayList<String>();
        for (int i = 0; i < newItemCodeList.size(); i++) {



            Cursor chkDebtor222 = null;

            try {

                String query222 = "select ItemName from fItem Where ItemCode='"
                        + newItemCodeList.get(i).trim() + "'";
                chkDebtor222 = mydb1.rawQuery(query222, null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            // ItemCodeList = new ArrayList<String>();

            if (chkDebtor222 != null) {
                if (chkDebtor222.getCount() > 0) {
                    while (chkDebtor222.moveToNext()) {

                        String IDis = chkDebtor222.getString(0);

                        Log.d("FFREEItemName", IDis);
                        newItemDisList.add(IDis);

                    }
                }
            }
            //chkDebtor222.close();


        }

        List<String> newDisCriptionList = new ArrayList<String>();

        Cursor chkDebtor333 = null;

        try {

            String query333 = "select DISCDESC from Ffreehed Where RefNo='"
                    + ref.trim() + "'";
            chkDebtor333 = mydb1.rawQuery(query333, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ItemCodeList = new ArrayList<String>();

        if (chkDebtor333 != null) {
            if (chkDebtor333.getCount() > 0) {
                while (chkDebtor333.moveToNext()) {

                    String IDis = chkDebtor333.getString(0);

                    Log.d("FFREEItemDiscription", IDis);
                    newDisCriptionList.add(IDis);

                }
            }
        }
        //chkDebtor333.close();


        if (newItemCodeList.size() == 1) {

            if (NoOfF > 0) {

               // SelectSequence();
                // AddFreeIssueToNewInvoiceItems("27", "FD", "0",
                // String.valueOf(NoOfF), newItemCodeList.get(0));
                showDialog11(newItemCodeList, NoOfF, newItemDisList,newDisCriptionList,Rem);
//                freeList.setF
            }
        } else {

            if (NoOfF > 0) {
                showDialog11(newItemCodeList, NoOfF, newItemDisList,newDisCriptionList,0);

            }

        }
    }

    ///////////////////////////////////////////////////////////////////

    @SuppressLint("InflateParams") public void showDialog11(final List<String> newList, final int freeQty, final List<String> NameList, List<String> ItemDiscrption, final int Rem)
    {
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        LayoutInflater inflrate = LayoutInflater.from(context);
        alertView11 = inflrate.inflate(R.layout.free_issue, null);
        builder.setView(alertView11);
        builder.setTitle("Free Issue Schemas");
        builder.setMessage("Free Qty You have :" + String.valueOf(freeQty));
       // builder.setMessage("Free Qty You have :" + String.valueOf(freeQty) + "\n" + "Item Name :" + IsFree_itemName);
        builder.setMessage("Free Qty You have :" + String.valueOf(freeQty) + "\n" + "Item Name :" );
//
//        String correct = "No";
//        if (newList.size() == ItemDiscrption.size())
//        {
//
//            correct = "same";
//
//        }
//        else
//        {
//            correct = "no";
//
//            dis = ItemDiscrption.get(0);
//
//        }
//
//        final TableLayout table = (TableLayout) alertView11
//                .findViewById(R.id.tbl);
//
//        for (int j = 0; j < newList.size(); j++)
//        {
//            TableRow row = new TableRow(context);
//            final String code = newList.get(j);
//            if (correct.equals("same"))
//            {
//                dis = ItemDiscrption.get(j);
//            }
//
//            final String name = NameList.get(j);
//            final TextView tvAlloc = new TextView(context);
//            final TextView tv1 = new TextView(context);
//            final TextView tvDis = new TextView(context);
//            tvDis.setText("" + name + "");
//            tvDis.setPadding(2, 2, 2, 2);
//            tvDis.setGravity(Gravity.CENTER);
//            tvDis.setHeight(60);
//            tvDis.setTextColor(Color.WHITE);
//
//            tvDis.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    showDialog22(freeQty, code, tvAlloc, name, tvDis,Rem);
//                }
//            });
//
//            tvDis.setOnLongClickListener(new View.OnLongClickListener()
//            {
//                @Override
//                public boolean onLongClick(View v)
//                {
//                    showEditOptions(tvAlloc, code,Rem);
//                    return false;
//                }
//            });
//
//            tv1.setText("" + code + "");
//            tv1.setPadding(2, 2, 2, 2);
//            tv1.setTextColor(Color.WHITE);
//            tv1.setHeight(60);
//            tv1.setGravity(Gravity.CENTER);
//
//            tv1.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    showDialog22(freeQty, code, tvAlloc, name, tvDis,0);
//                }
//            });
//
//            tv1.setOnLongClickListener(new View.OnLongClickListener()
//            {
//                @Override
//                public boolean onLongClick(View v)
//                {
//                    showEditOptions(tvAlloc, code,0);
//                    return false;
//                }
//            });
//
//            tv1.setBackgroundResource(R.drawable.row_bordersnew);
//            tvDis.setBackgroundResource(R.drawable.row_bordersnew);
//            tvAlloc.setText(String.valueOf(qtyFreeVal));
//            tvAlloc.setPadding(2, 2, 2, 2);
//            tvAlloc.setGravity(Gravity.CENTER);
//            tvAlloc.setHeight(60);
//            tvAlloc.setTextColor(Color.WHITE);
//            tvAlloc.setBackgroundResource(R.drawable.row_bordersnew);
//
//            // row.addView(tvID);
//            row.addView(tv1);
//            row.addView(tvDis);
//
//            // row.addView(tvEQQ);
//            row.addView(tvAlloc);
//
//            table.addView(row);
//
//        }
//
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
               // freeIssue.setEnabled(true);
               // freeIssue.setClickable(true);
                qtyFreeVal = 0;
                return;
            }
        });
        builder.show();
    }
}
