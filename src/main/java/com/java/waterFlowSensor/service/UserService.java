package com.java.waterFlowSensor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.waterFlowSensor.DAO.DeviceDAO;
import com.java.waterFlowSensor.DAO.UserDAO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.DTO.UserDTO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private DeviceDAO deviceDao;
	
	public UserDTO retrieveProfileData(String username) {
		if(!userDao.existsByUsername(username)) {
			throw new IllegalArgumentException("Usuário não encontrado");
		}
		
		return userDao.findByUsername(username);
	}
	
	public void updateProfile(UserDTO user, String currentUsername) {
		if(userDao.existsByUsername(user.getUsername()) && !user.getUsername().equals(currentUsername)) {
			throw new IllegalArgumentException("Nome de usuário já cadastrado");
		}
				
		userDao.save(user);
		
		if(!user.getUsername().equals(currentUsername)){
			List<DeviceDTO> devices = deviceDao.findAllByUsername(currentUsername);
			for(DeviceDTO device : devices) {
				device.setUsername(user.getUsername());
			}
			deviceDao.saveAll(devices);
		}
	}

}
