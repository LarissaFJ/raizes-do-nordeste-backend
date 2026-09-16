package br.com.raizesdonordeste.backend.exception;

public class PedidoJaConfirmadoException extends RuntimeException {

    public PedidoJaConfirmadoException(String mensagem) {
        super(mensagem);
    }
}
