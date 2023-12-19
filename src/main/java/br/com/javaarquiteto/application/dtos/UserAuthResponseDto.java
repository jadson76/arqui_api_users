package br.com.javaarquiteto.application.dtos;

import java.util.Date;
import java.util.UUID;


public record UserAuthResponseDto(UUID id,String name,String email,String accessToken,Date expiration) {	

}
