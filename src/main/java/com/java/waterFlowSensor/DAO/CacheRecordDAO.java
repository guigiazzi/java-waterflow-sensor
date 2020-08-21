package com.java.waterFlowSensor.DAO;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.waterFlowSensor.DTO.CacheRecordDTO;

@Repository
public interface CacheRecordDAO extends MongoRepository<CacheRecordDTO, String>{
			
}
