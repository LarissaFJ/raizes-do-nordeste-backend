package br.com.raizesdonordeste.backend;

import br.com.raizesdonordeste.backend.dto.request.AuthCadastroRequest;
import br.com.raizesdonordeste.backend.entity.Cliente;
import br.com.raizesdonordeste.backend.entity.Usuario;
import br.com.raizesdonordeste.backend.repository.ClienteRepository;
import br.com.raizesdonordeste.backend.repository.UsuarioRepository;
import br.com.raizesdonordeste.backend.security.JwtService;
import br.com.raizesdonordeste.backend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsentimentoLgpdTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    private AuthService authService;

    @BeforeEach
    void configurar() {
        authService = new AuthService(
                usuarioRepository, clienteRepository, passwordEncoder, jwtService);
    }

    @Test
    void cadastraQuandoConsentimentoFoiConcedido() {
        AuthCadastroRequest request = novaRequisicao(true);
        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(clienteRepository.existsByCpf(request.getCpf())).thenReturn(false);
        when(passwordEncoder.encode(request.getSenha())).thenReturn("senha-hash");
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente cliente = invocation.getArgument(0);
            cliente.setId(1L);
            return cliente;
        });
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuario = invocation.getArgument(0);
            usuario.setId(1L);
            return usuario;
        });
        var response = authService.cadastrar(request);

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository).save(clienteCaptor.capture());
        assertEquals(true, clienteCaptor.getValue().isConsentimentoLgpd());
        assertNotNull(response);
    }

    @Test
    void rejeitaCadastroQuandoConsentimentoNaoFoiConcedido() {
        AuthCadastroRequest request = novaRequisicao(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authService.cadastrar(request));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(clienteRepository, never()).save(any(Cliente.class));
        verify(usuarioRepository, never()).save(any());
    }

    private AuthCadastroRequest novaRequisicao(boolean consentimento) {
        AuthCadastroRequest request = new AuthCadastroRequest();
        request.setNome("Cliente Teste");
        request.setCpf("12345678900");
        request.setEmail("cliente@teste.com");
        request.setTelefone("81999999999");
        request.setSenha("senha123");
        request.setConsentimentoLgpd(consentimento);
        return request;
    }
}
