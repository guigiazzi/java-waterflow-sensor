package com.java.waterFlowSensor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.waterFlowSensor.DTO.UserDTO;
import com.java.waterFlowSensor.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(value = "/registrer")
	public ResponseEntity<String> registrer(@RequestBody UserDTO user) {
		String response;
		
		try {
			response = userService.registrer(user);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/login/{username}/{password}")
	public ResponseEntity<?> registrer(@PathVariable String username, @PathVariable String password) {		
		try {
			userService.login(username, password);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
