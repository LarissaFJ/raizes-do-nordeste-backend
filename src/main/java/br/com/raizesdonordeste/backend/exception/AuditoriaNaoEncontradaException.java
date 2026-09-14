package br.com.raizesdonordeste.backend.exception;

public class AuditoriaNaoEncontradaException extends RuntimeException{

    public AuditoriaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
