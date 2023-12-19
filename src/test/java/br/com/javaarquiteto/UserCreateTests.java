package br.com.javaarquiteto;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.javaarquiteto.application.dtos.UserCreateRequestDto;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserCreateTests {
	
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	static String emailUsuario;
	static String nomeUsuario;
	
	
	@Test
	@Order(1)
	public void userCreateSucessTest() throws Exception {	
		
		Faker faker = new Faker();
		
		UserCreateRequestDto request = new UserCreateRequestDto();
		request.setName(faker.name().fullName());
		request.setEmail("biojadson@gmail.com");
		request.setPassword("@Teste12345");
		
		//realizando a chamada para a API e verificando a resposta obtida
		mockMvc.perform(post("/api/users/create") //caminho do endpoint
				.contentType("application/json") //formato dos dados
				.content(objectMapper.writeValueAsString(request))) //serializando os dados
				.andExpect(status().isCreated()); //verificando o status da resposta
		
		emailUsuario = request.getEmail();
		nomeUsuario  = request.getName();
		
	}
	
	@Test
	@Order(2)
	public void userCreateBadRequestTest() throws Exception {
		
	
		UserCreateRequestDto request = new UserCreateRequestDto();
		request.setName(nomeUsuario);
		request.setEmail(emailUsuario);
		request.setPassword("@Teste1234");
		
		//realizando a chamada para a API e verificando a resposta obtida
		mockMvc.perform(post("/api/users/create") //caminho do endpoint
				.contentType("application/json") //formato dos dados
				.content(objectMapper.writeValueAsString(request))) //serializando os dados
				.andExpect(status().isBadRequest()); //verificando o status da resposta
	}

}
