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
import com.java.waterFlowSensor.DTO.FixedChartViewCardDTO;
import com.java.waterFlowSensor.service.HomeService;

import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomeController {

	@Autowired
	private HomeService homeService;

	@Autowired
	private Gson gson;

	@GetMapping(value = "/getFixedChartViewCards")
	public ResponseEntity<List<FixedChartViewCardDTO>> getChartViewCards() {
		log.info("\n\n--- Requisição para recuperar cards dos gráficos das visões");

		List<FixedChartViewCardDTO> chartViewCards = homeService.getFixedChartViewCards();

		log.info("Retorno da requisição de recuperar cards dos gráficos das visões: " + gson.toJson(chartViewCards));

		return new ResponseEntity<List<FixedChartViewCardDTO>>(chartViewCards, HttpStatus.OK);
	}

	@GetMapping(value = "/getDeviceCards")
	public ResponseEntity<List<String>> getDeviceCards(@Valid @RequestHeader String username) {
		log.info("\n\n--- Requisição para recuperar cards dos dispositivos recebida. Usuário: " + username);

		List<String> devices = homeService.getDeviceCards(username);

		log.info("Retorno da requisição de recuperar cards dos dispositivos: " + gson.toJson(devices));

		return new ResponseEntity<List<String>>(devices, HttpStatus.OK);
	}

	@GetMapping(value = "/getDeviceDetails")
	public ResponseEntity<DeviceDTO> getDeviceDetails(@Valid @RequestHeader String username,
			@Valid @RequestHeader String description) {
		log.info("\n\n--- Requisição para recuperar detalhes do dispositivo recebida. Username: " + username
				+ ". Description: " + description);

		DeviceDTO device = homeService.getDeviceDetails(username, description);

		log.info("Retorno da requisição de recuperar detalhes do dispositivo: " + gson.toJson(device));

		return new ResponseEntity<DeviceDTO>(device, HttpStatus.OK);
	}

	@GetMapping(value = "/getChartView")
	public ResponseEntity<ChartViewDTO> getChartView(@Valid @RequestHeader String type,
			@Valid @RequestHeader String title, @Valid @RequestHeader String username) {
		log.info("\n\n--- Requisição para recuperar gráfico recebida. Type: " + type + ". Title: " + title
				+ ". Usuário: " + username);

		ChartViewDTO chartView = homeService.getChartView(type, title, username);

		log.info("Retorno da requisição de recuperar gráfico: " + gson.toJson(chartView));

		return new ResponseEntity<ChartViewDTO>(chartView, HttpStatus.OK);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
		log.severe(ex.getMessage());

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
