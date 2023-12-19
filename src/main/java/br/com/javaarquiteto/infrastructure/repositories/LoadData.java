package br.com.javaarquiteto.infrastructure.repositories;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.javaarquiteto.domain.entities.User;
import br.com.javaarquiteto.infrastructure.components.Sha1Component;

@Component
public class LoadData implements ApplicationRunner{
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	Sha1Component sha1Component;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		
		String email = "admin@teste.com" ;
						
		User user = userRepository.find(email);
		
		if(user == null) {
			user = new User();
			user.setId(UUID.randomUUID());
			user.setEmail(email);
			user.setName("Administrador");
			user.setPassword(sha1Component.sha1Hash("@Admin123"));
			
			userRepository.save(user);
		}
		
	}

}
