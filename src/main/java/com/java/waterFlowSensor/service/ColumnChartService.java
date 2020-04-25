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

public class ColumnChartService {

	public ChartViewDTO createChart(String type, String title, String username, MongoTemplate mongoTemplate) {
		List<DataPointDTO> dataPoints = new ArrayList<DataPointDTO>();

		Aggregation agg = Aggregation.newAggregation( // group by weekday, average all flow rates
				match(Criteria.where("username").is(username)), 
				group("weekDay").avg("flowRate").as("flowRate"));
		AggregationResults<DeviceDTO> results = mongoTemplate.aggregate(agg, "DeviceCollection", DeviceDTO.class);
		List<DeviceDTO> weekDayAndFlowRateAvgList = results.getMappedResults();

		for (DeviceDTO weekDayAndFlowRateAvg : weekDayAndFlowRateAvgList) {
			String weekDay = weekDayAndFlowRateAvg.get_id();
			double flowRateAvg = weekDayAndFlowRateAvg.getFlowRate();

			DataPointDTO dataPoint = new DataPointDTO();
			dataPoint.setY(flowRateAvg);
			dataPoint.setLabel(weekDay);
			dataPoints.add(dataPoint);
		}

		return new ChartViewDTO(title, type, dataPoints);
	}

}