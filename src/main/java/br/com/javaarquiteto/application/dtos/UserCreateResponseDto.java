package br.com.javaarquiteto.application.dtos;

import java.time.Instant;
import java.util.UUID;

import lombok.NoArgsConstructor;


public record UserCreateResponseDto(UUID id,String name,String email,Instant createdAt) {
	

}
