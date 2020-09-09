package com.datamation.hmdsfa.helpers;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class BluetoothConnectionHelper {

    private Context context;
    private BluetoothAdapter bluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
   // private String sMAC = "00:06:20:3C:04:51";//Given Bar Code Reader
    private String sMAC =  "";
    public BluetoothConnectionHelper(Context context) {
        this.context = context;
        this.sMAC = new SharedPref(context).getGlobalVal("barcode_mac_address");
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean isSupportBluetooth(){
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)){
            return false;
        }else {
            return true;
        }
    }

    public boolean isBluetoothHardwareSupport(){
        if (bluetoothAdapter == null) {
            return false;
        }else{
            return true;
        }
    }

    public void enableBluetooth(Activity activity){
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    public String getDeviceMACAddress(){
        return sMAC;
    }


    public BluetoothDevice getDevice() throws Exception {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(getDeviceMACAddress());
        return device;
    }

}



