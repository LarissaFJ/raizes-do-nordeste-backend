package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import br.com.raizesdonordeste.backend.enums.FormaPagamento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoRequest {

    @NotNull
    @Positive
    private Long pedidoId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private FormaPagamento formaPagamento;
    @NotNull
    @Positive
    private BigDecimal valor;
}
