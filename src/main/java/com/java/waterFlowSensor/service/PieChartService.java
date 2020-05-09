package com.java.waterFlowSensor.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import com.java.waterFlowSensor.DTO.DataPointDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;

public class PieChartService {

	public List<DataPointDTO> createChart(String username, MongoTemplate mongoTemplate) {
		List<DataPointDTO> dataPoints = new ArrayList<DataPointDTO>();

		Aggregation agg = Aggregation.newAggregation( // group by description, average all flow rates
				match(Criteria.where("username").is(username)),
				group("description").sum("flowRate").as("flowRate"),
				sort(Sort.Direction.ASC, "flowRate"));
		AggregationResults<DeviceDTO> results = mongoTemplate.aggregate(agg, "DeviceCollection", DeviceDTO.class);
		List<DeviceDTO> descriptionAndFlowRateSumList = results.getMappedResults();

		for (DeviceDTO descriptionAndFlowRateSum : descriptionAndFlowRateSumList) {
			String description = descriptionAndFlowRateSum.get_id();
			double flowRateSum = descriptionAndFlowRateSum.getFlowRate();

			DataPointDTO dataPoint = new DataPointDTO();
			dataPoint.setY(flowRateSum);
			dataPoint.setLegendText(description);
			dataPoint.setName(description);
			dataPoints.add(dataPoint);
		}

		return dataPoints;
	}

}
