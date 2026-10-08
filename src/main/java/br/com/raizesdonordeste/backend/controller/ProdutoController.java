package br.com.raizesdonordeste.backend.controller;

import jakarta.validation.Valid;

import br.com.raizesdonordeste.backend.dto.request.ProdutoAtualizacaoRequest;
import br.com.raizesdonordeste.backend.dto.request.ProdutoRequest;
import br.com.raizesdonordeste.backend.dto.response.ProdutoResponse;
import br.com.raizesdonordeste.backend.service.ProdutoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrar(
            @RequestBody @Valid ProdutoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produtoService.cadastrar(request));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar() {

        return ResponseEntity.ok(produtoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoAtualizacaoRequest request) {

        return ResponseEntity.ok(
                produtoService.atualizar(id, request)
        );
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        produtoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
