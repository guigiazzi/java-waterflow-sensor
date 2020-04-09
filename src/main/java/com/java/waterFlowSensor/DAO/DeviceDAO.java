package com.java.waterFlowSensor.DAO;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.waterFlowSensor.DTO.DeviceDTO;

@Repository
public interface DeviceDAO extends MongoRepository<DeviceDTO, String>{
	
	boolean existsByUsername(String username);
	
	List<DeviceDTO> findAllByUsername(String username);
		
}
