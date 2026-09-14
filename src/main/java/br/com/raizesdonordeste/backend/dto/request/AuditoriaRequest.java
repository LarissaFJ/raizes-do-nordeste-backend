package br.com.raizesdonordeste.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditoriaRequest {

    private Long pedidoId;
    private String tipoOperacao;
    private String descricao;
}
