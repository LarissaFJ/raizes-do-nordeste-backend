package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoAtualizacaoRequest {

    @Pattern(regexp = "(?s).*\\S.*", message = "O formato do campo é inválido")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String nome;
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String descricao;
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal preco;
    private Boolean ativo;
}
