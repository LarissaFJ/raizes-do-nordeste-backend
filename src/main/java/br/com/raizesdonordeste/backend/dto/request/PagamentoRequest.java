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

    @NotNull(message = "Campo obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private Long pedidoId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Campo obrigatório")
    private FormaPagamento formaPagamento;
    @NotNull(message = "Campo obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal valor;
}
