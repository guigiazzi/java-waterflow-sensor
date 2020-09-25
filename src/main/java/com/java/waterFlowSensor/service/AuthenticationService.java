package com.java.waterFlowSensor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.waterFlowSensor.DAO.UserDAO;
import com.java.waterFlowSensor.DTO.UserDTO;

@Service
public class AuthenticationService {
	
	@Autowired
	private UserDAO userDao;
	
	public void register(UserDTO user) {
		if(userDao.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("Nome de usuário já cadastrado");
		}
		
		userDao.insert(user);
	}
	
	public UserDTO login(String username, String password) {
		if(!userDao.existsByUsernameAndPassword(username, password)) {
			throw new IllegalArgumentException("Usuário ou senha incorretos");
		}
		
		UserDTO user = userDao.findByUsername(username);
		
		return user;
	}

}
