package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeAtualizacaoRequest {

    @Pattern(regexp = "(?s).*\\S.*", message = "O formato do campo é inválido")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String nome;
    @Pattern(regexp = "(?s).*\\S.*", message = "O formato do campo é inválido")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String endereco;
    @Pattern(regexp = "(?s).*\\S.*", message = "O formato do campo é inválido")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String cidade;
    @Pattern(regexp = "(?s).*\\S.*", message = "O formato do campo é inválido")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String estado;
}
