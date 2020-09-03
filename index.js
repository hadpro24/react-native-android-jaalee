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
  StartBeaconConnection: function(proximityUUID, major, minor): Promise<any> {
      return new Promise((resolve, reject) => {
        NativeBeaconJalee.StartBeaconConnection(proximityUUID, major, minor, resolve, reject);
      })
  },
  callBeaconJalee: function() {
    NativeBeaconJalee.callBeaconJalee();
  },
  onStopRanging: function() {
    NativeBeaconJalee.onStop();
  },

};

export default JaleeBeaconManager;
