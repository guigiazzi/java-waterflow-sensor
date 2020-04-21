package com.java.waterFlowSensor.DAO;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.waterFlowSensor.DTO.ChartViewDTO;

@Repository
public interface ChartViewDAO extends MongoRepository<ChartViewDTO, String>{
		
	ChartViewDTO findByTypeAndUsername(String chartId, String username);
	
	List<ChartViewDTO> findAllByUsername(String username);
		
}
