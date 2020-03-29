package com.java.waterFlowSensor.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.java.waterFlowSensor.DTO.UserDTO;
import com.java.waterFlowSensor.service.UserService;

import lombok.extern.java.Log;

@Log
@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	Gson gson;

	@PostMapping(value = "/registrer")
	public ResponseEntity<String> registrer(@Valid @RequestBody UserDTO user) {
		log.info("Requisição para cadastro recebida: " + gson.toJson(user));

		userService.registrer(user);

		log.info("Usuário cadastrado com sucesso");

		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@GetMapping(value = "/login")
	public ResponseEntity<?> registrer(@Valid @RequestHeader String username, @Valid @RequestHeader String password) {
		log.info("Requisição para login recebida. Usuário: " + username + ", senha: " + password);

		userService.login(username, password);

		log.info("Login realizado com sucesso");

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ExceptionHandler({ MethodArgumentNotValidException.class, MissingRequestHeaderException.class })
	public ResponseEntity<String> handleValidationExceptions(Exception ex) {
		String message = "Todos os campos obrigatórios precisam ser preenchidos";
		log.severe(message);

		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
		log.severe(ex.getMessage());

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
