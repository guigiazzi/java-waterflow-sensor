package com.java.waterFlowSensor.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.java.waterFlowSensor.DAO.ChartViewDAO;
import com.java.waterFlowSensor.DAO.DeviceDAO;
import com.java.waterFlowSensor.DAO.FixedChartViewCardDAO;
import com.java.waterFlowSensor.DTO.ChartViewDTO;
import com.java.waterFlowSensor.DTO.DataPointDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.DTO.FixedChartViewCardDTO;

import lombok.extern.java.Log;

@Log
@Service
public class HomeService {

	@Autowired
	ChartViewDAO chartViewDAO;

	@Autowired
	FixedChartViewCardDAO fixedChartViewCardDAO;

	@Autowired
	DeviceDAO deviceDAO;

	@Autowired
	MongoTemplate mongoTemplate;

	public List<FixedChartViewCardDTO> getFixedChartViewCards() {
		log.info("Buscando cards para gráficos das visões");

		return fixedChartViewCardDAO.findAll();
	}

	public List<String> getDeviceCards(String username) {
		log.info("Buscando cards dos dispositivos para o usuário " + username);

		Criteria criteria = new Criteria("username").is(username);
		Query query = new Query();
		query.addCriteria(criteria);
		return mongoTemplate.findDistinct(query, "description", DeviceDTO.class, String.class);
	}

	public DeviceDTO getDeviceDetails(String username, String description) {
		log.info("Buscando detalhes do dispositivo. Username: " + username + ". Description: " + description);

		List<DeviceDTO> devices = deviceDAO.findAllByUsernameAndDescription(username, description);

		double flowRateSum = 0;
		for (DeviceDTO device : devices) {
			flowRateSum += device.getFlowRate();
		}

		DeviceDTO device = devices.get(0); // could choose any one, since we only want description and deviceId values
		device.setFlowRate(flowRateSum);
		return device;

//		Aggregation agg = Aggregation.newAggregation( // group by description, sum all flow rates
//		        match(Criteria.where("description").is(description)),
//		        group("description").sum("flowRate").as("flowRate"),
//		        project("flowRate")
//		    );
//
//		AggregationResults<String> results = mongoTemplate.aggregate(agg, "DeviceCollection", String.class);
//		String flowRateSum = results.getMappedResults().get(0);
//		System.out.println("flowRateSum: " + flowRateSum);
//		List<DeviceDTO> devices = deviceDAO.findAllByDescription(description);
//		DeviceDTO device = devices.get(0); // could choose any one
//		device.setFlowRate(flowRateSum);
//		return device;
//		return deviceDAO.findByDescription(description);
	}

	public ChartViewDTO getChartView(String type, String title, String username) {
		log.info("Buscando detalhes do gráfico");
		
		List<DataPointDTO> dataPoints = new ArrayList<DataPointDTO>();
		
		if(type.equals("column")) { //
			Aggregation agg = Aggregation.newAggregation( // group by description, sum all flow rates
		        match(Criteria.where("username").is(username)),
		        group("description").sum("flowRate").as("flowRate")
		    );
			AggregationResults<DeviceDTO> results = mongoTemplate.aggregate(agg, "DeviceCollection", DeviceDTO.class);
			List<DeviceDTO> descriptionAndFlowRateSumList = results.getMappedResults();
			
			for(DeviceDTO descriptionAndFlowRateSum : descriptionAndFlowRateSumList) {
				String description = descriptionAndFlowRateSum.get_id();
				double flowRateSum = descriptionAndFlowRateSum.getFlowRate();
						
				DataPointDTO dataPoint = new DataPointDTO();
				dataPoint.setY(flowRateSum);
				dataPoint.setLabel(description);
				dataPoints.add(dataPoint);	
			}			

		}
		
		return new ChartViewDTO(username, title, type, dataPoints);
		
	}
}
