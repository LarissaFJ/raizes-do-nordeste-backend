package br.com.raizesdonordeste.backend.dto.response;

import br.com.raizesdonordeste.backend.enums.CanalPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PedidoResponse {

    private Long id;
    private Long clienteId;
    private Long unidadeId;
    private CanalPedido canalPedido;
    private LocalDateTime dataPedido;
    private String statusPedido;
    private BigDecimal desconto;
    private BigDecimal valorTotal;

}
