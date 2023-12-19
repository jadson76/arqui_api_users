package br.com.javaarquiteto;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.javaarquiteto.application.dtos.ErrorResponseDto;
import br.com.javaarquiteto.application.dtos.UserAuthRequestDto;
import br.com.javaarquiteto.application.dtos.UserAuthResponseDto;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserAuthTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;

	
	
	@Test
	@Order(1)
	public void userAuthSuccessTest() throws Exception {
		
		UserAuthRequestDto dto = new UserAuthRequestDto();
		dto.setEmail("admin@teste.com");
		dto.setPassword("@Admin123");
		
		MvcResult result = mockMvc.perform(post("/api/users/auth")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		UserAuthResponseDto response = objectMapper.readValue(content, UserAuthResponseDto.class);
		
		assertNotNull(response.accessToken());
		assertNotNull(response.expiration());
		
	}
	
	@Test
	@Order(2)
	public void userAuthBadRequestTest() throws Exception {
		UserAuthRequestDto dto = new UserAuthRequestDto();
		
		Faker faker = new Faker();
		dto.setEmail(faker.internet().emailAddress());
		dto.setPassword("@Admin123");
		
		MvcResult result = mockMvc.perform(post("/api/users/auth")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isBadRequest())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		ErrorResponseDto response = objectMapper.readValue(content, ErrorResponseDto.class);
		
		assertTrue(response.errors().get(0).contains("Acesso negado"));
	}


}
