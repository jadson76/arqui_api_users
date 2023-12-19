package br.com.javaarquiteto.domain.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.javaarquiteto.application.dtos.UserMessageDto;
import br.com.javaarquiteto.domain.entities.User;
import br.com.javaarquiteto.domain.exceptions.UserExceptions;
import br.com.javaarquiteto.domain.interfaces.UserService;
import br.com.javaarquiteto.infrastructure.components.JwtTokenComponent;
import br.com.javaarquiteto.infrastructure.components.Sha1Component;
import br.com.javaarquiteto.infrastructure.producers.UserMessageProducer;
import br.com.javaarquiteto.infrastructure.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	Sha1Component sha1Component;
	
	@Autowired
	JwtTokenComponent jwtTokenComponent;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	UserMessageProducer userMessageProducer;

	@Override
	public User create(User user) throws UserExceptions {
		
		user.setId(UUID.randomUUID());
		user.setPassword(sha1Component.sha1Hash(user.getPassword()));
		
		if(repository.find(user.getEmail()) != null ) {
			throw new UserExceptions("O email informado já esta cadastrado.");
		}
		
		repository.save(user);
		createWelcomeMessage(user);
		
		return user;
	}

	@Override
	public User authenticate(String email, String password) throws UserExceptions {		
	
		User user = repository.find(email, sha1Component.sha1Hash(password));
		
		if(user == null)
			throw new UserExceptions("Acesso negado. Usuario invalido");
		
		user.setAccessToken(jwtTokenComponent.generateToken(email)); 
		user.setExpiration(jwtTokenComponent.getExpiration());
		
		return user;
	}
	
	private void createWelcomeMessage(User user) {
		UserMessageDto dto = new UserMessageDto();
		
		dto.setEmailTo(user.getEmail());
		dto.setSubject("Conta criada com sucesso - API de usuarios.");
		
		StringBuilder sb = new StringBuilder();
		sb.append("<div>");
		sb.append("<p>Parabens " + user.getName() + ", sua conta de usuario foi criada com sucesso. </p>");
		sb.append("<p>att, </p>");
		sb.append("<p>Equipe de Boas Vindas. </p>");
		sb.append("</div>");
		
		dto.setBody(sb.toString());
		
		try {
			String message = objectMapper.writeValueAsString(dto);
			userMessageProducer.sendMessage(message);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
