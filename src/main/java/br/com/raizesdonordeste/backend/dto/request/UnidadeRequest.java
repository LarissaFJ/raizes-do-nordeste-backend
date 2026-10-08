package br.com.raizesdonordeste.backend.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeRequest {

    @NotBlank(message = "Campo obrigatório")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String nome;
    @NotBlank(message = "Campo obrigatório")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String endereco;
    @NotBlank(message = "Campo obrigatório")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String cidade;
    @NotBlank(message = "Campo obrigatório")
    @Size(max = 255, message = "O campo deve ter no máximo 255 caracteres")
    private String estado;
}
