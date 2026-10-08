package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequest {

    @NotBlank
    @Size(max = 255)
    private String nome;
    @Size(max = 255)
    private String descricao;
    @NotNull
    @Positive
    private BigDecimal preco;
    private Boolean ativo;

}
