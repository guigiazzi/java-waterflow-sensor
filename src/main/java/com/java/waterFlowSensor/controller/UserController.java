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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.java.waterFlowSensor.DTO.UserDTO;
import com.java.waterFlowSensor.service.UserService;

import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private Gson gson;

	@PostMapping(value = "/register")
	public ResponseEntity<String> registrer(@Valid @RequestBody UserDTO user) {
		log.info("\n\n--- Requisição para cadastro recebida: " + gson.toJson(user));

		userService.registrer(user);

		log.info("Usuário cadastrado com sucesso");

		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/retrieveProfileData")
	public ResponseEntity<UserDTO> retrieveProfileData(@Valid @RequestHeader String username) {
		log.info("\n\n--- Requisição para recuperar dados completos do perfil do usuário " + username + " recebida");

		UserDTO user = userService.retrieveProfileData(username);

		log.info("Retorno da requisição de recuperar dados completos do perfil " + gson.toJson(user));

		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateProfile/{currentUsername}")
	public ResponseEntity<String> updateProfile(@Valid @RequestBody UserDTO user, @Valid @PathVariable String currentUsername) {
		log.info("\n\n--- Requisição para atualizar dados do perfil " + currentUsername + " recebida: " + gson.toJson(user));

		userService.updateProfile(user, currentUsername);

		log.info("Usuário atualizado com sucesso");

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@GetMapping(value = "/login")
	public ResponseEntity<?> registrer(@Valid @RequestHeader String username, @Valid @RequestHeader String password) {
		log.info("\n\n--- Requisição para login recebida. Usuário: " + username + ", senha: " + password);

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
