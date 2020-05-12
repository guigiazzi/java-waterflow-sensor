package com.java.waterFlowSensor.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import com.java.waterFlowSensor.DAO.UserDAO;
import com.java.waterFlowSensor.DTO.DataPointDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.util.TimestampUtil;

public class LiveChartService {
	
	@Autowired
	private UserDAO userDAO;

//	@Autowired
//	private TimestampUtil timestampUtil;
	
	public List<DataPointDTO> createChart(String username, MongoTemplate mongoTemplate) {
		List<DataPointDTO> dataPoints = new ArrayList<DataPointDTO>();

		Aggregation agg = Aggregation.newAggregation( // group by description, average all flow rates
				match(Criteria.where("username").is(username)),
				group("timestamp").sum("flowRate").as("flowRate"));
		AggregationResults<DeviceDTO> results = mongoTemplate.aggregate(agg, "DeviceCollection", DeviceDTO.class);
		List<DeviceDTO> unmodifiableList = results.getMappedResults();
		List<DeviceDTO> timestampAndFlowRateSumList = new ArrayList<DeviceDTO>(unmodifiableList);

		Collections.sort(timestampAndFlowRateSumList, TimestampUtil.timestampComparator);
		
		for (DeviceDTO timestampAndFlowRateSum : timestampAndFlowRateSumList) {
			String timestamp = timestampAndFlowRateSum.get_id();
			double flowRateSum = timestampAndFlowRateSum.getFlowRate();

			DataPointDTO dataPoint = new DataPointDTO();
			dataPoint.setY(flowRateSum);
			dataPoint.setLabel(timestamp);
			dataPoints.add(dataPoint);
		}

		return dataPoints;
	}

}
