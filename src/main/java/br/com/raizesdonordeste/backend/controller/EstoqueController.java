package br.com.raizesdonordeste.backend.controller;

import br.com.raizesdonordeste.backend.dto.request.EstoqueAtualizacaoRequest;
import br.com.raizesdonordeste.backend.dto.request.EstoqueRequest;
import br.com.raizesdonordeste.backend.dto.response.EstoqueResponse;
import br.com.raizesdonordeste.backend.service.EstoqueService;
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
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    @PostMapping
    public ResponseEntity<EstoqueResponse> cadastrar(
            @RequestBody EstoqueRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estoqueService.cadastrar(request));
    }

    @GetMapping
    public ResponseEntity<List<EstoqueResponse>> listar() {

        return ResponseEntity.ok(estoqueService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(estoqueService.buscarPorId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EstoqueResponse> atualizar(
            @PathVariable Long id,
            @RequestBody EstoqueAtualizacaoRequest request) {

        return ResponseEntity.ok(
                estoqueService.atualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        estoqueService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
