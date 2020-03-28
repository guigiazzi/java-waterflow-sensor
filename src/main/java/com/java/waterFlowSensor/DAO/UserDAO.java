package com.java.waterFlowSensor.DAO;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.waterFlowSensor.DTO.UserDTO;

@Repository
public interface UserDAO extends MongoRepository<UserDTO, String>{
	
	boolean existsByUsername(String username);
	
	boolean existsByUsernameAndPassword(String username, String password);
	
}
