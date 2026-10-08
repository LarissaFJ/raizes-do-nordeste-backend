package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueAtualizacaoRequest {

    @Positive
    private Long unidadeId;
    @Positive
    private Long produtoId;
    @PositiveOrZero
    private Integer quantidade;
}
