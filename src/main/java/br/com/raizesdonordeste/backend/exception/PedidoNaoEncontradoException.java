package br.com.raizesdonordeste.backend.exception;

public class PedidoNaoEncontradoException extends RuntimeException{

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
