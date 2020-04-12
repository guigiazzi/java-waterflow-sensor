package com.java.waterFlowSensor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.waterFlowSensor.DAO.ChartViewDAO;
import com.java.waterFlowSensor.DAO.DeviceDAO;
import com.java.waterFlowSensor.DTO.ChartViewDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;

import lombok.extern.java.Log;

@Log
@Service
public class HomeService {
	
	@Autowired
	ChartViewDAO chartViewDAO;
	
	@Autowired
	DeviceDAO deviceDAO;
	
	public List<ChartViewDTO> getChartViewCards(String username) {
		log.info("Buscando cards para gráficos das visões do usuário " + username);
		
		return chartViewDAO.findAllByUsername(username);
	}
		
	public List<DeviceDTO> getDeviceCards(String username) {
		log.info("Buscando cards dos dispositivos para o usuário " + username);
		
		return deviceDAO.findAllByUsername(username);
	}
	
	public DeviceDTO getDeviceDetails(String deviceId) {
		log.info("Buscando detalhes do dispositivo " + deviceId);
		
		return deviceDAO.findByDeviceId(deviceId);
	}
}
