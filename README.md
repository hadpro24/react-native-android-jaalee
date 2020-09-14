
# react-native-android-jaalee

## Getting started

`$ npm install react-native-android-jaalee --save`

### Mostly automatic installation

`$ react-native link react-native-android-jaalee`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.jaalee.sdk.RNAndroidJaaleePackage;` to the imports at the top of the file
  - Add `new RNAndroidJaaleePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-android-jaalee'
  	project(':react-native-android-jaalee').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-android-jaalee/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-android-jaalee')
  	```

## Add this to your AndroidManifest.xml
1. Open `android/app/src/main/AndroidManifest.xml`
  - Add `<service android:name="com.jaalee.sdk.service.BeaconService" android:exported="false"/>` for scanning in your `<application android:name=".MainApplication"...`
  - Assure you have:
  ```
  <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
  <uses-permission android:name="android.permission.BLUETOOTH" />
  <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
  ```
## Featurs
  - Scanning beacon JAALEE
  - Call Beacon

## Usage
```javascript
import JaleeBeaconManager from 'react-native-android-jaalee';
import { DeviceEventEmitter } from 'react-native';

// Initialize Connection
JaleeBeaconManager.InitializerConnection();

// Connection Beacon
 JaleeBeaconManager.StartRangingBeacon(uid, majoir, minor) 
 .then(() => {
   console.log('Starting connection');
 })
 .catch(e => {
   console.log('error start connection', e);
 });


 // listing my beacon
 DeviceEventEmitter.addListener('onBeaconJaaleeFound', (event) =>{
   console.log('data ...', event);
   // Object
   // identifier, uid, major, minor, rssi, measuredPower, macAddress, battLevel
   // connecting beacon service using default password
   JaleeBeaconManager.connectBeaconService().then(() =>{
     console.log('beacon connected')
   }).catch(error => {
       console.log('error connection', error)
   });
   
 });

// If beacon find run this to Call Beacon
JaleeBeaconManager.callBeaconJalee(); // this use the default password 666666

```

## You want to contribute
- dev.harouna@gmail.com

## Author
Harouna Diallo
