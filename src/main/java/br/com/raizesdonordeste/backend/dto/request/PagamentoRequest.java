package br.com.raizesdonordeste.backend.dto.request;

import br.com.raizesdonordeste.backend.enums.FormaPagamento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoRequest {

    private Long pedidoId;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    private BigDecimal valor;
}
