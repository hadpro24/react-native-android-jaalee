
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
  private int result_value = 0;
  private static final int REQUEST_ENABLE_BT = 1234;
  private static final Region ALL_BEACONS_REGION = new Region("rid", null, null, null);

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
    this.beaconManager = new BeaconManager(reactContext);
    // Configure BeaconManager.
    beaconManager.setRangingListener(new RangingListener() {
      @Override
      public void onBeaconsDiscovered(Region region, final List beacons) {

           Toast.makeText(reactContext, "Begining...", Toast.LENGTH_LONG).show();
           List<Beacon> JaaleeBeacons = filterBeacons(beacons);
           sendEvent(reactContext, "onBeaconJaaleeFound", createBeaconResponse(JaaleeBeacons));
           Log.e("Alvin", "finish : find beacon");
      }
    });
    //BLE device around the phone
    beaconManager.setDeviceDiscoverListener(new DeviceDiscoverListener() {
  		@Override
  		public void onBLEDeviceDiscovered(BLEDevice device) {
  			// TODO Auto-generated method stub
  			Log.i("JAALEE", "On ble device  discovery:" + device.getMacAddress());
  		}
  	});
    // Toast.makeText(reactContext, "Start... "+this.beaconManager.checkPermissionsAndService(), Toast.LENGTH_LONG).show();
    // Check if device supports Bluetooth Low Energy.
    if (!this.beaconManager.hasBluetooth()) {
      Toast.makeText(reactContext, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
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
  public void StartBeaconConnection(final String proximityUUID, int major, int minor, final Callback resolve, final Callback reject){
    minorTmp = (int) minor;

    Toast.makeText(reactContext, "Second... "+beaconManager.checkPermissionsAndService(), Toast.LENGTH_LONG).show();
    beaconManager.connect(new ServiceReadyCallback() {
        @Override
        public void onServiceReady() {
          Toast.makeText(reactContext, "Scanning...", Toast.LENGTH_LONG).show();
          try {
      			beaconManager.startRangingAndDiscoverDevice(ALL_BEACONS_REGION);
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

  private void connectToService() {
    // adapter.replaceWith(Collections.<Beacon>emptyList());
    this.beaconManager.connect(new ServiceReadyCallback() {
        @Override
        public void onServiceReady() {
          try {
      			beaconManager.startRangingAndDiscoverDevice(ALL_BEACONS_REGION);
      		} catch (RemoteException e) {
      			// TODO Auto-generated catch block
      			e.printStackTrace();
      		}
        }
    });
  }


  @ReactMethod
  public void callBeaconJalee(){
    if(connection != null){
      connection.CallBeacon();
    }else{
      connection = new BeaconConnection(this.reactContext, beacon, createConnectionCallback());
      connection.CallBeacon();
    }
  }

  private List<Beacon> filterBeacons(List<Beacon> beacons) {
     List<Beacon> filteredBeacons = new ArrayList<Beacon>(beacons.size());
     for (Beacon beacon : beacons)
     {
 //    	only detect the Beacon of Jaalee
 //    	if ( beacon.getProximityUUID().equalsIgnoreCase(JAALEE_BEACON_PROXIMITY_UUID) )
 //    	if (beacon.getRssi() > -50)
     	{
     		Log.i("JAALEE", "JAALEE:"+beacon.getBattLevel());
     		filteredBeacons.add(beacon);
     	}
     }
     return filteredBeacons;
   }

  private ConnectionCallback createConnectionCallback() {
   return new ConnectionCallback() {
     @Override
     public void onAuthenticated(final BeaconCharacteristics beaconChars) {
       UiThreadUtil.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Log.e("Alvin", "succesfully");
          }
        });
     }

     @Override
     public void onAuthenticationError() {
       UiThreadUtil.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Log.e("Alvin", "succesfully");
          }
        });
     }

     @Override
     public void onDisconnected() {
       UiThreadUtil.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Log.e("Alvin", "succesfully");
          }
        });
     }
   };
 }

 private void sendEvent(ReactContext reactContext, String eventName, WritableArray params) {
     Toast.makeText(reactContext, "Send event...", Toast.LENGTH_LONG).show();
      if (reactContext.hasActiveCatalystInstance()) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
      }
  }

  private WritableArray createBeaconResponse(List<Beacon> beacons) {
    WritableArray maps = new WritableNativeArray();
    for (Beacon beacon : beacons)
    {
       WritableMap map = new WritableNativeMap();
       map.putString("identifier", beacon.getName());
       map.putString("uuid", beacon.getProximityUUID());
       map.putInt("major", beacon.getMajor());
       map.putInt("minor", beacon.getMinor());
       map.putInt("measuredPower", beacon.getMeasuredPower());
       map.putString("macAddress", beacon.getMacAddress());
       map.putInt("rssi", beacon.getRssi());
       map.putInt("battLevel", beacon.getBattLevel());

       maps.pushMap(map);
    }
    return maps;
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
