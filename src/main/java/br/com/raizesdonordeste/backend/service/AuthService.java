package br.com.raizesdonordeste.backend.service;

import br.com.raizesdonordeste.backend.dto.request.AuthCadastroRequest;
import br.com.raizesdonordeste.backend.dto.request.AuthLoginRequest;
import br.com.raizesdonordeste.backend.dto.response.AuthResponse;
import br.com.raizesdonordeste.backend.entity.Cliente;
import br.com.raizesdonordeste.backend.entity.Usuario;
import br.com.raizesdonordeste.backend.enums.Role;
import br.com.raizesdonordeste.backend.exception.CredenciaisInvalidasException;
import br.com.raizesdonordeste.backend.exception.EmailJaCadastradoException;
import br.com.raizesdonordeste.backend.exception.CpfJaCadastradoException;
import br.com.raizesdonordeste.backend.repository.ClienteRepository;
import br.com.raizesdonordeste.backend.repository.UsuarioRepository;
import br.com.raizesdonordeste.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse cadastrar(AuthCadastroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }

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

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(Role.CLIENTE)
                .clienteId(clienteSalvo.getId())
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        log.info("Usuário cadastrado com sucesso. usuarioId={}, clienteId={}",
                usuarioSalvo.getId(), clienteSalvo.getId());

        return criarResposta(usuarioSalvo, clienteSalvo);
    }

    public AuthResponse login(AuthLoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CredenciaisInvalidasException("Credenciais inválidas"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException("Credenciais inválidas");
        }

        Cliente cliente = null;
        if (usuario.getClienteId() != null) {
            cliente = clienteRepository.findById(usuario.getClienteId())
                    .orElseThrow(() -> new IllegalStateException(
                            "Cliente associado ao usuário não encontrado"));
        }

        log.info("Login realizado com sucesso. usuarioId={}, clienteId={}",
                usuario.getId(), usuario.getClienteId());

        AuthResponse response;
        if (cliente != null) {
            response = criarResposta(usuario, cliente);
        } else {
            response = new AuthResponse();
            AuthResponse.User user = new AuthResponse.User();
            user.setId(usuario.getId());
            user.setNome(null);
            user.setPerfil(usuario.getRole());
            response.setUser(user);
        }

        response.setAccessToken(jwtService.gerarToken(usuario));
        response.setTokenType("Bearer");
        response.setExpiresIn(3600L);
        return response;
    }
    private AuthResponse criarResposta(Usuario usuario, Cliente cliente) {
        AuthResponse response = new AuthResponse();
        AuthResponse.User user = new AuthResponse.User();
        user.setId(cliente.getId());
        user.setNome(cliente.getNome());
        user.setPerfil(usuario.getRole());
        response.setUser(user);
        return response;
    }
}
