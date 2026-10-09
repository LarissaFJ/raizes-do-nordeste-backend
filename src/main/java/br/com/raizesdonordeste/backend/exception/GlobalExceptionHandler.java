package br.com.raizesdonordeste.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> tratarCorpoInvalido(
            HttpMessageNotReadableException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Corpo da requisição inválido ou com tipos incompatíveis");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacao(
            MethodArgumentNotValidException exception) {
        Map<String, String> erros = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(erro ->
                erros.put(erro.getField(), erro.getDefaultMessage()));
        return ResponseEntity.badRequest().body(erros);
    }


    @ExceptionHandler({
            ClienteNaoEncontradoException.class,
            UnidadeNaoEncontradaException.class,
            ProdutoNaoEncontradoException.class,
            EstoqueNaoEncontradoException.class,
            PedidoNaoEncontradoException.class,
            PagamentoNaoEncontradoException.class,
            AuditoriaNaoEncontradaException.class,
            RoleUsuarioInvalidoException.class
    })
    public ResponseEntity<String> tratarNaoEncontrado(RuntimeException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({
            EstoqueInsuficienteException.class,
            PedidoJaConfirmadoException.class,
            CpfJaCadastradoException.class,
            EmailJaCadastradoException.class
    })
    public ResponseEntity<String> tratarConflito(RuntimeException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(FidelidadeAcessoNegadoException.class)
    public ResponseEntity<Void> tratarAcessoFidelidade(FidelidadeAcessoNegadoException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<String> tratarCredenciaisInvalidas(CredenciaisInvalidasException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }
}
