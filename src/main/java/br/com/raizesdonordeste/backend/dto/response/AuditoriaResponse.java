package br.com.raizesdonordeste.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditoriaResponse {

    private Long id;
    private Long pedidoId;
    private String tipoOperacao;
    private String descricao;
    private LocalDateTime dataRegistro;
}
