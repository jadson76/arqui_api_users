package br.com.javaarquiteto.application.controllers;

import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.javaarquiteto.application.dtos.UserAuthRequestDto;
import br.com.javaarquiteto.application.dtos.UserAuthResponseDto;
import br.com.javaarquiteto.application.dtos.UserCreateRequestDto;
import br.com.javaarquiteto.application.dtos.UserCreateResponseDto;
import br.com.javaarquiteto.domain.entities.User;
import br.com.javaarquiteto.domain.exceptions.UserExceptions;
import br.com.javaarquiteto.domain.interfaces.UserService;
import br.com.javaarquiteto.infrastructure.components.MessageComponent;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/users")
public class UsersController {
	
	@Autowired
	UserService service;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	MessageComponent messages;
	
	@PostMapping("create")
	public ResponseEntity<UserCreateResponseDto> create(@RequestBody @Valid UserCreateRequestDto dto) throws UserExceptions {		
		
		User user = modelMapper.map(dto, User.class);	
		
		service.create(user);		
	
		UserCreateResponseDto response = new UserCreateResponseDto(user.getId(), user.getName(),user.getEmail(),Instant.now()); 
		
		log.info(messages.get("user.create.sucess"));
		
		return ResponseEntity.status(201)
				.body(response);
		
	}
	
	@PostMapping("auth")
	public ResponseEntity<UserAuthResponseDto> auth(@RequestBody @Valid UserAuthRequestDto dto) throws UserExceptions {
		
				
		User user = service.authenticate(dto.getEmail(), dto.getPassword());
		
		UserAuthResponseDto response = new UserAuthResponseDto(user.getId(), user.getName(), user.getEmail(), user.getAccessToken(), user.getExpiration());
		
		return ResponseEntity.status(200)
				.body(response);
		
	}

}
