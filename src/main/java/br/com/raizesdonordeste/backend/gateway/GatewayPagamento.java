package br.com.raizesdonordeste.backend.gateway;

import br.com.raizesdonordeste.backend.dto.request.PagamentoRequest;
import br.com.raizesdonordeste.backend.enums.CanalPedido;
import br.com.raizesdonordeste.backend.enums.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@RequiredArgsConstructor
@Component
public class GatewayPagamento {

    private final Random random = new Random();

    public String pagar(PagamentoRequest pagamentoRequest, CanalPedido canalPedido) {
        FormaPagamento formaPagamento = pagamentoRequest.getFormaPagamento();

        if (formaPagamento == FormaPagamento.DINHEIRO) {
            if (canalPedido == CanalPedido.BALCAO) {
                return "CONFIRMADO";
            } else {
                return "NEGADO";
            }
        }

        int numero = random.nextInt(100);

        if (numero < 20) {
            return "NEGADO";
        } else {
            return "CONFIRMADO";
        }
    }
}
