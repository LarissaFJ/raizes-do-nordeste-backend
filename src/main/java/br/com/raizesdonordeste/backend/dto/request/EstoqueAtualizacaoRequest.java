package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueAtualizacaoRequest {

    @Positive(message = "O valor deve ser maior que zero")
    private Long unidadeId;
    @Positive(message = "O valor deve ser maior que zero")
    private Long produtoId;
    @PositiveOrZero(message = "O valor não pode ser negativo")
    private Integer quantidade;
}
