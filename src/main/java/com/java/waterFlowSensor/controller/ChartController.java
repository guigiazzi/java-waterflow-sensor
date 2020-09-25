package com.java.waterFlowSensor.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.java.waterFlowSensor.DTO.ChartViewDTO;
import com.java.waterFlowSensor.DTO.DeviceDTO;
import com.java.waterFlowSensor.DTO.FixedChartViewCardDTO;
import com.java.waterFlowSensor.DTO.UserDTO;
import com.java.waterFlowSensor.service.HomeService;

import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1/chart")
public class ChartController {

	@Autowired
	private HomeService homeService;

	@Autowired
	private Gson gson;

	@GetMapping(value = "/user-data")
	public ResponseEntity<UserDTO> getUserData(@Valid @RequestHeader String username) {
		log.info("Request to get data from user " + username);

		UserDTO user = homeService.getUserData(username);
		 
		log.info("Return from getting data from user: " + gson.toJson(user));

		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}

	@GetMapping(value = "/fixed-chart-view-cards")
	public ResponseEntity<List<FixedChartViewCardDTO>> getChartViewCards() {
		log.info("Request to get fixed chart view cards");

		List<FixedChartViewCardDTO> chartViewCards = homeService.getFixedChartViewCards();

		log.info("Return from getting fixed chart view cards: " + gson.toJson(chartViewCards));

		return new ResponseEntity<List<FixedChartViewCardDTO>>(chartViewCards, HttpStatus.OK);
	}

	@GetMapping(value = "/device-cards")
	public ResponseEntity<List<String>> getDeviceCards(@Valid @RequestHeader String username) {
		log.info("Request to get device cards from user " + username);
		
		List<String> devices = homeService.getDeviceCards(username);

		log.info("Return from getting device cards: " + gson.toJson(devices));
		
		return new ResponseEntity<List<String>>(devices, HttpStatus.OK);
	}

	@GetMapping(value = "/device-details")
	public ResponseEntity<List<DeviceDTO>> getDeviceDetails(@Valid @RequestHeader String username) {
		log.info("Request to get device details received. Username:  " + username);
		
		List<DeviceDTO> devices = homeService.getDeviceDetails(username);

		log.info("Return from getting device details: " + gson.toJson(devices));
		
		return new ResponseEntity<List<DeviceDTO>>(devices, HttpStatus.OK);
	}

	@GetMapping(value = "/chart-view")
	public ResponseEntity<ChartViewDTO> getChartView(@Valid @RequestHeader String chartId,
			@Nullable @RequestHeader String deviceId, @Valid @RequestHeader String username,
			@Valid @RequestHeader String incomingSource) {
			
		
		log.info("Request to get chart received. ChartId: " + chartId + ". Username: " + username +
					". DeviceId: " + deviceId + ". IncomingSource: " + incomingSource);
			
		ChartViewDTO chartView = homeService.getChartView(chartId, deviceId, username, incomingSource);

		log.info("Return from getting chart: " + gson.toJson(chartView));
		
		return new ResponseEntity<ChartViewDTO>(chartView, HttpStatus.OK);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
		log.severe(ex.getMessage());

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
