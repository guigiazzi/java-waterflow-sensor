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
import com.java.waterFlowSensor.DTO.ChartDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.DTO.ViewCardDTO;
import com.java.waterFlowSensor.service.ChartService;
import com.java.waterFlowSensor.service.DeviceService;
import com.java.waterFlowSensor.service.ViewCardService;

import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomeController {

	@Autowired
	ViewCardService viewCardService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	ChartService chartService;

	@Autowired
	Gson gson;

	@GetMapping(value = "/getCards")
	public ResponseEntity<List<ViewCardDTO>> getCards(@Valid @RequestHeader String username) {
		log.info("\n\n--- Requisição para recuperar cards das visões recebida. Usuário: " + username);

		List<ViewCardDTO> cards = viewCardService.getCards(username);

		log.info("Retorno da requisição de recuperar cards das visões: " + gson.toJson(cards));

		return new ResponseEntity<List<ViewCardDTO>>(cards, HttpStatus.OK);
	}

	@GetMapping(value = "/getDevices")
	public ResponseEntity<List<DeviceDTO>> getDevices(@Valid @RequestHeader String username) {
		log.info("\n\n--- Requisição para recuperar dispositivos recebida. Usuário: " + username);

		List<DeviceDTO> devices = deviceService.getDevices(username);

		log.info("Retorno da requisição de recuperar dispositivos: " + gson.toJson(devices));

		return new ResponseEntity<List<DeviceDTO>>(devices, HttpStatus.OK);
	}

	@GetMapping(value = "/getChart")
	public ResponseEntity<ChartDTO> getChart(@Valid @RequestHeader String chartId,
			@Valid @RequestHeader String username) {
		log.info("\n\n--- Requisição para recuperar gráfico recebida. ChartId: " + chartId + " usuário: " + username);

		ChartDTO chart = chartService.getChart(chartId, username);

		log.info("Retorno da requisição de recuperar gráfico: " + gson.toJson(chart));

		return new ResponseEntity<ChartDTO>(chart, HttpStatus.OK);
	}

	@GetMapping(value = "/getDeviceDetails")
	public ResponseEntity<DeviceDTO> getDeviceDetails(@Valid @RequestHeader String deviceId) {
		log.info("\n\n--- Requisição para recuperar detalhes do dispositivo recebida. DeviceId: " + deviceId);

		DeviceDTO device = deviceService.getDeviceDetails(deviceId);

		log.info("Retorno da requisição de recuperar detalhes do dispositivo: " + gson.toJson(device));

		return new ResponseEntity<DeviceDTO>(device, HttpStatus.OK);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
		log.severe(ex.getMessage());

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
