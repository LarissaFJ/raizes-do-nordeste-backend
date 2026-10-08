package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeRequest {

    @NotBlank
    @Size(max = 255)
    private String nome;
    @NotBlank
    @Size(max = 255)
    private String endereco;
    @NotBlank
    @Size(max = 255)
    private String cidade;
    @NotBlank
    @Size(max = 255)
    private String estado;
}
