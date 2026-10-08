package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoRequest {

    @NotNull
    @Positive
    private Long produtoId;
    @NotNull
    @Positive
    private Integer quantidade;

}
