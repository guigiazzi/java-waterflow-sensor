package com.java.waterFlowSensor.service;

import java.util.List;

import javax.print.Doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.java.waterFlowSensor.DAO.DeviceDAO;
import com.java.waterFlowSensor.DAO.FixedChartViewCardDAO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.DTO.FixedChartViewCardDTO;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import lombok.extern.java.Log;

@Log
@Service
public class HomeService {

//	@Autowired
//	ChartViewDAO chartViewDAO;

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

//	public ChartViewDTO getChartView(String chartId, String username) {
//	public ChartViewDTO getChartView(String username) {
//		log.info("Buscando detalhes do gráfico");
//		
//		return chartViewDAO.findByChartIdAndUsername(chartId, username);
//	}
}
