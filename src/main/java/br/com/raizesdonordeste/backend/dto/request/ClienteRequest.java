package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {

    @NotBlank
    @Size(max = 255)
    private String nome;
    @NotBlank
    @Pattern(regexp = "\\d{11}")
    private String cpf;
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
    @NotBlank
    @Pattern(regexp = "\\d{10,11}")
    private String telefone;
}
