package br.com.raizesdonordeste.backend.exception;

public class EstoqueNaoEncontradoException extends RuntimeException{

    public EstoqueNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
