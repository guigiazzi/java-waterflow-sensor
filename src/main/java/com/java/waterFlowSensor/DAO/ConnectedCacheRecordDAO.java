package com.java.waterFlowSensor.DAO;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.waterFlowSensor.DTO.ConnectedCacheRecordDTO;
import com.java.waterFlowSensor.DTO.DataPointDTO;

@Repository
public interface ConnectedCacheRecordDAO extends MongoRepository<ConnectedCacheRecordDTO, String>{
	
	boolean existsByUsernameAndDeviceId(String username, String deviceId);
		
	List<ConnectedCacheRecordDTO> findAllByUsernameAndDeviceId(String username, String deviceId);
	
	boolean existsByUsernameAndDeviceIdAndDataPoints(String username, String deviceId, List<DataPointDTO> dataPoints);
			
	ConnectedCacheRecordDTO findByUsernameAndDeviceIdAndDataPoints(String username, String deviceId, List<DataPointDTO> dataPoints);

}
