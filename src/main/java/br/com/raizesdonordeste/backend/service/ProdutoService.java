package br.com.raizesdonordeste.backend.service;

import br.com.raizesdonordeste.backend.dto.request.ProdutoAtualizacaoRequest;
import br.com.raizesdonordeste.backend.dto.request.ProdutoRequest;
import br.com.raizesdonordeste.backend.dto.response.ProdutoResponse;
import br.com.raizesdonordeste.backend.entity.Produto;
import br.com.raizesdonordeste.backend.exception.ProdutoNaoEncontradoException;
import br.com.raizesdonordeste.backend.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoResponse cadastrar(ProdutoRequest request) {

        Produto produto = Produto.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .preco(request.getPreco())
                .ativo(true)
                .build();

        Produto produtoSalvo = produtoRepository.save(produto);

        log.info("Produto cadastrado com sucesso. id={}", produtoSalvo.getId());

        return mapearParaResponse(produtoSalvo);
    }

    public List<ProdutoResponse> listar() {

        List<Produto> produtos = produtoRepository.findAll();

        List<ProdutoResponse> responses = new ArrayList<>();

        for (Produto produto : produtos) {
            responses.add(mapearParaResponse(produto));
        }

        log.info("Lista de produtos consultada. quantidade={}", responses.size());

        return responses;
    }

    public ProdutoResponse buscarPorId(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException("Produto não encontrado"));

        log.info("Produto consultado. id={}", id);

        return mapearParaResponse(produto);
    }

    public ProdutoResponse atualizar(
            Long id,
            ProdutoAtualizacaoRequest request) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException("Produto não encontrado"));

        if (request.getNome() != null) {
            produto.setNome(request.getNome());
        }

        if (request.getDescricao() != null) {
            produto.setDescricao(request.getDescricao());
        }

        if (request.getPreco() != null) {
            produto.setPreco(request.getPreco());
        }

        if (request.getAtivo() != null) {
            produto.setAtivo(request.getAtivo());
        }

        Produto produtoAtualizado = produtoRepository.save(produto);

        log.info("Produto atualizado com sucesso. id={}", id);

        return mapearParaResponse(produtoAtualizado);
    }

    public void excluir(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ProdutoNaoEncontradoException("Produto não encontrado"));

        produtoRepository.delete(produto);

        log.info("Produto excluído com sucesso. id={}", id);
    }

    private ProdutoResponse mapearParaResponse(Produto produto) {

        ProdutoResponse response = new ProdutoResponse();

        response.setId(produto.getId());
        response.setNome(produto.getNome());
        response.setDescricao(produto.getDescricao());
        response.setPreco(produto.getPreco());
        response.setAtivo(produto.getAtivo());

        return response;
    }
}
