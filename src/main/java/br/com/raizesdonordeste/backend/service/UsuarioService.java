package br.com.raizesdonordeste.backend.service;

import br.com.raizesdonordeste.backend.dto.request.UsuarioRequest;
import br.com.raizesdonordeste.backend.dto.response.UsuarioResponse;
import br.com.raizesdonordeste.backend.entity.Usuario;
import br.com.raizesdonordeste.backend.enums.Role;
import br.com.raizesdonordeste.backend.exception.EmailJaCadastradoException;
import br.com.raizesdonordeste.backend.exception.RoleUsuarioInvalidoException;
import br.com.raizesdonordeste.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UsuarioResponse> listar() {
        List<UsuarioResponse> responses = new java.util.ArrayList<>();

        for (Usuario usuario : usuarioRepository.findAll()) {
            UsuarioResponse response = new UsuarioResponse();
            response.setId(usuario.getId());
            response.setEmail(usuario.getEmail());
            response.setRole(usuario.getRole());
            responses.add(response);
        }

        return responses;
    }

    @Transactional
    public void cadastrar(UsuarioRequest request) {
        if (request.getRole() == null || request.getRole() == Role.CLIENTE) {
            throw new RoleUsuarioInvalidoException(
                    "A role deve ser ADMIN ou ATENDENTE");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(request.getRole())
                .clienteId(null)
                .build();

        usuarioRepository.save(usuario);
    }
}