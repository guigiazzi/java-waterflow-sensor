package com.java.waterFlowSensor.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import com.java.waterFlowSensor.DTO.ChartViewDTO;
import com.java.waterFlowSensor.DTO.DataPointDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.enums.WeekDayEnum;

public class LineChartService {

	public ChartViewDTO createChart(String type, String title, String username, MongoTemplate mongoTemplate) {
		List<DataPointDTO> dataPoints = new ArrayList<DataPointDTO>();
		
		// gets past week values
		Date date = new Date();
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
	    c.add(Calendar.DATE, -i - 7);
	    Date start = c.getTime();
	    c.add(Calendar.DATE, 6);
	    Date end = c.getTime();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String formatStart = dateFormat.format(start);
		String formatEnd = dateFormat.format(end);

		Aggregation agg = Aggregation.newAggregation( // group by weekDay, sum all flow rates
				match(Criteria.where("username").is(username)), 
				match(Criteria.where("timestamp").gte(formatStart)), // gets range of past week 
				match(Criteria.where("timestamp").lte(formatEnd)), 
				group("weekDay").sum("flowRate").as("flowRate"));
		AggregationResults<DeviceDTO> results = mongoTemplate.aggregate(agg, "DeviceCollection", DeviceDTO.class);
		List<DeviceDTO> queryList = results.getMappedResults();
		List<DeviceDTO> weekDayAndFlowRateSumList = new ArrayList<DeviceDTO>();
		List<WeekDayEnum> weekDays = Arrays.asList(WeekDayEnum.values());		
		
		List<String> weekDaysFormat = new ArrayList<String>();
		for(WeekDayEnum weekDay : weekDays) {
			weekDaysFormat.add(weekDay.name());
		}
		System.out.println(weekDaysFormat);

//		for(int a = 0; a < queryList.size(); a++) {
//			
//		for() {
//			queryListFormat.add
//		}
		
		int a = 0;
		for(WeekDayEnum weekDayEnum : weekDays) { // finds missing days on query result, then adds a 0 flow rate to them
			String weekDay = weekDayEnum.name();
			DeviceDTO singleResult = queryList.get(a);
			DeviceDTO weekDayAndFlowRateSum = new DeviceDTO();
			if(queryList.contains(weekDay)) {
				weekDayAndFlowRateSum.set_id(singleResult.get_id());
				weekDayAndFlowRateSum.setFlowRate(singleResult.getFlowRate());
			} else {
				weekDayAndFlowRateSum.set_id(weekDaysFormat.get(a));
				weekDayAndFlowRateSum.setFlowRate(0);
			}
			weekDayAndFlowRateSumList.add(weekDayAndFlowRateSum);
			a++;
		
//			for(int a = 0; a < queryList.size(); a++) {
//				String queryWeekDay = queryList.get(a).get_id();
//				DeviceDTO weekDayAndFlowRateSum = new DeviceDTO();
//				if(queryWeekDay.equals(weekDay)) {
//					weekDayAndFlowRateSum.set_id(queryList.get(a).get_id());
//					weekDayAndFlowRateSum.setFlowRate(queryList.get(a).getFlowRate());
//				} else {
//					weekDayAndFlowRateSum.set_id(weekDay);
//					weekDayAndFlowRateSum.setFlowRate(0);
//				}
//				weekDayAndFlowRateSumList.add(weekDayAndFlowRateSum);
//			}
		}
		
		for (DeviceDTO weekDayAndFlowRateSum : weekDayAndFlowRateSumList) {
			String weekDay = weekDayAndFlowRateSum.get_id();
			double flowRateSum = weekDayAndFlowRateSum.getFlowRate();

			DataPointDTO dataPoint = new DataPointDTO();
			dataPoint.setY(flowRateSum);
			dataPoint.setLabel(weekDay);
			dataPoints.add(dataPoint);
		}

		return new ChartViewDTO(title, type, dataPoints);
	}

}
