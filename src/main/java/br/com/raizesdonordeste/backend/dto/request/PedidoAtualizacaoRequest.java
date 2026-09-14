package br.com.raizesdonordeste.backend.dto.request;

import br.com.raizesdonordeste.backend.enums.CanalPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PedidoAtualizacaoRequest {

    private Long unidadeId;
    private CanalPedido canalPedido;
    private String statusPedido;
    private BigDecimal desconto;

}
