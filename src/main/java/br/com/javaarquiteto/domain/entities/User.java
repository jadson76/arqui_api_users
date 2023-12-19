package br.com.javaarquiteto.domain.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "tb_user")
@Data
public class User {
	
	@Id
	@Column(name="id")
	private UUID id;
	
	@Column(name="name", length = 100, nullable = false)
	private String name;
	
	@Column(name="email", length = 50, nullable = false, unique = true)
	private String email;
	
	@Column(name="password", length = 40, nullable = false)
	private String password;
	
	@Transient
	private String accessToken;
	
	@Transient
	private Date expiration;

}
