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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.java.waterFlowSensor.DTO.UserDTO;
import com.java.waterFlowSensor.service.UserService;

import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private Gson gson;
	
	@GetMapping(value = "/profile-data")
	public ResponseEntity<UserDTO> retrieveProfileData(@Valid @RequestHeader String username) {
		log.info("Request to get profile data received: " + username);
		
		UserDTO user = userService.retrieveProfileData(username);

		log.info("Return from getting profile data: " + gson.toJson(user));
		
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	@PutMapping(value = "/profile/{currentUsername}")
	public ResponseEntity<String> updateProfile(@Valid @RequestBody UserDTO user, @Valid @PathVariable String currentUsername) {
		log.info("Request to update profile data from user " + currentUsername + " received: " + gson.toJson(user));

		userService.updateProfile(user, currentUsername);

		log.info("User updated successfully");

		return new ResponseEntity<String>(HttpStatus.OK);
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
