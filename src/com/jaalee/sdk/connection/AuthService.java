 package com.jaalee.sdk.connection;
 
 import android.bluetooth.BluetoothGattCharacteristic;
 import android.bluetooth.BluetoothGattService;
 import java.util.HashMap;
 import java.util.List;
 import java.util.UUID;
 /**
  * http://www.jaalee.com/
  * Jaalee, Inc.
  * This project is for developers, not for commercial purposes.
  * For the source codes which can be  used for commercial purposes, please contact us directly.
  * 
  * @author Alvin.Bert
  * Alvin.Bert.hu@gmail.com
  * 
  * service@jaalee.com
  */

 public class AuthService implements BluetoothService
 {
	 private final HashMap<UUID, BluetoothGattCharacteristic> characteristics = new HashMap<UUID, BluetoothGattCharacteristic>();
 
	 public void processGattServices(List<BluetoothGattService> services)
	 {
		 for (BluetoothGattService service : services)
			 if (JaaleeUuid.AUTH_SERVICE.equals(service.getUuid())) {
				 this.characteristics.put(JaaleeUuid.AUTH_SEED_CHAR, service.getCharacteristic(JaaleeUuid.AUTH_SEED_CHAR));
				 this.characteristics.put(JaaleeUuid.AUTH_VECTOR_CHAR, service.getCharacteristic(JaaleeUuid.AUTH_VECTOR_CHAR));
			 }
	 }
 
	 public void update(BluetoothGattCharacteristic characteristic)
	 {
		 this.characteristics.put(characteristic.getUuid(), characteristic);
	 }
 
	 public boolean isAvailable()
	 {
		 return this.characteristics.size() == 2;
	 }
 
	 public boolean isAuthSeedCharacteristic(BluetoothGattCharacteristic characteristic) {
		 return characteristic.getUuid().equals(JaaleeUuid.AUTH_SEED_CHAR);
	 }
 
	 public boolean isAuthVectorCharacteristic(BluetoothGattCharacteristic characteristic) {
		 return characteristic.getUuid().equals(JaaleeUuid.AUTH_VECTOR_CHAR);
	 }
 
	 public BluetoothGattCharacteristic getAuthSeedCharacteristic() {
		 return (BluetoothGattCharacteristic)this.characteristics.get(JaaleeUuid.AUTH_SEED_CHAR);
	 }
 
	 public BluetoothGattCharacteristic getAuthVectorCharacteristic() {
		 return (BluetoothGattCharacteristic)this.characteristics.get(JaaleeUuid.AUTH_VECTOR_CHAR);
	 }
 }
