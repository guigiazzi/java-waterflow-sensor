package com.java.waterFlowSensor.DAO;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.waterFlowSensor.DTO.FixedChartViewCardDTO;

@Repository
public interface FixedChartViewCardDAO extends MongoRepository<FixedChartViewCardDTO, String>{
	
	FixedChartViewCardDTO findByChartId(String chartId);
}
