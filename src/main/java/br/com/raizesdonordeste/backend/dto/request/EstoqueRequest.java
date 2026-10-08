package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueRequest {

    @NotNull
    @Positive
    private Long unidadeId;
    @NotNull
    @Positive
    private Long produtoId;
    @NotNull
    @PositiveOrZero
    private Integer quantidade;
}
