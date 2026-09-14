package br.com.raizesdonordeste.backend.dto.request;

import br.com.raizesdonordeste.backend.enums.CanalPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PedidoRequest {

    private Long clienteId;
    private Long unidadeId;
    private CanalPedido canalPedido;
    private BigDecimal desconto;
    private List<ItemPedidoRequest> itens;
}
