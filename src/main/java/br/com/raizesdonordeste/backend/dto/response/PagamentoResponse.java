package br.com.raizesdonordeste.backend.dto.response;

import br.com.raizesdonordeste.backend.enums.FormaPagamento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PagamentoResponse {

    private Long id;
    private Long pedidoId;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    private BigDecimal valor;
    private String status;
    private LocalDateTime dataPagamento;
}
