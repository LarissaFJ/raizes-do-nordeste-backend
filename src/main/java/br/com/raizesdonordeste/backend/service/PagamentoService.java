package br.com.raizesdonordeste.backend.service;

import br.com.raizesdonordeste.backend.dto.request.PagamentoAtualizacaoRequest;
import br.com.raizesdonordeste.backend.dto.request.PagamentoRequest;
import br.com.raizesdonordeste.backend.dto.response.PagamentoResponse;
import br.com.raizesdonordeste.backend.entity.Pagamento;
import br.com.raizesdonordeste.backend.entity.Pedido;
import br.com.raizesdonordeste.backend.exception.PagamentoNaoEncontradoException;
import br.com.raizesdonordeste.backend.exception.PedidoNaoEncontradoException;
import br.com.raizesdonordeste.backend.repository.PagamentoRepository;
import br.com.raizesdonordeste.backend.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

    public PagamentoResponse cadastrar(PagamentoRequest request) {

        Pedido pedido = pedidoRepository.findById(request.getPedidoId())
                .orElseThrow(() ->
                        new PedidoNaoEncontradoException("Pedido não encontrado"));

        Pagamento pagamento = Pagamento.builder()
                .pedidoId(request.getPedidoId())
                .formaPagamento(request.getFormaPagamento())
                .valor(request.getValor())
                .status("APROVADO")
                .dataPagamento(LocalDateTime.now())
                .build();

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);

        pedido.setStatusPedido("CONFIRMADO");
        pedidoRepository.save(pedido);

        log.info("Pagamento registrado com sucesso. id={}", pagamentoSalvo.getId());

        return mapearParaResponse(pagamentoSalvo);
    }

    public List<PagamentoResponse> listar() {

        List<Pagamento> pagamentos = pagamentoRepository.findAll();

        List<PagamentoResponse> responses = new ArrayList<>();

        for (Pagamento pagamento : pagamentos) {
            responses.add(mapearParaResponse(pagamento));
        }

        log.info("Lista de pagamentos consultada. quantidade={}", responses.size());

        return responses;
    }

    public PagamentoResponse buscarPorId(Long id) {

        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() ->
                        new PagamentoNaoEncontradoException("Pagamento não encontrado"));

        log.info("Pagamento consultado. id={}", id);

        return mapearParaResponse(pagamento);
    }

    public void excluir(Long id) {

        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() ->
                        new PagamentoNaoEncontradoException("Pagamento não encontrado"));

        pagamentoRepository.delete(pagamento);

        log.info("Pagamento excluído com sucesso. id={}", id);
    }

    private PagamentoResponse mapearParaResponse(Pagamento pagamento) {

        PagamentoResponse response = new PagamentoResponse();

        response.setId(pagamento.getId());
        response.setPedidoId(pagamento.getPedidoId());
        response.setFormaPagamento(pagamento.getFormaPagamento());
        response.setValor(pagamento.getValor());
        response.setStatus(pagamento.getStatus());
        response.setDataPagamento(pagamento.getDataPagamento());

        return response;
    }
}
