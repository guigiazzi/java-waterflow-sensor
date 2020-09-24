package com.java.waterFlowSensor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.waterFlowSensor.DAO.DeviceDAO;
import com.java.waterFlowSensor.DAO.UserDAO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.DTO.UserDTO;

import lombok.extern.java.Log;

@Log
@Service
public class UserService {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private DeviceDAO deviceDao;
	
	public void registrer(UserDTO user) {
		if(userDao.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("Usuário já cadastrado");
		}
		
		log.info("Inserindo usuário no MongoDB");
		userDao.insert(user);
	}
	
	public UserDTO retrieveProfileData(String userId) {
		if(!userDao.existsByUserId(userId)) {
			throw new IllegalArgumentException("Usuário não encontrado");
		}
		
		log.info("Recuperando dados do perfil no MongoDB");
		return userDao.findByUsername(username);
	}
	
	public void updateProfile(UserDTO user, String currentUsername) {
		if(userDao.existsByUsername(user.getUsername()) && !user.getUsername().equals(currentUsername)) {
			throw new IllegalArgumentException("Nome de usuário já cadastrado");
		}
		
		log.info("Atualizando usuário no MongoDB");
		
		userDao.save(user);
		
		if(!user.getUsername().equals(currentUsername)){
			List<DeviceDTO> devices = deviceDao.findAllByUsername(currentUsername);
			for(DeviceDTO device : devices) {
				device.setUsername(user.getUsername());
			}
			deviceDao.saveAll(devices);
		}
	}
	
	public void login(String username, String password) {
		if(!userDao.existsByUsernameAndPassword(username, password)) {
			throw new IllegalArgumentException("Usuário ou senha incorretos");
		}
	}

}
