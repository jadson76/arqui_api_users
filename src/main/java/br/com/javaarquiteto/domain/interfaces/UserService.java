package br.com.javaarquiteto.domain.interfaces;

import br.com.javaarquiteto.domain.entities.User;
import br.com.javaarquiteto.domain.exceptions.UserExceptions;

public interface UserService {
	
	User create( User user)  throws UserExceptions;
	User authenticate(String email, String password) throws UserExceptions;

}
