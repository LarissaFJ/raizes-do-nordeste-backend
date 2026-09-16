package br.com.raizesdonordeste.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ClienteNaoEncontradoException.class,
            UnidadeNaoEncontradaException.class,
            ProdutoNaoEncontradoException.class,
            EstoqueNaoEncontradoException.class,
            PedidoNaoEncontradoException.class,
            PagamentoNaoEncontradoException.class,
            AuditoriaNaoEncontradaException.class
    })
    public ResponseEntity<String> tratarNaoEncontrado(RuntimeException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({
            EstoqueInsuficienteException.class,
            PedidoJaConfirmadoException.class
    })
    public ResponseEntity<String> tratarConflito(RuntimeException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
}


