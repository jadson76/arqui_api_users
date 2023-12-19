package br.com.javaarquiteto.application.dtos;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorResponseDtoOld {
	
	private HttpStatus status;
	private List<String> errors;

}
