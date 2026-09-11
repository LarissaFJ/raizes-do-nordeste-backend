package br.com.raizesdonordeste.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeRequest {

    private String nome;
    private String endereco;
    private String cidade;
    private String estado;
}
