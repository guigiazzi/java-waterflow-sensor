package com.java.waterFlowSensor.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.java.waterFlowSensor.DTO.UserDTO;
import com.java.waterFlowSensor.service.AuthenticationService;

import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private Gson gson;

	@PostMapping(value = "/register")
	public ResponseEntity<String> register(@Valid @RequestBody UserDTO user) {
		log.info("Register request received: " + gson.toJson(user));
		
		authenticationService.register(user);

		log.info("User registered successfully");

		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@GetMapping(value = "/login")
	public ResponseEntity<UserDTO> login(@Valid @RequestHeader String username,
			@Valid @RequestHeader String password) {
		log.info("Login request received. User: : " + username + ", password: " + password);
		
		UserDTO user = authenticationService.login(username, password);

		log.info("Login successfull");

		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
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
