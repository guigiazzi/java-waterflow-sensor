package com.java.waterFlowSensor.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.java.waterFlowSensor.util.WeekDaysUtil;

public class LineChartService {
	
	private WeekDaysUtil weekDaysUtil = new WeekDaysUtil();

	public ChartViewDTO createChart(String type, String title, String username, MongoTemplate mongoTemplate) {

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
		System.out.println("Start: " + formatStart + ", end: " + formatEnd);
		Aggregation agg = Aggregation.newAggregation( // group by weekDay, sum all flow rates
				match(Criteria.where("username").is(username)),
				match(Criteria.where("timestamp").gte(formatStart)), // gets range of past week
				match(Criteria.where("timestamp").lte(formatEnd)),
				group("weekDay").sum("flowRate").as("flowRate"));
		AggregationResults<DeviceDTO> results = mongoTemplate.aggregate(agg, "DeviceCollection", DeviceDTO.class);
		List<DeviceDTO> queryList = results.getMappedResults();

		List<String> queryDaysFormatList = new ArrayList<String>();
		for (DeviceDTO queryDay : queryList) {
			queryDaysFormatList.add(queryDay.get_id());
		}
		
		List<DeviceDTO> weekDayAndFlowRateSumList = new ArrayList<DeviceDTO>();		

		weekDayAndFlowRateSumList = weekDaysUtil.fillMissingDays(queryDaysFormatList, queryList);
		
		List<DataPointDTO> dataPoints = new ArrayList<DataPointDTO>();
		
		dataPoints = weekDaysUtil.setDataPoints(weekDayAndFlowRateSumList);

		dataPoints = weekDaysUtil.sortDataPoints(dataPoints);

		return new ChartViewDTO(title, type, dataPoints);
	}

}
