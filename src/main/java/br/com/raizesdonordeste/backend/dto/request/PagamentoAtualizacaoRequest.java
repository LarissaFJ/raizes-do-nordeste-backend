package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagamentoAtualizacaoRequest {

    @Pattern(regexp = "(?s).*\\S.*", message = "O formato do campo é inválido")
    private String formaPagamento;
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal valor;
    @Pattern(regexp = "(?s).*\\S.*", message = "O formato do campo é inválido")
    private String status;
}
