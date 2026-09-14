package br.com.raizesdonordeste.backend.controller;

import br.com.raizesdonordeste.backend.dto.request.PedidoAtualizacaoRequest;
import br.com.raizesdonordeste.backend.dto.request.PedidoRequest;
import br.com.raizesdonordeste.backend.dto.response.PedidoResponse;
import br.com.raizesdonordeste.backend.service.PedidoService;
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
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> cadastrar(
            @RequestBody PedidoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedidoService.cadastrar(request));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar() {

        return ResponseEntity.ok(pedidoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PedidoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody PedidoAtualizacaoRequest request) {

        return ResponseEntity.ok(
                pedidoService.atualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        pedidoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
