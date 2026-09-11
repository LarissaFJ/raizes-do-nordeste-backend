package br.com.raizesdonordeste.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClienteResponse {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Integer pontosFidelidade;
    private LocalDateTime dataCadastro;
}
