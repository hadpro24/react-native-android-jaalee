package com.jaalee.sdk.utils;

import com.jaalee.sdk.Beacon;
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
public class JaaleeBeacons
{
	public static final String ESTIMOTE_PROXIMITY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
	public static final String ESTIMOTE_MAC_PROXIMITY_UUID = "08D4A950-80F0-4D42-A14B-D53E063516E6";
	public static final String ESTIMOTE_IOS_PROXIMITY_UUID = "8492E75F-4FD6-469D-B132-043FE94921D8";

	public static boolean isEstIOSBeacon(Beacon beacon)
	{
		return "8492E75F-4FD6-469D-B132-043FE94921D8".equalsIgnoreCase(beacon.getProximityUUID());
  }

	public static boolean isEstMacBeacon(Beacon beacon)
	{
		return "08D4A950-80F0-4D42-A14B-D53E063516E6".equalsIgnoreCase(beacon.getProximityUUID());
	}

	public static boolean isOriginalEstimoteUuid(Beacon beacon) {
		return "B9407F30-F5F8-466E-AFF9-25556B57FE6D".equalsIgnoreCase(beacon.getProximityUUID());
	}

	public static boolean isEstValidName(String name)
	{
		return ("estimote".equalsIgnoreCase(name)) || ("est".equalsIgnoreCase(name));
	}
	
	public static boolean isJaaleeBeacon(Beacon beacon)
	{
		return ("jaalee".equalsIgnoreCase(beacon.getName()) || "WWW.JAALEE.COM".equalsIgnoreCase(beacon.getName()) 
				|| "EBEFD083-70A2-47C8-9837-E7B5634DF524".equalsIgnoreCase(beacon.getProximityUUID()));
	}

	public static boolean isEstimoteBeacon(Beacon beacon)
	{
//		detcet all of the Beacon
		return true;
		
//    	only detect the Beacon of Jaalee or Estimote
//		return (isJaaleeBeacon(beacon) || isEstMacBeacon(beacon)) || (isEstIOSBeacon(beacon)) || (isOriginalEstimoteUuid(beacon)) || (isEstValidName(beacon.getName()));
	}
}
