package br.com.javaarquiteto.infrastructure.components;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenComponent {
	
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private String jwtExpiration;
	
	
	public String generateToken(String userName) {	
				
		
		//gerando o token
				return Jwts.builder()
		               .setSubject(userName) // nome do usuário
		               .setIssuedAt(new Date()) // data de geração
		               .setExpiration(getExpiration()) // data de expiração
		               .signWith(SignatureAlgorithm.HS256, jwtSecret) // chave antifalsificação
		               .compact();

	}
	
	
	public Date getExpiration() {
		Date dataAtual = new Date();
		
		return new Date(dataAtual.getTime() + Integer.parseInt(jwtExpiration) * 1000);
	}

}
