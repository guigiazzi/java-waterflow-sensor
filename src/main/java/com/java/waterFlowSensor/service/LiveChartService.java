package com.java.waterFlowSensor.service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import com.java.waterFlowSensor.DAO.UserDAO;
import com.java.waterFlowSensor.DTO.DataPointDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;

public class LiveChartService {
	
	@Autowired
	private UserDAO userDAO;

	public List<DataPointDTO> createChart(String username, MongoTemplate mongoTemplate) {
		List<DataPointDTO> dataPoints = new ArrayList<DataPointDTO>();
		
//		userDAO.findByUsername(username); //findAllByUsername?
//		
//		for() {
//			
//		}

		Aggregation agg = Aggregation.newAggregation( // group by description, average all flow rates
				match(Criteria.where("username").is(username)),
				group("timestamp").sum("flowRate").as("flowRate"));
//				sort(Sort.Direction.ASC, "flowRate"));
		AggregationResults<DeviceDTO> results = mongoTemplate.aggregate(agg, "DeviceCollection", DeviceDTO.class);
		List<DeviceDTO> timestampAndFlowRateSumList = results.getMappedResults();

		for (DeviceDTO timestampAndFlowRateSum : timestampAndFlowRateSumList) {
			String timestamp = timestampAndFlowRateSum.get_id();
			double flowRateSum = timestampAndFlowRateSum.getFlowRate();

			DataPointDTO dataPoint = new DataPointDTO();
			dataPoint.setX(timestamp);
			dataPoint.setY(flowRateSum);
//			dataPoint.setLegendText(description);
//			dataPoint.setName(description);
			dataPoints.add(dataPoint);
		}

		return dataPoints;
	}

}
