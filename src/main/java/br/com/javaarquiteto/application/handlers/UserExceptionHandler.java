package br.com.javaarquiteto.application.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.javaarquiteto.application.dtos.ErrorResponseDto;
import br.com.javaarquiteto.domain.exceptions.UserExceptions;


@ControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(UserExceptions.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponseDto handleUserException(UserExceptions e) {
		
		List<String> errors = new ArrayList<String>();
		errors.add(e.getMessage());
		
		ErrorResponseDto response = new ErrorResponseDto(HttpStatus.BAD_REQUEST,errors);		
		
		return response;

	}
}
