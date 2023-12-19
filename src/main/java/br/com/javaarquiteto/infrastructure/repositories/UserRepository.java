package br.com.javaarquiteto.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.javaarquiteto.domain.entities.User;

public interface UserRepository extends JpaRepository<User, UUID>{
	
	@Query("select u from User u where u.email = :email")
	User find(@Param("email") String email);
	
	@Query("select u from User u where u.email = :email and u.password = :password ")
	User find(@Param("email") String email , @Param("password") String password);

}
