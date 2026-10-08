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

    @Positive(message = "O valor deve ser maior que zero")
    private Long clienteId;
    @NotNull(message = "Campo obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private Long unidadeId;
    @NotNull(message = "Campo obrigatório")
    private CanalPedido canalPedido;
    @DecimalMin("0.00", message = "O valor está abaixo do mínimo permitido")
    private BigDecimal desconto;
    @NotEmpty(message = "A lista não pode estar vazia")
    @Valid
    private List<ItemPedidoRequest> itens;
}
