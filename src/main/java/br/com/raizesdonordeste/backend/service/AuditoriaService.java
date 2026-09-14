package br.com.raizesdonordeste.backend.service;

import br.com.raizesdonordeste.backend.dto.request.AuditoriaRequest;
import br.com.raizesdonordeste.backend.dto.response.AuditoriaResponse;
import br.com.raizesdonordeste.backend.entity.Auditoria;
import br.com.raizesdonordeste.backend.exception.AuditoriaNaoEncontradaException;
import br.com.raizesdonordeste.backend.repository.AuditoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaResponse registrar(AuditoriaRequest request) {

        Auditoria auditoria = Auditoria.builder()
                .pedidoId(request.getPedidoId())
                .tipoOperacao(request.getTipoOperacao())
                .descricao(request.getDescricao())
                .dataRegistro(LocalDateTime.now())
                .build();

        Auditoria auditoriaSalva = auditoriaRepository.save(auditoria);

        log.info("Auditoria registrada com sucesso. id={}", auditoriaSalva.getId());

        return mapearParaResponse(auditoriaSalva);
    }

    public List<AuditoriaResponse> listar() {

        List<Auditoria> auditorias = auditoriaRepository.findAll();

        List<AuditoriaResponse> responses = new ArrayList<>();

        for (Auditoria auditoria : auditorias) {
            responses.add(mapearParaResponse(auditoria));
        }

        log.info("Lista de auditorias consultada. quantidade={}", responses.size());

        return responses;
    }

    public AuditoriaResponse buscarPorId(Long id) {

        Auditoria auditoria = auditoriaRepository.findById(id)
                .orElseThrow(() ->
                        new AuditoriaNaoEncontradaException("Auditoria não encontrada"));

        log.info("Auditoria consultada. id={}", id);

        return mapearParaResponse(auditoria);
    }

    private AuditoriaResponse mapearParaResponse(Auditoria auditoria) {

        AuditoriaResponse response = new AuditoriaResponse();

        response.setId(auditoria.getId());
        response.setPedidoId(auditoria.getPedidoId());
        response.setTipoOperacao(auditoria.getTipoOperacao());
        response.setDescricao(auditoria.getDescricao());
        response.setDataRegistro(auditoria.getDataRegistro());

        return response;
    }
}
