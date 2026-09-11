package br.com.raizesdonordeste.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequest {

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean ativo;

}
