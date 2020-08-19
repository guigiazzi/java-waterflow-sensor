package com.java.waterFlowSensor.DAO;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.util.CacheRecordUtil;

@Repository
public interface CacheLikeDAO extends MongoRepository<DeviceDTO, String>{

	void insert(CacheRecordUtil cacheRecords);
			
}
