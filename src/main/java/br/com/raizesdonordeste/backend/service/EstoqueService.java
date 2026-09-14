package br.com.raizesdonordeste.backend.service;

import br.com.raizesdonordeste.backend.dto.request.EstoqueAtualizacaoRequest;
import br.com.raizesdonordeste.backend.dto.request.EstoqueRequest;
import br.com.raizesdonordeste.backend.dto.response.EstoqueResponse;
import br.com.raizesdonordeste.backend.entity.Estoque;
import br.com.raizesdonordeste.backend.exception.EstoqueNaoEncontradoException;
import br.com.raizesdonordeste.backend.repository.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;


    public EstoqueResponse cadastrar(EstoqueRequest request) {

        Estoque estoque = Estoque.builder()
                .unidadeId(request.getUnidadeId())
                .produtoId(request.getProdutoId())
                .quantidade(request.getQuantidade())
                .build();

        Estoque estoqueSalvo = estoqueRepository.save(estoque);

        log.info("Estoque cadastrado com sucesso. id={}", estoqueSalvo.getId());

        return mapearParaResponse(estoqueSalvo);
    }

    public List<EstoqueResponse> listar() {

        log.info("Listando estoques");

        List<Estoque> estoques = estoqueRepository.findAll();

        List<EstoqueResponse> responses = new ArrayList<>();

        for (Estoque estoque : estoques) {
            responses.add(mapearParaResponse(estoque));
        }

        return responses;
    }

    public EstoqueResponse buscarPorId(Long id) {

        log.info("Buscando estoque. id={}", id);

        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() ->
                        new EstoqueNaoEncontradoException("Estoque não encontrado"));

        return mapearParaResponse(estoque);
    }

    public EstoqueResponse atualizar(Long id, EstoqueAtualizacaoRequest request) {

        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() ->
                        new EstoqueNaoEncontradoException("Estoque não encontrado"));

        if (request.getUnidadeId() != null) {
            estoque.setUnidadeId(request.getUnidadeId());
        }

        if (request.getProdutoId() != null) {
            estoque.setProdutoId(request.getProdutoId());
        }

        if (request.getQuantidade() != null) {
            estoque.setQuantidade(request.getQuantidade());
        }

        Estoque estoqueAtualizado = estoqueRepository.save(estoque);

        log.info("Estoque atualizado com sucesso. id={}", estoqueAtualizado.getId());

        return mapearParaResponse(estoqueAtualizado);
    }

    public void excluir(Long id) {

        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() ->
                        new EstoqueNaoEncontradoException("Estoque não encontrado"));

        estoqueRepository.delete(estoque);

        log.info("Estoque excluído com sucesso. id={}", id);
    }

    private EstoqueResponse mapearParaResponse(Estoque estoque) {

        EstoqueResponse response = new EstoqueResponse();

        response.setId(estoque.getId());
        response.setUnidadeId(estoque.getUnidadeId());
        response.setProdutoId(estoque.getProdutoId());
        response.setQuantidade(estoque.getQuantidade());

        return response;
    }
}