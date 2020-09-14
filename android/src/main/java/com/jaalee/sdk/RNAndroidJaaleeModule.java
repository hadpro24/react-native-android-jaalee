
package com.jaalee.sdk;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.WritableArray;

import android.content.Context;
import android.os.*;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.bluetooth.BluetoothAdapter;
import android.app.Activity;
import android.Manifest;
import android.app.Activity;
import androidx.core.app.ActivityCompat;
// import org.jetbrains.annotations.Nullable;

import com.jaalee.sdk.connection.BeaconConnection;
import com.jaalee.sdk.connection.BeaconCharacteristics;
import com.jaalee.sdk.service.BeaconService;
import com.jaalee.sdk.connection.ConnectionCallback;
import com.jaalee.sdk.Beacon;
import com.jaalee.sdk.BeaconManager;
import com.jaalee.sdk.RangingListener;
import com.jaalee.sdk.DeviceDiscoverListener;
import com.jaalee.sdk.ServiceReadyCallback;
import com.jaalee.sdk.LeDeviceListAdapter;
// import com.jaalee.sdk.LeDeviceListAdapter;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

public class RNAndroidJaaleeModule extends ReactContextBaseJavaModule {

private ReactApplicationContext reactContext;
 private static Beacon beacon = null;
 private static BeaconManager beaconManager = null;
 private BeaconConnection connection;
 private Region region;
 private int minorTmp;
 private String proximityUUIDtmp;
 private int result_value = 0;
 private static final int REQUEST_ENABLE_BT = 1234;
 private static final Region ALL_BEACONS_REGION = new Region("regionId", null, null, null);
 private static ArrayList<Beacon> beaconsFound;
 private LeDeviceListAdapter adapter;

 private Context mApplicationContext;

 // private LeDeviceListAdapter adapter;

 public RNAndroidJaaleeModule(ReactApplicationContext reactContext) {
   super(reactContext);
   this.reactContext = reactContext;
 }

 @Override
 public String getName() {
   return "RNAndroidJaalee";
 }

