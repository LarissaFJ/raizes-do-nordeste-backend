package br.com.raizesdonordeste.backend.service;

import br.com.raizesdonordeste.backend.dto.request.UnidadeAtualizacaoRequest;
import br.com.raizesdonordeste.backend.dto.request.UnidadeRequest;
import br.com.raizesdonordeste.backend.dto.response.UnidadeResponse;
import br.com.raizesdonordeste.backend.entity.Unidade;
import br.com.raizesdonordeste.backend.exception.UnidadeNaoEncontradaException;
import br.com.raizesdonordeste.backend.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    public UnidadeResponse cadastrar(UnidadeRequest request) {

        Unidade unidade = Unidade.builder()
                .nome(request.getNome())
                .endereco(request.getEndereco())
                .cidade(request.getCidade())
                .estado(request.getEstado())
                .build();

        Unidade unidadeSalva = unidadeRepository.save(unidade);

        log.info("Unidade cadastrada com sucesso. id={}", unidadeSalva.getId());

        return mapearParaResponse(unidadeSalva);
    }

    public List<UnidadeResponse> listar() {

        List<Unidade> unidades = unidadeRepository.findAll();

        List<UnidadeResponse> responses = new ArrayList<>();

        for (Unidade unidade : unidades) {
            responses.add(mapearParaResponse(unidade));
        }

        log.info("Lista de unidades consultada. quantidade={}", responses.size());

        return responses;
    }

    public UnidadeResponse buscarPorId(Long id) {

        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() ->
                        new UnidadeNaoEncontradaException("Unidade não encontrada"));

        log.info("Unidade consultada. id={}", id);

        return mapearParaResponse(unidade);
    }

    public UnidadeResponse atualizar(
            Long id,
            UnidadeAtualizacaoRequest request) {

        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() ->
                        new UnidadeNaoEncontradaException("Unidade não encontrada"));

        if (request.getNome() != null) {
            unidade.setNome(request.getNome());
        }

        if (request.getEndereco() != null) {
            unidade.setEndereco(request.getEndereco());
        }

        if (request.getCidade() != null) {
            unidade.setCidade(request.getCidade());
        }

        if (request.getEstado() != null) {
            unidade.setEstado(request.getEstado());
        }

        Unidade unidadeAtualizada = unidadeRepository.save(unidade);

        log.info("Unidade atualizada com sucesso. id={}", id);

        return mapearParaResponse(unidadeAtualizada);
    }

    public void excluir(Long id) {

        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() ->
                        new UnidadeNaoEncontradaException("Unidade não encontrada"));

        unidadeRepository.delete(unidade);

        log.info("Unidade excluída com sucesso. id={}", id);
    }

    private UnidadeResponse mapearParaResponse(Unidade unidade) {

        UnidadeResponse response = new UnidadeResponse();

        response.setId(unidade.getId());
        response.setNome(unidade.getNome());
        response.setEndereco(unidade.getEndereco());
        response.setCidade(unidade.getCidade());
        response.setEstado(unidade.getEstado());

        return response;
    }
}
