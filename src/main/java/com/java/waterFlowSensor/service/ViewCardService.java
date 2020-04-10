package com.java.waterFlowSensor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.waterFlowSensor.DAO.ViewCardDAO;
import com.java.waterFlowSensor.DTO.ViewCardDTO;

import lombok.extern.java.Log;

@Log
@Service
public class ViewCardService {
	
	@Autowired
	ViewCardDAO viewCardDAO;
	
	public List<ViewCardDTO> getCards(String username) {
		log.info("Buscando cards para visões do usuário " + username);
		
		return viewCardDAO.findAllByUsername(username);
	}
	
}
