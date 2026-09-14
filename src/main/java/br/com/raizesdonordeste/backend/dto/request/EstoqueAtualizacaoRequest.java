package br.com.raizesdonordeste.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueAtualizacaoRequest {

    private Long unidadeId;
    private Long produtoId;
    private Integer quantidade;
}
