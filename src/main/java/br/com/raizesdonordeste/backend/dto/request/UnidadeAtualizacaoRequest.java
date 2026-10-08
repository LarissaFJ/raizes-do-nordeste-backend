package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeAtualizacaoRequest {

    @Pattern(regexp = "(?s).*\\S.*")
    @Size(max = 255)
    private String nome;
    @Pattern(regexp = "(?s).*\\S.*")
    @Size(max = 255)
    private String endereco;
    @Pattern(regexp = "(?s).*\\S.*")
    @Size(max = 255)
    private String cidade;
    @Pattern(regexp = "(?s).*\\S.*")
    @Size(max = 255)
    private String estado;
}
