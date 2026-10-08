package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthLoginRequest {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String senha;
}
