package com.java.waterFlowSensor.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import com.java.waterFlowSensor.DTO.ChartViewDTO;
import com.java.waterFlowSensor.DTO.DataPointDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.util.WeekDaysUtil;

public class ColumnChartService {
	
	private WeekDaysUtil weekDaysUtil = new WeekDaysUtil();

	public ChartViewDTO createChart(String type, String title, String username, MongoTemplate mongoTemplate) {
		List<DataPointDTO> dataPoints = new ArrayList<DataPointDTO>();

		Aggregation agg = Aggregation.newAggregation( // group by weekday, average all flow rates
				match(Criteria.where("username").is(username)), 
				group("weekDay").avg("flowRate").as("flowRate"));
		AggregationResults<DeviceDTO> results = mongoTemplate.aggregate(agg, "DeviceCollection", DeviceDTO.class);
		List<DeviceDTO> queryList = results.getMappedResults();
		
		List<String> queryDaysFormatList = new ArrayList<String>();
		for (DeviceDTO queryDay : queryList) {
			queryDaysFormatList.add(queryDay.get_id());
		}
		
		List<DeviceDTO> weekDayAndFlowRateAvgList = results.getMappedResults();
		weekDayAndFlowRateAvgList = weekDaysUtil.fillMissingDays(queryDaysFormatList, queryList);
		
		dataPoints = weekDaysUtil.setDataPoints(weekDayAndFlowRateAvgList);

		dataPoints = weekDaysUtil.sortDataPoints(dataPoints);
		
		dataPoints = weekDaysUtil.formatWeekDays(dataPoints);
		
		return new ChartViewDTO(title, type, dataPoints);
	}

}
