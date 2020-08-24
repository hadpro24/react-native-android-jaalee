
package com.jaalee.sdk;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.content.Context;
import android.os.*;

import com.jaalee.sdk.connection.BeaconConnection;
import com.jaalee.sdk.service.BeaconService;
import com.jaalee.sdk.connection.ConnectionCallback;
import com.jaalee.sdk.Beacon;
import com.jaalee.sdk.BeaconManager;
import com.jaalee.sdk.RangingListener;

import java.util.List;

public class RNAndroidJaaleeModule extends ReactContextBaseJavaModule implements ConnectionCallback {

  private final ReactApplicationContext reactContext;
  private static Beacon beacon = null;
  private static Beacon beaconManager = null;
  private BeaconConnection connection;

  public RNAndroidJaaleeModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNAndroidJaalee";
  }

  @Override
  public void StartBeaconConnectionWithPassword(String proximityUUID, int major, int minor, tring password){
    int minor = (int) minor
    beaconManager = new BeaconManager(this);
    beaconManager.setRangingListener(new RangingListener() {
      @Override
      public void onBeaconsDiscovered(Region region, final List beacons) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                // Just in case if there are multiple beacons with the same uuid, major, minor.
                Beacon foundBeacon = null;
                for (Object rangedBeacon : rangedBeacons) {
                  
                  Beacon temp = (Beacon)rangedBeacon;
                  if (temp.getMinor() == minor) {
                    foundBeacon = temp;
                  }
                }
                changeBeacon(foundBeacon);
            }
        });

      }
    });

    // connection
    connection = new BeaconConnection(this, beacon, createConnectionCallback());
    connection.connectBeaconWithPassword("666666");
    connection.CallBeacon();
  }

  @Override
  public void callBeaconJalee(){
    this.statusBeacon.CallBeacon();
  }
  @Override 
  public void changeBeacon(beaconFound){
    beacon = (Beacon) beaconFound;
  }

   private ConnectionCallback createConnectionCallback() {
    return new ConnectionCallback() {
      @Override public void onAuthenticated(final BeaconCharacteristics beaconChars) {
        
      }

      @Override public void onAuthenticationError() {
        
      }

      @Override public void onDisconnected() {

      }
    };
  }


}
