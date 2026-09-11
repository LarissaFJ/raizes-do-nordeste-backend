package br.com.raizesdonordeste.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteAtualizacaoRequest {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
}
