package br.com.raizesdonordeste.backend.service;

import br.com.raizesdonordeste.backend.dto.response.FidelidadeResponse;
import br.com.raizesdonordeste.backend.entity.Cliente;
import br.com.raizesdonordeste.backend.entity.Usuario;
import br.com.raizesdonordeste.backend.exception.ClienteNaoEncontradoException;
import br.com.raizesdonordeste.backend.repository.ClienteRepository;
import br.com.raizesdonordeste.backend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FidelidadeService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public FidelidadeResponse buscarPorClienteId(Long clienteId) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException("Cliente não encontrado"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        boolean clienteLogado = authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_CLIENTE"));

        if (clienteLogado) {
            Long usuarioId = Long.valueOf(authentication.getName());
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new IllegalStateException(
                            "Usuário autenticado não encontrado"));

            if (!java.util.Objects.equals(usuario.getClienteId(), clienteId)) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Você não tem acesso a esta fidelidade");
            }
        }

        log.info("Fidelidade consultada. clienteId={}", clienteId);

        FidelidadeResponse response = new FidelidadeResponse();
        response.setClienteId(cliente.getId());
        response.setPontos(cliente.getPontosFidelidade());

        return response;
    }
}