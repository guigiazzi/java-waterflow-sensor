package com.java.waterFlowSensor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.waterFlowSensor.DAO.UserDAO;
import com.java.waterFlowSensor.DTO.UserDTO;

import lombok.extern.java.Log;

@Log
@Service
public class UserService {
	
	@Autowired
	private UserDAO userDao;
	
	public void registrer(UserDTO user) {
		if(userDao.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("Usuário já cadastrado");
		}
		
		log.info("Inserindo usuário no MongoDB");
		userDao.insert(user);
	}
	
	public void login(String username, String password) {
		if(!userDao.existsByUsernameAndPassword(username, password)) {
			throw new IllegalArgumentException("Usuário ou senha incorretos");
		}
	}

}
