
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
## Featurs
  - Connection beacon JAALEE
  - Call Beacon

## Usage
```javascript
import RNAndroidJaalee from 'react-native-android-jaalee';

// Connection Beacon
RNAndroidJaalee.StartBeaconConnectionWithPassword(proximityUUID,major, minor, password);
// Call Beacon - Vibrate
 RNAndroidJaalee.CallBeaconJalee();

```

## Author
Harouna Diallo
