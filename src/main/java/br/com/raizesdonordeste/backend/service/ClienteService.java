package br.com.raizesdonordeste.backend.service;

import br.com.raizesdonordeste.backend.dto.request.ClienteAtualizacaoRequest;
import br.com.raizesdonordeste.backend.dto.request.ClienteRequest;
import br.com.raizesdonordeste.backend.dto.response.ClienteResponse;
import br.com.raizesdonordeste.backend.entity.Cliente;
import br.com.raizesdonordeste.backend.entity.Usuario;
import br.com.raizesdonordeste.backend.exception.ClienteNaoEncontradoException;
import br.com.raizesdonordeste.backend.exception.CpfJaCadastradoException;
import br.com.raizesdonordeste.backend.repository.ClienteRepository;
import br.com.raizesdonordeste.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;


    public ClienteResponse cadastrar(ClienteRequest request) {

        if (clienteRepository.existsByCpf(request.getCpf())) {
            throw new CpfJaCadastradoException("CPF já cadastrado");
        }

        Cliente cliente = Cliente.builder()
                .nome(request.getNome())
                .cpf(request.getCpf())
                .email(request.getEmail())
                .telefone(request.getTelefone())
                .pontosFidelidade(0)
                .dataCadastro(LocalDateTime.now())
                .build();

        Cliente clienteSalvo = clienteRepository.save(cliente);

        log.info("Cliente cadastrado com sucesso. id={}", clienteSalvo.getId());

        return mapearParaResponse(clienteSalvo);
    }

    public List<ClienteResponse> listar() {

        log.info("Listando clientes");

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        boolean clienteLogado = authentication.getAuthorities().contains(
                new SimpleGrantedAuthority("ROLE_CLIENTE"));

        List<Cliente> clientes;
        if (clienteLogado) {
            Long clienteId = obterClienteIdDoUsuarioLogado();
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() ->
                            new ClienteNaoEncontradoException("Cliente não encontrado"));
            clientes = List.of(cliente);
        } else {
            clientes = clienteRepository.findAll();
        }

        List<ClienteResponse> responses = new ArrayList<>();
        for (Cliente cliente : clientes) {
            responses.add(mapearParaResponse(cliente));
        }
        return responses;
    }
    public ClienteResponse buscarPorId(Long id) {

        verificarAcessoAoCliente(id);

        log.info("Buscando cliente. id={}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException("Cliente não encontrado"));

        return mapearParaResponse(cliente);
    }

    public ClienteResponse atualizar(Long id, ClienteAtualizacaoRequest request) {

        verificarAcessoAoCliente(id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException("Cliente não encontrado"));

        if (request.getNome() != null) {
            cliente.setNome(request.getNome());
        }

        if (request.getCpf() != null) {
            clienteRepository.findByCpf(request.getCpf())
                    .filter(clienteComMesmoCpf -> !clienteComMesmoCpf.getId().equals(cliente.getId()))
                    .ifPresent(clienteComMesmoCpf -> {
                        throw new CpfJaCadastradoException("CPF já cadastrado");
                    });

            cliente.setCpf(request.getCpf());
        }

        if (request.getEmail() != null) {
            cliente.setEmail(request.getEmail());
        }

        if (request.getTelefone() != null) {
            cliente.setTelefone(request.getTelefone());
        }

        Cliente clienteAtualizado = clienteRepository.save(cliente);

        log.info("Cliente atualizado com sucesso. id={}", clienteAtualizado.getId());

        return mapearParaResponse(clienteAtualizado);
    }

    public void excluir(Long id) {

        verificarAcessoAoCliente(id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException("Cliente não encontrado"));

        clienteRepository.delete(cliente);

        log.info("Cliente excluído com sucesso. id={}", id);
    }

    private Long obterClienteIdDoUsuarioLogado() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        Long usuarioId = Long.valueOf(authentication.getName());
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalStateException(
                        "Usuário autenticado não encontrado"));
        return usuario.getClienteId();
    }

    private void verificarAcessoAoCliente(Long clienteId) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        boolean clienteLogado = authentication.getAuthorities().contains(
                new SimpleGrantedAuthority("ROLE_CLIENTE"));

        if (clienteLogado) {
            Long clienteIdLogado = obterClienteIdDoUsuarioLogado();
            if (!java.util.Objects.equals(clienteIdLogado, clienteId)) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Você não tem acesso a este cliente");
            }
        }
    }
    private ClienteResponse mapearParaResponse(Cliente cliente) {

        ClienteResponse response = new ClienteResponse();

        response.setId(cliente.getId());
        response.setNome(cliente.getNome());
        response.setCpf(cliente.getCpf());
        response.setEmail(cliente.getEmail());
        response.setTelefone(cliente.getTelefone());
        response.setPontosFidelidade(cliente.getPontosFidelidade());
        response.setDataCadastro(cliente.getDataCadastro());

        return response;
    }
}
