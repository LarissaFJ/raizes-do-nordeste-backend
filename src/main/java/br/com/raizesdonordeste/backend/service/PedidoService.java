package br.com.raizesdonordeste.backend.service;

import br.com.raizesdonordeste.backend.dto.request.AuditoriaRequest;
import br.com.raizesdonordeste.backend.dto.request.ItemPedidoRequest;
import br.com.raizesdonordeste.backend.dto.request.PedidoAtualizacaoRequest;
import br.com.raizesdonordeste.backend.dto.request.PedidoRequest;
import br.com.raizesdonordeste.backend.dto.response.PedidoResponse;
import br.com.raizesdonordeste.backend.entity.Cliente;
import br.com.raizesdonordeste.backend.entity.Estoque;
import br.com.raizesdonordeste.backend.entity.ItemPedido;
import br.com.raizesdonordeste.backend.entity.Pedido;
import br.com.raizesdonordeste.backend.entity.Produto;
import br.com.raizesdonordeste.backend.exception.ClienteNaoEncontradoException;
import br.com.raizesdonordeste.backend.exception.EstoqueInsuficienteException;
import br.com.raizesdonordeste.backend.exception.EstoqueNaoEncontradoException;
import br.com.raizesdonordeste.backend.exception.PedidoNaoEncontradoException;
import br.com.raizesdonordeste.backend.exception.ProdutoNaoEncontradoException;
import br.com.raizesdonordeste.backend.repository.EstoqueRepository;
import br.com.raizesdonordeste.backend.repository.ClienteRepository;
import br.com.raizesdonordeste.backend.repository.ItemPedidoRepository;
import br.com.raizesdonordeste.backend.repository.PedidoRepository;
import br.com.raizesdonordeste.backend.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final EstoqueRepository estoqueRepository;
    private final ClienteRepository clienteRepository;
    private final AuditoriaService auditoriaService;

    @Transactional
    public PedidoResponse cadastrar(PedidoRequest request) {

        Map<Long, Integer> quantidadesPorProduto = new HashMap<>();

        for (ItemPedidoRequest itemRequest : request.getItens()) {
            produtoRepository.findById(itemRequest.getProdutoId())
                    .orElseThrow(() ->
                            new ProdutoNaoEncontradoException("Produto não encontrado"));

            quantidadesPorProduto.merge(
                    itemRequest.getProdutoId(), itemRequest.getQuantidade(), Integer::sum);
        }

        for (Map.Entry<Long, Integer> item : quantidadesPorProduto.entrySet()) {
            Estoque estoque = estoqueRepository
                    .findByUnidadeIdAndProdutoId(request.getUnidadeId(), item.getKey())
                    .orElseThrow(() -> new EstoqueNaoEncontradoException(
                            "Estoque não encontrado para o produto " + item.getKey()));

            if (estoque.getQuantidade() < item.getValue()) {
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para o produto " + item.getKey());
            }
        }

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException("Cliente não encontrado"));

        Pedido pedido = Pedido.builder()
                .clienteId(request.getClienteId())
                .unidadeId(request.getUnidadeId())
                .canalPedido(request.getCanalPedido())
                .dataPedido(LocalDateTime.now())
                .statusPedido("PENDENTE")
                .desconto(BigDecimal.ZERO)
                .valorTotal(BigDecimal.ZERO)
                .build();

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        BigDecimal subtotal = BigDecimal.ZERO;

        for (ItemPedidoRequest itemRequest : request.getItens()) {

            Produto produto = produtoRepository.findById(itemRequest.getProdutoId())
                    .orElseThrow(() ->
                            new ProdutoNaoEncontradoException("Produto não encontrado"));

            BigDecimal valorItem = produto.getPreco()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantidade()));

            ItemPedido itemPedido = ItemPedido.builder()
                    .pedidoId(pedidoSalvo.getId())
                    .produtoId(produto.getId())
                    .quantidade(itemRequest.getQuantidade())
                    .precoUnitario(produto.getPreco())
                    .build();

            itemPedidoRepository.save(itemPedido);

            subtotal = subtotal.add(valorItem);
        }

        BigDecimal desconto;
        String tipoDesconto = null;
        int pontosFidelidade = cliente.getPontosFidelidade() != null
                ? cliente.getPontosFidelidade()
                : 0;
        int percentualDesconto = 0;
        if (request.getDesconto() != null
                && request.getDesconto().compareTo(BigDecimal.ZERO) > 0) {
            desconto = request.getDesconto();
        } else {
            percentualDesconto = pontosFidelidade >= 300 ? 15
                    : pontosFidelidade >= 200 ? 10
                    : pontosFidelidade >= 100 ? 5
                    : 0;

            desconto = subtotal.multiply(BigDecimal.valueOf(percentualDesconto))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            if (desconto.compareTo(BigDecimal.ZERO) > 0) {
                tipoDesconto = "DESCONTO_FIDELIDADE";
            }
        }

        BigDecimal valorTotal = subtotal.subtract(desconto);

        pedidoSalvo.setDesconto(desconto);
        pedidoSalvo.setValorTotal(valorTotal);

        if ("DESCONTO_FIDELIDADE".equals(tipoDesconto)) {
            AuditoriaRequest auditoriaRequest = new AuditoriaRequest();
            auditoriaRequest.setPedidoId(pedidoSalvo.getId());
            auditoriaRequest.setTipoOperacao("DESCONTO_FIDELIDADE");
            auditoriaRequest.setDescricao(
                    "Desconto de fidelidade aplicado com " + pontosFidelidade
                            + " pontos, percentual de " + percentualDesconto
                            + "% e valor de " + desconto);
            auditoriaService.registrar(auditoriaRequest);
        }

        Pedido pedidoAtualizado = pedidoRepository.save(pedidoSalvo);

        log.info("Pedido cadastrado com sucesso. id={}", pedidoAtualizado.getId());

        return mapearParaResponse(pedidoAtualizado);
    }

    public List<PedidoResponse> listar() {

        List<Pedido> pedidos = pedidoRepository.findAll();

        List<PedidoResponse> responses = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            responses.add(mapearParaResponse(pedido));
        }

        log.info("Lista de pedidos consultada. quantidade={}", responses.size());

        return responses;
    }

    public PedidoResponse buscarPorId(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new PedidoNaoEncontradoException("Pedido não encontrado"));

        log.info("Pedido consultado. id={}", id);

        return mapearParaResponse(pedido);
    }

    public PedidoResponse atualizar(
            Long id,
            PedidoAtualizacaoRequest request) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new PedidoNaoEncontradoException("Pedido não encontrado"));

        BigDecimal descontoAntigo = pedido.getDesconto() != null
                ? pedido.getDesconto()
                : BigDecimal.ZERO;

        String statusAntigo = pedido.getStatusPedido();

        if (request.getUnidadeId() != null) {
            pedido.setUnidadeId(request.getUnidadeId());
        }

        if (request.getCanalPedido() != null) {
            pedido.setCanalPedido(request.getCanalPedido());
        }

        if (request.getStatusPedido() != null) {

            pedido.setStatusPedido(request.getStatusPedido());

            if (!request.getStatusPedido().equals(statusAntigo)
                    && request.getStatusPedido().equals("CANCELADO")) {

                AuditoriaRequest auditoriaRequest = new AuditoriaRequest();

                auditoriaRequest.setPedidoId(id);
                auditoriaRequest.setTipoOperacao("CANCELAMENTO");
                auditoriaRequest.setDescricao("Pedido cancelado");

                auditoriaService.registrar(auditoriaRequest);
            }
        }

        if (request.getDesconto() != null) {

            BigDecimal novoDesconto = request.getDesconto();

            pedido.setDesconto(novoDesconto);

            List<ItemPedido> itens = itemPedidoRepository.findByPedidoId(id);

            BigDecimal subtotal = BigDecimal.ZERO;

            for (ItemPedido item : itens) {

                BigDecimal valorItem = item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade()));

                subtotal = subtotal.add(valorItem);
            }

            BigDecimal valorTotal = subtotal.subtract(novoDesconto);

            pedido.setValorTotal(valorTotal);

            if (!novoDesconto.equals(descontoAntigo)) {

                AuditoriaRequest auditoriaRequest = new AuditoriaRequest();

                auditoriaRequest.setPedidoId(id);
                auditoriaRequest.setTipoOperacao("ALTERACAO_DESCONTO");
                auditoriaRequest.setDescricao(
                        "Desconto alterado de "
                                + descontoAntigo
                                + " para "
                                + novoDesconto
                );

                auditoriaService.registrar(auditoriaRequest);
            }
        }

        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        log.info("Pedido atualizado com sucesso. id={}", pedidoAtualizado.getId());

        return mapearParaResponse(pedidoAtualizado);
    }

    public void excluir(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new PedidoNaoEncontradoException("Pedido não encontrado"));

        pedidoRepository.delete(pedido);

        log.info("Pedido excluído com sucesso. id={}", id);
    }

    private PedidoResponse mapearParaResponse(Pedido pedido) {

        PedidoResponse response = new PedidoResponse();

        response.setId(pedido.getId());
        response.setClienteId(pedido.getClienteId());
        response.setUnidadeId(pedido.getUnidadeId());
        response.setCanalPedido(pedido.getCanalPedido());
        response.setDataPedido(pedido.getDataPedido());
        response.setStatusPedido(pedido.getStatusPedido());
        response.setDesconto(pedido.getDesconto());
        response.setValorTotal(pedido.getValorTotal());

        return response;
    }
}

