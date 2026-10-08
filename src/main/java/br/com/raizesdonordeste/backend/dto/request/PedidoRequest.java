package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.Valid;

import jakarta.validation.constraints.*;

import br.com.raizesdonordeste.backend.enums.CanalPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PedidoRequest {

    @Positive
    private Long clienteId;
    @NotNull
    @Positive
    private Long unidadeId;
    @NotNull
    private CanalPedido canalPedido;
    @DecimalMin("0.00")
    private BigDecimal desconto;
    @NotEmpty
    @Valid
    private List<ItemPedidoRequest> itens;
}
