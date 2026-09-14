package br.com.raizesdonordeste.backend.controller;

import br.com.raizesdonordeste.backend.dto.request.PagamentoAtualizacaoRequest;
import br.com.raizesdonordeste.backend.dto.request.PagamentoRequest;
import br.com.raizesdonordeste.backend.dto.response.PagamentoResponse;
import br.com.raizesdonordeste.backend.service.PagamentoService;
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
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoResponse> cadastrar(
            @RequestBody PagamentoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pagamentoService.cadastrar(request));
    }

    @GetMapping
    public ResponseEntity<List<PagamentoResponse>> listar() {

        return ResponseEntity.ok(pagamentoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(pagamentoService.buscarPorId(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        pagamentoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
