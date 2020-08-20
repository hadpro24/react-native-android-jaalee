
package com.jaalee.sdk;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.content.Context;

import com.jaalee.sdk.connection.BeaconConnection;
import com.jaalee.sdk.service.BeaconService;
import com.jaalee.sdk.connection.ConnectionCallback;
import com.jaalee.sdk.Beacon;
import com.jaalee.sdk.BeaconManager;

public class RNAndroidJaaleeModule extends ReactContextBaseJavaModule implements ConnectionCallback {

  private final ReactApplicationContext reactContext;
  private static Beacon beacon = null;
  private static BeaconConnection statusBeacon = null;

  public RNAndroidJaaleeModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNAndroidJaalee";
  }

  @Override
  public void StartBeaconConnectionWithPassword(String proximityUUID, String name, String macAddress,
      int major, int minor, int measuredPower, int rssi, int batt, String password){
    this.beacon = Beacon.Beacon(proximityUUID, name, macAddress, major, minor, measuredPower, rssi, batt);
    this.statusBeacon = BeaconConnection(this.reactContext, this.beacon, ConnectionCallback);
    this.statusBeacon.connectBeaconWithPassword(password);
  }

  @Override
  public void CallBeaconJalee(){
    this.statusBeacon.CallBeacon();
  }


}
