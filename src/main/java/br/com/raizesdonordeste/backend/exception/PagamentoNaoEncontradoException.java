package br.com.raizesdonordeste.backend.exception;

public class PagamentoNaoEncontradoException extends RuntimeException{

    public PagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
