package com.datamation.hmdsfa.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.adapter.StockInquiryAdaptor;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.SalRepController;
import com.datamation.hmdsfa.controller.VanStockController;
import com.datamation.hmdsfa.helpers.ListExpandHelper;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.StockInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.SearchView;

public class StockInquiryDialog
{
    public static final String SETTINGS = "SETTINGS";
    public static SharedPreferences localSP;
    ListView lvStockData,lvGStockData;
    String LocCode = "";
    ArrayList<StockInfo> arrayList;
    TextView txtTotQty;
    Spinner spn_stock_type;
    String PRINTER_MAC_ID;
    Context context;
    boolean isStock = false;
    ProgressBar prBar;
    StockInquiryAdaptor adaptor;
    SearchView productWiseSearch,groupWiseSearch;
    SharedPref pref;

    public StockInquiryDialog(final Context context) {
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.sales_mangement_stock_inquiry, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Stock Inquiry");
        alertDialogBuilder.setView(view);

        pref = new SharedPref(context);

        lvStockData = (ListView) view.findViewById(R.id.listviewStockData);
        lvGStockData = (ListView) view.findViewById(R.id.listviewGStockData);
        localSP = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
        PRINTER_MAC_ID = localSP.getString("printer_mac_address", "").toString();
        txtTotQty = (TextView) view.findViewById(R.id.txtTotQty);
        prBar = (ProgressBar)view.findViewById(R.id.stock_progress);
        spn_stock_type = (Spinner) view.findViewById(R.id.spn_StockType);
        productWiseSearch = (SearchView) view.findViewById(R.id.search_by_product_wise);
        groupWiseSearch = (SearchView) view.findViewById(R.id.search_by_group_wise);

        prBar.setVisibility(View.VISIBLE);

        List<String> listStockType = new ArrayList<String>();
        listStockType.add("VAN STOCK");
        listStockType.add("MAIN STOCK");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, listStockType);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_stock_type.setAdapter(dataAdapter);

        spn_stock_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){//van stock
                    isStock = false;
                    lvStockData.setAdapter(null);
                    LocCode = new SalRepController(context).getCurrentLoccode().trim();
                    txtTotQty.setText(new VanStockController(context).getTotalQOH(LocCode));
                   pref.setGlobalVal("stockType" , spn_stock_type.getSelectedItem().toString());
                    new LoadStockData(LocCode).execute();
                    new LoadGwiseStockData(LocCode).execute();

                }
                else if(position == 1)//Main stock
                {
                    isStock = true;
                    lvStockData.setAdapter(null);
                    LocCode = "MAINSTORES";
                    txtTotQty.setText(new ItemController(context).getTotalStockQOH(LocCode));
                    new LoadStockData(LocCode).execute();
                    new LoadGwiseStockData(LocCode).execute();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        productWiseSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                
                if(pref.getGlobalVal("stockType").equals("VAN STOCK")){
                    arrayList = new VanStockController(context).getVanStocks(new SalRepController(context).getCurrentLoccode().trim(),query);
                }
                else {
                    arrayList = new ItemController(context).getStocks(query,new SalRepController(context).getCurrentLoccode().trim());

                }
                lvStockData.setAdapter(new StockInquiryAdaptor(context, arrayList));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(pref.getGlobalVal("stockType").equals("VAN STOCK")){

                    arrayList = new VanStockController(context).getVanStocks(new SalRepController(context).getCurrentLoccode().trim(),newText);
                }
                else {
                    arrayList = new ItemController(context).getStocks(newText,new SalRepController(context).getCurrentLoccode().trim());
                }
                lvStockData.setAdapter(new StockInquiryAdaptor(context, arrayList));
                return true;
            }
        });

        groupWiseSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                if(pref.getGlobalVal("StockType").equals("VAN STOCK")){

                    arrayList = new VanStockController(context).getGwiseVanStocks(new SalRepController(context).getCurrentLoccode().trim(),query);
                }
                else {

                    arrayList = new ItemController(context).getGwiseStocks(query,new SalRepController(context).getCurrentLoccode().trim());
                }
                lvGStockData.setAdapter(new StockInquiryAdaptor(context, arrayList));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(pref.getGlobalVal("StockType").equals("VAN STOCK")){

                    arrayList = new VanStockController(context).getGwiseVanStocks(new SalRepController(context).getCurrentLoccode().trim(),newText);
                }
                else {
                    arrayList = new ItemController(context).getGwiseStocks(newText,new SalRepController(context).getCurrentLoccode().trim());
                }
                lvGStockData.setAdapter(new StockInquiryAdaptor(context, arrayList));
                return true;
            }
        });


        alertDialogBuilder.setCancelable(false).setPositiveButton("Print", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mPrintStock();
            }
        });

        alertDialogBuilder.setCancelable(false).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        ListExpandHelper.getListViewSize(lvStockData);
        ListExpandHelper.getListViewSize(lvGStockData);

    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        } else if (!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        return true;
    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void doProgress(String s, int timeout) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(s);
        progressDialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, timeout);

    }

    /*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public void mPrintStock() {

        setBluetooth(true);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Please confirm printing ?");
        alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                //PrintStock();
                doProgress("Stock details being printed..!", 3000);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                setBluetooth(false);
                dialog.cancel();
            }
        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
    }

    class LoadStockData extends AsyncTask<String, String, String> {

        //ProgressDialog progressDialog;
        StockInquiryAdaptor adaptor;
        String locCode;

        public LoadStockData(String locCode) {
            this.locCode = locCode;
        }

        @Override
        protected void onPreExecute() {
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Please wait while loading ...");
//            progressDialog.show();
            prBar.setVisibility(View.VISIBLE);
            lvStockData.clearTextFilter();
            lvStockData.setAdapter(null);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            if(!isStock)
            {
                arrayList = new VanStockController(context).getVanStocks(locCode,"");
            }
            else
            {
                arrayList = new ItemController(context).getStocks("", locCode);
            }

            adaptor = new StockInquiryAdaptor(context, arrayList);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            lvStockData.setAdapter(adaptor);
//            progressDialog.dismiss();
            prBar.setVisibility(View.GONE);
            super.onPostExecute(result);
        }
    }

    //-------------kaveesha -----------28/08/2020-----------To show Product Group wise stock---------------------------------
    class LoadGwiseStockData extends AsyncTask<String, String, String> {

        StockInquiryAdaptor adaptor;
        String locCode;

        public LoadGwiseStockData(String locCode) {
            this.locCode = locCode;
        }

        @Override
        protected void onPreExecute() {
            prBar.setVisibility(View.VISIBLE);
            lvGStockData.clearTextFilter();
            lvGStockData.setAdapter(null);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            if(!isStock)
            {
                arrayList = new VanStockController(context).getGwiseVanStocks(locCode,"");

            }
            else
            {
                arrayList = new ItemController(context).getGwiseStocks("",locCode);
            }

            adaptor = new StockInquiryAdaptor(context, arrayList);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            lvGStockData.setAdapter(adaptor);
//            progressDialog.dismiss();
            prBar.setVisibility(View.GONE);
            super.onPostExecute(result);
        }
    }


}
