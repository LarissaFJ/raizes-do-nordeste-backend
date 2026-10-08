package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import br.com.raizesdonordeste.backend.enums.CanalPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PedidoAtualizacaoRequest {

    @Positive(message = "O valor deve ser maior que zero")
    private Long unidadeId;
    private CanalPedido canalPedido;
    @Pattern(regexp = "PENDENTE|CONFIRMADO|CANCELADO", message = "Status do pedido inválido")
    private String statusPedido;
    @DecimalMin(value = "0.00", message = "O valor está abaixo do mínimo permitido")
    private BigDecimal desconto;

}
