package com.java.waterFlowSensor.util;

import java.util.Comparator;

import com.java.waterFlowSensor.DTO.DeviceDTO;

public class TimestampUtil {
	
	// sorts array by timestamp
    public static Comparator<DeviceDTO> timestampComparator = new Comparator<DeviceDTO>() {

		public int compare(DeviceDTO a, DeviceDTO b) {
		   String device1 = a.get_id();
		   String device2 = b.get_id();
	
		   return device1.compareTo(device2);
	    }
	};
}
