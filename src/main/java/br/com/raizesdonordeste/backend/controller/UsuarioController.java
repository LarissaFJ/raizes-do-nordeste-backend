package br.com.raizesdonordeste.backend.controller;

import br.com.raizesdonordeste.backend.dto.request.UsuarioRequest;
import br.com.raizesdonordeste.backend.dto.response.UsuarioResponse;
import br.com.raizesdonordeste.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody UsuarioRequest request) {
        usuarioService.cadastrar(request);
        return ResponseEntity.status(201).build();
    }
}