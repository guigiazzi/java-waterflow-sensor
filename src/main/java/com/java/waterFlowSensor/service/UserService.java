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
	
	public UserDTO retrieveProfileData(String username) {
		if(!userDao.existsByUsername(username)) {
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
	}
	
	public void login(String username, String password) {
		if(!userDao.existsByUsernameAndPassword(username, password)) {
			throw new IllegalArgumentException("Usuário ou senha incorretos");
		}
	}

}
