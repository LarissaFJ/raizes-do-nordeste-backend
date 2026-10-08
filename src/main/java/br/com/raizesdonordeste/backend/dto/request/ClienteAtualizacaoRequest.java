package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteAtualizacaoRequest {

    @Pattern(regexp = "(?s).*\\S.*")
    @Size(max = 255)
    private String nome;
    @Pattern(regexp = "\\d{11}")
    private String cpf;
    @Pattern(regexp = "(?s).*\\S.*")
    @Email
    @Size(max = 255)
    private String email;
    @Pattern(regexp = "\\d{10,11}")
    private String telefone;
}
