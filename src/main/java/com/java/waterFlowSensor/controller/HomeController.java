package com.java.waterFlowSensor.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.java.waterFlowSensor.DTO.ChartViewDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.service.HomeService;

import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomeController {
	
	@Autowired
	HomeService homeService;

	@Autowired
	Gson gson;

	@GetMapping(value = "/getChartViewCards")
	public ResponseEntity<List<ChartViewDTO>> getChartViewCards(@Valid @RequestHeader String username) {
		log.info("\n\n--- Requisição para recuperar cards dos gráficos das visões. Usuário: " + username);

		List<ChartViewDTO> chartViewCards = homeService.getChartViewCards(username);

		log.info("Retorno da requisição de recuperar cards dos gráficos das visões: " + gson.toJson(chartViewCards));

		return new ResponseEntity<List<ChartViewDTO>>(chartViewCards, HttpStatus.OK);
	}

	@GetMapping(value = "/getDeviceCards")
	public ResponseEntity<List<DeviceDTO>> getDeviceCards(@Valid @RequestHeader String username) {
		log.info("\n\n--- Requisição para recuperar cards dos dispositivos recebida. Usuário: " + username);

		List<DeviceDTO> devices = homeService.getDeviceCards(username);

		log.info("Retorno da requisição de recuperar cards dos dispositivos: " + gson.toJson(devices));

		return new ResponseEntity<List<DeviceDTO>>(devices, HttpStatus.OK);
	}


	@GetMapping(value = "/getDeviceDetails")
	public ResponseEntity<DeviceDTO> getDeviceDetails(@Valid @RequestHeader String deviceId) {
		log.info("\n\n--- Requisição para recuperar detalhes do dispositivo recebida. DeviceId: " + deviceId);

		DeviceDTO device = homeService.getDeviceDetails(deviceId);

		log.info("Retorno da requisição de recuperar detalhes do dispositivo: " + gson.toJson(device));

		return new ResponseEntity<DeviceDTO>(device, HttpStatus.OK);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
		log.severe(ex.getMessage());

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
