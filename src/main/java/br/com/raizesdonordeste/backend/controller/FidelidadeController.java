package br.com.raizesdonordeste.backend.controller;

import br.com.raizesdonordeste.backend.dto.response.FidelidadeResponse;
import br.com.raizesdonordeste.backend.service.FidelidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fidelidade")
@RequiredArgsConstructor
public class FidelidadeController {

    private final FidelidadeService fidelidadeService;

    @GetMapping("/{id}")
    public ResponseEntity<FidelidadeResponse> buscarPorClienteId(
            @PathVariable Long id) {

        return ResponseEntity.ok(fidelidadeService.buscarPorClienteId(id));
    }
}
