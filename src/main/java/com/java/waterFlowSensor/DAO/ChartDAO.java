package com.java.waterFlowSensor.DAO;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.waterFlowSensor.DTO.ChartDTO;

@Repository
public interface ChartDAO extends MongoRepository<ChartDTO, String>{
	
	boolean existsByChartIdAndUsername(String chartId, String username);
	
	ChartDTO findByChartIdAndUsername(String chartId, String username);
		
}
