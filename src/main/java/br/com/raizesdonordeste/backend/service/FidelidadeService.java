package br.com.raizesdonordeste.backend.service;

import br.com.raizesdonordeste.backend.dto.response.FidelidadeResponse;
import br.com.raizesdonordeste.backend.entity.Cliente;
import br.com.raizesdonordeste.backend.exception.ClienteNaoEncontradoException;
import br.com.raizesdonordeste.backend.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FidelidadeService {

    private final ClienteRepository clienteRepository;

    public FidelidadeResponse buscarPorClienteId(Long clienteId) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException("Cliente não encontrado"));

        log.info("Fidelidade consultada. clienteId={}", clienteId);

        FidelidadeResponse response = new FidelidadeResponse();
        response.setClienteId(cliente.getId());
        response.setPontos(cliente.getPontosFidelidade());

        return response;
    }
}