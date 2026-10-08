package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import br.com.raizesdonordeste.backend.enums.CanalPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PedidoAtualizacaoRequest {

    @Positive
    private Long unidadeId;
    private CanalPedido canalPedido;
    @Pattern(regexp = "PENDENTE|CONFIRMADO|CANCELADO")
    private String statusPedido;
    @DecimalMin("0.00")
    private BigDecimal desconto;

}
