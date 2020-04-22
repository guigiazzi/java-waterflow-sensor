package com.java.waterFlowSensor.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import com.java.waterFlowSensor.DTO.ChartViewDTO;
import com.java.waterFlowSensor.DTO.DataPointDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;

public class LineChartService {

	@Autowired
	MongoTemplate mongoTemplate;

	public ChartViewDTO createChart(String type, String title, String username, MongoTemplate mongoTemplate) {
		List<DataPointDTO> dataPoints = new ArrayList<DataPointDTO>();
		
		Date date = new Date();
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
	    c.add(Calendar.DATE, -i - 7);
	    Date start = c.getTime();
	    c.add(Calendar.DATE, 6);
	    Date end = c.getTime();
	    System.out.println(start + " - " + end);

		Aggregation agg = Aggregation.newAggregation( // group by description, sum all flow rates
				match(Criteria.where("username").is(username)), 
				match(Criteria.where("timestamp").gte(start)), // gets range of past week 
				match(Criteria.where("timestamp").lte(end)), 
				group("description").sum("flowRate").as("flowRate"));
		AggregationResults<DeviceDTO> results = mongoTemplate.aggregate(agg, "DeviceCollection", DeviceDTO.class);
		List<DeviceDTO> descriptionAndFlowRateSumList = results.getMappedResults();

		for (DeviceDTO descriptionAndFlowRateSum : descriptionAndFlowRateSumList) {
			String description = descriptionAndFlowRateSum.get_id();
			double flowRateSum = descriptionAndFlowRateSum.getFlowRate();

			DataPointDTO dataPoint = new DataPointDTO();
			dataPoint.setY(flowRateSum);
			dataPoint.setLabel(description);
			dataPoints.add(dataPoint);
		}

		return new ChartViewDTO(title, type, dataPoints);
	}

}
