package br.com.raizesdonordeste.backend.controller;

import br.com.raizesdonordeste.backend.dto.request.AuditoriaRequest;
import br.com.raizesdonordeste.backend.dto.response.AuditoriaResponse;
import br.com.raizesdonordeste.backend.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auditorias")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @PostMapping
    public ResponseEntity<AuditoriaResponse> registrar(
            @RequestBody AuditoriaRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(auditoriaService.registrar(request));
    }

    @GetMapping
    public ResponseEntity<List<AuditoriaResponse>> listar() {

        return ResponseEntity.ok(auditoriaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(auditoriaService.buscarPorId(id));
    }
}
