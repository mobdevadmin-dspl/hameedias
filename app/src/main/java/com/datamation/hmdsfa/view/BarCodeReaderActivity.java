package com.datamation.hmdsfa.view;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.datamation.hmdsfa.R;
import com.datamation.hmdsfa.controller.CustomerController;
import com.datamation.hmdsfa.controller.ItemController;
import com.datamation.hmdsfa.controller.OrderDetailController;
import com.datamation.hmdsfa.helpers.BluetoothConnectionHelper;
import com.datamation.hmdsfa.helpers.SharedPref;
import com.datamation.hmdsfa.model.Item;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class BarCodeReaderActivity extends AppCompatActivity {

    TextView textStatus,itemCode,itemName,avgPrice;
    ImageView back;
    ThreadConnectBTdevice threadConnectBTdevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_reader);
        this.setTitle("Barcode Reading"); // for set actionbar title
       // this.setDisplayHomeAsUpEnabled(true);

        textStatus = findViewById(R.id.tvStatus_main);
        back = findViewById(R.id.back);

        final EditText etSearchField = findViewById(R.id.etSearchField);
        etSearchField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    Log.v("ENTER CODE","Working.... ");

                    ArrayList<Item> aList = new ItemController(BarCodeReaderActivity.this)
                            .getAllItem(etSearchField.getText().toString());
                    Log.v("ENTERED CODE","itemcode "+etSearchField.getText().toString());
                    for (Item item: aList ) {
                        Log.v("code :",">> "+item.getFITEM_ITEM_CODE());
                        etSearchField.setText("");
                    }

                return false;
            }
        });

//        etSearchField.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                Log.v("ENTER CODE","Working.... ");
//                ArrayList<Item> aList = new ItemController(BarCodeReaderActivity.this)
//                        .getAllItem(etSearchField.getText().toString());
//                for (Item item: aList ) {
//                    Log.v("code :",">> "+item.getFITEM_ITEM_CODE());
//                    itemCode.setText(item.getFITEM_ITEM_CODE());
//                    itemName.setText(item.getFITEM_ITEM_NAME());
//                    avgPrice.setText(item.getFITEM_AVGPRICE());
//                    etSearchField.setText("");
//                }
//            }
//        });

        if(!new BluetoothConnectionHelper(this).isSupportBluetooth()){
            Toast.makeText(this,"FEATURE_BLUETOOTH NOT SUPPORTED", Toast.LENGTH_LONG).show();
            connectionError();
        }

        if(!new BluetoothConnectionHelper(this).isBluetoothHardwareSupport()){
            Toast.makeText(this,"Bluetooth is not supported on this hardware platform", Toast.LENGTH_LONG).show();
            connectionError();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                if(new OrderDetailController(BarCodeReaderActivity.this).isAnyActiveOrders()){
                    MaterialDialog materialDialog = new MaterialDialog.Builder(BarCodeReaderActivity.this)
                            .content("You have active orders. Cannot back without complete.")
                            .positiveText("OK")
                            .positiveColor(getResources().getColor(R.color.material_alert_positive_button))
//                            .negativeText("No")
//                            .negativeColor(getResources().getColor(R.color.material_alert_negative_button))

                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    super.onNegative(dialog);
                                }

                                @Override
                                public void onNeutral(MaterialDialog dialog) {
                                    super.onNeutral(dialog);
                                }
                            })
                            .build();
                    materialDialog.setCancelable(false);
                    materialDialog.setCanceledOnTouchOutside(false);
                    materialDialog.show();
                }else{
                    MaterialDialog materialDialog = new MaterialDialog.Builder(BarCodeReaderActivity.this)
                            .content("Do you want to back?")
                            .positiveText("Yes")
                            .positiveColor(getResources().getColor(R.color.material_alert_positive_button))
                            .negativeText("No")
                            .negativeColor(getResources().getColor(R.color.material_alert_negative_button))

                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    Intent back = new Intent(BarCodeReaderActivity.this, DebtorDetailsActivity.class);
                                    back.putExtra("outlet",new CustomerController(BarCodeReaderActivity.this).getSelectedCustomerByCode(SharedPref.getInstance(BarCodeReaderActivity.this).getSelectedDebCode()));
                                    startActivity(back);
                                    finish();
                                    dialog.dismiss();
                                }

                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    super.onNegative(dialog);
                                }

                                @Override
                                public void onNeutral(MaterialDialog dialog) {
                                    super.onNeutral(dialog);
                                }
                            })
                            .build();
                    materialDialog.setCancelable(false);
                    materialDialog.setCanceledOnTouchOutside(false);
                    materialDialog.show();
                }
            }

        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        //TURN ON BLUETOOTH IF IT IS OFF
        new BluetoothConnectionHelper(this).enableBluetooth(BarCodeReaderActivity.this);
        try {
            //MAC ADDRESS BT AUTO CONNECT.
            threadConnectBTdevice = new ThreadConnectBTdevice(new BluetoothConnectionHelper(this).getDevice());
            threadConnectBTdevice.start();

        }catch ( Exception ex ){
            Toast.makeText(BarCodeReaderActivity.this,ex.toString(), Toast.LENGTH_LONG).show();
            connectionError();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(threadConnectBTdevice!=null){
            threadConnectBTdevice.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(threadConnectBTdevice!=null){
            threadConnectBTdevice.cancel();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_barcode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.menu_item2: { // REFRESH BLUETOOTH CONNECTION
                textStatus.setText("Status : Connected.");
                textStatus.setBackgroundResource(R.color.Green);
                try {
                    threadConnectBTdevice.cancel();
                }catch ( Exception e ){
                    e.printStackTrace();
                }finally {
                    try {
                        threadConnectBTdevice = new ThreadConnectBTdevice(new BluetoothConnectionHelper(this).getDevice());
                        threadConnectBTdevice.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }

            return true;


        }
        return(super.onOptionsItemSelected(item));
    }

    // ---- BACKGROUND THREAD TO HANDLE BLUETOOTH CONNECTION --------
    private class ThreadConnectBTdevice extends Thread {

        private BluetoothSocket bluetoothSocket = null;
        private final BluetoothDevice bluetoothDevice;


        private ThreadConnectBTdevice(BluetoothDevice device) {
            bluetoothDevice = device;
            try {
                Method m = device.getClass().getMethod("createRfcommSocket", new Class[] { int.class });
                bluetoothSocket = (BluetoothSocket) m.invoke(device, 1);
            }

            catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            boolean success = false;
            try {
                bluetoothSocket.connect();
                success = true;
            } catch (IOException e) {
                e.printStackTrace();

                final String eMessage = e.getMessage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connectionError();
                    }
                });

                try {
                    bluetoothSocket.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    //textStatus.setText("STATUS : CONNECTION ERROR!");
                    e1.printStackTrace();
                }

            }

            if(success){
                //connect successful
                final String msgconnected = "Connected.";

                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        textStatus.setText("Status : Connected.");
                        textStatus.setBackgroundResource(R.color.Green);
                    }});

            }else{
                //fail
                Log.e("<<ERROR>>", "Failed to connect.");
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        connectionError();
                    }});
                //connectionError();
            }
        }

        public void cancel() {
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    private void connectionError(){
        textStatus.setText("STATUS : BLUETOOTH CONNECTION ERROR!");
        textStatus.setBackgroundResource(R.color.Red);
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }

//    @Override
//    public void onCreateOptionsMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.mnu_close, menu);
//    }


}
