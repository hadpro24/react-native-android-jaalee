/**
 * @providesModule BeaconBroadcast
 * @flow
 */
'use strict';
import React from 'react';
import {
  NativeModules,
} from 'react-native';

const NativeBeaconJalee = NativeModules.RNAndroidJaalee;

const JaleeBeaconManager = {

  InitializerConnection: function(){
    NativeBeaconJalee.InitializerConnection();
  },
  StartRangingBeacon: function(proximityUUID, major, minor): Promise<any> {
      return new Promise((resolve, reject) => {
        NativeBeaconJalee.StartBeaconConnection(proximityUUID, major, minor, resolve, reject);
      });
  },
  connectBeaconService: function(position){
    return new Promise((resolve, reject) => {
      NativeBeaconJalee.connectBeaconService(resolve, reject);
    });
  },
  callBeaconJalee: function() {
    return NativeBeaconJalee.callBeaconJalee();
  },
  onStopRanging: function() {
    NativeBeaconJalee.onStop();
  },

};

export default JaleeBeaconManager;
