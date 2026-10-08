package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueRequest {

    @NotNull(message = "Campo obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private Long unidadeId;
    @NotNull(message = "Campo obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private Long produtoId;
    @NotNull(message = "Campo obrigatório")
    @PositiveOrZero(message = "O valor não pode ser negativo")
    private Integer quantidade;
}
