package com.java.waterFlowSensor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.waterFlowSensor.DAO.DeviceDAO;
import com.java.waterFlowSensor.DTO.DeviceDTO;

import lombok.extern.java.Log;

@Log
@Service
public class DeviceService {
	
	@Autowired
	DeviceDAO deviceDAO;
	
	public List<DeviceDTO> getDevices(String username) {
		log.info("Buscando dispositivos para o usuário " + username);
		
		return deviceDAO.findAllByUsername(username);
	}
	
	public DeviceDTO getDeviceDetails(String deviceId) {
		log.info("Buscando dispositivo " + deviceId);
		
		return deviceDAO.findByDeviceId(deviceId);
	}
}
