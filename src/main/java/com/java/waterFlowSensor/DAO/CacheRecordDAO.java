package com.java.waterFlowSensor.DAO;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.waterFlowSensor.DTO.CacheRecordDTO;
import com.java.waterFlowSensor.DTO.DataPointDTO;

@Repository
public interface CacheRecordDAO extends MongoRepository<CacheRecordDTO, String>{
		
	boolean existsByUsernameAndDataPoints(String username, List<DataPointDTO> dataPoints);
			
}