 @ReactMethod
 public void InitializerConnection(){
   // ActivityCompat.requestPermissions(getCurrentActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
   //   Manifest.permission.ACCESS_COARSE_LOCATION},1);

  // Configure device list.
   this.beaconManager = new BeaconManager(reactContext);
   // Configure BeaconManager.
   beaconManager.setRangingListener(new RangingListener() {
     @Override
     public void onBeaconsDiscovered(Region region, final List beacons) {
         Beacon JaaleeBeacons = filterBeacons(beacons);
         if(JaaleeBeacons != null){
            sendEvent(reactContext, "onBeaconJaaleeFound", createBeaconResponse(JaaleeBeacons));
         }
     }

   });
   //BLE device around the phone
   beaconManager.setDeviceDiscoverListener(new DeviceDiscoverListener() {
     @Override
     public void onBLEDeviceDiscovered(BLEDevice device) {
       Log.i("JAALEE", "On ble device  discovery:" + device.getMacAddress());
     }
   });
   // Check if device supports Bluetooth Low Energy.
   if (!this.beaconManager.hasBluetooth()) {
     Toast.makeText(getCurrentActivity(), "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
     return;
   }

   // If Bluetooth is not enabled, let user enable it.
   if (!beaconManager.isBluetoothEnabled()) {
     final Activity activity = getCurrentActivity();
     Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
     activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
   }
 }


 @ReactMethod
 public void StartBeaconConnection(final String proximityUUID, final int major, final int minor,
    final Callback resolve, final Callback reject){
   minorTmp = (int) minor;
   proximityUUIDtmp = proximityUUID;

   onStop();
   if(connection != null && connection.isConnected()){
     sendEvent(reactContext, "onBeaconJaaleeFound", createBeaconResponse(beacon));
     resolve.invoke();
     return;
   }
   // Toast.makeText(reactContext, "Second... "+beaconManager.checkPermissionsAndService(), Toast.LENGTH_LONG).show();
   beaconManager.connect(new ServiceReadyCallback() {
       @Override
       public void onServiceReady() {
         // Toast.makeText(reactContext, "Scanning...", Toast.LENGTH_LONG).show();
         try {
           Region re = new Region("regionId", proximityUUID, major, minor);
           beaconManager.startRangingAndDiscoverDevice(re);
           resolve.invoke();
         } catch (RemoteException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
           reject.invoke(e.getMessage());
         }
       }
   });

   // end function
 }

 @ReactMethod
 public void connectBeaconService(final Callback resolve, final Callback reject){
   onStop();
   if(connection != null && connection.isConnected()){
     return;
   }
   // Beacon temp = adapter.getItem(position);
   // // "ebefd083-70a2-47c8-9837-e7b5634df599", "iMATA Bracelet", "EF:49:EB:5A:B7:34", 2500, 153, -80, -59, 79
   String mac = beacon.getMacAddress();
   if(beacon == null){
     return;
   }
   Toast.makeText(reactContext, "Connecting..."+mac, Toast.LENGTH_LONG).show();
   connection = new BeaconConnection(reactContext, beacon, new ConnectionCallback(){
     @Override
     public void onAuthenticated(final BeaconCharacteristics beaconChars){
         Log.e("Alvin", "succesfully");
         resolve.invoke();
     }
     @Override
     public void onAuthenticationError() {
       Log.e("Alvin", "auth error");
        reject.invoke("EERROR_AUTHENTICATED");
     }

     @Override
     public void onDisconnected() {
       Log.e("Alvin", "succesfully");
       reject.invoke("BEACON_DISCONNECTED");
      }
   });

   connectBeaconWithPassword("666666");
 }

 private void connectBeaconWithPassword(String password){
   if (!connection.isConnected()) {
      // Toast.makeText(reactContext, "Successfuly 1...", Toast.LENGTH_LONG).show();
      connection.connectBeaconWithPassword("666666");
    }else{
      // Toast.makeText(reactContext, "Successfuly 2...", Toast.LENGTH_LONG).show();
      connection.connectBeaconWithPassword("666666");
    }
 }

 @ReactMethod
 public Boolean callBeaconJalee(){
   if(connection != null){
    connection.CallBeacon();
    return true;
   }
   return false;
 }

 private Beacon filterBeacons(List<Beacon> beacons) {
    // Beacon filteredBeacons = new Beacon();
    // Toast.makeText(reactContext, "filtred...", Toast.LENGTH_LONG).show();
    for (Beacon _beacon : beacons){
     	if(_beacon.getMinor() == minorTmp && _beacon.getProximityUUID().equals(proximityUUIDtmp)){
         beacon = _beacon;
         break;
       }
    }
    return beacon;
  }


private void sendEvent(ReactContext reactContext, String eventName, WritableMap params) {
    // Toast.makeText(reactContext, "Send event...", Toast.LENGTH_LONG).show();
     if(reactContext.hasActiveCatalystInstance()) {
       reactContext
               .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
               .emit(eventName, params);
     }
 }

 private WritableMap createBeaconResponse(Beacon beacon) {
   WritableMap map = new WritableNativeMap();
   map.putString("identifier", beacon.getName());
   map.putString("uuid", beacon.getProximityUUID());
   map.putInt("major", beacon.getMajor());
   map.putInt("minor", beacon.getMinor());
   map.putInt("measuredPower", beacon.getMeasuredPower());
   map.putString("macAddress", beacon.getMacAddress());
   map.putInt("rssi", beacon.getRssi());
   map.putInt("battLevel", beacon.getBattLevel());

   return map;
}

@ReactMethod
public void onStop() {
   try {
   beaconManager.stopRanging(ALL_BEACONS_REGION);
 } catch (RemoteException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
 }
   // super.onStop();
 }


}
