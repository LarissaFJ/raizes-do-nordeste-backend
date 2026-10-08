package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoAtualizacaoRequest {

    @Pattern(regexp = "(?s).*\\S.*")
    private String formaPagamento;
    @Positive
    private BigDecimal valor;
    @Pattern(regexp = "(?s).*\\S.*")
    private String status;
}
