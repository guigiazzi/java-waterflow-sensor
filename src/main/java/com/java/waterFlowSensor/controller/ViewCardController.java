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
import com.java.waterFlowSensor.DTO.ViewCardDTO;
import com.java.waterFlowSensor.service.ViewCardService;

import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ViewCardController {

	@Autowired
	ViewCardService viewCardService;

	@Autowired
	Gson gson;

	@GetMapping(value = "/getCards")
	public ResponseEntity<List<ViewCardDTO>> getCards(@Valid @RequestHeader String username) {
		log.info("\n\n--- Requisição para recuperar cards das visões recebida. Usuário: " + username);

		List<ViewCardDTO> cards = viewCardService.getCards(username);

		log.info("Retorno da requisição de recuperar cards das visões: " + gson.toJson(cards));

		return new ResponseEntity<List<ViewCardDTO>>(cards, HttpStatus.OK);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
		log.severe(ex.getMessage());

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
