package com.java.waterFlowSensor.DAO;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.waterFlowSensor.DTO.ViewCardDTO;

@Repository
public interface ViewCardDAO extends MongoRepository<ViewCardDTO, String>{
	
	boolean existsByUsername(String username);
	
	List<ViewCardDTO> findAllByUsername(String username);
		
}
