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

import com.java.waterFlowSensor.DAO.CacheLikeDAO;
import com.java.waterFlowSensor.DAO.DeviceDAO;
import com.java.waterFlowSensor.DAO.FixedChartViewCardDAO;
import com.java.waterFlowSensor.DAO.UserDAO;
import com.java.waterFlowSensor.DTO.ChartViewDTO;
import com.java.waterFlowSensor.DTO.DataPointDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.DTO.FixedChartViewCardDTO;
import com.java.waterFlowSensor.DTO.UserDTO;
import com.java.waterFlowSensor.util.CacheRecordUtil;

import lombok.extern.java.Log;

@Log
@Service
public class HomeService {

	@Autowired
	private FixedChartViewCardDAO fixedChartViewCardDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private DeviceDAO deviceDAO;
	
	@Autowired
	private CacheLikeDAO cacheLikeDAO;

	@Autowired
	private MongoTemplate mongoTemplate;

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
			match(Criteria.where("username").is(username)),
//			group("title", "description", "deviceId", "username", "timestamp", "flowRate").sum("flowRate").as("flowRate"),
			group("deviceId").sum("flowRate").as("flowRate"),
			sort(Sort.Direction.ASC, "flowRate"));
		AggregationResults<DeviceDTO> results = mongoTemplate.aggregate(agg, "DeviceCollection", DeviceDTO.class);
		List<DeviceDTO> deviceIdAndFlowRateSumList = results.getMappedResults();

		// needs to be done because the group by function returns only deviceId and flowRate data
		List<DeviceDTO> completeDeviceIdList = new ArrayList<>();
		for(DeviceDTO d : deviceIdAndFlowRateSumList) {
			DeviceDTO completeDevice = deviceDAO.findAllByDeviceId(d.get_id()).get(0);
			completeDevice.setFlowRate(d.getFlowRate());
			completeDeviceIdList.add(completeDevice);	
		}
		
		return completeDeviceIdList;
	}

	public ChartViewDTO getChartView(String chartId, String deviceId, String username) {
		log.info("Buscando detalhes do gráfico");
		List<DataPointDTO> dataPoints = new ArrayList<DataPointDTO>();
		
		if(chartId.equals("1")) {
			PieChartService pieChart = new PieChartService();
			dataPoints = pieChart.createChart(username, mongoTemplate);
		} else if(chartId.equals("2")) {
			SplineChartService splineChart = new SplineChartService();
			dataPoints = splineChart.createChart(username, mongoTemplate);
		} else if(chartId.equals("3")) {
			ColumnChartService columnChart = new ColumnChartService();
			dataPoints = columnChart.createChart(username, mongoTemplate);
		} else if(chartId.equals("4")) {
			LiveChartService liveChart = new LiveChartService();
			dataPoints = liveChart.createChart(username, deviceId, mongoTemplate);
//			CacheRecordUtil cacheRecords = new CacheRecordUtil(username, dataPoints);
//			this.cacheLikeDAO.insert(cacheRecords);			
		}
		
		
		FixedChartViewCardDTO fixedChartView = fixedChartViewCardDAO.findByChartId(chartId);
		
		ChartViewDTO chartViewDTO = new ChartViewDTO(chartId, fixedChartView.getTitle(), fixedChartView.getType(), dataPoints);
		
//		ChartViewDTO chartViewDTO = new ChartViewDTO();
//		chartViewDTO.setChartId(chartId);
//		chartViewDTO.setTitle(fixedChartView.getTitle());
//		chartViewDTO.setType(fixedChartView.getType());
//		chartViewDTO.setDataPoints(dataPoints);
//
//		if(chartId.equals("4")) {
//			chartViewDTO.setConnectedDevice(false);
//		}
		
		return chartViewDTO;
	}
}
