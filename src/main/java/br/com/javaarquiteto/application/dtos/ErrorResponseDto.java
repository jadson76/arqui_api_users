package br.com.javaarquiteto.application.dtos;

import java.util.List;

import org.springframework.http.HttpStatus;

public record ErrorResponseDto(HttpStatus status,  List<String> errors) {

}
