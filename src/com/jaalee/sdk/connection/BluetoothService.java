package com.jaalee.sdk.connection;

import android.bluetooth.BluetoothGattCharacteristic;
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
public abstract interface BluetoothService
{
  public abstract void update(BluetoothGattCharacteristic paramBluetoothGattCharacteristic);
}
