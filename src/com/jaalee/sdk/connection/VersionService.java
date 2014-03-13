package com.jaalee.sdk.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

public class VersionService
  implements BluetoothService
{
	private final HashMap<UUID, BluetoothGattCharacteristic> characteristics = new HashMap();

	public void processGattServices(List<BluetoothGattService> services)
	{
		for (BluetoothGattService service : services)
			if (JaaleeUuid.VERSION_SERVICE.equals(service.getUuid())) {
				this.characteristics.put(JaaleeUuid.HARDWARE_VERSION_CHAR, service.getCharacteristic(JaaleeUuid.HARDWARE_VERSION_CHAR));

				this.characteristics.put(JaaleeUuid.SOFTWARE_VERSION_CHAR, service.getCharacteristic(JaaleeUuid.SOFTWARE_VERSION_CHAR));
			}
	}

	public String getSoftwareVersion()
	{
		return this.characteristics.containsKey(JaaleeUuid.SOFTWARE_VERSION_CHAR) ? getStringValue(((BluetoothGattCharacteristic)this.characteristics.get(JaaleeUuid.SOFTWARE_VERSION_CHAR)).getValue()) : null;
	}

	public String getHardwareVersion()
	{
		return this.characteristics.containsKey(JaaleeUuid.HARDWARE_VERSION_CHAR) ? getStringValue(((BluetoothGattCharacteristic)this.characteristics.get(JaaleeUuid.HARDWARE_VERSION_CHAR)).getValue()) : null;
	}

	public void update(BluetoothGattCharacteristic characteristic)
	{
		this.characteristics.put(characteristic.getUuid(), characteristic);
	}

	public Collection<BluetoothGattCharacteristic> getAvailableCharacteristics() {
		List chars = new ArrayList(this.characteristics.values());
		chars.removeAll(Collections.singleton(null));
		return chars;
	}

	private static String getStringValue(byte[] bytes) {
		int indexOfFirstZeroByte = 0;
		while (bytes[indexOfFirstZeroByte] != 0) {
			indexOfFirstZeroByte++;
		}

		byte[] strBytes = new byte[indexOfFirstZeroByte];
		for (int i = 0; i != indexOfFirstZeroByte; i++) {
			strBytes[i] = bytes[i];
		}
		
		return new String(strBytes);
	}
}
