
package com.jaalee.sdk;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.content.Context;
import android.os.*;
import android.content.Intent;

import com.jaalee.sdk.connection.BeaconConnection;
import com.jaalee.sdk.connection.BeaconCharacteristics;
import com.jaalee.sdk.service.BeaconService;
import com.jaalee.sdk.connection.ConnectionCallback;
import com.jaalee.sdk.Beacon;
import com.jaalee.sdk.BeaconManager;
import com.jaalee.sdk.RangingListener;
import com.jaalee.sdk.LeDeviceListAdapter;

import java.util.List;

public class RNAndroidJaaleeModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static Beacon beacon = null;
  private static BeaconManager beaconManager = null;
  private BeaconConnection connection;
  private int minorTmp;
  private LeDeviceListAdapter adapter;


  public RNAndroidJaaleeModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNAndroidJaalee";
  }

  @ReactMethod
  public void StartBeaconConnectionWithPassword(String proximityUUID, int major, int minor, String password){
    minorTmp = (int) minor;
    beaconManager = new BeaconManager(this.reactContext);
    beaconManager.setRangingListener(new RangingListener() {
      @Override
      public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                // Just in case if there are multiple beacons with the same uuid, major, minor.
                List<Beacon> JaaleeBeacons = filterBeacons(beacons);
                adapter.replaceWith(JaaleeBeacons);
            }
        });

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

    // connection
    beacon =  new Beacon(proximityUUID, "BNB Bracelet" , "F3:4E:88:E8:52:16", 2500, 3, 176, -42, 98);
    connection = new BeaconConnection(this.reactContext, beacon, createConnectionCallback());
    connection.connectBeaconWithPassword("666666");
    connection.CallBeacon();
  }

  private List<Beacon> filterBeacons(List<Beacon> beacons) {
    List<Beacon> filteredBeacons = new ArrayList<Beacon>(beacons.size());
    for (Beacon beacon : beacons) 
    {
//      only detect the Beacon of Jaalee
//      if ( beacon.getProximityUUID().equalsIgnoreCase(JAALEE_BEACON_PROXIMITY_UUID) ) 
//      if (beacon.getRssi() > -50)
      {
        Log.i("JAALEE", "JAALEE:"+beacon.getBattLevel());
        filteredBeacons.add(beacon);
      }
    }
    return filteredBeacons;
  }

  @ReactMethod
  public void callBeaconJalee(){
    connection.CallBeacon();
  }

  private void changeBeacon(Beacon beaconFound){
    beacon = (Beacon) beaconFound;
  }

   private ConnectionCallback createConnectionCallback() {
    return new ConnectionCallback() {
      @Override
      public void onAuthenticated(final BeaconCharacteristics beaconChars) {

      }

      @Override
      public void onAuthenticationError() {

      }

      @Override
      public void onDisconnected() {

      }
    };
  }


}
