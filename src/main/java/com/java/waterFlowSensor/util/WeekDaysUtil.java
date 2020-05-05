package com.java.waterFlowSensor.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.java.waterFlowSensor.DTO.DataPointDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.enums.WeekDayEnum;

public class WeekDaysUtil {
	
	private List<String> weekDaysFormatList = new ArrayList<String>();
	
	public WeekDaysUtil() {
		List<WeekDayEnum> weekDays = Arrays.asList(WeekDayEnum.values());
		for (WeekDayEnum weekDayEnum : weekDays) {
			this.weekDaysFormatList.add(weekDayEnum.name());
		}
	}

	// formats the list to respect the order of the week days
	public List<DataPointDTO> sortDataPoints(List<DataPointDTO> dataPoints) {
		List<DataPointDTO> sortedDataPoints = Arrays.asList(null, null, null, null, null, null, null);
		for (DataPointDTO dataPoint : dataPoints) {
			int idx = this.weekDaysFormatList.indexOf(dataPoint.getLabel());
			sortedDataPoints.set(idx, dataPoint);
		}

		return sortedDataPoints;
	}
	
	// formats weekday names, replacing Enum type for its value
	public List<DataPointDTO> formatWeekDays(List<DataPointDTO> dataPoints) {
		int a = 0;
		for(WeekDayEnum weekDayEnum : WeekDayEnum.values()) {
			DataPointDTO dataPoint = dataPoints.get(a);
			dataPoint.setLabel(weekDayEnum.weekDay);
			dataPoints.set(a, dataPoint);
			a++;
		}

		return dataPoints;
	}

	// sets data point values
	public List<DataPointDTO> setDataPoints(List<DeviceDTO> weekDayAndFlowRateList) {
		List<DataPointDTO> dataPoints = new ArrayList<DataPointDTO>();
		
		for (DeviceDTO weekDayAndFlowRate : weekDayAndFlowRateList) {
			String weekDay = weekDayAndFlowRate.get_id();
			double flowRateAvg = weekDayAndFlowRate.getFlowRate();
	
			DataPointDTO dataPoint = new DataPointDTO();
			dataPoint.setY(flowRateAvg);
			dataPoint.setLabel(weekDay);
			dataPoints.add(dataPoint);
		}
	
		return dataPoints;
	}
	
	// finds missing days on query result, then adds a 0 flow rate to them
	public List<DeviceDTO> fillMissingDays(List<String> queryFormatList, List<DeviceDTO> queryList) {
		List<DeviceDTO> weekDayAndFlowRateList = new ArrayList<DeviceDTO>();		
		int b = 0;
		
		for (int a = 0; a < this.weekDaysFormatList.size(); a++) { 
			DeviceDTO weekDayAndFlowRate = new DeviceDTO();
			if (queryFormatList.contains(weekDaysFormatList.get(a))) {
				weekDayAndFlowRate.set_id(queryList.get(b).get_id());
				weekDayAndFlowRate.setFlowRate(queryList.get(b).getFlowRate());
				b++;
			} else {
				weekDayAndFlowRate.set_id(weekDaysFormatList.get(a));
				weekDayAndFlowRate.setFlowRate(0);
			}
			weekDayAndFlowRateList.add(weekDayAndFlowRate);
		}
		
		return weekDayAndFlowRateList;
	}

}
