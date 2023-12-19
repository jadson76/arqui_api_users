package br.com.javaarquiteto.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserAuthRequestDto {
	
	@Email(message = "Informe um endereço de e-mail valido.")
	@NotBlank(message = "Informe o e-mail do usuario")
	private String email;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$",
			message = "Informe a senha com letras maiúsculas, minúsculas, números, símbolos e pelo menos 8 caracteres.")
	@NotBlank(message = "Informe a senha do usuario")
	private String password;

}
