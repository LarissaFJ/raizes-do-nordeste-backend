package br.com.raizesdonordeste.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleUsuarioInvalidoException extends RuntimeException {

    public RoleUsuarioInvalidoException(String mensagem) {
        super(mensagem);
    }
}