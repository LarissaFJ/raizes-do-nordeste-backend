package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthLoginRequest {

    @NotBlank(message = "Campo obrigatório")
    @Email(message = "E-mail deve ser válido")
    private String email;
    @NotBlank(message = "Campo obrigatório")
    private String senha;
}
