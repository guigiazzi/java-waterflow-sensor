package com.java.waterFlowSensor.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.java.waterFlowSensor.DAO.DeviceDAO;
import com.java.waterFlowSensor.DAO.FixedChartViewCardDAO;
import com.java.waterFlowSensor.DAO.UserDAO;
import com.java.waterFlowSensor.DTO.ChartViewDTO;
import com.java.waterFlowSensor.DTO.DataPointDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.DTO.FixedChartViewCardDTO;
import com.java.waterFlowSensor.DTO.UserDTO;

import lombok.extern.java.Log;

@Log
@Service
public class HomeService {

//	@Autowired
//	ChartViewDAO chartViewDAO;

	@Autowired
	private FixedChartViewCardDAO fixedChartViewCardDAO;

	@Autowired
	private DeviceDAO deviceDAO;
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ChartViewDTO chartView;
	
	public UserDTO getUserData(String username) {
		log.info("Buscando dados do usuário " + username);
		
		return userDAO.findByUsername(username);
	}

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

	public List<DeviceDTO> getDeviceDetails(String username) {
		log.info("Buscando detalhes dos dispositivos. Username: " + username);
		Aggregation agg = Aggregation.newAggregation( // group by deviceId, sum all flow rates
//			project("deviceId", "username", "flowRate", "description"),
			match(Criteria.where("username").is(username)),
			group("title", "description", "deviceId", "username", "timestamp", "flowRate").sum("flowRate").as("flowRate"),
//			group("deviceId").sum("flowRate").as("flowRate"),
			sort(Sort.Direction.ASC, "flowRate"));
		AggregationResults<DeviceDTO> results = mongoTemplate.aggregate(agg, "DeviceCollection", DeviceDTO.class);
		List<DeviceDTO> deviceIdAndFlowRateSumList = results.getMappedResults();

		return deviceIdAndFlowRateSumList;

//		List<DeviceDTO> devices = deviceDAO.findAllByUsername(username);
//		return devices;

//		double flowRateSum = 0;
//		for (DeviceDTO device : devices) {
//			flowRateSum += device.getFlowRate();
//		}
//
//		DeviceDTO device = devices.get(0); // could choose any one, since we only want description and deviceId values
//		device.setFlowRate(flowRateSum);
//		return device;

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

	public ChartViewDTO getChartView(String chartId, String username) {
		log.info("Buscando detalhes do gráfico");
		List<DataPointDTO> dataPoints = new ArrayList<DataPointDTO>();
		
		if(chartId.equals("3")) {
			ColumnChartService columnChart = new ColumnChartService();
			dataPoints = columnChart.createChart(username, mongoTemplate);		
		}
//		} else if(chartId.equals("2")) {
//			LineChartService lineChart = new LineChartService();
//			dataPoints = lineChart.createChart(username, mongoTemplate);
//		} else if(chartId.equals("1")) {
//			PieChartService pieChart = new PieChartService();
//			dataPoints = pieChart.createChart(username, mongoTemplate);
//		}
		
		FixedChartViewCardDTO fixedChartView = fixedChartViewCardDAO.findByChartId(chartId);
		chartView.setTitle(fixedChartView.getTitle());
		chartView.setType(fixedChartView.getType());
		chartView.setDataPoints(dataPoints);
		return chartView;
	}
}
