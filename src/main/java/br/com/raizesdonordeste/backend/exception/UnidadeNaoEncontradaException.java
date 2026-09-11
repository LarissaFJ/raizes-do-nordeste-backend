package br.com.raizesdonordeste.backend.exception;

public class UnidadeNaoEncontradaException extends RuntimeException{

    public UnidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
