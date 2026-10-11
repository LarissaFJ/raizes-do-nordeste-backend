package br.com.raizesdonordeste.backend.exception;

public class ValorPagamentoDivergenteException extends RuntimeException {

    public ValorPagamentoDivergenteException(String mensagem) {
        super(mensagem);
    }
}
