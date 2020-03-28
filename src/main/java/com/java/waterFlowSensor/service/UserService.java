package com.java.waterFlowSensor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.waterFlowSensor.DAO.UserDAO;
import com.java.waterFlowSensor.DTO.UserDTO;

@Service
public class UserService {
	
	@Autowired
	UserDAO userDao;
	
	public String registrer(UserDTO user) {
		if(userDao.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("Usuário já cadastrado");
		}
		
		userDao.insert(user);
		
		return "Cadastro realizado com sucesso!";
	}
	
	public void login(String username, String password) {
		if(!userDao.existsByUsernameAndPassword(username, password)) {
			throw new IllegalArgumentException("Usuário ou senha incorretos");
		}
	}

}
