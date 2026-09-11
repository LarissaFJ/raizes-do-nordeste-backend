package br.com.raizesdonordeste.backend.controller;

import br.com.raizesdonordeste.backend.dto.request.UnidadeAtualizacaoRequest;
import br.com.raizesdonordeste.backend.dto.request.UnidadeRequest;
import br.com.raizesdonordeste.backend.dto.response.UnidadeResponse;
import br.com.raizesdonordeste.backend.service.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/unidades")
@RequiredArgsConstructor
public class UnidadeController {

    private final UnidadeService unidadeService;

    @PostMapping
    public ResponseEntity<UnidadeResponse> cadastrar(
            @RequestBody UnidadeRequest request) {

        UnidadeResponse response = unidadeService.cadastrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UnidadeResponse>> listar() {

        return ResponseEntity.ok(unidadeService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(unidadeService.buscarPorId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UnidadeResponse> atualizar(
            @PathVariable Long id,
            @RequestBody UnidadeAtualizacaoRequest request) {

        return ResponseEntity.ok(
                unidadeService.atualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        unidadeService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
