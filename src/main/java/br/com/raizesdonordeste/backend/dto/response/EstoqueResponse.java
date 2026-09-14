package br.com.raizesdonordeste.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueResponse {

    private Long id;
    private Long unidadeId;
    private Long produtoId;
    private Integer quantidade;
}
