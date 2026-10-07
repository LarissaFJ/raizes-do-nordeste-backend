package br.com.raizesdonordeste.backend.controller;

import br.com.raizesdonordeste.backend.dto.request.AuthCadastroRequest;
import br.com.raizesdonordeste.backend.dto.request.AuthLoginRequest;
import br.com.raizesdonordeste.backend.dto.response.AuthResponse;
import br.com.raizesdonordeste.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody AuthCadastroRequest request) {
        try {
            return ResponseEntity.ok(authService.cadastrar(request));
        } catch (ResponseStatusException exception) {
            return ResponseEntity
                    .status(exception.getStatusCode())
                    .body(exception.getReason());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
