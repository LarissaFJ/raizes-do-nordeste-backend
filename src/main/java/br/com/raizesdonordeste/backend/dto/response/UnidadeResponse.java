package br.com.raizesdonordeste.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeResponse {

    private Long id;
    private String nome;
    private String endereco;
    private String cidade;
    private String estado;
}
